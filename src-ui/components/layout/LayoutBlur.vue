<template>
	<div v-bind:class="{blur,mount}">
		<slot/>
	</div>
</template>
<script lang="ts" setup>
import {onMounted, ref} from "vue";

const props = withDefaults(defineProps<{
	blur?: boolean
	duration?: number
	blurTime?: number
}>(), {
	blur: true,
	duration: 800,
	blurTime: 250
})
const mount = ref(false)
onMounted(() => {
	setTimeout(() => {
		mount.value = true
	}, props.blurTime)
})
</script>
<style lang="scss" scoped>
.blur {
	transition-duration: 0.3s;
	transition: opacity v-bind('duration+"ms"');
	opacity: 0;
	backdrop-filter: blur(5px);

	&.mount {
		opacity: 1;
	}
}
</style>