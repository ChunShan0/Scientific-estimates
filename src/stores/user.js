import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const token = ref('')
  const id = ref(null)
  const username = ref('')
  const role = ref('')
  const realName = ref('')

  function setUser(user) {
    token.value = user.token
    id.value = user.id
    username.value = user.username
    role.value = user.role
    realName.value = user.realName
    
    // 保存到本地存储
    localStorage.setItem('user', JSON.stringify(user))
  }

  function clearUser() {
    token.value = ''
    id.value = null
    username.value = ''
    role.value = ''
    realName.value = ''
    
    // 清除本地存储
    localStorage.removeItem('user')
  }

  // 初始化时从本地存储加载用户信息
  const storedUser = localStorage.getItem('user')
  if (storedUser) {
    setUser(JSON.parse(storedUser))
  }

  return {
    token,
    id,
    username,
    role,
    realName,
    setUser,
    clearUser
  }
}) 