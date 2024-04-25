<template>
  <div class="centre">
    <!--    <div class="centre-div-top">-->
    <!--      <a class="centre-span">分类自测</a>-->
    <!--      <a class="centre-span">本班排行</a>-->
    <!--      <a class="centre-span">分类自测</a>-->
    <!--      <a class="centre-span">分类自测</a>-->
    <!--    </div>-->
    <t-dialog
        placement="center"
        header="请选择要自测的课程"
        v-model:visible="visible"
         :footer="false"
    >
<CourseReview :number="number" />
     </t-dialog>

    <div>
      <div class="ck" style="margin-top: 20px">
        <span>每日刷题</span>
      </div>
      <div class="like" style="text-align: center; ">
        <t-row style="margin: 36px auto;font-size: 18px" justify="space-around">
          <t-col :span="2">
            <span class="centre-span-two">累计答题数: {{ presentData.length }}</span>

          </t-col>
          <t-col :span="2">
            <span class="centre-span-two">实际答题数: {{ presentDataDeduplication.length }}</span>

          </t-col>
          <t-col :span="2"><span class="centre-span-two">正确率: {{ Percentage(presentDataRight,presentData.length)  }} % </span>


          </t-col>
        </t-row>
      </div>
      <div class="ck">
        <span>测试</span>
      </div>
      <t-row style="text-align: center;margin: 50px auto 20px auto " justify="space-around">
        <t-col   :span="2">
          <div class="img_container">
            <img src="https://tiku.kgc.cn/resources/V12.0.0.6/img/unified_test_img.png" class="test_list_img centre_img round"/><br>
            <h3 style="line-height: 12px" class="span-centre">专项技能</h3>
            <span class="span-centre-but"><t-button class="centre-but" @click="showPopup">进入测试</t-button></span>
          </div>
        </t-col>
        <t-col  :span="2">
          <div class="img_container">
            <img src="https://tiku.kgc.cn/resources/V12.0.0.6/img/skill_test_img.png" class="test_list_img centre_img round"/><br>
            <h3 style="line-height: 12px" class="span-centre">课程复习</h3>
            <span class="span-centre-but"><t-button class="centre-but" @click="visible=true;number=20">进入测试</t-button></span>
          </div>
        </t-col>
        <t-col  :span="2">
          <div class="img_container">
            <img src="https://tiku.kgc.cn/resources/V12.0.0.6/img/imitate_test_img.png" class="test_list_img centre_img round"/><br>
            <h3 style="line-height: 12px" class="span-centre">模拟真题</h3>
            <span class="span-centre-but"><t-button class="centre-but" @click="visible=true;number=50">进入测试</t-button></span>
          </div>
        </t-col>
      </t-row>

    </div>
  </div>
</template>

<script>
import {ref} from "vue";
import service from "@/axios/service";
import {mapState} from "pinia";
import {useCounterStore} from "@/stores/counter";
import CourseReview from "@/components/practice/CourseReview.vue";

export default {
  components: {CourseReview},
  data() {
    return {
      number:20,
      visible:false,
      presentData: [],
      presentDataDeduplication: [],
      presentDataRight: 0,
      Accumulative: 0,
      practical: 0,
      ishowPopup: false,
      user: this.$route.query.name,
      password: this.$route.query.password
    };
  },
  created() {
    console.log(this.user);
    console.log(this.password);


    service('/practice/getTodaySimpleDetails').then(res => {
      this.presentData = res.data

      res.data.forEach(e => {
        if (this.presentDataDeduplication.indexOf(e.questionid) === -1) { //indexof()方法判断在数组中的位置，若不存在，返回-1
          this.presentDataDeduplication.push(e.questionid);
        }
        if (e.answer == e.ranswer) { //indexof()方法判断在数组中的位置，若不存在，返回-1
          this.presentDataRight++;
        }
      })
    })
  },
  methods: {


    Percentage(num, total) {
      if (num == 0 || total == 0) {
        return 0;
      }
      return (Math.round(num / total * 10000) / 100.00);// 小数点后两位百分比
    },
    showPopup() {
      this.$router.push({
        name: 'practice-topic',
        query: {user: this.user, password: this.password}
      })
    }
  },

  computed: {
   ...mapState(useCounterStore, ['loginUser']),

    calculateCorrectRate: function () {
      if (this.practical === 0) {
        return "0%";
      }
      return (this.Accumulative / this.practical).toFixed(2) + "%";
    },
  }
};
</script>

<style scoped>
img {
  width: 100px;
}
</style>