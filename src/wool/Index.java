package wool;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import wool.root.Application;

public class Index extends Mod {

    public Index() {
        var app = new Application();
        app.init();

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            app.deploy();
        });
    }

    @Override
    public void loadContent() {
        Log.info("Loading some example content.");
    }

}
