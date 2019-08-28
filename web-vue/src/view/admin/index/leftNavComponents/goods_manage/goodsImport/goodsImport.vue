<template>
  <div class="container">
    <section>
      <!-- 树状 -->
      <el-tree
        check-on-click-node
        :data="list"
        show-checkbox
        node-key="id"
        ref="tree"
        highlight-current
        :props="defaultProps"
      >
      </el-tree>
    </section>
  </div>
</template>
<script>
import { getAreaSelect } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate.js'

export default {
  // 组件名
  name: '',
  // data 数据
  created () {
    this.fetchList()
  },
  data () {
    return {
      list: [],
      dataList: [{
        id: 110000,
        name: '北京',
        children: [{
          id: 110100,
          name: '北京市',
          children: [{
            id: 110101,
            name: '东城区'
          }, {
            id: 110102,
            name: '西城区'
          }]
        }]
      }],
      // 配置选项
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  // 生命周期钩子
  create () {

  },
  mounted () {

  },
  // 方法
  methods: {
    fetchList () {
      getAreaSelect().then(res => {
        const { error, content } = res
        if (error === 0) {
          console.log(content)
          // console.log(this.formatData(content))
          this.list = this.formatData(content)
        }
      }).catch(err => console.log(err))
    },
    formatData (data) {
      let newArr = []
      data.forEach(item => {
        let arr2 = []
        item['areaCity'].forEach(item => {
          let arr3 = []
          item['areaDistrict'].forEach(item => {
            arr3.push({
              districtId: item['districtId'],
              districtName: item['districtName'],
              name: item['districtName']
            })
          })
          arr2.push({
            children: arr3,
            areaDistrict: item['areaDistrict'],
            cityId: item['cityId'],
            cityName: item['cityName'],
            name: item['cityName']
          })
        })
        newArr.push({
          provinceId: item['provinceId'],
          provinceName: item['provinceName'],
          areaCity: item['areaCity'],
          name: item['provinceName'],
          children: arr2
        })
      })
      // 返回处理后的数据
      return newArr
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
