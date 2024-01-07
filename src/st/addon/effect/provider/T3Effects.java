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
public class T3Effects extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Require(cls = EffectPreset.class)
	EffectPreset preset;
	public CoreBlock 核心 = new CoreBlock("t3核心") {{
		isFirstTier = true;
		itemCapacity = 60000;
		unitCapModifier = 17;
		size = 5;
	}};
	public MendProjector 修复器 = new MendProjector("t3修复器") {{
		consumePower(10f);
		reload = 180f;
		range = 32 * 8f;
		healPercent = 35f;
		phaseBoost = 5f;
		size = 2;
		phaseRangeBoost = 5 * 8f;
	}};
	public OverdriveProjector 加速器 = new OverdriveProjector("t3加速器") {{
		consumePower(10f);
		range = 32 * 8f;
		size = 2;
		speedBoostPhase = 0.5f;
		speedBoost = 4.5f;
		hasBoost = true;
		phaseRangeBoost = 5 * 8f;
		useTime = 300f;
	}};
	public SForceProjector 力场 = new SForceProjector("t3力场") {{
		consumePower(8f);
		size = 2;
		radius = 32 * 8;
		sides = 6;
		cooldownNormal = 0.05f;
		backColor = Color.cyan;
		category = Category.effect;
	}};
	public StorageBlock 仓库 = new StorageBlock("t3仓库") {{
		size = 4;
		itemCapacity = 240000;
		scaledHealth = 55;
	}};
	
	@Override
	public void run() {
		//核心
		{
			核心.requirements = ItemStack.with(items.金元素, 750, items.木元素, 500, items.水元素, 500, items.土元素, 1000);
			preset.inject(核心, 3);
		}
		//修复器
		{
			修复器.requirements = ItemStack.with(items.金元素, 300, items.木元素, 150, items.水元素, 250, items.土元素, 500);
			修复器.consumeItem(items.火元素).boost();
			preset.inject(修复器, 3);
		}
		//加速器
		{
			加速器.requirements = ItemStack.with(items.金元素, 500, items.木元素, 500, items.水元素, 450, items.土元素, 500);
			加速器.consumeItem(items.火元素).boost();
			preset.inject(加速器, 3);
		}
		//力场
		{
			力场.requirements = ItemStack.with(items.金元素, 250, items.木元素, 150, items.水元素, 250, items.土元素, 350);
			力场.consumeItem(items.土元素).boost();
			preset.inject(力场, 3);
		}
		//仓库
		{
			仓库.requirements = ItemStack.with(items.金元素, 250, items.木元素, 150, items.水元素, 250, items.土元素, 450);
			preset.inject(仓库, 3);
		}
	}
}
