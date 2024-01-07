package st.ht.quantum.block;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.meta.BlockGroup;
import st.dao.LibgdxDao;


public class LiquidOutput extends QuantumBlock {
	public LiquidOutput(String name) {
		super(name);
		category = Category.liquid;
		group = BlockGroup.liquids;
		update = solid = acceptsItems = true;
		health = 800;
		liquidCapacity = 100F;
		hasItems = false;
		hasLiquids = true;
		placeableLiquid = true;
		displayFlow = false;
		saveConfig = true;
		saveData = true;
		configurable = true;
		this.config(Liquid.class, (build, liquid) -> {
			((LiquidOutputBuild) build).selectLiquid = liquid;
		});
	}
	
	public QuantumBlock b = this;
	
	TextureRegion mainRegion;
	TextureRegion topRegion;
	
	@Override
	public void load() {
		super.load();
		mainRegion = Core.atlas.find("st-流体输出接口");
		topRegion = Core.atlas.find("st-流体输出接口-top");
	}
	
	@Override
	protected TextureRegion[] icons() {
		return new TextureRegion[]{
			mainRegion,
			topRegion
		};
	}
	
	public class LiquidOutputBuild extends QuantumBuilding {
		@Override
		public void draw() {
			Draw.rect(mainRegion, x, y);
			Draw.color(this.selectLiquid == null ? Color.clear : this.selectLiquid.color);
			Draw.rect(topRegion, this.x, this.y);
		}
		
		Liquid selectLiquid;
		
		public LiquidOutputBuild() {
			super();
		}
		
		@Override
		public void buildConfiguration(Table table) {
			ItemSelection.buildTable(table, Vars.content.liquids(), () -> this.selectLiquid, this::configure);
		}
		
		@Override
		public void configure(Object value) {
			this.selectLiquid = (Liquid) value;
		}
		
		@Override
		public boolean onConfigureBuildTapped(Building other) {
			if (this == other) {
				this.deselect();
				this.configure((Object) null);
				return false;
			} else {
				return true;
			}
		}
		
		@Override
		public void write(Writes write) {
			super.write(write);
			LibgdxDao.writeStr(write, this.selectLiquid == null ? "b" : this.selectLiquid.name);
		}
		
		@Override
		public void read(Reads read, byte res) {
			super.read(read);
			var name = LibgdxDao.readStr(read);
			var liq = Vars.content.liquid(name);
			if (liq == null) return;
			selectLiquid = liq;
		}
		
		
		public Liquid config() {
			return this.selectLiquid;
		}
		
		@Override
		public void updateTile() {
			if (selectLiquid == null) return;
			if (!isWaitLiquid()) return;
			var teams = store.quantumMap.team(team.id);
			if (teams.liquids.get(selectLiquid.name) <= 0) return;
			this.emitLiquid(this.selectLiquid);
		}
		
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return false;
		}
		
		public boolean acceptItem(Building source, Item item) {
			return false;
		}
		
		public int iterProximity = -1;
		
		public boolean emitLiquid(Liquid liquid) {
			++iterProximity;
			var ts = store.quantumMap.team(team.id);
			if (proximity.size == 0) return false;
			if (iterProximity >= proximity.size) iterProximity = 0;
			var build = proximity.get(iterProximity);
			if (build == null) return false;
			if (!build.acceptLiquid(this, liquid)) return false;
			var r = Math.min((float) b.liquidSpeed / 16, build.block.liquidCapacity);
			var take = ts.liquids.take(liquid.name, r);
			ts.liquids.change(liquid.name, -r);
			build.handleLiquid(this, liquid, take);
			waitLiquid();
			return true;
		}
	}
}
