package wool.module.product.t3;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductElementWood extends Product {
    public ProductElementWood() {
        super("ProductElementWood");
        level = 2;
        size = 4;
        consumePower(20f);
        craftTime = 30;
        requirements = ItemStack.with(item.Chrominar, 100, item.Kyrium, 100, item.Skyforge, 100, item.Superconductor, 200, item.CNT, 400);
        craftEffect = Fx.smeltsmoke;
        hasPower = true;
        consumeItems(with(Items.plastanium, 2));
        outputItem = new ItemStack(item.ElementWood, 2);
        diff();
    }
}
