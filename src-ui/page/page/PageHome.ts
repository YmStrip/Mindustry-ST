import {Page} from "@/page/entity/Page.ts";
// @ts-ignore
import PageHomeComp from "@/page/page/PageHomeComp.vue";
import {fs, Require} from "fyurry";

const ch = Require("child_process");

export class PageHome extends Page {
    component = PageHomeComp
    cwd = process.cwd()

    constructor() {
        super("home");
        this.Icon("mdi:home")
    }

    run() {
        //copy file
        fs.copy(
            this.cwd + '/build/libs/stDesktop.jar',
            this.cwd + '/test/data/Mindustry/mods/stDesktop.jar'
        )
        const q = ch.exec(`start.bat`, {
            cwd: `${this.cwd + "/test"}`
        })
        q.stdout.on('data', (d) => {
            console.log(d)
        })
        q.stdout.on('error',d=>{
            console.log(d)
        })
        q.stderr.on('data',d=>{
            console.log(d)
        })
    }
}