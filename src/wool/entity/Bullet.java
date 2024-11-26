package wool.entity;

import java.util.ArrayList;

public class Bullet extends Register {
    public Color color = new Color("#ffffff");
    //r/s
    public float rate = 1f;
    //block
    public float width = 0.5f;
    //block
    public float range = 30f;
    //damage total
    public float damage = 50f;
    //exist time (ms)
    public float time = 2000;
    //
    public ArrayList<Bullet> child = new ArrayList<>();

    //grad
    public mindustry.gen.Bullet backward(ArrayList<mindustry.gen.Bullet> child) {
        return null;
    }

    //grad
    public void forward() {

    }

    public void build() {
        this.forward();
    }
}
