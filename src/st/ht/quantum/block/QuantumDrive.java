package st.ht.quantum.block;

import mindustry.type.Category;

public class QuantumDrive extends QuantumBlock {
	public QuantumDrive(String name) {
		super(name);
		category = Category.effect;
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
		canOverdrive = false;
	}
	
	QuantumBlock b = this;
	
	public class QuantumDriveBuild extends QuantumBuilding {
		public QuantumDriveBuild() {
			super();
		}
	}
}
