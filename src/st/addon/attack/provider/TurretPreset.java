package st.addon.attack.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.Vars;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.BuildVisibility;
import st.ST;
import st.addon.entity.entity.StItem;
import st.plugin.value.SValue;
import st.plugin.value.provider.Values;

@Provider
public class TurretPreset extends LayerProvider {
	@Import(cls = SValue.class)
	@Require(cls = Values.class)
	Values values;
	
	public void inject(Turret t) {
		inject(t, BuildVisibility.shown);
	}
	
	public void inject(Turret t, BuildVisibility buildVisibility) {
		//Basic
		{
			t.buildVisibility = BuildVisibility.shown;
			t.researchCostMultiplier = 0.8f;
			t.buildCostMultiplier = 0.8f;
			t.reload = 60;
			t.targetAir = true;
			t.targetGround = true;
			//t.shootSound = Sounds.laser;
			t.chargeSound = Sounds.lasercharge;
			t.hasPower = true;
			t.hasLiquids = true;
			t.category = Category.turret;
		}
		//流体倍率计算
		{
			var m = 0f;
			var mc = 0f;
			for (var i : t.requirements) {
				++mc;
				if (i.item instanceof StItem si) {
					m += values.liquidMultiplier(si.damage, i.amount);
				} else {
					m += values.liquidMultiplier(i.item.cost * 100, i.amount);
				}
			}
			if (mc == 0) mc = 1;
			if (m == 0) m = 1;
			var v = m / mc;
			t.consumeCoolant(v - 1);
			t.coolantMultiplier = v;
		}
		//生命计算器
		{
			values.health(t);
		}
	}
	
	public TurretPreset injectItem(String name, EffectProvider.callItem callItem) {
		var b = Vars.content.block(ST.name + "-" + name);
		if (b == null) {
			b = new ItemTurret(name);
		}
		var data = (ItemTurret) b;
		data.hasLiquids = true;
		data.reload = 60;
		callItem.call(data);
		return this;
	}
	
	public TurretPreset injectPower(String name, EffectProvider.callPower callPower) {
		var b = Vars.content.block(ST.name + "-" + name);
		if (b == null) {
			b = new PowerTurret(name);
		}
		var data = (PowerTurret) b;
		data.reload = 60;
		callPower.call(data);
		data.init();
		return this;
	}
}
