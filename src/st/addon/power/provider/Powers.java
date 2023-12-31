package st.addon.power.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import st.addon.item.ITEM;
import st.addon.item.provider.AttrProvider;
import st.addon.item.provider.ItemProvider;

@Provider
public class Powers extends LayerProvider {
	@Import(cls = ITEM.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = ITEM.class)
	@Require(cls = AttrProvider.class)
	AttrProvider attrs;
	@Require(cls = PowerPreset.class)
	PowerPreset powerPreset;
	//T1
	//生物质发电机 孢子夹 540(1.5s)
	public ConsumeGenerator 生物质发电机 = new ConsumeGenerator("生物钟发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 100, Items.lead, 120, Items.titanium, 60, Items.silicon, 50));
		size = 2;
		powerProduction = 540 / 60f;
		itemDuration = 90f;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		generateEffect = Fx.generatespark;
		consumeItem(Items.sporePod);
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
	}};
	//燃煤发电机 煤炭  600(1.5s)
	public ConsumeGenerator 燃煤发电机 = new ConsumeGenerator("燃煤发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 80, Items.lead, 100, Items.titanium, 100, Items.silicon, 50));
		size = 2;
		powerProduction = 600 / 60f;
		itemDuration = 90f;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		generateEffect = Fx.generatespark;
		consumeItem(Items.coal);
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
	}};
	//石油发电机 石油/60 600
	public ConsumeGenerator 石油发电机 = new ConsumeGenerator("石油发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, Items.graphite, 150, 150, Items.lead, 120, Items.metaglass, 100, Items.titanium, 60, Items.silicon, 50, Items.plastanium, 80));
		size = 2;
		powerProduction = 600 / 60f;
		itemDuration = 90f;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		generateEffect = Fx.generatespark;
		consumeLiquid(Liquids.oil, 1f);
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
	}};
	//合金发电机 合金 1500(1s)
	public ConsumeGenerator 合金发电机 = new ConsumeGenerator("合金发电机") {{
		requirements(Category.power, ItemStack.with(Items.lead, 500, Items.metaglass, 300, Items.graphite, 450, Items.thorium, 150, Items.silicon, 300, Items.surgeAlloy, 100));
		size = 3;
		powerProduction = 1500 / 60f;
		itemDuration = 60f;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		generateEffect = Fx.generatespark;
		consumeItem(Items.surgeAlloy);
		consumeLiquid(Liquids.cryofluid, 60 / 16f);
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
	}};
	//水力发电机 120
	public ThermalGenerator 水力发电机 = new ThermalGenerator("水力发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 250, Items.lead, 300, Items.titanium, 250, Items.silicon, 200, Items.graphite, 200, Items.plastanium, 250));
		powerProduction = 120 / 60f;
		generateEffect = Fx.redgeneratespark;
		effectChance = 0.011f;
		size = 2;
		floating = true;
		ambientSound = Sounds.hum;
		ambientSoundVolume = 0.06f;
	}};
	//太阳能发电机 180
	public SolarGenerator 太阳能发电机 = new SolarGenerator("太阳能发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 200, Items.lead, 300, Items.titanium, 250, Items.silicon, 200, Items.graphite, 200, Items.plastanium, 250));
		powerProduction = 180 / 60f;
		size = 2;
	}};
	//地热发电机 240
	public ThermalGenerator 地热发电机 = new ThermalGenerator("地热发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 100, Items.lead, 300, Items.titanium, 250, Items.silicon, 200, Items.graphite, 200, Items.plastanium, 250));
		powerProduction = 240 / 60f;
		size = 2;
	}};
	//核能发电机 钍(1.5s) 水(16) 1080
	public NuclearReactor 核能发电机 = new NuclearReactor("核能发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 150, Items.lead, 200, Items.titanium, 150, Items.silicon, 100, Items.graphite, 100, Items.plastanium, 150));
		ambientSound = Sounds.hum;
		ambientSoundVolume = 0.24f;
		size = 3;
		itemDuration = 360f;
		powerProduction = 1080 / 60f;
		heating = 0.02f;
		consumeItem(Items.thorium);
		consumeLiquid(Liquids.water, heating / coolantPower).update(false);
	}};
	//反物质发电机 反物质 16k(2s)
	public ImpactReactor 反物质发电机 = new ImpactReactor("反物质发电机") {{
		size = 4;
		powerProduction = 16020 / 60f;
		itemDuration = 120f;
		ambientSound = Sounds.pulse;
		ambientSoundVolume = 0.07f;
		consumePower(1320 / 60f);
		consumeLiquid(Liquids.cryofluid, 0.25f);
		drawer = new DrawMulti(new DrawPlasma(), new DrawDefault(), new DrawWarmupRegion());
	}};
	//聚变发电机 水 64k(120/s)
	public ImpactReactor 水聚变发电机 = new ImpactReactor("水聚变发电机") {{
		canOverdrive = false;
		size = 4;
		powerProduction = 66000 / 60f;
		ambientSound = Sounds.pulse;
		ambientSoundVolume = 0.07f;
		consumePower(6000 / 60f);
		consumeLiquid(Liquids.cryofluid, 2f);
		drawer = new DrawMulti(new DrawPlasma(), new DrawDefault(), new DrawWarmupRegion());
	}};
	//流沙发电机 * 600
	public ThermalGenerator 流沙发电机 = new ThermalGenerator("流沙发电机") {{
		canOverdrive = false;
		powerProduction = 600 / 60f;
		generateEffect = Fx.redgeneratespark;
		effectChance = 0.011f;
		size = 2;
		floating = true;
		ambientSound = Sounds.hum;
		ambientSoundVolume = 0.06f;
		attribute = Attribute.oil;
	}};
	//零点矩阵 * 16k
	public ThermalGenerator 零点矩阵 = new ThermalGenerator("零点矩阵") {{
		canOverdrive = false;
		powerProduction = 16000 / 60f;
		generateEffect = Fx.redgeneratespark;
		effectChance = 0.011f;
		size = 1;
		floating = true;
		ambientSound = Sounds.hum;
		ambientSoundVolume = 0.06f;
	}};
	//暗能量矩阵 * 64k
	public ThermalGenerator 暗能量矩阵 = new ThermalGenerator("暗能量矩阵") {{
		canOverdrive = false;
		powerProduction = 64000 / 60f;
		generateEffect = Fx.redgeneratespark;
		effectChance = 0.011f;
		size = 1;
		floating = true;
		ambientSound = Sounds.hum;
		ambientSoundVolume = 0.06f;
	}};
	
	
	@Override
	public void run() {
		//合金
		{
			合金发电机.requirements = ItemStack.with(items.金元素, 250, items.木元素, 50, items.水元素, 150, items.土元素, 100);
			powerPreset.inject(合金发电机);
		}
		//反物质
		{
			反物质发电机.requirements = ItemStack.with(items.金元素, 500, items.木元素, 500, items.水元素, 1000, items.土元素, 500);
			反物质发电机.consumeItem(items.反物质);
			powerPreset.inject(反物质发电机, 2);
		}
		//聚变
		{
			水聚变发电机.requirements = ItemStack.with(items.金元素, 5000, items.木元素, 10000, items.水元素, 15000, items.土元素, 8000);
			powerPreset.inject(水聚变发电机, 2);
		}
		//流沙
		{
			流沙发电机.requirements = ItemStack.with(items.金元素, 1000, items.木元素, 500, items.水元素, 1500, items.土元素, 1000);
			powerPreset.inject(流沙发电机, 1);
		}
		//零点矩阵
		{
			零点矩阵.requirements = ItemStack.with(items.金元素, 10000, items.木元素, 15000, items.水元素, 20000, items.土元素, 10000, items.光元素, 2500);
			零点矩阵.attribute = attrs.以太;
			powerPreset.inject(零点矩阵, 3);
		}
		//暗能量矩阵
		{
			暗能量矩阵.requirements = ItemStack.with(items.金元素, 20000, items.木元素, 30000, items.水元素, 40000, items.土元素, 20000, items.光元素, 5000, items.暗元素, 2500);
		}
	}
}
