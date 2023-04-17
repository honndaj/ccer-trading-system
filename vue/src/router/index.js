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
        path: '/',
        name: 'Manage',
        component: Manage,
        redirect: "/home",
        children: [
            {
                path: 'home', name: "主页", component:() => import("../views/Home.vue")
            },
            {
                path: 'user', name: "用户管理", component:() => import("../views/User.vue")
            },
            {
                path: 'role', name: "角色管理", component:() => import("../views/Role.vue")
            },
            {
                path: 'menu', name: "菜单管理", component:() => import("../views/Menu.vue")
            },
            {
                path: '/person', name: '个人信息', component: () => import('../views/Person.vue')
            },
        ]
    },
    {
        path: '/about',
        name: 'About',
        component: () => import('../views/About.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {//to是要前往的路由对象，name属性定义在router.js中
    localStorage.setItem("currentPathName", to.name)
    store.commit("setPath")  // 触发store的数据更新
    next()  // 放行路由
  })

export default router
