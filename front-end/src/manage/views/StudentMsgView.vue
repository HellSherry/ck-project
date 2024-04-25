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
    "userEmail": "",
    "username": "",
    "password": '$2a$10$YENaMAtoqC7WcVh0SAjcPOSM8kzCeklbYAYS3gDGHw/kzPDL9jRM6',
    "grade": null,
    "userImg": "no",
    "defaultAddress": null,
    "identity": 1,
    "classid": null
  }
});


function initContex() {
  service.get(`/user/selectLimit?page=${operationValue.page}&identity=1&search=${operationValue.search}`).then(
      (res) => {
        tableData.value = res.data;
        console.log(res);
      }
  )
}

const classes = ref([])
service.get("classInfo/getBySpeciaid").then((res) => {
  classes.value = res.data
})


initContex()

async function beforeSubmit() {
  if (typeof await form.value.validate() === 'boolean') {
    service.post(`/user/${operationValue.isUpdate ? 'update' : 'insert'}`, operationValue.pojo).then((res) => {
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
  service.post(`/user/deletes?ids=` + id.toString()).then((res) => {
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
  let find = classes.value.find(e=>e.id==classid)
  if(find!=null){
    return find.name
  }
}
</script>

<template>
  <div style="position: relative;height: 102vh">
    <t-dialog
        placement="center"
        :header="operationValue.isUpdate?'编辑学生信息':'新增学生'"
        :visible="operationValue.visible"
        :on-confirm="()=>beforeSubmit()"
        :on-close="()=>operationValue.visible=false"
    >
      <t-form layout="inline" ref="form" :rules="{
  userEmail: [{required: true, message: '邮箱不能为空'}],
  username: [{required: true, message: '用户名不能为空'}],
  password: [{required: true, message: '密码不能为空'}],
  grade: [{required: true, message: '性别不能为空'}],
  defaultAddress: [{required: true, message: '地址不能为空'}],
  classid: [{required: true, message: '班级不能为空'}]
}" :data="operationValue.pojo" :colon="true">
        <t-form-item style="width: 96%;margin: 8px 0" label="邮箱" name="userEmail">
          <t-input v-model="operationValue.pojo.userEmail" placeholder="请输入邮箱"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="用户名" name="username">
          <t-input v-model="operationValue.pojo.username" placeholder="请输入用户名"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="密码" name="password">
          <t-input  v-if="!operationValue.isUpdate"  v-model="operationValue.pojo.password" placeholder="请输入密码"></t-input>        <t-alert STYLE="width: 100%;height: 40px;padding-top: 10px"  v-else theme="warning" message="你没有权限修改密码" />

        </t-form-item>

        <t-form-item style="width: 96%;margin: 8px 0" label="性别" name="grade">
          <t-radio-group v-model="operationValue.pojo.grade">
            <t-radio :value="0">男</t-radio>
            <t-radio :value="1">女</t-radio>
          </t-radio-group>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="地址" name="defaultAddress">
          <t-input v-model="operationValue.pojo.defaultAddress" placeholder="请输入地址"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="班级" name="classid">
          <t-select v-model="operationValue.pojo.classid">
            <t-option v-for="c in classes" :label="c.name" :value="c.id" :key="c.id"/>
          </t-select>
        </t-form-item>


      </t-form>
<!--      {{ operationValue.pojo }}-->
    </t-dialog>


    <div style="background-position: center"
         :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;min-height: 46vh ;margin-top: -20px`">

    </div>
    <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto;margin-top: -30px;min-height: 58vh">
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
          row-key="userId"
          v-model:selected-row-keys="operationValue.selectedRowKeys"
          :data="tableData.list"
          :columns="[
           { colKey: 'row-select', type: 'multiple' ,width: 60,},
            { colKey: 'userId', title: 'ID'},
            { colKey: 'userEmail', title: '邮箱',ellipsis: true,},
            // { colKey: 'paperDescription', title: 'paperDescription'},
            { colKey: 'username', title: '用户名'},
             { colKey: 'grade', title: '性别'},
            // { colKey: 'userImg', title: '用户头像'},
            { colKey: 'defaultAddress', title: '地址'},
            { colKey: 'classid', title: '班级'},
            {
    title: '操作',
    colKey: 'operation',
      }
      ]"
      >
        <template #grade="{ row }">

          {{ row.grade==0?'男':'女'}}
        </template>
        <template #classid="{ row }">

          {{ findClass(row.classid)}}
        </template>

        <template #operation="{ row }">
          <t-link @click="operationValue.visible=true;operationValue.isUpdate=true;operationValue.pojo={...row}"
                  theme="primary" hover="color">
            修改
          </t-link>&nbsp;
          <t-popconfirm theme="danger" :confirmBtn="{theme: 'danger'}" @confirm="deletes(row.userId)"
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