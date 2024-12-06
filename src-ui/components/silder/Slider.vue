<template>
	<ObserverForm
		v-bind="props"
		v-bind:o="observer"
		class="slider-container"
		name="slider"
	>
		<template
			v-if="$slots['border-left']"
			#border-left="{style}"
		>
			<slot
				name="border-left"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['border-right']"
			#border-right="{style}"
		>
			<slot
				name="border-right"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['border-top']"
			#border-top="{style}"
		>
			<slot
				name="border-top"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['border-bottom']"
			#border-bottom="{style}"
		>
			<slot
				name="border-bottom"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['point-left-top']"
			#point-left-top="{style}"
		>
			<slot
				name="point-left-top"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['point-left-bottom']"
			#point-left-bottom="{style}"
		>
			<slot
				name="point-left-bottom"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['point-right-top']"
			#point-right-top="{style}"
		>
			<slot
				name="point-right-top"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['point-right-bottom']"
			#point-right-bottom="{style}"
		>
			<slot
				name="point-right-bottom"
				v-bind:style="style"
			/>
		</template>
		<template #default="{style}">
			<div class="slider-body">
				<slot name="prefix"/>
				<div
					ref="noneSlider"
					style="flex: 1;height: 100%;display:flex;align-items: center;justify-content: flex-start"
					v-bind:style="{
					margin: padding,
				}"
					@mousedown="e=>noneDown(e)"
				>
					<slot>
						<div
							class="slider"
								v-bind:class="{editing}"
								v-bind:style="{
								background: `linear-gradient(to right,${style.point} ${(1-barPadding)*100}%,rgba(0, 0, 0, 0) 0)`,
								backgroundSize: `${barSize}px 100%`,
								width: `${Math.max(Math.min((value-min)/(max-min)*100,100),0)}%`
							}"
						/>
					</slot>
				</div>
				<slot name="suffix"/>
			</div>
		</template>
	</ObserverForm>
</template>
<script lang="ts" setup>

import {Observer} from "@/components/observer/Observer.ts";
import {computed, reactive, ref} from "vue";
import ObserverComp from "@/components/observer/ObserverComp.vue";
import BorderLine from "@/components/border/BorderSolid.vue";
import {Color} from "@/entity";
import {style} from "fyurry";
import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";
import PointLinear from "@/components/point/PointSolid.vue";
import {SliderProps} from "@/components/silder/SliderProps.ts";
import {Drager} from "@/entity/Drager.ts";
import Input from "@/components/input/Input.vue";
import ObserverForm from "@/components/observer/ObserverForm.vue";

const props = withDefaults(defineProps<SliderProps>(), {
	shape: 'none',
	height: 22,
	padding: '5px',
	barSize: 8,
	barPadding: .3,
	max: 1,
	min: 0,
	//
	borderTop: true,
	borderBottom: true,
	borderLeft: true,
	borderRight: true,
	pointLeftTop: true,
	pointLeftBottom: true,
	pointRightTop: true,
	pointRightBottom: true,
})
const input = ref<HTMLInputElement>()
const observer = reactive(new Observer())

const setV = v => {
	//@ts-ignore
	value.value = Math.max(Math.min(v, props.max), props.min)
}
const noneSlider = ref<HTMLElement>()
const noneDown = (e: MouseEvent) => {
	
	const drager = new Drager()
	const sx = e.clientX
	const {width, x} = noneSlider.value.getBoundingClientRect()
	setV((sx - x) / width * (props.max - props.min))
	drager.move = (dx, dy, e) => {
		setV((sx + dx - x) / width * (props.max - props.min))
	}
	drager.up = () => editing.value = false
	editing.value = true
	drager.down(e)
}
const editing = ref(false)

const value = defineModel<number>('value', {default: 0})
setV(value.value)
</script>
<style lang="scss" scoped>
.slider-container {
	height: v-bind("height+'px'");
	width: 100%;
	position: relative;
	margin: 1px;
}

.slider-body {
	width: 100%;
	height: 100%;
	position: relative;
	display: flex;
	align-items: center;
	justify-content: flex-start;
}

.slider {
	height: calc(100% - 10px);
	
	
	transition-duration: .2s;
	
	&.editing {
		transition: none;
	}
}
</style>