package st.addon.effect.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import st.addon.content.SContent;
import st.addon.content.provider.TechProvider;
import st.addon.effect.provider.T1Effects;
import st.addon.effect.provider.T2Effects;
import st.addon.effect.provider.T3Effects;

@Controller
public class EffectTech extends LayerController {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = T1Effects.class)
	T1Effects t1;
	@Require(cls = T2Effects.class)
	T2Effects t2;
	@Require(cls = T3Effects.class)
	T3Effects t3;
	public void tech_mod () {
		tech
			.tech(t1.前哨核心)
			.parent(tech.root)
			.child(t1.核心)
			.inChild(t2.核心)
			.inChild(t3.核心);
		tech
			.tech(t1.仓库)
			.parent(tech.root)
			.inChild(t2.仓库)
			.inChild(t3.仓库);
		tech
			.tech(t1.修复器)
			.parent(tech.root)
			.child(t2.修复器, x_0 -> x_0
				.inChild(t3.修复器))
			.child(t1.力场, x_0 -> x_0
				.inChild(t2.力场)
				.inChild(t3.力场))
			.child(t1.加速器, x_1 -> x_1
				.inChild(t2.加速器)
				.inChild(t3.加速器));
	}
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech_mod();
		});
	}
}
