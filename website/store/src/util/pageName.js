export function setPageName (name) {
  document.title = name + ' 微铺宝小程序商家后台'
}

export function delayJudgment (that, id, editName, addName = null) {
  setTimeout(function () {
    if (Number(id) > 0) {
      that.$setPageName(editName)
    } else if (addName) that.$setPageName(addName)
  }, 50)
}
