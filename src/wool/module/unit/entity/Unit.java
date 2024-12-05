package wool.module.unit.entity;


import mindustry.type.UnitType;
import wool.entity.Tooltip;
import wool.module.unit.render.RenderUnit;


public class Unit extends UnitType {
	public Unit(String name) {
		super(name);
		constructor = UnitEntity::create;
	}
	@FunctionalInterface
	public interface onLoad {
		void call();
	}

	public RenderUnit render = new RenderUnit();
	public int level = 1;
	public int size = 2;
	public Tooltip tooltip;
	public void diff() {
		if (tooltip != null) tooltip.clear();
		tooltip = new Tooltip(stats, Tooltip.cat());
		tooltip.setLevel(level);
		tooltip.set("debug",render.toString());
	}
	onLoad onLoad;
	public void load(onLoad onLoad) {
		this.onLoad = onLoad;
	}
	@Override
	public void load() {
		if (onLoad != null) onLoad.call();
		super.load();
		render.load(this);
		diff();
	}
	@Override
	public void drawBody(mindustry.gen.Unit unit) {
		super.drawBody(unit);

	}
	@Override
	public void update(mindustry.gen.Unit unit) {
		super.update(unit);
	}
	{
		drawBody = true;
	}

	public class UnitEntity extends mindustry.gen.UnitEntity {
		public RenderUnit render = Unit.this.render.copy();
		@Override
		public void update() {
			super.update();
			render.tick(this);
		}
	}
}