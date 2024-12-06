import {initialize} from "@electron/remote/main";
import {app} from "electron"
import {BasicWindow} from "./install/BasicWindow";
import {Protocol} from "./install/Protocol";

app.commandLine.appendSwitch('disable-site-isolation-trials');
app.on('ready', () => {
	initialize()
	Protocol()
	BasicWindow()
})
app.setPath('temp',__dirname+'/temp')
process.on('uncaughtException', e => {
	console.log(e)
})