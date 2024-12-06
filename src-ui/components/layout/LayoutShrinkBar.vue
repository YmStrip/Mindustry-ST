<template>
	<div
		class="layout-shrink-bar"
		v-bind:class="[position]"
		ref="container"
	>
		
		<ObserverForm
			class="bar"
			v-bind="props"
			v-bind:o="observer"
			v-bind:class="[position,{hidden:!show}]"
			v-bind:style="styleBar"
			v-if="show"
			name="collapsebar"
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
			<div
				class="expand-trigger"
				@mousedown="e=>api.down(e)"
				v-bind:class="[position]"
				v-bind:style="styleTrigger"
			/>
			<slot/>
		</ObserverForm>
		<div
			class="icon"
			v-bind:class="[position,{hidden:!show}]"
			v-bind:style="styleIcon"
			@mousedown="e=>api.down(e)"
		>
			<slot
				name="icon"
				v-bind:position="position"
				v-bind:show="show"
			>
				<IconShadow
					v-bind:icon="api.getIcon(expand,show,position)"
				/>
			</slot>
		</div>
	</div>
</template>
<script lang="ts" setup>
import {LayoutShrinkBarProps} from "@/components/layout/LayoutShrinkBarProps.ts";
import IconShadow from "@/module/icon/component/IconShadow.vue";
import {reactive, ref, watch} from "vue";
import {style} from "fyurry";
import {Observer} from "@/components/observer/Observer.ts";
import ObserverForm from "@/components/observer/ObserverForm.vue";
import {Drager} from "@/entity/Drager.ts";

const props = withDefaults(defineProps<LayoutShrinkBarProps>(), {
	size: 22,
	sizeMax: 100,
	iconPadding: 5,
	//
	pointLeftTop: true,
	pointLeftBottom: true,
	pointRightTop: true,
	pointRightBottom: true,
	borderRight: true,
	borderTop: true,
	borderBottom: true,
	borderLeft: true
})
const container = ref<HTMLElement>()
const observer = reactive(new Observer())
const show = defineModel('show', {default: true})
const expand = defineModel('expand', {default: 0})
if (expand.value < props.size) expand.value = props.size
//z = z_0 + [w_ix + w_jy]
const crossMatrix = {
	'top': [0, 1],
	'left': [1, 0],
	'right': [-1, 0],
	'bottom': [0, -1]
}
const api = reactive(new class {
	getIcon(...arg: any[]) {
		if (props.position == 'left') {
			if (show.value) return 'mdi:keyboard-arrow-left'
			else return 'mdi:keyboard-arrow-right'
		}
		else if (props.position == 'right') {
			if (show.value) return 'mdi:keyboard-arrow-right'
			else return 'mdi:keyboard-arrow-left'
		}
		else if (props.position == 'top') {
			if (show.value) return 'mdi:keyboard-arrow-up'
			else return 'mdi:keyboard-arrow-down'
		}
		else if (props.position == 'bottom') {
			if (show.value) return 'mdi:keyboard-arrow-down'
			else return 'mdi:keyboard-arrow-up'
		}
	}
	down(e: MouseEvent) {
		e.stopPropagation()
		const drager = new Drager()
		const expand0 = expand.value
		const weight = crossMatrix[props.position]
		const dt = Date.now()
		if (!weight) return
		drager.move = (dx, dy) => {
			if (Date.now() - dt < 250) return
			const z = expand0 + weight[0] * dx + weight[1] * dy
			if (z < props.size) {
				expand.value = props.size
				show.value = false
			}
			else if (z < props.size * 1.5) {
				expand.value = props.size
				show.value = true
			}
			else {
				expand.value = Math.min(z, props.sizeMax)
				show.value = true
			}
		}
		drager.up = () => {
			if (Date.now() - dt > 250) return
			if (show.value) {
				show.value = false
			}
			else {
				show.value = true
			}
		}
		drager.down(e)
	}
})
watch(() => props.size, () => {
	if (expand.value < props.size) expand.value = props.size
})
</script>
<style lang="scss" scoped>
.layout-shrink-bar {
	position: relative;
	display: inline-block;
	vertical-align: top;
	
	&.left {
		height: 100%;
	}
	
	&.right {
		height: 100%;
	}
	
	&.top {
		width: 100%;
	}
	
	&.bottom {
		width: 100%;
	}
}



.expand-trigger {
	position: absolute;
	
	&.left {
		right: -5px;
		width: 5px;
		height: 100%;
	}
	
	&.right {
		left: -5px;
		width: 5px;
		height: 100%;
	}
	
	&.top {
		bottom: -5px;
		height: 5px;
		width: 100%;
	}
	
	&.bottom {
		top: -5px;
		height: 5px;
		width: 100%;
	}
}

.bar {
	&.left, &.right {
		height: 100%;
		width: v-bind("(expand)+'px'");
	}
	
	&.top, &.bottom {
		width: 100%;
		height: v-bind("(expand)+'px'");
	}
	
	&.hidden {
		display: none;
	}
}

.icon {
	position: absolute;
	
	&.left {
		left: v-bind("(expand)+'px'");
		top: v-bind("(iconPadding)+'px'");
		
		&.hidden {
			left: 0;
		}
	}
	
	&.right {
		right: v-bind("(expand)+'px'");
		bottom: v-bind("(iconPadding)+'px'");
		
		&.hidden {
			right: 0;
		}
	}
	
	&.top {
		top: v-bind("(expand)+'px'");
		right: v-bind("(iconPadding)+'px'");
		
		&.hidden {
			top: 0;
		}
	}
	
	&.bottom {
		bottom: v-bind("(expand)+'px'");
		left: v-bind("(iconPadding)+'px'");
		
		&.hidden {
			bottom: 0;
		}
	}
}
</style>