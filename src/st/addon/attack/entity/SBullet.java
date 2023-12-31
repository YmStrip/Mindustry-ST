package st.addon.attack.entity;

import mindustry.entities.bullet.BulletType;
import st.addon.entity.entity.StItem;

public class SBullet {
	//弹药外部倍率
	public float damageMultiplier = 1;
	
	public SBulletBuilder build(StItem itemP, float rangeP, float speedP, float widthP, float damageMultiplierP) {
		return new SBulletBuilder(this, itemP, rangeP, speedP, widthP, damageMultiplierP);
	}
	public SBulletBuilder build(StItem itemP, float rangeP, float speedP, float widthP) {
		return new SBulletBuilder(this, itemP, rangeP, speedP, widthP);
	}
	//生命周期 damage之后,frag之前,返回弹药实例
	public BulletType provide(StItem itemP, float rangeP, float speedP, float widthP, float damageP) {
		return new BulletType();
	}
	
	//计算速度
	public float getSpeed(float 计算后的射程, float lifetime) {
		return (60 / lifetime) * 8f * (计算后的射程 / 8 / 60);
	}
	
	//生命周期: damage,bullet计算之前,负责修改一些参数之类的,例如倍增器
	public void prepare(SBulletBuilder sb) {
	}
}
