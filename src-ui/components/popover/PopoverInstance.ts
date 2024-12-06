import {EventEmitter} from "@/entity/EventEmitter.ts";
import {PopoverProps} from "@/components/popover/PopoverProps.ts";

export class PopoverInstance {
	deep = 0
	event = new EventEmitter<{
		'update:show': boolean
		'update:trigger': string
	}>()
	child: PopoverInstance[] = []
	childShow = false
	show = false
	readonly placement: PopoverProps['placement']

	open(v = false) {
		if (this.show) return
		if (this.parent) {
			this.parent.childShow = true
		}
		this.show = true
		if (v) this.event.emit('update:show', true)
	}
	close(v = false) {
		if (!this.show) return
		this.show = false
		if (this.parent) this.parent.checkChildShow()
		if (v) this.event.emit('update:show', false)
		this.child.forEach(d => {
			d.close(true)
		})
	}

	destroy() {
		if (this.parent) this.parent.delChild(this)
	}
	checkChildShow() {
		this.childShow = false
		for (let i of this.child) {
			if (i.show || i.childShow) {
				this.childShow = true
				break
			}
		}
	}
	delChild(ch: PopoverInstance) {
		const index = this.child.indexOf(ch)
		if (index > 0) {
			this.child.slice(index, 1)
		}
	}

	constructor(public parent?: PopoverInstance) {
		if (parent) {
			this.deep = parent.deep + 1
		}
	}
}