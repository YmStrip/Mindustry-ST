import path from "node:path";
import {resolve} from 'path'
import {defineConfig, externalizeDepsPlugin} from 'electron-vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
	main: {
		server: {
			hmr: false
		},
		plugins: [externalizeDepsPlugin()],
		build: {
			rollupOptions: {
				input: {
					index: resolve(__dirname, 'src-ui-app/index.ts')
				}
			}
		}
	},
	preload: {
		server: {
			hmr: false
		},
		plugins: [externalizeDepsPlugin()],
		build: {
			rollupOptions: {
				input: {
					index: resolve(__dirname, 'src-ui-app/preload.ts')
				}
			}
		}
	},
	renderer: {
		server: {
			hmr: false
		},
		optimizeDeps: {
			exclude: [
				"snapshot/*",
				"snapshot-dev/*",
				"saves/*",
				"mods/*",
				"config/*"
			],
		},
		assetsInclude: [
			"**/*.glb"
		],
		resolve: {
			alias: {
				'@': path.resolve(__dirname, 'src-ui'),
				'fyurry': resolve(__dirname,'src-ui/root/Fyurry.ts')
			}
		},
		plugins: [vue()],
		root: '.',
		build: {
			rollupOptions: {
				input: {
					index: resolve(__dirname, 'index.html')
				},
				external: ['axios']
			}
		}
	}
})