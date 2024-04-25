import {ref, computed, watch, nextTick} from 'vue'
import {defineStore} from 'pinia'
import {DialogPlugin, MessagePlugin} from "tdesign-vue-next";
import axios from "axios";
import service from "@/axios/service";
import {useWebNotification} from "@vueuse/core";
// export const useCounterStore = defineStore('counter',{
//   state:()=>({
//     darkT:false
// })
// })
export const useCounterStore = defineStore('counter', () => {
    // const BACKEND = '121.37.136.11:1985';
    const BACKEND = 'localhost:8080';
    const count = ref(0)
    const animate = ref([false,false])
    const examList = ref([])
    const search = ref(false)
    // let notifications = useWebNotification({
    //     title: "sdf",
    //     dir: 'auto',
    //     lang: 'en',
    //     renotify: true,
    //     tag: 'test',
    // })
    const showNotifi = (titleTemp) => {
        useWebNotification({
            title: titleTemp,
            dir: 'auto',
            lang: 'en',
            renotify: true,
            tag: 'test',
        }).show()
        ;
    }
    const getExamList = () => {
        service.get("/exam/getExamList").then((res) => {
            // console.log(res.data)
            examList.value = res.data

            // let paper = typeof res.data === 'string' ? JSON.parse(res.data) : res.data
            // var papers = [];
            // Object.keys(paper).forEach((key) => {
            //   papers.push({v: paper[key], k: key});
            // });
            // console.log(papers)
        })
    }
    const loginUser = ref(null)
    const darkT = ref(localStorage.getItem('theme-mode') === 'dark')
    if (darkT.value) {
        document.documentElement.setAttribute('theme-mode', 'dark');
    }
    const doubleCount = computed(() => count.value * 2)

    function getWsStatus() {
        return socket == null || socket.readyState === WebSocket.CLOSED;
    }

    function increment() {
        count.value++
    }

    watch(darkT, (value) => {
        if (!value) {
            document.documentElement.setAttribute('theme-mode', 'light');
            localStorage.setItem('theme-mode', 'light');
        } else {
            document.documentElement.setAttribute('theme-mode', 'dark');
            localStorage.setItem('theme-mode', 'dark');
        }
    })


    let lockReconnect = false;
    let socket;

    function websocketReconnect() {
        console.log(111)
        if (lockReconnect) {       // 是否已经执行重连
            return;
        }
        lockReconnect = true;
        //没连接上会一直重连，设置延迟避免请求过多
        tt && clearTimeout(tt);
        var tt = setTimeout(function () {

            socket = null
            socket = new WebSocket(`ws://${BACKEND}/ws/controller/${loginUser.value.sub.userId}`, [token]);

            lockReconnect = false;
            MessagePlugin.error('正在重连');
        }, 2000)
    }

    window.addEventListener("online", () => {

        MessagePlugin.success('网络已连接');
        if (socket != null) {
            console.log('socket')
            socket = null;
            newWS(localStorage.getItem('token'))
            // socket  = new WebSocket(`ws://${BACKEND}/ws/controller/${loginUser.value.sub.userId}`,[localStorage.getItem('token')]);
        } else {
            // window.location.reload();
        }
    })
    window.addEventListener("offline", () => {
        MessagePlugin.error('网络已断开');
        if (socket != null) socket.onclose();

        // setInterval(()=> {
        //   websocketReconnect();
        // },1000)
        // websocketReconnect();
        // socket=o


    })
    const axiosInstance = axios.create({
        baseURL: `http://${BACKEND}`, // 设置基本的 API 地址
        headers: {
            'token': localStorage.getItem("token") // 在 Authorization 头部中添加 token
        }
    });

    function newWS(token) {
        if (socket == null || socket.readyState === WebSocket.CLOSED) {
            socket = new WebSocket(`ws://${BACKEND}/ws/controller/${loginUser.value.sub.userId}`, [token]);
            socket.onopen = function () {
                setInterval(() => {
                    if (socket != null) {
                        console.log(socket.readyState);
                    }

                }, 1000);

                // Web Socket 已连接上，使用 send() 方法发送数据
                MessagePlugin.success('用户连接成功')
                setTimeout(() => {
                    loginUser.value.sub.online = true;
                }, 100)


                socket.onclose = function () {
                    loginUser.value.sub.online = false;
                }
            };
            socket.onclose = function () {
                socket = null;
                MessagePlugin.error('用户登录失败')
                localStorage.removeItem("token")
                loginUser.value = null
            };

            socket.onmessage = function (evt) {
                console.log('收到消息：' + evt.data)
                switch (evt.data) {
                    case "loginOut":
                        socket.onclose = function () {
                            socket = null;
                            MessagePlugin.error('用户登录已失效')
                            localStorage.removeItem("token")
                            loginUser.value = null
                        }
                        break;
                    case "CloseOther":
                        MessagePlugin.error('已断开用户在其他页面连接')
                        break;

                    case "NewExam":
                        showNotifi("你有一场新的考试")
                        MessagePlugin.info('你有一场新的考试')
                        break;
                    case "ConClose":

                        animate.value[0]=true;
                        animate.value[1]=true;
                        setTimeout(()=>{
                            animate.value[1]=false
                        },1000)
                        nextTick(() => {
                            animate.value[0]=false;
                        });
                        const confirmDia = DialogPlugin({
                            header: '用户在其他位置建立连接',
                            body: '此操作不会注销你的登录状态，\n' + '你仍然可以在此页面进行基础功能访问，\n' + '但无法接收消息，无法参加考试',
                            placement: "center",
                            confirmBtn: 'ok',
                            cancelBtn: 'cancel',
                            onConfirm: ({e}) => {
                                console.log('confirm button has been clicked!');
                                console.log('e: ', e);
                                confirmDia.hide();
                            },
                            onClose: ({e, trigger}) => {
                                console.log('e: ', e);
                                console.log('trigger: ', trigger);
                                confirmDia.hide();
                            },
                        });
                        break;
                    default:
                        switch (evt.data.substring(0, evt.data.indexOf(':'))) {
                            case 'newReply':
                                animate.value[0]=true;
                                animate.value[1]=true;
                                setTimeout(()=>{
                                    animate.value[1]=false
                                },1000)
                                nextTick(() => {
                                    animate.value[0]=false;
                                });
                                const {show,onClick} = useWebNotification({
                                    title: "有人回复了你的问题，快去看看吧",
                                    dir: 'auto',
                                    lang: 'en',
                                    renotify: true,
                                    tag: 'test',
                                });
                                onClick(()=> {
                                    window.location.href='/ask/'+evt.data.substring(evt.data.indexOf(':')+1);

                                })
                                show()

                                break;
                            case 'message':

                                animate.value[0]=true;
                                animate.value[1]=true;
                                setTimeout(()=>{
                                    animate.value[1]=false
                                },1000)
                                nextTick(() => {
                                    animate.value[0]=false;
                                });
                                const confirmDia = DialogPlugin({
                                    header: '通知',
                                    body:evt.data.substring(evt.data.indexOf(':')+1) ,
                                    cancelBtn: null,
                                    onConfirm: ({e}) => {
                                         confirmDia.hide();
                                    }
                                });
                                showNotifi("有一条新的通知")
                                break;

                            case 'addExam':

                                break;
                        }
                        break;
                }
                // if (evt.data=="loginOut"){
                //   socket.onclose=function () {
                //     socket=null;
                //     MessagePlugin.error('用户登录已失效')
                //     localStorage.removeItem("token")
                //     loginUser.value=null
                //   }
                // }

            }
        } else {
            MessagePlugin.error('用户已经登录')

        }
    }

    return {
        animate,
        search,
        showNotifi,
        examList,
        getExamList,
        axiosInstance,
        BACKEND,
        getWsStatus,
        newWS,
        loginUser,
        darkT,
        count,
        doubleCount,
        increment
    }
})