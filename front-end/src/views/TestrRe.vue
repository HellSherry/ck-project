<template>
{{checkboxValue}}
    <t-select-input
        v-model:inputValue="inputValue"
        :value="classValue"
        clearable
        multiple
        @tag-change="onTagChange"
        @input-change="onInputChange"
    >
      <template #panel>
        <t-checkbox-group
            v-if="options.length"
            :value="checkboxValue"
            :options="options"
            class="tdesign-demo__panel-options-multiple"
            @change="onCheckedChange"
        />
        <div v-else class="tdesign-demo__select-empty-multiple">暂无数据</div>
      </template>
      <template #suffixIcon>
        <chevron-down-icon />
      </template>
    </t-select-input>
</template>
<script setup>
import { computed, ref } from 'vue';

const OPTIONS = [
  // 全选
  { label: 'Check All', checkAll: true },
  { label: 'tdesign-vue', value: 1 },
  { label: 'tdesign-react', value: 2 },
  { label: 'tdesign-miniprogram', value: 3 },
  { label: 'tdesign-angular', value: 4 },
  { label: 'tdesign-mobile-vue', value: 5 },
  { label: 'tdesign-mobile-react', value: 6 },
];

const inputValue = ref('');
// 全量数据
  const options = ref([...OPTIONS]);
const classValue = ref([
  { label: 'Vue', value: 1 },
  { label: 'React', value: 2 },
  { label: 'Miniprogram', value: 3 },
]);



const checkboxValue = computed(() => {
  const arr = [];
  const list = classValue.value;
  // 此处不使用 forEach，减少函数迭代
  for (let i = 0, len = list.length; i < len; i++) {
    list[i].value && arr.push(list[i].value);
  }
  return arr;
});

// 直接 checkboxgroup 组件渲染输出下拉选项
const onCheckedChange = (val, { current, type }) => {
  console.log(current);
  // current 不存在，则表示操作全选
  if (!current) {
    classValue.value = type === 'check' ? options.value.slice(1) : [];
    return;
  }
  // 普通操作
  if (type === 'check') {
    const option = options.value.find((t) => t.value === current);
    classValue.value.push(option);
  } else {
    classValue.value = classValue.value.filter((v) => v.value !== current);
  }
  if (creatable.value && trigger === 'enter') {
    const current = { label: item, value: item };
    value.value.push(current);
    const newOptions = options.value.concat(current);
    options.value = newOptions;
    inputValue.value = '';
  }
};

// 可以根据触发来源，自由定制标签变化时的筛选器行为
const onTagChange = (currentTags, context) => {
  console.log(currentTags, context);
  const { trigger, index, item } = context;
  if (trigger === 'clear') {
    classValue.value = [];
  }
  if (['tag-remove', 'backspace'].includes(trigger)) {
    classValue.value.splice(index, 1);
  }
};
const onInputChange = (val, context) => {
  console.log(val, context);
};
</script>
<style>
.tdesign-demo__panel-options-multiple {
  width: 100%;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 2px;
}
.tdesign-demo__panel-options-multiple .t-checkbox {
  display: flex;
  border-radius: 3px;
  line-height: 22px;
  cursor: pointer;
  padding: 3px 8px;
  color: var(--td-text-color-primary);
  transition: background-color 0.2s linear;
  white-space: nowrap;
  word-wrap: normal;
  overflow: hidden;
  text-overflow: ellipsis;
  margin: 0;
}
.tdesign-demo__panel-options-multiple .t-checkbox:hover {
  background-color: var(--td-bg-color-container-hover);
}
</style>
