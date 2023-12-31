package st.addon.item.controller;

import arc.Core;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import mindustry.content.TechTree;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.StatCat;
import st.addon.entity.entity.StItem;
import st.addon.item.provider.ItemProvider;
import st.addon.tooltip.Tooltip;
import st.addon.tooltip.provider.ToolTipBar;

@Controller
public class ItemWallController extends LayerController {
	@Import(cls = Tooltip.class)
	@Require(cls = ToolTipBar.class)
	ToolTipBar toolTipBar;
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	
	public Wall[] create_wall(StItem item, float healthC) {
		var a = new Wall(item.name.replace("st-", "") + "墙") {
			{
				localizedName = item.localizedName + Core.bundle.get("block.st-wall");
				size = 1;
				health = (int) healthC;
				requirements(Category.defense, ItemStack.with(
					item, 6
				));
				toolTipBar.showTechLevel(item.techLevel, stats, new StatCat("st"));
			}
		};
		var b = new Wall("大型" + item.name.replace("st-", "") + "墙") {
			{
				size = 2;
				localizedName = Core.bundle.get("block.st-big") + item.localizedName + Core.bundle.get("block.st-wall");
				health = (int) healthC * 4;
				requirements(Category.defense, ItemStack.with(
					item, 24
				));
				toolTipBar.showTechLevel(item.techLevel, stats, new StatCat("st"));
			}
		};
		return new Wall[]{a, b};
	}
	
	public void tech(Item item, Wall[] walls) {
		new TechTree.TechNode(
			Blocks.titaniumWall.techNode,
			walls[0],
			ItemStack.with(
				item, 60
			)
		);
		new TechTree.TechNode(
			walls[0].techNode,
			walls[1],
			ItemStack.with(
				item, 1200
			)
		);
	}
	
	//注入建筑墙
	@Override
	public void run() {
		/*var 纳米碳管 = create_wall(items.纳米碳管, 440);
		var 反物质 = create_wall(items.反物质, 500);
		var 金元素 = create_wall(items.金元素, 1200);
		var 木元素 = create_wall(items.木元素, 1000);
		var 水元素 = create_wall(items.水元素, 600);
		var 火元素 = create_wall(items.火元素, 800);
		var 土元素 = create_wall(items.土元素, 700);
		var 光元素 = create_wall(items.光元素, 4500);
		var 暗元素 = create_wall(items.暗元素, 3500);
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech(items.纳米碳管, 纳米碳管);
			tech(items.反物质, 反物质);
			tech(items.金元素, 金元素);
			tech(items.木元素, 木元素);
			tech(items.水元素, 水元素);
			tech(items.火元素, 火元素);
			tech(items.土元素, 土元素);
			tech(items.光元素, 光元素);
			tech(items.暗元素, 暗元素);
		});*/
	}
}
