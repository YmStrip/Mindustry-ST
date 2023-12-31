package st.addon.ui.provider.components;

import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.gen.Icon;
import mindustry.ui.dialogs.BaseDialog;

import java.util.ArrayList;
import java.util.HashMap;

@Provider
public class Tab extends LayerProvider {
	@FunctionalInterface
	public interface returnElement {
		void returnElement(Table t);
	}
	public class component extends BaseDialog {
		
		public class tab {
			public String name;
			public TextureRegionDrawable icon;
			public returnElement call;
			public boolean isTab = true;
		}
		
		public String index = "";
		public HashMap<String, returnElement> slots = new HashMap<>();
		
		public component(String title, DialogStyle style) {
			super(title, style);
			addCloseListener();
			shouldPause = false;
			resized(this::setup);
			shown(this::setup);
		}
		
		
		public void tab(Object[] data) {
			tabs.add(data);
		}
		
		public void tab(tab data) {
			tabs.add(data);
		}
		
		public ArrayList<Object> tabs = new ArrayList<>();
		
		public void slot(String name, returnElement t) {
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
				if (i instanceof tab t) {
					if (t.icon != null) buttons.button(t.name, t.icon, () -> {
						if (t.isTab) page(t.name);
						t.call.returnElement(cont);
					}).width(t.name.length() * 16);
					else buttons.button(t.name, () -> {
						if (t.isTab) page(t.name);
						t.call.returnElement(cont);
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
				call.returnElement(t);
			});
		}
	}
	
}
