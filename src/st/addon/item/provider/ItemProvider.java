package st.addon.item.provider;

import arc.graphics.Color;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.world.Block;
import st.addon.entity.entity.StItem;


@Provider
public class ItemProvider extends LayerProvider {
	//t1 原料, 铜铅, damage = 40
	public StItem 反物质 = new StItem("反物质") {{
		damage = 40;
		techLevel = 1;
		color = Color.rgb(153, 50, 204);
		explosiveness = 2.5f;
		charge = 1f;
		cost = 1.5f;
	}};
	//t1 无, 铜铅, damage = 40
	public StItem 纳米碳管 = new StItem("纳米碳管") {{
		damage = 40;
		techLevel = 1;
		color = Color.rgb(20, 20, 20);
		cost = 2f;
		flammability = 1.05f;
	}};
	//t2 合金,能源, 相位物, damage = 1000
	public StItem 金元素 = new StItem("金元素") {{
		hardness = 42;
		damage = 800;
		techLevel = 1;
		color = Color.rgb(255, 215, 0);
		radioactivity = 1.5f;
		explosiveness = 1.5f;
		charge = 1.5f;
		flammability = 1.5f;
		cost = 2;
	}};
	//t2 效果,治疗, 塑钢, damage = 800
	public StItem 木元素 = new StItem("木元素") {
		{
			hardness = 42;
			damage = 300;
			techLevel = 1;
			color = Color.rgb(0, 255, 0);
			radioactivity = 1.5f;
			explosiveness = 1.5f;
			charge = 1.5f;
			flammability = 1.5f;
			cost = 2;
		}
	};
	//t2 单位,弹药, 硅, damage = 1100
	public StItem 水元素 = new StItem("水元素") {{
		hardness = 42;
		damage = 1100;
		techLevel = 1;
		radioactivity = 1.5f;
		explosiveness = 1.5f;
		charge = 1.5f;
		flammability = 1.5f;
		cost = 2;
		color = Color.rgb(30, 144, 255);
		//巨大闪电
	}};
	//t2 单位,弹药, 巨浪合计, damage = 1500
	public StItem 火元素 = new StItem("火元素") {{
		hardness = 42;
		damage = 1500;
		techLevel = 1;
		radioactivity = 1.5f;
		explosiveness = 1.5f;
		charge = 1.5f;
		flammability = 1.5f;
		cost = 2;
		color = Color.rgb(220, 20, 60);
		
	}};
	//t2 建筑,结构, 玻璃, damage = 900
	public StItem 土元素 = new StItem("土元素") {{
		hardness = 42;
		damage = 900;
		techLevel = 1;
		radioactivity = 1.5f;
		explosiveness = 1.5f;
		charge = 1.5f;
		flammability = 1.5f;
		cost = 2;
		color = Color.rgb(255, 0, 255);
	}};
	///t3 高级科技, *, damage = 5500
	public StItem 光元素 = new StItem("光元素") {{
		hardness = 42;
		damage = 5500;
		techLevel = 3;
		color = Color.rgb(130, 55, 254);
		radioactivity = 4f;
		explosiveness = 4f;
		charge = 4f;
		flammability = 4f;
		cost = 4;
	}};
	
	
	///t3 高级科技, *, damage = 2500
	public StItem 暗元素 = new StItem("暗元素") {{
		damage = 2500;
		hardness = 42;
		techLevel = 3;
		color = Color.rgb(0, 0, 128);
		radioactivity = 2.5f;
		explosiveness = 2.5f;
		charge = 2.5f;
		flammability = 2.5f;
		cost = 2.5f;
	}};
}
