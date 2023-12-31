package st.addon.entity.entity;

import mindustry.type.Item;

public class StItem extends Item {
	public StItem(String name) {
		super(name);
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
