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
const resourceItem = ref();

const askDetail = ref();
if (sessionStorage.getItem(`resource-${id} `) != null) {
  resourceItem.value = JSON.parse(sessionStorage.getItem(`resource-${id} `))
  if (typeof resourceItem.value.images == 'string') {
    resourceItem.value.images = JSON.parse(resourceItem.value.images)
  }
} else {
  service.get('/public/soft/getDetail?id=' + id).then(res => {
    resourceItem.value = res.data
    if (typeof resourceItem.value.images == 'string') {
      resourceItem.value.images = JSON.parse(resourceItem.value.images)
    }
  })
}

initDetail()

//大小格式化
function sizeFormatting(now) {
  if (now === 0 || now === null || now == "") {
    return now
  }
  var unitArr = new Array("B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB");
  var index = 0,
      srcsize = parseFloat(now);
  index = Math.floor(Math.log(srcsize) / Math.log(1024));
  var size = srcsize / Math.pow(1024, index);
  //  保留的小数位数
  if (index !== 0) {
    size = size.toFixed(2);
  }
  return size + unitArr[index];
}

function downLoadFile(url) {
  let a = document.createElement('a')
  a.href = url;
  a.download = '文件名'
  a.click();
}

const addDialog = ref(false);
const clickIndex = ref(0);
const addInput = ref('');

function addAsk() {
  if (addInput.value.trim() == '') {
    MessagePlugin.error("请输入内容");
    addInput.value = ''
    return;
  }
  service.get("/public/soft/addRes?context=" + addInput.value + "&askId=" + id + "&askUser=" + resourceItem.value.creatorUser).then((res) => {
    addDialog.value = false;
    initDetail()
  })
}

function initDetail() {
  if (resourceItem.value == null) {
    console.log(1)
    setTimeout(() => {
      initDetail()
    }, 100)
    return;
  }
  service.get('/public/soft/getDetail?id=' + id).then(res => {
    askDetail.value = res.data
  })

}


</script>

<template>

  <div style="background-image: url('/assets1/image/back.jpg');">
    <div :class="store.darkT?'boxdark':'box'" style=" min-height: 100vh;backdrop-filter: blur(10PX);padding-top:16px">

      <t-layout style="max-width: 1080px;margin:  0 auto;background-color: #0000">
        <t-content>

          <t-card v-if="resourceItem!=null"
                  style="border-radius: 12px;min-height: 90vh; height: 90%;margin-bottom: 2%;width: 99%;"
                  header-bordered>
            <template #title>

              <!--              @click="//this.$router.go(-1);"-->
              <t-button @click="()=>{
                try {router.go(-1);}
                catch(err) {router.push('/resource') }
              }
              " style="margin-right: 4px" shape="circle" variant="outline" theme="primary">
                <template #icon>
                  <t-icon name="chevron-left"/>
                </template>
              </t-button>
              软件详情

            </template>
            <template #actions>
              <t-button @click="downLoadFile(resourceItem.downurl)" shape="round">立即下载</t-button>
            </template>


            <div style="margin-left: 40px;margin-top: 20px">
              <t-avatar
                  v-bind:image=" resourceItem.icon"
                  shape="round"
                  style="margin: 4px;box-shadow: rgba(116,149,178,0.48) 6px 2px 16px 0px, var(--td-bg-color-container) -6px -2px 16px 0px;
 margin-right: 40px;border-radius: 12px" size="160px">
              </t-avatar>
              <div style=" display: inline-block;height: 160px;vertical-align: middle">
                <h2 style="line-height: 8px">{{ resourceItem.name }}</h2>
                <t-link hover="color" target="_blank" :href="'https://cn.bing.com/search?q='+resourceItem.company">
                  <t-icon style="margin-right: 4px" name="link"/>
                  {{ resourceItem.company }}
                </t-link>
                <div style="margin-top:  8px;margin-bottom: 12px">
                  <t-tag size="small">
                    <t-icon name="animation"/>

                    {{ sizeFormatting(resourceItem.size) }}
                  </t-tag>
                  <t-tag size="small" style="margin-left: 6px">
                    <t-icon name="cloud-download"/>

                    {{ resourceItem.quantity }}
                  </t-tag>


                  <t-tag size="small" style="margin-left: 6px">
                    <t-icon name="time"/>
                    {{
                      dayjs(resourceItem.addtime).format('YYYY-MM-DD HH:mm:ss')
                    }}
                  </t-tag>
                </div>
                <t-button @click="downLoadFile(resourceItem.downurl)" size="large">

                  立即下载
                  <t-icon style="padding-left: 8px;padding-top: 2px;margin-right: -8px" name="download"/>

                </t-button>
              </div>
            </div>

            <!--            <div >  <nut-empty description="暂时没有问题哦"></nut-empty></div>-->
            <div class="resource" style="width: 90%;margin: 10px auto">

              <t-swiper :autoplay="false" :navigation="{ placement: 'outside',showSlideBtn:'hover',size:'large' }"
                        class="tdesign-demo-block--swiper" type="card" :height="280">
                <t-swiper-item @click="addDialog=true;clickIndex=index" v-for="(item,index) in resourceItem.images"
                               :key="item">

                  <img :src="item" width="450px" style="border-radius: 4px" alt="">
                  <!--      <div class="demo-item" style="background-color: red">{{ item }}</div>-->
                </t-swiper-item>
                <t-image-viewer v-model:visible="addDialog" :images="resourceItem.images">
                  <template #trigger>

                  </template>
                </t-image-viewer>

              </t-swiper>
              <t-image-viewer v-model:visible="addDialog" v-model:index="clickIndex" :images="resourceItem.images">
                <template #trigger>
                  <div class="tdesign-demo-image-viewer__ui-image">

                  </div>
                </template>
              </t-image-viewer>
              <h2>简介</h2>
              <p style="font-size: 16px;font-weight: 200">
                {{ resourceItem.particulars }}
              </p>


            </div>


          </t-card>

        </t-content>
      </t-layout>


    </div>
  </div>

</template>

