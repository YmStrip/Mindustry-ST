package st.addon.block_io.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import st.addon.entity.Entity;
import st.addon.entity.entity.StBlock;
import st.addon.entity.provider.EntityStoreProvider;
import st.addon.item.ITEM;
import st.addon.item.provider.ItemProvider;

@Provider
public class BlockIOProviders extends LayerProvider {
	@Require(cls = ItemProvider.class)
	@Import(cls = ITEM.class)
	ItemProvider itemProviders;
	@Require(cls = EntityStoreProvider.class)
	@Import(cls = Entity.class)
	EntityStoreProvider store;
	public StBlock 量子驱动器;
	public StBlock 超密集量子驱动器;
	public StBlock 时间膨胀量子驱动器;
	public StBlock 量子接口;
	public StBlock 高级量子接口;
	public StBlock 物品输入接口;
	public StBlock 高级物品输入接口;
	public StBlock 物品输出接口;
	public StBlock 高级物品输出接口;
	//liq
	public StBlock 流体输入接口;
	public StBlock 高级流体输入接口;
	public StBlock 流体输出接口;
	public StBlock 高级流体输出接口;
	public StBlock 流体中心;
	
	@Override
	public void run() {
		物品输入接口 = new ItemInput(store.store, "物品输入接口") {{
			requirements(Category.distribution, ItemStack.with(
				itemProviders.金元素, 1,
				itemProviders.土元素, 1
			));
			buildCost = 0.2f;
			itemSpeed = 10;
			techLevel = 1;
		}};
		高级物品输入接口 = new ItemInput(store.store, "高级物品输入接口") {{
			requirements(Category.distribution, ItemStack.with(
				itemProviders.金元素,5,
				itemProviders.土元素, 5
			));
			buildCost = 0.4f;
			itemSpeed = 2400;
			techLevel = 1;
		}};
		物品输出接口 = new ItemOutput(store.store, "物品输出接口") {{
			requirements(Category.distribution, ItemStack.with(
				itemProviders.金元素, 1,
				itemProviders.土元素, 1
			));
			buildCost = 0.2f;
			itemSpeed = 10;
			techLevel = 1;
		}};
		高级物品输出接口 = new ItemOutput(store.store, "高级物品输出接口") {{
			requirements(Category.distribution, ItemStack.with(
				itemProviders.金元素, 5,
				itemProviders.土元素, 5
			));
			buildCost = 0.4f;
			itemSpeed = 2400;
			techLevel = 2;
		}};
		//LIQUID
		流体输入接口 = new LiquidInput(store.store, "流体输入接口") {{
			requirements(Category.liquid, ItemStack.with(
				itemProviders.金元素, 1,
				itemProviders.土元素, 1
			));
			buildCost = 0.2f;
			liquidSpeed = 60;
			techLevel = 1;
		}};
		高级流体输入接口 = new LiquidInput(store.store, "高级流体输入接口") {{
			requirements(Category.liquid, ItemStack.with(
				itemProviders.金元素, 5,
				itemProviders.土元素, 5
			));
			buildCost = 0.4f;
			liquidSpeed = 24000;
			techLevel = 2;
		}};
		流体输出接口 = new LiquidOutput(store.store, "流体输出接口") {{
			requirements(Category.liquid, ItemStack.with(
				itemProviders.金元素, 1,
				itemProviders.土元素, 1
			));
			liquidSpeed = 60;
			techLevel = 1;
			buildCost = 0.2f;
		}};
		高级流体输出接口 = new LiquidOutput(store.store, "高级流体输出接口") {{
			requirements(Category.liquid, ItemStack.with(
				itemProviders.金元素, 5,
				itemProviders.土元素, 5
			));
			liquidSpeed = 2400;
			techLevel = 2;
			buildCost = 0.4f;
		}};
		流体中心 = new LiquidCenter(store.store, "流体中心") {{
			requirements(Category.liquid, ItemStack.with(
				itemProviders.金元素, 35,
				itemProviders.土元素, 30
			));
			techLevel = 1;
			exportLiquidSpeed = 24;
			liquidCapacity = 8000;
			buildCost = 1f;
		}};
		//PLANET
		量子驱动器 = new QuantumDrive(store.store, "量子驱动器") {{
			health = 12000;
			buildCost = 3;
			requirements(Category.effect, ItemStack.with(
				itemProviders.金元素, 30,
				itemProviders.土元素, 120,
				itemProviders.木元素, 60
			));
			exportItemSpeed = 1400;
			exportLiquidSpeed = 1400;
			techLevel = 3;
			itemCapacity = 1200;
			liquidCapacity = 1200;
		}};
		超密集量子驱动器 = new QuantumDrive(store.store, "超密集量子驱动器") {{
			health = 50000;
			buildCost = 5;
			requirements(Category.effect, ItemStack.with(
				itemProviders.金元素, 100,
				itemProviders.土元素, 200,
				itemProviders.木元素, 120,
				itemProviders.光元素, 25,
				itemProviders.暗元素, 25
			));
			exportItemSpeed = 1400;
			exportLiquidSpeed = 1400;
			itemCapacity = 128000;
			liquidCapacity = 128000;
			techLevel = 3;
		}};
		时间膨胀量子驱动器 = new QuantumDrive(store.store, "时间膨胀量子驱动器") {{
			health = 100000;
			buildCost = 10;
			requirements(Category.effect, ItemStack.with(
				itemProviders.金元素, 500,
				itemProviders.土元素, 1000,
				itemProviders.木元素, 250,
				itemProviders.光元素, 150,
				itemProviders.暗元素, 150
			));
			exportItemSpeed = 1400;
			exportLiquidSpeed = 1400;
			itemCapacity = 1000000;
			liquidCapacity = 1000000;
			techLevel = 4;
		}};
		量子接口 = new QuantumInterface(store.store, "量子接口") {{
			requirements(Category.effect, ItemStack.with(
				itemProviders.暗元素, 10,
				itemProviders.光元素, 10
			));
			consumePower(1F);
			techLevel = 3;
			importItemSpeed = 1;
			importLiquidSpeed = 1;
		}};
		高级量子接口 = new QuantumInterface(store.store, "高级量子接口") {{
			requirements(Category.effect, ItemStack.with(
				itemProviders.暗元素, 100,
				itemProviders.光元素, 100
			));
			consumePower(500F);
			techLevel = 4;
			importItemSpeed = 2400;
			importLiquidSpeed = 2400;
		}};
	}
}
