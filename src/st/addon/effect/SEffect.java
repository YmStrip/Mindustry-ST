package st.addon.effect;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.addon.effect.controller.EffectTech;
import st.addon.effect.provider.EffectPreset;
import st.addon.effect.provider.T1Effects;
import st.addon.effect.provider.T2Effects;
import st.addon.effect.provider.T3Effects;
import st.provider.place.SPlace;

@Module(name = "effect")
public class SEffect extends LayerModule {
	public SEffect() {
		imports(
			SContent.class,
			SPlace.class
		);
		provider(
			new EffectPreset(),
			new T1Effects(),
			new T2Effects(),
			new T3Effects()
		);
		controller(
			new EffectTech()
		);
	}
}
