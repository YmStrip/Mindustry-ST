<template>
  <LayoutBlur style="margin: 5px;padding: 5px;position:relative;background: rgba(255,255,255,0.05)">
    <slot></slot>
  </LayoutBlur>
</template>
<script lang="ts" setup>
import LayoutBlur from "@/components/layout/LayoutBlur.vue";
</script>