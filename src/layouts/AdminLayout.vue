<template>
  <base-layout>
    <template #menu>
      <el-menu-item index="/admin/users">
        <el-icon><User /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
    </template>
  </base-layout>
</template>

<script setup>
import BaseLayout from './BaseLayout.vue'
import { User } from '@element-plus/icons-vue'
</script> 