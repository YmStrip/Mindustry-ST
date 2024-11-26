package wool.module.tech;

import mindustry.ctype.UnlockableContent;
import wool.module.tech.entity.Tech;
import wool.root.AppModule;

import java.util.HashMap;

public class tech extends AppModule {
    public static HashMap<String, Tech> tech = new HashMap();
    public static Tech get(UnlockableContent item) {
        var old = tech.get(item.name);
        if (old == null) {
            old = new Tech(item);
            tech.put(item.name, old);
        }
        return old;
    }
    public static void init() {}
    public static void deploy() {}
}
