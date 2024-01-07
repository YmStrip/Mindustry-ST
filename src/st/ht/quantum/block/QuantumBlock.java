package st.ht.quantum.block;

import arc.scene.ui.layout.Table;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Teamc;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.Tile;
import st.ST;

public class QuantumBlock extends Block {
	public QuantumBlock b;
	
	public QuantumBlock(String name) {
		super(name);
		researchCostMultiplier = 1f;
		b = this;
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
	
	public boolean isNetworkBlock() {
		return this instanceof QuantumInterface || this instanceof QuantumDrive || this instanceof LiquidCenter;
	}
	
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		if (!isNetworkBlock()) return true;
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
			.show("item-speed", itemSpeed)
			.show("liquid-speed", liquidSpeed)
			.show("import-item-speed", importItemSpeed)
			.show("import-liquid-speed", importLiquidSpeed)
		;
	}
	
	public class QuantumBuilding extends Building {
		public QuantumBuilding() {
			super();
			this.block = b;
			//this.store = block.store;
			//itemsBlock = b.itemsBlock;
			//liquidsBlock = b.liquidsBlock;
		}
		
		public QuantumBlock block;
		//public boolean itemsBlock = false;
		//public boolean liquidsBlock = false;
		long LastItemTime = System.currentTimeMillis();
		long LastLiquidTime = System.currentTimeMillis();
		
		public boolean isWaitItem() {
			if (b == null) return false;
			if (b.itemSpeed <= 0) return true;
			long T = System.currentTimeMillis();
			return T - LastItemTime >= 1000 / b.itemSpeed;
		}
		
		public boolean isWaitLiquid() {
			if (b == null) return false;
			if (b.liquidSpeed <= 0) return true;
			long T = System.currentTimeMillis();
			return T - LastLiquidTime >= 62.5;
		}
		
		public void waitItem() {
			if (b == null) return;
			LastItemTime = System.currentTimeMillis();
		}
		
		public void waitLiquid() {
			if (b == null) return;
			LastLiquidTime = System.currentTimeMillis();
		}
		
		@Override
		public void onRemoved() {
			super.onRemoved();
			if (b.isNetworkBlock()) {
				var ps = store.placeMap.team(team.id);
				ps.remove(this, 1);
			}
			var ts = store.quantumMap.team(team.id);
			ts.removeBuilding(this);
		}
		
		@Override
		public void created() {
			super.created();
			if (b.isNetworkBlock()) {
				var ps = store.placeMap.team(team.id);
				ps.add(this, 1);
				var ts = store.quantumMap.team(team.id);
				ts.addBuilding(this);
			}
		}
		
		@Override
		public boolean onConfigureBuildTapped(Building other) {
			return true;
		}
		
		@Override
		public void buildConfiguration(Table table) {
			super.buildConfiguration(table);
			if (b.itemsBlock || b.liquidsBlock || b.quantum) {
				store.quantumUI.new component(this);
			}
		}
		
		@Override
		public boolean acceptItem(Building source, Item item) {
			if (ST.debug) {
				//System.out.println("handelItem:" + this);
			}
			if (block.itemsBlock) return true;
			return super.acceptItem(source, item);
		}
		
		@Override
		public void handleItem(Building source, Item item) {
			var ts = store.quantumMap.team(team.id);
			if (ST.debug) {
				//System.out.println(String.format("队伍：%s，物品：%s，", ts, item.name + ": " + ts.items.get(item.name)));
			}
			ts.items.add(item.name, 1f);
			if (items != null && items.get(item) < block.itemCapacity) {
				super.handleItem(source, item);
			} else {
				ts.items.change(item.name, 1f);
			}
		}
		
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			if (!block.liquidsBlock || liquids == null) return super.acceptLiquid(source, liquid);
			var ts = store.quantumMap.team(team.id);
			if (ts.liquids.get(liquid.name) >= ts.liquidCapability) return false;
			return true;
		}
		
		@Override
		public void handleLiquid(Building source, Liquid liquid, float amount) {
			var ts = store.quantumMap.team(team.id);
			ts.liquids.add(liquid.name, amount);
			if (liquids.get(liquid) + amount < block.liquidSpeed) {
				liquids.add(liquid, amount);
			} else {
				var rm = block.liquidSpeed - liquids.get(liquid);
				liquids.add(liquid, rm);
				ts.liquids.change(liquid.name, amount - rm);
			}
		}
		
		@Override
		public boolean canPickup() {
			return false;
		}
		
		@Override
		public int removeStack(Item item, int amount) {
			if (!block.itemsBlock) return super.removeStack(item, amount);
			amount = Math.min(amount, this.items.get(item));
			this.noSleep();
			this.items.remove(item, amount);
			store.quantumMap.team(team.id).items.add(item.name, (float) -amount);
			return amount;
		}
		
		@Override
		public void handleStack(Item item, int amount, Teamc source) {
			if (!block.itemsBlock) super.handleStack(item, amount, source);
			this.noSleep();
			this.items.add(item, amount);
			store.quantumMap.team(team.id).items.add(item.name, (float) amount);
		}
	}
}