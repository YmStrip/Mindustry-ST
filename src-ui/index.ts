import fyurry from "fyurry";
// @ts-ignore
import App from "@/App.vue";
import {createApp} from "vue";

const app = async () => {
	const v = createApp(App)
	v.config.warnHandler = () => null
	await fyurry.init(v)
	setTimeout(()=>{
		v.mount('#app')
	},250)
}
app()