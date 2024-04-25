<script setup lang="jsx">
import {ref} from "vue";
import {useCounterStore} from "@/stores/counter";
import TestEcharts from "@/manage/compoments/TestEcharts.vue";
import CropperImage from "@/components/CropperImage.vue";
import {storeToRefs} from "pinia";
import service from "@/axios/service";
import {MessagePlugin} from "tdesign-vue-next";


const store = useCounterStore();
const {loginUser} = storeToRefs(store);
const dialogVis = ref(
    {hand: false, password: false, name: false, user: false}
);
const temUser = ref({...loginUser.value});
const temPassword = ref({old: '', newP: ''});
const temName = ref('');

const form = ref();

function openHander() {
  document.getElementById('uploads').click();
}

function reloadToken() {
  loginUser.value = JSON.parse(
      new TextDecoder().decode(Uint8Array.from(atob(localStorage.getItem('token').split(".")[1]), (c) => c.charCodeAt(0)))
      // atob(parse.data.token.split(".")[1])
  )
  loginUser.value.sub = JSON.parse(loginUser.value.sub)
  loginUser.value.sub.userImg = loginUser.value.sub.userImg + "?isupdate=" + Math.round(Math.random() * 300) + 'and' + Math.round(Math.random() * 300)
}

function reloadUpload() {
  dialogVis.value.hand = false;
  reloadToken();
  // store.newWS(localStorage.getItem('token'))
}

const classValue = ref([]);
service.get("/classInfo/getBySpeciaid")
    .then(response => {
      // 处理成功响应
      console.log(response.data);
      classValue.value = response.data
      // specias.value = response.data
    })
    .catch(error => {
      // 处理错误
      console.error('There was an error!', error);
    })

function findClass(id) {
  let find = classValue.value.find(e => e.id === id);
  if (find != null) {
    return find.name
  }
}

function upload(data) {
  console.log(data)
}

async function toUpdatePassword() {
  if (temPassword.value.newP == temPassword.value.old) {
    store.showNotifi("两次密码不能一致")
    MessagePlugin.error("两次密码不能一致")
    return
  }
  if (typeof await form.value.validate() === 'boolean') {
    dialogVis.value.password = false
    service.post("/user/updatePassword?old=" + temPassword.value.old + "&newP=" + temPassword.value.newP).then(
        response => {

          if (response.data == 'err') {
            store.showNotifi("原密码错误")
            MessagePlugin.error("原密码错误")
          } else {
            if (response.data > 0) {
              store.showNotifi("密码修改成功")
              MessagePlugin.success("修改成功")
            } else
              store.showNotifi("密码修改失败")
            MessagePlugin.error("修改失败")
          }

        })
  }
}

function toUpdateName() {
  if (temName.value.trim() == '') {
    store.showNotifi("名称不能为空")
    MessagePlugin.error("名称不能为空")
    return
  }

  dialogVis.value.name = false
  service.post("/user/updateName?name=" + temName.value).then(
      response => {
        localStorage.setItem('token', response.data)
        reloadToken()
        store.showNotifi("名称修改成功")
        MessagePlugin.success("名称修改成功")
      })
}

async function toUpdateUser() {
  temUser.value.userImg = '';

  if (typeof await form.value.validate() === 'boolean') {
    dialogVis.value.user = false
    service.post(`/user/update`, temUser.value).then((res) => {
      if (res.data > 0) {
        service.post("/user/getNewToken").then(
            response => {
              localStorage.setItem('token', response.data)
              // reloadToken()
              store.showNotifi("用户信息修改成功")
              MessagePlugin.success("用户信息名称修改成功")
            })
      } else {
        MessagePlugin.error("用户信息失败")
      }

    })
  } else {

  }


}

</script>

<template>
  <div style="background-position: center"
       :style="`background-image: url('${store.darkT? `//assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;  `">

    <t-dialog header="更改照片" @confirm="$refs.cropperImage.uploadHander()" placement="center"
              v-model:visible="dialogVis.hand">
      <CropperImage ref="cropperImage" @upload="upload(data)" @open="dialogVis.hand=true" @close="reloadUpload"/>
    </t-dialog>
    <t-dialog destroyOnClose header="更改密码" @confirm="toUpdatePassword" placement="center"
              v-model:visible="dialogVis.password">
      <t-form layout="inline" ref="form" :rules="{
  old: [{required: true, message: '当前密码不能为空'}],
  newP: [{required: true, message: '新密码不能为空'}]
}" :data="temPassword" :colon="true">
        <t-form-item style="width: 96%;margin: 8px 0" label="当前密码" name="old">
          <t-input type="password" v-model="temPassword.old" placeholder="请输入当前密码">
            <template #prefix-icon>
              <t-icon name="lock-on"></t-icon>
            </template>
          </t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="新密码" name="newP">
          <t-input type="password" v-model="temPassword.newP" placeholder="请输入新密码">
            <template #prefix-icon>
              <t-icon name="lock-on"></t-icon>
            </template>
          </t-input>
        </t-form-item>


      </t-form>
    </t-dialog>
    <t-dialog destroyOnClose header="更改名称" @confirm="toUpdateName" placement="center"
              v-model:visible="dialogVis.name">
      <t-form layout="inline" :colon="true">
        <t-form-item style="width: 96%;margin: 8px 0" label="名称" name="temName">
          <t-input v-model="temName" placeholder="请输入名称">
          </t-input>
        </t-form-item>


      </t-form>
    </t-dialog>
    <t-dialog destroyOnClose header="更改信息" @confirm="toUpdateUser" placement="center"
              v-model:visible="dialogVis.user">
      <t-form layout="inline" ref="form" :rules="{
  userEmail: [{required: true, message: '邮箱不能为空'}],
  phone: [{required: true, message: '邮箱不能为空'}],
  username: [{required: true, message: '用户名不能为空'}],
  password: [{required: true, message: '密码不能为空'}],
  grade: [{required: true, message: '性别不能为空'}],
  defaultAddress: [{required: true, message: '地址不能为空'}],
  classid: [{required: true, message: '班级不能为空'}]
}" :data="temUser" :colon="true">
        <t-form-item style="width: 96%;margin: 8px 0" label="邮箱" name="userEmail">
          <t-input v-model="temUser.userEmail" placeholder="请输入邮箱"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="用户名" name="username">
          <t-input v-model="temUser.username" placeholder="请输入用户名"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="电话" name="phone">
          <t-input v-model="temUser.phone" placeholder="请输入电话"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="性别" name="grade">
          <t-radio-group v-model="temUser.grade">
            <t-radio :value="0">男</t-radio>
            <t-radio :value="1">女</t-radio>
          </t-radio-group>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="地址" name="defaultAddress">
          <t-input v-model="temUser.defaultAddress" placeholder="请输入地址"></t-input>
        </t-form-item>
        <t-form-item style="width: 96%;margin: 8px 0" label="班级" name="classid">
          <t-select v-model="temUser.classid">
            <t-option v-for="c in classValue" :label="c.name" :value="c.id" :key="c.id"/>
          </t-select>
        </t-form-item>


      </t-form>
    </t-dialog>

    <div :class="store.darkT?'boxdark':'box'"
         style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top:8px;padding-bottom: 10px">
      <div style="width: 92%;max-width: 2000px;margin: 0 auto ">
        <t-space :size="10" style="width: 100%" direction="vertical">
          <t-card :class="store.darkT?'boxdark':'box'" size="small" hoverShadow>
            <h2 style="line-height: 8px">您的信息
              <t-link @click="dialogVis.password=true;temPassword={newP:'',old:''}"
                      style="float: right;margin-top: -8px" theme="primary">
                <t-icon style="font-size: 16;margin-right: 2px" name="user-password"></t-icon>
                更改密码
              </t-link>
            </h2>
          </t-card>
          <t-card :class="store.darkT?'boxdark':'box'" hoverShadow>


            <div style=" background-color: rgba(145,145,145,0.13);display: inline-block;border-radius: 50%">
              <t-avatar
                  v-bind:image=" loginUser.sub.userImg=='no'?'': (loginUser.sub.userImg)"
                  style="margin: 4px;border: 2px solid rgba(150,150,150,0.2)" size="160px">
                {{ store.loginUser.sub.username.substring(0, 1) }}
              </t-avatar>

            </div>
            <main style="vertical-align: middle;display: inline-block;margin-left: 2vw">

              添加照片，个性化你的账户。<br>你的个人资料照片会显示在使用你的帐户的应用和设备上。<br>
              <t-button @click="openHander()" ghost class="ubutton" theme="default" variant="dashed">更改照片</t-button>
            </main>

            <t-divider style="border-color: var(--td-text-color-primary)" dashed/>
            <table style="margin-top: -12px;margin-bottom: -8px;font-size: 16px;width: 100%">
              <tr>
                <td style="width: 20%">全名</td>
                <td style="font-weight: bold">{{ store.loginUser.sub.username }}</td>
                <td style="text-align: right">
                  <t-link @click="dialogVis.name=true;temName=''+loginUser.sub.username" theme="primary">
                    编辑名称
                  </t-link>
                </td>
              </tr>
            </table>


          </t-card>
          <t-card :class="store.darkT?'boxdark':'box'" title="个人资料信息" hoverShadow>
            <template #actions>
              <t-link @click="dialogVis.user=true;temUser={...loginUser.sub}" theme="primary">
                编辑个人资料信息
              </t-link>
            </template>
            <t-divider style="margin-top: -10px;margin-bottom: 8px;border-color: var(--td-text-color-primary)" dashed/>
            <table style="font-size: 16px;width: 100%">
              <tr>
                <td style="width: 20%">账号</td>
                <td style="font-weight: bold">{{ store.loginUser.sub.userId }}</td>
                <td style="width: 40%">您的专属账号</td>
              </tr>
            </table>

            <t-divider style="margin-top: 10px;margin-bottom: 8px;border-color: var(--td-text-color-primary)" dashed/>
            <table style="font-size: 16px;width: 100%">
              <tr>
                <td style="width: 20%">地址</td>
                <td style="font-weight: bold">
                  {{ store.loginUser.sub.defaultAddress == null ? '未填写' : store.loginUser.sub.defaultAddress }}
                </td>
                <td style="width: 40%">用于通讯的地址</td>

              </tr>
            </table>
            <t-divider style="margin-top: 10px;margin-bottom: 8px;border-color: var(--td-text-color-primary)" dashed/>
            <table style="font-size: 16px;width: 100%">
              <tr>
                <td style="width: 20%">班级</td>
                <td style="font-weight: bold">{{ findClass(store.loginUser.sub.classid) }}</td>
                <td style="width: 40%">用以确认您的身份</td>

              </tr>
            </table>
            <t-divider style="margin-top: 10px;margin-bottom: 8px;border-color: var(--td-text-color-primary)" dashed/>
            <table style="font-size: 16px;width: 100%">
              <tr>
                <td style="width: 20%">性别</td>
                <td style="font-weight: bold">{{ store.loginUser.sub.grade === 0 ? '男' : '女' }}</td>
                <td style="width: 40%">您的性别确认</td>

              </tr>
            </table>
            <t-divider style="margin-top: 10px;margin-bottom: 8px;border-color: var(--td-text-color-primary)" dashed/>

          </t-card>
          <t-card :class="store.darkT?'boxdark':'box'" title="账号信息" hoverShadow>
            <template #actions>
              <t-link @click="dialogVis.user=true;temUser={...loginUser.sub}" theme="primary">
                编辑账号信息
              </t-link>
            </template>
            <t-divider style="margin-top: -10px;margin-bottom: 8px;border-color: var(--td-text-color-primary)" dashed/>


            <table style="font-size: 16px;width: 100%">
              <tr>
                <td style="width: 20%">电子邮件地址</td>
                <td style="font-weight: bold">
                  {{ store.loginUser.sub.userEmail == null ? '未填写' : store.loginUser.sub.userEmail }}
                </td>
                <td style="width: 40%">用于登录的电子邮件地址</td>


              </tr>
            </table>
            <t-divider style="margin-top: 10px;margin-bottom: 8px;border-color: var(--td-text-color-primary)" dashed/>
            <table style="font-size: 16px;width: 100%">
              <tr>
                <td style="width: 20%">电话号码</td>
                <td style="font-weight: bold">
                  {{ store.loginUser.sub.phone == null ? '未填写' : store.loginUser.sub.phone }}
                </td>
                <td style="width: 40%">用于登录的电话号码</td>

              </tr>
            </table>
            <t-divider style="margin-top: 10px;margin-bottom: 8px;border-color: var(--td-text-color-primary)" dashed/>

          </t-card>

        </t-space>

      </div>
    </div>
  </div>

</template>

<style scoped>
.box {
  background-color: rgba(255, 255, 255, 0.58);

}

.ubutton {
  margin-top: 10px;
  border-color: var(--td-text-color-primary);
  color: var(--td-text-color-primary)
}

.boxdark {
  background-color: rgba(0, 0, 0, .48);
}
</style>