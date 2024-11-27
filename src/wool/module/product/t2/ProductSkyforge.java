package wool.module.product.t2;

import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;

import static mindustry.type.ItemStack.with;

public class ProductSkyforge extends Product {
    public ProductSkyforge() {
        super("ProductSkyforge");
        level = 2;
        size = 3;
        consumePower(15f);
        craftTime = 60;
        requirements = ItemStack.with(Items.titanium, 100, item.CNT, 150, Items.silicon, 70, Items.surgeAlloy, 50);
        craftEffect = Fx.smeltsmoke;
        hasPower = true;
        consumeLiquid(Liquids.cryofluid, 1f);
        consumeItems(with(Items.thorium, 1, Items.scrap, 1));
        outputItem = new ItemStack(item.Chrominar, 2);
        diff();
    }
}
