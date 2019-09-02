<template>
  <!-- 添加运费模板组件 -->
  <div class="deliverTemplateAdd">
    <el-form
      ref="form"
      :model="formData"
      label-width="120px"
    >
      <el-form-item
        label="模板名称："
        class="name"
      >
        <!-- 模板名称 -->
        <section>
          <el-input
            v-model="templateName"
            size="small"
            style="width:210px"
          ></el-input>
        </section>
        <!-- 橙黄框 -->
        <section class="wrap">
          <el-checkbox v-model="checked">除可配送区域外，其他不可配送</el-checkbox>
        </section>
        <!-- 其他区域运费 -->
        <section
          class="other"
          v-show="!checked"
        >
          其他区域运费:
          <el-input
            size="small"
            v-model="piece"
            style="width:50px;"
          ></el-input>
          件内，
          <el-input
            size="small"
            v-model="money"
            style="width:50px;"
          ></el-input>
          元，每增加
          <el-input
            size="small"
            v-model="addPiece"
            style="width:50px;"
          ></el-input>
          件，增加运费
          <el-input
            size="small"
            v-model="addMoney"
            style="width:50px;"
          ></el-input>
          元
        </section>
      </el-form-item>
      <!-- 配送区域 -->
      <el-form-item>
        <!-- 配送区域表格 -->
        <section>
          <el-table
            :data="tableData"
            border
            style="width: 100%"
            header-align="center"
            :header-cell-style="tableHeaderStyle"
          >
            <!-- 可配送区域 -->
            <el-table-column
              prop="area"
              label="可配送区域"
              align="center"
            >
              <template slot-scope="scope">
                <el-button
                  type="text"
                  @click="dialogStat=true"
                >{{ scope.row.areaText}}
                </el-button>
              </template>
            </el-table-column>
            <!-- 首件（件） -->
            <el-table-column
              prop="price"
              label="首件（件）"
              align="center"
            >
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.firstNum"
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
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.firstFee"
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
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.continueNum"
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
              <template slot-scope="scope">
                <el-input
                  v-model="scope.row.continueFee"
                  size="small"
                ></el-input>
              </template>
            </el-table-column>
          </el-table>
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
    <!-- 指定运费模板省市区三级联动 弹窗-->
    <locat-t-p
      :location-list=this.locatList
      :outer-visible=this.dialogStat
      @close="this.showData"
      @checkList="this.getCheckList"
    ></locat-t-p>
  </div>
</template>
<script>
// 引入省市区三级联动
import LocatTP from '@/components/admin/areaLinkage/LocatTP'
// import areaLinkage from '@/components/admin/areaLinkage/areaLinkage'
// api

import { addTemplate, getAreaSelect } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate'
export default {
  name: 'templateAdd',
  components: { LocatTP },
  created () {
    this.fetchAreaData()
  },
  mounted () {
    this.getData()
  },
  computed: {
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
  data () {
    return {
      formData: {},
      // 模板名称
      templateName: ``,
      // 其他区域运费:几件内
      piece: 1,
      // 其他区域运费:多少元
      money: 0,
      // 每增加多少件
      addPiece: 1,
      // 增加运费多少
      addMoney: 0,
      checked: false, // 默认其他区域可配送
      checked1: false, // 指定条件包邮（可选）
      // 表格数据
      tableData1: [],
      outerVisible: false,
      dialogStat: false,
      tableData: [
        { areaText: '指定配送区域和运费', firstNum: 1, firstFee: 0, continueNum: 1, continueFee: 0 }
      ],
      locatList: [],
      checkeList: []

    }
  },
  methods: {
    showData (flag) {
      this.dialogStat = flag
    },
    getCheckList (value) {
      this.checkeList = value
      // console.log(value)
      let str = ''
      for (let i = 0; i < this.checkeList.length; i++) {
        for (let j = 0; j < this.locatList.length; j++) {
          if (this.checkeList[i] !== 0) {
            if (this.checkeList[i] === this.locatList[j].provinceId) {
              str += this.locatList[j].provinceName
              str += ','
            }
          }
        }
      }
      this.tableData = [
        { area: str }
      ]
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
      this.outerVisible = true
    },
    // 获取省市区数据
    fetchAreaData () {
      getAreaSelect().then(res => {
        const { error, content } = res
        if (error === 0) {
          content.unshift({ 'provinceId': 1, 'provinceName': '全选' })
          this.locatList = content
        }
      }).catch(err => console.log(err))
    },
    // 添加模板
    handleAddTemplate () {
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
      console.log(params)
      let obj = {
        'templateName': this.templateName,
        'goodsDeliverTemplateLimitParam': {
          'limit_deliver_area': '0',
          'area_list': '0',
          'area_text': '全国（其他地区）',
          'first_num': '1',
          'first_fee': '0',
          'continue_num': '1',
          'continue_fee': '0'
        }
      }
      addTemplate(obj).then(res => {
        // console.log(res)
      }).catch(err => console.log(err))
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
