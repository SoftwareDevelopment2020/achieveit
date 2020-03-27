/**
 * 时间工具类
 */

export function stringToChinese(date) {
  const d = new Date(date)
  return d.getFullYear() + '年' + d.getMonth() + '月' + d.getDate() + '日'
}
