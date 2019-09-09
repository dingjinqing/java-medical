<template>
  <div>
    <el-form
      label-width="100px"
      class="demo-ruleForm"
      :model="delivery"
      ref="formData"
      style="margin-bottom:60px;"
    >
      <el-form-item
        label="模板名称"
        prop="templateName"
        :rules="rules"
        style="width:350px;"
      >
        <el-input
          size="small"
          v-model="delivery.templateName"
          placeholder="请输入模板名称"
        ></el-input>
      </el-form-item>
      <!-- 橙色框 -->
      <el-form-item>
        <el-alert
          :closable="false"
          type="warning"
          class="checkbox-delivery"
        >
          <el-checkbox v-model="delivery.goodsDeliverTemplateLimitParam.limit_deliver_area">除可配送区域外，其他不可配送</el-checkbox>
        </el-alert>
      </el-form-item>
      <!-- goodsDeliverTemplateLimitParam配置的验证,表单 -->
      <el-form-item v-if="!delivery.goodsDeliverTemplateLimitParam.limit_deliver_area">
        <el-form
          :inline="true"
          :model="delivery.goodsDeliverTemplateLimitParam"
          :rules="rulesForm"
          ref="freightRegion"
        >
          <el-form-item
            label="其他区域运费:"
            prop="first_num"
          >
            <el-input
              style="width:80px;"
              size="small"
              v-model.number="delivery.goodsDeliverTemplateLimitParam.first_num"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="件内,"
            prop="first_fee"
          >
            <el-input
              style="width:80px;"
              size="small"
              v-model.number="delivery.goodsDeliverTemplateLimitParam.first_fee"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="元，每增加"
            prop="continue_num"
          >
            <el-input
              style="width:80px;"
              size="small"
              v-model.number="delivery.goodsDeliverTemplateLimitParam.continue_num"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="件，增加运费"
            prop="continue_fee"
          >
            <el-input
              style="width:80px;"
              size="small"
              v-model.number="delivery.goodsDeliverTemplateLimitParam.continue_fee"
            ></el-input>元
          </el-form-item>
        </el-form>
      </el-form-item>
      <!-- 可配送区域组件 -->
      <el-form-item>
        <LocatTPTable
          ref="regionData"
          :isRegion="true"
          appointContent="指定配送区域和运费"
        />
        <!-- <Region ref="regionData" /> -->
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="delivery.goodsDeliverTemplateFeeParam.has_fee_0_condition">指定条件包邮（可选）</el-checkbox>
      </el-form-item>
      <!-- 指定条件包邮组件 -->
      <el-form-item v-if="delivery.goodsDeliverTemplateFeeParam.has_fee_0_condition">
        <!-- <FreeShipping ref="freeShippingData" /> -->
        <LocatTPTable
          ref="freeShippingData"
          :isRegion="false"
          appointContent="指定可包邮配送区域和条件"
        />
      </el-form-item>
    </el-form>
    <div class="add-template">
      <el-button
        @click="getData"
        size="small"
        type="primary"
      >添加模板</el-button>
    </div>
  </div>
</template>

<script>
import LocatTPTable from '@/components/admin/areaLinkage/LocatTPTable'
import RulesMixins from '@/mixins/RulesMixins' // mixin混入
import { addWeightTemplateApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
export default {
  name: 'delivery',
  components: {
    LocatTPTable
  },
  mixins: [RulesMixins],
  data () {
    return {
      /**
       * 模板名称验证规则
       */
      rules: [{ required: true, message: '模板名称不能为空' }],
      /**
       * 验证规则，goodsDeliverTemplateLimitParam的一些验证
       */
      rulesForm: {
        first_num: [
          { validator: this.checkLimitParamFirstNum, trigger: 'blur' }
        ],
        first_fee: [
          { validator: this.checkLimitParamFirstFee, trigger: 'blur' }
        ],
        continue_num: [
          {
            validator: this.ckeckLimitParamContinueNum,
            trigger: 'blur'
          }
        ],
        continue_fee: [
          {
            validator: this.ckeckLimitParamContinueFee,
            trigger: 'blur'
          }
        ]
      },
      /**
       *  发送给后端的数据格式,具体看请求报文
       */
      delivery: {
        templateName: '',
        flag: 1,
        goodsDeliverTemplateLimitParam: {
          limit_deliver_area: 0,
          area_list: 1,
          area_text: '全国（其他地区）',
          first_num: 1,
          first_fee: 1,
          continue_num: 1,
          continue_fee: 1
        },
        goodsDeliverTemplateAreaParam: [],
        goodsDeliverTemplateFeeParam: {
          has_fee_0_condition: 0
        },
        goodsDeliverTemplateFeeConditionParam: []
      }
    }
  },
  methods: {
    getData () {
      // 状态，存储，为0的情况下验证全部通过，否则就是不通过
      /* eslint-disable */
      let stats = 0,
        freeShippingData = []
      if (
        !this.delivery.goodsDeliverTemplateLimitParam.limit_deliver_area
      ) {
        this.$refs.freightRegion.validate(required => {
          if (!required) {
            stats++
            return false
          }
        })
      }
      if (
        this.delivery.goodsDeliverTemplateFeeParam.has_fee_0_condition
      ) {
        freeShippingData = this.$refs.freeShippingData.getTableData()
      }
      // 获取子组件数据并发送请求
      const regionData = this.$refs.regionData.getTableData()
      if (
        this.delivery.goodsDeliverTemplateLimitParam
          .limit_deliver_area &&
        !freeShippingData.length
      ) {
        this.$message.error('可配送区域不能为空')
        return
      }
      if (
        this.delivery.goodsDeliverTemplateFeeParam
          .has_fee_0_condition &&
        !freeShippingData.length
      ) {
        this.$message.error('条件包邮不能为空')
        return
      }
      this.$refs.formData.validate(required => {
        if (!required) return false
        if (!stats && regionData) {
          // 状态为0，并且freeShippingData和regionData都不为''字符串
          // this.$refs.freeShippingData;

          const data = {
            ...this.delivery,
            goodsDeliverTemplateAreaParam: regionData,
            goodsDeliverTemplateFeeConditionParam: freeShippingData
          }
          // 发送请求,传递data给后台
          // console.log(data)

          data.goodsDeliverTemplateLimitParam.limit_deliver_area = Number(data.goodsDeliverTemplateLimitParam.limit_deliver_area)
          data.goodsDeliverTemplateFeeParam.has_fee_0_condition = Number(data.goodsDeliverTemplateLimitParam.limit_deliver_area)
          console.log(data)

          addWeightTemplateApi(data).then(res => {
            const { error } = res
            if (error === 0) {
              this.$message.success('模板添加成功')
              this.$router.push({
                name: `deliverTemplateWeightList`,
                query: { active: `1` }
              })
            }
          }).catch(err => console.log(err))
          // 报文参考
          let obj = {
            "templateName": "运费模板001",
            "flag": "0",
            "goodsDeliverTemplateLimitParam":
            {
              "limit_deliver_area": "0",
              "area_list": "0",
              "area_text": "全国（其他地区）",
              "first_num": "1",
              "first_fee": "0",
              "continue_num": "1",
              "continue_fee": "0"
            },
            "goodsDeliverTemplateAreaParam":
              [{
                "area_list": "[\"110000\",\"120000\",\"130000\",\"140000\",\"150000\",\"210000\",\"220000\",\"230000\",\"310000\",\"320000\",\"330000\",\"340000\",\"350000\",\"360000\",\"370000\",\"410000\",\"420000\",\"430000\",\"440000\",\"450000\",\"460000\",\"500000\",\"510000\",\"520000\",\"530000\",\"540000\",\"610000\",\"620000\",\"630000\",\"640000\",\"650000\",\"710000\",\"810000\",\"820000\"]",
                "area_text": "北京市、天津市、河北省、山西省、内蒙古自治区、辽宁省、吉林省、黑龙江省、上海市、江苏省、浙江省、安徽省、福建省、江西省、山东省、河南省、湖北省、湖南省、广东省、广西壮族自治区、海南省、重庆市、四川省、贵州省、云南省、西藏自治区、陕西省、甘肃省、青海省、宁夏回族自治区、新疆维吾尔自治区、台湾省、香港特别行政区、澳门特别行政区",
                "first_num": "1",
                "first_fee": "1",
                "continue_num": "1",
                "continue_fee": "1"
              }],
            "goodsDeliverTemplateFeeParam": {
              "has_fee_0_condition": "1"
            },
            "goodsDeliverTemplateFeeConditionParam":
              [{
                "area_list": "0",
                "area_text": "全国（其他地区）",
                "fee_0_condition": "1",
                "fee_0_con1_num": "0",
                "fee_0_con2_num": "10",
                "fee_0_con3_num": "1",
                "fee_0_con3_fee": "0"
              }]
          }
          // console.log(obj)
        }
      })
    }
  }
}
</script>
<style scoped>
div.el-alert.el-alert--warning.is-light.checkbox-delivery {
  width: 450px;
  padding: 20px;
}
div.el-form-item__error {
  margin-left: -40px;
}
div.add-template {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 60px;
  line-height: 60px;
  z-index: 10;
  text-align: center;
  background-color: #eee;

  border-top: 1px solid #f2f2f2;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  z-index: 2;
  width: 88%;
  height: 50px;
  background: #f8f8fa;
  margin-left: -20px;
}
</style>
