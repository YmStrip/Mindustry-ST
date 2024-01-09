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
	
	public PowerTurret 微型光粒炮 = new PowerTurret("微型光粒炮") {{
		consumePower(300);
		shootSound = Sounds.railgun;
		size = 5;
		range = 480;
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
	public PowerTurret 元点打击 = new PowerTurret("元点打击") {{
		consumePower(800);
		size = 7;
		range = 70 * 8;
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
		//微型光粒炮 dps 500
		{
			微型光粒炮.requirements = ItemStack.with(items.金元素, 1500, items.木元素, 500, items.水元素, 500, items.火元素, 500, items.土元素, 1200, items.光元素, 50, items.暗元素, 50);
			//为啥光粒伤害这么高？
			微型光粒炮.shootType = bullets.弹药_光粒
				.build(450, items.木元素, 60, 0.15f, 2)
				.frag(bullets.弹片_光粒, items.木元素, 10, 0.7f, 12, 1f)
				.frag(bullets.弹片_星光, items.木元素, 1, 0.8f, 8, 2)
				.frag(bullets.弹片_爆炸, items.木元素, 1, 0.25f, 8, 1)
				.bullet();
			
			turrets.inject(微型光粒炮, 3);
		}
		//以太爆破 dps 金木水火土光暗 800/500/1200/1200/800/3500/1500
		{
			以太爆破.requirements = ItemStack.with(items.金元素, 1500, items.木元素, 500, items.水元素, 1500, items.火元素, 1500, items.土元素, 1000, items.光元素, 150, items.暗元素, 150);
			以太爆破.ammoTypes.put(items.金元素, bullets.弹药_轨道炮
				.build(800, items.金元素, 100, 0.5f, 2.5f)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.金元素, 3, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.金元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.木元素, bullets.弹药_轨道炮
				.build(500, items.木元素, 100, 0.5f, 2.5f)
				.frag(bullets.弹片_光粒, items.木元素, 2, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.木元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.木元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.水元素, bullets.弹药_轨道炮
				.build(1200, items.水元素, 100, 0.5f, 2.5f)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.水元素, 2, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.水元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.火元素, bullets.弹药_轨道炮
				.build(1200, items.火元素, 100, 0.5f, 2.5f)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.火元素, 2, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.火元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.火元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.土元素, bullets.弹药_轨道炮
				.build(800, items.土元素, 100, 0.5f, 2.5f)
				.frag(bullets.弹片_光粒, items.土元素, 2, 0.2f, 8, 2f)
				.frag(bullets.弹片_星光, items.土元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.土元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.光元素, bullets.弹药_轨道炮
				.build(3500, items.光元素, 1, 0.5f, 2.5f)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.光元素, 3, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.光元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.光元素, 1, 0.9f, 8f, 2)
				.bullet());
			以太爆破.ammoTypes.put(items.暗元素, bullets.弹药_轨道炮
				.build(1500, items.暗元素, 1, 0.5f, 2.5f)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.暗元素, 3, 0.8f, 8, 2f)
				.frag(bullets.弹片_星光, items.暗元素, 1, 0.1f, 8, 2f)
				.frag(bullets.弹片_爆炸, items.暗元素, 1, 0.9f, 8f, 2)
				.bullet());
			turrets.inject(以太爆破, 3);
		}
		//远点打击
		{
			元点打击.requirements = ItemStack.with(items.金元素, 1800, items.木元素, 800, items.水元素, 1200, items.火元素, 1200, items.土元素, 800, items.光元素, 85, items.暗元素, 85);
			元点打击.shootType = bullets.弹药_轨道炮
				.build(1500, items.水元素, 70, 0.5f, 2)
				.frag(bullets.弹片_爆炸, items.光元素, 1, 0.5f, 7f)
				.frag(bullets.弹片_元素狙击, items.光元素, 6, 0.4f, 6f)
				.bullet();
			turrets.inject(元点打击, 3);
		}
		//以太毁灭 dps 水火光暗 2500/3500/10000/8000
		{
			以太毁灭.requirements = ItemStack.with(items.金元素, 3000, items.木元素, 2000, items.水元素, 5000, items.火元素, 5000, items.土元素, 2000, items.光元素, 500, items.暗元素, 500);
			以太毁灭.ammoTypes.put(items.火元素, bullets.弹药_轨道炮
				.build(3500, items.火元素, 185, 0.1f, 4f)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.火元素, 3, 0.8f, 16, 3f)
				.frag(bullets.弹片_星光, items.火元素, 1, 0.1f, 16, 3f)
				.frag(bullets.弹片_爆炸, items.火元素, 1, 0.9f, 16f, 3f)
				.bullet());
			以太毁灭.ammoTypes.put(items.水元素, bullets.弹药_轨道炮
				.build(2500, items.水元素, 185, 0.1f, 4f)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.水元素, 3, 0.8f, 16, 3f)
				.frag(bullets.弹片_星光, items.水元素, 1, 0.1f, 16, 3f)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.9f, 16f, 3f)
				.bullet());
			以太毁灭.ammoTypes.put(items.光元素, bullets.弹药_轨道炮
				.build(10000, items.光元素, 185, 0.1f, 4f)
				.vectorShock()
				.frag(bullets.弹片_星光, items.光元素, 3, 0.1f, 16, 3)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.光元素, 6, 0.5f, 16, 3)
				.frag(bullets.弹片_星光, items.光元素, 1, 0.1f, 16, 3)
				.frag(bullets.弹片_爆炸, items.光元素, 1, 0.8f, 16, 3)
				.bullet());
			以太毁灭.ammoTypes.put(items.暗元素, bullets.弹药_轨道炮
				.build(8000, items.暗元素, 185, 0.1f, 4f)
				.vectorShock()
				.frag(bullets.弹片_星光, items.暗元素, 3, 0.1f, 16, 3)
				.vectorShock()
				.frag(bullets.弹片_光粒, items.暗元素, 6, 0.5f, 16, 3)
				.frag(bullets.弹片_星光, items.暗元素, 1, 0.1f, 16, 3)
				.frag(bullets.弹片_爆炸, items.暗元素, 1, 0.8f, 16, 3)
				.bullet());
			turrets.inject(以太毁灭, 3);
		}
		//光粒 dps 3000
		{
			光粒.requirements = ItemStack.with(items.金元素, 2500, items.木元素, 2000, items.水元素, 4500, items.火元素, 4500, items.土元素, 30000, items.光元素, 450, items.暗元素, 450);
			光粒.shootType = bullets.弹药_光粒
				.build(3000, items.水元素, 100, 0.25f, 2)
				.frag(bullets.弹片_光粒, items.光元素, 20, 0.85f, 30, 2f)
				.frag(bullets.弹片_星光, items.土元素, 1, 0.25f, 24, 2)
				.frag(bullets.弹片_爆炸, items.土元素, 1, 0.65f, 16, 1)
				.bullet();
			turrets.inject(光粒, 3);
		}
		//以太黑洞 dps 240
		{
			以太黑洞.requirements = ItemStack.with(items.金元素, 3000, items.木元素, 3000, items.水元素, 6000, items.火元素, 6000, items.土元素, 3000, items.光元素, 600, items.暗元素, 1000);
			以太黑洞.shoot = new ShootSpread() {{
				spread = 1;
				shots = 120;
			}};
			以太黑洞.shootType = bullets.弹药_波
				.build(240, items.水元素, 130, 0.1f, 10f)
				.frag(bullets.弹片_狙击, items.水元素, 1, 0.25f, 16, 1f)
				.frag(bullets.弹片_星光, items.水元素, 1, 0.25f, 60, 0.5f)
				.frag(bullets.弹片_爆炸, items.水元素, 1, 0.5f, 30, 2)
				.bullet();
			turrets.inject(以太黑洞, 3);
		}
	}
}
