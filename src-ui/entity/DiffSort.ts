import {ArrayList, ArrayListItem} from "@/entity/ArrayList.ts";
import {Diff} from "@/entity/Diff.ts";
import {SortGroup} from "@/entity/SortGroup.ts";
import {SortItem} from "@/entity/SortItem.ts";
import {Timer} from "@/entity/Timer.ts";

export abstract class DiffSortItem {
	abstract groupArray(): ArrayList<ArrayListItem<SortItem>>
	abstract group(): Record<string, SortItem>
	diffTimer = new Timer()
	diffAsync() {
		this.diffTimer.callAsync(() => this.diff())
	}
	diff() {
		const Group = this.group()
		const GroupArray = this.groupArray()
		new Diff()
			.pipeDifferKey(Group)
			.pipeDiffKey(GroupArray.data, d => d.key)
			.onDel(key => GroupArray.del(key))
			.onSet(key => {
				const group = Group[key]
				const item = new ArrayListItem(group).Rank(group.rank)
				GroupArray.set(key, item)
			})
			.onPass(key => {
				const item = GroupArray.get(key)
				item?.Rank(item.data?.rank)
			})
			.invoke()
		GroupArray.sort()
		this.pipeDiff()
	}
	pipeDiff() {
	}
	PipeDiff(call: () => any) {
		this.pipeDiff = call
		return this
	}
	static create<G extends SortItem>(getArray: () => ArrayList<ArrayListItem<G>>, getGroup: () => Record<string, G>) {
		return new class extends DiffSortItem {
			group() {
				return getGroup()
			}
			groupArray() {
				return getArray()
			}
		}
	}
	static pipe<T>(call: (that: T) => any, that: T) {
		return call(that)
	}
	static pipeCreate<T>(
		getArray: (that: T) => ArrayList<ArrayListItem<SortItem>>,
		getGroup: (that: T) => Record<string, SortItem>,
		that: T
	) {
		return this.create(
			() => getArray(that),
			() => getGroup(that),
		)
	}
}
export abstract class DiffSort {
	abstract groupArray(): ArrayList<ArrayListItem<SortGroup>>
	abstract group(): Record<string, SortGroup>
	abstract item(): Record<string, SortItem>
	static create<G extends SortGroup, T extends SortItem>(getArray: () => ArrayList<ArrayListItem<G>>, getGroup: () => Record<string, G>, getItem: () => Record<string, T>) {
		return new class extends DiffSort {
			group() {
				return getGroup()
			}
			groupArray() {
				return getArray()
			}
			item() {
				return getItem()
			}
		}
	}
	static pipe<T>(call: (that: T) => any, that: T) {
		return call(that)
	}
	static pipeCreate<T>(
		getArray: (that: T) => ArrayList<ArrayListItem<SortGroup>>,
		getGroup: (that: T) => Record<string, SortGroup>,
		getItem: (that: T) => Record<string, SortItem>,
		that: T
	) {
		return this.create(
			() => getArray(that),
			() => getGroup(that),
			() => getItem(that)
		)
	}
	diffTimer = new Timer()
	createGroupIcon?: any
	pipeDiff() {
	}
	PipeDiff(call: () => any) {
		this.pipeDiff = call
		return this
	}
	CreateGroupIcon(icon: string) {
		this.createGroupIcon = icon
		return this
	}
	/**
	 * auto diff group , but make sure override group constructor func,
	 * if the group noy basic group
	 */
	createGroup(id: string, group: Record<string, SortGroup> = {}) {
		const that = this
		return new class extends SortGroup {
			Provide(): this {
				group[id] = this
				return this
			}
			constructor() {
				super(id);
				if (that.createGroupIcon) this.icon = that.createGroupIcon
			}
		}
	}
	CreateGroup(call: (id: string, group: Record<string, SortGroup>) => SortGroup) {
		this.createGroup = call
		return this
	}
	/**
	 * auto diff group , but make sure override group constructor func
	 */
	autoDiffGroup = false
	/**
	 * auto diff group , but make sure override group constructor func
	 */
	AutoDiffGroup(v = true): DiffSort {
		this.autoDiffGroup = v
		return this
	}
	/**
	 * auto diff group , but make sure override group constructor func
	 */
	diffGroup() {
		const Group = this.group()
		const Item = this.item()
		//if not matched item , del
		const GroupCount: any = {}
		//if not require group , add
		for (let i in Item) {
			const item = Item[i]
			if (!GroupCount[item.group]) GroupCount[item.group] = 0
			if (!Group[item.group]) {
				Group[item.group] = this.createGroup(item.group)
			}
			GroupCount[item.group]++
		}
		//del none
		for (let i in Group) {
			const group = Group[i]
			if (!GroupCount[i]) delete Group[i]
		}
	}
	diff() {
		if (this.autoDiffGroup) this.diffGroup()
		const Group = this.group()
		const GroupArray = this.groupArray()
		const Item = this.item()
		//clamp group
		for (let i in Group) {
			const group = Group[i]
			for (let j in group.data) {
				if (!Item[j]) {
					delete group.data[j]
					continue
				}
				if (group.data[j].group !== i) {
					delete group.data[j]
				}
			}
		}
		for (let i in Item) {
			if (Group[Item[i].group]) {
				Group[Item[i].group].data[i] = Item[i]
			}
		}
		//diff group item
		for (let i in Group) {
			Group[i].diffSort()
		}
		//diff group array
		new Diff()
			.pipeDifferKey(Group)
			.pipeDiffKey(GroupArray.data, d => d.key)
			.onDel(key => GroupArray.del(key))
			.onSet(key => {
				const group = Group[key]
				const item = new ArrayListItem(group).Rank(group.rank)
				GroupArray.set(key, item)
			})
			.onPass(key => {
				const item = GroupArray.get(key)
				item?.Rank(item.data?.rank)
			})
			.invoke()
		GroupArray.sortAsync()
		this.pipeDiff()
	}
	diffAsync(dt?: number) {
		this.diffTimer.callAsync(() => this.diff(), dt)
	}
}