package st.quamtum.provider.provider;

import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.type.Sector;
import st.ST;
import st.dao.Dao;
import st.quamtum.provider.entity.QuantumMapState;
import st.quamtum.provider.entity.QuantumNetwork;
import st.quamtum.provider.entity.QuantumMap;

import java.util.Map;


//当前地图
@Provider
public class QuantumMapTile extends LayerProvider {
	@Require
	Logger logger;
	@Require(cls = QuantumWorld.class)
	QuantumWorld quantumWorld;
	@Require(name = "sector")
	Dao<Object> sectorDB;
	public boolean isSector = false;
	public Sector sector;
	public QuantumMapState state = QuantumMapState.close;
	public QuantumMap map = new QuantumMap();
	
	public QuantumNetwork team(int id) {
		return map.team(id);
	}
	
	public QuantumNetwork team(String id) {
		return map.team(id);
	}
	
	
	public void initWorld() {
		map.initWorld();
	}
	
	public String state() {
		if (!ST.debug) return "bug这么多直接放弃吧";
		return String.format("是否区块：%s，区块：%s", isSector, sector);
	}
	
	public void clear() {
		map.clear();
	}
	
	public void close() {
		state = QuantumMapState.close;
	}
	
	public void saveMap() {
		if (!isSector) {
			return;
		}
		//如果没有任何的建筑物,删掉区块
		var notCapability = true;
		for (Map.Entry<String, QuantumNetwork> i : map.teams.entrySet()) {
			var t = i.getValue();
			if (t.itemCapability > 0 || t.liquidCapability > 0) {
				notCapability = false;
				break;
			}
		}
		if (notCapability) {
			//删掉
			quantumWorld.delSector(sector);
			return;
		}
		quantumWorld.setSector(sector, map);
	}
}
