<template>
  <LayoutFlex style="height: 100%;width: 100%">
    <LayoutShrinkBar position="left" style="height: 100%">
      <template #default>
        <Button
            style="margin: 0;width: 22px;"
            v-for="d in page.page"
            @click="page.pageCurrent=d.id"
        >
          <Icon v-bind:icon="d.icon"/>
        </Button>
      </template>
    </LayoutShrinkBar>
    <PageComp
        style="flex: 1;"
        v-if="page.page[page.pageCurrent]"
        v-bind:page="page.page[page.pageCurrent]"
    />
  </LayoutFlex>
</template>
<script lang="ts" setup>

import Button from "@/components/button/Button.vue";
import Icon from "@/module/icon/component/Icon.vue";
import LayoutShrinkBar from "@/components/layout/LayoutShrinkBar.vue";
import {page} from "fyurry";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import LayoutScroll from "@/components/scroll/LayoutScroll.vue";
import LayoutRelative from "@/components/layout/LayoutRelative.vue";
import PageComp from "@/page/component/PageComp.vue";
</script>