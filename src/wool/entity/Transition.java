package wool.entity;

public class Transition {
	public static float M_PI = (float) Math.PI;
	public static float L1(float x) {
		return x;
	}
	public static float L2In(float x) {
		return x * x;
	}
	public static float L2Out(float x) {
		return 1 - (1 - x) * (1 - x);
	}
	public static float L2InOut(float x) {
		return x < 0.5 ? 2 * x * x : 1 - (float) Math.pow(-2 * x + 2, 2) / 2;
	}
	public static float L3In(float x) {
		return (float) Math.pow(x, 3);
	}
	public static float L3Out(float x) {
		return 1 - (float) Math.pow(1 - x, 3);
	}
	public static float L3InOut(float x) {
		return x < 0.5 ? 4 * (float) Math.pow(x, 3) : 1 - (float) Math.pow(-2 * x + 2, 3) / 2;
	}
	public static float L4In(float x) {
		return (float) Math.pow(x, 4);
	}
	public static float L4Out(float x) {
		return 1 - (float) Math.pow(1 - x, 4);
	}
	public static float L4InOut(float x) {
		return x < 0.5 ? 8 * (float) Math.pow(x, 4) : 1 - (float) Math.pow(-2 * x + 2, 4) / 2;
	}
	public static float L5In(float x) {
		return (float) Math.pow(x, 5);
	}
	public static float L5Out(float x) {
		return 1 - (float) Math.pow(1 - x, 5);
	}
	public static float L5InOut(float x) {
		return x < 0.5 ? 16 * (float) Math.pow(x, 5) : 1 - (float) Math.pow(-2 * x + 2, 5) / 2;
	}
	public static float ExpIn(float x) {
		return x == 0 ? 0 : (float) Math.pow(2, 10 * x - 10);
	}
	public static float ExpOut(float x) {
		return x == 1 ? 1 : 1 - (float) Math.pow(2, -10 * x);
	}
	public static float ExpInOut(float x) {
		return x == 0
			? 0
			: x == 1
			? 1
			: x < 0.5
			? (float) Math.pow(2, 20 * x - 10) / 2
			: (2 - (float) Math.pow(2, -20 * x + 10)) / 2;
	}
	public static float CircIn(float x) {
		return 1 - (float) Math.sqrt(1 - (float) Math.pow(x, 2));
	}
	public static float CircOut(float x) {
		return (float) Math.sqrt(1 - (float) Math.pow(x - 1, 2));
	}
	public static float CircInOut(float x) {
		return x < 0.5
			? (1 - (float) Math.sqrt(1 - (float) Math.pow(2 * x, 2))) / 2
			: ((float) Math.sqrt(1 - (float) Math.pow(-2 * x + 2, 2)) + 1) / 2;
	}
	public static float BackIn(float x) {
		var c1 = 1.70158;
		var c3 = c1 + 1;

		return (float) (c3 * x * x * x - c1 * x * x);
	}
	public static float BackOut(float x) {
		var c1 = 1.70158;
		var c3 = c1 + 1;

		return (float) (1 + c3 * (float) Math.pow(x - 1, 3) + c1 * (float) Math.pow(x - 1, 2));
	}
	public static float BackInOut(float x) {
		var c1 = 1.70158;
		var c2 = c1 * 1.525;

		return (float) (x < 0.5
			? ((float) Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2
			: ((float) Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2);
	}
	public static float ElasticIn(float x) {
		var c4 = (2 * M_PI) / 3;

		return x == 0
			? 0
			: (float) (x == 1
			? 1
			: -(float) Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * c4));
	}
	public static float ElasticOut(float x) {
		var c4 = (2 * M_PI) / 3;

		return x == 0
			? 0
			: (float) (x == 1
			? 1
			: (float) Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1);
	}
	public static float ElasticInOut(float x) {
		var c5 = (2 * M_PI) / 4.5;

		return x == 0
			? 0
			: (float) (x == 1
			? 1
			: x < 0.5
			? -((float) Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * c5)) / 2
			: ((float) Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * c5)) / 2 + 1);
	}
	public static float BounceIn(float x) {
		return 1 - BounceOut(1 - x);
	}
	public static float BounceOut(float x) {
		var n1 = 7.5625;
		var d1 = 2.75;

		if (x < 1 / d1) {
			return (float) (n1 * x * x);
		}
		if (x < 2 / d1) {
			return (float) (n1 * (x -= 1.5 / d1) * x + 0.75);
		}
		if (x < 2.5 / d1) {
			return (float) (n1 * (x -= 2.25 / d1) * x + 0.9375);
		}
		return (float) (n1 * (x -= 2.625 / d1) * x + 0.984375);
	}
	public static float BounceInOut(float x) {
		return x < 0.5
			? (1 - BounceOut(1 - 2 * x)) / 2
			: (1 + BounceOut(2 * x - 1)) / 2;
	}
	public static float SineIn(float x) {
		return (float) (1 - Math.cos((x * M_PI) / 2));
	}
	public static float SineOut(float x) {
		return (float) Math.sin((x * M_PI) / 2);
	}
	public static float SineInOut(float x) {
		return (float) (-(Math.cos(M_PI * x) - 1) / 2);
	}
}
