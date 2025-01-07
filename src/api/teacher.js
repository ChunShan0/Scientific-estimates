import api from './index'

export const teacherApi = {
  // 获取课程列表
  getCourses: () => api.get('/teacher/courses'),
  
  // 获取评估记录
  getEvaluations: (courseId) => api.get(`/teacher/evaluations/${courseId}`),
  
  // 更新个人信息
  updateProfile: (data) => api.put('/teacher/profile', data)
} 