import {AppModule} from "@/root/AppModule.ts";
import {createI18n} from 'vue-i18n'
import en from "@/locate/en.json"
import {app, config} from "fyurry";

export class I18NModule extends AppModule {
	i18n = createI18n({
		locale: 'en-us',
		messages: {
			'en-us': en,
			'zh-cn': {},
			'zh-tw': {},
			'ja-jp': {},
		},
		silentTranslationWarn: true,
		silentFallbackWarn: true
	})
	local: string = 'en-us'
	languageList: Record<string, any> = {
		'en-us': 'English',
		'zh-cn': '简体中文',
		'zh-tw': '繁體中文',
		'ja-jp': '日本語'
	}
	configEntity = config.get('bundle', {
		lang: 'en-us'
	})
	config = this.configEntity.data
	Local(v: string) {
		if (!this.languageList[v]) return
		this.local = v
		this.i18n.global.locale = <any>v
		this.t = this.t
		return this
	}
	format(text: any, call: (d: string) => string) {
		if (typeof text === "string") {
			return call(text)
		}
		if (typeof text === 'object') {
			if (text[this.local]) return call(text[this.local] || '');
			return call(text[Object.keys(text || {})[0]] || '')
		}
		return call(text + '')
	}
	//when add a text like "@xxx" should space?
	shouldSpace() {
		return this.local != 'zh'
	}
	atSpace() {
		return this.shouldSpace() ? ' ' : ''
	}
	lang(text: any) {
		if (typeof text === 'object') {
			if (text[this.local]) this.local
			return Object.keys(text || {})[0] || this.local
		}
		return this.local
	}
	t(text: any, def: any = text) {
		if (!text) return def
		if (typeof text === "string") {
			const vs = this.i18n.global.t(text || '');
			if (vs && vs != text) return vs
			return def
		}
		if (typeof text === 'object') {
			if (text[this.local]) return text[this.local];
			return text[Object.keys(text || {})[0]] || def
			//translate by api
		}
		return def
	}
	getStrType(data: any) {
	}
	split(text: any) {
		return this.splitString(this.t(text))
	}
	splitString(text: string): string[] {
		const result: string[] = [];
		let currentWord = '';
		for (let i = 0; i < text.length; i++) {
			const char = text[i];
			if ((char == ' ') && currentWord) { // 按照空格分割英文
				if (currentWord) {
					result.push(currentWord + ' ')
				}
				else {
					result.unshift(' ');
				}
				currentWord = '';
			}
			else if (/[\u4e00-\u9fa5]/.test(char)) { // 中文每一个字分割一次
				if (currentWord) result.push(currentWord);
				result.push(char);
				currentWord = '';
			}
			else {
				currentWord += char;
			}
		}
		if (currentWord) result.push(currentWord);
		return result;
	}
	join(text: any[]) {
		if (this.local == 'zh') return text.join('')
		return text.join(' ')
	}
	getOptions() {
		let d = []
		for (let i in this.languageList) {
			d.push({
				value: i,
				label: this.languageList[i]
			})
		}
		return d
	}
	addLang(lang: any, label: any) {
		this.languageList[lang] = lang
	}
	options() {
		const data: any[] = []
		for (let i in this.languageList) {
			data.push({
				label: this.languageList[i] || i,
				value: i
			})
		}
		return data
	}
	addMessageObject(key: string, data: any) {
		if (typeof data === 'string') {
			this.addMessage(this.local, {[key]: data})
		}
		else if (typeof data === 'object') {
			for (let i in data) {
				this.addMessage(i, {[key]: data[i]})
			}
		}
	}
	addMessage(lang: any, data: any = {}) {
		if (!this.languageList[lang]) this.addLang(lang, lang)
		// @ts-ignore
		if (!this.i18n.global.messages[lang]) this.i18n.global.messages[lang] = {}
		const deepAssign = (a: any, b: any) => {
			for (let i in b) {
				if (typeof (a[i]) === 'object' && typeof (b[i]) === 'object') {
					deepAssign(a[i], b[i])
				}
				else {
					a[i] = b[i]
				}
			}
		}
		//@ts-ignore
		deepAssign(this.i18n.global.messages[lang], data)
	}
	async deploy(): Promise<void> {
		app.use(<any>this.i18n)
		this.Local(this.config.lang)
	}
}