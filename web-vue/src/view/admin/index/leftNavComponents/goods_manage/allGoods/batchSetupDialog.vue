<template>
  <div class="batchSetup">
    <el-dialog
      title="商品管理/出售中/批量设置"
      :visible.sync="dialogVisible"
      width="53%"
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
            已选：1件商品<i>最多可批量设置20件商品</i>
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
                      <div>
                        <div class="item_goods_img">
                          <img :src="scope.row.goodsImg">
                        </div>
                        <div class="goods_message">
                          <div class="item_goods_name">{{scope.row.goodsName}}</div>
                          <div class="item_goods_desc">
                            <span></span>
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
                        <span v-if="!scope.row.isDefaultPrd">
                          <template v-if="scope.row.prdMinShopPrice === scope.row.prdMaxShopPrice">
                            {{scope.row.prdMinShopPrice}}
                          </template>
                          <template v-else>
                            {{scope.row.prdMinShopPrice}}~{{scope.row.prdMaxShopPrice}}
                          </template>
                        </span>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="goodsName"
                    label="折扣"
                    align="center"
                  >
                    <template slot-scope="scope">
                      <div>
                        <div class="discount">
                          <el-input
                            v-model="scope.row.discountInputVal"
                            size="small"
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
                            v-model="scope.row.priceRevisionVal"
                            size="small"
                          ></el-input>&nbsp;&nbsp;元
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>

            </div>
          </div>
          <!--右侧动态内容end-->
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="$emit('update:dialogVisible',false)"
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
      nowIndex: 0
    }
  },
  watch: {
    dialogVisible (newData) {
      console.log(newData)
      if (!newData) this.$emit('update:dialogVisible', false)
    },
    checkGoodsData: {
      handler (newData) {
        console.log(newData)
        if (newData.length) {
          newData.forEach(item => {
            item.discountInputVal = ''
            item.priceIncreaseVal = ''
            item.priceRevisionVal = ''
          })
        }
      },
      deep: true
    }
  },
  mounted () {
    console.log(this.dialogVisible)
  },
  methods: {
    handleClickLeftLi (index) {
      console.log(index)
      this.nowIndex = index
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
            .item_goods_img {
              width: 40px;
              height: 40px;
              img {
                width: 100%;
                height: 100%;
              }
            }
            .goods_message {
              width: 120px;
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
