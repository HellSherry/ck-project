<script setup>
import {useCounterStore} from '@/stores/counter'
import router from "@/router";
import {onUnmounted, reactive, ref, watch} from "vue";
import {onBeforeRouteLeave} from "vue-router";
import {DialogPlugin, MessagePlugin} from "tdesign-vue-next";
import service from "@/axios/service";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";
import AnswerUI from "@/components/ui/AnswerUI.vue";
import dayjs from "dayjs";

import {useRoute} from 'vue-router';

const route = useRoute()
//从路由 中获取id
const store = useCounterStore();
const score = ref(-1);
const loading = ref(false);
console.log(route.query)

const id = route.query.id
const examPaperInformation = ref([]);
const examQuestions = ref([])
const practice = reactive(
    {
      "starttime": dayjs().format('YYYY-MM-DD HH:mm:ss'),
      "duration": 0,
      "name": "试题",
      "userid": store.loginUser == null ? 0 : store.loginUser.sub.userId
    }
)
setInterval(() => {
  practice.duration += 3
}, 3000)

//
// service.get("/exam/getExamById?paperId=" + id).then((res) => {
//   examPaperInformation.value = res.data
//   sessionStorage.setItem(`exam-${id}`, JSON.stringify(examPaperInformation.value))
//
// })
switch (route.query.method) {
  case 'order':
    service.get("/practice/inOrder?groupId=" + route.query.groupId).then((res) => {

      examQuestions.value = res.data
      initAnwer();
      service.get("/questionGroup/" + route.query.groupId).then((resp) => {
        practice.name = resp.data.name + "测试题";
      })
    })
    break;
    case 'random':
      service.get("/practice/inRandom?groupId=" + route.query.groupId).then((res) => {
        examQuestions.value = res.data
        initAnwer();
        service.get("/questionGroup/" + route.query.groupId).then((resp) => {
          practice.name = resp.data.name + "测试题";
        })
      })
      break;
  case 'course':
    service.get("/practice/inCourseId?courseId=" + route.query.courseId+"&num="+route.query.num).then((res) => {
      examQuestions.value = res.data
      initAnwer();
      service.get("/questionCourse/" + route.query.courseId).then((resp) => {
        practice.name = route.query.num>20?resp.data.name + "模拟测试": resp.data.name + "复习题";

      })
    })
    break;
  case 'again':
    service.get("/practice/again?pricaticeid=" + route.query.pricaticeid).then((res) => {
      examQuestions.value = res.data
      initAnwer();
      service.get("/practice/getInfoByID?pricaticeid=" + route.query.pricaticeid).then((resp) => {
        practice.name = resp.data.name + "-重做";
      })
    })
        break;
  case 'againExam':
    service.get("/practice/againExam?paperid=" + route.query.paperid).then((res) => {
      examQuestions.value = res.data
      initAnwer();
      service.get("/exam/getExamById?paperId=" + route.query.paperid).then((resp) => {
        practice.name = resp.data.papername + "-重做";
      })
    })
    break;
}


const answerList = ref([]);
const rightNum = ref(0);

function initAnwer() {
  for (let i = 0; i < examQuestions.value.length; i++) {
    answerList.value.push([])
  }
}

initAnwer();


const value = ref(1);

var sss;

function notcutTheScreen() {
  if (answerList.value.join('').trim() != '' && score.value === -1) {
    const elementById = document.getElementById("audioController");
    elementById.src = '/assets1/audio/' + (Math.random() > 0.5 ? '3360' : '4397') + '.wav'
    elementById.play()
  }
}


function scoreTo(index) {
  document.getElementById(`answer${index}`).scrollIntoView({behavior: "smooth", block: "center"})
}

const times = ref({});

function questionNum() {
  let i = 0;
  answerList.value.forEach(
      (e) => {
        if (e.length != 0) {
          i++
        }
      }
  )
  return i
}

function submitExam() {
  loading.value = true;
  let tempAnswerList = [];
  answerList.value.forEach((item, index) => {

    if (item.length === 0) {

    } else {
      if (item.sort().join(',') === examQuestions.value[index].ranswer) {
        rightNum.value++;
      }
      tempAnswerList.push(examQuestions.value[index].id + '@' + item.sort().join(','))
    }
  })
  service.post('/practice/submit?answers=' + tempAnswerList.join('-'), practice).then(
      (r) => {

        score.value = r.data;
      }
  ).finally(
      () => {
        loading.value = false;

      }
  )
}

function toSubmit() {
  const confirmDia = DialogPlugin({
    header: '提示',
    body: '您确认交卷吗？',
    confirmBtn: '提交考卷',
    cancelBtn: '继续做题',
    onConfirm: ({e}) => {
      console.log('confirm button has been clicked!');
      console.log('e: ', e);
      submitExam();
      confirmDia.hide();
    },
    onClose: ({e, trigger}) => {
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

        <t-layout>
          <t-aside style="height: 86vh;border-right: 1px solid var(--td-text-color-primary)">
            <div>
              <img src="../../assets/image/1.png" style='width: 92%;margin-top: 30%;margin-bottom: 20%'
                   class="report_img">
              <br>
<!--              <t-button size="large" shape="round" style="  width: 92%" @click="toreport">-->
<!--                查看报告-->
<!--              </t-button>-->
              <t-button size="large" shape="round" style=" margin: 12px 0;width: 92%" @click="$router.push('/practice/history/'+score)">
                查看解析
              </t-button>
              <!--    <div><button class="report_but" @click="toreport">查看报告</button></div>-->
              <!--    <div><button class="report_but" @click="toWrong">查看解析</button></div>-->
            </div>
          </t-aside>
          <t-content style="background-color: var(--td-bg-color-container)">


            <div class="has-bottom-border-Two">

              <div style="display: inline-block; vertical-align: middle ;margin-top: -10px  ">
                <div class="has-bottom">共{{ answerList.length }}题,你答对了</div>
                <div style="text-align: center; margin-top: 18px">
                  <span style="font-size: 40px;">{{ rightNum }}</span>
                </div>
              </div>
              <div class="has-bottom-Two"
                   style="vertical-align: middle;display: inline-block;margin-left: 20px;width: 300px">
                <div class="has_report">
                  <span>考试时间:{{ practice.starttime }}</span>
                </div>
                <div class="has_report_Two">
                  <t-space :size="6"
                           style=" user-select: none;margin-left: 7%;max-height: 50vh;overflow: auto;margin-bottom: 28px"
                           break-line>
                    <t-button v-for="(item,index) in answerList" style="border-radius: 12px"
                              :variant="item.length>0?'base':'dashed'"
                              :theme=" item.toString()==examQuestions[index].ranswer.toString()?'success':'danger' "
                              shape="square"

                    >{{ index + 1 }}
                    </t-button>
                  </t-space>
                </div>
              </div>
            </div>

          </t-content>
        </t-layout>


      </t-card>
    </div>
  </div>

  <t-layout v-else>
    <t-aside style="overflow: hidden; width: 300px">
      <!--<div style="height: 300px; background-color: #ffd8d2;-->
      <!--position:relative;-->
      <!--  top:20%">-->
      <t-progress size="large" class="aline-center" style="width: 160px;margin: 10% auto 10px auto"
                  :percentage="  parseInt((questionNum()/answerList.length)*100)" theme="circle">
        <template #label>
          <h3 style="font-size: 28px" v-if="answerList.length==0">
            加载中...
          </h3>
          <!--          <nut-countdown @ended="submitExam" style="display: inline;user-select: none; font-size: 28px"  :end-time="dayjs(examPaperInformation.starttime).valueOf() +examPaperInformation.duration*60*1000"  v-model="times" />-->
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

      <t-space :size="12" style=" margin-left: 7%;max-height: 50vh;overflow: auto" break-line>
        <t-button style="border-radius: 12px" :variant=" item.length>0?'base':'dashed'" theme="primary" shape="square"
                  @click="scoreTo(index)" v-for="(item,index) in answerList">{{ index + 1 }}
        </t-button>
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
        <t-card v-for="(item, index) in examQuestions"

                :id="`answer${index}`"
                :key="index"
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
}">
            选择 {{ item.ranswer.split(',').length }} 项
          </t-check-tag>
          <!--          {{ index + 1 }}.-->
          <ToHtml style="overflow: hidden;width: 90%;margin: 0 auto;  font-weight: 600;" v-html=" item.question"/>
          <!--          {{item.ranswer}}-->
          <table :ref="`tableRef[${index}]`">

          </table>

          <!--          <table>-->
          <!--            <tr>-->
          <!--              <td>-->


          <!--&lt;!&ndash;                {{ item.question }}&ndash;&gt;-->
          <!--&lt;!&ndash;                ({{ item.longestScore }})&ndash;&gt;-->
          <!--              </td>-->
          <!--            </tr>-->
          <!--            <tr>-->
          <!--              <td v-if="item.longestScore == '单选题'">-->
          <!--                <input type="radio" :value="1" v-model="answerList[index]" />-->
          <!--                {{ item.answer1 }}-->
          <!--              </td>-->
          <!--              <td v-if="item.longestScore == '多选题'">-->
          <!--                <input type="checkbox" :value="'1' + '$' + index" v-model="answerList" />-->
          <!--                {{ item.answer1 }}-->
          <!--              </td>-->
          <!--            </tr>-->
          <!--            <tr>-->
          <!--              <td v-if="item.longestScore == '单选题'">-->
          <!--                <input type="radio" :value="2" v-model="answerList[index]" />-->
          <!--                {{ item.answer2 }}-->
          <!--              </td>-->
          <!--              <td v-if="item.longestScore == '多选题'">-->
          <!--                <input type="checkbox" :value="'2' + '$' + index" v-model="answerList" />-->
          <!--                {{ item.answer2 }}-->
          <!--              </td>-->
          <!--            </tr>-->
          <!--            <tr>-->
          <!--              <td v-if="item.longestScore == '单选题'">-->
          <!--                <input type="radio" :value="3" v-model="answerList[index]" />-->
          <!--                {{ item.answer3 }}-->
          <!--              </td>-->
          <!--              <td v-if="item.longestScore == '多选题'">-->
          <!--                <input type="checkbox" :value="'3' + '$' + index" v-model="answerList" />-->
          <!--                {{ item.answer3 }}-->
          <!--              </td>-->
          <!--            </tr>-->
          <!--            <tr>-->
          <!--              <td v-if="item.longestScore == '单选题'">-->
          <!--                <input type="radio" :value="4" v-model="answerList[index]" />-->
          <!--                {{ item.answer4 }}-->
          <!--              </td>-->
          <!--              <td v-if="item.longestScore == '多选题'">-->
          <!--                <input type="checkbox" :value="'4' + '$' + index" v-model="answerList" />-->
          <!--                {{ item.answer4 }}-->
          <!--              </td>-->
          <!--            </tr>-->
          <!--          </table>/-->
          <!--          <div :style="answerList[index]==null||answerList[index]==''?'':'background-color: var(&#45;&#45;td-brand-color-4);' "-->
          <!--               class="subscribe">-->
          <!--            <div-->
          <!--                :style="'position: absolute;left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(&#45;&#45;td-brand-color-'+(answerList[index]==null||answerList[index]==''?'8':'5')+');'"-->
          <!--                class="sdf"></div>-->
          <!--            Topic {{ (index + 1 + '').padStart(2, "0") }}-->
          <!--          </div>-->
          <!--          <t-check-tag style="float: right;margin-top: 4px" :checked="true" :checked-props="{-->
          <!--  theme: 'primary',-->
          <!--  variant: 'outline',-->
          <!--}">选择 {{ item[2] }} 项-->
          <!--          </t-check-tag>-->

          <!--          <br>-->
          <!--{{item.ranswer}}-->
          <AnswerUI :answerLen="(item.ranswer.length+1)/2" style="overflow: hidden;width: 90%;margin: 30px auto;"
                    :checkbox="false"
                    v-model="answerList[index]" :options="JSON.parse(item.answers)"/>
        </t-card>

        <!--        <t-card v-for="(item,index) in examQuestions"-->
        <!--                :id="`answer${index}`"-->
        <!--                style="width: 80%;margin:20px auto;min-width: 100px;max-width: 800px" :bordered="false" hoverShadow-->
        <!--                size="small" theme="poster2">-->
        <!--          <div :style="answerList[index]==null||answerList[index]==''?'':'background-color: var(&#45;&#45;td-brand-color-4);' "-->
        <!--               class="subscribe">-->
        <!--            <div-->
        <!--                :style="'position: absolute;left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(&#45;&#45;td-brand-color-'+(answerList[index]==null||answerList[index]==''?'8':'5')+');'"-->
        <!--                class="sdf"></div>-->
        <!--            Topic {{ (index + 1 + '').padStart(2, "0") }}-->
        <!--          </div>-->
        <!--          <t-check-tag style="float: right;margin-top: 4px" :checked="true" :checked-props="{-->
        <!--  theme: 'primary',-->
        <!--  variant: 'outline',-->
        <!--}">选择 {{ item[2] }} 项-->
        <!--          </t-check-tag>-->

        <!--          <br>-->
        <!--          <ToHtml style="overflow: hidden;width: 90%;margin: 0 auto;  font-weight: 600;" v-html="item.question"/>-->

        <!--          <AnswerUI :answerLen="item[2]" style="overflow: hidden;width: 90%;margin: 30px auto;" :checkbox="false"-->
        <!--                    v-model="answerList[index]" :options="JSON.parse(item.answers)"/>-->
        <!--        </t-card>-->
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
      <div v-if="answerList.length===0" style="text-align: center">

        <h1 style="font-size: 60px;line-height: 80px">
          <br>
          Sorry,题目正在加载中
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
  <t-loading :loading="loading" text="正在提交中..." fullscreen/>

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

.has-bottom-border-Two {
  max-width: 540px;
  margin: 20% auto;
  min-height: 140px;
  text-align: center;
  border-top: 1px solid rgb(206, 202, 202);
  border-bottom: 1px solid rgb(206, 202, 202);
  border-left: 1px solid rgb(206, 202, 202);
  border-right: 1px solid rgb(206, 202, 202);
}

.has_report_Two {

  display: inline-block;
  margin-top: 20px;
}

.has_report {

  border-bottom: 1px solid rgb(206, 202, 202);
}

.has-bottom-Two {
  transform: translateY(10%);
}

.has-bottom {
  margin-top: 30px;
  border-bottom: 1px solid rgb(201, 198, 198);
}

.centre-div-bottom div {
  display: flex;
  align-items: center;
}
</style>