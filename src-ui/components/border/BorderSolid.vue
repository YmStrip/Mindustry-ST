<template>
	<div
		class="border"
		v-bind:class="{
			[position]:true,
			dashed
		}"
	/>
</template>
<script lang="ts" setup>
import {BorderProps} from "@/components/border/BorderProps.ts";
import {computed} from "vue";
import {Color} from "@/entity";

const sizeWidth = computed(() => `calc(100% - ${2 * props.padding}px)`)
const sizePadding = computed(() => props.padding + 'px')
const sizeSize = computed(() => props.size + 'px')


const props = withDefaults(defineProps<BorderProps>(), {
	color: 'white',
	size: .5,
	padding: 4,
	offset: 0
})

const Dark = computed(()=>{
	const c = new Color(props.color)
	return c.alpha(c.a*.75)
})

</script>
<style lang="scss" scoped>
.border {
	pointer-events: none;
	position: absolute;
	background: none;
	border: none;
	
	&.top {
		top: 0;
		left: v-bind(sizePadding);
		width: v-bind(sizeWidth);
		height: v-bind(sizeSize);
		border-top: v-bind(sizeSize) solid v-bind(Dark);
		
		&.dashed {
			border-top-style: dashed;
		}
	}
	
	&.bottom {
		bottom: 0;
		left: v-bind(sizePadding);
		width: v-bind(sizeWidth);
		height: v-bind(sizeSize);
		border-bottom: v-bind(sizeSize) solid v-bind(Dark);
		
		&.dashed {
			border-bottom-style: dashed;
		}
	}
	
	&.left {
		left: 0;
		top: v-bind(sizePadding);
		height: v-bind(sizeWidth);
		width: v-bind(sizeSize);
		border-left: v-bind(sizeSize) solid v-bind(Dark);
		
		&.dashed {
			border-left-style: dashed;
		}
	}
	
	&.right {
		right: 0;
		top: v-bind(sizePadding);
		height: v-bind(sizeWidth);
		width: v-bind(sizeSize);
		border-right: v-bind(sizeSize) solid v-bind(Dark);
		
		&.dashed {
			border-right-style: dashed;
		}
	}
}
</style>