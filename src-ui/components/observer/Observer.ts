export class Observer {
	static focus: Record<string, Observer> = {}
	focus?: any
	hover = false
	active = false
	el: HTMLElement
	mouse: MouseEvent
	onHover() {
	}
	onHoverEnd() {

	}
	onActive() {
	}
	onActiveEnd() {
	}
	onFocus() {
	}
	onBlur() {
	}
	onMounted() {
	}
	onResize() {

	}
	onUnmounted() {
	}
	onMove() {

	}
	onMousedown(e:MouseEvent) {

	}
	onMouseup(e:MouseEvent) {

	}
	onClick(e:MouseEvent) {

	}
	resize() {
		this.onResize()
	}
	triggerFocus() {
		if (!this.hover) return
		const last = Observer.focus[this.focus]
		if (last == this) return;
		if (last) last.triggerBlur()
		Observer.focus[this.focus] = this
		this.onFocus()
	}
	triggerBlur() {
		if (this.hover) return
		const last = Observer.focus[this.focus]
		if (last != this) return;
		this.onBlur()
		delete Observer.focus[this.focus]
	}
}
