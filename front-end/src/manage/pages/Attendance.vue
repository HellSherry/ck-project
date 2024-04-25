<script setup>
import {computed, reactive, ref, watch} from "vue";
import service from "@/axios/service";
import {useCounterStore} from "@/stores/counter";
import dayjs from "dayjs";
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
  isUpdate: false,
  selectedRowKeys: [],
  visible: false,
  page: 1,
  search: '',
  pojo: {
    "id": "",
    "stedent": "",
    "classs": "",
    "date": "",
    "time": "",
    "status": "",
    "details": ""

  },
   options1 : [
    { label: '同意', value: '同意' },
    { label: '审批中', value: '审批中' },
    { label: '拒绝', value: '拒绝' },

  ],

});


function initContex() {
  service.get(`/attendance/selectAll?page=${operationValue.page}&size=8&names=${operationValue.search}`).then(
      (res) => {
        tableData.value = res.data;
        console.log(res);
      }
  )
}


initContex()

async function beforeSubmit() {

  if (typeof await form.value.validate() === 'boolean') {
    service.post(`/attendance/${operationValue.isUpdate ? 'update' : 'insert'}`, operationValue.pojo).then((res) => {
      console.log(res)
      initContex()

      if (res.data > 0) {
        MessagePlugin.success((operationValue.isUpdate ? "修改" : "新增") + "成功")
      } else {
        MessagePlugin.error((operationValue.isUpdate ? "修改" : "新增") + "失败")

      }
      operationValue.pojo.id= "";
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
  service.post(`/attendance/delete?ids=` + id.toString()).then((res) => {
    console.log(res)
    initContex()
    if (res.data > 0) {
      MessagePlugin.success("删除成功")
    } else {
      MessagePlugin.error("删除失败")
    }

  })
}

</script>

<template>
  <div style="position: relative;height: 102vh">
    <t-dialog
        placement="center"
        :header="operationValue.isUpdate?'编辑考勤信息':'新增考勤'"
        :visible="operationValue.visible"
        :on-confirm="()=>beforeSubmit()"
        :on-close="()=>operationValue.visible=false"
    >
      <t-form layout="inline" ref="form" :rules="{
  // id: [{required: true, message: '邮箱不能为空'}],
  stedent: [{required: true, message: '姓名不能为空'}],
  classs: [{required: true, message: '班级不能为空'}],
  // time: [{required: true, message: '考勤时间不能为空'}],
  date: [{required: true, message: '考勤日期不能为空'}],
  status: [{required: true, message: '状态不能为空'}],
  details: [{required: true, message: '详情不能为空'}]
 }" :data="operationValue.pojo" :colon="true">
<!--        <t-form-item style="width: 96%;margin: 8px 0" label="邮箱" name="userEmail">-->
<!--          <t-input v-model="operationValue.pojo.id" placeholder="请输入邮箱"></t-input>-->
<!--        </t-form-item>-->
        <t-form-item style="width: 96%;margin: 8px 0" label="学员姓名" name="stedent">
          <t-input v-model="operationValue.pojo.stedent" placeholder="请输入学员姓名"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="班级" name="classs">
          <t-input  v-model="operationValue.pojo.classs" placeholder="请输入班级"/>
<!--          <t-input placeholder="请输入开始时间"></t-input>-->
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="考勤日期" name="date">
          <t-date-picker v-model="operationValue.pojo.date"  enable-time-picker allow-input clearable />

        </t-form-item>
<!--        <t-form-item style="width: 96%;margin: 8px 0" label="考勤时间" name="time">-->
<!--          <t-date-picker v-model="operationValue.pojo.time" placeholder="请输入考勤时间"></t-date-picker>-->
<!--        </t-form-item>-->

        <t-form-item style="width: 96%;margin: 8px 0" label="状态" name="status">
          <!-- 方式一：使用 options 输出下拉选项。优先级高于 t-option-->
          <t-input v-model="operationValue.pojo.status" placeholder="请输入考勤状态"></t-input>

        </t-form-item>

        <t-form-item style="width: 96%;margin: 8px 0" label="详情" name="details">
          <t-input v-model="operationValue.pojo.details" placeholder="请输入详情"></t-input>
        </t-form-item>



      </t-form>
      <!--      {{ operationValue.pojo }}       -->
    </t-dialog>


    <!--    <div style="background-position: center"-->
    <!--         :style="`background-image: url('${store.darkT? `/src/assets/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/src/assets/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;min-height: 46vh ;margin-top: -20px`">-->

    <!--    </div>-->
    <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto;margin-top: 30px;min-height: 94vh">
      <!--      {{ searchForm }}-->
      <div>
        <t-space :size="6">
          <t-button size="small" @click="operationValue.visible=true;operationValue.isUpdate=false;$refs.form.reset();"
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
                        :pageSize="8"
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
            { colKey: 'stedent', title: '学员姓名',ellipsis: true,},
            // { colKey: 'paperDescription', title: 'paperDescription'},
            { colKey: 'classs', title: '班级'},
             // { colKey: 'time', title: '考勤时间'},
            // { colKey: 'userImg', title: '用户头像'},
            { colKey: 'date', title: '考勤时间'},
            { colKey: 'status', title: '状态'},
            { colKey: 'details', title: '详情'},
            {
    title: '操作',
    colKey: 'operation',
      }
      ]"
      >
        <template #date="{ row }">
          {{ dayjs(row.date).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
<!--        <template #time="{ row }">-->
<!--          {{ dayjs(row.time).format('HH:mm:ss') }}-->
<!--        </template>-->


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