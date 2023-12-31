package st.addon.power.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.type.Category;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.BuildVisibility;
import st.addon.tooltip.Tooltip;
import st.addon.tooltip.provider.ToolTipBar;
import st.plugin.value.SValue;
import st.plugin.value.provider.Values;

@Provider
public class PowerPreset extends LayerProvider {
	@Import(cls = Tooltip.class)
	@Require(cls = ToolTipBar.class)
	ToolTipBar toolTipBar;
	@Import(cls = SValue.class)
	@Require(cls = Values.class)
	Values values;
	
	public void inject(PowerGenerator b) {
		inject(b, 1);
	}
	
	public void inject(PowerGenerator b, int level) {
		b.category = Category.power;
		var cat = toolTipBar.cat();
		toolTipBar.showTechLevel(level, b.stats, cat);
		b.buildVisibility = BuildVisibility.shown;
		values.health(b);
	}
}
