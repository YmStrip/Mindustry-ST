package st.addon.stransport.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import st.addon.content.SContent;
import st.addon.content.entity.STooltipBuilder;
import st.addon.content.provider.TooltipProvider;
import st.addon.content.provider.ValueProvider;

@Provider
public class TransportPreset extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	@Import(cls = SContent.class)
	@Require(cls = ValueProvider.class)
	ValueProvider value;
	public STooltipBuilder inject(Block b) {
		value.health(b);
		b.buildVisibility = BuildVisibility.shown;
		return tooltip
			.tooltip(b.stats);
	}
	
	public STooltipBuilder inject(Block b, int level) {
		return inject(b).techLevel(level);
	}
}
