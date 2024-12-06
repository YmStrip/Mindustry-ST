import {Require} from "@/root/Require.ts";
import {out} from "fyurry";
import * as FsType from "node:fs"
import {CopyOptionsSync, MoveOptions} from "fs-extra";
import {AppModule} from "../root/AppModule.ts";

const FsApi = Require('fs')
const FsE = Require('fs-extra')
export class FsModule extends AppModule {
	fs = FsApi
	fsE = FsE
	chokidar = Require('chokidar')
	cwd = Require('process').cwd()
	filename(path: string): any {
		return this.src(path).split('/').pop() || ''
	}
	dirname(path: string) {
		return this.src(path).split('/').slice(0, -1).join('/') || ''
	}
	removeName(path: string, rm: string) {
		return this.path(this.path(path).replace(this.path(rm), ''))
	}
	//child or self
	isChildName(path: string, parent: string) {
		return this.path(path).startsWith(this.path(parent))
	}
	fileAndDir(path: string) {
		path = this.src(path)
		const list = path.split('/')
		return {
			path,
			filename: list[list.length - 1] || '',
			dirname: list.slice(0, -1).join('/') || ''
		}
	}
	path(path: string = '') {
		let src = this.src(path)
		while (true) {
			if (!src.length) break
			if (src[0] == '/') src = src.substring(1)
			else break
		}
		while (true) {
			if (!src.length) break
			if (src[src.length - 1] == '/') src = src.substring(0, src.length - 2)
			else break
		}
		return src
	}
	src(path: string = '') {
		return path.replace(/(\\)/g, '/').replace(/(\.\.)/g, '.')
	}
	stats(path: string): FsType.Stats {
		try {
			return FsApi.statSync(path)
		} catch (e) {
			return null
		}
	}
	isDir(path: string): boolean {
		try {
			return FsApi.statSync(path).isDirectory();
		} catch (e) {
			return false
		}
	}
	setDir(path: string) {
		try {
			FsApi.mkdirSync(path, {
				recursive: true
			});
			return true
		} catch (e) {
			return false
		}
	}
	//deep
	delPath(path: string) {
		try {
			if (this.isFile(path)) return this.delFile(path)
			if (this.isDir(path)) {
				FsE.emptyDirSync(path)
				return this.delDir(path)
			}
		} catch (e) {
			console.log(e)
			console.log('error:', e)
		}
	}
	//Not Deep
	delDir(path: string): boolean {
		console.log('[warn] del dir ', path)
		try {
			FsApi.rmdirSync(path)
			return true
		} catch (e) {
			return false
		}
	}
	getDir(path: string) {
		try {
			return FsApi.readdirSync(path)
		} catch (e) {
			return []
		}
	}
	isEmptyDir(path: string) {
		return this.isDir(path) && !this.getDir(path).length
	}
	isFile(path: string): boolean {
		try {
			return FsApi.statSync(path).isFile()
		} catch (e) {
			return false
		}
	}
	isPath(path: string): boolean {
		return this.isDir(path) || this.isFile(path)
	}
	setFile(file: FsType.PathOrFileDescriptor, data: string | NodeJS.ArrayBufferView, options?: FsType.WriteFileOptions | undefined) {
		try {
			FsApi.writeFileSync(file, data, options)
			return true
		} catch (e) {
			return false
		}
	}
	getFile(path: FsType.PathOrFileDescriptor, options?: {
		encoding?: null | undefined,
		flag?: string | undefined
	} | null | undefined) {
		try {
			return FsApi.readFileSync(path, options)
		} catch (e) {
			return null
		}
	}
	getJson(path: string) {
		try {
			return JSON.parse(this.getFile(path) + '') || null
		} catch (e) {
			return null
		}
	}
	setJson(path: string, json: any, tab = true) {
		try {
			return this.setFile(path, tab ? JSON.stringify(json, null, '\t') : JSON.stringify(json))
		} catch (e) {
			out.error(e)
			//console.log('circle:', Util.getCircular(json))
			return false
		}
	}
	delFile(path: string): boolean {
		try {
			FsApi.unlinkSync(path)
			return true
		} catch (e) {
			return false
		}
	}
	rename(a: string, b: string): Error | true {
		try {
			FsE.renameSync(a, b)
			return true
		} catch (e: any) {
			return e
		}
	}
	copy(a: string, b: string, opt?: CopyOptionsSync): Error | true {
		try {
			FsE.copySync(a, b, opt)
			return true
		} catch (e: any) {
			return e
		}
	}
	move(a: string, b: string, opt?: MoveOptions): Error | true {
		try {
			FsE.moveSync(a, b, opt)
			return true
		} catch (e: any) {
			return e
		}
	}
	//replace readdirp , very fatal bugs, for await ,break program
	async rec(fi: string, call: (f: string, type: "file" | 'dir') => any, dx = '', dep = 0) {
		if (dep == 0) {
			if (this.isFile(fi)) {
				await call(this.filename(fi), 'file')
				return
			} else if (this.isDir(fi)) {
				const dirs = this.getDir(fi)
				for (let i in dirs) {
					await this.rec(fi + '/' + dirs[i], call, dirs[i], 1)
				}
			}
		} else {
			if (this.isFile(fi)) {
				await call(dx, 'file')
				return
			} else if (this.isDir(fi)) {
				const dirs = this.getDir(fi)
				for (let i in dirs) {
					await this.rec(fi + '/' + dirs[i], call, dx + '/' + dirs[i], dep + 1)
				}
				await call(dx, 'dir')
			}
		}
	}
	async zip(buffer: Buffer) {
		//return compressSync(buffer)
	}
	async zipJson(byte: Record<string, any>) {
		return this.zip(Buffer.from(JSON.stringify(byte)))
	}
	//error?
	async unZip(buffer: Buffer) {
		//return decompressSync(buffer)
	}
	async unZipJson(byte: Buffer) {
		return JSON.parse('' + <any>await this.unZip(byte))
	}
}