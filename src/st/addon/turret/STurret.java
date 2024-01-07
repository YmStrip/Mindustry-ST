package st.addon.turret;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.addon.turret.controller.TurretTech;
import st.addon.turret.provider.T1Turret;
import st.addon.turret.provider.T2Turret;
import st.addon.turret.provider.T3Turret;
import st.addon.turret.provider.TurretPreset;
import st.provider.attack.SAttack;

@Module(name = "turret")
public class STurret extends LayerModule {
	public STurret() {
		imports(
			SContent.class,
			SAttack.class
		);
		provider(
			new TurretPreset(),
			new T1Turret(),
			new T2Turret(),
			new T3Turret()
		);
		controller(
			new TurretTech()
		);
	}
}
