<script setup>
import {onMounted, onUnmounted, ref} from 'vue';
import axios from "axios";
import Typer from "@/components/ui/Typer.vue";
import {showDialog} from "@nutui/nutui";
import router from "@/router";
import {useCounterStore} from "@/stores/counter";
import dayjs from "dayjs";

import {useNow} from '@vueuse/core'
import {MessagePlugin} from "tdesign-vue-next";
import service from "@/axios/service";

const now = useNow()
const store = useCounterStore();
const source=ref({
  src: 'src/assets/video/视频提取.mp4',
  type: 'video/mp4'
})
const ref1 = ref({
  poster:'//p9-pc-sign.douyinpic.com/tos-cn-p-0015/6b60733c8bc34c80b0812af3ca8cd2f2_1677297055~tplv-dy-360p.jpeg?biz_tag=pcweb_cover&from=3213915784&s=PackSourceEnum_SEARCH&sc=origin_cover&se=false&x-expires=1704279600&x-signature=ZUbaClOzh%2F70czQQkxw9DmOkADk%3D',
  autoplay: true,
  muted:true,
  controls: true,
  loop:true
});

const presentData = ref([]);
const presentDataDeduplication = ref([]);
const presentDataRight = ref(0);
function Percentage(num, total) {
  if (num == 0 || total == 0) {
    return 0;
  }
  return (Math.round(num / total * 10000) / 100.00);// 小数点后两位百分比
}
if (store.loginUser!==null)
service('/practice/getTodaySimpleDetails').then(res => {
  presentData.value=  res.data

  res.data.forEach(e=>{
    if (presentDataDeduplication.value.indexOf(e.questionid) === -1) { //indexof()方法判断在数组中的位置，若不存在，返回-1
      presentDataDeduplication.value.push(e.questionid);
    }
    if (e.answer == e.ranswer) { //indexof()方法判断在数组中的位置，若不存在，返回-1
      presentDataRight.value++;
    }
  })
})



const menuPanna=ref(0)
if (window.innerWidth < 500) {
  // alert("ss")
  const onCancel = () => {
    console.log('event cancel');
  };
  const onOk = () => {
    router.push('/mobile')
    console.log('event ok');
  };
  showDialog({

    'footer-direction': "vertical",
    teleport: "#app",
    title: "移动端跳转",
    content: "如果你在手机上使用，请点击确定前往移动版页面",
    onCancel,
    onOk
  });
}
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
function getBase64Image(img, width, height) {
  img = window.document.getElementsByName('img')[0];
  var canvas = document.createElement("canvas");
  canvas.width = width ? width : img.width;
  canvas.height = height ? height : img.height;
  var ctx = canvas.getContext("2d");
  ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
  var dataURL = canvas.toDataURL();
  img.src = dataURL
  console.log(dataURL)
}

store.getExamList()
function getStudyTime() {
return   dayjs(new Date(new Date().toLocaleDateString()).getTime()+parseInt(sessionStorage.getItem("studyTime")) ).format('HH:mm:ss')
}
</script>

<template>
<div   :style='`background-image: url("/assets1/bannerpic/${menuPanna}.jpg") ;`' style="transition: all 0.5s;margin-top: -56px; background-repeat: no-repeat;background-size: cover">
<div  :class="store.darkT?'boxdark':'box'" style="backdrop-filter: blur(20px);padding-top: 56px;padding-bottom: 10px">


  <t-layout v-if="store.loginUser!=null" style="background-color: #00000000 ;width: 94%;margin: 0 auto;padding: 12px 0;min-width: 500px">
    <t-aside   :class="store.darkT?'boxdark':'box'" width="19%" style="overflow: hidden; border-radius: 12px">
      <!--  屏幕比例宽高    height: 40vh;-->
      <div class="">
        <t-progress theme="circle" align="center" style="margin-top: 10%;" :percentage="Percentage(presentDataRight,presentData.length)"/>
        <t-row style="text-align: center;margin-top: 10%" justify="space-around">

          <t-col :span="5">
            <h2 style="line-height: 1px;margin-bottom: 12px">{{presentDataDeduplication.length}}</h2>
            实际答题数量
          </t-col>


          <t-col :span="5">
            <h2 style="line-height: 1px;margin-bottom: 12px">{{ presentData.length }}</h2>
            本日答题数量
          </t-col>
        </t-row>
        <t-row style="text-align: center;margin-top: 4%" justify="space-around">
          <t-col :span="5">
            <h2 style="line-height: 1px;margin-bottom: 12px">{{ getStudyTime()}}</h2>
            学习时长
          </t-col>

          <t-col :span="5">
            <h2 style="line-height: 1px;margin-bottom: 12px">{{ Percentage(presentDataRight,presentData.length) }}%</h2>
            答题正确率
          </t-col>

        </t-row>
      </div>


      <!--        <nut-grid :column-num="2" style=" width: 90%;margin: 20px auto;">-->
      <!--          <nut-grid-item text="学习时长"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>-->
      <!--          <nut-grid-item text="本日答题数量"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>-->
      <!--          <nut-grid-item text="实际答题数量"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>-->
      <!--          <nut-grid-item text="答题正确率"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>-->
      <!--        </nut-grid>-->


    </t-aside>
    <t-content style="width: 60% ;user-select: none;margin: 0 1%">
      <t-swiper v-model:current="menuPanna"  class="tdesign-demo-block--swiper" style="border-radius: 12px;overflow: hidden;" :duration="300"
                :interval="2000">
        <t-swiper-item

            :style='`background-image: url("/assets1/bannerpic/${menuPanna}.jpg") ;`'
            style="background-position: center;background-repeat: no-repeat;background-size: cover;aspect-ratio: 2.2"
            v-for="item in 6" :key="item">
          <div style="height: 44vh;">

          </div>
          <!--            <img name='img' style="width: 100% ;-->
          <!--object-fit: cover;" src="../assets/bannerpic/4.jpg" draggable="false" alt="">-->
        </t-swiper-item>

      </t-swiper>
    </t-content>
    <t-aside   :class="store.darkT?'boxdark':'box'" width="19%" style="max-height: 44vh;overflow: auto;border-radius: 12px">


      <h3 style="margin-left: 20px; line-height: 12px;margin-bottom: 4px">考试</h3>
      <div style="padding: 10px;">

        <t-collapse style="background-color: #00000000;border-radius: 12px;max-height: 34vh;overflow: auto "  expand-icon-placement="right" >
          <t-collapse-panel v-if="store.examList.length!=0" v-for="item in store.examList" :header="item.papername">
            <div>开始时间：
              <br>
              {{dayjs(item.starttime).format('YY年MM月DD日 HH:mm') }}

              <br>
              总分：{{ item.totalscore }}</div>
            <t-button v-if="dayjs(item.starttime).valueOf()+item.duration*1000*60< now.valueOf()" theme="warning"
                      :disabled="true" :variant="store.darkT?'':'outline'" shape="round">已结束
            </t-button>
            <t-button v-else-if="dayjs(item.starttime).valueOf()< now.valueOf()" @click="goToExam(item)"
                      :ghost="!store. darkT" shape="round">去考试
            </t-button>
            <span v-else><nut-countdown style="display: inline"
                                        @click="notStart"
                                        :end-time="dayjs(item.starttime).valueOf()"></nut-countdown></span>
          </t-collapse-panel>
      <div style="line-height: 200px;user-select: none;text-align: center" v-else>
          暂时还没有你需要参加的考试哦
      </div>
        </t-collapse>

        <!--            <t-list stripe style="border-radius: 10px;box-shadow: 0 0 1px 0 rgba(121,121,121,0.51);font-size: 10px"  >-->
        <!--              <t-list-item v-for="item in store.examList"  >-->
        <!--                <t-list-item-meta    :title="item.papername" />-->
        <!--                <template #action>-->

        <!--                </template>-->
        <!--              </t-list-item>-->
        <!--            </t-list>-->

      </div>


      <!--      <Typer style="text-align: center;margin: 10px 2%  "/>-->
    </t-aside>


  </t-layout>
  <t-layout v-else style="background-color: #00000000 ;width: 94%;margin: 0 auto;padding: 12px 0;min-width: 500px">
     <t-content style="  user-select: none; ">
      <t-swiper v-model:current="menuPanna"  class="tdesign-demo-block--swiper" style="border-radius: 12px;overflow: hidden;" :duration="300"
                :interval="2000">
        <t-swiper-item

            :style='`background-image: url("/assets1/bannerpic/${menuPanna}.jpg") ;`'
            style="background-position: center;background-repeat: no-repeat;background-size: cover;aspect-ratio: 2.2"
            v-for="item in 6" :key="item">
          <div style="height: 300px;">

          </div>
<!--                      <img name='img' style="width: 100% ;-->
<!--     object-fit: cover;" src="../assets/bannerpic/4.jpg" draggable="false" alt="">-->
        </t-swiper-item>

      </t-swiper>
    </t-content>
  </t-layout>

  <t-card  :class="store.darkT?'boxdark':'box'" size="small"
           :bordered="false"
          style="min-height: 40px;display: block;text-align: center;margin: 0 auto;width: 94%;border-radius: 12px;overflow: hidden;">
    <t-tag theme="primary" STYLE="margin-right: 10PX" variant="outline">一言</t-tag><Typer style="display: inline-block" />
  </t-card>
  <div style="display: block;margin: 10px auto;width: 94%;border-radius: 12px;overflow: hidden;">
<!--    <iframe style="width: 100%;height: 80vh;margin-bottom: -10px" src="//player.bilibili.com/player.html?aid=1201876206&bvid=BV1ZF4m1c7M8&cid=1472052937&p=1" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"> </iframe>-->
    <nut-video :source="source" :options="videoOptions"></nut-video>
  </div>




</div>
</div>

  <!--  <video style="display: block;margin: 10px auto;width: 94%;border-radius: 12px" autoplay loop muted src="https://download.cloud.189.cn/file/downloadFile.action?dt=71&expired=1702957205937&sid=1244418625150&sk=9623b4ea-9c16-4f23-8368-71e44d4d24ff&ufi=524501106662423003&zyc=180&token=cloud16&sig=75f9TJJ6OSKWGIbQ4sM0h%2FmfQos%3D"></video>-->


</template>
<style scoped>
.box {
   background-color: rgba(255, 255, 255, .48);

}

.boxdark {
  background-color: rgba(0, 0, 0, .48);
}
</style>