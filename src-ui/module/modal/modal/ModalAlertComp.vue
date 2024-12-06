<template>
	<ModalForm
		v-bind:modal="modal"
		@cancel="modal.onCancel();modal.close()"
		@confirm="modal.onConfirm();modal.close()"
		keybind
	>
		<component
			v-if="modal.context"
			v-bind:is="typeof (modal.context) === 'string'?()=>modal.context:modal.context"
			v-bind:modal="modal"
		/>
	</ModalForm>
</template>
<script lang="ts" setup>
import {ModalAlert} from "@/module/modal/modal/ModalAlert.ts";
import ModalForm from "@/module/modal/modal/ModalForm.vue";

const props = defineProps<{ modal: ModalAlert }>()
</script>