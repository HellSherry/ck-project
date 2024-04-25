<script setup>
import {computed, reactive, ref, watch} from "vue";
import {useCounterStore} from "@/stores/counter";
import dayjs from 'dayjs';
import duration from 'dayjs/plugin/duration';
import service from "@/axios/service";
import {Message, MessagePlugin} from "tdesign-vue-next";
import App from "@/App.vue";
import {now} from "@vueuse/core";
import GradesDetail from "@/manage/compoments/GradesDetail.vue";

 const total = ref(0);
const searchForm = reactive({
  page: 1,
  search: '',
  time: [],
});

dayjs.extend(duration);

const drawerVisible = ref(false);
const store = useCounterStore();

 // 全量数据


 const data = ref([
  {
    "paperid": 1,
    "papername": "第一条测试试卷",
    "questions": "[\r\n  \"1697182447.215191\",\r\n  \"1697182455.7710226\",\r\n  \"1697182460.4997933\",\r\n  \"1697182466.5208726\",\r\n  \"1697182472.274864\",\r\n  \"1697182478.4024422\",\r\n  \"1697182484.2858622\",\r\n  \"1697182491.1385913\",\r\n  \"1697182502.916686\",\r\n  \"1697182513.1574242\",\r\n  \"1697182523.9296796\",\r\n  \"1697182536.179753\",\r\n  \"1697182548.0525\",\r\n  \"1697182556.697243\",\r\n  \"1697182565.346125\",\r\n  \"1697182572.125598\",\r\n  \"1697182584.0691957\",\r\n  \"1697182592.7712471\",\r\n  \"1697182602.657546\",\r\n  \"1697182612.7260299\",\r\n  \"1697182625.055601\",\r\n  \"1697182639.1223602\",\r\n  \"1697182651.017039\",\r\n  \"1697182662.9913886\",\r\n  \"1697182675.3650398\",\r\n  \"1697182690.1543\",\r\n  \"1697182701.2358558\",\r\n  \"1697182715.3210168\",\r\n  \"1697182729.3673716\",\r\n  \"1697182742.6456878\",\r\n  \"1697182753.112941\",\r\n  \"1697182764.8161106\",\r\n  \"1697182781.5966773\",\r\n  \"1697182792.5887868\",\r\n  \"1697182807.3700113\",\r\n  \"1697182824.35289\",\r\n  \"1697182834.4852557\",\r\n  \"1697182844.3831558\",\r\n  \"1697182858.3031824\",\r\n  \"1697182869.1886208\",\r\n  \"1697182880.4490285\",\r\n  \"1697182909.0475578\",\r\n  \"1697182923.2650566\",\r\n  \"1697182933.1916134\",\r\n  \"1697182944.2247226\",\r\n  \"1697182956.553987\",\r\n  \"1697182962.9953659\",\r\n  \"1697182977.1483552\",\r\n  \"1697182989.2608874\",\r\n  \"1697183009.544582\",\r\n  \"1697183021.0839071\"\r\n]",
    "creatorusername": "汤凯旋",
    "totalscore": 100,
    "starttime": "2023-12-26T20:42:58.000+00:00",
    "duration": 1,
    "classid": null
  }])
const selectData = ref([])

const selectClass = ref([])
const selectValue = ref()
const selectPaper = ref()
function selectAll() {
  let url = '';
  if (searchForm.search !== '' && searchForm.search !== undefined) {
    url += ('&search=' + searchForm.search)
  }
  if (typeof searchForm.time === 'object' && searchForm.time.length > 0) {
    url += ('&starttime=' + searchForm.time[0] + '&endtime=' + searchForm.time[1])
    // url+=(url===''?'?':'&')+('starttime='+searchForm.time[0]+'&endtime='+searchForm.time[1])
  }else {
    url += (  '&endtime=' +dayjs().format("YYYY-MM-DD- HH:mm"))

  }
  console.log(url)
  service.post('/paper/get?page=' + searchForm.page + url)
      .then(response => {
        // 处理成功响应
        console.log(response.data);
        total.value = response.data.total
        data.value = response.data.list
        data.value.forEach(i => {
          i.starttime = dayjs(i.starttime).format("YY年MM月DD日 HH:mm")

          i.classid = []
          i.paperClasses.forEach((ite) => {
            i.classid.push(ite.name)
          })
        })
      })
      .catch(error => {
        // 处理错误
        console.error('There was an error!', error);
      });
}
const onlineUser=ref([])

selectAll()
function selectGrades() {
  service.get("/exam/selectGrades?classId="+selectValue.value+"&paperId="+selectPaper.value).then(response => {
    console.log(response)
    selectData.value = response.data
    onlineUser.value=[];
    service.get("/exam/getUserExamsAreOngoing?paperid="+selectPaper.value).then(res=>{


      res.data.forEach(e=>{
            onlineUser.value.push(e.substring(e.indexOf(":")+1,e.indexOf("-startTime")))

          })
    })
  })
}





 const presets = ref({
  最近7天: dayjs().add(7, 'day'),
  最近3天: dayjs().add(3, 'day'),
  今天: dayjs(),
});
const sortChange = (val) => {
  sort.value = val;
  // Request: 发起远程请求进行排序
  console.log('发起远程请求进行排序（未模拟请求数据）');
}

 watch(searchForm, () => {
  selectAll()
})
const excelData = computed(()=>{
  let tempSelectData=[];
  selectData.value.forEach(
      (item) => {
        tempSelectData.push({'学号': item.userId,'姓名': item.answerContent,'成绩': item.score==null?'未提交':item.score,'时间': item.answerTime==null?'未提交':dayjs.duration(item.answerTime).format("mm:ss"),'班级': selectClass.value.find(i=>i.classid==selectValue.value).name })
      }
  )
  return tempSelectData;
})
function openGrades(row) {
   drawerVisible.value=true
  selectClass.value=   [ {classid:'',name:'全部'},...row.paperClasses]
  selectPaper.value=row.paperid
if (selectValue.value==selectClass.value[1].classid){
  selectGrades();
}else   {
  selectValue.value=selectClass.value[1].classid

}

}
const averageMinutes = computed(() => {
  let num=0;
  let total=0;
  selectData.value.forEach((item) => {
    if (item.score != null) {
      num++
      total += item.score;
    }
  })


  return num==0?0: (total/num).toFixed(1);
})

const notSubmitted = computed(() => {
  let num=0;
  selectData.value.forEach((item) => {
    if (item.score == null) {
   num++
    }
  })

  return num;
})

function openView(row) {
  // window.open(`/assets1/paperImg/${ row.paperid}.png`, '_blank', 'width=1000,top=100,left=100')

  window.open( '','','width=600 ,height=2000,top=100,left=600,location=no,menubar=no,status=no,titlebar=no,toolbar=no')
      .document.write(`<img style="width: 100%" src='/assets1/paperImg/${ row.paperid}.png'/>`);
}
watch(selectValue,()=>{
  selectGrades()
    }
)

</script>

<template>
  <div style="position: relative;height: 102vh">

    <div style="background-position: center" :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;min-height: 46vh ;margin-top: -20px`">

    </div>
    <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto;margin-top: -30px;min-height: 58vh">
      <!--      {{ searchForm }}-->
      <div>
        <t-button size="small" @click="$router.push('/manage/papers')" theme="primary">
          <template #icon>
            <t-icon name="add"/>
          </template>
          新建
        </t-button>

        <t-tag variant="light-outline" theme="primary"  style="margin-left: 6px" shape="round" size="small">共有{{total}}条记录</t-tag>
        <div style="display: inline-block;float: right;width:200px;margin-left: 10px">

          <t-pagination :totalContent="false"  v-model="searchForm.page" theme="simple" :showPageSize="false" size="small"
                        :total="total"/>
        </div>


        <t-date-range-picker   v-model="searchForm.time" presetsPlacement="left" size="small"
                               style="float: right;width: 208px;margin-left: 10px" clearable/>

        <t-input v-model.lazy="searchForm.search" size="small" style="width: 20%;float: right" placeholder="输入试卷名称"
                 clearable>
          <template #suffixIcon>
            <t-icon :style="{ cursor: 'pointer' }" name="search"/>
          </template>
        </t-input>


      </div>

      <t-table
          size="small"
          row-key="index"
          :data="data"
          :columns="[
            { colKey: 'paperid', title: 'ID'},
            { colKey: 'papername', title: '试卷名',ellipsis: true,},
            // { colKey: 'paperDescription', title: 'paperDescription'},
            { colKey: 'creatorusername', title: '创建者'},
            { colKey: 'totalscore', title: '总分'},
           { colKey: 'classid', title: '参考班级'},
           { colKey: 'starttime', title: '开始时间',width:'150px' },
            { colKey: 'duration', title: '考试时长'},
            {
    title: '操作',
    colKey: 'operation',
      }
      ]"
      >

        <template #classid="{ row }">
          <span style="padding: 2px" v-for="item in row.classid" theme="success" variant="light">{{ item }}</span>
        </template>

        <template #operation="{ row }">
          <t-link theme="primary" hover="color" @click="openView(row)">
            预览
          </t-link>&nbsp;
          <t-link theme="primary" hover="color" @click="openGrades(row)">
            成绩
          </t-link>
        </template>

      </t-table>

    </t-card>
    <t-drawer :closeBtn="true" :footer="false" destroy-on-close v-model:visible="drawerVisible"  size="520"  >
      <template #header>
        <h3 style="position: relative;width: 100%"> 成绩详情

          <t-select   v-model="selectValue"  style="position: absolute;margin-top: -24px;right: 30px;width: 200px">
            <t-option v-for="item in selectClass" :key="item.classid" :label="item.name" :value="item.classid" />
          </t-select>
        </h3>


      </template>

      <div style="width: 100%;position: relative;height: 36px;margin-top: -8px">

        <download-excel
style="float: right"
            :data="excelData"
            type="xls"
            name="成绩导出.xls"
        >
          <t-button variant="outline">
            <template #icon>
              <t-icon name="upload">

              </t-icon> </template>
            导出Excel
          </t-button>

        </download-excel>
        <t-space :size="8">
          <t-tag variant="outline" theme="primary"   >
            正在考试：{{onlineUser.length}}
          </t-tag>
          <t-tag variant="outline" theme="danger"   >
            未提交：{{notSubmitted}}
          </t-tag>
          <t-tag variant="outline" theme="success"   >
            平均分：{{averageMinutes}}
          </t-tag>
        </t-space>

      </div>
<!--      {{selectData}}-->
      <GradesDetail :onlineUser="onlineUser" :selectData="selectData"/>
    </t-drawer>
  </div>

</template>

<style scoped>
.tdesign-demo__panel-options-multiple {
  width: 100%;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.tdesign-demo__panel-options-multiple .t-checkbox {
  display: flex;
  border-radius: 3px;
  line-height: 22px;
  cursor: pointer;
  padding: 3px 8px;
  color: var(--td-text-color-primary);
  transition: background-color 0.2s linear;
  white-space: nowrap;
  word-wrap: normal;
  overflow: hidden;
  text-overflow: ellipsis;
  margin: 0;
}

.tdesign-demo__panel-options-multiple .t-checkbox:hover {
  background-color: var(--td-bg-color-container-hover);
}
</style>