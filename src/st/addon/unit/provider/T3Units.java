package st.addon.unit.provider;

import arc.Core;
import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.Vars;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.Sounds;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.addon.content.provider.ValueProvider;
import st.addon.unit.entity.SUnitStage;
import st.addon.unit.entity.SUnitType;
import st.provider.attack.SAttack;
import st.provider.attack.provider.BulletProvider;
import st.provider.attack.provider.UnitAttackProvider;

@Provider
public class T3Units extends LayerProvider {
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
	//boss类
	public SUnitType 歌者 = new SUnitType("歌者") {{
		//搭配光粒炮
		//血量极高
		//免疫激光
	}};
	public SUnitType Umnitrix = new SUnitType("Umnitrix") {{
		flying = true;
		targetGround = true;
		targetAir = true;
		health = 50000;
		armor = 1500;
		hitSize = 4 * 8;
		speed = 40 / 8f;
	}};
	public SUnitType 九尾巨兵 = new SUnitType("九尾巨兵") {{
		flying = true;
		targetGround = true;
		targetAir = true;
		health = 50000;
		armor = 1500;
		hitSize = 4 * 8;
		speed = 60 / 8f;
	}};
	public SUnitType 水滴 = new SUnitType("水滴") {
		{
			flying = true;
			targetGround = true;
			targetAir = true;
			health = 50000;
			armor = 1500;
			hitSize = 4 * 8;
			speed = 60 / 8f;
			//横冲直闯
			//血量极高
		}
		
		public class SUnit extends SUnitType.SUnit {
			@Override
			public void update() {
				super.update();
				//每秒对周围建筑和单位造成伤害[建筑%10],并且添加特效
				//if (!Vars.net.client()||isLocal()) {
				//
				//}
			}
		}
	};
	public SUnitType Kello = new SUnitType("kello") {{
		//暗黑星大王？
	}};
	//元素类[可制造]
	public SUnitType 中子星 = new SUnitType("中子星") {{
	
	}};
	public SUnitType 星界主宰 = new SUnitType("星界主宰") {{
	
	}};
	public SUnitType 自然之灵 = new SUnitType("自然之灵") {{
	
	}};
	public SUnitType 海王 = new SUnitType("海王") {{
	
	}};
	public SUnitType 大地守护者 = new SUnitType("大地守护者") {{
	
	}};
	
	@Override
	public void run() {
		//歌者
		{
			preset.inject(歌者, 4);
		}
		//水滴
		{
			var weapon = unitAttack.weapon(
				水滴,
				unitAttack.pos_光粒(0, 0),
				bullet.弹药_水滴
					.build(items.水元素, 4, 5, 2, 1)
					.multiplier(items.水元素, 1000)
					.frag(bullet.弹片_元素狙击, items.水元素, 1, 0.9f, 5, 0.5f)
					.frag(bullet.弹片_爆炸, items.水元素, 1, 0.9f, 5, 0.5f)
					.bullet()
			);
			weapon.shootSound = Sounds.none;
			水滴.weapons.add(weapon);
			水滴.stages.add(new SUnitStage() {{
				name = "狂暴";
				health = 水滴.health * 0.8f;
				armor = 水滴.armor * 1.5f;
				effect(new StatusEntry().set(StatusEffects.overdrive, 60 * 60));
			}});
			水滴.stages.add(new SUnitStage() {{
				name = "背水一战";
				speed = 水滴.speed * 1.5f;
				health = 水滴.health * 0.6f;
				armor = 水滴.armor * 2f;
				effect(new StatusEntry().set(StatusEffects.boss, 60 * 60));
				effect(new StatusEntry().set(StatusEffects.overdrive, 60 * 60));
			}});
			
			preset.inject(水滴, 4);
		}
		//九尾巨兵,九条命
		{
			九尾巨兵.stages.add(new SUnitStage(){{
				name = "狐生";
				speed = 九尾巨兵.speed * 0.9f;
				health = 九尾巨兵.health * 0.8f;
				armor = 九尾巨兵.armor * 0.8f;
				effect(new StatusEntry().set(StatusEffects.overclock,30*60));
			}});
			九尾巨兵.stages.add(new SUnitStage(){{
				name = "狐生";
				speed = 九尾巨兵.speed * 0.8f;
				health = 九尾巨兵.health * 0.75f;
				armor = 九尾巨兵.armor * 0.75f;
				effect(new StatusEntry().set(StatusEffects.overclock,30*60));
			}});
			九尾巨兵.stages.add(new SUnitStage(){{
				name = "狐生";
				speed = 九尾巨兵.speed * 0.75f;
				health = 九尾巨兵.health * 0.6f;
				armor = 九尾巨兵.armor * 0.6f;
				effect(new StatusEntry().set(StatusEffects.overclock,30*60));
			}});
			九尾巨兵.stages.add(new SUnitStage(){{
				name = "狐生";
				speed = 九尾巨兵.speed * 0.7f;
				health = 九尾巨兵.health * 0.55f;
				armor = 九尾巨兵.armor * 0.55f;
				effect(new StatusEntry().set(StatusEffects.overclock,30*60));
			}});
			九尾巨兵.stages.add(new SUnitStage(){{
				name = "狐生";
				speed = 九尾巨兵.speed * 0.6f;
				health = 九尾巨兵.health * 0.45f;
				armor = 九尾巨兵.armor * 0.45f;
				effect(new StatusEntry().set(StatusEffects.overclock,30*60));
			}});
			九尾巨兵.stages.add(new SUnitStage(){{
				name = "狐生";
				speed = 九尾巨兵.speed * 0.5f;
				health = 九尾巨兵.health * 0.35f;
				armor = 九尾巨兵.armor * 0.35f;
				effect(new StatusEntry().set(StatusEffects.overclock,30*60));
			}});
			九尾巨兵.stages.add(new SUnitStage(){{
				name = "狐生";
				speed = 九尾巨兵.speed * 0.5f;
				health = 九尾巨兵.health * 0.25f;
				armor = 九尾巨兵.armor * 0.25f;
				effect(new StatusEntry().set(StatusEffects.overclock,30*60));
			}});
			九尾巨兵.stages.add(new SUnitStage(){{
				name = "狐生";
				speed = 九尾巨兵.speed * 0.5f;
				health = 九尾巨兵.health * 0.2f;
				armor = 九尾巨兵.armor * 0.2f;
				effect(new StatusEntry().set(StatusEffects.overclock,30*60));
			}});
			unitAttack.injectWeapon(
				九尾巨兵,
				unitAttack.pos_光粒(0, 0),
				bullet.弹药_元素
					.build(items.土元素, 60, 12, 1, 1)
					.multiplier(items.土元素, 1500)
					.frag(bullet.弹片_元素狙击, items.水元素, 1, 0.9f, 5, 0.5f)
					.frag(bullet.弹片_爆炸, items.水元素, 1, 0.5f, 5, 0.5f)
					.bullet()
			);
			unitAttack.injectWeapon(
				九尾巨兵,
				unitAttack.pos_光粒(0, 0),
				bullet.弹药_导弹
					.build(items.土元素, 80, 1, 1, 1)
					.multiplier(items.土元素, 800)
					.frag(bullet.弹片_元素狙击, items.水元素, 1, 0.9f, 5, 0.5f)
					.frag(bullet.弹片_爆炸, items.水元素, 1, 0.5f, 5, 0.5f)
					.bullet()
			);
			preset.inject(九尾巨兵,4);
		}
		//暗面主宰
		{
			preset.inject(Kello, 4);
		}
		//中子星=
		{
			preset.inject(中子星, 4);
		}
		//中子星=
		{
			preset.inject(星界主宰, 4);
		}
		//中子星=
		{
			preset.inject(自然之灵, 4);
		}
		//中子星=
		{
			preset.inject(海王, 1);
		}
		//中子星=
		{
			preset.inject(大地守护者, 1);
		}
	}
}
