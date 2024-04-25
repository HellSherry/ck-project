<script setup lang="jsx">
import {ref} from "vue";
import {useCounterStore} from "@/stores/counter";
const store = useCounterStore();

</script>

<template>
    <div :style="`background-image: url('${store.darkT? `http://hbimg.huaban.com/8e149140bd05698de8174d7d575ef065263e42d818388b-4YqBxT`:`http://hbimg.huaban.com/8e149140bd05698de8174d7d575ef065263e42d818388b-4YqBxT`}');background-repeat: no-repeat;background-size: cover;min-height: 40vh;background-position-y: 44%;margin-top: -20px`">

  </div>
  <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto;margin-top: -40px;min-height: 60vh">
》》》》》》敬请期待
  </t-card>

</template>

<style scoped>

</style>