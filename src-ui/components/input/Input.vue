<template>
	<ObserverForm
		v-bind="props"
		v-bind:o="observer"
		class="input-container"
		name="input"
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
			<div
				class="input-body"
				@mousedown="e=>{
				
				}"
			>
				<slot name="prefix"/>
				<input
					class="input"
					ref="input"
					v-bind:value="edit?api.copy:value"
					@input="e=>api.copy=e.target.value"
					@mousedown="e=>api.edit(e)"
					@keydown="e=>api.keydown(e)"
				/>
				<slot name="suffix"/>
			</div>
		</template>
	</ObserverForm>
</template>
<script lang="ts" setup>
import {InputProps} from "@/components/input/InputProps.ts";
import {Observer} from "@/components/observer/Observer.ts";
import {onMounted, onUnmounted, reactive, ref, watch} from "vue";
import {style} from "fyurry";
import ObserverForm from "@/components/observer/ObserverForm.vue";
import {EventEmitter} from "@/entity/EventEmitter.ts";

const props = withDefaults(defineProps<InputProps>(), {
	padding: '0 5px',
	textAlign: 'left',
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
const input = ref<HTMLInputElement>()
const observer = reactive(new Observer())

const value = defineModel<any>('value', {default: ''})
const event = new EventEmitter()
onMounted(() => {
	if (props.autoFocus) input.value.focus()
	if (props.autoSelectMounted) input.value.select()
})
const emits = defineEmits<{
	(e: 'confirm', v: any): any
	(e: 'cancel'): any
}>()
const edit = defineModel<boolean>('edit')
let editCount = 0
const api = reactive(new class {
	copy: any
	edit(e: MouseEvent) {
		if (edit.value) return e.stopPropagation()
		this.copy = value.value
		edit.value = <any>true
		input.value.focus()
		setTimeout(()=>{

			if (props.autoSelect) input.value.select()
		},250)
		setTimeout(() => {
			const v = editCount
			event.onceWindow('mousedown', (e: MouseEvent) => {
				if (editCount != v) return
				if (e.button == 2) return this.cancel()
				return this.confirm()
			})
		}, 250)
	}
	cancel() {
		input.value.blur()
		if (!edit.value) return
		edit.value = <any>false
		emits('cancel')
	}
	confirm() {
		input.value.blur()
		if (!edit.value) return
		edit.value = <any>false
		value.value = this.copy
		emits('confirm', value.value)
	}
	keydown(e: KeyboardEvent) {
		if (e.key == 'Escape') return this.cancel()
		if (e.key == 'Enter' && !e.shiftKey) return this.confirm()
	}
})
watch(edit, () => {
	editCount++
})
</script>
<style lang="scss" scoped>
.input-container {
	pointer-events: all;
	display: inline-block;
	font-size: 14px;
	flex: 1;
	margin: 1px;
}

.input-body {
	height: 100%;
	position: relative;
	display: flex;
	align-items: center;
	justify-content: flex-start;
}

.input {
	width: 0;
	outline: none;
	border: none;
	flex: 1;
	height: 100%;
	background: none;
	color: v-bind("style.Item('text')");
	padding: v-bind(padding);
	position: relative;
	top: 1px;
	text-align: v-bind(textAlign);
	
	&::selection {
		background: v-bind("style.Item('color.select')");
		color: v-bind("style.Item('text.select')");;
	}
}
</style>