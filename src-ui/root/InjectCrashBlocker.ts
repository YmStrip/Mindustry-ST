//import {modal, out} from "fyurry";

const exit = process.exit
//@ts-ignore
process.exit = async () => {
	//out.error('[Block] process.exit()')
	//if (!await modal.confirm('somebody want exit process , accept ?')) return
	exit()
}
//@ts-nocheck
const crash = process.crash
//@ts-ignore
process.crash = async () => {
	//out.error('[Block] process.crash()')
	//if (!await modal.confirm('somebody want crash process , accept ?')) return
	crash()
}