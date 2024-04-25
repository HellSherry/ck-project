<script setup>

import AnswerSheets from "@/components/ui/TimeCountDown.vue";
import Login from "@/components/user/login.vue";
import AnswerUI from "@/components/ui/AnswerUI.vue";
import {ref} from "vue";
import AnswerTheQuestion from "@/components/answer-correlation/AnswerTheQuestion.vue";
import A3DUI from "@/components/ui/A3DUI.vue";
import AButton from "@/components/ui/AButton.vue";
import typer from "@/components/ui/Typer.vue";
import TestrRe from "@/views/TestrRe.vue";
import axios from "axios";
import TimeClock from "@/components/Time.vue";
import JoinExam from "@/test/JoinExam.vue";
 import Tailoring from "@/components/Tailoring.vue";
import Ddd from "@/components/ddd.vue";


const ref1 = ref(1);
const options = ref([{dshf: "sd"}, {dshf: "sd"}, {dshf: "sd"}, {dshf: "sd"}]);
const value = ref('1');
function winClose() {
  window.close()
}
const asfsa= ref([]);

function getimg() {
  axios.get("https://raw.onmicrosoft.cn/Bing-Wallpaper-Action/main/data/en-US_all.json")
      .then(res => {
        asfsa.value=res.data.data
        console.log(res)
      })
}
function playMusic(){
  const elementById = document.getElementById("audioController");
  elementById.src='/assets1/audio/2869.wav'
  elementById .play()
}
</script>

<template>
<ddd/>
<Tailoring/>
   <button @click="playMusic">asdfjksad</button>
  <JoinExam/>
  <TimeClock/>
  <button @click="getimg">
    sdf
  </button>
  <img :src="'https://cn.bing.com'+ item.url" v-for="item in asfsa" />
  <TestrRe/>

  <typer></typer>
  <AButton @click="winClose"></AButton>
  <A3DUI style="transform: scale(30%);border: 1px solid red"/>
  <AnswerSheets endTime="60">

  </AnswerSheets>
  <Login></Login>
  <AnswerUI :checkbox="false" v-model="ref1" :options="options"/>
  <AnswerTheQuestion/>

</template>

<style scoped>

</style>