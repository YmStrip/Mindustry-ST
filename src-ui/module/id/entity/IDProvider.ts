import {EventEmitter} from "@/entity/EventEmitter.ts";
import {Register} from "@/entity/Register.ts";
import {IDBus} from "@/module/id/entity/IDBus.ts";
import fyurry, {fs} from "fyurry";
//id provider usually not save data to reduce complexity
export class IDProvider<T = any> extends Register {
	event = new EventEmitter<{
		del: []
		rename: [newName: string, oldName: string]
		set: []
	}>()
	icon: string = ''
	//vue update trigger
	update = 0
	//FakeUser mode
	fake = false
	//user list
	user: Record<string, any> = {}
	inited = false
	//slot desc
	desc() {
		return 'Slot:' + this.id
	}
	//it will not be registered when constructor
	//init check no repeat id and register
	init() {
		this.inited = true
		//check name exist
		const bus = this.bus.group(this.type)
		const newName = bus.idpName(this.id)
		this.id = newName
		bus.setIdp(this)
		this.update++
		this.bus.update++
		return newName
	}
	checkInited() {
		if (!this.inited) throw new Error('use .init() first')
	}
	//check all use count , delete if not fake and user is 0
	//if id slot not init , it will not add user to this
	fakeDel() {
		if (this.fakeTest()) this.del()
	}
	fakeTest() {
		this.calcUser()
		return !(this.fake || this.userCount() != 0);
	}
	//offline , but not change user
	offline() {
		this.bus.group(this.type).delIdp(this.id)
	}
	//send a del event , override to do some when del
	del() {
		this.checkInited()
		this.bus.group(this.type)
			.idsEach(ids => {
				if (ids.target == this.id) {
					ids.onDel(this)
					ids.event.emitAsync('update')
				}
			})
			.delIdp(this.id)
		this.event.emit('del').then(d => {
			this.event.clear()
		})
		this.update++
		this.bus.update++
	}
	//rename id, and all user will also get the onRename() event. by default, them will modify the target to the new name
	protected _rename(newName: string) {
		this.checkInited()
		if (!newName) newName = this.type
		if (newName == this.id) return newName
		//console.log(newName)
		//get a available name
		const lastId = this.id
		const bus = this.bus.group(this.type)
		newName = bus.idpName(newName)
		this.id = newName
		//iter all user , and rename
		bus.idsEach(ids => {
			if (ids.target == lastId) {
				ids.onRename(newName, this)
				ids.event.emitAsync('update')
			}
		})
		//
		bus.delIdp(lastId)
		bus.setIdp(this)
		this.event.emit('rename', lastId, newName)
		this.update++
		this.bus.update++
		return newName
	}
	rename(newName: string) {
		return this._rename(newName)
	}
	//make sure that has path and id , rename path and rename container item
	renameContainerItem(newName: string, that: { path(): string, id: any }, container: any) {
		if (newName == that.id) return newName
		const oldId = that.id
		const oldPath = that.path()
		const newId = this._rename(newName);
		that.id = newId
		const newPath = that.path()
		delete container[oldId]
		container[newId] = that
		fs.rename(oldPath, newPath)
		return newId
	}
	renameRecord(newName: string, that: { id: any }, container: any) {
		if (newName == that.id) return newName
		const oldId = that.id
		const newId = this._rename(newName);
		that.id = newId
		delete container[oldId]
		container[newId] = that
		return newId
	}
	//when something is changed, increase this update and user update value
	set() {
		this.checkInited()
		this.bus.group(this.type)
			.pipe(() => {
				this.update++
				this.bus.update++
			})
			.idsEach(ids => {
				if (ids.target == this.id) {
					ids.onSet(this)
					ids.event.emitAsync('update')
				}
			})
		this.event.emit('set')
	}
	//triggered when the user set or del, the user will also get onChangeUser()
	changeUser() {
		this.checkInited()
		this.bus.group(this.type)
			.pipe(() => {
				this.update++
				this.bus.update++
			})
			.idsEach(ids => {
				if (ids.target == this.id) {
					ids.onChangeUser(this)
					ids.event.emitAsync('update')
				}
			})
	}
	//re calc
	calcUser() {
		this.checkInited()
		this.user = {}
		const group = this.bus.group(this.type)
		for (let i in group.ids) {
			const id = group.ids[i]
			if (id.target == this.id) {
				this.user[i] = id.desc()
			}
		}
	}
	userCount() {
		return Object.keys(this.user).length
	}
	//get data
	get(): T {
		return null
	}
	//n principle, dynamic modify type is not support, which would be very matter.
	constructor(public bus: IDBus, public type: string, public id = fyurry.id.id()) {
		super();
		this.setIgnoreKey([
			'event', 'user', 'inited', 'type', 'id', 'bus'
		])
	}
}