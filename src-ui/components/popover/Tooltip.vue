<template>
	<Popover
		trigger="hover"
		v-bind="props"
		v-model:show="<any>show"
		style="height: auto;min-height: 0;text-align: center;"
		v-bind:style="style"
	>
		<template #default>
			<slot name="tooltip">
				{{tooltip}}
			</slot>
		</template>
		<template #trigger>
			<span>
				<slot/>
			</span>
		</template>
	</Popover>
</template>
<script lang="ts" setup>
import {PopoverProps} from "@/components/popover/PopoverProps.ts";
import Popover from "@/components/popover/Popover.vue";

const props = withDefaults(defineProps<PopoverProps & { tooltip?: string,style?:any }>(), {
	placement: 'bottom',
	blur: true
})
const show = defineModel<boolean>('show')
</script>