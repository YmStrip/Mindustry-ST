package wool.module.turret.render;

import arc.Core;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.draw.DrawTurret;
import wool.module.render.entity.Render;
import wool.module.render.entity.RenderEntity;


public class RenderTurret extends RenderEntity {
	public String base = "default";
	//wing animate in [!shoot,shoot]
	public Render renderWing = new Render("turret.wing");
	@Override
	public void load(Object object) {
		if (object instanceof Turret turret) {
			var drawer = (DrawTurret) turret.drawer;
			drawer.base = Core.atlas.find("wool-base-" + base);
			setScale(drawer.base, turret.size);
			setScale(drawer.top, turret.size);
			setScale(drawer.heat, turret.size);
			setScale(turret.region, turret.size);
			setScale(drawer.preview, turret.size);
			//
			renderLight.alpha.clear();
			renderLight.alpha.set(0, 0);
			renderLight.alpha.set(1, 1);
			renderLight.region(turret.name + "-light");
			renderLight.setScaleBlock(turret.size, sizeBase);
			//
			renderAnimate.setScaleBlock(turret.size, sizeBase);
			renderAnimate.alpha.clear();
			renderAnimate.alpha.set(0, 0);
			renderAnimate.alpha.set(1, 1);
			//
			renderWing.alpha.lock(1);
			renderWing.region(turret.name + "-wing");
			renderWing.setScaleBlock(turret.size, sizeBase);
		}
	}
	@Override
	public void render(float x, float y, float rotate) {
		super.render(x, y, rotate);
		renderWing.setPos(x, y, rotate);
		renderWing.render();
	}
	public void tick(Object object) {
		if (object instanceof Turret.TurretBuild turret) {
			renderLight.iter(turret.hasAmmo());
			renderAnimate.iter(turret.hasAmmo());
			renderWing.tick(turret);
			renderWing.iter(turret.hasAmmo());
		}
	}
	@Override
	public RenderTurret copy() {
		var copy = new RenderTurret();
		copy.renderWing = renderWing.copy();
		copy(copy);
		return copy;
	}
}
