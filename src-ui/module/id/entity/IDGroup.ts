import {SortGroup} from "@/entity/SortGroup.ts";
import {IDSlot} from "@/module/id/entity/IDSlot.ts";
import {IDProvider} from "@/module/id/entity/IDProvider.ts";
import {id} from "fyurry";
//Flow Mode
export class IDGroup extends SortGroup {
	ids: Record<string, IDSlot> = {}
	idp: Record<string, IDProvider> = {}
	idpEach(call: (data: IDProvider) => any) {
		for (let i in this.idp) {
			call(this.idp[i])
		}
		return this
	}
	idsEach(call: (data: IDSlot) => any) {
		for (let i in this.ids) {
			call(this.ids[i])
		}
		return this
	}
	//not fire event!
	delIds(id: string) {
		delete this.ids[id]
		return this
	}
	//not fire event!
	delIdp(id: string) {
		delete this.idp[id]
		return this
	}
	pipe(v: () => any) {
		v()
		return this
	}
	//not fire event!
	setIds(data: IDSlot) {
		this.ids[data.id] = data
		return this
	}
	//not fire event!
	setIdp(data: IDProvider) {
		this.idp[data.id] = data
		return this
	}
	//break flow
	idsName(key: string,) {
		return id.key(key, v => this.ids[v])
	}
	idpName(key: string,) {
		return id.key(key, v => this.idp[v])
	}
	//Find Available
	availableIds(dir: 'start' | 'end' = 'end') {
		const keys = Object.keys(this.ids)
		return dir == 'start' ? keys[0] : keys[keys.length - 1]
	}
	availableIdp(dir: 'start' | 'end' = 'end') {
		const keys = Object.keys(this.idp)
		return dir == 'start' ? keys[0] : keys[keys.length - 1]
	}
	//not support
	Provide() {
		return this
	}
}