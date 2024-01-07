package st.addon.effect.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.ForceProjector;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;
import st.addon.content.SContent;
import st.addon.content.entity.STooltipBuilder;
import st.addon.content.provider.TooltipProvider;
import st.addon.content.provider.ValueProvider;
import st.provider.place.provider.PlaceProvider;

@Provider
public class EffectPreset extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	@Import(cls = SContent.class)
	@Require(cls = ValueProvider.class)
	ValueProvider values;
	
	public STooltipBuilder inject(Block b, int level) {
		b.buildVisibility = BuildVisibility.shown;
		b.category = Category.effect;
		values.health(b);
		if (b instanceof ForceProjector f) {
			values.shield(f);
		}
		if (b instanceof CoreBlock) b.health = b.health * 5;
		return tooltip
			.tooltip(b.stats)
			.techLevel(level);
	}
	
	public STooltipBuilder inject(Block b) {
		return inject(b, 1);
	}
}
