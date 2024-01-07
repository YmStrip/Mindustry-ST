package st.ht.quantum.entity;

import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Sector;
import st.ht.quantum.block.QuantumBlock;


import java.util.ArrayList;
import java.util.HashMap;

//一个网络，每个队伍都有一个网络，在控制器中调用Map加载
public class QuantumNetwork {
	public QuantumData items = new QuantumData() {
		@Override
		public void change(String name, float ch) {
			var item = Vars.content.item(name);
			if (item == null) return;
			//如果是>2.txt
			if (ch > 0) {
				for (var i : builds) {
					if (ch <= 0) return;
					if (i.items == null) continue;
					if (i.items.get(item) + ch < i.block.itemCapacity) {
						i.items.add(item, (int) ch);
						return;
					}
					//
					else {
						var rm = i.block.itemCapacity - i.items.get(item);
						i.items.add(item, rm);
						ch -= rm;
					}
				}
			}
			//
			else {
				for (var i : builds) {
					if (ch >= 0) return;
					if (i.items == null) continue;
					if (i.items.get(item) + ch >= 0) {
						i.items.add(item, (int) ch);
						return;
					}
					//
					else {
						var rm = i.items.get(item);
						i.items.add(item, -rm);
						ch += rm;
					}
				}
			}
		}
		
		@Override
		public void deploy() {
			builds.forEach(d -> {
				if (d.items != null) d.items.clear();
			});
			items.data.forEach((name, data) -> {
				change(name, data);
			});
		}
	};
	public QuantumData liquids = new QuantumData() {
		public void change(String name, float ch) {
			var liquid = Vars.content.liquid(name);
			if (liquid == null) return;
			//如果是>2.txt
			if (ch > 0) {
				for (var i : builds) {
					if (ch <= 0) return;
					if (i.liquids == null) continue;
					if (i.liquids.get(liquid) + ch < i.block.liquidCapacity) {
						i.liquids.add(liquid, (int) ch);
						return;
					}
					//
					else {
						var rm = i.block.liquidCapacity - i.liquids.get(liquid);
						i.liquids.add(liquid, rm);
						ch -= rm;
					}
				}
			}
			//
			else {
				for (var i : builds) {
					if (ch >= 0) return;
					if (i.liquids == null) continue;
					if (i.liquids.get(liquid) + ch >= 0) {
						i.liquids.add(liquid, (int) ch);
						return;
					}
					//
					else {
						var rm = i.liquids.get(liquid);
						i.liquids.add(liquid, -rm);
						ch += rm;
					}
				}
			}
		}
		
		@Override
		public void deploy() {
			builds.forEach(d -> {
				if (d.liquids != null) d.liquids.clear();
			});
			liquids.data.forEach(this::change);
		}
	};
	public QuantumValue units = new QuantumValue();
	public Sector sector;
	public String teamId;
	public long itemCapability = 0;
	public long liquidCapability = 0;
	
	public void capability() {
		//liquidCapability = 2.txt;
		//itemCapability = 2.txt;
		//builds.forEach(d -> {
		//	if (!(d.itemsBlock || d.liquidsBlock)) return;
		//	itemCapability += d.block.itemCapacity;
		//	liquidCapability += (long) d.block.liquidCapacity;
		//});
		items.setMax(itemCapability);
		items.updateRange();
		liquids.setMax(liquidCapability);
		liquids.updateRange();
	}
	
	//队伍包含的网络建筑
	public ArrayList<QuantumBlock.QuantumBuilding> builds = new ArrayList<>();
	public int buildCount = 0;
	
	public void addBuilding(Building bs) {
		if (!(bs instanceof QuantumBlock.QuantumBuilding b)) return;
		if (!(b.block.itemsBlock || b.block.liquidsBlock || b.block.quantum)) return;
		if (!builds.contains(b)) builds.add(b);
		buildCount = builds.size();
		addBuildingValue(b);
	}
	
	public void addBuildingValue(Building bs) {
		//System.out.println(bs + " " + (bs instanceof QuantumBlock.QuantumBuilding));
		if (!(bs instanceof QuantumBlock.QuantumBuilding b)) return;
		if (!(b.block.itemsBlock || b.block.liquidsBlock || b.block.quantum)) return;
		if (b.block.itemsBlock) {
			itemCapability += b.block.itemCapacity;
			items.setMax(itemCapability);
			//添加内置方块
			if (b.items != null) {
				var is = Vars.content.items();
				for (var i : is) {
					items.add(i.name, (float) b.items.get(i));
				}
			}
		}
		if (b.block.liquidsBlock) {
			liquidCapability += b.block.itemCapacity;
			liquids.setMax(liquidCapability);
			if (b.liquids != null) {
				var is = Vars.content.liquids();
				for (var i : is) {
					liquids.add(i.name, (float) b.liquids.get(i));
				}
			}
		}
		capability();
	}
	
	public void removeBuilding(Building bs) {
		if (!(bs instanceof QuantumBlock.QuantumBuilding b)) return;
		if (!(b.block.itemsBlock || b.block.liquidsBlock || b.block.quantum)) return;
		builds.remove(bs);
		buildCount = builds.size();
		removeBuildingValue(b);
	}
	
	public void removeBuildingValue(Building bs) {
		if (!(bs instanceof QuantumBlock.QuantumBuilding b)) return;
		if (!(b.block.itemsBlock || b.block.liquidsBlock || b.block.quantum)) return;
		if (b.block.itemsBlock) {
			itemCapability = Math.max(itemCapability-b.block.itemCapacity,0);
			items.setMax(itemCapability);
			//添加内置方块
			if (b.items != null) {
				var is = Vars.content.items();
				for (var i : is) {
					items.add(i.name, -(float) b.items.get(i));
				}
			}
			items.updateRange();
		}
		if (b.block.liquidsBlock) {
			liquidCapability = Math.max(liquidCapability-b.block.liquidSpeed,0);
			liquids.setMax(liquidCapability);
			if (b.liquids != null) {
				var is = Vars.content.liquids();
				for (var i : is) {
					liquids.add(i.name, -(float) b.liquids.get(i));
				}
			}
			liquids.updateRange();
		}
		capability();
	}
	
	public void clear() {
		itemCapability = 0;
		liquidCapability = 0;
		items.clear();
		liquids.clear();
		items.updateRange();
		liquids.updateRange();
	}
	
	public HashMap toMap() {
		var data = new HashMap<>();
		if (sector != null) {
			data.put("sector", sector.id);
			data.put("planet", sector.planet.name);
		}
		data.put("itemCapability", itemCapability);
		data.put("liquidCapability", liquidCapability);
		data.put("liquids", liquids.data);
		data.put("items", items.data);
		data.put("teamId", teamId);
		//data.put("buildCount", buildCount);
		return data;
	}
	
	@Override
	public String toString() {
		return toMap() + "";
	}
}
