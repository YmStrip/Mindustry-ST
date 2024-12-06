<template>
  <LayoutFlex column width="100%" justify-content="flex-start" style="padding: 5px">
    <div style="width: 100%">
      <div>Refactor Dir</div>
      <div>
        <div>dir from</div>
        <LayoutFlex>
          <Button>
            <IconShadow icon="mdi:cog"/>
          </Button>
          <Input style="width: 100%" v-model:value="page.dir"/>
        </LayoutFlex>
      </div>
      <div>
        <div>file template from</div>
        <LayoutFlex>
          <Button>
            <IconShadow icon="mdi:cog"/>
          </Button>
          <Input style="width: 100%" v-model:value="page.from"></Input>
        </LayoutFlex>
      </div>
      <div>
        <div>file template to</div>
        <LayoutFlex>
          <Button>
            <IconShadow icon="mdi:cog"/>
          </Button>
          <Input style="width: 100%" v-model:value="page.to"></Input>
        </LayoutFlex>
      </div>
      <br>
      <LayoutFlex justify-content="flex-end">
        <Button display="bevel" @click="page.renameDir()">start</Button>
      </LayoutFlex>
    </div>
  </LayoutFlex>
</template>
<script lang="ts" setup>

import LayoutFlex from "@/components/layout/LayoutFlex.vue";
import Button from "@/components/button/Button.vue";
import Input from "@/components/input/Input.vue";
import IconShadow from "@/module/icon/component/IconShadow.vue";
import {PageProps} from "@/page/entity/PageProps.ts";
import {PageRename} from "@/page/page/PageRename.ts";

const props = defineProps<PageProps<PageRename>>()
</script>