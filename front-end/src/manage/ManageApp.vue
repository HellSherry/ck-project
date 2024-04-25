<script setup>

import router from "@/router";
import NoPermissions from "@/components/NoPermissions.vue";
import {useCounterStore} from "@/stores/counter";
const store = useCounterStore();
</script>

<template>

  <t-layout style="height: 100vh;overflow: auto">
    <t-content>
     <NoPermissions v-if="store.loginUser===null||store.loginUser.sub.identity===1"/>

      <router-view v-else></router-view>

    </t-content>
    <t-footer >
      <span style="text-align: center">
              <p>长沙创科职校   0731-8455-6741 | 长沙市芙蓉区</p>

      <p> 湘ICP备2023020435号| Copyright © icloud All rights reserved</p>
      </span>


    </t-footer>
  </t-layout>

</template>

<style scoped>

</style>