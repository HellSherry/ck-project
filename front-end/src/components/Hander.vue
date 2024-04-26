<script setup>
import {ref, watch} from "vue";
import {RouterLink, RouterView, useRoute, useRouter} from 'vue-router'
import Login from "@/components/user/login.vue";
import {useCounterStore} from "@/stores/counter";
import SwitchGalahhad2 from "@/components/ui/SwitchGalahhad2.vue";
import AButton from "@/components/ui/AButton.vue";
import {storeToRefs} from "pinia";
import {DialogPlugin, MessagePlugin} from "tdesign-vue-next";
import service from "@/axios/service";



const store = useCounterStore();
const {search} = storeToRefs(store );
var scrollTopLast = 0;
window.onscroll = function (e) {
  var scrollTop = e.target.scrollingElement.scrollTop;
  if (scrollTop > scrollTopLast) {
    // console.log("滑轮向下滚动");
    height.value = 0
  } else {
    height.value = 56
    // console.log("滑轮向上滚动");
  }
  // console.log(e.target.scrollingElement.scrollTop);
  scrollTopLast = scrollTop;
}
const router = useRouter();
const visible = ref(false);
const menu1Value = ref(router.currentRoute.value.name);
const size = ref('small');
const placement = ref('right');
const height = new ref('56');

watch(
    () => router.currentRoute.value,
    (newValue) => {
      menu1Value.value = newValue.name.split('-')[0]
      console.log('newValue', newValue)
    }
)
// watch(
//     menu1Value,
//     (newValue) => {
//     router.push({name:newValue})
//       console.log('newValue',newValue)
//     }
// )
function toLogOut() {
  const confirmDia = DialogPlugin({
    header: '提示',
    body: '您确认退出当前账号的登录吗？',
    confirmBtn: {
      content: '退出登录',
      theme: 'danger',
    },
    cancelBtn: {content: '取消', variant: 'outline'},
    onConfirm: ({e}) => {
      service.get( "/user/logout").then(res=>{
        MessagePlugin.success('退出成功');

      }).finally(
          () => {
            localStorage.removeItem("token")
            window.location.href="/"
          })
       confirmDia.hide();
    }
  });
}
function removeMessage() {
  localStorage.removeItem('messages')
  window.location.reload();
}
</script>
<template>
<!--  <t-drawer v-if="store.loginUser==null"  v-model:visible="visible" :placement="placement" :size="size" header="登录">-->
<!--    <Login></Login>-->
<!--  </t-drawer>-->
  <t-dialog header="登录" :footer="null" v-model:visible="visible" >
    <Login @close="visible=false"  style=" width: 99%"></Login>
  </t-dialog>
  <div :class="store.darkT?'boxdark':'box'" :style="
  '  border: 1px solid rgba(255, 255, 255, 0.222);\n'+
'  -webkit-backdrop-filter: blur(20px);\n'+
'  backdrop-filter: blur(20px);width: 100%;transition: height 280ms;height: '+height+'px;overflow: hidden; box-shadow: rgba(17, 17, 26, 0.08) 0px 1px 0px;position: fixed;z-index: 1000;top: 0'">
    <t-head-menu style="position: absolute; bottom: 0;background-color: rgba(0,0,0,0%)"
                 v-model="menu1Value" :theme="store.darkT?'dark': 'light'">
      <template #logo>
        <img @click="$router.push('/')" style="cursor: pointer;   -webkit-user-select: none;-webkit-user-drag: none;"
             height="30" src="../../assets1/图标头.png" alt="logo"/>
        <div @click="$router.push('/')" style="cursor: pointer; ;margin-left: 8px;
         -webkit-user-select: none;
         padding-top: 8px;
      margin-right: -38px;
width: 100px;
                /*border: 1px solid red;*/
                text-indent:  -100px;
                overflow: hidden;">
          <img height="30" width="90"
               style="  -webkit-user-drag: none;;filter: drop-shadow(140px 0px var(--td-text-color-primary));margin-left: -40px"
               src="../../assets1/汉字pinyu.png" alt="logo"/>
        </div>

      </template>
      <t-menu-item @click="$router.push('/')" value="home"> 首页</t-menu-item>
      <t-menu-item @click="$router.push('/practice')" value="practice"> 练习</t-menu-item>
      <t-menu-item @click="$router.push('/exam')" value="exam"> 考试</t-menu-item>
      <!--      <t-menu-item @click="$router.push('/history')" value="history"> 历史 </t-menu-item>-->
      <t-menu-item @click="$router.push('/ask')" value="ask"> 讨论</t-menu-item>
      <t-menu-item @click="$router.push('/chat')" value="chat"> CHAT</t-menu-item>
      <t-menu-item @click="$router.push('/resource')" value="resource"> 软件资料</t-menu-item>
      <t-menu-item @click="$router.push('/course')" value="course"> 课表</t-menu-item>
      <t-menu-item @click="$router.push('/translation')" value="translation"> 翻译</t-menu-item>
      <t-menu-item v-if="store.loginUser!=null&&store.loginUser.sub.identity!=1" @click="$router.push('/manage')" value="manage">后台</t-menu-item>
<!--      <t-menu-item @click="$router.push('/game')" value="game"> GAME</t-menu-item>-->
      <t-menu-item @click="$router.push('/about')" value="about">
        <RouterLink to="/about">关于</RouterLink>
      </t-menu-item>
      <!--      <t-menu-item value="item4" :disabled="true"> 禁用菜单 </t-menu-item>-->
      <template #operations>
        <t-button  @click="router.push('/ask');search=true;"  v-if="menu1Value=='ask'" variant="text" shape="square">
                    <template #icon><t-icon style="font-size: 18px" name="search"/> </template>
                  </t-button>
        <t-button  @click="router.push('/resource');search=true;"  v-if="menu1Value=='resource'" variant="text" shape="square">
          <template #icon><t-icon style="font-size: 18px" name="search"/> </template>
        </t-button>
        <t-button  @click="removeMessage"  v-if="menu1Value=='ai'" variant="text" shape="square">
          <template #icon><t-icon style="font-size: 18px" name="delete"/> </template>
        </t-button>

        <switch-galahhad2/>
        &nbsp;
        <AButton style="margin-top: -1px;margin-right: 4px" @click="$router.push('/ai')"/>


        <!--        <t-button variant="text" shape="square">-->
        <!--          <template #icon><t-icon name="search" /></template>-->
        <!--        </t-button>-->


        <t-badge v-if="store.loginUser!=null" size="medium" :color="store.loginUser.sub.online?'#00A870': 'red'"
                 :offset="[4, 24]" count="2" dot>
          <t-button @click="$router.push('/my')" variant="text" shape="square">
            <t-avatar  v-bind:image=" store.loginUser.sub.userImg=='no'?'': (store.loginUser.sub.userImg)" size="small">{{ store.loginUser.sub.username.substring(0, 1) }}</t-avatar>

          </t-button>

        </t-badge>

        <t-button @click="visible=!visible" v-else variant="text" shape="square">
          <template #icon>

            <t-icon name="user"/>
          </template>
        </t-button>
        <!--        {{store.loginUser.sub.online}}-->
<!--                <div v-if="store.loginUser!=null" :style="'width: 8px;height: 8px;border-radius: 50%;background-color:'+(store.loginUser.sub.online?'green': 'red')"></div>-->
        <t-button @click="toLogOut" v-if="store.loginUser!=null" variant="text" shape="square">
          <template #icon>
            <t-icon name="logout"/>
          </template>
        </t-button>
      </template>
    </t-head-menu>
  </div>


</template>
<style scoped>
.box {
  background-color: rgba(255, 255, 255, 0.174);
}

.boxdark {
  background-color: rgba(0, 0, 0, 0.18);
}

</style>
