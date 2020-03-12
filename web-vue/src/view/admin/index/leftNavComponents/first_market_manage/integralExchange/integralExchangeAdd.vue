<template>
  <div>
    <div
      class="integralExchangeAdd"
      @click="handleToreset"
      :style="(holdToSeeLi||holdToPost )&&formBottom.style==='1'?'padding-bottom: 230px;':''"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item
          label="活动名称："
          prop="name"
        >
          <el-input
            size="small"
            v-model="ruleForm.name"
          ></el-input>
          <span class="tips">只作为商家记录使用，用户不会看到这个名称</span>
        </el-form-item>
        <el-form-item
          label="有效期："
          required
        >
          <div class="date">
            <el-form-item prop="customTime">
              <el-date-picker
                size="small"
                v-model="ruleForm.customTime"
                type="datetime"
                :placeholder="$t('allGoods.batchDialog.selectDateTime')"
                default-time="12:00:00"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
            </el-form-item>
            &nbsp;至&nbsp;
            <el-form-item prop="customTimeEnd">
              <el-date-picker
                size="small"
                v-model="ruleForm.customTimeEnd"
                type="datetime"
                :placeholder="$t('allGoods.batchDialog.selectDateTime')"
                default-time="12:00:00"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
            </el-form-item>
          </div>

        </el-form-item>
        <el-form-item
          label="单个用户最多可兑换数量："
          prop="maxExchangeNum"
        >
          <el-input
            size="small"
            v-model="ruleForm.maxExchangeNum"
          ></el-input>
          <span class="tips">填0则不限制</span>
        </el-form-item>
        <el-form-item
          label="添加商品："
          prop="checkGoods"
        >
          <div>
            <span
              class="addGoods"
              @click="handleToChooseGoods()"
            >
              + 选择商品
            </span>

          </div>
        </el-form-item>
        <el-form-item
          label="积分兑换设置："
          prop="checkGoods"
        >
        </el-form-item>
      </el-form>
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        :span-method="arraySpanMethod"
        style="width: 100%"
      >
        <el-table-column
          prop="goodsName"
          label="商品名称/规格"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="originPrice"
          label="原价（元）"
          align="center"
        >
          <template slot-scope="scope">
            <div :class="scope.$index===(tableData.length-1)?'batchSetup':''">
              {{scope.$index===(tableData.length-1)?'':scope.row.originPrice}}
              <div v-if="scope.$index===(tableData.length-1)">
                <span
                  class="batchSpan"
                  @click.stop="handleToClick(1)"
                  :style="batchFlag===1?'color: #606266':''"
                >商品兑换价格</span>
                <span
                  class="batchSpan"
                  @click.stop="handleToClick(2)"
                  :style="batchFlag===2?'color: #606266':''"
                >商品兑换库存</span>
              </div>
            </div>

          </template>
        </el-table-column>
        <el-table-column
          prop="exchange"
          label="商品兑换价格"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="goodsStock"
          label="商品库存"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="stock"
          label="兑换商品库存"
          align="center"
        >
        </el-table-column>
      </el-table>
      <div class="showMore">
        <span @click="showMoreFlag = !showMoreFlag">{{showMoreFlag?'收起更多配置':'展开更多配置'}}</span>
        <img :src="showMoreFlag?($imageHost+'/image/admin/info_up.png'):($imageHost+'/image/admin/info_down.png')">
      </div>
      <el-form
        :model="formBottom"
        :rules="rulesBottom"
        ref="formBottom"
        label-width="100px"
        class="demo-ruleForm"
        v-if="showMoreFlag"
      >
        <el-form-item
          label="店铺分享："
          prop="style"
          required
        >
          <div class="hiddleShare">
            <div class="top">
              <el-radio
                v-model="formBottom.style"
                label="1"
              >默认样式</el-radio>
              <a
                href="javascript:;"
                class="show_eg"
                :class="holdToSeeLi?'holdToSeeLi':''"
                :style="hoverFlag===1?'color:#333':''"
                @mouseover="mouseover(1)"
                @mouseleave="mouseleave(1)"
                @click.stop="hoverFlag=1"
              >查看示例
                <div
                  class="hover_show"
                  style="top: -31px;"
                >
                  <img :src="$imageHost+'/image/admin/share/integral_share_new.jpg'">
                </div>
              </a>
              <a
                @click.stop="hoverFlag=0"
                :style="hoverFlag===0?'color:#333':''"
                href="javascript:;"
                class="show_eg"
                :class="holdToPost?'holdToSeeLi':''"
                @mouseover="mouseover(0)"
                @mouseleave="mouseleave(0)"
              >下载海报
                <div
                  class="hover_show"
                  style="top: -31px;"
                >
                  <img :src="$imageHost+'/image/admin/share/integral_pictorial.jpg'">
                </div>
              </a>
            </div>
            <div class="bottom">
              <el-radio
                v-model="formBottom.style"
                label="2"
              >自定义样式</el-radio>
            </div>
            <div
              class="cumtom"
              v-if="formBottom.style==='2'"
            >
              <span>文案：</span>
              <el-input
                size="small"
                v-model="copywriting"
              ></el-input>
            </div>
            <div
              class="cumtom"
              v-if="formBottom.style==='2'"
            >
              <span>分享图：</span>
              <div class="shareImg">
                <div>
                  <el-radio
                    v-model="sharedGraph"
                    label="1"
                  >活动商品信息图</el-radio>
                </div>
                <div>
                  <el-radio
                    v-model="sharedGraph"
                    label="2"
                  >自定义图片</el-radio>
                </div>
              </div>
            </div>
            <!--选择图片-->
            <div
              class="addImg"
              v-if="sharedGraph==='2'&&formBottom.style==='2'"
            >
              <div
                class="img"
                :style="`background:url(${$imageHost}/image/admin/btn_add.png) no-repeat`"
              >

              </div>
              <span>建议尺寸: 800*800像素</span>
            </div>
          </div>

        </el-form-item>
      </el-form>
      <!--保存-->
      <div class="footer">
        <div>
          <el-button
            type="primary"
            size="small"
            @click="handleToClickSave()"
          >保存</el-button>
        </div>
      </div>
    </div>
    <!--选择商品弹窗-->
    <ChoosingGoods
      :loadProduct="true"
      :singleElection="true"
      :tuneUpChooseGoods="chooseFlag"
      :chooseGoodsBack="chooseGoodsBack"
      @resultGoodsRow="resultGoodsRow"
    />
  </div>
</template>
<script>
import { goodsSpecDetail } from '@/api/admin/marketManage/integralExchange'
export default {
  components: {
    ChoosingGoods: () => import('@/components/admin/choosingGoods') // 选择商品弹窗
  },
  data () {
    return {
      chooseFlag: false, // 选择商品弹窗flag
      chooseGoodsBack: [], // 选择商品回显
      sharedGraph: '1', // 分享图radio值
      copywriting: '', // 文案
      hoverFlag: -1, // 查看示例和下载海报点击flag
      holdToPost: false, // 下载海报flag
      holdToSeeLi: false, // 查看示例flag
      batchFlag: -1, // 点击批量设置子项
      showMoreFlag: false, // 展开flag
      tableData: [// 积分兑换设置表格数据
        {
          goodsName: '腾飞测试',
          originPrice: '100',
          exchange: {
            'money': '100',
            'score': '50'
          },
          goodsStock: '20',
          stock: '2'
        },
        {
          goodsName: '批量设置：',
          originPrice: '1',
          exchange: '',
          goodsStock: '',
          stock: ''
        }
      ],
      ruleForm: { // 顶部表格数据
        name: '', // 活动名称
        customTime: '', // 有效期开始时间
        customTimeEnd: '', // 有效期结束时间
        maxExchangeNum: 1, // 单个用户最多可兑换数量
        checkGoods: '' // 选择的商品
      },
      rules: {// 顶部表格数据校验
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' }
        ],
        customTime: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        customTimeEnd: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        maxExchangeNum: [
          { required: true, message: '请输入单个用户最多可兑换数量', trigger: 'blur' }
        ],
        checkGoods: [
          { required: true, message: '请选择商品', trigger: 'blur' }
        ]
      },
      formBottom: {
        style: '1'
      },
      rulesBottom: {

      }
    }
  },
  methods: {
    // 点击保存
    handleToClickSave () {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 表格末行合并处理
    arraySpanMethod ({ row, column, rowIndex, columnIndex }) {
      console.log(row, column, rowIndex, columnIndex)
      if (rowIndex === this.tableData.length - 1) {
        if (columnIndex === 1) {
          return [1, 4]
        } else if (columnIndex === 2) {
          return [0, 0]
        } else if (columnIndex === 3) {
          return [0, 0]
        } else if (columnIndex === 4) {
          return [0, 0]
        }
      }
    },
    handleToClick (flag) { // 点击批量设置子项
      this.batchFlag = flag
    },
    handleToreset () { // 重置批量设置子项
      console.log('触发')
      this.batchFlag = -1
      this.hoverFlag = -1
    },
    mouseover (flag) { // 滑过查看示例
      if (flag) {
        this.holdToSeeLi = true
      } else {
        this.holdToPost = true
      }
    },
    mouseleave (flag) { // 滑出查看示例
      this.holdToSeeLi = false
      this.holdToPost = false
    },
    handleToChooseGoods () { // 调起选择商品弹窗
      this.chooseFlag = !this.chooseFlag
    },
    resultGoodsRow (res) { // 选中商品弹窗回传数据
      console.log(res)
      goodsSpecDetail({ goodsId: res.goodsId }).then(res => {
        console.log(res)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.integralExchangeAdd {
  padding: 8px 23px;
  background: #ffffff;
  min-height: 500px !important;
  padding-top: 20px;
  padding-bottom: 90px;
  /deep/ .el-input {
    width: auto;
  }
  /deep/ .el-form-item__label {
    width: 195px !important;
    text-align: right;
    white-space: nowrap;
  }
  .date {
    display: flex;
    /deep/ .el-input__inner {
      width: 188px;
    }
    /deep/ .el-form-item {
      margin-bottom: 0 !important;
    }
  }
  .tips {
    font-size: 12px;
    color: #999;
  }
  .addGoods {
    display: inline-block;
    width: 120px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    background-color: #fff;
    color: #5a8bff;
    border: 1px solid #ddd;
    cursor: pointer;
  }
  /deep/ .el-table__header {
    background-color: #f5f5f5;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  /deep/ .el-table--enable-row-hover .el-table__body tr:hover > td {
    background-color: #fff !important;
  }
  .batchSetup {
    display: flex;
    justify-content: flex-start;
    cursor: pointer;
    .batchSpan {
      color: #5a8bff;
      &:hover {
        color: #606266;
      }
    }
  }
  /deep/ .el-form-item {
    margin-bottom: 16px;
  }
  .showMore {
    padding-left: 91px;
    color: #5a8bff;
    margin-top: 10px;
    span {
      color: #5a8bff;
      cursor: pointer;
    }
  }
  .hiddleShare {
    padding-left: 100px;
    .top {
      .show_eg {
        display: inline-block;
        position: relative;
        padding-right: 5px;
        color: #5a8bff;
        text-decoration: none;
        .hover_show {
          position: absolute;
          left: 68px;
          padding: 20px;
          background-color: #fff;
          border-radius: 5px;
          display: none;
          z-index: 3;
          box-shadow: 1px 1px 10px 5px #eee;
          img {
            width: 240px;
            height: 355.74px;
            border: 1px solid #eee;
          }
        }
        .hover_show:before {
          content: " ";
          position: absolute;
          top: 48px;
          left: -7px;
          width: 14px;
          height: 14px;
          background-color: #fff;
          transform: rotate(-45deg);
          z-index: 4;
          box-shadow: -3px -3px 3px #e5e5e5;
        }
      }
      .holdToSeeLi {
        color: #5a8bff;
        .hover_show {
          display: block;
        }
      }
    }
  }
  .cumtom {
    display: flex;
  }
  .addImg {
    padding-left: 58px;
    display: flex;
    .img {
      width: 70px;
      height: 70px;
      cursor: pointer;
    }
    span {
      display: flex;
      align-items: center;
      justify-content: flex-start;
      margin-left: 20px;
    }
  }
  .footer {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: fixed;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
  }
}
</style>
