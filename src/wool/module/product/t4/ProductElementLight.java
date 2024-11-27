package wool.module.product.t4;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductElementLight extends Product {
    public ProductElementLight() {
        super("ProductElementLight");
        level = 2;
        size = 5;
        consumePower(128f);
        craftTime = 240;
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
        consumeItems(with(
                item.ElementMetal, 32,
                item.ElementWood, 32,
                item.ElementWater, 32,
                item.ElementFire, 32,
                item.ElementEarth, 32
        ));
        outputItem = new ItemStack(item.ElementLight, 4);
        diff();
    }
}
