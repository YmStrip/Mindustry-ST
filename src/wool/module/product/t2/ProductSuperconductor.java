package wool.module.product.t2;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductSuperconductor extends Product {
    public ProductSuperconductor() {
        super("ProductSuperconductor");
        level = 2;
        size = 3;
        consumePower(5f);
        craftTime = 60;
        requirements = ItemStack.with(Items.titanium, 80, item.CNT, 120, Items.silicon, 90, Items.plastanium,50);
        craftEffect = Fx.smeltsmoke;
        hasPower = true;
        consumeLiquid(Liquids.cryofluid, 1f);
        consumeItems(with(Items.thorium, 1, Items.sand, 1));
        outputItem = new ItemStack(item.Superconductor, 2);
        diff();
    }
}
