package wool.module.render.entity;

public class RenderIter {
	public float timeTotal;
	public float key = 0;
	//s0 = 0
	//s1 = 1
	//k = 60tick = 1000ms
	//g * time = k = 60tick
	public void iter(boolean add) {
		if (add) this.add();
		else this.sub();
	}
	public void add() {
		add(1 / 60f * (1000 / timeTotal));
	}
	public void add(float v) {
		key = clamp(key + v);
	}
	public void sub() {
		sub(1 / 60f * (1000 / timeTotal));
	}
	public void sub(float v) {
		key = clamp(key - v);
	}
	public void div(float v) {
		key = clamp(key / v);
	}
	public void mul(float v) {
		key = clamp(key * v);
	}
	public float clamp(float v) {
		return Math.max(0, Math.min(1, v));
	}
	public RenderIter() {
		this(1500);
	}
	public RenderIter(float time) {
		this.timeTotal = time;
	}
	public RenderIter copy() {
		return copy(new RenderIter());
	}
	protected RenderIter copy(RenderIter copy) {
		copy.timeTotal = timeTotal;
		copy.key = key;
		return copy;
	}
}
