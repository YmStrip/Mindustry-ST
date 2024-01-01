package st.provider.ui.provider.views;

import arc.Core;
import arc.Input;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.gen.Building;

@Provider
public class MessageUI extends LayerProvider {
	public class component {
		
		public component(Building building, String texts) {
			Core.input.getTextInput(new Input.TextInput() {{
				text = texts + "";
				multiline = true;
				maxLength = 99999;
				accepted = str -> {
					if (!str.equals(text)) building.configure(str);
				};
			}});
		}
	}
}
