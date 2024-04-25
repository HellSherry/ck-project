<template>
    <div class="centre">
<!--        <div class="centre-div-top">-->
<!--            <a class="centre-span" style="margin-left: 100px;">自测卷</a>-->
<!--            <a class="centre-span">......</a>-->
<!--        </div>-->
      <t-list style="height: 52vh" size="small" :split="true">
        <t-list-item v-for="(item, index) in topicList">
          <t-list-item-meta image="/assets1/image/试卷.png" :title=" item.gname+'(做'+ item.count+'题/共'+item.answers+'题)'"
                            :description="`考试时间：${  item.submissionTime}`">

            <template #description>
              <div style="width: 90vh;margin-top: -4px">
                <span  >
                  考试时间：{{ item.submissionTime}}
                </span>
<!--                 <t-progress style="width: 330px;display: inline-block" theme="plump" :percentage="30" />-->
              </div>

            </template>
          </t-list-item-meta>
          <template #action>
            <t-space :size="6">
              <t-button class="but_topic" @click="toreport(item.number)">查看报告</t-button>
              <t-button class="but_topic" @click="toWrong(item.number)">试题分析</t-button>
            </t-space>
             </template>

        </t-list-item>

      </t-list>
<!--        <div style="text-align: center;">-->
<!--            <div v-for="(item, index) in topicList" :key="index" style="font-size: 20px;margin-bottom: 30px;">-->
<!--                <div style="width: 600px;display: inline-block;text-align: left;" class="display">-->
<!--                    <span class="tname_topic">{{ item.gname}} (做{{ item.count }}题/共{{ FillAnswersAll }}题)</span>-->
<!--                    <div style="font-size: 15px;">-->
<!--                        考试时间: {{ item.submissionTime }}-->
<!--                    </div>-->
<!--                </div>-->
<!--                <div class="progress-topic">-->
<!--                    <button class="but_topic" @click="toreport(item.number)">查看报告</button>-->
<!--                    <button class="but_topic" @click="toWrong(item.number)">试题分析</button>-->
<!--                </div>-->
<!--            </div>-->
<!--        </div>-->
    </div>
</template>
<!-- npm run serve -->
<script>


import {mapState} from "pinia";
import {useCounterStore} from "@/stores/counter";
import dayjs from "dayjs";
 import service from "@/axios/service";

export default {
  computed:{ ...mapState(useCounterStore, ['loginUser']),}
  ,
    data() {
        return {
            topicList: [],
            FillAnswers: [],
            FillAnswersAll: 20,
            number: 0,
            user: this.$route.query.name,
            password: this.$route.query.password
        }
    },
    async created() {
        try {
            const response = await service.get("/getQuestionBank",{
                params: {
                    // user: this.user,
                  user: this.loginUser.sub.username,
                  password: 1
                }
            });
          console.log(response)
            this.FillAnswers = response.data;
            console.log(response.data);
            this.FillAnswersAll = this.FillAnswers[0].count;
        } catch (error) {
            console.error(error);
        }

        try {
            const response = await service.get("/FillAnswersByNumber?userId="+this.loginUser.sub.userId,);
            this.topicList = response.data;
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    },
    methods: {
      dayjs,
        toWrong(number) {
            this.$router.push({
                        name: 'vueWrong',
                        query: { number:number,name:this.user, password:this.password}
                    })
        },
        toreport(number) {
            this.$router.push({
                        name: 'vueReport',
                        query: { number:number,name:this.user, password:this.password}
                    })
        }
    }
}
</script>

<style>

</style>