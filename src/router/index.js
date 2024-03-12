import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Encrypt from '../components/Encrypt.vue'
import UserMember from '../views/UserMember.vue'
import FileDownload from '../views/FileDownload.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/encrypt',
    name: 'Encrypt',
    component: Encrypt
  },
  {
    path: '/fileDownload',
    name: 'FileDownload',
    component: FileDownload
  },
  {
    path: '/user',
    name: 'UserMember',
    component: UserMember
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
