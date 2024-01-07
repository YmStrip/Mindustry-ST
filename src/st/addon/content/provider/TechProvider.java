package st.addon.content.provider;

import arc.Events;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import mindustry.type.Item;
import st.addon.content.entity.STechBuilder;

@Provider
public class TechProvider extends LayerProvider {
	public STechBuilder tech(UnlockableContent item) {
		return new STechBuilder(item);
	}
	
	public UnlockableContent root = new Item("welcome"){{
		alwaysUnlocked = true;
	}};
	public TechTree.TechNode techTree = TechTree.nodeRoot("st", root, () -> {
	});
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
		
		});
	}
}
