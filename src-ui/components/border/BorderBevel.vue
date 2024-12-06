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
					`0 100%`,
					`${size}px 100%`
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
					`${size}px 100%`,
					`100% 100%`,
				].join(',')})`,
				background: fill,
				width:`${width}px`,
			}"
		/>
	</template>
	<template v-if="position=='right'">
		<div
			style="height: 100%;position: absolute;right: 0;top: 0;"
			v-bind:style="{
				right: -width+'px',
				filter: `blur(${-10*size+10})`,
				clipPath: `polygon(${[
					`100% 0`,
					`calc(100% - ${size}px) 0`,
					`0 100%`,
					`${size}px 100%`
				].join(',')})`,
				background: color,
				width:`${width}px`,
			}"
		/>
		<div
			style="height: 100%;position: absolute;right: 0;top: 0;"
			v-bind:style="{
				right: -width+'px',
				clipPath: `polygon(${[
					`calc(100% - ${size}px) 0`,
					`0 0`,
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
	width: 8,
	color: 'white',
	size: 1,
	padding: 4,
	offset: 0,
})
</script>
<style lang="scss" scoped>

</style>