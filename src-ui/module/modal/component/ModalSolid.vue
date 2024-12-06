<template>
	<div
		style="width: 100%;height: 100%;background-size: 100% 100%;pointer-events: none"
		v-bind:style="{
			filter: Color.filterScreen('red','rgba(255,255,255,0.8)')+' drop-shadow(0px 0px 10px rgba(255,255,255,.8))',
			backgroundImage: `url(${al})`
		}"
	/>
	<div style="width: 100%;height: 100%;backdrop-filter: blur(1px)">
		<div style="position: absolute;left: 15%;top: 10.5%;width: 78.5%;height: 30px;font-weight: bold;font-size: 20px;">
			<slot name="header">
				{{ modal.title }}
			</slot>
		</div>
		<div
			style="position: absolute;left: 15%;top: calc(10.5% + 35px);width: 78.5%;height: calc(100% - 12.5% - 48px)">
			<slot/>
		</div>
	</div>
</template>
<script lang="ts" setup>
import al from "@/assets/ui/alert.png"
import {Color} from "@/entity";
import {ModalProps} from "@/module/modal/entity/ModalProps.ts";
const props = defineProps<ModalProps>()

</script>