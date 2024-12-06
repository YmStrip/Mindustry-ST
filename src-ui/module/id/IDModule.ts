import {IDBus} from "@/module/id/entity/IDBus.ts";
import {AppModule} from "@/root/AppModule.ts";
import {out} from "fyurry";

export class IDModule extends AppModule {
	//global single id bus
	bus = new IDBus()
	//util
	//get a no repeat key in object
	key(key: string, get: any) {
		if (!get(key)) {
			return key
		}
		//split as
		const keySplit = key.split('.')
		//test is a number
		const rawEndNumber = Math.round(Number(keySplit[keySplit.length - 1]))
		let start: number
		if (keySplit.length == 1 || isNaN(rawEndNumber) || !Number.isFinite(rawEndNumber)) {
			start = 0
		} else if (Number.isInteger(rawEndNumber)) {
			key = keySplit.slice(0, -1).join('.')
			start = rawEndNumber
		} else {
			start = 0
		}
		let count = 0
		//for test
		while (true) {
			++count
			//max 100000
			if (count > 100000) {
				out.error('net next key too many > 100000')
				key = this.id()
				break
			}
			if (isNaN(start) || !Number.isFinite(start)) {
				start = Math.round(Math.random() * 500)
			}
			let forTest = start == 0 ? key : key + '.' + start
			if (get(forTest) !== undefined) {
				++start
				continue
			}
			key = forTest
			break
		}
		return key
	}
	protected idCurrent: any
	protected idCurrentTime: any
	//random id
	id(length = 12, map = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789') {
		if (this.idCurrentTime !== Date.now()) {
			this.idCurrentTime = Date.now()
			this.idCurrent = 0
		} else {
			this.idCurrent++
		}
		const date = (Math.random() * Date.now()).toString(36).split('.')[0]
		const dateCurrent = this.idCurrent + ''
		let t = length - dateCurrent.length - date.length
		if (t < 0) {
			console.warn('[random id] at least ' + (dateCurrent.length + date.length) + ' char')
			t = 2
		}
		const result = [...Array(t)].map(() => map.charAt(Math.floor(Math.random() * map.length))).join('') + this.idCurrent + date
		//console.log(result)
		return result
	}
	async deploy(): Promise<void> {

	}
}