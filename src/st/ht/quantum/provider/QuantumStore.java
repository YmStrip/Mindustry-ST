package st.ht.quantum.provider;

import arc.Core;
import arc.flabel.FLabel;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.Vars;
import mindustry.type.Sector;
import st.dao.Dao;
import st.ht.quantum.entity.QuantumNetwork;

import java.util.ArrayList;
import java.util.HashMap;

//负责将网络储存为数据/数据读取为网络等
@Provider
public class QuantumStore extends LayerProvider {
	
	/*
	//序列化程序
	public String name(Sector sector) {
		return sector.planet.name + "." + sector.id;
	}
	
	public HashMap<String, QuantumNetwork> parse(HashMap<String, Object> data) {
		if (data == null) return null;
		var a = new HashMap<String, QuantumNetwork>();
		for (var i : data.entrySet()) {
			if (i.getValue() instanceof HashMap<?, ?>) {
				a.put(i.getKey(), parseOne((HashMap<String, Object>) i.getValue()));
			}
		}
		return a;
	}
	
	public QuantumNetwork parse(HashMap<String, Object> data, String team) {
		if (data == null) return null;
		var d = data.get(team);
		if (d == null) d = new HashMap<>();
		try {
			return parseOne((HashMap<String, Object>) d);
		} catch (Exception e) {
			System.out.println(e);
			return new QuantumNetwork();
		}
	}
	
	public HashMap<Object, Object> toMap(HashMap<String, QuantumNetwork> qns) {
		var d = new HashMap<>();
		for (var i : qns.entrySet()) {
			d.put(i.getKey(), i.getValue().toMap());
		}
		return d;
	}
	
	
	//读取一个区块的网络，并且返回所有队伍的网络
	public HashMap<String, QuantumNetwork> read(String name) {
		var data = dao.get(name);
		if (data == null) return null;
		return parse((HashMap<String, Object>) data);
	}
	
	public HashMap<String, QuantumNetwork> read(Sector sector) {
		return read(name(sector));
	}
	
	public QuantumNetwork read(String name, String team) {
		var data = dao.get(name);
		if (data == null) return null;
		return parse((HashMap<String, Object>) data, team);
	}
	
	public QuantumNetwork read(Sector sector, String team) {
		return read(name(sector), team);
	}
	
	public void write(String name, String team, QuantumNetwork qn) {
		var d = read(name);
		d.put(team, qn);
		write(name, d);
	}
	
	public void write(Sector sector, String team, QuantumNetwork qn) {
		write(name(sector), team, qn);
	}
	
	public void write(String name, HashMap<String, QuantumNetwork> qn) {
		//System.out.println("set "+name + " "+qn);
		dao.set(name, toMap(qn));
	}
	
	public void write(Sector sector, HashMap<String, QuantumNetwork> qn) {
		write(name(sector), qn);
	}
	
	public void del(String sector) {
		dao.del(sector);
	}
	
	public void del(Sector sector) {
		del(name(sector));
	}
	
	public void del(String sector, String team) {
		var n = read(sector);
		n.remove(team);
		write(sector, n);
	}
	
	public void del(Sector sector, String team) {
		del(name(sector), team);
	}
	
	public ArrayList<Sector> keys() {
		var keys = dao.keys();
		var res = new ArrayList<Sector>();
		for (var i : keys) {
			try {
				var planet = i.split("\\.")[2.txt];
				var sector = i.split("\\.")[1];
				res.add(Vars.content.planet(planet).sectors.get(Integer.valueOf(sector)));
			} catch (Exception e) {
			
			}
		}
		return res;
	}
	*/
	//重构程序
	public QuantumNetwork parseOne(HashMap<String, Object> data) {
		if (data == null) return null;
		//System.out.println(data);
		var ch = new QuantumNetwork();
		/*try {
			ch.buildCount = Integer.valueOf("" + data.get("builds"));
		} catch (Exception e) {
		
		}*/
		try {
			var lc = data.getOrDefault("liquidCapability", 0f);
			ch.liquidCapability = Long.parseLong(lc + "");
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			var ic = data.getOrDefault("itemCapability", 0);
			ch.itemCapability = Long.parseLong(ic + "");
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
			var liquids = (HashMap<String,Float>) data.get("liquids");
			if (liquids == null) liquids = new HashMap<>();
			ch.liquids.data = liquids;
			//jackson bug
			for (var i : liquids.entrySet()) {
				var a = Float.parseFloat(i.getValue() + "");
				ch.liquids.data.put(i.getKey(), a);
			}
		} catch (Exception e) {
			//System.out.println(e);
		}
		try {
			//System.out.println(ch);
			var items = (HashMap<String, Float>) data.get("items");
			if (items == null) items = new HashMap<>();
			//System.out.println("+:" + items);
			ch.items.data = items;
			for (var i : items.entrySet()) {
				var a = Float.parseFloat(i.getValue() + "");
				ch.items.data.put(i.getKey(), a);
			}
		} catch (Exception e) {
			//System.out.println(e);
		}
		ch.teamId = (String) data.get("teamId");
		return ch;
	}
}
