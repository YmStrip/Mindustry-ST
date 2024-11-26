package wool.module.product.t1;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductCNT extends Product {
    public ProductCNT() {
        super("ProductCnt");
        size = 3;
        consumePower(3f);
        craftTime = 30;
        requirements = ItemStack.with(Items.copper, 100, Items.lead, 80);
        craftEffect = Fx.smeltsmoke;
        hasPower = true;
        consumeItems(with(Items.coal, 2));
        outputItem = new ItemStack(item.CNT, 2);
        diff();
    }
}
