package st.addon.block_io.controller;


import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import mindustry.content.TechTree;
import mindustry.game.EventType;
import mindustry.type.ItemStack;
import st.addon.block_io.provider.BlockIOProviders;
import st.addon.item.ITEM;
import st.addon.item.provider.ItemProvider;

@Controller
public class BlockIoTechController extends LayerController {
	@Require(cls = ItemProvider.class)
	@Import(cls = ITEM.class)
	ItemProvider item;
	@Require(cls = BlockIOProviders.class)
	BlockIOProviders blockIOProviders;
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			new TechTree.TechNode(
				Blocks.mechanicalDrill.techNode,
				blockIOProviders.物品输入接口,
				ItemStack.with(
					item.金元素, 1000,
					item.土元素, 1000
				)
			);
			new TechTree.TechNode(
				blockIOProviders.物品输入接口.techNode,
				blockIOProviders.高级物品输入接口,
				ItemStack.with(
					item.金元素, 6000,
					item.土元素, 6000
				)
			);
			new TechTree.TechNode(
				Blocks.mechanicalDrill.techNode,
				blockIOProviders.物品输出接口,
				ItemStack.with(
					item.金元素, 1000,
					item.土元素, 1000
				)
			);
			new TechTree.TechNode(
				blockIOProviders.物品输出接口.techNode,
				blockIOProviders.高级物品输出接口,
				ItemStack.with(
					item.金元素, 6000,
					item.土元素, 6000
				)
			);
			new TechTree.TechNode(
				Blocks.mechanicalDrill.techNode,
				blockIOProviders.流体输入接口,
				ItemStack.with(
					item.金元素, 1000,
					item.土元素, 1000
				)
			);
			new TechTree.TechNode(
				blockIOProviders.流体输入接口.techNode,
				blockIOProviders.高级流体输入接口,
				ItemStack.with(
					item.金元素, 6000,
					item.土元素, 6000
				)
			);
			new TechTree.TechNode(
				Blocks.mechanicalDrill.techNode,
				blockIOProviders.流体输出接口,
				ItemStack.with(
					item.金元素, 1000,
					item.土元素, 1000
				)
			);
			new TechTree.TechNode(
				blockIOProviders.流体输出接口.techNode,
				blockIOProviders.高级流体输出接口,
				ItemStack.with(
					item.金元素, 6000,
					item.土元素, 6000
				)
			);
			new TechTree.TechNode(
				Blocks.mechanicalDrill.techNode,
				blockIOProviders.量子驱动器,
				ItemStack.with(
					item.金元素, 3000,
					item.土元素, 12000,
					item.木元素, 6000
				)
			);
			new TechTree.TechNode(
				blockIOProviders.量子驱动器.techNode,
				blockIOProviders.超密集量子驱动器,
				ItemStack.with(
					item.金元素, 10000,
					item.土元素, 24000,
					item.木元素, 12000,
					item.光元素, 1000,
					item.暗元素, 1000
				)
			);
			new TechTree.TechNode(
				blockIOProviders.超密集量子驱动器.techNode,
				blockIOProviders.时间膨胀量子驱动器,
				ItemStack.with(
					item.金元素, 500000,
					item.土元素, 1000000,
					item.木元素, 250000,
					item.光元素, 15000,
					item.暗元素, 15000
				)
			);
			new TechTree.TechNode(
				Blocks.mechanicalDrill.techNode,
				blockIOProviders.量子接口,
				ItemStack.with(
					item.光元素, 500,
					item.暗元素, 500
				)
			);
			new TechTree.TechNode(
				blockIOProviders.量子接口.techNode,
				blockIOProviders.高级量子接口,
				ItemStack.with(
					item.光元素, 15000,
					item.暗元素, 15000
				)
			);
		});
	}
}
