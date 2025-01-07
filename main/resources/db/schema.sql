-- 删除已存在的表（注意顺序）
DROP TABLE IF EXISTS sys_evaluation;
DROP TABLE IF EXISTS sys_course;
DROP TABLE IF EXISTS sys_user;

-- 用户表
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    role VARCHAR(20) NOT NULL COMMENT '角色(teacher/evaluator/admin)',
    real_name VARCHAR(50) COMMENT '真实姓名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    enabled BOOLEAN DEFAULT TRUE COMMENT '是否启用',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) COMMENT '用户表';

-- 课程表
CREATE TABLE sys_course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '课程ID',
    course_name VARCHAR(100) NOT NULL COMMENT '课程名称',
    course_code VARCHAR(50) NOT NULL COMMENT '课程代码',
    semester VARCHAR(20) NOT NULL COMMENT '学期',
    teacher_id BIGINT NOT NULL COMMENT '教师ID',
    status VARCHAR(20) NOT NULL COMMENT '状态(未评估/评估中/已评估)',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    FOREIGN KEY (teacher_id) REFERENCES sys_user(id)
) COMMENT '课程表';

-- 评估表
CREATE TABLE sys_evaluation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评估ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    evaluator_id BIGINT NOT NULL COMMENT '评估人ID',
    teaching_score INT COMMENT '教学水平评分',
    content_score INT COMMENT '课程内容评分',
    interaction_score INT COMMENT '师生互动评分',
    comments TEXT COMMENT '评价意见',
    status VARCHAR(20) NOT NULL COMMENT '状态(草稿/已提交)',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    FOREIGN KEY (course_id) REFERENCES sys_course(id),
    FOREIGN KEY (evaluator_id) REFERENCES sys_user(id)
) COMMENT '评估表';

-- 初始化用户数据
INSERT INTO sys_user (username, password, role, real_name, email, phone, enabled, create_time) VALUES
-- 管理员
('admin', '$2a$10$X/uMNuiis.fyO8pL2I4K3.4Y7Dh5oXZ9qtR.U7UzNqO.nY4n3Th86', 'admin', '系统管理员', 'admin@example.com', '13800000000', true, NOW()),
-- 教师
('teacher1', '$2a$10$X/uMNuiis.fyO8pL2I4K3.4Y7Dh5oXZ9qtR.U7UzNqO.nY4n3Th86', 'teacher', '张教授', 'zhang@example.com', '13800000001', true, NOW()),
('teacher2', '$2a$10$X/uMNuiis.fyO8pL2I4K3.4Y7Dh5oXZ9qtR.U7UzNqO.nY4n3Th86', 'teacher', '李教授', 'li@example.com', '13800000002', true, NOW()),
-- 评估人员
('evaluator1', '$2a$10$X/uMNuiis.fyO8pL2I4K3.4Y7Dh5oXZ9qtR.U7UzNqO.nY4n3Th86', 'evaluator', '王评估', 'wang@example.com', '13800000003', true, NOW()),
('evaluator2', '$2a$10$X/uMNuiis.fyO8pL2I4K3.4Y7Dh5oXZ9qtR.U7UzNqO.nY4n3Th86', 'evaluator', '刘评估', 'liu@example.com', '13800000004', true, NOW());

-- 等待1秒确保用户数据已插入
SELECT SLEEP(1);

-- 初始化课程数据
INSERT INTO sys_course (course_name, course_code, semester, teacher_id, status, create_time) VALUES
-- 张教授的课程（teacher1的ID是2）
('Java程序设计', 'CS101', '2024-spring', 2, '未评估', NOW()),
('数据结构', 'CS102', '2024-spring', 2, '评估中', NOW()),
-- 李教授的课程（teacher2的ID是3）
('数据库原理', 'CS201', '2024-spring', 3, '已评估', NOW()),
('软件工程', 'CS202', '2024-spring', 3, '评估中', NOW());

-- 等待1秒确保课程数据已插入
SELECT SLEEP(1);

-- 初始化评估数据
INSERT INTO sys_evaluation (course_id, evaluator_id, teaching_score, content_score, interaction_score, comments, status, create_time) VALUES
-- 数据结构课程的评估（evaluator1的ID是4，evaluator2的ID是5）
(2, 4, 90, 85, 88, '教学方法新颖，课程内容充实，但师生互动可以再加强', '已提交', NOW()),
(2, 5, 88, 90, 85, '整体表现优秀，建议增加实践环节', '已提交', NOW()),
-- 数据库原理课程的评估
(3, 4, 95, 92, 94, '教学效果显著，学生参与度高', '已提交', NOW()),
(3, 5, 93, 90, 92, '课程设计合理，教学目标明确', '已提交', NOW()),
-- 软件工程课程的评估
(4, 4, 85, 88, 86, '理论与实践结合得当', '草稿', NOW());

