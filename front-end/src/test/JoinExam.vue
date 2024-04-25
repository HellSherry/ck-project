<script setup>
import service from "@/axios/service";

service.get("/exam/getPaper?paperId=24").then((res) => {
  console.log(res.data)
  // let paper = typeof res.data === 'string' ? JSON.parse(res.data) : res.data
  // var papers = [];
  // Object.keys(paper).forEach((key) => {
  //   papers.push({v: paper[key], k: key});
  // });
  // console.log(papers)
})
</script>

<template>

</template>

<style scoped>

</style>