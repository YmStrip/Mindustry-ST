<template>
	<Collapse
			v-for="(p,i) in id.dataSort.data"
			v-bind:icon="p.data.icon"
			v-bind:title="p.data.label||p.data.id"
			v-bind:padding="true"
			v-bind:show="false"
	>
		<IDProviderComp
				v-for="d in p.data.idp"
				v-bind:idp="d"
				v-bind:mode="'single'"
				show-fake
				show-user
				show-delete
				@click-fake="d.fake=!d.fake"
				@click-delete="async ()=>{
					if (!await Yurry.modal.confirm('[Danger] sure?')) return
					d.del()
				}"
		/>
	</Collapse>
</template>
<script lang="ts" setup>
import Collapse from "@/components/Collapse.vue";
import IDProviderComp from "@/module/id/component/IDProviderComp.vue";
import {IDBus} from "@/module/id/entity/IDBus.ts";
import {Yurry} from "@/root/Application.ts";

const props = defineProps<{
	id: IDBus
}>()
</script>