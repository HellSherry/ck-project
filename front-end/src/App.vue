<script setup>
import {RouterLink, RouterView} from 'vue-router'
import {ref, watch,nextTick } from 'vue'
import {useCounterStore} from '@/stores/counter'
import {storeToRefs} from 'pinia'
import Hander from './components/Hander.vue'
import SwitchGalahhad from "@/components/ui/SwitchGalahhad.vue";
import {MessagePlugin} from "tdesign-vue-next";
import router from "@/router";
import ManageHander from "@/manage/compoments/ManageHander.vue";

const store = useCounterStore();
const {darkT, loginUser,animate} = storeToRefs(store);


var token = localStorage.getItem("token");
if (token != null) {
  loginUser.value = JSON.parse(
      new TextDecoder().decode(Uint8Array.from(atob(token.split(".")[1]), (c) => c.charCodeAt(0)))
  )
  loginUser.value.sub = JSON.parse(loginUser.value.sub)

  store.newWS(token)
  //
  //  let socket = new WebSocket(`ws://localhost:8080/ws/controller/${loginUser.value.sub.userId}`,[token]);
  // socket.onopen = function()
  //  {
  //    // Web Socket 已连接上，使用 send() 方法发送数据
  //    MessagePlugin.success('自动登录成功')
  //  };
  //  socket.onclose = function()
  //  {
  //    socket=null;
  //    const confirmDia =  DialogPlugin({
  //      header: 'Dialog-Confirm-Plugin',
  //      body: '登录已过期',
  //      placement:"center",
  //      confirmBtn: 'ok',
  //      cancelBtn: 'cancel',
  //      onConfirm: ({ e }) => {
  //        console.log('confirm button has been clicked!');
  //        console.log('e: ', e);
  //        confirmDia.hide();
  //      },
  //      onClose: ({ e, trigger }) => {
  //        console.log('e: ', e);
  //        console.log('trigger: ', trigger);
  //        confirmDia.hide();
  //      },
  //    });
  //    // 关闭 websocket
  //    // alert("连接已关闭...");
  //  };
  //  console.log(socket.readyState);
}


// socket.addEventListener('beforeSend', function (event) {
//   event.target.setRequestHeader('token', '111 ');
// });


 watch(
    () => router.currentRoute.value,
    ( ) => {
      animate.value[0]=true;
        nextTick(() => {
         animate.value[0]=false;
      });
    }
)
function hhhhhh() {
  var myHeaders = new Headers();
  // myHeaders.append("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1aWRcIjoxLFwicGhvbmVOdW1iZXJcIjpcIjEzMTMyODQ1MTJcIixcInBhc3N3b3JkXCI6bnVsbCxcInVuYW1lXCI6XCJyb290XCIsXCJnZW5kZXJcIjpcIuWls1wiLFwiZW1haWxcIjpudWxsLFwiaW5zZXJ0VGltZVwiOm51bGx9IiwiaWF0IjoxNzAyODkzODQwfQ.JRJxRcVkfEvQeIatePfXGo6f6nwqu9GCR-24RFJkO70");
  myHeaders.append("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
  myHeaders.append("Accept", "*/*");
  myHeaders.append("Host", "localhost:8080");
  myHeaders.append("Connection", "keep-alive");
  myHeaders.append("Cookie", "JSESSIONID=D9D2D51B5F32588CF00E3912F17A7DD0");
  const themeVars = new ref({
    primaryColor: '#008000',
    primaryColorEnd: '#008000'
  });
  var requestOptions = {
    method: 'GET',
    headers: myHeaders,
    redirect: 'follow'
  };
  fetch("http://localhost:8080/test", requestOptions)
      .then(response => response.text())
      .then(result => console.log("www" + result))
      .catch(error => console.log('error', error));
}
</script>

<template>

  <audio id="audioController" src="/assets1/audio/2544.wav"></audio>
  <nut-config-provider v-if="router.currentRoute.value.path.substring(1,7)==='mobile'" style="min-height: 80vh"
                       :theme="darkT?'dark':''" :theme-vars="{
    primaryColor: '#2c68ff',
    primaryColorEnd: '#2c68ff',
gridItemContentBgColor:'rgba(255,255,255,0.56)',
  darkBackground:'#00000099'
  }">
    <t-layout>


      <t-space style="min-height: 100vh">
        <RouterView/>
      </t-space>
    </t-layout>


  </nut-config-provider>
  <nut-animate  v-else :type="animate[1]?'shake':'float'" :show="animate[0]">
    <div style="width: 100vw;">
  <t-layout>

    <t-aside style="height: 100vh" width="auto" v-if="router.currentRoute.value.path.substring(1,7)==='manage'">
      <ManageHander v-if="store.loginUser!=null" />

    </t-aside>
    <!--      <p style="text-align: center;width: 100%">欢迎来到创科☆*: .｡. o(≧▽≦)o .｡.:*☆</p>-->
    <t-header v-else>

      <Hander/>
      <p style="text-align: center;width: 100%">欢迎来到创科☆*: .｡. o(≧▽≦)o .｡.:*☆</p>
    </t-header>
    <t-layout>
      <t-content>

        <nut-config-provider style="min-height: 80vh" :theme="darkT?'dark':''" :theme-vars="{
    primaryColor: '#2c68ff',
    primaryColorEnd: '#2c68ff'
  }">

              <router-view></router-view>



<!--          <transition name="fade">-->

<!--          </transition>-->



        </nut-config-provider>
      </t-content>
      <!--{{router.currentRoute.value.path.substring(1,7)}}-->


      <t-footer
          v-if="router.currentRoute.value.path.substring(1,7)!=='manage'&&router.currentRoute.value.name!=='exam-answer'&&router.currentRoute.value.name!=='exam-analysis'">
      <span style="text-align: center">
              <p>长沙创科职校   0731-8455-6741 | 长沙市芙蓉区</p>

      <p> 湘ICP备2023020435号| Copyright © icloud All rights reserved</p>
      </span>


      </t-footer>
    </t-layout>

  </t-layout>
    </div>
  </nut-animate>
  <!--  {{ loginUser }}-->
</template>

<style scoped>


.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
