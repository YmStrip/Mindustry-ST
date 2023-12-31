package st.addon.attack;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.addon.attack.controller.TurretAttack;
import st.addon.attack.controller.UnitAttack;
import st.addon.attack.provider.*;
import st.addon.item.ITEM;
import st.addon.tech.STech;
import st.plugin.value.SValue;

@Module
public class SAttack extends LayerModule {
	public SAttack() {
		imports(
			SValue.class,
			ITEM.class,
			STech.class
		);
		provider(
			new UnitAttackProvider(),
			new TurretPreset(),
			new EffectProvider(),
			new BulletProvider(),
			new STurret()
		);
		controller(
			new TurretAttack(),
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
