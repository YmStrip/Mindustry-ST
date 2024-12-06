import {Modal} from "@/module/modal/entity/Modal.ts";

export type ModalProps<T extends Modal=Modal> = {
	modal:T
}