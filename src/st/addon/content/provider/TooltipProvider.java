package st.addon.content.provider;

import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.Stats;
import st.addon.content.entity.STooltipBuilder;

@Provider
public class TooltipProvider extends LayerProvider {
	public StatCat cat() {
		return new StatCat("st");
	}
	
	public STooltipBuilder tooltip(Stats stats, StatCat cat) {
		return new STooltipBuilder(stats, cat);
	}
	
	public STooltipBuilder tooltip(Stats stats) {
		return new STooltipBuilder(stats, cat());
	}
}
