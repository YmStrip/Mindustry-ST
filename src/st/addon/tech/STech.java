package st.addon.tech;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.tech.provider.STechProvider;

@Module(name = "tech")
public class STech extends LayerModule {
	public STech() {
		provider(
			new STechProvider()
		);
	}
}
