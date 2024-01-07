package st.addon.effect.provider;

import arc.graphics.Color;
import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.blocks.defense.OverdriveProjector;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.addon.effect.entity.SForceProjector;
import st.provider.place.SPlace;
import st.provider.place.provider.PlaceProvider;

@Provider
public class T2Effects extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Require(cls = EffectPreset.class)
	EffectPreset preset;
	public CoreBlock 核心 = new CoreBlock("t2核心") {{
		isFirstTier = true;
		itemCapacity = 24000;
		unitCapModifier = 15;
		size = 4;
	}};
	public MendProjector 修复器 = new MendProjector("t2修复器") {{
		consumePower(10f);
		reload = 180f;
		range = 28 * 8f;
		healPercent = 25f;
		phaseBoost = 5f;
		size = 2;
		phaseRangeBoost = 5 * 8f;
		consumeItem(Items.phaseFabric).boost();
	}};
	public OverdriveProjector 加速器 = new OverdriveProjector("t2加速器") {{
		consumePower(10f);
		range = 28 * 8f;
		size = 2;
		speedBoostPhase = 0.5f;
		speedBoost = 3.5f;
		hasBoost = true;
		phaseRangeBoost = 5 * 8f;
		useTime = 300f;
		consumeItem(Items.phaseFabric).boost();
	}};
	public SForceProjector 力场 = new SForceProjector("t2力场") {{
		consumePower(8f);
		size = 2;
		radius = 24 * 8;
		sides = 6;
		cooldownNormal = 0.15f;
		backColor = Color.cyan;
		category = Category.effect;
	}};
	public StorageBlock 仓库 = new StorageBlock("t2仓库") {{
		size = 3;
		itemCapacity = 60000;
		scaledHealth = 55;
	}};
	
	@Override
	public void run() {
		//核心
		{
			核心.requirements = ItemStack.with(items.纳米碳管, 5000, items.超导体, 2500, items.铬纳尔, 4500, items.反重力陶瓷, 2500, items.晶金, 1500);
			preset.inject(核心, 2);
		}
		//修复器
		{
			修复器.requirements = ItemStack.with(items.纳米碳管, 250, items.超导体, 150, items.铬纳尔, 250, items.晶金, 150);
			修复器.consumeItem(items.辐矿石).boost();
			preset.inject(修复器, 2);
		}
		//加速器
		{
			加速器.requirements = ItemStack.with(items.纳米碳管, 350, items.超导体, 250, items.铬纳尔, 350, items.晶金, 250);
			加速器.consumeItem(items.辐矿石).boost();
			preset.inject(加速器, 2);
		}
		//力场
		{
			力场.requirements = ItemStack.with(items.纳米碳管, 500, items.超导体, 350, items.晶金, 300);
			力场.consumeItem(items.辐矿石).boost();
			preset.inject(力场, 2);
		}
		//仓库
		{
			仓库.requirements = ItemStack.with(items.纳米碳管, 500, items.超导体, 300, items.铬纳尔, 150, items.晶金, 200);
			preset.inject(仓库, 2);
		}
	}
}
