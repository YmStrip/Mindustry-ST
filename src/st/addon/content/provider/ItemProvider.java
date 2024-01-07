package st.addon.content.provider;

import arc.graphics.Color;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import st.addon.content.entity.StItem;


@Provider
public class ItemProvider extends LayerProvider {
	//t1纳米碳管 1硅 1石墨 = 2.txt 300
	public StItem 纳米碳管 = new StItem("纳米碳管") {{
		damage = 300;
		techLevel = 1;
		color = Color.rgb(12, 8, 69);
		explosiveness = 2.5f;
		charge = 1f;
		cost = 1.5f;
	}};
	//t1超导体 1合金 1相位物 = 2.txt 750
	public StItem 超导体 = new StItem("超导体") {{
		
		damage = 750;
		techLevel = 1;
		color = Color.rgb(235, 243, 196);
		explosiveness = 2.5f;
		charge = 1f;
		cost = 1.5f;
	}};
	//t2反物质 1钛 1爆炸混合物 = 2.txt 500
	public StItem 反物质 = new StItem("反物质") {{
		damage = 500;
		techLevel = 2;
		color = Color.rgb(153, 50, 204);
		explosiveness = 2.5f;
		charge = 1f;
		cost = 1.5f;
	}};
	//t2反重力陶瓷 1纳米碳管 1相位物 = 2.txt 650
	public StItem 反重力陶瓷 = new StItem("反重力陶瓷") {{
		damage = 650;
		techLevel = 2;
		color = Color.rgb(243, 198, 158);
		explosiveness = 2.5f;
		charge = 1f;
		cost = 1.5f;
	}};
	//t2辐矿石 1纳米碳管 2钍 = 1 350
	public StItem 辐矿石 = new StItem("辐矿石") {{
		damage = 350;
		techLevel = 2;
		color = Color.rgb(243, 156, 207);
		explosiveness = 2.5f;
		charge = 1f;
		cost = 1.5f;
	}};
	//t2铬纳尔Chromnar 1纳米碳管 2合金 = 1 850
	public StItem 铬纳尔 = new StItem("铬纳尔") {{
		damage = 850;
		techLevel = 2;
		color = Color.rgb(154, 207, 243);
		explosiveness = 2.5f;
		charge = 1f;
		cost = 1.5f;
	}};
	//t2晶金 1纳米碳管 2钢化玻璃 = 1 500
	public StItem 晶金 = new StItem("晶金") {{
		damage = 500;
		techLevel = 2;
		color = Color.rgb(116, 243, 149);
		explosiveness = 2.5f;
		charge = 1f;
		cost = 1.5f;
	}};
	//T3 2反物质 2超导体 = 3 1500
	public StItem 金元素 = new StItem("金元素") {{
		hardness = 42;
		damage = 1500;
		techLevel = 3;
		color = Color.rgb(255, 215, 0);
		radioactivity = 1.5f;
		explosiveness = 1.5f;
		charge = 1.5f;
		flammability = 1.5f;
		cost = 2;
	}};
	//t2 2反物质 2辐矿石 = 3 效果,治疗, 塑钢, damage = 1500
	public StItem 木元素 = new StItem("木元素") {
		{
			hardness = 42;
			damage = 1500;
			techLevel = 3;
			color = Color.rgb(0, 255, 0);
			radioactivity = 1.5f;
			explosiveness = 1.5f;
			charge = 1.5f;
			flammability = 1.5f;
			cost = 2;
		}
	};
	//t2 2反物质 2晶金 = 3 单位,弹药, damage = 1500
	public StItem 水元素 = new StItem("水元素") {{
		hardness = 42;
		damage = 1500;
		techLevel = 3;
		radioactivity = 1.5f;
		explosiveness = 1.5f;
		charge = 1.5f;
		flammability = 1.5f;
		cost = 2;
		color = Color.rgb(30, 144, 255);
		//巨大闪电
	}};
	//t2 2反物质 2铬纳尔 = 3 单位,弹药, damage = 2500
	public StItem 火元素 = new StItem("火元素") {{
		hardness = 42;
		damage = 2500;
		techLevel = 3;
		radioactivity = 1.5f;
		explosiveness = 1.5f;
		charge = 1.5f;
		flammability = 1.5f;
		cost = 2;
		color = Color.rgb(220, 20, 60);
		
	}};
	//t2  2反物质 2反重力陶瓷 = 3 建筑,结构, damage = 1500
	public StItem 土元素 = new StItem("土元素") {{
		hardness = 42;
		damage = 1500;
		techLevel = 3;
		radioactivity = 1.5f;
		explosiveness = 1.5f;
		charge = 1.5f;
		flammability = 1.5f;
		cost = 2;
		color = Color.rgb(255, 0, 255);
	}};
	///t4 高级科技, *, damage = 7500
	public StItem 光元素 = new StItem("光元素") {{
		hardness = 42;
		damage = 7500;
		techLevel = 4;
		color = Color.rgb(148, 243, 243);
		radioactivity = 4f;
		explosiveness = 4f;
		charge = 4f;
		flammability = 4f;
		cost = 4;
	}};
	
	
	///t3 高级科技, *, damage = 5000
	public StItem 暗元素 = new StItem("暗元素") {{
		damage = 5000;
		hardness = 42;
		techLevel = 4;
		color = Color.rgb(0, 0, 128);
		radioactivity = 2.5f;
		explosiveness = 2.5f;
		charge = 2.5f;
		flammability = 2.5f;
		cost = 2.5f;
	}};
}
