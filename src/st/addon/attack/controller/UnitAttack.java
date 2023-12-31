package st.addon.attack.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;

import mindustry.game.EventType;
import st.addon.attack.provider.BulletProvider;
import st.addon.attack.provider.UnitAttackProvider;
import st.addon.item.ITEM;
import st.addon.item.provider.ItemProvider;


@Controller
public class UnitAttack extends LayerController {
	@Require(cls = BulletProvider.class)
	BulletProvider bullets;
	@Import(cls = ITEM.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Require(cls = UnitAttackProvider.class)
	UnitAttackProvider units;
	
	public void inject_val() {
	
	}
	
	//固定dps倍率
	//t1 *0.1x ~40
	//t2 *0.5x ~50
	//t3 *1x ~65
	//t4 *5x ~90
	//战士
	public void 金() {
		units.inject("金-t1", t -> {
			t.hitSize = 8f;
			t.clipSize = -1;
			units.injectWeapon(t, units.pos_机枪(0, 0),
				bullets.弹药_机枪
					.build(items.金元素, 45f, 6f, 0.2f, 0.1f)
					.frag(bullets.弹片_狙击, items.金元素, 1, 0.5f,4,0.5f)
					.bullet());
		});
		units.inject("金-t2", t -> {
			t.hitSize = 16f;
			units.injectWeapon(t, units.pos_机枪(0, 0),
				bullets.弹药_机枪
					.build(items.金元素, 45f, 7f, 0.4f, 0.25f)
					.frag(bullets.弹片_狙击, items.金元素, 1, 0.5f,5,0.5f)
					.bullet());
			units.injectWeapon(t, units.pos_导弹(0, 0), bullets.弹药_导弹
				.build(items.金元素, 50f, 0.5f, 1f, 0.5f)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.75f,10f,1f)
				.bullet());
		});
		units.inject("金-t3", t -> {
			t.hitSize = 32f;
			units.injectWeapon(t, units.pos_机枪(5, 1),
				bullets.弹药_机枪
					.build(items.金元素, 55f, 8f, 0.5f, 0.45f)
					.frag(bullets.弹片_狙击, items.金元素, 1, 0.5f,6,0.5f)
					.bullet());
			units.injectWeapon(t, units.pos_导弹(7.5f, 1.5f), bullets.弹药_导弹
				.build(items.金元素, 60f, 0.5f, 1f, 0.65f)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.75f,10f,1f)
				.bullet());
			units.injectWeapon(t, units.pos_光粒(10, 2), bullets.弹药_光粒
				.build(items.金元素, 65f, 0.2f, 2f, 1f)
				.frag(bullets.弹片_光粒, items.金元素, 6, 0.25f,6,8)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.75f,10f,1f)
				.bullet());
		});
		units.inject("金-t4", t -> {
			t.hitSize = 64f;
			units.injectWeapon(t, units.pos_机枪(10, -5),
				bullets.弹药_机枪
					.build(items.金元素, 60f, 12f, 1f, 1.5f)
					.frag(bullets.弹片_狙击, items.金元素, 1, 0.5f,10,0.5f)
					.bullet());
			units.injectWeapon(t, units.pos_导弹(8, -2), bullets.弹药_导弹
				.build(items.金元素, 65f, 2f, 8, 1f)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.75f,10f,1f)
				.bullet());
			units.injectWeapon(t, units.pos_光粒(15, 2), bullets.弹药_光粒
				.build(items.金元素, 80f, 1f, 3f, 3f)
				.frag(bullets.弹片_光粒, items.金元素, 6, 0.25f, 6, 15)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.75f,10f,1f)
				.bullet());
			units.injectWeapon(t, units.pos_轨道炮(2, -5), bullets.弹药_轨道炮
				.build(items.金元素, 90f, 0.5f, 2, 5f)
				.frag(bullets.弹片_星光, items.金元素, 3, 0.25f, 20, 10)
				.frag(bullets.弹片_爆炸, items.金元素, 1, 0.75f,10f,1f)
				.bullet());
		});
	}
	
	//辅助
	public void 木() {
		//base = 800
		var base = items.木元素.damage;
	}
	
	//法师
	public void 水() {
		//base = 1100
		var base = items.水元素.damage;
	}
	
	//输出
	public void 火() {
		//base = 1500
		var base = items.火元素.damage;
	}
	
	//坦克
	public void 土() {
		//base = 900
		var base = items.土元素.damage;
	}
	
	public void inject_mod() {
		金();
		木();
		水();
		火();
		土();
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			inject_val();
			inject_mod();
		});
	}
}
