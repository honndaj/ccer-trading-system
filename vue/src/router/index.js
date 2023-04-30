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
        menus.forEach(menu => {
            if(menu.menuRoute) {
                let parentMenu = {path: menu.menuRoute.replace("/", ""), name: menu.menuName,
                component: () => import ('../views/' + menu.viewPath + '.vue')}
                manageRoute.children.push(parentMenu)
            }else if(menu.children.length) {
                menu.children.forEach(menu => {
                    if(menu.menuRoute) {
                        let childMenu = {path: menu.menuRoute.replace("/", ""), name: menu.menuName,
                        component: () => import ('../views/' + menu.viewPath + '.vue')}
                        manageRoute.children.push(childMenu)
                    }
                })
            }
        })
        if(!router.getRoutes().map(v => v.name).includes("Manage"))
        router.addRoute(manageRoute)
    }
}

setRoutes()

router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)
    store.commit("setPath")
    if(!to.matched.length) {
        const storeMenus = localStorage.getItem("menus")
        if(storeMenus) {
            next('/404')
        }else {
            next("/login")
        }
    }
    next()
  })

export default router
