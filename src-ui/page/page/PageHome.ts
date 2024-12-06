import {Page} from "@/page/entity/Page.ts";
// @ts-ignore
import PageHomeComp from "@/page/page/PageHomeComp.vue";

export class PageHome extends Page {
    component = PageHomeComp
    constructor() {
        super("home");
        this.Icon("mdi:home")
    }
}