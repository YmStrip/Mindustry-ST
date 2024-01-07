package st.ht.quantum.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import st.addon.content.SContent;
import st.addon.content.provider.TechProvider;
import st.ht.quantum.provider.QuantumBlocks;

@Controller
public class QuantumTech extends LayerController {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = QuantumBlocks.class)
	QuantumBlocks blocks;
	public void tech_mod () {
		tech
			.tech(blocks.紧急量子接口)
			.parent(Blocks.duct);
		tech
			.tech(blocks.物品输入接口)
			.parent(tech.root)
			.child(blocks.高级物品输入接口)
			.child(blocks.物品输出接口, t -> t
				.child(blocks.高级物品输出接口))
			.child(blocks.流体输入接口, t -> t
				.child(blocks.高级流体输入接口))
			.child(blocks.流体输出接口, t -> t
				.child(blocks.高级流体输出接口))
			.child(blocks.流体中心)
			.child(blocks.量子驱动器, t -> t
				.child(blocks.超密集量子驱动器, t1 -> t1
					.child(blocks.时间膨胀量子驱动器)))
			.child(blocks.量子接口, t -> t
				.child(blocks.高级量子接口))
		;
	}
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech_mod();
		});
	}
}
