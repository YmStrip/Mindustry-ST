package st.provider.attack.provider;

import arc.graphics.Color;
import arc.math.Mathf;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import st.provider.attack.entity.SBullet;
import st.addon.content.entity.StItem;
import st.provider.attack.entity.SBulletBuilder;

@Provider
public class BulletProvider extends LayerProvider {
	@Require
	Logger logger;
	@Require(cls = EffectProvider.class)
	EffectProvider effect;
	public SBullet 弹药_光粒 = new SBullet() {
		{
			time = 15;
			rate = 1;
			color = Color.yellow;
			damage = 500;
			range = 50;
			width = 1.5f;
		}
		
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				
				absorbable = true;
				shootEffect = effect.发射(Color.white, Color.rgb((int) (prov.color.r * 0.8), (int) (prov.color.g * 0.8), (int) (prov.color.b * 0.8)));
				smokeEffect = Fx.smokeCloud;
				fragRandomSpread = 1f;
				collides = true;
				pierce = false;
				hitShake = prov.width / 2;
				width = prov.width;
				length = prov.width / 4;
				colors = new Color[]{Color.white, prov.color};
				damage = prov.damage;
			}};
		}
	};
	public SBullet 弹药_激光 = new SBullet() {
		{
			time = 60;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				lifetime = -1;
				speed = 0;
				range = -1;
				absorbable = true;
				length = prov.range;
				width = prov.width;
				damage = prov.damage;
				colors = new Color[]{prov.color, Color.white};
			}};
		}
	};
	public SBullet 弹药_元素 = new SBullet() {
		{
			width = 0.8f;
			time = 15;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new FlakBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				collidesAir = true;
				collidesGround = true;
				damage = prov.damage;
				sprite = "missile-large";
				width = prov.width;
				height = prov.width * 1.5f;
				hitSize = prov.width / 2;
				shootEffect = Fx.shootSmokeSquareBig;
				smokeEffect = Fx.shootSmokeDisperse;
				ammoMultiplier = 1;
				hitColor = backColor = trailColor = lightningColor = prov.color;
				frontColor = Color.white;
				hitEffect = despawnEffect = Fx.hitBulletColor;
				buildingDamageMultiplier = 0.3f;
				
				trailWidth = prov.width / 3;
				trailLength = (int) (prov.width * 1.5f);
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
		{
			width = 0.5f;
			range = 4f;
			time = 22;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				lifetime = -1;
				speed = 0;
				damage = prov.damage * 0.2f;
				length = prov.range;
				colors = new Color[]{
					prov.color,
					Color.white
				};
				buildingDamageMultiplier = 0.25f;
				width = prov.width;
				hitEffect = Fx.hitLancer;
				//sideAngle = 175f;
				//sideWidth = 1f;
				//sideLength = rangeP / 4;
				drawSize = prov.range * 3;
				pierceCap = 2;
				
				lightning = 3;
				lightningCone = 5f;
				lightningLength = (int) (prov.range * 0.5f);
				lightningColor = prov.color;
				lightningDamage = prov.damage * 0.8f / 3;
			}};
		}
	};
	public SBullet 弹药_持续火焰 = new SBullet() {
		{
			width = 2f;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new ContinuousFlameBulletType() {{
				lifetime = -1;
				speed = 0;
				range = -1;
				damage = prov.damage / 60 * 1.5f;
				length = prov.range;
				knockback = prov.width / 16f;
				pierceCap = 1;
				buildingDamageMultiplier = 0.35f;
				
				colors = new Color[]{
					new Color(prov.color).a(0.55f),
					new Color(prov.color).mul(0.9f).a(0.7f),
					new Color(prov.color).mul(0.8f).a(0.8f),
					new Color(prov.color).mul(0.7f),
					Color.white};
			}};
		}
	};
	public SBullet 弹药_持续激光 = new SBullet() {
		{
			width = 2f;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new ContinuousLaserBulletType() {{
				lifetime = -1;
				speed = 0;
				range = -1;
				absorbable = true;
				damage = prov.damage / 60 * 1.5f;
				length = prov.range;
				hitEffect = Fx.hitMeltdown;
				hitColor = prov.color;
				status = StatusEffects.melting;
				drawSize = prov.range * 2;
				trailRotation = true;
				incendChance = 0f;
				incendSpread = 0f;
				incendAmount = 1;
				colors = new Color[]{prov.color, Color.white};
			}};
		}
	};
	public SBullet 弹药_轨道炮 = new SBullet() {
		{
			width = 2f;
			time = 30;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new PointBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				var 亮色 = prov.color;
				var 暗色 = new Color(prov.color).mul(0.8f).a(0.8f);
				shootEffect = effect.发射(亮色, 暗色, prov.width);
				hitEffect = effect.打击(亮色, 暗色, prov.width);
				smokeEffect = effect.打击烟雾(亮色, 暗色);
				trailEffect = effect.轨道(亮色, 暗色, prov.width);
				despawnEffect = effect.消失(亮色, 暗色);
				damage = prov.damage;
				maxRange = prov.range;
				buildingDamageMultiplier = -0.5f;
				hitShake = 6f;
				pierce = false;
				collidesTiles = true;
				collides = true;
				absorbable = true;
				trailWidth = prov.width;
			}};
		}
	};
	public SBullet 弹药_大炮 = new SBullet() {
		{
			width = 0.8f;
			time = 60;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new ArtilleryBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				absorbable = true;
				sprite = "spear";
				frontColor = prov.color;
				damage = prov.damage / 4 * 3;
				knockback = (float) (Math.log(prov.damage) / Math.log(1500));
				width = height = prov.width;
				collidesTiles = true;
				splashDamageRadius = (float) (Math.log(prov.damage) / Math.log(1.3));
				splashDamage = prov.damage / 4;
				
				
				trailLength = (int) (prov.width * 4);
				trailWidth = prov.width;
				trailSinScl = 2.5f;
				trailSinMag = 0.5f;
				trailColor = prov.color;
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
		{
			width = 1.25f;
			time = 45;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				shootEffect = Fx.shootBig;
				smokeEffect = Fx.shootSmokeMissile;
				ammoMultiplier = 1f;
				absorbable = true;
				sprite = "scathe-missile";
				backColor = prov.color;
				damage = prov.damage / 4 * 3;
				knockback = (float) (Math.log(prov.damage) / Math.log(1500));
				width = height = prov.width;
				collidesTiles = true;
				splashDamageRadius = (float) (Math.log(prov.damage) / Math.log(1.3));
				splashDamage = prov.damage / 4;
				homingPower = 0.08f;
				homingRange = prov.range * 0.9f;
				
				trailWidth = prov.width / 3;
				trailLength = 12;
				trailEffect = Fx.colorSpark;
				trailInterval = 3f;
				trailColor = prov.color;
			}};
		}
	};
	public SBullet 弹药_高射 = new SBullet() {
		{
			time = 16;
			width = 0.5f;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new PointBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				absorbable = true;
				shootEffect = smokeEffect = Fx.thoriumShoot;
				trailEffect = effect.细轨道(prov.color);
				trailWidth = prov.width;
				damage = prov.damage;
			}};
		}
	};
	public SBullet 弹药_机枪 = new SBullet() {
		{
			width = 0.8f;
			time = 15;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				shootEffect = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
				smokeEffect = Fx.shootBigSmoke;
				hitSize = prov.width;
				width = prov.width;
				height = prov.width * 1.5f;
				damage = prov.damage;
				frontColor = prov.color;
				
				trailWidth = prov.width / 3;
				trailLength = 12;
				trailEffect = Fx.colorSpark;
				trailInterval = 3f;
				trailColor = new Color(prov.color).mul(0.95f).a(0.95f);
				
				despawnShake = 3f;
			}};
		}
	};
	public SBullet 弹药_波 = new SBullet() {
		{
			damageMultiplier = 1.1f;
			time = 15;
			width = 1.5f;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {
				{
					lifetime = -1;
					speed = -1;
					range = -1;
					width = prov.width;
					sprite = "swave";
					backColor = Color.white;
					frontColor = Color.white;
					collidesTiles = true;
					reflectable = false;
					hittable = false;
					absorbable = true;
					drawSize = 0;
					collidesTeam = false;
					hitSize = 36;
					pierce = true;
					pierceCap = 5;
					pierceBuilding = true;
					knockback = 0;
					ammoMultiplier = 4;
					damage = prov.damage;
					shootEffect = new WaveEffect() {{
						lifetime = 35;
						sizeFrom = 0;
						sizeTo = prov.range / 8;
						strokeFrom = 4;
						strokeTo = 0;
						colorFrom = Color.white;
						colorTo = prov.color;
					}};
					incendChance = 0;
				}
			};
		}
	};
	public SBullet 弹药_扩散 = new SBullet() {
		{
			time = 30;
			width = 1.25f;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new BasicBulletType() {{
				lifetime = -1;
				speed = -1;
				range = -1;
				damage = prov.damage;
				knockback = prov.width / 20;
				width = prov.width * 1;
				hitSize = prov.width * 1;
				height = prov.width * 1.15f;
				shootEffect = Fx.shootBigColor;
				smokeEffect = Fx.shootSmokeSquareSparse;
				hitColor = backColor = trailColor = Color.white;
				trailWidth = prov.width;
				trailLength = 3;
				frontColor = prov.color;
				hitEffect = despawnEffect = Fx.hitSquaresColor;
				buildingDamageMultiplier = 0.35f;
			}};
		}
	};
	//spread -> 1.25x
	public SBullet 弹片_光粒 = new SBullet() {
		{
			damageMultiplier = 1.25f;
			range = 8;
			time = 30;
			width = 1.5f;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				absorbable = true;
				pierce = true;
				fragOnHit = false;
				lightningColor = prov.color;
				lightning = 2;
				lightningLength = (int) (Math.log(prov.damage) / Math.log(1.3f));
				lightningDamage = prov.damage * 0.5f / 2;
				width = prov.width;
				length = prov.range;
				damage = prov.damage * 0.5f;
				lifetime = 30f;
				speed = 0f;
				colors = new Color[]{Color.white, prov.color};
			}};
		}
	};
	public SBullet 弹片_星光 = new SBullet() {
		{
			damageMultiplier = 1.3f;
			time = 30;
			range = 10;
			width = 2.5f;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				lifetime = -1;
				speed = -1;
				damage = prov.damage;
				pierce = true;
				fragOnHit = false;
				length = prov.width;
				colors = new Color[]{prov.color, Color.white};
			}};
		}
	};
	//扩散倍率 1x
	public SBullet 弹片_狙击 = new SBullet() {
		{
			width = 1.5f;
			range = 8;
			time = 20;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new LaserBulletType() {{
				pierce = true;
				fragOnHit = false;
				length = prov.range;
				width = prov.width;
				damage = prov.damage;
				speed = 2f;
				lifetime = 15f;
				colors = new Color[]{prov.color, Color.white};
			}};
		}
	};
	
	//扩散倍率 1x
	public SBullet 弹片_爆炸 = new SBullet() {
		{
			range = 30;
			width = 2f;
			time = 0;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new BombBulletType() {{
				lifetime = 0f;
				damage = prov.damage * 0.2f;
				despawnEffect = Fx.flakExplosion;
				splashDamageRadius = prov.damage;
				splashDamage = prov.damage * 0.8f;
				//hitSound = Sounds.titanExplosion;
				hitEffect = new MultiEffect(Fx.titanExplosion, Fx.smokeCloud);
			}};
		}
	};
	//这种弹药只能适用于自杀型单位(横冲直撞)
	public SBullet 弹药_水滴 = new SBullet() {
		{
			range = 6;
			width = 1;
			time = 0;
		}
		
		@Override
		public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
			return new BulletType() {{
				lifetime = 0;
				range = 0;
				speed = 0f;
				damage = prov.damage;
				splashDamageRadius = prov.rate;
				splashDamage = prov.damage;
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
