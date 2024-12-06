import {IDBus} from "@/module/id/entity/IDBus.ts";
import {IDProvider} from "@/module/id/entity/IDProvider.ts";
import {IDSlot} from "@/module/id/entity/IDSlot.ts";
//ID Refactor Factory,
//Refactor A not instance ID,if ID already instance, may just need to rename.
export abstract class IDFactoryInstance {
	group() {
		return this.bus.group(this.type)
	}
	ids: Record<string, { ids: IDSlot, context: any }> = {}
	idp: Record<string, { idp: IDProvider, context: any }> = {}
	abstract handleIdsInstance(ids: IDSlot, context: any, oldName: any, newName: any): IDSlot
	abstract handleIdpInstance(ids: IDProvider, context: any, oldName: any, newName: any): IDProvider
	handleIdsNewName(ids: IDSlot) {
		return this.group().idsName(ids.id)
	}
	handleIdpNewName(idp: IDProvider) {
		return this.group().idpName(idp.id)
	}
	putIds(ids: IDSlot, context: any) {
		this.ids[ids.id] = {ids, context}
		return this
	}
	putIdp(idp: IDProvider, context: any) {
		this.idp[idp.id] = {idp, context}
		return this
	}
	start() {
		this.ids = {}
		this.idp = {}
		return this
	}
	end() {
		//iter ids, instance it
		const rename: Record<string, string> = {}
		for (let i in this.idp) {
			const idp = this.idp[i]
			const oldname = i
			const newname = this.handleIdpNewName(idp.idp)
			idp.idp.id = newname
			rename[oldname] = newname
			this.handleIdpInstance(idp.idp, idp.context, oldname, newname)
		}
		for (let i in this.ids) {
			const ids = this.ids[i]
			const oldname = i
			const newname = this.handleIdsNewName(ids.ids)
			ids.ids.id = newname
			const idsInstance = this.handleIdsInstance(ids.ids, ids.context, oldname, newname)
			if (rename[idsInstance.target]) {
				idsInstance.setID(rename[idsInstance.target])
			}
		}
	}
	constructor(public bus: IDBus, public type: string) {
	}
}