import {Color} from "@/entity/Color.ts";
import {Theme} from "@/module/style/entity/Theme.ts";
import {AppModule} from "@/root/AppModule.ts";
import fyurry, {config} from "fyurry";
// @ts-ignore
import Back1 from "@/assets/background/Back1.jpg"
//
import {reactive} from "vue";
import {ThemeColorConfig} from "@/module/style/entity/ThemeColorConfig.ts";

export class StyleModule extends AppModule {
	//some components config
	configEntity = config.get('style', {
		themeList: {},
		theme: ''
	})
	config = this.configEntity.data
	backgroundList: Record<string, any> = {
		Back1,
		/*Back2,
		Back3,
		Back4,
		Back5,
		Back6,
		BackRef*/
	}
	autoSize = reactive(new class {
		width = window.innerWidth
		height = window.innerHeight
	})
	protected autoSizeUpdate() {
		this.autoSize.width = window.innerWidth
		this.autoSize.height = window.innerHeight
	}
	theme = new Theme()
	item = this.theme.item
	update = 0
	Item(key: string) {
		return this.theme.Item(key)
	}
	//util fast calc form color
	color(config: ThemeColorConfig) {
		if (config.color) {
			if (config.active) {
				return new Color(config.color).alpha(config.aActive)
			}
			if (config.hover) {
				return new Color(config.color).alpha(config.aHover)
			}
			return new Color(config.color).alpha(config.a)
		}
		else {
			if (config.active) {

				return this.Item(`${config.name}.${config.field || 'color'}.active`)
			}
			if (config.hover) {
				return this.Item(`${config.name}.${config.field || 'color'}.hover`)
			}
			return this.Item(`${config.name}.${config.field || 'color'}`)
		}
	}
	colorList: Record<string, any> = {
		red: [
			'#651016ff',
			'#e2a1b1ff',
			'#000',
		],
		cyan: [
			'#0b3237ff',
			'#9feaeaff',
			'#000',
		],
		green: [
			'#194325cc',
			'#a7e2b8ff',
			'#000',
		],
		orange: [
			'#5e1903ff',
			'#ea9975ff',
			'#000',
		],
		yellow: [
			'#6b5e0b',
			'#ead35bff',
			'#000',
		],
		purple: [
			'#611390ff',
			'#c07cc6ff',
			'#000',
		],
		blue: [
			'#171e59ff',
			'#c5c8ddff',
			'#000',
		],
		black: [
			'#090102ff',
			'#f7e4e8ff',
			'#000',
		],
		white: [
			'#e9ebecff',
			'#fffffff8',
			'#000',
		]
	}
	setColor(data: string[]) {
		this.theme.item['color']?.setData(data[0])
		this.theme.item['text']?.setData(data[1])
		this.theme.item['color.select']?.setData(data[1])
		this.theme.item['text.select']?.setData(data[2])
	}
	setTheme(id: string) {
		this.config.theme = id
		if (this.config.themeList[id]) {
			this.theme.configure(this.config.themeList[id])
		}
		else {
			this.theme.resetTheme()
		}
	}
	saveTheme(id: string) {
		this.config.themeList[id] = this.theme.serialize()
		this.configEntity.writeAsync()
	}
	delTheme(id: string) {
		delete this.config.themeList[id]
		if (this.config.theme == id) this.resetTheme()
	}
	resetTheme() {
		this.config.theme = ''
		this.theme.resetTheme()
	}
	addItem(id: string, def?: any) {
		return this.theme.addItem(id, def)
	}
	addGroup(id: string) {
		return this.theme.addGroup(id)
	}
	async init(): Promise<void> {
		this.theme.label = 'Red Fyurry'
		//BASE COLOR
		this.addItem('font', 'Fira Code')
		this.addItem('color', this.colorList.white[0])
		this.addItem('text', this.colorList.white[1])
		this.addItem('color.select', this.colorList.white[1])
		this.addItem('text.select', this.colorList.white[2])
		this.addItem('text.shadow').Extend('color', v => `1px 1px 1px ${v}`).Label('TextShadow')
		this.addItem('background', 'Back1').Label('Background')
		this.addItem('background.shadow', 'linear-gradient(45deg,rgba(0,0,0,.2),rgba(0,0,0,.3))')
		this.addGroup('basic').Rank(10)
		//scrollbar
		this.addItem('scrollbar.color').Extend('color', v => new Color(v).alpha(.05).hex()).Group('scrollbar')
		this.addItem('scrollbar.color.hover').Extend('color', v => new Color(v).alpha(.05).hex()).Group('scrollbar')
		this.addItem('scrollbar.color.active').Extend('color', v => new Color(v).alpha(.05).hex()).Group('scrollbar')
		this.addItem('scrollbar.thumb').Extend('color', v => new Color(v).alpha(.65).hex()).Group('scrollbar')
		this.addItem('scrollbar.thumb.hover').Extend('color', v => new Color(v).alpha(.75).hex()).Group('scrollbar')
		this.addItem('scrollbar.thumb.active').Extend('color', v => new Color(v).alpha(.95).hex()).Group('scrollbar')
		//form
		this.addItem('form.color').Extend('color', v => new Color(v).alpha(.05).hex()).Group('form')
		this.addItem('form.color.hover').Extend('color', v => new Color(v).alpha(.15).hex()).Group('form')
		this.addItem('form.color.active').Extend('color', v => new Color(v).alpha(.2).hex()).Group('form')
		this.addItem('form.border').Extend('color', v => new Color(v).alpha(.5).hex()).Group('form')
		this.addItem('form.border.hover').Extend('color', v => new Color(v).alpha(.6).hex()).Group('form')
		this.addItem('form.border.active').Extend('color', v => new Color(v).alpha(.7).hex()).Group('form')
		//button
		this.addItem('button.color').Extend('form.color').Group('button')
		this.addItem('button.color.hover').Extend('form.color.hover').Group('button')
		this.addItem('button.color.active').Extend('form.color.active').Group('button')
		this.addItem('button.border').Extend('form.border').Group('button')
		this.addItem('button.border.hover').Extend('form.border.hover').Group('button')
		this.addItem('button.border.active').Extend('form.border.active').Group('button')
		//input
		this.addItem('input.color').Extend('form.color').Group('input')
		this.addItem('input.color.hover').Extend('form.color.hover').Group('input')
		this.addItem('input.color.active').Extend('form.color.active').Group('input')
		this.addItem('input.border').Extend('form.border').Group('input')
		this.addItem('input.border.hover').Extend('form.border.hover').Group('input')
		this.addItem('input.border.active').Extend('form.border.active').Group('input')
		this.addItem('input.mask').Extend('color', v => new Color(v).alpha(.1).hex()).Group('input')
		//slider
		this.addItem('slider.color').Extend('form.color').Group('slider')
		this.addItem('slider.color.hover').Extend('form.color.hover').Group('slider')
		this.addItem('slider.color.active').Extend('form.color.active').Group('slider')
		this.addItem('slider.border').Extend('form.border').Group('slider')
		this.addItem('slider.border.hover').Extend('form.border.hover').Group('slider')
		this.addItem('slider.border.active').Extend('form.border.active').Group('slider')
		//input
		this.addItem('select.color').Extend('form.color').Group('select')
		this.addItem('select.color.hover').Extend('form.color.hover').Group('select')
		this.addItem('select.color.active').Extend('form.color.active').Group('select')
		this.addItem('select.border').Extend('form.border').Group('select')
		this.addItem('select.border.hover').Extend('form.border.hover').Group('select')
		this.addItem('select.border.active').Extend('form.border.active').Group('select')
		//shrinkbar
		this.addItem('shrinkbar.expand.color').Extend('color', v => new Color(v).alpha(.1).hex())
		this.addItem('shrinkbar.color').Extend('form.color').Group('shrinkbar')
		this.addItem('shrinkbar.color.hover').Extend('form.color.hover').Group('shrinkbar')
		this.addItem('shrinkbar.color.active').Extend('form.color.active').Group('shrinkbar')
		this.addItem('shrinkbar.border').Extend('form.border').Group('shrinkbar')
		this.addItem('shrinkbar.border.hover').Extend('form.border.hover').Group('shrinkbar')
		this.addItem('shrinkbar.border.active').Extend('form.border.active').Group('shrinkbar')
		//card
		this.addItem('card.border').Extend('color', v => new Color(v).alpha(.95).hex()).Group('card')
		//border
		this.addItem('border.color').Extend('color', v => new Color(v).alpha(.15).hex()).Group('border')
		this.addItem('border.size', 2).Group('border')
		this.addItem('border.size.padding', .85).Group('border')
		//point
		this.addItem('point.color').Extend('color', v => new Color(v).alpha(.75).hex()).Group('point')
		this.addItem('point.size.padding', .92).Group('point')
		this.addItem('point.drop').Extend('color', v => new Color(v).alpha(.4).hex()).Group('card')
		this.addItem('point.size', 4).Group('point')
		//grid
		this.addItem('grid.axis.size', 2.5).Group('grid')
		this.addItem('grid.size', 1.5).Group('grid')
		this.addItem('grid.padding', 120).Group('grid')
		this.addItem('grid.axis').Extend('color', v => new Color(v).alpha(.45).hex()).Group('grid')
		this.addItem('grid.axis.hover').Extend('color', v => new Color(v).alpha(.5).hex()).Group('grid')
		this.addItem('grid.axis.active').Extend('color', v => new Color(v).alpha(65).hex()).Group('grid')
		this.addItem('grid.color').Extend('color', v => new Color(v).alpha(.075).hex()).Group('grid')
		this.addItem('grid.color.hover').Extend('color', v => new Color(v).alpha(.12).hex()).Group('grid')
		this.addItem('grid.color.active').Extend('color', v => new Color(v).alpha(.18).hex()).Group('grid')
		//path
		this.addItem('path.editor.back').Extend('color', v => new Color(v).alpha(.1).hex()).Group('path')
		this.addItem('path.point').Extend('color', v => new Color(v).alpha(.2).hex()).Group('path')
		this.addItem('path.point.select').Extend('color', v => new Color(v).alpha(.4).hex()).Group('path')
		this.addItem('path.point.active').Extend('color', v => new Color(v).alpha(1).hex()).Group('path')
		//selecter
		this.addItem('selecter.border.op', '#3f1702').Group('selecter')
		this.addItem('selecter.border', '#f35d2f').Group('selecter')
		this.addItem('selecter.border.active', '#f3942f').Group('selecter')
		this.addItem('selecter.cursor').Extend('color').Group('selecter')
		this.addItem('selecter.cursor.size', 4).Group('selecter')
		this.addItem('selecter.border.size', 2).Group('selecter')
		//project
		this.addItem('project.color.border').Extend('color', v => new Color(v).alpha(.8).hex()).Group('project')
		//
		this.setColor(this.colorList.white)

	}
	async deploy(): Promise<void> {
		//use basic style
		//set
		//new SetGroupStyle().Provide()
		this.setTheme(this.config.theme)
		focus()
		fyurry.event.on('ui:resize', () => this.autoSizeUpdate())
	}
}