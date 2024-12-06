import {ArrayList, ArrayListItem} from "@/entity/ArrayList.ts";
import {DiffSort} from "@/entity/DiffSort.ts";
import {Register} from "@/entity/Register.ts";
import {Timer} from "@/entity/Timer.ts";
import {ThemeItem} from "@/module/style/entity/ThemeItem.ts";
import {reactive} from "vue";
import fyurry from "fyurry";
import {SortGroup} from "@/entity/SortGroup.ts";

export class Theme extends Register {
	updateTimer = new Timer()
	update = 0
	label: string = 'Red Fyurry'
	extendTree: Record<string, Record<string, ThemeItem>> = {}
	diffTimer = new Timer()
	diffAsync() {
		this.diffTimer.callAsync(() => this.diff())
	}
	diff() {
		this.diffGroup()
		this.diffTheme()
	}
	diffGroupAsync() {
		this.itemDiff.diffAsync()
	}
	diffGroup() {
		this.itemDiff.diff()
	}
	diffTheme() {
		this.extendTree = {}
		for (let i in this.item) {
			this.diffThemeItem(this.item[i], 'set')
		}
	}
	//increase diff
	diffThemeItem(item: ThemeItem, mode: 'set' | 'del', cir: any = {}) {
		item.diffAsync(mode, cir)
	}
	//
	addGroup(id: string) {
		return this.itemDiff.createGroup(id, this.itemGroup).Provide()
	}
	resetTheme() {
		for (let i in this.item) {
			this.item[i].extendOver = false
		}
		this.diff()
	}

	addItem(id: string, def?: any) {
		const item = this.item[id] = new ThemeItem(this, id)
		if (def) {
			item.data = def
			item.setType()
		}
		this.diffThemeItem(item, 'set')
		return item
	}
	//
	serialize(): any {
		return Object.assign(super.serialize(), {
			item: Register.serializeDeep(this.item)
		});
	}
	configure(data: any): any {
		super.configure(data)
		for (let i in this.item) {
			if (data.item[i]) {
				this.item[i].configure(data.item[i])
			}
		}
		this.diffAsync()
		return this
	}
	Status(type: string) {
		return this.Item('status.' + type) || this.Item('status.grey')
	}
	Item(id: string) {
		return this.item[id]?.data
	}
	Update() {
		this.updateTimer.callInter(() => {
			this.Item = this.Item
			fyurry.style.Item = fyurry.style.Item
			++this.update
		},500)
		return this
	}
	//get style
	constructor() {
		super();
		this.setIgnoreKey([
			'id', 'diffTimer', 'extendTree', 'requireTree', 'update',
			'item', 'itemDiff', 'itemSort', 'itemGroup',
			'style', 'styleDiff', 'styleSort', 'styleGroup','updateTimer'
		])
	}
	//webstorm stupid bugs , dead cache
	item: Record<string, ThemeItem> = {}
	itemGroup: Record<string, SortGroup<ThemeItem>> = {}
	itemSort: ArrayList<ArrayListItem<SortGroup<ThemeItem>>> = <any>reactive(new ArrayList())
	itemDiff = DiffSort.pipeCreate(
		that => that.itemSort,
		that => that.itemGroup,
		that => that.item,
		this
	)
		.CreateGroupIcon('mdi:colorize')
		.AutoDiffGroup()
}