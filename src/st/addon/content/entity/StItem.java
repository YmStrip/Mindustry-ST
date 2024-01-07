package st.addon.content.entity;

import mindustry.type.Item;

public class StItem extends Item {
	public StItem(String name) {
		super(name);
		alwaysUnlocked = false;
	}
	
	public boolean isInit = false;
	//科技等级
	public int techLevel = 0;
	@Override
	public void load() {
		super.load();
	}
	//基准伤害
	public int damage = 50;
}
