package wool.module.turret;


import mindustry.entities.bullet.PointBulletType;
import wool.module.item.item;
import wool.module.render.entity.Render;
import wool.module.render.entity.RenderKey;
import wool.module.render.modify.RenderModifyFrame;
import wool.module.turret.entity.TurretItem;
import wool.root.AppModule;

public class turret extends AppModule {
	public static TurretItem TurretAetherAnnihilator;

	public static void init() {
		TurretAetherAnnihilator = new TurretItem("TurretAetherAnnihilator") {{
			//render.sizeRegion = (int) (512*.85f);
			level = 3;
			size = 6;
			range = 120;
			hasLiquids = true;
			hasPower = true;
			coolantMultiplier = 2.5f;
			this.coolant = this.consumeCoolant(1F);
			consumePower(64 * 1000 / 60f);
			//============================
			ammo(
				item.ElementMetal, new PointBulletType(),
				item.ElementWood, new PointBulletType(),
				item.ElementWater, new PointBulletType(),
				item.ElementFire, new PointBulletType(),
				item.ElementEarth, new PointBulletType(),
				item.ElementLight, new PointBulletType(),
				item.ElementDark, new PointBulletType()
			);
			//============================
			load(() -> {
				render.renderLight.region(name + "-light");
				//add frame repeat modify
				new RenderModifyFrame(render.renderAnimate) {{
					regions(name + "-animate", 24, 48, 96);
					iterFrame = true;
					iterFrameRepeat = true;
				}};
				//============================
				render.renderAnimate.alpha.clear();
				render.renderAnimate.alpha.set(0, 0);
				render.renderAnimate.alpha.set(1, 1);
				render.renderAnimate.alpha.transition = RenderKey.transitions.l2;
				//============================
				render.renderWing.offset.set(0f, 0, 0);
				render.renderWing.offset.set(0.5f, -20, 0);
				render.renderWing.offset.set(1f, -20, -20);
				//overlap alpha
				render.renderWing.child(new Render("turret.wing.light") {{
					region(name + "-wing-light");
					alpha.clear();
					alpha.set(0, 0);
					alpha.set(1, 1);
				}});
				new RenderModifyFrame(render.renderWing) {{
					regions(name + "-wing", 24, 48, 96);
					iterFrame = true;
					iterFrameRepeat = true;
				}};
				//============================
			});
		}};
	}

	public static void deploy() {

	}
}
