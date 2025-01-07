<template>
  <base-layout>
    <template #menu>
      <el-menu-item index="/evaluator/courses">
        <el-icon><Document /></el-icon>
        <span>待评估课程</span>
      </el-menu-item>
      <el-menu-item index="/evaluator/history">
        <el-icon><List /></el-icon>
        <span>评估历史</span>
      </el-menu-item>
    </template>
  </base-layout>
</template>

<script setup>
import BaseLayout from './BaseLayout.vue'
import { Document, List } from '@element-plus/icons-vue'
</script> 