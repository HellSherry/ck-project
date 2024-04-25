<template>

  <t-layout v-if=" topicList !=null">
    <t-aside style="overflow: hidden; width: 300px">



      <t-progress size="large" class="aline-center" style="width: 160px;margin: 10% auto  " :percentage="(rQuesNumber/topicList.length)*100"
                  theme="circle">
        <template #label>
          <div style="user-select: none">
            <p style="line-height: 10px">{{(rQuesNumber/topicList.length)*100}}分</p>

<!--            <p style="font-size: 20px;line-height: 0px">{{  // dayjs.duration(analysis.answerTime).format("mm:ss") }}</p>-->
          </div>


        </template>
      </t-progress>
      <!--        <t-button size="large" shape="round" style=" margin: 20px 4%;width: 92%" @click="toSubmit">-->
      <!--        我要交卷-->
      <!--      </t-button>-->

      <t-space :size="12" style=" user-select: none;margin-left: 7%;max-height: 50vh;overflow: auto" break-line>

        <t-button v-for="(item,index) in topicList" style="border-radius: 12px"
                  @click="changeView(index + 1)"
                  :variant="item.response[0]!='null'?'base':'dashed'"
                  :theme=" item.response.toString()==item.ranswer.toString()?'success':'danger' " shape="square"

        >{{ index + 1 }}
        </t-button>
<!--        <button-->
<!--            class="but_topic"-->
<!--          -->
<!--            style="margin-left: 10px; width: 40px"-->
<!--            v-for="(item, index) in topicList"-->
<!--            :key="index"-->
<!--        >-->
<!--          {{ index + 1 }}-->
<!--        </button>-->

      </t-space>


      <div>

      </div>
    </t-aside>
    <t-content style="overflow:  auto; height: 100vh;margin-top: -56px">
      <div style="height: 56px">

      </div>
       <t-space style="width: 100%;" direction="vertical">
<!--         <table-->
<!--             v-for="(item, index) in topicList"-->
<!--             :key="index"-->
<!--             :ref="`tableRef[${index}]`"-->
<!--         >-->
<!--           <tr>-->
<!--             <td>{{ index + 1 }}.{{ item.question }}</td>-->
<!--           </tr>-->
<!--           <tr>-->
<!--             <td>-->
<!--               <input type="radio" :checked="item.response === '1'" disabled />-->
<!--               {{ item.answer1 }}-->
<!--             </td>-->
<!--           </tr>-->
<!--           <tr>-->
<!--             <td>-->
<!--               <input type="radio" :checked="item.response === '2'" disabled />-->
<!--               {{ item.answer2 }}-->
<!--             </td>-->
<!--           </tr>-->
<!--           <tr>-->
<!--             <td>-->
<!--               <input type="radio" :checked="item.response === '3'" disabled />-->
<!--               {{ item.answer3 }}-->
<!--             </td>-->
<!--           </tr>-->
<!--           <tr>-->
<!--             <td>-->
<!--               <input type="radio" :checked="item.response === '4'" disabled />-->
<!--               {{ item.answer4 }}-->
<!--             </td>-->
<!--           </tr>-->
<!--           <tr>-->
<!--             <td>-->
<!--               正确答案:{{ item.ranswer }},你的回答:-->
<!--               <span v-if="item.response != 'null'">-->
<!--                {{ item.response }}-->
<!--              </span>-->
<!--               <span v-if="item.response == 'null'"> 未作答 </span>-->
<!--               {{ item.successOrFailure }}-->
<!--               <span style="position: absolute; right: 30px">-->
<!--                <i class="el-icon-s-order"></i>-->
<!--                <el-button-->
<!--                    type="text"-->
<!--                    @click="open(item)"-->
<!--                    style="font-size: 15px; color: black"-->
<!--                >-->
<!--                  <span>试题分析</span>-->
<!--                </el-button>-->
<!--              </span>-->
<!--             </td>-->
<!--           </tr>-->
<!--         </table>-->
        <t-card       size="small"    style="width: 80%;margin:20px auto 0 auto;min-width: 100px;max-width: 800px" :bordered="false" hoverShadow>
<!--          {{topicList}}-->
          <h2 style="text-align: center;line-height: 6px;user-select: none"> {{topicList[0].gName}}</h2>        </t-card>
        <t-card v-for="(item,index) in topicList"
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
}">选择 1  项
          </t-check-tag>
             <table   :ref="`tableRef[${index}]`"
                 />
          <br>
          <ToHtml style="overflow: hidden;width: 90%;margin: 0 auto;  font-weight: 600;" v-html="item.question"/>


          <div style="overflow: hidden;width: 90%;margin: 30px auto;">
            <div v-for="(answer,index) in item.answers">

              <ToHtml
                                    :style="((item.response.some(ele => ele == index+1))&&!(item.ranswer.some(ele => ele == index+1)))? 'background-color:#FF000050;':((item.response.some(ele => ele == index+1))&&(item.ranswer.some(ele => ele == index+1)))?'background-color:#366ef47a;':item.ranswer.some(ele => ele == index+1)?'background-color:#36f44970;':'  border: 1px solid var(--td-text-color-primary);'"

                  style=" border-radius: 14px;padding: 12px;margin-bottom: 12px"
                  v-html="answer"/>
            </div>
            正确答案：<span v-for="an in item.ranswer">{{ toABC(an) }}&nbsp;</span><br>

            你选择的：<span v-for="an in item.response">{{ an=='null'?'未选择': toABC(an) }}&nbsp;</span>&nbsp;
            <t-tag v-if="item.response.toString()==item.ranswer.toString()" size="small" theme="success" variant="light-outline">回答正确</t-tag>
            <t-tag v-else theme="danger" variant="light-outline">回答错误</t-tag>
          </div>
          <t-divider style="border-color: var(--td-text-color-primary)" dashed/>
          <div style="padding:  0 30px 30px 30px">
            <h4>解析</h4>
            <ToHtml v-html="item.parse"/>
          </div>

        </t-card>
      </t-space>
    </t-content>


  </t-layout>
  <div style="text-align: center;margin-top: 20%; font-size: 40px;font-weight: 400" v-else>
    你没有这场考试的权限
  </div>







<!--  <div class="topic_div">-->
<!--    <div class="but_topic_div">-->
<!--      <div class="topic_zuo" style="position: fixed; left: 0px">-->
<!--        <div class="but_topic_divTwo">-->
<!--          <button-->
<!--            class="but_topic"-->
<!--            @click="changeView(index + 1)"-->
<!--            style="margin-left: 10px; width: 40px"-->
<!--            v-for="(item, index) in topicList"-->
<!--            :key="index"-->
<!--          >-->
<!--            {{ index + 1 }}-->
<!--          </button>-->
<!--        </div>-->
<!--      </div>-->
<!--      <div-->
<!--        style="-->
<!--          background-color: rgba(245, 224, 224, 0.401);-->
<!--          display: inline-block;-->
<!--          margin-top: 100px;-->
<!--          width: 900px;-->
<!--          position: absolute;-->
<!--          left: 700px;-->
<!--        "-->
<!--      >-->
<!--        <table-->
<!--          v-for="(item, index) in topicList"-->
<!--          :key="index"-->
<!--          :ref="`tableRef[${index}]`"-->
<!--        >-->
<!--          <tr>-->
<!--            <td>{{ index + 1 }}.{{ item.question }}</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>-->
<!--              <input type="radio" :checked="item.response === '1'" disabled />-->
<!--              {{ item.answer1 }}-->
<!--            </td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>-->
<!--              <input type="radio" :checked="item.response === '2'" disabled />-->
<!--              {{ item.answer2 }}-->
<!--            </td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>-->
<!--              <input type="radio" :checked="item.response === '3'" disabled />-->
<!--              {{ item.answer3 }}-->
<!--            </td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>-->
<!--              <input type="radio" :checked="item.response === '4'" disabled />-->
<!--              {{ item.answer4 }}-->
<!--            </td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <td>-->
<!--              正确答案:{{ item.ranswer }},你的回答:-->
<!--              <span v-if="item.response != 'null'">-->
<!--                {{ item.response }}-->
<!--              </span>-->
<!--              <span v-if="item.response == 'null'"> 未作答 </span>-->
<!--              {{ item.successOrFailure }}-->
<!--              <span style="position: absolute; right: 30px">-->
<!--                <i class="el-icon-s-order"></i>-->
<!--                <el-button-->
<!--                  type="text"-->
<!--                  @click="open(item)"-->
<!--                  style="font-size: 15px; color: black"-->
<!--                >-->
<!--                  <span>试题分析</span>-->
<!--                </el-button>-->
<!--              </span>-->
<!--            </td>-->
<!--          </tr>-->
<!--        </table>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
</template>

<script>
// import { mapActions } from "vuex";
import {mapState} from "pinia";
import {useCounterStore} from "@/stores/counter";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";
import dayjs from "dayjs";
import service from "@/axios/service";
export default {
  components: {ToHtml},
  data() {
    return {
      rQuesNumber: 0,
      Number: this.$route.query.number,
      user: this.$route.query.name,
      password: this.$route.query.password,
      topicList: [],
      FillAnswers: "",
    };
  },computed:{
    dayjs() {
      return dayjs
    }, ...mapState(useCounterStore, ['loginUser']),},
  mounted() {
    // this.setUserName(this.user);
    // this.setUserId(this.password);
  },
  methods: {
      toABC(item) {
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
},
// ...mapActions(["setUserName", "setUserId"]),
    open(item) {
      this.$alert(item.parse, {
        title: "试题分析",
        confirmButtonText: "确定",
      });
    },
    changeView(index) {
      this.activeIndex = index; // 设置当前选中的按钮索引
      this.$nextTick(() => {
        this.$refs[`tableRef[${index - 1}]`][0].scrollIntoView({

          block: "center",
        });
      });
    },
  },
  async created() {
    try {
      const response = await service.get(
        "/getQuestionBankByNumber",
        {
          params: {
            number: this.Number,
            userId: this.loginUser.sub.userId,
            username: this.loginUser.sub.username
            // username: this.user,
            // userId: this.password,
          },
        }
      );
      this.topicList = response.data;
      this.topicList.forEach(e=>{
        e.answers=JSON.parse(e.answers)
        if (e.ranswer==e.response){
          this.rQuesNumber++
        }
        e.ranswer=e.ranswer.split(',')
        e.response=e.response.split(',')

      })
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
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