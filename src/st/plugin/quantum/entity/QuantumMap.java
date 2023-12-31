package st.plugin.quantum.entity;

import mindustry.Vars;
import mindustry.type.Planet;
import mindustry.type.Sector;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StBuilding;

import java.util.HashMap;


public class QuantumMap {
	
	public Sector Sector;
	public int sector;
	public Planet Planet;
	public String planet;
	public HashMap<String, QuantumNetwork> teams = new HashMap<>();
	
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
	
	public void init(HashMap data) {
		planet = data.get("planet") + "";
		try {
			sector = Integer.valueOf("" + data.get("sectorId"));
		} catch (Exception e) {
		}
		try {
			Planet = Vars.content.planet(planet);
		} catch (Exception e) {
		}
		try {
			Sector = Planet.sectors.get(sector);
		} catch (Exception e) {
		
		}
		try {
			var l = data.get("teams");
			if (l instanceof HashMap<?, ?> list) {
				list.forEach((name, _team) -> {
					if (!(_team instanceof HashMap team)) return;
					var ch = new QuantumNetwork();
					try {
						ch.buildCount = Integer.valueOf("" + team.get("builds"));
					} catch (Exception e) {
					
					}
					try {
						var lc = team.getOrDefault("liquidCapability", 0f);
						ch.liquidCapability = (long) lc;
					} catch (Exception e) {
					
					}
					try {
						var ic = team.getOrDefault("itemCapability", 0);
						ch.itemCapability = (long) ic;
					} catch (Exception e) {
					
					}
					
					try {
						var liquids = (HashMap<String, Float>) team.get("liquids");
						if (liquids == null) liquids = new HashMap<>();
						ch.liquids.data = liquids;
					} catch (Exception e) {
					
					}
					try {
						var items = (HashMap<String, Float>) team.get("items");
						if (items == null) items = new HashMap<>();
						ch.items.data = items;
					} catch (Exception e) {
					
					}
					ch.teamId = (String) team.get("teamId");
					teams.put((String) name, ch);
				});
			}
		} catch (Exception e) {
		
		}
	}
	
	public QuantumMap(HashMap data) {
		init(data);
	}
	
	public QuantumMap() {
	
	}
	
	public HashMap toMap() {
		var d = new HashMap<>();
		d.put("sector", sector);
		d.put("sectorId", sector);
		d.put("planet", planet);
		var at = new HashMap<>();
		teams.forEach((name, data) -> {
			at.put(name, data.toMap());
		});
		d.put("teams", at);
		return d;
	}
	
	public void clear() {
		teams.clear();
	}
	
	public void initBuild() {
		var width = Vars.world.width();
		var height = Vars.world.height();
		var isMap = new HashMap<>();
		var isClear = new HashMap<>();
		for (var i = 0; i < width; ++i) {
			for (var j = 0; j < height; ++j) {
				var tile = Vars.world.tile(i, j);
				if (tile == null || tile.build == null) continue;
				var blockId = tile.build.id;
				var teamId = tile.build.team.id;
				if (isClear.get(teamId) == null) {
					isClear.put(teamId, true);
					var ts = team(teamId);
					ts.builds.clear();
					ts.buildCount = 0;
				}
				if (isMap.get(blockId) != null) continue;
				isMap.put(blockId, true);
				if (!StBlock.isSt(tile.block())) continue;
				var block = StBlock.getBlock(tile.block());
				if (block.itemsBlock || block.liquidsBlock) {
					var t = team(teamId);
					t.builds.add((StBuilding) tile.build);
					t.buildCount = t.builds.size();
				}
			}
		}
		teams.forEach((name, d) -> {
			d.capability();
		});
	}
	
	public void initValue() {
		teams.forEach((tName, tData) -> {
			tData.builds.forEach(tData::addBuildingValue);
		});
	}
	
	public void initWorld() {
		clear();
		initBuild();
		initValue();
	}
}
