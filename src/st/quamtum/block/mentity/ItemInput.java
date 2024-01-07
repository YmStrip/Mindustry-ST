package st.quamtum.block.mentity;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.world.meta.BlockGroup;
import st.quamtum.block.entity.QuantumBlock;
import st.quamtum.block.entity.QuantumBuilding;
import st.quamtum.block.entity.QuantumStore;

public class ItemInput extends QuantumBlock {
	public ItemInput(String name) {
		super(name);
		group = BlockGroup.transportation;
		update = this.solid = this.acceptsItems = true;
		health = 800;
		itemCapacity = 250;
		liquidCapacity = 200F;
		hasItems = false;
		hasLiquids = false;
		category = Category.distribution;
	}
	
	TextureRegion mainRegion;
	TextureRegion topRegion;
	
	@Override
	public void load() {
		super.load();
		mainRegion = Core.atlas.find("st-物品输入接口");
		topRegion = Core.atlas.find("st-物品输入接口-top");
	}
	
	@Override
	protected TextureRegion[] icons() {
		return new TextureRegion[]{
			mainRegion,
			topRegion
		};
	}
	
	QuantumBlock b = this;
	
	public class ItemInputBuild extends QuantumBuilding {
		public ItemInputBuild() {
			super(b);
		}
		
		@Override
		public void updateTile() {
			super.updateTile();
		}
		
		Building core;
		
		@Override
		public void handleItem(Building source, Item item) {
			core = this.team.core();
			var ts = store.quantumMap.team(team.id);
			if (core == null) return;
			//如果>,哪么尝试 核心物品 < 核心容量
			if (core.items.get(item) < core.block.itemCapacity) {
				core.handleItem(core, item);
			} else {
				ts.items.add(item.name, 1f);
				ts.items.change(item.name, 1f);
			}
			waitItem();
		}
		
		@Override
		public boolean acceptItem(Building source, Item item) {
			core = this.team.core();
			return (core != null) && isWaitItem();
		}
		
		@Override
		public void draw() {
			Draw.rect(mainRegion, this.x, this.y);
			Draw.rect(topRegion, this.x, this.y);
		}
	}
	
}
