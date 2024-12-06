<template>
  <LayoutFlex column width="100%" justify-content="flex-start" style="padding: 5px">
    <div style="width: 100%">
      <LayoutFlex>
        <Button>
          <Icon icon="mdi:cog"></Icon>
        </Button><Input v-model:value="page.cwd" style="width: 100%"></Input>
      </LayoutFlex>
      <br>
      <Button @click="page.run()" display="bracket">
        <IconShadow icon="mdi:play"/>
        PLAYGROUND
      </Button>
    </div>
  </LayoutFlex>
</template>
<script lang="ts" setup>

import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import Button from "@/components/button/Button.vue";
import IconShadow from "@/module/icon/component/IconShadow.vue";
import {PageProps} from "@/page/entity/PageProps.ts";
import Input from "@/components/input/Input.vue";
import {PageHome} from "@/page/page/PageHome.ts";
import Icon from "@/module/icon/component/Icon.vue";
const props= defineProps<PageProps<PageHome>>()
</script>