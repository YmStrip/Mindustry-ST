let isInit: any
export const AppList: Record<string, AppModule> = {}
export const AppListInit = async () => {
	if (isInit) return isInit
	isInit = new Promise(async resolve => {
		let time = Date.now()
		for (let i in AppList) {
			time = Date.now()
			const mod: any = AppList[i]
			if (!mod) continue
			if (mod.init) {
				await mod.init()
			}
			const dt = Date.now() - time
		}
		for (let i in AppList) {
			time = Date.now()
			const mod: any = AppList[i]
			if (!mod) continue
			if (mod.deploy) {
				await mod.deploy()
			}
			const dt = Date.now() - time
		}
		resolve(this)
	})
	return isInit
}
export class AppModule {
	async init() {

	}
	async deploy() {

	}
	constructor() {
		if (AppList[this.constructor.name]) throw new Error('Exist Module')
		AppList[this.constructor.name] = this
	}
}