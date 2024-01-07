package st.provider.attack.provider;

import arc.graphics.Color;
import arc.math.Mathf;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import st.provider.attack.entity.SBullet;
import st.addon.content.entity.StItem;

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
	public SBullet 弹药_元素 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new FlakBulletType() {{
				collidesAir = true;
				collidesGround = true;
				damage = damageP;
				speed = -1;
				range = -1;
				lifetime = 10;
				sprite = "missile-large";
				width = widthP;
				height = widthP * 1.5f;
				hitSize = widthP / 2;
				shootEffect = Fx.shootSmokeSquareBig;
				smokeEffect = Fx.shootSmokeDisperse;
				ammoMultiplier = 1;
				hitColor = backColor = trailColor = lightningColor = itemP.color;
				frontColor = Color.white;
				hitEffect = despawnEffect = Fx.hitBulletColor;
				buildingDamageMultiplier = 0.3f;
				
				trailWidth = widthP / 3;
				trailLength = 12;
				trailEffect = Fx.colorSpark;
				trailInterval = 3f;
				
				homingPower = 0.17f;
				homingDelay = 19f;
				homingRange = 160f;
				flakInterval = 20f;
				despawnShake = 3f;
			}};
		}
	};
	public SBullet 弹片_元素狙击 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new LaserBulletType() {{
				damage = damageP;
				speed = 0;
				length = rangeP;
				lifetime = 22f;
				colors = new Color[]{
					itemP.color,
					Color.white
				};
				buildingDamageMultiplier = 0.25f;
				width = widthP;
				hitEffect = Fx.hitLancer;
				//sideAngle = 175f;
				//sideWidth = 1f;
				//sideLength = rangeP / 4;
				drawSize = rangeP * 3;
				pierceCap = 2;
				
				lightning = 3;
				lightningCone = 5f;
				lightningLength = (int) (rangeP * 0.5f);
				lightningColor = itemP.color;
				lightningDamage = damageP / 3;
			}};
		}
	};
	public SBullet 弹药_持续火焰 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new ContinuousFlameBulletType() {{
				damage = damageP / 60 * 1.5f;
				length = rangeP;
				knockback = widthP / 16f;
				pierceCap = 1;
				buildingDamageMultiplier = 0.35f;
				
				colors = new Color[]{
					new Color(itemP.color).a(0.55f),
					new Color(itemP.color).mul(0.9f).a(0.7f),
					new Color(itemP.color).mul(0.8f).a(0.8f),
					new Color(itemP.color).mul(0.7f),
					Color.white};
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
				frontColor = itemP.color;
				damage = damageP / 4 * 3;
				knockback = (float) (Math.log(damageP) / Math.log(1500));
				lifetime = 60f;
				width = height = widthP;
				collidesTiles = true;
				splashDamageRadius = (float) (Math.log(damageP) / Math.log(1.3));
				splashDamage = damageP / 4;
				
				
				trailLength = (int) (widthP * 4);
				trailWidth = widthP;
				trailSinScl = 2.5f;
				trailSinMag = 0.5f;
				trailColor = itemP.color;
				trailEffect = Fx.none;
				despawnShake = 7f;
				smokeEffect = Fx.shootSmokeTitan;
				trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
				shrinkX = 0.2f;
				shrinkY = 0.1f;
				buildingDamageMultiplier = 0.3f;
			}};
		}
	};
	public SBullet 弹药_导弹 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new BasicBulletType() {{
				shootEffect = Fx.shootBig;
				smokeEffect = Fx.shootSmokeMissile;
				ammoMultiplier = 1f;
				absorbable = true;
				speed = -1;
				sprite = "scathe-missile";
				backColor = itemP.color;
				damage = damageP / 4 * 3;
				knockback = (float) (Math.log(damageP) / Math.log(1500));
				width = height = widthP;
				collidesTiles = true;
				splashDamageRadius = (float) (Math.log(damageP) / Math.log(1.3));
				splashDamage = damageP / 4;
				homingPower = 0.08f;
				homingRange = rangeP * 0.9f;
				
				trailWidth = widthP / 3;
				trailLength = 12;
				trailEffect = Fx.colorSpark;
				trailInterval = 3f;
				trailColor = itemP.color;
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
				shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
				smokeEffect = Fx.shootBigSmoke;
				speed = -1f;
				hitSize = widthP;
				width = widthP;
				height = widthP * 1.5f;
				damage = damageP;
				frontColor = itemP.color;
				
				trailWidth = widthP / 3;
				trailLength = 12;
				trailEffect = Fx.colorSpark;
				trailInterval = 3f;
				trailColor = itemP.color;
				
				despawnShake = 3f;
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
					width = widthP;
					sprite = "swave";
					backColor = Color.white;
					frontColor = Color.white;
					collidesTiles = true;
					reflectable = false;
					hittable = false;
					absorbable = true;
					lifetime = 10;
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
						sizeTo = rangeP / 8;
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
	public SBullet 弹药_扩散 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new BasicBulletType() {{
				speed = -1;
				range = -1;
				damage = damageP;
				knockback = widthP;
				width = widthP * 1;
				hitSize = widthP * 1;
				height = widthP * 1.15f;
				shootEffect = Fx.shootBigColor;
				smokeEffect = Fx.shootSmokeSquareSparse;
				hitColor = backColor = trailColor = Color.white;
				trailWidth = widthP;
				trailLength = 3;
				frontColor = itemP.color;
				hitEffect = despawnEffect = Fx.hitSquaresColor;
				buildingDamageMultiplier = 0.35f;
			}};
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
				despawnEffect = Fx.flakExplosion;
				splashDamageRadius = rangeP;
				splashDamage = damageP * 0.8f;
				hitSound = Sounds.titanExplosion;
				hitEffect = new MultiEffect(Fx.titanExplosion, Fx.smokeCloud);
			}};
		}
	};
	//这种弹药只能适用于自杀型单位(横冲直撞)
	public SBullet 弹药_水滴 = new SBullet() {
		@Override
		public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
			return new BulletType() {{
				lifetime = 0;
				range = 0;
				speed = 0f;
				damage = damageP;
				splashDamageRadius = rangeP;
				splashDamage = damageP;
				collidesTiles = true;
				collides = true;
				hitSound = Sounds.glow;
				instantDisappear = true;
				hittable = false;
				collidesAir = true;
				shootEffect = Fx.none;
				//特效撞击
				//killShooter = true;
			}};
		}
	};
}
