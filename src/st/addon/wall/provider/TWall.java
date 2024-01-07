package st.addon.wall.provider;

import arc.Events;
import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Blocks;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.Wall;
import st.ST;
import st.addon.content.SContent;
import st.addon.content.entity.STooltipBuilder;
import st.addon.content.entity.StItem;
import st.addon.content.provider.ItemProvider;
import st.addon.content.provider.TechProvider;
import st.addon.content.provider.TooltipProvider;
import st.addon.content.provider.ValueProvider;

@Provider
public class TWall extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Import(cls = SContent.class)
	@Require(cls = ValueProvider.class)
	ValueProvider value;
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	
	public class WallGroup {
		Wall small;
		Wall big;
		Wall sup;
		UnlockableContent parent;
	}
	
	public STooltipBuilder inject(Wall wall) {
		value.health(wall);
		return tooltip.tooltip(wall.stats);
	}
	
	public WallGroup inject(StItem item) {
		return inject(item, Blocks.copperWall);
	}
	
	public WallGroup inject(StItem item, WallGroup wallGroup) {
		return inject(item, wallGroup.small);
	}
	
	public WallGroup inject(StItem item, UnlockableContent _parent) {
		//small,big,super
		var name = item.name.replace(ST.name + "-", "");
		var _small = new Wall(name + "墙") {{
			requirements(Category.defense, ItemStack.with(item, 6));
			scaledHealth = item.damage / 2f;
		}};
		inject(_small)
			.techLevel(1);
		var _big = new Wall("大型" + name + "墙") {{
			requirements(Category.defense, ItemStack.with(item, 64));
			scaledHealth = item.damage;
		}};
		inject(_big)
			.techLevel(2);
		var _sup = new Wall("究极" + name + "墙") {{
			requirements(Category.defense, ItemStack.with(item, 128, items.暗元素, 24));
			scaledHealth = item.damage * 10;
		}};
		inject(_sup)
			.techLevel(3);
		return new WallGroup() {{
			parent = _parent;
			small = _small;
			big = _big;
			sup = _sup;
		}};
	}
	
	public void tech(WallGroup group) {
		tech
			.tech(group.small)
			.parent(group.parent)
			.inChild(group.big)
			.inChild(group.sup);
	}
	
	@Override
	public void run() {
		var 金 = inject(items.金元素, tech.root);
		var 木 = inject(items.木元素, 金);
		var 水 = inject(items.水元素, 木);
		var 火 = inject(items.火元素, 水);
		var 土 = inject(items.土元素, 火);
		var 暗 = inject(items.暗元素, 土);
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech(金);
			tech(木);
			tech(水);
			tech(火);
			tech(土);
			tech(暗);
		});
	}
}
