package st.addon.effect.entity;

import mindustry.game.Team;
import mindustry.type.Category;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import st.provider.place.provider.PlaceProvider;

import static mindustry.Vars.state;

public class SOutpost extends CoreBlock {
	public PlaceProvider places;
	
	public void places(PlaceProvider p) {
		places = p;
	}
	
	public boolean canBreak = false;
	
	@Override
	public boolean canBreak(Tile tile) {
		return state.isEditor() || canBreak;
	}
	
	public SOutpost(String name) {
		super(name);
		category = Category.effect;
	}
	
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		return places.canPlaceOn(this, tile, team);
	}
	
	public class SCoreBuild extends CoreBuild {
		@Override
		public void onRemoved() {
			super.onRemoved();
			places
				.team(team.id)
				.remove(block, 1);
		}
		
		@Override
		public void created() {
			super.created();
			places
				.team(team.id)
				.add(block, 1);
		}
	}
}
