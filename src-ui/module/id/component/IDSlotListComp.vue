<template>
	<LayoutFlex class="id-slot-list" align-items="flex-start" justify-content="flex-start">
		<LayoutScroll style="flex: 1"></LayoutScroll>
		<LayoutFlex column align-items="center" justify-content="flex-start" style="width: 22px">
			<Button mirror>
				<Icon icon="ic:baseline-add"></Icon>
			</Button>
			<Button mirror>
				<Icon icon="ic:baseline-remove"></Icon>
			</Button>
			<div style="margin: 2px">
				<Popover
						v-if="showOption"
						placement="left-start"
						v-bind:style="{
							width: '150px',
							height: '200px',
							...(styleOption||{})
						}"
				>
					<template #trigger>
						<Button mirror>
							<Arrow value></Arrow>
						</Button>
					</template>
					<template #default>
						<slot name="option"></slot>
					</template>
				</Popover>

			</div>
			<Button mirror>
				<Icon icon="mdi:menu-up"></Icon>
			</Button>
			<Button mirror>
				<Icon icon="mdi:menu-down"></Icon>
			</Button>
		</LayoutFlex>
	</LayoutFlex>
</template>
<script lang="ts" setup>

import Arrow from "@/module/icon/component/IconArrow.vue";
import Button from "@/components/button/Button.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import LayoutScroll from "@/components/layout/LayoutScroll.vue";
import Popover from "@/components/Popover.vue";
import Icon from "@/module/icon/component/Icon.vue";

const props = withDefaults(defineProps<{
	showOption?: boolean
	styleOption?: any
}>(), {
	showOption: true
})
const emits = defineEmits<{
	(event:'add',)
	(event:'remove',)
}>()
</script>
<style lang="scss" scoped>
.id-slot-list {

	width: 100%;
	height: 200px;
}
</style>