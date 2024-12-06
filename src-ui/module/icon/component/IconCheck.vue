<template>
	<Icon icon="ic:outline-check-box-outline-blank" v-if="!value"/>
	<Icon icon="ic:outline-check-box" v-else/>
</template>
<script lang="ts" setup>
import Icon from "@/module/icon/component/Icon.vue";

const props = defineProps<{
	value?: boolean
}>()
</script>