package st.quamtum.provider.provider;

import layer.annotations.Provider;
import layer.annotations.Require;

import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.type.Sector;
import st.dao.Dao;
import st.quamtum.provider.entity.QuantumNetwork;
import st.quamtum.provider.entity.QuantumMap;
import st.quamtum.provider.entity.QuantumRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

//整个游戏的数据
@Provider
public class QuantumWorld extends LayerProvider {
	@Require
	Logger logger;
	@Require(cls = QuantumMapTile.class)
	QuantumMapTile quantumMap;
	//每一个区块都有一个map对象,储存当前的数值
	@Require(name = "sector")
	Dao<Object> sectorDB;
	public HashMap<String, QuantumNetwork> teams = new HashMap<>();
	
	public QuantumNetwork team(String id) {
		var t = teams.get(id);
		if (t == null) {
			var ts = new QuantumNetwork();
			ts.items.setMax(99999999999999999999f);
			ts.liquids.setMax(99999999999999999999f);
			teams.put(id, ts);
			return ts;
		}
		return t;
	}
	
	public QuantumNetwork team(int id) {
		return team("" + id);
	}
	
	public String getSectorKey(Sector sector) {
		return sector.planet.name + "." + sector.id;
	}
	
	//增删查改
	public void delSector(Sector sector) {
		var key = getSectorKey(sector);
		delIndex(key);
		sectorDB.del(key);
	}
	
	public QuantumMap getSector(String sector) {
		var _db = sectorDB.get(sector);
		if (!(_db instanceof HashMap<?, ?> db)) return null;
		return new QuantumMap(db);
	}
	
	public QuantumMap getSector(Sector sector) {
		
		return getSector(getSectorKey(sector));
	}
	
	public void setSector(Sector sector, QuantumMap qns) {
		var key = getSectorKey(sector);
		var data = qns.toMap();
		setIndex(key);
		sectorDB.set(key, data);
	}
	
	public void setSector(String key, QuantumMap qns) {
		setIndex(key);
		sectorDB.set(key, qns.toMap());
	}
	
	public ArrayList<String> getIndex() {
		var key = "index";
		var index = sectorDB.get(key);
		if (!(index instanceof ArrayList<?>)) return new ArrayList<>();
		return (ArrayList<String>) index;
	}
	
	public void delIndex(String name) {
		var data = getIndex();
		var key = "index";
		data.remove(name);
		sectorDB.set(key, data);
	}
	
	public void setIndex(String name) {
		var data = getIndex();
		var key = "index";
		if (!data.contains(name)) data.add(name);
		sectorDB.set(key, data);
	}
	
	//重载全局量子网络
	public void reload(Sector ignore) {
		teams.clear();
		var ignoreKey = getSectorKey(ignore);
		var sectorIndex = getIndex();
		for (var i : sectorIndex) {
			if (i.equals(ignoreKey)) continue;
			var qns = getSector(i);
			if (qns == null) continue;
			if (qns.Sector == null) continue;
			qns.teams.forEach((teamId, teamQN) -> {
				var globalQN = team(teamId);
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
				var dt = team(id);
				dt.itemCapability += qns.Sector.info.storageCapacity;
				var items = qns.Sector.info.items;
				items.forEach((d) -> {
					dt.items.add(d.item.name, (float) d.amount);
				});
			} catch (Exception e) {
			
			}
		}
	}
	
	//从全局拿资源,并且修正request
	public QuantumRequest take(String teamId, QuantumRequest require, Sector ignore) {
		var result = new QuantumRequest();
		//给一份清单，然后从全局网络减少
		var ignoreKey = getSectorKey(ignore);
		var sectorIndex = getIndex();
		for (var i : sectorIndex) {
			if (i.equals(ignoreKey)) continue;
			var qns = getSector(i);
			if (qns.Sector == null) continue;
			var sec = qns.Sector;
			var qt = qns.team(teamId);
			AtomicBoolean change = new AtomicBoolean(false);
			//优先核心拿物品
			if ((Vars.state.rules.defaultTeam.id + "").equals(teamId)) {
				var items = sec.items();
				require.items.data.forEach((name, data) -> {
					if (data <= 0) return;
					var item = Vars.content.item(name);
					if (item == null) return;
					//核心物品大于
					if (items.get(item) > data) {
						sec.removeItem(item, Math.round(data));
						result.items.add(name, data);
						require.items.add(name, -data);
					}
					//
					else {
						var has = items.get(item);
						sec.removeItem(item, has);
						result.items.add(name, (float) has);
						require.items.add(name, (float) -has);
					}
				});
			}
			require.items.data.forEach((name, data) -> {
				if (data <= 0) return;
				if (qt.items.get(name) > data) {
					change.set(true);
					qt.items.add(name, -data);
					result.items.add(name, data);
					require.items.add(name, -data);
					data = 0f;
				}
				//
				else {
					change.set(true);
					var has = qt.items.get(name);
					qt.items.add(name, -has);
					result.items.add(name, (float) has);
					require.items.add(name, (float) -has);
					data -= has;
				}
			});
			require.liquids.data.forEach((name, data) -> {
				if (data <= 0) return;
				if (qt.liquids.get(name) > data) {
					change.set(true);
					qt.liquids.add(name, -data);
					result.liquids.add(name, data);
					require.liquids.add(name, -data);
				}
				//
				else {
					change.set(true);
					var has = qt.liquids.get(name);
					qt.liquids.add(name, -has);
					result.liquids.add(name, (float) has);
					require.liquids.add(name, (float) -has);
				}
			});
			if (change.get()) {
				setSector(sec, qns);
			}
		}
		reload(ignore);
		return result;
	}
	
}
