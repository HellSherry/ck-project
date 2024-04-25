

<template>

      <div  :style="store.darkT?'background-color: rgb(224,230,255);':''" style=" height: 800px;overflow: hidden ;">


        <iframe allowtransparency="true" style="margin-top: -150px;width: 100vw ;" :style="store.darkT?'mix-blend-mode:difference':''" src="https://cn.bing.com/translator"

                height="1000" frameborder="0">
          加载中
        </iframe>



      </div>





</template>

<style>
body{
  background-color:transparent;
}
</style>
<script setup  >
import {useCounterStore} from "@/stores/counter";

const store = useCounterStore();
</script>