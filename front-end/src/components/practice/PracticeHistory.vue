<template>
    <div v-if="practiceList != null" class="centre">
<!--        <div class="centre-div-top">-->
<!--            <a class="centre-span" style="margin-left: 100px;">自测卷</a>-->
<!--            <a class="centre-span">......</a>-->
<!--        </div>-->
      <div style="margin-top: 8px">

          <t-tag variant="light-outline" theme="primary" shape="round" size="small">
            共有{{ practiceList.total }}条历史
          </t-tag>



        <div style="display: inline-block;float: right;width:200px;margin-left: 10px">

          <t-pagination :totalContent="false" @change="initContex" v-model="operationValue.page" theme="simple" :showPageSize="false"
                        size="small"
                        :total="practiceList.total"/>
        </div>


        <t-input @input.lazy="operationValue.page=1;initContex();" v-model.lazy="operationValue.search" size="small" style="width: 20%;float: right"
                 placeholder="输入内容"
                 clearable>
          <template #suffixIcon>
            <t-icon  :style="{ cursor: 'pointer' }" name="search"/>
          </template>
        </t-input>

      </div>
      <t-list style="height: 76vh;width: 100%" size="small" :split="true">
        <t-list-item v-for="(item, index) in practiceList.records">
          <t-list-item-meta image="/assets1/image/试卷.png" :title=" item.name"
                            :description="`做题时间：${  item.starttime}`">

            <template #description>
              <div style="width: 90vh;margin-top: -4px">
                <span  >
                  做题时间：{{ item.starttime}}
                </span>
<!--                 <t-progress style="width: 330px;display: inline-block" theme="plump" :percentage="30" />-->
              </div>

            </template>
          </t-list-item-meta>
          <template #action>
            <t-space :size="6">
              <t-button class="but_topic" @click="$router.push({
          name: 'practice-question',
          query: {method: 'again', pricaticeid: item.id}
         })">再做一遍</t-button>
              <t-button class="but_topic" @click="$router.push('/practice/history/'+item.id)">试题分析</t-button>
            </t-space>
             </template>

        </t-list-item>

      </t-list>

    </div>
</template>
<script setup>
import service from "@/axios/service";
import {reactive, ref} from "vue";


const practiceList = ref();

const operationValue = reactive({
  page: 1,
  search: ''
});



function initContex() {
  service.get(`/practice/page?page=${operationValue.page}&size=10&search=${operationValue.search}`).then(
      (res) => {
        practiceList.value = res.data;
        console.log(res);
      }
  )
}
initContex()


</script>
