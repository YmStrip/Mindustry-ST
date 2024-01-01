package st.addon.power.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.type.Category;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.BuildVisibility;
import st.addon.content.SContent;
import st.addon.content.provider.TooltipProvider;
import st.addon.content.provider.ValueProvider;

@Provider
public class PowerPreset extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider toolTip;
	@Import(cls = SContent.class)
	@Require(cls = ValueProvider.class)
	ValueProvider values;
	
	public PowerPreset inject(PowerGenerator b) {
		return inject(b, 1);
	}
	
	public PowerPreset inject(PowerGenerator b, int level) {
		values.health(b);
		b.category = Category.power;
		b.buildVisibility = BuildVisibility.shown;
		toolTip
			.tooltip(b.stats)
			.techLevel(level);
		return this;
	}
}
