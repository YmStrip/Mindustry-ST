import {ArrayList, ArrayListItem} from "@/entity/ArrayList.ts";
import {DiffSortItem} from "@/entity/DiffSort.ts";
import {KeyItem} from "@/module/key/entity/KeyItem.ts";
import {KeySearch} from "@/module/key/entity/KeySearch.ts";
import {reactive} from "vue";

export class KeyContainer {
	parent: KeyContainer
	child: Record<string, KeyContainer> = {}
	split = '.'
	str2ary(str: string) {
		return str.split(this.split)
	}
	ary2str(key: string[]) {
		return key.join(this.split)
	}
	hasChild() {
		for (let i in this.child) {
			return true
		}
	}
	hasData() {
		for (let i in this.data) {
			return true
		}
	}
	get(key: KeyItem, add = false): KeyContainer {
		return this.getStr(key.id, add)
	}
	getStr(str: string, add = false): KeyContainer {
		const parent = this.str2ary(str)
		const name = parent.pop()
		return this.getAry(parent, add)
	}
	getAry(parent: string[], add = false): KeyContainer {
		let p = this
		for (let i = -1, l = parent.length; ++i < l;) {
			const id = parent[i]
			let c = p.child[id]
			if (!c) {
				if (add) {
					c = new KeyContainer(p.id ? p.id + p.split + id : id)
					c.parent = this
					p.child[id] = c
				}
				else {
					return undefined
				}
			}
			// @ts-ignore
			p = c
		}
		return p
	}
	set(key: KeyItem) {
		const p = this.get(key, true)
		key.UpdateSearch()
		p.data[key.id] = key
		p.dataDiff.diffAsync()
		return this
	}
	delStr(key: string) {
		const parent = this.str2ary(key)
		const name = parent.pop()
		let p = this.getAry(parent)
		delete p.data[name]
		p.dataDiff.diffAsync()
		while (true) {
			if (p.hasChild() || p.hasData()) break
			if (p.parent) {
				delete p.parent.child[p.id]
				p = p.parent
			}
			else {
				break
			}
		}
		return this
	}
	del(key: KeyItem) {
		return this.delStr(key.id)
	}
	search(key: string = ''): KeySearch {
		key = key.toLowerCase()
		const search = new KeySearch(this)
		search.diff(key)
		return search
	}
	searchCurrent(key: string): KeyItem[] {
		key = key.toLowerCase()
		const res: KeyItem[] = []
		for (let i in this.child) {
			const item = this.child[i]
			if (i.toLowerCase().includes(key)) {
				const u = new KeyItem(item.id)
				u.obj = true
				res.push(u)
			}
		}
		for (let i in this.dataSort.data) {
			const item = this.dataSort.data[i]
			if (item.data.search.includes(key)) {
				res.push(item.data)
			}
		}
		return res
	}
	data: Record<string, KeyItem> = {}
	dataSort: ArrayList<ArrayListItem<KeyItem>> = <any>reactive(new ArrayList())
	dataDiff = DiffSortItem.pipeCreate(
		that => that.dataSort,
		that => that.data,
		this
	)
	constructor(public id?: string) {
	}
}