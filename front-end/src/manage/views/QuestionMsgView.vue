<script setup lang="jsx">
import {computed, nextTick, reactive, ref, watch} from "vue";
import {useCounterStore} from "@/stores/counter";
import dayjs from "dayjs";
import service from "@/axios/service";
import {DialogPlugin, MessagePlugin} from "tdesign-vue-next";

const store = useCounterStore();
const form = ref();
const tableData = ref({
  "records": [
    {
      "id": 1,
      "title": "这是一条通知",
      "details": "这是通知详情",
      "addtime": "2024-01-10T11:15:42.000+00:00"
    }
  ],
  "total": 1,
  "size": 10,
  "current": 1,
  "pages": 1
});
const operationValue = reactive({
  options: {
    group: 0,
    course: 0,
    specia: 1,
  },
  isUpdate: false,
  insertStep: 0,
  selectedRowKeys: [],
  visible: false,
  page: 1,
  search: '',
  pojo: {
    question: '',
    answers: ['', '', '', ''],
    ranswer: [],
    parse: '',
    "groupid": 1, "difficulty": 3, "enable": 1,
  }
});
const courseValue = ref([])
const groupValue = ref([])

const specias = ref([]);
service.get("/specia/getAllFull")
    .then(response => {
      // 处理成功响应
      console.log(response.data);
      specias.value = response.data
      operationValue.specia = response.data[0].id
      courseValue.value = specias.value[0].questionCourse;
      operationValue.options.course = courseValue.value[0].id
      groupValue.value = courseValue.value[0].questionGroups;
      operationValue.options.group = groupValue.value[0].id
      // initOptions()
    })
    .catch(error => {
      // 处理错误
      console.error('There was an error!', error);
    });


function initCourse(val) {
  if (specias.value.find((item) => item.id === val).questionCourse.length > 0) {
    courseValue.value = specias.value.find((item) => item.id === val).questionCourse;
    operationValue.options.course = courseValue.value[0].id
  } else {
    courseValue.value = []
    operationValue.options.course = null

  }

  initGroups()
}

// watch(() => operationValue.options.course, (val) => {
// })
function initGroups() {
  if (courseValue.value.find((item) => item.id === operationValue.options.course) !== undefined && courseValue.value.find((item) => item.id === operationValue.options.course).questionGroups.length > 0) {
    groupValue.value = courseValue.value.find((item) => item.id === operationValue.options.course).questionGroups
    operationValue.options.group = groupValue.value[0].id
  } else {

    groupValue.value = []
    operationValue.options.group = null
  }
}


function initContex() {
  service.get(`/question/selectLimit?page=${operationValue.page}&search=${operationValue.search}`).then(
      (res) => {
        tableData.value = res.data;
        console.log(res);
      }
  )
}

initContex()

async function beforeSubmit() {
  // if (typeof await form.value.validate() === 'boolean') {
  if (operationValue.options.group == null) {
    MessagePlugin.error("请选择课程")
    return;
  }
  let tempPojo = {...operationValue.pojo};
  tempPojo.answers = JSON.stringify(tempPojo.answers)
  tempPojo.ranswer = tempPojo.ranswer.toString();
  tempPojo.groupid = operationValue.options.group
  console.log(tempPojo)

  service.post(`/question/${operationValue.isUpdate ? 'update' : 'insert'}`, tempPojo).then((res) => {
    console.log(res)
    initContex()
    if (res.data > 0) {
      MessagePlugin.success((operationValue.isUpdate ? "修改" : "新增") + "成功")
    } else {
      MessagePlugin.error((operationValue.isUpdate ? "修改" : "新增") + "失败")
    }
    operationValue.visible = false;
  })
  // } else {
  //
  // }
}

function toDeleteInBulk() {
  if (operationValue.selectedRowKeys.length > 0) {
    const confirmDia = DialogPlugin({
      header: '提示',
      body: '您确认删除所选 ' + operationValue.selectedRowKeys.length + ' 项数据吗？',
      confirmBtn: {
        content: '删除',
        theme: 'danger',
      },
      cancelBtn: {content: '取消', variant: 'outline'},
      onConfirm: ({e}) => {
        // if (typeof await form.value.validate() === 'boolean') {

        deletes(operationValue.selectedRowKeys)
        confirmDia.hide();
      }
    });
  } else {
    MessagePlugin.error("请选择要删除的项")
  }

}

function deletes(id) {
  service.post(`/question/deletes?ids=` + id.toString(),).then((res) => {
    console.log(res)
    initContex()
    if (res.data > 0) {
      MessagePlugin.success("删除成功")
    } else {
      MessagePlugin.error("删除失败")
    }

  })
}

async function toUpdate(row) {
  operationValue.visible = true;
  operationValue.isUpdate = true;
  operationValue.pojo = {...row};
  operationValue.pojo.answers = JSON.parse(row.answers);
  operationValue.pojo.ranswer = row.ranswer.split(',');
  operationValue.pojo.ranswer.forEach((item, index) => operationValue.pojo.ranswer[index] = parseInt(item))
  // operationValue.pojo.groupid;
  let options = findCourseObject(operationValue.pojo.groupid);
  operationValue.options = {...options};
  operationValue.insertStep = 0


}

async function nextTab() {
  if (typeof await form.value.validate() === 'boolean') {
    let a = false;
    operationValue.pojo.answers.forEach(item => {
      if (item == '') {
        a = true
      }
    })
    if (a) {
      MessagePlugin.error("请填写完整")
    } else {
      operationValue.insertStep++

    }
  }
}

function findCourseObject(id) {
  for (const sp of specias.value) {
    for (const c of sp.questionCourse) {
      const targetGroup = c.questionGroups.find(g => g.id === id);

      if (targetGroup) {
        return {
          specia: sp.id,
          course: c.id,
          group: targetGroup.id,
        };
      }
    }
  }

  // 如果没有找到匹配的id，则返回默认值或特定错误提示
  return {specia: null, course: null, group: null};
}

// function findCourseObject(id) {
//   let o = {speciaid: null, courseid: null, groupid: null}
//   let isFound = false;
//   specias.value.forEach(sp => {
//     sp.questionCourse.forEach(c => {
//
//       c.questionGroups.forEach(g => {
//         if (g.id === id) {
//           o.groupid = g.id;
//           isFound = true;
//         }
//       })
//       if (isFound){
//         o.courseid = c.id;
//       }
//     })
//     if (isFound){
//       o.speciaid = sp.id;
//     }
//   })
//   return o;
// }

// function courseValueTrue(id) {
//   let name="";
//   specias.value.forEach(sp => {
//     sp.questionCourse.forEach(c => {
//       console.log(c.questionGroups)
//         c.questionGroups.forEach(g => {
//       if ( g.id === id)  {
//         name = g.name;
//       }
//       })
//     })
//   })
//   return name
// }
function courseValueTrue(id) {
  for (const sp of specias.value) {
    const foundGroup = sp.questionCourse.find(c =>
        c.questionGroups.some(g => g.id === id)
    );
    if (foundGroup) {
      const targetGroup = foundGroup.questionGroups.find(g => g.id === id);
      return targetGroup ? targetGroup.name : "";
    }
  }
  return "";
}


</script>

<template>
  <div style="background-position: center"
       :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;  `">
    <div :class="store.darkT?'boxdark':'box'"
         style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 3vh;padding-bottom: 10px">
      <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto; min-height: 94vh">

        <t-dialog

            placement="center"
            v-model:visible="operationValue.visible"
            :confirm-btn="{
          content: '下一步',
          variant: 'outline',
        }"
            width="640px"
            @confirm="nextTab"
        >

          <template #header>
            <span style="float: left;user-select: none">{{ operationValue.isUpdate ? '修改试题' : '创建试题' }}</span>

            <t-steps id="setpicon"
                     style="text-decoration:none;width: 320px;margin:0 auto;letter-spacing:1px;user-select: none"
                     class="bbb" v-model="operationValue.insertStep">
              <t-step-item title="编辑"/>
              <t-step-item title="选择分类"/>

            </t-steps>


          </template>
          <div style="min-height: 100px" slot="body">

            <t-form v-if="operationValue.insertStep<1" layout="inline" ref="form" :rules="{
  question: [{required: true, message: '题目不能为空'}],
  ranswer: [{required: true, message: '内容不能为空'}]
}" :data="operationValue.pojo" :colon="true">

              <t-form-item style="width: 96%;margin: 8px 0" label="题目" name="question">
                <t-textarea :autosize="{ minRows: 1 }"
                            v-model="operationValue.pojo.question" placeholder="请输入内容"

                />
                <div style="margin-left: 10px">
                  <t-button @click=" operationValue.pojo.answers.push('')" shape="circle"
                  >
                    <t-icon name="add"/>
                  </t-button>
                </div>
              </t-form-item>
              <t-form-item v-for="(item,index) in operationValue.pojo.answers" style="width: 96%;margin: 8px 0"
                           :label="'选项'+(index+1)" name="answers">
                <t-textarea :autosize="{ minRows: 1 }"
                            v-model="operationValue.pojo.answers[index]" placeholder="请输入内容"

                />

                <div style="margin-left: 10px">
                  <t-button
                      @click="operationValue.pojo.ranswer=[];operationValue.pojo.answers.length>2?operationValue.pojo.answers.splice(index,1):$message('warning', '需要至少保留两个选项')"
                      variant="outline" shape="circle"
                  >
                    <t-icon name="close"/>
                  </t-button>
                </div>
              </t-form-item>
              <t-form-item style="width: 96%;margin: 8px 0" label="正确答案" name="ranswer">
                <t-checkbox-group v-model="operationValue.pojo.ranswer">
                  <t-checkbox :key="index" :value="(index+1)" v-for="(item,index) in operationValue.pojo.answers">选项
                    {{ index + 1 }}
                  </t-checkbox>

                </t-checkbox-group>

              </t-form-item>

              <t-form-item style="width: 96%;margin: 8px 0" label="试题解析" name="parse">
                <t-textarea :autosize="{ minRows: 1 }"
                            v-model="operationValue.pojo.parse" placeholder="请输入内容"

                />
              </t-form-item>

            </t-form>
            <t-form v-else>

              <t-form-item label="专业" name="gender">
                <t-radio-group @change="initCourse" v-model="operationValue.options.specia">
                  <t-radio v-for="{id,name} in specias" :value="id">{{ name }}</t-radio>
                </t-radio-group>
              </t-form-item>
              <t-form-item label="课程" name="course">

                <t-select v-model="operationValue.options.course">
                  <t-option @click="initGroups" v-for="item in  courseValue" :key="item.id" :label="item.name"
                            :value="item.id"/>

                </t-select>
              </t-form-item>
              <t-form-item label="章节" name="group">
                <t-select v-model="operationValue.options.group">
                  <t-option v-for="item in  groupValue" :key="item.id" :label="item.name" :value="item.id"/>

                </t-select>
              </t-form-item>


            </t-form>


          </div>
          <template #footer>
            <div v-if="operationValue.insertStep>0">
              <t-button ghost @click="operationValue.insertStep--" theme="primary">
                上一步
              </t-button>
              <t-button @click="beforeSubmit" theme="primary">
                完成
              </t-button>
            </div>

          </template>

        </t-dialog>
        <div>
          <t-space :size="6">
            <t-button size="small"
                      @click="operationValue.visible=true;operationValue.isUpdate=false;$refs.form.reset();operationValue.pojo.answers=['','','','']"
                      theme="primary">
              <template #icon>
                <t-icon name="add"/>
              </template>
              新建
            </t-button>
            <t-button size="small" @click="toDeleteInBulk" theme="danger">
              <template #icon>
                <t-icon name="delete"/>
              </template>
              删除
            </t-button>
            <t-tag variant="light-outline" theme="primary" shape="round" size="small">
              共有{{ tableData.total }}条记录
            </t-tag>
          </t-space>


          <div style="display: inline-block;float: right;width:200px;margin-left: 10px">

            <t-pagination :totalContent="false" @change="initContex" v-model="operationValue.page" theme="simple"
                          :pageSize="15"
                          :showPageSize="false"
                          size="small"
                          :total="tableData.total"/>
          </div>


          <t-input @input="operationValue.page=1;initContex();" v-model.lazy="operationValue.search" size="small"
                   style="width: 20%;float: right"
                   placeholder="输入内容"
          >
            <template #suffixIcon>
              <t-icon @click="operationValue.page=1;initContex();" :style="{ cursor: 'pointer' }" name="search"/>
            </template>
          </t-input>

        </div>

        <t-table
            row-key="id"
            v-model:selected-row-keys="operationValue.selectedRowKeys"
            :data="tableData.list"
            :columns="[
           { colKey: 'row-select', type: 'multiple' ,width: 60,},
            { colKey: 'id', title: 'ID'},
            { colKey: 'question', title: '标题',ellipsis: true,},
            // { colKey: 'paperDescription', title: 'paperDescription'},
            { colKey: 'answers', title: '详情',ellipsis: true},
            // { colKey: 'ranswer', title: '详情'},
            { colKey: 'groupid', title: '章节',ellipsis: true},
            // { colKey: 'difficulty', title: '详情'},
            // { colKey: 'enable', title: '详情'},
            { colKey: 'parse', title: '详情',ellipsis: true},

            {
    title: '操作',
    colKey: 'operation',
      }
      ]"
        >


          <template #groupid="{ row }">
            {{ courseValueTrue(row.groupid) }}
          </template>

          <template #operation="{ row }">
            <t-link @click="toUpdate(row)"
                    theme="primary" hover="color">
              修改
            </t-link>&nbsp;
            <t-popconfirm theme="danger" :confirmBtn="{theme: 'danger'}" @confirm="deletes(row.id)"
                          :cancelBtn="{variant: 'outline'}" content="确认删除吗">
              <t-link theme="danger" hover="color">
                删除
              </t-link>

            </t-popconfirm>


          </template>

        </t-table>
        <!--        {{ operationValue }}-->
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
</style>