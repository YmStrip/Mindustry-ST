package st.addon.tooltip.provider;

import arc.Core;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.type.Item;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.Stats;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StItem;

@Provider
public class ToolTipBar extends LayerProvider {
	public StatCat cat () {
		return new StatCat("st");
	}
	public class showValueConfig {
		public boolean bool;
		public float value;
		public StatCat cat;
		public String stat;
		public Stats stats;
	}
	
	public void showValue(showValueConfig data) {
		String v;
		if (data.cat == null || data.stats == null) return;
		if (data.value != 0) {
			//v = UI.formatAmount(Float.valueOf(data.value).longValue());
			v = String.valueOf((long) data.value);
		} else if (data.bool) {
			v = Core.bundle.get("stat.st-yes");
		} else return;
		{
			Stat Left = new Stat("st-" + data.stat + "-left", data.cat);
			String Right = new Stat("st-" + data.stat + "-right").localized();
			data.stats.add(Left, v + " " + Right);
		}
	}
	
	public void showTechLevel(int level, Stats stats, StatCat Cat) {
		int v = level;
		if (v < 1) v = 1;
		if (v > 4) v = 4;
		Stat Left = new Stat("st-level-left", Cat);
		String Right = new Stat("st-level-" + v).localized();
		stats.add(Left, Right);
	}
	
	public void init(StBlock c) {
		if (c.isInit) return;
		c.isInit = true;
		var Cat = cat();
		//计算科技等级
		showTechLevel(c.techLevel, c.stats, Cat);
		//量子网络
		showValue(new showValueConfig() {{
			bool = c.quantum;
			stat = "quantum-network";
			cat = Cat;
			stats = c.stats;
		}});
		//最大放置
		showValue(new showValueConfig() {{
			value = c.maxPlace;
			stat = "max-place";
			cat = Cat;
			stats = c.stats;
		}});
		//传输速度
		showValue(new showValueConfig() {{
			value = c.itemSpeed;
			stat = "item-speed";
			cat = Cat;
			stats = c.stats;
		}});
		showValue(new showValueConfig() {{
			value = c.liquidSpeed;
			stat = "liquid-speed";
			cat = Cat;
			stats = c.stats;
		}});
		showValue(new showValueConfig() {{
			value = c.importItemSpeed;
			stat = "import-item-speed";
			cat = Cat;
			stats = c.stats;
		}});
		showValue(new showValueConfig() {{
			value = c.importLiquidSpeed;
			stat = "import-liquid-speed";
			cat = Cat;
			stats = c.stats;
		}});
		showValue(new showValueConfig() {{
			value = c.exportItemSpeed;
			stat = "export-item-speed";
			cat = Cat;
			stats = c.stats;
		}});
		showValue(new showValueConfig() {{
			value = c.exportLiquidSpeed;
			stat = "export-liquid-speed";
			cat = Cat;
			stats = c.stats;
		}});
	}
	
	public void init(StItem c) {
		if (c.isInit) return;
		c.isInit = true;
		var Cat = new StatCat("st");
		//计算科技等级
		showTechLevel(c.techLevel, c.stats, Cat);
		//伤害
		showValue(new showValueConfig() {{
			value = c.damage;
			stat = "damage";
			cat = Cat;
			stats = c.stats;
		}});
	}
}
