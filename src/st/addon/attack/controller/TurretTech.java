package st.addon.attack.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import st.addon.attack.provider.T1Turret;
import st.addon.attack.provider.T2Turret;
import st.addon.attack.provider.T3Turret;
import st.addon.content.SContent;
import st.addon.content.provider.TechProvider;

@Controller
public class TurretTech extends LayerController {
	@Require(cls = T3Turret.class)
	T3Turret t3;
	@Require(cls = T2Turret.class)
	T2Turret t2;
	@Require(cls = T1Turret.class)
	T1Turret t1;
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider techs;
	
	public void tech_mod() {
		techs
			.tech(t1.超导激光炮)
			.parent(Blocks.lancer)
			.child(t1.脉冲)
			.child(t1.毒刺导弹)
			.child(t1.超导电磁炮, x -> x
				.child(t2.脉冲发射器)
				.child(t2.光子大炮)
				.child(t2.正电子冲击波)
				.child(t2.离子光束)
				.child(t2.超电磁炮, x1 -> x1
					.child(t3.高能元素机枪)
					.child(t3.机械暴徒)
					.child(t3.高能激光炮, t -> t
						.child(t3.死光炮)
					)
					.child(t3.高能电浆炮, t -> t
						.inChild(t3.微型光粒炮)
						.inChild(t3.光粒))
					.child(t3.等离子光束)
					.child(t3.以太爆破, x2 -> x2
						.inChild(t3.以太黑洞)
						.child(t3.以太毁灭))
				))
		;
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech_mod();
		});
	}
}
