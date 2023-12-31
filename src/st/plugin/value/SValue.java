package st.plugin.value;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.plugin.value.provider.Values;

@Module
public class SValue extends LayerModule {
	public SValue() {
		provider(
			new Values()
		);
	}
}
