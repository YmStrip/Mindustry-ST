package wool.module.product.entity;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Mat;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.type.Category;
import mindustry.world.Tile;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BuildVisibility;
import wool.entity.Tooltip;

public class Product extends GenericCrafter {
    public int sizeRegion = 512;
    public int sizeBase = 512;
    public int level = 1;
    //ms
    public float heatTime = 1500;
    public float heatCycle = 800;
    public arc.graphics.g2d.TextureRegion regionHeat;

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
        regionHeat = Core.atlas.find(name + "-heat");
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
        category = Category.crafting;
    }

    public class ProductBuild extends GenericCrafter.GenericCrafterBuild {
        public float heatTimeIter = 0;
        public float heatCycleIter = 0;

        @Override
        public void updateTile() {
            super.updateTile();
            //60*dt = 1000
            if (Product.this.regionHeat.found() && this.efficiency > 0f) {
                heatTimeIter = Math.min(heatTimeIter + 1000 / 60f, heatTime);
            } else {
                heatTimeIter = Math.max(heatTimeIter - 1000 / 60f, 0);
            }
            //sin(x+w) = sin(x)
            //w = 2pi
            //f(z) = 60*dw = 2pi while z = 1000
            //f(z*s) = s * f(z) = 60 * dw / s = 2pi
            //dw = 2pi * s / 60
            //1000 * s = r
            //s = r / 1000
            heatCycleIter += (float) (Math.PI * 2f * (heatCycle / 1000) / 60);
        }

        @Override
        public void draw() {
            super.draw();
            Draw.alpha((float) ((.25f * Math.sin(heatCycleIter) + .75) * (heatTimeIter / heatTime)));
            Draw.scl *= size * 32f / sizeBase;
            Draw.rect(Product.this.regionHeat, x, y);
            Draw.z(50.5F);
            Draw.scl /= size * 32f / sizeBase;
            Draw.alpha(1);
        }
    }
}
