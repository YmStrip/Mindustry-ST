import {Modal} from "@/module/modal/entity/Modal.ts";
// @ts-ignore
import ModalAlertComp from "@/module/modal/modal/ModalAlertComp.vue";

export class ModalConfirm extends Modal {
	component = ModalAlertComp
	onCancel(){}
	onConfirm(){}
	constructor(public context: any, id?: any) {
		super(id);
		this.resizeCenterAuto(350)
	}
}