package st.addon.attack.entity;

import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.PointBulletType;
import st.addon.content.entity.StItem;

public class SBulletBuilder {
	//避免过度夸张的数值
	public float speedMultiplier(float speed) {
		return (float) (0.87951602063152f / Math.pow(speed, 0.846409565237684f));
	}
	
	public SBulletBuilder(SBullet sBulletP, StItem itemP, float rangeP, float speedP, float widthP, float damageMultiplierP) {
		sBullet = sBulletP;
		item = itemP;
		speed = speedP;
		range = rangeP;
		width = widthP;
		damageMultiplier = damageMultiplierP;
	}
	
	public SBulletBuilder(SBullet sBulletP, StItem itemP, float rangeP, float speedP, float widthP) {
		this(sBulletP, itemP, rangeP, speedP, widthP, 1);
	}
	
	public SBulletBuilder(SBullet sBulletP) {
		sBullet = sBulletP;
	}
	
	public StItem item;
	
	public SBulletBuilder item(StItem s) {
		if (lastRoot != null) {
			lastRoot.item = s;
		} else {
			item = s;
		}
		return this;
	}
	
	//数量
	public boolean root = true;
	public int count = 1;
	
	public SBulletBuilder count(int s) {
		if (lastRoot != null) {
			lastRoot.count = s;
		} else
		//
		{
			count = s;
		}
		return this;
	}
	
	public float damageMultiplier = 1;
	
	public SBulletBuilder multiplier(StItem item, float damage) {
		damageMultiplier = damage / item.damage;
		return this;
	}
	
	public SBulletBuilder damageMultiplier(float s) {
		if (lastRoot != null) {
			lastRoot.damageMultiplier = s;
		} else {
			damageMultiplier = s;
		}
		return this;
	}
	
	public float lifeTime = -1;
	
	public SBulletBuilder lifetime(float s) {
		if (lastRoot != null) {
			lastRoot.lifeTime = s;
		} else {
			lifeTime = s;
		}
		return this;
	}
	
	//frag倍率
	public float fragUse = 0f;
	
	public SBulletBuilder fragUse(float s) {
		if (lastRoot != null) {
			lastRoot.fragUse = s;
		} else {
			fragUse = s;
		}
		return this;
	}
	
	public float range = 8f;
	
	public SBulletBuilder range(float s) {
		if (lastRoot != null) {
			lastRoot.range = s;
		} else {
			range = s;
		}
		return this;
	}
	
	public float width = 8f;
	
	public SBulletBuilder width(float s) {
		if (lastRoot != null) {
			lastRoot.width = s;
		} else {
			width = s;
		}
		return this;
	}
	
	public float speed = 1f;
	
	public SBulletBuilder speed(float s) {
		if (lastRoot != null) {
			lastRoot.speed = s;
		} else {
			speed = s;
		}
		return this;
	}
	
	//弹药
	public SBullet sBullet;
	//分配弹药
	public SBulletBuilder frag;
	public SBulletBuilder lastRoot;
	public BulletType bullet;
	//扩散方式 1 随机 2矢量冲击
	public int spread = 1;
	
	//矢量冲击,类似噩兆效果的碎片方向
	public SBulletBuilder vectorShock() {
		if (lastRoot != null) {
			lastRoot.spread = 2;
		} else {
			spread = 2;
		}
		return this;
	}
	
	@FunctionalInterface
	public interface frag {
		void frag(SBulletBuilder sBulletBuilder);
	}
	
	public SBulletBuilder frag(SBullet sBullet, StItem item) {
		return frag(sBullet, 0.5f, t -> {
			t.item = item;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, StItem item, int count, float use) {
		return frag(sBullet, use, t -> {
			t.count = count;
			t.item = item;
		});
	}
	
	
	//类型,物品,数量,倍率,范围,宽度
	public SBulletBuilder frag(SBullet sBullet, StItem item, int count, float use, float range, float width) {
		return frag(sBullet, use, t -> {
			t
				.item(item)
				.width(width)
				.range(range)
				.count(count)
			;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, StItem item, int count) {
		return frag(sBullet, 0.5f, t -> {
			t.item(item).count(count);
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, float use, frag f) {
		if (frag != null) {
			lastRoot.frag(sBullet, use, f);
			lastRoot = lastRoot.lastRoot;
		}
		//
		else {
			fragUse = use;
			frag = new SBulletBuilder(sBullet);
			frag.root = false;
			frag.item = item;
			lastRoot = frag;
			f.frag(frag);
		}
		return this;
	}
	
	public BulletType bullet() {
		init(item.damage * damageMultiplier);
		return bullet;
	}
	
	public BulletType bullet(float damage) {
		init(damage);
		return bullet;
	}
	
	//init
	public void init(float d) {
		var damage = 50f;
		var fragDamage = 50f;
		sBullet.prepare(this);
		var speedMultiplier = speedMultiplier(speed);
		if (frag != null) {
			damage = d * (1 - fragUse) * speedMultiplier;
			fragDamage = d * fragUse * speedMultiplier;
		} else {
			damage = d * speedMultiplier;
		}
		{
			damage *= sBullet.damageMultiplier;
			if (!root) damage /= count;
		}
		
		bullet = sBullet.provide(item, range * 8, speed, width * 8, damage);
		if (frag != null) {
			bullet.fragBullet = new BasicBulletType() {{
				lifetime = 0f;
				damage = 0f;
				speed = 0;
				fragBullets = frag.count;
				if (spread == 1) {
					weaveRandom = true;
				} else {
					weaveMag = 1f;
					weaveScale = 1f;
				}
			}};
			bullet.fragBullet.fragBullet = frag.bullet(fragDamage);
			bullet.fragBullets = 1;
			bullet.fragBullet.init();
		}
		if (root) {
			if (!(bullet instanceof PointBulletType)) bullet.range = range * 8;
			//bullet.rangeOverride = range * 8;
			bullet.reloadMultiplier = speed;
			if (lifeTime > 0) {
				bullet.lifetime = lifeTime;
			}
			if (bullet.speed == -1) bullet.speed = sBullet.getSpeed(range * 8, bullet.lifetime);
		}
		bullet.init();
		if (root) {
			bullet.maxRange = range * 8;
		}
	}
}
