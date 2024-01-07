package st.addon.content.provider;

import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.content.Blocks;
import mindustry.world.meta.Attribute;

@Provider
public class AttrProvider extends LayerProvider {
	public Attribute 富集地 = Attribute.add("富集地");
	public Attribute 浅层矿物 = Attribute.add("浅层矿物");
	public Attribute 深层矿物 = Attribute.add("深层矿物");
	public Attribute 水 = Attribute.add("水");
	
	public void 浅层矿物() {
		//玄武岩
		Blocks.basalt.attributes.set(浅层矿物, 1f);
		//安山岩
		Blocks.dacite.attributes.set(浅层矿物, 1f);
		//地热岩
		Blocks.hotrock.attributes.set(浅层矿物, 1.25f);
		//大地热岩
		Blocks.magmarock.attributes.set(浅层矿物, 1.5f);
		//焦土
		Blocks.charr.attributes.set(浅层矿物, 1.25f);
		//盐碱地
		Blocks.salt.attributes.set(浅层矿物, 0.15f);
		//页岩地
		Blocks.shale.attributes.set(浅层矿物, 0.2f);
		//泥土
		Blocks.dirt.attributes.set(浅层矿物, 0.15f);
		//雪
		Blocks.snow.attributes.set(浅层矿物, 0.15f);
		//苔藓地
		Blocks.moss.attributes.set(浅层矿物, 0.25f);
		//陨石坑
		Blocks.craters.attributes.set(浅层矿物, 1.5f);
		//流纹岩
		Blocks.rhyolite.attributes.set(浅层矿物, 0.65f);
		//黄石
		Blocks.yellowStone.attributes.set(浅层矿物, 1.25f);
	}
	
	public void 深层矿物() {
		//玄武岩
		Blocks.basalt.attributes.set(深层矿物, 0.8f);
		//安山岩
		Blocks.dacite.attributes.set(深层矿物, 0.8f);
		//地热岩
		Blocks.hotrock.attributes.set(深层矿物, 1f);
		//大地热岩
		Blocks.magmarock.attributes.set(深层矿物, 1.25f);
		//焦土
		Blocks.charr.attributes.set(深层矿物, 1f);
		//盐碱地
		Blocks.salt.attributes.set(深层矿物, 0.15f);
		//页岩地
		Blocks.shale.attributes.set(深层矿物, 0.2f);
		//泥土
		Blocks.dirt.attributes.set(深层矿物, 0.15f);
		//雪
		Blocks.snow.attributes.set(深层矿物, 0.15f);
		//苔藓地
		Blocks.moss.attributes.set(深层矿物, 0.25f);
		//陨石坑
		Blocks.craters.attributes.set(深层矿物, 1.5f);
		//流纹岩
		Blocks.rhyolite.attributes.set(深层矿物, 0.65f);
		//黄石
		Blocks.yellowStone.attributes.set(深层矿物, 0.65f);
	}
	
	public void 富集地() {
		//地热岩
		Blocks.hotrock.attributes.set(富集地, 0.5f);
		//大地热岩
		Blocks.magmarock.attributes.set(富集地, 1f);
		//陨石坑
		Blocks.craters.attributes.set(富集地, 1.25f);
		//铁陨石坑
		Blocks.ferricCraters.attributes.set(富集地, 1f);
		//焦土
		Blocks.charr.attributes.set(富集地, 0.75f);
	}
	
	public void 水() {
		//核心区 2.txt.8f
		Blocks.coreZone.attributes.set(水, 10f);
		Blocks.water.attributes.set(水, 1f);
		Blocks.sandWater.attributes.set(水, 2.5f);
		Blocks.taintedWater.attributes.set(水, 0.5f);
		Blocks.darksandWater.attributes.set(水, 2.5f);
		Blocks.deepTaintedWater.attributes.set(水, 0.5f);
		Blocks.deepwater.attributes.set(水, 3f);
	}
	
	@Override
	public void run() {
		水();
		浅层矿物();
		深层矿物();
		富集地();
	}
}
