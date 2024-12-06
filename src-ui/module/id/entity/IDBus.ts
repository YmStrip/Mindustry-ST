//ID BUS
import {ArrayList, ArrayListItem} from "@/entity/ArrayList.ts";
import {DiffSort} from "@/entity/DiffSort.ts";
import {Register} from "@/entity/Register.ts";
import {IDGroup} from "@/module/id/entity/IDGroup.ts";
//manage the relationship between provider and slot
export class IDBus extends Register {
	// this catalog just save a rank
	update = 0;
	data: Record<string, IDGroup> = {}
	dataSort = new ArrayList<ArrayListItem<IDGroup>>()
	dataDiff = DiffSort.pipeCreate(
		that => that.dataSort,
		that => that.data,
		that => ({}),
		this
	)
	group(type: string) {
		if (!this.data[type]) {
			this.data[type] = new IDGroup(type)
			this.dataDiff.diffAsync()
		}
		return this.data[type]
	}
	//find fake
	fakeTest() {
		let z = 0
		for (let i in this.data) {
			const group = this.data[i]
			group.idpEach(idp => {
				if (idp.fakeTest()) z++
			})
		}
		return z
	}
	fakeDel() {
		let z = 0
		for (let i in this.data) {
			const group = this.data[i]
			group.idpEach(idp => {
				if (idp.fakeTest()) {
					z++
					idp.del()
				}
			})
		}
		return z
	}
	//
	serialize(): Record<string, any> {
		return {}
	}
	configure(data: any): any {
		return this
	}
	constructor() {
		super();
	}
}