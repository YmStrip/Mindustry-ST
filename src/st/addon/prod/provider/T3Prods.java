package st.addon.prod.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.GenericCrafter;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;

@Provider
public class T3Prods extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Require(cls = ProdPreset.class)
	ProdPreset preset;
	
	public class fc extends GenericCrafter {
		
		public fc(String name) {
			super(name);
			craftTime = 30;
			craftEffect = Fx.smeltsmoke;
			consumePower(50f);
		}
		
		public void req() {
			this.requirements = ItemStack.with(items.纳米碳管, 500, items.反重力陶瓷, 250, items.超导体, 250, items.晶金, 300, items.铬纳尔, 350, Items.surgeAlloy, 500);
			preset.inject(this, 3);
		}
	}
	
	public fc 金元素构造厂 = new fc("金元素构造厂");
	public fc 木元素构造厂 = new fc("木元素构造厂");
	public fc 水元素构造厂 = new fc("水元素构造厂");
	public fc 火元素构造厂 = new fc("火元素构造厂");
	public fc 土元素构造厂 = new fc("土元素构造厂");
	
	@Override
	public void run() {
		金元素构造厂.consumeItems(ItemStack.with(items.反物质, 2, items.超导体, 2));
		金元素构造厂.outputItem = new ItemStack(items.金元素, 3);
		金元素构造厂.req();
		木元素构造厂.consumeItems(ItemStack.with(items.反物质, 2, items.铬纳尔, 2));
		木元素构造厂.outputItem = new ItemStack(items.木元素, 3);
		木元素构造厂.req();
		水元素构造厂.consumeItems(ItemStack.with(items.反物质, 2, items.晶金, 2));
		水元素构造厂.outputItem = new ItemStack(items.水元素, 3);
		水元素构造厂.req();
		火元素构造厂.consumeItems(ItemStack.with(items.反物质, 2, items.辐矿石, 2));
		火元素构造厂.outputItem = new ItemStack(items.火元素, 3);
		火元素构造厂.req();
		土元素构造厂.consumeItems(ItemStack.with(items.反物质, 2, items.反重力陶瓷, 2));
		土元素构造厂.outputItem = new ItemStack(items.土元素, 3);
		土元素构造厂.req();
	}
}
