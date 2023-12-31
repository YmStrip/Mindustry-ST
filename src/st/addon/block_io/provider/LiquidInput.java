package st.addon.block_io.provider;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.meta.BlockGroup;
import st.addon.entity.entity.EntityStore;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StBuilding;

public class LiquidInput extends StBlock {
	public LiquidInput(EntityStore store, String name) {
		super(store, name);
		this.group = BlockGroup.liquids;
		update = solid = acceptsItems = true;
		health = 800;
		liquidCapacity = 200F;
		hasItems = false;
		hasLiquids = true;
		placeableLiquid = true;
		displayFlow = false;
	}
	
	StBlock b = this;
	TextureRegion mainRegion;
	TextureRegion topRegion;
	
	@Override
	public void load() {
		super.load();
		mainRegion = Core.atlas.find(
			"st-流体输入接口");
		topRegion = Core.atlas.find("st-流体输入接口-top");
	}
	
	@Override
	protected TextureRegion[] icons() {
		return new TextureRegion[]{
			mainRegion,
			topRegion
		};
	}
	
	public class LiquidInputBuild extends StBuilding {
		public LiquidInputBuild() {
			super(b);
		}
		
		@Override
		public void draw() {
			Draw.rect(mainRegion, x, y);
			Draw.rect(topRegion, x, y);
		}
		
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			var t = store.quantumMap.team(team.id);
			return t.liquids.get(liquid.name) < t.liquidCapability;
		}
		
		@Override
		public void handleLiquid(Building source, Liquid liquid, float amount) {
			var t = store.quantumMap.team(team.id);
			t.liquids.add(liquid.name, amount);
			t.liquids.change(liquid.name, amount);
		}
	}
}
