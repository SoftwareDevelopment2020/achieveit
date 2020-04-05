/**
 * 时间工具类
 */

export function stringToChinese(date) {
  const d = new Date(date)
  const month = d.getMonth() + 1
  return d.getFullYear() + '年' + month + '月' + d.getDate() + '日'
}

export function dateToString(date) {
  const year = date.getFullYear()
  const month = date.getMonth()+1
  const day = date.getDate()
  return year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day
}
