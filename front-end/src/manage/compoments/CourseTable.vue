<script setup lang="ts">
import {  defineProps,h } from 'vue'//使用语法糖才能使用他们
import { Dongdong } from '@nutui/icons-vue';

 const props=defineProps({
  data:{
    type:Array,
    default:()=>[]
  }
})
 </script>


<template>
   <nut-table

      :bordered="false"
      :striped="true"
      style="box-shadow: 0 0 8px rgba(0,0,0,0.07);border-radius: 8px;overflow: hidden; cursor: pointer;user-select: none;"
      :columns="
                    [
            { key: 'day' ,align: 'right',height: 200 ,
             render: (record) => {
      return h('div',
        {
          style: {
 width:'48px'
          },
          onClick: () => {
            console.log(record);
          }
        },
        record.day);
    }},
            { key: 'lesson_1', title: '第一节课\n8:30/9:15' ,align: 'center' },
            { key: 'lesson_2', title: '第二节课\n8:30/9:15' ,align: 'center'},
            { key: 'lesson_3', title: '第三节课\n8:30/9:15' ,align: 'center'},
            { key: 'lesson_4', title: '第四节课\n8:30/9:15' ,align: 'center'},
            { key: 'lesson_5', title: '第五节课\n8:30/9:15' ,align: 'center'},
            { key: 'lesson_6', title: '第六节课\n8:30/9:15' ,align: 'center'},
            { key: 'lesson_7', title: '第七节课\n8:30/9:15' ,align: 'center'},
            { key: 'lesson_8', title: '第八节课\n8:30/9:15' ,align: 'center'},
            { key: 'eveningSelfStudy', title: '晚自习\n8:30/9:15'  ,align: 'center' },
             ]" :data="  props.data  "></nut-table>

</template>

<style scoped>

</style>