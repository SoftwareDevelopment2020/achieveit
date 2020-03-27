/**
 * 常量
 */

/**
 * 项目状态
 */
const projectStatus = [{
  label: '已归档',
  svg: 'status-file'
}, {
  label: '审核中',
  svg: 'status-audit'
}, {
  label: '已驳回',
  svg: 'status-refuse'
}, {
  label: '进行中',
  svg: 'status-process'
}]

/**
 * 研发类型
 */
const projectType = [{
  label: '开发',
  value: 'D'
}, {
  label: '维护',
  value: 'M'
}, {
  label: '服务',
  value: 'S'
}, {
  label: '其他',
  value: 'O'
}]

export default {
  projectStatus,
  projectType
}
