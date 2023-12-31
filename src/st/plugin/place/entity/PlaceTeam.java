package st.plugin.place.entity;

import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StBuilding;

import java.util.HashMap;

public class PlaceTeam {
	public void clear() {
		places.clear();
	}
	
	//队伍建筑上限
	public HashMap<String, Integer> places = new HashMap<>();
	
	public int maxPlace(Block b) {
		if (!(b instanceof StBlock st)) {
			return 99999;
		}
		return st.maxPlace;
	}
	
	public int maxPlace(Building b) {
		if (!(b instanceof StBuilding st)) {
			return 99999;
		}
		return st.block.maxPlace;
	}
	
	public int placeCount(Block b) {
		var name = placeName(b);
		return places.getOrDefault(name, 0);
	}
	
	public int placeCount(Building b) {
		var name = placeName(b);
		return places.getOrDefault(name, 0);
	}
	
	public boolean canPlaceOn(Tile tile, Block b, int rotation) {
		return placeCount(b) < maxPlace(b);
	}
	
	public boolean canPlaceOn(Tile tile, Building b, int rotation) {
		return placeCount(b) < maxPlace(b);
	}
	
	public String placeName(Block block) {
		if (!(block instanceof StBlock bs)) return block.name;
		return bs.placeClass.isEmpty() ? block.name : bs.placeClass;
	}
	
	public String placeName(Building block) {
		if (!(block instanceof StBuilding)) return block.block.name;
		return placeName(block.block);
	}
	
	public void remove(Building b, int count) {
		remove(b.block, count);
	}
	
	public void remove(Block b, int count) {
		var name = placeName(b);
		places.put(name, Math.max(0, places.getOrDefault(name, 0) - count));
	}
	public void add(Building b,int count) {
		add(b.block,count);
	}
	public void add(Block b,int count) {
		var name = placeName(b);
		places.put(name, Math.max(0, places.getOrDefault(name, 0) + count));
	}
}
