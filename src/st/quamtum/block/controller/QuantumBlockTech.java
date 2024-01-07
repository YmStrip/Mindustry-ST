package st.quamtum.block.controller;


import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import st.addon.content.provider.TechProvider;
import st.quamtum.block.provider.QuantumBlocks;
import st.addon.content.SContent;

@Controller
public class QuantumBlockTech extends LayerController {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = QuantumBlocks.class)
	QuantumBlocks blocks;
	
	@Override
	public void run() {
		tech
			.tech(blocks.物品输入接口)
			.parent(Blocks.duo)
			.child(blocks.高级流体输入接口)
			.next(blocks.物品输出接口, t -> t
				.child(blocks.高级物品输出接口))
			.next(blocks.流体输入接口, t -> t
				.child(blocks.高级流体输入接口))
			.next(blocks.流体输出接口, t -> t
				.child(blocks.高级流体输出接口))
			.next(blocks.流体中心)
			.next(blocks.量子驱动器, t -> t
				.child(blocks.超密集量子驱动器, t1 -> t1
					.child(blocks.时间膨胀量子驱动器)))
			.next(blocks.量子接口, t -> t
				.child(blocks.高级量子接口))
		;
	}
}
