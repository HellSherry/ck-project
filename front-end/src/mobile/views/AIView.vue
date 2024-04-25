<script setup>
import {nextTick, onUnmounted, ref} from "vue";

import ToHtml from "@/components/answer-correlation/ToHtml.vue";
import axios from "axios";
import {useCounterStore} from "@/stores/counter";

const messages = ref([{
  msg: "你好！我是GPT，一个由OpenAI创建的人工智能语言模型。我可以回答你关于各种主题的问题，帮助你解决问题或者提供信息。有什么我可以帮助你的吗？",
  type: 'AI',
  time: new Date().toLocaleString()
}]);
if (localStorage.getItem("messages") != null) {
  messages.value = JSON.parse(localStorage.getItem("messages"))
}
onUnmounted(() => {
  localStorage.setItem("messages", JSON.stringify(messages.value))
})
const nowQu = ref();
const isLoding = ref(false)
const store = useCounterStore();

function bingAi() {
  if (isLoding.value) {
    return
  }
  isLoding.value = true;
  messages.value.push({msg: nowQu.value, type: 'Me', time: new Date().toLocaleString()})
  // document.getElementById('aimsgLast' ).scrollIntoView({ behavior: "smooth", block: 'end' });
  // http://ovoa.cc/api/Bing.php?msg=你好?&model=down&type="&model=down&type=json"
  axios
      // .get('https://v1.apigpt.cn/?apitype=sql&q='+nowQu.value)
      // .get('http://' + store.BACKEND + "/public/bing?apitype=sql&q=" + nowQu.value)
      .get("http://7d9fe8e2.r5.cpolar.top/public/bing?apitype=sql&q=" + nowQu.value)
      .then(async result => {
        messages.value.push({msg: result.data.ChatGPT_Answer, type: 'AI', time: new Date().toLocaleString()})
        console.log(result)
        localStorage.setItem("messages", JSON.stringify(messages.value))

      })
      .finally(() => {
        nowQu.value = '';
        document.getElementById('aimsgLast')
            .scrollIntoView({behavior: "smooth", block: 'end'});
        isLoding.value = false;
        // 总是会执行
      });

  // fetch("http://ovoa.cc/api/Bing.php?msg=%E4%BD%A0%E5%A5%BD&model=down&type=json")
  //     .then(response => response.text())
  //     .then(result => console.log(result))
}
</script>

<template>
<!--  <nut-navbar title="Title" fixed="true"></nut-navbar>-->
  <div style="width: 94%;margin: 20px auto  100px auto ;">

    <t-space style="width: 100%;min-height: 80vh;" direction="vertical">

      <div :id="'aimsg'+index" v-for="(item,index) in messages">

        <t-avatar size="large" :style="item.type=='AI'?'':'float: right'  ">{{ item.type }}</t-avatar>
        <ToHtml
            :style="'vertical-align: top;max-width: 72%;margin-top: 2px;display: inline-block;margin-left:6px;margin-right:6px;padding: 10px;border-radius: 8px;background-color: var(--td-bg-color-container); width:fit-content;'+(item.type=='AI'?'':'float: right;')"
            :value="item.msg.replace(/\n\n```/g,'<pre>').replace(/```\n\n/g,'</pre>').replace(/\n/g,'<br>')"/>


      </div>
      <div id="aimsgLast" style="height: 100px" v-if="isLoding">
        <t-avatar>AI</t-avatar>
        <ToHtml
            style="vertical-align: top;max-width: 80%;margin-top: 2px;display: inline-block;margin-left:6px;margin-right:6px;padding: 10px;border-radius: 8px;background-color: var(--td-bg-color-container); width:fit-content;"
            value="正在加载..."/>
      </div>

    </t-space>


  </div>

      <div style="width:100%;position: fixed;bottom: 54px">
        <nut-textarea v-model="nowQu" placeholder="请输入内容"  :rows="1" autosize />
        <div style="text-align: right;margin-top: -38px;margin-right: 8px">
          <t-button @click="bingAi" :disabled="nowQu==''">发送</t-button>
        </div>
      </div>

</template>

<style scoped>

</style>