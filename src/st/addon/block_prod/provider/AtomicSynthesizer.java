package st.addon.block_prod.provider;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import st.addon.entity.entity.EntityStore;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StBuilding;


public class AtomicSynthesizer extends StBlock {
	public AtomicSynthesizer(EntityStore store, String name) {
		super(store, name);
		size = 6;
		itemCapacity = 100;
		liquidCapacity = 240f;
		update = true;
		hasLiquids = true;
		hasPower = true;
		hasItems = true;
		configurable = true;
		saveConfig = true;
		saveData = true;
		outputsLiquid = true;
		outputsPayload = true;
	}
	
	public TextureRegion centerRegion;
	public TextureRegion topRegion;
	
	@Override
	public void load() {
		super.load();
		this.centerRegion = Core.atlas.find(this.name);
		this.topRegion = Core.atlas.find(this.name + "-top");
	}
	
	StBlock b = this;
	
	public class AtomicSynthesizerBuild extends StBuilding {
		public AtomicSynthesizerBuild() {
			super(b);
		}
		
		@Override
		public void draw() {
			super.draw();
			Draw.rect(centerRegion, this.x, this.y);
			if (productionValid()) {
				Draw.rect(topRegion, this.x, this.y);
			}
		}
		
		@Override
		public void buildConfiguration(Table table) {
			store.atomicSynthesizerUI.new component();
		}
	}
}
