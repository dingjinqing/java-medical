<template>
  <!-- 运费模板列表 -->
  <div class="deliverTemplateList">
    <section
      class="wrap"
      style="padding: 0 10px;margin-bottom: 10px; background: #fff;"
    >
      <section>
        <el-form
          :rules="formRules"
          ref="form"
          :model="formData"
          label-width="140px"
          :inline="true"
        >
          <!-- 店铺默认运费模板 -->
          <el-form-item label="店铺默认运费模板：">
            <el-select
              @change="handleChange"
              style="width: 170px;"
              size="small"
              v-model="formData.templateName"
              placeholder="请选择运费模板"
            >
              <el-option
                v-for="(item, index) in options"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <!-- 运费 -->
          <el-form-item
            v-if="isShow === true"
            prop="price"
          >
            <span>运费：</span>
            <!-- 运费输入框 -->
            <el-input
              size="small"
              v-model="formData.price1"
              style="width:170px"
            ></el-input><span>元</span>
          </el-form-item>
          <!-- 订单金额 -->
          <el-form-item
            v-if="isShow === false"
            prop="feeLimit"
          >
            <span> 订单金额>=</span>
            <el-input
              size="small"
              v-model="formData.feeLimit"
              style="width:80px"
            ></el-input>
            <span>元时包邮,否则运费为</span>
            <el-input
              size="small"
              v-model="formData.price2"
              style="width:80px"
            ></el-input>
            <span>元</span>
          </el-form-item>
          <el-form-item>
          </el-form-item>
        </el-form>
      </section>
      <section>
        <!-- 保存按钮 -->
        <el-button
          type="primary"
          size="small"
          @click="handleSaveConfig"
        >保存配置</el-button>
      </section>
    </section>
    <div>
      <deliverTemplateTable :listType="`list`" />
    </div>
  </div>

</template>
<script>
import { formatTemplateData } from '@/util/formatData.js'

// 引入模板列表
import deliverTemplateTable from './deliverTemplateTable'
import { deliverConfig, fetchDeliverTemplateList } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
export default {
  components: { deliverTemplateTable },
  data () {
    // 自定义校验规则
    var checkMoney = (rule, value, callback) => {
      var re = /^\d+(\.\d{1,2})?$/
      if (!this.formData.price1) {
        callback(new Error('请填写运费'))
      } else if (!re.test(this.formData.price1)) {
        callback(new Error('请输入合法数字值'))
      } else {
        callback()
      }
    }
    var checkFeeLimit = (rule, value, callback) => {
      var re = /^\d+(\.\d{1,2})?$/
      if (!value) {
        callback(new Error('请填写订单金额'))
      } else if (this.formData.price2 === '') {
        callback(new Error('请填写运费'))
      } else if (!re.test(value)) {
        callback(new Error('订单金额不合符要求'))
      } else if (!re.test(this.formData.price2)) {
        callback(new Error('运费不合符要求'))
      } else {
        callback()
      }
    }
    return {
      tableData: [], // 表格的数据
      content: ``, // 后台返回的数据
      lists: [],
      pageParams: {},
      /**
       * 头部的数据
       */
      activeName: `0`,
      formData: {
        templateName: '',
        price1: '',
        price2: '',
        feeLimit: ''
      },
      options: [{
        label: '统一运费',
        value: 0
      }, {
        label: '满额包邮',
        value: 1
      }],
      // 表单输入的验证
      formRules: {
        price: [{ validator: checkMoney, trigger: ['blur', 'change'] }], // 统一运费
        feeLimit: [{ validator: checkFeeLimit, trigger: ['blur', 'change'] }] // 满额包邮
      },
      isShow: true // 用来控制显示隐藏

    }
  },
  mounted () {
    fetchDeliverTemplateList(this.pageParams).then(res => {
      const { error, content: { config, pageResult: { dataList, page } } } = res
      if (error === 0) {
        this.pageParams = page
        this.content = res.content

        var requestParam = JSON.parse(config)
        this.formData.templateName = requestParam.templateName
        this.formData.feeLimit = requestParam.feeLimit
        if (this.formData.templateName === 0) {
          this.isShow = true
          this.formData.price1 = requestParam.price
          this.formData.price2 = ''
        } else if (this.formData.templateName === 1) {
          this.isShow = false
          this.formData.price2 = requestParam.price
          this.formData.price1 = ''
        }

        // if (this.formData.templateName === 0) {
        //   this.isShow = true
        // } else if (this.formData.templateName === 1) {
        //   this.isShow = false
        // }
        let resData = formatTemplateData(dataList)
        this.lists = resData
      }
    })
  },
  methods: {
    // 选中运费模板的时候
    handleChange (val) {
      this.$refs.form.resetFields()
      switch (val) {
        case 0:

          this.$refs.form.validateField('price')
          this.isShow = true
          break
        case 1:

          this.$refs.form.validateField('feeLimit')
          this.isShow = false
          break
      }
    },
    // 保存配置
    handleSaveConfig () {
      var requestParam = {}
      requestParam.templateName = this.formData.templateName
      requestParam.feeLimit = this.formData.feeLimit
      if (this.formData.templateName === 0) {
        requestParam.price = this.formData.price1
      } else {
        requestParam.price = this.formData.price2
      }
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // 修改默认运费模板配置
          deliverConfig(requestParam).then(res => {
            // let templateOption = res.content
            if (res.error === 0) {
              this.$message.success({ message: '保存成功' })
            }
          })
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.wrap {
  display: flex;
  justify-content: space-between;
}
</style>
