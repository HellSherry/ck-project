import axios from 'axios'
import {MessagePlugin} from "tdesign-vue-next";


const axios1 = axios.create({
    // baseURL: 'http://203c4f5.r19.cpolar.top',
    // baseURL: 'http://121.37.136.11:8081',
    baseURL: 'http://localhost:8081',
    // baseURL: store.BACKEND,
    timeout: 5000, // request timeout
})



axios1.interceptors.response.use(
    response => {
        console.log(response)

            return response

    },
    error => {
        console.log('接口信息报错' + error)
        return Promise.reject(error)
    },
)

export default axios1

