package wool.module.product.t2;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductKyrium extends Product {
    public ProductKyrium() {
        super("ProductKyrium");
        level = 2;
        size = 3;
        consumePower(3f);
        craftTime = 60;
        requirements = ItemStack.with(Items.copper, 150, Items.lead, 80, Items.silicon, 100, Items.titanium, 100, item.CNT, 100);
        craftEffect = Fx.smeltsmoke;
        hasPower = true;
        consumeItems(with(Items.silicon, 1, Items.thorium, 1));
        outputItem = new ItemStack(item.Kyrium, 2);
        diff();
    }
}
