<template>
  <div style="background-color: var(--td-bg-color-container);height: 100%">
    <div v-show="!store.darkT" style="filter: blur(92px); background-color: rgb(0,0,255);position: absolute;top: 4px;left: 0;width: 160px;height: 50px;border-radius: 0 0 500px 0"></div>
    <div v-show="!store.darkT" style="filter: blur(120px); background-color: rgb(255,0,242);position: absolute;top: 8px;right:0;width: 230px;height: 40px;border-radius: 0 0 0 500px"></div>

    <nut-tabs swipeable background="#00000000"  type="smile" v-model="value" title-scroll  name="tabName">
      <nut-tab-pane style="background-color: #00000000"  title="练习" pane-key="2">
        练习
      </nut-tab-pane>
      <nut-tab-pane style="background-color: #00000000"  title="考试" pane-key="1">
        dsfjadsffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
      </nut-tab-pane>
      <nut-tab-pane   disabled="true" pane-key="-10"/>
      <nut-tab-pane   disabled="true" pane-key="-10"/>
      <nut-tab-pane   disabled="true" pane-key="-10"/>
      <nut-tab-pane   disabled="true" pane-key="-10"/>

    </nut-tabs>

  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue';
const value = ref('1');
import { useCounterStore } from '@/stores/counter';
const store = useCounterStore();
</script>
<style>
.nut-tabs__titles{
  margin-top: 14px;

}

.nut-theme-dark .nut-tabs__titles-item {
  background-color: var(--td-bg-color-container);
}
.nut-tabs__titles-item__text{
  color: var(--td-text-color-primary);
  font-size: larger;
  margin-bottom: 6px;
}

</style>

