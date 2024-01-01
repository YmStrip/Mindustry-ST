package st.addon.power;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.addon.power.contoller.PowerTech;
import st.addon.power.provider.PowerPreset;
import st.addon.power.provider.T1Powers;
import st.addon.power.provider.T2Powers;
import st.addon.power.provider.T3Powers;
import st.addon.prod.provider.T2Prods;
import st.addon.prod.provider.T3Prods;

@Module(name = "power")
public class SPower extends LayerModule {
	public SPower() {
		imports(
			SContent.class
		);
		provider(
			new PowerPreset(),
			new T1Powers(),
			new T2Powers(),
			new T3Powers()
		);
		controller(
			new PowerTech()
		);
	}
}
