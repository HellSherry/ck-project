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
    title: '',
    details: ''
  }
});


function initContex() {
  service.get(`/online/getUser?page=${operationValue.page}`).then(
      (res) => {
        tableData.value = res.data;
        console.log(res);
      }
  )
}
initContex()
async function beforeSubmit() {
  if (typeof await form.value.validate() === 'boolean') {
   service.post(`/message/${operationValue.isUpdate?'update':'insert'}`, operationValue.pojo).then( (res) => {
     console.log(11121)
     console.log(res)
     initContex()
     if (res.data>0){
       MessagePlugin.success((operationValue.isUpdate?"修改":"新增")+"成功")
     }else {
       MessagePlugin.error((operationValue.isUpdate?"修改":"新增")+"失败")
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
      body: '您确认退出 ' + operationValue.selectedRowKeys.length + '位用户登录吗？',
      confirmBtn: {
        content: '确认',
        theme: 'danger',
      },
      cancelBtn: {content: '取消', variant: 'outline'},
      onConfirm: ({e}) => {
        deletes(operationValue.selectedRowKeys)
        confirmDia.hide();
      }
    });
  } else {
    MessagePlugin.error("请选择要退出的用户")
  }

}

function deletes(id) {

  // if (id===store.loginUser.sub.userId||id.indexOf(store.loginUser.sub.userId)!=-1){
  //   MessagePlugin.error("您无法退出自己的登录账号")
  //   return;
  // }

  service.post(`/online/delete`, typeof id === 'object' ?id :[id]).then( (res) => {
    console.log(res)
    initContex()
    if (res.data>0){
      MessagePlugin.success("退出成功")
    }else {
      MessagePlugin.error("退出失败")
    }

  })
}

</script>

<template>
  <div style="position: relative;height: 102vh">
    <t-dialog
        placement="center"
        :header="operationValue.isUpdate?'编辑通知':'新增通知'"
        :visible="operationValue.visible"
        :on-confirm="()=>beforeSubmit()"
        :on-close="()=>operationValue.visible=false"
    >
      <t-form layout="inline" ref="form" :rules="{
  title: [{required: true, message: '标题不能为空'}],
  details: [{required: true, message: '内容不能为空'}]
}" :data="operationValue.pojo" :colon="true">
        <t-form-item style="width: 96%;margin: 8px 0" label="通知标题" name="title">
          <t-input v-model="operationValue.pojo.title" placeholder="请输入内容"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="通知内容" name="details">
          <t-input v-model="operationValue.pojo.details" placeholder="请输入内容"></t-input>
        </t-form-item>


      </t-form>
    </t-dialog>


    <div style="background-position: center"
         :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;min-height: 46vh ;margin-top: -20px`">

    </div>
    <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto;margin-top: -30px;min-height: 58vh">
      <!--      {{ searchForm }}-->
      <div>
        <t-space :size="6">

          <t-button size="small" @click="toDeleteInBulk" theme="danger">
            <template #icon>
              <t-icon name="logout"/>
            </template>
            退出
          </t-button>
          <t-tag variant="light-outline" theme="primary" shape="round" size="small">
            共有{{ tableData.total }}条记录
          </t-tag>
        </t-space>


        <div style="display: inline-block;float: right;width:200px;margin-left: 10px">

          <t-pagination :totalContent="false" @change="initContex" v-model="operationValue.page" theme="simple" :showPageSize="false"
                        size="small"
                        :total="tableData.total"/>
        </div>


<!--        <t-input @input.lazy="operationValue.page=1;initContex();" v-model.lazy="operationValue.search" size="small" style="width: 20%;float: right"-->
<!--                 placeholder="输入内容"-->
<!--                 clearable>-->
<!--          <template #suffixIcon>-->
<!--            <t-icon  :style="{ cursor: 'pointer' }" name="search"/>-->
<!--          </template>-->
<!--        </t-input>-->

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

            { colKey: 'defaultAddress', title: '地址'},
            { colKey: 'identity', title: '身份'},
            // { colKey: 'classid', title: 'classid'},

            {
    title: '操作',
    colKey: 'operation',
      }
      ]"
      >

        <template #grade="{ row }">
          {{  row.grade ==0?'男':'女' }}
        </template>
        <template #identity="{ row }">
          {{  row.identity ==1?'学生':(row.identity ==2? '教师':'管理员'  )}}
        </template>
        <template #operation="{ row }">
<!--          <t-link @click="operationValue.visible=true;operationValue.isUpdate=true;operationValue.pojo={...row}" theme="primary" hover="color">-->
<!--            修改-->
<!--          </t-link>&nbsp;-->
          <t-popconfirm  v-if="row.userId!=store.loginUser.sub.userId" theme="danger" :confirmBtn="{theme: 'danger'}" @confirm="deletes(row.userId)"
                        :cancelBtn="{variant: 'outline'}" content="确认删除吗">
            <t-link theme="danger" hover="color">
              退出
            </t-link>


          </t-popconfirm>       <t-tag v-else>我</t-tag>


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