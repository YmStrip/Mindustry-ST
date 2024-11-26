package wool.module.item;

import wool.module.item.entity.Item;
import wool.root.AppModule;

public class item extends AppModule {
    public static Item CNT;
    public static Item Antimatter;
    public static Item Chrominar;
    public static Item Kyrium;
    public static Item Nexarion;
    public static Item Skyforge;
    public static Item Superconductor;
    public static Item ElementMetal;
    public static Item ElementWood;
    public static Item ElementWater;
    public static Item ElementFire;
    public static Item ElementEarth;
    public static Item ElementLight;
    public static Item ElementDark;

    public static void init() {
        CNT = new Item("CNT") {{
            level = 1;
            damage = 35f;
            Color("#1b2865");
            explosiveness = 0f;
            flammability = .1f;
            radioactivity = 0f;
            charge = 0f;
            hardness = 2;
            diff();
        }};
        Antimatter = new Item("Antimatter") {{
            level = 1;
            damage = 20f;
            Color("#df2199");
            explosiveness = .8f;
            flammability = .1f;
            radioactivity = .5f;
            charge = .5f;
            hardness = 1;
            diff();
        }};
        Chrominar = new Item("Chrominar") {{
            level = 2;
            damage = 100f;
            Color("#b1ddf5");
            explosiveness = .0f;
            flammability = .0f;
            radioactivity = .0f;
            charge = .25f;
            hardness = 3;
            diff();
        }};
        Kyrium = new Item("Kyrium") {{
            level = 2;
            damage = 25f;
            Color("#daf5b1");
            explosiveness = .0f;
            flammability = .0f;
            radioactivity = .8f;
            charge = .8f;
            hardness = 1;
            diff();
        }};
        Nexarion = new Item("Nexarion") {{
            level = 2;
            damage = 20f;
            Color("#f5b7b1");
            explosiveness = .85f;
            flammability = .8f;
            radioactivity = 1.5f;
            charge = .5f;
            hardness = 2;
            diff();
        }};
        Skyforge = new Item("Skyforge") {{
            level = 2;
            damage = 70f;
            Color("#f5b7b1");
            explosiveness = 0f;
            flammability = .0f;
            radioactivity = 0f;
            charge = 0f;
            hardness = 1;
            diff();
        }};
        Superconductor = new Item("Superconductor") {{
            level = 2;
            damage = 30f;
            Color("#b5a293");
            explosiveness = 0f;
            flammability = 0f;
            radioactivity = 0f;
            charge = 2f;
            hardness = 1;
            diff();
        }};
        ElementMetal = new Item("ElementMetal") {{
            level = 3;
            damage = 250f;
            Color("#f6de29");
            explosiveness = 0f;
            flammability = 0f;
            radioactivity = 0f;
            charge = 0f;
            hardness = 5;
            diff();
        }};
        ElementWood = new Item("ElementWood") {{
            level = 3;
            damage = 100f;
            Color("#29f6a1");
            explosiveness = 0f;
            flammability = 0f;
            radioactivity = 0f;
            charge = 0f;
            hardness = 2;
            diff();
        }};
        ElementWater= new Item("ElementWater") {{
            level = 3;
            damage = 150f;
            Color("#29aef6");
            explosiveness = 0f;
            flammability = 0f;
            radioactivity = 0f;
            charge = 0f;
            hardness = 0;
            diff();
        }};
        ElementFire= new Item("ElementFire") {{
            level = 3;
            damage = 300f;
            Color("#f62929");
            explosiveness = 0f;
            flammability = 0f;
            radioactivity = 0f;
            charge = 0f;
            hardness = 0;
            diff();
        }};
        ElementEarth= new Item("ElementEarth") {{
            level = 3;
            damage = 250f;
            Color("#f629f6");
            explosiveness = 0f;
            flammability = 0f;
            radioactivity = 0f;
            charge = 0f;
            hardness = 5;
            diff();
        }};
        ElementLight= new Item("ElementLight") {{
            level = 4;
            damage = 500f;
            Color("#f9f9f9");
            explosiveness = 0f;
            flammability = 0f;
            radioactivity = 0f;
            charge = 0f;
            hardness = 0;
            diff();
        }};
        ElementDark= new Item("ElementDark") {{
            level = 4;
            damage = 450f;
            Color("#3f063f");
            explosiveness = 0f;
            flammability = 0f;
            radioactivity = 0f;
            charge = 0f;
            hardness = 0;
            diff();
        }};
    }

    public static void deploy() {

    }
}