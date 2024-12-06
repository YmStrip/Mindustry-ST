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
import ModalForm from "@/module/modal/modal/ModalForm.vue";
import {ModalConfirm} from "@/module/modal/modal/ModalConfirm.ts";

const props = defineProps<{ modal: ModalConfirm }>()
</script>