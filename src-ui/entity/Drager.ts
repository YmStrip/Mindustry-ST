import {keymap} from "fyurry";

export class Drager {
	upEvent: any
	moveEvent: any
	clearEvent() {
		if (this.upEvent) {
			window.removeEventListener('pointerup', this.upEvent)
			window.removeEventListener('mouseup', this.upEvent)
		}
		if (this.moveEvent) window.removeEventListener('mousemove', this.moveEvent)
	}
	sx = 0
	sy = 0
	downTime = 0
	drag = false
	filterType?: ('lockX' | 'lockY') | string = ''
	filter(dx: number, dy: number) {
		return [dx, dy]
	}
	filterClear() {
		this.filterType = ''
		this.filter = (dx, dy) => [dx, dy]
		return this
	}
	filterLockY() {
		this.filterType = 'lockY'
		this.filter = (dx, dy) => [dx, 0]
		return this
	}
	filterLockX() {
		this.filterType = 'lockX'
		this.filter = (dx, dy) => [0, dy]
		return this
	}
	bindKeymapXY(bus: any) {
		keymap.KeyX.event.onMounted('trigger', () => {
			if (this.filterType=='lockY') {
				this.filterClear()
			} else {
				this.filterLockY()
			}
		}, bus)
		keymap.KeyY.event.onMounted('trigger', () => {
			if (this.filterType=='lockX') {
				this.filterClear()
			} else {
				this.filterLockX()
			}
		}, bus)
	}
	clear() {
		this.clearEvent()
		this.up(this.mouse)
		this.drag = false
		return this
	}
	mouse: MouseEvent
	protected triggerMove(e: MouseEvent = this.mouse) {
		const dx = e.clientX - this.sx
		const dy = e.clientY - this.sy
		const transform = this.filter(dx, dy)
		this.move(transform[0], transform[1], e)
	}
	down(e: MouseEvent) {
		this.clearEvent()
		this.mouse = e
		this.sx = e.clientX
		this.sy = e.clientY
		this.drag = true
		this.downTime = Date.now()
		this.upEvent = (e: MouseEvent) => {
			this.clearEvent()
			this.up(e)
			this.drag = false
		}
		this.moveEvent = (e: MouseEvent) => {
			this.mouse = e
			if (!this.drag) return
			this.triggerMove(e)
		}
		window.addEventListener('pointerup', this.upEvent)
		window.addEventListener('mouseup', this.upEvent)
		window.addEventListener('mousemove', this.moveEvent)
		this.move(0, 0, e)
	}
	move(x: number, y: number, e: MouseEvent): void {
	}
	up(e: MouseEvent): void {
	}
}