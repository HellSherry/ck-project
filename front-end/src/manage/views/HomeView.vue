<script setup lang="jsx">
import {ref} from "vue";
import {useCounterStore} from "@/stores/counter";
import TestEcharts from "@/manage/compoments/TestEcharts.vue";

const store = useCounterStore();

</script>

<template>
  <div style="background-position: center"
       :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;  `">
    <div :class="store.darkT?'boxdark':'box'"
         style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 3vh;padding-bottom: 10px">
      <t-card :bordered="false" hoverShadow
              style="display: flex;text-align: center;flex-flow: column;width: 96%;margin: 0 auto; min-height: 94vh">


        <div class="header-container">
          <div style="height:10vh"></div>
          <h2 style="font-size: 76px;font-family: '楷体'" class="title">品钰教育管理系统</h2>
          <p style="font-size: 20px" class="subtitle">打造高效便捷的全新教育管理体验</p>
        <div style="height: 20px"></div>
        </div>
        <div class="welcome-section">
          <div class="welcome-image-container"
               style="height: 200px;box-shadow:0px 6px 12px #1E76F2 inset,  0px -6px 12px #037F05 inset;border: 1px solid black  ;  border-radius: 50%;width: 200px; ;margin: 0 auto; ">
             <img @click="$router.push('/')" src="../../assets/图标头.png" alt="欢迎图片"
                 class="welcome-image"
                 style=" transition: all 0.2s ease-in-out;;cursor: pointer;width: 100%">
          </div>
          <div class="welcome-content" style="padding-top: 20px;padding-bottom: 10px"><h2 class="welcome-text">WELCOME TO CK MANAGE SYSTEM!</h2>
            <p class="description"> 这里整合了丰富的教育资源，配备了科学的课程管理体系以及全面的学生信息管理模块。<br>
              让我们共同致力于提升教育质量和效率，携手前行！ </p></div>
        </div>
        <div class="action-buttons-container" style="margin-top: 20px">


          <nav>
            <t-space style="cursor: pointer">

            <t-card  @click="$router.push('/')" hoverShadow style="width:140px;display: inline-block">
              开始探索
            </t-card>
              <t-card  @click="$router.push('/about')" hoverShadow style="width:140px;display: inline-block">
              了解我们
            </t-card>
              <t-card  @click="$router.push('/manage/papers')" hoverShadow style="width:140px;display: inline-block">
              试卷管理
            </t-card>
              <t-card  @click="$router.push('/manage/grades')" hoverShadow style="width:140px;display: inline-block">
              成绩管理
            </t-card>
            </t-space>
<!--            <a href="/manage/papers" class="btn btn-primary">开始探索</a> <a href="/about" class="btn btn-secondary">了解我们</a>-->
          </nav>
        </div>


      </t-card>
    </div>
  </div>

</template>

<style scoped>
.box {
  background-color: rgba(255, 255, 255, .48);

}

.boxdark {
  background-color: rgba(0, 0, 0, .48);
}

.welcome-image:hover {
  transform: scale(1.1);

}

</style>