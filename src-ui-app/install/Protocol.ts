import {normalize} from "path";
import * as electron from "electron"

export const Protocol = () => {
    electron.protocol.registerFileProtocol('fi', (request, callback) => {
        const url = request.url.substring(5).split('?')[0];
        callback(decodeURI(normalize(url)))
    })
}