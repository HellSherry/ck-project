<template>
  <t-menu width="200px" :theme="darkT?'dark':'light'" default-value="item2" :collapsed="collapsed"
          @change="changeHandler">
    <template #logo>
      <img @click="$router.push('/')" style="cursor: pointer;   -webkit-user-select: none;-webkit-user-drag: none;"
           height="30" src="../../assets/图标头.png" alt="logo"/>
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
             src="../../assets/汉字pinyu.png" alt="logo"/>
      </div>
    </template>
    <t-menu-group title="教务管理">
      <t-submenu title="常规" value="rule">
        <template #icon>
          <t-icon name="server"/>
        </template>

        <t-menu-item to="/manage/special" value="special">专业管理</t-menu-item>
        <t-menu-item to="/manage/class" value="class">班级管理</t-menu-item>
        <t-menu-item to="/manage/course" value="course">课程管理</t-menu-item>
        <t-menu-item to="/manage/group" value="group">章节管理</t-menu-item>
<!--        <t-menu-item to="/manage/student" value="student">学生管理</t-menu-item>-->
<!--        <t-menu-item to="/manage/teacher" value="teacher">教师管理</t-menu-item>-->
      </t-submenu>
      <t-menu-item to="/manage/question" value="question">
        <template #icon>
          <t-icon name="tab"/>
        </template>
        试题管理
      </t-menu-item>
      <t-menu-item to="/manage/statistics" value="statistics">
        <template #icon>
          <t-icon name="parabola"/>
        </template>
        统计分析
      </t-menu-item>

    </t-menu-group>
    <t-menu-group title="考试管理">
      <t-menu-item to="/manage/papers" value="papers">
        <template #icon>
          <t-icon name="root-list"/>
        </template>
        定制测试
      </t-menu-item>
<!--      <t-menu-item to="/manage/exam" value="exam">-->
<!--        <template #icon>-->
<!--          <t-icon name="certificate-1"/>-->
<!--        </template>-->
<!--        定制-->
<!--      </t-menu-item>-->
      <t-menu-item to="/manage/grades" value="grades">
        <template #icon>
          <t-icon name="file-1"/>
        </template>
        成绩管理
      </t-menu-item>
    </t-menu-group>
    <t-menu-group title="行政">
      <t-menu-item to="/manage/courseTable" value="courseTable">
        <template #icon>
          <t-icon name="layout"/>
        </template>
        课表管理
      </t-menu-item>
      <t-menu-item to="/manage/Dormitory" value="Dormitory">
        <template #icon>
          <t-icon name="city-11"/>
        </template>
        宿舍管理
      </t-menu-item>
      <t-menu-item to="/manage/Classroom" value="Classroom">
        <template #icon>
          <t-icon name="store"/>
        </template>
        教室管理
      </t-menu-item>
      <t-submenu title="人员信息" value="">
        <template #icon>
          <t-icon name="user-1"/>
        </template>
        <t-menu-item to="/manage/student" value="student">学员管理</t-menu-item>
        <t-menu-item to="/manage/teacher" value="teacher">教师管理</t-menu-item>
      </t-submenu>
      <t-submenu title="考勤管理" value="1">
        <template #icon>
          <t-icon name="calendar-edit"/>
        </template>
        <t-menu-item to="/manage/leaveRequest" value="leaveRequest">假条管理</t-menu-item>
        <t-menu-item to="/manage/attendance" value="attendance">学生考勤</t-menu-item>
        <t-menu-item to="/manage/schedule" value="schedule">作息管理</t-menu-item>
      </t-submenu>


    </t-menu-group>

    <t-menu-group title="平台管理">

      <t-menu-item to="/manage/message" value="message">
        <template #icon>
          <t-icon name="chat-checked"/>
        </template>
        通知管理
      </t-menu-item>
<!--      <t-menu-item to="/manage/banner" value="banner">-->
<!--        <template #icon>-->
<!--          <t-icon name="image-edit"/>-->
<!--        </template>-->
<!--        轮播图-->
<!--      </t-menu-item>-->
      <t-menu-item to="/manage/administrator" value="administrator">
        <template #icon>
          <t-icon name="user-safety"/>
        </template>
        管理员
      </t-menu-item>
      <t-menu-item to="/manage/onlineUsers" value="onlineUsers">
        <template #icon>
          <t-icon name="user-safety"/>
        </template>
        在线用户
      </t-menu-item>
      <t-menu-item to="/manage/resource" value="resource">
        <template #icon>
          <t-icon name="system-2"/>
        </template>
        软件资源
      </t-menu-item>
    </t-menu-group>

    <t-menu-group title="更多">

      <t-menu-item style="cursor: pointer" :disabled="true">
        <template #icon>
          <switch-galahhad2 style="margin-left: -3px"/>
        </template>
        <span :style="'color:'+(collapsed?'white':'var(--td-text-color-secondary)')  " @click="darkT=!darkT">
          {{ darkT ? '亮色模式' : '暗色模式' }}
        </span>

      </t-menu-item>

      <t-menu-item to="/manage/myself" value="myself">
        <template #icon>
          <t-icon name="user"/>
        </template>
        个人页
      </t-menu-item>
      <t-menu-item @click="toLogOut"   value="logout">
        <template #icon>
          <t-icon name="login"/>
        </template>
        退出登录
      </t-menu-item>

    </t-menu-group>
    <template #operations>
      <t-button class="t-demo-collapse-btn" variant="text" shape="square" @click="changeCollapsed">
        <template #icon>
          <t-icon name="view-list"/>
        </template>
      </t-button>

    </template>
  </t-menu>
</template>

<script setup>
import {ref} from 'vue';
import SwitchGalahhad2 from "@/components/ui/SwitchGalahhad2.vue";
import {useCounterStore} from "@/stores/counter";
import {storeToRefs} from "pinia";
import {DialogPlugin, MessagePlugin} from "tdesign-vue-next";
import service from "@/axios/service";

const store = useCounterStore();
const {darkT, loginUser} = storeToRefs(store);

const collapsed = ref(false);
const iconUrl = ref('https://tdesign.gtimg.com/site/baseLogo-light.png');

const changeCollapsed = () => {
  collapsed.value = !collapsed.value;
  iconUrl.value = collapsed.value
      ? 'https://oteam-tdesign-1258344706.cos.ap-guangzhou.myqcloud.com/site/logo%402x.png'
      : 'https://tdesign.gtimg.com/site/baseLogo-light.png';
};

const changeHandler = (active) => {
  console.log('change', active);
};
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
        // MessagePlugin.success('退出成功');
        // localStorage.removeItem("token")
      }).finally(
          () => {
            localStorage.removeItem("token")
            window.location.href="/"
        }
      )
      confirmDia.hide();
    }
  });
}
</script>
