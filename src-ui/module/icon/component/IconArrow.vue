<template>
	<Icon v-bind:icon="value?arrowOpen:arrowClose" @click="value=<any>!value"></Icon>
</template>
<script lang="ts" setup>
import Icon from "@/module/icon/component/Icon.vue";

const props = withDefaults(defineProps<{
	arrowOpen?: string,
	arrowClose?: string
}>(), {
	arrowOpen: 'mdi:keyboard-arrow-down',
	arrowClose: 'mdi:keyboard-arrow-right'
})
const value = defineModel<boolean>('value')
</script>