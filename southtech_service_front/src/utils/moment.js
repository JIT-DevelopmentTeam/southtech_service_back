import {dic} from "./moment-dic";
import moment from 'moment';

export function calculationTime(dateTime) {
  dic
  let result
  let date = moment(dateTime)
  if (date.isBefore(moment())) {
    result = '过去 ' + date.fromNow(true)
  } else {
    result = '还有 ' + date.toNow(true)
  }
  for (let i in dic) {
    let obj = Object.keys(dic)
    if (result.indexOf(obj[obj.length - 1]) != -1) {
      result = dic[obj[obj.length - 1]]
    } else {
      result = result.replace(i, dic[i])
    }
  }
  return result
}