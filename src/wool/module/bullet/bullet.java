package wool.module.bullet;

import wool.module.bullet.entity.Bullet;
import wool.module.key.entity.KeyContainer;
import wool.root.AppModule;

import java.util.HashMap;

public class bullet extends AppModule {
    public static HashMap<String, Class<Bullet>> bullet = new HashMap<>();
    public static KeyContainer bulletKey = new KeyContainer();


    public static void init() {

    }

    public static void deploy() {

    }
}
