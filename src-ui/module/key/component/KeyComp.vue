<template>
	<div
			style="width: 100%"
			v-if="item"
	>
		<Popover

				placement="left-start"
				trigger="hover"
				style="width: 300px;"
				v-bind:delay="delay"
		>
			<template #trigger>
				<LayoutFlex
						v-if="item.obj"
						@click.stop="emits('clickObj',item.id)"
				>
					<Button>
						<Icon icon="mdi:view-list"/>
					</Button>
					<div>{{ item.id }}</div>
				</LayoutFlex>
				<LayoutFlex v-else>
					<Button>
						<Icon
								v-bind:icon="item.icon"
								v-bind:color="ColorIcon"
						/>
					</Button>
					<div style="flex: 1">{{ item.id }}</div>
					<div>{{ item.suffix }}</div>
				</LayoutFlex>
			</template>
			<template #default>
				<LayoutFlex>
					<Button>
						<Icon
								v-bind:icon="item.icon"
								v-bind:color="ColorIcon"
						/>
					</Button>
					<div>
						{{ item.label || item.id }}
					</div>
				</LayoutFlex>
				<div style="padding: 10px">
					<template v-if="item.obj">
						List
					</template>
					<template v-else>
						<div v-if="!item.doc">
							Not Document
						</div>
						<div v-else v-html="item.doc">

						</div>
					</template>
				</div>

			</template>
		</Popover>
	</div>
</template>
<script lang="ts" setup>
import Button from "@/components/button/Button.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import Popover from "@/components/Popover.vue";
import {Color} from "@/entity/Color.ts";
import Icon from "@/module/icon/component/Icon.vue";
import {KeyItem} from "@/module/key/entity/KeyItem.ts";
import {Yurry} from "@/root/Application.ts";
import {computed} from "vue";

const props = withDefaults(defineProps<{
	item: KeyItem
	delay?: number
}>(), {
	delay: 150
})
const emits = defineEmits<{
	(e: 'clickObj', id: string)
}>()
const ColorIcon = computed(() => {
	return new Color(props.item?.color)
			.screen(
					new Color(Yurry.style.theme.Item('color')), .1)
			.softLight(new Color('white'), .85)
			.hex()
})
</script>