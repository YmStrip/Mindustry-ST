<template>
	<LayoutAbsolute left="0" top="0" width="100%" height="100%">
		<Image
				style="z-index: 1;width: 100%"
				v-bind:src="style.backgroundList[style.Item('background')]"
		/>
		<LayoutAbsolute
				left="0"
				top="0"
				width="100%"
				height="100%"
				z-index="2"
				v-bind:style="{
					background: style.Item('background.shadow')
				}"
		>
			<slot/>
		</LayoutAbsolute>
	</LayoutAbsolute>
</template>
<script lang="ts" setup>
import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";
import Image from "@/module/icon/component/Image.vue";
import fyurry, {style} from "fyurry";
const Theme = fyurry.style.theme
</script>
<style lang="scss" scoped>
::v-deep(*) {
	font-family: v-bind('Theme.Item("font")');
	color:  v-bind('Theme.Item("text")');
	text-shadow:  v-bind('Theme.Item("text.shadow")');
}

::v-deep(.splitpanes__splitter) {
	background-color: rgba(0, 0, 0, 0);
	position: relative;
	z-index: 50;
	
	&:before {
		content: '';
		position: absolute;
		left: 0;
		top: 0;
		transition: opacity 0.2s;
		background-color: v-bind(SplitColor);
		opacity: 0;
		z-index: 1;
	}
	
	&:hover:before {
		opacity: 1;
	}
}

::v-deep(.splitpanes--vertical) > .splitpanes__splitter:before {
	left: -4px;
	right: -4px;
	height: 100%;
}

::v-deep(.splitpanes--horizontal ) > .splitpanes__splitter:before {
	top: -4px;
	bottom: -4px;
	width: 100%;
}
</style>