import {AppModule} from "fyurry";
import {KeyContainer} from "@/module/key/entity/KeyContainer.ts";
import {PageHome} from "@/page/page/PageHome.ts";
import {Page} from "@/page/entity/Page.ts";
import {PageRename} from "@/page/page/PageRename.ts";
import {PageDiffusion} from "@/page/page/PageDiffusion.ts";

export class PageModule extends AppModule {
    pageCurrent = "home";
    page: Record<string, Page> = {}
    pageKey = new KeyContainer()

    async init(): Promise<void> {
        new PageHome().Provide()
        new PageRename().Provide()
        new PageDiffusion().Provide()
    }

    async deploy(): Promise<void> {

        const test = (key: number) => {
            const data = [0, 1]
            let left = 0;
            let right = data.length - 1;
            while (left <= right) {
                const mid = Math.floor(((left + right) / 2));
                const item = data[mid]
                if (item == key) {
                    return [item]
                }
                if (item < key) {
                    left = mid + 1;
                } else if (item > key) {
                    right = mid - 1;
                }
            }
            return [left, right]
        }
       /* for (let i = 0, l = 2; i < l; i += .1) {
            console.log(i, test(i))
        }*/
    }
}