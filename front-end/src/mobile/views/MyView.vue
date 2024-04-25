<script setup>
import {useCounterStore} from "@/stores/counter";
import SwitchGalahhad2 from "@/components/ui/SwitchGalahhad2.vue";

const store = useCounterStore();
</script>

<template>
  <div
      :style="`background: url('/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg');height: 100vh;width: 100vw;background-position-x: center;background-size: cover;background-repeat: no-repeat`">
<div style="height: 100vh;backdrop-filter: blur(8px)">
  <div style="height: 320px"></div>
  <div  v-if="true">
    <view  :style="'background-color: '+(store.darkT?'rgba(0,0,0,0.5)':'rgba(255,255,255,0.5)')" class="backColor">
<!--      <h3 style="margin-left: 20px; line-height: 12px;margin-bottom: 4px">考试</h3>-->
      <div style="padding: 10px;">
        <t-list style="margin-top: 8px;border-radius: 10px" size="small" :split="true">
          <t-list-item>
            <t-list-item-meta   description="主题颜色" />
            <template #action>
              <div style="height: 40px;margin-top: -20px">
                <switch-galahhad2/>
              </div>

            </template>
          </t-list-item>
        </t-list>
        <t-list style="margin-top: 8px;border-radius: 10px" size="small" :split="true">
          <t-list-item>
            <t-list-item-meta   description="主题颜色" />
            <template #action>
              <div style="height: 40px;margin-top: -20px">
                <switch-galahhad2/>
              </div>

            </template>
          </t-list-item>
          <t-list-item>
            <t-list-item-meta   description="主题颜色" />
            <template #action>
              <div style="height: 40px;margin-top: -20px">
                <switch-galahhad2/>
              </div>

            </template>
          </t-list-item>
          <t-list-item>
            <t-list-item-meta   description="主题颜色" />
            <template #action>
              <div style="height: 40px;margin-top: -20px">
                <switch-galahhad2/>
              </div>

            </template>
          </t-list-item>
        </t-list>
        <t-list style="margin-top: 8px;border-radius: 10px" size="small" :split="true">
          <t-list-item>
            <t-list-item-meta   description="主题颜色" />
            <template #action>
              <div style="height: 40px;margin-top: -20px">
                <switch-galahhad2/>
              </div>

            </template>
          </t-list-item>
          <t-list-item>
            <t-list-item-meta   description="主题颜色" />
            <template #action>
              <div style="height: 40px;margin-top: -20px">
                <switch-galahhad2/>
              </div>

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
</div>
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