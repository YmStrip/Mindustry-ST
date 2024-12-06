<template>
	<Button hover-able>
		<Icon icon="mdi:triangle-outline" v-if="!image"></Icon>
		<Image v-bind:src="<any>icon" width="16px" height="16px" v-else></Image>
	</Button>
</template>
<script lang="ts" setup>
import Button from "@/components/button/Button.vue";
import Icon from "@/module/icon/component/Icon.vue";
import Image from "@/module/icon/component/Image.vue";
import icon from "./icon.png"
const props = defineProps<{
	image?: boolean
}>()
</script>
