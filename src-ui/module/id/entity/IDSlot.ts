import {EventEmitter} from "@/entity/EventEmitter.ts";
import {Register} from "@/entity/Register.ts";
import {IDBus} from "@/module/id/entity/IDBus.ts";
import {IDProvider} from "@/module/id/entity/IDProvider.ts";
import fyurry from "fyurry";
//multi
export class IDSlot<T = any> extends Register {
	event = new EventEmitter<{
		del: []
		update: []
		rename: [newName: string, oldName: string]
	}>()
	inited = false
	icon: string = ''
	target = ''
	//vue update trigger
	update = 0
	//user desc
	desc() {
		return 'User:' + this.id
	}
	//not auto register when constructor ,
	//init to register and setID target
	init() {
		//check name exist
		this.inited = true
		const bus = this.bus.group(this.type)
		const newName = bus.idsName(this.id)
		this.id = newName
		bus.setIds(this)
		this.update++
		this.bus.update++
		this.event.emitAsync('update')
		return newName
	}
	//rename self id,override to do something when return newName (if newName===oldName , then rename failed)
	rename(name: string) {
		if (!name) name = 'slot.' + this.type
		if (name == this.id) return name
		const lastTarget = this.target
		const bus = this.bus.group(this.type)
		const newId = bus.idsName(name)
		const lastId = this.id
		this.delID()
		bus.delIds(this.id)
		this.id = newId
		bus.setIds(this)
		this.setID(lastTarget)
		this.event.emit('rename', lastId, newId)
		this.event.emitAsync('update')
		this.update++
		this.bus.update++
		return newId
	}
	//on id provider rename
	onRename(newName: string, data: IDProvider) {
		this.update++
		this.bus.update++
		this.target = newName
	}
	//on id provider deleted
	onDel(data: IDProvider) {
		this.update++
		this.bus.update++
		delete this.target
	}
	//on id provider update
	onSet(data: IDProvider) {
		this.update++
		this.bus.update++
	}
	//on id provider change user
	onChangeUser(data: IDProvider) {
		this.update++
		this.bus.update++
	}
	checkInited() {
		if (!this.inited) throw new Error('use .init() first')
	}
	del() {
		this.checkInited()
		this.delID()
		this.bus.group(this.type).delIds(this.id)
		this.event.emit('del').then(d => {
			this.event.clear()
		})
		this.event.emitAsync('update')
	}
	//set target id
	setID(id: string) {
		this.checkInited()
		if (this.target == id) return this.getID()
		const v = this.bus.group(this.type).idp[id]
		if (!v) return undefined
		this.delID()
		this.target = id
		v.user[this.id] = this.desc()
		v.changeUser()
		this.onSet(v)
		this.event.emitAsync('update')
		return v
	}
	//set none user
	delID() {
		this.checkInited()
		const v = this.bus.group(this.type).idp[this.target]
		if (v) v.changeUser()
		const last = this.bus.group(this.type).idp[this.target]
		if (last) {
			delete last.user[this.id]
			last.changeUser()
		}
		if (v) this.onDel(v)
		this.event.emitAsync('update')
		delete this.target
	}
	//get target id
	getID(): IDProvider<T> {
		if (!this.target) return
		const v = this.bus.group(this.type).idp[this.target]
		if (!v) delete this.target
		return v
	}

	configure(data: any): any {
		super.configure(data);
		//likely rename
		this.delID()
		this.setID(data.target)
		return this
	}
	typeCount(...arg: any[]) {
		//get type count
		return Object.keys(this.bus.group(this.type).idp).length
	}
	Update() {
		const data = this.getID()
		if (data) this.onSet(data)
		return this
	}
	//a member of an idp will be deleted when the idp is deleted
	parentIdp(idp: IDProvider) {
		idp.event.on('del', () => {
			this.del()
		})
	}
	//IDSlot TYPE
	constructor(public bus: IDBus, public type: string, public id: string = fyurry.id.id()) {
		super()
		this.setIgnoreKey(['bus', 'icon', 'id', 'inited'])
	}
}