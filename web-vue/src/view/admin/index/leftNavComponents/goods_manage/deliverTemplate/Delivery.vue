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
          :editLocation="delivery.goodsDeliverTemplateAreaParam"
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
          :editLocation="delivery.goodsDeliverTemplateFeeConditionParam"
        />
      </el-form-item>
    </el-form>
    <div class="add-template">
      <el-button
        size="small"
        @click="getData"
        type="primary"
      >{{addOrUpdate?addOrUpdate:button }}</el-button>
    </div>
  </div>
</template>

<script>
import LocatTPTable from '@/components/admin/areaLinkage/LocatTPTable'
import RulesMixins from '@/mixins/RulesMixins' // mixin混入

export default {
  name: 'delivery',
  components: {
    LocatTPTable
  },
  props: {
    propDelivery: {
      required: false,
      type: Object
    },
    addOrUpdate: {
      type: String
    },
    flag: {
      required: true,
      type: Number
    }
  },
  mixins: [RulesMixins],
  watch: {
    propDelivery: {
      handler: function (newVal, oldVal) {
        this.affectation()
      },
      immediate: true
    },
    flag: {
      handler: function (newVal, oldVal) {
        this.delivery.flag = this.flag
      }
    }
  },
  data () {
    return {
      button: `添加模板`,
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
        flag: 0, // 默认添加普通
        goodsDeliverTemplateLimitParam: {
          limit_deliver_area: 0,
          area_list: 0,
          area_text: '全国（其他地区）',
          first_num: 1,
          first_fee: 0,
          continue_num: 1,
          continue_fee: 0
        },
        goodsDeliverTemplateAreaParam: [],
        goodsDeliverTemplateFeeParam: {
          has_fee_0_condition: 0
        },
        goodsDeliverTemplateFeeConditionParam: []
      }
    }
  },
  created () {
    this.affectation()
  },
  methods: {
    affectation () {
      this.delivery.flag = this.flag // 传入类型来判断是普通的还是重量的
      // 判断有没有传递参数
      if (
        this.propDelivery &&
        this.propDelivery.templateName &&
        this.propDelivery.goodsDeliverTemplateLimitParam &&
        this.propDelivery.goodsDeliverTemplateFeeParam
      ) {
        const {
          deliverTemplateId,
          templateName,
          goodsDeliverTemplateLimitParam,
          goodsDeliverTemplateAreaParam,
          goodsDeliverTemplateFeeParam,
          goodsDeliverTemplateFeeConditionParam
        } = this.propDelivery
        this.delivery = {
          deliverTemplateId,
          templateName,
          goodsDeliverTemplateLimitParam: {
            ...goodsDeliverTemplateLimitParam,
            limit_deliver_area: Boolean(
              goodsDeliverTemplateLimitParam.limit_deliver_area
            )
          },
          goodsDeliverTemplateAreaParam,
          goodsDeliverTemplateFeeParam: {
            ...goodsDeliverTemplateFeeParam,
            has_fee_0_condition: Boolean(
              goodsDeliverTemplateFeeParam.has_fee_0_condition
            )
          },
          goodsDeliverTemplateFeeConditionParam
        }
      }
    },
    getData () {
      // 状态，存储，为0的情况下验证全部通过，否则就是不通过
      /* eslint-disable */
      let stats = 0,
        freeShippingData = ''
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
        !regionData.length
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
          const {
            flag,
            templateName,
            goodsDeliverTemplateLimitParam,
            goodsDeliverTemplateFeeParam
          } = this.delivery
          const data = {
            flag,
            templateName,
            goodsDeliverTemplateLimitParam: {
              ...goodsDeliverTemplateLimitParam,
              limit_deliver_area: Number(
                goodsDeliverTemplateLimitParam.limit_deliver_area
              )
            },
            goodsDeliverTemplateAreaParam: regionData,
            goodsDeliverTemplateFeeParam: {
              ...goodsDeliverTemplateFeeParam,
              has_fee_0_condition: Number(
                goodsDeliverTemplateFeeParam.has_fee_0_condition
              )
            }
          }
          data[
            'goodsDeliverTemplateFeeConditionParam'
          ] = freeShippingData.length ? freeShippingData : []
          if (this.propDelivery) {
            data[
              'deliverTemplateId'
            ] = this.delivery.deliverTemplateId
            // 发送请求,传递data给后台,这是修改
            this.$emit('updateDelivery', data)
          } else {
            // 发送请求,传递data给后台
            this.$emit('addDelivery', data)
          }
          this.$message.success('模板添加成功!!!')
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
div.el-alert.el-alert--warning.is-light.checkbox-delivery {
  width: 450px;
  padding: 20px;
}
div.el-form-item__error {
  margin-left: -40px;
}
div.add-template {
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
