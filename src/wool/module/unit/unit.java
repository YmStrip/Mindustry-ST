package wool.module.unit;

import wool.module.unit.entity.Unit;
import wool.root.AppModule;

public class unit extends AppModule {
	public static Unit UnitSigma;
	public static Unit UnitZeta;
	public static Unit UnitChronoDevour;
	public static void init() {
		UnitSigma = new Unit("UnitSigma") {{
			level = 2;
			hitSize = 5 * 8;
			size = 5;
			flying = true;
			speed = 6;
			load(() -> {
				render.renderFire.modifyFrame().regions(name + "-fire", 24, 48, 96);
			});
		}};
		UnitZeta = new Unit("UnitZeta") {{
			level = 2;
			hitSize = 5 * 8;
			size = 5;
			flying = true;
			speed = 6;
			load(() -> {
				render.renderFire.modifyFrame().regions(name + "-fire", 24, 48, 96);
			});
		}};
		UnitChronoDevour = new Unit("UnitChronoDevour") {{
			level = 4;
			hitSize = 9 * 8;
			size = 9;
			speed = 4;
			flying = true;
			load(() -> {
				render.renderAnimate.modifyFrame().regions(name, 24, 48, 114);
			});
		}};
	}
	public static void deploy() {

	}
}
