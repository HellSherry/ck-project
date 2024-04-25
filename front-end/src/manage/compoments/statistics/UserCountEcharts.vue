<template>
  <v-chart class="chart" :option="option" autoresize/>
</template>

<script setup>
import {use} from 'echarts/core';
import {CanvasRenderer} from 'echarts/renderers';
import {PieChart} from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
} from 'echarts/components';
import VChart, {THEME_KEY} from 'vue-echarts';
import {ref, defineComponent, watch} from 'vue';
import {useCounterStore} from "@/stores/counter";
import {provide} from 'vue'
import service from "@/axios/service";

use([
  CanvasRenderer,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
]);


const store = useCounterStore();
provide(THEME_KEY, store.darkT ? 'dark' : 'light')

const option = ref({
  title: {
    text: '用户分布',
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)',
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    data: ['学生', '教师', '管理员'],
  },
  series: [
    {
      name: '用户分布',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [
        {value: 335, name: 'Direct'},
        {value: 310, name: 'Email'},
        {value: 234, name: 'Ad Networks'},
        {value: 135, name: 'Video Ads'},
        {value: 1548, name: 'Search Engines'},
      ],      itemStyle:{
        normal:{
          label:{
            show: true,
            formatter: '{b}:{c}',

          },

        }
      },
      emphasis: {
        itemStyle: {

          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)',
        },
      },
    },
  ],
});

service.get('/statistics/userCount').then((res) => {
  console.log(res)
  option.value.series[0].data=[]
  option.value.legend.data=[]
  res.data.forEach((item) => {
    item.name=toChina(item.name)
    option.value.legend.data.push(item.name)
    option.value.series[0].data.push({name: item.name, value: item.value})
  })

})
function toChina(aa) {
  switch (aa) {
    case 1:
      return '学生';
    case 2:
      return '教师';  case 3:
      return '管理员';
  }
}



</script>