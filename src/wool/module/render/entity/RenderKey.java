package wool.module.render.entity;


import wool.entity.JSON;
import wool.entity.Transition;

import java.util.ArrayList;
import java.util.HashMap;

public class RenderKey {
	public float min = -9999999.0f;
	public float max = 9999999.0f;
	private int size = 0;
	public String label = "Keyframe";
	public RenderKey label(String label) {
		this.label = label;
		return this;
	}
	public float[] defaultValue;
	public RenderKey() {
		this(0);
	}
	public RenderKey(float... def) {
		this.defaultValue = def;
		this.size = def.length;
		this.lock(def);
	}
	public boolean enable = true;
	public ArrayList<RenderKeyItem> keyframe = new ArrayList<>();
	public void lock(float... _arg) {
		this.clear();
		set(0, _arg);
	}
	public void set(float key, float... value) {
		clamp(value);
		var obj = new RenderKeyItem();
		obj.key = key;
		obj.value = clamp(value);
		if (keyframe.isEmpty()) {
			keyframe.add(obj);
			return;
		}
		var left = 0;
		var right = keyframe.size() - 1;
		while (left <= right) {
			var mid = (int) (left + right) / 2;
			var item = keyframe.get(mid);
			if (item.key == key) {
				keyframe.set(mid, obj);
				return;
			}
			if (item.key < key) {
				left = mid + 1;
			} else if (item.key > key) {
				right = mid - 1;
			}
		}
		keyframe.add(left, obj);
		//set mode
		if (left == 0) {
			var r = keyframe.get(left + 1);
			obj.rightMode = r.rightMode;
			obj.rightTransition = r.rightTransition;
		} else {
			var r = keyframe.get(left - 1);
			obj.rightMode = r.rightMode;
			obj.rightTransition = r.rightTransition;
		}
	}
	public void set(float key, float v) {
		this.set(key, new float[]{v});
	}
	public void set(float key, RenderKeyTransition t, RenderKeyMode m) {
		this.near(
			key,
			() -> null,
			(a) -> {
				a.rightMode = m;
				a.rightTransition = t;
				return null;
			},
			(a, b) -> {
				a.rightMode = m;
				a.rightTransition = t;
				return null;
			}
		);
	}

	public void set(float key, RenderKeyTransition t) {
		set(key, t, RenderKeyMode.easeInOut);
	}
	public float[] get(float key) {
		return this.near(
			key,
			() -> {
				return clamp(new float[size]);
			},
			(a) -> {
				return clamp(cache(key, a.value));
			},
			(a, b) -> {
				return clamp(cache(key, transition(key, a, b)));
			}
		);
	}
	public void del(float key) {
		if (keyframe.isEmpty()) return;
		var left = 0;
		var right = (keyframe.size()) - 1;
		while (left <= right) {
			var mid = (left + right) / 2;
			var item = keyframe.get(mid);
			if (item.key == key) {
				keyframe.remove(mid);
				return;
			}
			if (item.key < key) {
				left = mid + 1;
			} else if (item.key > key) {
				right = mid - 1;
			}
		}
	}
	public float[] clamp(float[] value) {
		var data = new float[size];
		for (int i = -1, size = value.length; ++i < size; ) {
			if (size - 1 >= i) {
				data[i] = Math.max(min, Math.min(max, value[i]));
			} else {
				data[i] = (Math.max(min, Math.min(max, value[i])));
			}
		}
		return data;
	}
	public void clamp() {
		for (var i : this.keyframe) {
			i.value = clamp(i.value);
		}
	}
	public void size(int size) {
		this.size = size;
		for (var i : keyframe) {
			clamp(i.value);
		}
	}
	public int size() {
		return size;
	}
	public void clear() {
		this.keyframe.clear();
		this.set(0, defaultValue);
	}
	public float[] cache(float key, float[] value) {
		return value;
	}
	//
	public float[] solve = new float[]{0, 0, 0};
	public void solve(float key) {
		this.solve = this.get(key);
	}
	public void solveAdd(RenderKey key) {
		key.forEach(((index, value, s) -> {
			if (this.solve != null && index >= 0 && index < this.solve.length) {
				this.solve[index] += value;
			}
		}));
	}
	//input shape [1] , transform pos
	//x = x cos + y sin
	//y = y cos - x sin
	public void solveRotateX(RenderKey y, RenderKey rotate) {
		if (rotate.solve == null || solve == null || y.solve == null) return;
		if (rotate.solve.length == 0 || solve.length == 0 || y.solve.length == 0) return;
		var rs = Math.toRadians(rotate.solve[0]);
		var xs = solve[0];
		var ys = y.solve[0];
		solve[0] = (float) (xs * Math.cos(rs) + ys * Math.sin(rs));
	}
	public void solveRotateY(RenderKey x, RenderKey rotate) {
		if (rotate.solve == null || solve == null || x.solve == null) return;
		if (rotate.solve.length == 0 || solve.length == 0 || x.solve.length == 0) return;
		var rs = Math.toRadians(rotate.solve[0]);
		var ys = solve[0];
		var xs = x.solve[0];
		solve[0] = (float) (ys * Math.cos(rs) - xs * Math.sin(rs));
	}
	public void solveRotateOffset(RenderKey rotate) {
		if (rotate.solve == null || solve == null) return;
		if (rotate.solve.length == 0 || solve.length < 2) return;
		var rs = Math.toRadians(rotate.solve[0]);
		var xs = solve[0];
		var ys = solve[1];
		solve[0] = (float) (xs * Math.cos(rs) + ys * Math.sin(rs));
		solve[1] = (float) (ys * Math.cos(rs) - xs * Math.sin(rs));
	}
	public void solveMul(RenderKey key) {
		key.forEach(((index, value, s) -> {
			if (this.solve != null && index >= 0 && index < this.solve.length) {
				this.solve[index] = value * this.solve[index];
			}
		}));
	}
	@FunctionalInterface
	public static interface forEach {
		void call(int index, float value, float[] solve);
	}
	public void forEach(forEach callback) {
		if (this.solve == null || this.solve.length == 0) {
			return;
		}
		for (int i = -1, l = this.solve.length; ++i < l; ) {
			callback.call(i, solve[i], solve);
		}
	}
	//
	public RenderKey copy() {
		var key = new RenderKey(defaultValue);
		return this.copy(key);
	}
	protected RenderKey copy(RenderKey key) {
		key.keyframe.clear();
		key.keyframe.addAll(this.keyframe);
		key.size = this.size;
		key.solve = this.solve;
		key.label = this.label;
		key.defaultValue = defaultValue;
		if (keyframe.isEmpty()) lock(defaultValue);
		return key;
	}
	@Override
	public String toString() {
		return JSON.stringify(json(), null, "    ");
	}
	//
	public HashMap<Object, Object> json() {
		return json(new HashMap<>());
	}
	public HashMap<Object, Object> json(HashMap<Object, Object> data) {
		var ch = new HashMap<>();
		data.put("Key(" + label + ")", ch);
		for (int i = -1, l = this.keyframe.size(); ++i < l; ) {
			ch.put(i + "", keyframe.get(i) + "");
		}
		var so = new ArrayList<String>();
		if (solve != null) {
			for (int i = 0; i < solve.length; ++i) {
				so.add(solve[i] + "");
			}
		}
		data.put("solve", "[" + String.join(",", so) + "]");
		return data;
	}
	@FunctionalInterface
	public interface nearFind0<T> {
		T call();
	}

	@FunctionalInterface
	public interface nearFind1<T> {
		T call(RenderKeyItem a);
	}

	@FunctionalInterface
	public interface nearFind2<T> {
		T call(RenderKeyItem a, RenderKeyItem b);
	}
	public <T> T near(
		float key,
		nearFind0<T> find0,
		nearFind1<T> find1,
		nearFind2<T> find2
	) {
		if (keyframe.isEmpty()) return find0.call();
		if (keyframe.size() == 1) return find1.call(keyframe.get(0));
		var z0 = keyframe.get(0);
		var z1 = keyframe.get(keyframe.size() - 1);
		if (key <= z0.key) {
			return find1.call(z0);
		}
		if (key >= z1.key) {
			return find1.call(z1);
		}
		var left = 0;
		var right = keyframe.size() - 1;
		while (left <= right) {
			var mid = (int) ((left + right) / 2);
			var item = keyframe.get(mid);
			if (item.key == key) {
				return find1.call(item);
			}
			if (item.key < key) {
				left = mid + 1;
			} else if (item.key > key) {
				right = mid - 1;
			}
		}
		var l = keyframe.get(right);
		var r = keyframe.get(left);
		return find2.call(l, r);
	}
	static float[] transition(float key, RenderKeyItem a, RenderKeyItem b) {
		var array = new float[a.value.length];

		var norm = (float) (key - a.key) / (b.key - a.key);
		for (int i = -1; ++i < a.value.length; ) {
			var start = a.value[i];
			var delta = b.value[i] - start;

			switch (a.rightTransition) {
				case l1:
					array[i] = (Transition.L1(norm) * delta + start);
					break;
				case l2:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.L2In(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.L2Out(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.L2InOut(norm) * delta + start);
					}
				case l3:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.L3In(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.L3Out(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.L3InOut(norm) * delta + start);
					}
				case l4:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.L4In(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.L4Out(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.L4InOut(norm) * delta + start);
					}
				case l5:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.L5In(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.L5Out(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.L5InOut(norm) * delta + start);
					}
				case exp:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.ExpIn(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.ExpOut(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.ExpInOut(norm) * delta + start);
					}
				case circ:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.CircIn(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.CircOut(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.CircInOut(norm) * delta + start);
					}
				case elastic:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.ElasticIn(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.ElasticOut(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.ElasticInOut(norm) * delta + start);
					}
				case back:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.BackIn(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.BackOut(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.BackInOut(norm) * delta + start);
					}
				case bounce:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.BounceIn(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.BounceOut(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.BounceOut(norm) * delta + start);
					}
				case sine:
					switch (a.rightMode) {
						case easeIn:
							array[i] = (Transition.SineIn(norm) * delta + start);
							break;
						case easeOut:
							array[i] = (Transition.SineOut(norm) * delta + start);
							break;
						default:
							array[i] = (Transition.SineInOut(norm) * delta + start);
					}
				default:
					array[i] = (Transition.L1(norm) * delta + start);
					break;
			}
		}
		return array;
	}
	public static float rotateX(float rad, float dx, float dy) {
		return (float) (dx * Math.cos(rad) - dy * Math.sin(rad));
	}
	public static float rotateY(float rad, float dx, float dy) {
		return (float) (dy * Math.cos(rad) + dx * Math.sin(rad));
	}
}
