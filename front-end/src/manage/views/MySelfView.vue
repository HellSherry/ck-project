<script setup lang="jsx">
import {ref} from "vue";
import {useCounterStore} from "@/stores/counter";
import CropperImage from "@/components/CropperImage.vue";
const dialogVis = ref(
    {hand: false}

);
const store = useCounterStore();

</script>

<template>
    <div :style="`background-image: url('${store.darkT? `http://hbimg.huaban.com/8e149140bd05698de8174d7d575ef065263e42d818388b-4YqBxT`:`http://hbimg.huaban.com/8e149140bd05698de8174d7d575ef065263e42d818388b-4YqBxT`}');background-repeat: no-repeat;background-size: cover;min-height: 40vh;background-position-y: 44%;margin-top: -20px`">

  </div>
  <t-dialog v-model:visible="dialogVis.hand">
    <CropperImage/>
  </t-dialog>

    <t-layout style="width: 96%;margin: 0 auto;border-radius: 12px;overflow: hidden;margin-top: -40px;min-height: 66vh;background-color: var(--td-bg-color-container)">

      <t-aside style="border-right: 1px dashed var(--td-text-color-primary)">
        <div style="width: 100%;text-align: center;padding-top: 10px">
          <t-avatar  size="100px"> {{ store.loginUser.sub.username.substring(0, 1)}} </t-avatar>

        </div>
        <t-menu style="height: 50vh" >


          <t-menu-item>
            <t-icon name="home" />
            <span>首页</span>
          </t-menu-item>
        </t-menu>

      </t-aside>
      <t-content>
        sdf
      </t-content>
    </t-layout>


</template>

<style scoped>

</style>