package wool.module.unit;

import wool.module.unit.entity.Unit;
import wool.root.AppModule;

public class unit extends AppModule {
	public static Unit UnitSigma;
	public static Unit UnitZeta;
	public static void init() {
		UnitSigma = new Unit("UnitSigma") {{
			level = 2;
			size = 5;
			flying = true;
			speed = 6;
			load(() -> {
				render.renderFire.modifyFrame().regions(name + "-fire", 24, 48, 96);
			});
		}};
		UnitZeta = new Unit("UnitZeta") {{
			size = 5;
			flying = true;
			speed = 6;
			load(() -> {
				render.renderFire.modifyFrame().regions(name + "-fire", 24, 48, 96);
			});
		}};
	}
	public static void deploy() {

	}
}
