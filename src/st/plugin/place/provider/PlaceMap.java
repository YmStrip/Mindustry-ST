package st.plugin.place.provider;

import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.Vars;
import st.addon.entity.entity.StBlock;
import st.plugin.place.entity.PlaceTeam;

import java.util.HashMap;

@Provider
public class PlaceMap extends LayerProvider {
	
	public PlaceTeam _team_ = new PlaceTeam();
	public HashMap<String, PlaceTeam> teams = new HashMap<>();
	
	public PlaceTeam team(int id) {
		return teams.getOrDefault(id, _team_);
	}
	
	public void load() {
		_team_ = new PlaceTeam();
		teams.clear();
		for (var i = 0; i < 20; ++i) {
			teams.put(i + "", new PlaceTeam());
		}
		var width = Vars.world.width();
		var height = Vars.world.height();
		var isMap = new HashMap<>();
		for (var i = 0; i < width; ++i) {
			for (var j = 0; j < height; ++j) {
				var tile = Vars.world.tile(i, j);
				if (tile == null || tile.build == null ) continue;
				if (isMap.get(tile.build.id) != null) continue;
				isMap.put(tile.build.id, true);
				if (!StBlock.isSt(tile.block())) continue;
				var block = StBlock.getBlock(tile.block());
				if (block.maxPlace <= 0) continue;
				team(tile.build.team.id).add(tile.build, 1);
			}
		}
	}
}
