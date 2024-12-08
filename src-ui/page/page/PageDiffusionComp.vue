<template>
  <div>TODO...</div>
  <Card>
    <div style="font-weight: bold">predict</div>
    <div>prompt</div>
    <Input style="width: 100%"></Input>
    <div>seed {{page.seed}}</div>
    <InputNumber style="width: 100%" v-bind:precision="0" v-bind:min="0" v-bind:max="999999999999999" v-model:value="page.seed"></InputNumber>
    <div>batch {{page.batch}}</div>
    <Slider style="width: 100%" v-bind:precision="0" v-bind:min="0" v-bind:max="20" v-model:value="page.batch" @update:value="v=>page.batch=Math.round(v)"></Slider>
    <br>
    <LayoutFlex justify-content="flex-end">
      <Button display="bevel">
        <IconShadow icon="mdi:play"/>
        start
      </Button>
    </LayoutFlex>
  </Card>
  <Card>
    <div style="font-weight: bold">fit</div>
    <div>dataset dir</div>
    <Input style="width: 100%"></Input>
    <div>lr</div>
    <Input style="width: 100%"></Input>
    <br>
    <br>
    <LayoutFlex justify-content="flex-end">
      <Button display="bevel">
        <IconShadow icon="mdi:play"/>
        start
      </Button>
    </LayoutFlex>
  </Card>
</template>
<script lang="ts" setup>
import {PageDiffusion} from "@/page/page/PageDiffusion.ts";
import {PageProps} from "@/page/entity/PageProps.ts";

import Input from "@/components/input/Input.vue";
import Card from "@/page/page/Card.vue";
import Slider from "@/components/silder/Slider.vue";
import InputNumber from "@/components/input/InputNumber.vue";
import Button from "@/components/button/Button.vue";
import IconShadow from "@/module/icon/component/IconShadow.vue";
import LayoutFlex from "@/components/layout/LayoutFlex.vue";

const props = defineProps<PageProps<PageDiffusion>>()
</script>