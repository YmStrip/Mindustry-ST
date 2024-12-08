// vite.electron.config.ts
import path from "node:path";
import { resolve } from "path";
import { defineConfig, externalizeDepsPlugin } from "electron-vite";
import vue from "@vitejs/plugin-vue";
var __electron_vite_injected_dirname = "C:\\Users\\YmStrip\\Desktop\\mdt\\st";
var vite_electron_config_default = defineConfig({
  main: {
    server: {
      hmr: false
    },
    plugins: [externalizeDepsPlugin()],
    build: {
      rollupOptions: {
        input: {
          index: resolve(__electron_vite_injected_dirname, "src-ui-app/index.ts")
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
          index: resolve(__electron_vite_injected_dirname, "src-ui-app/preload.ts")
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
      ]
    },
    assetsInclude: [
      "**/*.glb"
    ],
    resolve: {
      alias: {
        "@": path.resolve(__electron_vite_injected_dirname, "src-ui"),
        "fyurry": resolve(__electron_vite_injected_dirname, "src-ui/root/Fyurry.ts")
      }
    },
    plugins: [vue()],
    root: ".",
    build: {
      rollupOptions: {
        input: {
          index: resolve(__electron_vite_injected_dirname, "index.html")
        },
        external: ["axios"]
      }
    }
  }
});
export {
  vite_electron_config_default as default
};
