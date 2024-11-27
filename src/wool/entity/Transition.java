package wool.entity;

public class Transition {
	public static float norm(float x, float a, float b) {
		return (x - a) / (b - a);
	}
	public static float l1(float norm, float a, float b) {
		return norm * (b - a) + a;
	}
	public static float l2(float norm, float a, float b) {
		return norm * norm * (b - a) + a;
	}
	public static float l3(float norm, float a, float b) {
		return norm * norm * norm * (b - a) + a;
	}
}
