<template>
  <div class="deliverTemplateUpdate">
    <!-- 运费编辑 -->
    <delivery
      :propDelivery="template"
      @updateDelivery="updateDelivery"
      :flag="type"
      :addOrUpdate="update"
    />
  </div>
</template>

<script>
import delivery from './Delivery'
import { getTemplateOneApi, updateTemplateApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate.js'
export default {
  components: { delivery },
  data () {
    return {
      update: '保存',
      flag: 0,
      type: 0, // 类型
      template: {
        templateName: '',
        flag: 0, // 默认添加普通
        contentParam: {
          limitParam: {
            limit_deliver_area: 0,
            area_list: 0,
            area_text: '全国（其他地区）',
            first_num: 1,
            first_fee: 0,
            continue_num: 1,
            continue_fee: 0
          },
          areaParam: [],
          hasFee0Condition: 0,
          feeConditionParam: []
        }
      }
    }
  },
  watch: {
    $route (to, from) {
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
      }
    },
    // 获取单个数据
    initData () {
      this.type = this.$route.query.type
      getTemplateOneApi({
        deliverTemplateId: this.$route.query.deliverTemplateId
      }).then(res => {
        if (res.error === 0) {
          this.template = res.content
        }
      })
    },
    // 编辑保存
    updateDelivery (data) {
      if (this.type === 0) {
        // 普通运费
        updateTemplateApi(data).then(res => {
          if (res.error === 0) {
            this.$message.success('修改运费模板成功!')
            this.$router.push({
              path: `/admin/home/main/goodsManage/deliverTemplate/list`
            })
          } else {
            this.$message.warning('修改运费模板失败!')
          }
        })
      } else {
        // 重量运费
        updateTemplateApi(data).then(res => {
          if (res.error === 0) {
            this.$message.success('修改重量运费模板成功!')
            this.$router.push({
              path: `/admin/home/main/goodsManage/deliverTemplate/weightList`
            })
          } else {
            this.$message.warning('修改重量运费模板失败!')
          }
        })
      }
    }
  }
}
</script>

<style>
</style>
