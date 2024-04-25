<template>
  <div style="background-position: center"
       :style="`background-image: url(' /assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg');background-repeat: no-repeat;background-size: cover;  `">
    <div style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 1.6vh;padding-bottom: 10px">
      <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto; min-height: 90vh">

            <div  style=" font-size: 20PX;width: 460px;margin: 0 auto;margin-top: 20vh;">
              <H2 style="margin-right: 100px;">题卷共{{ FillAnswersAll }}题/你答对了:{{ FillAnswers }}题</H2>
              <span style="margin-right: 60px;">
          你的题测成绩是：{{ countTwo }}分
        </span>
              <div style="text-align: right;width: 80%;margin-top: 10px">
                <t-button class="history_but" @click="goToTopic">确定</t-button>
              </div>
            </div>


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
      FillAnswers: 0,
      FillAnswersAll: 0,
      id: this.$route.query.id,
      name: this.$route.query.name,
      password: this.$route.query.password,
      count: 0
    };
  }
  ,
  methods: {
    goToTopic() {
      this.$router.push({
        name: 'topic',
        query: {user: this.name, password: this.password}
      })
    }
  },
  computed: {
    ...mapState(useCounterStore, ['loginUser']),
    countTwo() {
      return (100 / this.FillAnswersAll) * this.FillAnswers;
    }
  },
  async created() {
    console.log(this.id)
    try {
      const response = await service.get("/getFill", {
        params: {
          groupId: this.id,
          userId: this.loginUser.sub.userId,
          username: this.loginUser.sub.username

        }
      });
      this.FillAnswers = response.data[0].count;
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }


    try {
      const response = await service.get("/getFillAll", {
        params: {
          groupId: this.id,
          userId: this.loginUser.sub.userId,
          username: this.loginUser.sub.username
        }
      });
      this.FillAnswersAll = response.data[0].count;
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  }
};
</script>
<style>

</style>