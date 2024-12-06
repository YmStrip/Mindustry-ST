import {Register} from "@/entity";
import {EventEmitter} from "@/entity/EventEmitter.ts";
import {keymap} from "fyurry";
import {Keyname} from "@/module/keymap/entity/Keyname.ts";
import {KeyType} from "@/module/keymap/entity/KeyType.ts";

export class Keymap extends Register {
	static id = ''
	static shift = 'shift'
	static esc = 'escape'
	static ctrl = 'control'
	static enter = 'enter'
	static alt = 'alt'
	static tab = 'tab'
	static arrowUp = 'arrowUp'
	static arrowDown = 'arrowDown'
	static arrowLeft = 'arrowLeft'
	static arrowRight = 'arrowRight'
	static backspace = 'backspace'
	static wheelUp = 'wheelUp'
	static wheelDown = 'wheelDown'
	static downLeft = 'downLeft'
	static downCenter = 'downCenter'
	static downRight = 'downRight'
	static label = ''
	static icon = 'mdi:keyboard-variant'
	static rank = 0
	icon: any = null
	//event z index
	rank = 1
	//can be break
	event = new EventEmitter<{
		//trigger
		'trigger': [e: MouseEvent]
		//before trigger start
		'trigger:start': [e: MouseEvent]
		//after trigger end
		'trigger:end': [e: MouseEvent]
		//
		'key:down': [key: KeyboardEvent, e: MouseEvent]
		//
		'key:up': [key: KeyboardEvent, e: MouseEvent]
	}>()
	key: string[] = []
	active = false
	activeTime = Date.now()
	mode: 'in-out' | 'press' | 'in' | 'out' | 'none' = 'in-out'
	combMax = 1
	combMin = 1
	pressIter = 0
	Mode(mode: 'in-out' | 'press' | 'in' | 'out' | 'none') {
		this.mode = mode
		this.clear()
		return this
	}
	Press() {
		return this.Mode('press')
	}
	Down() {
		return this.Mode('in')
	}
	Up() {
		return this.Mode('out')
	}
	Click() {
		return this.Mode('in-out')
	}
	Double() {
		return this.Comb(1, 2)
	}
	Rank(v: number) {
		this.rank = v
		if (keymap.key[this.id]) {
			keymap.del(this)
			keymap.set(this)
		}
		this.clear()
		return this
	}
	Key(key: string | string[]) {
		this.setKey(key)
		this.clear()
		return this
	}
	Comb(min = 1, max = 1) {
		this.combMin = min
		this.combMax = max
		this.clear()
		return this
	}
	modeNone() {
		return this.mode == 'none'
	}
	//
	keyActive: Record<string, boolean> = {}
	keyComb: Record<string, number> = {}
	keyComTimer: Record<string, any> = {}
	keyDown(key: KeyboardEvent, e: MouseEvent) {
		if (this.modeNone()) return this;
		const id = key.key.toLocaleLowerCase()
		if (!this.key.includes(id)) return this;
		if (this.keyComb[id] == undefined) this.keyComb[id] = 0
		this.keyComb[id]++
		this.keyActive[id] = true
		this.event.emit('key:down', key, e)
		this.check(e)
		return this
	}
	keyUp(key: KeyboardEvent, e: MouseEvent) {
		if (this.modeNone()) return this;
		const id = key.key.toLocaleLowerCase()
		if (!this.key.includes(id)) return this
		if (!this.keyComTimer[id]) this.keyComTimer[id] = 0
		delete this.keyActive[id]
		const timer = ++this.keyComTimer[id]
		setTimeout(() => {
			if (this.keyComTimer[id] != timer) return this
			delete this.keyComb[id]
			delete this.keyComTimer[id]
		}, 250)
		this.event.emit('key:up', key, e)
		this.check(e)
		return this
	}
	check(e: MouseEvent) {
		const total = this.key.length
		let combMax = 0
		let combMin = 99
		let active = 0
		for (let i in this.keyComb) {
			const u = this.keyComb[i]
			if (combMax < u) combMax = u
			if (u < combMin) combMin = u
		}
		for (let i in this.keyActive) {
			active++
		}
		if (active <= 0) combMax = combMin = 0
		//
		if (!this.active) {
			if (total != active) return
			if (!(this.combMin <= combMin && this.combMax <= combMax)) return
			switch (this.mode) {
				case "in":
					this.triggerStart(e)
					this.trigger()
					return this.triggerEnd(e)
				case "press":
					this.triggerStart(e)
					this.trigger()
					this.active = true
					this.activeTime = Date.now()
					const iter = ++this.pressIter
					const bus = EventEmitter.bus()
					this.event.onMountedWindow('mousemove', () => {
						if (!this.active || iter != this.pressIter) return
						this.trigger()
						keymap.trigger()
					}, bus)
					this.event.once('trigger:end', () => {
						bus.emit()
						return
					})
					return
				case "in-out":
					this.active = true
					this.activeTime = Date.now()
					this.triggerStart(e)
					return
				case "out":
					break
			}
		}
		else {
			if (active == total) return
			switch (this.mode) {
				case "in":
					return
				case "in-out":
					this.active = false
					if (Date.now() - this.activeTime > 250 * Math.max(this.combMin, this.combMax)) {
						return this.triggerEnd(e)
					}
					else {
						this.trigger()
						return this.triggerEnd(e)
					}
				case "out":
					this.triggerStart(e)
					this.trigger()
					return this.triggerEnd(e)
				case "press":
					this.active = false
					this.triggerEnd(e)
					return
			}
		}
	}
	clear() {
		this.keyComb = {}
		this.keyComTimer = {}
		this.keyActive = {}
		this.active = false
		return this
	}
	//
	getEvent(key: KeyType = this) {
		if (key instanceof KeyboardEvent) return key
		return new Keyname(key).event()
	}
	up(key: KeyType) {
		return this.keyUp(this.getEvent(key), keymap.mouse)
	}
	down(key: KeyType) {
		return this.keyDown(this.getEvent(key), keymap.mouse)
	}
	switch(key: KeyType) {
		const e = this.getEvent(key)
		const k = e.key.toLocaleLowerCase()
		if (this.keyActive[k]) return this.keyUp(e, keymap.mouse)
		return this.keyDown(e, keymap.mouse)
	}
	pipeSwitchStart(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger:start', () => {
			key.switch(k)
		}, bus)
		return this
	}
	pipeSwitchEnd(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger:end', () => {
			key.switch(k)
		}, bus)
		return this
	}
	pipeSwitch(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger', () => {
			key.switch(k)
		}, bus)
		return this
	}
	pipeUp(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger', () => {
			key.up(k)
		}, bus)
		return this
	}
	pipeUpStart(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger:start', () => {
			key.up(k)
		}, bus)
		return this
	}
	pipeUpEnd(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger:end', () => {
			key.up(k)
		}, bus)
		return this
	}
	pipeDown(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger', () => {
			key.down(k)
		}, bus)
		return this
	}
	pipeDownStart(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger:start', () => {
			key.down(k)
		}, bus)
		return this
	}
	pipeDownEnd(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger:end', () => {
			key.down(k)
		}, bus)
		return this
	}
	pipeClear(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger', () => {
			key.up(k)
			key.clear()
		}, bus)
		return this
	}
	pipeClearStart(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger:start', () => {
			key.up(k)
			key.clear()
		}, bus)
		return this
	}
	pipeClearEnd(key: Keymap, k: KeyType = this, bus = EventEmitter.bus()) {
		this.event.onMounted('trigger:end', () => {
			key.up(k)
			key.clear()
		}, bus)
		return this
	}
	//
	bindClear(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeClear(this, k, bus)
		return this
	}
	bindClearStart(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeClearStart(this, k, bus)
		return this
	}
	bindClearEnd(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeClearEnd(this, k, bus)
		return this
	}
	bindUp(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeUp(this, k, bus)
		return this
	}
	bindUpStart(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeUpStart(this, k, bus)
		return this
	}
	bindUpEnd(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeUpEnd(this, k, bus)
		return this
	}
	bindDown(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeDown(this, k, bus)
		return this
	}
	bindDownStart(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeDownStart(this, k, bus)
		return this
	}
	bindDownEnd(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeDownEnd(this, k, bus)
		return this
	}
	bindSwitch(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeSwitch(this, k, bus)
		return this
	}
	bindSwitchStart(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeSwitchStart(this, k, bus)
		return this
	}
	bindSwitchEnd(key: Keymap, k: KeyType = key, bus = EventEmitter.bus()) {
		key.pipeSwitchEnd(this, k, bus)
		return this
	}
	//
	triggerStart(e: MouseEvent) {
		this.event.emit('trigger:start', e)
	}
	trigger() {
		keymap.triggerList.push(this)
	}
	triggerEnd(e: MouseEvent) {
		this.event.emit('trigger:end', e)
	}
	//
	putKey(key: KeyType) {
		if (key instanceof Keymap) {
			key = key.id.toLocaleLowerCase()
		}
		else if (key instanceof Keyname) {
			key = key.key
		}
		else if (key instanceof KeyboardEvent) {
			key = key.key.toLocaleLowerCase()
		}
		key = key.toLocaleLowerCase()
		if (!this.key.includes(key)) this.key.push(key)
	}
	setKey(key: KeyType | KeyType[]) {
		let o = keymap[this.id]
		if (o) this.off()
		if (!Array.isArray(key)) key = <any[]>[key]
		this.key = []
		key.forEach(d => this.putKey(<any>d))
		if (o) this.on()
		return this
	}
	//
	on() {
		keymap.set(this)
		return this
	}
	onMounted(bus: any) {
		keymap.set(this)
		bus(() => {
			keymap.del(this)
		})
		return this
	}
	off() {
		keymap.del(this)
		return this
	}
	//provide template and listen
	Provide() {
		this.ProvideTemplate()
		this.on()
		return this
	}
	Debug() {
		this.event.on('trigger:start', (e) => {
			console.log(`#${this.id} start (${e.clientX},${e.clientY})`)
		})
		this.event.on('trigger', () => {
			console.log(`#${this.id} trigger`)
		})
		this.event.on('trigger:end', () => {
			console.log(`#${this.id} end`)
		})
		return this
	}
	//provide template not auto listen
	ProvideTemplate() {
		keymap.keyList[this.id] = this
		//@ts-ignore
		const k = this.constructor.key()
		k.id = this.id
		//@ts-ignore
		keymap.keyListKey.set(k)
		return this
	}
	constructor(public id: string, k?: KeyType | KeyType[]) {
		super();
		this.assignByConstructor()
		this.setIgnoreKey([
			'id',
			'event',
		])
		this.setIgnoreKey([
			'pressTimer',
			'active',
			'activeTime',
			'keyActive',
			'keyComb',
			'keyCombTimer',
			'timer'
		])
		if (k) {
			this.setKey(k)
		}
	}
}