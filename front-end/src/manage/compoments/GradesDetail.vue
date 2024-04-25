<template>

<!--{{onlineUser}}-->
  <t-table
      size="small"
      row-key="index"
      :columns="columns"
      :data="data"
      :sort="sort"
      lazy-load
      @sort-change="sortChange"
      @data-change="dataChange"
  >

    <template #answerTime="{ row }">
      {{ dayjs.duration(row.answerTime).format("mm:ss") }}
    </template>
    <template #score="{ row }">
       <t-tag size="small" v-if="props.onlineUser.some(user => user ==row.userId)" theme="primary">正在考试</t-tag>

      <t-tag size="small" v-else-if="row.score==null" theme="danger">未提交</t-tag>

      <span v-else>
            {{ row.score }}
          </span>
    </template>


  </t-table>


</template>

<script setup lang="jsx">
import {ref, watch} from 'vue';
import dayjs from "dayjs";

const props = defineProps(['selectData','onlineUser']);


const columns = ref([
  {colKey: 'userId', title: 'ID', width: '60px'},
  {colKey: 'answerContent', title: '考生', ellipsis: true,},
  {
    colKey: 'score', title: '分数', sortType: 'all',
    sorter: (a, b) => a.score - b.score
  },
  {
    colKey: 'answerTime', title: '时间', sortType: 'all',
    sorter: (a, b) => a.answerTime - b.answerTime
  }
]);

const data = ref([...props.selectData]);

watch(() => props.selectData,
    (val) => {
      data.value = [...props.selectData];
    })
const sort = ref({});
const sortChange = (sortVal, options) => {
  console.log('sort-change', sortVal, options);
  // sort.value 和 data.value 的赋值都是必须
  sort.value = sortVal;
  data.value = options.currentDataSource;
};
const dataChange = (newData) => {
  data.value = newData;
};
function isOnline(id){
  let a=false
  props.onlineUser.forEach(e=>{
    if (e==id){
      a=true
    }
  })
  return a
}
</script>

