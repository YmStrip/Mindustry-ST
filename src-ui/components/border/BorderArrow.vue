<template>
	<template v-if="position=='left'">
		<div
			style="height: 100%;position: absolute;left: 0;top: 0;"
			v-bind:style="{
				left: -width+'px',
				filter: `blur(${-10*size+10})`,
				clipPath: `polygon(${[
					`100% 0`,
					`calc(100% - ${size}px) 0`,
					`0 50%`,
					`calc(100% - ${size}px) 100%`,
					`100% 100%`,
					`${size}px 50%`,
				].join(',')})`,
				background: color,
				width:`${width}px`,
			}"
		/>
		<div
			style="height: 100%;position: absolute;left: 0;top: 0;"
			v-bind:style="{
				left: -width+'px',
				clipPath: `polygon(${[
					`100% 0`,
					`${size}px 50%`,
					`100% 100%`,
				].join(',')})`,
				background: fill,
				width:`${width}px`,
			}"
		/>
	</template>
	<template v-else-if="position=='right'">
		<div
			style="height: 100%;position: absolute;right: 0;top: 0"
			v-bind:style="{
				right: -width+'px',
				filter: `blur(${-10*size+10})`,
				clipPath: `polygon(${[
					`0 0`,
					`${size}px 0`,
					`100% 50%`,
					`${size}px 100%`,
					`0 100%`,
					`calc(100% - ${size}px) 50%`,
				].join(',')})`,
				background: color,
				width:`${width}px`,
			}"
		/>
		<div
			style="height: 100%;position: absolute;right: 0;top: 0"
			v-bind:style="{
				right: -width+'px',
				clipPath: `polygon(${[
					`0 0`,
					`calc(100% - ${size}px) 50%`,
					`0 100%`,
				].join(',')})`,
				background: fill,
				width:`${width}px`,
			}"
		/>
	</template>
</template>
<script lang="ts" setup>
import {BorderProps} from "@/components/border/BorderProps.ts";

const props = withDefaults(defineProps<BorderProps & {
	fill?: string
	width?:number
}>(), {
	width: 10,
	color: 'white',
	size: 1,
	padding: 4,
	offset: 0,
})
</script>
<style lang="scss" scoped>

</style>