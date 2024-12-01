package wool.module.unit;

import wool.module.unit.core.UnitSigma;
import wool.module.unit.core.UnitZeta;
import wool.root.AppModule;

public class unit extends AppModule {
	public static UnitSigma UnitSigma;
	public static UnitZeta UnitZeta;
	public static void init() {
		UnitSigma = new UnitSigma();
		UnitZeta = new UnitZeta();
	}
	public static void deploy() {

	}
}
