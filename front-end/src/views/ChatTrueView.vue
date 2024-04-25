<script setup>
import {ref} from "vue";

const activeItem = ref();
const testValue = ref();
</script>

<template>
  <t-layout style=" height: 82vh;border-radius: 6px;overflow: auto">
    <t-aside style="height:  82vh;overflow: hidden">
      <t-menu v-model="activeItem"   style="">

        <t-menu-item :value="i" v-for="i in 100" style="height: 60px">
          <template #icon>
            <div style="margin-left: -12px; display: inline-block;height: 40px;width: 48px ">
              <!--        <t-avatar style=""  size="40px"> {{ store.loginUser.sub.username.substring(0, 1)}} </t-avatar>-->
              <t-avatar  size="40px" :image="'https://v2.api-m.com/api/head?return=302&sf='+i" :hide-on-load-failed="false" />
            </div>
            <!--      <img style="width: 40px" src="@/assets/image/aa.png" alt="">-->
          </template>
          <div style=" display: inline-block;  color: var(--td-text-color-primary);line-height: 8px;padding-top: 12px ">
            用户{{i}}
            <p style="font-weight: 1">sadf</p>
          </div>
        </t-menu-item>
      </t-menu>
    </t-aside>

    <t-content style="display: flex;flex-flow: column;">
      <t-layout>
        <t-header style="height: 48px;line-height: 48px;padding-left: 16px">用户
{{activeItem}}
        </t-header>
        <t-content style="height: 100%;display: flex;flex-flow: column;">
          
          <div style="flex: 1;">
            敬请期待？？？
          </div>
          <t-card size="small" style="border-radius:0;min-height: 12vh; ">
            <nut-textarea v-model="testValue" :rows="1" autosize />

          </t-card>

        </t-content>

      </t-layout>

    </t-content>
  </t-layout>
</template>

<style scoped>

</style>