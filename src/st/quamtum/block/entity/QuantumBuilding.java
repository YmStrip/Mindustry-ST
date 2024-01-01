package st.quamtum.block.entity;

import arc.scene.ui.layout.Table;
import layer.annotations.Provider;
import mindustry.gen.Building;
import mindustry.gen.Teamc;
import mindustry.type.Item;
import mindustry.type.Liquid;
import st.ST;


@Provider
public class QuantumBuilding extends Building {
	public QuantumStore store;
	
	//上次时间
	public QuantumBuilding(QuantumBlock b) {
		super();
		this.b = b;
		this.block = b;
		this.store = block.store;
		itemsBlock = b.itemsBlock;
		liquidsBlock = b.liquidsBlock;
	}
	
	public QuantumBlock block;
	public boolean itemsBlock = false;
	public boolean liquidsBlock = false;
	QuantumBlock b;
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
		var ts = store.quantumMap.team(team.id);
		ts.removeBuilding(this);
		var ps = store.placeMap.team(team.id);
		ps.remove(this, 1);
	}
	
	@Override
	public void created() {
		super.created();
		var ts = store.quantumMap.team(team.id);
		ts.addBuilding(this);
		var ps = store.placeMap.team(team.id);
		ps.add(this, 1);
	}
	
	@Override
	public boolean onConfigureBuildTapped(Building other) {
		return true;
	}
	
	@Override
	public void buildConfiguration(Table table) {
		super.buildConfiguration(table);
		if (b.itemsBlock || b.liquidsBlock || b.quantum) {
			store.quantumNetworkDashUI.new component(this);
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