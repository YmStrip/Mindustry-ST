package wool.module.unit.core;

import wool.module.unit.entity.Unit;

public class UnitSigma extends Unit {
	public UnitSigma() {
		super("UnitSigma");
		size = 5;
		flying = true;
		speed = 6;
	}
	@Override
	public void load() {
		super.load();
		renderBottom.regions(name, 24, 48, 96);
		renderBottom.scaleBlock(size, sizeBase);
	}
}
