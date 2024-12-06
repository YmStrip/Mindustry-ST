<template>
	<div
		v-bind:style="{
				width: ((w>h?1:w/h/(wp/hp))*100)+'%',
				height: ((h>w?1:h/w/(hp/wp))*100)+'%',
			}"
	>
		<slot/>
	</div>
</template>
<script lang="ts" setup>

const props = defineProps<{
	w: number
	h: number
	wp: number
	hp: number
}>()
</script>