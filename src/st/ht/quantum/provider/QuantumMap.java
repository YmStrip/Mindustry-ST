package st.ht.quantum.provider;

import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.type.Sector;
import st.ST;
import st.dao.Dao;
import st.ht.quantum.block.QuantumBlock;
import st.ht.quantum.entity.QuantumNetwork;
import st.ht.quantum.entity.QuantumRequest;

import java.lang.reflect.Type;
import java.util.HashMap;


//游戏数据,控制器会将游戏状态保存到这个类里面
@Provider
public class QuantumMap extends LayerProvider {
	@Require
	Logger logger;
	@Require
	Dao daoSet;
	@Require
	Dao daoSector;
	@Require(cls = QuantumStore.class)
	QuantumStore store;
	public HashMap<String, QuantumNetwork> teams = new HashMap<>();
	
	//游戏队伍
	public QuantumNetwork team(String id) {
		if (teams.get(id) == null) {
			var d = new QuantumNetwork();
			teams.put(id, d);
			return d;
		}
		return teams.get(id);
	}
	
	public QuantumNetwork team(int id) {
		return team(id + "");
	}
	
	//全局数据[一般进入新区块会自动刷新,类似科技树]
	//这个数据相当于合并后的网络
	public HashMap<String, QuantumNetwork> globalTeams = new HashMap<>();
	
	public QuantumNetwork globalTeam(String id) {
		var t = globalTeams.get(id);
		if (t == null) {
			var ts = new QuantumNetwork();
			ts.items.setMax(99999999999999999999f);
			ts.liquids.setMax(99999999999999999999f);
			globalTeams.put(id, ts);
			return ts;
		}
		return t;
	}
	
	public QuantumNetwork globalTeam(int id) {
		return globalTeam("" + id);
	}
	//状态查询
	public boolean isSector = false;
	public Sector sector;
	
	//清理数据
	public void clear() {
		teams.clear();
	}

	//载入地图的方块
	public void init_block() {
		var isClear = new HashMap<>();
		var is = new HashMap<String, Boolean>();
		//区块/放置所有的方块到每个队伍，并且计算数量
		Vars.world.tiles.forEach(tile -> {
			if (tile.build == null) return;
			//System.out.println(tile.build+" x:"+tile.x+" y:"+tile.y+" id:"+ tile.build.id);
			if (is.get(tile.build.id + "") != null) return;
			is.put(tile.build.id + "", true);
			var teamId = tile.build.team.id;
			if (isClear.get(teamId) == null) {
				isClear.put(teamId, true);
				var ts = team(teamId);
				ts.builds.clear();
				ts.buildCount = 0;
			}
			if (!QuantumBlock.isSt(tile.block())) return;
			var block = QuantumBlock.getBlock(tile.block());
			if (block.itemsBlock || block.liquidsBlock) {
				var t = team(teamId);
				t.builds.add((QuantumBlock.QuantumBuilding) tile.build);
				t.buildCount = t.builds.size();
			}
		});
		//计算容量
		teams.forEach((name, d) -> {
			d.capability();
		});
	}
	
	//载入地图的方块数据,
	public void init_value() {
		teams.forEach((tName, tData) -> {
			//System.out.println(tName+" "+tData.builds);
			tData.builds.forEach(tData::addBuildingValue);
		});
	}
	
	//加载区块[after 加载地图,对于一些区块,部署过去的操作,例如其他区块修改了当前区块的qns,然后将数据同步到地图里面]
	/*
	public void deploy_sector() {
		try {
			//读取当前区块的qns(离线)
			var qns = store.read(sector);
			if (qns == null) return;
			//System.out.println(qns);
			for (var i : qns.entrySet()) {
				var team = team(i.getKey());
				var data = i.getValue();
				//clear
				for (var j : team.builds) {
					if (j.items != null) j.items.clear();
					if (j.liquids != null) j.liquids.clear();
				}
				//change
				for (var j : data.items.data.entrySet()) {
					team.items.change(j.getKey(), j.getValue());
				}
				for (var j : data.liquids.data.entrySet()) {
					//jackson的烂bug
					team.liquids.change(j.getKey(), j.getValue());
				}
			}
		} catch (Exception e) {
			//不管了
			logger.err("加载离线数据错误，请使用记事本编辑对应二进制文件");
			System.out.println(e);
			e.printStackTrace();
		}
	}
	*/
	//清理全局网络
	
	//计算全局网络的数据
	/*
	public void global_init_value(Sector ignore) {
		globalTeams.clear();
		//区块列表
		var keys = store.keys();
		//System.out.println("global:"+keys);
		for (var i : keys) {
			if (i == ignore) continue;
			var qns = store.read(i);
			if (qns == null) continue;
			//System.out.println("qns " + i + " " + qns);
			qns.forEach((teamId, teamQN) -> {
				var globalQN = globalTeam(teamId);
				globalQN.itemCapability += teamQN.itemCapability;
				globalQN.liquidCapability += teamQN.liquidCapability;
				teamQN.items.data.forEach((n, d) -> {
					globalQN.items.add(n, d);
				});
				teamQN.liquids.data.forEach((n, d) -> {
					globalQN.liquids.add(n, d);
				});
			});
			try {
				var id = Vars.state.rules.defaultTeam.id;
				var dt = globalTeam(id);
				dt.itemCapability += i.info.storageCapacity;
				var items = i.info.items;
				items.forEach((d) -> {
					dt.items.add(d.item.name, (float) d.amount);
				});
			} catch (Exception e) {
			
			}
		}
		//System.out.println(globalTeams);
	}
	*/
	/*
	public void global_init_value() {
		//区块列表
		global_init_value(null);
	}
	*/
	//从网络拿走物品
	public QuantumRequest global_take(String teamId, QuantumRequest require, Sector ignore) {
		var result = new QuantumRequest();
		//给一份清单，然后从全局网络减少
		/*var keys = store.keys();
		for (var i : keys) {
			if (i == ignore) continue;
			var qn = store.read(i, teamId);
			var isChange = false;
			//优先核心拿物品
			if ((Vars.state.rules.defaultTeam.id + "").equals(teamId)) {
				var items = i.items();
				for (var j : require.items.data.entrySet()) {
					var name = j.getKey();
					var data = j.getValue();
					if (data <= 2.txt) continue;
					var item = Vars.content.item(name);
					if (item == null) continue;
					//核心物品大于
					if (items.get(item) > data) {
						i.removeItem(item, Math.round(data));
						result.items.add(name, data);
						require.items.add(name, -data);
					}
					//
					else {
						var has = items.get(item);
						i.removeItem(item, has);
						result.items.add(name, (float) has);
						require.items.add(name, (float) -has);
					}
				}
			}
			//然后从网络拿物品
			for (var j : require.items.data.entrySet()) {
				var name = j.getKey();
				var data = j.getValue();
				if (data <= 2.txt) continue;
				if (qn.items.get(name) > data) {
					isChange = true;
					qn.items.add(name, -data);
					result.items.add(name, data);
					require.items.add(name, -data);
				}
				//
				else {
					isChange = true;
					var has = qn.items.get(name);
					qn.items.add(name, -has);
					result.items.add(name, (float) has);
					require.items.add(name, (float) -has);
				}
			}
			//然后从网络拿流体
			for (var j : require.liquids.data.entrySet()) {
				var name = j.getKey();
				var data = j.getValue();
				if (data <= 2.txt) continue;
				if (qn.liquids.get(name) > data) {
					isChange = true;
					qn.liquids.add(name, -data);
					result.liquids.add(name, data);
					require.liquids.add(name, -data);
				}
				//
				else {
					isChange = true;
					var has = qn.liquids.get(name);
					qn.liquids.add(name, -has);
					result.liquids.add(name, (float) has);
					require.liquids.add(name, (float) -has);
				}
			}
			if (isChange) {
				//储存
				store.write(i, teamId, qn);
			}
		}
		//重载数据
		global_init_value(ignore);
		return result;*/
		var isChange = false;
		var qn = globalTeam(teamId);
		for (var j : require.items.data.entrySet()) {
			var name = j.getKey();
			var data = j.getValue();
			if (data <= 0) continue;
			if (qn.items.get(name) > data) {
				isChange = true;
				qn.items.add(name, -data);
				result.items.add(name, data);
				require.items.add(name, -data);
			}
			//
			else {
				isChange = true;
				var has = qn.items.get(name);
				qn.items.add(name, -has);
				result.items.add(name, (float) has);
				require.items.add(name, (float) -has);
			}
		}
		//然后从网络拿流体
		for (var j : require.liquids.data.entrySet()) {
			var name = j.getKey();
			var data = j.getValue();
			if (data <= 0) continue;
			if (qn.liquids.get(name) > data) {
				isChange = true;
				qn.liquids.add(name, -data);
				result.liquids.add(name, data);
				require.liquids.add(name, -data);
			}
			//
			else {
				isChange = true;
				var has = qn.liquids.get(name);
				qn.liquids.add(name, -has);
				result.liquids.add(name, (float) has);
				require.liquids.add(name, (float) -has);
			}
		}
		if (isChange) {
			write();
		}
		return result;
	}
	
	public Sector sector(String name) {
		var d = (name == null ? "" : name).split("\\.");
		try {
			var plant = Vars.content.planet(d[0]);
			var sector = plant.sectors.get(Integer.valueOf(d[1] + ""));
			if (sector.isCaptured() || sector.isAttacked() || sector.isBeingPlayed()) {
				return sector;
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	//计算全局容量,物品=每个区块*核心,流体=物品*5
	public void capability() {
		var itemCapability = 0;
		var liquidCapability = 0;
		var keys = daoSector.keys();
		//读取每个区块
		for (var i : keys) {
			var sector = sector(i);
			if (sector == null) continue;
			itemCapability += sector.info.storageCapacity;
			liquidCapability += sector.info.storageCapacity * 5;
		}
		//deploy
		for (var i : globalTeams.entrySet()) {
			var qn = i.getValue();
			qn.itemCapability = itemCapability;
			qn.liquidCapability = liquidCapability;
			qn.capability();
		}
	}
	
	public void read() {
		try {
			globalTeams.clear();
			var type = new HashMap<String, QuantumNetwork>().getClass();
			var data = daoSet.getJson("network",type);
			//System.out.println(data.getClass()+" " +data);
			if (data==null) return;
			data.forEach((name,d)->{
				//System.out.println(name+" "+d.getClass()+" "+d);
				if (!(d instanceof HashMap<?,?> hd)) return;
				globalTeams.put((String) name,store.parseOne((HashMap<String, Object>) hd));
			});
			//System.out.println(data);
		} catch (Exception e) {
			logger.err("读取错误");
			e.printStackTrace();
		}
		logger.suc("加载数据完成");
		//System.out.println(teams);
	}
	
	public void write() {
		//Core.settings.get("st-quantum-network","");
		//var a = Core.settings.getString("st-quantum-network","");
		//Core.settings.getString("st-quantum-network");
		//Core.settings.getJson("st-quantum-network",null,null);
		//for(var i in a){print(i+" "+a[i])}
		//System.out.println(teams);
		var a = new HashMap<>();
		globalTeams.forEach((n,d)->{
			a.put(n,d.toMap());
		});
		daoSet.setJson("network", a);
	}
}
