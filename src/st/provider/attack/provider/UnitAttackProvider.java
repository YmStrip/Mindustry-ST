package st.provider.attack.provider;

import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
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
		return new WeaponPos(ST.name + "-机枪", x, y);
	}
	
	public WeaponPos pos_光粒(float x, float y) {
		return new WeaponPos("-collaris-weapon", x, y);
	}
	
	public WeaponPos pos_轨道炮(float x, float y) {
		return new WeaponPos("-omura-cannon", x, y);
	}
	
	public WeaponPos pos_激光炮(float x, float y) {
		return new WeaponPos(ST.name + "-激光炮", x, y);
	}
	
	public class WeaponPos {
		public WeaponPos(String nameC, float xC, float yC) {
			name = nameC;
			x = xC;
			y = yC;
		}
		
		public WeaponPos(String nameC) {
			name = nameC;
		}
		
		public boolean top = true;
		
		public WeaponPos top(boolean top) {
			this.top = top;
			return this;
		}
		
		float x = 5;
		
		public WeaponPos x(float x) {
			this.x = x;
			return this;
		}
		
		float y = 0;
		
		public WeaponPos y(float y) {
			this.y = y;
			return this;
		}
		
		String name;
		
		public WeaponPos name(String name) {
			this.name = name;
			return this;
		}
		
		public float shootY = 4.45f;
		
		public WeaponPos shootY(float shootY) {
			this.shootY = shootY;
			return this;
		}
		
		public float rotateSpeed = 3f;
		
		public WeaponPos rotateSpeed(float rotateSpeed) {
			this.rotateSpeed = rotateSpeed;
			return this;
		}
	}
	
	public Weapon weapon(UnitType unitType, WeaponPos pos, BulletType bulletType) {
		var data = new Weapon(pos.name) {{
			x = pos.x;
			y = pos.y;
			rotate = true;
			rotateSpeed = pos.rotateSpeed;
			//shadow = 20f;
			top = pos.top;
			shootY = pos.shootY;
			recoil = 4f;
			bullet = bulletType;
			reload = 60 / bulletType.reloadMultiplier;
			if (unitType.maxRange < bulletType.maxRange) {
				unitType.maxRange = bulletType.maxRange;
			}
			init();
		}};
		return data;
	}
	
	public UnitAttackProvider injectWeapon(UnitType unitType, WeaponPos pos, BulletType bulletType) {
		unitType.weapons.add(weapon(unitType, pos, bulletType));
		return this;
	}
}
