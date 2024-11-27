package wool.module.product.t2;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductChrominar extends Product {
    public ProductChrominar() {
        super("ProductChrominar");
        level = 2;
        size = 3;
        consumePower(3f);
        craftTime = 60;
        requirements = ItemStack.with(Items.titanium, 100, item.CNT, 50, Items.silicon, 70);
        craftEffect = Fx.smeltsmoke;
        hasPower = true;
        consumeLiquid(Liquids.cryofluid, 1f);
        consumeItems(with(Items.titanium, 1, Items.sand, 1));
        outputItem = new ItemStack(item.Chrominar, 2);
        diff();
    }
}
