package st.addon.turret.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Fx;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LaserTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.provider.attack.SAttack;
import st.provider.attack.provider.BulletProvider;

@Provider
public class T3Turret extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SAttack.class)
	@Require(cls = BulletProvider.class)
	BulletProvider bullets;
	@Require(cls = TurretPreset.class)
	TurretPreset turrets;
	public PowerTurret 高能电浆炮 = new PowerTurret("高能电浆炮") {{
		size = 3;
		range = 360;
		consumePower(25);
		shootSound = Sounds.laser;
	}};
	
	public PowerTurret 高能元素机枪 = new PowerTurret("高能元素机枪") {{
		shootSound = Sounds.shoot;
		size = 3;
		range = 360;
		inaccuracy = 3;
		consumePower(15);
	}};
	public PowerTurret 高能激光炮 = new PowerTurret("高能激光炮") {{
		shootSound = Sounds.laser;
		size = 3;
		range = 360;
		consumePower(18.5f);
	}};
	public PowerTurret 机械暴徒 = new PowerTurret("机械暴徒") {{
		inaccuracy = 2;
		inaccuracy = 1f;
		shootSound = Sounds.missile;
		size = 5;
		range = 480;
		shoot = new ShootBarrel() {{
			barrels = new float[]{
				-10, 4, 0,
				-5, 4, 0,
				0, 4, 0,
				5, 4, 0,
				10, 4, 0
			};
			shots = 1;
			shotDelay = 1f;
		}};
		consumePower(36);
	}};
	public PowerTurret 微型光粒炮 = new PowerTurret("微型光粒炮") {{
		consumePower(120);
		shootSound = Sounds.railgun;
		size = 5;
		range = 480;
	}};
	public LaserTurret 等离子光束 = new LaserTurret("等离子光束") {{
		consumePower(36f);
		shootEffect = Fx.shootBigSmoke2;
		shootCone = 40f;
		recoil = 4f;
		shake = 2f;
		range = 360f;
		reload = 90f;
		firingMoveFract = 0.5f;
		shootDuration = 230f;
		shootSound = Sounds.laserbig;
		loopSound = Sounds.beam;
		loopSoundVolume = 2f;
		scaledHealth = 200;
		size = 5;
	}};
	public PowerTurret 死光炮 = new PowerTurret("死光炮") {{
		consumePower(40);
		shootSound = Sounds.railgun;
		size = 5;
		range = 400;
	}};
	public ItemTurret 以太爆破 = new ItemTurret("以太爆破") {{
		consumePower(360);
		ammoPerShot = 5;
		size = 8;
		range = 800;
		shootSound = Sounds.railgun;
	}};
	public PowerTurret 光粒 = new PowerTurret("光粒") {{
		consumePower(3600);
		shootSound = Sounds.railgun;
		size = 8;
		range = 800;
	}};
	public ItemTurret 以太毁灭 = new ItemTurret("以太毁灭") {{
		ammoPerShot = 25;
		range = 185 * 8;
		size = 14;
		consumePower(3000);
		shootSound = Sounds.railgun;
	}};
	public PowerTurret 以太黑洞 = new PowerTurret("以太黑洞") {{
		consumePower(4500);
		size = 14;
		range = 1040;
		shootSound = Sounds.railgun;
	}};
	
	@Override
	public void run() {
		//高能电浆炮 dps 1000
		{
			高能电浆炮.requirements = ItemStack.with(items.金元素, 50, items.火元素, 30, items.土元素, 50);
			高能电浆炮.shootType = bullets.弹药_元素
				.build(items.水元素, 45, 1, 1)
				.multiplier(items.水元素, 1000)
				.frag(bullets.弹片_星光, items.水元素, 8, 0.9f, 8f, 1)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.9f, 8f, 1)
				.bullet();
			turrets.inject(高能电浆炮, 3);
		}
		//高能元素机枪 dps 1500
		{
			高能元素机枪.requirements = ItemStack.with(items.金元素, 50, items.火元素, 80, items.土元素, 120);
			高能元素机枪.shootType = bullets.弹药_元素
				.build(items.水元素, 45, 7, 1f)
				.multiplier(items.水元素, 1500)
				.frag(bullets.弹片_元素狙击, items.火元素, 1, 0.4f, 4f, 0.5f)
				.bullet();
			turrets.inject(高能元素机枪, 3);
		}
		//高能激光炮 dps 1200
		{
			高能激光炮.requirements = ItemStack.with(items.金元素, 100, items.火元素, 50, items.土元素, 50);
			高能激光炮.shootType = bullets.弹药_激光.build(items.金元素, 45, 1, 0.8f)
				.multiplier(items.金元素, 1200)
				.bullet();
			turrets.inject(高能激光炮, 3);
		}
		//微型光粒炮 dps 150
		{
			微型光粒炮.requirements = ItemStack.with(items.金元素, 1500, items.木元素, 450, items.水元素, 500, items.火元素, 500, items.土元素, 1200, items.光元素, 50, items.暗元素, 50);
			微型光粒炮.shootType = bullets.弹药_光粒
				.build(items.木元素, 60, 0.15f, 2)
				.multiplier(items.木元素, 150)
				.frag(bullets.弹片_光粒, items.木元素, 10, 0.7f, 12, 1f)
				.frag(bullets.弹片_星光, items.木元素, 1, 0.8f, 4, 2)
				.frag(bullets.弹片_爆炸, items.木元素, 1, 0.25f, 8, 1)
				.bullet();
			
			turrets.inject(微型光粒炮, 3);
		}
		//机械暴徒 dps 3000
		{
			机械暴徒.requirements = ItemStack.with(items.金元素, 250, items.木元素, 200, items.水元素, 150, items.火元素, 250, items.土元素, 200);
			机械暴徒.shootType = bullets.弹药_高射
				.build(items.火元素, 60, 16f, 0.5f)
				.multiplier(items.火元素, 3000)
				.frag(bullets.弹片_狙击, items.火元素, 1, 0.2f, 10, 0.5f)
				.bullet();
			turrets.inject(机械暴徒, 3);
		}
		//死光炮 dps 1500
		{
			死光炮.requirements = ItemStack.with(items.金元素, 500, items.木元素, 150, items.水元素, 150, items.火元素, 250, items.土元素, 250);
			死光炮.shootType = bullets.弹药_激光
				.build(items.水元素, 50, 0.8f, 2.5f)
				.multiplier(items.水元素, 1500)
				.bullet();
			turrets.inject(死光炮, 3);
		}
		//等离子光束 dps 2500
		{
			等离子光束.requirements = ItemStack.with(items.金元素, 500, items.木元素, 100, items.水元素, 500, items.火元素, 250, items.土元素, 250);
			等离子光束.shootType = bullets.弹药_持续激光
				.build(items.水元素, 45, 0.5f, 2.5f)
				.multiplier(items.水元素, 2500)
				.bullet();
			turrets.inject(等离子光束, 3);
		}
		//以太爆破 dps 金木水火土光暗 800/500/1200/1200/800/3500/1500
		{
			以太爆破.requirements = ItemStack.with(items.金元素, 1500, items.木元素, 500, items.水元素, 1500, items.火元素, 1500, items.土元素, 1000, items.光元素, 150, items.暗元素, 150);
			以太爆破.ammoTypes.put(items.金元素, bullets.弹药_轨道炮
				.build(items.金元素, 100, 0.5f, 2.5f)
				.multiplier(items.金元素, 800)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.金元素, 3, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.金元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.木元素, bullets.弹药_轨道炮
				.build(items.木元素, 100, 0.5f, 2.5f)
				.multiplier(items.木元素, 500)
				.frag(bullets.弹片_光粒, items.木元素, 2, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.木元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.木元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.水元素, bullets.弹药_轨道炮
				.build(items.水元素, 100, 0.5f, 2.5f, 1f)
				.multiplier(items.水元素, 1200)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.水元素, 2, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.水元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.火元素, bullets.弹药_轨道炮
				.build(items.火元素, 100, 0.5f, 2.5f, 1f)
				.multiplier(items.火元素, 1200)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.火元素, 2, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.火元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.火元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.土元素, bullets.弹药_轨道炮
				.build(items.土元素, 100, 0.5f, 2.5f, 1f)
				.multiplier(items.土元素, 800)
				.frag(bullets.弹片_光粒, items.土元素, 2, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.土元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.土元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.光元素, bullets.弹药_轨道炮
				.build(items.光元素, 1, 0.5f, 2.5f, 1f)
				.multiplier(items.光元素, 3500)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.光元素, 3, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.光元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.光元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.暗元素, bullets.弹药_轨道炮
				.build(items.暗元素, 1, 0.5f, 2.5f, 1f)
				.multiplier(items.暗元素, 1500)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.暗元素, 3, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.暗元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.暗元素, 1, 0.9f, 8f, 2)
				.bullet());
			turrets.inject(以太爆破, 4);
		}
		//以太毁灭 dps 水火光暗 2500/3500/10000/8000
		{
			以太毁灭.requirements = ItemStack.with(items.金元素, 3000, items.木元素, 2000, items.水元素, 5000, items.火元素, 5000, items.土元素, 2000, items.光元素, 500, items.暗元素, 500);
			以太毁灭.ammoTypes.put(items.火元素, bullets.弹药_轨道炮
				.build(items.火元素, 185, 0.1f, 4f, 10f)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.火元素, 3, 0.8f, 16, 3f)
				.multiplier(items.火元素, 3500)
				.frag(bullets.弹片_星光, items.火元素, 1, 0.1f, 16, 3f)
				.frag(bullets.弹片_爆炸, items.火元素, 1, 0.9f, 16f, 3f)
				.bullet());
			以太毁灭.ammoTypes.put(items.水元素, bullets.弹药_轨道炮
				.build(items.水元素, 185, 0.1f, 4f, 10f)
				.multiplier(items.水元素, 2500)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.水元素, 3, 0.8f, 16, 3f)
				.frag(bullets.弹片_星光, items.水元素, 1, 0.1f, 16, 3f)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.9f, 16f, 3f)
				.bullet());
			以太毁灭.ammoTypes.put(items.光元素, bullets.弹药_轨道炮
				.build(items.光元素, 185, 0.1f, 4f, 10f)
				.multiplier(items.光元素, 10000)
				.vectorShock()
				.frag(bullets.弹片_星光, items.光元素, 3, 0.1f, 16, 3)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.光元素, 6, 0.5f, 16, 3)
				.frag(bullets.弹片_星光, items.光元素, 1, 0.1f, 16, 3)
				.frag(bullets.弹片_爆炸, items.光元素, 1, 0.8f, 16, 3)
				.bullet());
			以太毁灭.ammoTypes.put(items.暗元素, bullets.弹药_轨道炮
				.build(items.暗元素, 185, 0.1f, 4f, 10f)
				.multiplier(items.暗元素, 8000)
				.vectorShock()
				.frag(bullets.弹片_星光, items.暗元素, 3, 0.1f, 16, 3)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.暗元素, 6, 0.5f, 16, 3)
				.frag(bullets.弹片_星光, items.暗元素, 1, 0.1f, 16, 3)
				.frag(bullets.弹片_爆炸, items.暗元素, 1, 0.8f, 16, 3)
				.bullet());
			turrets.inject(以太毁灭, 4);
		}
		//光粒 dps 3000
		{
			光粒.requirements = ItemStack.with(items.金元素, 2500, items.木元素, 2000, items.水元素, 4500, items.火元素, 4500, items.土元素, 30000, items.光元素, 450, items.暗元素, 450);
			光粒.shootType = bullets.弹药_光粒
				.build(items.水元素, 100, 0.25f, 2)
				.multiplier(items.水元素, 3000)
				.frag(bullets.弹片_光粒, items.光元素, 20, 0.85f, 30, 2f)
				.frag(bullets.弹片_星光, items.土元素, 1, 0.25f, 24, 2)
				.frag(bullets.弹片_爆炸, items.土元素, 1, 0.65f, 16, 1)
				.bullet();
			turrets.inject(光粒, 4);
		}
		//以太黑洞 dps 240
		{
			以太黑洞.requirements = ItemStack.with(items.金元素, 3000, items.木元素, 3000, items.水元素, 6000, items.火元素, 6000, items.土元素, 3000, items.光元素, 600, items.暗元素, 1000);
			以太黑洞.shoot = new ShootSpread() {{
				spread = 1;
				shots = 120;
			}};
			以太黑洞.shootType = bullets.弹药_波
				.build(items.水元素, 130, 0.1f, 10f)
				.multiplier(items.水元素, 240)
				.frag(bullets.弹片_狙击, items.水元素, 1, 0.25f, 16, 1f)
				.frag(bullets.弹片_星光, items.水元素, 1, 0.25f, 60, 0.5f)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.5f, 30, 2)
				.bullet();
			turrets.inject(以太黑洞, 4);
		}
	}
}
