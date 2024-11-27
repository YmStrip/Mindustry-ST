package wool.module.product.t2;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductNexarion extends Product {
    public ProductNexarion() {
        super("ProductNexarion");
        level = 2;
        size = 3;
        consumePower(5f);
        craftTime = 60;
        requirements = ItemStack.with(Items.copper, 100, Items.lead, 150, Items.silicon, 125, Items.thorium, 100, item.CNT, 100);
        craftEffect = Fx.smeltsmoke;
        hasPower = true;
        consumeLiquid(Liquids.cryofluid, 1f);
        consumeItems(with(Items.thorium, 3));
        outputItem = new ItemStack(item.Kyrium, 1);
        diff();
    }
}
