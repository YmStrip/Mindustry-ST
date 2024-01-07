package st.addon.power.contoller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.game.EventType;
import st.addon.content.SContent;
import st.addon.power.provider.T1Powers;
import st.addon.content.provider.TechProvider;
import st.addon.power.provider.T2Powers;
import st.addon.power.provider.T3Powers;

@Controller
public class PowerTech extends LayerController {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = T1Powers.class)
	T1Powers t1;
	@Require(cls = T2Powers.class)
	T2Powers t2;
	@Require(cls = T3Powers.class)
	T3Powers t3;
	
	public void inject_mod() {
		tech
			.tech(t1.激光节点)
			.parent(tech.root)
			.inChild(t2.激光节点)
		;
		tech
			.tech(t1.电池)
			.parent(tech.root)
			.inChild(t2.电池)
			.inChild(t3.电池);
		tech
			.tech(t1.生物质)
			.parent(tech.root)
			.child(t1.太阳能, x -> x
				.inChild(t1.地热)
				.inChild(t1.水力))
			.child(t1.煤炭, x -> x
				.inChild(t1.石油)
				.inChild(t1.核能)
				.child(t1.合金, _0 -> _0
					.child(t2.流沙)
					.child(t2.反物质, _1 -> _1
						.child(t2.引力波)
						.inChild(t2.等离子)
						.child(t2.水聚变))
					.child(t2.辐矿石, x1 -> x1
						.child(t3.木元素, x2 -> x2
							.child(t3.光元素))
						.inChild(t3.零点矩阵)
						.child(t3.t3暗能量矩阵))
				))
		
		;
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			inject_mod();
		});
	}
}
