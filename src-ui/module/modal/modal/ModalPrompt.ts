import {Modal} from "@/module/modal/entity/Modal.ts";
// @ts-ignore
import ModalPromptComp from "@/module/modal/modal/ModalPromptComp.vue";

export class ModalPrompt extends Modal {
	component = ModalPromptComp
	onCancel() {
	}
	onConfirm(v: any) {
	}
	value = ''
	constructor(public context: any, id?: any) {
		super(id);
		this.resizeCenterAuto(350)
	}
}