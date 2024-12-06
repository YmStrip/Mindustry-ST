<template>
	<div
		v-bind:class="['flex',{
			full,
			column,
			label,
		}]"
		v-bind:style="{
			flex: flex||'',
			background: background || ''
		}"
	>
		<slot></slot>
	</div>
</template>
<script lang="ts" setup>
import {LayoutFlexProps} from "@/components/layout/LayoutFlexProps.ts";
import {ref} from "vue";

const props = withDefaults(defineProps<LayoutFlexProps>(), {
	alignItems: 'center'
})
const container = ref<HTMLElement>()

</script>
<style lang="scss" scoped>
.flex {
	height: v-bind(height);
	width: v-bind(width);
	display: flex;
	justify-content: v-bind(justifyContent);
	align-items: v-bind(alignItems);

	&.label {
		height: 22px;
	}
}

.column {
	flex-direction: column;
}

.full {
	flex: 1;
}

</style>