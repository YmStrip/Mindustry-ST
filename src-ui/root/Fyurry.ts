import "./InjectCrashBlocker.ts"
import "./InjectStyle.scss"
import "./InjectReload.ts"

import Package from "@/../package.json"
//

//
import {FsModule} from "@/module/FsModule.ts";
import {AppListInit} from "./AppModule";
import {ConfigModule} from "@/module/config/ConfigModule.ts";
import {I18NModule} from "@/module/i18n/I18NModule.ts";
import {IDModule} from "@/module/id/IDModule.ts";
import {OutModule} from "@/module/OutModule.ts";
import {TransitionModule} from "@/module/TransitionModule.ts";
import {StyleModule} from "@/module/style/StyleModule.ts";
import {reactive} from "vue";
import {IconModule} from "@/module/icon/IconModule.ts";
import {EventGlobal} from "@/module/EventGlobal.ts";
import {KeymapModule} from "@/module/keymap/KeymapModule.ts";
import {PageModule} from "@/page/PageModule.ts";
import { ModalModule } from "@/module/modal/ModalModule.ts";
//[Pipe]============================
//
export * from "./AppModule.ts"
export * from "./Require.ts"
export * from "@/entity/index.ts"
//[PRESET]============================
//
export let app: any = null
export const fs = new FsModule()
export const config = new ConfigModule()
//[META]============================
//
export const event = EventGlobal
export const pack = Package
export const name = Package.name
export const version = Package.version
//[NAME]============================
export const i18n = new I18NModule()
export const icon = new IconModule()
export const id = new IDModule()
export const out = new OutModule()
export const style: StyleModule = <any>reactive(new StyleModule())
export const keymap = new KeymapModule()
export const transition = new TransitionModule()
export const modal: ModalModule = <any>reactive(new ModalModule())
//[APP]
export const page: PageModule = <any>reactive(new PageModule())
//
//[AFTER]============================
//
export const init = (a: any) => {
    app = a
    return AppListInit()
}
const fyurry = new class {
    app = app
    name = name
    version = version
    pack = pack
    fs = fs
    id = id
    style = style
    event = event
    icon = icon
    keymap = keymap
    page = page
    modal = modal
    //
    init = init
}

export default fyurry
//@ts-ignore
//window.fyurry = fyurry
