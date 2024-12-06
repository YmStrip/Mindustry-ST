#include <valarray>
#include <math.h>
class Transition {
public:
	static float L1(const float x) {
		return x;
	}
	static float L2In(const float x) {
		return x * x;
	}
	static float L2Out(const float x) {
		return 1 - (1 - x) * (1 - x);
	}
	static float L2InOut(const float x) {
		return x < 0.5 ? 2 * x * x : 1 - pow(-2 * x + 2, 2) / 2;
	}
	static float L3In(const float x) {
		return pow(x, 3);
	}
	static float L3Out(const float x) {
		return 1 - pow(1 - x, 3);
	}
	static float L3InOut(const float x) {
		return x < 0.5 ? 4 * pow(x, 3) : 1 - pow(-2 * x + 2, 3) / 2;
	}
	static float L4In(const float x) {
		return pow(x, 4);
	}
	static float L4Out(const float x) {
		return 1 - pow(1 - x, 4);
	}
	static float L4InOut(const float x) {
		return x < 0.5 ? 8 * pow(x, 4) : 1 - pow(-2 * x + 2, 4) / 2;
	}
	static float L5In(const float x) {
		return pow(x, 5);
	}
	static float L5Out(const float x) {
		return 1 - std::pow(1 - x, 5);
	}
	static float L5InOut(const float x) {
		return x < 0.5 ? 16 * pow(x, 5) : 1 - pow(-2 * x + 2, 5) / 2;
	}
	static float ExpIn(const float x) {
		return x == 0 ? 0 : pow(2, 10 * x - 10);
	}
	static float ExpOut(const float x) {
		return x == 1 ? 1 : 1 - pow(2, -10 * x);
	}
	static float ExpInOut(const float x) {
		return x == 0
			       ? 0
			       : x == 1
				         ? 1
				         : x < 0.5
					           ? pow(2, 20 * x - 10) / 2
					           : (2 - pow(2, -20 * x + 10)) / 2;
	}
	static float CircIn(const float x) {
		return 1 - sqrt(1 - pow(x, 2));
	}
	static float CircOut(const float x) {
		return sqrt(1 - pow(x - 1, 2));
	}
	static float CircInOut(const float x) {
		return x < 0.5
			       ? (1 - sqrt(1 - pow(2 * x, 2))) / 2
			       : (sqrt(1 - pow(-2 * x + 2, 2)) + 1) / 2;
	}
	static float BackIn(const float x) {
		auto c1 = 1.70158;
		auto c3 = c1 + 1;

		return c3 * x * x * x - c1 * x * x;
	}
	static float BackOut(const float x) {
		auto c1 = 1.70158;
		auto c3 = c1 + 1;

		return 1 + c3 * pow(x - 1, 3) + c1 * pow(x - 1, 2);
	}
	static float BackInOut(const float x) {
		auto c1 = 1.70158;
		auto c2 = c1 * 1.525;

		return x < 0.5
			       ? (pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2
			       : (pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2;
	}
	static float ElasticIn(const float x) {
		auto c4 = (2 * M_PI) / 3;

		return x == 0
			       ? 0
			       : x == 1
				         ? 1
				         : -pow(2, 10 * x - 10) * sin((x * 10 - 10.75) * c4);
	}
	static float ElasticOut(const float x) {
		auto c4 = (2 * M_PI) / 3;

		return x == 0
			       ? 0
			       : x == 1
				         ? 1
				         : pow(2, -10 * x) * sin((x * 10 - 0.75) * c4) + 1;
	}
	static float ElasticInOut(const float x) {
		auto c5 = (2 * M_PI) / 4.5;

		return x == 0
			       ? 0
			       : x == 1
				         ? 1
				         : x < 0.5
					           ? -(pow(2, 20 * x - 10) * sin((20 * x - 11.125) * c5)) / 2
					           : (pow(2, -20 * x + 10) * sin((20 * x - 11.125) * c5)) / 2 + 1;
	}
	static float BounceIn(const float x) {
		return 1 - BounceOut(1 - x);
	}
	static float BounceOut(float x) {
		auto n1 = 7.5625;
		auto d1 = 2.75;

		if (x < 1 / d1) {
			return n1 * x * x;
		}
		if (x < 2 / d1) {
			return n1 * (x -= 1.5 / d1) * x + 0.75;
		}
		if (x < 2.5 / d1) {
			return n1 * (x -= 2.25 / d1) * x + 0.9375;
		}
		return n1 * (x -= 2.625 / d1) * x + 0.984375;
	}
	static float BounceInOut(const float x) {
		return x < 0.5
			       ? (1 - BounceOut(1 - 2 * x)) / 2
			       : (1 + BounceOut(2 * x - 1)) / 2;
	}
	static float SineIn(const float x) {
		return 1 - cos((x * M_PI) / 2);
	}
	static float SineOut(float x) {
		return sin((x * M_PI) / 2);
	}
	static float SineInOut(const float x) {
		return -(cos(M_PI * x) - 1) / 2;
	}
};
