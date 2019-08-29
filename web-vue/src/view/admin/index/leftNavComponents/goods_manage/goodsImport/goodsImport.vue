<template>
  <div class="container">
    <section class="">
      <el-button @click="test">测试按钮</el-button>
      <imageDialogExpansion
        pageIndex='basicInfo'
        :maxNum="maxNum"
        @handleGoodsImgs='handleGoodsImgs'
      />
    </section>
    <section>
      <!-- 全选 -->
      <el-checkbox
        v-model="checked"
        @change="checkedAll"
      >全选</el-checkbox>

      <!-- 树状 -->
      <el-tree
        check-on-click-node
        :data="list"
        show-checkbox
        node-key="id"
        ref="tree"
        highlight-current
        :props="defaultProps"
        @check-change="handleCheckChange"
        @node-click="handleClick"
      >
      </el-tree>
    </section>
    <section>
      <el-button @click="handleTest">测试</el-button>
    </section>
  </div>
</template>
<script>
import { getAreaSelect } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate.js'
import imageDialogExpansion from '@/components/admin/imageDialogExpansion/imageDialogExpansion'
export default {
  // 组件名
  name: '',
  components: { imageDialogExpansion },
  // data 数据
  created () {
    this.fetchList()
  },
  mounted () {
    this.langDefault()
  },
  data () {
    return {

      maxNum: 8,
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
      },
      // 选中
      selected: ``,
      checked: false
    }
  },
  // 生命周期钩子
  create () {

  },

  // 方法
  methods: {
    test () {
      this.$http.$emit('dtVisible')
    },
    handleGoodsImgs (val) {
      console.log(val)
    },
    checkedAll () {
      if (this.checked) {
        // 全选
        this.$refs.tree.setCheckedNodes(this.list)
      } else {
        // 取消选中
        this.$refs.tree.setCheckedKeys([])
      }
    },
    // 节点被点击时的回调
    handleClick (obj, node, self) {
      // console.log(obj, node, self)
    },
    // 节点选中状态发生变化时的回调
    handleCheckChange (obj, isSelected, node) {
      let res = this.$refs.tree.getCheckedNodes()

      this.selected = res
      console.log()
    },
    handleTest () {
      console.log(this.selected)
    },
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
              id: item['districtId'],
              districtId: item['districtId'],
              districtName: item['districtName'],
              name: item['districtName']
            })
          })
          arr2.push({
            id: item['cityId'],
            children: arr3,
            areaDistrict: item['areaDistrict'],
            cityId: item['cityId'],
            cityName: item['cityName'],
            name: item['cityName']
          })
        })
        newArr.push({
          id: item['provinceId'],
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
