<template>
  <div class="container">
    <!-- 运费模板容器卡片 -->
    <el-card>
      <!-- 头部headerTabs组件 -->
      <headerTabs
        :labels="labels"
        @index="getIndex"
      />
      <!-- 运费模板列表 -->
      <deliverTemplateList v-show="this.flag === '0'" />
      <!-- 重量运费模板列表 -->
      <deliverTemplateWeightList v-show="this.flag === '1'" />
      <!-- 添加运费模板 -->
      <deliverTemplateAdd v-show="this.flag === '2'" />
      <!-- 添加重量运费模板 -->
      <deliverTemplateWeightAdd v-show="this.flag === '3'" />
    </el-card>
  </div>
</template>
<script>
// import deliverTemplateHeader from './deliverTemplateHeader'
import headerTabs from '@/components/admin/headerTabs/headerTabs'
// import deliverTemplateMain from './deliverTemplateMain'
import deliverTemplateList from './deliverTemplateList'
import deliverTemplateWeightList from './deliverTemplateWeightList'
import deliverTemplateAdd from './deliverTemplateAdd'
import deliverTemplateWeightAdd from './deliverTemplateWeightAdd'
export default {
  // 组件名
  name: 'deliverTemplate',
  components: { headerTabs, deliverTemplateList, deliverTemplateWeightList, deliverTemplateAdd, deliverTemplateWeightAdd },
  // data 数据
  data () {
    return {
      flag: `0`,
      labels: [
        {
          label: `运费模板列表`,
          name: `deliverTemplateList`
        },
        {
          label: `重量运费模板列表`,
          name: `deliverTemplateWeightList`
        },
        {
          label: `添加运费模板`,
          name: `deliverTemplateAdd`
        },
        {
          label: `添加重量运费模板`,
          name: `deliverTemplateWeightAdd`
        }
      ]
    }
  },
  // 生命周期钩子
  create () {

  },
  mounted () {

  },
  // 方法
  methods: {
    getIndex (val) {
      this.flag = val
    }
  }
}
</script>
<style lang="scss" scoped>
.container {
  padding: 10px;
}
</style>
