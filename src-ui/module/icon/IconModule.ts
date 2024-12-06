import {AppModule} from "@/root/AppModule.ts";
import {icons as ICON_MDI} from "@iconify-json/mdi";
import {icons as ICON_IC} from "@iconify-json/ic";
import {icons as ICON_VSCODE} from "@iconify-json/vscode-icons";
import {icons as ICON_SvgSpinners} from "@iconify-json/svg-spinners"
import {icons as ICON_LineMd} from "@iconify-json/line-md"
import {icons as ICON_Devicon} from "@iconify-json/devicon"
import {addCollection} from "@iconify/vue/offline";
import {IconifyJSON} from "@iconify/vue/offline";
import {out} from "fyurry";

export class IconModule extends AppModule {
	iconList: Record<string, IconifyJSON> = {}
	add(name: string, data: IconifyJSON) {
		addCollection(data)
		this.iconList[name] = data;
		out.print(`[Iconify] add ${name}`)
	}
	// example mdi:xxx => true
	include(src = '') {
		for (let i in this.iconList) {
			if (src.startsWith(i + ':')) return true
		}
		return false
	}
	total() {
		let v = 0
		for (let i in this.iconList) {
			const data = this.iconList[i]
			v += Object.keys(data.icons).length
		}
		return v
	}
	async init(): Promise<void> {
		this.add('ic', ICON_IC)
		this.add('mdi', ICON_MDI)
		this.add('vscode-icons', ICON_VSCODE)
		this.add('line-md', ICON_LineMd)
		this.add('svg-spinners', ICON_SvgSpinners)
		this.add('devicon', ICON_Devicon)
	}
	async deploy(): Promise<void> {
		out.print(`[Iconify] load ${Object.keys(this.iconList).length} Repository Σ = ${this.total()} icons`)
	}
}