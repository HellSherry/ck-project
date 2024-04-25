<script setup>
import {ref} from "vue";
import {useCounterStore} from "@/stores/counter";
import ExamHistory from "@/components/exam/ExamHistory.vue";
import ExamList from "@/components/exam/ExamList.vue";

const store = useCounterStore();
const tab = ref(1);
const value = ref(1);
</script>

<template>
  <div
      :style="`background-image: url('${store.darkT? `/assets1/image/我们终将上岸暗.png`:`/assets1/image/我们终将上岸亮.png`}');background-repeat: no-repeat;background-size: cover;min-height:  ${value==1?'40vh':'100vh'};background-position: center;margin-top: -40px`">

  </div>
  <t-card :style="value==1?'margin: 0 auto;min-height:40vh;margin-top: -44px;':'margin: 0 auto;min-height:90vh;margin-top: -94vh;'" :bordered="false" hoverShadow style="width: 96%;">
    <t-tabs v-model="value">
      <t-tab-panel :value="1" label="我的考试" :destroy-on-hide="false">
        <ExamList/>
      </t-tab-panel>
      <t-tab-panel :value="2" label="我的历史" :destroy-on-hide="false">
        <template #panel>

          <!--<p style="text-align: right">     {{ now }}</p>-->
          <ExamHistory/>
        </template>
      </t-tab-panel>

    </t-tabs>

    <!--  <nut-empty description="404"></nut-empty>-->
  </t-card>

</template>


<style scoped>
*{
  transition: all 300ms;
}

</style>