<template>
	<div class="card-solid" style="height: 200px;width: 200px;">
		<div
			class="background border"
			v-bind:style="{
				clipPath: `polygon(${[
					`0% ${padding}px`,
					`${padding}px 0%`,
					`100% 0%`,
					`100% calc(100% - ${padding*2}px)`,
					`calc(100% - ${padding*2}px) 100%`,
					`0% 100%`
				].join(',')})`
			}"
		/>
		<div>
		
		</div>
	</div>
</template>
<script lang="ts" setup>
import {CardProps} from "@/components/card/CardProps.ts";
import {computed} from "vue";
import {style} from "fyurry";
import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";

const props = withDefaults(defineProps<CardProps & {
	padding?:number
	size?:number
}>(), {
	size: 4,
	padding: 15
})
const Border = computed(()=>props.color||style.Item('card.border'))
</script>
<style lang="scss" scoped>
.background {
	background: v-bind(Border);
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	pointer-events: none;
}
.border {
	background: none;
	border: 2px solid v-bind(Border);
}
.card-solid {
	margin-top: 50px;
}
</style>