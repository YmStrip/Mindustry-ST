#pragma once
#include "KeyTransition.cc"
#include "KeyTransitionMode.cc"
#include <vector>
struct KeyItem {
	float key = 0.0f;
	auto rightTransition = l1;
	auto rightMode = easeInOut;
	std::vector<float> value{0.0f};
};
