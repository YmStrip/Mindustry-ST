<template>
	<div
			v-bind:class="['absolute',{
				blur,
				flex,
				column,
				fullFlex,
				disabled
			}]"
			@mousedown="e=>ban(e)"
			@click="e=>ban(e)"
	>
		<slot></slot>
	</div>
</template>
<script lang="ts" setup>

import {LayoutAbstractProps} from "@/components/layout/LayoutAbstractProps.ts";
import {computed} from "vue";

const props = withDefaults(defineProps<LayoutAbstractProps>(), {
	zIndex: 1,
	width: '100%',
	height: '100%',
	shadow: 0
})
const Shadow = computed(() => `rgba(255,255,255,${props.shadow})`)
const ban = (e: UIEvent) => {
	if (props.disabled) {
		e.stopPropagation()
		e.stopImmediatePropagation()
		e.preventDefault()
	}
}
</script>
<style lang="scss" scoped>
.absolute {
	pointer-events: v-bind(pointer);
	position: absolute;
	z-index: v-bind(zIndex);
	width: v-bind(width);
	height: v-bind(height);
	left: v-bind(left);
	top: v-bind(top);
	right: v-bind(right);
	bottom: v-bind(bottom);
	align-items: v-bind(alignItems);
	justify-content: v-bind(justifyContent);
	background: v-bind(Shadow);

	&.blur {
		backdrop-filter: v-bind('`blur(${blur}px)`');
	}

	&.disabled {
		cursor: not-allowed;
	}

	&.flex {
		display: flex;
	}

	&.column {
		flex-direction: column;
	}

	&.fullFlex {
		flex: 1;
	}
}
</style>