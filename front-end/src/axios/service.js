import axios from 'axios'
import {MessagePlugin} from "tdesign-vue-next";

import pinia from '@/stores/index.js';
import { useCounterStore } from '@/stores/counter';



 const service = axios.create({
    // baseURL: 'http://203c4f5.r19.cpolar.top',
    //  baseURL: 'http://121.37.136.11:1985',
     baseURL: 'http://localhost:8080',
    // baseURL: useCounterStore(pinia).BACKEND,
    timeout: 5000, // request timeout
})

service.interceptors.request.use(
    config => {
        if (localStorage.getItem("token")!=null) {
            config.headers['token'] =localStorage.getItem("token")
        }
        return config
    },
    error => {
        console.log(error) // for debug
        return Promise.reject(error)
    },
)

service.interceptors.response.use(
    response => {
        const res = response.data
        console.log(res.code)
        if (res.code !== 200) {
            console.log('接口信息报错',res.message)
            MessagePlugin.error('用户登录异常')
             if ("5173/"!=window.location.href.substring(window.location.href.length-5,window.location.href.length)){
                // window.location.href="/"
            }
             return Promise.reject(new Error(res.message || 'Error'))
        } else {
            return res
        }
    },
    error => {
        console.log('接口信息报错' + error)
        return Promise.reject(error)
    },
)

export default service

