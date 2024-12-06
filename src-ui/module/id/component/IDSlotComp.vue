<template>
	<LayoutFlex
			v-if="ids"
			class="id-slot"
			v-bind:style="{
				background: Yurry.style?.style['button.backColor']
			}"
	>
		<Popover
				trigger="click"
				v-model:show="show"
				v-bind:placement="placement"
				v-bind:style="{
						width: '250px',
						height: '300px',
						...(popoverStyle||{})
					}"
		>
			<template #trigger>
				<Button style="height: 100%" mirror>
					<Icon v-bind:icon="iconSlot"/>
					<Arrow value/>
				</Button>
			</template>
			<template #default>
				<slot name="popover"></slot>
			</template>
		</Popover>
		<template v-if="!ids.typeCount(ids.bus.update)">
			<slot name="add" v-bind:empty="true" v-if="$slots.add"></slot>
			<Button
					v-else
					@click="e=>emits('clickCreate',e)"
					mirror
					style="margin-left: 1px;flex: 1;width: 100px"
			>
				<Icon icon="mdi:add"></Icon>
			</Button>
		</template>
		<template v-else>
			<LayoutFlex v-if="target" v-bind:update="ids.update" style="width: 100%;height: 22px">
				<Tooltip v-bind:text="tooltipInput" placement="bottom-start">
					<Input
							v-bind:radius="false"
							style="height: 22px;flex: 1;margin: 0 1px;padding-left: 5px"
							v-model:value="target.id"
							mode="single"
							@confirm="vs=>target.rename(vs)"
							@click="al()"
					></Input>
				</Tooltip>
				<Tooltip v-bind:text="tooltipUser" v-if="showUser&&ids">
					<Button style="height: 100%" mirror>
						{{ target.userCount() }}
					</Button>
				</Tooltip>
				<Tooltip v-bind:text="tooltipFake" v-if="showFake">
					<Button
							style="height: 100%"
							v-bind:active="target.fake"
							@click="()=>{
								target.fake = !target.fake
							}"
					>
						<Icon icon="mdi:shield-outline"></Icon>
					</Button>
				</Tooltip>
				<slot v-if="$slots.add" name="add"/>
				<Tooltip v-bind:text="tooltipAdd" v-else-if="showAdd">
					<Button style="height: 100%" mirror @click="e=>emits('clickAdd',e)">
						<Icon icon="mdi:content-copy"></Icon>
					</Button>
				</Tooltip>
				<Tooltip v-bind:text="tooltipDelete" v-if="showDelete">
					<Button
							style="height: 100%"
							mirror
							@click="async e=>{
								if(!e.shiftKey) return ids.delID()
								if (!await Yurry.modal.confirm('sure?')) return
								target.del()
							}"
					>
						<Icon icon="mdi:close"></Icon>
					</Button>
				</Tooltip>
			</LayoutFlex>
		</template>
	</LayoutFlex>
	<div>
	</div>
</template>
<script lang="ts" setup>
import Button from "@/components/button/Button.vue";
import Input from "@/components/Input.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import Popover from "@/components/Popover.vue";
import Tooltip from "@/components/Tooltip.vue";
import {EventEmitter} from "@/entity/EventEmitter.ts";
import Icon from "@/module/icon/component/Icon.vue";
import Arrow from "@/module/icon/component/IconArrow.vue";
import {IDProvider} from "@/module/id/entity/IDProvider.ts";
import {IDSlot} from "@/module/id/entity/IDSlot.ts";
import {Yurry} from "@/root/Application.ts";
import {onMounted, onUnmounted, ref, watch} from "vue";

const props = withDefaults(defineProps<{
	ids: IDSlot
	iconSlot?: string
	tooltipSlot?: string
	tooltipInput?: string
	tooltipFake?: string
	tooltipAdd?: string
	tooltipDelete?: string
	tooltipUser?: string
	showFake?: boolean
	showAdd?: boolean
	showDelete?: boolean
	showUser?: boolean
	placement?: string
	popoverStyle?: any
	mirror?: boolean
}>(), {
	iconSlot: 'mdi:data-usage',
	tooltipSlot: () => Yurry.i18n.t(<any>'ID Slot'),
	tooltipInput: () => Yurry.i18n.t(<any>'ID Slot'),
	tooltipFake: () => Yurry.i18n.t(<any>'fake user'),
	tooltipAdd: () => Yurry.i18n.t(<any>'add user'),
	tooltipDelete: () => Yurry.i18n.t(<any>'unlink (shift unlink and delete)'),
	tooltipUser: () => Yurry.i18n.t(<any>'user count'),
	showFake: true,
	showAdd: true,
	showDelete: true,
	showUser: true,
	placement: 'bottom-end'
})
const target = ref<IDProvider>()
const al = () => alert('')
const name = defineModel<string>('name', {default: ''})
const emits = defineEmits<{
	(event: 'clickAdd', e: MouseEvent)
	(event: 'clickCreate', e: MouseEvent)
}>()
const show = defineModel<boolean>('show', {default: false})
const setTarget = () => {
	target.value = props.ids.getID()
}
const hotBus = EventEmitter.hotBus(onMounted, onUnmounted).listen(() => {
	props.ids.event.onMounted('update', () => {
		setTarget()
	}, hotBus)
	setTarget()
})
watch(() => props.ids, () => hotBus.dynamic())
</script>
<style lang="scss" scoped>
.id-slot {
	height: 22px;
	width: 100%;
}
</style>