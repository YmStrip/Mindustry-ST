<template>
	{{ translate(text || '', i18n.config.lang) }}
	<slot></slot>
</template>
<script lang="ts" setup>

import {i18n} from "fyurry";

const props = defineProps<{
	text?: string
}>()
const translate = (text: any, ...arg) => {
	return i18n?.t(text)
}
</script>