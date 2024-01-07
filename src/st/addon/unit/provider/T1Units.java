package st.addon.unit.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.units.StatusEntry;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.blocks.units.UnitFactory;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.addon.content.provider.ValueProvider;
import st.addon.unit.entity.SUnitStage;
import st.addon.unit.entity.SUnitType;
import st.provider.attack.SAttack;
import st.provider.attack.provider.BulletProvider;
import st.provider.attack.provider.UnitAttackProvider;

@Provider
public class T1Units extends LayerProvider {
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
	//emm
	public SUnitType 量子建造机 = new SUnitType("量子建造机") {{
		health = 1500;
		armor = 4;
		flying = true;
		speed = 22 / 8f;
		buildSpeed = 5;
		itemCapacity = 120;
		mineTier = 2;
		mineSpeed = 2;
	}};
	public SUnitType 量子挖矿机 = new SUnitType("量子挖矿机") {{
		health = 1200;
		armor = 4;
		flying = true;
		speed = 20f / 8f;
		itemCapacity = 240;
		mineTier = 3;
		mineSpeed = 5;
	}};
	public SUnitType 量子特工队 = new SUnitType("量子特工队") {{
		health = 3000;
		armor = 12;
		flying = true;
		speed = 30 / 8f;
		itemCapacity = 120;
	}};
	public SUnitType 量子电锯人 = new SUnitType("量子电锯人") {{
		health = 10000;
		armor = 15;
		flying = true;
		speed = 35 / 8f;
		itemCapacity = 120;
		hitSize = 4 * 8;
	}};
	//纳米元素构造厂
	public UnitFactory 原始人单位构造厂 = new UnitFactory("原始人单位构造厂") {{
		itemCapacity = 500;
	}};
	
	@Override
	public void run() {
		//Blocks.groundFactory
		
		//量子建造机
		{
			unitAttack.injectWeapon(量子建造机, unitAttack.pos_光粒(1, 1), bullet.弹药_机枪.build(items.超导体, 28, 5, 0.5f)
				.multiplier(items.超导体, 50)
				.frag(bullet.弹片_狙击, items.金元素, 1, 0.2f, 6, 0.5f)
				.bullet());
			preset.inject(量子建造机, 1);
		}
		//量子挖矿机
		{
			unitAttack.injectWeapon(量子挖矿机, unitAttack.pos_光粒(1, 1), bullet.弹药_机枪.build(items.超导体, 26, 5, 0.5f)
				.multiplier(items.超导体, 26)
				.frag(bullet.弹片_狙击, items.金元素, 1, 0.2f, 4, 0.5f)
				.bullet());
			preset.inject(量子挖矿机, 1);
		}
		//量子特工队
		{
			unitAttack.injectWeapon(量子特工队, unitAttack.pos_光粒(0, 0), bullet.弹药_机枪.build(items.超导体, 26, 5, 0.8f)
				.multiplier(items.超导体, 100)
				.frag(bullet.弹片_狙击, items.金元素, 1, 0.2f, 5, 0.5f)
				.bullet());
			unitAttack.injectWeapon(量子特工队, unitAttack.pos_光粒(0, 0), bullet.弹药_导弹.build(items.超导体, 26, 0.5f, 0.5f)
				.multiplier(items.水元素, 100)
				.frag(bullet.弹片_爆炸, items.金元素, 1, 0.45f, 8, 0.5f)
				.bullet());
			量子特工队.stages.add(new SUnitStage("狂暴") {{
				health = 量子特工队.health / 1.5f;
				armor = 量子特工队.armor * 1.5f;
				effect(new StatusEntry().set(StatusEffects.sapped, 120 * 60));
				effect(new StatusEntry().set(StatusEffects.overdrive, 120 * 60));
			}});
			preset.inject(量子特工队, 1);
		}
		//量子电锯人
		{
			unitAttack.injectWeapon(量子电锯人,
				unitAttack.pos_导弹(0, 0).top(false),
				bullet.弹药_导弹
					.build(items.土元素, 40, 1, 1f)
					.multiplier(items.土元素, 250)
					.frag(bullet.弹片_爆炸, items.土元素, 1, 0.5f, 7, 1)
					.frag(bullet.弹片_元素狙击, items.土元素, 1, 0.1f, 4, 1)
					.bullet()
			);
			unitAttack.injectWeapon(量子电锯人, unitAttack.pos_激光炮(0, 0), bullet.弹药_激光.build(items.木元素, 20, 1, 2.8f)
				.multiplier(items.木元素, 500)
				.bullet());
			unitAttack.injectWeapon(量子电锯人, unitAttack.pos_机枪(-12, 6), bullet.弹药_元素.build(items.火元素, 35, 6, 0.5f)
				.multiplier(items.火元素, 300)
				.frag(bullet.弹片_元素狙击, items.金元素, 1, 0.2f, 5, 0.5f)
				.bullet());
			unitAttack.injectWeapon(量子电锯人, unitAttack.pos_机枪(12, 6), bullet.弹药_元素.build(items.水元素, 35, 6, 0.5f)
				.multiplier(items.水元素, 300)
				.frag(bullet.弹片_元素狙击, items.金元素, 1, 0.2f, 5, 0.5f)
				.bullet());
			量子电锯人.stages.add(new SUnitStage("狂暴") {{
				health = 量子电锯人.health * 0.8f;
				armor = 量子电锯人.armor * 1.5f;
				effect(new StatusEntry().set(StatusEffects.sapped, 120 * 60));
				effect(new StatusEntry().set(StatusEffects.overdrive, 120 * 60));
			}});
			preset.inject(量子电锯人, 1);
		}
		//原始人单位厂
		{
			原始人单位构造厂.requirements = ItemStack.with(items.超导体, 150, items.纳米碳管, 300, Items.silicon, 150, Items.titanium, 250, Items.metaglass, 150, Items.titanium, 50);
			原始人单位构造厂.plans.add(new UnitFactory.UnitPlan(量子建造机, 60 * 25, ItemStack.with(items.纳米碳管, 150, items.超导体, 100, Items.silicon, 150, Items.metaglass, 150, Items.plastanium, 100)));
			原始人单位构造厂.plans.add(new UnitFactory.UnitPlan(量子挖矿机, 60 * 15, ItemStack.with(items.纳米碳管, 125, items.超导体, 125, Items.silicon, 100, Items.metaglass, 125)));
			原始人单位构造厂.plans.add(new UnitFactory.UnitPlan(量子特工队, 60 * 20, ItemStack.with(items.纳米碳管, 200, items.超导体, 150, Items.silicon, 150, Items.metaglass, 50, Items.plastanium, 150)));
			原始人单位构造厂.plans.add(new UnitFactory.UnitPlan(量子电锯人, 60 * 20, ItemStack.with(items.纳米碳管, 800, items.超导体, 600, Items.silicon, 450, Items.metaglass, 550, Items.plastanium, 500)));
			preset.inject(原始人单位构造厂, 1);
		}
	}
}
