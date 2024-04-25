<template>
  <div @click="()=>{
    play()
  }">
    {{ obj.output }}
    <span class="easy-typed-cursor"></span>
  </div>
</template>

<script setup lang="ts">
import {onMounted, reactive, ref} from 'vue'
 import axios from 'axios'
import EasyTyper from "easy-typer-js";
import {useSpeechSynthesis} from "@vueuse/core";
const obj = reactive({
  output: '',
  isEnd: false,
  speed: 80,
  singleBack: false,
  sleep: 0,
  type: 'normal',
  backSpeed: 40,
  sentencePause: false
})


const voice = ref<SpeechSynthesisVoice>(undefined as unknown as SpeechSynthesisVoice)
const text = ref('Hello, everyone! Good morning!')
const pitch = ref(1)
const rate = ref(1)

const speech = useSpeechSynthesis(text, {
  voice,
  pitch,
  rate,
})

let synth: SpeechSynthesis

const voices = ref<SpeechSynthesisVoice[]>([])

onMounted(() => {
  if (speech.isSupported.value) {
    // load at last
    setTimeout(() => {
      synth = window.speechSynthesis

          synth.getVoices().forEach(v => {
        if (v.name.indexOf('Chinese')!=-1){
          voices.value.push(v)
        }
      })


      console.log(voices.value);
      voice.value = voices.value[Math.floor(Math.random()*voices.value.length)]

      // voice.value = voices.value[0]
    })
  }
})

function play() {
  if (speech.status.value === 'pause') {
    console.log('resume')
    window.speechSynthesis.resume()
  }
  else {
    speech.speak()
  }
}



axios.get("http://v1.hitokoto.cn").then(
    response => {
      new EasyTyper(obj, response.data.hitokoto,null,null)
      text.value = response.data.hitokoto
     }
)
</script>

