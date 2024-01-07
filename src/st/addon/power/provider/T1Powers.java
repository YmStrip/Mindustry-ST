package st.addon.power.provider;

import arc.graphics.Color;
import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.power.*;
import mindustry.world.draw.*;
import st.addon.content.SContent;
import st.addon.content.provider.AttrProvider;
import st.addon.content.provider.ItemProvider;

@Provider
public class T1Powers extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = AttrProvider.class)
	AttrProvider attrs;
	@Require(cls = PowerPreset.class)
	PowerPreset preset;
	//生物质发电机 孢子夹 300(1.5s)
	public ConsumeGenerator 生物质 = new ConsumeGenerator("t1生物质发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 100, Items.lead, 120, Items.titanium, 60, Items.silicon, 50));
		size = 2;
		powerProduction = 300 / 60f;
		itemDuration = 90f;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		generateEffect = Fx.generatespark;
		consumeItem(Items.sporePod);
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
	}};
	//燃煤发电机 煤炭  540(1.5s)
	public ConsumeGenerator 煤炭 = new ConsumeGenerator("t1煤炭发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 80, Items.lead, 100, Items.titanium, 100, Items.silicon, 50));
		size = 2;
		powerProduction = 540 / 60f;
		itemDuration = 90f;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		generateEffect = Fx.generatespark;
		consumeItem(Items.coal);
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
	}};
	//石油发电机 石油/60 600
	public ConsumeGenerator 石油 = new ConsumeGenerator("t1石油发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 150, Items.graphite, 150, Items.lead, 120, Items.metaglass, 100, Items.titanium, 60, Items.silicon, 50, Items.plastanium, 80));
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
	public ConsumeGenerator 合金 = new ConsumeGenerator("t1合金发电机") {{
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
	public ThermalGenerator 水力 = new ThermalGenerator("t1水力发电机") {{
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
	public SolarGenerator 太阳能 = new SolarGenerator("t1太阳能发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 200, Items.lead, 300, Items.titanium, 250, Items.silicon, 200, Items.graphite, 200, Items.plastanium, 250));
		powerProduction = 180 / 60f;
		size = 2;
	}};
	//地热发电机 240
	public ThermalGenerator 地热 = new ThermalGenerator("t1地热发电机") {{
		requirements(Category.power, ItemStack.with(Items.copper, 100, Items.lead, 300, Items.titanium, 250, Items.silicon, 200, Items.graphite, 200, Items.plastanium, 250));
		powerProduction = 240 / 60f;
		size = 2;
	}};
	//核能发电机 钍(1.5s) 水(16) 1080
	public NuclearReactor 核能 = new NuclearReactor("t1核能发电机") {{
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
	/*
	
	//聚变发电机 水 64k(120/s)
	
	//流沙发电机 * 600
	
	//零点矩阵 * 16k
	
	//暗能量矩阵 * 64k

	*/
	
	//激光节点
	public BeamNode 激光节点 = new BeamNode("t1激光节点") {{
		size = 1;
		laserColor2 = Color.rgb(255,255,200);
		consumesPower = outputsPower = true;
		range = 24;
		fogRadius = 5;
		consumePowerBuffered(4000f);
		requirements(Category.power,ItemStack.with(Items.silicon,15,Items.titanium,15));
	}};
	//激光节点
	public Battery 电池 = new Battery("t1电池") {{
		size = 1;
		consumePowerBuffered(100000f);
		baseExplosiveness = 5f;
		requirements(Category.power,ItemStack.with(Items.silicon,100,Items.titanium,50,Items.copper,150,Items.lead,50));
	}};
	
	@Override
	public void run() {
		//合金
		preset
			.inject(生物质)
			.inject(煤炭)
			.inject(石油)
			.inject(水力)
			.inject(合金)
			.inject(太阳能)
			.inject(地热)
			.inject(核能)
			.inject(激光节点)
			.inject(电池)
		;
	}
}
