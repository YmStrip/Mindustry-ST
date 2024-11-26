package wool.module.qt.entity;

import wool.entity.Register;

import java.util.HashMap;

public class QTContainer extends Register {

    public HashMap<String, Float> item = new HashMap<>();
    public HashMap<String, Float> liquid = new HashMap<>();
    public HashMap<String, Float> unit = new HashMap<>();

    @Override
    public Register configure(HashMap data) {
        super.configure(data);
        Register.configureData(item, (HashMap) data.get("item"));
        Register.configureData(liquid, (HashMap) data.get("liquid"));
        Register.configureData(unit, (HashMap) data.get("unit"));
        return this;
    }

    @Override
    public HashMap serialize() {
        var that = this;
        return assign(super.serialize(), new Object() {{
            item = Register.serializeDeep(that.item);
            liquid = Register.serializeDeep(that.liquid);
            unit = Register.serializeDeep(that.unit);
        }});
    }

    public String id;

    public QTContainer(String id) {
        this.id = id;
        setIgnoreKeys(new String[]{
                "item", "liquid", "unit"
        });
    }
}
