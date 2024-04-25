import './assets/main.css'
import TDesign from 'tdesign-vue-next';
import { createApp } from 'vue'
import 'tdesign-vue-next/es/style/index.css';
import App from './App.vue'
import router from './router'
import NutUI from "@nutui/nutui";
import "@nutui/nutui/dist/style.css";
import 'vue-cropper/dist/index.css'
import pinia from './stores'

const app = createApp(App)
app.use(NutUI);
app.use(TDesign);
app.use(pinia)
app.use(router)
import JsonExcel from "vue-json-excel3";
app.component("downloadExcel", JsonExcel);
app.mount('#app')
