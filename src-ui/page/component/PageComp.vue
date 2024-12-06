<template>
  <template v-if="page">
    <LayoutFlex column width="100%" height="100%">
      <LayoutFlex style="background: rgba(27,40,101,0.28);width: 100%;" justify-content="center">
        {{ page.label || page.id }}
      </LayoutFlex>
      <LayoutRelative style="flex: 1">
        <LayoutScroll style="position: absolute;width: 100%;height: 100%">
          <component
              v-bind:page="page"
              v-bind:is="page.component"
          />
        </LayoutScroll>
      </LayoutRelative>
    </LayoutFlex>
  </template>
</template>
<script lang="ts" setup>
import {PageProps} from "@/page/entity/PageProps.ts";
import LayoutScroll from "@/components/scroll/LayoutScroll.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import LayoutRelative from "@/components/layout/LayoutRelative.vue";

const props = defineProps<PageProps>()
</script>