<template>
	<div class="image-container">
		<template v-if="!src">
			<Icon
					icon="mdi:image-filter-hdr-outline"
					v-bind:width="iconSize"
					v-bind:height="iconSize"
			/>
		</template>
		<template v-else-if="fyurry.icon.include(src+'')">
			<Icon
					v-bind:icon="src"
					v-bind:width="iconSize"
					v-bind:height="iconSize"
			/>
		</template>
		<template v-else>
			<img
					alt=""
					ref="img"
					v-bind:src="src"
					v-bind:class="['img',{
						full,
						cover,
						width:api.fullType=='width',
						height:api.fullType=='height'
					}]"
			/>
		</template>
		<LayoutAbsolute
				pointer="none"
				width="auto"
				height="auto"
				left="0"
				top="0"
				v-if="desc&&typeof desc==='string'"
				style="font-size: 6px;line-height: 6px;padding: 1px;background: black;color: white;font-weight: lighter"
		>
			{{ fyurry.out.descName(desc) }}
		</LayoutAbsolute>
	</div>
</template>
<script lang="ts" setup>
import Icon from "@/module/icon/component/Icon.vue"
import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";
import {onMounted, onUnmounted, reactive, ref, watch} from "vue";
import fyurry from "fyurry";

const props = withDefaults(defineProps<{
	src?: string;
	width?: any
	height?: any
	full?: boolean
	update?: number
	cover?: boolean
	iconSize?: any
	desc?: any
	scale?: number
}>(), {
	src: '',
	iconSize: '10px',
})
const img = ref<HTMLImageElement>(<any>null)
const api = reactive(new class {
	scale = 1
	fullType: "width" | 'height' = 'width'
	cover() {
		if (!props.cover) return
		if (!img.value) return null
		if (!img.value.parentElement) return
		const pl = img.value.parentElement
		const el = img.value
		const scaleWidth = pl.clientWidth / el.offsetWidth
		const scaleHeight = pl.clientHeight / el.offsetHeight
		//console.log(scaleWidth, scaleHeight)
		this.scale = 1 / Math.min(scaleHeight, scaleWidth)
	}
	full() {
		if (!img.value) return null
		const el = img.value
		if (el.offsetWidth >= el.offsetHeight) {
			this.fullType = 'width'
		}
		else {
			this.fullType = 'height'
		}
	}
	resize() {
		setTimeout(() => {
			this.cover()
			this.full()
		}, 50)
	}
})
onMounted(() => {
	api.resize()
})
watch(() => props.full, () => {
	api.resize()
})
watch(() => props.src, () => {
	api.resize()
})
watch(() => props.update, () => {
	api.resize()
})
fyurry.event.onMounted('resize', () => api.resize(), onUnmounted)
</script>
<style lang="scss" scoped>
.image-container {
	/*border: 2px solid; debug*/
	overflow: hidden;
	position: relative;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	width: v-bind(width);
	height: v-bind(height);
}

.img {
	transform: scale(v-bind('scale'));
	width: 100%;
	height: 100%;
	transition-duration: 0.25s;

	&.cover {
		width: auto;
		height: auto;
		transform: scale(v-bind('api.scale'));

	}

	&.full {
		width: auto;
		height: auto;
	}

	&.full, &.cover {
		&.width {
			width: 100%
		}

		&.height {
			height: 100%;
		}
	}
}
</style>