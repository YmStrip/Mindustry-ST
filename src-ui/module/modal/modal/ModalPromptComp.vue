<template>
	<ModalForm
		v-bind:modal="modal"
		@cancel="modal.onCancel();modal.close()"
		@confirm="modal.onConfirm(modal.value);modal.close()"
		keybind
	>
		<component
			v-if="modal.context"
			v-bind:is="typeof (modal.context) === 'string'?()=>modal.context:modal.context"
			v-bind:modal="modal"
		/>
		<Input
			v-model:value="modal.value"
			auto-focus
			@keydown.stop="e=>{
				if(e.key=='Enter'&&!e.shiftKey) {
					modal.onConfirm(modal.value);modal.close()
				}
			}"
		/>
	</ModalForm>
</template>
<script lang="ts" setup>
import ModalForm from "@/module/modal/modal/ModalForm.vue";
import {ModalPrompt} from "@/module/modal/modal/ModalPrompt.ts";
import Input from "@/components/input/Input.vue";
import {ref} from "vue";

const props = defineProps<{ modal: ModalPrompt }>()
</script>