<template>
  <div class="teacher-container">
    <el-container>
      <el-aside width="200px">
        <el-menu default-active="1">
          <el-menu-item index="1">
            <span>课程管理</span>
          </el-menu-item>
          <el-menu-item index="2">
            <span>评估记录</span>
          </el-menu-item>
          <el-menu-item index="3">
            <span>个人信息</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      
      <el-container>
        <el-header>
          <div class="header-content">
            <h2>教师管理系统</h2>
            <el-dropdown>
              <span class="user-info">
                用户名 <el-icon><arrow-down /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>个人信息</el-dropdown-item>
                  <el-dropdown-item>退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.teacher-container {
  height: 100vh;
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #ddd;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.user-info {
  cursor: pointer;
}

.el-aside {
  background-color: #304156;
  color: #fff;
}
</style> 