package wool.module.render.entity;


import wool.entity.JSON;
import wool.entity.Transition;

import java.util.ArrayList;
import java.util.HashMap;

public class RenderKey {
	//min count of a item
	public enum transitions {
		l1, l2, l3
	}

	public transitions transition = transitions.l1;

	public enum eases {
		easeInOut,
	}

	public eases ease = eases.easeInOut;
	public int count = 0;
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
		this.count = def.length;
		this.same(def);
	}
	public boolean enable = true;
	public ArrayList<RenderKeyItem> keyframe = new ArrayList<>();
	public void same(float... _arg) {
		this.clear();
		set(0, _arg);
	}
	public void set(float _key, float... _arg) {
		//clamp
		if (_arg.length < count) {
			//push 0
			var newArr = new float[count];
			for (int i = -1; ++i < count; ) {
				if (i <= _arg.length - 1) {
					newArr[i] = _arg[i];
				} else {
					newArr[i] = 0;
				}
			}
			_arg = newArr;
		}
		float[] final_arg = _arg;
		var obj = new RenderKeyItem() {{
			this.key = _key;
			this.value = final_arg;
		}};
		if (this.keyframe.isEmpty()) {
			this.keyframe.add(obj);
			return;
		}
		var left = 0;
		var right = keyframe.size() - 1;
		while (left <= right) {
			var mid = (int) ((left + right) / 2);
			var item = this.keyframe.get(mid);
			if (item.key == _key) {
				this.keyframe.set(mid, obj);
				return;
			} else if (item.key < _key) {
				left = mid + 1;
			} else if (item.key > _key) {
				right = mid - 1;
			}
		}
		keyframe.add(left, obj);
	}
	public float[] get(float key) {
		if (this.keyframe.size() == 1) {
			return transform(this.keyframe.get(0).value);
		}
		if (this.keyframe.isEmpty()) {
			var arr = new float[count];
			for (int i = -1; ++i < count; ) {
				arr[i] = 0;
			}
			return transform(arr);
		}
		var z0 = this.keyframe.get(0);
		var z1 = this.keyframe.get(this.keyframe.size() - 1);
		if (key <= z0.key) {
			return transform(z0.value);
		} else if (key >= z1.key) {
			return transform(z1.value);
		}
		var left = 0;
		var right = keyframe.size() - 1;
		while (left <= right) {
			var mid = (int) ((left + right) / 2);
			var item = this.keyframe.get(mid);
			if (item.key == key) {
				return transform(item.value);
			} else if (right - left == 1) {
				break;
			} else if (item.key < key) {
				left = mid + 1;
			} else if (item.key > key) {
				right = mid - 1;
			}
		}
		var l = this.keyframe.get(right);
		var r = this.keyframe.get(left);
		var array = new float[l.value.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = transition(key, l.value[i], r.value[i]);
		}
		return transform(array);
	}
	public void del(float key) {
		if (this.keyframe.isEmpty()) {
			return;
		}
		var left = 0;
		var right = keyframe.size() - 1;
		while (left < right) {
			var mid = (int) ((left + right) / 2);
			var item = this.keyframe.get(mid);
			if (item.key == key) {
				this.keyframe.remove(mid);
				return;
			} else if (item.key < key) {
				left = mid + 1;
			} else if (item.key > key) {
				right = mid - 1;
			}
		}
	}
	public float[] transform(float[] value) {
		var newArr = new float[value.length];
		System.arraycopy(value, 0, newArr, 0, value.length);
		return newArr;
	}
	public boolean has() {
		return this.enable && !this.keyframe.isEmpty();
	}
	public float transition(float norm, float a, float b) {
		switch (transition) {
			case l1 -> {
				return Transition.l1(norm, a, b);
			}
			case l2 -> {
				return Transition.l2(norm, a, b);
			}
			case l3 -> {
				return Transition.l3(norm, a, b);
			}
		}
		return Transition.l1(norm, a, b);
	}
	public void clear() {
		this.keyframe.clear();
		this.set(0, defaultValue);
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
		key.count = this.count;
		key.solve = this.solve;
		key.label = this.label;
		key.transition = this.transition;
		key.ease = this.ease;
		key.defaultValue = defaultValue;
		if (keyframe.isEmpty()) same(defaultValue);
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
}
