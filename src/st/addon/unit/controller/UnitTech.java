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
import st.addon.unit.provider.T2Units;

@Controller
public class UnitTech extends LayerController {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = T1Units.class)
	T1Units t1;
	
	@Require(cls = T2Units.class)
	T2Units t2;
	
	public void tech_mod() {
		tech
			.tech(t1.原始人单位构造厂)
			.parent(tech.root)
			.inChild(t2.夸特动力船坞);
		tech
			.tech(t1.量子建造机)
			.parent(t1.原始人单位构造厂)
			.next(t1.量子挖矿机, t -> {
				t.child(t2.星际挖矿机);
			})
			.child(t2.星际建造机)
			.next(t1.量子特工队, t -> t
				.child(t1.量子电锯人))
		;
		tech
			.tech(t2.星际护卫舰)
			.parent(t2.夸特动力船坞)
			.child(t2.星际歼星舰)
			.inChild(t2.星际巡洋舰)
			.inChild(t2.星际战列舰)
			.inChild(t2.猫盒)
		;
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech_mod();
		});
	}
}
