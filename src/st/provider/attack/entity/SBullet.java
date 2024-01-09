package st.provider.attack.entity;

import arc.graphics.Color;
import mindustry.entities.bullet.BulletType;
import st.addon.content.entity.StItem;

public class SBullet {
	//乘数
	public float damageMultiplier = 1f;
	//默认射击速度
	public float rate = 1f;
	//默认宽度
	public float width = 0.5f;
	//默认射程
	public float range = 30f;
	//默认时间
	public float time = 30f;
	//预设颜色
	public Color color = Color.white;
	//预设伤害
	public float damage = 50f;
	public float ammoMultiplier = 2f;
	
	//damage color range speed width
	public SBulletBuilder build(float damage) {
		return new SBulletBuilder(this, damage);
	}
	
	public SBulletBuilder build(float damage, Color color) {
		return new SBulletBuilder(this, damage, color);
	}
	
	public SBulletBuilder build(float damage, StItem item) {
		return new SBulletBuilder(this, damage, item.color);
	}
	
	
	public SBulletBuilder build(float damage, Color color, float range, float rate, float width) {
		return new SBulletBuilder(this, damage, color).range(range).rate(rate).width(width);
	}
	
	public SBulletBuilder build(float damage, StItem item, float range, float rate, float width) {
		return new SBulletBuilder(this, damage, item.color).range(range).rate(rate).width(width);
	}
	
	public SBulletBuilder build(float damage, Color color, float range, float rate) {
		return new SBulletBuilder(this, damage, color).range(range).rate(rate);
	}
	
	public SBulletBuilder build(float damage, StItem item, float range, float rate) {
		return new SBulletBuilder(this, damage, item.color).range(range).rate(rate);
	}
	
	public SBulletBuilder build(float damage, Color color, float range) {
		return new SBulletBuilder(this, damage, color).range(range);
	}
	
	public SBulletBuilder build(float damage, StItem item, float range) {
		return new SBulletBuilder(this, damage, item.color).range(range);
	}
	
	//生命周期 damage之后,frag之前,返回弹药实例
	public BulletType provide(SBulletBuilder.SBulletDamageProv prov) {
		return new BulletType();
	}
}
