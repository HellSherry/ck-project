<script setup>
import {computed, reactive, ref, watch} from "vue";
import service from "@/axios/service";
import {useCounterStore} from "@/stores/counter";
import dayjs from "dayjs";
import {Input, Select, DialogPlugin, MessagePlugin} from "tdesign-vue-next";
import CourseTabledetails from "@/manage/pages/CourseTabledetails.vue";
// import { Input, Select, DatePicker, MessagePlugin } from 'tdesign-vue-next';

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
  isUpdate: false,
  selectedRowKeys: [],
  visible: false,
  page: 1,
  search: '',
  pojo: {
    id: '',
    classid: '',
    detail: [],
    name: ''
  },
  classs: [],
});


function initContex() {
  service.get(`/courseschedule/page?page=${operationValue.page}&size=10&search=${operationValue.search}`).then(
      (res) => {
        tableData.value = res.data;
        tableData.value.records.forEach(item => {
          // console.log(JSON.parse(item.detail))
          item.detail = JSON.parse(item.detail)

        })

        console.log(res);
      }
  )
}

initContex()

async function beforeSubmit() {
  operationValue.pojo.detail = JSON.stringify(operationValue.pojo.detail)
  if (typeof await form.value.validate() === 'boolean') {
    service.post(`/courseschedule/${operationValue.isUpdate ? 'update' : 'insert'}`, operationValue.pojo).then((res) => {

      console.log(res)
      initContex()
      if (res.data > 0) {
        MessagePlugin.success((operationValue.isUpdate ? "修改" : "新增") + "成功")
      } else {
        MessagePlugin.error((operationValue.isUpdate ? "修改" : "新增") + "失败")
      }

      operationValue.visible = false;
    })
  } else {

  }
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
        deletes(operationValue.selectedRowKeys)
        confirmDia.hide();
      }
    });
  } else {
    MessagePlugin.error("请选择要删除的项")
  }

}

function deletes(id) {
  service.post(`/courseschedule/delete`, typeof id === 'object' ? id : [id]).then((res) => {
    console.log(res)
    initContex()
    if (res.data > 0) {
      MessagePlugin.success("删除成功")
    } else {
      MessagePlugin.error("删除失败")
    }

  })
}

function findClass(classid) {
  let find = operationValue.classs.find(e => e.id == classid)
  if (find != null) {
    return find.name
  }
}

function classs() {
  service.get(`/classInfo/selectLimit?page=${operationValue.page}&search=${operationValue.search}`).then(
      (res) => {
        operationValue.classs = res.data.list;

        console.log(res);
      }
  )
}

classs()

function initDeil() {
  operationValue.pojo.detail =
      [
        {
          day: '星期一',
          lesson_1: '开发',
          lesson_2: '开发',
          lesson_3: '开发',
          lesson_4: '开发',
          lesson_5: '实训',
          lesson_6: '实训',
          lesson_7: '实训',
          lesson_8: '实训',
          eveningSelfStudy: '晚自习'
        },{
        day: '星期二',
        lesson_1: '实训',
        lesson_2: '实训',
        lesson_3: '实训',
        lesson_4: '实训',
        lesson_5: '开发',
        lesson_6: '开发',
        lesson_7: '开发',
        lesson_8: '开发',
        eveningSelfStudy: '晚自习'
      },{
        day: '星期三',
        lesson_1: '开发',
        lesson_2: '开发',
        lesson_3: '开发',
        lesson_4: '开发',
        lesson_5: '实训',
        lesson_6: '实训',
        lesson_7: '实训',
        lesson_8: '实训',
        eveningSelfStudy: '晚自习'
      },{
        day: '星期四',
        lesson_1: '实训',
        lesson_2: '实训',
        lesson_3: '实训',
        lesson_4: '实训',
        lesson_5: '开发',
        lesson_6: '开发',
        lesson_7: '开发',
        lesson_8: '开发',
        eveningSelfStudy: '晚自习'
      },{
        day: '星期五',
        lesson_1: '开发',
        lesson_2: '开发',
        lesson_3: '开发',
        lesson_4: '开发',
        lesson_5: '实训',
        lesson_6: '实训',
        lesson_7: '实训',
        lesson_8: '实训',
        eveningSelfStudy: '晚自习'
      },{
        day: '星期六',
        lesson_1: '实训',
        lesson_2: '实训',
        lesson_3: '实训',
        lesson_4: '实训',
        lesson_5: '开发',
        lesson_6: '开发',
        lesson_7: '开发',
        lesson_8: '开发',
        eveningSelfStudy: '晚自习'
      },{
        day: '星期日',
        lesson_1: '实训',
        lesson_2: '实训',
        lesson_3: '实训',
        lesson_4: '实训',
        lesson_5: '开发',
        lesson_6: '开发',
        lesson_7: '开发',
        lesson_8: '开发',
        eveningSelfStudy: '晚自习'
      }

      ]
  operationValue.pojo.name=null;
  operationValue.pojo.classid=null;


}


</script>


<template>
  <div style="position: relative;height: 102vh">
    <t-dialog
        destroyOnClose="true"
        placement="center"
        :header="operationValue.isUpdate?'编辑课表':'新增课表'"
        :visible="operationValue.visible"
        :on-confirm="()=>beforeSubmit()"
        :on-close="()=>operationValue.visible=false"
        width="130vh"

    >
       <t-form layout="inline" ref="form" :rules="{
  classid: [{required: true, message: '班级不能为空'}],
  detail: [{required: true, message: '内容不能为空'}],
  name: [{required: true, message: '标题不能为空'}]
}" :data="operationValue.pojo" :colon="true"

      >

        <!--        <t-form-item style="width: 96%;margin: 8px 0" label="classid" name="classid">-->
        <!--          <t-input v-model="operationValue.pojo.classid" placeholder="请输入内容"></t-input>-->
        <!--        </t-form-item>-->
        <t-form-item style="width: 40%;margin: 8px 0" label="班级" name="classid">
          <t-space style="width: 100%">
            <t-select
                v-model="operationValue.pojo.classid"
                placeholder="请选择管理的班级"
                clearable
                @focus="onFocus"
                @blur="onBlur"
            >
              <t-option v-for="class1 in operationValue.classs" :key="class1.id" :value="class1.id"
                        :label="class1.name" v-model="operationValue.pojo.classs      "></t-option>
            </t-select>
          </t-space>
        </t-form-item>
        <t-form-item style="width: 40%;margin: 8px 0" label="课表名" name="name">
          <t-input v-model="operationValue.pojo.name" placeholder="请输入内容"></t-input>
        </t-form-item>
        <div></div>
        <!--        <t-form-item style="width: 96%;margin: 8px 0" label="内容" name="detail">-->
        <!--          <t-input v-model="operationValue.pojo.detail" placeholder="请输入内容"></t-input>-->
        <!--        </t-form-item>-->

        <div>
          <CourseTabledetails @dataChange="(s)=>{operationValue.pojo.detail=s}"
                              :table-data="operationValue.pojo.detail"/>
        </div>


      </t-form>
    </t-dialog>


    <div style="background-position: center"
         :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;min-height: 46vh ;margin-top: -20px`">

    </div>
    <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto;margin-top: -30px;min-height: 58vh">
      <!--      {{ searchForm }}-->
      <div>
        <t-space :size="6">
          <t-button size="small"
                    @click="initDeil();operationValue.visible=true;operationValue.isUpdate=false;$refs.form.reset(); operationValue.pojo.id=null;"
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
                        :showPageSize="false"
                        size="small"
                        :total="tableData.total"/>
        </div>


        <t-input @input.lazy="operationValue.page=1;initContex();" v-model.lazy="operationValue.search" size="small"
                 style="width: 20%;float: right"
                 placeholder="输入内容"
                 clearable>
          <template #suffixIcon>
            <t-icon :style="{ cursor: 'pointer' }" name="search"/>
          </template>
        </t-input>

      </div>

      <t-table
          size="small"
          row-key="id"
          v-model:selected-row-keys="operationValue.selectedRowKeys"
          :data="tableData.records"
          :columns="[
           { colKey: 'row-select', type: 'multiple' ,width: 60,},
            { colKey: 'id', title: 'ID'},
            { colKey: 'classid', title: '班级',ellipsis: true,},

            // { colKey: 'detail', title: ''},
            { colKey: 'name', title: '课表名'},
            {
    title: '操作',
    colKey: 'operation',
      }
      ]"
      >

        <template #classid="{ row }">

          {{ findClass(row.classid) }}
        </template>
        <template #addtime="{ row }">
          {{ dayjs(row.addtime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>

        <template #operation="{ row }">
          <t-link @click="operationValue.visible=true;operationValue.isUpdate=true;operationValue.pojo={...row}"
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

    </t-card>
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