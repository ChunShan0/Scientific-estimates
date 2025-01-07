<template>
  <div class="pending-evaluations">
    <div class="header">
      <h3>待评估课程</h3>
    </div>

    <el-table v-loading="loading" :data="courses" border>
      <el-table-column prop="courseName" label="课程名称" />
      <el-table-column prop="courseCode" label="课程代码" />
      <el-table-column prop="semester" label="学期" />
      <el-table-column prop="teacherName" label="授课教师" />
      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button 
            type="primary" 
            :disabled="row.status === '已评估'"
            @click="handleEvaluate(row)"
          >
            {{ row.status === '已评估' ? '查看评估' : '开始评估' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 评估对话框 -->
    <el-dialog
      title="课程评估"
      v-model="dialogVisible"
      width="600px"
    >
      <el-form
        ref="evaluationFormRef"
        :model="evaluationForm"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="教学水平评分" prop="teachingScore">
          <el-rate
            v-model="evaluationForm.teachingScore"
            :max="100"
            show-score
          />
        </el-form-item>
        <el-form-item label="课程内容评分" prop="contentScore">
          <el-rate
            v-model="evaluationForm.contentScore"
            :max="100"
            show-score
          />
        </el-form-item>
        <el-form-item label="师生互动评分" prop="interactionScore">
          <el-rate
            v-model="evaluationForm.interactionScore"
            :max="100"
            show-score
          />
        </el-form-item>
        <el-form-item label="评价意见" prop="comments">
          <el-input
            v-model="evaluationForm.comments"
            type="textarea"
            :rows="4"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button @click="handleSaveDraft">保存草稿</el-button>
        <el-button type="primary" @click="handleSubmit">提交评估</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const loading = ref(false)
const courses = ref([])
const dialogVisible = ref(false)
const evaluationFormRef = ref(null)
const currentCourse = ref(null)

const evaluationForm = reactive({
  teachingScore: 0,
  contentScore: 0,
  interactionScore: 0,
  comments: ''
})

const rules = {
  teachingScore: [{ required: true, message: '请评分', trigger: 'change' }],
  contentScore: [{ required: true, message: '请评分', trigger: 'change' }],
  interactionScore: [{ required: true, message: '请评分', trigger: 'change' }],
  comments: [{ required: true, message: '请填写评价意见', trigger: 'blur' }]
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
    const response = await fetch('/api/courses/pending')
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

// 开始评估
const handleEvaluate = (row) => {
  currentCourse.value = row
  dialogVisible.value = true
  // 如果有草稿，加载草稿数据
  loadDraftEvaluation(row.id)
}

// 加载草稿评估
const loadDraftEvaluation = async (courseId) => {
  try {
    const response = await fetch(`/api/evaluations/draft/${courseId}`)
    const data = await response.json()
    if (data.code === 200 && data.data) {
      Object.assign(evaluationForm, data.data)
    }
  } catch (error) {
    console.error('加载草稿失败', error)
  }
}

// 保存草稿
const handleSaveDraft = async () => {
  try {
    const response = await fetch('/api/evaluations', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        ...evaluationForm,
        courseId: currentCourse.value.id,
        evaluatorId: userStore.id,
        status: '草稿'
      })
    })
    
    const data = await response.json()
    if (data.code === 200) {
      ElMessage.success('保存草稿成功')
      dialogVisible.value = false
      fetchCourses()
    }
  } catch (error) {
    ElMessage.error('保存草稿失败')
  }
}

// 提交评估
const handleSubmit = async () => {
  if (!evaluationFormRef.value) return
  
  await evaluationFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const response = await fetch('/api/evaluations', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            ...evaluationForm,
            courseId: currentCourse.value.id,
            evaluatorId: userStore.id,
            status: '已提交'
          })
        })
        
        const data = await response.json()
        if (data.code === 200) {
          ElMessage.success('提交评估成功')
          dialogVisible.value = false
          fetchCourses()
        }
      } catch (error) {
        ElMessage.error('提交评估失败')
      }
    }
  })
}

onMounted(() => {
  fetchCourses()
})
</script>

<style scoped>
.pending-evaluations {
  padding: 20px;
}

.header {
  margin-bottom: 20px;
}

.el-rate {
  margin-top: 8px;
}
</style> 