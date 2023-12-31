package st.addon.block_io.provider;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.graphics.Drawf;
import mindustry.type.Liquid;
import mindustry.world.meta.BlockGroup;
import org.checkerframework.checker.units.qual.C;
import st.addon.entity.entity.EntityStore;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StBuilding;

public class LiquidCenter extends StBlock {
	public LiquidCenter(EntityStore store, String name) {
		super(store, name);
		this.group = BlockGroup.liquids;
		size = 3;
		update = true;
		noUpdateDisabled = true;
		displayFlow = false;
		hasLiquids = false;
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
	
	StBlock b = this;
	
	public class LiquidCenterBuild extends StBuilding {
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
