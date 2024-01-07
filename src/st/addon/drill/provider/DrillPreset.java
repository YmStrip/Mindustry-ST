package st.addon.drill.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.production.SolidPump;
import mindustry.world.meta.BuildVisibility;
import st.addon.content.SContent;
import st.addon.content.entity.STooltipBuilder;
import st.addon.content.provider.TooltipProvider;
import st.addon.content.provider.ValueProvider;

@Provider
public class DrillPreset extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	@Import(cls = SContent.class)
	@Require(cls = ValueProvider.class)
	ValueProvider value;
	
	public STooltipBuilder inject(Block t) {
		t.buildVisibility = BuildVisibility.shown;
		t.category = Category.production;
		if (t instanceof Pump) {
			t.category = Category.liquid;
		}
		value.health(t);
		return tooltip.tooltip(t.stats);
	}
	
	public STooltipBuilder inject(Block t, int level) {
		return inject(t).techLevel(level);
	}
}
