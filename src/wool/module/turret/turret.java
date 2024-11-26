package wool.module.turret;
import wool.module.turret.turrets.TurretT1Stinger;
import wool.root.AppModule;

public class turret extends AppModule {
    public static TurretT1Stinger Stringer;

    public static void init() {
        Stringer = new TurretT1Stinger();
    }

    public static void deploy() {

    }
}
