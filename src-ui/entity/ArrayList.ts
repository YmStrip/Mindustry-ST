import {EventEmitter} from "@/entity/EventEmitter.ts";
import {Register} from "@/entity/Register.ts";
import {id} from "fyurry";
//do not store very large data >= O(n log n)~O(n^2)
//The default serialize and configure do not support data object has function or circular reference, otherwise override and implement manually
export class ArrayList<T extends ArrayListItem = ArrayListItem> extends Register {
	data: T[] = []
	map: Record<string, number> = {}
	//remove perform but not need manaualy run .sortAsync()
	autoSort = true
	count() {
		return this.data.length
	}
	//O(1)
	indexOf(key: string | T | number) {
		if (typeof key === 'number') return key
		const keyName = typeof key === 'object' ? key.key : key
		const index = this.map[keyName]
		if (index == undefined) return -1
		return index
	}
	//O(1)
	index(key: string | T) {
		return this.indexOf(key)
	}
	init(key: string, data: () => T) {
		if (this.map[key] == undefined) {
			this.set(key, data())
		}
	}
	//O(1)
	//not auto sort for --O , use .sort(),the sort is O(n log n+)
	set(key: string | T, data?: T) {
		const keyName = typeof key === 'object' ? key.key : key
		const keyData = typeof key === 'object' ? key : data
		if (!keyData) return
		keyData.parent = this
		keyData.key = keyName
		if (this.map[keyName] == undefined) {
			this.data.push(keyData)
			this.map[keyName] = this.data.length - 1
		}
		else {
			this.data[this.map[keyName]] = keyData
		}
		if (this.autoSort) this.sortAsync()
	}
	//O(1)
	get(key: string | T): T {
		const index = this.indexOf(key)
		return <any>this.data[index]
	}
	del(key: string | T) {
		const keyName = typeof key === 'object' ? key.key : key
		if (this.map[keyName] == undefined) return
		const index = this.map[keyName]
		const item = this.data[index]
		if (index == -1 || !item) return;
		this.data.splice(index, 1)
		delete this.map[keyName]
	}
	//O(nlog n+)
	sort() {
		this.data.sort((a, b) => {
			return b.rank - a.rank
		})
		this.map = {}
		for (let i = -1, l = this.data.length; ++i < l;) {
			this.map[this.data[i].key] = i
		}
	}
	protected sortAsyncIter = 0
	//in time,only call once
	//in some for(){ .sortAsync() }
	sortAsync(dt = 250) {
		const v = ++this.sortAsyncIter
		setTimeout(() => {
			if (v != this.sortAsyncIter) return
			this.sort()
		}, dt)
	}
	//O(nlog n+)
	//clamp rand as (10,9,8,7...)
	clampRank() {
		let length = this.data.length
		this.data.forEach(d => {
			d.rank = length
			--length
		})
		this.sortAsync()
	}
	//O(n)
	keys() {
		Object.keys(this.map)
	}
	clear() {
		for (let i in this.map) {
			this.del(i)
		}
		this.map = {}
		this.data = []
	}
	insert(index: number, ...data: T[]) {
		if (index == -1) {
			index = this.data.length
		}
		return this.splice(index, 0, ...data)
	}
	//O(n) 2n+
	splice(index: number | string | T, del: number, ...app: T[]) {
		index = this.indexOf(index) || 0
		if (this.data.length >= index) {
			app.forEach(d => {
				this.data.push(d)
			})
		}
		const delKeys: any[] = []
		for (let i = index - 1, l = index + del; ++i < l;) {
			if (!this.data[i]) break
			delKeys.push(this.data[i].key)
		}
		for (let i = delKeys.length; --i >= 0;) {
			this.del(delKeys[i])
		}
		for (let i = -1, l = app.length; ++i < l;) {
			this.data.splice(index + i, 0, app[i])
		}
		this.sortAsync()
	}
	nextKey(key: string) {
		return id.key(key, v => this.get(v))
	}
	//change item rank , return k * (number | string) + b , use k and b to control rank
	//O(n) + O(sort)
	resort(call: (data: T) => { data: number | string, k: number, b: number }) {
		const count = this.data.length
		let min: number = null
		let max: number = null
		const cache: {
			k: number,
			b: number,
			data: (number | string)
		}[] = []
		for (let i in this.data) {
			const res = call(this.data[i])
			if (Number.isFinite(res.data)) {
				const number = <number>res.data
				if (min > number || min == number) min = number
				if (max < number || max == number) max = number
				cache.push({
					k: res.k,
					b: res.b,
					data: number,
				})
			}
			else {
				cache.push(res)
			}
		}
		const d = (max || count) - (min || 0)
		//directly calc the rank of a string in unicode space
		const stringToScore = (str: string, a: number, b: number) => {
			const maxUnicode = 65536; //
			const minUnicode = 0; //
			let score = 0;
			let factor = 1;
			for (let i = -1, l = str.length; ++i < l;) {
				const charCode = str.charCodeAt(i);
				const normalizedValue = (charCode - minUnicode) / (maxUnicode - minUnicode);
				score += normalizedValue * factor;
				factor /= maxUnicode;
			}
			return a + score * (b - a);
		}
		for (let i in this.data) {
			const data = cache[i]
			if (Number.isFinite(data.data)) {
				const number = <number>data.data
				this.data[i].rank = data.k * ((number - (min || 0)) / d * count) + data.b
			}
			else {
				const name = <string>data.data
				const hash = stringToScore(name, count, 0)
				this.data[i].rank = data.k * hash + data.b
			}
		}
		this.sort()
	}
	//rename,maybe override new
	rename(oldKey: string, newKey: string) {
		if (oldKey == newKey) return
		this.set(newKey, this.get(oldKey))
		this.del(oldKey)
	}
	//rename but not repeat and not override new
	renameNext(oldKey: string, newKey: string) {
		if (oldKey == newKey) return
		newKey = this.nextKey(newKey)
		this.set(newKey, this.get(oldKey))
		this.del(oldKey)
	}
	constructor() {
		super();
	}
}
export class ArrayListItem<T extends any = any> extends Register {
	event?: EventEmitter
	parent: ArrayList
	rank = 1
	key: string = id.id()
	Rank(v: number) {
		this.rank = v
		if (this.parent) this.parent.sortAsync()
		return this
	}
	Key(v: string) {
		this.key = v
		return this
	}
	constructor(public data: T) {
		super()
	}
}