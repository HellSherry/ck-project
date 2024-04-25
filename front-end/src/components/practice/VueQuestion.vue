<template>
  <t-loading :loading="loading" text="正在提交中..." fullscreen />

  <t-layout   >
    <t-aside style="overflow: hidden; width: 300px">
      <!--<div style="height: 300px; background-color: #ffd8d2;-->
      <!--position:relative;-->
      <!--  top:20%">-->
       <t-progress size="large" class="aline-center" style="width: 160px;margin: 10% auto 10px auto" :percentage="  parseInt((questionNum/selectedAnswer.length)*100)" theme="circle"  >
        <template #label>
<h3 style="font-size: 28px" v-if="selectedAnswer.length==0">
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
      <t-button size="large" shape="round" style=" margin: 20px 4%;width: 92%" @click="toTijiao">
        我要交卷
      </t-button>
<!--{{selectedAnswer}}-->
      <t-space  :size="12" style=" margin-left: 7%;max-height: 50vh;overflow: auto"  break-line>
<!--        <button class="but_topic" style="margin-left: 10px; width: 40px" v-for="(item, index) in topicList" :key="index"-->
<!--                :class="{ active: activeIndex === index }" @click="changeView(index + 1)">-->
<!--          {{ index + 1 }}-->
<!--        </button>-->
        <t-button   style="border-radius: 12px" :variant=" item!=''?'base':'dashed'" theme="primary" shape="square" @click="changeView(index + 1)" v-for="(item,index) in selectedAnswer" >{{index+1}}</t-button>
<!--                      <span :style="{-->
<!--                'background-color': item.length>0?'green':'red',-->
<!--                      }" style="width: 20px;height: 20px;display: inline-block" v-for="(item,index) in selectedAnswer">{{index+1}}</span>-->
      </t-space>






      <div>

      </div>
    </t-aside>
    <t-content style="overflow:  auto; height: 100vh;margin-top: -56px">
      <div style="height: 56px">

      </div>




      <!--            {{examQuestions}}-->

      <t-space v-if="true" style="width: 100%;" direction="vertical">
        <t-card   v-for="(item, index) in topicList" :key="index"
                style="width: 80%;margin:20px auto;min-width: 100px;max-width: 800px" :bordered="false" hoverShadow
                size="small" theme="poster2">

          <div :style="selectedAnswer[index]==null||selectedAnswer[index]==''?'':'background-color: var(--td-brand-color-4);' "
               class="subscribe">
            <div
                :style="'position: absolute;left: 1px;top: 46px;width: 0;height: 0;border-right: 0px solid transparent;border-left: 9px solid transparent;border-top: 9px solid var(--td-brand-color-'+(selectedAnswer[index]==null||selectedAnswer[index]==''?'8':'5')+');'"
                class="sdf"></div>
            Topic {{ (index + 1 + '').padStart(2, "0") }}
          </div><t-check-tag style="float: right;margin-top: 4px" :checked="true" :checked-props="{
  theme: 'primary',
 variant: 'outline',
}">
          {{ item.longestScore }}
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
<!--                <input type="radio" :value="1" v-model="selectedAnswer[index]" />-->
<!--                {{ item.answer1 }}-->
<!--              </td>-->
<!--              <td v-if="item.longestScore == '多选题'">-->
<!--                <input type="checkbox" :value="'1' + '$' + index" v-model="selectedAnswer" />-->
<!--                {{ item.answer1 }}-->
<!--              </td>-->
<!--            </tr>-->
<!--            <tr>-->
<!--              <td v-if="item.longestScore == '单选题'">-->
<!--                <input type="radio" :value="2" v-model="selectedAnswer[index]" />-->
<!--                {{ item.answer2 }}-->
<!--              </td>-->
<!--              <td v-if="item.longestScore == '多选题'">-->
<!--                <input type="checkbox" :value="'2' + '$' + index" v-model="selectedAnswer" />-->
<!--                {{ item.answer2 }}-->
<!--              </td>-->
<!--            </tr>-->
<!--            <tr>-->
<!--              <td v-if="item.longestScore == '单选题'">-->
<!--                <input type="radio" :value="3" v-model="selectedAnswer[index]" />-->
<!--                {{ item.answer3 }}-->
<!--              </td>-->
<!--              <td v-if="item.longestScore == '多选题'">-->
<!--                <input type="checkbox" :value="'3' + '$' + index" v-model="selectedAnswer" />-->
<!--                {{ item.answer3 }}-->
<!--              </td>-->
<!--            </tr>-->
<!--            <tr>-->
<!--              <td v-if="item.longestScore == '单选题'">-->
<!--                <input type="radio" :value="4" v-model="selectedAnswer[index]" />-->
<!--                {{ item.answer4 }}-->
<!--              </td>-->
<!--              <td v-if="item.longestScore == '多选题'">-->
<!--                <input type="checkbox" :value="'4' + '$' + index" v-model="selectedAnswer" />-->
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
          <AnswerUI :answerLen="(item.ranswer.length+1)/2" style="overflow: hidden;width: 90%;margin: 30px auto;" :checkbox="false"
                    v-model="selectedAnswer[index]" :options="JSON.parse(item.answers)"/>
        </t-card>
      </t-space>

    </t-content>


  </t-layout>
<!--  <div class="topic_div">-->
<!--    <div class="but_topic_div">-->
<!--      <div class="topic_zuo" style="position: fixed; top: 50px; left: 0px">-->
<!--        <div class="but_topic_divTwo">-->
<!--          {{selectedAnswer}}-->
<!--          <button class="but_topic" style="margin-left: 10px; width: 40px" v-for="(item, index) in topicList" :key="index"-->
<!--            :class="{ active: activeIndex === index }" @click="changeView(index + 1)">-->
<!--            {{ index + 1 }}-->
<!--          </button>-->
<!--        </div>-->
<!--        <div style="text-align: center; margin-top: 30px">-->
<!--          <button class="but" @click="tijiao">交卷</button>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--    <div style="-->
<!--        background-color: rgba(245, 224, 224, 0.401);-->
<!--        display: inline-block;-->
<!--        margin-top: 100px;-->
<!--        width: 900px;-->
<!--        float: right;-->
<!--        margin-right: 200px;-->
<!--      ">-->
<!--      <table v-for="(item, index) in topicList" :key="index" :ref="`tableRef[${index}]`">-->
<!--        <tr>-->
<!--          <td>-->
<!--            {{ index + 1 }}. {{ item.question }} ({{ item.longestScore }})-->
<!--          </td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td v-if="item.longestScore == '单选题'">-->
<!--            <input type="radio" :value="1" v-model="selectedAnswer[index]" />-->
<!--            {{ item.answer1 }}-->
<!--          </td>-->
<!--          <td v-if="item.longestScore == '多选题'">-->
<!--            <input type="checkbox" :value="'1' + '$' + index" v-model="selectedAnswer" />-->
<!--            {{ item.answer1 }}-->
<!--          </td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td v-if="item.longestScore == '单选题'">-->
<!--            <input type="radio" :value="2" v-model="selectedAnswer[index]" />-->
<!--            {{ item.answer2 }}-->
<!--          </td>-->
<!--          <td v-if="item.longestScore == '多选题'">-->
<!--            <input type="checkbox" :value="'2' + '$' + index" v-model="selectedAnswer" />-->
<!--            {{ item.answer2 }}-->
<!--          </td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td v-if="item.longestScore == '单选题'">-->
<!--            <input type="radio" :value="3" v-model="selectedAnswer[index]" />-->
<!--            {{ item.answer3 }}-->
<!--          </td>-->
<!--          <td v-if="item.longestScore == '多选题'">-->
<!--            <input type="checkbox" :value="'3' + '$' + index" v-model="selectedAnswer" />-->
<!--            {{ item.answer3 }}-->
<!--          </td>-->
<!--        </tr>-->
<!--        <tr>-->
<!--          <td v-if="item.longestScore == '单选题'">-->
<!--            <input type="radio" :value="4" v-model="selectedAnswer[index]" />-->
<!--            {{ item.answer4 }}-->
<!--          </td>-->
<!--          <td v-if="item.longestScore == '多选题'">-->
<!--            <input type="checkbox" :value="'4' + '$' + index" v-model="selectedAnswer" />-->
<!--            {{ item.answer4 }}-->
<!--          </td>-->
<!--        </tr>-->
<!--      </table>-->
<!--    </div>-->
<!--  </div>-->
</template>
  
<script>
// import "bootstrap/dist/css/bootstrap.min.css";
import router from "@/router";
import {mapState} from "pinia";
import {useCounterStore} from "@/stores/counter";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";
import AnswerUI from "@/components/ui/AnswerUI.vue";
import dayjs from "dayjs";
import {DialogPlugin} from "tdesign-vue-next";
import service from "@/axios/service";
export default {
  components: {AnswerUI, ToHtml},
  data() {
    return {
      loading:false,
      id: this.$route.query.id,
      user: this.$route.query.user,
      password: this.$route.query.password,
      topicList: [],
      selectedAnswer: [],
      activeIndex: 0, // 记录当前选中的按钮索引
    };
  },computed:{
    questionNum() {
      return this.selectedAnswer.reduce((total, item) => (total +=item!=''?1:0), 0);
    },
...mapState(useCounterStore, ['loginUser'])
},
  methods: {
    dayjs,
    tijiao() {
      this.loading = true;
      let tempAnswerList = [];
      this.selectedAnswer.forEach((item, index) => {

        if (item.length === 0) {
          tempAnswerList.push(0)
        }else {
          tempAnswerList.push(item.sort().join(','))
        }
      })

      console.log(tempAnswerList)
      service
        .post("/submit", tempAnswerList, {
          params: {
            id: this.id,
            userId: this.loginUser.sub.userId,
            username: this.loginUser.sub.username
          },
        })
        .then((response) => {
          console.log(response.data);
          router.push("/history/" + this.id);
          this.$router.push({
            name: "history",
            query: { name: this.user, password: this.password, id: response.data },
          });
          console.log(response.data);
        })
        .catch((error) => {
          console.error(error);
        }).finally(() => {
          this.loading = false;
        })
    },
  toTijiao(){
    const confirmDia = DialogPlugin({
      header: '提示',
      body: '您确认交卷吗？',
      confirmBtn: '提交考卷',
      cancelBtn: '继续做题',
      onConfirm: ({ e }) => {
        console.log('confirm button has been clicked!');
        console.log('e: ', e);
        this.tijiao()
        confirmDia.hide();
      },
      onClose: ({ e, trigger }) => {
        console.log('e: ', e);
        console.log('trigger: ', trigger);
        confirmDia.hide();
      },
    });
  }
  ,
    async setquestions() {
      await service
          .get("/findByIdTwo", {
            params: {
              groupId: this.id,
            },
          })
          .then((response) => {
            console.log(response.data);
            this.topicList = response.data;
            this.topicList.forEach(e => {
              this.selectedAnswer.push([])
            })
          })
          .catch((error) => {
            console.error(error);
          });

    },
    changeView(index) {
      this.activeIndex = index; // 设置当前选中的按钮索引
      this.$nextTick(() => {
        this.$refs[`tableRef[${index - 1}]`][0].scrollIntoView({
          behavior: "smooth",
          block: "center",
        });
      });
    },
  },
  async created() {

    await service
      .get("/findById", {
        params: {
          groupId: this.id,
          userId: this.loginUser.sub.userId,
        },
      })
      .then((response) => {

        console.log(response.data);
        if (response.data[0] == null) {
          this.setquestions();
        } else {
           this.topicList = response.data;
          this.topicList.forEach(e=>{
            this.selectedAnswer.push([])
          })
        }

      })
      .catch((error) => {

        console.error(error);
      });
  },
};
</script>
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