import {Color} from "@/entity/Color.ts";
import {Modal} from "@/module/modal/entity/Modal.ts";
import {Require} from "@/root/Require.ts";
import type {FileFilter} from "electron"
import {AppModule} from "@/root/AppModule.ts";
import fyurry, {i18n} from "fyurry";
import {ModalAlert} from "@/module/modal/modal/ModalAlert.ts";
import {ModalPrompt} from "@/module/modal/modal/ModalPrompt.ts";
import {ModalConfirm} from "@/module/modal/modal/ModalConfirm.ts";
import {KeyContainer} from "@/module/key/entity/KeyContainer.ts";

export class ModalModule extends AppModule {
	modal: Record<string, typeof Modal> = {}
	modalKey = new KeyContainer()
	modalInstance: Modal[] = []
	modalTop?: string
	//
	async openExplorer(options: Electron.OpenDialogOptions): Promise<Electron.OpenDialogReturnValue> {
		return Require('@electron/remote').dialog.showOpenDialog(options)
	}
	async openExplorerDir(title: string, def?: string) {
		return this.openExplorer({
			properties: ['openDirectory', 'showHiddenFiles'],
			title: i18n.t(title),
			defaultPath: def
		})
	}
	async openExplorerFile(title: string, filters: FileFilter[], def?: string) {
		return this.openExplorer({
			properties: ['openFile', 'showHiddenFiles'],
			title: i18n.t(title),
			defaultPath: def,
			filters
		})
	}
	open(modal: Modal) {
		if (!modal) return
		if (!this.modalInstance.includes(modal)) this.modalInstance.push(modal)
	}
	off(modal: Modal) {
		const index = this.modalInstance.indexOf(modal)
		if (index > -1) this.modalInstance.splice(index, 1)
	}
	alert(content: any, title: any = 'Alert'): Promise<true | false> {
		return new Promise((s) => {
			const modal = new ModalAlert(content)
			modal.title = title
			modal.onCancel = () => s(false)
			modal.onConfirm = () => s(true)
			modal.open()
		})
	}
	prompt(content: any, def = '', title: any = 'Prompt'): Promise<string | null> {
		return new Promise((s) => {
			const modal = new ModalPrompt(content)
			modal.value = def
			modal.title = title
			modal.onCancel = () => s(null)
			modal.onConfirm = (v: any) => s(v)
			modal.open()
		})
	}
	confirm(content: any, title: any = 'Confirm'): Promise<boolean> {
		return new Promise((s) => {
			const modal = new ModalConfirm(content)
			modal.title = title
			modal.onCancel = () => s(false)
			modal.onConfirm = () => s(true)
			modal.open()
		})
	}
	async init(): Promise<void> {
		const theme = fyurry.style.theme
		theme.addItem('modal.text').Extend('text').Group('modal')
		theme.addItem('modal.color').Extend('color', v => new Color(v).alpha(.2).hex()).Group('modal')
		theme.addItem('modal.bar.text').Extend('text').Group('modal')
		theme.addItem('modal.bar.color').Extend('color', v => new Color(v).alpha(.3).darkener(-.5).hex()).Group('modal')
		theme.addItem('modal.color.shadow').Extend('color', v => `0 0 4px 1px ${new Color(v).alpha(.3).hex()}`).Group('modal')
	}
}