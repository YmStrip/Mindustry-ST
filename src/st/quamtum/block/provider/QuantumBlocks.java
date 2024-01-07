package st.quamtum.block.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.meta.BuildVisibility;
import st.addon.content.provider.TooltipProvider;
import st.provider.place.SPlace;
import st.provider.place.provider.PlaceProvider;
import st.provider.ui.UI;
import st.provider.ui.provider.views.QuantumNetworkDashUI;
import st.quamtum.block.entity.QuantumStore;
import st.quamtum.block.mentity.*;
import st.quamtum.block.entity.QuantumBlock;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;
import st.quamtum.provider.Quantum;
import st.quamtum.provider.provider.QuantumMapTile;
import st.quamtum.provider.provider.QuantumWorld;

@Provider
public class QuantumBlocks extends LayerProvider {
	@Require(cls = ItemProvider.class)
	@Import(cls = SContent.class)
	ItemProvider itemProviders;
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	@Import(cls = SPlace.class)
	@Require(cls = PlaceProvider.class)
	PlaceProvider placeMap;
	@Import(cls = Quantum.class)
	@Require(cls = QuantumMapTile.class)
	QuantumMapTile quantumMap;
	@Import(cls = Quantum.class)
	@Require(cls = QuantumWorld.class)
	QuantumWorld quantumWorld;
	@Import(cls = UI.class)
	@Require(cls = QuantumNetworkDashUI.class)
	QuantumNetworkDashUI quantumNetworkDashUI;
	
	
	public QuantumStore store;
	
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
		techLevel = 3;
	}};
	public QuantumBlock 高级物品输入接口 = new ItemInput("高级物品输入接口") {{
		buildCost = 0.4f;
		itemSpeed = 2400;
		techLevel = 4;
	}};
	public QuantumBlock 物品输出接口 = new ItemOutput("物品输出接口") {{
		buildCost = 0.2f;
		itemSpeed = 10;
		techLevel = 3;
	}};
	public QuantumBlock 高级物品输出接口 = new ItemOutput("高级物品输出接口") {{
		buildCost = 0.4f;
		itemSpeed = 2400;
		techLevel = 4;
	}};
	//liquid
	public QuantumBlock 流体输入接口 = new LiquidInput("流体输入接口") {{
		buildCost = 0.2f;
		liquidSpeed = 60;
		techLevel = 3;
	}};
	public QuantumBlock 高级流体输入接口 = new LiquidInput("高级流体输入接口") {{
		buildCost = 0.4f;
		liquidSpeed = 24000;
		techLevel = 4;
	}};
	public QuantumBlock 流体输出接口 = new LiquidOutput("流体输出接口") {{
		liquidSpeed = 60;
		techLevel = 3;
		buildCost = 0.2f;
	}};
	public QuantumBlock 高级流体输出接口 = new LiquidOutput("高级流体输出接口") {{
		liquidSpeed = 2400;
		techLevel = 4;
		buildCost = 0.4f;
	}};
	public QuantumBlock 流体中心 = new LiquidCenter("流体中心") {{
		techLevel = 3;
		exportLiquidSpeed = 24;
		liquidCapacity = 8000;
		buildCost = 1f;
	}};
	public QuantumBlock 量子驱动器 = new QuantumDrive("量子驱动器") {{
		health = 12000;
		buildCost = 3;
		exportItemSpeed = 1400;
		exportLiquidSpeed = 1400;
		techLevel = 3;
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
		techLevel = 3;
	}};
	public QuantumBlock 时间膨胀量子驱动器 = new QuantumDrive("时间膨胀量子驱动器") {{
		health = 100000;
		buildCost = 10;
		exportItemSpeed = 1400;
		exportLiquidSpeed = 1400;
		itemCapacity = 1000000;
		liquidCapacity = 1000000;
		techLevel = 4;
	}};
	public QuantumBlock 量子接口 = new QuantumInterface("量子接口") {{
		consumePower(1F);
		techLevel = 3;
		importItemSpeed = 1;
		importLiquidSpeed = 1;
	}};
	public QuantumBlock 高级量子接口 = new QuantumInterface("高级量子接口") {{
		consumePower(500F);
		techLevel = 4;
		importItemSpeed = 2400;
		importLiquidSpeed = 2400;
	}};
	
	@Override
	public void run() {
		store = new QuantumStore();
		store.items = items;
		store.tooltip = tooltip;
		store.placeMap = placeMap;
		store.quantumMap = quantumMap;
		store.quantumWorld = quantumWorld;
		store.quantumNetworkDashUI = quantumNetworkDashUI;
		this
			.inject(物品输入接口, ItemStack.with(items.金元素, 1, items.土元素, 1))
			.inject(高级流体输入接口, ItemStack.with(items.金元素, 15, items.土元素, 15))
			.inject(物品输出接口, ItemStack.with(items.金元素, 1, items.土元素, 1))
			.inject(高级物品输出接口, ItemStack.with(items.金元素, 15, items.土元素, 15))
			.inject(流体输入接口, ItemStack.with(items.金元素, 1, items.土元素, 1))
			.inject(高级流体输入接口, ItemStack.with(items.金元素, 15, items.土元素, 15))
			.inject(流体输出接口, ItemStack.with(items.金元素, 1, items.土元素, 1))
			.inject(高级流体输出接口, ItemStack.with(items.金元素, 15, items.土元素, 15))
			.inject(流体中心, ItemStack.with(items.金元素, 15, items.土元素, 15, items.水元素, 15))
			.inject(量子驱动器, ItemStack.with(items.金元素, 25, items.土元素, 25, items.水元素, 25))
			.inject(超密集量子驱动器, ItemStack.with(items.金元素, 125, items.土元素, 50, items.水元素, 45, items.光元素, 5))
			.inject(时间膨胀量子驱动器, ItemStack.with(items.金元素, 300, items.土元素, 250, items.水元素, 500, items.光元素, 50))
			.inject(量子接口, ItemStack.with(items.金元素, 5, items.土元素, 5, items.水元素, 5))
			.inject(高级量子接口, ItemStack.with(items.金元素, 250, items.土元素, 200, items.水元素, 300, items.光元素, 50))
		;
	}
}
