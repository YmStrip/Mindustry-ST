package st.quamtum.block.mentity;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.graphics.Drawf;
import mindustry.type.Category;
import mindustry.type.Liquid;
import mindustry.world.meta.BlockGroup;
import st.quamtum.block.entity.QuantumBlock;
import st.quamtum.block.entity.QuantumBuilding;
public class LiquidCenter extends QuantumBlock {
	public LiquidCenter(String name) {
		super(name);
		category = Category.liquid;
		this.group = BlockGroup.liquids;
		size = 3;
		update = true;
		noUpdateDisabled = true;
		displayFlow = false;
		outputsLiquid = false;
		configurable = true;
		saveConfig = true;
		saveData = true;
		hasPower = true;
		consumesPower = true;
		solid = true;
		quantum = true;
		liquidsBlock = true;
		maxPlace = 1;
		placeClass = "liquid-center";
		hasLiquids = true;
		consumePowerBuffered(1000F);
		this.config(String.class, (tile, l) -> {
			LiquidCenterBuild b = (LiquidCenterBuild) tile;
			((LiquidCenterBuild) tile).selectLiquidName = l;
		});
		this.configClear((tile) -> {
			LiquidCenterBuild b = (LiquidCenterBuild) tile;
			((LiquidCenterBuild) tile).selectLiquidName = "";
		});
	}
	
	public TextureRegion liquidRegion;
	public TextureRegion topRegion;
	public TextureRegion mainRegion;
	
	@Override
	public void load() {
		super.load();
		this.mainRegion = Core.atlas.find("st-流体中心");
		this.topRegion = Core.atlas.find("st-流体中心-top");
		this.liquidRegion = Core.atlas.find("st-流体中心-liquid");
	}
	
	@Override
	protected TextureRegion[] icons() {
		return new TextureRegion[]{
			mainRegion,
			topRegion
		};
	}
	
	QuantumBlock b = this;
	
	public class LiquidCenterBuild extends QuantumBuilding {
		public LiquidCenterBuild() {
			super(b);
		}
		
		public Liquid selectLiquid = null;
		public String selectLiquidName = "";
		
		public void displayBars(Table table) {
			super.displayBars(table);
		}
		
		@Override
		public void draw() {
			float rotation = 0.0F;
			Draw.rect(mainRegion, this.x, this.y, rotation);
			if (this.selectLiquid != null) {
				Vars.ui.showLabel(this.selectLiquid.localizedName + "\n" + (int) this.liquids.get(this.selectLiquid), 0.01F, this.x, this.y + 17.0F);
				if (this.liquids.get(this.selectLiquid) >= 1.0F) {
					Drawf.liquid(liquidRegion, this.x, this.y, this.liquids.get(this.selectLiquid) / liquidCapacity + 0.4F, this.selectLiquid.color);
				}
			}
			
			Draw.rect(topRegion, this.x, this.y, rotation);
		}
	}
}
