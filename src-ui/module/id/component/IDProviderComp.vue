<template>
	<LayoutFlex v-if="idp" class="id-provider">
		<Button v-bind:mirror="mirror?idp.id==id:idp.id!=id" style="height: 100%">
			<Icon v-bind:icon="idp.get()?.icon||idp.icon"></Icon>
		</Button>
		<Input
				v-bind:mirror="mirror"
				v-bind:radius="false"
				style="flex: 1;height: 100%"
				v-bind:class="{current:idp.id==id}"
				v-model:value="idp.id"
				v-bind:mode="mode=='double'?'double':'single'"
				@click="e=>$emit('click',e)"
				@confirm="v=>idp.rename(v)"
		/>
		<Button
				v-if="showUser"
				style="height: 100%"
				mirror
		>
			{{ Object.keys(idp.user || {}).length }}
		</Button>
		<Button
				v-if="showFake"
				style="height: 100%"
				v-bind:active="idp.fake"
				@click="e=>emits('clickFake',e)"
		>
			<Icon icon="mdi:shield-outline"></Icon>
		</Button>
		<Button
				v-if="showDelete"
				style="height: 100%"
				mirror
				@click.stop="e=>emits('clickDelete',e)">
			<Icon icon="mdi:close"></Icon>
		</Button>
	</LayoutFlex>
</template>
<script lang="ts" setup>
import Button from "@/components/button/Button.vue";
import Input from "@/components/Input.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import Tooltip from "@/components/Tooltip.vue";
import Icon from "@/module/icon/component/Icon.vue";
import {IDProvider} from "@/module/id/entity/IDProvider.ts";

const props = withDefaults(defineProps<{
	idp: IDProvider
	id?: string
	mode?: 'double' | 'single'
	mirror?: boolean
	showUser?: boolean
	showFake?: boolean
	showDelete?: boolean
}>(), {
	mode: "double"
})
const emits = defineEmits<{
	(event: 'click', e: MouseEvent)
	(event: 'clickFake', e: MouseEvent)
	(event: 'clickDelete', e: MouseEvent)
}>()
</script>
<style lang="scss" scoped>
.id-provider {
	margin: 1px 0;
	height: 22px;
	justify-content: flex-start;

	& > .current {
		border: 2px solid;
	}
}

</style>