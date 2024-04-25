<script setup>
import service from "@/axios/service";
import {ref} from "vue";
const countList = ref([]);
service.get("/questionCourse/getAll").then((response) => {
      countList.value = response.data;})
const props = defineProps(['number']);
console.log(props)
 </script>

<template>
   <t-list size="small" :split="true">
    <t-list-item v-for="(item, index) in countList" :key="index">
      <t-list-item-meta  :description="item.name"   />
      <template #action>
         <t-button @click="$router.push({
          name: 'practice-question',
          query: {method: 'course',num:props.number, courseId: item.id}
         })" size="small">
            进入测试
         </t-button>
      </template>
    </t-list-item>
   </t-list>
</template>

<style scoped>

</style>