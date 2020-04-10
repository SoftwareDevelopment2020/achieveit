/**
 * 时间工具类
 */

export function stringToChinese(date) {
  const d = new Date(date)
  const month = d.getMonth() + 1
  return d.getFullYear() + '年' + month + '月' + d.getDate() + '日'
}

export function stringToTime(date) {
  const d = new Date(date)
  const hours = d.getHours()
  const minutes = d.getMinutes()
  const seconds = d.getSeconds()
  return (hours < 10 ? '0' : '') + hours + ':' + (minutes < 10 ? '0' : '') + minutes  + ':' + (seconds < 10 ? '0' : '') + seconds
}

export function stringToDateTime(date) {
  const d = new Date(date)
  const month = d.getMonth() + 1
  const hours = d.getHours()
  const minutes = d.getMinutes()
  const seconds = d.getSeconds()
  return d.getFullYear() + '年' + month + '月' + d.getDate() + '日  ' + (hours < 10 ? '0' : '') + hours + ':' + (minutes < 10 ? '0' : '') + minutes  + ':' + (seconds < 10 ? '0' : '') + seconds
}

export function dateToString(date) {
  const year = date.getFullYear()
  const month = date.getMonth()+1
  const day = date.getDate()
  return year + '-' + (month < 10 ? '0' : '') + month + '-' + (day < 10 ? '0' : '') + day
}
