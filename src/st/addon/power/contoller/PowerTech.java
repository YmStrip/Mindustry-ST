package st.addon.power.contoller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import st.addon.power.provider.Powers;
import st.addon.tech.STech;
import st.addon.tech.provider.STechProvider;

@Controller
public class PowerTech extends LayerController {
	@Import(cls = STech.class)
	@Require(cls = STechProvider.class)
	STechProvider tech;
	@Require(cls = Powers.class)
	Powers powers;
	
	public void inject_mod() {
		tech
			.tech(powers.合金发电机)
			.parent(Blocks.steamGenerator)
			.child(powers.反物质发电机, t -> t
				.child(powers.水聚变发电机))
			.next(powers.流沙发电机, t -> t
				.child(powers.零点矩阵, t1 -> t1
					.child(powers.暗能量矩阵)))
		;
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			inject_mod();
		});
	}
}
