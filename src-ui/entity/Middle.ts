import fyurry from "fyurry";

export type MiddleArg<I = any, O = any> = {
	//prop prop
	props: I extends any[] ? I : [I]
	//current value
	value: O
	//target object
	target: Middle
	//set skip number , -1 to skip to end
	skip: (number?: any) => any
}
export type MiddleFunc<I = any, O = any> = (data: MiddleArg<I, O>) => O
export type MiddleFuncAsync<I = any, O = any> = (data: MiddleArg<I, O>) => Promise<O>
export type MiddlePreset<I = any, O = any> = (...data: I extends any[] ? I : [I]) => O
export type MiddlePresetAsync<I = any, O = any> = (...data: I extends any[] ? I : [I]) => Promise<O>
export function MiddleFunc(async = false, ...argBuild: any[]) {
	// @ts-ignore
	const middle = new MiddleClass(...argBuild)
	return new Proxy(async ? middle.asyncCall : <any>middle.call, {
		apply(target: (...data: any) => any, thisArg: any, argArray: any[]): any {
			return middle.call(...argArray)
		},
		get(target: any, p: string | symbol, receiver: any): any {
			// @ts-ignore
			return middle[<any>p]
		},
		set(target: any, p: string | symbol, newValue: any, receiver: any): boolean {
			// @ts-ignore
			middle[<any>p] = newValue
			return true
		}
	})
}
MiddleFunc.constructor = (...arg: any) => {
	return MiddleFunc(...arg)
}
export class MiddleClass<I = any, O = any> {
	middle: any[] = []
	preset: any
	prefix(func: MiddleFunc<I, O>) {
		this.middle.splice(0, 0, func)
		return this
	}
	suffix(func: MiddleFunc<I, O>) {
		this.middle.push(func)
		return this
	}
	splice(index: number, func: MiddleFunc<I, O>) {
		this.middle.splice(index, 0, func)
		return this
	}
	clear() {
		this.middle = []
		return this
	}
	//...arg:any[],last
	call(...data: I extends any[] ? I : [I]): O {
		let start: any = this.preset(...data)
		let skip = 0
		const that = this
		for (let i in this.middle) {
			if (skip > 0) {
				skip--
				continue
			}
			const v = {
				props: data,
				value: start,
				target: that,
				skip(v: number = 1) {
					if (v == -1) v = 99999
					skip = v
				}
			}
			try {
				start = this.middle[i](v)
			} catch (e) {
				Yurry.out.error(e)
			}
		}
		return start
	}
	async asyncCall(...data: I extends any[] ? I : [I]): Promise<O> {
		let start: any = await this.preset(...data)
		let skip = 0
		const that = this
		for (let i in this.middle) {
			if (skip > 0) {
				skip--
				continue
			}
			const v = {
				props: data,
				value: start,
				target: that,
				skip(v: number = 1) {
					if (v == -1) v = 99999
					skip = v
				}
			}
			try {
				start = await this.middle[i](v)
			} catch (e) {
				fyurry.out.error(e)
			}
		}
		return start
	}
	constructor(func: MiddleFunc<I, O>, preset: MiddlePreset<I, O>) {
		this.preset = preset
		if (func) this.suffix(func)
	}
}
export type MiddleType<I = any, O = any> = {
	(...data: I extends any[] ? I : [I]): O
} & MiddleClass<I, O>
export type MiddleTypeAsync<I = any, O = any> = {
	(...data: I extends any[] ? I : [I]): Promise<O>
} & MiddleClass<I, O>
export type Middle = {
	new<I = any, O = any>(call: MiddleFunc<I, O>, preset: MiddlePreset<I, O>): MiddleType<I, O>
	<I = any, O = any>(call: MiddleFunc<I, O>, preset: MiddlePreset<I, O>): MiddleType<I, O>
}
export type MiddleAsync = {
	new<I = any, O = any>(call: MiddleFuncAsync<I, O>, preset: MiddlePresetAsync<I, O>): MiddleTypeAsync<I, O>
	<I = any, O = any>(call: MiddleFuncAsync<I, O>, preset: MiddlePresetAsync<I, O>): MiddleTypeAsync<I, O>
}
export const Middle: Middle = <any>((...arg: any) => MiddleFunc(false, ...arg))
export const MiddleAsync: MiddleAsync = <any>((...arg: any) => MiddleFunc(true, ...arg))