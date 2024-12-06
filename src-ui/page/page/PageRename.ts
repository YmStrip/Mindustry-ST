import {Page} from "@/page/entity/Page.ts";
// @ts-ignore
import PageRenameComp from "@/page/page/PageRenameComp.vue";
import {fs, modal} from "fyurry";

export class PageRename extends Page {
    icon = "mdi:rename"
    component = PageRenameComp
    log: string[] = []
    dir: string = ''
    from: string = "xxx.$"
    to: string = 'xxx.$'

    match(a: string, temp: string): boolean | string[] {
        let indexReal = -1
        let indexTemp = -1;
        let isInNumber = false
        const numberCache = []
        const strCache = []
        const data = []
        const put = () => {
            //if (strCache.length) data.push(strCache.join(''))
            if (numberCache.length) data.push(numberCache.join(''))
            //strCache.splice(0, strCache.length)
            numberCache.splice(0, numberCache.length)
        }
        while (indexReal < a.length - 1) {
            indexReal++
            indexTemp++
            if (isInNumber) {
                indexTemp--;
                // @ts-ignore
                if (a[indexReal] >= 0 && a[indexReal] <= 9) {
                    numberCache.push(a[indexReal])
                } else {
                    indexReal--;
                    isInNumber = false;
                    put()
                }
            } else if (temp[indexTemp] == '$') {
                put()
                indexReal--;
                isInNumber = true;
            } else {
                if (temp[indexTemp] != a[indexReal]) return false
                strCache.push(a[indexReal])
            }
        }
        put();
        return data;

    }

    rename(dirname: string, fi: string, matchFrom: string, matchTo: string) {
        if (!fs.isFile(dirname + '/' + fi)) return;
        const m = this.match(fi, matchFrom)
        // console.log(m)
        if (!m) return;
        const res = []
        let resIndex = -1
        for (let i = -1, l = matchTo.length; ++i < l;) {
            if (matchTo[i] == '$') {
                resIndex++
                res.push(m[resIndex])
            } else {
                res.push(matchTo[i])
            }
        }
        const from = dirname + '/' + fi
        const to = dirname + '/' + res.join('')
        fs.move(from, to)
        return (from + " -> " + to)
    }

    renameDir() {
        this.log = []
        if (!fs.isDir(this.dir)) return modal.alert("not found")
        const f = fs.getDir(this.dir)
        for (let i of f) {
            this.log.push(this.rename(this.dir, i, this.from, this.to))
        }
    }

    constructor() {
        super("rename");
    }
}