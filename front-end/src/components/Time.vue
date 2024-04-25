<template>
  <div id="TimeClock">
    <div id="clockBox">
      <canvas ref="clock" id="clock" width="180px" height="180px">您的浏览器不支持 HTML5 canvas 标签</canvas>

      <div id="timeData" class="text-center">
        <h2 style="line-height: 0">{{ time }}</h2>
        <small>{{ date }}</small>

      </div>
    </div>
  </div>
</template>

<script>
import DayJs from 'dayjs'

export default {
  name: "TimeClock",
  data() {
    return {
      clockCanvas: null,
      time: DayJs().format('HH:mm:ss'),
      date: DayJs().format('YYYY:MM:DD')
    }
  },
  methods: {
    drawClock() {
      // 获取画布对象
      const clock = this.clockCanvas

      // 将360度分为100份，并将一份的角度赋给变量 rad
      let rad = (Math.PI * 2) / 100
      let hourRad = this.time.slice(0, 2) * 15 / 3.6
      let minuteRad = this.time.slice(3, 5) * 6 / 3.6
      let secondRad = this.time.slice(6, 8) * 6 / 3.6

      // 实现时钟更新
      hourRad = this.time.slice(0, 2) * 15 / 3.6
      minuteRad = this.time.slice(3, 5) * 6 / 3.6
      secondRad = this.time.slice(6, 8) * 6 / 3.6

      // 绘画
      if (clock.getContext) {
        let clockBrush = clock.getContext('2d')

        // 重绘画布
        clockBrush.clearRect(0, 0, 180, 180)

        // 小时
        /// 蒙版
        clockBrush.beginPath()
        clockBrush.lineWidth = 3
        clockBrush.strokeStyle = '#cccccc50'
        clockBrush.arc(90, 90, 89,0, 100 * rad , false)
        clockBrush.stroke()
        /// 进度
        clockBrush.beginPath()
        clockBrush.lineWidth = 3
        clockBrush.strokeStyle = 'rgb(0, 82, 217)'
        clockBrush.arc(90, 90, 89,0, hourRad * rad , false)
        clockBrush.stroke()

        // 分钟
        /// 蒙版
        clockBrush.beginPath()
        clockBrush.lineWidth = 2
        clockBrush.strokeStyle = '#cccccc50'
        clockBrush.arc(90, 90, 81,0, 100 * rad , false)
        clockBrush.stroke()
        /// 进度
        clockBrush.beginPath()
        clockBrush.lineWidth = 2
        clockBrush.strokeStyle = 'rgb(0, 82, 217)'
        clockBrush.arc(90, 90, 81,0, minuteRad * rad , false)
        clockBrush.stroke()

        // 秒
        /// 蒙版
        clockBrush.beginPath()
        clockBrush.lineWidth = 1
        clockBrush.strokeStyle = '#cccccc50'
        clockBrush.arc(90, 90, 73,0, 100 * rad , false)
        clockBrush.stroke()
        /// 进度
        clockBrush.beginPath()
        clockBrush.lineWidth = 1
        clockBrush.strokeStyle = 'rgb(0, 82, 217)'
        clockBrush.arc(90, 90, 73,0, secondRad * rad , false)
        clockBrush.stroke()

      } else {
        console.log('浏览器不支持canvas')
      }
    }
  },
  created() {
    // 实现时间刷新
    let _this = this
    setInterval(function () {
      _this.date = DayJs().format('YYYY:MM:DD')
    }, 1000 * 3600 * 24)
    setInterval(function () {
      _this.time = DayJs().format('HH:mm:ss')
    }, 1000)
  },
  mounted() {
    // 初始化绑定canvas对象
    this.clockCanvas = this.$refs.clock

    // 初始化时钟
    this.drawClock()
    // 实现时钟刷新
    let that = this
    setInterval(function () {
      that.drawClock()
    }, 1000)
  }
}
</script>

<style lang="scss" scoped>
#TimeClock {

  #clockBox {
    width: 180px;
    height: 180px;
    position: relative;
    z-index: 2;

    #clock {
      position: relative;
      border-radius: 50%;
      transform: rotate(-90deg);   /* 配合 canvas 画布，使得圆形起始点为正上方中间位置 */
      background-color:var(--td-bg-color-container);
      z-index: 2;
    }

    #timeData {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      cursor: default;
      transition: 0.4s;
      z-index: 3;

      small {
        font-size: 14px;
      }

      p {
        margin: 0;
        font-size: 16px;
        font-weight: bold;
      }
    }
    #timeData:hover{
      text-shadow: 0 2px 10px rgba(0, 0, 0, 0.8);
    }
  }
  #clockBox::before {
    content: '';
    position: absolute;
    width: 180px;
    height: 180px;
    top: 50%;
    left: 50%;
    border-radius: 50%;
    transform: translate(-50%, -50%);
    background-color: rgba(0, 111, 255, 0.27);
    filter: blur(24px);
    z-index: 1;
  }
}
</style>
