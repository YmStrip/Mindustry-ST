import {Observer} from "@/components/observer/Observer.ts";
import {Component} from "@/entity";

export class ObserverComponent extends Observer {

	onBlur() {
		this.component.onBlur()
	}
	onFocus() {
		this.component.onFocus()
	}
	onMounted() {
		this.setRect()
		this.component.onMounted()
		this.component.onResize()
	}
	onResize() {
		this.setRect()
		this.component.onResize()
	}
	onUnmounted() {
		this.component.onUnmounted()
	}
	onMove() {
		this.component.recentMouse = this.mouse
	}
	setRect() {
		const b = <any>this.el?.getBoundingClientRect()
		this.component.rect = {
			x: b?.left || 0,
			y: b?.top || 0,
			h: b?.height || 0,
			w: b?.width || 0
		}
	}
	onMousedown(e:MouseEvent) {
		this.component.onMousedown(e)
	}
	onMouseup(e:MouseEvent) {
		this.component.onMouseup(e)
	}
	onClick(e:MouseEvent) {
		this.component.onClick(e)
	}
	constructor(public component: Component) {
		super();
		this.focus = component.focusType
	}
}