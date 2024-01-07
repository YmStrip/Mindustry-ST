package st.ht.quantum.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import mindustry.world.meta.BuildVisibility;
import st.addon.content.provider.TooltipProvider;
import st.provider.place.SPlace;
import st.provider.place.provider.PlaceProvider;
import st.ht.quantum.block.*;
import st.ht.quantum.block.QuantumBlock;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.ht.quantum.Quantum;

@Provider
public class QuantumBlocks extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	@Import(cls = SPlace.class)
	@Require(cls = PlaceProvider.class)
	PlaceProvider placeMap;
	@Require(cls = QuantumMap.class)
	QuantumMap quantumMap;
	@Require(cls = QuantumUI.class)
	QuantumUI quantumUI;
	
	
	public st.ht.quantum.block.QuantumStore store;
	
	public QuantumBlocks inject(QuantumBlock b, ItemStack[] stacks) {
		b.buildVisibility = BuildVisibility.shown;
		b.requirements = stacks;
		b.store(store);
		b.tooltip();
		return this;
	}
	
	//item
	public QuantumBlock 物品输入接口 = new ItemInput("物品输入接口") {{
		buildCost = 0.2f;
		itemSpeed = 10;
		techLevel = 2;
	}};
	public QuantumBlock 高级物品输入接口 = new ItemInput("高级物品输入接口") {{
		buildCost = 0.4f;
		itemSpeed = 2400;
		techLevel = 2;
	}};
	public QuantumBlock 物品输出接口 = new ItemOutput("物品输出接口") {{
		buildCost = 0.2f;
		itemSpeed = 10;
		techLevel = 2;
	}};
	public QuantumBlock 高级物品输出接口 = new ItemOutput("高级物品输出接口") {{
		buildCost = 0.4f;
		itemSpeed = 2400;
		techLevel = 2;
	}};
	//liquid
	public QuantumBlock 流体输入接口 = new LiquidInput("流体输入接口") {{
		buildCost = 0.2f;
		liquidSpeed = 60;
		techLevel = 2;
	}};
	public QuantumBlock 高级流体输入接口 = new LiquidInput("高级流体输入接口") {{
		buildCost = 0.4f;
		liquidSpeed = 24000;
		techLevel = 2;
	}};
	public QuantumBlock 流体输出接口 = new LiquidOutput("流体输出接口") {{
		liquidSpeed = 60;
		techLevel = 2;
		buildCost = 0.2f;
	}};
	public QuantumBlock 高级流体输出接口 = new LiquidOutput("高级流体输出接口") {{
		liquidSpeed = 2400;
		techLevel = 2;
		buildCost = 0.4f;
	}};
	public QuantumBlock 流体中心 = new LiquidCenter("流体中心") {{
		techLevel = 2;
		exportLiquidSpeed = 24;
		liquidCapacity = 8000;
		buildCost = 1f;
	}};
	
	public QuantumBlock 量子驱动器 = new QuantumDrive("量子驱动器") {{
		health = 12000;
		buildCost = 3;
		exportItemSpeed = 1400;
		exportLiquidSpeed = 1400;
		techLevel = 2;
		itemCapacity = 1200;
		liquidCapacity = 1200;
	}};
	public QuantumBlock 超密集量子驱动器 = new QuantumDrive("超密集量子驱动器") {{
		health = 50000;
		buildCost = 5;
		exportItemSpeed = 1400;
		exportLiquidSpeed = 1400;
		itemCapacity = 128000;
		liquidCapacity = 128000;
		techLevel = 2;
	}};
	public QuantumBlock 时间膨胀量子驱动器 = new QuantumDrive("时间膨胀量子驱动器") {{
		health = 100000;
		buildCost = 10;
		exportItemSpeed = 1400;
		exportLiquidSpeed = 1400;
		itemCapacity = 1000000;
		liquidCapacity = 1000000;
		techLevel = 3;
	}};
	public QuantumBlock 紧急量子接口 = new QuantumInterface("紧急量子接口") {{
		consumePower(1F);
		techLevel = 2;
		importItemSpeed = 15;
		importLiquidSpeed = 15;
	}};
	public QuantumBlock 量子接口 = new QuantumInterface("量子接口") {{
		consumePower(1F);
		techLevel = 2;
		importItemSpeed = 5;
		importLiquidSpeed = 5;
	}};
	public QuantumBlock 高级量子接口 = new QuantumInterface("高级量子接口") {{
		consumePower(500F);
		techLevel = 3;
		importItemSpeed = 2400;
		importLiquidSpeed = 2400;
	}};
	
	@Override
	public void run() {
		store = new st.ht.quantum.block.QuantumStore();
		store.items = items;
		store.tooltip = tooltip;
		store.placeMap = placeMap;
		store.quantumMap = quantumMap;
		store.quantumUI = quantumUI;
		this
			.inject(物品输入接口, ItemStack.with(items.超导体, 1, items.铬纳尔, 2, items.反重力陶瓷, 1))
			.inject(高级物品输入接口, ItemStack.with(items.超导体, 10, items.铬纳尔, 20, items.反重力陶瓷, 10))
			.inject(物品输出接口, ItemStack.with(items.超导体, 1, items.铬纳尔, 2, items.反重力陶瓷, 1))
			.inject(高级物品输出接口, ItemStack.with(items.超导体, 10, items.铬纳尔, 20, items.反重力陶瓷, 10))
			.inject(流体输入接口, ItemStack.with(items.超导体, 1, items.铬纳尔, 2, items.反重力陶瓷, 1))
			.inject(高级流体输入接口, ItemStack.with(items.超导体, 10, items.铬纳尔, 20, items.反重力陶瓷, 10))
			.inject(流体输出接口, ItemStack.with(items.超导体, 1, items.铬纳尔, 2, items.反重力陶瓷, 1))
			.inject(高级流体输出接口, ItemStack.with(items.超导体, 10, items.铬纳尔, 20, items.反重力陶瓷, 10))
			.inject(流体中心, ItemStack.with(items.超导体, 25, items.铬纳尔, 30, items.反重力陶瓷, 15, items.晶金, 25))
			.inject(量子驱动器, ItemStack.with(items.超导体, 75, items.铬纳尔, 100, items.反重力陶瓷, 100, items.晶金, 150))
			.inject(超密集量子驱动器, ItemStack.with(items.超导体, 250, items.铬纳尔, 500, items.反重力陶瓷, 450, items.晶金, 350))
			.inject(时间膨胀量子驱动器, ItemStack.with(items.超导体, 1200, items.铬纳尔, 1000, items.反重力陶瓷, 1500, items.晶金, 1500, items.光元素, 50))
			.inject(紧急量子接口, ItemStack.with(Items.beryllium, 50, Items.silicon, 50, Items.graphite, 50))
			.inject(量子接口, ItemStack.with(items.超导体, 25, items.晶金, 25))
			.inject(高级量子接口, ItemStack.with(items.超导体, 500, items.晶金, 750, items.光元素, 25))
		;
	}
}
