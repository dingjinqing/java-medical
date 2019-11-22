<template>
  <div class="add">
    <delivery
      @addDelivery="addDelivery"
      :flag="flag"
    />
  </div>
</template>

<script>
import delivery from './Delivery'
import { addTemplate } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate.js'
export default {
  name: `deliverTemplateAdd`,
  components: { delivery },
  data () {
    return {
      flag: 1
    }
  },
  methods: {
    addDelivery (params) {
      addTemplate(params).then(res => {
        if (res.error === 0) {
          this.$message.success('添加重量运费模板成功!')
          this.$router.push({
            path: `/admin/home/main/goodsManage/deliverTemplate/weightList`
          })
        }
      })
    }
  }
}
</script>

<style>
</style>
