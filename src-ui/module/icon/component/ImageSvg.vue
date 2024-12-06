<template>
	<img
			v-bind:src="src+(noPreserveAspectRatio?'#svgView(preserveAspectRatio(none))':'')"
			v-bind:style="{
				filter: `drop-shadow(${color||'#000'} 100px 0)`,
				transform: `${rotate?`rotate(${rotate}) `:''}translateX(-100px)`,
				width: wf?'100%':w||'',
				height: hf?'100%':h||'',
				flex: clip?1:'',
				boxShadow: shadow
			}"
	/>
</template>
<script lang="ts" setup>


const props = defineProps<{
	src: any
	color?: string
	shadow?:string
	noPreserveAspectRatio?: boolean
	rotate?:string
	wf?: boolean
	hf?: boolean
	w?: string
	h?: string
	clip?: boolean
}>()
</script>