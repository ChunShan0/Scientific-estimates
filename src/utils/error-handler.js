import { ElMessage } from 'element-plus'

export const errorHandler = (error) => {
  if (error.response) {
    const status = error.response.status
    const message = error.response.data.message || '请求失败'
    
    switch (status) {
      case 400:
        ElMessage.error('请求参数错误')
        break
      case 401:
        ElMessage.error('未授权，请重新登录')
        break
      case 403:
        ElMessage.error('拒绝访问')
        break
      case 404:
        ElMessage.error('请求错误，未找到该资源')
        break
      case 500:
        ElMessage.error('服务器内部错误')
        break
      default:
        ElMessage.error(message)
    }
  } else {
    ElMessage.error('网络错误，请稍后重试')
  }
} 