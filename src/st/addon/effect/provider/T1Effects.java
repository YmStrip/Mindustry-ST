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
import st.addon.effect.entity.SOutpost;
import st.provider.place.SPlace;
import st.provider.place.provider.PlaceProvider;

@Provider
public class T1Effects extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SPlace.class)
	@Require(cls = PlaceProvider.class)
	PlaceProvider places;
	@Require(cls = EffectPreset.class)
	EffectPreset preset;
	public SOutpost 前哨核心 = new SOutpost("t1前哨核心") {{
		size = 2;
		isFirstTier = true;
		canBreak = true;
		unitCapModifier = 4;
	}};
	public CoreBlock 核心 = new CoreBlock("t1核心") {{
		isFirstTier = true;
		itemCapacity = 12000;
		unitCapModifier = 12;
		size = 3;
	}};
	public MendProjector 修复器 = new MendProjector("t1修复器") {{
		consumePower(4f);
		reload = 180f;
		range = 24 * 8f;
		healPercent = 15f;
		phaseBoost = 15f;
		size = 2;
		phaseRangeBoost = 5 * 8f;
		consumeItem(Items.phaseFabric).boost();
	}};
	public OverdriveProjector 加速器 = new OverdriveProjector("t1加速器") {{
		consumePower(10f);
		range = 24 * 8f;
		size = 2;
		speedBoostPhase = 0.5f;
		speedBoost = 2.5f;
		hasBoost = true;
		phaseRangeBoost = 5 * 8f;
		useTime = 300f;
		consumeItem(Items.phaseFabric).boost();
	}};
	public SForceProjector 力场 = new SForceProjector("t1力场") {{
		consumePower(8f);
		size = 2;
		radius = 18 * 8;
		sides = 6;
		cooldownNormal = 0.4f;
		backColor = Color.cyan;
		category = Category.effect;
	}};
	public StorageBlock 仓库 = new StorageBlock("t1仓库") {{
		size = 2;
		itemCapacity = 2500;
		scaledHealth = 55;
	}};
	
	@Override
	public void run() {
		//前哨核心
		{
			前哨核心.requirements = ItemStack.with(items.纳米碳管, 150, Items.copper, 1000, Items.lead, 1000, Items.silicon, 800, Items.titanium, 500);
			前哨核心.places(places);
			places.max(前哨核心, 8);
			preset.inject(前哨核心, 1)
				.maxPlace(8);
		}
		//超导核心
		{
			核心.requirements = ItemStack.with(items.纳米碳管, 1000, items.超导体, 500, Items.silicon, 1000, Items.titanium, 850, Items.metaglass, 800, Items.thorium, 1000);
			preset.inject(核心, 1);
		}
		//超导修复器
		{
			修复器.requirements = ItemStack.with(items.纳米碳管, 150, items.超导体, 50, Items.silicon, 100, Items.plastanium, 150, Items.titanium, 100, Items.surgeAlloy, 50);
			preset.inject(修复器, 1);
		}
		//超导加速器
		{
			加速器.requirements = ItemStack.with(items.纳米碳管, 200, items.超导体, 100, Items.silicon, 150, Items.plastanium, 200, Items.titanium, 250, Items.surgeAlloy, 150);
			preset.inject(加速器, 1);
		}
		//超导力场
		{
			力场.requirements = ItemStack.with(items.纳米碳管, 250, items.超导体, 150, Items.silicon, 200, Items.plastanium, 150, Items.titanium, 200, Items.surgeAlloy, 200);
			preset.inject(力场, 1);
		}
		//超导仓库
		{
			仓库.requirements = ItemStack.with(items.纳米碳管, 150, items.超导体, 50, Items.silicon, 50, Items.titanium, 100);
			preset.inject(仓库, 1);
		}
	}
}
