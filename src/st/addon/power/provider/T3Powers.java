package st.addon.power.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import mindustry.type.ItemStack;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.ImpactReactor;
import mindustry.world.blocks.power.ThermalGenerator;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawPlasma;
import mindustry.world.draw.DrawWarmupRegion;
import st.addon.content.SContent;
import st.addon.content.provider.AttrProvider;
import st.addon.content.provider.ItemProvider;

@Provider
public class T3Powers extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = AttrProvider.class)
	AttrProvider attrs;
	@Require(cls = PowerPreset.class)
	PowerPreset preset;
	//木元素(5s) 64k(16k) 木元素发电机
	public ImpactReactor 木元素 = new ImpactReactor("t3木元素发电机") {{
		size = 4;
		powerProduction = 64020 / 60f;
		consumePower(16020 / 60f);
		itemDuration = 60f * 5;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
		drawer = new DrawMulti(new DrawPlasma(), new DrawDefault(), new DrawWarmupRegion());
	}};
	//光元素(5s) 1000k 1020000(128k) 光元素发电机
	public ImpactReactor 光元素 = new ImpactReactor("t3光元素发电机") {{
		size = 4;
		powerProduction = 1020000 / 60f;
		consumePower(128040 / 60f);
		itemDuration = 60f * 5;
		ambientSound = Sounds.smelter;
		ambientSoundVolume = 0.03f;
		drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
	}};
	//32k
	public ThermalGenerator 零点矩阵 = new ThermalGenerator("t3零点矩阵") {{
		canOverdrive = false;
		powerProduction = 32000 / 60f;
		generateEffect = Fx.redgeneratespark;
		effectChance = 0.011f;
		size = 1;
		floating = true;
		ambientSound = Sounds.hum;
		ambientSoundVolume = 0.06f;
	}};
	//96k
	public ThermalGenerator t3暗能量矩阵 = new ThermalGenerator("t3暗能量矩阵") {{
		canOverdrive = false;
		powerProduction = 96000 / 60f;
		generateEffect = Fx.redgeneratespark;
		effectChance = 0.011f;
		size = 1;
		floating = true;
		ambientSound = Sounds.hum;
		ambientSoundVolume = 0.06f;
	}};
	//激光节点
	public Battery 电池 = new Battery("t3电池") {{
		size = 1;
		consumePowerBuffered(50000000f);
		baseExplosiveness = 25f;
	}};
	
	@Override
	public void run() {
		//金元素
		{
			木元素.requirements = ItemStack.with(items.金元素, 2500, items.木元素, 500, items.水元素, 1500, items.火元素, 500);
			木元素.consumeItem(items.木元素, 1);
			preset.inject(木元素, 4);
		}
		//光元素
		{
			光元素.requirements = ItemStack.with(items.金元素, 5000, items.木元素, 1000, items.水元素, 3000, items.火元素, 1000, items.光元素, 2500, items.暗元素, 1500);
			光元素.consumeItem(items.光元素, 1);
			preset.inject(光元素, 4);
		}
		//零点矩阵
		{
			零点矩阵.requirements = ItemStack.with(items.金元素, 5000, items.木元素, 2500, items.水元素, 5000, items.火元素, 2000, items.光元素, 10000, items.暗元素, 10000);
			零点矩阵.attribute = attrs.水;
			preset.inject(零点矩阵, 4);
		}
		//暗能量矩阵
		{
			t3暗能量矩阵.requirements = ItemStack.with(items.金元素, 15000, items.木元素, 7500, items.水元素, 15000, items.火元素, 6000, items.光元素, 30000, items.暗元素, 30000);
			t3暗能量矩阵.attribute = attrs.水;
			preset.inject(t3暗能量矩阵, 4);
		}
		//电池
		{
			电池.requirements = ItemStack.with(items.金元素, 50, items.土元素, 50, items.木元素, 25);
			preset.inject(电池, 3);
		}
	}
}
