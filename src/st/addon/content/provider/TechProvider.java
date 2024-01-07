package st.addon.content.provider;

import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.ctype.UnlockableContent;
import st.addon.content.entity.STechBuilder;

@Provider
public class TechProvider extends LayerProvider {
	public STechBuilder tech(UnlockableContent item) {
		return new STechBuilder(item);
	}
}
