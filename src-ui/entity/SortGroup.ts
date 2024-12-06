import {ArrayList, ArrayListItem} from "@/entity/ArrayList.ts";
import {Diff} from "@/entity/Diff.ts";
import {Register} from "@/entity/Register.ts";

export abstract class SortGroup<T extends Register = Register> extends Register {
	abstract Provide(): this
	//
	icon = 'ic:baseline-data-array'
	rank = 0
	data: Record<string, T> = {}
	sort = new ArrayList<ArrayListItem<T>>()
	label = ''
	Icon(v: string) {
		this.icon = v
		return this
	}
	Rank(v: number) {
		this.rank = v
		return this
	}
	Label(v: string) {
		this.label = v
		return this
	}
	diffSort() {
		new Diff()
			.pipeDifferKey(this.data)
			.pipeDiffKey(this.sort.data, d => d.key)
			.onSet(key => {
				const item = this.data[key]
				const arrayItem = new ArrayListItem(item).Rank(item.rank)
				this.sort.set(key, arrayItem)
			})
			.onPass(key => {
				const item = this.sort.get(key)
				item?.Rank(item?.data?.rank)
			})
			.onDel(key => this.sort.del(key))
			.invoke()
		//O(n log n)
		this.sort.sort()
	}
	constructor(public id: string) {
		super();
		this.setIgnoreKey(['sort', 'data'])
	}
}