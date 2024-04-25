<script setup>
import {computed, reactive, ref, watch} from "vue";
import service from "@/axios/service";
import {useCounterStore} from "@/stores/counter";
import dayjs from "dayjs";
import {DialogPlugin, MessagePlugin} from "tdesign-vue-next";

const store = useCounterStore();
const form = ref();
const tableData = ref({
  "records": [
    {
      "id": 1,
      "title": "这是一条通知",
      "details": "这是通知详情",
      "addtime": "2024-01-10T11:15:42.000+00:00"
    }
  ],
  "total": 1,
  "size": 10,
  "current": 1,
  "pages": 1
});
const operationValue = reactive({
  useIconUrl: true,
  useImagesUrl: true,
  useUrl: true,

  isUpdate: false,
  selectedRowKeys: [],
  visible: false,
  page: 1,
  insertStep: 0,
  file: [],
  iconFile: [],
  imagesFile: [],
  search: '',
  pojo: {
    name: '',
    particulars: '',
    images: [''],
    icon: '',
    company: '',
    size: '',
    quantity: '',
    downurl: '',
    addtime: ''
  }
});


function initContex() {
  service.get(`/public/soft/page?page=${operationValue.page}&size=10&search=${operationValue.search}`).then(
      (res) => {
        tableData.value = res.data;
        console.log(res);
        tableData.value.records.forEach((item) => {
          item.images = JSON.parse(item.images)
        })
      }
  )
}

initContex()

async function beforeSubmit() {
  if (typeof await form.value.validate() === 'boolean') {
    let temPojo = {...operationValue.pojo}
    temPojo.images = JSON.stringify(temPojo.images)
    service.post(`/public/soft/${operationValue.isUpdate ? 'update' : 'insert'}`, temPojo).then((res) => {
      console.log(11121)
      console.log(res)
      initContex()
      if (res.data > 0) {
        MessagePlugin.success((operationValue.isUpdate ? "修改" : "新增") + "成功")
      } else {
        MessagePlugin.error((operationValue.isUpdate ? "修改" : "新增") + "失败")
      }
      operationValue.visible = false;
    })
  } else {

  }
}

function toDeleteInBulk() {
  if (operationValue.selectedRowKeys.length > 0) {
    const confirmDia = DialogPlugin({
      header: '提示',
      body: '您确认删除所选 ' + operationValue.selectedRowKeys.length + ' 项数据吗？',
      confirmBtn: {
        content: '删除',
        theme: 'danger',
      },
      cancelBtn: {content: '取消', variant: 'outline'},
      onConfirm: ({e}) => {
        deletes(operationValue.selectedRowKeys)
        confirmDia.hide();
      }
    });
  } else {
    MessagePlugin.error("请选择要删除的项")
  }

}

function deletes(id) {
  service.post(`/public/soft/delete`, typeof id === 'object' ? id : [id]).then((res) => {
    console.log(res)
    initContex()
    if (res.data > 0) {
      MessagePlugin.success("删除成功")
    } else {
      MessagePlugin.error("删除失败")
    }

  })
}

let getFileSizeByUrl = computed((url) => {
  if (url == null || url == '') {
    return 0
  }

  service(`/public/soft/getSizeByUrl?fileUrl=${url}`).then(
      res => {
        return res.data
      }
  )
  return 0
})

function getCurrentDate(needTime = false) {
  const d = new Date();
  let month = d.getMonth() + 1;
  month = month < 10 ? Number(`0${month}`) : month;
  const date = `${d.getFullYear()}-${month}-${d.getDate()}`;
  const time = `${d.getHours()}:${d.getMinutes()}:${d.getSeconds()}`;
  if (needTime) return [date, time].join(' ');
  return date;
}

function formatResponse(res) {
  // 响应结果添加上传时间字段，用于 UI 显示
  res.uploadTime = getCurrentDate();
  return res;
}

function formatImgResponse(res) {
  // console.log(res)
  if (res.error) {

    res.url = 'https://tdesign.gtimg.com/site/avatar.jpg'

    res.status = 'fail'
    return res;
  }

  res.url = "https://avatar.sherry.games" + res[0].src
  res.status = 'success'
  return res;
}

function initImgUrl() {
  console.log('lsdfljfdsl')
  if (operationValue.imagesFile.length === 0) {
    operationValue.pojo.images = ['']
    return
  }
  operationValue.pojo.images = []

  operationValue.imagesFile.forEach(
      (item) => {
        operationValue.pojo.images.push(item.url)
      }
  )
}

function initIconUrl() {
  if (operationValue.iconFile.length === 0) {
    operationValue.pojo.icon = ''
    return
  }
  operationValue.pojo.icon = operationValue.iconFile[0].url
}

function initDownUrl() {
  if (operationValue.file.length === 0) {
    operationValue.pojo.downurl = ''
    return
  }
  operationValue.pojo.downurl = operationValue.file[0].url
}

function initImgFile() {
  operationValue.imagesFile = []
  operationValue.pojo.images.forEach((item) => {
    if (item !== '')
      operationValue.imagesFile.push({url: item})
  })
}

function initIconFile() {
  if (operationValue.pojo.icon) {
    operationValue.iconFile = [{url: operationValue.pojo.icon}]
  }
}

function initDownFile() {
  if (operationValue.pojo.downurl) {
    operationValue.file = [{
      url: operationValue.pojo.downurl,
      name: operationValue.pojo.downurl.substring(operationValue.pojo.downurl.lastIndexOf('/') + 1, operationValue.pojo.downurl.length),
      size: operationValue.pojo.size,
      "type": "image/png",
      "percent": 100,
      "status": "success",
      "uploadTime": dayjs(operationValue.pojo.addtime).format("YYYY-MM-DD HH:mm:ss"),
    }]

  }
}

watch(() => operationValue.pojo.icon, (value) => {
  initIconFile();
})
watch(() => operationValue.pojo.downurl, (value) => {
  initDownFile();
})

function initSize() {
  if (operationValue.pojo.downurl == null || operationValue.pojo.downurl == '') {
    operationValue.pojo.size = 0
  } else


    service(`/public/soft/getSizeByUrl?fileUrl=${operationValue.pojo.downurl}`).then(
        res => {
          operationValue.pojo.size = res.data
        }
    )

}

function sizeFormatting(now) {
  if (now === 0 || now === null || now == "") {
    return now
  }
  var unitArr = new Array("B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB");
  var index = 0,
      srcsize = parseFloat(now);
  index = Math.floor(Math.log(srcsize) / Math.log(1024));
  var size = srcsize / Math.pow(1024, index);
  //  保留的小数位数
  if (index !== 0) {
    size = size.toFixed(2);
  }
  return size + unitArr[index];
}
</script>

<template>
  <div style="position: relative;height: 102vh">
    <t-dialog

        placement="center"
        v-model:visible="operationValue.visible"
        :confirm-btn="{
          content: '下一步',
          variant: 'outline',
        }"
        width="640px"
        @confirm="()=>{operationValue.insertStep++}"
    >

      <template #header>
        <span style="float: left;user-select: none">{{ operationValue.isUpdate ? '修改试题' : '创建试题' }}</span>

        <t-steps id="setpicon"
                 style="text-decoration:none;width: 320px;margin:0 auto;letter-spacing:1px;user-select: none"
                 class="bbb" v-model="operationValue.insertStep">
          <t-step-item title="编辑信息"/>
          <t-step-item title="上传文件"/>

        </t-steps>


      </template>
      <div style="min-height: 100px" slot="body">

        <t-form v-if="operationValue.insertStep === 0"
                layout="inline" ref="form" :rules="{
  name: [{required: true, message: '标题不能为空'}],
  quantity: [{required: true, message: '标题不能为空'}]
}" :data="operationValue.pojo" :colon="true">
          <t-form-item style="width: 96%;margin: 8px 0" label="软件名" name="name">
            <t-input v-model="operationValue.pojo.name" placeholder="请输入内容"></t-input>
          </t-form-item>
          <t-form-item style="width: 96%;margin: 8px 0" label="发布者" name="company">
            <t-input v-model="operationValue.pojo.company" placeholder="请输入内容"></t-input>
          </t-form-item>
          <t-form-item style="width: 96%;margin: 8px 0" label="详细介绍" name="particulars">
            <t-textarea :autosize="{ minRows: 1,maxRows: 10 }" v-model="operationValue.pojo.particulars"
                        placeholder="请输入内容"></t-textarea>
          </t-form-item>

        </t-form>
        <t-form v-else
                layout="inline" ref="form" :rules="{
  name: [{required: true, message: '标题不能为空'}],
  quantity: [{required: true, message: '标题不能为空'}]
}" :data="operationValue.pojo" :colon="true">


          <t-form-item style="width: 96%;margin: 8px 0" label="图标" name="icon">
            <t-input v-show="operationValue.useIconUrl" v-model="operationValue.pojo.icon" placeholder="请输入图标链接">
              <template #suffixIcon>
                <div style="height: 24px;width: 24px;margin-right: -4px;border-radius: 2px;overflow: hidden  ">
                  <img style="height: 100%;object-fit:cover " :src="operationValue.pojo.icon" alt="">
                </div>
              </template>
            </t-input>
            <t-upload v-if="!operationValue.useIconUrl"
                      v-model="operationValue.iconFile"
                      :auto-upload="true"
                      theme="image"
                      @success="initIconUrl"
                      @remove=" initIconUrl()"
                      :show-image-file-name="false"
                      :data="{ extra_data: 123, file_name: 'certificate' }"
                      :abridge-name="[10, 8]"
                      :format-response="formatImgResponse"
                      action="https://avatar.sherry.games/upload"
            />
          </t-form-item>
          <t-form-item
              v-show="operationValue.useImagesUrl"
              v-for="(item,index) in operationValue.pojo.images" style="width: 96%;margin: 8px 0"
              :label="'详情图片 '+(index+1)" name="images">
            <t-input
                @input="initImgFile()"
                v-model="operationValue.pojo.images[index]" placeholder="请输入内容"

            >
              <template #suffixIcon>
                <div style="height: 24px;width: 24px;margin-right: -4px;border-radius: 2px;overflow: hidden  ">
                  <img style="height: 100%;object-fit:cover " :src="operationValue.pojo.images[index]" alt="">
                </div>
              </template>
            </t-input>

            <div style="margin-left: 6px">
              <t-button
                  @click="initImgFile(); operationValue.pojo.images.length>1?operationValue.pojo.images.splice(index,1):$message('warning', '需要至少保留一个选项')"
                  variant="outline" shape="circle"
              >
                <t-icon name="close"/>
              </t-button>
            </div>
            <div style="margin-left: 6px">
              <t-button @click=" initImgFile(); operationValue.pojo.images.push('')" shape="circle"
              >
                <t-icon name="add"/>
              </t-button>
            </div>
          </t-form-item>
          <t-form-item v-if="!operationValue.useImagesUrl" style="width: 96%;margin: 8px 0" label="详细图片"
                       name="images">
            <t-upload

                v-if="!operationValue.useImagesUrl"
                v-model="operationValue.imagesFile"
                placeholder="支持批量上传图片文件"
                theme="image"
                accept="image/*"
                multiple
                action="https://avatar.sherry.games/upload"
                @success="initImgUrl"
                @remove="initImgUrl"
                :auto-upload="true"
                :show-image-file-name="false"
                :format-response="formatImgResponse"
                :abridge-name="[6, 6]"
                :upload-button="false ? undefined : null"
                :cancel-upload-button="true ? { theme: 'default', content: '取消上传' } : null"
            ></t-upload>

          </t-form-item>
          <t-form-item style="width: 96%;margin: 8px 0" label="资源" name="downurl">


            <t-input v-show="operationValue.useUrl" v-model="operationValue.pojo.downurl"
                     @input="initSize()"
                     placeholder="请输入资源下载链接">
              <template #suffixIcon>
                <t-tag size="small">
                  <t-icon name="animation"/>

                  {{ sizeFormatting(operationValue.pojo.size) }}
                </t-tag>
                <!--<div style="height: 24px;width: 24px;margin-right: -4px;border-radius: 2px;overflow: hidden  ">-->
                <!--  {{sizeFormatting(operationValue.pojo.size)  }}-->
                <!--&lt;!&ndash;  <img style="height: 100%;object-fit:cover "  :src="operationValue.pojo.downurl" alt="">&ndash;&gt;-->
                <!--</div>-->
              </template>

            </t-input>

            <t-upload v-if="!operationValue.useUrl"
                      v-model="operationValue.file"
                      @remove="initDownUrl()"
                      @success="initDownUrl()"
                      :auto-upload="true"
                      :data="{ extra_data: 123, file_name: 'certificate' }"
                      :abridge-name="[10, 8]"
                      :format-response="formatResponse"
                      draggable
                      action="https://service-bv448zsw-1257786608.gz.apigw.tencentcs.com/api/upload-demo"
            />
          </t-form-item>


          <div style="position: absolute;bottom: 26px;left: 60px;text-align: center;user-select: none;cursor: pointer">
            <t-link style="line-height: 16px;margin-right: 10px"
                    @click="initIconFile();operationValue.useIconUrl=!operationValue.useIconUrl" theme="primary"
                    size="small">图标 <br> {{ operationValue.useIconUrl ? '使用文件' : '使用下载链接' }}
            </t-link>
            <t-link style="line-height: 16px;margin-right: 10px"
                    @click="initImgFile();operationValue.useImagesUrl=!operationValue.useImagesUrl" theme="primary"
                    size="small">详情图片 <br> {{ operationValue.useImagesUrl ? '使用文件' : '使用下载链接' }}
            </t-link>
            <t-link style="line-height: 16px; "
                    @click="operationValue.useUrl=!operationValue.useUrl" theme="primary"
                    size="small"> 资源 <br> {{ operationValue.useUrl ? '使用文件' : '使用下载链接' }}
            </t-link>
          </div>


        </t-form>

      </div>
      <template #footer>
        <div v-if="operationValue.insertStep>0">
          <t-button ghost @click="operationValue.insertStep--" theme="primary">
            上一步
          </t-button>
          <t-button @click="beforeSubmit" theme="primary">
            完成
          </t-button>
        </div>

      </template>

    </t-dialog>


    <div style="background-position: center"
         :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover;min-height: 46vh ;margin-top: -20px`">

    </div>
    <t-card :bordered="false" hoverShadow style="width: 96%;margin: 0 auto;margin-top: -30px;min-height: 58vh">
      <!--      {{ searchForm }}-->
      <div>
        <t-space :size="6">
          <!--          <button @click="getFileSizeByUrl"></button>-->
          <t-button size="small" @click="operationValue.pojo={
    name: '',
    particulars: '',
    images: [''],
    icon: '',
    company: '',
    size: '',
    quantity: '',
    downurl: '',
    addtime: ''
  }; operationValue.insertStep=0;operationValue.visible=true;operationValue.isUpdate=false;$refs.form.reset()"
                    theme="primary">
            <template #icon>
              <t-icon name="add"/>
            </template>
            新建
          </t-button>
          <t-button size="small" @click="toDeleteInBulk" theme="danger">
            <template #icon>
              <t-icon name="delete"/>
            </template>
            删除
          </t-button>
          <t-tag variant="light-outline" theme="primary" shape="round" size="small">
            共有{{ tableData.total }}条记录
          </t-tag>
        </t-space>


        <div style="display: inline-block;float: right;width:200px;margin-left: 10px">

          <t-pagination :totalContent="false" @change="initContex" v-model="operationValue.page" theme="simple"
                        :showPageSize="false"
                        size="small"
                        :total="tableData.total"/>
        </div>


        <t-input @input.lazy="operationValue.page=1;initContex();" v-model.lazy="operationValue.search" size="small"
                 style="width: 20%;float: right"
                 placeholder="输入内容"
                 clearable>
          <template #suffixIcon>
            <t-icon :style="{ cursor: 'pointer' }" name="search"/>
          </template>
        </t-input>

      </div>

      <t-table
          size="small"
          row-key="id"
          v-model:selected-row-keys="operationValue.selectedRowKeys"
          :data="tableData.records"
          :columns="[
           { colKey: 'row-select', type: 'multiple' ,width: 60,},
                    { colKey: 'icon', title: '图标',width: '100px'},
    { colKey: 'id', title: 'ID'},
            { colKey: 'name', title: '名称',ellipsis: true,},
            { colKey: 'particulars', title: '详细介绍',ellipsis: true,},
            { colKey: 'images', title: '图片',ellipsis: {disabled: true} },
            { colKey: 'size', title: '大小',ellipsis: true,},
            { colKey: 'quantity', title: '下载量',ellipsis: true,},
              // { colKey: 'paperDescription', title: 'paperDescription'},
             { colKey: 'addtime', title: '创建时间'},
            {
    title: '操作',
    colKey: 'operation',
      }
      ]"
      >

        <template #icon="{ row }">
          <t-image-viewer v-model:visible="row.iconVisable" draggable :images="[row.icon]">
            <template #trigger>
              <div class="tdesign-demo-image-viewer__ui-image">
                <img alt="test" :src="row.icon" class="tdesign-demo-image-viewer__ui-image--img"/>
                <div class="tdesign-demo-image-viewer__ui-image--hover" @click="row.iconVisable=true">
                  <span>预览</span>
                </div>
              </div>
            </template>
          </t-image-viewer>

        </template>
        <template #images="{ row }">


          <t-image-viewer v-model:visible="row.imageVisable" :images="row.images">
            <template #trigger>

              <t-avatar-group class="images_group" style="position: relative;padding-left: 54px"
                              cascading="left-up">
                <t-avatar :style="'height:'+(46-index*2)+'px;width:'+(64-index*2)+'px'"
                          style="margin-left: -54px;border-radius: 6px"
                          :imageProps="{style:{width:'120px',height:'100px',objectFit:'contain'}}"
                          v-for="(i,index) in row.images" :key="index" size="large" shape="round" :image="i"/>
                <div class="images_group_div" @click="row.imageVisable=true">
                  <t-icon style="margin-right: 4px" name="browse"/>
                  <span>预览</span>
                </div>
              </t-avatar-group>


            </template>
          </t-image-viewer>


        </template>
        <template #addtime="{ row }">
          {{ dayjs(row.addtime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template #size="{ row }">
          {{ sizeFormatting(row.size) }}
        </template>
        <template #operation="{ row }">


          <t-dropdown

              @click="(data)=>{
                operationValue.useUrl=operationValue.useImagesUrl=operationValue.useIconUrl=true;
                operationValue.insertStep=data.value;
                operationValue.visible=true;operationValue.isUpdate=true;operationValue.pojo={...row}
              }"
              :options="[{ content: '修改信息', value: 0 }, { content: '修改文件', value: 1 }]"
          >
            <t-link style="margin-right: 4px"
                    @click="operationValue.visible=true;operationValue.isUpdate=true;operationValue.pojo={...row};operationValue.useUrl=operationValue.useImagesUrl=operationValue.useIconUrl=true"
                    theme="primary" hover="color">
              修改
            </t-link>&nbsp;
          </t-dropdown>

          <t-popconfirm theme="danger" :confirmBtn="{theme: 'danger'}" @confirm="deletes(row.id)"

                        :cancelBtn="{variant: 'outline'}" content="确认删除吗">
            <t-link theme="danger" hover="color">
              删除
            </t-link>

          </t-popconfirm>


        </template>

      </t-table>

    </t-card>
  </div>

</template>

<style scoped>
.images_group:hover .images_group_div {
  opacity: 1;
  cursor: pointer;
}

.images_group_div {
  transition: all 0.2s;
  opacity: 0;
  color: white;
  background-color: rgba(0, 0, 0, 0.44);
  position: absolute;
  display: flex;
  padding-left: 20px;
  align-items: center;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 50
}

.tdesign-demo-image-viewer__ui-image {
  width: 40px;
  height: 40px;
  display: inline-flex;
  position: relative;
  justify-content: center;
  align-items: center;
  border-radius: var(--td-radius-small);
  overflow: hidden;
}

.tdesign-demo-image-viewer__ui-image--hover {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 0;
  top: 0;
  opacity: 0;
  background-color: rgba(0, 0, 0, 0.6);
  color: var(--td-text-color-anti);
  line-height: 22px;
  transition: 0.2s;
}

.tdesign-demo-image-viewer__ui-image:hover .tdesign-demo-image-viewer__ui-image--hover {
  opacity: 1;
  cursor: pointer;
}

.tdesign-demo-image-viewer__ui-image--img {
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  cursor: pointer;
  position: absolute;
}

.tdesign-demo-image-viewer__ui-image--footer {
  padding: 0 16px;
  height: 56px;
  width: 100%;
  line-height: 56px;
  font-size: 16px;
  position: absolute;
  bottom: 0;
  color: var(--td-text-color-anti);
  background-image: linear-gradient(0deg, rgba(0, 0, 0, 0.4) 0%, rgba(0, 0, 0, 0) 100%);
  display: flex;
  box-sizing: border-box;
}

.tdesign-demo-image-viewer__ui-image--title {
  flex: 1;
}

.tdesign-demo-popup__reference {
  margin-left: 16px;
}

.tdesign-demo-image-viewer__ui-image--icons .tdesign-demo-icon {
  cursor: pointer;
}

.tdesign-demo-image-viewer__base {
  width: 160px;
  height: 160px;
  margin: 10px;
  border: 4px solid var(--td-bg-color-secondarycontainer);
  border-radius: var(--td-radius-medium);
}


/*fffffffffffffffffffffffffffffffffffff*/


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