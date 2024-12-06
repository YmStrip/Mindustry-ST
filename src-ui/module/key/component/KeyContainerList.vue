<template>

	<LayoutFlex
			column
			justify-content="flex-start"
			align-items="flex-start"
			style="height: 150px;width: 100%;pointer-events: all"
	>
		<Input
				style="width: 100%;height: 22px"
				auto-focus
				v-model:value="value"
				@update:value="v => search.diffAsync(v)"
		/>
		<Scroll style="flex: 1;width: 100%;">
			<div
					v-for="d in search.data"
					style="width: 100%"
			>
				<KeyComp
						v-for="d1 in d"
						v-bind:item="d1"
						@click="onAdd(d1.id)"
						@click-obj="id => {
							value=id+'.';
							search.diffAsync(value)
						}"
				/>
			</div>
		</Scroll>
	</LayoutFlex>
</template>
<script lang="ts" setup>
import Input from "@/components/Input.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import Scroll from "@/components/Scroll.vue";
import {EventEmitter} from "@/entity/EventEmitter.ts";
import KeyComp from "@/module/key/component/KeyComp.vue";
import {KeyContainer} from "@/module/key/entity/KeyContainer.ts";
import {KeySearch} from "@/module/key/entity/KeySearch.ts";
import {onMounted, onUnmounted, reactive, ref, watch} from "vue";

const props = defineProps<{
	key: KeyContainer
	onAdd?: (id: string) => any
}>()
const value = defineModel<string>('value')
const search = ref<KeySearch>(null)
const hotBus = EventEmitter.hotBus(onMounted, onUnmounted).listen(() => {
	search.value = props.key.search(value.value)
})
watch(() => props.key, () => hotBus.dynamic())

</script>