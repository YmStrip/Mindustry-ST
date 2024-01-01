package st.addon.content.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.game.EventType;
import st.addon.content.entity.StItem;
import st.addon.content.provider.ItemProvider;
import st.addon.content.provider.TooltipProvider;

@Controller
public class ItemToolBar extends LayerController {
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	
	public ItemToolBar inject(StItem item) {
		tooltip
			.tooltip(item.stats)
			.techLevel(item.techLevel)
			.showValue("damage", item.damage);
		return this;
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			this
				.inject(items.纳米碳管)
				.inject(items.超导体)
				.inject(items.反物质)
				.inject(items.反重力陶瓷)
				.inject(items.辐矿石)
				.inject(items.铬纳尔)
				.inject(items.晶金)
				.inject(items.金元素)
				.inject(items.木元素)
				.inject(items.水元素)
				.inject(items.火元素)
				.inject(items.土元素)
				.inject(items.光元素)
				.inject(items.暗元素)
			;
		});
	}
}
