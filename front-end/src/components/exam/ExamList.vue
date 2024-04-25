<script setup>

import dayjs from "dayjs";
import {useNow} from "@vueuse/core";
import {useCounterStore} from "@/stores/counter";
import {ref} from "vue";
import {MessagePlugin} from "tdesign-vue-next";
import router from "@/router";
import TimeClock from "@/components/Time.vue";
const now = useNow()

const store = useCounterStore();
store.getExamList()

function goToExam(item) {
  if (dayjs(item.starttime).valueOf() + item.duration * 1000 * 60 < dayjs().valueOf()) {
    MessagePlugin.error('本场考试已经结束了哦');
  } else {
    // -${store.loginUser.sub.userId}
    sessionStorage.setItem(`exam-${item.paperid}`, JSON.stringify(item))
    router.push('/exam/answer/' + item.paperid)
  }
}
function notStart(){

  const elementById = document.getElementById("audioController");
  elementById.src='/assets1/audio/2869.wav'
  elementById .play()
}
</script>

<template>
  <div style="margin-top: 8px;width: 70%;display: inline-block;vertical-align: middle;">
    <!--          {{store.examList}}-->
    <t-list style="height: 52vh" size="small" :split="true">
      <t-list-item v-for=" item in store.examList">
        <t-list-item-meta image="/assets1/image/试卷.png" :title="item.papername"
                          :description="`开始时间：${  dayjs(item.starttime).format('YY年MM月DD日 HH:mm') +'    总分：'+item.totalscore}`"/>
        <template #action>
          <t-button v-if="dayjs(item.starttime).valueOf()+item.duration*1000*60< now.valueOf()" theme="warning"
                    :disabled="true" :variant="store. darkT?'':'outline'" shape="round">已结束
          </t-button>
          <t-button v-else-if="dayjs(item.starttime).valueOf()< now.valueOf()" @click="goToExam(item)"
                    :ghost="!store. darkT" shape="round">去考试
          </t-button>
          <span v-else><nut-countdown @click="notStart" style="display: inline"
                                      :end-time="dayjs(item.starttime).valueOf()"></nut-countdown></span>
        </template>
      </t-list-item>

    </t-list>


  </div>
  <div style="display: inline-block; ;vertical-align: middle;width: 30%;height:240px;">
    <!--          <button @click="show">sdf</button>-->
    <nut-sticky style="width: 100%;margin-top: 20px ; " bottom="40" position="bottom">

      <TimeClock style=" margin: 0 auto;width:180px"/>
      <div style="text-align: center; margin-top: 20px;max-height: 60px" v-if="store.examList.length>0">
        <div v-if="dayjs(store.examList[0].starttime).valueOf()> now.valueOf()">
          距离下一场考试开始还有：
          <nut-countdown style="display: inline"
                         :end-time="dayjs(store.examList[0].starttime).valueOf()"></nut-countdown>
        </div>
        <div v-else>
          <div
              v-if="dayjs(store.examList[0].starttime).valueOf()+store.examList[0].duration*1000*60>now.valueOf()">
            距离本场考试结束还有：
            <nut-countdown style="display: inline"
                           :end-time="dayjs(store.examList[0].starttime).valueOf()+store.examList[0].duration*1000*60"></nut-countdown>
          </div>
          <div v-else>本场考试已经结束</div>

          <div v-if="store.examList.length>1">
            距离下一场考试开始还有：
            <nut-countdown style="display: inline"
                           :end-time="dayjs(store.examList[1].starttime).valueOf()"></nut-countdown>
          </div>
        </div>
      </div>


    </nut-sticky>

  </div>

</template>

<style scoped>

</style>