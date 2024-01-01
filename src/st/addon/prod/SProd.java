package st.addon.prod;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.addon.prod.controller.ProdTech;
import st.addon.prod.provider.*;

@Module
public class SProd extends LayerModule {
	public SProd() {
		imports(
			SContent.class
		);
		provider(
			new ProdPreset(),
			new T1Prods(),
			new T2Prods(),
			new T3Prods(),
			new T4Prods()
		);
		controller(
			new ProdTech()
		);
	}
}
