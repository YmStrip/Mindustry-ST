package st.addon.unit.provider;

import arc.graphics.Color;
import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.ItemStack;
import mindustry.world.blocks.units.UnitFactory;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.addon.content.provider.ValueProvider;
import st.addon.unit.entity.SUnitType;
import st.provider.attack.SAttack;
import st.provider.attack.provider.BulletProvider;
import st.provider.attack.provider.UnitAttackProvider;

@Provider
public class T2Units extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = ValueProvider.class)
	ValueProvider value;
	@Import(cls = SAttack.class)
	@Require(cls = UnitAttackProvider.class)
	UnitAttackProvider unitAttack;
	@Import(cls = SAttack.class)
	@Require(cls = BulletProvider.class)
	BulletProvider bullet;
	@Require(cls = UnitPreset.class)
	UnitPreset preset;
	
	public SUnitType 星际建造机 = new SUnitType("星际建造机") {{
		health = 3000;
		armor = 20;
		flying = true;
		speed = 40 / 8f;
		buildSpeed = 12;
		itemCapacity = 600;
		mineTier = 4;
		mineSpeed = 4;
		hitSize = 2 * 8;
	}};
	public SUnitType 星际挖矿机 = new SUnitType("星际挖矿机") {{
		health = 3000;
		armor = 20;
		flying = true;
		speed = 40 / 8f;
		buildSpeed = -1;
		itemCapacity = 3000;
		mineTier = 6;
		mineSpeed = 12;
		hitSize = 2 * 8;
	}};
	public SUnitType 星际护卫舰 = new SUnitType("星际护卫舰") {{
		flying = true;
		hitSize = 5 * 8;
		health = 10000;
		armor = 300;
		speed = 30 / 8f;
		itemCapacity = 1200;
		engines.add(new UnitEngine(0, -4 * 8, 12f, 4f));
	}};
	public SUnitType 星际巡洋舰 = new SUnitType("星际巡洋舰") {{
		flying = true;
		hitSize = 8 * 8;
		health = 80000;
		armor = 400;
		speed = 15 / 8f;
		itemCapacity = 2000;
		engines.add(new UnitEngine(0, -7 * 8, 14f, 4f));
	}};
	public SUnitType 星际战列舰 = new SUnitType("星际战列舰") {{
		flying = true;
		hitSize = 10 * 8;
		health = 640000;
		armor = 500;
		speed = 10 / 8f;
		itemCapacity = 6000;
		engines.add(new UnitEngine(0, -9.5f * 8, 16f, 4f));
	}};
	public SUnitType 星际歼星舰 = new SUnitType("星际歼星舰") {{
		flying = true;
		hitSize = 13 * 8;
		health = 2500000;
		armor = 650;
		speed = 8 / 8f;
		itemCapacity = 6000;
		engines.add(new UnitEngine(0, -12f * 8, 18f, 4f));
	}};
	public SUnitType 猫盒 = new SUnitType("猫盒") {{
		health = 400000;
		armor = 900;
		speed = 5 / 8f;
		itemCapacity = 6000;
	}};
	//3600/s
	public UnitFactory 夸特动力船坞 = new UnitFactory("夸特动力船坞") {{
		size = 7;
		itemCapacity = 10000;
		consumePower(3600 / 60f);
		consumeLiquid(Liquids.cryofluid, 60 / 60f);
	}};
	
	@Override
	public void run() {
		//星际建造机
		{
			unitAttack.injectWeapon(
				星际建造机,
				unitAttack.pos_涡轮激光炮(0, 0).sound(Sounds.laser),
				bullet
					.弹药_轨道炮
					.build(200, Color.green, 36, 1, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.8f, 6, 0.5f)
					.frag(bullet.弹片_爆炸, items.木元素, 1, 0.5f, 6)
					.bullet()
			);
			preset.inject(星际建造机);
		}
		//星际挖矿机
		{
			unitAttack.injectWeapon(
				星际挖矿机,
				unitAttack.pos_涡轮激光炮(0, 0).sound(Sounds.laser),
				bullet
					.弹药_轨道炮
					.build(100, Color.green, 36, 1, 0.5f)
					.bullet()
			);
			preset.inject(星际挖矿机);
		}
		//星际护卫舰 4x 夸特动力激光炮
		{
			unitAttack.injectWeapon(
				星际护卫舰,
				unitAttack.pos_涡轮激光炮(12, -16).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(300, Color.green, 40, 1f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际护卫舰,
				unitAttack.pos_涡轮激光炮(8, 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(300, Color.green, 40, 1.5f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			preset.inject(星际护卫舰, 2);
		}
		//星际巡演舰 6x
		{
			unitAttack.injectWeapon(
				星际巡洋舰,
				unitAttack.pos_涡轮激光炮(4, 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(500, Color.green, 50, 1f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际巡洋舰,
				unitAttack.pos_涡轮激光炮(12, -16).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(500, Color.green, 50, 1.5f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际巡洋舰,
				unitAttack.pos_涡轮激光炮(20, -40).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(500, Color.green, 50, 2f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			preset.inject(星际巡洋舰, 2);
		}
		//星际战列舰 8x
		{
			preset.inject(星际战列舰, 2);
			unitAttack.injectWeapon(
				星际战列舰,
				unitAttack.pos_涡轮激光炮(4, 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(700, Color.green, 65, 1f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际战列舰,
				unitAttack.pos_涡轮激光炮(4 + 2f * 8, 4f * 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(700, Color.green, 65, 1.5f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际战列舰,
				unitAttack.pos_涡轮激光炮(4 - 2f * 8, -2f * 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(700, Color.green, 65, 2f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际战列舰,
				unitAttack.pos_涡轮激光炮(4 - 4f * 8, -5f * 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(700, Color.green, 65, 2.5f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
		}
		//星际歼星舰 12x
		{
			preset.inject(星际歼星舰, 2);
			unitAttack.injectWeapon(
				星际歼星舰,
				unitAttack.pos_涡轮激光炮(4 + 2f * 8, 4f * 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(1000, Color.green, 80, 1f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际歼星舰,
				unitAttack.pos_涡轮激光炮(4 + 4f * 8, 7f * 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(1000, Color.green, 80, 1.5f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际歼星舰,
				unitAttack.pos_涡轮激光炮(4 + 0f * 8, 1f * 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(1000, Color.green, 80, 2f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际歼星舰,
				unitAttack.pos_涡轮激光炮(4 - 2f * 8, -2f * 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(1000, Color.green, 80, 2.5f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际歼星舰,
				unitAttack.pos_涡轮激光炮(4 - 4f * 8, -5f * 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(1000, Color.green, 80, 3f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				星际歼星舰,
				unitAttack.pos_涡轮激光炮(4 - 6f * 8, -8f * 8).mirror(true).sound(Sounds.laser),
				bullet.弹药_轨道炮
					.build(1000, Color.green, 80, 3.5f, 0.5f)
					.frag(bullet.弹片_元素狙击, items.木元素, 1, 0.5f, 6, 0.5f)
					.bullet()
			);
		}
		//猫盒
		{
			preset.inject(猫盒, 2);
		}
		//夸特造船厂
		{
			夸特动力船坞.requirements = ItemStack.with(items.纳米碳管, 1000, items.超导体, 800, items.晶金, 800, items.铬纳尔, 500, items.反重力陶瓷, 500, items.辐矿石, 500);
			夸特动力船坞.plans.add(new UnitFactory.UnitPlan(星际挖矿机, 30 * 60, ItemStack.with(items.纳米碳管, 150, items.超导体, 150, items.反重力陶瓷, 200)));
			夸特动力船坞.plans.add(new UnitFactory.UnitPlan(星际建造机, 30 * 60, ItemStack.with(items.纳米碳管, 200, items.超导体, 150, items.反重力陶瓷, 200, items.晶金, 50)));
			夸特动力船坞.plans.add(new UnitFactory.UnitPlan(星际护卫舰, 2 * 60 * 60, ItemStack.with(items.纳米碳管, 700, items.超导体, 550, items.反重力陶瓷, 250, items.辐矿石, 800)));
			夸特动力船坞.plans.add(new UnitFactory.UnitPlan(星际巡洋舰, 2.5f * 60 * 60, ItemStack.with(items.纳米碳管, 800, items.超导体, 500, items.铬纳尔, 350, items.反重力陶瓷, 350, items.水元素, 50)));
			夸特动力船坞.plans.add(new UnitFactory.UnitPlan(星际战列舰, 3 * 60 * 60, ItemStack.with(items.纳米碳管, 1500, items.超导体, 1000, items.晶金, 500, items.铬纳尔, 500, items.反重力陶瓷, 500, items.水元素, 500)));
			夸特动力船坞.plans.add(new UnitFactory.UnitPlan(星际歼星舰, 5f * 60 * 60, ItemStack.with(items.纳米碳管, 5000, items.超导体, 2500, items.晶金, 1500, items.铬纳尔, 1500, items.反重力陶瓷, 1500, items.反物质, 500, items.水元素, 5000, items.光元素, 50)));
			//夸特造船厂.plans.add(new UnitFactory.UnitPlan(猫盒, 10 * 60 * 60, ItemStack.with(items.纳米碳管, 10000, items.超导体, 5000, items.晶金, 3000, items.铬纳尔, 3000, items.反重力陶瓷, 3000, items.反物质, 1000)));
			preset.inject(夸特动力船坞);
		}
	}
}
