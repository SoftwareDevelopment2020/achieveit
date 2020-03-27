/**
 * 时间工具类
 */

export function stringToChinese(date) {
  const d = new Date(date)
  const month = d.getMonth() + 1
  return d.getFullYear() + '年' + month + '月' + d.getDate() + '日'
}
