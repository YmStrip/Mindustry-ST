package wool.module.product.entity;

import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.type.Category;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BuildVisibility;
import wool.entity.Tooltip;
import wool.module.render.entity.Render;

public class Product extends GenericCrafter {
	public Product(String name) {
		super(name);
	}
	public int sizeRegion = 512;
	public int sizeBase = 512;
	public int level = 1;
	public Render renderHeat = new Render() {{
		cycle = 800;
		cycleEnable = true;
		transition = 1500;
	}};
	public void diff() {
		var tooltip = new Tooltip(stats, Tooltip.cat());
		tooltip.setLevel(level);
	}
	@Override
	public void load() {
		super.load();
		region.scale = size * 32f / sizeRegion;
		renderHeat.region(name + "-heat");
		renderHeat.scaleBlock(size, sizeBase);
	}

	@Override
	public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
		Draw.scl *= size * 32f / sizeBase;
		super.drawPlanRegion(plan, list);
		Draw.scl /= size * 32f / sizeBase;
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
			renderHeat.transitionIter(renderHeat.region.found() && this.efficiency > 0f);
			renderHeat.cycleIter();
		}

		@Override
		public void draw() {
			super.draw();
			renderHeat.render(x, y);
		}
	}
}
