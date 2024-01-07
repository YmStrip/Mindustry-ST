package st.provider.place.provider;

import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import st.provider.place.entity.PlaceTeam;

import java.util.HashMap;

@Provider
public class PlaceProvider extends LayerProvider {
	@Require
	Logger logger;
	public PlaceTeam _team_ = new PlaceTeam();
	public HashMap<String, PlaceTeam> teams = new HashMap<>();
	
	public PlaceTeam team(int id) {
		return teams.getOrDefault(id, _team_);
	}
	
	//注入方法
	public PlaceProvider max(Block b, int count) {
		PlaceTeam.setMaxPlace(b, count);
		return this;
	}
	
	public PlaceProvider tag(Block b, String tag) {
		PlaceTeam.tag(b, tag);
		return this;
	}
	
	public boolean canPlaceOn(Block block,Tile tile,Team team) {
		return team(team.id).canPlaceOn(block,tile);
	}
	
	public void load() {
		_team_ = new PlaceTeam();
		teams.clear();
		for (var i = 0; i < 20; ++i) {
			teams.put(i + "", new PlaceTeam());
		}
		for (var i : Vars.world.tiles) {
			if (i == null || i.build == null) continue;
			//计数
			var name = PlaceTeam.placeName(i.build.block);
			if (!PlaceTeam.isSetMaxBlock(name)) return;
			team(i.build.team.id).add(i.build, 1);
		}
	}
	
}
