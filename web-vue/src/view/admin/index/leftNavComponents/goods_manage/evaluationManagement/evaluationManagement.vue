<template>
  <div>
    <Crumbs :mTitle="M_title" />
    <evaluationManagementContent />

  </div>
</template>
<script>
import Crumbs from '@/components/admin/crumbs'
import evaluationManagementContent from '@/view/admin/index/leftNavComponents/goods_manage/evaluationManagement/evaluationManagementContent'
export default {
  components: { Crumbs, evaluationManagementContent },
  data () {
    return {
      M_title: [
        '商品管理', '评价管理'
      ]
    }
  },
  mounted () {

  },
  methods: {

  }
}
</script>
<style scoped>
</style>
