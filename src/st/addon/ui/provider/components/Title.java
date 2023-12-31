package st.addon.ui.provider.components;

import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.gen.Tex;
@Provider
public class Title extends LayerProvider {
	public class component extends Table {
		public component(String name) {
			
			var t = new Label(name);
			t.setFontScale(1.1f);
			add(t).expandX().left().padLeft(5f);
			background(Tex.underline2);
		}
	}
}
