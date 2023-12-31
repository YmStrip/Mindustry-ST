package st.addon.tech.provider;

import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.ctype.UnlockableContent;
import st.addon.tech.entity.STechBuilder;

@Provider
public class STechProvider extends LayerProvider {
	public STechBuilder tech(UnlockableContent item) {
		return new STechBuilder(item);
	}
}
