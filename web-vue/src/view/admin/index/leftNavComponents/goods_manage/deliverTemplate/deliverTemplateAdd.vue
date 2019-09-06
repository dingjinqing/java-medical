<template>
  <!-- 添加运费模板组件 -->
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
          <el-checkbox v-model="limit_deliver_area">除可配送区域外，其他不可配送</el-checkbox>
        </section>
        <!-- 其他区域运费 -->
        <section
          style="display:flex"
          v-show="!limit_deliver_area"
        >
          <el-form-item prop="goodsDeliverTemplateLimitParam.first_num">
            其他区域运费:
            <el-input
              size="small"
              v-model.number="formData[`goodsDeliverTemplateLimitParam`]['first_num']"
              style="width:80px;"
            ></el-input>
          </el-form-item>
          <el-form-item prop="goodsDeliverTemplateLimitParam.first_fee">
            件内，
            <el-input
              size="small"
              v-model.number="formData[`goodsDeliverTemplateLimitParam`][`first_fee`]"
              style="width:80px;"
            ></el-input>
          </el-form-item>
          <el-form-item prop="goodsDeliverTemplateLimitParam.continue_num">
            元，每增加
            <el-input
              size="small"
              v-model.number="formData[`goodsDeliverTemplateLimitParam`][`continue_num`]"
              style="width:80px;"
            ></el-input>
          </el-form-item>
          <el-form-item prop="goodsDeliverTemplateLimitParam.continue_fee">
            件，增加运费
            <el-input
              size="small"
              v-model.number="formData[`goodsDeliverTemplateLimitParam`][`continue_fee`]"
              style="width:80px;"
            ></el-input>
            元
          </el-form-item>
        </section>
      </el-form-item>
      <!-- 配送区域 -->
      <el-form-item>
        <!-- 配送区域表格 -->
        <section style="border:1px solid black">
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
          <!-- 配送区域弹窗 -->
          <locat-t-p
            :location-list=this.locatList
            :outer-visible=this.dialogStatA
            @close="this.showDataA"
            :inner-obj-j="valueA.innerObj"
            :check-list-t="valueA.checkList"
            @checkList="this.getCheckListA"
          ></locat-t-p>
          <!-- 包邮条件弹窗 -->
          <locat-t-p
            :location-list=this.locatList
            :outer-visible=this.dialogStatB
            @close="this.showDataB"
            :inner-obj-j="valueB.innerObj"
            :check-list-t="valueB.checkList"
            @checkList="this.getCheckListB"
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
          <el-checkbox v-model="formData[`goodsDeliverTemplateFeeParam`][`has_fee_0_condition`]">指定条件包邮（可选）</el-checkbox>
        </section>
        <!-- 当点击指定包邮条件的时候展示的表格 -->
        <section
          class="footer_table"
          v-show="formData[`goodsDeliverTemplateFeeParam`][`has_fee_0_condition`]"
        >
          <el-table
            :data="tableData1"
            border
          >
            <!-- 包邮可配送区域 -->
            <el-table-column
              prop="text"
              label="包邮可配送区域"
              align="left"
            >
              <template slot-scope="scope">
                <div class="">
                  <!-- 展示的中文区域名称 -->
                  <span>{{scope.row.text}}</span>
                  <!-- 编辑 -->
                  <el-button
                    @click="handleEdirArea"
                    type="text"
                  >编辑</el-button>
                  <!-- 删除 -->
                  <el-button
                    @click="handleDelArea"
                    type="text"
                  >删除</el-button>
                </div>
              </template>
            </el-table-column>
            <!-- 设置包邮条件 -->
            <el-table-column
              prop="obj"
              label="设置包邮条件"
              align="left"
            >
              <!-- 每个单元格的的包邮条件 -->
              <template slot-scope="scope">
                <div class="">
                  <!-- 件数 -->

                  <el-select
                    @change="shandleAreaChange($event,scope.$index)"
                    style="width:120px"
                    size="small"
                    v-model="scope.row.obj.value"
                  >
                    <el-option
                      v-for="(item) in scope.row.obj.options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                  <span>满</span>
                  <span v-show="isShowFee0Con1Num">
                    <el-input
                      v-model="scope.row.obj.fee0Con1Num"
                      size="small"
                      style="width:120px"
                    ></el-input>件包邮
                  </span>
                  <span v-show="isShowFee0Con2Fee">
                    <el-input
                      v-model="scope.row.obj.fee0Con2Fee"
                      size="small"
                      style="width:120px"
                    ></el-input>元包邮
                  </span>
                  <span v-show="isShowFee0Con3Num">
                    <el-input
                      v-model="scope.row.obj.fee0Con3Num"
                      size="small"
                      style="width:120px"
                    ></el-input>件
                    <el-input
                      v-model="scope.row.obj.fee0Con3Fee"
                      size="small"
                      style="width:120px"
                    ></el-input>元包邮
                  </span>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <!-- 指定可包邮配送区域和条件按钮 -->
          <section class="addArea">
            <el-button
              type="text"
              @click="handleShipping"
            >指定可包邮配送区域和条件</el-button>
          </section>

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
    // 自定义验证规则/首件的运费为大于0的数
    let checkLimitParamFirstFee = (rule, value, callback) => {
      if (Number(value) > 0) {
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
    // 自定义验证规则/续件运费为大于0的数
    let ckeckLimitParamContinueFee = (rule, value, callback) => {
      if (Number(value) > 0) {
        callback()
      } else {
        callback(new Error('续件运费必须为大于0的数'))
      }
    }
    return {
      // form表单的数据
      limit_deliver_area: false,
      formData: {
        // 模板名称
        templateName: ``,
        // 配送区域限制信息
        goodsDeliverTemplateLimitParam: {
          limit_deliver_area: `0`, // 是否限制区域 // 默认其他区域可配送
          area_list: '0', // 区域代码
          area_text: '全国（其他地区）', // 区域名称
          first_num: '1', // 其他区域运费:几件内
          first_fee: '0', // 其他区域运费:多少元
          continue_num: '1', // 每增加多少件
          continue_fee: '0' // 增加运费多少
        },
        // 配送区域详细信息
        goodsDeliverTemplateAreaParam: [{
          area_list: '', // 区域代码
          area_text: '', // 区域名称
          first_num: '1', // 首件（件）
          first_fee: '0', // 运费（元）
          continue_num: '1', // 续件（件）
          continue_fee: '0' // 续费（元）
        }],
        // 是否包邮信息
        goodsDeliverTemplateFeeParam: {
          has_fee_0_condition: false
        },
        // 包邮条件详细信息
        goodsDeliverTemplateFeeConditionParam: [{
          'area_list': '0', // 区域代码
          'area_text': '全国（其他地区）', // 区域名称
          'fee_0_condition': '1', // 包邮类型（1:件数,2:金额,3:件数+金额）
          'fee_0_con1_num': '0', // 件数包邮类型最低件数
          'fee_0_con2_num': '10', // 金额包邮类型最低金额
          'fee_0_con3_num': '1', // 件数+金额类型最低件数
          'fee_0_con3_fee': '0' // 件数+金额类型最低金额
        }]
      },
      // options
      options: [

      ],
      // 验证规则
      formDataRules: {
        // 模板名称验证
        templateName: [
          { validator: checkTemplateName, trigger: 'blur' }
        ],
        'goodsDeliverTemplateLimitParam.first_num': [
          { validator: checkLimitParamFirstNum, trigger: 'blur' }
        ],
        'goodsDeliverTemplateLimitParam.first_fee': [
          { validator: checkLimitParamFirstFee, trigger: 'blur' }
        ],
        'goodsDeliverTemplateLimitParam.continue_num': [
          { validator: ckeckLimitParamContinueNum, trigger: 'blur' }
        ],
        'goodsDeliverTemplateLimitParam.continue_fee': [
          { validator: ckeckLimitParamContinueFee, trigger: 'blur' }
        ]
      },
      // 指定包邮条件的表格数据
      tableData1: [

      ],
      isShowFee0Con1Num: true,
      isShowFee0Con2Fee: false,
      isShowFee0Con3Num: false,
      outerVisible: false,
      dialogStatA: false,
      dialogStatB: false,
      tableData: [
        // { areaText: ``, firstNum: 1, firstFee: 0, continueNum: 1, continueFee: 0 }
      ],
      locatList: [],
      checkeListA: [],
      checkListB: [],
      valueA: {},
      valueB: {},
      firstNum: 1,
      firstFee: 0,
      continueNum: 1,
      continueFee: 0,
      showArrA: [],
      showArrB: [], // 展示在表格中的数据
      area_list: [], // 选中的值对应的ID 区域代码
      area_text: ``, // 选中的值中文字符串 区域名称
      index: ``

    }
  },
  created () {
    this.fetchAreaData()
  },
  mounted () {
    this.getData()
  },
  computed: {
    // 处理指定条件包邮
    formatHas_fee_0_condition () {
      return this.formData[`goodsDeliverTemplateFeeParam`][`has_fee_0_condition`] ? `1` : `0`
    },
    formatLimitDeliverArea: function () {
      // `this` 指向 vm 实例
      return this.limit_deliver_area ? `1` : `0`
    },
    comput: {
      get () {
        return this.dialogStatB
      },
      set () {
        this.dialogStatB = true
        return this.dialogStatB
      }
    }
  },

  methods: {
    showDataB (flag) {
      this.dialogStatB = flag
    },
    showDataA (flag) {
      this.dialogStatA = flag
    },
    getCheckListA (val) {
      // console.log(val)
      const { idList, showArr, checkList } = val
      this.valueA = val
      this.checkeListA = checkList // 复选框选中LIst(传回组件用)
      this.showArrA = showArr
      this.tableData.push({
        'area_list': idList.toString(),
        'area_text': '北京市、天津市、河北省、山西省、内蒙古自治区、辽宁省、吉林省、黑龙江省、上海市、江苏省、浙江省、安徽省、福建省、江西省、山东省、河南省、湖北省、湖南省、广东省、广西壮族自治区、海南省、重庆市、四川省、贵州省、云南省、西藏自治区、陕西省、甘肃省、青海省、宁夏回族自治区、新疆维吾尔自治区、台湾省、香港特别行政区、澳门特别行政区',
        'first_num': '1',
        'first_fee': '1',
        'continue_num': '1',
        'continue_fee': '1'
      })
    },
    getCheckListB (val) {
      const { areaList, idList, showArr, checkList } = val
      this.valueB = val
      this.checkeListB = checkList // 复选框选中LIst(传回组件用)
      this.showArrB = showArr
      this.tableData1.push(
        {
          'area_list': idList.toString(),
          'area_text': areaList.toString(),
          'list': `10000`,
          'text': showArr.toString(),
          'obj': {
            options: [
              {
                value: `1`,
                label: `件数`
              },
              {
                value: `2`,
                label: `金额`
              },
              {
                value: `3`,
                label: `件数+金额`
              }
            ],
            value: `件数`,
            fee0Con1Num: `1`, // 件数包邮类型最低件数
            fee0Con2Fee: `0`, // 金额包邮类型最低金额
            fee0Con3Num: `1`, // 件数+金额类型最低件数
            fee0Con3Fee: `0` // 件数+金额类型最低金额
          }
        }
      )

      for (let i = 0; i < this.locatList.length; i++) {
        if (val.checkList.findIndex(item => item === this.locatList[i].provinceId) !== -1) {
          this.locatList[i]['state'] = true
        }
        let areaCity = this.locatList[i].areaCity
        if (areaCity !== undefined) {
          for (let j = 0; j < areaCity.length; j++) {
            if (val.checkList.findIndex(item => item === areaCity[j].cityId) !== -1) {
              areaCity[j]['state'] = true
            }
            // this.innerObj[areaCity[j].cityId]=0
            let distn = areaCity[j].areaDistrict
            for (let k = 0; k < distn.length; k++) {
              if (val.checkList.findIndex(item => item === distn[k].districtId) !== -1) {
                distn[k]['state'] = true
              }
            }
          }
        }
      }
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
      // this.dialogStatB = true
    },
    // 获取省市区数据
    fetchAreaData () {
      getAreaSelect().then(res => {
        const { error, content } = res
        if (error === 0) {
          console.log(content)
          localStorage.setItem(`qwq`, JSON.stringify(content))
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
      // 请求参数
      this.formData.goodsDeliverTemplateLimitParam.limit_deliver_area = this.formatLimitDeliverArea
      let params = {
        'templateName': this.formData[`templateName`], // 模板名称
        'goodsDeliverTemplateLimitParam': this.formData.goodsDeliverTemplateLimitParam,
        // 第一个表格的数据
        'goodsDeliverTemplateAreaParam': [
          {

          }
        ],
        'goodsDeliverTemplateFeeParam': {
          'has_fee_0_condition': this.formatHas_fee_0_condition
        },
        // 第二个表格的数据
        'goodsDeliverTemplateFeeConditionParam': []
      }
      console.log(params)
      // 发送请求
      addTemplate(params).then(res => {
        const { error } = res
        if (error === 0) {
          // 添加成功的提示信息
          this.$message({
            message: '添加成功',
            center: true,
            type: 'success',
            duration: 1000
          })
          // 跳转到列表页
          this.$router.push({
            name: `deliverTemplateList`,
            query: { active: `0` }
          })
        }
      }).catch(err => console.log(err))
    },
    // 编辑每条
    handleEdit (index) {
      console.log(index)
    },
    // 删除每条
    handleDel (index) {
      // this.tableData.splice(index, 1)
      // console.log(this.tableData)
    },
    // 指定可包邮配送区域和条件
    handleShipping () {
      this.dialogStatB = true
    },
    // 包邮可配送区域/编辑
    handleEdirArea () {

    },
    // 包邮可配送区域/删除
    handleDelArea () {

    },
    // 包邮条件发生该改变触发的函数
    shandleAreaChange (val, index) {
      switch (val) {
        case `1`: this.isShowFee0Con1Num = true; this.isShowFee0Con2Fee = false; this.isShowFee0Con3Num = true
          break
        case `2`: this.isShowFee0Con1Num = false; this.isShowFee0Con2Fee = true; this.isShowFee0Con3Num = false
          break
        case `3`: this.isShowFee0Con1Num = false; this.isShowFee0Con2Fee = false; this.isShowFee0Con3Num = true
          break
      }
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
.footer_table {
  /* background-color: skyblue; */
  border: 1px solid black;
}
.addArea {
  height: 50px;
  border: 1px solid #e0dedb;
  border-top: 0px;
  display: flex;
  align-items: center;
}
</style>
