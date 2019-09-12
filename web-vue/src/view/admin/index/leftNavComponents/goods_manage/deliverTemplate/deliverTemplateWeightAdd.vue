<template>
  <div class="add">
    <delivery
      @addDelivery="addDelivery"
      :flag="1"
    />
  </div>
</template>

<script>
import delivery from './Delivery'
import { addWeightTemplateApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate.js'
export default {
  name: `deliverTemplateAdd`,
  components: {
    delivery
  },
  data () {
    return {

    }
  },
  methods: {
    addDelivery (params) {
      // console.log(params)
      addWeightTemplateApi(params).then(res => {
        console.log(res)
        const { error } = res
        if (error === 0) {
          this.$message.success('添加运费模板成功')
          this.$router.push({
            path: `/admin/home/main/goodsManage/deliverTemplate/weightList`
          })
        }
      }).catch(err => console.log(err))
    }
  }
}
</script>

<style>
</style>
