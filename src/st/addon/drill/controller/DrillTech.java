package st.addon.drill.controller;

import arc.Events;
import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import st.addon.content.SContent;
import st.addon.content.provider.TechProvider;
import st.addon.drill.provider.T1Drills;
import st.addon.drill.provider.T2Drills;
import st.addon.drill.provider.T3Drills;

@Provider
public class DrillTech extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = T1Drills.class)
	T1Drills t1;
	@Require(cls = T2Drills.class)
	T2Drills t2;
	@Require(cls = T3Drills.class)
	T3Drills t3;
	
	public void inject_mod() {
		tech.tech(t1.泵)
			.parent(tech.root)
			.inChild(t2.泵)
			.inChild(t3.泵)
		;
		tech.tech(t1.钻头)
			.parent(tech.root)
			.child(t2.钻头, t -> t
				.inChild(t3.钻头))
			.child(t1.浅层挖矿机)
			.child(t1.深层挖矿机)
			.child(t1.特种挖矿机)
			.child(t1.富集机);
		tech.tech(t1.抽水机)
			.parent(Blocks.waterExtractor)
			.child(t1.抽矿机)
			.child(t1.抽冷冻液机);
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, r -> {
			inject_mod();
		});
	}
}
