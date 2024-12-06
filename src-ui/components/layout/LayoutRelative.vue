<template>
	<LayoutAbsolute style="position: relative" v-bind="props">
		<slot></slot>
	</LayoutAbsolute>
</template>
<script lang="ts" setup>
import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";
import {LayoutAbstractProps} from "@/components/layout/LayoutAbstractProps.ts";

const props = defineProps<LayoutAbstractProps>()
</script>