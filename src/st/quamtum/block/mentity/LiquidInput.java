package st.quamtum.block.mentity;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.Liquid;
import mindustry.world.meta.BlockGroup;
import st.quamtum.block.entity.QuantumBlock;
import st.quamtum.block.entity.QuantumBuilding;

public class LiquidInput extends QuantumBlock {
	public LiquidInput(String name) {
		super(name);
		category = Category.liquid;
		this.group = BlockGroup.liquids;
		update = solid = acceptsItems = true;
		health = 800;
		liquidCapacity = 200F;
		hasItems = false;
		hasLiquids = true;
		placeableLiquid = true;
		displayFlow = false;
	}
	
	QuantumBlock b = this;
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
	
	public class LiquidInputBuild extends QuantumBuilding {
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
