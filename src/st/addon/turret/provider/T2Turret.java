package st.addon.turret.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.ContinuousLiquidTurret;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LaserTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.provider.attack.SAttack;
import st.provider.attack.provider.BulletProvider;

@Provider
public class T2Turret extends LayerProvider {
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
		consumePower(25*2.5f);
		shootSound = Sounds.laser;
	}};
	
	public PowerTurret 高能元素机枪 = new PowerTurret("高能元素机枪") {{
		shootSound = Sounds.shoot;
		size = 3;
		range = 360;
		inaccuracy = 3;
		consumePower(15*2.5f);
	}};
	public PowerTurret 高能激光炮 = new PowerTurret("高能激光炮") {{
		shootSound = Sounds.laser;
		size = 3;
		range = 360;
		consumePower(18.5f*2.5f);
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
	public LaserTurret 等离子光束 = new LaserTurret("等离子光束") {{
		consumePower(36f * 4);
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
		consumePower(40*2.5f);
		shootSound = Sounds.railgun;
		size = 5;
		range = 400;
	}};
	@Override
	public void run() {
		//高能电浆炮 dps 1000
		{
			高能电浆炮.requirements = ItemStack.with(items.超导体,350,items.纳米碳管,150,items.反重力陶瓷,50,items.反物质,50,items.晶金,50);
			高能电浆炮.shootType = bullets.弹药_元素
				.build(1000, items.水元素, 45f, 1f, 1f)
				.frag(bullets.弹片_星光, items.水元素, 8, 0.9f, 8f, 1)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.9f, 8f, 1)
				.bullet();
			turrets.inject(高能电浆炮, 2);
		}
		//高能元素机枪 dps 1500
		{
			高能元素机枪.requirements = ItemStack.with(items.超导体,150,items.纳米碳管,200,items.反重力陶瓷,75,items.反物质,50,items.晶金,250);
			高能元素机枪.shootType = bullets.弹药_元素
				.build(1500, items.水元素, 45, 7, 1f)
				.frag(bullets.弹片_元素狙击, items.火元素, 1, 0.4f, 4f, 0.5f)
				.bullet();
			turrets.inject(高能元素机枪, 2);
		}
		//高能激光炮 dps 1200
		{
			高能激光炮.requirements = ItemStack.with(items.超导体,150,items.纳米碳管,150,items.反重力陶瓷,50,items.晶金,300);
			高能激光炮.shootType = bullets.弹药_激光.build(1200, items.金元素, 45, 1, 0.8f)
				.bullet();
			turrets.inject(高能激光炮, 2);
		}
		//机械暴徒 dps 3000
		{
			机械暴徒.requirements = ItemStack.with(items.超导体,500,items.纳米碳管,650,items.反重力陶瓷,250,items.反物质,250,items.晶金,50);
			机械暴徒.shootType = bullets.弹药_高射
				.build(3000, items.火元素, 60, 16f, 0.5f)
				.frag(bullets.弹片_狙击, items.火元素, 1, 0.2f, 10, 0.5f)
				.bullet();
			turrets.inject(机械暴徒, 2);
		}
		//死光炮 dps 1500
		{
			死光炮.requirements = ItemStack.with(items.超导体,450,items.纳米碳管,150,items.反重力陶瓷,150,items.反物质,50,items.晶金,650);
			死光炮.shootType = bullets.弹药_激光
				.build(1500, items.水元素, 50, 0.8f, 2.5f)
				.bullet();
			turrets.inject(死光炮, 2);
		}
		//等离子光束 dps 2500
		{
			等离子光束.requirements = ItemStack.with(items.超导体,450,items.纳米碳管,350,items.反重力陶瓷,250,items.反物质,50,items.晶金,350);
			等离子光束.shootType = bullets.弹药_持续激光
				.build(2500, items.水元素, 45, 0.5f, 2.5f)
				.bullet();
			turrets.inject(等离子光束, 2);
		}
	}
}
