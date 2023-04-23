import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

import Manage from '../views/Manage.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/404',
        name: '404',
        component: () => import('../views/404.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export const setRoutes = ()  => {
    const storeMenus = localStorage.getItem("menus")
    if(storeMenus) {
        const menus = JSON.parse(storeMenus)
        const manageRoute = {path: '/', name: "Manage",component: Manage, redirect: "/login", children: []}
        menus.forEach(item => {
            if(item.path) {
                let itemMenu = {path: item.path.replace("/", ""), name: item.name,
                component: () => import ('../views/' + item.viewPath + '.vue')}
                manageRoute.children.push(itemMenu)
            }else if(item.children.length) {
                item.children.forEach(item => {
                    if(item.path) {
                        let itemMenu = {path: item.path.replace("/", ""), name: item.name,
                        component: () => import ('../views/' + item.viewPath + '.vue')}
                        manageRoute.children.push(itemMenu)
                    }
                })
            }
        })
        const currentRouteNames = router.getRoutes().map(v => v.name)
        if(!currentRouteNames.includes("Manage"))
        router.addRoute(manageRoute)
    }
}

setRoutes()

// 路由守卫
router.beforeEach((to, from, next) => {//to是要前往的路由对象，name属性定义在router.js中
    localStorage.setItem("currentPathName", to.name)
    store.commit("setPath")  // 触发store的数据更新
    if(!to.matched.length) {
        const storeMenus = localStorage.getItem("menus")
        if(storeMenus) {
            next('/404')  // 放行路由
        }else {
            next("/login")
        }
    }
    next()
  })

export default router
