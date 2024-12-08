package wool.module.turret;


import mindustry.entities.bullet.PointBulletType;
import wool.module.item.item;
import wool.module.render.entity.Render;
import wool.module.render.entity.RenderKeyMode;
import wool.module.render.entity.RenderKeyTransition;
import wool.module.render.modify.RenderModifyFrame;
import wool.module.render.modify.RenderModifyMirror;
import wool.module.render.modify.RenderModifyOffset;
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
				//============================
				render.renderWing.alpha.lock(1);
				var wing = render.renderWing;
				new RenderModifyOffset(render.renderWing) {{
					rotate = 0;
					offset.clear();
					offset.set(0f, 0f, 0);
					offset.set(0, RenderKeyTransition.bounce, RenderKeyMode.easeIn);
					offset.set(.75f, -.25f * 10, -1f * 10);
					offset.set(0.75f, RenderKeyTransition.bounce, RenderKeyMode.easeOut);
					offset.set(1f, -1f * 10, -2f * 10);
				}};
				new RenderModifyMirror(render.renderWing);
				//overlap alpha
				render.renderWing.child(new Render("turret.wing.light") {{
					region(name + "-wing-light");
					alpha.clear();
					alpha.set(0, 0);
					alpha.set(1, 1);
					new RenderModifyMirror(this) {{
						align = wing;
					}};
				}});
				//============================
			});
		}};
	}

	public static void deploy() {

	}
}
