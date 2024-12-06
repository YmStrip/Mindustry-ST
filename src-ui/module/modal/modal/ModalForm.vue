<template>
	<ModalSolid
		v-bind:modal="modal"
	>
		<LayoutFlex height="100%" width="100%" column>
			<LayoutFlex style="flex: 1;position: relative;width: 100%;">
				<LayoutScroll style=";width: 100%;height: 100%;position:absolute;">
					<slot/>
				</LayoutScroll>
			</LayoutFlex>
			<LayoutFlex
				justify-content="flex-end"
				width="100%"
				style="padding: 0 10px;height: 30px;"
			>
				<Button
					v-bind:height="25"
					style="margin-right: 10px;"
					@click="emits('cancel')"
				>
					<Icon
						icon="mdi:close"
						width="25px"
						height="25px"
					/>
				</Button>
				<Button
					v-bind:height="25"
					
					@click="emits('confirm')"
				>
					<Icon
						icon="mdi:check"
						width="25px"
						height="25px"
					/>
				</Button>
			</LayoutFlex>
		</LayoutFlex>
	</ModalSolid>
</template>
<script lang="ts" setup>
import ModalSolid from "@/module/modal/component/ModalSolid.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import LayoutScroll from "@/components/scroll/LayoutScroll.vue";
import Button from "@/components/button/Button.vue";
import Icon from "@/module/icon/component/Icon.vue";
import {Modal} from "@/module/modal/entity/Modal.ts";
import {EventEmitter} from "@/entity/EventEmitter.ts";
import {onUnmounted} from "vue";

const props = withDefaults(defineProps<{ modal: Modal, keybind?: boolean }>(),{
	keybind: false
})
const emits = defineEmits<{
	(e: "confirm"): any
	(e: 'cancel'): any
}>()
const event = new EventEmitter()
event.onMountedWindow('keydown', e => {
	if (!props.keybind) return;
	if (e.key == 'Escape') {
		emits('cancel')
	}
	else if (e.key == 'Enter') {
		if (e.shiftKey) return
		emits('confirm')
	}
}, onUnmounted)
</script>