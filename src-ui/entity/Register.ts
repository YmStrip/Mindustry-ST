import {fs} from "fyurry";
import {KeyItem} from "@/module/key/entity/KeyItem.ts";

export class Register {
	static label: string
	static id: string
	static icon: string
	static color: string
	static rank = 0

	static serializeDeep(data: any) {
		if (Array.isArray(data)) {
			const res: any[] = []
			for (let item of res) {
				res.push(
					item instanceof Register ? item.serialize() : this.serializeDeep(item)
				)
			}
			return res
		}
		else if (typeof data === 'object') {
			const res: any = {}
			for (let i in res) {
				const item = res[i]
				res[i] = item instanceof Register ? item.serialize() : this.serializeDeep(item)
			}
			return res
		}
		return data
	}
	static configureRecord(data: any, over: any = {}) {
		if (!(typeof data === 'object')) return
		for (let i in over) {
			if (!data[i]) continue
			if (!(data[i] instanceof Register)) continue
			data[i].configure(over[i])
		}
	}
	static key() {
		const key = new KeyItem(this.id)
		key.icon = this.icon
		key.label = this.label
		key.color = this.color
		key.rank = this.rank
		key.doc = this.doc()
		return key
	}
	static doc() {
		return ''
	}
	Label(v:string) {
		this.label = v
		return this
	}
	Icon(v:string) {
		this.icon = v
		return this
	}
	Color(v:string) {
		this.color = v
		return this
	}
	Rank(v:number) {
		this.rank = v
		return this
	}
	label: string = null
	//@ts-ignore
	type: string = this.id
	icon: string = null
	color: string = null
	rank = 0
	changedView = false
	changedViewCount = 0
	changed = false
	changedCount = 0
	serializeCount = 0
	configureCount = 0
	ignoreKeys: Record<string, any> = {
		component: true,
		ignoreKeys: true,
		focus: true,
		changed: true,
		changedCount: true,
		changedView: true,
		changedViewCount: true,
		focusType: true
	}
	//======================================
	//======================================
	//
	change() {
		this.changed = true
		this.changedCount++
	}
	changeView() {
		this.changedView = true
		this.changedViewCount++
	}
	assignByConstructor() {
		for (let i in this.constructor) {
			if (this[i] == null && this[i] !== undefined && this.constructor[i] != undefined) {
				this[i] = this.constructor[i]
			}
		}
	}
	setIgnoreKey(...key: (string | string[])[]) {
		for (let i of key) {
			if (Array.isArray(i)) {
				i.forEach(d => this.ignoreKeys[d] = true)
			}
			else this.ignoreKeys[i] = true
		}
	}
	delIgnoreKey(...key: (string | string[])[]) {
		for (let i of key) {
			if (Array.isArray(i)) {
				i.forEach(d => delete this.ignoreKeys[d])
			}
			else delete this.ignoreKeys[i]
		}
	}
	configure(data: any = {}): this {
		for (let i in data) {
			const item = this[i]
			if (this.ignoreKeys[i]) continue
			if (item instanceof Register) {
				item.configure(data[i] || {})
			}
			else {
				this[i] = item[i]
			}
		}
		this.configureCount += 1
		return this
	}
	serialize() {
		this.serializeCount += 1
		const data: any = {}
		for (let i in this) {
			const item = this[i]
			if (this.ignoreKeys[i]) continue
			data[i] = item instanceof Register ? item.serialize() : item
		}
		return data
	}
	writeFi(fi: string) {
		fs.setJson(fi, this.serialize())
	}
	write() {
		if (!this.changed) return
		this.changed = false
		this.writeFi(this.path())
	}
	readFi(fi: string) {
		this.configure(fs.getJson(fi) || {})
	}
	read() {
		this.readFi(this.path())
		this.changed = false
	}
	path() {
		return ''
	}
	pathChild(name: string) {
		return this.path() + '/' + name
	}
	setDirParent() {
		fs.setDir(fs.dirname(this.path()))
	}
	setDir() {
		fs.setDir(this.path())
	}
	//======================================
	//
	clearObject(data: any) {
		if (Array.isArray(data)) for (let i = data.length; --i >= 0;) data.pop()
		else for (let i in data) delete data[i]
	}
	Set<T extends keyof this>(key: T, value: this[T]) {
		this[key] = value
		return this
	}
	Del<T extends keyof this>(key: T) {
		delete this[key]
		return this
	}
	//======================================
	//
	constructor() {
		this.assignByConstructor()
	}
}

export class Component extends Register {
	render(): any {
	}
	//mounted in ui
	onMounted(): any {
	}
	//unmounted in ui
	onUnmounted(): any {
	}
	//focus by group
	onFocus(): any {
	}
	//blur by group
	onBlur(): any {
	}
	onResize(): any {
	}
	onMousedown(e: MouseEvent) {
	}
	onMouseup(e: MouseEvent) {
	}
	onClick(e: MouseEvent) {
	}
	rect: ComponentRect = new ComponentRect()
	focusType: string
	recentMouse: MouseEvent
}
export class ComponentRect {
	w = 0
	h = 0
	x = 0
	y = 0
}