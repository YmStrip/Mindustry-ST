import {AppModule, Timer, config} from "fyurry";
import {Keymap} from "@/module/keymap/entity/Keymap.ts";
import {KeyContainer} from "@/module/key/entity/KeyContainer.ts";
import {EventEmitterElement, EventPipeElement} from "@/entity/EventEmitter.ts";
import {Keyname} from "@/module/keymap/entity/Keyname.ts";


export class KeymapModule extends AppModule {
	mouse: MouseEvent
	config = config.get('keymap')
	configData = this.config.data
	keyList: Record<string, Keymap> = {}
	keyListKey = new KeyContainer()
	//override key
	key: Record<string, Keymap> = {}
	keyMap: Record<string, Keymap[]> = {}
	keyMapSortTask: Record<string, boolean> = {}
	keyMapSortTimer = new Timer
	keyDown: Record<string, boolean> = {}
	keyMapSortAsync() {
		this.keyMapSortTimer.callInter(() => {
			for (let i in this.keyMapSortTask) {
				if (!this.keyMapSortTask[i]) continue
				if (!this.keyMap[i]) continue
				this.keyMap[i].sort((a, b) => {
					return b.rank - a.rank
				})
			}
			this.keyMapSortTask = {}
		})
	}
	//trigger task list
	triggerList: Keymap[] = []
	async trigger() {
		const cache = this.triggerList
		this.triggerList = []
		for (let i = -1, l = cache.length; ++i < l;) {
			const key = cache[i]
			await key.event.emit('trigger', this.mouse)
		}
	}
	//
	set(key: Keymap) {
		key.key.forEach(d => {
			const id = d.toLocaleLowerCase()
			if (!this.keyMap[id]) this.keyMap[id] = []
			const map = this.keyMap[id]
			if (!map.includes(key)) map.push(key)
			this.keyMapSortTask[id] = true
			this.keyMapSortAsync()
		})
	}
	del(key: Keymap) {
		key.key.forEach(d => {
			const id = d.toLocaleLowerCase()
			if (!this.keyMap[id]) return
			const map = this.keyMap[id]
			const index = map.indexOf(key)
			if (index < 0) return;
			map.splice(index, 1)
			if (!map.length) return delete this.keyMap[id]
			this.keyMapSortTask[id] = true
			this.keyMapSortAsync()
		})
	}
	keydown(e: KeyboardEvent) {
		const id = e.key.toLocaleLowerCase()
		if (this.keyDown[id]) return
		this.keyDown[id] = true
		this.keyMap[id]?.forEach(d => {
			d.keyDown(e, this.mouse)
		})
		this.trigger()
	}
	keyup(e: KeyboardEvent) {
		const id = e.key.toLocaleLowerCase()
		if (!this.keyDown[id]) return
		delete this.keyDown[id]
		this.keyMap[e.key.toLocaleLowerCase()]?.forEach(d => {
			d.keyUp(e, this.mouse)
		})
		this.trigger()
	}
	wheel(e: WheelEvent) {
		let event: KeyboardEvent
		if (e.deltaY < 0) {
			event = new KeyboardEvent('keydown', {
				key: Keymap.wheelUp
			})

		}
		else {
			event = new KeyboardEvent('keydown', {
				key: Keymap.wheelDown
			})
		}
		this.keydown(event)
		this.keyup(event)
	}
	mouseevent(e: MouseEvent) {
		let event: KeyboardEvent
		if (e.which == 2) {
			event = new KeyboardEvent('keydown', {
				key: Keymap.downCenter
			})
		}
		else if (e.button == 0) {
			event = new KeyboardEvent('keydown', {
				key: Keymap.downLeft
			})
		}
		else {
			event = new KeyboardEvent('keydown', {
				key: Keymap.downRight
			})
		}
		return event
	}
	mousedown(e: MouseEvent) {
		const event = this.mouseevent(e)
		this.keydown(event)
	}
	mouseup(e: MouseEvent) {
		const event = this.mouseevent(e)
		this.keyup(event)
	}
	res(key: Keymap | string) {
		const id = typeof key === 'string' ? key : key.id
		if (!this.key[id] || !this.keyList[id]) return
		this.del(this.key[id])
		this.key[id].configure(this.keyList[id].serialize())
		this.set(this.key[id])
	}
	keyDownName(key: Keyname | string) {
		const id = typeof key === 'string' ? key : key.key
		const event = new KeyboardEvent('keydown', {
			key: id
		})
		return this.keydown(event)
	}
	keyUpName(key: Keyname | string) {
		const id = typeof key === 'string' ? key : key.key
		const event = new KeyboardEvent('keydown', {
			key: id
		})
		return this.keyup(event)
	}
	//
	KeyShift: Keymap
	KeyAlt: Keymap
	KeyCtrl: Keymap
	//
	KeyX: Keymap
	KeyY: Keymap
	KeyZ: Keymap
	//
	KeyDownLeft: Keymap
	KeyDownRight: Keymap
	KeyDownCenter: Keymap
	//
	KeyEditMode: Keymap
	KeyEditUndo: Keymap
	KeyEditMove: Keymap
	KeyEditScale: Keymap
	KeyViewMove: Keymap
	KeyViewScaleUp: Keymap
	KeyViewScaleDown: Keymap
	//
	listener = new EventEmitterElement()
	listenerPipe = new EventPipeElement(window)
	on() {
		this.listener.pipe(this.listenerPipe)
	}
	off() {
		this.listener.delPipe(this.listenerPipe)
	}
	async init(): Promise<void> {
		this.listener.on('wheel', e => {
			this.wheel(e)
		})
		this.listener.on('keydown', e => {
			this.keydown(e)
		})
		this.listener.on('keyup', e => {
			this.keyup(e)
		})
		this.listener.on('mousedown', e => {
			this.mousedown(e)
		})
		this.listener.on('mouseup', e => {
			this.mouseup(e)
		})
		this.listener.on('mousemove', e => {
			this.mouse = e
		})
		//abc
		this.KeyShift = new Keymap('shift', Keymap.shift)
			.Click()
			.Provide()
		this.KeyAlt = new Keymap('alt', Keymap.alt)
			.Click()
			.Provide()
		this.KeyCtrl = new Keymap('ctrl', Keymap.ctrl)
			.Click()
			.Provide()
		//xyz
		this.KeyX = new Keymap('x', 'x')
			.Click()
			.Provide()
		this.KeyY = new Keymap('y', 'y')
			.Click()
			.Provide()
		this.KeyZ = new Keymap('z', 'z')
			.Click()
			.Provide()
		//
		this.KeyDownLeft = new Keymap('down.left', [Keymap.downLeft])
			.Click()
			.Provide()
		this.KeyDownRight = new Keymap('down.right', [Keymap.downRight])
			.Click()
			.Provide()
		this.KeyDownCenter = new Keymap('down.center', [Keymap.downCenter])
			.Click()
			.Provide()
		//
		this.KeyEditMode = new Keymap('edit.mode', [Keymap.tab])
			.Click()
			.Provide()
		this.KeyEditUndo = new Keymap('edit.undo', [Keymap.ctrl, 'z'])
			.Click()
			.Provide()
		this.KeyEditMove = new Keymap('edit.move', ['g'])
			.Click()
			.Provide()
		this.KeyEditScale = new Keymap('edit.scale', ['s'])
			.Click()
			.Provide()
		this.KeyViewMove = new Keymap('view.move', [Keymap.alt, Keymap.downLeft])
			.Press()
			.Provide()
		this.KeyViewScaleUp = new Keymap('view.scale.up', [Keymap.wheelUp])
			.Click()
			.Provide()
		this.KeyViewScaleDown = new Keymap('view.scale.down', [Keymap.wheelDown])
			.Click()
			.Provide()
		this.on()
	}
}