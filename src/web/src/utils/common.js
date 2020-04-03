/**
 * 工具类
 */

import {isNull} from "./validate";

/**
 * 设置表格数据
 */
export function setTable(data, table, callback) {
  table.data = data.records
  table.total = data.total
  table.page = data.current

  if (callback) {
    callback()
  }
}

export function getNullOrValue(value) {
  return isNull(value) ? null : value
}



