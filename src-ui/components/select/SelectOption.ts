export class SelectOption {
	icon = 'mdi:cube-send'
	color?: any
	Value(v: any) {
		this.value = v
		return this
	}
	Label(v:any) {
		this.label = v
		return this
	}
	Color(v:any) {
		this.color = v
		return this
	}
	Icon(v:any) {
		this.icon = v
		return this
	}
	constructor(public value: any, public label?: any) {
	}
}