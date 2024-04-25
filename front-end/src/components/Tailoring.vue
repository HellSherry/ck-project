<template>

  <div class="cut">
    <vue-cropper ref="cropper" :img="option.img" :output-size="option.size" :output-type="option.outputType" :info="true" :full="option.full" :fixed="fixed" :fixed-number="fixedNumber"
                 :can-move="option.canMove" :can-move-box="option.canMoveBox" :fixed-box="option.fixedBox" :original="option.original"
                 :auto-crop="option.autoCrop" :auto-crop-width="option.autoCropWidth" :auto-crop-height="option.autoCropHeight" :center-box="option.centerBox"
                 @real-time="realTime" :high="option.high"
                 @img-load="imgLoad"   :max-img-size="option.max" @crop-moving="cropMoving" mode="cover"></vue-cropper>
  </div>
  <div class="show-preview" :style="{'width': previews.w + 'px', 'height': previews.h + 'px',  'overflow': 'hidden', 'margin': '5px'}">
    <div :style="previews.div">
      <img :src="previews.url" :style="previews.img">
    </div>
  </div>
  <div class="test-button">
    <button @click="changeImg" class="btn">changeImg</button>
    <label class="btn" for="uploads">upload</label>
    <input type="file" id="uploads" style="position:absolute; clip:rect(0 0 0 0);" accept="image/png, image/jpeg, image/gif, image/jpg"
           @change="uploadImg($event, 1)">
    <button @click="startCrop" v-if="!crap" class="btn">start</button>
    <button @click="stopCrop" v-else class="btn">stop</button>
    <button @click="clearCrop" class="btn">clear</button>
    <button @click="refreshCrop" class="btn">refresh</button>
    <button @click="changeScale(1)" class="btn">+</button>
    <button @click="changeScale(-1)" class="btn">-</button>
    <button @click="rotateLeft" class="btn">rotateLeft</button>
    <button @click="rotateRight" class="btn">rotateRight</button>
    <button @click="finish('base64')" class="btn">preview(base64)</button>
    <button @click="finish('blob')" class="btn">preview(blob)</button>
    <a @click="down('base64')" class="btn">download(base64)</a>
    <a @click="down('blob')" class="btn">download(blob)</a>
    <a @click="() => option.img = ''" class="btn">清除图片</a>
    <div style="display:block; width: 100%;">
      <label class="c-item">
        <span>上传图片是否显示原始宽高 (针对大图 可以铺满)</span>
        <input type="checkbox" v-model="option.original">
        <span>original: {{ option.original}}</span>
      </label>
      <label class="c-item">
        <span>能否拖动图片</span>
        <input type="checkbox" v-model="option.canMove">
      </label>
      <label class="c-item">
        <span>能否拖动截图框</span>
        <input type="checkbox" v-model="option.canMoveBox">
        <span>canMoveBox: {{ option.canMoveBox}}</span>
      </label>
      <label class="c-item">
        <span>截图框固定大小</span>
        <input type="checkbox" v-model="option.fixedBox">
        <span>fixedBox: {{ option.fixedBox}}</span>
      </label>
      <label class="c-item">
        <span>是否输出原图比例的截图</span>
        <input type="checkbox" v-model="option.full">
        <span>full: {{ option.full}}</span>
      </label>
      <label class="c-item">
        <span>是否自动生成截图框</span>
        <input type="checkbox" v-model="option.autoCrop">
        <span>autoCrop: {{ option.autoCrop}}</span>
      </label>
      <label class="c-item">
        <span>是否根据dpr生成适合屏幕的高清图片</span>
        <input type="checkbox" v-model="option.high">
        <span>high: {{ option.high}}</span>
      </label>
      <label class="c-item">
        <span>截图框是否限制在图片里(只有在自动生成截图框时才能生效)</span>
        <input type="checkbox" v-model="option.centerBox">
        <span>centerBox: {{ option.centerBox}}</span>
      </label>
      <label class="c-item">
        <p>输出图片格式</p>
        <label>jpg
          <input type="radio" name="type" value="jpeg" v-model="option.outputType">
        </label>
        <label>png
          <input type="radio" name="type" value="png" v-model="option.outputType">
        </label>
        <label>webp
          <input type="radio" name="type" value="webp" v-model="option.outputType">
        </label>
      </label>
    </div>
  </div>
</template>

<script  >
import { VueCropper }  from "vue-cropper";
export default {
  components: {
    VueCropper
  },
  data () {
    return {
      model: false,
      modelSrc: '',
      crap: false,
      previews: {},
      lists: [
        {
          img: 'https://avatars2.githubusercontent.com/u/15681693?s=460&v=4',
        },
      ],
      option: {
        img: 'https://avatars2.githubusercontent.com/u/15681693?s=460&v=4',
        size: 1,
        full: false,
        outputType: 'png',
        canMove: true,
        fixedBox: false,
        original: false,
        canMoveBox: true,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 750,
        autoCropHeight: 340,
        centerBox: true,
        high: true,
        max: 99999,
      },
      show: true,
      fixed: false,
      fixedNumber: [75, 34],
    }
  },
   methods: {
    changeImg() {
      this.option.img = this.lists[~~(Math.random() * this.lists.length)].img;
    },
    startCrop() {
      // start
      this.crap = true;
      this.$refs.cropper.startCrop();
    },
    stopCrop() {
      //  stop
      this.crap = false;
      this.$refs.cropper.stopCrop();
    },
    clearCrop() {
      // clear
      this.$refs.cropper.clearCrop();
    },
    refreshCrop() {
      // clear
      this.$refs.cropper.refresh();
    },
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    finish(type) {
      // 输出
      // var test = window.open('about:blank')
      // test.document.body.innerHTML = '图片生成中..'
      if (type === 'blob') {
        this.$refs.cropper.getCropBlob((data) => {
          console.log(data);
          var img = window.URL.createObjectURL(data);
          this.model = true;
          this.modelSrc = img;
        });
      } else {
        this.$refs.cropper.getCropData((data) => {
          this.model = true;
          this.modelSrc = data;
        });
      }
    },
    // 实时预览函数
    realTime(data) {
      this.previews = data;
      console.log(data);
    },

    finish2(type) {
      this.$refs.cropper2.getCropData((data) => {
        this.model = true;
        this.modelSrc = data;
      });
    },
    finish3(type) {
      this.$refs.cropper3.getCropData((data) => {
        this.model = true;
        this.modelSrc = data;
      });
    },
    down(type) {
      // event.preventDefault()
      var aLink = document.createElement('a');
      aLink.download = 'demo';
      // 输出
      if (type === 'blob') {
        this.$refs.cropper.getCropBlob((data) => {
          this.downImg = window.URL.createObjectURL(data);
          aLink.href = window.URL.createObjectURL(data);
          aLink.click();
        });
      } else {
        this.$refs.cropper.getCropData((data) => {
          this.downImg = data;
          aLink.href = data;
          aLink.click();
        });
      }
    },

    uploadImg(e, num) {
      //上传图片
      // this.option.img
      var file = e.target.files[0];
      if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
        alert('图片类型必须是.gif,jpeg,jpg,png,bmp中的一种');
        return false;
      }
      var reader = new FileReader();
      reader.onload = (e) => {
        let data;
        if (typeof e.target.result === 'object') {
          // 把Array Buffer转化为blob 如果是base64不需要
          data = window.URL.createObjectURL(new Blob([e.target.result]));
        } else {
          data = e.target.result;
        }
        if (num === 1) {
          this.option.img = data;
        } else if (num === 2) {
          this.example2.img = data;
        }
      };
      // 转化为base64
      // reader.readAsDataURL(file)
      // 转化为blob
      reader.readAsArrayBuffer(file);
    },
    imgLoad(msg) {
      console.log(msg);
    },
    cropMoving(data) {
      console.log(data, '截图框当前坐标');
    },
  },
  mounted: function() {
    // console.log(window['vue-cropper'])
  },

}
</script>

<style scoped>

</style>

