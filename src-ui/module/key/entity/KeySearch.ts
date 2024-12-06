import {Diff} from "@/entity/Diff.ts";
import {Timer} from "@/entity/Timer.ts";
import {KeyContainer} from "@/module/key/entity/KeyContainer.ts";
import {KeyItem} from "@/module/key/entity/KeyItem.ts";


export class KeySearch {
	key: string[] = []
	data: KeyItem[][] = []
	ary2diff(key: string[]) {
		let res: any = {}
		for (let i = -1, l = key.length; ++i < l;) {
			res[this.root.ary2str(key.slice(0, i + 1))] = true
		}
		return res
	}
	diffTimer = new Timer()
	focus = 0
	/**
	 * increase diff
	 * @param root root container
	 * @param str search key
	 */
	diff(str: string) {
		const raw = this.root.str2ary(str)
		/*console.log(
			this.ary2diff(this.key), 'd->',
			this.ary2diff(raw)
		)*/
		new Diff()
			.pipeDiffKey(this.ary2diff(this.key))
			.pipeDifferKey(this.ary2diff(raw))
			//key : "xx.xx.xx ..."
			.onPass(str => {
				const ary = this.root.str2ary(str)
				if (ary.length==raw.length) return
				this.data[ary.length - 1] = []
			})
			.onDel(str => {
				const ary = this.root.str2ary(str)
				this.data.splice(ary.length - 1, 1)
				this.key.splice(ary.length - 1, 1)
				if (!(ary.length > 1 && ary.pop() == '')) return
				//search one
				const container = this.root.getAry(ary.slice(0, -1))
				if (container) {
					this.data[ary.length - 1] = container.searchCurrent(ary[ary.length - 1])
				}
			})
			.onSet(str => {
				const ary = this.root.str2ary(str)
				this.key[ary.length - 1] = ary[ary.length - 1]
				//di = xxx.xxx
				if (ary.length != raw.length) {
					//console.log('setLast', this.key, ary)
					this.data[ary.length - 1] = []
				}
				//di = xxx
				else {
					//console.log('set', this.key, ary)
					const container = this.root.getStr(str)
					if (container) {
						this.data[ary.length - 1] = container.searchCurrent(ary.pop())
					}
					else {
						this.data[ary.length - 1] = []
					}
				}
			})
			.invoke()
		//console.log('de', this.key, this.key.length, this.ary2diff(this.key))
	}
	diffAsync(str: string) {
		this.diffTimer.callAsync(() => this.diff(str), 150)
	}
	constructor(public root: KeyContainer) {
	}
}