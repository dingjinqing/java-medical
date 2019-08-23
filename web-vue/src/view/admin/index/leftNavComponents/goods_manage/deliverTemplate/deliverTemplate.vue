<template>
  <div class="container">
    <!-- 运费模板容器卡片 -->
    <el-card>
      <!-- 头部组件 -->
      <deliverTemplateHeader />
      <!-- 主要内容区域 -->
      <deliverTemplateMain />
    </el-card>
  </div>
</template>
<script>
import deliverTemplateHeader from './deliverTemplateHeader'
import deliverTemplateMain from './deliverTemplateMain'
export default {
  // 组件名
  name: 'deliverTemplate',
  components: { deliverTemplateHeader, deliverTemplateMain },
  // data 数据
  data () {
    return {

    }
  },
  // 生命周期钩子
  create () {

  },
  mounted () {

  },
  // 方法
  methods: {

  }
}
</script>
<style lang="scss" scoped>
.container {
  padding: 10px;
}
</style>
