package st.addon.content.provider;

import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.dialogs.BaseDialog;

import java.util.ArrayList;
import java.util.HashMap;

@Provider
public class UIProvider extends LayerProvider {
	@FunctionalInterface
	public interface render {
		void render(Table table);
	}
	@Require(cls = UIProvider.class)
	UIProvider uis;
	public class tab extends BaseDialog {
		public tab(String title, DialogStyle style) {
			super(title, style);
			addCloseListener();
			shouldPause = false;
			resized(this::setup);
			shown(this::setup);
		}
		public class tabInfo {
			public String name;
			public TextureRegionDrawable icon;
			public boolean isTab = true;
			render call = (t) -> {};
		}
		public String index = "";
		public HashMap<String, render> slots = new HashMap<>();
		
		public void tab(Object[] data) {
			tabs.add(data);
		}
		
		public void tab(tabInfo data) {
			tabs.add(data);
		}
		
		public ArrayList<Object> tabs = new ArrayList<>();
		
		public void slot(String name, render t) {
			slots.put(name, t);
		}
		
		void setup() {
			clearChildren();
			tabs();
			add(buttons).expandX().center();
			row();
			add(cont).expand().fillX().center();
			page(index);
		}
		
		void tabs() {
			buttons.clearChildren();
			buttons.button("@close", Icon.left, this::hide);
			for (var i : tabs) {
				if (i instanceof tabInfo t) {
					if (t.icon != null) buttons.button(t.name, t.icon, () -> {
						if (t.isTab) page(t.name);
						t.call.render(cont);
					}).width(t.name.length() * 16);
					else buttons.button(t.name, () -> {
						if (t.isTab) page(t.name);
						t.call.render(cont);
					}).width(t.name.length() * 16 + 40);
					continue;
				}
				if (i instanceof Object[] t) {
					if (t.length == 0) {
						continue;
					}
					if (t.length == 1) {
						buttons.button((String) t[0], () -> page((String) t[0]));
						continue;
					}
					if (t.length == 2) {
						buttons.button((String) t[0], (TextureRegionDrawable) t[1], () -> page((String) t[0]));
						continue;
					}
				}
				if (i instanceof String t) {
					buttons.button(t, () -> page(t));
				}
			}
		}
		
		void page(String name) {
			cont.clearChildren();
			cont.pane(t -> {
				var call = slots.get(name);
				if (call == null) return;
				call.render(t);
			});
		}
	}
	public class title extends Table {
		public title(String name) {
			
			var t = new Label(name);
			t.setFontScale(1.1f);
			add(t).expandX().left().padLeft(5f);
			background(Tex.underline2);
		}
	}
}
