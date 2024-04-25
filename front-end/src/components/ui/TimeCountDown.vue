<script setup>
import { ref } from 'vue';
const props = defineProps(['endTime'])
console.log(props.endTime)
// const end = ref( 60*30 );
const starTime = ref(Date.now() );
const times = ref({});
</script>

<template>
<!--  {{times}}-->
  <nut-circle-progress  :color="{
      '0%':  'rgba(44,142,255,0.7)',
  '100%':  '#2c68ff',
  }" :progress="(times.s+(times.m+(times.h*60))*60)*(100/endTime)"  >  <nut-countdown style="user-select: none;padding-left: 18PX"  :end-time="starTime+endTime*1000"  v-model="times" /></nut-circle-progress>
</template>

<style scoped>

</style>