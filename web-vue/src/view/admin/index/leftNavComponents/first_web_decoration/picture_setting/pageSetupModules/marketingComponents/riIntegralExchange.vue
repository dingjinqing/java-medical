<template>
  <div class="rightCommodity">
    <div class="rightCommodityMain">
      <h2>积分兑换</h2>
      <!--模块私有区域-->
      <div class="main">
        <div class="listStyle">
          <div class="title">列表样式：</div>
          <el-radio
            v-model="moduleData.list_styles"
            :label="1"
          >单列</el-radio>
          <el-radio
            v-model="moduleData.list_styles"
            :label="2"
          >双列</el-radio>
        </div>
        <div class="listStyle">
          <div class="title">选择活动：</div>
          <div
            class="choose_goods"
            @click="handleToAddAct()"
          >
            <img
              src="http://mpdevimg2.weipubao.cn/image/admin/icon_jia.png"
              alt=""
            >
            添加活动
          </div>
          <div class="question">
            <img :src="$imageHost+'/image/admin/analysis_tishi.png'">
            <div class="float-layer">
              <div
                class="float-layer-i"
                style="left:81px; top:-11px"
              ></div>
              <div>仅可以选择进行中以及未开始的积分兑换活动，将以活动商品形式展示在小程序前端，每个“积分兑换“组件最多可添加6个积分兑换活动。
                对积分兑换活动中商品进行更换时，当前组件商品将同步更新。</div>
            </div>
          </div>
        </div>
        <!--添加活动显示模块-->
        <div
          class="dialogMain"
          v-if="isShowTable"
        >
          <el-table
            ref="multipleTable"
            :data="moduleData.integral_goods"
            class="version-manage-table"
            style="width: 100%"
            header-row-class-name="tableClss"
            border
          >
            <el-table-column
              prop="name"
              label="商品信息"
            >
              <template slot-scope="scope">
                <div class="goodsInfo">
                  <img :src="scope.row.goods_img">
                  <div>{{scope.row.goods_name}}</div>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="stock_sum"
              label="库存"
              width="80"
            >
            </el-table-column>
            <el-table-column
              prop="xianjin"
              label="现金+积分"
            >
              <template slot-scope="scope">
                ￥{{scope.row.money}}+{{scope.row.score}}积分
              </template>
            </el-table-column>
            <el-table-column
              prop="status"
              label="状态"
              width="80"
            >
              <template slot-scope="scope">
                {{scope.row.is_on_sale==='1'?'正常操作':'异常操作'}}
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              width="80"
            >
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="上移"
                  placement="top"
                >
                  <span
                    @click="handleToClickOperation(0,scope.$index)"
                    class="iconfont iconshangyi"
                    style="color: #5A8BFF;cursor: pointer;"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="下移"
                  placement="top"
                >
                  <span
                    @click="handleToClickOperation(1,scope.$index)"
                    class="iconfont iconxiayi"
                    style="color: #5A8BFF;cursor: pointer;"
                  ></span>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="删除"
                  placement="top"
                >
                  <span
                    @click="handleToClickOperation(2,scope.$index)"
                    class="iconfont iconshanchu2"
                    style="color: #5A8BFF;cursor: pointer;"
                  ></span>
                </el-tooltip>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--底部显示内容模块-->
        <div class="listStyle">
          <div class="title">显示内容：</div>
          <el-checkbox v-model="moduleData.show_goods_price">商品原价</el-checkbox>
        </div>
      </div>
      <!--选择活动弹窗-->
      <el-dialog
        title="选择积分活动"
        :visible.sync="dialogVisible"
        width="60%"
        header-row-class-name="tableClss"
        :append-to-body="true"
      >
        <div class="addActDialogMain">
          <div class="dialogTop">
            <div class="key">
              <span>关键词</span>
              <el-input
                v-model="keyInput"
                size="small"
              ></el-input>
            </div>
            <el-select
              v-model="value"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <el-button
              type="primary"
              @click="handleToSearch()"
              size="small"
            >搜索</el-button>
          </div>
          <div class="dialogMain">
            <el-table
              ref="addActTable"
              :data="tableData"
              class="version-manage-table"
              style="width: 100%"
              header-row-class-name="tableClss"
              border
              @selection-change="handleSelectionChange"
            >
              <el-table-column type="selection">
              </el-table-column>
              <el-table-column
                prop="name"
                label="商品信息"
              >
                <template slot-scope="scope">
                  <div class="goodsInfo">
                    <img :src="scope.row.goods_img">
                    <div>{{scope.row.goods_name}}</div>
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                prop="stock_sum"
                label="库存"
                width="80"
              >
              </el-table-column>
              <el-table-column
                prop="prd_price"
                label="价格"
                width="80"
              >
              </el-table-column>
              <el-table-column
                prop="money"
                label="现金"
                width="80"
              >
              </el-table-column>
              <el-table-column
                prop="score"
                label="积分"
                width="80"
              >
              </el-table-column>
              <el-table-column
                prop="start_time"
                label="开始时间"
              >
              </el-table-column>
              <el-table-column
                prop="end_time"
                label="结束时间"
              >
              </el-table-column>
            </el-table>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="handleToSure()"
          >确 定</el-button>
        </span>
      </el-dialog>
      <!--模块私有end-->
    </div>
  </div>
</template>
<script>
export default {
  props: {
    modulesData: Object, // 模块公共
    sortIndex: Number // 模块公共
  },
  data () {
    return {
      dialogVisible: false, //  选择活动弹窗flag
      keyInput: '', // 关键词输入框
      options: [{
        value: '0',
        label: '请选择上下架'
      }, {
        value: '1',
        label: '上架'
      }, {
        value: '2',
        label: '下架'
      }],
      value: '0',
      tableData: [ // 模拟数据
        {
          'goods_id': '1139',
          'integral_goods_id': '175',
          'goods_img': 'http://mpdevimg2.weipubao.cn/upload/4748160/image/20191211/yGJPwQANqKOHCqWS.jpeg',
          'goods_name': '夜间保湿修护唇膜 补水保湿 20克',
          'stock_sum': '4',
          'prd_price': '59.00',
          'money': '10.00',
          'score': '100',
          'start_time': '2019-12-25 14:10:31',
          'end_time': '2020-01-02 14:10:33',
          'is_on_sale': '1',
          'is_delete': '0'
        },
        {
          'goods_id': '1156',
          'integral_goods_id': '176',
          'goods_img': 'http://mpdevimg2.weipubao.cn/upload/4748160/image/20190929/5b641c86N5b3f6ae6.jpg',
          'goods_name': '原味泡芙',
          'stock_sum': '132',
          'prd_price': '200.00',
          'money': '22.00',
          'score': '34',
          'start_time': '2019-12-29 14:51:47',
          'end_time': '2020-01-09 14:51:53',
          'is_on_sale': '1',
          'is_delete': '0'
        }
      ],
      isShowTable: false,
      selectData: [], // 添加活动弹窗选中数据
      moduleData: {
        integral_goods: []
      }
    }
  },
  watch: {
    // 中间模块当前高亮index
    sortIndex: { // 模块公共
      handler (newData) {
        console.log(newData, this.modulesData)
        if (Object.keys(this.modulesData).length) {
          if (this.modulesData) {
            this.$nextTick(() => {
              if (this.modulesData.integral_goods.length) {
                this.isShowTable = true
              }
              this.moduleData = this.modulesData
            })
          }
        }
      },
      immediate: true
    },
    // 监听数据变换
    moduleData: { // 模块公共
      handler (newData) {
        console.log(newData)
        this.$emit('handleToBackData', newData)
      },
      deep: true
    }
  },

  methods: {
    // 点击添加活动
    handleToAddAct () {
      this.dialogVisible = true

      this.$nextTick(() => {
        console.log(this.tableData, this.moduleData.integral_goods)
        let arr = []
        this.tableData.forEach((item, index) => {
          arr.push(item.goods_id)
        })
        console.log(this.moduleData.integral_goods, arr)
        this.$refs.addActTable.clearSelection()
        if (this.moduleData.integral_goods.length) {
          arr.forEach((item, index) => {
            this.moduleData.integral_goods.forEach((itemC, indexC) => {
              if (item === itemC.goods_id) {
                this.$refs.addActTable.toggleRowSelection(this.tableData[index], true)
              }
            })
          })
        }
      })
    },
    // 表格选中
    handleSelectionChange (res) {
      if (res.length) {
        this.isShowTable = true
      } else {
        this.isShowTable = false
      }
      this.selectData = res
      // this.moduleData.integral_goods = res
      console.log(this.moduleData.integral_goods)
    },
    // 添加活动弹窗确定事件
    handleToSure () {
      this.moduleData.integral_goods = this.selectData
      this.dialogVisible = false
    },
    // 搜索点击
    handleToSearch () {

    },
    // 表格操作点击综合处理
    handleToClickOperation (flag, index) {
      let arr = JSON.parse(JSON.stringify(this.moduleData.integral_goods))
      let pre, next, temp
      if ((index - 1) < 0) {
        pre = -1
      } else {
        pre = arr[(index - 1)]
      }
      if ((index + 1) > (arr.length - 1)) {
        next = -1
      } else {
        next = arr[(index + 1)]
      }
      temp = arr[index]
      switch (flag) {
        case 0:
          if (pre === -1) return
          arr[index] = pre
          arr[(index - 1)] = temp
          break
        case 1:
          if (next === -1) return
          arr[index] = next
          arr[(index + 1)] = temp
          break
        case 2:
          arr.splice(index, 1)
          break
      }
      console.log(arr)
      if (!arr.length) this.isShowTable = false
      this.moduleData.integral_goods = arr
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.rightCommodity {
  .rightCommodityMain {
    background: #f8f8f8;
    border: 1px solid #e5e5e5;
    height: 550px;
    overflow-y: auto;
    padding: 10px 2%;
    h2 {
      font-size: 14px;
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
    }
    //模块私有样式  --------------
    .main {
      margin-top: 20px;
      .listStyle {
        display: flex;
        align-items: center;
        .title {
          height: 40px;
          line-height: 40px;
        }
        .choose_goods {
          width: 120px;
          height: 30px;
          line-height: 30px;
          text-align: center;
          color: #5a8bff;
          border: 1px solid #ccc;
          background: #fff;
          cursor: pointer;
        }
        /deep/ .el-radio {
          display: flex;
          align-items: center;
        }
        .question {
          position: relative;
          margin-left: 10px;
          cursor: pointer;
          .float-layer {
            position: absolute;
            top: 35px;
            left: -85px;
            z-index: 9999;
            width: 250px;
            /* height: 130px; */
            line-height: 30px;
            font-size: 14px;
            padding: 15px;
            border: 1px solid #fff;
            word-wrap: break-word;
            word-break: break-all;
            box-shadow: 0 0 20px rgba(150, 150, 150, 0.3);
            border-radius: 5px;
            background: #fff;
            display: none;
            .float-layer-i {
              position: absolute;
              display: inline-block;
              width: 0;
              height: 0;
              border-width: 12px;
              border-style: dashed;
              border-color: transparent;
              border-top-width: 0;
              border-bottom-color: #fff;
              display: inline-block;
              left: 81px;
              top: -11px;
            }
          }
          &:hover {
            .float-layer {
              display: block;
            }
          }
        }
      }
    }

    //end
  }
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  // padding: 8px 10px;
  .el-checkbox {
    margin-left: -4px;
  }
}
/deep/ .el-table-column--selection {
  .cell {
    display: flex;
    justify-content: center;
  }
}
.addActDialogMain {
  .dialogTop {
    display: flex;
    justify-content: flex-start;
    margin-bottom: 10px;
    .key {
      /deep/ .el-input {
        width: 140px;
        margin: 0 30px 0 10px;
      }
    }
    /deep/ .el-select {
      width: 140px;
    }
  }
  .el-button {
    margin-left: 10px;
  }
}
.dialogMain {
  .goodsInfo {
    display: flex;
    img {
      width: 40px;
      height: 40px;
    }
    div {
      flex: 1;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
    }
  }
}
</style>
