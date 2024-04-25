<script setup>
import {ref} from "vue";
const activeMenu = ref("0")
const ref1 = ref([
  {
    name: "1",
    url: "https://dazi.91xjr.com/game/fruit.html",
    style1:'float: right;width: 640px;height: 480px;overflow: hidden',
    style2:'margin-left: -200px;margin-top: -118px'
  },
  {
    name: "1",
    url: "https://dazi.91xjr.com/game/apple.html",
    style1:'float: right;width: 590px;height: 600px;overflow: hidden',
    style2:'margin-left: -200px;margin-top: -120px'
  },
  {
    name: "1",
    url: "https://dazi.91xjr.com/game/jump.html",
    style1:'float: right;width: 590px;height: 600px;overflow: hidden',
    style2:'margin-left: -200px;margin-top: -120px'
  }

]);
</script>

<template>
  <div style=" width: 900px;margin: 20px auto ">


    <t-layout>
      <t-aside>
<t-menu v-model="activeMenu">
  <t-menu-item value="0">切水果</t-menu-item>
  <t-menu-item value="1">消灭苹果</t-menu-item>
  <t-menu-item value="2">Jump</t-menu-item>
 </t-menu>
      </t-aside>
      <div :style="ref1[activeMenu].style1">
        <iframe  :style="ref1[activeMenu].style2" :src="ref1[activeMenu].url" width="1000"
                height="10000" frameborder="0">
          加载中
        </iframe>

      </div>

    </t-layout>

  </div>

</template>

<style scoped>

</style>