package st.plugin.place.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import layer.layer.Logger;
import mindustry.game.EventType;
import st.plugin.place.provider.PlaceMap;


@Controller
public class PlaceEvent extends LayerController {
	@Require
	PlaceMap PlaceMap;
	@Require
	Logger logger;
	@Override
	public void run() {
		Events.on(EventType.WorldLoadEvent.class, e -> {
			//logger.info("加载地图");
			PlaceMap.load();
		});
	}
}
