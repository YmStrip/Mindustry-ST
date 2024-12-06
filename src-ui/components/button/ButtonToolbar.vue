<template>
	<Button
			style="width: 100%;"
			v-bind="props"
			@click="e=>{
				if(!disabled)return
				e.stopImmediatePropagation()
				e.stopPropagation()
			}"
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
		<slot name="prefix"></slot>
		<LayoutFlex style="height: 100%;width: 20px;" align-items="center" justify-content="center">
			<slot name="icon">
				<Icon
						icon="ic:baseline-check"
						v-if="check"
				/>
				<Icon
						v-bind:icon="icon"
						v-else-if="icon"
				/>
			</slot>
		</LayoutFlex>
		<slot></slot>
		<LayoutFlex full justify-content="flex-end" style="height: 100%">
			<slot name="suffix"></slot>
		</LayoutFlex>
	</Button>
</template>
<script lang="ts" setup>


import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import Icon from "@/module/icon/component/Icon.vue";
import {ButtonProps} from "@/components/button/ButtonProps.ts";
import Button from "@/components/button/Button.vue";

const props = withDefaults(defineProps<ButtonProps & {
	icon?: string
	check?: boolean
}>(), {
	icon: 'mdi:cube-send',
	//
	borderTop: true,
	borderBottom: true,
	borderLeft: true,
	borderRight: true,
	pointLeftTop: true,
	pointLeftBottom: true,
	pointRightTop: true,
	pointRightBottom: true
})
</script>