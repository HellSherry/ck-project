<script setup>
import {ref} from "vue";
import SwitchGalahhad2 from "@/components/ui/SwitchGalahhad2.vue";
import {useCounterStore} from "@/stores/counter";
import {showToast} from "@nutui/nutui";

const store = useCounterStore();
const backImg=Math.round(Math.random()*30);
const textToast = (msg) => {
  showToast.text("sajhdfkljjsad", {
    center: false,
    bottom: '90%',
    left: '50%',
  });
};
</script>

<template>
  <div
      :style="`background: url('/assets1/image/mobileback/th (${backImg}).jpg');height: 100vh;width: 100vw;background-position-x: center;background-size: cover;background-repeat: no-repeat`">
    <div style="height: 80px"></div>
    <div v-if="true">
      <view :style="'background-color: '+(store.darkT?'rgba(0,0,0,0.5)':'rgba(255,255,255,0.5)')" class="backColor">
        <h3 style="margin-left: 20px; line-height: 12px;margin-bottom: 4px">考试</h3>
        <div style="padding: 10px;">
          <t-list style="border-radius: 10px" size="small" :split="true">
            <t-list-item>
              <t-list-item-meta   description="列表主内容" />
              <template #action>
                <t-button @click="textToast"  :ghost="!store.darkT" shape="round">去考试</t-button>
              </template>
            </t-list-item>
          </t-list>
<!--          <nut-cell-group   >-->
<!--            <nut-cell  center title="12 27 T36周测"-->
<!--                       sub-title=""    is-link>-->
<!--              <template #link><t-button @click="textToast"  ghost shape="round">去考试</t-button></template>-->


<!--            </nut-cell>-->
<!--          </nut-cell-group>-->
        </div>
      </view>
      <div style="height: 12px"></div>

    </div>

    <view :style="'background-color: '+(store.darkT?'rgba(0,0,0,0.5)':'rgba(255,255,255,0.5)')" class="backColor">
      <h3 style="margin-left: 20px; line-height: 12px">学情报告</h3>
      <nut-grid :column-num="3" style=" width: 90%;margin: 20px auto;">
        <nut-grid-item text="学习知识点"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>
        <nut-grid-item text="学习时长"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>
        <nut-grid-item text="累计答题"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>
        <nut-grid-item text="本日答题数量"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>
        <nut-grid-item text="实际答题数量"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>
        <nut-grid-item text="答题正确率"><h2 style="line-height: 1px;margin-bottom: 4px">0</h2></nut-grid-item>

      </nut-grid>
    </view>
    <div style="height: 12px"></div>
    <view :style="'background-color: '+(store.darkT?'rgba(0,0,0,0.5)':'rgba(255,255,255,0.5)')" class="backColor">
      <h3 style="margin-left: 20px; line-height: 12px">公共自习室</h3>
      <div style="padding: 10px;text-align: center">
        <nut-button shape="round" type="info" style="width: 50%">一键自习</nut-button>

      </div>
    </view>
    <div style="height: 20px"></div>
    <view v-if="false" :style="'background-color: '+(store.darkT?'rgba(0,0,0,0.5)':'rgba(255,255,255,0.5)')"
          class="backColor">
      <h3 style="margin-left: 20px; line-height: 12px">考试</h3>
      <div style="padding: 10px;text-align: center">

      </div>
    </view>
  </div>

</template>

<style scoped>
.backColor {
  display: block;
  backdrop-filter: blur(4px);
  width: 90%;
  margin: 0 auto;
  border: 1px solid rgba(245, 245, 245, 0.1);
  border-radius: 12px;
}
</style>