package st.addon.prod.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Fx;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.blocks.production.GenericCrafter;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.addon.content.provider.LiquidProvider;

import static mindustry.type.ItemStack.with;

@Provider
public class T4Prods extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = LiquidProvider.class)
	LiquidProvider liquid;
	@Require(cls = ProdPreset.class)
	ProdPreset preset;
	public GenericCrafter 究极纳米元素融合机 = new GenericCrafter("究极纳米元素融合机") {{
		size = 4;
		craftTime = 30;
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumePower(2500f);
	}};
	public GenericCrafter 纳米元素流体制造机 = new GenericCrafter("纳米元素流体制造机") {{
		size = 4;
		craftTime = 60;
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumePower(20f);
	}};
	public GenericCrafter 虚空提取仪 = new GenericCrafter("虚空提取仪") {{
		size = 4;
		craftTime = 60;
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumePower(9000f);
	}};
	
	@Override
	public void run() {
		//融合
		{
			究极纳米元素融合机.requirements = ItemStack.with(items.金元素, 120, items.木元素, 120, items.水元素, 120, items.火元素, 120, items.土元素, 120);
			究极纳米元素融合机.consumeItems(with(items.金元素, 1, items.木元素, 1, items.水元素, 1, items.火元素, 1, items.土元素, 1));
			究极纳米元素融合机.outputItem = new ItemStack(items.光元素, 5);
			preset.inject(究极纳米元素融合机, 4);
		}
		//流体
		{
			纳米元素流体制造机.requirements = ItemStack.with(items.金元素, 120, items.木元素, 120, items.水元素, 120, items.火元素, 120, items.土元素, 120);
			纳米元素流体制造机.consumeItems(with(items.光元素, 1));
			纳米元素流体制造机.outputLiquid = new LiquidStack(liquid.纳米元素流体, 5);
			preset.inject(纳米元素流体制造机, 4);
		}
		//虚空
		{
			虚空提取仪.requirements = ItemStack.with(items.金元素, 120, items.木元素, 120, items.水元素, 120, items.火元素, 120, items.土元素, 120);
			虚空提取仪.outputItem = new ItemStack(items.暗元素, 5);
			preset.inject(虚空提取仪, 4);
		}
	}
}
