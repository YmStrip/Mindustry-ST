package st.addon.content.entity;

import arc.Core;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.Stats;

public class STooltipBuilder {
	public Stats stats;
	public StatCat cat;
	
	public STooltipBuilder(Stats stats, StatCat cat) {
		this.stats = stats;
		this.cat = cat;
	}
	public STooltipBuilder maxPlace(int count) {
		return showValue("max-place",count);
	}
	public STooltipBuilder techLevel(int level) {
		int v = level;
		if (v < 1) v = 1;
		if (v > 4) v = 4;
		Stat Left = new Stat("st-level-left", cat);
		String Right = new Stat("st-level-" + v).localized();
		stats.add(Left, Right);
		return this;
	}
	
	public STooltipBuilder showValue(String name, float value) {
		if (value==0) return this;
		var Left = new Stat("st-" + name + "-left", cat);
		var Right = new Stat("st-" + name + "-right").localized();
		stats.add(Left, ((long) value) + " " + Right);
		return this;
	}
	
	public STooltipBuilder showValue(String name, boolean value) {
		var Left = new Stat("st-" + name + "-left", cat);
		var Right = new Stat("st-" + name + "-right").localized();
		var v = value ? Core.bundle.get("stat.st-yes") : Core.bundle.get("stat.st-not");
		stats.add(Left, v + " " + Right);
		return this;
	}
}
