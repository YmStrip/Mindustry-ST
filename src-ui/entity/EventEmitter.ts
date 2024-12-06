import {Timer} from "@/entity/Timer.ts";
import {out,id as _id} from "fyurry";

export type EventEmitterArg<T> = T extends any[] ? T : [T]
export type EventEmitterListenerArg<T extends Record<string, any>, K extends (keyof T) | EventClass> =
	K extends keyof T ? (EventEmitterArg<T[K]>) :
		K extends EventClass ? EventEmitterArg<K['props']> :
			([])
export type EventEmitterListener<T extends Record<string, any>, K extends (keyof T) | EventClass> = (...arg: EventEmitterListenerArg<T, K>) => ('break' | false | 'breakFlow' | void) | Promise<('break' | false | 'breakFlow' | void)>
//virtual event bind
//let p = 0
export class EventPipe {
	public parent: EventEmitter = <any>{}
	linked = false
	event: Record<string, any[]> = {}
	set(key: string, listener: any) {
	}
	del(key: string, listener: any) {
	}
	_set(key: string, listener: any) {
		if (!this.event[key]) this.event[key] = []
		this.event[key].push(listener)
		this.set(key, listener)
	}
	_del(key: string, listener: any) {
		//Yurry.out.errorStack()
		//++p
		//if (p > 50) throw new Error('U')
		if (this.event[key]) {
			const index = this.event[key].indexOf(listener)
			if (index > -1) this.event[key].splice(index, 1)
		}
		this.del(key, listener)
	}
	_delAll(key: string) {
		for (let i in this.event[key]) {
			this._del(key, this.event[key][i])
		}
	}
	destroy() {
		//console.log('destroy')
		for (let i in this.event) {
			const eventList = this.event[i]
			//console.log('inA ', i, eventList)
			for (let j of eventList) {
				//console.log('inB ', j)
				this._del(i, j)
			}
		}
		delete this.parent.pipeList[this.id]
	}
	instead(pipe: EventPipe) {
		this.destroy()
		delete this.parent.pipeList[this.id]
		return this.parent.pipe(pipe)
	}
	link() {
		if (this.linked) return
		this.linked = true
		this.forceLink()
	}
	forceLink() {
		for (let i in this.parent.onList) {
			for (let j of this.parent.onList[i]) {
				this._set(i, j)
			}
		}
		for (let i in this.parent.onceList) {
			for (let j of this.parent.onceList[i]) {
				this._set(i, j)
			}
		}
	}
	constructor(public id = _id.id()) {
	}
}
//virtual event bind html[hot-map multi EventEmitter to multiple real HTMLElement]
export class EventPipeElement extends EventPipe {
	set(key: string, listener: any) {
		this.el.addEventListener(key, listener)
	}
	del(key: string, listener: any) {
		this.el.removeEventListener(key, listener)
	}
	constructor(public el: EventTarget, id?: string) {
		super(id);
	}
}
//event emitter
export class EventEmitter<T extends Record<string, any> = {}> {
	timer = new Timer()
	//multi onUnmounted , async and after setup , or custom onMounted trigger
	static bus() {
		type Bus = {
			(caller: any): Bus,
			emit(): Promise<any>
			clear(): Bus
		}
		const bus = new EventEmitter<{
			un: []
		}>()
		const un: Bus = <any>((unCall: any) => {
			bus.once('un', unCall)
			return this
		})
		un.emit = () => {
			return bus.emit('un')
		}
		un.clear = () => {
			bus.clear()
			return un
		}
		return un
	}
	static mountedBus(end: any) {
		const bus = this.bus()
		end(() => {
			bus.emit().then(() => bus.clear())
		})
		return bus
	}
	//hot bus bind visual event
	static hotBusElVe(start: any, end: any, getE: () => EventEmitter, getEL: () => HTMLElement) {
		let vep: EventPipe
		const hotBus = this.hotBus(start, end).listen(() => {
			if (vep) vep.destroy()
			const el = getEL()
			const e = getE()
			if (e && el) {
				vep = new class extends EventPipe {
					set(key: string, listener: any) {
						el.addEventListener(key, listener)
					}
					del(key: string, listener: any) {
						el.removeEventListener(key, listener)
					}
				}
				e.pipeMounted(vep, hotBus)
			}
		})
		return hotBus
	}
	//dynamically bind event or pipe base on prop
	static hotBus(start: any, end: any) {
		type DynamicPropsBus = {
			(caller: any): DynamicPropsBus,
			listen(call: () => any): DynamicPropsBus
			emit(): Promise<any>
			dynamic(): DynamicPropsBus
		}
		const bus = new EventEmitter<{
			un: []
			listen: []
		}>()
		const un: DynamicPropsBus = <any>((unCall: any) => {
			bus.once('un', unCall)
			return this
		})
		//@ts-ignore
		un.bus = bus
		un.listen = (call) => {
			bus.on('listen', call)
			return un
		}
		un.emit = () => {
			return bus.emit('un')
		}
		un.dynamic = () => {
			bus.emit('un').then(d => {
				bus.emit('listen')
			})
			return un
		}
		start(() => un.dynamic())
		end(() => {
			un.emit().then(() => bus.clear())
		})
		return un
	}
	//fake click
	static fakeClick(e: MouseEvent, button = 0) {
		return new MouseEvent('click', {
			...e,
			bubbles: true,
			cancelable: true,
			button
		})
	}
	static fakeMouse(e: MouseEvent, as: any = {}) {
		return new MouseEvent('mousedown', {
			...e,
			bubbles: true,
			cancelable: true,
			...as,
		})
	}
	//if want to listen all events to other objects, such as window
	pipeList: Record<string, EventPipe> = {}
	onList: Record<any, any> = {}
	onceList: Record<any, any> = {}
	protected hookOffOn(key: string, listener: any) {
	}
	protected hookOffOnce(key: string, listener: any) {
	}
	protected hookOn(key: string, listener: any) {
	}
	protected hookOnce(key: string, listener: any) {
	}
	protected initEventType(event: any) {
		if (event === 'constructor') throw new Error('error event name: ' + event)
		//@ts-ignore
		if (event.typeClass) event = event.class
		if (!this.onList[event]) this.onList[event] = []
		if (!this.onceList[event]) this.onceList[event] = []
	}
	protected offOn(event: any, listener: any) {
		//@ts-ignore
		if (event.typeClass) event = event.class
		if (this.onList[event]) {
			const onIndex = this.onList[event].indexOf(listener)
			if (onIndex > -1) {
				this.onList[event].splice(onIndex, 1)
				this.hookOffOn(event, listener)
			}
			//if (this.onList[event].length == 0) delete this.onList[event]
		}
	}
	protected offOnce(event: any, listener: any) {
		//@ts-ignore
		if (event.typeClass) event = event.class
		if (this.onceList[event]) {
			const onIndex = this.onceList[event].indexOf(listener)
			if (onIndex > -1) {
				this.onceList[event].splice(onIndex, 1)
				this.hookOffOnce(event, listener)
			}
			//if (this.onceList[event].length == 0) delete this.onceList[event]
		}
	}
	countOn<K extends keyof (Record<string, any> & T) | EventClass>(event: K) {
		//@ts-ignore
		if (event.typeClass) event = event.class
		return (this.onList[event]?.length || 0)
	}
	countOnce<K extends keyof (Record<string, any> & T) | EventClass>(event: K) {
		//@ts-ignore
		if (event.typeClass) event = event.class
		return (this.onceList[event]?.length || 0)
	}
	count<K extends keyof (Record<string, any> & T) | EventClass>(event: K) {
		return this.countOn(event) + this.countOnce(event)
	}
	public type<K extends keyof (Record<string, any> & T) | EventClass>(event: K) {
		//@ts-ignore
		if (event.typeClass) event = event.class
		if (!this.onList[event]) this.onList[event] = <any>{}
		return this
	}
	public on<K extends keyof (Record<string, any> & T) | EventClass>(event: K, listener: EventEmitterListener<T, K>) {
		//@ts-ignore
		if (event.typeClass) event = event.class
		this.initEventType(event)
		this.onList[event].push(listener)
		this.hookOn(<any>event, listener)
		this.setPipeListener(<any>event, listener)
		return this
	}
	public onTimeout<K extends keyof (Record<string, any> & T) | EventClass>(event: K, listener: EventEmitterListener<T, K>, time = 250) {
		let key = -1
		return this.on(event, (...arg: any) => {
			let current = ++key
			setTimeout(() => {
				if (current != key) return
				listener(...arg)
			}, time)
		})
	}
	public once<K extends keyof (Record<string, any> & T) | EventClass>(event: K, listener: EventEmitterListener<T, K>) {
		//@ts-ignore
		if (event.typeClass) event = event.class
		this.initEventType(event)
		this.onceList[event].push(listener)
		this.hookOnce(<any>event, listener)
		this.setPipeListener(<any>event, listener)
		return this
	}
	public onceTimeout<K extends keyof (Record<string, any> & T) | EventClass>(event: K, listener: EventEmitterListener<T, K>, time = 250) {
		let key = -1
		return this.once(event, (...arg: any) => {
			let current = ++key
			setTimeout(() => {
				if (current != key) return
				listener(...arg)
			}, time)
		})
	}
	public onMounted<K extends keyof (Record<string, any> & T) | EventClass>(event: K, listener: EventEmitterListener<T, K>, unMounted: any) {
		//@ts-ignore
		this.on(event, listener)
		unMounted(() => {
			this.off(event, listener)
		})
		return this
	}
	public onceMounted<K extends keyof (Record<string, any> & T) | EventClass>(event: K, listener: EventEmitterListener<T, K>, unMounted: any) {
		//@ts-ignore
		this.once(event, listener)
		unMounted(() => {
			this.off(event, listener)
		})
		return this
	}
	public async emit<K extends keyof (Record<string, any> & T) | EventClass>(event: K, ...arg: EventEmitterListenerArg<T, K>) {
		return new Promise(async resolve => {
			//@ts-ignore
			if (event.typeClass) event = event.class
			await this.emitBefore(<any>event, ...<any>arg)
			let res: any
			const onList = this.onList[event]
			const onceList = this.onceList[event]
			if (onceList) {
				for (let i = onceList?.length; --i >= 0;) {
					//if (arg[0] == 'debug') console.log([onceList[i]])
					try {
						if (!onceList[i]) continue
						// @ts-ignore
						res = await onceList[i](...arg)
						if (res == false || res == 'break') break
						if (res == 'breakFlow') return
					} catch (e) {
						console.log(e)
						out.error(e)
					}
				}
				if (this.onceList[event]?.length) {
					for (let iter = this.onList[event], i = -1, l = iter.length; ++i < l;) {
						this.hookOffOnce(<any>event, iter[i])
					}
					this.onceList[event] = <any>[]
				}
			}
			if (onList) {
				for (let i = onList?.length; --i >= 0;) {
					try {
						if (!onList[i]) continue
						// @ts-ignore
						res = await onList[i](...arg)
						if (res == false || res == 'break') break
						if (res == 'breakFlow') return
					} catch (e) {
						console.log(e, i, onList[i], onList)
						out.error(e)
					}
				}
			}
			await this.emitAfter(<any>event, ...<any>arg)
			resolve(res)
		})
	}
	public async emitAsync<K extends keyof (Record<string, any> & T) | EventClass>(event: K, ...arg: EventEmitterListenerArg<T, K>) {
		this.timer.callAsync(() => {
			return this.emit(event, ...arg)
		})
	}
	//custom override emit , before Emit
	public async emitBefore(event: string, ...arg: any[]) {
	}
	public async emitAfter(event: string, ...arg: any[]) {
	}
	public off<K extends keyof (Record<string, any> & T) | EventClass>(event: K, listener: any) {
		//@ts-ignore
		if (event.typeClass) event = event.class
		this.offOn(event, listener)
		this.offOnce(event, listener)
		this.delPipeListener(<any>event, listener)
		return this
	}
	public clear() {
		const key: Record<string, any> = {}
		for (let i in this.onList) {
			key[i] = true
		}
		for (let i in this.onceList) {
			key[i] = true
		}
		for (let i in key) {
			this.offAll(i)
		}
	}
	public offAll<K extends keyof (Record<string, any> & T) | EventClass>(event: K) {
		//@ts-ignore
		if (event.typeClass) event = event.class
		for (let iter = this.onList[event], i = -1, l = iter?.length; ++i < l;) {
			this.delPipeListener(<any>event, iter[i])
			this.hookOffOn(<any>event, iter[i])
		}
		for (let iter = this.onceList[event], i = -1, l = iter?.length; ++i < l;) {
			this.delPipeListener(<any>event, iter[i])
			this.hookOffOnce(<any>event, iter[i])
		}
		delete this.onList[event]
		delete this.onceList[event]
		return this
	}
	//pipe
	public setPipeListener(key: string, listener: any) {
		for (let i in this.pipeList) {
			this.pipeList[i]._set(key, listener)
		}
	}
	public delPipeListener(key: string, listener: any) {
		for (let i in this.pipeList) {
			this.pipeList[i]._del(key, listener)
		}
	}
	public delPipeListenerAll(key: string) {
		for (let i in this.pipeList) {
			this.pipeList[i]._delAll(key)
		}
	}
	public getPipe(key: string | EventPipe,) {
		const keyName = typeof key === 'object' ? key.id : key
		return this.pipeList[keyName]
	}
	//pipe emitter, use set | del hook,map event to target
	public pipe(key: string | EventPipe, loadTime = 50) {
		const keyName = typeof key === 'object' ? key.id : key
		const old = this.pipeList[keyName]
		if (old) {
			old.destroy()
		}
		//console.log(old)
		const rep = typeof key === 'object' ? key : new EventPipe(keyName)
		this.pipeList[keyName] = rep
		rep.parent = this
		if (loadTime > 0) {
			setTimeout(() => {
				//console.log('link')
				rep.link()
			}, loadTime)
		}
		else {
			rep.link()
		}
		return key
	}
	public pipeMounted(key: string | EventPipe, un: any, loadTime = 50) {
		const p = this.pipe(key, loadTime)
		un(() => this.delPipe(p))
		return p
	}
	public delPipeAll() {
		for (let i in this.pipeList) {
			this.delPipe(this.pipeList[i])
		}
		return this
	}
	public delPipe(key: string | EventPipe) {
		if (typeof key === 'object') key = key.id
		const u = this.pipeList[key]
		if (u !== undefined) {
			u.destroy()
			delete this.pipeList[key]
		}
		return this
	}
	//util
	public onMountedCustom(custom: any, event: any, listener: any, un: any) {
		custom.addEventListener(event, listener)
		un(() => {
			custom.removeEventListener(event, listener)
		})
		return this
	}
	public onceWindow<K extends keyof WindowEventMap>(event: K, listener: (ev: WindowEventMap[K]) => any) {
		const f = (arg: any) => {
			window.removeEventListener(event, f)
			listener(arg)
		}
		window.addEventListener(event, f)
		return this
	}
	public onMountedWindow<K extends keyof WindowEventMap>(event: K, listener: (this: Window, ev: WindowEventMap[K]) => any, un: any) {
		return this.onMountedCustom(window, event, listener, un)
	}
}
//Bind VNode Event,  while it mounted runtime , limit , a namespace can only bind one element , if band repeat , will unbind before new bind
export class EventEmitterElementVNode<T extends Record<string, any>> extends EventEmitter<WindowEventMap & T & {
	mounted: [namespace: string, el: HTMLElement]
}> {
	public exited = false
	public exitedBus: any
	public time = 50
	public timerTask: Record<string, Timer> = {}
	clearTask(namespace: any) {
		if (this.timerTask[namespace]) {
			this.timerTask[namespace].clear()
			delete this.timerTask[namespace]
		}
	}
	bind(namespace: any, vnode: any) {
		if (!vnode) return
		this.clearTask(namespace)
		const timer = new Timer()
		this.timerTask[namespace] = timer
		timer.onTimer = () => {
			if (this.exited) return this.clearTask(namespace)
			if (!vnode.el) return
			const el: HTMLElement = vnode.el
			//@ts-ignore
			this.emit('mounted',namespace,el)
			this.pipeMounted(new EventPipeElement(el, namespace), this.exitedBus)
			return this.clearTask(namespace)
		}
		this.timerTask[namespace] = timer
		timer.timer(50, -1)
	}
	unbind(namespace: any) {
		this.delPipe(namespace)
	}
	constructor(unmounted: any) {
		super();
		unmounted(() => {
			this.exited = true
			this.delPipeAll()
		})
		this.exitedBus = EventEmitter.mountedBus(unmounted)
	}
}
//event bus html
export class EventEmitterElement<T extends Record<string, any> = {}> extends EventEmitter<WindowEventMap & T> {
	constructor(el?: EventTarget, mounted?: any) {
		super();
		if (el) {
			if (mounted) {
				this.pipeMounted(new EventPipeElement(el), mounted)
			}
			else {
				this.pipe(new EventPipeElement(el))
			}
		}
	}
}
//event provider type
export class EventClass<T extends any = any> {
	protected typeClass = true
	props: T
	class = ''
	Provide() {
	}
	constructor(name: string = '') {
		this.class = name
		if (!this.class) this.class = this.constructor.name
	}
}
