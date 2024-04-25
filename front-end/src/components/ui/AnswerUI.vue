<script setup>
import {ref, watch} from "vue";
import ToHtml from "@/components/answer-correlation/ToHtml.vue";


const props = defineProps(['options', 'modelValue', 'answerLen'])
const emit = defineEmits(['update:modelValue'])
const selectedValue = ref()
if (typeof props.modelValue == "object") {
  selectedValue.value = props.modelValue
} else {
  selectedValue.value = ('' + props.modelValue).replace('"', '').replace('"', '').split(",")
}

// const selectedValue = ref(typeof props.modelValue =="object" ? props.modelValue:props.modelValue.replace(new RegExp('"',"g"),'').length===1?props.modelValue.replace(new RegExp('"',"g"),''):props.modelValue.replace(new RegExp('"',"g"),'').split(","));
console.log(selectedValue.value)
watch(selectedValue, (val) => {
  console.log(val.length)
  console.log(props.answerLen)
  if (val.length > props.answerLen) {
    selectedValue.value.splice(0, 1)
  }
  console.log(typeof selectedValue.value)
  emit('update:modelValue', selectedValue.value)
})
</script>

<template>
  <!--{{selectedValue}}-->
  <!--  <input type="text" v-model="selectedValue">-->
  <div class="radio-input">

    <label v-for="(item,index) in options"
           :style="selectedValue.indexOf(index+1)!==-1||selectedValue.indexOf((index+1)+'')!==-1?'background-color:#0052d918;box-shadow: 0 0 2px  rgb(0, 82, 217,0.4), inset 0 0 4px  rgb(0, 82, 217,0.4);border-color: rgb(0, 82, 217);':''">
      <ToHtml :value="item"/>
      <input
          type="checkbox"
          :value="index+1"
          v-model="selectedValue"
      />
    </label>


  </div>

  <!--{{selectedValue.toString()}}-->
</template>

<style scoped>
.radio-input input {
  display: none;
}

.info {
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

p {
  padding: 0;
  margin: 0;
}

.radio-input label {
  display: flex;
  padding: 14px;
  margin: 8px 0 0 0;
  font-size: 13px;
  font-weight: 600;
  border-radius: 12px;
  cursor: pointer;
  border: 1px solid var(--td-text-color-primary);
  color: var(--td-text-color-primary);
  transition: .3s ease;
}

@media screen and (min-width: 500px) {
  .radio-input label:hover {
    background-color: rgba(24, 24, 24, 0.13);
    border: 1px solid #bbb;
  }
}


.result {
  margin-top: 10px;
  font-weight: 600;
  font-size: 12px;
  display: none;
  transition: display .4s ease;
}


</style>