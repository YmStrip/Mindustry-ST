import {ArrayList, ArrayListItem} from "@/entity/ArrayList.ts";
import {DiffSort} from "@/entity/DiffSort.ts";
import {Register} from "@/entity/Register.ts";
import {SortGroup} from "@/entity/SortGroup.ts";
import {reactive} from "vue";
import {out} from "fyurry";

export class DiffSortGroup<G extends SortGroup = SortGroup, I extends Register = Register> extends Register {
	item: Record<string, I> = reactive({})
	sort: ArrayList<ArrayListItem<G>> = <any>reactive(new ArrayList())
	diff = DiffSort.pipeCreate(
		that => that.sort,
		that => that.group,
		that => that.item,
		this
	)
	//this group is empty and not support configure
	setBasicGroup(name: string, icon?: string): G {
		const that = this
		const data = new class extends SortGroup {
			Provide() {
				that.setGroup(<any>this)
				return this
			}
			serialize(): Record<string, any> {
				return {}
			}
			configure(data: any): any {
				return this
			}
			constructor() {
				super(name);
				if (icon) this.icon = icon
			}
		}
		data.Provide()
		return <any>data
	}
	setItem(item: I) {
		this.item[item.id] = item
		this.diff.diffAsync()
		return item
	}
	delItem(item: I) {
		delete this.item[item.id]
		this.diff.diffAsync()
		return
	}
	group: Record<string, G> = reactive({})
	setGroup(item: G) {
		this.group[item.id] = item
		this.diff.diffAsync()
		return item
	}
	delGroup(item: G) {
		delete this.group[item.id]
		this.diff.diffAsync()
		return
	}
	clearGroup() {
		this.clearObject(this.group)
		this.diff.diffAsync()
	}
	clearItem() {
		this.clearObject(this.item)
		this.diff.diffAsync()
	}
	//if exist is configure & data , set
	configureExist(data: any) {
		for (let i in data.item) {
			const raw = data.item[i]
			const item: any = this.item[i]
			if (!item.configure || !raw) continue
			try {
				item.configure(raw)
			} catch (e) {
				out.error('configure error:' + e)
				console.log(i, item, raw)
			}
		}
		for (let i in data.group) {
			const raw = data.group[i]
			const item: any = this.group[i]
			if (!item.configure || !raw) continue
			try {
				item.configure(raw)
			} catch (e) {
				out.error('configure error:' + e)
				console.log(i, item, raw)
			}
		}
		return this
	}
	configure(data: any): any {
		return this
	}
	serialize(): Record<string, any> {
		return {};
	}
	serializeExist() {
		const data: any = {
			item: {},
			group: {}
		}
		for (let i in this.item) {
			const item: any = this.item[i]
			if (!item.serialize) continue
			data.item[i] = item.serialize()
		}
		for (let i in this.group) {
			const item = this.group[i]
			if (!item.serialize) continue
			data.group[i] = item.serialize()
		}
		return data
	}
}