<template>
  <InjectStyle>
    <InjectBackground>
      <InjectModal/>
      <LayoutAbsolute left="20%" width="50%" top="20%" height="60%">
        <LayoutBlur style="width: 100%;height: 100%;">
          <PageComp/>
        </LayoutBlur>
      </LayoutAbsolute>
    </InjectBackground>
  </InjectStyle>
</template>
<script setup lang="ts">
import InjectBackground from "@/module/style/component/InjectStyle.vue";
import LayoutAbsolute from "@/components/layout/LayoutAbsolute.vue";
import LayoutBlur from "@/components/layout/LayoutBlur.vue";
import PageComp from "@/page/component/PageContainer.vue";
import InjectModal from "@/module/modal/component/InjectModal.vue";
import InjectStyle from "@/module/style/component/InjectStyle.vue";

</script>

<style scoped lang="scss">

</style>