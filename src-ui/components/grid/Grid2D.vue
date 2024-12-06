<template>
	<div class="grid">
		<slot
			name="axis-x"
			v-bind:grid="Grid"
			v-bind:axis="Axis"
			v-bind:scale="scaleX"
			v-bind:x="x"
			v-bind:y="y"
		>
			<Grid2DAxis
				position="x"
				v-bind:scale="scaleX"
				v-bind:active="active"
				v-bind:hover="hover"
				v-bind:color="Axis"
				v-bind:x="x"
				v-bind:y="y"
			/>
		</slot>
		<slot
			name="axis-y"
			v-bind:grid="Grid"
			v-bind:axis="Axis"
			v-bind:scale="scaleY"
			v-bind:x="x"
			v-bind:y="y"
		>
			<Grid2DAxis
				position="y"
				v-bind:scale="scaleY"
				v-bind:active="active"
				v-bind:hover="hover"
				v-bind:color="Axis"
				v-bind:x="x"
				v-bind:y="y"
			/>
		</slot>
	</div>
	<slot
		v-bind:grid="Grid"
		v-bind:axis="Axis"
	/>
</template>
<script lang="ts" setup>
import {computed} from "vue";
import {Grid2DProps} from "@/components/grid/Grid2DProps.ts";
import {style} from "fyurry";
import Grid2DAxis from "@/components/grid/Grid2DAxis.vue";

const props = withDefaults(defineProps<Grid2DProps & {
	sin: (x: number) => number
}>(), {
	sin: (x: number) => {
		return .5 * Math.sin(10 * Math.log(x)) + .8
	}
})
//grid.color
const Grid = computed(() => style.color({
	name: 'grid',
	color: props.color,
	active: props.active,
	hover: props.hover,
	a: .075,
	aHover: .12,
	aActive: .18
}))
//grid.axis
const Axis = computed(() => style.color({
	name: 'grid',
	color: props.color,
	active: props.active,
	hover: props.hover,
	a: .15,
	aHover: .2,
	aActive: .25,
	field: 'axis'
}))
const sizePaddingNumX = computed(() => {
	return (props.padding != undefined ? props.padding : style.Item('grid.padding')) * props.sin(props.scaleX)
})
const sizeSizeNumX = computed(() => {
	return (props.size != undefined ? props.size : style.Item('grid.size'))
})
const sizePaddingNumY = computed(() => {
	return (props.padding != undefined ? props.padding : style.Item('grid.padding')) * props.sin(props.scaleY)
})
const sizeSizeNumY = computed(() => {
	return (props.size != undefined ? props.size : style.Item('grid.size'))
})
const sizePaddingX = computed(() => `${sizePaddingNumX.value / 2}px`)
const sizeSizeX = computed(() => `${sizeSizeNumX.value + sizePaddingNumX.value / 2 }px`)
const sizePaddingY = computed(() => `${sizePaddingNumY.value / 2}px`)
const sizeSizeY = computed(() => `${sizeSizeNumY.value + sizePaddingNumX.value / 2}px`)
</script>
<style lang="scss" scpoed>
.grid {
	position: relative;
	width: 100%;
	height: 100%;
	overflow: hidden;
	background-size: 50%;
	background: repeating-linear-gradient(
			to right,
			transparent 0,
			transparent v-bind(sizePaddingX),
			v-bind(Grid) v-bind(sizePaddingX),
			v-bind(Grid) v-bind(sizeSizeX)
	), repeating-linear-gradient(
			to bottom,
			transparent 0,
			transparent v-bind(sizePaddingY),
			v-bind(Grid) v-bind(sizePaddingY),
			v-bind(Grid) v-bind(sizeSizeY)
	);
	background-position: v-bind("x+'px'") v-bind("y+'px'");
}
</style>