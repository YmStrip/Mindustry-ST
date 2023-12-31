package st.addon.seffect;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.attack.provider.EffectProvider;

@Module(name = "s-effect")
public class SEffect extends LayerModule {
	public SEffect() {
		provider(
			new EffectProvider()
		);
	}
}
