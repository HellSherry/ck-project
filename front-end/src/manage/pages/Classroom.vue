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
  options: '',
  classs: [],
  pojo: {
    "id": "",
    "name": "",
    "num": "",
    "teacher": "",
    "classs": "",
  },
  options1: [
    {label: '架构云', value: '1', title: '架构云选项'},
    {label: '大数据', value: '2'},
    {label: '区块链', value: '3'},
    {label: '物联网', value: '4', disabled: true},
    {label: '人工智能', value: '5'},

  ]
});

function classs() {
  service.get(`/classInfo/selectLimit?page=${operationValue.page}&search=${operationValue.search}`).then(
      (res) => {
        operationValue.classs = res.data.list;
        console.log(res);
      }
  )
}

classs()

function initContex() {
  service.get(`/classroom/selectAll?page=${operationValue.page}&size=8&names=${operationValue.search}`).then(
      (res) => {
        tableData.value = res.data;
        console.log(res);
      }
  )
}


initContex()

async function beforeSubmit() {
  if (typeof await form.value.validate() === 'boolean') {
    if (operationValue.isUpdate == false) {
      operationValue.pojo.id = 0
    }

    service.post(`/classroom/${operationValue.isUpdate ? 'update' : 'insert'}`, operationValue.pojo).then((res) => {
      console.log(11121)
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
  service.post(`/classroom/deletes?ids=` + id.toString()).then((res) => {
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
        :header="operationValue.isUpdate?'编辑教室信息':'新增教室'"
        :visible="operationValue.visible"
        :on-confirm="()=>beforeSubmit()"
        :on-close="()=>operationValue.visible=false"
    >
      <t-form layout="inline" ref="form" :rules="{
  name: [{required: true, message: '名称不能为空'}],
  num: [{required: true, message: '数量不能为空'}],
  teacher: [{required: true, message: '老师不能为空'}],
  classs: [{required: true, message: '班级不能为空'}],
 }" :data="operationValue.pojo" :colon="true">
        <t-form-item style="width: 96%;margin: 8px 0" label="名称" name="name">
          <t-input v-model="operationValue.pojo.name" placeholder="请输入名称"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="人数" name="num">
          <t-input v-model="operationValue.pojo.num" placeholder="请输入人数"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="老师" name="teacher">
          <t-input v-model="operationValue.pojo.teacher" placeholder="请输入老师"></t-input>
        </t-form-item>
        <!--        <t-form-item style="width: 96%;margin: 8px 0" label="管理的班级" name="classs">-->
        <!--          <t-input v-model="operationValue.pojo.classs" placeholder="请输入管理的班级"></t-input>-->
        <!--        </t-form-item>-->
        <t-form-item style="width: 96%;margin: 8px 0" label="管理的班级" name="classs">
          <t-space style="width: 100%">
            <t-select
                v-model="operationValue.pojo.classs"
                placeholder="请选择管理的班级"
                clearable
                @focus="onFocus"
                @blur="onBlur"
            >
              <t-option v-for="class1 in operationValue.classs" :key="class1.id" :value="class1.name"
                        :label="class1.name" v-model="operationValue.pojo.classs      "></t-option>


            </t-select>
          </t-space>
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
          <t-button size="small" @click="operationValue.visible=true;operationValue.isUpdate=false;$refs.form.reset()"
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
          :data="tableData.list"
          :columns="[
           { colKey: 'row-select', type: 'multiple' ,width: 60,},
            { colKey: 'id', title: 'ID'},
            { colKey: 'name', title: '名称',ellipsis: true,},
            // { colKey: 'paperDescription', title: 'paperDescription'},
            { colKey: 'num', title: '人数'},
             { colKey: 'teacher', title: '老师'},
            // { colKey: 'userImg', title: '用户头像'},
            { colKey: 'classs', title: '管理的班级'},
            // { colKey: 'classid', title: '班级'},
            {
    title: '操作',
    colKey: 'operation',
      }
      ]"
      >


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