<template>
  <div class="add">
    <delivery
      @addDelivery="addDelivery"
      :flag="0"
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

    }
  },
  methods: {
    addDelivery (params) {
      console.log(params, 'paramas id')
      addTemplate(params).then(res => {
        console.log(res)
        const { error } = res
        if (error === 0) {
          this.$message.success('添加运费模板成功')
          this.$router.push({
            path: `/admin/home/main/goodsManage/deliverTemplate/list`
          })
        }
      }).catch(err => console.log(err))
    }
  }
}
</script>

<style>
</style>
