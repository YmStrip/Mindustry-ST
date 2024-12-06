<template>
	<ObserverComp
		v-bind:o="observer"
		v-bind:class="{top:fyurry.modal.modalTop==modal.id}"
		v-bind:style="{
			left:`${modal.x}px`,
			top:`${modal.y}px`,
			width:`${modal.w}px`,
			height:`${modal.h}px`,
			zIndex:modal.zIndex
		}"
		@mousedown.stop="fyurry.modal.modalTop=modal.id"
		class="modal"
	>
		<component
			v-bind:is="modal.component"
			v-bind:modal="modal"
		/>
	</ObserverComp>
</template>
<script lang="ts" setup>
import {ModalProps} from "@/module/modal/entity/ModalProps.ts";
import {onMounted, onUnmounted} from "vue";
import fyurry from "fyurry";
import ObserverComp from "@/components/observer/ObserverComp.vue";
import {ObserverComponent} from "@/components/observer/ObserverComponent.ts";

const props = defineProps<ModalProps>()
const observer = new ObserverComponent(props.modal)
</script>
<style lang="scss" scoped>
.modal {
	position: absolute;
	pointer-events: all;
	&.top {
		z-index: 9999;
	}
}
</style>