<template>
  <!-- 配送信息 -->
  <div class="deliveryInfo">
    <el-form
      :model="formData"
      :rules="rules"
      ref="form"
      label-width="120px"
      class="delivery"
    >
      <el-form-item
        label="运费模板："
        prop="value"
      >
        <el-select
          @change="getTemplateHandle"
          size="small"
          v-model="formData.value"
          filterable
          placeholder="请选择"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <span class="el-link">
          <el-link
            type="primary"
            class="link"
            :underline="false"
          >刷新</el-link>|
          <el-link
            class="link"
            type="primary"
            :underline="false"
          >新建模板</el-link>|
          <el-link
            class="link"
            type="primary"
            :underline="false"
          >管理模板</el-link>
        </span>
      </el-form-item>
      <el-form-item
        label-width="120px"
        class="defaultTemplate"
      >
        <section class="template">
          <span v-show="templateName === '0'">店铺统一运费：{{price}}元</span>
          <span v-show="templateName === '1'">店铺满额包邮：订单金额>={{feeLimit}}元时包邮， 否则运费为{{price}}元</span>
          <el-button
            type="text"
            @click="toDetailHandle"
            class="detail"
          >查看详情</el-button>
        </section>
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="商品重量："
        prop="weight"
      >
        <el-input
          v-model="goodsWeight"
          size="small"
          style="width:160px;margin-right:8px"
        >
        </el-input>Kg
      </el-form-item>
      <el-form-item
        label-width="120px"
        label="发货地："
        prop="place"
      >
        <el-input
          size="small"
          style="width:260px;margin-right:8px"
        >
        </el-input>
        <span>最多填写15个字</span>
      </el-form-item>
    </el-form>
    <el-button
      @click="handleTest"
      type="success"
    >测试按钮</el-button>
  </div>
</template>
<script>
import { deliverTemplatelist } from '@/api/admin/goods_manage/deliver/deliver'
export default {
  name: 'deliveryInfo',
  created () {
    this.fetchDeliverTemplatelist()
  },
  data () {
    return {
      formData: {
        value: '店铺默认运费模板'
      },
      rules: {
        value: [
          { required: true, message: '请选择运费模板', trigger: 'change' }
        ]
      },
      options: [{
        value: '',
        label: '',
        deliverTemplateId: '',
        flag: ''
      }],
      templateName: null,
      feeLimit: null,
      price: null,
      deliverTemplateId: ``,
      goodsWeight: ``,
      flag: ''
    }
  },
  methods: {
    handleTest () {
      let params = {
        deliverTemplateId: this.goodsWeight,
        goodsWeight: this.deliverTemplateId
      }
      console.log(params)
    },

    fetchDeliverTemplatelist () {
      let params = {}
      deliverTemplatelist(params).then(res => {
        const { error, content } = res
        if (error === 0) {
          console.log(content)

          let optionArr = []
          content.pageResult.dataList.forEach(item => {
            optionArr.push({
              label: item.templateName,
              value: item.templateName,
              deliverTemplateId: item.deliverTemplateId,
              flag: item.flag
            })
          })
          this.options = optionArr
          let newArr = []
          let arr = content.config.substring(22, 60).split(',')
          arr.forEach(item => {
            let key = item.split('=')[0]
            let value = item.split('=')[1]
            newArr.push({ key, value })
          })
          this.templateName = newArr[0].value
          this.feeLimit = newArr[1].value
          this.price = newArr[2].value
        }
      }).catch(err => console.log(err))
    },
    toDetailHandle () {
      this.$router.push({ path: '/admin/home/main/deliver/template/list' })
    },
    getTemplateHandle (val) {
      let res = this.options.filter((item) => {
        return item.value === val
      })
      console.log(res)
      this.deliverTemplateId = res[0].deliverTemplateId
    }
  }

}
</script>
<style scoped>
.el-link .link {
  margin: 0 5px;
}
.template {
  width: 100%;
  height: 40px;
  padding: 0 5px;
  background-color: #f5f5f5;
}
.defaultTemplate {
  margin-top: -20px;
}
.detail {
  float: right;
}
</style>
