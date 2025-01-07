<template>
  <base-layout>
    <template #menu>
      <el-menu-item index="/teacher/courses">
        <el-icon><Document /></el-icon>
        <span>课程管理</span>
      </el-menu-item>
      <el-menu-item index="/teacher/evaluations">
        <el-icon><Star /></el-icon>
        <span>评估记录</span>
      </el-menu-item>
    </template>
  </base-layout>
</template>

<script setup>
import BaseLayout from './BaseLayout.vue'
import { Document, Star } from '@element-plus/icons-vue'
</script> 