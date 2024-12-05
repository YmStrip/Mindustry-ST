package wool.module.product.render;

import wool.module.product.entity.Product;
import wool.module.render.entity.RenderEntity;


public class RenderProduct extends RenderEntity {
	@Override
	public void load(Object o) {
		if (o instanceof Product product) {
			//
			renderLight.region(product.name + "-light");
			renderLight.setScaleBlock(product.size, sizeRegion);
			//
			renderAnimate.setScaleBlock(product.size, sizeRegion);
			//
			product.region.scale = product.size * 32f / sizeRegion;
		}
	}
	@Override
	public void tick(Object o) {
		if (o instanceof Product.ProductBuild build) {
			renderLight.iter(build.efficiency > 0f);
			renderAnimate.iter(build.efficiency > 0f);
		}
	}
	@Override
	public RenderProduct copy() {
		var copy = new RenderProduct();
		copy(copy);
		return copy;
	}
}
