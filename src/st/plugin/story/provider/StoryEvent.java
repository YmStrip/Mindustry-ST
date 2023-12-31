package st.plugin.story.provider;

import arc.Events;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.Vars;
import mindustry.type.Sector;
import mindustry.type.SectorPreset;


@Provider
public class StoryEvent extends LayerProvider {
	@FunctionalInterface
	public interface Cons<A, B> {
		void call(A A, B B);
	}
	public <T> void on(SectorPreset sector, Class<T> cls, Cons<Sector, T> call) {
		Events.on(cls, e -> {
			if (Vars.state.rules.sector == null) return;
			if (Vars.state.rules.sector.preset == sector) {
				call.call(Vars.state.rules.sector, e);
			}
		});
	}
}
