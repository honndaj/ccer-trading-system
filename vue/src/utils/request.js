import axios from 'axios'
import ElementUI from 'element-ui'

const request = axios.create({
    baseURL: 'http://localhost:80',
    timeout: 5000
})

//处理发出的request
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null
    if(user) {
        config.headers['token'] = user.token;
    }
    return config
}, error => {
    return Promise.reject(error)
});

// 处理返回的response
request.interceptors.response.use(
    response => {
        let res = response.data;
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        if (res.code === '401') {
            ElementUI.Message({
                message: res.msg,
                type: 'error'
            });
        }
        return res;
    },
    error => {
        console.log('err' + error)
        return Promise.reject(error)
    }
)


export default request

