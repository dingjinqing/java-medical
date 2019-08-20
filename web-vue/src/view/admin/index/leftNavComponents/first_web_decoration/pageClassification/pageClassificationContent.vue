<template>
  <div class="pageClassificationContent">
    页面分类
    <el-button @click="handleFetchData">获取页面分类数据</el-button>
  </div>
</template>
<script>
// import { getPageclassificationData, addPageclassification } from '@/api/admin/smallProgramManagement/pageClassification/pageClassification'
export default {
  name: 'pageClassificationContent',
  components: {},
  methods: {
    handleFetchData () {
      // addPageclassification({
      //   'pageName': '页面分类的测试'
      // }).then(res => console.log(res)).catch(err => console.log(err))
      // getPageclassificationData({}).then(res => console.log(res)).catch(err => console.log(err))
    }
  }
}
</script>
<style lang="css" scoped>
</style>
