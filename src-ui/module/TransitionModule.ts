import {AppModule} from "fyurry";

export class TransitionModule extends AppModule {
	norm(x: number, a: number, b: number) {
		return (x - a) / (b - a)
	}
	normClamp(x: number, a: number, b: number) {
		return this.clamp((x - a) / (b - a), 0, 1)
	}
	mapLinear(x: number, a: number, b: number, c: number, d: number) {
		return this.linear(this.norm(x, a, b), c, d)
	}
	mapLinearClamp(x: number, a: number, b: number, c: number, d: number) {
		return this.linear(this.normClamp(x, a, b), c, d)
	}
	linear(x: number, a: number, b: number) {
		return a + b * x
	}
	l2(x: number, a: number, b: number) {
		return a + b * x ** 2
	}
	l3(x: number, a: number, b: number) {
		return a + b * x ** 3
	}
	l4(x: number, a: number, b: number) {
		return a + b * x ** 4
	}
	clamp(x: number, a: number, b: number) {
		return Math.max(Math.min(x, b), a)
	}
}