package wool.entity;


import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.Stats;

import java.util.ArrayList;

public class Tooltip {
    public static StatCat cat() {
        return new StatCat("wool");
    }
    public ArrayList<Stat> statList = new ArrayList<>();
    public Stats stats;
    public StatCat cat;

    public Tooltip set(String name, float value) {
        if (value == 0) return this;
        var Left = new Stat(name, cat);
        statList.add(Left);
        stats.add(Left, ((long) value) + "[#ffffff]");
        return this;
    }

    public Tooltip set(String name, String value) {
        var Left = new Stat(name, cat);
        statList.add(Left);
        stats.add(Left, value + "[#ffffff]");
        return this;
    }

    public Tooltip setLevel(int level) {
        var levelColor = level <= 1 ? "" : level == 2 ? "[#f9960c]" : level == 3 ? "[#29f66a]" : "[#29f3f6]";
        this.set("level", levelColor + level);
        return this;
    }

    public Tooltip show(String name, boolean value) {
        return this.set(name, value ? "yes" : "no");
    }

    public Tooltip clear() {
        for (Stat s : statList) {
            stats.remove(s);
        }
        return this;
    }

    public Tooltip(Stats stats, StatCat cat) {
        this.stats = stats;
        this.cat = cat;
    }
}
