<template>
	<div
		ref="observer"
		class="observer"
		v-bind:class="{
				strong,
				absolute,
				relative,
				flex,
				clip,
				disabled
		}"
		@click="e=>{
			o.onClick(e)
		}"
		@mousemove="(e)=>{
			o.mouse = e
			if(!disabled) o.onMove()
			if (o.hover||disabled) return
				o.hover = true
				o.onHover()
				o.triggerFocus()
		}"
		@mouseover="()=>{
			/*if (o.hover||disabled) {
				o.hover = false
				o.onHoverEnd()
				o.triggerBlur()
			}
			activeTemp = false
			activeCheck()*/
		}"
		@mouseleave="()=>{
			if (o.hover||disabled) {
				o.hover = false
				o.onHoverEnd()
				o.triggerBlur()
			}
			activeTemp = false
			activeCheck()
		}"
		@mouseenter="()=>{
			if (o.hover||disabled) return
			o.hover = true
			o.onHover()
			o.triggerFocus()
		}"
		@mousedown="(e)=>{
			activeTemp = true
			activeCheck()
			o.onMousedown(e)
		}"
		@mouseup="(e)=>{
			activeTemp = false
			activeCheck()
			o.onMouseup(e)
		}"
	>
		<LayoutAbsolute
			v-if="disabled||readonly"
			left="0"
			top="0"
			z-index="10"
			v-bind:style="{cursor: disabled?'not-allowed':''}"
			@mousedown.stop
			@mousemove.stop
			@mouseover.stop
			@mouseenter.stop
			@click.stop
		/>
		<slot/>
	</div>
</template>
<script lang="ts" setup>
import {ObserverProps} from "@/components/observer/ObserverProps.ts";
import {Observer} from "@/components/observer/Observer.ts";
import {onMounted, onUnmounted, ref, watch} from "vue";
import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";
import fyurry from "fyurry";

const props = defineProps<ObserverProps & {
	o: Observer
}>()
const observer = ref<HTMLElement>()
const activeTemp = ref(false)
const activeCheck = () => {
	if (!props.disabled && (activeTemp.value || props.active)) {
		if (!props.o?.active) {
			props.o.active = true
			props.o.onActive()
		}
	}
	else {
		if (props.o?.active) {
			props.o.active = false
			props.o.onActiveEnd()
		}
	}
	if (props.disabled) {
		if (props.o?.hover) {
			props.o.hover = false
			props.o.onHoverEnd()
		}
	}
}
activeCheck()
watch(() => props.active, () => activeCheck())
watch(() => props.disabled, () => activeCheck())
let ol = props.o
onMounted(() => {
	props.o.el = observer.value
	props.o.onMounted()
})
onUnmounted(() => {
	props.o.onUnmounted()
})
watch(() => props.o, () => {
	if (props.o !== ol) {
		ol.onUnmounted()
		props.o.el = observer.value
		props.o.onMounted()
		ol = props.o
	}
})
fyurry.event.onMounted('ui:resize', () => {
	props.o.onResize()
}, onUnmounted)
</script>
<style lang="scss" scoped>
.observer {
	justify-content: v-bind(justifyContent);
	align-items: v-bind(alignItems);
	position: relative;
}

.strong {
	font-weight: bold;
}

.absolute {
	position: absolute;
}

.relative {
	position: relative;
}

.flex {
	display: flex;
}

.clip {
	flex: 1;
}

.disabled {
	filter: brightness(.75);
}
</style>