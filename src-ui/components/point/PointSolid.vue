<template>
	<div
		class="point"
		v-bind:class="{
			[position]:true
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
	padding: 3,
	offset: 0
})

const sizeSize = computed(() => props.size + 'px')
const sizePadding = computed(() => props.padding + 'px')

</script>
<style lang="scss" scoped>
.point {
	pointer-events: none;
	position: absolute;
	background: none;
	border: none;
	&.left-top {
		left: 0;
		top: 0;
		border-left: v-bind(sizeSize) solid v-bind(color);
		border-top: v-bind(sizeSize) solid v-bind(color);
		width: v-bind(sizePadding);
		height: v-bind(sizePadding);
	}
	&.left-bottom {
		left: 0;
		bottom: 0;
		border-left: v-bind(sizeSize) solid v-bind(color);
		border-bottom: v-bind(sizeSize) solid v-bind(color);
		width: v-bind(sizePadding);
		height: v-bind(sizePadding);
	}
	&.right-top {
		right: 0;
		top: 0;
		border-right: v-bind(sizeSize) solid v-bind(color);
		border-top: v-bind(sizeSize) solid v-bind(color);
		width: v-bind(sizePadding);
		height: v-bind(sizePadding);
	}
	&.right-bottom {
		right: 0;
		bottom: 0;
		border-right: v-bind(sizeSize) solid v-bind(color);
		border-bottom: v-bind(sizeSize) solid v-bind(color);
		width: v-bind(sizePadding);
		height: v-bind(sizePadding);
	}
}
</style>