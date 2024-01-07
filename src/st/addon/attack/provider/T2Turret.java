package st.addon.attack.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Items;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.type.ItemStack;
import mindustry.world.blocks.defense.turrets.LaserTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;

@Provider
public class T2Turret extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
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
	}};
	//dps 800 range 40 power 1080
	public PowerTurret 离子光束 = new PowerTurret("离子光束") {{
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
	}};
	//dps 1000/12 range 45 power 1500
	public PowerTurret 正电子冲击波 = new PowerTurret("正电子冲击波") {{
		shoot = new ShootSpread() {{
			shots = 12;
			spread = 1f;
		}};
		size = 3;
		range = 45 * 8;
		consumePower(1500 / 60f);
		shootSound = Sounds.shotgun;
	}};
	//dps 800 range 65 power 1800
	public PowerTurret 超电磁炮 = new PowerTurret("超电磁炮") {{
		size = 3;
		range = 65 * 8;
		consumePower(1800 / 60f);
		shootSound = Sounds.railgun;
	}};
	
	@Override
	public void run() {
		//脉冲发射器
		{
			脉冲发射器.requirements = ItemStack.with(Items.surgeAlloy, 150, items.超导体, 50, items.纳米碳管, 250, items.反重力陶瓷, 15, items.晶金, 50);
			脉冲发射器.shootType = bullets.弹药_机枪
				.build(items.金元素, 40, 3, 2f)
				.multiplier(items.金元素, 1024)
				.frag(bullets.弹片_星光, items.金元素, 1, 0.1f, 4, 0.5f)
				.bullet();
			preset.inject(脉冲发射器, 2);
		}
		//离子光束
		{
			离子光束.requirements = ItemStack.with(Items.surgeAlloy, 200, items.超导体, 80, items.纳米碳管, 300, items.反重力陶瓷, 30, items.晶金, 100);
			离子光束.shootType = bullets.弹药_激光
				.build(items.火元素, 40, 3, 1.5f)
				.multiplier(items.火元素, 800)
				.bullet();
			preset.inject(离子光束, 2);
		}
		//光子大炮
		{
			光子大炮.requirements = ItemStack.with(Items.surgeAlloy, 300, items.超导体, 50, items.纳米碳管, 150, items.反重力陶瓷, 10, items.晶金, 50);
			光子大炮.shootType = bullets.弹药_光粒
				.build(items.金元素, 40, 1, 1f)
				.multiplier(items.金元素, 1500)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.8f, 10, 1)
				.bullet();
			preset.inject(光子大炮, 2);
		}
		//正电子冲击波
		{
			正电子冲击波.requirements = ItemStack.with(Items.surgeAlloy, 300, items.超导体, 50, items.纳米碳管, 150, items.反重力陶瓷, 10, items.晶金, 50);
			正电子冲击波.shootType = bullets.弹药_波
				.build(items.金元素, 40, 1, 1f)
				.multiplier(items.金元素, 1000 / 12f)
				.bullet();
			preset.inject(正电子冲击波, 2);
		}
		//超电磁炮
		{
			超电磁炮.requirements = ItemStack.with(Items.surgeAlloy, 300, items.超导体, 50, items.纳米碳管, 150, items.反重力陶瓷, 10, items.晶金, 50);
			超电磁炮.shootType = bullets.弹药_轨道炮
				.build(items.晶金, 65, 0.15f, 2f)
				.multiplier(items.晶金, 800)
				.frag(bullets.弹片_爆炸, items.晶金, 1, 0.2f, 15, 1)
				.bullet();
			preset.inject(超电磁炮, 2);
		}
	}
}
