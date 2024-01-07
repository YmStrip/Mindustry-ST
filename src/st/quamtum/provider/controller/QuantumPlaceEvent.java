package st.quamtum.provider.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import layer.layer.Logger;
import mindustry.game.EventType;
import mindustry.gen.Building;
import org.checkerframework.checker.units.qual.C;
import st.quamtum.block.entity.QuantumBuilding;
import st.quamtum.provider.provider.QuantumMapTile;
import st.quamtum.provider.provider.QuantumWorld;

@Controller
public class QuantumPlaceEvent extends LayerController {
	@Require
	Logger logger;
	@Require(cls = QuantumMapTile.class)
	QuantumMapTile quantumMap;
	@Require(cls = QuantumWorld.class)
	QuantumWorld quantumWorld;
	public void set(Building b) {
		if (b == null) return;
		if (!(b instanceof QuantumBuilding q)) return;
		quantumMap
			.team(q.team == null ? 0 : q.team.id)
			.addBuilding(q);
	}
	
	public void del(Building b) {
		if (b == null) return;
		if (!(b instanceof QuantumBuilding q)) return;
		quantumWorld
			.team(q.team == null ? 0 : q.team.id)
			.removeBuilding(q);
	}
	@Override
	public void run() {
		//BLOCK相关
		/*Events.on(EventType.BlockBuildEndEvent.class, e -> {
			if (e.breaking) {
				del(e.tile.build);
			}
			//
			else {
				set(e.tile.build);
			}
		});
		Events.on(EventType.BuildingBulletDestroyEvent.class, e -> {
			if (e.build != null) del(e.build);
		});
		Events.on(EventType.BlockDestroyEvent.class, e -> {
			del(e.tile.build);
		});*/
	}
}
