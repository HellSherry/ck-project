<template>
  <v-chart class="chart" :option="option" autoresize/>
</template>

<script setup>
import {use} from 'echarts/core';
import {CanvasRenderer} from 'echarts/renderers';
import {PieChart} from 'echarts/charts';

import VChart, {THEME_KEY} from 'vue-echarts';
import {ref, defineComponent, watch} from 'vue';
import {useCounterStore} from "@/stores/counter";
import {provide} from 'vue'
import service from "@/axios/service";
 import { BarChart } from 'echarts/charts'
import {
  TitleComponent,
  GridComponent,
  DataZoomComponent
} from 'echarts/components'

use([
    PieChart,
   TitleComponent,
  GridComponent,
  DataZoomComponent,
  BarChart,
  CanvasRenderer
])



const store = useCounterStore();
provide(THEME_KEY, store.darkT ? 'dark' : 'light')


// Enable data zoom when user click bar.
// const zoomSize = 6;
// myChart.on('click', function (params) {
//   console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
//   myChart.dispatchAction({
//     type: 'dataZoom',
//     startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
//     endValue:
//         dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
//   });
// });
const option = ref({
  tooltip: {
    trigger: 'item',
    formatter: '统计分析<br/>{b} : {c}',
  },
  title: {
    text: '统计分析',
    subtext: 'StatisticalAnalysis',
    left: 'center',
  },
  xAxis: {
    data: [],
    axisLabel: {
      inside: false,
      fontSize: 12,
      color: 'var(--td-text-color-primary)'
    },
    axisTick: {
      show: false
    },
    axisLine: {
      show: false
    },
    z: 10
  },
  yAxis: {
    axisLine: {
      show: false
    },
    axisTick: {
      show: false
    },
    axisLabel: {
      color: '#999'
    }
  },
  dataZoom: [
    {
      type: 'inside'
    }
  ],
  series: [
    {
      type: 'bar',
      showBackground: true,
      label: {
        show: true, //开启显示
        position: 'top', //在上方显示

        textStyle: { //数值样式
          color: 'black',//字体颜色
          fontSize: 10//字体大小
        }},
      data: []
    }
  ]
});

service.get('/statistics/more').then((res) => {
  console.log(res)

  res.data.forEach((item) => {
    option.value.xAxis.data.push(item.name)
    option.value.series[0].data.push(item.value)

  })


})


</script>