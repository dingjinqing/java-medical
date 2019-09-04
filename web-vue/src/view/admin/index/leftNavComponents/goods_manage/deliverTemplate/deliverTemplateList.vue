<template>
  <!-- 运费模板列表 -->
  <div class="deliverTemplateList">
    <el-button @click="handleDelTemplate">删除运费模板</el-button>
    <section class="wrap">
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
              style="width:100px;"
              size="small"
              v-model="value"
              placeholder="请选择运费模板"
            >
              <el-option
                v-for="item in options"
                :key="item.value "
                :label="item.label | showTemplate"
                :value="item.value | showTemplate"
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
              style="width:80px"
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
    <!-- 列表的内容 -->
    <section class="list_content">
      <section
        class="list"
        v-for="(item,i) in lists"
        :key="i"
      >
        <section class="table_header">
          <section class="left"></section>
          <section class="title">
            <span>{{item.templateName}}</span>
          </section>
          <section class="right">
            <el-button type="text">复制模板</el-button>
            <el-button type="text">修改</el-button>
            <el-button type="text">删除</el-button>
          </section>
        </section>
        <section class="table_list">
          <!-- 表格 -->
          <el-table
            border
            style="width: 100%"
          >
            <!-- 可配送区域 -->
            <el-table-column
              prop="templateContent[0].datalist[0].area_text"
              align="center"
              label="可配送区域"
            >
            </el-table-column>
            <!-- 首件 -->
            <el-table-column
              prop="templateContent[0].datalist[0].first_num"
              align="center"
              label="首件（件）"
            >
            </el-table-column>
            <!-- 运费 -->
            <el-table-column
              align="center"
              prop="templateContent[0].datalist[0].first_fee"
              label="运费（元）"
            >
            </el-table-column>
            <!-- 续件 -->
            <el-table-column
              prop="templateContent[0].datalist[0].continue_num"
              label="续件（件）"
              align="center"
            >
            </el-table-column>
            <!-- 续费 -->
            <el-table-column
              align="center"
              prop="templateContent[0].datalist[0].continue_fee"
              label="续费（元）"
            >
            </el-table-column>
          </el-table>
        </section>
      </section>
    </section>
    <section class="paginationContainer">

    </section>
  </div>
</template>
<script>
import { fetchDeliverTemplateList, deliverConfig, deliverDelete } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
import pagination from '@/components/admin/pagination/pagination'
export default {
  name: 'deliverTemplateList',
  components: { pagination },
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
      value: '统一运费', // 默认统一运费
      isShow: true, // 用来控制显示隐藏
      tableData: [], // 表格的数据
      content: ``, // 后台返回的数据
      lists: []
    }
  },
  // 局部过滤器
  filters: {
    // 根据后台返回的0|1显示统一运费|满额包邮
    showTemplate (val) {
      switch (val) {
        case '0': return '统一运费'
        case '1': return '满额包邮'
      }
    }
  },

  created () {
    this.initPriceData()
  },
  methods: {
    // 删除运费模板
    handleDelTemplate () {
      deliverDelete({
        'deliverTemplateId': '29'
      }).then(res => { console.log(res) }).catch(err => console.log(err))
    },
    // 选中运费模板的时候
    handleChange (val) {
      switch (val) {
        case '统一运费': this.isShow = true
          break
        case '满额包邮': this.isShow = false
          break
      }
    },
    // 格式化返回的config
    formatConfig (config) {
      // DeliverTemplateConfig(templateName=1, feeLimit=300, price=10)
      // 截取有限字符串
      let jsonObj = {}
      let res = config.slice(22, 60).split(',')

      res.forEach((item, i) => {
        jsonObj[res[i].split('=')[0].trim()] = res[i].split('=')[1].trim()
      })
      return jsonObj
    },
    // 初始化运费数据
    initPriceData () {
      fetchDeliverTemplateList({}).then(res => {
        const { error, content: { config, pageResult: { dataList } } } = res
        if (error === 0) {
          console.log(res[`content`][`pageResult`][`dataList`])
          this.content = res.content
          this.formData = JSON.parse(config)
          let resData = this.formatTemplateContent(dataList)
          // console.log(resData)
          this.lists = resData
          // console.log(this.lists)
        }
      }).catch(err => console.log(err))
    },
    // 保存配置
    handleSaveConfig () {
      // console.log(this.formData)
      // 修改默认运费模板配置
      deliverConfig(this.formData).then(res => {
        const { error } = res
        if (error === 0) {
          this.$message({
            showClose: true,
            message: '保存成功',
            type: 'success',
            center: true,
            duration: 1000
          })
        }
      }).catch(err => console.log(err))
    },
    // 格式化模板的内容
    formatTemplateContent (dataList) {
      // console.log(dataList[0])
      let newArrList = []
      dataList.forEach((item, i) => {
        newArrList.push({
          'deliverTemplateId': item.deliverTemplateId,
          'flag': item.flag,
          'templateName': item.templateName,
          'templateContent': JSON.parse(item['templateContent'])
        })
      })
      return newArrList
    }
  }
}
</script>
<style lang="css" scoped>
.wrap {
  display: flex;
  justify-content: space-between;
}
.block {
  background-color: #e6e9f0;
  width: 100%;
  height: 10px;
}
.table_header {
  padding-right: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 50px;
  width: 100%;
  background: #eef1f6;
}
.left {
  background-color: #5a8bff;
  height: 100%;
  width: 2px;
}
.title {
  color: #333;
  font-size: 14px;
}
.list {
  margin-bottom: 10px;
}
</style>
