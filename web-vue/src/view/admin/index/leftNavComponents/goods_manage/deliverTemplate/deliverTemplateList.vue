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
                v-for="item in options"
                :key="item.value "
                :label="item.label | showTemplate "
                :value="item.value "
              ></el-option>
            </el-select>
          </el-form-item>
          <!-- 运费 -->
          <el-form-item
            v-if="isShow"
            prop="price"
          >
            <span>运费：</span>
            <!-- 运费输入框 -->
            <el-input
              size="small"
              v-model.number="formData.price"
              style="width:170px"
            ></el-input><span>元</span>
          </el-form-item>
          <!-- 订单金额 -->
          <el-form-item
            v-else
            prop="feeLimit"
          >
            <span> 订单金额>=</span>
            <el-input
              size="small"
              v-model.number="formData.feeLimit"
              style="width:80px"
            ></el-input>
            <span>元时包邮,否则运费为</span>
            <el-input
              size="small"
              v-model.number="formData.price"
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
    let checkPrice = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('运费不能为空'))
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入合法数字值'))
        }
      }, 1000)
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
      },
      // 表单输入的验证
      formRules: {
        price: [
          { validator: checkPrice, trigger: 'blur' }
        ],
        feeLimit: [
          { validator: checkPrice, trigger: 'blur' }
        ]
      },
      options: [
        {
          label: '0',
          value: '0'
        },
        {
          label: '1',
          value: '1'
        }
      ],
      // value: '统一运费', // 默认统一运费
      isShow: true // 用来控制显示隐藏

    }
  },
  // 局部过滤器
  filters: {
    // 根据后台返回的0|1显示统一运费|满额包邮
    showTemplate (val) {
      console.log(val)
      switch (val) {
        case '0': return '统一运费'
        case '1': return '满额包邮'
      }
    }
  },
  created () {
    fetchDeliverTemplateList(this.pageParams).then(res => {
      console.log(res)
      const { error, content: { config, pageResult: { dataList, page } } } = res
      if (error === 0) {
        console.log(res)
        this.pageParams = page
        this.content = res.content
        this.formData = JSON.parse(config)
        let resData = formatTemplateData(dataList)
        this.lists = resData
        console.log(this.formData.templateName)
        if (this.formData.templateName === 0) {
          this.formData.templateName = '统一运费'
        } else {
          this.formData.templateName = '满额包邮'
        }
      }
    }).catch(err => console.log(err))
  },
  methods: {
    // 选中运费模板的时候
    handleChange (val) {
      console.log(val)
      switch (val) {
        case '统一运费': this.isShow = true
          break
        case '满额包邮': this.isShow = false
          break
      }
    },
    // 保存配置
    handleSaveConfig () {
      console.log(this.formData)
      // 修改默认运费模板配置
      deliverConfig(this.formData).then(res => {
        // let templateOption = res.content
        if (res.error === 0) {
          this.$message({
            showClose: true,
            message: '保存成功',
            type: 'success',
            center: true,
            duration: 1000
          })
        }
      }).catch(err => console.log(err))
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
