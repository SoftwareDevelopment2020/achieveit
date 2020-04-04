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

/**
 * 项目基本信息约束
 */
const projectRules = {
  clientId: [
    {required: true, message: '客户ID不能为空', trigger: 'blur'},
    {min: 4, max: 4, message: '客户ID为4位', trigger: 'blur'}
  ],
  name: [
    {required: true, message: '项目名称不能为空', trigger: 'blur'}
  ],
  superior: [
    {required: true, message: '项目上级不能为空', trigger: 'blur'}
  ],
  majorMilestone: [
    {required: true, message: '采用技术不能为空', trigger: 'blur'}
  ],
  mainTechnique: [
    {required: true, message: '主要里程碑不能为空', trigger: 'blur'}
  ],
  businessField: [
    {required: true, message: '业务领域不能为空', trigger: 'blur'}
  ],
  mainFunction: [
    {required: true, message: '主要功能不能为空', trigger: 'blur'}
  ]
}

/**
 * 角色
 */
const roles = [{
  name: 'ROLE_PM',
  detail: '项目经理'
}, {
  name: 'ROLE_DEVLEADER',
  detail: '开发Leader'
}, {
  name: 'ROLE_DEV',
  detail: '开发'
}, {
  name: 'ROLE_TESTLEADER',
  detail: '测试Leader'
}, {
  name: 'ROLE_TEST',
  detail: '测试'
}, {
  name: 'ROLE_CONFIG',
  detail: '配置管理员'
}, {
  name: 'ROLE_EPG',
  detail: 'EPG'
}, {
  name: 'ROLE_QA',
  detail: 'QA'
}]

const permissions = [{
  name: 'git',
  detail: 'git'
}, {
  name: 'filesys',
  detail: '文件系统'
}, {
  name: 'mail',
  detail: '邮件系统'
}, {
  name: 'manhour',
  detail: '登记工时'
}, {
  name: 'bug',
  detail: '缺陷跟踪管理'
}]

const deviceConditions = [{
  value: true,
  label: '完好'
}, {
  value: false,
  label: '损坏'
}]

const isReturned = [{
  value: true,
  label: '已归还'
}, {
  value: false,
  label: '未归还'
}]

const riskType=[{
  id:'1',
  value:'需求风险'
},{
  id:'2',
  value:'计划和控制风险'
},{
  id:'3',
  value:'技术风险'
},{
  id:'4',
  value:'用户风险'
},{
  id:'5',
  value:'团队风险'
},{
  id:'6',
  value:'外部风险'
},{
  id:'7',
  value:'组织风险'
},{
  id:'8',
  value:'合同风险'
}]


export default {
  projectStatus,
  projectType,
  projectRules,
  roles,
  permissions,
  deviceConditions,
  isReturned,
  riskType
}
