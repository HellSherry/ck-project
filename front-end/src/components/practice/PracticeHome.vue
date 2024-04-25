<script setup lang="jsx">
import {ref, watch} from "vue";
import {useCounterStore} from "@/stores/counter";
import VueCentre from "@/components/practice/VueCentre.vue";
import VueHistory from "@/components/practice/VueHistory.vue";
import service from "@/axios/service";
import {RouterView, useRouter} from "vue-router";

const store = useCounterStore();
const router = useRouter();
console.log();
const value = ref(router.currentRoute.value.name=='practice-centre'?1:2);
const tab = ref(1);
watch(()=> value.value, (newValue, oldValue) => {
 if (newValue === 1)
   router.push({name: 'practice-centre'})
  else
    router.push({name: 'practice-history'})
})

</script>

<template>
<!--    <div :style="`background-image: url('${store.darkT? `http://hbimg.huaban.com/8e149140bd05698de8174d7d575ef065263e42d818388b-4YqBxT`:`http://hbimg.huaban.com/8e149140bd05698de8174d7d575ef065263e42d818388b-4YqBxT`}');background-repeat: no-repeat;background-size: cover;min-height: 40vh;background-position-y: 44%;margin-top: -20px`">-->
  <div style="background-position: center" :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;min-height: ${value==1?'40vh':'100vh'} ;margin-top: -20px`">

  </div>
  <t-card :style="value==1?'margin: 0 auto;min-height:58vh;margin-top: -40px;':'margin: 0 auto;min-height:90vh;margin-top: -96vh;'" :bordered="false" hoverShadow style="width: 96%;">
  <t-tabs v-model="value">
    <t-tab-panel :value="1" label="分类自测" :destroy-on-hide="false">
    </t-tab-panel>
    <t-tab-panel  :value="2" label="我的历史" :destroy-on-hide="false">

    </t-tab-panel>


    <!--    <t-tab-panel :value="3" label="本班排行" :destroy-on-hide="false">-->
<!--      <template #panel>-->
<!--        <p style="padding: 25px">选项卡3的内容，使用 t-tab-panel 渲染</p>-->
<!--      </template>-->
<!--    </t-tab-panel>-->
<!--    <t-tab-panel :value="4" label="本校排行" :destroy-on-hide="false">-->
<!--      <template #panel>-->
<!--        <p style="padding: 25px">选项卡4的内容，使用 t-tab-panel 渲染</p>-->
<!--      </template>-->
<!--    </t-tab-panel>-->

  </t-tabs>
    <RouterView/>


  </t-card>

</template>

<style scoped>
*{
  transition: all 300ms;
}

</style>