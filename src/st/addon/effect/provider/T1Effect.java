package st.addon.effect.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.type.ItemStack;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.addon.effect.entity.SCore;
import st.provider.place.SPlace;
import st.provider.place.provider.PlaceProvider;

@Provider
public class T1Effect extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SPlace.class)
	@Require(cls = PlaceProvider.class)
	PlaceProvider places;
	@Require(cls = EffectPreset.class)
	EffectPreset preset;
	public SCore 前哨核心 = new SCore("t1前哨核心") {{
		size = 2;
		canBreak = true;
	}};
	public CoreBlock 纳米核心 = new CoreBlock("t1纳米核心") {{
		size = 3;
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
	}
}
