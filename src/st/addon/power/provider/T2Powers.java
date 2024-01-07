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
import mindustry.type.ItemStack;
import mindustry.world.blocks.power.*;
import mindustry.world.consumers.ConsumeItemExplode;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawPlasma;
import mindustry.world.draw.DrawWarmupRegion;
import mindustry.world.meta.Attribute;
import st.addon.content.SContent;
import st.addon.content.provider.AttrProvider;
import st.addon.content.provider.ItemProvider;

//请阅读这篇文章，卡尔达肖夫指数（英语：Kardashev Scale，俄语：Шкала Кардашёва，又译卡尔达肖夫尺度）是根据一个文明所能够利用的能源量级，来量度文明层次及技术先进程度的一种假说。1964年苏联天文学家尼古拉·卡尔达肖夫首先提出用能量级把文明分成三个量级：I型、II型和III型。I型文明使用在它的故乡行星所有可用的能量，II型文明利用它的行星所围绕的恒星所有的能量，III型文明则利用它所处星系的所有能量。一般认为人类文明现在接近但尚未达到I型文明，经过其公式换算目前大约处在0.75级左右[来源请求]。其中，分别分析I,II,III型文明的科技水平,并且为每个文明写出10个最高科技的发电机名称


@Provider
public class T2Powers extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = AttrProvider.class)
	AttrProvider attrs;
	@Require(cls = PowerPreset.class)
	PowerPreset preset;
	//反物质(2s) 12k
	public ImpactReactor 反物质 = new ImpactReactor("t2反物质发电机") {{
		size = 3;
		powerProduction = 12000 / 60f;
		itemDuration = 60f * 2;
		ambientSound = Sounds.pulse;
		ambientSoundVolume = 0.07f;
		consumePower(1320 / 60f);
		consumeLiquid(Liquids.water, 0.25f);
		drawer = new DrawMulti(new DrawPlasma(), new DrawDefault(), new DrawWarmupRegion());
	}};
	//水(120/s) 14k-1.2k
	public ImpactReactor 水聚变 = new ImpactReactor("t2水聚变发电机") {{
		canOverdrive = false;
		size = 4;
		powerProduction = 6000 / 60f;
		ambientSound = Sounds.pulse;
		ambientSoundVolume = 0.07f;
		consumePower(1200 / 60f);
		consumeLiquid(Liquids.water, 2f);
		drawer = new DrawMulti(new DrawPlasma(), new DrawDefault(), new DrawWarmupRegion());
	}};
	//流沙发电机 600
	public ThermalGenerator 流沙 = new ThermalGenerator("t2流沙发电机") {{
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
	//等离子发电机 x(5s) 2.txt.4k (火力发电)
	public ConsumeGenerator 等离子 = new ConsumeGenerator("t2等离子发电机") {{
		size = 3;
		powerProduction = 2400 / 60f;
		itemDuration = 60 * 5;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		generateEffect = Fx.generatespark;
		consume(new ConsumeItemFlammable());
		consume(new ConsumeItemExplode());
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
	}};
	//反重力(60s) 3k
	public ConsumeGenerator 引力波 = new ConsumeGenerator("t2引力波发电机") {{
		size = 3;
		powerProduction = 3000 / 60f;
		itemDuration = 60 * 30;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		generateEffect = Fx.generatespark;
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
	}};
	//辐矿石(4s) 24k
	public ImpactReactor 辐矿石 = new ImpactReactor("t2辐矿石发电机") {{
		size = 4;
		powerProduction = 24000 / 60f;
		itemDuration = 60f * 4;
		ambientSound = Sounds.pulse;
		ambientSoundVolume = 0.07f;
		consumePower(1320 / 60f);
		drawer = new DrawMulti(new DrawPlasma(), new DrawDefault(), new DrawWarmupRegion());
	}};
	//激光节点
	public BeamNode 激光节点 = new BeamNode("t2激光节点") {{
		size = 1;
		laserColor2 = Color.rgb(200,255,255);
		consumesPower = outputsPower = true;
		range = 60;
		fogRadius = 10;
		consumePowerBuffered(12000f);
	}};
	//激光节点
	public Battery 电池 = new Battery("t2电池") {{
		size = 1;
		consumePowerBuffered(400000f);
		baseExplosiveness = 10f;
	}};
	
	@Override
	public void run() {
		//反物质
		{
			反物质.requirements = ItemStack.with(Items.silicon, 60, items.纳米碳管, 50, items.晶金, 100, items.超导体, 50, items.铬纳尔, 150);
			反物质.consumeItem(items.反物质);
			preset.inject(反物质, 2);
		}
		//水聚变
		{
			水聚变.requirements = ItemStack.with(Items.silicon, 1000, items.纳米碳管, 350, items.晶金, 300, items.超导体, 500, items.铬纳尔, 500);
			preset.inject(水聚变, 2);
		}
		//流沙
		{
			流沙.requirements = ItemStack.with(Items.silicon, 100, items.纳米碳管, 50, items.超导体, 50, items.铬纳尔, 50);
			preset.inject(流沙, 2);
		}
		//等离子
		{
			等离子.requirements = ItemStack.with(Items.silicon, 250, items.纳米碳管, 50, items.晶金, 100, items.超导体, 50, items.铬纳尔, 150);
			preset.inject(等离子, 2);
		}
		//引力波
		{
			引力波.requirements = ItemStack.with(Items.silicon, 1000, items.纳米碳管, 750, items.晶金, 500, items.超导体, 500, items.铬纳尔, 750);
			引力波.consumeItem(items.反重力陶瓷);
			preset.inject(引力波, 2);
		}
		//辐矿石
		{
			辐矿石.requirements = ItemStack.with(Items.silicon, 1250, items.纳米碳管, 850, items.晶金, 650, items.超导体, 700, items.铬纳尔, 850);
			辐矿石.consumeItem(items.辐矿石);
			preset.inject(辐矿石, 2);
		}
		//激光节点
		{
			激光节点.requirements = ItemStack.with(items.超导体, 5, items.纳米碳管, 10, Items.silicon, 20);
			preset.inject(激光节点, 2);
		}
		//电池
		{
			电池.requirements = ItemStack.with(items.超导体, 50, items.纳米碳管, 100, Items.silicon, 150);
			preset.inject(电池, 2);
		}
	}
}
