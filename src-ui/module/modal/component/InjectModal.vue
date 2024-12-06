<template>
	<LayoutAbsolute
		left="0"
		top="0"
		width="100%"
		height="100%"
		z-index="10"
		pointer="none"
	>
		<ModalComp
			v-for="d in modal.modalInstance"
			v-bind:modal="d"
		/>
	</LayoutAbsolute>
</template>
<script lang="ts" setup>

import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";
import ModalComp from "@/module/modal/component/ModalComp.vue";
import {modal} from "fyurry";
</script>