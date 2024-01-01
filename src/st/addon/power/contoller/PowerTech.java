package st.addon.power.contoller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
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
			.tech(t1.t1生物质)
			.parent(Blocks.combustionGenerator)
			.child(t1.t1太阳能, x -> x
				.inChild(t1.t1地热)
				.inChild(t1.t1水力))
			.child(t1.t1煤炭, x -> x
				.inChild(t1.t1石油)
				.inChild(t1.t1核能)
				.child(t1.t1合金, _0 -> _0
					.child(t2.t2流沙)
					.child(t2.t2反物质, _1 -> _1
						.child(t2.t2引力波)
						.inChild(t2.t2等离子)
						.child(t2.t2水聚变))
					.child(t2.t2辐矿石, x1 -> x1
						.child(t3.t3金元素, x2 -> x2
							.child(t3.t3光元素))
						.inChild(t3.t3零点矩阵)
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
