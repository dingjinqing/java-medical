<template>
  <div class="deliverTemplateUpdate">
    <!-- 运费编辑 -->
    <delivery
      :propDelivery="template"
      @updateDelivery="updateDelivery"
      :flag="flag"
      :addOrUpdate="update"
    />
  </div>
</template>

<script>
import delivery from './Delivery'
import { getTemplateOneApi, updateTemplateApi, updateWeightTemplateApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate.js'
export default {
  components: { delivery },
  data () {
    return {
      update: '保存',
      flag: 0,
      template: {} // 传递需要修改的数据，如果不用修改，则无需传递
      // templates: {
      //   templateName: '',
      //   flag: 0, // 默认添加普通
      //   contentParam: {
      //     limitParam: {
      //       limit_deliver_area: 0,
      //       area_list: 0,
      //       area_text: '全国（其他地区）',
      //       first_num: 1,
      //       first_fee: 0,
      //       continue_num: 1,
      //       continue_fee: 0
      //     },
      //     areaParam: [],
      //     hasFee0Condition: 0,
      //     feeConditionParam: []
      //   }
      // }
    }
  },
  watch: {
    $route (to, from) {
      console.log(from.path)
      switch (from.path) {
        case `/admin/home/main/goodsManage/deliverTemplate/list`: this.flag = 0
          break
        case `/admin/home/main/goodsManage/deliverTemplate/weightList`: this.flag = 1
          break
      }
      switch (to.name) {
        case `deliverTemplateUpdate`: console.log(to.name); this.$http.$emit('edit', true); this.initData()
          break
      }
      switch (from.name) {
        case `deliverTemplateUpdate`: console.log(to.name); this.$http.$emit('edit', false)
          break
      }
    }
  },
  created () {
    this.initData()
  },
  mounted () {
    this.showEdit()
  },
  methods: {
    showEdit () {
      switch (this.$route.path) {
        case `/admin/home/main/goodsManage/deliverTemplate/deliverTemplateUpdate`:
          this.$http.$emit('edit', true)
          break
        default:
          break
      }
    },
    initData () {
      getTemplateOneApi({
        deliverTemplateId: this.$route.query.deliverTemplateId
      }).then(res => {
        if (res.error === 0) {
          res.content[0].templateContent = JSON.parse(res.content[0].templateContent)
          this.template = res.content[0]
          console.log(res.content[0])
          console.log(this.template)
        }
      }).catch(err => console.log(err))
    },

    updateDelivery (data) {
      if (this.flag === 0) {
        updateTemplateApi(data).then(res => {
          console.log(res)
          const { error } = res
          if (error === 0) {
            this.$message.success('修改成功')
            this.$router.push({
              path: `/admin/home/main/goodsManage/deliverTemplate/list`
            })
          }
        }).catch(err => console.log(err))
      } else {
        updateWeightTemplateApi(data).then(res => {
          console.log(res)
          const { error } = res
          if (error === 0) {
            this.$message.success('修改成功')
            this.$router.push({
              path: `/admin/home/main/goodsManage/deliverTemplate/weightList`
            })
          }
        }).catch(err => alert(err))
      }
    }
  }
}
</script>

<style>
</style>
