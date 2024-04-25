<script setup lang="jsx">
import {ref} from "vue";
import {useCounterStore} from "@/stores/counter";
import dayjs from "dayjs";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";
import service from "@/axios/service";
import ChatTrueView from "@/views/ChatTrueView.vue";
import CourseTable from "@/manage/compoments/CourseTable.vue";
const store = useCounterStore();

const askDetail = ref();
const tableData = ref([]);
const message = ref();
service.get('/message/getAll').then(res => {
  message.value = res.data
})

initDetail();

function initDetail() {

  store.loginUser.sub.classid>0 && service.get(`/courseschedule/getByClassId?classid=${store.loginUser.sub.classid}`).then(
      (res) => {
        console.error(res)
        tableData.value = res.data;
        tableData.value.forEach(item => {
          // console.log(JSON.parse(item.detail))
          item.detail = JSON.parse(item.detail)

        })

        console.log(res);
      }
  )

}
const getChineseDay = (num) => {
  switch (num)
    {
      case 0:
        return '星期一'
      case 1:
        return '星期二'
      case 2:
        return '星期三'
      case 3:
        return '星期四'
      case 4:
        return '星期五'
      case 5:
        return '星期六'
      case 6:
        return '星期天'
      default:
        return 'day'+num
    }
}

const menuItemActive = ref(0);
</script>

<template>
  <div style="background-position: center"
       :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;  `">
    <div :class="store.darkT?'boxdark':'box'"
         style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 12px">
      <t-layout :class="store.darkT?'boxdark':'box'" style="margin: 0 auto ;width: 90%; ">
        <t-hander style="background-color: #0000">
          <div  style=" margin:8px 1%;width: 98%;overflow: auto;margin-bottom: 0"  :class="store.darkT?'tem':'box'"    >


  <t-icon style="font-size: 16px;padding: 10px 0;vertical-align: middle ;margin-left: 20px" name="send"/>
  课程安排


           </div>
<div  style=" margin:8px 1%;width: 98%;overflow: auto;margin-bottom: 0"  :class="store.darkT?'tem':'box'"   :theme="store.darkT?'dark':'light' ">
  <div  v-if="tableData!= undefined && tableData.length>=0" class="radio-inputs">
    <label v-for="i in (tableData.length)">
      <input class="radio-input" type="radio" v-model="menuItemActive"  :value="i-1">
      <span class="radio-tile">
					<span class="radio-icon">
 					</span>
					<span class="radio-label">{{tableData[i-1].name}}</span>
				</span>
    </label>

  </div>
</div>



<!--          <t-menu :class="store.darkT?'tem':'box'" v-model="menuItemActive" :theme="store.darkT?'dark':'light' "-->
<!--                  default-value="item1" height="550px">-->
<!--            <template #logo>-->
<!--              <t-icon style="font-size: 18px" name="send"/> &nbsp;-->

<!--              课程安排-->
<!--            </template>-->
<!--            <t-menu-item v-for="i of (tableData.length)" :value="i-1">{{tableData[i-1].name}}</t-menu-item>-->


<!--          </t-menu>-->
        </t-hander>
        <t-content>


          <t-space :size="8" style=" margin:8px 1%;width: 99%; height: 82vh;overflow: auto;" direction="vertical">

               <div v-if="tableData!= undefined && tableData.length==0">
                <nut-empty description="暂时没有数据哦"></nut-empty>
              </div>
              <div v-else>
                <course-table   :data="  tableData[menuItemActive].detail   " />


              </div>




          </t-space>


        </t-content>
      </t-layout>

    </div>
  </div>

</template>

<style scoped>

.box {
  background-color: rgba(255, 255, 255, .48);
}

.tem {
  background-color: rgba(0, 0, 0, .2);
}

.boxdark {
  background-color: rgba(0, 0, 0, .48);
}
.radio-inputs {
  display: flex;
   padding: 4px 8px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.radio-inputs > * {
  margin: 6px;
}

.radio-input:checked + .radio-tile {
  border-color: #2260ff;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
  color: #2260ff;
}

.radio-input:checked + .radio-tile:before {
  transform: scale(1);
  opacity: 1;
  background-color: #2260ff;
  border-color: #2260ff;
}

.radio-input:checked + .radio-tile .radio-icon svg {
  fill: #2260ff;
}

.radio-input:checked + .radio-tile .radio-label {
  color: #2260ff;
}

.radio-input:focus + .radio-tile {
  border-color: #2260ff;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1), 0 0 8px 4px #b5c9fc;
}

.radio-input:focus + .radio-tile:before {
  transform: scale(1);
  opacity: 1;
}

.radio-tile {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 80px;
  min-height: 80px;
  border-radius: 0.5rem;
  border: 1.4px solid #b5bfd9;
  background-color: #fff;
  box-shadow: 0 5px 10px rgba(0, 164, 255, 0.1);
  transition: 0.15s ease;
  cursor: pointer;
  position: relative;
}

.radio-tile:before {
  content: "";
  position: absolute;
  display: block;
  width: 0.75rem;
  height: 0.75rem;
  border: 1px solid rgba(0, 60, 255, 0.07);
  background-color: #fff;
  border-radius: 50%;
  top: 0.25rem;
  left: 0.25rem;
  opacity: 0;
  transform: scale(0);
  transition: 0.25s ease;
}

.radio-tile:hover {
  border-color: var(--td-brand-color);
}

.radio-tile:hover:before {
  transform: scale(1);
  opacity: 1;
}

.radio-icon svg {
  width: 2rem;
  height: 2rem;
  fill: #494949;
}

.radio-label {
  color: #707070;
  transition: 0.375s ease;
  text-align: center;
  font-size: 13px;
}

.radio-input {
  clip: rect(0 0 0 0);
  -webkit-clip-path: inset(100%);
  clip-path: inset(100%);
  height: 1px;
  overflow: hidden;
  position: absolute;
  white-space: nowrap;
  width: 1px;
}
</style>