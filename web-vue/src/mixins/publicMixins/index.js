/**
 * 定义混入组件，存放公共验证部分
 */
export default {
  methods: {
    // 递归调用，增加state属性
    addState (data, checkList) {
      return data.map(item => {
        // 最后返回数据
        // 循环遍历，赋值
        const index = checkList.includes(
          item.provinceId || item.cityId || item.districtId
        )
        if (index) {
          item.state = true
        } else {
          item.state = false
        }
        if (item.areaCity) {
          // 当市存在的时候，继续循环遍历
          item.areaCity = this.addState(item.areaCity, checkList)
        } else if (item.areaDistrict) {
          // 当区存在的时候，继续循环遍历
          item.areaDistrict = this.addState(item.areaDistrict, checkList)
        }
        return item
      })
    },
    // 查找市，并判断数据中是否有和showArr一致的，并返回id，否则查找子集
    searchCity (data, idList, showArr) {
      return data.map(item => {
        // 循环遍历，赋值
        let data = {}
        showArr.forEach(name => {
          if (item.provinceName === name || item.cityName === name) {
            data = item
          }
        })
        // 然后选择判断一下 返回的data的id和idList来比较一下
        if (item.areaCity) {
          data.areaCity = this.searchCity(item.areaCity, idList, showArr)
        }
        return data
      })
    },
    // 遍历筛选出还未展示的省市和id字符串
    selectTableData (showArr, idList, data) {
      // 存储前判断一下
      let showArrStr = showArr.toString() // 把需要显示的省市数组变成字符串
      let checkIdArr = idList.toString() // 选中id字符串
      let isAdd = true // 是否添加，默认是
      // 删除掉已经存在的省市字符串
      data.forEach(item => {
        showArrStr = [...showArrStr.split(item.area_text)].toString() // 删除已经存在页面显示的省市字符串，返回还未在页面显示的省市字符串
        // 去多余逗号
        showArrStr = showArrStr
          .split(',')
          .filter(str => str)
          .toString()
        // id也相同操作,不过需要判断一下，防止更高级的省市区覆盖
        if (!showArrStr) {
          // 当添加操作的情况下，该字符串为空，表示只是操作之前的区县
          // 这时候就要把id赋值给先前的数据
          item.area_list = checkIdArr
          // 并改变状态，说明这次只是修改
          isAdd = false
        } else {
          // 需要判断一下，如果改变前面的市，增加了区,又增加市
          const newIdList = idList.filter(id =>
            item.area_list.includes(id.toString().slice(0, 4))
          ) // 前面三个字符一致则返回true
          if (newIdList.length > 0) {
            item.area_list = newIdList.toString()
          }
          checkIdArr = [...checkIdArr.split(item.area_list)].toString() // 删除已经存在页面显示的id字符串，返回还未在页面显示的id字符串
          // 去多余逗号
          checkIdArr = checkIdArr
            .split(',')
            .filter(str => str)
            .toString()
        }
      })
      // 最后返回这两个字符串
      return {
        data, // 原本的数据，用于替换
        isAdd, // 是否添加
        showArrStr,
        checkIdArr
      }
    }
  }
}
