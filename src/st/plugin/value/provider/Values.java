package st.plugin.value.provider;

import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;
import st.addon.entity.entity.StItem;

@Provider
public class Values extends LayerProvider {
	//伤害*c->防御力 log(health)/log(2)
	public float armor(float damage, float count) {
		return (float) (Math.log(health(damage, count)) / Math.log(2));
	}
	
	public void armor(UnitType t) {
		var items = t.getFirstRequirements();
		if (items == null) {
			t.armor = 5;
		} else {
			var armor = 1f;
			for (var i : items) {
				if (i.item instanceof StItem s) {
					armor += armor(s.damage, i.amount);
				} else {
					armor += armor(i.item.cost * 100 + 1, i.amount);
				}
			}
		}
	}
	
	//伤害*c->生命值
	public float health(float damage, float count) {
		return (float) (damage * 0.1 * (1 + Math.log(count) / Math.log(100)) * (1 + Math.log(count) / Math.log(5000)));
	}
	
	public void health(Block t) {
		var health = 100f;
		for (var i : t.requirements) {
			if (i.item instanceof StItem si) {
				health += (int) health(si.damage, i.amount);
			} else {
				health += (int) health(i.item.cost * 100 + 1, i.amount);
			}
		}
		t.health = (int) health;
	}
	
	//伤害*c->流体倍率
	public float liquidMultiplier(float damage, float count) {
		//设计一个数学函数，f(a,b)，其中a代表伤害，值域[0,无穷大]，b代表数量，[0,无穷大)，f最大值1.99，最小值1.01，当b零点时，f最小值1.01，当b无穷大时，f1.99，其中b从左到右单调递减，a影响平滑度，但是不影响值域
		//e的-x次方
		//f(a,b) = 1.01 + (1.99 - 1.01) * e^(-a/b) ，修正 a = count b = damage
		return (float) (1.01 + (1.99 - 1.01) * Math.pow(Math.E, (-count / damage)));
	}
	
	public void liquidMultiplier(Turret t) {
		var m = 0f;
		var mc = 0f;
		for (var i : t.requirements) {
			++mc;
			if (i.item instanceof StItem si) {
				m += liquidMultiplier(si.damage, i.amount);
			} else {
				m += liquidMultiplier(i.item.cost * 100 + 1, i.amount);
			}
		}
		if (mc == 0) mc = 1;
		if (m == 0) m = 1;
		var v = m / mc;
		t.consumeCoolant(v - 1);
		t.coolantMultiplier = v;
	}
}
