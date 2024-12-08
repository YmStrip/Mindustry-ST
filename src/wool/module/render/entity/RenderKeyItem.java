package wool.module.render.entity;

public class RenderKeyItem {
	public float[] value;
	public float key;
	public RenderKeyTransition rightTransition = RenderKeyTransition.l1;
	public RenderKeyMode rightMode = RenderKeyMode.easeInOut;
	@Override
	public String toString() {
		var data = new String[value.length];
		for (int i = 0; i < value.length; i++) {
			data[i] = value[i] + "";
		}
		return "K(" + key + ")|[" + String.join(",", data) + "]>";
	}
}
