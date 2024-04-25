<script setup>
import {useCounterStore} from '@/stores/counter'

const store = useCounterStore();
const score = ref(-1);
const loading = ref(false);
import router from "@/router";
import AnswerSheets from "@/components/ui/TimeCountDown.vue";
import {onUnmounted, ref, watch} from "vue";
import {onBeforeRouteLeave} from "vue-router";
import {DialogPlugin, MessagePlugin} from "tdesign-vue-next";
import service from "@/axios/service";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";
import AnswerUI from "@/components/ui/AnswerUI.vue";
import dayjs from "dayjs";
//从路由 中获取id
const id = router.currentRoute.value.params.id
const examPaperInformation = ref([]);
const examQuestions = ref([])
if (sessionStorage.getItem(`exam-${id}-${store.loginUser.sub.userId}-score`) != null) {
score.value=sessionStorage.getItem(`exam-${id}-${store.loginUser.sub.userId}-score`)
}else {
  if (sessionStorage.getItem(`exam-${id}`) != null) {
    examPaperInformation.value = JSON.parse(sessionStorage.getItem(`exam-${id}`));
  }
  else
  {
    service.get("/exam/getExamById?paperId=" + id).then((res) => {
      examPaperInformation.value = res.data
      sessionStorage.setItem(`exam-${id}`, JSON.stringify(examPaperInformation.value))
      timeCheck();
    })
  }

  if (sessionStorage.getItem(`exam-${id}-${store.loginUser.sub.userId}`) != null) {
    examQuestions.value = JSON.parse(sessionStorage.getItem(`exam-${id}-${store.loginUser.sub.userId}`));
  } else {
    service.get("/exam/getPaper?paperId=" + id).then((res) => {

      if (typeof res.data ==='object'){
        examQuestions.value = res.data
        sessionStorage.setItem(`exam-${id}-${store.loginUser.sub.userId}`, JSON.stringify(examQuestions.value))
        initAnwer();
      }else {
        score.value = res.data
        sessionStorage.setItem(`exam-${id}-${store.loginUser.sub.userId}-score`, JSON.stringify(score.value))

      }

    })
  }
}

timeCheck();
function timeCheck() {
  if ( examPaperInformation.value.length!==0){
    if (dayjs(examPaperInformation.value.starttime).valueOf() + examPaperInformation.value.duration * 1000 * 60 < dayjs().valueOf()) {
      MessagePlugin.error( '本场考试已经结束了哦')
      sessionStorage.clear();
    }
    if (dayjs(examPaperInformation.value.starttime).valueOf()>dayjs().valueOf()){
      MessagePlugin.error( '本场考试未开始')
      const elementById = document.getElementById("audioController");
      elementById.src='/assets1/audio/2869.wav'
      elementById .play()
    }
  }

}

const answerList = ref([]);

function initAnwer() {
  if (sessionStorage.getItem(`exam-${id}-${store.loginUser.sub.userId}-answer`) != null) {
    answerList.value = JSON.parse(sessionStorage.getItem(`exam-${id}-${store.loginUser.sub.userId}-answer`));
  } else {
    for (let i = 0; i < examQuestions.value.length; i++) {
      answerList.value.push([])
    }
  }
}

initAnwer();



const value = ref(1);

onBeforeRouteLeave(() => {
  if (answerList.value.join('').trim() != ''&&score.value===-1) {
    MessagePlugin.error('正在考试中…')
    history.pushState()
  }

})
watch(answerList.value, (val) => {
  sessionStorage.setItem(`exam-${id}-${store.loginUser.sub.userId}-answer`, JSON.stringify(val))
})

var sss;
function notcutTheScreen() {
  if (answerList.value.join('').trim() != ''&&score.value===-1) {
  const elementById = document.getElementById("audioController");
  elementById.src='/assets1/audio/'+(Math.random()>0.5?'3360':'4397')+'.wav'
  elementById .play()
  }
}
const leave = () => {
  sss !== undefined && window.clearTimeout(sss);
  notcutTheScreen()
  document.title = '用户切屏啦';

};

const enter = () => {
  sss !== undefined && window.clearTimeout(sss);
  notcutTheScreen()

  document.title = '用户回来啦';
  sss = setTimeout(() => {
    document.title = '北大青鸟长沙创科校区'
  }, 3000)
};

window.addEventListener('blur', leave);
window.addEventListener('focus', enter);
onUnmounted(() => {
  window.removeEventListener('blur', leave)
  window.removeEventListener('focus', enter)
})
function scoreTo(index) {
  document.getElementById(`answer${index}`).scrollIntoView({behavior: "smooth", block: "center"})
}
const times = ref({});
function submitExam() {
  loading.value = true;
let tempAnswerList = [];
  answerList.value.forEach((item, index) => {

    if (item.length === 0) {
      tempAnswerList.push(0)
    }else {
      tempAnswerList.push(item.sort().join(','))
    }
  })
  service.post('/exam/calculateTheScore?answers=' + tempAnswerList.join('-') + '&paperId=' + id).then(
      (r)=>{
        score.value = r.data.toFixed(1)
        MessagePlugin.success( "成绩："+ score.value)
        sessionStorage.setItem(`exam-${id}-${store.loginUser.sub.userId}-score`, JSON.stringify(score.value))
        console.log(r)
      }
  ).finally(
      ()=>{
        loading.value = false;

      }
  )
  console.log(tempAnswerList.join("-"))
}
function toSubmit() {
  const confirmDia = DialogPlugin({
    header: '提示',
    body: '您确认交卷吗？',
    confirmBtn: '提交考卷',
    cancelBtn: '继续做题',
    onConfirm: ({ e }) => {
      console.log('confirm button has been clicked!');
      console.log('e: ', e);
      submitExam();
      confirmDia.hide();
    },
    onClose: ({ e, trigger }) => {
      console.log('e: ', e);
      console.log('trigger: ', trigger);
      confirmDia.hide();
    },
  });
}


</script>

<template>


  <div v-if="score!==-1" style="background-position: center"
       :style="`background-image: url('/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg');background-repeat: no-repeat;background-size: cover;  `">
    <div style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 1.6vh;padding-bottom: 10px">
      <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto; min-height: 90vh">

        <div  style=" font-size: 20PX;width: 460px;margin: 0 auto;margin-top: 12vh;">
             <nut-animate type="jump" :show="true">
              <h2 style="user-select: none;margin-top: 20vh;font-size: 28px"> 你的考试成绩是<span style="font-size: 60px">{{score}}</span>分
              </h2>
               <p>为防止泄题，本场考试结束结束后可以查看答案！</p>
            </nut-animate>


        </div>


      </t-card>
    </div>
  </div>

  <t-layout  v-else >
    <t-aside style="overflow: hidden; width: 300px">
      <!--<div style="height: 300px; background-color: #ffd8d2;-->
      <!--position:relative;-->
      <!--  top:20%">-->
      <t-progress size="large" class="aline-center" style="width: 160px;margin: 10% auto 10px auto" :percentage="(times.s+(times.m+(times.h*60))*60)*(100/(examPaperInformation.duration*60))" theme="circle"  >
        <template #label>

          <nut-countdown @ended="submitExam" style="display: inline;user-select: none; font-size: 28px"  :end-time="dayjs(examPaperInformation.starttime).valueOf() +examPaperInformation.duration*60*1000"  v-model="times" />
        </template>

      </t-progress>
      <!--</div>-->
<!--      <nut-circle-progress style="margin: 4% auto;text-align: center"  :color="{-->
<!--      '0%':  'rgba(44,142,255,0.7)',-->
<!--  '100%':  '#2c68ff',-->
<!--  }" :progress="(times.s+(times.m+(times.h*60))*60)*(100/(examPaperInformation.duration*60))"  >  <nut-countdown style="display: inline;user-select: none;"  :end-time="dayjs(examPaperInformation.starttime).valueOf() +examPaperInformation.duration*60*1000"  v-model="times" /></nut-circle-progress>-->
      <t-button size="large" shape="round" style=" margin: 20px 4%;width: 92%" @click="toSubmit">
        我要交卷
      </t-button>

        <t-space  :size="12" style=" margin-left: 7%;max-height: 50vh;overflow: auto"  break-line>
          <t-button   style="border-radius: 12px" :variant=" item.length>0?'base':'dashed'" theme="primary" shape="square" @click="scoreTo(index)" v-for="(item,index) in answerList" >{{index+1}}</t-button>
          <!--              <span :style="{-->
          <!--        'background-color': item.length>0?'green':'red',-->
          <!--              }" style="width: 20px;height: 20px;display: inline-block" v-for="(item,index) in answerList">{{index+1}}</span>-->
        </t-space>






      <div>

      </div>
    </t-aside>
    <t-content style="overflow:  auto; height: 100vh;margin-top: -56px">
      <div style="height: 56px">

      </div>
      <!--      {{examQuestions}}-->
      <t-space v-if="true" style="width: 100%;" direction="vertical">
        <t-card v-for="(item,index) in examQuestions"
                :id="`answer${index}`"
                style="width: 80%;margin:20px auto;min-width: 100px;max-width: 800px" :bordered="false" hoverShadow
                size="small" theme="poster2">
          <div :style="answerList[index]==null||answerList[index]==''?'':'background-color: var(--td-brand-color-4);' "
               class="subscribe">
            <div
                :style="'position: absolute;left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(--td-brand-color-'+(answerList[index]==null||answerList[index]==''?'8':'5')+');'"
                class="sdf"></div>
            Topic {{ (index + 1 + '').padStart(2, "0") }}
          </div>
          <t-check-tag style="float: right;margin-top: 4px" :checked="true" :checked-props="{
  theme: 'primary',
  variant: 'outline',
}">选择 {{ item[2] }} 项
          </t-check-tag>

          <br>
          <ToHtml style="overflow: hidden;width: 90%;margin: 0 auto;  font-weight: 600;" v-html="item[0]"/>

          <AnswerUI :answerLen="item[2]" style="overflow: hidden;width: 90%;margin: 30px auto;" :checkbox="false"
                    v-model="answerList[index]" :options="JSON.parse(item[1])"/>
        </t-card>
      </t-space>
      <div v-else>

        <div style="overflow: hidden">
          <t-tabs v-model="value" style="margin-top: -48px;background-color: rgba(255,255,255,0)" :default-value="1">
            <t-tab-panel style="" :value="index+1" :label="index+1" v-for="(item,index) in examQuestion">
              <t-card style="width: 80%;margin:20px auto;min-width: 100px;max-width: 800px" :bordered="false" shadow
                      size="small" theme="poster2">
                <div
                    :style="answerList[index]==null||answerList[index]==''?'':'background-color: var(--td-brand-color-4);' "
                    class="subscribe">
                  <div
                      :style="'position: absolute;left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(--td-brand-color-'+(answerList[index]==null||answerList[index]==''?'8':'5')+');'"
                      class="sdf"></div>
                  Topic {{ (index + 1 + '').padStart(2, "0") }}
                </div>
                <div style="float: right;">
                  <t-check-tag :checked="true" :checked-props="{
  theme: 'primary',
  variant: 'outline',
}">选择一项
                  </t-check-tag>
                  <button :disabled="value<=1" @click="value--">上一题</button>
                  <button :disabled="value>=examQuestion.length" @click="value++">下一题</button>
                </div>

                <br>
                <ToHtml style="overflow: hidden;width: 90%;margin: 0 auto;  font-weight: 600;" v-html="item.question"/>

                <AnswerUI :answerLen="4" style="overflow: hidden;width: 90%;margin: 30px auto;" :checkbox="false"
                          v-model="answerList[index]" :options="[item.answer1,item.answer2,item.answer3,item.answer4]"/>
              </t-card>
            </t-tab-panel>

          </t-tabs>

        </div>

      </div>
      <div v-if="answerList.length===0"  style="text-align: center">

        <h1 style="font-size: 60px;line-height: 80px">
          502 <br>
          Sorry,你暂时没有本场考试权限
        </h1>
        <a href="/">clickhere to home</a>

      </div>

      <t-footer>
      <span style="text-align: center">
              <p>长沙创科职校   0731-8455-6741 | 长沙市芙蓉区</p>

      <p> 湘ICP备2023020435号| Copyright © icloud All rights reserved</p>
      </span>
      </t-footer>
    </t-content>


  </t-layout>
  <t-loading :loading="loading" text="正在提交中..." fullscreen />

</template>

<style scoped>


.sdf {
  position: absolute;
  content: "";
  left: 1px;
  top: 46px;
  width: 0;
  height: 0;
  border-right: 0px solid transparent;
  border-left: 9px solid transparent;
  border-top: 9px solid var(--td-brand-color-8);
}


.subscribe {
  position: relative;

  border-bottom-right-radius: 30px;
  border-top-right-radius: 30px;
  background-color: var(--td-brand-color-6);
  color: #FFF;
  padding: 12px 25px;
  display: inline-block;
  font-size: 12px;
  font-weight: bold;
  letter-spacing: 5px;
  left: -26px;
  top: -20px;
  cursor: pointer;
  transition: all .25s ease;
  box-shadow: -5px 6px 20px 0px rgba(26, 26, 26, 0.4);
}

.subscribe:hover {
  background-color: var(--td-brand-color-8);
  box-shadow: -5px 6px 20px 0px rgba(88, 88, 88, 0.569);
}
</style>