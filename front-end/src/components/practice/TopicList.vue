<template>

  <div style="background-position: center"
       :style="`background-image: url('/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg');background-repeat: no-repeat;background-size: cover;  `">
    <div style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 1.6vh;padding-bottom: 10px">
      <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto; min-height: 90vh">
        <h2>试题测试</h2>
        <t-collapse expand-mutex>
          <t-collapse-panel v-for="(item, index) in countList" :value="index">
            <template #header>
              <div>{{ item.name }}</div>
            </template>
            <template #default>

              <table style="width: 100%">
                <tr v-for="(topic, index2) in topicList">
                  <template v-if="item.id===topic.courseid">
                    <td>{{ topic.name }}</td>
                    <td>
<!--                      <t-progress style="vertical-align: middle;width: 330px;display: inline-block" theme="plump"-->
<!--                                  :percentage="countListAll[index2]"/>-->
                      <t-progress style="vertical-align: middle;width: 330px;display: inline-block" theme="plump"
                                  :percentage=" parseInt((topic.num/topic.total)*100)"/>
                    </td>
                    <td>{{topic.num}}/{{topic.total}}</td>

                    <td>
                      <t-button style="margin: 4px" class="but_topic" @click="  goToQuestion(topic.groupId,topic.num===topic.total)">开始测试
                      </t-button>
                    </td>
                  </template>

                </tr>
              </table>

              <t-alert v-if="!topicList.find((e)=>e.courseid===item.id)" message="暂时没有题目哦"/>


            </template>
          </t-collapse-panel>


        </t-collapse>


      </t-card>
    </div>
  </div>


  <!--  <div>-->
  <!--    <div>-->
  <!--      <div>-->


  <!--        <div v-for="(item, index) in topicList" :key="index">-->
  <!--          <el-collapse accordion style="width: 80%;display: inline-block;margin-left: 200px;">-->
  <!--            <el-collapse-item>-->
  <!--              <template slot="title">-->
  <!--                <div v-if="count[index].courseid == 1">Java开发之框架基础技术</div>-->
  <!--                <div v-else>sdafa</div>-->
  <!--              </template>-->
  <!--              <el-row :gutter="20">-->
  <!--                <el-col :span="4">-->
  <!--                  <div class="grid-content bg-purple"><span>{{ item.name }}</span></div>-->
  <!--                </el-col>-->
  <!--                <el-col :span="16">fdgsdg-->
  <!--                  {{ countListAll[index] }}-->
  <!--                  <div class="grid-content bg-purple">-->
  <!--                    <el-progress :text-inside="true"-->
  <!--                                 style="width: 400px;margin-left: 400px;" :stroke-width="26"-->
  <!--                                 :percentage="countListAll[index]"></el-progress>-->
  <!--                  </div>-->
  <!--                </el-col>-->
  <!--                <el-col :span="4">-->
  <!--                  <div class="grid-content bg-purple">-->
  <!--                    <div>-->
  <!--                      <button class="but_topic" @click="goToQuestion(item.groupId)">开始测试</button>-->
  <!--                    </div>-->
  <!--                  </div>-->
  <!--                </el-col>-->
  <!--              </el-row>-->
  <!--            </el-collapse-item>-->
  <!--          </el-collapse>-->
  <!--        </div>-->
  <!--      </div>-->
  <!--    </div>-->
  <!--  </div>-->
</template>

<script>
import {useCounterStore} from "@/stores/counter";
import {mapState} from 'pinia'
import TestEcharts from "@/manage/compoments/TestEcharts.vue";
import {DialogPlugin} from "tdesign-vue-next";
import service from "@/axios/service";

export default {
  components: {TestEcharts},
  data() {
    return {
      topicList: [],
      count: [],
      countList: [],
      countListAll: [],
      percentList: [],
      groupIdList: [],
      List: [],
      cname: '',
      user: this.$route.query.user,
      password: this.$route.query.password
    };
  }, computed: {
    ...mapState(useCounterStore, ['loginUser'])
  },

  methods: {
    goToQuestion(id, isDoAll) {
      if (isDoAll) {
        const confirmDia = DialogPlugin({
          header: '提示',
          body: '你已完成所有题目测试,继续测试将可能遇到重复题目！',
          confirmBtn: {
            content: '继续测试',
            theme: 'primary',
          },
          cancelBtn: {content: '取消', variant: 'outline'},
          onConfirm: ({e}) => {
            this.$router.push({
              name: 'practice-question',
              query: {groupId: id,method:'random'}
            })
            confirmDia.hide();
          }
        });
      } else
        this.$router.push({
          name: 'practice-question',
          query: {groupId: id,method:'order'}
        })
    },
    deleteTopic() {
      service.post("/QuestionBank", this.groupIdList, {
        params: {
          userId: this.loginUser.sub.userId,
        }
      })
          .then((response) => {
            this.countListAll = response.data;
            console.log(this.countListAll);
          })
    },

  },
  async created() {
    console.log("user:" + this.user + "password:" + this.password);
    console.log();
    try {
      await service.get("/Question")
          .then((response) => {
            console.log(response)
            this.topicList = response.data;
            this.count = response.data;
            console.log(this.topicList);

            service('/practice/getProgress').then(res => {
              this.topicList.forEach(element => {
                // 将 groupId 添加到数组中
                this.groupIdList.push(element.groupId);
                if (res.data.find(item => item.groupId === element.groupId)) {
                  let find = res.data.find(item => item.groupId === element.groupId);
                  element.num = find.num;
                  element.total = find.total;
                }
              });
            })

            this.deleteTopic();
          });
    } catch (error) {
      console.error(error);
    }

    try {
      service.get("/QuestionCourse")
          .then((response) => {
            this.countList = response.data;
            console.log(response.data);
          })
    } catch (error) {
      console.error(error);
    }
  },
};
</script>

<style>

</style>
 