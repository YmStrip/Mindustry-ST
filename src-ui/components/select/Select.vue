<template>
	<ObserverForm
		v-bind="props"
		v-bind:o="observer"
		class="select-container"
		name="select"
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
			<Popover
				style="max-height: 250px;"
				v-model:show="<any>show"
				v-bind:placement="popover?.placement||'bottom-start'"
				v-bind:trigger="popover?.trigger||'click'"
				v-bind:style="popoverStyle"
			>
				<template #trigger>
					<div class="select-body">
						<slot
							name="empty"
							v-if="!options?.length"
							v-bind:style="style"
							v-bind:value="value"
							v-bind:item="item"
							v-bind:index="index"
						>
							<Icon
								style="margin-right: 5px;"
								v-bind:icon="item?.icon||'mdi:cube-send'"
							/>
							<LayoutFlex justify-content="center" full>
								[ NOT DATA ]
							</LayoutFlex>
						</slot>
						<slot
							name="no-select"
							v-else-if="!item"
							v-bind:style="style"
							v-bind:value="value"
							v-bind:item="item"
							v-bind:index="index"
						>
							<Icon
								style="margin-right: 5px;"
								v-bind:icon="item?.icon||'mdi:cube-send'"
							/>
							<LayoutFlex full justify-content="center">
								[ SELECT ]
							</LayoutFlex>
						</slot>
						<slot
							v-else
							v-bind:style="style"
							v-bind:value="value"
							v-bind:item="item"
							v-bind:index="index"
						>
							<Icon
								style="margin-right: 5px;"
								v-bind:icon="item?.icon||'mdi:cube-send'"
							/>
							{{ item?.label || item?.value || index }}
						</slot>
					</div>
				</template>
				<template #default>
					<LayoutScroll style="max-height:inherit;height:100%;width: 100%;">
						<template v-for="(d,i) in options">
							<slot
								v-bind:style="style"
								v-bind:value="value"
								v-bind:item="item"
								v-bind:index="index"
								v-bind:option="d"
								v-bind:optionIndex="i"
							>
								<LayoutFlex
									style="width: 100%;height: 22px;"
									@click="()=>{
										value = d.value
										index = i
										item = d
										show = <any>false
									}"
								>
									<Button v-bind:color="d.color">
										<Icon v-bind:icon="d.icon"/>
									</Button>
									<Button v-bind:color="d.color" clip justify-content="flex-start">
										{{ d.label || d.value || i }}
									</Button>
								</LayoutFlex>
							</slot>
						</template>
					</LayoutScroll>
				</template>
			</Popover>
		</template>
	</ObserverForm>
</template>
<script lang="ts" setup>
import {onMounted, reactive, ref, watch} from "vue";
import {style} from "fyurry";
import {Observer} from "@/components/observer/Observer.ts";
import ObserverForm from "@/components/observer/ObserverForm.vue";
import {SelectProps} from "@/components/select/SelectProps.ts";
import {SelectOption} from "@/components/select/SelectOption.ts";
import Button from "@/components/button/Button.vue";
import Icon from "@/module/icon/component/Icon.vue";
import Popover from "@/components/popover/Popover.vue";
import LayoutScroll from "@/components/scroll/LayoutScroll.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";

const props = withDefaults(defineProps<SelectProps>(), {
	alignItems: 'center',
	justifyContent: 'flex-start',
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


const observer = reactive(new Observer())
const value = defineModel<any>('value')
const show = defineModel<boolean>('show', {default: false})
const item = ref<SelectOption>()
const index = ref(-1)
const diff = () => {
	for (let i = -1, l = props.options.length; ++i < l;) {
		const d = props.options[i]
		if (d.value === value.value) {
			item.value = d
			index.value = i
			return
		}
	}
	item.value = null
	index.value = -1
}
const setDef = () => {
	if (!props.options.length) return
	if (props.defaultValue == undefined || props.defaultValue == value.value) return
	if (value.value != undefined) return
	for (let i = 0, l = props.options.length; i < l; ++i) {
		const d = props.options[i]
		if (d.value === props.defaultValue) {
			value.value = d.value
			return diff()
		}
	}
}
if (props.defaultValue != undefined) {
	setDef()
}
watch(value,()=>{
	diff()
})
watch(() => props.defaultValue, () => setDef())
onMounted(()=>{
	diff()
})
</script>
<style lang="scss" scoped>
.select-container {
	pointer-events: all;
	margin: 1px;
	display: inline-block;
	font-size: 14px;
	min-width: 150px;
}

.select-body {
	justify-content: v-bind(justifyContent);
	align-items: v-bind(alignItems);
	height: 100%;
	width: 100%;
	display: inline-flex;
	padding: 0 5px;
	position: relative;
}
</style>