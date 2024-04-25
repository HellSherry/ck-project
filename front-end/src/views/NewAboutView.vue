<template>
  <!--  <img  draggable="false"   :src="'/src/assets/aboutpic/img'+(index+3)+'.jpg'"  style="width: 100%"    v-for="index in 5">-->
  <img  draggable="false"   :src="'/assets1/aboutpic/img'+(index+3)+'.jpg'"  style="width: 100%"    v-for="index in 5">
  <!--  <t-image-->
  <!--      style=" -webkit-user-select: none;-webkit-user-drag: none;"-->
  <!--      v-for="index in 5"-->
  <!--      :key="index"-->
  <!--      :src="'/src/assets/aboutpic/img'+(index+3)+'.jpg'"-->
  <!--      :alt="'img'+(index+3)+'.jpg'"-->
  <!--      :lazy="true"-->
  <!--  />-->
</template>

<style scoped>
img{
  padding: 0;
}
</style>
<script setup>
</script>