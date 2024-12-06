import electron, {app} from "electron";
import {CWindow} from "../lib/CWindow";
import {is} from '@electron-toolkit/utils'
import {join} from "node:path"

export const BasicWindow = () => {
	if (is.dev && process.env['ELECTRON_RENDERER_URL']) {
		//const main = new CWindow()
		const main = new CWindow({
			width: electron.screen.getPrimaryDisplay().workArea.width,
			height: electron.screen.getPrimaryDisplay().workArea.height,
		})
		//main.loadURL(process.env['ELECTRON_RENDERER_URL']+ `/`)
		main.loadURL(process.env['ELECTRON_RENDERER_URL'] + ``)
	}
	else {
		const main = new CWindow()
		main.loadFile(join(__dirname, '../renderer/index.html'))
	}
	//add a file protocol
	app.on('window-all-closed', () => {
		if (process.platform !== 'darwin') app.quit()
	})
}