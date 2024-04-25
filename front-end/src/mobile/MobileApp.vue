<template>

  <!--  <div style="background: url('https://hbimg.huaban.com/925d1a70580f1c5dcee557116a75563452667e8b279cfc-dPZDtO');height: 100vh;background-size: cover;background-repeat: no-repeat">-->
  <view  :style="`opacity:`+(router.currentRoute.value.path==='/mobile/ai'?'0':'1')+';'+(router.currentRoute.value.path=='/mobile/my'?'position: absolute;right: 44%;top: 16%;transform: scale(360%);':'position: fixed;top: 8px; '+`${router.currentRoute.value.path=='/mobile'?`${showMenu?'right:60%;':'right:82%;'}`:'right:12px;'}`)+'background-color: '+(store.darkT?'rgba(0,0,0,0.6)':'rgba(255,255,255,0.6)')" style=" padding: 2px; vertical-align: middle;backdrop-filter: blur(4px);
 height: 46px;width: auto;z-index: 999;border: 1px solid rgba(245,245,245,0.1);border-radius: 50px;
transition: all 480ms;

">
    <nut-animate type="ripple" action="click">
      <view @click="showMenu=!showMenu"
            style='background-image: url("/assets1/image/aa.png");background-size: cover;vertical-align: middle;display: inline-block;width: 46px ;height: 46px;border-radius: 50%; '>
<!--        <t-avatar style="margin-top: 3px;margin-left: 3px;transform: scale(116%)" size="large">sdf</t-avatar>-->

<!--                  <t-avatar style="margin: 3px;transform: scale(116%)" size="large">sdf</t-avatar>-->


      </view>


    </nut-animate>
    <view
v-if="router.currentRoute.value.path!=='/mobile/ai'&&router.currentRoute.value.path!=='/mobile/my'"
        :style="'vertical-align: middle;transition: width 260ms;display: inline-block;height: 46px;'+(showMenu?'width: 100px;':'width: 0px;')">
      <switch-galahhad2 v-show="showMenu" style="position: absolute;right: 8px;top: -6px;"/>
    </view>
  </view>

<router-view/>

  <nut-tabbar style="position: fixed"   bottom safe-area-inset-bottom placeholder>
    <nut-tabbar-item to="/mobile"  style="background-color: rgba(255,0,0,0)" tab-title="首页" >
      <template #icon>
        <Home></Home>
      </template>
    </nut-tabbar-item>
    <nut-tabbar-item to="/mobile/message"  tab-title="消息" >
      <template #icon>
        <Comment></Comment>
      </template>
    </nut-tabbar-item>
    <nut-tabbar-item  to="/mobile/ai" >
      <template  #icon="props">
        <nut-animate type="breath" duration="3500" :loop="!props.active" >
          <nut-animate type="jump" :show="props.active">
          <nut-animate type="slide-top" :show="!props.active">
            <AIButton :isActive="props.active"></AIButton>
          </nut-animate>
        </nut-animate>
        </nut-animate>

      </template>
    </nut-tabbar-item>
    <nut-tabbar-item  to="/mobile/questions" tab-title="试题" >
      <template #icon>
        <Order></Order>
      </template>
    </nut-tabbar-item>
    <nut-tabbar-item to="/mobile/my" tab-title="我的" >
      <template #icon>
        <My></My>
      </template>
    </nut-tabbar-item>

  </nut-tabbar>
<!--  </div>-->
</template>
<script setup>
import { Home, Comment, Order, My } from '@nutui/icons-vue';
import AIButton from "@/mobile/compoments/AIButton.vue";
import SwitchGalahhad2 from "@/components/ui/SwitchGalahhad2.vue";
import {useCounterStore} from "@/stores/counter";
import {ref, watch} from "vue";
import router from "@/router";
import {showDialog} from "@nutui/nutui";




if (window.innerWidth>700){
  // alert("ss")
  const onCancel = () => {
    console.log('event cancel');
  };
  const onOk = () => {
    router.push('/')
    console.log('event ok');
  };
  showDialog({

    'footer-direction':"vertical",
    teleport:"#app",
    title:"客户端跳转",
    content:"如果你在PC上使用，请点击确定前往PC版页面",
    onCancel,
    onOk
  });
}

const store = useCounterStore();
const showMenu = ref(false);
watch(
    () => router.currentRoute.value,
    (newValue) => {
      showMenu.value = false;
      // menu1Value.value = newValue.name.split('-')[0]
      console.log('newValue', newValue)
    }
)

</script>
