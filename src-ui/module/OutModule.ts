import {AppModule} from "@/root/AppModule.ts";
import {fs, i18n} from "fyurry";
import {format} from "date-fns"

export class OutModule extends AppModule {
	protected $time = Date.now()
	print(...data: any[]) {
		console.log(`[${this.formatTime(new Date)}]`, ...data)
	}
	println(...arg: any[]) {
		return this.print(...arg)
	}
	printName(name: string, ...arg: any[]) {
		this.print(`[${name}]`, ...arg)
	}
	printInit(name: string) {
		return this.printName('init', name)
	}
	printDeploy(name: string) {
		return this.printName('deploy', name)
	}
	error(error: any = '') {
		console.error(error)
		this.errorStack()
		this.Message?.error(error, {
			keepAliveOnHover: true,
		})
	}
	errorStack() {
		try {
			throw new Error('stack')
		} catch (e) {
			console.error(e)
			return e
		}
	}
	errorStackData() {
		try {
			throw new Error('stack')
		} catch (e) {
			return e
		}
	}
	throw(err?: any) {
		console.log(err)
		this.error(err)
		throw new Error(err)
	}
	warn(error: any = '') {
		console.warn(error)
	}
	formatTime(time: number | Date = new Date, str = 'yyyy-MM-dd HH:mm:ss') {
		return format(time, str)
	}
	//format relative time
	relativeTime(dt = 0) {
		let res = ''
		if (dt < 1000) res = `${dt}ms`
		else if (dt < 60 * 1000) {
			//const a = Math.floor(dt / 1000)
			//const b = dt % 1000
			const a = (dt / 1000).toFixed(2)
			res = `${a}s`
		}
		else if (dt < 60 * 60 * 1000) {
			const a = Math.floor(dt / (1000 * 60))
			const b = dt % (1000 * 60)
			const c = (b / 1000).toFixed(2)
			res = `${a}m ${c}s`
		}
		else {
			const a = Math.floor(dt / (1000 * 60 * 60))
			const b = dt % (1000 * 60 * 60)
			const c = Math.floor(b / (1000 * 60))
			const d = b % (1000 * 60)
			const e = (d / 1000).toFixed(2)
			res = `${a}h ${c}m ${e}s`
		}
		return res
	}
	relativeTimeFormat(str = '', dt = 0) {
		const res = this.relativeTime(dt)
		return str.replace(/(%s)/g, res)
	}
	//print relative time between last setTime
	printTime(str = '', t = 0) {
		const dt = t || Date.now() - this.$time
		this.$time = Date.now()
		this.print(this.relativeTimeFormat(str, t || dt))
	}
	//return time between last setTime
	time() {
		const dt = Date.now() - this.$time
		this.$time = Date.now()
		return dt
	}
	//set time cache
	setTime() {
		this.$time = Date.now()
		return this.$time
	}
	getTime() {
		return Date.now() - this.$time
	}
	descName(code: any) {
		if (!(typeof code === 'string')) return '...'
		if (code.startsWith('data:')) return 'base64url'
		return fs.filename(code)
	}
	message(type: 'info' | 'error' | 'warn' | 'success', message?: any, /*options?: MessageOptions*/) {
		return
		message = i18n.t(message)
		this.hint(type, message)
		if (type == 'info') this.Message?.info(message || type, options)
		else if (type == 'error') this.Message?.error(message || type, options)
		else if (type == 'warn') this.Message?.warning(message || type, options)
		else if (type == 'success') this.Message?.success(message || type, options)
	}
	hintList: { type: string, message: any }[] = []
	hint(type: 'info' | 'error' | 'warn' | 'success', message?: any) {
		const f = {
			type,
			message
		}
		this.hintList.splice(0, 0, f)
		this.print(`[${type}] ${message}`)
		setTimeout(() => {
			const index = this.hintList.indexOf(f)
			if (index !== -1) this.hintList.splice(index, 1)
		}, 5 * 1000)
	}
	async init(): Promise<void> {
		//out style
	}
}