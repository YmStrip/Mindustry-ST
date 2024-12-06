<template>
	<div
		class="point left-top"
		v-if="position=='left-top'"
		v-bind:style="{
			clipPath: `polygon(${[
				`100% 0`,
				`calc(100% - ${size}px) 0`,
				`0 100%`,
				`${size}px 100%`
				].join(',')})`,
		}"
	/>
	<div
		class="point right-top"
		v-else-if="position=='right-top'"
		v-bind:style="{
			clipPath: `polygon(${[
				`0 0`,
				`${size}px 0`,
				`100% 100%`,
				`calc(100% - ${size}px) 100%`
				].join(',')})`,
		}"
	/>
	<div
		class="point left-bottom"
		v-else-if="position=='left-bottom'"
		v-bind:style="{
			clipPath: `polygon(${[
				`0 0`,
				`${size}px 0`,
				`100% 100%`,
				`calc(100% - ${size}px) 100%`
				].join(',')})`,
		}"
	/>
	<div
		class="point right-bottom"
		v-else-if="position=='right-bottom'"
		v-bind:style="{
			clipPath: `polygon(${[
				`100% 0`,
				`calc(100% - ${size}px) 0`,
				`0 100%`,
				`${size}px 100%`
				].join(',')})`,
		}"
	/>
</template>
<script lang="ts" setup>
import {computed} from "vue";
import {PointProps} from "@/components/point/PointProps.ts";

const props = withDefaults(defineProps<PointProps & {
	extend?: boolean
}>(), {
	color: 'white',
	extend: true,
	size: 1,
	padding: 4,
	offset: 0
})


const sizePadding = computed(() => props.padding + 'px')
const sizeSize1 = computed(() =>0 + 'px')

</script>
<style lang="scss" scoped>
.point {
	pointer-events: none;
	position: absolute;
	border: none;
	background: v-bind(color);
	filter: v-bind("`blur(${-10*size+10})`");
	width: v-bind(sizePadding);
	height: v-bind(sizePadding);
	
	&.left-top {
		left: v-bind(sizeSize1);
		top: v-bind(sizeSize1);
	}
	
	&.left-bottom {
		left: v-bind(sizeSize1);
		bottom: v-bind(sizeSize1);
	}
	
	&.right-top {
		right: v-bind(sizeSize1);
		top: v-bind(sizeSize1);
	}
	
	&.right-bottom {
		right: v-bind(sizeSize1);
		bottom: v-bind(sizeSize1);
	}
}
</style>