package wool.module.product.t1;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductAntimatter extends Product {
    public ProductAntimatter() {
        super("ProductAntimatter");
        size = 3;
        consumePower(20f);
        craftTime = 60;
        requirements = ItemStack.with(Items.copper, 150, Items.lead, 80, Items.titanium, 100, item.CNT, 50);
        craftEffect = Fx.smeltsmoke;
        hasPower = true;
        consumeItems(with(Items.copper, 1, Items.coal, 1));
        outputItem = new ItemStack(item.Antimatter, 2);
        diff();
    }
}
