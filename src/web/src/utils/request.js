import axios from 'axios'
// import { MessageBox, Message } from 'element-ui'
import {Message} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'
import router from "@/router";

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  // baseURL: 'https://localhost:8443',
  // url = base url + request url
  // withCredentials: true,
  // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    if (store.getters.token) {
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    console.log(response)
    if (!res.success) {
      console.log(res)
      console.log(res.data)
      Message({
        message: res.data || 'Unknown Error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.data || 'Error'))
    } else {
      return res
    }
  },
  error => {
    //登录过期,跳转到登陆界面，登录后返回原先路由界面
    if (error.response.status === 401) {
      console.log(error.response)
      Message({
        message: "登录已过期，请重新登录",
        type: "error",
        duration: 5 * 1000
      })
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
    //其他错误，弹窗提示
    else {
      console.log('err' + error) // for debug
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service
