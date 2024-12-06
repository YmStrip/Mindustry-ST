<template>
	<div
		class="scroll-container"
	>
		<ObserverComp
			class="top"
			v-bind:o="observerTop"
			v-if="scrollTop&&api.x"
			@mousedown="e=>api.downX(e)"
		>
			<slot
				name="scroll-top"
				v-bind:color-fill="FillTop"
				v-bind:color-thumb="ThumbTop"
				v-bind:thumb="api.thumbX"
				v-bind:scroll="api.scrollX"
				v-bind:height="scroll.offsetWidth"
				v-bind:thumbTick="()=>api.tickTopFn()"
			>
				<ScrollbarLine
					v-if="display=='line'"
					position="x"
					v-bind:color-fill="FillTop"
					v-bind:color-thumb="ThumbTop"
					v-bind:thumb="api.thumbX"
					v-bind:scroll="api.scrollX"
					v-bind:height="scroll.offsetWidth"
					v-bind:thumbTick="()=>api.tickTopFn()"
				/>
				<ScrollbarSolid
					v-else-if="display=='solid'"
					position="x"
					v-bind:color-fill="FillTop"
					v-bind:color-thumb="ThumbTop"
					v-bind:thumb="api.thumbX"
					v-bind:scroll="api.scrollX"
					v-bind:height="scroll.offsetWidth"
					v-bind:thumbTick="()=>api.tickTopFn()"
				/>
			</slot>
		</ObserverComp>
		<div class="middle">
			<ObserverComp
				class="left"
				v-bind:o="observerLeft"
				v-if="scrollLeft&&api.y"
				@mousedown="e=>api.downY(e)"
			>
				<slot
					name="scroll-left"
					v-bind:color-fill="FillLeft"
					v-bind:color-thumb="ThumbLeft"
					v-bind:thumb="api.thumbY"
					v-bind:scroll="api.scrollY"
					v-bind:height="scroll.offsetHeight"
					v-bind:thumbTick="()=>api.tickLeftFn()"
				>
					<ScrollbarLine
						v-if="display=='line'"
						position="y"
						v-bind:color-fill="FillLeft"
						v-bind:color-thumb="ThumbLeft"
						v-bind:thumb="api.thumbY"
						v-bind:scroll="api.scrollY"
						v-bind:height="scroll.offsetHeight"
						v-bind:thumbTick="()=>api.tickLeftFn()"
					/>
					<ScrollbarSolid
						v-else-if="display=='solid'"
						position="y"
						v-bind:color-fill="FillLeft"
						v-bind:color-thumb="ThumbLeft"
						v-bind:thumb="api.thumbY"
						v-bind:scroll="api.scrollY"
						v-bind:height="scroll.offsetHeight"
						v-bind:thumbTick="()=>api.tickLeftFn()"
					/>
				</slot>
			</ObserverComp>
			<div
				class="body"
				ref="scroll"
				v-bind:class="{
					nowrap,
				}"
				v-bind:style="styleBody"
				@wheel="e=>api.wheel(e)"
			>
				<slot/>
			</div>
			<ObserverComp
				class="right"
				v-bind:o="observerRight"
				v-if="scrollRight&&api.y"
				style="height: 100%"
				@mousedown="e=>api.downY(e)"
			>
				<slot
					name="scroll-right"
					v-bind:color-fill="FillRight"
					v-bind:color-thumb="ThumbRight"
					v-bind:thumb="api.thumbY"
					v-bind:scroll="api.scrollY"
					v-bind:height="scroll.offsetHeight"
					v-bind:thumbTick="()=>api.tickRightFn()"
				>
					<ScrollbarLine
						v-if="display=='line'"
						position="y"
						v-bind:color-fill="FillRight"
						v-bind:color-thumb="ThumbRight"
						v-bind:thumb="api.thumbY"
						v-bind:scroll="api.scrollY"
						v-bind:height="scroll.offsetHeight"
						v-bind:thumbTick="()=>api.tickRightFn()"
					/>
					<ScrollbarSolid
						v-else-if="display=='solid'"
						position="y"
						v-bind:color-fill="FillRight"
						v-bind:color-thumb="ThumbRight"
						v-bind:thumb="api.thumbY"
						v-bind:scroll="api.scrollY"
						v-bind:height="scroll.offsetHeight"
						v-bind:thumbTick="()=>api.tickRightFn()"
					/>
				</slot>
			</ObserverComp>
		</div>
		<ObserverComp
			class="bottom"
			v-bind:o="observerBottom"
			v-if="scrollBottom&&api.x"
			@mousedown="e=>api.downX(e)"
		>
			<slot
				name="scroll-bottom"
				v-bind:color-fill="FillBottom"
				v-bind:color-thumb="ThumbBottom"
				v-bind:thumb="api.thumbX"
				v-bind:scroll="api.scrollX"
				v-bind:height="scroll.offsetWidth"
				v-bind:thumbTick="()=>api.tickBottomFn()"
			>
				<ScrollbarLine
					v-if="display=='line'"
					position="x"
					v-bind:color-fill="FillBottom"
					v-bind:color-thumb="ThumbBottom"
					v-bind:thumb="api.thumbX"
					v-bind:scroll="api.scrollX"
					v-bind:height="scroll.offsetWidth"
					v-bind:thumbTick="()=>api.tickBottomFn()"
				/>
				<ScrollbarSolid
					v-else-if="display=='line'"
					position="x"
					v-bind:color-fill="FillBottom"
					v-bind:color-thumb="ThumbBottom"
					v-bind:thumb="api.thumbX"
					v-bind:scroll="api.scrollX"
					v-bind:height="scroll.offsetWidth"
					v-bind:thumbTick="()=>api.tickBottomFn()"
				/>
			</slot>
		
		</ObserverComp>
	</div>
</template>
<script lang="ts" setup>
import {LayoutScrollProps} from "@/components/scroll/LayoutScrollProps.ts";
import {computed, onBeforeUnmount, onMounted, reactive, ref} from "vue";
import ObserverComp from "@/components/observer/ObserverComp.vue";
import {Observer} from "@/components/observer/Observer.ts";
import ScrollbarLine from "@/components/scroll/ScrollbarLine.vue";
import fyurry, {transition} from "fyurry";
import {Drager} from "@/entity/Drager.ts";
import ScrollbarSolid from "@/components/scroll/ScrollbarSolid.vue";

const props = withDefaults(defineProps<LayoutScrollProps>(), {
	step: 23.5,
	display: 'solid',
	scrollTop: false,
	scrollLeft: false,
	scrollBottom: false,
	scrollRight: true
})
const scroll = ref<HTMLElement>()
const observer = new MutationObserver(() => {
	console.log(5)
	setTimeout(() => api.diff())
})
const observerTop = reactive(new Observer())
const observerRight = reactive(new Observer())
const observerLeft = reactive(new Observer())
const observerBottom = reactive(new Observer())
const api = reactive(new class {
	x = false
	y = false
	scrollX = 0
	thumbX = 0
	scrollY = 0
	thumbY = 0
	tickLeft = false
	tickTop = false
	tickRight = false
	tickBottom = false
	tickLeftFn() {
		this.tickLeft = true
	}
	tickTopFn() {
		this.tickTop = true
	}
	tickRightFn() {
		this.tickRight = true
	}
	tickBottomFn() {
		this.tickBottom = true
	}
	diff() {
		const s = scroll.value
		this.y = s.offsetHeight < s.scrollHeight
		this.x = s.offsetWidth < s.scrollWidth
		//
		this.scrollX = s.scrollLeft / (s.scrollWidth - s.offsetWidth)
		this.thumbX = s.offsetWidth / s.scrollWidth
		//
		this.scrollY = s.scrollTop / (s.scrollHeight - s.offsetHeight)
		this.thumbY = s.offsetHeight / s.scrollHeight
	}
	wheel(e: WheelEvent) {
		e.preventDefault()
		if (observerTop.hover || observerBottom.hover) {
			if (e.deltaY > 0) {
				scroll.value.scrollTo(
					scroll.value.scrollLeft + props.step,
					scroll.value.scrollTop
				)
			}
			else {
				scroll.value.scrollTo(
					scroll.value.scrollLeft - props.step,
					scroll.value.scrollTop
				)
			}
		}
		else {
			if (e.deltaY > 0) {
				scroll.value.scrollTo(
					scroll.value.scrollLeft,
					scroll.value.scrollTop + props.step
				)
			}
			else {
				scroll.value.scrollTo(
					scroll.value.scrollLeft,
					scroll.value.scrollTop - props.step
				)
			}
		}
		api.diff()
	}
	moveX(x: number) {
		const b = scroll.value.getBoundingClientRect()
		scroll.value.scrollTo(
			transition.mapLinearClamp((x - b.x) / b.width, 0, 1, 0, scroll.value.scrollWidth - scroll.value.offsetWidth),
			scroll.value.scrollTop
		)
		api.diff()
	}
	moveY(y: number) {
		const b = scroll.value.getBoundingClientRect()
		scroll.value.scrollTo(
			scroll.value.scrollLeft,
			transition.mapLinearClamp((y - b.y) / b.height, 0, 1, 0, scroll.value.scrollHeight - scroll.value.offsetHeight),
		)
		api.diff()
	}
	downX(e: MouseEvent) {
		//out thumb
		if (!(this.tickTop || this.tickBottom)) this.moveX(e.clientX)
		this.tickTop = false
		this.tickBottom = false
		//
		const sxt = scroll.value.scrollLeft
		const drager = new Drager()
		drager.move = (dx, dy) => {
			scroll.value.scrollTo(
				sxt + dx / scroll.value.offsetWidth * scroll.value.scrollWidth,
				scroll.value.scrollTop
			)
			this.diff()
		}
		drager.down(e)
	}
	downY(e: MouseEvent) {
		//out thumb
		if (!(this.tickLeft || this.tickRight)) this.moveY(e.clientY)
		this.tickLeft = false
		this.tickRight = false
		//
		const syt = scroll.value.scrollTop
		const drager = new Drager()
		drager.move = (dx, dy) => {
			scroll.value.scrollTo(
				scroll.value.scrollLeft,
				syt + dy / scroll.value.offsetHeight * scroll.value.scrollHeight
			)
			this.diff()
		}
		drager.down(e)
	}
})

const FillLeft = computed(() => fyurry.style.color({
	name: "scrollbar",
	color: props.color,
	a: .05,
	aHover: .05,
	aActive: .05,
	active: observerLeft.active,
	hover: observerLeft.hover
}))
const ThumbLeft = computed(() => fyurry.style.color({
	name: "scrollbar",
	color: props.color,
	a: .05,
	aHover: .05,
	aActive: .05,
	active: observerLeft.active,
	hover: observerLeft.hover,
	field: 'thumb'
}))

const FillRight = computed(() => fyurry.style.color({
	name: "scrollbar",
	color: props.color,
	a: .05,
	aHover: .05,
	aActive: .05,
	active: observerRight.active,
	hover: observerRight.hover
}))

const ThumbRight = computed(() => fyurry.style.color({
	name: "scrollbar",
	color: props.color,
	a: .05,
	aHover: .05,
	aActive: .05,
	active: observerRight.active,
	hover: observerRight.hover,
	field: 'thumb'
}))

const FillTop = computed(() => fyurry.style.color({
	name: "scrollbar",
	color: props.color,
	a: .05,
	aHover: .05,
	aActive: .05,
	active: observerTop.active,
	hover: observerTop.hover
}))
const ThumbTop = computed(() => fyurry.style.color({
	name: "scrollbar",
	color: props.color,
	a: .05,
	aHover: .05,
	aActive: .05,
	active: observerTop.active,
	hover: observerTop.hover,
	field: 'thumb'
}))

const FillBottom = computed(() => fyurry.style.color({
	name: "scrollbar",
	color: props.color,
	a: .05,
	aHover: .05,
	aActive: .05,
	active: observerBottom.active,
	hover: observerBottom.hover
}))
const ThumbBottom = computed(() => fyurry.style.color({
	name: "scrollbar",
	color: props.color,
	a: .05,
	aHover: .05,
	aActive: .05,
	active: observerBottom.active,
	hover: observerBottom.hover,
	field: 'thumb'
}))


onMounted(() => {
	observer.observe(scroll.value, {
		attributes: true,
		attributeFilter: ['scrollHeight', 'scrollWidth', 'offsetWidth', 'offsetHeight'],
		childList: false,
		subtree: false,
	})
	api.diff()
})
onBeforeUnmount(() => {
	observer.disconnect()
})
</script>
<style lang="scss" scoped>
.scroll-container {
	display: flex;
	flex-direction: column;
	max-height: inherit;
	align-items: center;
	justify-content: center;
}

.middle {
	flex: 1;
	width: 100%;
	height: 100%;
	max-height: inherit;
	display: flex;
	align-items: center;
	justify-content: center;
}

.body {
	flex: 1;
	height: 100%;
	max-height: inherit;
	overflow: auto;
	
	&::-webkit-scrollbar {
		display: none;
	}
	
	&.nx {
		overflow-x: hidden;
	}
	
	&.ny {
		overflow-y: hidden;
	}
	
	&.nowrap {
		white-space: nowrap;
	}
	
	&.relative {
		position: relative;
	}
}
</style>