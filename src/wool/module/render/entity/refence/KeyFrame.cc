#include <functional>
#include <utility>
#include <vector>
#include "KeyItem.cc"
#include "KeyTransition.cc";
#include "Transition.cc"
class KeyFrame {
private:
	auto size = 1;

public:
	std::vector<KeyItem> keyframe{};
	auto min = -9999999.0f;
	auto max = 9999999.0f;
	auto clamp(std::vector<float> value) {
		for (auto i = -1, size = value.size(); ++i < this->size;) {
			if (size - 1 >= i) {
				value[i] = std::max(min, std::min(max, value[i]));
			} else {
				value.push_back(std::max(min, std::min(max, 0.0f)));
			}
		}
		return value;
	};
	auto set(const float key, std::vector<float> value) {
		clamp(value);
		KeyItem obj{};
		obj.key = key;
		obj.value = std::move(value);
		if (keyframe.empty()) {
			keyframe.push_back(obj);
			return;
		}
		auto left = 0;
		auto right = keyframe.size() - 1;
		while (left <= right) {
			const auto mid = static_cast<int>(left + right) / 2;
			auto item = keyframe.at(mid);
			if (item.key == key) {
				keyframe[mid] = obj;
				return;
			}
			if (item.key < key) {
				left = mid + 1;
			} else if (item.key > key) {
				right = mid - 1;
			}
		}
		keyframe.insert(keyframe.begin() + left, obj);
		//set mode
		if (left == 0) {
			auto r = keyframe.at(left + 1);
			obj.rightMode = r.rightMode;
			obj.rightTransition = r.rightTransition;
		} else {
			auto r = keyframe.at(left - 1);
			obj.rightMode = r.rightMode;
			obj.rightTransition = r.rightTransition;
		}
	}
	auto set(const float key, const float v) {
		const std::vector vv{v};
		return this->set(key, vv);
	}
	auto set(const float key, const KeyTransition t, const KeyTransitionMode m) {
		return near<void>(
			key,
			[]() {
			},
			[m, t](const KeyItem &a) {
				a.rightMode = m;
				a.rightTransition = t;
			},
			[m,t](const KeyItem &a, const KeyItem &b) {
				a.rightMode = m;
				a.rightTransition = t;
			}
		);
	}
	auto set(const float key, const KeyTransition t) {
		set(key, t, KeyTransitionMode::easeInOut);
	}
	auto del(const float key) {
		if (keyframe.empty()) return;
		auto left = 0;
		auto right = (keyframe.size()) - 1;
		while (left <= right) {
			const auto mid = (left + right) / 2;
			const auto item = keyframe.at(mid);
			if (item.key == key) {
				keyframe.erase(keyframe.begin() + mid);
				return;
			}
			if (item.key < key) {
				left = mid + 1;
			} else if (item.key > key) {
				right = mid - 1;
			}
		}
	}
	auto get(const float key) {
		return near<std::vector<float> >(
			key,
			[this]() {
				return clamp(std::vector{0.0f});
			},
			[this,key](const KeyItem &a) {
				return clamp(cache(key, a.value));
			},
			[this,key](const KeyItem &a, const KeyItem &b) {
				return clamp(cache(key, transition(key, a, b)));
			}
		);
	}

	template<typename T>
	T near(
		const float key,
		std::function<T()> find0,
		std::function<T(KeyItem a)> find1,
		std::function<T(KeyItem a, KeyItem b)> find2
	) {
		if (keyframe.empty()) return find0();
		if (keyframe.size() == 1) return find1(keyframe.at(0));
		auto z0 = keyframe.at(0);
		auto z1 = keyframe.at(keyframe.size() - 1);
		if (key <= z0.key) {
			return find1(z0);
		}
		if (key >= z1.key) {
			return find1(z0);
		}
		auto left = 0;
		auto right = keyframe.size() - 1;
		while (left <= right) {
			auto mid = (int) ((left + right) / 2);
			auto item = keyframe.at(mid);
			if (item.key == key) {
				return find1(item);
			}
			if (item.key < key) {
				left = mid + 1;
			} else if (item.key > key) {
				right = mid - 1;
			}
		}
		auto l = keyframe.at(right);
		auto r = keyframe.at(left);
		return find2(l, r);
	}
	auto sizeSet(const int size = 1) {
		this->size = size;
		for (const auto &[key, rightTransition, rightMode, value]: keyframe) {
			clamp(value);
		}
	}
	auto sizeGet() {
		return size;
	}
	auto cache(float key, std::vector<float> value) {
		return value;
	}
	static auto transition(const float key, const KeyItem &a, const KeyItem &b) {
		auto vec = std::vector<float>();
		const auto norm = (key - a.key) / (b.key - a.key);
		for (auto i = -1; ++i < size;) {
			const auto start = a.value.at(i);
			const auto delta = b.value.at(i) - start;

			switch (a.rightTransition) {
				case KeyTransition::l1:
					vec.push_back(Transition::L1(norm) * delta + start);
					break;
				case KeyTransition::l2:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::L2In(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::L2Out(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::L2InOut(norm) * delta + start);
					}
				case KeyTransition::l3:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::L3In(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::L3Out(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::L3InOut(norm) * delta + start);
					}
				case KeyTransition::l4:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::L4In(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::L4Out(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::L4InOut(norm) * delta + start);
					}
				case KeyTransition::l5:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::L5In(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::L5Out(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::L5InOut(norm) * delta + start);
					}
				case KeyTransition::exp:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::ExpIn(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::ExpOut(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::ExpInOut(norm) * delta + start);
					}
				case KeyTransition::circ:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::CircIn(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::CircOut(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::CircInOut(norm) * delta + start);
					}
				case KeyTransition::elastic:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::ElasticIn(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::ElasticOut(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::ElasticInOut(norm) * delta + start);
					}
				case KeyTransition::back:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::BackIn(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::BackOut(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::BackInOut(norm) * delta + start);
					}
				case KeyTransition::bounce:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::BounceIn(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::BounceOut(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::BounceOut(norm) * delta + start);
					}
				case KeyTransition::sine:
					switch (a.rightMode) {
						case easeIn:
							vec.push_back(Transition::SineIn(norm) * delta + start);
							break;
						case easeOut:
							vec.push_back(Transition::SineOut(norm) * delta + start);
							break;
						default:
							vec.push_back(Transition::SineInOut(norm) * delta + start);
					}
				default:
					vec.push_back(Transition::L1(norm) * delta + start);
					break;;
			}
		}
		return vec;
	}
};
