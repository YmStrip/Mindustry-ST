package wool.module.item.entity;



import wool.entity.Color;
import wool.entity.Tooltip;


public class Item extends mindustry.type.Item {
    public void diff() {
        var tooltip = new Tooltip(stats, Tooltip.cat());
        tooltip.setLevel(level);
        tooltip.set("damage", damage);
    }

    public int level = 0;
    public float damage = 5;

    public Item Color(String color) {
        this.color = new Color(color).to();
        return this;
    }

    public Item Color(Color color) {
        this.color = new Color(color).to();
        return this;
    }

    public Item(String name) {
        super(name);
    }

    {
        alwaysUnlocked = false;
    }
}