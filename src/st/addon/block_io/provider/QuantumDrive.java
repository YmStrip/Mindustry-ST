package st.addon.block_io.provider;

import mindustry.type.Category;
import st.addon.entity.entity.EntityStore;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StBuilding;

public class QuantumDrive extends StBlock {
	public QuantumDrive(EntityStore store, String name) {
		super(store,name);
		update = true;
		hasPower = true;
		size = 8;
		configurable = true;
		saveConfig = true;
		saveData = true;
		quantum = true;
		itemsBlock = true;
		liquidsBlock = true;
		placeClass = "io-quantum-drive";
		hasLiquids = true;
		hasItems = true;
		maxPlace = 8;
		category = Category.effect;
		canOverdrive = false;
	}
	
	StBlock b = this;
	
	public class QuantumDriveBuild extends StBuilding {
		public QuantumDriveBuild() {
			super(b);
		}
	}
}
