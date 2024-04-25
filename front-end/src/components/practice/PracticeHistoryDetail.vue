<script setup>
import service from "@/axios/service";
import router from "@/router";
import {reactive, ref} from "vue";
import AnswerUI from "@/components/ui/AnswerUI.vue";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";
import dayjs from "dayjs";
import duration from "dayjs/plugin/duration";

dayjs.extend(duration);
const practice = ref({});

function scoreTo(index) {
  document.getElementById(`answer${index}`).scrollIntoView({block: "center"})
}

const id = router.currentRoute.value.params.id
service.get("/practice/getInfoByID?id=" + id).then((res) => {
    practice.value = res.data
   })

const analysis = ref();
const rightNum = ref(0);
console.log(id)
service.get("/practice/getDetailsByID?id=" + id).then((res) => {
  console.log(res)
  analysis.value = res.data
  analysis.value.forEach(item => {
    item.answers = JSON.parse(item.answers)
    item.ranswer = item.ranswer.split(',')
    item.answer = item.answer.split(',')
    if (item.ranswer.toString() ==item.answer.toString()){
      rightNum.value++
    }
  })
})

function toABC(item) {
  switch (item) {
    case '0':
      return '未选择'
    case '1':
      return 'A'
    case '2':
      return 'B'
    case '3':
      return 'C'
    case '4':
      return 'D'
    case '5':
      return 'E'
    case '6':
      return 'F'
    case '7':
      return 'G'
    case '8':
      return 'H'
    case '9':
      return 'I'
    case '10':
      return 'J'
    default:
      return item;


  }
}
</script>

<template>


  <t-layout v-if=" analysis !=null">
    <t-aside style="overflow: hidden; width: 300px">



      <t-progress size="large" class="aline-center" style="width: 160px;margin: 10% auto  " :percentage="(rightNum/analysis.length)*100"
                  theme="circle">
        <template #label>


             <h3 style="font-size: 28px" v-if="analysis.length==0">
              加载中...
            </h3>
            <!--          <nut-countdown @ended="submitExam" style="display: inline;user-select: none; font-size: 28px"  :end-time="dayjs(examPaperInformation.starttime).valueOf() +examPaperInformation.duration*60*1000"  v-model="times" />-->


          <div v-else style="user-select: none">
            <p style="line-height: 10px">{{rightNum}}题</p>

            <p style="font-size: 20px;line-height: 0px">{{ dayjs.duration(  practice.duration*1000).format("mm:ss") }}</p>
          </div>

          <!--          -->
        </template>

      </t-progress>
      <!--        <t-button size="large" shape="round" style=" margin: 20px 4%;width: 92%" @click="toSubmit">-->
      <!--        我要交卷-->
      <!--      </t-button>-->

      <t-space :size="12" style=" user-select: none;margin-left: 7%;max-height: 50vh;overflow: auto" break-line>

        <t-button v-for="(item,index) in analysis" style="border-radius: 12px"
                  :variant="item.answer[0]!=0?'base':'dashed'"
                  :theme=" item.answer.toString()==item.ranswer.toString()?'success':'danger' " shape="square"
                  @click="scoreTo(index)">{{ index + 1 }}
        </t-button>

      </t-space>


      <div>

      </div>
    </t-aside>
    <t-content style="overflow:  auto; height: 100vh;margin-top: -56px">
      <div style="height: 56px">

      </div>
      <!--      {{examQuestions}}-->
      <t-space style="width: 100%;" direction="vertical">
        <t-card       size="small"    style="width: 80%;margin:20px auto 0 auto;min-width: 100px;max-width: 800px" :bordered="false" hoverShadow>
          <h2 style="text-align: center;line-height: 6px;user-select: none"> {{
              practice.name
            }}</h2>        </t-card>
        <t-card v-for="(item,index) in analysis"
                :id="`answer${index}`"
                style="width: 80%;margin:20px auto;min-width: 100px;max-width: 800px;user-select: none" :bordered="false" hoverShadow
                size="small" theme="poster2">
          <div style="background-color: var(--td-brand-color-4);"
               class="subscribe">
            <div
                :style="'position: absolute;left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(--td-brand-color-8);'"
                class="sdf"></div>
            Topic {{ (index + 1 + '').padStart(2, "0") }}
          </div>
          <t-check-tag style="float: right;margin-top: 4px" :checked="true" :checked-props="{
  theme: 'primary',
  variant: 'outline',
}">选择 {{ item.ranswer.length }} 项
          </t-check-tag>

          <br>
          <ToHtml style="overflow: hidden;width: 90%;margin: 0 auto;  font-weight: 600;" v-html="item.question"/>


          <div style="overflow: hidden;width: 90%;margin: 30px auto;">
            <div v-for="(answer,index) in item.answers">
              <ToHtml
                  :style="((item.answer.some(ele => ele == index+1))&&!(item.ranswer.some(ele => ele == index+1)))? 'background-color:#FF000050;':((item.answer.some(ele => ele == index+1))&&(item.ranswer.some(ele => ele == index+1)))?'background-color:#366ef47a;':item.ranswer.some(ele => ele == index+1)?'background-color:#36f44970;':'  border: 1px solid var(--td-text-color-primary);'"
                  style=" border-radius: 14px;padding: 12px;margin-bottom: 12px"
                  v-html="answer"/>
            </div>
            正确答案：<span v-for="an in item.ranswer">{{ toABC(an) }}&nbsp;</span><br>

            你选择的：<span v-for="an in item.answer">{{ toABC(an) }}&nbsp;</span>&nbsp;
            <t-tag v-if="item.answer.toString()==item.ranswer.toString()" size="small" theme="success" variant="light-outline">回答正确</t-tag>
            <t-tag v-else theme="danger" variant="light-outline">回答错误</t-tag>
          </div>
          <t-divider style="border-color: var(--td-text-color-primary)" dashed/>
          <div style="padding:  0 30px 30px 30px">
            <h4>解析</h4>
            <ToHtml v-html="item.parse"/>
          </div>

        </t-card>
      </t-space>

      <t-footer>
      <span style="text-align: center">
              <p>长沙创科职校   0731-8455-6741 | 长沙市芙蓉区</p>

      <p> 湘ICP备2023020435号| Copyright © icloud All rights reserved</p>
      </span>
      </t-footer>
    </t-content>


  </t-layout>
  <div style="text-align: center;margin-top: 20%; font-size: 40px;font-weight: 400" v-else>
    你没有这场考试的权限
  </div>
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