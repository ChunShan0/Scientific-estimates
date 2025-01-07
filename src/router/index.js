import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/teacher',
      component: () => import('@/layouts/TeacherLayout.vue'),
      meta: { requiresAuth: true, role: 'teacher' },
      children: [
        {
          path: 'courses',
          name: 'TeacherCourses',
          component: () => import('@/views/teacher/CourseManagement.vue')
        },
        {
          path: 'evaluations',
          name: 'TeacherEvaluations',
          component: () => import('@/views/teacher/EvaluationRecords.vue')
        }
      ]
    },
    {
      path: '/evaluator',
      component: () => import('@/layouts/EvaluatorLayout.vue'),
      meta: { requiresAuth: true, role: 'evaluator' },
      children: [
        {
          path: 'courses',
          name: 'EvaluatorCourses',
          component: () => import('@/views/evaluator/PendingEvaluations.vue')
        },
        {
          path: 'history',
          name: 'EvaluationHistory',
          component: () => import('@/views/evaluator/EvaluationHistory.vue')
        }
      ]
    },
    {
      path: '/admin',
      component: () => import('@/layouts/AdminLayout.vue'),
      meta: { requiresAuth: true, role: 'admin' },
      children: [
        {
          path: 'users',
          name: 'UserManagement',
          component: () => import('@/views/admin/UserManagement.vue')
        }
      ]
    }
  ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  if (to.meta.requiresAuth) {
    if (!userStore.token) {
      next('/login')
    } else if (to.meta.role && to.meta.role !== userStore.role) {
      next('/login')
    } else {
      next()
    }
  } else {
    if (userStore.token && to.path === '/login') {
      // 已登录用户访问登录页，重定向到对应的首页
      switch (userStore.role) {
        case 'teacher':
          next('/teacher/courses')
          break
        case 'evaluator':
          next('/evaluator/courses')
          break
        case 'admin':
          next('/admin/users')
          break
        default:
          next('/login')
      }
    } else {
      next()
    }
  }
})

export default router 