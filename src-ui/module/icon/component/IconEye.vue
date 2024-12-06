<template>
	<Icon icon="mdi:eye-off-outline" v-if="!value" @click="value=<any>!value"/>
	<Icon icon="mdi:eye-outline" v-else  @click="value=<any>!value"/>
</template>
<script lang="ts" setup>
import Icon from "@/module/icon/component/Icon.vue";

const value = defineModel<boolean>('value')
</script>