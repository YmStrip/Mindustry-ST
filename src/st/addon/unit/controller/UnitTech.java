package st.addon.unit.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.game.EventType;
import st.addon.content.SContent;
import st.addon.content.provider.TechProvider;
import st.addon.unit.provider.T1Units;

@Controller
public class UnitTech extends LayerController {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = T1Units.class)
	T1Units t1;
	
	public void tech_mod() {
		tech
			.tech(t1.原始人单位构造厂)
			.parent(tech.root);
		tech
			.tech(t1.量子建造机)
			.parent(t1.原始人单位构造厂)
			.next(t1.量子挖矿机)
			.next(t1.量子特工队)
			.next(t1.量子电锯人);
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech_mod();
		});
	}
}
