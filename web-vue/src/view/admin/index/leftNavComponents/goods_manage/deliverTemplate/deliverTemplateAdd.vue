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
          <!-- 配送区域弹窗 -->
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
                    @click="handelEdirArea"
                    type="text"
                  >编辑</el-button>
                  <!-- 删除 -->
                  <el-button
                    @click="handelDelArea"
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
                    @click="shandleAreaChange"
                    style="width:120px"
                    size="small"
                    v-model="scope.row.obj.value"
                  >
                    <el-option
                      v-for="item in scope.row.obj.fee0Condition"
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
          <!-- 包邮条件弹窗 -->
          <locat-t-p
            :location-list=this.locatList
            :outer-visible=this.dialogStat
            @close="this.showData"
            :inner-formData-j="valueA.innerformData"
            :check-list-t="valueA.checkList"
            @checkList="this.getCheckList1"
          ></locat-t-p>
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
          has_fee_0_condition: true
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
    getCheckList1 (val) {
      console.log(val)
      const { areaList, idList, showArr, checkList } = val
      this.valueA = val
      this.checkeList = checkList // 复选框选中LIst(传回组件用)
      this.showArr = showArr
      console.log(areaList)
      console.log(idList)
      console.log(showArr)

      console.log(this.locatList)
      // let arr1 = Array.from(new Set(idList))
      // let arr2 = Array.from(new Set(areaList))
      // let arr3 = Array.from(new Set(showArr))
      // console.log(arr1)
      this.tableData1.push(
        {
          'area_list': idList.toString(),
          'area_text': areaList.toString(),
          'list': `10000`,
          'text': showArr.toString(),
          'obj': {
            fee0Condition: [
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

      console.log(this.tableData1)
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
    getCheckList (value) {
      console.log(value)
      // 获取的id数组
      this.valueA = value
      this.checkeList = value.checkList // 复选框选中LIst(传回组件用)
      this.showArr = value.showArr
      this.area_text = value.areaList // fag

      if (value.areaList.length > 0) {
        for (let i = 0; i < value.areaList.length; i++) {
          this.formData.goodsDeliverTemplateAreaParam[i].area_list = i + 1
          this.formData.goodsDeliverTemplateAreaParam[i].area_text = '测试'
        }
      }
      console.log('goodsDeliverTemplateAreaParam:', this.formData.goodsDeliverTemplateAreaParam)
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
          // console.log(content)
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
        // this.formData.goodsDeliverTemplateLimitParam.limit_deliver_area = this.formatLimitDeliverArea
        // 请求参数
        let params = {
          'templateName': this.formData[`templateName`], // 模板名称
          'goodsDeliverTemplateLimitParam': this.formData.goodsDeliverTemplateLimitParam
          // 'goodsDeliverTemplateAreaParam': this.formData.goodsDeliverTemplateAreaParam
        }
        console.log(params)
        // 发送请求
        addTemplate(params).then(res => {
          const { error } = res
          if (error === 0) {
            this.$router.push({
              name: `deliverTemplateList`
            })
          }
        }).catch(err => console.log(err))
      } else if (this.formData.goodsDeliverTemplateAreaParam.length > 0) {
        this.formData.goodsDeliverTemplateLimitParam.limit_deliver_area = this.formatLimitDeliverArea
        // 请求参数
        let params = {
          'templateName': this.formData[`templateName`], // 模板名称
          'goodsDeliverTemplateLimitParam': this.formData.goodsDeliverTemplateLimitParam,
          'goodsDeliverTemplateAreaParam': this.formData.goodsDeliverTemplateAreaParam
        }
        // 发送请求
        addTemplate(params).then(res => {
          console.log(res)
        }).catch(err => console.log(err))
      } else {

      }
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
    },
    // 指定可包邮配送区域和条件
    handleShipping () {
      this.dialogStat = true
    },
    // 包邮可配送区域/编辑
    handelEdirArea () {

    },
    // 包邮可配送区域/删除
    handelDelArea () {

    },
    // 包邮条件发生该改变触发的函数
    shandleAreaChange (val) {
      console.log(val)
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
