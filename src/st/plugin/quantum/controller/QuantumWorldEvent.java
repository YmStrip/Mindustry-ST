package st.plugin.quantum.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.game.EventType;
import st.ST;
import st.plugin.quantum.entity.QuantumMapState;
import st.plugin.quantum.provider.QuantumMapTile;
import st.plugin.quantum.provider.QuantumWorld;

import java.util.Timer;
import java.util.TimerTask;

@Controller
public class QuantumWorldEvent extends LayerController {
	@Require
	Logger logger;
	@Require(cls = QuantumMapTile.class)
	QuantumMapTile quantumMap;
	@Require(cls = QuantumWorld.class)
	QuantumWorld quantumWorld;
	public long lastReloadTime = System.currentTimeMillis();
	
	@Override
	public void run() {
		//概述
		//如果新区块和当前区块一样，返回
		//保存地图
		//加载地图->如果是区块
		//	->储存旧区块
		//	->如果新地图是区块
		//		->尝试部署
		//		->如果错误那么载入
		//	->否则
		//		->载入
		//否则
		//	->载入
		Events.on(EventType.WorldLoadEvent.class, e -> {
			//如果新旧一样
			if (Vars.state.rules.sector != null && Vars.state.rules.sector == quantumMap.sector) {
				if (ST.debug) logger.warn("新区块和旧区块一样");
				return;
			}
			//保存旧地图
			if (quantumMap.isSector) {
				quantumMap.saveMap();
			}
			quantumMap.state = QuantumMapState.init;
			quantumMap.isSector = false;
			//如果新地图是区块
			if (Vars.state.rules.sector != null) {
				var sec = Vars.state.rules.sector;
				quantumWorld.reload(Vars.state.rules.sector);
				quantumMap.isSector = true;
				quantumMap.sector = Vars.state.rules.sector;
				var data = quantumWorld.getSector(quantumMap.sector);
				//如果存在
				if (data != null) {
					quantumMap.clear();
					quantumMap.map = data;
					quantumMap.map.Sector = sec;
					quantumMap.map.sector = sec.id;
					quantumMap.map.planet = sec.planet.name;
					quantumMap.map.Planet = sec.planet;
					quantumMap.map.initBuild();
					//建筑已经带入 然后deploy
					quantumMap.map.teams.forEach((tName, tData) -> {
						tData.items.deploy();
						tData.liquids.deploy();
						tData.units.deploy();
					});
				}
				//否则载入
				else {
					quantumMap.initWorld();
				}
			}
			//否则载入
			else {
				quantumMap.initWorld();
			}
		});
		//保存
		Events.on(EventType.SaveWriteEvent.class, e -> {
			if (Vars.state.rules.sector != null && quantumMap.isSector) {
				quantumMap.saveMap();
			}
			//如果是区块
			if (quantumMap.isSector) {
				//quantumWorld.reload(quantumMap.sector);
			}
		});
		//区块丢失
		Events.on(EventType.SectorLoseEvent.class, e -> {
			quantumWorld.delSector(e.sector);
			//
		});
		//reload
		Events.on(EventType.SaveWriteEvent.class, e -> {
			//每隔一段时间重载
			if (System.currentTimeMillis() - lastReloadTime < 60 * 1000) return;
			lastReloadTime = System.currentTimeMillis();
			if (!quantumMap.isSector) return;
			quantumWorld.reload(quantumMap.sector);
		});
	}
}
