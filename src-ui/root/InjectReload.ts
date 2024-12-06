import fyurry from "fyurry";


export const Reload = async () => {
	await fyurry.event.emit('ui:reload')
	location.reload()
}
/*
Object.defineProperty(location,'reload',{
	writable: true,
	configurable: true,
	value:Reload
})
*/
window.addEventListener('keydown', e => {
	switch (e.key) {
		case "F5":
			return Reload()
	}
})