package wool.module.turret.turrets;

import wool.module.turret.entity.Turret;

public class TurretT1Stinger extends Turret {
    public TurretT1Stinger() {
        super("Stringer");
        size = 2;
        sizeRegion = (int) (512 * .8);
        diff();
    }
}
