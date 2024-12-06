import {AppModule} from "fyurry";
import {KeyContainer} from "@/module/key/entity/KeyContainer.ts";
import {PageHome} from "@/page/page/PageHome.ts";
import {Page} from "@/page/entity/Page.ts";
import {PageRename} from "@/page/page/PageRename.ts";

export class PageModule extends AppModule {
    pageCurrent = "home";
    page:Record<string, Page> = {}
    pageKey = new KeyContainer()

    async init(): Promise<void> {
        new PageHome().Provide()
        new PageRename().Provide()
    }
}