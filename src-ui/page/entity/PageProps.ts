import {Page} from "@/page/entity/Page.ts";

export type PageProps<T extends Page=Page> = {
    page: T
}