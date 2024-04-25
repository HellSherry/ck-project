<script setup>
import {computed, reactive, ref, watch} from "vue";
import {useCounterStore} from "@/stores/counter";
import dayjs from 'dayjs';
import service from "@/axios/service";
import {Message, MessagePlugin} from "tdesign-vue-next";
import App from "@/App.vue";

const form = ref(null);
const total = ref(0);
const searchForm = reactive({

  page: 1,
  search: '',
  time: [],
});
const FORM_RULES = {
  papername: [{required: true, message: '试卷名称不能为空'}],
  classid: [{required: true, message: '参考班级不能为空'}],
  starttime: [{required: true, message: '开始时间不能为空'}],
  duration: [{required: true, message: '时长不能为空'}],
};

const insertVisible = ref(false);
const store = useCounterStore();
const tab = ref(1);

const inputValue = ref('');
// 全量数据
const options = ref([
  // 全选
  {label: '全选', checkAll: true},
  {label: 'T35', value: 1},
  {label: 'T36', value: 2},
  {label: 'T37', value: 3},
  {label: 'T38', value: 4},
  {label: 'T39', value: 5},
  {label: 'T40', value: 6},
]);
const classValue = ref([]);


const checkboxValue = computed(() => {
  const arr = [];
  const list = classValue.value;
  // 此处不使用 forEach，减少函数迭代
  for (let i = 0, len = list.length; i < len; i++) {
    list[i].value && arr.push(list[i].value);
  }
  return arr;
});
const totalNum = computed(() => {
  let total = 0;
  Object.keys(formData.questions).forEach((key) => {
    total += formData.questions[key];
  });
  return total;
});

// 直接 checkboxgroup 组件渲染输出下拉选项
const onCheckedChange = (val, {current, type}) => {
  console.log(current);
  // current 不存在，则表示操作全选
  if (!current) {
    classValue.value = type === 'check' ? options.value.slice(1) : [];
    return;
  }
  // 普通操作
  if (type === 'check') {
    const option = options.value.find((t) => t.value === current);
    classValue.value.push(option);
  } else {
    classValue.value = classValue.value.filter((v) => v.value !== current);
  }
};

// 可以根据触发来源，自由定制标签变化时的筛选器行为
const onTagChange = (currentTags, context) => {
  console.log(currentTags, context);
  const {trigger, index, item} = context;
  if (trigger === 'clear') {
    classValue.value = [];
  }
  if (['tag-remove', 'backspace'].includes(trigger)) {
    classValue.value.splice(index, 1);
  }
};
const onInputChange = (val, context) => {
  console.log(val, context);
};
const insertStep = ref(0);
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

function selectAll() {
  let url = '';
  if (searchForm.search !== '' && searchForm.search !== undefined) {
    url += ('&search=' + searchForm.search)
  }
  if (typeof searchForm.time === 'object' && searchForm.time.length > 0) {
    url += ('&starttime=' + searchForm.time[0] + '&endtime=' + searchForm.time[1])
    // url+=(url===''?'?':'&')+('starttime='+searchForm.time[0]+'&endtime='+searchForm.time[1])
  }
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

selectAll()
const formData = reactive({
  specia: 1,
  papername: '',
  starttime: dayjs().valueOf(),
  totalscore: 100,
  duration: 60,
  classid: checkboxValue,
  questions: {},
});


const isInitOption = ref(false);

function initOptions() {
  isInitOption.value = true;

  options.value = []
  classValue.value = [];
  service.get("/classInfo/getBySpeciaid?id=" + formData.specia)
      .then(response => {
        // 处理成功响应
        console.log(response.data);
        options.value = [
          // 全选
          {label: '全选', checkAll: true}
        ]
        response.data.forEach(item => {
          options.value.push({label: item.name, value: item.id})
        })
        // specias.value = response.data
      })
      .catch(error => {
        // 处理错误
        console.error('There was an error!', error);
      }).finally(() => {

    isInitOption.value = false;
  })

}

const specias = ref([]);
service.get("/specia/getAll")
    .then(response => {
      // 处理成功响应
      console.log(response.data);
      specias.value = response.data
      formData.specia = response.data[0].id
      initOptions()
    })
    .catch(error => {
      // 处理错误
      console.error('There was an error!', error);
    });

const courseValue = ref([]);
const presets = ref({
  最近7天: dayjs().add(7, 'day'),
  最近3天: dayjs().add(3, 'day'),
  今天: dayjs(),
});

watch(insertStep, async (newVal, oldVal) => {

  if (newVal === 1) {
    if (typeof await form.value.validate() === 'boolean') {
      service.get("/questionCourse/getCourseAndGroupBySpeciaid?id=" + formData.specia)
          .then(response => {
            // 处理成功响应
            console.log(response.data);
            courseValue.value = response.data;
          })
          .catch(error => {
            // 处理错误
            console.error('There was an error!', error);
          })
    } else {
      MessagePlugin.warning('请完整填写信息')
      insertStep.value = oldVal;
    }
  }

})

function toAdd() {
  Object.keys(formData.questions).forEach(key => {
    if (formData.questions[key] <= 0) {
      delete formData.questions[key]
    }
  })
  if (Object.keys(formData.questions).length === 0) {
    MessagePlugin.error("至少选择一个题目")
  } else {
    let tempData = {...formData}
    tempData.questions = JSON.stringify(tempData.questions)
    tempData.classid = JSON.stringify(tempData.classid)

    service.post("/paper/add", tempData
    )
        .then(response => {
          // 处理成功响应
          console.log(response.data);
          selectAll()
          insertVisible.value=false;
          MessagePlugin.success("创建成功");
          insertStep.value=0
          formData.questions={}
          formData.papername=''
        })
        .catch(error => {
          // 处理错误
          console.error('There was an error!', error);
        })
  }
}
watch(searchForm, () => {
  selectAll()
})

function openView(row) {
  window.open( '','','width=600 ,height=2000,top=100,left=600,location=no,menubar=no,status=no,titlebar=no,toolbar=no')
      .document.write(`<img style="width: 100%" src='/assets1/paperImg/${ row.paperid}.png'/>`);
  // window.open(`/src/assets/paperImg/${ row.paperid}.png`, '_blank', 'width=1000,top=100,left=100')
}

</script>

<template>
  <div style="position: relative;height: 102vh">


    <div
        :style="`background-image: url('${store.darkT? `http://hbimg.huaban.com/8e149140bd05698de8174d7d575ef065263e42d818388b-4YqBxT`:`http://hbimg.huaban.com/8e149140bd05698de8174d7d575ef065263e42d818388b-4YqBxT`}');background-repeat: no-repeat;background-size: cover;min-height: 46vh;background-position-y: 44%;margin-top: -20px`">

    </div>
    <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto;margin-top: -30px;min-height: 58vh">
<!--      {{ searchForm }}-->
      <div>
        <t-button size="small" @click="insertVisible=true" theme="primary">
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

      <t-base-table
          size="small"
          row-key="index"
          :data="data"
          :columns="[
            { colKey: 'paperid', title: 'ID' },
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
          </t-link>
        </template>

      </t-base-table>

    </t-card>
    <t-dialog


        v-model:visible="insertVisible"
        header="创建试卷"
        destroy-on-close
        :confirm-btn="{
          content: '下一步',
          variant: 'outline',
        }"
        width="640px"
        :show-in-attached-element="true"
        placement="center"
        @confirm="()=>{insertStep++}"
    >

      <template #header>
        <span style="float: left;user-select: none;margin-right: 8px">创建试卷 </span>
        <t-tag v-if="totalNum>0" style="margin-left: 10px;margin-right: -60px;">已选:{{totalNum}}</t-tag>

        <t-steps id="setpicon"
                 style="text-decoration:none;width: 320px;margin:0 auto;letter-spacing:1px;user-select: none"
                 class="bbb" v-model="insertStep">
          <t-step-item title="基本信息"/>
          <t-step-item title="选择试题"/>

        </t-steps>


      </template>
      <div style="min-height: 100px" slot="body">
        <t-form v-if="insertStep===0" layout="inline" ref="form" :rules="FORM_RULES" :data="formData" :colon="true"
                @reset="onReset"
                @submit="onSubmit">
          <t-form-item style="width: 96%;margin: 8px 0" label="试卷名称" name="papername">
            <t-input v-model="formData.papername" placeholder="请输入内容" @enter="onEnter"></t-input>
          </t-form-item>

          <t-form-item label="开始时间" name="starttime">
            <t-date-picker style="width: 216px;margin-right: -16px" enable-time-picker allow-input
                           :disable-date="{ before: dayjs().subtract(1, 'day').format() }"
                           value-type="time-stamp"
                           format="YYYY-MM-DD a hh:mm:ss"
                           v-model="formData.starttime" :presets="presets"/>

          </t-form-item>

          <t-form-item style="width: 10px" label="考试时长" name="duration">
            <t-input-number min="1" style="margin-left: -16px" :format="(value) => `${value} 分钟`"
                            v-model="formData.duration"/>

            <!--            <t-switch v-model="formData.duration"></t-switch>-->
          </t-form-item>

          <t-form-item label="专业" name="specia">
            <t-radio-group @change="initOptions" v-model="formData.specia">
              <t-radio v-for="{id,name} in specias" :value="id">{{ name }}</t-radio>
            </t-radio-group>
          </t-form-item>
          <t-form-item style="width: 10px" label="总分" name="totalscore">
            <t-input-number min="1" style="margin-left: -16px" :format="(value) => `${value} 分`"
                            v-model="formData.totalscore"/>

            <!--            <t-switch v-model="formData.duration"></t-switch>-->
          </t-form-item>

          <t-form-item style="width: 100%" label="参考班级" name="classid">
            <t-select-input
                v-model:inputValue="inputValue"
                :loading="isInitOption"
                :value="classValue"
                clearable
                multiple
                @tag-change="onTagChange"
                @input-change="onInputChange"
            >
              <template #panel>
                <t-checkbox-group

                    v-if="options.length"
                    :value="checkboxValue"
                    :options="options"
                    class="tdesign-demo__panel-options-multiple"
                    @change="onCheckedChange"
                />

                <div v-else class="tdesign-demo__select-empty-multiple">暂无数据</div>
              </template>
              <template #suffixIcon>
                <chevron-down-icon/>
              </template>
            </t-select-input>
            <!--            <t-switch v-model="formData.duration"></t-switch>-->
          </t-form-item>

          <!--          <t-form-item>-->
          <!--            <t-space size="small">-->
          <!--&lt;!&ndash;              <t-button theme="primary" type="submit">提交</t-button>&ndash;&gt;-->
          <!--&lt;!&ndash;              <t-button theme="default" variant="base" type="reset">重置</t-button>&ndash;&gt;-->
          <!--              &lt;!&ndash; 下方示例代码，有效，勿删 &ndash;&gt;-->
          <!--              &lt;!&ndash;<t-button theme="default" @click="submitForm">实例方法提交</t-button>&ndash;&gt;-->
          <!--              &lt;!&ndash;<t-button theme="default" variant="base" @click="resetForm">实例方法重置</t-button>&ndash;&gt;-->
          <!--              &lt;!&ndash;<t-button theme="default" variant="base" @click="validateOnly">仅校验</t-button>&ndash;&gt;-->
          <!--            </t-space>-->
          <!--          </t-form-item>-->
        </t-form>
        <div v-else>

          <t-collapse expand-mutex borderless>
            <t-collapse-panel v-for="{id, name,questionGroups} in courseValue" :header="name">
              <t-list style="margin-top: -20px" split size="small">
                <t-list-item v-for="{id,name,courseid} in questionGroups ">{{ name }}({{ courseid==null?courseid=0:courseid }})
                  <template #action>
                    <t-input-number min="0" :max="courseid==null?courseid=0:courseid" v-model="formData.questions[id]" size="small" autoWidth/>
                  </template>

                </t-list-item>
              </t-list>
            </t-collapse-panel>
          </t-collapse>
        </div>

      </div>
      <template #footer>
        <div v-if="insertStep>0">
          <t-button ghost @click="insertStep--" theme="primary">
            上一步
          </t-button>
          <t-button @click="toAdd" theme="primary">
            完成
          </t-button>
        </div>

      </template>

    </t-dialog>
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