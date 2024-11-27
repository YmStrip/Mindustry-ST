package wool.root;

import wool.module.bullet.bullet;
import wool.module.drill.drill;
import wool.module.effect.effect;
import wool.module.fs;
import wool.module.item.item;
import wool.module.key.key;
import wool.module.product.product;
import wool.module.qt.qt;
import wool.module.tech.tech;
import wool.module.turret.turret;
import wool.module.unit.unit;

import java.util.ArrayList;

public class Application {
    public static ArrayList<wrapper> app = new ArrayList<>();

    @FunctionalInterface
    interface wrapper {
        void callback(boolean dep);
    }

    public void init() {
        for (var i = -1; ++i < app.size(); ) {
            var module = app.get(i);
            module.callback(false);
        }
    }

    public void deploy() {
        for (var i = -1; ++i < app.size(); ) {
            var module = app.get(i);
            module.callback(true);
        }
    }

    public Application() {
        //pre
        app.add((b) -> {
            if (b) {
                fs.deploy();
            } else {
                fs.init();
            }
        });
        //a-z
        app.add((b) -> {
            if (b) {
                bullet.deploy();
            } else {
                bullet.init();
            }
        });
        app.add((b) -> {
            if (b) {
                drill.deploy();
            } else {
                drill.init();
            }
        });
        app.add((b) -> {
            if (b) {
                effect.deploy();
            } else {
                effect.init();
            }
        });
        app.add((b) -> {
            if (b) {
                item.deploy();
            } else {
                item.init();
            }
        });
        app.add((b) -> {
            if (b) {
                key.deploy();
            } else {
                key.init();
            }
        });
        app.add((b) -> {
            if (b) {
                product.deploy();
            } else {
                product.init();
            }
        });
        app.add((b) -> {
            if (b) {
                qt.deploy();
            } else {
                qt.init();
            }
        });
        app.add((b) -> {
            if (b) {
                tech.deploy();
            } else {
                tech.init();
            }
        });
        app.add((b) -> {
            if (b) {
                turret.deploy();
            } else {
                turret.init();
            }
        });
        app.add((b) -> {
            if (b) {
                unit.deploy();
            } else {
                unit.init();
            }
        });
    }
}