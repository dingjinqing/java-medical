<template>
  <div>
    <el-form
      label-width="100px"
      class="demo-ruleForm"
      :model="delivery"
      ref="formData"
      :rules="rules"
      style="margin-bottom:60px;background: #fff;"
    >
      <el-form-item
        label="模板名称："
        prop="templateName"
        style="width:350px;"
      >
        <el-input
          size="small"
          style="width: 170px;"
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
          <el-checkbox
            v-model="delivery.contentParam.limitParam.limit_deliver_area"
            :true-label="1"
            :false-label="0"
          >除可配送区域外，其他不可配送</el-checkbox>
        </el-alert>
      </el-form-item>
      <!-- contentParam.limitParam配置的验证,表单 -->
      <el-form-item v-if="delivery.contentParam.limitParam.limit_deliver_area === 0">
        <el-form
          :inline="true"
          :model="delivery.contentParam.limitParam"
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
              v-model.number="delivery.contentParam.limitParam.first_num"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="件内,"
            prop="first_fee"
          >
            <el-input
              style="width:80px;"
              size="small"
              v-model.number="delivery.contentParam.limitParam.first_fee"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="元，每增加"
            prop="continue_num"
          >
            <el-input
              style="width:80px;"
              size="small"
              v-model.number="delivery.contentParam.limitParam.continue_num"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="件，增加运费"
            prop="continue_fee"
          >
            <el-input
              style="width:80px;"
              size="small"
              v-model.number="delivery.contentParam.limitParam.continue_fee"
            ></el-input>元
          </el-form-item>
        </el-form>
      </el-form-item>
      <!-- 可配送区域组件 -->
      <el-form-item label="配送区域：">
        <LocatTPTable
          ref="regionData"
          :isRegion="true"
          appointContent="指定配送区域和运费"
          :editLocation="delivery.contentParam.areaParam"
        />
      </el-form-item>
      <el-form-item>
        <el-checkbox
          v-model="delivery.contentParam.hasFee0Condition"
          :true-label="1"
          :false-label="0"
        >指定条件包邮（可选）</el-checkbox>
      </el-form-item>
      <!-- 指定条件包邮组件 -->
      <el-form-item v-if="delivery.contentParam.hasFee0Condition === 1">
        <LocatTPTable
          ref="freeShippingData"
          :isRegion="false"
          appointContent="指定可包邮配送区域和条件"
          :editLocation="delivery.contentParam.feeConditionParam"
        />
      </el-form-item>
    </el-form>
    <div class="add-template">
      <el-button
        size="small"
        @click="addDeliveryTemplate"
        type="primary"
      >{{ addOrUpdate?addOrUpdate:button }}</el-button>
    </div>
  </div>
</template>

<script>
import LocatTPTable from '@/components/admin/areaLinkage/LocatTPTable'
import RulesMixins from '@/mixins/RulesMixins' // mixin混入

export default {
  components: { LocatTPTable },
  props: {
    propDelivery: {
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
        // 编辑初始化
        this.initData()
      }
    }
    // flag: {
    //   handler: function (newVal, oldVal) {
    //     this.delivery.flag = this.flag
    //   }
    // }
  },
  data () {
    var validateNum = (rule, value, callback) => {
      var re = /^[1-9]\d*$/
      if (value === '') {
        callback(new Error('请填写件数'))
      } else if (!re.test(value)) {
        callback(new Error('格式不正确'))
      } else {
        callback()
      }
    }
    var validateMoney = (rule, value, callback) => {
      var re = /^\d+(\.\d+)?$/
      if (value === '') {
        callback(new Error('请填写运费'))
      } else if (!re.test(value)) {
        callback(new Error('格式不正确'))
      } else {
        callback()
      }
    }
    return {
      button: `添加模板`,
      // 验证规则
      rules: {
        templateName: [{ required: true, message: '模板名称不能为空', trigger: 'blur' }]
      },
      rulesForm: {
        first_num: [{ required: true, validator: validateNum, trigger: 'blur' }],
        first_fee: [{ required: true, validator: validateMoney, trigger: 'blur' }],
        continue_num: [{ required: true, validator: validateNum, trigger: 'blur' }],
        continue_fee: [{ required: true, validator: validateMoney, trigger: 'blur' }]
      },
      delivery: {
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
      },
      updateId: null // 修改数据id
    }
  },
  methods: {
    initData () {
      // 编辑回显
      var data = this.propDelivery
      this.updateId = data.deliverTemplateId
      this.delivery = {
        templateName: data.templateName,
        flag: data.flag,
        contentParam: {
          limitParam: {
            limit_deliver_area: data.templateContent.limitParam.limit_deliver_area,
            area_list: data.templateContent.limitParam.area_list,
            area_text: data.templateContent.limitParam.area_text,
            first_num: data.templateContent.limitParam.first_num,
            first_fee: data.templateContent.limitParam.first_fee,
            continue_num: data.templateContent.limitParam.continue_num,
            continue_fee: data.templateContent.limitParam.continue_fee
          },
          areaParam: data.templateContent.areaParam,
          feeConditionParam: data.templateContent.feeConditionParam
        }
      }
    },
    // 添加运费模板
    addDeliveryTemplate () {
      var hasFeeData = []
      this.$refs['formData'].validate((valid) => {
        if (!valid) {
          return false
        }
      })
      if (this.delivery.contentParam.limitParam.limit_deliver_area === 0) {
        this.$refs['freightRegion'].validate((valid) => {
          if (!valid) {
            return false
          }
        })
      }
      var regionData = this.$refs['regionData'].getTableData()
      this.delivery.contentParam.areaParam = regionData
      if (this.delivery.contentParam.limitParam.limit_deliver_area && !regionData.length) {
        this.$message.error('可配送区域不能为空')
        return false
      }
      if (this.delivery.contentParam.hasFee0Condition === 1) {
        hasFeeData = this.$refs['freeShippingData'].getTableData()
      }
      this.delivery.contentParam.feeConditionParam = hasFeeData
      if (this.delivery.contentParam.hasFee0Condition === 1 && !hasFeeData.length) {
        this.$message.error('包邮条件不能为空')
        return false
      }
      if (!this.propDelivery) {
        // 添加保存
        this.delivery.flag = this.flag
        this.$emit('addDelivery', this.delivery)
      } else {
        alert(this.flag)
        var obj = this.delivery
        obj.deliverTemplateId = this.updateId
        // 编辑保存
        this.$emit('updateDelivery', obj)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
div.el-alert.el-alert--warning.is-light.checkbox-delivery {
  width: 450px;
  padding: 20px;
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
  margin-left: -35px;
}
</style>
