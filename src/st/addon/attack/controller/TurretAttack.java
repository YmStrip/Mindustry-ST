package st.addon.attack.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import mindustry.content.TechTree;
import mindustry.game.EventType;
import st.addon.attack.provider.STurret;
import st.addon.tech.STech;
import st.addon.tech.provider.STechProvider;

@Controller
public class TurretAttack extends LayerController {
	@Require(cls = STurret.class)
	STurret sTurret;
	@Import(cls = STech.class)
	@Require(cls = STechProvider.class)
	STechProvider techs;
	
	public void tech_mod() {
		techs
			.tech(sTurret.高能元素机枪)
			.parent(Blocks.lancer)
			.child(sTurret.机械暴徒)
			.next(sTurret.高能激光炮, t -> t
				.child(sTurret.死光炮)
			)
			.next(sTurret.高能电浆炮, t -> t
				.child(sTurret.微型光粒炮, t1 -> t1
					.child(sTurret.光粒)))
		;
		techs
			.tech(sTurret.等离子光束)
			.parent(Blocks.meltdown)
		;
		techs
			.tech(sTurret.以太爆破)
			.parent(Blocks.foreshadow)
			.child(sTurret.以太黑洞)
			.child(sTurret.以太毁灭);
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech_mod();
		});
	}
}
