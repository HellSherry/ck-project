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
    text: '班级分布',
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)',
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    data: ['Direct', 'Email', 'Ad Networks', 'Video Ads', 'Search Engines'],
  },
  series: [
    {
      name: '班级分布',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [
        {value: 335, name: 'Direct'},
        {value: 310, name: 'Email'},
        {value: 234, name: 'Ad Networks'},
        {value: 135, name: 'Video Ads'},
        {value: 1548, name: 'Search Engines'},
      ],
      itemStyle:{
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

service.get('/statistics/classCount').then((res) => {
  console.log(res)
  option.value.series[0].data=res.data
  option.value.legend.data=[]
  res.data.forEach((item) => {
    option.value.legend.data.push(item.name)

  })

})




</script>