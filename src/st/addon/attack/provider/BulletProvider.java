package st.addon.attack.provider;

import arc.graphics.Color;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Bullet;
import st.addon.attack.entity.SBullet;
import st.addon.entity.entity.StItem;

@Provider
public class BulletProvider extends LayerProvider {
	@Require
	Logger logger;
	@Require(cls = EffectProvider.class)
	EffectProvider effect;
	public SBullet 弹药_光粒 = new SBullet() {
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new LaserBulletType() {{
				absorbable = true;
				shootEffect = effect.发射(Color.white, Color.rgb((int) (itemP.color.r * 0.8), (int) (itemP.color.g * 0.8), (int) (itemP.color.b * 0.8)));
				smokeEffect = Fx.smokeCloud;
				fragRandomSpread = 1f;
				lifetime = 15;
				speed = getSpeed(rangeP, 15);
				collides = true;
				pierce = false;
				hitShake = widthP / 2;
				width = widthP;
				length = widthP / 4;
				colors = new Color[]{Color.white, itemP.color};
				damage = damageP;
			}};
		}
	};
	public SBullet 弹药_激光 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new LaserBulletType() {{
				absorbable = true;
				speed = 0;
				lifetime = 60;
				length = rangeP;
				width = widthP;
				damage = damageP;
				colors = new Color[]{itemP.color, Color.white};
			}};
		}
	};
	public SBullet 弹药_持续激光 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new ContinuousLaserBulletType() {{
				absorbable = true;
				speed = 0;
				damage = damageP / 60 * 1.5f;
				length = rangeP;
				hitEffect = Fx.hitMeltdown;
				hitColor = itemP.color;
				status = StatusEffects.melting;
				drawSize = rangeP * 2;
				trailRotation = true;
				incendChance = 0f;
				incendSpread = 0f;
				incendAmount = 1;
				colors = new Color[]{itemP.color, Color.white};
			}};
		}
	};
	public SBullet 弹药_轨道炮 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new PointBulletType() {{
				var 亮色 = itemP.color;
				var 暗色 = Color.rgb((int) (itemP.color.r * 0.8f), (int) (itemP.color.g * 0.8f), (int) (itemP.color.b * 0.8f));
				shootEffect = effect.发射(亮色, 暗色);
				hitEffect = effect.打击(亮色, 暗色);
				smokeEffect = effect.打击烟雾(亮色, 暗色);
				trailEffect = effect.轨道(亮色, 暗色);
				despawnEffect = effect.消失(亮色, 暗色);
				damage = damageP;
				maxRange = rangeP;
				buildingDamageMultiplier = -0.5f;
				hitShake = 6f;
				lifetime = 30f;
				speed = getSpeed(rangeP, 30);
				pierce = false;
				collidesTiles = true;
				collides = true;
				absorbable = true;
				trailWidth = widthP;
			}};
		}
	};
	public SBullet 弹药_大炮 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new ArtilleryBulletType() {{
				absorbable = true;
				speed = -1;
				sprite = "spear";
				backColor = itemP.color;
				damage = damageP / 4 * 3;
				knockback = (float) (Math.log(damageP) / Math.log(1500));
				lifetime = 60f;
				width = height = widthP;
				collidesTiles = true;
				splashDamageRadius = (float) (Math.log(damageP) / Math.log(1.3));
				splashDamage = damageP / 4;
			}};
		}
	};
	public SBullet 弹药_导弹 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new ArtilleryBulletType() {{
				absorbable = true;
				speed = -1;
				sprite = "missile";
				backColor = itemP.color;
				damage = damageP / 4 * 3;
				knockback = (float) (Math.log(damageP) / Math.log(1500));
				width = height = widthP;
				collidesTiles = true;
				splashDamageRadius = (float) (Math.log(damageP) / Math.log(1.3));
				splashDamage = damageP / 4;
				homingPower = 0.08f;
				homingRange = rangeP * 0.9f;
			}};
		}
	};
	public SBullet 弹药_高射 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new PointBulletType() {{
				absorbable = true;
				shootEffect = smokeEffect = Fx.thoriumShoot;
				trailEffect = effect.细轨道(itemP.color);
				trailWidth = widthP;
				damage = damageP;
				lifetime = 5;
				speed = getSpeed(rangeP, 5f);
			}};
		}
	};
	public SBullet 弹药_机枪 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new BasicBulletType() {{
				speed = -1f;
				hitSize = widthP;
				width = widthP;
				height = widthP * 1.5f;
				damage = damageP;
				frontColor = itemP.color;
			}};
		}
	};
	public SBullet 弹药_波 = new SBullet() {
		{
			damageMultiplier = 1.1f;
		}
		
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new BasicBulletType() {
				{
					sprite = "swave";
					backColor = Color.white;
					frontColor = Color.white;
					collidesTiles = true;
					reflectable = false;
					hittable = false;
					absorbable = true;
					lifetime = 240;
					speed = getSpeed(rangeP, lifetime);
					drawSize = 0;
					collidesTeam = false;
					hitSize = 36;
					pierce = true;
					pierceCap = 5;
					pierceBuilding = true;
					knockback = 0;
					ammoMultiplier = 4;
					damage = damageP;
					shootEffect = new WaveEffect() {{
						lifetime = 35;
						sizeFrom = 0;
						sizeTo = widthP;
						strokeFrom = 4;
						strokeTo = 0;
						colorFrom = Color.white;
						colorTo = itemP.color;
					}};
					incendChance = 0;
				}
			};
		}
	};
	//spread -> 1.25x
	public SBullet 弹片_光粒 = new SBullet() {
		{
			damageMultiplier = 1.25f;
		}
		
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new LaserBulletType() {{
				absorbable = true;
				pierce = true;
				fragOnHit = false;
				lightningColor = itemP.color;
				lightning = 2;
				lightningLength = (int) (Math.log(damageP) / Math.log(1.3f));
				lightningDamage = damageP * 0.5f / 2;
				width = widthP;
				length = rangeP;
				damage = damageP * 0.5f;
				lifetime = 30f;
				speed = 0f;
				colors = new Color[]{Color.white, itemP.color};
			}};
		}
	};
	
	//spread -> 1.3x
	public SBullet 弹片_星光 = new SBullet() {
		{
			damageMultiplier = 1.3f;
		}
		
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new LaserBulletType() {{
				damage = damageP;
				pierce = true;
				fragOnHit = false;
				speed = getSpeed(rangeP, 30);
				length = widthP;
				lifetime = 30;
				colors = new Color[]{itemP.color, Color.white};
			}};
		}
	};
	//扩散倍率 1x
	public SBullet 弹片_狙击 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new LaserBulletType() {{
				pierce = true;
				fragOnHit = false;
				length = rangeP;
				width = widthP;
				damage = damageP;
				speed = 2f;
				lifetime = 15f;
				colors = new Color[]{itemP.color, Color.white};
			}};
		}
	};
	
	//扩散倍率 1x
	public SBullet 弹片_爆炸 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new BombBulletType() {{
				lifetime = 0f;
				damage = damageP * 0.2f;
				despawnEffect = effect.消失(Color.white, itemP.color);
				splashDamageRadius = rangeP;
				splashDamage = damageP * 0.8f;
			}};
		}
	};
}
