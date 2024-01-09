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
public class T1Turret extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SAttack.class)
	@Require(cls = BulletProvider.class)
	BulletProvider bullets;
	
	@Require(cls = TurretPreset.class)
	TurretPreset preset;
	//dps 240 range 30 power 540
	public PowerTurret 超导激光炮 = new PowerTurret("超导激光炮") {{
		size = 2;
		range = 30 * 8;
		consumePower(540 / 60f);
		shootSound = Sounds.laser;
		
	}};
	//dps 600+-2.txt.35x range 36 power 780
	public ItemTurret 超导炮 = new ItemTurret("超导炮") {{
		size = 2;
		range = 36 * 8;
		consumePower(180 / 60f);
		shootSound = Sounds.shoot;
		inaccuracy = 4;
	}};
	
	
	//dps 150+-2.txt.85x range 45
	public ItemTurret 毒刺导弹 = new ItemTurret("毒刺导弹") {{
		size = 2;
		range = 45 * 8;
		//consumePower(780 / 60f);
		shootSound = Sounds.missile;
	}};
	//dps 150+-2.txt.85x range 40
	public ItemTurret 脉冲 = new ItemTurret("脉冲") {{
		size = 2;
		range = 40 * 8;
		//consumePower(780 / 60f);
		shootSound = Sounds.shoot;
		inaccuracy = 6;
	}};
	//dps 1024 range 40 power 1200
	public PowerTurret 脉冲发射器 = new PowerTurret("脉冲发射器") {{
		size = 3;
		range = 34 * 8;
		consumePower(960 / 60f);
		shootSound = Sounds.missile;
		inaccuracy = 4;
	}};
	//dps 800 range 40 power 1080*4
	public ContinuousLiquidTurret 离子光束 = new ContinuousLiquidTurret("离子光束") {{
		liquidCapacity = 240;
		size = 3;
		range = 40 * 8;
		consumePower(1080 / 60f * 1.5f);
		loopSound = Sounds.beam;
	}};
	//dps 1200 range 45 power 1500
	public PowerTurret 光子大炮 = new PowerTurret("光子大炮") {{
		size = 3;
		range = 45 * 8;
		consumePower(960 / 60f);
		shootSound = Sounds.laser;
		inaccuracy = 2;
	}};
	//dps 1000/12 range 45 power 1200
	public PowerTurret 正电子冲击波 = new PowerTurret("正电子冲击波") {{
		shoot = new ShootSpread(15, 4f);
		size = 3;
		range = 45 * 8;
		shootEffect = smokeEffect = Fx.lightningShoot;
		consumePower(1200 / 60f);
		shootSound = Sounds.shotgun;
		inaccuracy = 1;
	}};
	//dps 400 range 65 power 960*1.5
	public PowerTurret 超电磁炮 = new PowerTurret("超电磁炮") {{
		size = 4;
		range = 65 * 8;
		consumePower(960 / 60f * 1.5f);
		shootSound = Sounds.railgun;
	}};
	
	public ItemTurret 阿姆斯特朗大炮 = new ItemTurret("阿姆斯特朗大炮") {{
		size = 3;
		range = 48 * 8;
		shootSound = Sounds.shootBig;
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
		//超导激光
		{
			超导激光炮.requirements = ItemStack.with(Items.thorium, 100, Items.silicon, 100, items.纳米碳管, 150, items.超导体, 50, Items.metaglass, 200);
			超导激光炮.shootType = bullets.弹药_激光
				.build(240, items.超导体, 30, 1, 0.8f)
				.bullet();
			preset.inject(超导激光炮);
		}
		//超导炮
		{
			超导炮.requirements = ItemStack.with(Items.thorium, 80, Items.silicon, 100, items.纳米碳管, 250, items.超导体, 50);
			超导炮.ammoTypes.put(Items.metaglass, bullets.弹药_元素
				.build(300 + 0.1f * 450f, items.超导体, 36, 2f, 1.5f)
				.time(20)
				.frag(bullets.弹片_狙击, items.金元素, 8, 0.1f, 13f, 0.5f)
				.bullet());
			超导炮.ammoTypes.put(Items.surgeAlloy, bullets.弹药_元素
				.build(300 + 0.1f * 1200, items.金元素, 36, 2f, 1.5f)
				.time(20)
				.frag(bullets.弹片_狙击, items.金元素, 8, 0.1f, 13f, 0.5f)
				.bullet());
			preset.inject(超导炮);
		}
		//毒刺导弹
		{
			毒刺导弹.requirements = ItemStack.with(Items.thorium, 200, Items.silicon, 350, items.纳米碳管, 300, items.超导体, 100);
			毒刺导弹.ammoTypes.put(Items.silicon, bullets.弹药_导弹
				.build(150 + 0.25f * 100, items.水元素, 50, 0.5f, 2f)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.35f, 13f, 0.5f)
				.bullet());
			毒刺导弹.ammoTypes.put(Items.blastCompound, bullets.弹药_导弹
				.build(150 + 0.25f * 150, items.火元素, 50, 0.5f, 2f)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.85f, 13f, 0.5f)
				.bullet());
			preset.inject(毒刺导弹);
		}
		//脉冲
		{
			脉冲.requirements = ItemStack.with(Items.thorium, 200, Items.silicon, 350, items.纳米碳管, 200);
			脉冲.ammoTypes.put(Items.copper, bullets.弹药_高射
				.build(150 + 0.25f * 50, items.火元素, 40, 5f, 1f)
				.frag(bullets.弹片_狙击, items.金元素, 1, 0.25f, 6f, 0.5f)
				.bullet());
			脉冲.ammoTypes.put(Items.lead, bullets.弹药_高射
				.build(150 + 0.25f * 50, items.水元素, 40, 5f, 1f)
				.frag(bullets.弹片_狙击, items.水元素, 1, 0.25f, 6f, 0.5f)
				.bullet());
			preset.inject(脉冲, 1);
		}
		//脉冲发射器
		{
			脉冲发射器.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 250, items.反重力陶瓷, 15, items.晶金, 50);
			脉冲发射器.shootType = bullets.弹药_机枪
				.build(1024, items.金元素, 40, 3, 2f)
				.frag(bullets.弹片_星光, items.金元素, 1, 0.1f, 4, 0.5f)
				.bullet();
			preset.inject(脉冲发射器, 1);
		}
		//离子光束
		{
			离子光束.requirements = ItemStack.with(items.超导体, 180, items.纳米碳管, 300, items.反重力陶瓷, 30, items.晶金, 100);
			离子光束.ammo(Liquids.water, bullets.弹药_持续火焰
				.build(800, items.水元素, 40, 1, 1.5f)
				.ammoMultiplier(1 / 48f)
				.bullet());
			preset.inject(离子光束, 1);
		}
		//光子大炮
		{
			光子大炮.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 150, items.反重力陶瓷, 10, items.晶金, 50);
			光子大炮.shootType = bullets.弹药_元素
				.build(600, items.金元素, 40, 1, 1f)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.5f, 10, 1)
				.bullet();
			preset.inject(光子大炮, 1);
		}
		//质量驱动炮
		{
			质量驱动炮.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 90, items.反重力陶瓷, 50, items.晶金, 80);
			质量驱动炮.ammoTypes.put(Items.copper, bullets.弹药_大炮
				.build(450, items.超导体, 45, 1, 0.8f)
				.frag(bullets.弹片_爆炸, items.超导体, 1, 0.5f)
				.bullet());
			质量驱动炮.ammoTypes.put(Items.lead, bullets.弹药_大炮
				.build(450, items.反重力陶瓷, 45, 1, 0.8f)
				.frag(bullets.弹片_爆炸, items.反重力陶瓷, 1, 0.5f)
				.bullet());
			preset.inject(质量驱动炮, 1);
		}
		//电力幽灵
		{
			电力幽灵.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 50, items.反重力陶瓷, 50, items.晶金, 150);
			电力幽灵.ammoTypes.put(Items.thorium, bullets.弹药_机枪
				.build(1050, items.辐矿石, 38, 6, 0.5f)
				.frag(bullets.弹片_元素狙击, items.辐矿石, 1, 0.3f, 4, 1)
				.bullet());
			电力幽灵.ammoTypes.put(Items.pyratite, bullets.弹药_机枪
				.build(550, items.铬纳尔, 38, 6, 0.5f)
				.frag(bullets.弹片_元素狙击, items.铬纳尔, 1, 0.8f, 4, 1)
				.frag(bullets.弹片_爆炸, items.铬纳尔, 1, 0.8f, 5, 1)
				.bullet());
			preset.inject(电力幽灵, 1);
		}
		//阿姆斯特朗大炮
		{
			阿姆斯特朗大炮.requirements = ItemStack.with(items.超导体, 175, items.纳米碳管, 80, items.反重力陶瓷, 80, items.晶金, 50);
			阿姆斯特朗大炮.ammoTypes.put(Items.blastCompound, bullets.弹药_大炮
				.build(600, items.超导体, 48, 0.75f, 0.5f)
				.frag(bullets.弹片_爆炸, items.铬纳尔, 1, 0.8f, 5, 1)
				.frag(bullets.弹片_元素狙击, items.超导体, 2, 0.5f, 6, 1f)
				.bullet());
			阿姆斯特朗大炮.ammoTypes.put(Items.pyratite, bullets.弹药_大炮
				.build(300, items.晶金, 48, 0.75f, 0.5f)
				.frag(bullets.弹片_爆炸, items.铬纳尔, 1, 0.8f, 5, 1)
				.frag(bullets.弹片_星光, items.晶金, 4, 0.25f, 6, 1f)
				.bullet());
			preset.inject(阿姆斯特朗大炮, 1);
		}
		//正电子冲击波
		{
			正电子冲击波.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 150, items.反重力陶瓷, 10, items.晶金, 50);
			正电子冲击波.shootType = bullets.弹药_扩散
				.build(1800 / 12f, items.金元素, 45, 1, 1f)
				.bullet();
			preset.inject(正电子冲击波, 1);
		}
		//超电磁炮
		{
			超电磁炮.requirements = ItemStack.with(items.超导体, 250, items.纳米碳管, 150, items.反重力陶瓷, 10, items.晶金, 50);
			超电磁炮.shootType = bullets.弹药_轨道炮
				.build(200, items.晶金, 65, 0.15f, 2f)
				.frag(bullets.弹片_爆炸, items.晶金, 1, 0.2f, 15, 1)
				.bullet();
			preset.inject(超电磁炮, 1);
		}
	}
}
