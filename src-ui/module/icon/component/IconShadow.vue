<template>
	<div
		style="display: inline-flex;position: relative;width: auto;height: auto;align-items: center;justify-content: center;">
		<Icon v-bind="props"/>
		<div
			style="position: absolute;left: 50%;top: 50%;"
			v-bind:style="{
				boxShadow: `0 0 ${size}px ${blur}px`
			}"
		/>
	</div>
</template>
<script setup lang="ts">
import {IconProps} from "@iconify/vue/offline";
import Icon from "@/module/icon/component/Icon.vue";

const props = withDefaults(defineProps<IconProps & {
	size?: number
	blur?: number
}>(), {
	size: 8,
	blur: 4
})
</script>