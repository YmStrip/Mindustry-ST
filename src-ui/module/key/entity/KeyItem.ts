import {KeyContainer} from "@/module/key/entity/KeyContainer.ts";

export class KeyItem {
	icon: string = 'mdi:drag-variant';
	label: string = ''
	color: string = '#fff'
	rank: number = 0;
	group: string;
	doc: string = ''
	path: string = ''
	suffix: string = ''
	search: string = ''
	obj?: boolean
	Icon(icon: string) {
		this.icon = icon
		return this
	}
	Label(icon: string) {
		this.label = icon
		return this
	}
	Rank(icon: number) {
		this.rank = icon
		return this
	}
	Provide(container: KeyContainer) {
		container.set(this)
		return this
	}
	splitLabel() {
		this.label = this.id.split('.').pop()
	}
	UpdateSearch() {
		this.search = this.id.toLowerCase() + this.label.toLowerCase()
		return this
	}
	constructor(public id: string) {
	}
}