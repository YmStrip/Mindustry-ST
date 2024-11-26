package wool.module.tech.entity;

import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;
import wool.entity.Register;
import wool.module.tech.tech;

import java.util.ArrayList;
import java.util.HashMap;

public class Tech extends Register {
    public String id;
    public ArrayList<Object> requireList = new ArrayList<>();
    public UnlockableContent item;
    //cache S(n) -> S(n^2+)
    public HashMap<String, Tech> parents = new HashMap<>();
    public ArrayList<Tech> childs = new ArrayList<>();
    public TechTree.TechNode node;
    public Tech parent;

    private Tech parentDeep(Tech tech) {
        parents.put(tech.id, tech);
        for (Tech child : childs) {
            child.parentDeep(tech);
        }
        return this;
    }

    public Tech parent(Tech t) {
        if (parents.containsKey(t.id)) return this;
        if (t.childs.contains(t)) return this;
        t.childs.add(t);
        t.parent = this;
        t.parentDeep(this);
        new TechTree.TechNode(item.techNode, this.item, this.item.researchRequirements());
        this.diff();
        return this;
    }

    public Tech parent(UnlockableContent item) {
        var t = tech.get(item);
        return parent(t);
    }

    public Tech child(Tech tech) {
        tech.parent(tech);
        return this;
    }

    public Tech child(UnlockableContent item) {
        var t = tech.get(item);
        return child(t);
    }

    @FunctionalInterface
    interface Ch {
        void callback(Tech t);
    }

    public Tech child(Tech tech, Ch ch) {
        tech.parent(tech);
        ch.callback(tech);
        return this;
    }

    public Tech child(UnlockableContent item, Ch ch) {
        var t = tech.get(item);
        return child(t, ch);
    }

    public Tech(UnlockableContent item) {
        id = item.name;
        this.item = item;
    }

    public Tech require(UnlockableContent item) {
        this.requireList.add(item);
        return this.diff();
    }

    public Tech require(Objectives.Objective item) {
        this.requireList.add(item);
        return this.diff();
    }

    public Tech require(ItemStack[] item) {
        this.requireList.add(item);
        return this.diff();
    }
    //diff (list) -> node
    public Tech diff() {
        if (this.node == null) return this;
        for (Object o : requireList) {
            if (o instanceof UnlockableContent) {
                node.objectives.add(new Objectives.Research(item));
            } else if (o instanceof ItemStack[]) {
                node.requirements = (ItemStack[]) o;
            } else if (o instanceof Objectives.Objective) {
                node.objectives.add((Objectives.Objective) o);
            }
        }
        requireList.clear();
        return this;
    }
}
