package wool.module.unit;

import wool.module.unit.core.UnitSigma;
import wool.root.AppModule;

public class unit extends AppModule {
	public static UnitSigma UnitSigma;
	public static void init() {
		UnitSigma = new UnitSigma();
	}
	public static void deploy() {

	}
}
