package st.provider.attack.entity;

import arc.graphics.Color;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import st.addon.content.entity.StItem;


public class SBulletBuilder {
	//计算后的数据
	public class SBulletDamageProv {
		//飞行速度 射程/时间*8
		public float speed = 1f;
		//伤害
		public float damage = 1f;
		//射程 range*8
		public float range = 1f;
		//射速 count/s
		public float rate = 1f;
		//时间
		public float lifetime = 1f;
		//宽度
		public float width = 1;
		//颜色
		public Color color;
	}
	
	public boolean root = true;
	//分裂伤害比率
	public float fragUse = 0f;
	//分裂个数
	public int count = 1;
	//分裂类型
	public int spread = 1;
	public float time = -1;
	
	public float range = -1f;
	public float rate = -1f;
	public float damage = -1f;
	public float width = -1f;
	public float ammoMultiplier = -1f;
	public Color color;
	//弹药
	public SBullet sBullet;
	//分配弹药
	public SBulletBuilder frag;
	public SBulletBuilder lastRoot;
	public BulletType bullet;
	//扩散方式 1 随机 2矢量冲击
	
	//避免过度夸张的数值
	public float rateMultiplier(float speed) {
		return (float) (0.87951602063152f / Math.pow(speed, 0.846409565237684f));
	}
	
	public SBulletBuilder(SBullet sBulletP) {
		sBullet = sBulletP;
	}
	
	public SBulletBuilder(SBullet sBulletP, float damage) {
		this(sBulletP);
		this.damage = damage;
	}
	
	public SBulletBuilder(SBullet sBulletP, float damage, Color color) {
		this(sBulletP, damage);
		this.color = color;
	}
	
	public SBulletBuilder item(StItem s) {
		if (lastRoot != null) {
			lastRoot.damage = s.damage;
			lastRoot.color = s.color;
		} else {
			damage = s.damage;
			color = s.color;
		}
		return this;
	}
	
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
	
	public SBulletBuilder time(float s) {
		if (lastRoot != null) {
			lastRoot.time = s;
		} else {
			time = s;
		}
		return this;
	}
	
	//frag倍率
	public SBulletBuilder fragUse(float s) {
		if (lastRoot != null) {
			lastRoot.fragUse = s;
		} else {
			fragUse = s;
		}
		return this;
	}
	
	public SBulletBuilder damage(float s) {
		if (lastRoot == null) {
			damage = s;
		}
		return this;
	}
	
	public SBulletBuilder range(float s) {
		if (lastRoot != null) {
			lastRoot.range = s;
		} else {
			range = s;
		}
		return this;
	}
	
	
	public SBulletBuilder width(float s) {
		if (lastRoot != null) {
			lastRoot.width = s;
		} else {
			width = s;
		}
		return this;
	}
	
	
	public SBulletBuilder rate(float s) {
		if (lastRoot != null) {
			lastRoot.rate = s;
		} else {
			rate = s;
		}
		return this;
	}
	
	public SBulletBuilder ammoMultiplier(float s) {
		if (root) {
			ammoMultiplier = s;
		}
		return this;
	}
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
	
	//bullet color count use range width
	
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
			frag.color = color;
			lastRoot = frag;
			f.frag(frag);
		}
		return this;
	}
	
	public SBulletBuilder frag(SBullet sBullet, StItem item) {
		return frag(sBullet, 0.5f, t -> {
			t.color = item.color;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, Color color) {
		return frag(sBullet, 0.5f, t -> {
			t.color = color;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, StItem item, int count) {
		return frag(sBullet, 0.5f, t -> {
			t.color = item.color;
			t.count = count;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, Color color, int count) {
		return frag(sBullet, 0.5f, t -> {
			t.color = color;
			t.count = count;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, StItem item, int count, float use) {
		return frag(sBullet, use, t -> {
			t.color = item.color;
			t.count = count;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, Color color, int count, float use) {
		return frag(sBullet, use, t -> {
			t.color = color;
			t.count = count;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, StItem item, int count, float use, float range) {
		return frag(sBullet, use, t -> {
			t.color = item.color;
			t.count = count;
			t.range = range;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, Color color, int count, float use, float range) {
		return frag(sBullet, use, t -> {
			t.color = color;
			t.count = count;
			t.range = range;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, StItem item, int count, float use, float range, float width) {
		return frag(sBullet, use, t -> {
			t.color = item.color;
			t.count = count;
			t.range = range;
			t.width = width;
		});
	}
	
	public SBulletBuilder frag(SBullet sBullet, Color color, int count, float use, float range, float width) {
		return frag(sBullet, use, t -> {
			t.color = color;
			t.count = count;
			t.range = range;
			t.width = width;
		});
	}
	
	public BulletType bullet() {
		init(damage);
		return bullet;
	}
	
	public BulletType bullet(float damage) {
		init(damage);
		return bullet;
	}
	
	float def(float f, float def1) {
		if (f == -1) return def1;
		return f;
	}
	
	Color def(Color now, Color def) {
		if (now == null) {
			if (def == null) return Color.white;
			return def;
		}
		return now;
	}
	
	//init
	public void init(float d) {
		//生成计算数据
		var _lifetime = def(time, sBullet.time);
		var _width = def(width, sBullet.width) * 8;
		var _range = def(range, sBullet.range) * 8;
		var _rate = def(rate, sBullet.rate);
		var _color = def(color, sBullet.color);
		var _ammoMultiplier = def(ammoMultiplier,sBullet.ammoMultiplier);
		//计算子弹射速(大部分适用) (60 / 时间) * 8f * (射程 / 8 / 60) = 射程/时间
		var _speed = (_range / _lifetime);
		var _baseDamage = def(d, def(damage, sBullet.damage));
		var _fragDamage = 1f;
		var _damage = 1f;
		var _rateMultiplier = rateMultiplier(_rate);
		//根据分裂子弹计算分割伤害
		if (frag != null) {
			_damage = _baseDamage * (1 - fragUse) * _rateMultiplier;
			_fragDamage = _baseDamage * fragUse * _rateMultiplier;
		}
		//
		else {
			_damage = _baseDamage * _rateMultiplier;
		}
		//如果该子弹增加伤害
		_damage *= sBullet.damageMultiplier;
		//分裂子弹数量分割伤害
		if (!root) _damage /= count;
		//构建子弹
		float final_damage = _damage;
		bullet = sBullet.provide(new SBulletDamageProv() {{
			color = _color;
			damage = final_damage;
			range = _range;
			speed = _speed;
			rate = _rate;
			lifetime = _lifetime;
			width = _width;
		}});
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
			bullet.fragBullet.fragBullet = frag.bullet(_fragDamage);
			bullet.fragBullets = 1;
			bullet.fragBullet.init();
		}
		//preset
		{
			//if (bullet.range == -1)
			if (root) bullet.range = _range;
			bullet.reloadMultiplier = _rate;
			if (bullet.lifetime == -1) bullet.lifetime = _lifetime;
			if (bullet.speed == -1) bullet.speed = _speed;
		}
		bullet.init();
		if (root) {
			bullet.maxRange = _range;
			bullet.ammoMultiplier = _ammoMultiplier;
		}
	}
}
