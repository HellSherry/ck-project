<script setup>

import {ref} from "vue";
import {useRouter} from "vue-router";
import service from "@/axios/service";
import dayjs from "dayjs";
import {useCounterStore} from "@/stores/counter";
import {MessagePlugin} from "tdesign-vue-next";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";

const store = useCounterStore();

const router = useRouter();
const id = router.currentRoute.value.params.id
const askItem = ref();
const addDialog = ref(false);
const userList = ref([]);
const addInput = ref('');
const askDetail = ref();


if (sessionStorage.getItem(`ask-${id} `) != null) {
  askItem.value = JSON.parse(sessionStorage.getItem(`ask-${id} `))
  if (askItem.value.creatorUser !== -1) {
    if (userList.value.find((item) => item.id === askItem.value.creatorUser)) {
      askItem.value.user = userList.value.find((item) => item.id === askItem.value.creatorUser)
    }
    service.get("/user/getById?id=" + askItem.value.creatorUser).then((res) => {
      userList.value.push(res.data)
      askItem.value.user = res.data
    })
  }


} else {
  service.get('/askQuestion/getById?id=' + id).then(res => {
    askItem.value = res.data
    if (askItem.value.creatorUser === -1) return;
    if (userList.value.find((item) => item.id === askItem.value.creatorUser)) {
      askItem.value.user = userList.value.find((item) => item.id === askItem.value.creatorUser)
    }
    service.get("/user/getById?id=" + askItem.value.creatorUser).then((res) => {
      userList.value.push(res.data)
      askItem.value.user = res.data
    })


  })
}
initDetail()


function addAsk() {
  if (addInput.value.trim() == '') {
    MessagePlugin.error("请输入内容");
    addInput.value = ''
    return;
  }
  service.get("/askQuestion/addRes?context=" + addInput.value + "&askId=" + id + "&askUser=" + askItem.value.creatorUser).then((res) => {
    addDialog.value = false;
    initDetail()
  })
}

function initDetail() {
  if (askItem.value == null) {
    console.log(1)
    setTimeout(() => {
      initDetail()
    }, 100)
    return;
  }
  service.get('/askQuestion/getDetail?id=' + id).then(res => {
    askDetail.value = res.data
    askDetail.value.forEach(item => {
      if (item.creatorUser !== -1) {
        if (userList.value.find((item) => item.id === item.creatorUser)) {
          item.user = userList.value.find((e) => e.id === item.creatorUser)
        }
        service.get("/user/getById?id=" + item.creatorUser).then((res) => {
          userList.value.push(res.data)
          item.user = res.data
        })
      }

    })
  })

}


</script>

<template>
  <t-dialog :visible="addDialog" :close-btn="true" confirm-btn="提交" cancel-btn="取消"
            :onConfirm="addAsk"
            :on-close="()=>{addDialog=false}">
    <template #header>解答问题</template>
    <template #body>
      <t-textarea
          style="margin-bottom: -10px"
          v-model="addInput"
          placeholder="请输入解答"
          name="description"
          :autosize="{ minRows: 1, maxRows: 5 }"
      />
    </template>
  </t-dialog>

  <!--  <div style="background-image: url('/assets1/image/back.jpg');">-->
  <div style="background-image: url('/assets1/image/back.jpg');">
    <div :class="store.darkT?'boxdark':'box'" style=" min-height: 100vh;backdrop-filter: blur(10PX);padding-top:16px">

      <t-layout style="max-width: 1080px;margin:  0 auto;background-color: #0000">
        <t-content>

          <t-card v-if="askItem!=null"
                  style="border-radius: 12px;min-height: 90vh; height: 90%;margin-bottom: 2%;width: 99%;"
                  header-bordered>
            <template #title>
              <t-button @click="()=>{
                try {router.go(-1);}
                catch(err) {router.push('/ask') }
              }
              " style="margin-right: 4px" shape="circle" variant="outline" theme="primary">
                <template #icon>
                  <t-icon name="chevron-left"/>
                </template>
              </t-button>
              问题详情

            </template>
            <template #actions>
              <t-button @click="addDialog=true" shape="round">解答问题</t-button>
            </template>
            <h2>{{ askItem.question }}</h2>
            <!--            <div >  <nut-empty description="暂时没有问题哦"></nut-empty></div>-->
            <t-list style="max-height: 100%" :scroll="{ type: 'virtual' }" :split="true">
              <t-list-item v-for="(item, index) in askDetail" :key="index">
                <template #content>
                  <!--                  :author="item.creatorUser"-->
                  <t-comment
                      :avatar="item.creatorUser==-1?'/assets1/userAvatar/-1.png':item.user==undefined? '':item.user.userImg   "


                      :datetime="dayjs(item.creatTime).format('YYYY-MM-DD HH:mm:ss')  ">
                    <template #actions>


                    </template>
                    <template #content>

                      <ToHtml
                          :value="item.answer.replace(/\n\n```/g,'<pre>').replace(/```\n\n/g,'</pre>').replace(/\n/g,'<br>')"/>
                    </template>
                  </t-comment>
                </template>
              </t-list-item>
            </t-list>
          </t-card>

        </t-content>
      </t-layout>


    </div>
  </div>

</template>

