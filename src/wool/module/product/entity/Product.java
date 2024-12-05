package wool.module.product.entity;

import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.type.Category;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BuildVisibility;
import wool.entity.Tooltip;
import wool.module.product.render.RenderProduct;

public class Product extends GenericCrafter {
	public Product(String name) {
		super(name);
	}
	public int sizeRegion = 512;
	public int sizeBase = 512;
	public int level = 1;
	public RenderProduct render = new RenderProduct();
	public Tooltip tooltip;
	public void diff() {
		if (tooltip != null) tooltip.clear();
		tooltip = new Tooltip(stats, Tooltip.cat());
		tooltip.setLevel(level);
		tooltip.set("debug",render.toString());
	}
	@Override
	public void load() {
		super.load();
		region.scale = size * 32f / sizeRegion;
		render.load(this);
		diff();
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
		public RenderProduct render = Product.this.render.copy();
		public ProductBuild() {
			//System.out.println("build:");
			//System.out.println(render.toString());
		}
		@Override
		public void updateTile() {
			super.updateTile();
			render.tick(this);
		}

		@Override
		public void draw() {
			super.draw();
			render.render(x, y, rotation);
		}
	}
}
