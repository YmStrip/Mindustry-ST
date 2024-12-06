<template>
	<div
		class="axis"
		v-bind:class="{
			[position]: true
		}"
	/>
</template>
<script lang="ts" setup>
import {computed} from "vue";
import {style} from "fyurry";
import {Grid2DAxisProps} from "@/components/grid/Grid2DAxisProps.ts";

const props = defineProps<Grid2DAxisProps>()
const sizeSizeNum = computed(() => {
	return props.size != undefined ? props.size : style.Item('grid.axis.size')
})
const Size = computed(() => `${sizeSizeNum.value}px`)
const Pos = computed(() => `calc(50% - ${sizeSizeNum.value * props.scale / 2}px)`)
</script>
<style lang="scss" scoped>
.axis {
	position: absolute;
	background: v-bind(color);
	
	&.x {
		width: v-bind(Size);
		height: 100%;
		left: calc(v-bind("x+'px'") + v-bind(Pos));
		top: 0;
	}
	
	&.y {
		height: v-bind(Size);
		width: 100%;
		top: calc(v-bind("y+'px'") + v-bind(Pos));
		left: 0;
	}
}
</style>