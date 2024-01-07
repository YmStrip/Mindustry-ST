package st.addon.unit;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.addon.unit.controller.UnitBoss;
import st.addon.unit.controller.UnitTech;
import st.addon.unit.provider.T1Units;
import st.addon.unit.provider.T2Units;
import st.addon.unit.provider.T3Units;
import st.addon.unit.provider.UnitPreset;
import st.provider.attack.SAttack;

@Module(name = "unit")
public class SUnit extends LayerModule {
	public SUnit() {
		imports(
			SContent.class,
			SAttack.class
		);
		provider(
			new T1Units(),
			new T2Units(),
			new T3Units(),
			new UnitPreset()
		);
		controller(
			new UnitBoss(),
			new UnitTech()
		);
	}
}
