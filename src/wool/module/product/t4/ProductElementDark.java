package wool.module.product.t4;

import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductElementDark extends Product {
    public ProductElementDark() {
        super("ProductElementDark");
        level = 2;
        size = 5;
        consumePower(600f);
        craftTime = 600;
        requirements = ItemStack.with(
                item.Superconductor, 500,
                item.Skyforge, 300,
                item.ElementMetal, 50,
                item.ElementWood, 50,
                item.ElementFire, 50,
                item.ElementWater, 50,
                item.ElementEarth, 50
        );
        craftEffect = Fx.smeltsmoke;
        hasPower = true;
        consumeLiquid(Liquids.nitrogen, 6);
        consumeItems(with(
                item.ElementLight, 1
        ));
        outputItem = new ItemStack(item.ElementDark, 1);
        diff();
    }
}
