package st.ht.quantum.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.game.EventType;
import st.ht.quantum.provider.QuantumMap;
import st.ht.quantum.provider.QuantumStore;


//控制器-总线
@Controller
public class QuantumController extends LayerController {
	@Require
	Logger logger;
	@Require(cls = QuantumStore.class)
	QuantumStore store;
	@Require(cls = QuantumMap.class)
	QuantumMap map;
	public long lastReloadTime = System.currentTimeMillis();
	
	@Override
	public void run() {
		//加载世界后事件
		Events.on(EventType.WorldLoadEvent.class, e -> {
			//储存上次的数据
			/*if (map.isSector) {
				store.write(map.sector, map.teams);
			}
			//设置状态
			{
				map.isSector = false;
				map.sector = null;
			}
			//如果地图是一个区块
			if (Vars.state.rules.sector != null) {
				System.out.println("isSector");
				map.isSector = true;
				map.sector = Vars.state.rules.sector;
				map.clear();
				map.init_block();
				map.init_value();
				map.deploy_sector();
				store.write(map.sector, map.teams);
				map.global_init_value(map.sector);
				//System.out.println(map.teams);
			}
			//
			else {
				map.clear();
				map.init_block();
				map.init_value();
			}*/
			//sector
			{
				map.sector = Vars.state.rules.sector;
				map.isSector = Vars.state.rules.sector != null;
			}
			//init-local-n
			{
				map.clear();
				map.init_block();
				map.init_value();
				System.out.println(map.teams);
			}
			map.capability();
		});
		Events.on(EventType.SectorLoseEvent.class, e -> {
			map.capability();
		});
		//保存
		Events.on(EventType.SaveWriteEvent.class, e -> {
			/*if (Vars.state.rules.sector != null && map.isSector && map.sector != null) {
				//储存地图[特殊的dao会忽略这个行为]
				store.write(map.sector, map.teams);
			}*/
			map.write();
		});
		//每隔一段时间加载网络数据
		Events.on(EventType.SaveWriteEvent.class, e -> {
			/*if (System.currentTimeMillis() - lastReloadTime < 60 * 1000) return;
			lastReloadTime = System.currentTimeMillis();
			if (!map.isSector) return;
			map.global_init_value(map.sector);*/
		});
		//科技，启动！
		Events.on(EventType.ContentInitEvent.class, e -> {
			map.read();
			logger.suc("量子网络启动");
		});
	}
}
