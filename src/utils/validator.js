export const validators = {
  required: (message) => ({
    required: true,
    message: message || '此项为必填项',
    trigger: 'blur'
  }),
  
  length: (min, max, message) => ({
    min,
    max,
    message: message || `长度应在${min}到${max}个字符之间`,
    trigger: 'blur'
  }),
  
  email: (message) => ({
    type: 'email',
    message: message || '请输入正确的邮箱格式',
    trigger: ['blur', 'change']
  })
} 