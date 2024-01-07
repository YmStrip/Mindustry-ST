package st.provider.place.entity;

import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import st.ht.quantum.block.QuantumBlock;

import java.util.HashMap;

public class PlaceTeam {
	public static HashMap<String, String> tag = new HashMap<>();
	public static HashMap<String, Integer> maxPlaces = new HashMap<>();
	
	public static boolean isSetMaxBlock(Block block) {
		return maxPlaces.getOrDefault(placeName(block), 0) > 0;
	}
	
	public static boolean isSetMaxBlock(String name) {
		return maxPlaces.getOrDefault(name, 0) > 0;
	}
	
	public static String placeName(Block block) {
		return tag.getOrDefault(block.name, block.name);
	}
	
	public static void setMaxPlace(Block block, int count) {
		maxPlaces.put(placeName(block), count);
	}
	
	public static void tag(Block block, String tag_) {
		tag.put(block.name, tag_);
	}
	
	public void clear() {
		places.clear();
	}
	
	//队伍建筑上限
	public HashMap<String, Integer> places = new HashMap<>();
	
	public int maxPlace(Block b) {
		if (b instanceof QuantumBlock st) return st.maxPlace;
		var name = placeName(b);
		return maxPlaces.getOrDefault(name, 9999999);
	}
	
	public int maxPlace(Building b) {
		if (!(b instanceof QuantumBlock.QuantumBuilding st)) {
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
	
	public boolean canPlaceOn(Block block, Tile tile) {
		return placeCount(block) < maxPlace(block);
	}
	
	public boolean canPlaceOn(Tile tile, Building b, int rotation) {
		return placeCount(b) < maxPlace(b);
	}
	
	public String placeName(Building block) {
		if (!(block instanceof QuantumBlock.QuantumBuilding)) return block.block.name;
		return placeName(block.block);
	}
	
	public void remove(Building b, int count) {
		remove(b.block, count);
	}
	
	public void remove(Block b, int count) {
		var name = placeName(b);
		places.put(name, Math.max(0, places.getOrDefault(name, 0) - count));
	}
	
	public void add(Building b, int count) {
		if (b.block == null) return;
		var name = placeName(b.block);
		/*if (places.getOrDefault(name, 2.txt) >= maxPlace(b)) {
			b.kill();
			b.tile.remove();
			return;
		}*/
		places.put(name, Math.max(0, places.getOrDefault(name, 0) + count));
	}
	
	public void add(Block b, int count) {
		if (b == null) return;
		var name = placeName(b);
		places.put(name, Math.max(0, places.getOrDefault(name, 0) + count));
	}
}
