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
              <sortCatTreeSelect
                ref="sortTree"
                :filterGoodsInfo="initSortCatParams"
                treeType="sort"
                :selectedId.sync="sortId"
                :labelStyle="true"
              />
            </div>
            <!--运费模板-->
            <div
              v-if="nowIndex===2"
              class="template"
            >
              <div class="title">
                <div class="name">运费模板：</div>
                <el-select
                  v-model="templateValue"
                  size="small"
                >
                  <el-option
                    v-for="item in templateOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
                <div class="rightOptions">
                  <span @click="handleToClickTemplate(0)">刷新</span>|<span
                    @click="handleToClickTemplate(1)"
                    style="width:80px"
                  >新建模板</span>|<span
                    @click="handleToClickTemplate(2)"
                    style="width:80px"
                  >模板管理</span>
                </div>
              </div>
              <!--选中运费模板后显示模块-->
              <div
                class="hiddenTemplate"
                v-if="templateValue!==null"
              >
                <div class="content">
                  <div class="top">
                    <span>店铺统一运费：0元</span>
                    <span class="toDetail">查看详情</span>
                  </div>
                  <div
                    class="bottomContent"
                    v-if="templateValue===1"
                  >
                    <div class="title">指定条件包邮可配送区域运费:</div>
                    <div class="hiddencontent">太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮</div>
                  </div>
                  <div
                    class="bottomContent"
                    v-if="templateValue===1"
                  >
                    <div class="title">指定条件包邮可配送区域运费:</div>
                    <div class="hiddencontent">：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：满1件包邮太原市、朔州市：</div>
                  </div>
                </div>

              </div>
            </div>
            <!--限购数量-->
            <div
              v-if="nowIndex===3"
              class="limitNum"
            >
              <div class="content">
                <span>最小限购数量：</span>
                <el-input
                  v-model="MinPurchaseInputVal"
                  size="small"
                  onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                ></el-input>
                <i>0或不填表示不限制购买数量</i>
              </div>
              <div
                class="content"
                style="margin-top:10px"
              >
                <span>最大限购数量：</span>
                <el-input
                  v-model="MaxPurchaseInputVal"
                  size="small"
                  onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                ></el-input>
                <i>0或不填表示不限制购买数量</i>
              </div>
            </div>
            <!--上架时间-->
            <div
              v-if="nowIndex===4"
              class="onSaleTime"
            >
              <div class="onSaleTimeLeft">
                上下架：
              </div>
              <div class="onSaleTimeRight">
                <el-radio
                  v-model="onSaleRadio"
                  label="1"
                >不修改</el-radio>
                <el-radio
                  v-model="onSaleRadio"
                  label="2"
                >立即上架售卖</el-radio>
                <div class="custom">
                  <el-radio
                    v-model="onSaleRadio"
                    label="3"
                  >自定义上架售卖</el-radio>
                  <div class="tips">
                    <el-date-picker
                      size="small"
                      v-model="customTime"
                      type="datetime"
                      placeholder="选择日期时间"
                      default-time="12:00:00"
                      format="yyyy-MM-dd HH:mm:ss"
                      value-format="yyyy-MM-dd HH:mm:ss"
                    >
                    </el-date-picker>
                    <span style="margin-left:10px">选择上架售卖时间</span>
                  </div>

                </div>

                <el-radio
                  v-model="onSaleRadio"
                  label="4"
                >暂不售卖，放入仓库</el-radio>
              </div>
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
  components: {
    sortCatTreeSelect: () => import('@/components/admin/sortCatTreeSelect') // 商家分类
  },
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
      innerVisible: false, // 内层
      judgeIsEdit: false, // 判断商品价格是否被修改过
      initSortCatParams: {
        needGoodsNum: true,
        isOnSale: 1,
        isSaleOut: false,
        selectType: 1
      },
      sortId: null,
      templateOptions: [{
        value: null,
        label: '不修改'
      }, {
        value: 0,
        label: '腾飞测试1'
      }, {
        value: 1,
        label: '腾飞测试2'
      }],
      templateValue: null, // 运费模板selectVal
      MinPurchaseInputVal: '', // 最小限购数量
      MaxPurchaseInputVal: '', // 最大限购数量
      onSaleRadio: '1', // 上架时间radio
      customTime: '' // 自定义上架售卖时间
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
        this.judgeIsEdit = false
      }
    },
    customTime (newData) {
      console.log(newData)
    }
  },
  mounted () {
    console.log(new Date().format('yyyy-MM-dd hh:mm:ss'))
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
    },
    // 运费模板右侧文字点击综合处理
    handleToClickTemplate (flag) {
      switch (flag) {
        case 0:

          break
        case 1:

          break
        case 2:

          break
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
          /deep/ .el-form-item__label {
            width: 83px !important;
          }
        }
        .template {
          .title {
            display: flex;
            .name {
              display: flex;
              align-items: center;
            }
            .rightOptions {
              display: flex;
              width: 230px;
              color: #5a8bff;
              align-items: center;
              span {
                display: inline-block;
                width: 60px;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                padding: 0 10px;
              }
            }
          }
          .hiddenTemplate {
            padding: 10px 0 0 70px;
            .content {
              width: 455px;
              background: #f5f5f5;
              padding: 10px 10px;
              max-height: 330px;
              overflow-y: auto;
              .top {
                display: flex;
                justify-content: space-between;
                .toDetail {
                  color: #5a8bff;
                }
              }
              .bottomContent {
                border-top: 1px solid #e6e6e6;
                margin-top: 10px;
                padding-top: 8px;
                .title {
                  margin-bottom: 8px;
                }
                .hiddencontent {
                  line-height: 18px;
                  text-align: justify;
                }
              }
            }
          }
        }
        .limitNum {
          .content {
            display: flex;
            /deep/ .el-input {
              width: 80px;
              margin: 0 10px;
            }
            span,
            i {
              display: flex;
              align-items: center;
            }
            i {
              color: #999;
            }
          }
        }
        .onSaleTime {
          display: flex;
          .onSaleTimeRight {
            display: flex;
            flex-direction: column;
            /deep/ .el-radio {
              margin-bottom: 20px;
            }
            .custom {
              display: flex;
              align-items: center;
              .tips {
                display: flex;
                align-items: center;
                position: relative;
                top: -10px;
              }
            }
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
