<template>
  <t-space direction="vertical" size="large">

    <t-form :data="formData" label-align="right" :label-width="60">
      <t-loading  :loading="loading" text="正在登录中..." fullscreen/>

      <t-form-item label="账号" name="username">
        <t-input v-model="formData.username"></t-input>
      </t-form-item>
      <t-form-item label="密码" name="password">
        <t-input v-model="formData.password" type="password"></t-input>
      </t-form-item>
      <t-form-item>
<div style="margin: 0 auto;">
  <t-space style="margin-right:   40px">
    <t-button theme="primary"  @click="login" type="submit">登录</t-button>
    <t-button theme="primary" type="reset">重置</t-button>
  </t-space>

</div>


      </t-form-item>
    </t-form>
  </t-space>

</template>
<script setup>
import {reactive, ref} from 'vue';
import {MessagePlugin} from "tdesign-vue-next";

import {useCounterStore} from'@/stores/counter'
import { storeToRefs } from 'pinia'
const store = useCounterStore();
const {loginUser} = storeToRefs(store);
const emits = defineEmits(['close']);
const loading = ref(false);

 function login() {
   if (!store.getWsStatus()){
     MessagePlugin.error('已经登录了哦！');
     return;
   }
  var myHeaders = new Headers();
  myHeaders.append("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
  myHeaders.append("Content-Type", "application/json");
  myHeaders.append("Accept", "*/*");
  myHeaders.append("Connection", "keep-alive");


  var requestOptions = {
    method: 'POST',
    headers: myHeaders,
    body: JSON.stringify(formData) ,
    redirect: 'follow'
  };
   loading.value = true;

  fetch(`http://${store.BACKEND}/user/login`, requestOptions)
      .then(response => response.text())
      .then(result => {
        console.log(result)
        let parse = JSON.parse(result);
        if (parse.code==200){
          // alert('登录成功')
          // MessagePlugin.success('用户登录成功')
          localStorage.setItem("token",parse.data.token)

          loginUser.value= JSON.parse(
              new TextDecoder().decode(Uint8Array.from(atob(parse.data.token.split(".")[1]), (c) => c.charCodeAt(0)))
          // atob(parse.data.token.split(".")[1])
        )
          loginUser.value.sub=JSON.parse(loginUser.value.sub)
          store.newWS(parse.data.token)
        }else {
          MessagePlugin.error("登录错误")
        }
        emits('close')
        console.log(result)
      })
      .catch(error =>           MessagePlugin.error("登录错误")
      ).finally(
      () => {
        loading.value = false;

      }
  );
}
const formData = reactive({
  username: '',
  password: '',
});
</script>
