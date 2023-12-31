package st.addon.seffect.provider;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;

public class CubeBulletType extends BasicBulletType {
	public float width = 3f;
	
	public CubeBulletType(float speed, float damage) {
		super(speed, damage);
		fragOnHit = true;
		fragOnAbsorb = false;
		fragRandomSpread = 2.0f;
		smokeEffect = Fx.hitLaser;
		hitEffect = Fx.hitLaser;
		despawnEffect = Fx.hitLaser;
		hittable = false;
		reflectable = false;
		lightColor = Pal.heal;
		lightOpacity = 0.6f;
		hitEffect = new ExplosionEffect() {{
		
		}};
	}
	
	public CubeBulletType() {
		this(1f, 1f);
	}
	
	public float ro = 0f;
	
	@Override
	public void draw(Bullet b) {
		super.draw(b);
		ro += 0.1f;
		trailRotation = true;
		Lines.stroke(width);
		Draw.color(backColor);
		//绘制正方体
		Lines.lineAngleCenter(b.x, b.y, ro + b.rotation(), width);
		Draw.reset();
	}
}
