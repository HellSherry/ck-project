<template>
  <div style="background-position: center"
       :style="`background-image: url(' /assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg');background-repeat: no-repeat;background-size: cover;  `">
    <div style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 1.6vh;padding-bottom: 10px">
      <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto; min-height: 86vh">
<!--        {{topList}}-->
        <t-layout>
          <t-aside style="height: 86vh;border-right: 1px solid var(--td-text-color-primary)">
            <div>
              <img src="../../assets/image/1.png" style='width: 92%;margin-top: 30%;margin-bottom: 20%'
                   class="report_img">
              <br>
              <t-button size="large" shape="round" style="  width: 92%" @click="toreport">
                查看报告
              </t-button>
              <t-button size="large" shape="round" style=" margin: 12px 0;width: 92%" @click="toWrong">
                查看解析
              </t-button>
              <!--    <div><button class="report_but" @click="toreport">查看报告</button></div>-->
              <!--    <div><button class="report_but" @click="toWrong">查看解析</button></div>-->
            </div>
          </t-aside>
          <t-content style="background-color: var(--td-bg-color-container)">


            <div class="has-bottom-border-Two">

              <div style="display: inline-block; vertical-align: middle ;margin-top: -10px  ">
                <div class="has-bottom">共{{ topList.length }}题,你答对了</div>
                <div style="text-align: center; margin-top: 18px">
                  <span style="font-size: 40px;">{{ FillAnswers }}</span>
                </div>
              </div>
              <div class="has-bottom-Two" style="vertical-align: middle;display: inline-block;margin-left: 20px;width: 300px">
                <div class="has_report">
                  <span>交卷时间:2020-02-01 14:00</span>
                </div>
                <div class="has_report_Two">
                  <t-space :size="6" style=" user-select: none;margin-left: 7%;max-height: 50vh;overflow: auto;margin-bottom: 28px" break-line>
  <t-button v-for="(item,index) in topList" style="border-radius: 12px"
            :variant="item.response[0]!='null'?'base':'dashed'"
            :theme=" item.response.toString()==item.ranswer.toString()?'success':'danger' " shape="square"

  >{{ index + 1 }}
  </t-button>
</t-space>


<!--                            <span v-for="(item, index) in topList" :key="index">-->
<!--                                <button v-if="item.ranswer == item.response"-->
<!--                                        style="background-color: rgb(104, 194, 104);color: aliceblue;"-->
<!--                                        class="but_report">{{-->
<!--                                    index + 1-->
<!--                                  }}</button>-->
<!--                                <button v-if="item.ranswer != item.response"-->
<!--                                        style="background-color: rgb(247, 101, 101);color: aliceblue;"-->
<!--                                        class="but_report">{{-->
<!--                                    index + 1-->
<!--                                  }}</button>-->
<!--                            </span>-->
                </div>
              </div>
            </div>

          </t-content>
        </t-layout>


      </t-card>
    </div>
  </div>

</template>

<script>

import {mapState} from "pinia";
import {useCounterStore} from "@/stores/counter";
import service from "@/axios/service";

export default {
  data() {
    return {
      pname: '',
      Number: this.$route.query.number,
      user: this.$route.query.name,
      password: this.$route.query.password,
      topList: [],
      FillAnswers: 0,
      FillAnswersAll: 0,
    }
  }, computed: {...mapState(useCounterStore, ['loginUser']),},
  methods: {
    toWrong() {
      this.$router.push({
        name: 'vueWrong',
        query: {number: this.Number, name: this.user, password: this.password}
      })
    },
    toreport() {
      location.reload();
    }
  },
  async created() {
    try {
      const response = await service.get("/getReportByNumber?number="+this.Number);
      this.topList = response.data;
      this.topList.forEach(item => {
        if (item.response.toString() == item.ranswer.toString()) {
          this.FillAnswers += 1;
        }
      })
    } catch (error) {
      console.error(error);
    }

    // try {
    //   const response = await service().get("/getFillAllTwo", {
    //     params: {
    //       groupId: this.Number,
    //       password: 1,
    //       user: this.loginUser.sub.username
    //       // user: this.user,
    //       // password: this.password
    //     }
    //   });
    //   this.topList = response.data.result
    //   console.log(response.data.result);
    // } catch (error) {
    //   console.error(error);
    // }
    //
    // try {
    //   const response = await service.get("/getFillAnswersAll", {
    //     params: {
    //       number: this.Number,
    //       password: 1,
    //       user: this.loginUser.sub.username
    //       // user: this.user,
    //       // password: this.password
    //     }
    //   });
    //   this.FillAnswersAll = response.data.result[0].count;
    //   console.log(response.data.result);
    // } catch (error) {
    //   console.error(error);
    // }
  }
}
</script>

<style scoped>
.has-bottom-border-Two {
  max-width:  540px;
  margin: 20% auto;
 min-height:  140px;
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