<script setup lang="jsx">
import {ref} from "vue";
import {useCounterStore} from "@/stores/counter";
import ClassCountEcharts from "@/manage/compoments/statistics/ClassCountEcharts.vue";
import CourseCountEcharts from "@/manage/compoments/statistics/CourseCountEcharts.vue";
import UserCountEcharts from "@/manage/compoments/statistics/UserCountEcharts.vue";
import MoreEcharts from "@/manage/compoments/statistics/MoreEcharts.vue";

const store = useCounterStore();

</script>

<template>
  <div style="background-position: center"
       :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;  `">
    <div :class="store.darkT?'boxdark':'box'"
         style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 3vh;padding-bottom: 10px">
      <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto; min-height: 94vh">
        <t-row style="width: 94%;margin: 34px auto" justify="space-between">
          <t-col :span="3.2">
            <ClassCountEcharts
                style="height: 140px;width: 300px;border-radius: 20px;border: 1px solid var(--td-text-color-primary)"/>
          </t-col>
          <t-col :span="3.2">
            <UserCountEcharts
                style="height: 140px;width: 300px;border-radius: 20px;border: 1px solid var(--td-text-color-primary)"/>
          </t-col>
          <t-col :span="3.2">
            <CourseCountEcharts
                style="height: 140px;width: 300px;border-radius: 20px;border: 1px solid var(--td-text-color-primary)"/>

          </t-col>

        </t-row>

        <MoreEcharts
            style="height:520px;width: 94%;margin: 0 auto;border-radius: 20px;border: 1px solid var(--td-text-color-primary)"/>


      </t-card>
    </div>
  </div>

</template>

<style scoped>
.box {
  background-color: rgba(255, 255, 255, .48);

}

.boxdark {
  background-color: rgba(0, 0, 0, .48);
}
</style>