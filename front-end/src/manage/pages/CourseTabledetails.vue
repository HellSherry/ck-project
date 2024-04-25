<template>
  <div>
    <!-- 当前示例包含：输入框、单选、多选、日期 等场景 -->
    <!-- editableCellState 用于控制某些单元格为只读状态 -->
    <t-table
        ref="tableRef"
        row-key="key"
        :columns="
                    [
            { colKey: 'day' ,align: 'center' },
            { colKey: 'lesson_1', title: '第一节课\n8:30-9:15' ,align: 'center',edit: edit},
            { colKey: 'lesson_2', title: '第二节课\n8:30-9:15' ,align: 'center',edit: edit},
            { colKey: 'lesson_3', title: '第三节课\n8:30-9:15' ,align: 'center',edit: edit},
            { colKey: 'lesson_4', title: '第四节课\n8:30-9:15' ,align: 'center',edit: edit},
            { colKey: 'lesson_5', title: '第五节课\n8:30-9:15' ,align: 'center',edit: edit},
            { colKey: 'lesson_6', title: '第六节课\n8:30-9:15' ,align: 'center',edit: edit},
            { colKey: 'lesson_7', title: '第七节课\n8:30-9:15' ,align: 'center',edit: edit},
            { colKey: 'lesson_8', title: '第八节课\n8:30-9:15' ,align: 'center',edit: edit},
            { colKey: 'eveningSelfStudy', title: '晚自习\n8:30-9:15' ,align: 'center',edit: edit},
             ]"
        :data=tableData
        :editable-cell-state="editableCellState"
        bordered
        lazy-load
    />
    <br />

    <!-- 示例代码有效，勿删 -->
<!--    <t-space>-->
<!--      <t-button @click="validateTableData">校验</t-button>-->
<!--    </t-space>-->
  </div>
<div style="margin-bottom: -16px"></div>
    <t-button style=" z-index: 1000 ;position: absolute;bottom: 32px;" @click=" openDrawer=true">模板</t-button>

  <t-drawer
      v-model:visible="openDrawer"
      header="课表模板"
      size="large"
        :size-draggable="true"
      :closeBtn="true"
      @confirm="()=>{
        openDrawer = false;
        useModel!=null && (tableData=[...model[useModel]])
        emit('dataChange',tableData )
      }"
      @cancel="openDrawer = false;useModel=null"
      @close="openDrawer = false;useModel=null"
  >
    <t-alert theme="info" v-if="useModel==null" message="请选择一个模板" />

    <nut-table

        v-for="(item,index) in model"
        @click="useModel==index?useModel=null:useModel=index"
        :style="useModel===index?'border:1.3px solid #216efa':''"
       :bordered="false"
        :striped="true"
        style="box-shadow: 0 0 8px rgba(0,0,0,0.07);border-radius: 8px;overflow: hidden;margin-right: -40px;transform: scale(.88);width: 110%;cursor: pointer;margin-left: -5%;user-select: none;"
        :columns="
                    [
            { key: 'day' ,align: 'center',height: 200 },
            { key: 'lesson_1', title: '第一节课' ,align: 'center'},
            { key: 'lesson_2', title: '第二节课' ,align: 'center'},
            { key: 'lesson_3', title: '第三节课' ,align: 'center'},
            { key: 'lesson_4', title: '第四节课' ,align: 'center'},
            { key: 'lesson_5', title: '第五节课' ,align: 'center'},
            { key: 'lesson_6', title: '第六节课' ,align: 'center'},
            { key: 'lesson_7', title: '第七节课' ,align: 'center'},
            { key: 'lesson_8', title: '第八节课' ,align: 'center'},
            { key: 'eveningSelfStudy', title: '晚自习'  ,align: 'center' },
             ]" :data="item"></nut-table>

   </t-drawer>
</template>

<script setup >
import {ref, computed, reactive, watch, onMounted} from 'vue';
import { Input, Select, DatePicker, MessagePlugin } from 'tdesign-vue-next';
import dayjs from 'dayjs';
const props = defineProps(['tableData'])

const tableData =  ref(props.tableData);
const useModel =  ref(null);
onMounted(
    () => {
       tableData.value = props.tableData;
    }
)


const emit = defineEmits(['dataChange'])



const openDrawer=ref(false);

const align = ref('left');
const getChineseDay = (num) => {
  switch (num)
  {
    case 0:
      return '星期一'
    case 1:
      return '星期二'
    case 2:
      return '星期三'
    case 3:
      return '星期四'
    case 4:
      return '星期五'
    case 5:
      return '星期六'
    case 6:
      return '星期天'
    default:
      return 'day'+num
  }
}
//
const model = reactive([
 [
    {
      day: '星期一',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期二',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期三',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期四',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期五',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期六',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期日',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    }

  ],
  [
    {
      day: '星期一',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期二',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期三',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期四',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期五',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期六',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期日',
      lesson_1: '开发',
      lesson_2: '开发',
      lesson_3: '开发',
      lesson_4: '开发',
      lesson_5: '实训',
      lesson_6: '实训',
      lesson_7: '实训',
      lesson_8: '实训',
      eveningSelfStudy: '晚自习'
    }

  ],
 [
    {
      day: '星期一',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期二',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期三',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期四',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期五',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期六',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    },{
      day: '星期日',
      lesson_1: '实训',
      lesson_2: '实训',
      lesson_3: '实训',
      lesson_4: '实训',
      lesson_5: '开发',
      lesson_6: '开发',
      lesson_7: '开发',
      lesson_8: '开发',
      eveningSelfStudy: '晚自习'
    }

  ]

]);
function models(){
  
}

// const data = ref([...operationValue.header]);

// 用于控制哪些行或哪些单元格不允许出现编辑态，参数有 { row, col, rowIndex, colIndex }
const editableCellState = (cellParams) => {
  // 第一行不允许编辑
  const { row } = cellParams;
  return row.status !== 2;
};

const tableRef = ref();
// 用于提交前校验数据（示例代码有效，勿删）
const validateTableData = () => {
  // 仅校验处于编辑态的单元格
  tableRef.value.validateTableData().then((result) => {
    console.log('validate result: ', result);
  });
};
const edit = {
  // 1. 支持任意组件。需保证组件包含 `value` 和 `onChange` 两个属性，且 onChange 的第一个参数值为 new value。
  // 2. 如果希望支持校验，组件还需包含 `status` 和 `tips` 属性。具体 API 含义参考 Input 组件
  component: Input,
  // props, 透传全部属性到 Input 组件。可以是一个函数，不同行有不同的 props 属性 时，使用 Function）
  props: {
    clearable: true,
   },
  // 触发校验的时机（when to validate)
  validateTrigger: 'change',
  // 透传给 component: Input 的事件（也可以在 edit.props 中添加）
  on: (editContext) => ({
    onInput: (ctx) => {
        const newData = [...tableData.value];
      newData.splice(editContext.rowIndex, 1, editContext.editedRow);
      tableData.value = newData;


      emit('dataChange',newData )

      console.log('失去焦点', editContext);
    },
    onEnter: (ctx) => {
      ctx?.e?.preventDefault();
      console.log('onEnter', ctx);
    },
  }),
  // 除了点击非自身元素退出编辑态之外，还有哪些事件退出编辑态
  abortEditOnEvent: ['onEnter'],
  // 编辑完成，退出编辑态后触发

  onEdited: (context) => {
    console.log(context);
    const newData = [...tableData.value];
    newData.splice(context.rowIndex, 1, context.newRowData);
    tableData.value = newData;
    console.log('Edit firstName:', context);

    emit('dataChange',newData )



    console.log(operationValue.header)
    MessagePlugin.success('Success');
  },
  // 校验规则，此处同 Form 表单。https://tdesign.tencent.com/vue-next/components/form
  rules: [
    { required: true, message: '不能为空' },
    { max: 10, message: '字符数量不能超过 10', type: 'warning' },
  ],
  // 默认是否为编辑状态
  defaultEditable: false,
}

</script>
