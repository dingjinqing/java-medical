<template>
  <div class="deliverTemplateUpdate">
    <!-- 运费编辑 -->
    <delivery
      :propDelivery="tempalte"
      @updateDelivery="updateDelivery"
      :flag="flag"
      :addOrUpdate="update"
    />
  </div>
</template>

<script>
// import { formatTemplateData } from '@/util/formatData.js'
import delivery from './Delivery'
import { getTemplateOneApi, updateTemplateApi, updateWeightTemplateApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate.js'
export default {
  components: { delivery },
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
        case `deliverTemplateUpdate`: this.$http.$emit('edit', true); this.initData()
          break
      }
      switch (from.name) {
        case `deliverTemplateUpdate`: this.$http.$emit('edit', false)
          break
      }
    }
  },
  methods: {

    initData () {
      getTemplateOneApi({
        deliverTemplateId: this.$route.query.deliverTemplateId
      }).then(res => {
        const { error, content } = res
        if (error === 0) {
          console.log(res)
          // this.flag = res.content[0].flag
          console.log(res.content[0])
          // localStorage.setItem('data222', JSON.stringify(res.content[0]))
          res.content[0].templateContent = JSON.parse(content[0].templateContent)
          let obj = res.content[0]
          console.log(obj)
          console.log(this.tempalte)
          // 重新赋值给tempalte
          this.tempalte = { // 传递需要修改的数据，如果不用修改，则无需传递
            deliverTemplateId: obj.deliverTemplateId,
            templateName: obj.templateName,
            goodsDeliverTemplateLimitParam: obj.templateContent[0].datalist[0],
            goodsDeliverTemplateAreaParam: obj.templateContent[0].datalist[1],
            goodsDeliverTemplateFeeParam: obj.templateContent[1],
            goodsDeliverTemplateFeeConditionParam: obj.templateContent[2].fee_0_data_list[0]

          }
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
        }).catch(err => console.log(err))
      }
    }
  },
  created () {
    this.initData()
  },
  mounted () {
  },

  data () {
    return {
      update: '保存',
      flag: 0,
      tempalte: { // 传递需要修改的数据，如果不用修改，则无需传递
        deliverTemplateId: '36',
        templateName: '修改模版测试',
        goodsDeliverTemplateLimitParam: {
          limit_deliver_area: 0,
          area_list: '0',
          area_text: '全国（其他地区）',
          first_num: 1,
          first_fee: 10,
          continue_num: 1,
          continue_fee: 10
        },
        goodsDeliverTemplateAreaParam: [
          {
            area_list: [
              '110000',
              '120000',
              '130000',
              '140000',
              '150000',
              '210000',
              '220000',
              '230000',
              '310000',
              '320000',
              '330000',
              '340000',
              '350000',
              '360000',
              '370000',
              '410000',
              '420000',
              '430000',
              '440000',
              '450000',
              '460000',
              '500000',
              '510000',
              '520000',
              '530000',
              '540000',
              '610000',
              '620000',
              '630000',
              '640000',
              '650000',
              '710000',
              '810000',
              '820000'
            ],
            area_text:
              '北京市、天津市、河北省、山西省、内蒙古自治区、辽宁省、吉林省、黑龙江省、上海市、江苏省、浙江省、安徽省、福建省、江西省、山东省、河南省、湖北省、湖南省、广东省、广西壮族自治区、海南省、重庆市、四川省、贵州省、云南省、西藏自治区、陕西省、甘肃省、青海省、宁夏回族自治区、新疆维吾尔自治区、台湾省、香港特别行政区、澳门特别行政区',
            first_num: 1,
            first_fee: 1,
            continue_num: 1,
            continue_fee: 1
          }
        ],
        goodsDeliverTemplateFeeParam: {
          has_fee_0_condition: 1
        },
        goodsDeliverTemplateFeeConditionParam: [
          {
            area_list: [
              '110000',
              '120000',
              '130000',
              '140000',
              '150000',
              '210000',
              '220000',
              '230000',
              '310000',
              '320000',
              '330000',
              '340000',
              '350000',
              '360000',
              '370000',
              '410000',
              '420000',
              '430000',
              '440000',
              '450000',
              '460000',
              '500000',
              '510000',
              '520000',
              '530000',
              '540000',
              '610000',
              '620000',
              '630000',
              '640000',
              '650000',
              '710000',
              '810000',
              '820000'
            ],
            area_text: '北京市、天津市、河北省、山西省、内蒙古自治区、辽宁省、吉林省、黑龙江省、上海市、江苏省、浙江省、安徽省、福建省、江西省、山东省、河南省、湖北省、湖南省、广东省、广西壮族自治区、海南省、重庆市、四川省、贵州省、云南省、西藏自治区、陕西省、甘肃省、青海省、宁夏回族自治区、新疆维吾尔自治区、台湾省、香港特别行政区、澳门特别行政区',
            fee_0_condition: '1',
            fee_0_con1_num: '0',
            fee_0_con2_num: '10',
            fee_0_con3_num: '1',
            fee_0_con3_fee: '0'
          }
        ]
      }
    }
  }
}
</script>

<style>
</style>
