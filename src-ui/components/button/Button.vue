<template>
	<ObserverForm
		v-bind="props"
		v-bind:o="observer"
		class="button-container"
		name="button"
	>
		<template
			v-if="$slots['border-left']"
			#border-left="{style}"
		>
			<slot
				name="border-left"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['border-right']"
			#border-right="{style}"
		>
			<slot
				name="border-right"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['border-top']"
			#border-top="{style}"
		>
			<slot
				name="border-top"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['border-bottom']"
			#border-bottom="{style}"
		>
			<slot
				name="border-bottom"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['point-left-top']"
			#point-left-top="{style}"
		>
			<slot
				name="point-left-top"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['point-left-bottom']"
			#point-left-bottom="{style}"
		>
			<slot
				name="point-left-bottom"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['point-right-top']"
			#point-right-top="{style}"
		>
			<slot
				name="point-right-top"
				v-bind:style="style"
			/>
		</template>
		<template
			v-if="$slots['point-right-bottom']"
			#point-right-bottom="{style}"
		>
			<slot
				name="point-right-bottom"
				v-bind:style="style"
			/>
		</template>
		<template #default="{style}">
			<div class="button-body">
				<slot v-bind:style="style"/>
			</div>
		</template>
	</ObserverForm>
</template>
<script lang="ts" setup>
import {ButtonProps} from "@/components/button/ButtonProps.ts";
import {reactive} from "vue";
import {style} from "fyurry";
import {Observer} from "@/components/observer/Observer.ts";
import ObserverForm from "@/components/observer/ObserverForm.vue";

const props = withDefaults(defineProps<ButtonProps>(), {
	alignItems: 'center',
	justifyContent: 'center',
	//
	borderTop: true,
	borderBottom: true,
	borderLeft: true,
	borderRight: true,
	pointLeftTop: true,
	pointLeftBottom: true,
	pointRightTop: true,
	pointRightBottom: true,
})


const observer = reactive(new Observer())

</script>
<style lang="scss" scoped>
.button-container {
	pointer-events: all;
	margin: 1px;
	display: inline-block;
	font-size: 14px;
}

.button-body {
	justify-content: v-bind(justifyContent);
	align-items: v-bind(alignItems);
	height: 100%;
	width: 100%;
	display: inline-flex;
	padding: 0 5px;
	position: relative;
}


</style>