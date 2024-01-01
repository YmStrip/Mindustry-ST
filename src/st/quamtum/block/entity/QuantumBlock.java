package st.quamtum.block.entity;

import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;

public class QuantumBlock extends Block {
	public QuantumBlock(String name) {
		super(name);
		researchCostMultiplier = 50f;
	}
	
	public QuantumBlock store(QuantumStore store) {
		this.store = store;
		return this;
	}
	
	@Override
	public boolean outputsItems() {
		return false;
	}
	
	public QuantumStore store;
	public boolean isInit = false;
	//科技等级
	public int techLevel = 0;
	//物品传输速度
	public int itemSpeed = 0;
	//流体船速速度
	public int liquidSpeed = 0;
	//导入/导出速度
	public int importItemSpeed = 0;
	public int importLiquidSpeed = 0;
	public int exportItemSpeed = 0;
	public int exportLiquidSpeed = 0;
	public boolean liquidsBlock;
	public boolean itemsBlock;
	public int maxPlace = 0;
	public boolean quantum = false;
	public String placeClass = "";
	
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		return store.placeMap.canPlaceOn(this, tile, team);
	}
	
	public static boolean isSt(Block block) {
		return block instanceof QuantumBlock;
	}
	
	public static boolean isSt(Building block) {
		return block instanceof QuantumBuilding;
	}
	
	public static QuantumBlock getBlock(Block b) {
		return (QuantumBlock) b;
	}
	
	public static QuantumBlock getBlock(Building b) {
		return (QuantumBlock) b.block;
	}
	
	public static QuantumBuilding getBuilding(Building b) {
		return (QuantumBuilding) b;
	}
	
	public void tooltip() {
		super.load();
		if (isInit) return;
		isInit = true;
		if (!placeClass.isEmpty()) {
			store.placeMap.tag(this, placeClass);
		}
		if (maxPlace > 0) {
			store.placeMap.max(this, maxPlace);
		}
		store.tooltip
			.tooltip(stats)
			.techLevel(techLevel)
			.maxPlace(maxPlace)
			.showValue("item-speed", itemSpeed)
			.showValue("liquid-speed", liquidSpeed)
			.showValue("import-item-speed", importItemSpeed)
			.showValue("import-liquid-speed", importLiquidSpeed)
		;
	}
	
}