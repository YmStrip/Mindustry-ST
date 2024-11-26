package wool.module.turret.entity;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BuildVisibility;
import wool.entity.Tooltip;

public class Turret extends ItemTurret {
    public int level = 1;
    public int sizeRegion = 512;
    public int sizeBase = 512;
    public int sizePreview = 512;
    public int sizeTop = 512;
    public int sizeHeat = 512;
    public String base = "default";

    public Turret(String name) {
        super(name);
    }

    public void diff() {
        var tooltip = new Tooltip(stats, Tooltip.cat());
        tooltip.setLevel(level);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.scl *= size * 32f / sizeBase;
        super.drawPlanRegion(plan, list);
    }

    @Override
    public void load() {
        super.load();
        var drawer = (DrawTurret) this.drawer;
        drawer.base = Core.atlas.find("wool-base-" + base);
        //block * 32 = scale * size
        //scale = block * 32 / size
        drawer.base.scale = size * 32f / sizeBase;
        drawer.preview.scale = size * 32f / sizePreview;
        drawer.top.scale = size * 32f / sizeTop;
        drawer.heat.scale = size * 32f / sizeHeat;
        region.scale = size * 32f / sizeRegion;
    }
    {
        buildVisibility = BuildVisibility.shown;
        researchCostMultiplier = 0.8f;
        buildCostMultiplier = 0.8f;
        reload = 60;
        targetAir = true;
        targetGround = true;

        chargeSound = Sounds.lasercharge;
        hasPower = true;
        hasLiquids = true;
        category = Category.turret;
    }
}