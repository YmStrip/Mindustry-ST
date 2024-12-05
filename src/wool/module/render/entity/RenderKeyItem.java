package wool.module.render.entity;

public class RenderKeyItem {
	float[] value;
	float key;
	@Override
	public String toString() {
		var data = new String[value.length];
		for (int i = 0; i < value.length; i++) {
			data[i] = value[i] + "";
		}
		return "K("+key+")|[" + String.join(",", data) + "]>";
	}
}
