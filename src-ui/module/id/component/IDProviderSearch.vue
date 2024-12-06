<template>
	<LayoutFlex
			column
			class="id-group-search"
			v-if="idg"
	>
		<Scroll style="flex: 1;width: 100%">
			<template v-for="(d,i) in idg.idp">
				<IDProviderComp
						v-if="((i+'').toLowerCase()).includes(api.searchDump)&&filter(d)"
						v-bind:mode="mode"
						v-bind:idp="d"
						v-bind:id="id"
						v-bind:mirror="!mirror"
						@click="emits('target',i)"
				/>
			</template>
		</Scroll>
		<LayoutFlex style="height: 22px;width: 100%">
			<Input
					v-bind:radius="false"
					style="height: 100%;flex: 1"
					auto-focus
					auto-select
					v-bind:mirror="!mirror"
					v-model:value="search"
			/>
			<Button>
				<Icon icon="mdi:search"></Icon>
			</Button>
		</LayoutFlex>
	</LayoutFlex>
</template>
<script lang="ts" setup>
import Button from "@/components/button/Button.vue";
import Input from "@/components/Input.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import LayoutScroll from "@/components/layout/LayoutScroll.vue";
import Scroll from "@/components/Scroll.vue";
import Icon from "@/module/icon/component/Icon.vue";
import IDProviderComp from "@/module/id/component/IDProviderComp.vue";
import {IDGroup} from "@/module/id/entity/IDGroup.ts";
import {IDProvider} from "@/module/id/entity/IDProvider.ts";
import {reactive, watch} from "vue";

const props = withDefaults(defineProps<{
	id?: string
	idg: IDGroup
	mode?: 'single' | 'double'
	dt?: number
	mirror?: boolean
	filter?: (idp: IDProvider) => boolean
}>(), {
	mode: 'double',
	dt: 250,
	filter: (idp) => true
})
const search = defineModel<string>('search', {default: ''})
const api = reactive(new class {
	searchDump = search.value
	searchI = 0
	handleInput() {
		const v = ++this.searchI
		setTimeout(() => {
			if (v != this.searchI) return
			this.searchDump = search.value
		}, props.dt)
	}
})
const emits = defineEmits<{
	(event: 'target', target: string)
}>()
watch(search, v => api.handleInput())
</script>
<style lang="scss" scoped>
.id-group-search {
	height: 100%;
	width: 100%;
	position: absolute;
}
</style>