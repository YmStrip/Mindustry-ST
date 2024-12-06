import {BrowserWindow} from "electron";
import BrowserWindowConstructorOptions = Electron.BrowserWindowConstructorOptions;
import {shell} from 'electron'
import {enable} from "@electron/remote/main/index.js"
import {join} from "node:path";
// @ts-ignore
import icon from "./icon.png"
export class CWindow extends BrowserWindow {
	//safe option
	//not support open not local html in app
	isLocal(url: string) {
		return (
			//localhost
			url.startsWith('http://localhost') || url.startsWith('https://localhost') ||
			//ws
			url.startsWith('ws://localhost') || url.startsWith('wss://localhost') || url.startsWith('http://127.0.0.1') || url.startsWith('https://127.0.0.1') || url.startsWith('ws://127.0.0.1') ||
			//file
			url.startsWith('file:///')
		)
	}
	constructor(config?: BrowserWindowConstructorOptions) {
		if (!config) config = {}
		if (!config?.webPreferences) config.webPreferences = {}
		config.webPreferences.nodeIntegration = true
		config.webPreferences.contextIsolation = false
		config.webPreferences.nodeIntegrationInSubFrames = false
		config.autoHideMenuBar = true
		//config.icon = path.join(__dirname,icon)
		//allow local res
		config.webPreferences.webSecurity = false
		// config.webPreferences.preload = join(__dirname, 'Preload.js')
		delete config.icon
		super(config);
		this.setMenu(null)
		this.setIcon(join(__dirname,icon))
		this.webContents.executeJavaScript('window.sys=this')
		this.webContents.on('before-input-event', (_, input) => {
			if (input.type === 'keyDown' && input.key === 'F12') {
				this.webContents.toggleDevTools();
			}
		});
		//not allow open or jump remote url , open in browser
		this.webContents.on('will-navigate', e => {
			if (!this.isLocal(e.url)) {
				shell.openExternal(e.url)
				e.preventDefault()
			}
		})
		this.webContents.setWindowOpenHandler((e) => {
			if (!this.isLocal(e.url)) {
				shell.openExternal(e.url)
				return {
					action: 'deny'
				}
			}
			return {
				action: 'allow'
			}
		})
		enable(this.webContents)
		/*this.webContents.addListener('unresponsive',e=>{
			console.error(e)
		})*/
		/*this.webContents.addListener('crashed',e=>{
			console.error(e)
		})*/
	}
}