package st.provider.attack;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.provider.attack.provider.*;
import st.addon.content.SContent;

@Module
public class SAttack extends LayerModule {
	public SAttack() {
		imports(
			SContent.class
		);
		provider(
			new UnitAttackProvider(),
			new EffectProvider(),
			new BulletProvider()
		);
		controller();
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
