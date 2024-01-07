package st.addon.turret.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Items;
import mindustry.gen.Sounds;
import mindustry.type.ItemStack;
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
	public ItemTurret 超导电磁炮 = new ItemTurret("超导电磁炮") {{
		size = 2;
		range = 36 * 8;
		consumePower(780 / 60f);
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
	
	@Override
	public void run() {
		//超导激光
		{
			超导激光炮.requirements = ItemStack.with(Items.thorium, 100, Items.silicon, 100, items.纳米碳管, 150, items.超导体, 50, Items.metaglass, 200);
			超导激光炮.shootType = bullets.弹药_激光
				.build(items.超导体, 30, 1, 0.8f)
				.multiplier(items.超导体, 240)
				.bullet();
			preset.inject(超导激光炮);
		}
		//超导电磁炮
		{
			超导电磁炮.requirements = ItemStack.with(Items.thorium, 80, Items.silicon, 100, items.纳米碳管, 250, items.超导体, 50);
			超导电磁炮.ammoTypes.put(Items.metaglass, bullets.弹药_元素
				.build(items.超导体, 36, 0.3f, 1.5f)
				.multiplier(items.超导体, 300 + 0.1f * 450)
				.lifetime(20)
				.frag(bullets.弹片_狙击, items.金元素, 8, 0.1f, 13f, 0.5f)
				.bullet());
			超导电磁炮.ammoTypes.put(Items.surgeAlloy, bullets.弹药_元素
				.build(items.金元素, 36, 0.3f, 1.5f)
				.multiplier(items.金元素, 300 + 0.1f * 1200)
				.lifetime(20)
				.frag(bullets.弹片_狙击, items.金元素, 8, 0.1f, 13f, 0.5f)
				.bullet());
			preset.inject(超导电磁炮);
		}
		//毒刺导弹
		{
			毒刺导弹.requirements = ItemStack.with(Items.thorium, 200, Items.silicon, 350, items.纳米碳管, 300, items.超导体, 100);
			毒刺导弹.ammoTypes.put(Items.silicon, bullets.弹药_导弹
				.build(items.水元素, 50, 0.5f, 2f)
				.multiplier(items.水元素, 150 + 0.85f * 100)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.35f, 13f, 0.5f)
				.bullet());
			毒刺导弹.ammoTypes.put(Items.blastCompound, bullets.弹药_导弹
				.build(items.火元素, 50, 0.5f, 2f)
				.multiplier(items.火元素, 150 + 0.85f * 150)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.85f, 13f, 0.5f)
				.bullet());
			preset.inject(毒刺导弹);
		}
		//脉冲
		{
			脉冲.requirements = ItemStack.with(Items.thorium, 200, Items.silicon, 350, items.纳米碳管, 200);
			脉冲.ammoTypes.put(Items.copper, bullets.弹药_高射
				.build(items.火元素, 40, 5f, 1f)
				.multiplier(items.火元素, 150 + 0.85f * 50)
				.frag(bullets.弹片_狙击, items.金元素, 1, 0.25f, 6f, 0.5f)
				.bullet());
			preset.inject(脉冲, 1);
		}
	}
}
