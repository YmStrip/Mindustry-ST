import {Keymap} from "@/module/keymap/entity/Keymap.ts";

export class Keyname {
	key:string
	event() {
		return new KeyboardEvent('keydown',{
			key: this.key
		})
	}
	constructor(key: string|Keyname|Keymap) {
		if (key instanceof Keymap) {
			this.key = key.id.toLocaleLowerCase()
		}
		else if (key instanceof Keyname) {
			this.key = key.key
		}
		else this.key = key.toLocaleLowerCase()
	}
	toString() {
		return this.key
	}
}