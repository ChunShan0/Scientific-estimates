<template>
  <div class="course-management">
    <div class="header">
      <h3>课程管理</h3>
      <el-button type="primary" @click="handleAddCourse">添加课程</el-button>
    </div>

    <el-table v-loading="loading" :data="courses" border>
      <el-table-column prop="courseName" label="课程名称" />
      <el-table-column prop="courseCode" label="课程代码" />
      <el-table-column prop="semester" label="学期" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleViewEvaluations(row)">
            查看评估
          </el-button>
          <el-button type="primary" link @click="handleEdit(row)">
            编辑
          </el-button>
          <el-popconfirm title="确定删除该课程吗？" @confirm="handleDelete(row)">
            <template #reference>
              <el-button type="danger" link>删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑课程对话框 -->
    <el-dialog
      :title="dialogType === 'add' ? '添加课程' : '编辑课程'"
      v-model="dialogVisible"
      width="500px"
    >
      <el-form
        ref="courseFormRef"
        :model="courseForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="courseForm.courseName" />
        </el-form-item>
        <el-form-item label="课程代码" prop="courseCode">
          <el-input v-model="courseForm.courseCode" />
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-select v-model="courseForm.semester" placeholder="请选择学期">
            <el-option label="2024春季" value="2024-spring" />
            <el-option label="2024秋季" value="2024-fall" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const courses = ref([])
const dialogVisible = ref(false)
const dialogType = ref('add')
const courseFormRef = ref(null)

const courseForm = reactive({
  courseName: '',
  courseCode: '',
  semester: ''
})

const rules = {
  courseName: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  courseCode: [{ required: true, message: '请输入课程代码', trigger: 'blur' }],
  semester: [{ required: true, message: '请选择学期', trigger: 'change' }]
}

const getStatusType = (status) => {
  switch (status) {
    case '未评估':
      return 'info'
    case '评估中':
      return 'warning'
    case '已评估':
      return 'success'
    default:
      return 'info'
  }
}

// 获取课程列表
const fetchCourses = async () => {
  try {
    loading.value = true
    const response = await fetch(`/api/courses/teacher/${userStore.id}`)
    const data = await response.json()
    if (data.code === 200) {
      courses.value = data.data
    }
  } catch (error) {
    ElMessage.error('获取课程列表失败')
  } finally {
    loading.value = false
  }
}

// 添加课程
const handleAddCourse = () => {
  dialogType.value = 'add'
  dialogVisible.value = true
  courseForm.courseName = ''
  courseForm.courseCode = ''
  courseForm.semester = ''
}

// 编辑课程
const handleEdit = (row) => {
  dialogType.value = 'edit'
  dialogVisible.value = true
  Object.assign(courseForm, row)
}

// 查看评估
const handleViewEvaluations = (row) => {
  router.push(`/teacher/course/${row.id}/evaluations`)
}

// 删除课程
const handleDelete = async (row) => {
  try {
    await fetch(`/api/courses/${row.id}`, { method: 'DELETE' })
    ElMessage.success('删除成功')
    fetchCourses()
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!courseFormRef.value) return
  
  await courseFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const method = dialogType.value === 'add' ? 'POST' : 'PUT'
        const url = dialogType.value === 'add' 
          ? '/api/courses'
          : `/api/courses/${courseForm.id}`
        
        const response = await fetch(url, {
          method,
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            ...courseForm,
            teacherId: userStore.id
          })
        })
        
        const data = await response.json()
        if (data.code === 200) {
          ElMessage.success(dialogType.value === 'add' ? '添加成功' : '更新成功')
          dialogVisible.value = false
          fetchCourses()
        }
      } catch (error) {
        ElMessage.error(dialogType.value === 'add' ? '添加失败' : '更新失败')
      }
    }
  })
}

onMounted(() => {
  fetchCourses()
})
</script>

<style scoped>
.course-management {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
</style> 