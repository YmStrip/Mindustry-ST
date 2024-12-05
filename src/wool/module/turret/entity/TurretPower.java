package wool.module.turret.entity;

import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BuildVisibility;
import wool.entity.Tooltip;
import wool.module.turret.render.RenderTurret;

public class TurretPower extends mindustry.world.blocks.defense.turrets.PowerTurret {
	@FunctionalInterface
	public interface onLoad {
		void call();
	}
	public TurretPower(String name) {
		super(name);
		hasLiquids = true;
		hasPower = true;
		hasItems = true;
	}
	public int level = 1;
	public RenderTurret render = new RenderTurret();
	public Tooltip tooltip;
	public void diff() {
		if (tooltip != null) tooltip.clear();
		tooltip = new Tooltip(stats, Tooltip.cat());
		tooltip.setLevel(level);
		tooltip.set("debug",render.toString());
	}
	@Override
	public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
		Draw.scl *= size * 32f / render.sizeBase;
		super.drawPlanRegion(plan, list);
	}
	onLoad onLoad;
	public void load(onLoad onLoad) {
		this.onLoad = onLoad;
	}
	@Override
	public void load() {
		super.load();
		if (onLoad != null) onLoad.call();
		render.load(this);
		diff();
	}
	{
		buildVisibility = BuildVisibility.shown;
		researchCostMultiplier = 0.8f;
		buildCostMultiplier = 0.8f;
		reload = 60;
		targetAir = true;
		targetGround = true;

		chargeSound = Sounds.lasercharge;
		hasPower = true;
		hasLiquids = true;
		category = Category.turret;
	}

	public class TurretPowerBuild extends PowerTurret.PowerTurretBuild {
		public RenderTurret render = TurretPower.this.render.copy();
		@Override
		public void updateTile() {
			super.updateTile();
			render.tick(this);
		}
		@Override
		public void draw() {
			super.draw();
			render.render(x, y, rotation-90);
		}
	}
}