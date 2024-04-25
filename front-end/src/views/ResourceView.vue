<script setup>
import {useCounterStore} from "@/stores/counter";
import {storeToRefs} from "pinia";
import SwitchGalahhad2 from "@/components/ui/SwitchGalahhad2.vue";
import {ref, watch} from "vue";

import service from "@/axios/service";
import dayjs from "dayjs";
import {useRouter} from "vue-router";
import {MessagePlugin} from "tdesign-vue-next";


const router = useRouter();
const value = ref(0);
const page = ref(1);
const total = ref(1);
const searchValue = ref('');
const imgs = ref([
  "http://browser9.qhimg.com/bdm/1024_768_85/t010824ab8b5cdfa138.jpg",
  "http://browser9.qhimg.com/bdm/1024_768_85/t01753453b660de14e9.jpg",
  "http://browser9.qhimg.com/bdm/1024_768_85/t01bbd94b90e850d1d3.jpg",
  "http://browser9.qhimg.com/bdm/1024_768_85/t019fd908f724f51900.jpg",
  "http://browser9.qhimg.com/bdm/1024_768_85/t010448c46c1ecf7cab.jpg",
  "http://browser9.qhimg.com/bdm/1024_768_85/t0183def7a3a7924215.jpg",
  "http://browser9.qhimg.com/bdm/1024_768_85/t016ad88ddaf2ae2d92.jpg",
  "http://browser9.qhimg.com/bdm/1024_768_85/t0179b947962a684673.jpg",
  "http://browser9.qhimg.com/bdm/1024_768_85/t01cd97ec806b712059.jpg",
  "http://browser9.qhimg.com/bdm/1024_768_85/t018160b069da5cac0d.jpg"
])
const askList = ref([]);
selectAll();

function selectAll() {
  service.get("/public/soft/page?page=" + page.value + "&search=" + searchValue.value + (value.value == 1 ? '&isHot=true' : '&isHot=false')).then((res) => {
    total.value = res.data.total;
    askList.value = res.data.records;
  })
}

const addDialog = ref(false);
const addInput = ref('');

function addAsk() {
  if (addInput.value.trim() == '') {
    MessagePlugin.error("请输入内容");
    addInput.value = ''
    return;
  }
  service.get("/public/soft/add?context=" + addInput.value).then((res) => {

  })
  setTimeout(() => {
    addDialog.value = false
    selectAll();
  }, 1000)


}


const store = useCounterStore();
const {search} = storeToRefs(store);


function toDetail(item) {
  sessionStorage.setItem("resource-" + item.id, JSON.stringify(item));
  router.push('/resource/' + item.id)
}

if (search.value) {
  setTimeout(() => {
    document.getElementById('input').focus();
    document.getElementById('search-input-container').style.transform = 'none';
  }, 200)
}
watch(search, (newValue) => {
  if (newValue) {
    setTimeout(() => {
      document.getElementById('input').focus();
      document.getElementById('search-input-container').style.transform = 'none';
    }, 100)
  }
})
watch(searchValue, (newValue) => {
  if (page.value === 1) {
    selectAll();
  } else {
    page.value = 1;
  }

})
watch(page, (newValue) => {
  selectAll();
})
watch(value, (newValue) => {
  if (page.value == 1) {
    selectAll();
  } else {
    page.value = 1
  }
})

function downLoadFile(url) {
  MessagePlugin.info("下载即将开始，请不要重复点击")

  let a = document.createElement('a')
  a.href = url;
  a.download = '文件名'
  a.click();
}
function sizeFormatting(now){
  if(now===0||now===null||now==""){
    return now
  }
  var unitArr = new Array("B","KB","MB","GB","TB","PB","EB","ZB","YB");
  var index=0,
      srcsize = parseFloat(now);
  index=Math.floor(Math.log(srcsize)/Math.log(1024));
  var size =srcsize/Math.pow(1024,index);
  //  保留的小数位数
  if(index!==0){
    size=size.toFixed(2);
  }
  return size+unitArr[index];
}
</script>

<template>
  <t-dialog :visible="addDialog" :close-btn="true" confirm-btn="提交" cancel-btn="取消"
            :onConfirm="addAsk"
            :on-close="()=>{addDialog=false}">
    <template #header>请求帮助</template>
    <template #body>
      <t-textarea
          style="margin-bottom: -10px"
          v-model="addInput"
          placeholder="请输入问题"
          name="description"
          :autosize="{ minRows: 1, maxRows: 5 }"
      />
    </template>
  </t-dialog>
  <t-drawer
      destroyOnClose
      v-model:visible="search"
      size="59px"
      :footer="null"
      :header="null"
      :on-overlay-click="() => (search = false)"
      placement="top"
      @cancel="search = false"
  >
    <div style="height: 10px">
      <img @click="$router.push('/')" style="cursor: pointer;   -webkit-user-select: none;-webkit-user-drag: none;"
           height="28" src="../assets/home_hm_left.png" alt="logo"/>
      <div @click="$router.push('/')" style=";display: inline-block;cursor: pointer; ;margin-left: 8px;
         -webkit-user-select: none;
      margin-right: -38px;
width: 100px;
                /*border: 1px solid red;*/
                text-indent:  -100px;
                overflow: hidden;">
        <img height="28"
             style="  -webkit-user-drag: none;;filter: drop-shadow(140px 0px var(--td-text-color-primary));margin-left: -40px"
             src="../assets/汉字创科.png" alt="logo"/>
      </div>
    </div>


    <div id="search-input-container" class="input-container" style="transition: all 300ms;">

      <input @keydown.enter="searchValue= $refs.searchInput.value;search=false" ref="searchInput" type="text" id="input"
             required="">
      <label for="input" class="label">Enter Text</label>
      <div class="underline"></div>
    </div>
    <t-button size="large" style="position: absolute;right: 12%;top: 12px;"
              @click="searchValue= $refs.searchInput.value;search=false" variant="text" shape="square">
      <template #icon>
        <t-icon style="font-size: 32px" name="search"/>
      </template>
    </t-button>
  </t-drawer>
  <div style="background-image: url('/assets1/image/back.jpg');height: 100vh;">
    <div :class="store.darkT?'boxdark':'box'" style=" height: 100vh;backdrop-filter: blur(10PX);padding-top:16px">

      <t-layout style="max-width: 1280px;margin:  0 auto;background-color: #0000">
        <t-content>

          <t-card style="border-radius: 12px;min-height: 90vh;width: 99%;" header-bordered>
            <template #header>
              <nut-tabs style="height: 40px;margin-top: -12px;margin-bottom: -10px" swipeable background="#00000000"
                        type="smile" v-model="value" title-scroll name="tabName">
                <nut-tab-pane style="background-color: #00000000" title="最新" pane-key="0">
                </nut-tab-pane>
                <nut-tab-pane style="background-color: #00000000" title="热门" pane-key="1">
                </nut-tab-pane>
                <nut-tab-pane disabled="true" pane-key="-10"/>
                <nut-tab-pane disabled="true" pane-key="-10"/>
                <nut-tab-pane disabled="true" pane-key="-10"/>
                <nut-tab-pane disabled="true" pane-key="-10"/>

              </nut-tabs>

              <div
                  style="cursor: pointer;background-color: var(--td-brand-color);border-radius:0 12px 0 0;position: absolute;top: 0;right: 0;height: 50px;padding-right: 20px">

                <div
                    style="height: 100%;display: inline-block;min-width: 20px;vertical-align: middle;border-radius: 0 20px 0 0;background-color: var(--td-bg-color-container);line-height: 48px;padding-right: 10px">
                  <div style="display: inline-block;float: right;width:200px;margin-left: 10px;margin-top: 12px">
                    <t-pagination :totalContent="false" v-model="page" theme="simple" :showPageSize="false" size="small"
                                  :total="total"/>
                  </div>

                  <t-tag closable @close="searchValue=''" shape="mark" variant="outline" theme="primary"
                         style="vertical-align: middle" v-if="searchValue!=''" @click="handleClickAdd">
                    <t-icon style="margin-left: -4px " name="search"/>
                    {{ searchValue }}
                  </t-tag>


                </div>

                <div
                    style="height: 100%;display: inline-block;width: 20px;vertical-align: middle; background-color:var(--td-bg-color-container)">
                  <div
                      style="height: 100%;display: inline-block;width: 20px;vertical-align: middle;border-radius: 0  0 0 20px;background-color:  var(--td-brand-color  )"></div>
                </div>


                <t-icon @click="search = true" style="font-size: 24px;color: white" name="search"/>

              </div>
            </template>
<!--{{askList}}-->
            <t-list :scroll="{ type: 'virtual' }" size="large" style="margin-top: -4px;max-height: 80vh" :split="true">
              <t-list-item @click="toDetail(item)"  v-for="item in askList">
                <t-list-item-meta style="padding-left: 30px" :image="item.icon" :title="item.name"
                                >
                  <template #title>
                    <div style="margin: -8px 0">
                      {{item.name}}
                    </div>
                  </template>
                  <template #description>
                    <div style=" width: 800px  ">
                      <nut-ellipsis cursor: pointer :content= "item.particulars" direction="end" rows="6" expand-text="展开" collapse-text="收起"></nut-ellipsis>

                    </div>
                    <div style="margin-top: 8px;margin-bottom: -8px">
                  <t-tag size="small" >
                    <t-icon name="animation" />

                    {{sizeFormatting(item.size)}}</t-tag>
                      <t-tag  size="small" style="margin-left: 6px" >
                        <t-icon name="cloud-download" />

                        {{item.quantity}} </t-tag>


                      <t-tag  size="small" style="margin-left: 6px" >
                        <t-icon name="time" />
                        {{
                          dayjs(item.addtime).format('YYYY-MM-DD HH:mm:ss')
                        }}
                        </t-tag>
                     </div>

                  </template>
                </t-list-item-meta>


                <template #action>
<div  style="margin-right: 60px;width: 160px">
  <t-button @click.stop="downLoadFile(item.downurl);
  service(
      '/public/soft/addDownload?id='+item.id
  )
" block variant="outline" shape="round"  >
    <template #icon><t-icon name="download" /></template>

    立即下载
  </t-button>
</div>

                </template>
              </t-list-item>

            </t-list>



            <div v-if="askList.length==0">
              <nut-empty description="暂时没有哦"></nut-empty>
            </div>

          </t-card>

        </t-content>
<!--        <t-aside :style="`background-image: linear-gradient(to bottom, #0000, #${store.darkT?'000000C9':'ffffffC9'} )`"-->
<!--                 style="border-radius: 12px; background-color: #0000; ">-->

<!--          <t-swiper style="border-radius: 12px  ;overflow: hidden" :height="100" class="tdesign-demo-block&#45;&#45;swiper"-->

<!--                    interval="8000"-->
<!--                    direction="vertical" :navigation="{ placement: 'outside',showSlideBtn:'never',size:'small' }"-->
<!--                    :duration="300" :interval="2000">-->
<!--            <t-swiper-item-->
<!--                style=";height: 100%;background-repeat: no-repeat;background-size: cover;background-position: center"-->
<!--                :style="`background-image: url('${item}')`"-->
<!--                v-for="item in imgs">-->
<!--              <div class="demo-item"></div>-->
<!--            </t-swiper-item>-->
<!--          </t-swiper>-->
<!--&lt;!&ndash;          <t-button shape="round" @click="addDialog=true" style="width: 96%;margin: 10px 2% -10px 2%">发起求助&ndash;&gt;-->
<!--&lt;!&ndash;          </t-button>&ndash;&gt;-->

<!--          <h4 style="padding-left: 8px;margin-bottom: 4px">热门问题</h4>-->
<!--          <div style="width: 80%;margin: 0 auto">-->
<!--            <nut-ellipsis-->
<!--                style="margin-top: 2px;cursor: pointer "-->
<!--                v-for="item in 6"-->
<!--                :content="item+'、NutUI3.0上线后我们研发团队也在不断的优化、测试、使用、迭代 Vue3 的相关组件，但是在跨端小程序的开发过程中，发现没有合适的组件库可以支持多端开发。为了填补这一空白，同时为了优化开发者体验，让 NutUI 能够为更多的开发者带来便利，我们决定在 NutUI 中增加小程序多端适配的能力。'"-->
<!--                direction="end"-->
<!--            ></nut-ellipsis>-->
<!--          </div>-->

<!--          <div @click="$router.push('/ai')"-->
<!--               style="cursor: pointer;margin-top: 20px;height: 100px;background-image: url('/assets1/image/c-aside-codegpt.jpg');background-repeat: no-repeat;background-size: cover;background-position: center  ;border-radius: 12px"></div>-->
<!--          <h4 style="padding-left: 8px">友情链接</h4>-->
<!--        </t-aside>-->
      </t-layout>


    </div>
  </div>
</template>

<style scoped>

.input-container {

  margin: 50px auto;
  position: absolute;
  left: 178px;
  top: -25px;
  width: 70%;
  transform: translateX(70vw) rotateY(80deg);
}

.input-container input[type="text"] {
  font-size: 20px;
  width: 100%;
  border: none;
  border-bottom: 2px solid #ccc;
  padding: 5px 0;
  background-color: transparent;
  outline: none;
}

.input-container .label {
  position: absolute;
  top: 0;
  left: 0;
  color: #ccc;
  transition: all .4s ease-in;
  pointer-events: none;
}

.input-container input[type="text"]:focus ~ .label,
.input-container input[type="text"]:valid ~ .label {
  top: -20px;
  font-size: 16px;
  color: #333;
}

.input-container .underline {
  position: absolute;
  bottom: 0;
  left: 0;
  height: 2px;
  width: 100%;
  background-color: var(--td-brand-color-7);
  transform: scaleX(0);
  transition: all 0.3s ease;
}

.input-container input[type="text"]:focus ~ .underline,
.input-container input[type="text"]:valid ~ .underline {
  transform: scaleX(1);
}

.box {
  //background-color:  rgba(var(--nut-badge-color),100%) ;
  background-color: rgba(255, 255, 255, 0.5);
  //opacity: 1;

}

.boxdark {
  background-color: rgba(0, 0, 0, 0.6);
}

.nut-tabs.horizontal .nut-sticky__box > .nut-tabs__titles .nut-tabs__titles-item, .nut-tabs.horizontal > .nut-tabs__titles .nut-tabs__titles-item {
  background-color: var(--td-bg-color-container) !important;
}

.nut-tabs__titles-item__text {
  color: var(--td-text-color-primary);
  font-size: larger;
  margin-bottom: 6px;
}

</style>