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
import st.addon.content.provider.TooltipProvider;
import st.addon.prod.entity.UpgradeProds;

import static mindustry.type.ItemStack.with;

@Provider
public class T1Prods extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	@Require(cls = ProdPreset.class)
	ProdPreset preset;
	//纳米碳管构建厂
	public GenericCrafter 纳米碳管构建厂 = new GenericCrafter("纳米碳管构建厂") {{
		consumePower(6f);
		craftTime = 30;
		requirements = ItemStack.with(Items.copper, 100, Items.lead, 80, Items.silicon, 150, Items.titanium, 80, Items.metaglass, 80);
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumeItems(with(Items.coal, 2));
		
	}};
	//超导体构建厂
	public GenericCrafter 超导体构建厂 = new GenericCrafter("超导体构建厂") {{
		consumePower(8f);
		craftTime = 30;
		requirements = ItemStack.with(Items.copper, 150, Items.lead, 100, Items.silicon, 250, Items.titanium, 150, Items.metaglass, 150);
		craftEffect = Fx.smeltsmoke;
		hasPower = true;
		consumeItems(with(Items.surgeAlloy, 2, Items.phaseFabric, 2));
		
	}};
	public UpgradeProds u = new UpgradeProds();
	//超导硅厂
	public GenericCrafter 超导玻璃厂 = u.new UpgradeProd("超导玻璃厂")
		.input(with(Items.sand, 2))
		.output(new ItemStack(Items.metaglass, 2))
		.multiplier(2f)
		.power(5)
		.t(UpgradeProds.type.t1)
		.block();
	public GenericCrafter 超导硅厂 = u.new UpgradeProd("超导硅厂")
		.input(with(Items.sand, 2))
		.output(new ItemStack(Items.silicon, 2))
		.multiplier(2.2f)
		.power(5)
		.t(UpgradeProds.type.t1)
		.block();
	public GenericCrafter 超导相位物厂 = u.new UpgradeProd("超导相位物厂")
		.input(with(Items.sand, 3, Items.thorium, 2))
		.output(new ItemStack(Items.phaseFabric, 2))
		.size(3)
		.power(8f)
		.multiplier(4f)
		.t(UpgradeProds.type.t1)
		.block();
	public GenericCrafter 超导石墨厂 = u.new UpgradeProd("超导石墨厂")
		.input(with(Items.coal, 2))
		.output(new ItemStack(Items.graphite, 3))
		.power(5)
		.multiplier(2.75f)
		.t(UpgradeProds.type.t1)
		.block();
	public GenericCrafter 超导塑钢厂 = u.new UpgradeProd("超导塑钢厂")
		.input(with(Items.titanium, 2, Items.coal, 2))
		.size(3)
		.output(new ItemStack(Items.plastanium, 2))
		.power(8)
		.multiplier(2.5f)
		.t(UpgradeProds.type.t1)
		.block();
	public GenericCrafter 超导合金厂 = u.new UpgradeProd("超导合金厂")
		.input(with(Items.titanium, 2, Items.silicon, 2, Items.copper, 2, Items.lead, 2))
		.size(3)
		.output(new ItemStack(Items.surgeAlloy, 2))
		.power(8.5f)
		.multiplier(4.2f)
		.t(UpgradeProds.type.t1)
		.block();
	public GenericCrafter 超导硫厂 = u.new UpgradeProd("超导硫厂")
		.input(with(Items.coal, 2, Items.sporePod, 2))
		.output(new ItemStack(Items.pyratite, 2))
		.power(5)
		.multiplier(2.5f)
		.t(UpgradeProds.type.t1)
		.block();
	public GenericCrafter 超导爆炸厂 = u.new UpgradeProd("超导爆炸厂")
		.input(with(Items.sporePod, 2, Items.coal, 2, Items.sand, 2))
		.output(new ItemStack(Items.blastCompound, 2))
		.power(10)
		.multiplier(2.5f)
		.t(UpgradeProds.type.t1)
		.block();
	public GenericCrafter 超导冷冻液厂 = u.new UpgradeProd("超导冷冻液厂")
		.input(with(Items.titanium, 2))
		.inputLiquid(new LiquidStack(Liquids.water, 1))
		.outputLiquid(new LiquidStack(Liquids.cryofluid, 5))
		.power(6)
		.multiplier(2.75f)
		.t(UpgradeProds.type.t1)
		.block();
	
	@Override
	public void run() {
		//纳米碳管
		{
			纳米碳管构建厂.outputItem = new ItemStack(items.纳米碳管, 2);
			preset.inject(纳米碳管构建厂);
		}
		//超导体
		{
			超导体构建厂.outputItem = new ItemStack(items.超导体, 1);
			preset.inject(超导体构建厂);
		}
		u.registry(items, preset);
	}
}
