<script setup>
defineProps(['value'])
</script>
<template>
  <div class="vueInnerHtml" v-html="value"></div>
</template>

<style>
.vueInnerHtml * {
  margin: 0;
  padding: 0;
  -webkit-user-drag: none;
  user-select: none;
}
.vueInnerHtml {
 }
.vueInnerHtml img{
  display: block;
  filter: drop-shadow(-1000px 0px var(--td-text-color-primary));
  margin-left: 1000px;
 }
.vueInnerHtml p{
  word-break:break-all;

}
.vueInnerHtml pre{
  padding: 10px;
  margin:8px  2%;
  border-radius: 8px;
  color:var(--td-bg-color-container) ;
background-color: var(--td-text-color-primary);
  opacity:0.94;

}

</style>