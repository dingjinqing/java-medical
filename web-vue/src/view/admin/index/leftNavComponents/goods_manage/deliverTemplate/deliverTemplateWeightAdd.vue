<template>
  <!-- 添加重量运费模板组件 -->
  <div class="deliverTemplateAdd">
    <el-form
      ref="form"
      :model="formData"
      label-width="120px"
      :rules="formDataRules"
    >
      <el-form-item label="模板名称：">
        <!-- 模板名称 -->
        <section>
          <el-form-item prop="templateName">
            <el-input
              ref="templateName"
              v-model="formData[`templateName`]"
              size="small"
              style="width:210px"
            ></el-input>
          </el-form-item>

        </section>
        <!-- 橙黄框 -->
        <section class="wrap">
          <el-checkbox v-model="formData[`limitParam_limitDeliverArea`]">除可配送区域外，其他不可配送</el-checkbox>
        </section>
        <!-- 其他区域运费 -->
        <section
          style="display:flex"
          v-show="!formData[`limitParam_limitDeliverArea`]"
        >
          <el-form-item prop="limitParam_firstNum">
            其他区域运费:
            <el-input
              size="small"
              v-model.number="formData[`limitParam_firstNum`]"
              style="width:80px;"
            ></el-input>
          </el-form-item>
          <el-form-item prop="limitParam_firstFee">
            件内，
            <el-input
              size="small"
              v-model.number="formData[`limitParam_firstFee`]"
              style="width:80px;"
            ></el-input>
          </el-form-item>
          <el-form-item prop="limitParam_continueNum">
            元，每增加
            <el-input
              size="small"
              v-model.number="formData[`limitParam_continueNum`]"
              style="width:80px;"
            ></el-input>
          </el-form-item>
          <el-form-item prop="formData[`limitParam_continueNum`]">
            件，增加运费
            <el-input
              size="small"
              v-model.number="formData[`limitParam_continueFee`]"
              style="width:80px;"
            ></el-input>
            元
          </el-form-item>

        </section>
      </el-form-item>
      <!-- 配送区域 -->
      <el-form-item>
        <!-- 配送区域表格 -->
        <section>
          <el-table
            highlight-current-row
            :data="tableData"
            border
            style="width: 100%"
            header-align="center"
            :header-cell-style="tableHeaderStyle"
          >
            <!-- 可配送区域 -->
            <el-table-column
              prop="area_text"
              label="可配送区域"
              align="center"
            >
              <template slot-scope="scoped">
                <span>{{scoped.row.area_text}}</span>
                <el-button
                  @click="handleEdit(scoped.$index )"
                  type="text"
                >编辑</el-button>
                <el-button
                  @click="handleDel(scoped.$index )"
                  type="text"
                >删除</el-button>
              </template>
            </el-table-column>
            <!-- 首件（件） -->
            <el-table-column
              prop="firstNum"
              label="首件（件）"
              align="center"
            >
              <template slot-scope="">
                <el-input
                  v-model="firstNum"
                  size="small"
                ></el-input>
              </template>
            </el-table-column>
            <!-- 运费（元） -->
            <el-table-column
              prop="price"
              label="运费（元）"
              align="center"
            >
              <template slot-scope="">
                <el-input
                  v-model="firstFee"
                  size="small"
                ></el-input>
              </template>
            </el-table-column>
            <!-- 续件（件） -->
            <el-table-column
              prop="price"
              label="续件（件）"
              align="center"
            >
              <template slot-scope="">
                <el-input
                  v-model="continueNum"
                  size="small"
                ></el-input>
              </template>
            </el-table-column>
            <!-- 续费（元） -->
            <el-table-column
              prop="price"
              label="续费（元）"
              align="center"
            >
              <template slot-scope="">
                <el-input
                  v-model="continueFee"
                  size="small"
                ></el-input>
              </template>
            </el-table-column>
          </el-table>
          <locat-t-p
            :location-list=this.locatList
            :outer-visible=this.dialogStat
            @close="this.showData"
            :inner-formData-j="valueA.innerformData"
            :check-list-t="valueA.checkList"
            @checkList="this.getCheckList"
          ></locat-t-p>
          <section class="add">
            <el-button
              type="text"
              style="color:#686868"
              @click="handleAdd"
            >指定可配送区域和运费</el-button>
          </section>
        </section>
        <!-- 指定条件包邮（可选） -->
        <section class="designation">
          <el-checkbox v-model="checked1">指定条件包邮（可选）</el-checkbox>
        </section>
        <section class="">
          <el-table
            :data="tableData1"
            border
          >
            <!-- 包邮可配送区域 -->
            <el-table-column
              prop="price"
              label="包邮可配送区域"
              align="center"
            >
            </el-table-column>
            <!-- 设置包邮条件 -->
            <el-table-column
              prop="price"
              label="设置包邮条件"
              align="center"
            >
            </el-table-column>
          </el-table>

        </section>
      </el-form-item>
    </el-form>
    <!-- 添加模板 -->
    <section class="add_template">
      <!-- 添加模板按钮 -->
      <el-button
        @click="handleAddTemplate"
        size="small"
        type="primary"
      >添加模板</el-button>
    </section>

  </div>
</template>
<script>
// 引入省市区三级联动
import LocatTP from '@/components/admin/areaLinkage/LocatTP'
import { getAreaSelect, addTemplate } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
export default {
  name: 'templateAdd',
  components: { LocatTP },

  data () {
    // 自定义验证规则/验证运费模板名称不能为空
    let checkTemplateName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('模板名称不能为空'))
      } else {
        callback() // 必须要有回调，要不然表单无法提交
      }
    }
    // 自定义验证规则/首件的件数为大于0的整数
    let checkLimitParamFirstNum = (rule, value, callback) => {
      if (Number.isInteger(Number(value)) && Number(value) > 0) {
        callback()
      } else {
        callback(new Error('首件件数必须为大于0'))
      }
    }
    // 自定义验证规则/首件的运费为大于0的整数
    let checkLimitParamFirstFee = (rule, value, callback) => {
      if (Number.isInteger(Number(value)) && Number(value) > 0) {
        callback()
      } else {
        callback(new Error('首件运费必须为大于0'))
      }
    }
    // 自定义验证规则/续重为大于0的整数
    let ckeckLimitParamContinueNum = (rule, value, callback) => {
      if (Number.isInteger(Number(value)) && Number(value) > 0) {
        callback()
      } else {
        callback(new Error('续重必须为大于0'))
      }
    }
    // 自定义验证规则/续件运费为大于0的整数
    let ckeckLimitParamContinueFee = (rule, value, callback) => {
      if (Number.isInteger(Number(value)) && Number(value) > 0) {
        callback()
      } else {
        callback(new Error('续件运费必须为大于0的数'))
      }
    }
    return {
      // form表单的数据
      formData: {
        // 模板名称
        templateName: ``,
        // 配送区域限制信息
        limitParam_limitDeliverArea: false, // 默认其他区域可配送
        limitParam_areaList: `0`,
        limitParam_areaText: `全国（其他地区）`,
        limitParam_firstNum: `1`, // 其他区域运费:几件内
        limitParam_firstFee: `0`, // 其他区域运费:多少元
        limitParam_continueNum: `1`, // 每增加多少件
        limitParam_continueFee: `0` // 增加运费多少
      },
      // 验证规则
      formDataRules: {
        // 模板名称验证
        templateName: [
          { validator: checkTemplateName, trigger: 'blur' }
        ],
        limitParam_firstNum: [
          { validator: checkLimitParamFirstNum, trigger: 'blur' }
        ],
        limitParam_firstFee: [
          { validator: checkLimitParamFirstFee, trigger: 'blur' }
        ],
        limitParam_continueNum: [
          { validator: ckeckLimitParamContinueNum, trigger: 'blur' }
        ],
        limitParam_continueFee: [
          { validator: ckeckLimitParamContinueFee, trigger: 'blur' }
        ]
      },
      checked1: false, // 指定条件包邮（可选）
      // 表格数据
      tableData1: [],
      outerVisible: false,
      dialogStat: false,
      tableData: [
        // { areaText: ``, firstNum: 1, firstFee: 0, continueNum: 1, continueFee: 0 }
      ],
      locatList: [],
      checkeList: [],
      valueA: {},
      firstNum: 1,
      firstFee: 0,
      continueNum: 1,
      continueFee: 0,
      showArr: [], // 展示在表格中的数据
      area_list: [], // 选中的值对应的ID 区域代码
      area_text: `` // 选中的值中文字符串 区域名称

    }
  },
  created () {
    this.fetchAreaData()
  },
  mounted () {
    this.getData()
  },
  computed: {
    formatLimitDeliverArea: function () {
      // `this` 指向 vm 实例
      return this.formData[`limitParam_limitDeliverArea`] ? `1` : `0`
    },
    comput: {
      get () {
        return this.dialogStat
      },
      set () {
        this.dialogStat = true
        return this.dialogStat
      }
    }
  },

  methods: {
    showData (flag) {
      this.dialogStat = flag
    },
    getCheckList (value) {
      console.log(value)
      // 获取的id数组
      this.valueA = value
      this.checkeList = value.checkList // 复选框选中LIst(传回组件用)
      this.showArr = value.showArr
      this.area_text = value.areaList // fag
      // 获取中文拼接字符串，只有省份
      // if (value.areaList.length > 0) {
      //   this.tableData.push(
      //     { area_text: value.areaList.toString(), first_num: this.firstNum, first_fee: this.firstFee, continue_num: this.continueNum, continue_fee: this.continueFee, area_list: value.idList })
      // }
      // console.log(this.tableData)
    },
    // getData
    getData () {
      // console.log(this.locatList)
    },
    //  修改table header的样式
    tableHeaderStyle ({ row, column, rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        return 'background-color: #f5f5f5;color: #333;font-weight: 500;'
      }
    },
    // 指定可配送区域和运费
    handleAdd () {
      this.dialogStat = true
    },
    // 获取省市区数据
    fetchAreaData () {
      getAreaSelect().then(res => {
        const { error, content } = res
        if (error === 0) {
          console.log(content)
          content.unshift({ 'provinceId': 1, 'provinceName': '全选' })
          this.locatList = content
        }
      }).catch(err => console.log(err))
    },
    // 添加模板
    handleAddTemplate () {
      // 验证模板名称是否输入
      if (this.formData[`templateName`] === ``) {
        this.$refs.templateName.focus()
        this.$message({
          message: '模板名称不能为空',
          type: 'warning',
          center: true,
          duration: 1000
          // showClose: true
        })
        return
      }
      // 判断除可配送区域外，其他不可配送的值
      if (this.formatLimitDeliverArea === `0`) {
        // 请求参数
        let params = {
          'templateName': this.formData[`templateName`], // 模板名称
          'goodsDeliverTemplateLimitParam': {
            'limit_deliver_area': this.formatLimitDeliverArea,
            'area_list': this.formData[`limitParam_areaList`],
            'area_text': this.formData[`limitParam_areaText`],
            'first_num': this.formData[`limitParam_firstNum`],
            'first_fee': this.formData[`limitParam_firstFee`],
            'continue_num': this.formData[`limitParam_continueNum`],
            'continue_fee': this.formData[`limitParam_continueFee`]
          }
        }
        // 发送请求
        addTemplate(params).then(res => {
          console.log(res)
        }).catch(err => console.log(err))
      } else {

      }

      // 请求报文参数
      let params = {
        'templateName': '运费模板001',
        'flag': '0',
        'goodsDeliverTemplateLimitParam':
        {
          'limit_deliver_area': '0',
          'area_list': '0',
          'area_text': '全国（其他地区）',
          'first_num': '1',
          'first_fee': '0',
          'continue_num': '1',
          'continue_fee': '0'
        },
        'goodsDeliverTemplateAreaParam':
          [{
            'area_list': '["110000","120000","130000","140000","150000","210000","220000","230000","310000","320000","330000","340000","350000","360000","370000","410000","420000","430000","440000","450000","460000","500000","510000","520000","530000","540000","610000","620000","630000","640000","650000","710000","810000","820000"]',
            'area_text': '北京市、天津市、河北省、山西省、内蒙古自治区、辽宁省、吉林省、黑龙江省、上海市、江苏省、浙江省、安徽省、福建省、江西省、山东省、河南省、湖北省、湖南省、广东省、广西壮族自治区、海南省、重庆市、四川省、贵州省、云南省、西藏自治区、陕西省、甘肃省、青海省、宁夏回族自治区、新疆维吾尔自治区、台湾省、香港特别行政区、澳门特别行政区',
            'first_num': '1',
            'first_fee': '1',
            'continue_num': '1',
            'continue_fee': '1'
          }],
        'goodsDeliverTemplateFeeParam': {
          'has_fee_0_condition': '1'
        },
        'goodsDeliverTemplateFeeConditionParam':
          [{
            'area_list': '0',
            'area_text': '全国（其他地区）',
            'fee_0_condition': '1',
            'fee_0_con1_num': '0',
            'fee_0_con2_num': '10',
            'fee_0_con3_num': '1',
            'fee_0_con3_fee': '0'
          }]
      }
      // console.log(this.templateName)
      console.log(params['goodsDeliverTemplateAreaParam'][0]['area_list'])
      // 请求params
      let formData = {
        'templateName': this.formData[`templateName`], // 模板名称
        'goodsDeliverTemplateLimitParam': {
          'limit_deliver_area': this.formatLimitDeliverArea,
          'area_list': this.formData[`limitParam_areaList`],
          'area_text': this.formData[`limitParam_areaText`],
          'first_num': this.formData[`limitParam_firstNum`],
          'first_fee': this.formData[`limitParam_firstFee`],
          'continue_num': this.formData[`limitParam_continueNum`],
          'continue_fee': this.formData[`limitParam_continueFee`]
        }
      }
      console.log(formData)
    },
    // 编辑每条
    handleEdit (index) {
      console.log(index)
      this.dialogStat = true
    },
    // 删除每条
    handleDel (index) {
      this.tableData.splice(index, 1)
      // console.log(this.tableData)
    }
  }
}
</script>
<style scoped>
.wrap {
  width: 455px;
  height: 50px;
  background-color: #fff7ec;
  border: 1px solid #ffd6a6;
  display: flex;
  align-items: center;
  padding: 10px;
  margin: 20px 0;
}
.designation {
  margin: 10px 0;
}
.add_template {
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
.add {
  border: 1px solid #ddd;
  border-top: 0px solid #ddd;
}
</style>
