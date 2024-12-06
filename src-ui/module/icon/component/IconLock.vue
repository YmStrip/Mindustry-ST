<template>
	<Icon icon="ic:sharp-lock-open" v-if="!value" @click="value=<any>!value"/>
	<Icon icon="ic:sharp-lock" v-else  @click="value=<any>!value"/>
</template>
<script lang="ts" setup>
import Icon from "@/module/icon/component/Icon.vue";
const value = defineModel<boolean>('value')

</script>