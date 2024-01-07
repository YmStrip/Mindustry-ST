package st.addon.attack.provider;

import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import st.ST;

@Provider
public class UnitAttackProvider extends LayerProvider {
	@Require
	Logger logger;
	
	@FunctionalInterface
	public interface callUnit {
		void call(UnitType type);
	}
	
	public UnitAttackProvider inject(String name, callUnit callUnit) {
		var u = Vars.content.unit(ST.name + "-" + name);
		if (u == null) {
			logger.err("%s 找不到", name);
			return this;
		}
		u.maxRange = -1;
		u.range = -1;
		callUnit.call(u);
		return this;
	}
	
	
	public WeaponPos pos_导弹(float x, float y) {
		return new WeaponPos("-sei-launcher", x, y);
	}
	
	public WeaponPos pos_机枪(float x, float y) {
		return new WeaponPos("-reign-weapon", x, y);
	}
	
	public WeaponPos pos_光粒(float x, float y) {
		return new WeaponPos("-collaris-weapon", x, y);
	}
	
	public WeaponPos pos_轨道炮(float x, float y) {
		return new WeaponPos("-omura-cannon", x, y);
	}
	
	public WeaponPos pos_激光炮(float x, float y) {
		return new WeaponPos("conquer-weapon", x, y);
	}
	
	public class WeaponPos {
		float x = 5;
		float y = 0;
		String name;
		
		public WeaponPos(String nameC) {
			name = nameC;
		}
		
		public WeaponPos(String nameC, float xC, float yC) {
			name = nameC;
			x = xC;
			y = yC;
		}
	}
	
	public UnitAttackProvider injectWeapon(UnitType unitType, WeaponPos pos, BulletType bulletType) {
		var data = new Weapon(pos.name) {{
			x = pos.x;
			y = pos.y;
			rotate = true;
			rotateSpeed = 4f;
			//shadow = 20f;
			shootY = 4.5f;
			recoil = 4f;
			bullet = bulletType;
			reload = 60 / bulletType.reloadMultiplier;
			if (unitType.maxRange < bulletType.maxRange) {
				unitType.maxRange = bulletType.maxRange;
			}
			init();
		}};
		unitType.weapons.add(data);
		return this;
	}
}
