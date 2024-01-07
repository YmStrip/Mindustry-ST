package st.addon.attack;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.addon.attack.controller.TurretTech;
import st.addon.attack.controller.UnitAttack;
import st.addon.attack.provider.*;
import st.addon.content.SContent;

@Module
public class SAttack extends LayerModule {
	public SAttack() {
		imports(
			SContent.class
		);
		provider(
			new UnitAttackProvider(),
			new TurretPreset(),
			new EffectProvider(),
			new BulletProvider(),
			new T1Turret(),
			new T2Turret(),
			new T3Turret()
		);
		controller(
			new TurretTech(),
			new UnitAttack()
		);
	}
	
	@Override
	public void handelInstance(InstanceFactory f) {
		f
			.instance("logger", t -> t
				.implement(new Logger())
				.config("name", "Bullet")
			);
	}
}
