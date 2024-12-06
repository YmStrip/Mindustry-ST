<template>
	<Image
			v-if="icon=='fyurry'||icon=='app'"
			v-bind:src="IconAppPath"
			width="16px"
			height="16px"
	/>
	<ICON_IFY
			v-else-if="fyurry.icon.include(icon+'')||(typeof icon==='object')"
			v-bind="props"
	/>
	<Image
			v-else
			v-bind:src="icon"
			width="16px"
			height="16px"
	/>
</template>
<script setup lang="ts">
import {IconAppPath} from "@/module/icon/component/IconAppPath.ts";
import Image from "@/module/icon/component/Image.vue";
import {Icon as ICON_IFY, IconProps} from "@iconify/vue/offline";
import fyurry from "fyurry";
const props = defineProps<IconProps>()
</script>