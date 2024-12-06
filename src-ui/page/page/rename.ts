//@ts-nocheck

const api = new class {
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

    isFile(fi: string) {
        try {
            return require("fs").statSync(fi).isFile()
        } catch (e) {
            return false
        }
    }

    moveFile(fi: string, to: string) {
        try {
            return require("fs").renameSync(fi, to) ? true : true
        } catch (e) {
            return false
        }
    }

    rename(dirname: string, fi: string, matchFrom: string, matchTo: string[]) {
        if (!this.isFile(dirname + '/' + fi)) return;
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
        const from = dirname+'/'+fi
        const to = dirname+'/'+res.join('')
        this.moveFile(from,to)
        console.log(from+" -> "+to)
    }

    renameDir(dirname: string, match: string, matchTo: string[]) {
        const f = require("fs").readdirSync(dirname)
        for (let i of f) {
            this.rename(dirname, i, match, matchTo)
        }
    }
}

api.renameDir(
    "C:\\Users\\YmStrip\\Desktop\\mdt\\st\\assets\\sprites\\unit\\zeta\\fire",
    "UnitZeta$.png",
    "UnitZeta-fire$.png"
)










