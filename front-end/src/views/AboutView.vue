
<template>
  <div style="background-position: center"
       :style="`background-image: url('${store.darkT? `/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`:`/assets1/image/mobileback/th (${Math.round(Math.random()*30)}).jpg`}');background-repeat: no-repeat;background-size: cover; margin-top: -20px `">
    <div :class="store.darkT?'boxdark':'box'"
         style=" min-height: 100vh;backdrop-filter: blur(20px);padding-top: 2px;">
      <div  :class="store.darkT?'boxdark':'box'"  style="width: 90%;margin: 0 auto">

        <h2 style="font-size: 50px;text-align: center;font-family: 'Candara Light';padding-top: 46px">ABOUT US</h2>

        <t-divider style="border-color: var(--td-text-color-primary)" dashed/>
        <div style="font-size: 14px;vertical-align: top;display: inline-block;width: 40%;margin-left: 6%;margin-right: 3.5%">
          <h1 style="font-family: '楷体';font-size: 26px" class="section-title">公司简介</h1>
          <p>
            10086gdf创科教育是一家专注于教育信息化建设与服务的企业，我们致力于研发创新、高效的教育管理系统，帮助各类教育机构提升管理效率，优化教学过程，实现教育质量的持续改进。
          </p>
          <p>
            自成立以来，凭借深厚的技术积累和行业经验，我们的产品和服务已经覆盖全国数千家学校和教育机构，深受广大师生和管理者的好评。
          </p>
        </div>

        <div style="font-size: 14px;display: inline-block;width: 40%;margin-left: 4%;margin-right: 4%">
          <h1 style="font-family: '楷体';font-size: 26px" class="section-title">我们的使命与愿景</h1>
          <p>
            我们的使命是利用先进的信息技术推动教育公平，赋能教育变革，让每一位学子都能享受到优质、个性化的教育资源。
          </p>
          <p>
            我们的愿景是成为全球领先的教育信息化解决方案提供商，构建智慧教育生态，助力中国乃至全球教育事业的发展。
          </p>
        </div>

        <h2 style="font-size: 50px;text-align: center;font-family: 'Candara Light';margin: 56px 0">THE TEAM</h2>
        <t-divider style="border-color: var(--td-text-color-primary)" dashed/>
<div style="margin: 0 auto;width: 90%">
  <t-row :gutter="16">
    <t-col v-for="i in a" :span="3">
      <div :class="store.darkT?'boxdark':'box'" style="margin-bottom: 8px;padding: 20px 10px;border-radius: 8px" >
<!--        <div style="width: 100px;margin: 0 auto">-->
<!--          <t-avatar shape="round" size="100px" :image-props="{-->
<!--            fit: 'cover'-->
<!--          }" :image="i.img"> W </t-avatar>-->

<!--        </div>-->
        <div style="margin: 0 auto;width: 130px;background-color: rgba(145,145,145,0.13);border-radius: 50%">
          <t-avatar

              :image-props="{
            fit: 'cover'
          }"
              v-bind:image="i.img"
              style="margin: 4px;border: 2px solid rgba(150,150,150,0.2)" size="120px">
           </t-avatar>

        </div>
        <h4 style="text-align: center;font-size: 24px;font-family: '楷体';margin-top: 10px;margin-bottom: -2px;font-weight: lighter">
          {{ i.name }}</h4>
        <p style="word-wrap: break-word;
        width: 90%;margin: 10px auto;
	word-break: break-all;">
          &emsp;{{i.content}}


        </p>
      </div>
    </t-col>

  </t-row>

</div>
        <t-divider style="border-color: var(--td-text-color-primary)" dashed/>
<div style="position: relative ;height: 300px;overflow: hidden;margin-top: -24px">

<!--  <div :class="store.darkT?'boxdark':'box'" style="position: absolute;width: 100%;height: 100%">-->
<!--     -->
<!--  </div>-->
  <iframe style="margin-top: -10px  " src="https://www.jq22.com/demo/jquery-xiaxue" width="100%" height="400px" frameborder="0">


  </iframe>

</div>
      </div>
    </div>
  </div>

</template>

<style scoped>
.box {
  background-color: rgba(255, 255, 255, .6);
}
.tem {
  background-color: rgba(0, 0, 0, .2);
}
.boxdark {
  background-color: rgba(0, 0, 0, .48);
}
</style>
<script setup>
 import {useCounterStore} from "@/stores/counter";

 const store = useCounterStore();
 const a = [
   {
     name: '汤凯旋',
     content: '深谙Java开发之道，在解决难题时游刃有余，团队合作也是他的强项，是团队技术支柱之一。',
     img: '/assets1/image/about/1 (1).jpg'
   },
   {
     name: '陈戊烽',
     content: '富有激情，拥有丰富的Java开发经验。善于创新，推动团队不断进步，是我们团队的技术推动者。',
     img: '/assets1/image/about/1 (5).jpg'
   },
   {
     name: '沈耀豪',
     content: '稳重可靠，擅长项目管理。对代码高效规范，对团队发展有着清晰的愿景，是团队中的重要骨干之一。',
     img: '/assets1/image/about/1 (3).jpg'
   },
   {
     name: '李紫姨',
     content: '细心严谨，对代码质量要求极高。在项目中负责关键模块的开发，确保项目的稳定性和可靠性。',
     img: '/assets1/image/about/1 (7).jpg'
   },
   {
     name: '帅明',
     content: '创造力十足，架构设计能力出众。常为团队带来新的思路和灵感，是团队中不可或缺的核心成员之一。',
     img: '/assets1/image/about/1 (9).jpg'
   },
   {
     name: '李磊',
     content: '工作认真负责，对Java技术了如指掌。解决问题时快速而准确，是团队中不可或缺的一员。',
     img: '/assets1/image/about/ast.jpg'
   },
   {
     name: '李一帆',
     content: '勇于挑战，敢于突破技术难关。对新技术有较强的吸收能力，为团队带来新的活力和动力。',
     img: '/assets1/image/about/cjxy.jpg'
   },
   {
     name: '李雯',
     content: '沟通协调能力强，工作细致周到。负责需求分析和功能设计，确保项目顺利推进。',
     img: '/assets1/image/about/1 (2).jpg'
   },
   {
     name: '殷川',
     content: '具备深谋远虑，对项目方向把握精准。为团队提供宝贵的指导和建议，是团队的智囊之一。',
     img: '/assets1/image/about/gms.jpg'
   },
   {
     name: '李成量',
     content: '勤奋努力，对技术有着强烈的热情。不断探索新技术，为团队带来更多可能性和机遇。',
     img: '/assets1/image/about/qyye.jpg'
   },
   {
     name: '唐章波',
     content: '灵活应变，善于解决问题。在项目中经验丰富，能够迅速应对各种挑战，为团队保驾护航。',
     img: '/assets1/image/about/stzp.jpg'
   },
   {
     name: '李锋',
     content: '勇于挑战，对技术有着强烈的热情。不断探索新技术，工作细致周到，是团队中不可或缺的一员。',
     img: '/assets1/image/about/1 (8).jpg'
   }
 ];

</script>