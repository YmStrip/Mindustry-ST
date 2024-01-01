package st.addon.prod.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import st.addon.content.SContent;
import st.addon.content.provider.TechProvider;
import st.addon.prod.provider.T1Prods;
import st.addon.prod.provider.T2Prods;
import st.addon.prod.provider.T3Prods;
import st.addon.prod.provider.T4Prods;

@Controller
public class ProdTech extends LayerController {
	@Require(cls = T1Prods.class)
	T1Prods t1;
	@Require(cls = T2Prods.class)
	T2Prods t2;
	@Require(cls = T3Prods.class)
	T3Prods t3;
	@Require(cls = T4Prods.class)
	T4Prods t4;
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	
	public void inject_mod() {
		tech
			.tech(t1.纳米碳管构建厂)
			.parent(Blocks.siliconSmelter)
			.child(t1.超导体构建厂, c -> c
				.child(t1.超导石墨厂, x -> x
					.inChild(t1.超导玻璃厂)
					.inChild(t1.超导硅厂)
					.child(t1.超导塑钢厂, x1 -> x1
						.inChild(t1.超导相位物厂)
						.inChild(t1.超导合金厂))
					.child(t1.超导冷冻液厂)
					.child(t1.超导硫厂, x1 -> x1
						.inChild(t1.超导爆炸厂)))
				.child(t2.反物质构造厂, _0 -> _0
					.child(t2.辐矿石构造厂))
				.child(t2.反重力陶瓷构造厂, _0 -> _0
					.child(t2.铬纳尔构造厂, _1 -> _1
						.child(t2.晶金构造厂))))
			.child(t3.金元素构造厂, _0 -> _0
				.inChild(t3.木元素构造厂)
				.inChild(t3.水元素构造厂)
				.inChild(t3.火元素构造厂)
				.inChild(t3.土元素构造厂)
				.inChild(t4.究极纳米元素融合机)
				.child(t4.纳米元素流体制造机)
				.child(t4.虚空提取仪))
		;
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, t -> {
			inject_mod();
		});
	}
}
