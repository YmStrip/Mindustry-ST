package st.addon.attack.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.entities.pattern.*;
import mindustry.gen.Sounds;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.ForceProjector;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LaserTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import st.addon.item.ITEM;
import st.addon.item.provider.ItemProvider;

@Provider
public class STurret extends LayerProvider {
	@Import(cls = ITEM.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Require(cls = BulletProvider.class)
	BulletProvider bullets;
	@Require(cls = TurretPreset.class)
	TurretPreset turrets;
	public PowerTurret 高能电浆炮 = new PowerTurret("高能电浆炮") {{
		size = 3;
		range = 360;
		consumePower(20);
		shootSound = Sounds.laser;
	}};
	
	public PowerTurret 高能元素机枪 = new PowerTurret("高能元素机枪") {{
		shootSound = Sounds.shoot;
		size = 3;
		range = 360;
		consumePower(5);
	}};
	public PowerTurret 高能激光炮 = new PowerTurret("高能激光炮") {{
		shootSound = Sounds.laser;
		size = 3;
		range = 360;
		consumePower(13.5f);
	}};
	public PowerTurret 机械暴徒 = new PowerTurret("机械暴徒") {{
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
		consumePower(80);
	}};
	public PowerTurret 微型光粒炮 = new PowerTurret("微型光粒炮") {{
		consumePower(1500);
		shootSound = Sounds.railgun;
		size = 5;
		range = 480;
	}};
	public LaserTurret 等离子光束 = new LaserTurret("等离子光束") {{
		consumePower(80f);
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
		consumePower(100);
		shootSound = Sounds.railgun;
		size = 5;
		range = 400;
	}};
	public ItemTurret 以太爆破 = new ItemTurret("以太爆破") {{
		consumePower(1000);
		ammoPerShot = 5;
		size = 8;
		range = 800;
		shootSound = Sounds.railgun;
	}};
	public PowerTurret 光粒 = new PowerTurret("光粒") {{
		consumePower(2500);
		shootSound = Sounds.railgun;
		size = 8;
		range = 800;
	}};
	public ItemTurret 以太毁灭 = new ItemTurret("以太毁灭") {{
		ammoPerShot = 25;
		range = 2400;
		size = 14;
		consumePower(4000);
		shootSound = Sounds.railgun;
	}};
	public PowerTurret 以太黑洞 = new PowerTurret("以太黑洞") {{
		consumePower(3500);
		size = 14;
		range = 1040;
		shootSound = Sounds.railgun;
	}};
	
	@Override
	public void run() {
		//高能电浆炮 dps 500
		{
			高能电浆炮.requirements = ItemStack.with(items.金元素, 150, items.火元素, 300, items.土元素, 150);
			高能电浆炮.shootType = bullets.弹药_光粒
				.build(items.水元素, 45, 1, 1)
				.multiplier(items.水元素, 500)
				.frag(bullets.弹片_星光, items.水元素, 8, 0.45f, 8f, 1)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.9f, 8f, 1)
				.bullet();
			turrets.inject(高能电浆炮);
		}
		//高能元素机枪 dps 700
		{
			高能元素机枪.requirements = ItemStack.with(items.金元素, 100, items.火元素, 150, items.土元素, 100);
			高能元素机枪.inaccuracy = 3f;
			高能元素机枪.shootType = bullets.弹药_机枪
				.build(items.水元素, 45, 7, 1f)
				.multiplier(items.水元素, 700)
				.frag(bullets.弹片_狙击, items.火元素, 1, 0.4f, 4f, 0.5f)
				.bullet();
			turrets.inject(高能元素机枪);
		}
		//高能激光炮 dps 400
		{
			高能激光炮.requirements = ItemStack.with(items.金元素, 200, items.火元素, 350, items.土元素, 200);
			高能激光炮.shootType = bullets.弹药_激光.build(items.金元素, 45, 1, 0.8f)
				.multiplier(items.金元素, 400)
				.bullet();
			turrets.inject(高能激光炮);
		}
		//微型光粒炮 dps 4500
		{
			微型光粒炮.requirements = ItemStack.with(items.金元素, 20000, items.木元素, 15000, items.水元素, 20000, items.火元素, 20000, items.土元素, 10000, items.光元素, 250, items.暗元素, 250);
			微型光粒炮.shootType = bullets.弹药_光粒
				.build(items.木元素, 60, 0.15f, 2)
				.multiplier(items.木元素, 4500)
				.frag(bullets.弹片_光粒, items.木元素, 10, 0.7f, 12, 1f)
				.frag(bullets.弹片_星光, items.木元素, 1, 0.8f, 4, 2)
				.frag(bullets.弹片_爆炸, items.木元素, 1, 0.25f, 8, 1)
				.bullet();
			
			turrets.inject(微型光粒炮);
		}
		//机械暴徒 dps 4000
		{
			机械暴徒.requirements = ItemStack.with(items.金元素, 1000, items.木元素, 1500, items.水元素, 2500, items.火元素, 2500, items.土元素, 2000);
			机械暴徒.shootType = bullets.弹药_高射
				.build(items.火元素, 60, 16f, 0.5f)
				.multiplier(items.火元素, 4000)
				.frag(bullets.弹片_狙击, items.火元素, 1, 0.2f, 10, 0.5f)
				.bullet();
			turrets.inject(机械暴徒);
		}
		//死光炮 dps 1200
		{
			死光炮.requirements = ItemStack.with(items.金元素, 5000, items.木元素, 3000, items.水元素, 5000, items.火元素, 5000, items.土元素, 2500);
			死光炮.shootType = bullets.弹药_激光
				.build(items.水元素, 50, 0.8f, 2.5f)
				.multiplier(items.水元素, 1200)
				.bullet();
			turrets.inject(死光炮);
		}
		//等离子光束 dps 2500
		{
			等离子光束.requirements = ItemStack.with(items.金元素, 2500, items.木元素, 1000, items.水元素, 5000, items.火元素, 5000, items.土元素, 1000, items.光元素, 25, items.暗元素, 25);
			等离子光束.shootType = bullets.弹药_持续激光
				.build(items.水元素, 45, 0.5f, 2.5f)
				.multiplier(items.水元素, 2500)
				.bullet();
			turrets.inject(等离子光束);
		}
		//以太爆破 dps 金木水火土光暗 8000/5000/12000/12000/8000/50000/30000
		{
			以太爆破.requirements = ItemStack.with(items.金元素, 10000, items.木元素, 5000, items.水元素, 15000, items.火元素, 15000, items.土元素, 10000, items.光元素, 200, items.暗元素, 200);
			以太爆破.ammoTypes.put(items.金元素, bullets.弹药_轨道炮
				.build(items.金元素, 100, 0.5f, 2.5f)
				.multiplier(items.金元素, 8000)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.金元素, 3, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.金元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.木元素, bullets.弹药_轨道炮
				.build(items.木元素, 100, 0.5f, 2.5f)
				.multiplier(items.木元素, 5000)
				.frag(bullets.弹片_光粒, items.木元素, 2, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.木元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.木元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.水元素, bullets.弹药_轨道炮
				.build(items.水元素, 100, 0.5f, 2.5f, 1f)
				.multiplier(items.水元素, 12000)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.水元素, 2, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.水元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.火元素, bullets.弹药_轨道炮
				.build(items.火元素, 100, 0.5f, 2.5f, 1f)
				.multiplier(items.火元素, 12000)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.火元素, 2, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.火元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.火元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.土元素, bullets.弹药_轨道炮
				.build(items.土元素, 100, 0.5f, 2.5f, 1f)
				.multiplier(items.土元素, 8000)
				.frag(bullets.弹片_光粒, items.土元素, 2, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.土元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.土元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.光元素, bullets.弹药_轨道炮
				.build(items.光元素, 1, 0.5f, 2.5f, 1f)
				.multiplier(items.光元素, 50000)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.光元素, 3, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.光元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.光元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.暗元素, bullets.弹药_轨道炮
				.build(items.暗元素, 1, 0.5f, 2.5f, 1f)
				.multiplier(items.暗元素, 30000)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.暗元素, 3, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.暗元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.暗元素, 1, 0.9f, 8f, 2)
				.bullet());
			turrets.inject(以太爆破);
		}
		//以太毁灭 dps 水火光暗 25000/25000/150000/100000
		{
			以太毁灭.requirements = ItemStack.with(items.金元素, 30000, items.木元素, 20000, items.水元素, 50000, items.火元素, 50000, items.土元素, 20000, items.光元素, 5000, items.暗元素, 5000);
			以太毁灭.ammoTypes.put(items.火元素, bullets.弹药_轨道炮
				.build(items.火元素, 100, 0.1f, 4f, 10f)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.火元素, 3, 0.8f, 16, 3f)
				.multiplier(items.火元素, 25000)
				.frag(bullets.弹片_星光, items.火元素, 1, 0.1f, 16, 3f)
				.frag(bullets.弹片_爆炸, items.火元素, 1, 0.9f, 16f, 3f)
				.bullet());
			以太毁灭.ammoTypes.put(items.水元素, bullets.弹药_轨道炮
				.build(items.水元素, 100, 0.1f, 4f, 10f)
				.multiplier(items.水元素, 25000)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.水元素, 3, 0.8f, 16, 3f)
				.frag(bullets.弹片_星光, items.水元素, 1, 0.1f, 16, 3f)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.9f, 16f, 3f)
				.bullet());
			以太毁灭.ammoTypes.put(items.光元素, bullets.弹药_轨道炮
				.build(items.光元素, 100, 0.1f, 4f, 10f)
				.multiplier(items.光元素, 150000)
				.vectorShock()
				.frag(bullets.弹片_星光, items.光元素, 3, 0.1f, 16, 3)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.光元素, 6, 0.5f, 16, 3)
				.frag(bullets.弹片_星光, items.光元素, 1, 0.1f, 16, 3)
				.frag(bullets.弹片_爆炸, items.光元素, 1, 0.8f, 16, 3)
				.bullet());
			以太毁灭.ammoTypes.put(items.暗元素, bullets.弹药_轨道炮
				.build(items.暗元素, 100, 0.1f, 4f, 10f)
				.multiplier(items.暗元素, 100000)
				.vectorShock()
				.frag(bullets.弹片_星光, items.暗元素, 3, 0.1f, 16, 3)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.暗元素, 6, 0.5f, 16, 3)
				.frag(bullets.弹片_星光, items.暗元素, 1, 0.1f, 16, 3)
				.frag(bullets.弹片_爆炸, items.暗元素, 1, 0.8f, 16, 3)
				.bullet());
			turrets.inject(以太毁灭);
		}
		//光粒 dps 20000
		{
			光粒.requirements = ItemStack.with(items.金元素, 25000, items.木元素, 20000, items.水元素, 45000, items.火元素, 45000, items.土元素, 30000, items.光元素, 4500, items.暗元素, 4500);
			光粒.shootType = bullets.弹药_光粒
				.build(items.水元素, 100, 0.25f, 2)
				.multiplier(items.水元素, 20000)
				.frag(bullets.弹片_光粒, items.光元素, 20, 0.85f, 30, 2f)
				.frag(bullets.弹片_星光, items.土元素, 1, 0.25f, 24, 2)
				.frag(bullets.弹片_爆炸, items.土元素, 1, 0.65f, 16, 1)
				.bullet();
			turrets.inject(光粒);
		}
		//以太黑洞 dps 500
		{
			以太黑洞.requirements = ItemStack.with(items.金元素, 30000, items.木元素, 30000, items.水元素, 60000, items.火元素, 60000, items.土元素, 30000, items.光元素, 6000, items.暗元素, 10000);
			以太黑洞.shoot = new ShootSpread() {{
				spread = 1;
				shots = 60;
			}};
			以太黑洞.shootType = bullets.弹药_波
				.build(items.水元素, 130, 0.1f, 30f)
				.multiplier(items.水元素, 500)
				.frag(bullets.弹片_狙击, items.水元素, 1, 0.25f, 16, 1f)
				.frag(bullets.弹片_星光, items.水元素, 1, 0.25f, 60, 0.5f)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.5f, 30, 2)
				.bullet();
			turrets.inject(以太黑洞);
		}
	}
}
