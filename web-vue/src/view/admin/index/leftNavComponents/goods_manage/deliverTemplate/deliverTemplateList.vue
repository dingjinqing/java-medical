<template>
  <!-- 运费模板列表 -->
  <div class="deliverTemplateList">
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
            <el-button
              type="text"
              @click="handleCopyTemplate(item.deliverTemplateId)"
            >复制模板</el-button>
            <el-button
              type="text"
              @click="upDateTemplate(item.deliverTemplateId)"
            >修改</el-button>
            <el-button
              type="text"
              @click="handleDelTemplate(item.deliverTemplateId)"
            >删除</el-button>
          </section>
        </section>
        <section class="table_list">
          <!-- 表格 -->
          <table border="1">
            <thead>
              <tr>
                <th>可配送区域</th>
                <th>首件（件）</th>
                <th>运费（元）</th>
                <th>续件（件）</th>
                <th>续费（元）</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{{item.templateContent[0].datalist[0].area_text}}</td>
                <td>{{item.templateContent[0].datalist[0].first_num}}</td>
                <td>{{item.templateContent[0].datalist[0].first_fee}}</td>
                <td>{{item.templateContent[0].datalist[0].continue_num}}</td>
                <td>{{item.templateContent[0].datalist[0].continue_fee}}</td>
              </tr>
              <!-- 其他区域以外的表格 -->
              <tr
                v-for="(it22,i) in item.templateContent[0].datalist[1]"
                :key="i"
                v-show="it22.area_text!== null"
              >
                <td>{{it22.area_text}}</td>
                <td>{{it22.first_num}}</td>
                <td>{{it22.first_fee}}</td>
                <td>{{it22.continue_num}}</td>
                <td>{{it22.continue_fee}}</td>
              </tr>
            </tbody>
          </table>
          <!-- 表格 -->

        </section>
      </section>
    </section>
    <section class="paginationContainer">
      <pagination
        :pageParams="pageParams"
        @pagination="initData"
      />
    </section>
  </div>
</template>
<script>
import { formatTemplateData } from '@/util/formatData.js'
import { fetchDeliverTemplateList, deliverConfig, deliverDelete, copyDeliverTemplateApi } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
import pagination from '@/components/admin/pagination/pagination'
export default {
  name: 'deliverTemplateList',
  components: { pagination },
  inject: ['reload'], // 注入
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
      lists: [],
      pageParams: {}
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
    this.initData()
    this.$http.$emit('activeName', this.$route.query.active)
  },

  methods: {
    // 删除运费模板
    handleDelTemplate (id) {
      // 删除操作的提示
      this.$confirm('此操作将永久删除该模板, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deliverDelete({
          'deliverTemplateId': id
        }).then(res => {
          const { error } = res
          if (error === 0) {
            this.initData()
            // 删除成功提示
            this.$message({
              type: 'success',
              center: 'true',
              message: '删除成功!'
            })
          }
        }).catch(err => console.log(err))
      }).catch((error) => console.log(error))
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

    // 初始化运费模板数据
    initData () {
      fetchDeliverTemplateList(this.pageParams).then(res => {
        console.log(res.content.pageResult.dataList)
        const { error, content: { config, pageResult: { dataList, page } } } = res
        if (error === 0) {
          // console.log(page)
          this.pageParams = page
          // console.log(res[`content`][`pageResult`][`dataList`])
          // console.log(res.content)
          this.content = res.content
          this.formData = JSON.parse(config)
          let resData = formatTemplateData(dataList)
          // let resData = this.formatTemplateContent(dataList)
          // console.log(resData)
          this.lists = resData
          console.log(this.lists)
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

    // 复制运费模板
    handleCopyTemplate (deliverTemplateId) {
      // copyDeliverTemplateApi
      copyDeliverTemplateApi({ deliverTemplateId }).then(res => {
        const { error } = res
        if (error === 0) {
          console.log(`复制成功`)
          this.initData()
          // this.reload() // 局部刷新
        }
      }).catch(error => console.log(error))
    },
    // 修改运费模板
    upDateTemplate (deliverTemplateId) {
      this.$http.$emit('showUpDate', true)
      this.$router.push({
        name: `deliverTemplateEdit`,
        query: { deliverTemplateId }
      })
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
  margin-left: 20px;
  font-weight: 700;
}
/* .right {
  margin-left: 250px;
} */
.list {
  margin-bottom: 10px;
}
table,
th,
td {
  border: 1px solid #ddd;
}
table {
  width: 100%;
  margin-top: 2px;
}
th {
  height: 30px;
  text-align: center;
  vertical-align: center;
}
td {
  text-align: center;
  height: 30px;
  vertical-align: center;
}
</style>
