/**
 * 工具类
 */

/**
 * 设置表格数据
 */
export function setTable(data, table) {
  table.data = data.records
  table.total = data.total
  table.page = data.current
  table.limit = data.size
}



/**
 * 设置项目信息页面表格数据
 */
export function setBaseInfoTable(form, data) {
  console.log(data)
  form.projectId = data.projectId
  form.name = data.name
  form.scheduledDate = data.scheduledDate
  form.deliveryDate = data.deliveryDate
  form.superior = data.superior
  form.majorMilestone = data.majorMilestone
  form.businessField = data.businessField
  form.mainFunction = data.mainFunction
  form.statusId = data.statusId
}
