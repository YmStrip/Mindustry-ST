import {EventEmitter} from "@/entity/EventEmitter.ts";
import {Component, Register} from "@/entity/Register.ts";

import fyurry, {modal} from "fyurry";


export class Modal extends Component {
	static id = 'modal'
	static group = 'modal'
	static icon = 'mdi:warning-lights'
	static rank = 0
	static label = 'modal'
	static Provide() {
		modal.modal[this.id] = this
		modal.modalKey.set(this.key())
		return this
	}
	focusType = 'modal'
	component: any
	event = new EventEmitter<{
		//when close
		close: []
	}>()
	zIndex = 10
	w = 300
	h = 300
	x = window.innerWidth / 2 - 150
	y = window.innerHeight / 2 - 150
	resizeCenter(w: number, h: number) {
		this.w = w
		this.h = h
		this.x = window.innerWidth / 2 - w / 2
		this.y = window.innerHeight / 2 - h / 2
		return this
	}
	resizeCenterAuto(w: number) {
		return this.resizeCenter(w, w * window.innerHeight / window.innerWidth)
	}
	resizeCenterAutoK(v: number) {
		const w = window.innerWidth*v
		return this.resizeCenter(w, w * window.innerHeight / window.innerWidth)
	}
	//@ts-ignore
	title?: string = this.constructor.id
	icon: string = null
	close() {
		this.event.emit('close')
		modal.off(this)
		return this
	}
	open() {
		modal.open(this)
		return this
	}
	group: string = 'group'
	rank: number = 0
	init() {
	}
	//
	constructor(public id = fyurry.id.id()) {
		super();
		this.setIgnoreKey(['component'])
		this.init()
	}
}