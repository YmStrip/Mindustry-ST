package st.provider.place.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import st.provider.place.provider.PlaceProvider;


@Controller
public class PlaceEvent extends LayerController {
	@Require(cls = PlaceProvider.class)
	PlaceProvider placeMap;
	@Require
	Logger logger;
	
	public void set(Building build, Team team) {
		System.out.println("set " + build.block.name);
		placeMap
			.team(team.id)
			.add(build, 1);
	}
	
	public void del(Block block, Team team) {
		System.out.println("del " + block.name);
		placeMap
			.team(team.id)
			.add(block, -1);
	}
	
	@Override
	public void run() {
		Events.on(EventType.WorldLoadEvent.class, e -> {
			//logger.info("加载地图");
			placeMap.load();
		});
		//anuke 一堆bug (
		/*
		Events.on(EventType.BlockBuildBeginEvent.class, e -> {
			if (e.breaking) {
				del(e.tile.cblock(), e.team);
			}
		});
		Events.on(EventType.BlockBuildEndEvent.class, e -> {
			if (!e.breaking) {
				set(e.tile.build, e.team);
			}
		});
		Events.on(EventType.BuildingBulletDestroyEvent.class, e -> {
			if (e.build != null) del(e.build.block(), e.build.team);
		});
		Events.on(EventType.BlockDestroyEvent.class, e -> {
			del(e.tile.block(), e.tile.team());
		});*/
	}
}
