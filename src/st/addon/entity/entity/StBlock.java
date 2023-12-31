package st.addon.entity.entity;

import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import st.plugin.place.provider.PlaceMap;

public class StBlock extends Block {
	public StBlock(EntityStore store, String name) {
		super(name);
		researchCostMultiplier = 50f;
		this.store = store;
	}
	
	@Override
	public boolean outputsItems() {
		return false;
	}
	
	public EntityStore store;
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
		if (maxPlace > 0) {
			var can = store.placeMap.team(team.id);
			return can.canPlaceOn(tile, this, rotation);
		}
		return true;
	}
	
	public static boolean isSt(Block block) {
		return block instanceof StBlock;
	}
	
	public static boolean isSt(Building block) {
		return block instanceof StBuilding;
	}
	
	public static StBlock getBlock(Block b) {
		return (StBlock) b;
	}
	
	public static StBlock getBlock(Building b) {
		return (StBlock) b.block;
	}
	
	public static StBuilding getBuilding(Building b) {
		return (StBuilding) b;
	}
	
	@Override
	public void load() {
		super.load();
		store.toolTipBar.init(this);
	}
	
}