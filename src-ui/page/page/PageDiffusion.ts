import {Page} from "@/page/entity/Page.ts";
// @ts-ignore
import PageDiffusionComp from "@/page/page/PageDiffusionComp.vue";

export class PageDiffusion extends Page {
    icon = "devicon:pytorch"
    component = PageDiffusionComp
    seed = 1024
    batch = 1
    eval(code:string) {

    }
    constructor() {
        super("diffusion");
    }
}