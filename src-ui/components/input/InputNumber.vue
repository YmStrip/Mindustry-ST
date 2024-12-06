<template>
	<Input
		v-bind="props"
		v-bind:value="<any>value"
		v-model:edit="<any>edit"
		v-bind:upd="upd"
		@confirm="v=>api.upd(v)"
		@mousedown="e=>api.drager.down(e)"
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
		<template #suffix>
			<div
				ref="container"
				class="input-number-slider"
				tabindex="0"
			>
				<LayoutAbsolute
					pointer="none"
					class="mask"
					v-if="api.edit"
					v-bind:style="{
						background: ColorShadow,
						width: ((value-min)/(max-min)*100)+'%'
					}"
				/>
			</div>
		</template>
	</Input>
</template>
<script lang="ts" setup>
import {computed, getCurrentInstance, reactive, ref, watch} from "vue";
import {out, style} from "fyurry";
import Input from "@/components/input/Input.vue";
import {InputNumberProps} from "@/components/input/InputNumberProps.ts";
import {Drager} from "@/entity/Drager.ts";
import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";

const props = withDefaults(defineProps<InputNumberProps>(), {
	//
	min: 0,
	max: 1,
	precision: 2,
	step: 1,
	autoSelect: true,
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
const value = defineModel<number>('value', {default: 0})
const container = ref<HTMLElement>()
const api = reactive(new class {
	edit = false
	drager = new class extends Drager {
		sv = value.value
		w = 0
		dt = 0
		down(e: MouseEvent) {
			if (!container.value) return
			this.dt = Date.now()
			this.w = container.value.getBoundingClientRect().width || 100
			this.sv = value.value
			super.down(e);
		}
		move(x: number, y: number) {
			if (Date.now() - this.dt > 250) {
				api.edit = true
				edit.value = <any>false
				value.value = api.clamp(this.sv + (x * (props.step) / this.w))
				container.value?.focus()
			}
			
		}
		up(e: MouseEvent) {
			if (Date.now() - this.dt < 250) return
			container.value?.focus()
			container.value?.blur()
			edit.value = <any>false
			api.edit = false
		}
	}
	clamp(v: any) {
		try {
			v = eval(v)
		} catch (e) {
			out.error('error number:' + e)
			console.log(e)
			return props.min
		}
		v = <any>Math.max(Math.min(Math.floor(v * 10 ** props.precision) / (10 ** props.precision), props.max), props.min)
		if (!Number.isFinite(v) || isNaN(v)) return props.min
		return v
	}
	clampValue() {
		value.value = this.clamp(value.value)
	}
	clampTest() {
		if (value.value < props.min || value.value > props.max) return this.clampValue()
		const per = (value + '').split('.')
		if (per.length > 2) return this.clampValue()
		if (per.length != 2) return
		const u = per[1]
		if (u?.length > props.precision) return this.clampValue()
	}
	upd(v: any) {
		setTimeout(() => {
			value.value = api.clamp(v)
			upd.value++
		}, 250)
	}
})
const upd = ref(0)
const edit = defineModel<boolean>('edit')
api.clampTest()
watch(()=>props.min,()=>api.clampTest())
watch(()=>props.max,()=>api.clampTest())

const ColorShadow = computed(() => style.color({
	name: 'input',
	a: .1,
	aActive: .5,
	aHover: .1,
	field: 'mask',
	active: false,
	hover: false,
	color: props.color
}))

</script>
<style lang="scss" scoped>
.input-number-slider {
	position: absolute;
	width: 100%;
	height: 100%;
	pointer-events: none;
	outline: none;
}
</style>