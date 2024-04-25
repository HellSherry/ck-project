<script setup lang="jsx">
import {ref} from "vue";
import {useCounterStore} from "@/stores/counter";
import dayjs from "dayjs";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";
import service from "@/axios/service";
import ChatTrueView from "@/views/ChatTrueView.vue";
import {DialogPlugin} from "tdesign-vue-next";
const askDetail = ref();
const message = ref();
service.get('/message/getAll').then(res => {
  message.value = res.data
})


initDetail();
function initDetail() {
  if (false) {
    console.log(1)
    setTimeout(() => {
      initDetail()
    }, 100)
    return;
  }
  service.get('/askQuestion/getReply' ).then(res => {
    askDetail.value = res.data
  })

}

const store = useCounterStore();
const menuItemActive = ref('回复我的');
</script>

<template>
  <div style="background-position: center"
       :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;  `">
    <div :class="store.darkT?'boxdark':'box'"
         style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 12px">
      <t-layout  :class="store.darkT?'boxdark':'box'"  style="margin: 0 auto ;width: 90%; ">
        <t-aside style="background-color: #0000">
          <t-menu  :class="store.darkT?'tem':'box'" v-model="menuItemActive"  :theme="store.darkT?'dark':'light' " default-value="item1"   height="550px" >
            <template #logo>
              <t-icon style="font-size: 18px" name="send"/> &nbsp;

               消息通知
            </template>
            <t-menu-item value="回复我的"> 回复我的 </t-menu-item>
            <t-menu-item value="系统通知"> 系统通知 </t-menu-item>
<!--            <t-menu-item value="我的消息"> 我的消息 </t-menu-item>-->

          </t-menu>
        </t-aside>
  <t-content >
    <t-card size="small" style="width: 98%;margin: 8px  auto 0 auto;" >

      <h3 style="line-height: 0px">
        {{ menuItemActive }}
      </h3>
     </t-card>
      <t-space :size="8" style=" margin:8px 1%;width: 99%; height: 82vh;overflow: auto;" direction="vertical">

        <t-card style="height: 82vh" v-if="menuItemActive==='回复我的'" size="small"  >
          <div v-if="askDetail!= undefined && askDetail.length==0">
            <nut-empty description="暂时没有数据哦"></nut-empty>
          </div>
          <t-list style="max-height: 80vh" :scroll="{ type: 'virtual' }" :split="true">
            <t-list-item @click="$router.push('/ask/'+item.askId)" v-for="(item, index) in askDetail" :key="index">
              <template #content>
                <t-comment :avatar=" item.userImg=='no'?'': (item.userImg==null?'/assets1/userAvatar/-1.png':item.userImg)" :author="item.username" :datetime="dayjs(item.creatTime).format('YYYY-MM-DD HH:mm:ss')  " >
                  <template #actions>


                  </template>
                  <template #content>
                    <ToHtml :value="item.answer.replace(/\n\n```/g,'<pre>').replace(/```\n\n/g,'</pre>').replace(/\n/g,'<br>')"/>
                  </template>
                </t-comment>
              </template>
            </t-list-item>
          </t-list>

  </t-card>

        <template v-else-if="menuItemActive==='系统通知'">

          <t-card   @click="
()=>{
   const confirmDia = DialogPlugin({
                                    header: '通知：'+i.title,

                                    body:i.details ,
                                    cancelBtn: null,
                                    onConfirm: ({e}) => {
                                         confirmDia.hide();
                                    }
                                });
}"   v-for=" i in message">
            <template #title>
              <div style="height: 0">
                {{ i.title }}

                <span style="font-weight:2;font-size: small">{{dayjs(i.addtime).format("YYYY-MM-DD HH:mm:ss")}}</span>
              </div>

            </template>

            {{i.details}}
          </t-card>
        </template>

<ChatTrueView  v-else-if="menuItemActive==='我的消息'"/>

      </t-space>




  </t-content>
      </t-layout>

    </div>
  </div>

</template>

<style scoped>
.box {
  background-color: rgba(255, 255, 255, .48);
}
.tem {
  background-color: rgba(0, 0, 0, .2);
 }
.boxdark {
  background-color: rgba(0, 0, 0, .48);
}
</style>