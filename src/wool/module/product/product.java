package wool.module.product;


import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.ItemStack;
import wool.module.item.item;
import wool.module.product.entity.Product;
import wool.root.AppModule;

import static mindustry.type.ItemStack.with;

public class product extends AppModule {
	//t1
	public static Product ProductCnt;
	public static Product ProductAntimatter;
	public static Product ProductChrominar;
	public static Product ProductKyrium;
	public static Product ProductNexarion;
	public static Product ProductSkyforge;
	public static Product ProductSuperconductor;
	public static Product ProductElementMetal;
	public static Product ProductElementWood;
	public static Product ProductElementWater;
	public static Product ProductElementFire;
	public static Product ProductElementEarth;
	public static Product ProductElementLight;
	public static Product ProductElementDark;

	//
	public static void init() {
		ProductCnt = new Product("ProductCnt") {{
			size = 3;
			consumePower(2f);
			craftTime = 60;
			requirements = ItemStack.with(Items.copper, 100, Items.lead, 80);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeItems(with(Items.coal, 1));
			outputItem = new ItemStack(item.CNT, 2);
		}};
		ProductAntimatter = new Product("ProductAntimatter"){{
			size = 3;
			consumePower(20f);
			craftTime = 60;
			requirements = ItemStack.with(Items.copper, 150, Items.lead, 80, Items.titanium, 100, item.CNT, 50);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeItems(with(Items.copper, 1, Items.coal, 1));
			outputItem = new ItemStack(item.Antimatter, 2);
		}};
		ProductChrominar = new Product("ProductChrominar"){{
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
		}};
		ProductKyrium = new Product("ProductKyrium"){{
			level = 2;
			size = 3;
			consumePower(3f);
			craftTime = 60;
			requirements = ItemStack.with(Items.copper, 150, Items.lead, 80, Items.silicon, 100, Items.titanium, 100, item.CNT, 100, Items.plastanium, 75, Items.surgeAlloy, 50);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeLiquid(Liquids.cryofluid, 1f);
			consumeItems(with(Items.silicon, 1, Items.thorium, 1));
			outputItem = new ItemStack(item.Kyrium, 2);
		}};
		ProductNexarion = new Product("ProductNexarion"){{
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
		}};
		ProductSkyforge = new Product("ProductSkyforge"){{
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
		}};
		ProductSuperconductor = new Product("ProductSuperconductor"){{
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
		}};
		ProductElementMetal = new Product("ProductElementMetal"){{
			level = 2;
			size = 4;
			consumePower(20f);
			craftTime = 30;
			requirements = ItemStack.with(item.Chrominar, 100, item.Kyrium, 100, item.Skyforge, 100, item.Superconductor, 200, item.CNT, 400);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeItems(with(Items.surgeAlloy, 2));
			outputItem = new ItemStack(item.ElementMetal, 2);
		}};
		ProductElementWood = new Product("ProductElementWood"){{
			level = 2;
			size = 4;
			consumePower(20f);
			craftTime = 30;
			requirements = ItemStack.with(item.Chrominar, 100, item.Kyrium, 100, item.Skyforge, 100, item.Superconductor, 200, item.CNT, 400);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeItems(with(Items.plastanium, 2));
			outputItem = new ItemStack(item.ElementWood, 2);
		}};
		ProductElementWater = new Product("ProductElementWater"){{
			level = 2;
			size = 4;
			consumePower(20f);
			craftTime = 30;
			requirements = ItemStack.with(item.Chrominar, 100, item.Kyrium, 100, item.Skyforge, 100, item.Superconductor, 200, item.CNT, 400);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeItems(with(Items.silicon, 2));
			outputItem = new ItemStack(item.ElementWater, 2);
		}};
		ProductElementFire = new Product("ProductElementFire"){{
			level = 2;
			size = 4;
			consumePower(20f);
			craftTime = 30;
			requirements = ItemStack.with(item.Chrominar, 100, item.Kyrium, 100, item.Skyforge, 100, item.Superconductor, 200, item.CNT, 400);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeItems(with(Items.phaseFabric, 2));
			outputItem = new ItemStack(item.ElementFire, 2);
		}};
		ProductElementEarth = new Product("ProductElementEarth"){{
			level = 2;
			size = 4;
			consumePower(20f);
			craftTime = 30;
			requirements = ItemStack.with(item.Chrominar, 100, item.Kyrium, 100, item.Skyforge, 100, item.Superconductor, 200, item.CNT, 400);
			craftEffect = Fx.smeltsmoke;
			hasPower = true;
			consumeItems(with(Items.metaglass, 2));
			outputItem = new ItemStack(item.ElementEarth, 2);
		}};
		ProductElementLight = new Product("ProductElementLight"){{
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
		}};
		ProductElementDark = new Product("ProductElementDark"){{
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
		}};
	}

	public static void deploy() {

	}
}
