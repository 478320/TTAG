import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/products'
    },
    {
      path: '/login',
      component: () => import('@/views/Login.vue')
    },
    {
      path: '/reg',
      component: () => import('@/views/Register.vue')
    },
    {
      path: '/products',
      component: () => import('@/views/Products.vue'),
      children: [
        {
          path: '',
          component: () => import('@/views/ProductList.vue')
        },
        {
          path: 'detail/:id',
          component: () => import('@/views/ProductDetail.vue')
        }
      ]
    },
    {
      path: '/order',
      component: () => import('@/views/Order.vue')
    },
    {
      path: '/order-other',
      component: () => import('@/views/OrderOther.vue')
    },
    {
      path: '/me',
      component: () => import('@/views/Me.vue')
    },
    {
      path: '/me-products',
      component: () => import('@/views/MeProducts.vue')
    },
    {
      path: '/me-products/publish',
      component: () => import('@/views/PublishProduct.vue')
    },
    {
      path: '/admin',
      component: () => import('@/views/Admin.vue'),
      redirect: '/admin/farmer',
      children: [
        {
          path: 'farmer',
          component: () => import('@/views/AdminFarmer.vue')
        },
        {
          path: 'products',
          component: () => import('@/views/AdminProducts.vue')
        }
      ]
    }
  ]
})


export default router
