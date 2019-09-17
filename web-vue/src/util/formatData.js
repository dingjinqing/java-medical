export function formatTemplateData (data) {
  let newArrList = []
  data.forEach((item, i) => {
    newArrList.push({
      deliverTemplateId: item.deliverTemplateId,
      flag: item.flag,
      templateName: item.templateName,
      templateContent: JSON.parse(item['templateContent'])
    })
  })
  return newArrList
}

export function formatConfig (config) {
  // DeliverTemplateConfig(templateName=1, feeLimit=300, price=10)
  // 截取有限字符串
  let jsonObj = {}
  let res = config.slice(22, 60).split(',')

  res.forEach((item, i) => {
    jsonObj[res[i].split('=')[0].trim()] = res[i].split('=')[1].trim()
  })
  return jsonObj
}
export function delObj ({ arr, val }) {
  // eslint-disable-next-line
  const res = arr.find(item => item.label === val);
  return arr.filter(res => {
    return res.label !== val
  })
}
