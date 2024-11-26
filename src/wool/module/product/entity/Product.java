package wool.module.product.entity;

import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.type.Category;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BuildVisibility;
import wool.entity.Tooltip;

public class Product extends GenericCrafter {
    public int sizeRegion = 512;
    public int sizeBase = 512;
    public int level = 1;

    public Product(String name) {
        super(name);
    }

    public void diff() {
        var tooltip = new Tooltip(stats, Tooltip.cat());
        tooltip.setLevel(level);
    }

    @Override
    public void load() {
        super.load();
        region.scale = size * 32f / sizeRegion;
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.scl *= size * 32f / sizeBase;
        super.drawPlanRegion(plan, list);
    }

    {
        buildVisibility = BuildVisibility.shown;
        researchCostMultiplier = 0.8f;
        buildCostMultiplier = 0.8f;

        hasPower = true;
        hasLiquids = true;
        category = Category.production;
    }
}
