<template>
	<div
		class="scrollbar"
		v-bind:class="{
			[position]: true
		}"
	>
		<div
			class="thumb"
			v-bind:class="{
				[position]: true
			}"
			v-bind:style="{
				clipPath: position=='y'? `polygon(${[
					`100% 0%`,
					`0 ${size}px`,
					`0 calc(100% - ${size}px)`,
					`100% 100%`
				].join(',')})`:``
			}"
			@mousedown="thumbTick?.()"
		/>
	</div>
</template>
<script lang="ts" setup>
import {ScrollbarProps} from "@/components/scroll/ScrollbarProps.ts";
import {computed} from "vue";
import {Color, transition} from "fyurry";

const props = withDefaults(defineProps<ScrollbarProps & {
}>(), {
	padding: 2,
	size: 6
})
const sizeSize = computed(() => props.size + 'px')
const sizeWidth = computed(() => `${props.height - props.padding * 2}px`)

const sizeThumb = computed(() => `${props.thumb * 100}%`)
const sizeThumbPos = computed(() => `${transition.mapLinearClamp(props.scroll, 0, 1, 0, 1 - props.thumb) * 100}%`)
</script>
<style lang="scss" scoped>
.scrollbar {
	position: relative;
	background: v-bind(colorFill);
	&.x {
		height: v-bind(sizeSize);
		width: v-bind(sizeWidth);
	}
	
	&.y {
		width: v-bind(sizeSize);
		height: v-bind(sizeWidth);
	}
}

.thumb {
	background: v-bind(colorThumb);
	position: absolute;
	
	&.x {
		height: 100%;
		width: v-bind(sizeThumb);
		left: v-bind(sizeThumbPos);
	}
	
	&.y {
		width: 100%;
		height: v-bind(sizeThumb);
		top: v-bind(sizeThumbPos);

	}
}
</style>