<template>
  <div class="batchSetup">
    <el-dialog
      title="商品管理/出售中/批量设置"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      width="53%"
      :before-close="handleToCloseDialog"
    >
      <div class="batchSetupMain">
        <ul class="left">
          <li
            v-for="(item,index) in liData"
            :key="index"
            @click="handleClickLeftLi(index)"
          >
            <span :class="nowIndex===index?'hover':''">{{item}}</span>
          </li>
        </ul>
        <div class="right">
          <div class="br_title">
            已选：{{checkGoodsData.length}}件商品<i>最多可批量设置20件商品</i>
          </div>
          <!--右侧动态内容-->
          <div class="dynamic">
            <!--商品价格-->
            <div
              v-if="nowIndex===0"
              class="price"
            >
              <div class="title">批量设置：<i>折扣</i><i>涨/降价</i><i>改价金额</i><span>填写"负值"即降价</span></div>
              <div class="bottom">
                <el-table
                  class="version-manage-table"
                  header-row-class-name="tableClss"
                  :data="checkGoodsData"
                  border
                  style="width: 100%"
                >
                  <el-table-column
                    prop="goodsName"
                    label="商品规格"
                    align="center"
                    width="160"
                  >
                    <template slot-scope="scope">
                      <div class="goodsDiv">
                        <div class="item_goods_img">
                          <img :src="scope.row.goodsImg">
                        </div>
                        <div class="goods_message">
                          <div class="item_goods_name">{{scope.row.goodsName}}</div>
                          <div class="item_goods_desc">
                            <span
                              v-for="(item,index) in scope.row.goodsSpecProducts"
                              :key="index"
                            >{{item.prdSpecs}}{{index!==scope.row.goodsSpecProducts.length-1?';':''}}</span>
                          </div>
                        </div>

                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="shopPrice"
                    label="原价"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <div>
                        <span>
                          <!-- <template v-if="scope.row.prdMinShopPrice === scope.row.prdMaxShopPrice">
                            {{scope.row.prdMinShopPrice}}
                          </template>
                          <template v-else>
                            {{scope.row.prdMinShopPrice}}~{{scope.row.prdMaxShopPrice}}
                          </template> -->
                          <template>
                            {{scope.row.prdMinShopPrice.toFixed(2)}}
                          </template>
                        </span>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="折扣"
                    align="center"
                    prop="discountInputVal"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            v-model="scope.row.discountInputVal"
                            size="small"
                            @blur="handleToBlur(scope,0)"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                          ></el-input>&nbsp;&nbsp;折
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="goodsName"
                    label="涨/降价"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            v-model="scope.row.priceIncreaseVal"
                            size="small"
                            @blur="handleToBlur(scope,1)"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                          ></el-input>&nbsp;&nbsp;元
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="goodsName"
                    label="改价金额"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            @blur="handleToBlur(scope,2)"
                            v-model="scope.row.priceRevisionVal"
                            size="small"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                          ></el-input>&nbsp;&nbsp;元
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>

            </div>
            <!--商家分类-->
            <div
              v-if="nowIndex===1"
              class="classification"
            >
              <div class="name">商家分类：</div>
              <el-select
                size="small"
                v-model="classificationValue"
              >
                <el-option
                  v-for="item in classifiOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </div>
          </div>
          <!--右侧动态内容end-->
        </div>
      </div>
      <!--编辑离开提示弹窗-->
      <el-dialog
        width="30%"
        title="提醒"
        :visible.sync="innerVisible"
        append-to-body
      >
        <span style="width:100%;text-align:center;display: block">确定要退出编辑吗？</span>
        <div slot="footer">
          <el-button @click="innerVisible = false">取 消</el-button>
          <el-button @click="handleToCloseInnerDialog()">确定</el-button>
        </div>
      </el-dialog>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="handleToSave()"
        >保存</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    checkGoodsData: { // 已选中的商品
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      liData: ['商品价格', '商家分类', '运费模板', '限购数量', '上架时间', '商品详情', '商品标签', '商品品牌', '会员专属', '发货地'],
      nowIndex: 0,
      classificationValue: -1, // 商家分类下拉框value
      classifiOptions: [{
        value: -1,
        label: '请选择商家分类'
      }, {
        value: 0,
        label: '腾飞测试1'
      }, {
        value: 1,
        label: '腾飞测试2'
      }],
      innerVisible: false, // 内层
      judgeIsEdit: false // 判断商品价格是否被修改过
    }
  },
  watch: {
    dialogVisible (newData) {
      console.log(newData)
      if (newData) {
        console.log(this.checkGoodsData)
        this.checkGoodsData.forEach((item, index) => {
          this.$set(this.checkGoodsData[index], 'priceRevisionVal', '')
          this.$set(this.checkGoodsData[index], 'priceIncreaseVal', '')
          this.$set(this.checkGoodsData[index], 'discountInputVal', '')
        })
      }
    }
  },
  mounted () {
    console.log(this.dialogVisible)
  },
  methods: {
    // 内层判断是否编辑弹窗确认事件
    handleToCloseInnerDialog () {
      this.innerVisible = false
      this.$emit('update:dialogVisible', false)
    },
    // 弹窗左侧点击
    handleClickLeftLi (index) {
      console.log(index)
      this.nowIndex = index
    },
    // 商品价格输入框失焦
    handleToBlur (scope, flag) {
      console.log(scope, flag)
      let initPrice = scope.row.shopPrice
      console.log(scope)
      let reg = /[^\d.]/g
      switch (flag) {
        case 0:
          if (reg.test(scope.row.discountInputVal)) return
          this.judgeIsEdit = true // 判断是否编辑过
          let nowPrice = (initPrice * (Number(scope.row.discountInputVal) / 10)).toFixed(2)
          this.$set(this.checkGoodsData[scope.$index], 'priceRevisionVal', nowPrice)
          this.$set(this.checkGoodsData[scope.$index], 'priceIncreaseVal', (nowPrice - initPrice).toFixed(2))
          break
        case 1:
          if (reg.test(scope.row.priceIncreaseVal)) return
          this.judgeIsEdit = true// 判断是否编辑过
          let nowPrice2 = (initPrice + Number(scope.row.priceIncreaseVal)).toFixed(2)
          this.$set(this.checkGoodsData[scope.$index], 'priceRevisionVal', nowPrice2)
          this.$set(this.checkGoodsData[scope.$index], 'discountInputVal', ((nowPrice2 / initPrice) * 10).toFixed(2))
          break
        case 2:
          if (reg.test(scope.row.priceRevisionVal)) return
          this.judgeIsEdit = true// 判断是否编辑过
          let nowPrice3 = (Number(scope.row.priceRevisionVal) - initPrice).toFixed(2)
          this.$set(this.checkGoodsData[scope.$index], 'priceIncreaseVal', nowPrice3)
          this.$set(this.checkGoodsData[scope.$index], 'discountInputVal', ((Number(scope.row.priceRevisionVal) / initPrice) * 10).toFixed(2))
          break
      }

      console.log(scope.row)
    },
    // 保存点击
    handleToSave () {
      console.log('触发')
      this.$emit('update:dialogVisible', false)
    },
    // 父弹窗右上角点击关闭icon
    handleToCloseDialog () {
      console.log('2222')
      if (this.judgeIsEdit) {
        this.innerVisible = true
      } else {
        this.$emit('update:dialogVisible', false)
      }
    }
  }
}
</script>
<style scoped lang="scss">
.batchSetup {
  /deep/ .el-dialog__header {
    text-align: left;
  }
  /deep/ .el-dialog__body {
    padding: 0;
  }
  .batchSetupMain {
    height: 100%;
    display: flex;
    .left {
      width: 100px;
      border-right: 1px solid #eee;
      font-size: 14px;
      li {
        text-align: center;
        margin-top: 20px;
        span {
          display: inline-block;
          padding-bottom: 10px;
          text-decoration: none;
          color: #333;
          border-bottom: 1px solid #fff;
          font-size: 14px;
          cursor: pointer;
          &:hover {
            color: #5a8bff;
          }
        }
      }
      .hover {
        color: #5a8bff;
        border-bottom: 1px solid #5a8bff;
      }
    }
    .right {
      width: 100%;
      padding: 10px 20px 0 20px;
      .br_title {
        background: #f5f5f5;
        color: #333;
        font-size: 13px;
        margin-bottom: 20px;
        height: 40px;
        line-height: 40px;
        width: 96%;
        padding-left: 10px;
        i {
          color: #999;
          margin-left: 30px;
        }
      }
      .dynamic {
        width: 100%;
        height: 100%;
        // padding-bottom: 60px;
        .price {
          height: 100%;
          .title {
            margin-bottom: 20px;
            i {
              color: #5a8bff;
              margin-right: 10px;
            }
            span {
              color: #999;
            }
          }
          .bottom {
            height: 337px;
            overflow-y: auto;
            .goodsDiv {
              display: flex;
              .item_goods_img {
                width: 40px;
                height: 40px;
                margin-right: 5px;
                img {
                  width: 100%;
                  height: 100%;
                }
              }
              .goods_message {
                width: 90px;
                height: 40px;
                display: flex;
                flex-direction: column;
                justify-content: space-around;
                text-align: left;
                font-size: 13px;
                .item_goods_name {
                  width: 100%;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                  overflow: hidden;
                  color: #000;
                }
                .item_goods_desc {
                  color: #666;
                  text-overflow: ellipsis;
                  overflow: hidden;
                  white-space: nowrap;
                  width: 100%;
                  font-size: 12px;
                }
              }
            }

            .discount {
              display: flex;
              align-items: center;
              /deep/ .el-input {
                width: 80px;
              }
            }
          }
          /deep/ .tableClss th {
            background-color: #f5f5f5;
            border: none;
            height: 36px;
            font-weight: bold;
            color: #000;
            padding: 8px 10px;
          }
        }
        .classification {
          display: flex;
          .name {
            display: flex;
            align-items: center;
          }
        }
      }
    }
  }
  .dialog-footer {
    display: block;
    width: 100%;
    text-align: center;
  }
}
</style>
