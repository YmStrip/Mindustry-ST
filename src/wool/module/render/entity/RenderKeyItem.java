package wool.module.render.entity;

public class RenderKeyItem {
	public static String name(RenderKeyTransition transition) {
		switch (transition) {
			case l2 -> {
				return "l2";
			}
			case l3 -> {
				return "l3";
			}
			case l4 -> {
				return "l4";
			}
			case l5 -> {
				return "l5";
			}
			case exp -> {
				return "exp";
			}
			case back -> {
				return "back";
			}
			case circ -> {
				return "circ";
			}
			case sine -> {
				return "sine";
			}
			case bounce -> {
				return "bounce";
			}
			case elastic -> {
				return "elastic";
			}
			default -> {
				return "l1";
			}
		}
	}
	public static String name(RenderKeyMode mode) {
		switch (mode) {
			case easeIn -> {
				return "easeIn";
			}
			case easeOut -> {
				return "easeOut";
			}
			default -> {
				return "easeInOut";
			}
		}
	}
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
		var point = "[" + String.join(",", data) + "]";
		return name(rightTransition) + "." + name(rightMode) + "(" + key + ") -> " + point;
	}
}
