<template>
  <div class="container">

    <div style="width: 100%;">
      <!-- header  标签页-->
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
        style="padding: 10px; background: #fff;"
      >
        <el-tab-pane
          v-for="(item ,i) in labels"
          :key="i"
          :label="item.label"
          :name="item.name"
        ></el-tab-pane>
        <template v-if="edit">
          <el-tab-pane
            label="运费模板编辑"
            name="/admin/home/main/goodsManage/deliverTemplate/deliverTemplateUpdate"
          ></el-tab-pane>
        </template>

      </el-tabs>

      <!-- 路由出口 -->
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </div>

  </div>
</template>
<script>
// 运费模板列表
import deliverTemplateList from './deliverTemplateList'
// 重量列表
import deliverTemplateWeightList from './deliverTemplateWeightList'
// 添加运费模板
import deliverTemplateAdd from './deliverTemplateAdd'
// 添加重量
import deliverTemplateWeightAdd from './deliverTemplateWeightAdd'
export default {
  // 组件名
  name: 'deliverTemplate',
  components: { deliverTemplateList, deliverTemplateWeightList, deliverTemplateAdd, deliverTemplateWeightAdd },
  // data 数据
  data () {
    return {
      edit: false,
      activeName: ``,
      labels: [
        {
          label: `运费模板列表`,
          name: `/admin/home/main/goodsManage/deliverTemplate/list`
        },
        {
          label: `重量运费模板列表`,
          name: `/admin/home/main/goodsManage/deliverTemplate/weightList`
        },
        {
          label: `添加运费模板`,
          name: `/admin/home/main/goodsManage/deliverTemplate/deliverTemplateAdd`
        },
        {
          label: `添加重量运费模板`,
          name: `/admin/home/main/goodsManage/deliverTemplate/deliverTemplateWeightAdd`
        }

      ]

    }
  },
  watch: {
    $route (to, from) {
      // console.log(to)
      switch (to.path) {
        case `/admin/home/main/goodsManage/deliverTemplate/deliverTemplateUpdate`:
          this.edit = true
          break

        default:
          break
      }
    }
  },
  // 生命周期钩子
  created () {
    this.$http.$on('edit', (val) => {
      this.edit = val
    })
    this.activeName = this.$route.path
  },
  updated () {
    this.activeName = this.$route.path
  },
  // 方法
  methods: {
    handleClick (tab) {
      this.$router.push(tab.name)
    }
  }

}
</script>
<style lang="scss" scoped>
.container {
  padding: 10px;
}
</style>
