/**
 * 工具类
 */

/**
 * 设置表格数据
 */
export function setTable(data, table, callback) {
  table.data = data.records
  table.total = data.total
  table.page = data.current
  table.limit = data.size

  if (callback) {
    callback()
  }
}



