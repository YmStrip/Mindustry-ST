package st.addon.prod.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.type.Category;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BuildVisibility;
import st.addon.content.SContent;
import st.addon.content.provider.TooltipProvider;
import st.addon.content.provider.ValueProvider;

@Provider
public class ProdPreset extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ValueProvider.class)
	ValueProvider values;
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	
	public ProdPreset inject(GenericCrafter b, int level) {
		values.health(b);
		b.category = Category.crafting;
		b.buildVisibility = BuildVisibility.shown;
		tooltip
			.tooltip(b.stats)
			.techLevel(level)
		;
		return this;
	}
	
	public ProdPreset inject(GenericCrafter b) {
		return inject(b, 1);
	}
}
