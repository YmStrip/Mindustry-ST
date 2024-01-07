package st.addon.drill;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.addon.drill.controller.DrillTech;
import st.addon.drill.provider.DrillPreset;
import st.addon.drill.provider.T1Drills;
import st.addon.drill.provider.T2Drills;
import st.addon.drill.provider.T3Drills;

@Module(name = "drill")
public class SDrill extends LayerModule {
	public SDrill () {
		imports(
			SContent.class
		);
		provider(
			new DrillPreset(),
			new T1Drills(),
			new T2Drills(),
			new T3Drills()
		);
		controller(
			new DrillTech()
		);
	}
}
