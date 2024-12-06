<template>
	<teleport
		v-bind:to="to"
		v-if="show||instance.childShow"
	>
		<div
			ref="el"
			class="popover"
			v-bind:style="[api.sty,{
				zIndex: zIndex+instance.deep,
			},style]"
			v-bind:class="[{
				blur,
			},props.class]"
			@mousedown="api.breakClick=true"
			@mousemove="api.breakMouse=true"
			@mouseenter="api.breakMouse=true"
			@mouseleave="()=>api.handlePopoverLeave()"
		>
			<slot/>
		</div>
	</teleport>
	<component
		v-if="$slots.trigger"
		v-bind:is="triggerComp"
	/>
</template>
<script lang="ts" setup>

import {PopoverInstance} from "@/components/popover/PopoverInstance.ts";
import {computed, getCurrentInstance, inject, onUnmounted, provide, reactive, ref, watch} from "vue";
import {PopoverProps} from "@/components/popover/PopoverProps.ts";
import {EventEmitterElementVNode} from "@/entity/EventEmitter.ts";
import {Timer} from "@/entity";

const props = withDefaults(defineProps<PopoverProps>(), {
	trigger: 'hover',
	to: 'body',
	easeIn: 0,
	easeOut: 0,
	zIndex: 10,
	blur: true
})
const el = ref<HTMLElement>()

const parent: PopoverInstance = <any>(inject('popover'))

const instance: PopoverInstance = <any>reactive(new PopoverInstance(parent))

const show = defineModel<boolean>('show', {default: false})


const slot = defineSlots<{
	trigger: {}
	default: {}
}>()
const bus = new EventEmitterElementVNode(onUnmounted)
const triggerComp = computed(() => {
	const f: any = slot.trigger
	if (!f) return
	const node: any[] = f({instance})
	if (!node?.length || node?.length > 1) return
	const z = node[0]
	if (!z) return
	bus.bind('popover', z)
	return () => node
})

//mapper
const fixedMatrix = {
	//[triggerWidth,popWidth,triggerHeight,popHeight,]
	'left': [0, -1, .5, -.5],
	'left-start': [0, -1, 0, 0],
	'left-end': [0, -1, 1, -1],
	'right': [1, 0, .5, -.5],
	'right-start': [1, 0, 0, 0],
	'right-end': [1, 0, 1, -1],
	'top': [.5, -.5, 0, -1],
	'top-start': [0, 0, 0, -1],
	'top-end': [1, -1, 0, -1],
	'bottom': [.5, -.5, 1, 0],
	'bottom-start': [0, 0, 1, 0],
	'bottom-end': [1, -1, 1, 0]
}
const api = reactive(new class {
	trigger: HTMLElement
	popover = el
	//
	exited = false
	//mouse store
	sty: any = {}
	//break
	breakMouse = true
	breakClick = true
	focusTime = 0
	//es timer
	esShow = false
	esIter = 0
	fixedTimer = new Timer()
	fixed() {
		if (!this.trigger || !this.hasShow()) return
		const t = this.trigger
		const e = el.value
		if (!t || !e) return
		const eB = e.getBoundingClientRect()
		const tB = t.getBoundingClientRect()
		const eW = eB.width
		const eH = eB.height
		const tW = tB.width
		const tH = tB.height
		const {x, y} = t.getBoundingClientRect()
		const weight = fixedMatrix[props.placement] || fixedMatrix["bottom"]
		let X = x + tW * weight[0] + eW * weight[1]
		let Y = y + tH * weight[2] + eH * weight[3]
		if (X + eW > window.innerWidth) {
			X = tB.x - eW
		}
		
		if (X < 0) X = 0
		if (Y + eH > window.innerHeight) {
			Y = tB.y - eH
		}
		if (Y < 0) Y = 0
		if (props.placement == 'top-end' || props.placement == 'top-start' || props.placement == 'top' || props.placement == 'bottom' || props.placement == 'bottom-start' || props.placement == 'bottom-end') this.sty.width = tW + 'px'
		else delete this.sty.width
		if (X == 0 && Y == 0) return;
		this.sty.left = X + 'px'
		this.sty.top = Y + 'px'
		if (this.hasShow()) this.sty.opacity = 1
	}
	fixedRunner() {
		this.fixedTimer.clear()
		if (this.exited) return
		this.fixedTimer.onTimer = () => {
			if (this.exited) return this.fixedTimer.clear()
			if (!this.hasShow()) return this.fixedTimer.clear()
			this.fixed()
		}
		this.fixedTimer.timer(250, -1)
	}
	hasShow() {
		return show.value || instance.childShow
	}
	diffShow(v: boolean) {
		if (!v && instance.childShow) return;
		if (show.value == v) return
		const esIter = ++this.esIter
		if (v) {
			setTimeout(() => {
				return;
				if (esIter != this.esIter) return
				this.fixed()
				this.fixedRunner()
			}, show.value ? props.easeOut : props.easeIn + 50)
			show.value = <any>v
		}
		else {
			setTimeout(() => {
				show.value = <any>v
				this.sty.opacity = 0
			}, show.value ? props.easeOut : props.easeIn + 50)
		}
	}
	//handler
	handlePopoverLeave() {
		this.breakMouse = false
		setTimeout(() => {
			if (!this.breakMouse) {
				if (!(props.trigger == 'hover' || props.trigger == 'active')) return
				this.diffShow(false)
			}
		}, 150)
	}
})
onUnmounted(() => api.exited = true)
bus.onMounted('mounted', (namespace, el) => {
	api.trigger = el
}, onUnmounted)
bus.onMounted('mousemove', (e: MouseEvent) => {
	
	if (props.trigger == 'hover') {
		api.diffShow(true)
	}
}, onUnmounted)
//leave trigger , try hidden hover and active
bus.onMounted('mouseleave', (e: MouseEvent) => {
	if (props.trigger == 'hover' || props.trigger == 'active') {
		api.breakMouse = false
		setTimeout(() => {
			if (!api.breakMouse) {
				if (!(props.trigger == 'hover' || props.trigger == 'active')) return
				api.diffShow(false)
			}
			api.breakMouse = false
		}, 250)
	}
}, onUnmounted)
//if enter trigger set mouse
bus.onMounted('mouseenter', (e: MouseEvent) => {
	if (props.trigger == 'hover') {
		api.breakMouse = true
		api.diffShow(true)
	}
}, onUnmounted)
//break click
bus.onMounted('mousedown', (e: MouseEvent) => {
	api.breakClick = true
	if (props.trigger == 'active') {
		api.diffShow(true)
	}
}, onUnmounted)
//this.
bus.onMounted('mouseup', (e: MouseEvent) => {
	if (props.trigger == 'active') {
		api.diffShow(false)
	}
}, onUnmounted)


bus.onMounted('focus', (e: MouseEvent) => {
	if (props.trigger == 'focus') {
		api.focusTime = Date.now()
		this.diffShow(true)
	}
}, onUnmounted)
bus.onMounted('blur', (e: MouseEvent) => {
	if (props.trigger == 'focus') {
		if (Date.now() - api.focusTime < 250) return
		api.diffShow(false)
	}
}, onUnmounted)
bus.onMounted('click', (e: MouseEvent) => {
	if (props.trigger == 'switch' || props.trigger == 'click') {
		api.diffShow(!show.value)
	}
}, onUnmounted)
bus.onMountedWindow('mouseup', e => {
	if (!api.breakClick) {
		if (props.trigger == 'switch') return
		api.diffShow(false)
	}
	api.breakClick = false
}, onUnmounted)

watch(show, (value, oldValue) => {
	if (value) instance.open()
	else instance.close()
	if (value && !oldValue) {
		setTimeout(() => {
			api.fixed()
			api.fixedRunner()
		}, props.easeIn + 50)
	}
})
watch(() => props.trigger, () => {

})
watch(() => props.placement, () => {
	instance['placement' + ''] = props.placement
})
instance.event.onMounted('update:show', (v: any) => {
	show.value = v
}, onUnmounted)

onUnmounted(() => {
	instance.destroy()
})

provide('popover', instance)

</script>
<style lang="scss" scoped>
.popover {
	position: absolute;
	left: 0;
	top: 0;
	opacity: 0;
	margin-left: .01px;
	margin-top: .01px;
	
	&.blur {
		backdrop-filter: blur(5px);
	}
}
</style>