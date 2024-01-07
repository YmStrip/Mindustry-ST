package st.addon.turret.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.ContinuousLiquidTurret;
import mindustry.world.blocks.defense.turrets.ItemTurret;
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
	TurretPreset preset;
	//dps 1024 range 40 power 1200
	public PowerTurret 脉冲发射器 = new PowerTurret("脉冲发射器") {{
		size = 3;
		range = 34 * 8;
		consumePower(1200 / 60f);
		shootSound = Sounds.missile;
		inaccuracy = 4;
	}};
	//dps 800 range 40 power 1080
	public ContinuousLiquidTurret 离子光束 = new ContinuousLiquidTurret("离子光束") {{
		size = 3;
		range = 40 * 8;
		consumePower(1080 / 60f);
		shootSound = Sounds.laser;
	}};
	//dps 1200 range 45 power 1500
	public PowerTurret 光子大炮 = new PowerTurret("光子大炮") {{
		size = 3;
		range = 45 * 8;
		consumePower(1500 / 60f);
		shootSound = Sounds.laser;
		inaccuracy = 2;
	}};
	//dps 1000/12 range 45 power 1500
	public PowerTurret 正电子冲击波 = new PowerTurret("正电子冲击波") {{
		shoot = new ShootSpread(15, 4f);
		size = 3;
		range = 45 * 8;
		shootEffect = smokeEffect = Fx.lightningShoot;
		consumePower(1500 / 60f);
		shootSound = Sounds.shotgun;
		inaccuracy = 1;
	}};
	//dps 400 range 65 power 1800
	public PowerTurret 超电磁炮 = new PowerTurret("超电磁炮") {{
		size = 3;
		range = 65 * 8;
		consumePower(1800 / 60f);
		shootSound = Sounds.railgun;
	}};
	
	
	public ItemTurret 电力幽灵 = new ItemTurret("电力幽灵") {{
		size = 4;
		range = 38 * 8;
		consumePower(360 / 60f);
		shootSound = Sounds.shoot;
		inaccuracy = 5;
	}};
	
	public ItemTurret 质量驱动炮 = new ItemTurret("质量驱动炮") {{
		size = 3;
		range = 45 * 8;
		consumePower(780 / 60f);
		shootSound = Sounds.shoot;
		inaccuracy = 4;
	}};
	
	@Override
	public void run() {
		//脉冲发射器
		{
			脉冲发射器.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 250, items.反重力陶瓷, 15, items.晶金, 50);
			脉冲发射器.shootType = bullets.弹药_机枪
				.build(items.金元素, 40, 3, 2f)
				.multiplier(items.金元素, 1024)
				.frag(bullets.弹片_星光, items.金元素, 1, 0.1f, 4, 0.5f)
				.bullet();
			preset.inject(脉冲发射器, 2);
		}
		//离子光束
		{
			离子光束.requirements = ItemStack.with(items.超导体, 180, items.纳米碳管, 300, items.反重力陶瓷, 30, items.晶金, 100);
			离子光束.ammo(Liquids.water, bullets.弹药_持续火焰
				.build(items.土元素, 40, 1, 1.5f)
				.multiplier(items.土元素, 800)
				.bullet());
			preset.inject(离子光束, 2);
		}
		//光子大炮
		{
			光子大炮.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 150, items.反重力陶瓷, 10, items.晶金, 50);
			光子大炮.shootType = bullets.弹药_元素
				.build(items.金元素, 40, 1, 1f)
				.multiplier(items.金元素, 1500)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.8f, 10, 1)
				.bullet();
			preset.inject(光子大炮, 2);
		}
		//质量驱动炮
		{
			质量驱动炮.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 90, items.反重力陶瓷, 50, items.晶金, 80);
			质量驱动炮.ammoTypes.put(Items.copper, bullets.弹药_大炮
				.build(items.超导体, 45, 1, 0.8f)
				.multiplier(items.超导体, 450)
				.frag(bullets.弹片_爆炸, items.超导体, 1, 0.5f)
				.bullet());
			质量驱动炮.ammoTypes.put(Items.lead, bullets.弹药_大炮
				.build(items.反重力陶瓷, 45, 1, 0.8f)
				.multiplier(items.反重力陶瓷, 450)
				.frag(bullets.弹片_爆炸, items.反重力陶瓷, 1, 0.5f)
				.bullet());
			preset.inject(质量驱动炮, 2);
		}
		//电力幽灵
		{
			电力幽灵.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 50, items.反重力陶瓷, 50, items.晶金, 150);
			电力幽灵.ammoTypes.put(Items.thorium, bullets.弹药_机枪
				.build(items.辐矿石, 38, 6, 0.5f)
				.multiplier(items.辐矿石, 1050)
				.frag(bullets.弹片_元素狙击, items.辐矿石, 1, 0.3f, 4, 1)
				.bullet());
			电力幽灵.ammoTypes.put(Items.pyratite, bullets.弹药_机枪
				.build(items.铬纳尔, 38, 6, 0.5f)
				.multiplier(items.铬纳尔, 550)
				.frag(bullets.弹片_元素狙击, items.铬纳尔, 1, 0.8f, 4, 1)
				.frag(bullets.弹片_爆炸, items.铬纳尔, 1, 0.8f, 5, 1)
				.bullet());
			preset.inject(电力幽灵, 2);
		}
		//正电子冲击波
		{
			正电子冲击波.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 150, items.反重力陶瓷, 10, items.晶金, 50);
			正电子冲击波.shootType = bullets.弹药_扩散
				.build(items.金元素, 45, 1, 1f)
				.multiplier(items.金元素, 1000 / 12f)
				.bullet();
			preset.inject(正电子冲击波, 2);
		}
		//超电磁炮
		{
			超电磁炮.requirements = ItemStack.with(items.超导体, 250, items.纳米碳管, 150, items.反重力陶瓷, 10, items.晶金, 50);
			超电磁炮.shootType = bullets.弹药_轨道炮
				.build(items.晶金, 65, 0.15f, 2f)
				.multiplier(items.晶金, 400)
				.frag(bullets.弹片_爆炸, items.晶金, 1, 0.2f, 15, 1)
				.bullet();
			preset.inject(超电磁炮, 2);
		}
	}
}
