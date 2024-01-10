package st.addon.prod.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.blocks.production.GenericCrafter;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.addon.prod.entity.UpgradeProds;

import static mindustry.type.ItemStack.with;

@Provider
public class T2Prods extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Require(cls = ProdPreset.class)
	ProdPreset preset;
	public GenericCrafter 反物质构造厂 = new GenericCrafter("反物质构造厂") {{
		size = 4;
		craftTime = 30;
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumePower(24f);
		consumeItems(with(Items.blastCompound, 1, Items.titanium, 1));
	}};
	public GenericCrafter 反重力陶瓷构造厂 = new GenericCrafter("反重力陶瓷构造厂") {{
		size = 4;
		craftTime = 30;
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumePower(35f);
	}};
	public GenericCrafter 辐矿石构造厂 = new GenericCrafter("辐矿石构造厂") {{
		size = 4;
		craftTime = 30;
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumePower(15f);
	}};
	public GenericCrafter 铬纳尔构造厂 = new GenericCrafter("铬纳尔构造厂") {{
		size = 4;
		craftTime = 30;
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumePower(24f);
	}};
	public GenericCrafter 晶金构造厂 = new GenericCrafter("晶金构造厂") {{
		size = 4;
		craftTime = 30;
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumePower(28f);
	}};
	public UpgradeProds u = new UpgradeProds();
	public GenericCrafter 三钛合金厂 = u.new UpgradeProd("三钛合金厂")
		.input(with(Items.titanium, 3))
		.inputLiquid(new LiquidStack(Liquids.water, 0.5f))
		.speed(30)
		.size(4)
		.output(new ItemStack(Items.surgeAlloy, 2))
		.power(34f)
		.multiplier(3.5f)
		.t(UpgradeProds.type.t2)
		.block();
	
	@Override
	public void run() {
		//反物质
		{
			反物质构造厂.requirements = ItemStack.with(Items.lead, 450, Items.silicon, 300, Items.titanium, 250, Items.surgeAlloy, 300, items.纳米碳管, 250, items.超导体, 500);
			反物质构造厂.outputItem = new ItemStack(items.反物质, 2);
			preset.inject(反物质构造厂);
		}
		//反重力
		{
			反重力陶瓷构造厂.requirements = ItemStack.with(Items.copper, 1000, Items.lead, 500, Items.silicon, 800, Items.titanium, 500, Items.metaglass, 500, Items.surgeAlloy, 500, items.纳米碳管, 500, items.超导体, 1000);
			反重力陶瓷构造厂.consumeItems(ItemStack.with(Items.phaseFabric, 1, items.纳米碳管, 1));
			反重力陶瓷构造厂.outputItem = new ItemStack(items.反重力陶瓷, 2);
			preset.inject(反重力陶瓷构造厂);
		}
		//辐矿
		{
			辐矿石构造厂.requirements = ItemStack.with(Items.lead, 250, Items.silicon, 150, Items.titanium, 250, Items.surgeAlloy, 100, items.纳米碳管, 250);
			辐矿石构造厂.consumeItems(ItemStack.with(Items.thorium, 2, items.纳米碳管, 1));
			辐矿石构造厂.outputItem = new ItemStack(items.辐矿石, 1);
			preset.inject(辐矿石构造厂);
		}
		//铬纳尔
		{
			铬纳尔构造厂.requirements = ItemStack.with(Items.lead, 500, Items.silicon, 350, Items.titanium, 300, Items.surgeAlloy, 250, Items.plastanium, 250, items.纳米碳管, 500, items.超导体, 50);
			铬纳尔构造厂.consumeItems(ItemStack.with(Items.surgeAlloy, 2, items.纳米碳管, 1));
			铬纳尔构造厂.outputItem = new ItemStack(items.铬纳尔, 1);
			preset.inject(铬纳尔构造厂);
		}
		//金晶
		{
			晶金构造厂.requirements = ItemStack.with(Items.copper, 650, Items.lead, 450, Items.silicon, 300, Items.titanium, 350, Items.surgeAlloy, 350, Items.plastanium, 350, Items.phaseFabric, 350, items.纳米碳管, 250, items.超导体, 150);
			晶金构造厂.consumeItems(ItemStack.with(Items.metaglass, 2, items.纳米碳管, 1));
			晶金构造厂.outputItem = new ItemStack(items.晶金, 1);
			preset.inject(晶金构造厂);
		}
	}
}
