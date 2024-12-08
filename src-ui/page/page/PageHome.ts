import {Page} from "@/page/entity/Page.ts";
// @ts-ignore
import PageHomeComp from "@/page/page/PageHomeComp.vue";
import {fs, Require} from "fyurry";

const ch = Require("child_process");
import type {ChildProcess} from "child_process"

export class PageHome extends Page {
    component = PageHomeComp
    cwd = process.cwd()
    last:ChildProcess

    constructor() {
        super("home");
        this.Icon("mdi:home")
    }
    killLast() {
        try {this.last.kill(0)} catch (e) {}
    }

    build() {
        this.killLast()
        return new Promise((s, e) => {
            console.log('build...')
            const q = ch.exec(`gradlew.bat jar`, {
                cwd: `${this.cwd}`
            })
            this.last = q
            q.stdout.on('data', (d) => {
                console.log(d)
            })
            q.stdout.on('error', d => {
                console.log(d)
            })
            q.stderr.on('data', d => {
                console.log(d)
            })
            q.stdout.on('end', (e) => {
                s(e)
            })
        })
    }

    run() {
        this.killLast()
        return new Promise((s, e) => {
            //copy file
            fs.copy(
                this.cwd + '/build/libs/stDesktop.jar',
                this.cwd + '/test/data/Mindustry/mods/stDesktop.jar'
            )
            const q = ch.exec(`start.bat`, {
                cwd: `${this.cwd + "/test"}`
            })
            this.last = q
            q.stdout.on('data', (d) => {
                console.log(d)
            })
            q.stdout.on('error', d => {
                console.log(d)
            })
            q.stderr.on('data', d => {
                console.log(d)
            })
            q.stdout.on('end', (e) => {
                s(e)
            })
        })
    }
}