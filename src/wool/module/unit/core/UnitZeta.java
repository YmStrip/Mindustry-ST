package wool.module.unit.core;

import wool.module.unit.entity.Unit;

public class UnitZeta extends Unit {
	public UnitZeta() {
		super("UnitZeta");
		size = 5;
		flying = true;
		speed = 6;
	}
	@Override
	public void load() {
		super.load();
		renderFire.regions(name, 24, 48, 96);
		renderFire.scaleBlock(size, sizeBase);
	}
}
