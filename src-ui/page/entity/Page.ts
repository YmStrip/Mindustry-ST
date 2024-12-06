import {Register} from "@/entity";
import {page} from "fyurry";

export class Page extends Register {
    component: any

    Provide() {
        page.page[this.id] = this;
        return this
    }

    constructor(public id: string) {
        super();
    }
}