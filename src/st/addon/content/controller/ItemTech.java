package st.addon.content.controller;

import arc.Events;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.game.EventType;
import st.addon.content.provider.ItemProvider;
import st.addon.content.provider.LiquidProvider;
import st.addon.content.provider.TechProvider;

@Provider
public class ItemTech extends LayerProvider {
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Require(cls = LiquidProvider.class)
	LiquidProvider liquids;
	
	public void tech_item() {
		tech
			.tech(items.纳米碳管)
			.parent(tech.root)
			.child(items.超导体, t -> t
				.child(items.金元素)
				.child(items.晶金, t4 -> t4
					.child(items.水元素))
				.child(items.辐矿石, t1 -> t1
					.child(items.木元素))
			)
			.child(items.反重力陶瓷, t3 -> t3
				.child(items.土元素)
				.child(items.铬纳尔, t2 -> t2
					.inChild(items.火元素)
					.child(items.光元素)
					.child(items.暗元素)
					.child(liquids.纳米元素流体)));
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech_item();
		});
	}
}
