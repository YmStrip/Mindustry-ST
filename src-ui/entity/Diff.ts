
//O(n),FAST DIFF,MAP differ => diff
import {Middle} from "@/entity/Middle.ts";

export class Diff {
	//B key
	del = Middle<[string]>(
		v => v.value,
		v => v
	)
	set = Middle<[string]>(
		v => v.value,
		v => v
	)
	pass = Middle<[string]>(
		v => v.value,
		v => v
	)
	diffKeys: Record<string, any> = {}
	differKeys: Record<string, any> = {}
	diffKey(key: string) {
		this.diffKeys[key] = true
		return this
	}
	differKey(key: string) {
		this.differKeys[key] = true
		return this
	}
	onSet(call: (key: string) => any) {
		this.set.suffix(v => call(v.value))
		return this
	}
	onDel(call: (key: string) => any) {
		this.del.suffix(v => call(v.value))
		return this
	}
	onPass(call: (key: string) => any) {
		this.pass.suffix(v => call(v.value))
		return this
	}
	//liner O(n)
	invoke() {
		for (let i in this.diffKeys) {
			if (!this.differKeys[i]) this.del(i)
		}
		for (let i in this.differKeys) {
			if (!this.diffKeys[i]) this.set(i)
			else this.pass(i)
		}
		return this
	}
	invokeSeq(data: ('set' | 'del' | 'pass')[]) {
		let pass:any[] = []
		for (let mode of data) {
			if (mode=='set') {
				for (let i in this.differKeys) {
					if (!this.diffKeys[i]) this.set(i)
					else pass.push(i)
				}
			}
			else if (mode=='del') {
				for (let i in this.diffKeys) {
					if (!this.differKeys[i]) this.del(i)
				}
			}
			else if (mode=='pass') {
				for (let i of pass) {
					this.pass(i)
				}
			}
		}
		return this
	}
	//two index
	//...
	//start
	start() {
		this.diffKeys = {}
		this.differKeys = {}
		return this
	}
	pipe(call: (diff: Diff) => any) {
		call(this)
		return this
	}
	pipeDifferKey(differ: any, map: ((d: any, i: any) => any) = (d, i) => i) {
		for (let i in differ) this.differKey(map(differ[i], i))
		return this
	}
	pipeDiffKey(diff: any, map: ((d: any, i: any) => any) = (d, i) => i) {
		for (let i in diff) this.diffKey(map(diff[i], i))
		return this
	}
}