<template>
	<ObserverComp
		v-bind="props"
		v-bind:o="o"
		class="form"
		v-bind:class="{
			solid: display=='solid',
			solidBevel:display=='solid-bevel',
			arrow: display=='arrow',
			bevel: display=='bevel',
			bracket: display=='bracket'
		}"
		v-bind:style="{
			clipPath: display=='solid-bevel' ? (`polygon(${[
				``
			].join(',')})`) : ''
		}"
		tabindex="1"
	>
		<LayoutAbsolute
			v-if="borderLeft"
			left="0"
			top="0"
			pointer="none"
		>
			<slot
				name="border-left"
				v-bind:style="style"
			>
				<BorderSolid
					position="left"
					v-if="isSolidClass(display)"
					v-bind:color="Border"
				/>
				<BorderArrow
					position="left"
					v-else-if="display=='arrow'"
					v-bind:color="Point"
					v-bind:fill="Back"
				/>
				<BorderBracket
					position="left"
					v-else-if="display=='bracket'"
					v-bind:color="Point"
					v-bind:fill="Back"
				/>
				<BorderBevel
					position="left"
					v-else-if="display=='bevel'"
					v-bind:color="Point"
					v-bind:fill="Back"
				/>
			</slot>
		</LayoutAbsolute>
		<LayoutAbsolute
			v-if="borderRight"
			left="0"
			top="0"
			pointer="none"
		>
			<slot
				name="border-right"
				v-bind:style="style"
			>
				<BorderSolid
					position="right"
					v-if="isSolidClass(display)"
					v-bind:color="Border"
				/>
				<BorderArrow
					position="right"
					v-else-if="display=='arrow'"
					v-bind:color="Point"
					v-bind:fill="Back"
				/>
				<BorderBracket
					position="right"
					v-else-if="display=='bracket'"
					v-bind:color="Point"
					v-bind:fill="Back"
				/>
				<BorderBevel
					position="right"
					v-else-if="display=='bevel'"
					v-bind:color="Point"
					v-bind:fill="Back"
				/>
			</slot>
		</LayoutAbsolute>
		<LayoutAbsolute
			v-if="borderTop"
			left="0"
			top="0"
			pointer="none"
		>
			<slot
				name="border-top"
				v-bind:style="style"
			>
				<BorderSolid
					position="top"
					v-if="isSolidClass(display)"
					v-bind:dashed="dashed"
					v-bind:color="Border"
				/>
			</slot>
		</LayoutAbsolute>
		<LayoutAbsolute
			v-if="borderBottom"
			left="0"
			top="0"
			pointer="none"
		>
			<slot
				name="border-bottom"
				v-bind:style="style"
			>
				<BorderSolid
					position="bottom"
					v-if="isSolidClass(display)"
					v-bind:dashed="dashed"
					v-bind:color="Border"
				/>
			</slot>
		</LayoutAbsolute>
		<LayoutAbsolute
			v-if="pointLeftTop"
			left="0"
			top="0"
			pointer="none"
		>
			<slot
				name="point-left-top"
				v-bind:style="style"
			>
				<PointSolid
					position="left-top"
					v-if="display=='solid'"
					v-bind:color="Point"
				/>
				<PointBevel
					position="left-top"
					v-else-if="display=='solid-bevel'"
					v-bind:color="Point"
				/>
			</slot>
		</LayoutAbsolute>
		<LayoutAbsolute
			v-if="pointLeftBottom"
			left="0"
			top="0"
			pointer="none"
		>
			<slot
				name="point-left-bottom"
				v-bind:style="style"
			>
				<PointSolid
					position="left-bottom"
					v-if="display=='solid'"
					v-bind:color="Point"
				/>
				<PointBevel
					position="left-bottom"
					v-else-if="display=='solid-bevel'"
					v-bind:color="Point"
				/>
			</slot>
		</LayoutAbsolute>
		<LayoutAbsolute
			v-if="pointRightTop"
			left="0"
			top="0"
			pointer="none"
		>
			<slot
				name="point-right-top"
				v-bind:style="style"
			>
				<PointSolid
					position="right-top"
					v-if="display=='solid'"
					v-bind:color="Point"
				/>
				<PointBevel
					position="right-top"
					v-else-if="display=='solid-bevel'"
					v-bind:color="Point"
				/>
			</slot>
		</LayoutAbsolute>
		<LayoutAbsolute
			v-if="pointRightBottom"
			left="0"
			top="0"
			pointer="none"
		>
			<slot
				name="point-right-bottom"
				v-bind:style="style"
			>
				<PointSolid
					position="right-bottom"
					v-if="display=='solid'"
					v-bind:color="Point"
				/>
				<PointBevel
					position="right-bottom"
					v-else-if="display=='solid-bevel'"
					v-bind:color="Point"
				/>
			</slot>
		</LayoutAbsolute>
		<slot
			v-bind:style="style"
		/>
	</ObserverComp>
</template>
<script lang="ts" setup>
import {ObserverFormProps} from "@/components/observer/ObserverFormProps.ts";
import {Observer} from "@/components/observer/Observer.ts";
import ObserverComp from "@/components/observer/ObserverComp.vue";
import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";
import {computed} from "vue";
import {Color} from "@/entity";
import BorderSolid from "@/components/border/BorderSolid.vue";
import {ObserverFormStyle} from "@/components/observer/ObserverFormStyle.ts";
import PointSolid from "@/components/point/PointSolid.vue";
import BorderArrow from "@/components/border/BorderArrow.vue";
import BorderBracket from "@/components/border/BorderBracket.vue";
import BorderBevel from "@/components/border/BorderBevel.vue";
import PointBevel from "@/components/point/PointBevel.vue";
import fyurry from "fyurry";

const props = withDefaults(defineProps<ObserverFormProps & {
	o: Observer
}>(), {
	display: 'solid',
	borderTop: true,
	borderBottom: true,
	borderLeft: true,
	borderRight: true,
	pointLeftTop: true,
	pointLeftBottom: true,
	pointRightTop: true,
	pointRightBottom: true,
	height: 22,
	aBorder: .6,
	aBorderHover: .7,
	aBorderActive: .8,
	aBack: .05,
	aBackHover: .15,
	aBackActive: .25
})
const isSolidClass = (t: any) => {
	return t == 'solid' || t == 'solid-bevel'
}
const Back = computed(() => fyurry.style.color({
	name: props.name,
	hover: props.o.hover,
	active: props.o.active,
	color: props.color,
	aActive: props.aBackActive,
	aHover: props.aBackHover,
	a: props.aBack
}))
const Border = computed(() =>  fyurry.style.color({
	name: props.name,
	hover: props.o.hover,
	active: props.o.active,
	color: props.color,
	aActive: props.aBorderActive,
	aHover: props.aBorderHover,
	a: props.aBorder,
	field: 'border'
}))
const Point = computed(() => {
	const c = new Color(Border.value)
	return c.alpha(c.a * 1.5).hex()
})
const style = computed(() => {
	return <ObserverFormStyle>{
		back: Back.value,
		point: Point.value,
		border: Border.value
	}
})
</script>
<style lang="scss" scoped>
.form {
	height: v-bind("height+'px'");
	pointer-events: all;
	background: v-bind(Back);
	outline: none;
	
	&.arrow {
		margin: 0 11px;
	}
	
	&.bracket, &.bevel {
		margin: 0 9px;
	}
}
</style>