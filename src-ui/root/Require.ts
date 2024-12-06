export const Require: NodeRequire = require;
export const RequireClear = (path: string) => {
	try {
		const resolvedPath = Require.resolve(path);
		if (require.cache[resolvedPath]) {
			delete require.cache[resolvedPath];
		}
	} catch (e) {
	}
}
// @ts-ignore
//delete window.require
// @ts-ignore
//window.require = null
// @ts-ignore
//delete window.require
