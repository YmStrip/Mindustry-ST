import {Register} from "@/entity/Register.ts";
import {Timer} from "@/entity/Timer.ts";
import {Theme} from "@/module/style/entity/Theme.ts";
import {style} from "fyurry";

export class ThemeItem extends Register {
	public icon = 'ic:outline-invert-colors'
	public group: string = 'basic'
	public rank: number = 0;
	public label = ''
	Label(v: string) {
		this.label = v
		return this
	}
	Icon(v: string) {
		this.icon = v
		this.theme.diffGroupAsync()
		return this
	}
	Rank(v: number) {
		this.rank = v
		this.theme.diffGroupAsync()
		return this
	}
	Group(v: string) {
		this.group = v
		this.theme.diffGroupAsync()
		return this
	}
	type: 'string' | 'size' | 'color' | 'number' = 'string'
	data: any
	//extend by else theme , if circularly return undefined , override data
	extend: string
	//override extend
	extendOver?: boolean
	//custom implement , when extend , do something , if extend undefined , prop ''
	extendModify(data: any) {
		return data
	}
	setData(d: any) {
		this.data = d
		this.setType()
		this.diffAsync('set')
		return this
	}
	ExtendOver(v = true) {
		if (!this.extend) {
			this.extendOver = false
			return
		}
		this.extendOver = v
		return this
	}
	Extend(key: string | ThemeItem, modify: (data: any) => any = (d) => d) {
		if (typeof key === "object") key = key.id
		this.extend = key
		this.extendModify = modify
		return this
	}
	diffTimer = new Timer()
	diff(mode: 'set' | 'del', cir: any = {}) {
		this.theme.Update()
		const extend = this.theme.extendTree
		if (mode == 'set') {
			if (!extend[this.id]) extend[this.id] = {}
			if (this.extendOver) return this.diffNext('set', cir);
			if (!this.extend) return this.diffNext('set', cir);
			if (!this.theme.item[this.extend]) {
				this.data = this.extendModify('')
				this.setType()
				return this.diffNext('set', cir);
			}
			if (!extend[this.extend]) this.theme.item[this.extend].diff('set', cir)
			extend[this.extend][this.id] = this
			this.data = this.extendModify(this.theme.item[this.extend].data)
			this.setType()
			return this.diffNext('set', cir);
		} else {
			this.data = ''
			this.setType()
			if (extend[this.extend]?.[this.id]) delete extend[this.extend][this.id]
			this.diffNext('del', cir);
		}
	}
	protected diffNext(mode: 'set' | 'del', cir: boolean) {
		const extend = this.theme.extendTree[this.id]
		if (mode == 'set') {
			for (let i in extend) {
				if (cir[i]) continue
				cir[i] = true
				if (this.theme.item[i]) this.theme.item[i].diff('set', cir)
				else delete extend[i]
			}
		} else {
			for (let i in extend) {
				if (cir[i]) continue
				cir[i] = true
				if (this.theme.item[i]) this.theme.item[i].diff('del', cir)
				else delete extend[i]
			}
			delete this.theme.extendTree[this.id]
		}
	}
	diffAsync(mode: 'set' | 'del', cir: any = {}) {
		this.diffTimer.callAsync(() => this.diff(mode, cir))
	}
	//get data 's type
	setType(data = this.data) {
		this.type = this.getType(data)
		return this
	}
	getType(data = '') {
		const v = data + ''
		if (v.startsWith('#') || v.startsWith('rgb') || v.startsWith('hs')) {
			return 'color'
		} else if (!(/[,|_()\s]/).test(v) && /((px)|(%)|(em))/g.test(v)) {
			return 'size'
		} else if (typeof v === 'number' && !isNaN(Number(v)) && !Number.isFinite(Number(v))) {
			return 'number'
		} else {
			return 'string'
		}
	}
	constructor(public theme: Theme, public id: string) {
		super();
		this.setIgnoreKey([
			'id', 'theme', 'diffTimer', 'extend',
			'rank', 'group', 'label', 'icon'
		])
	}
}