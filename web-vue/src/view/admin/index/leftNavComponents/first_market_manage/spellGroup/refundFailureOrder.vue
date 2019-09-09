<!--
* 拼团退款失败订单页面
*
* @author:赵鑫
-->
<template>
  <div class="refundFailureOrder">
    <wrapper class="refundFailureOrder_main">
      <ul>
        <li class="liItem">
          <div class="liNav">
            <span>商品名称</span>
            <el-input
              placeholder="商品名称"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
          <div class="liNav">
            <span>订单号</span>
            <el-input
              placeholder="订单号"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
          <div class="liNav">
            <span>订单状态</span>
            <el-select
              size="small"
              v-model="orderNumber"
              placeholder="请选择订单状态"
              class="inputWidth"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav">
            <span>收货人姓名</span>
            <el-input
              placeholder="收货人姓名"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
          <div class="liNav">
            <span>收货人手机号</span>
            <el-input
              placeholder="收货人手机号"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav date">
            <span>下单时间</span>
            <el-date-picker
              class="pickerWidth"
              size="small"
              v-model="value1"
              type="datetimerange"
              range-separator="至"
              start-placeholder="下单时间"
              end-placeholder="下单时间"
            >
            </el-date-picker>
          </div>
          <div
            class="liNav"
            style="margin-left:180px"
          >
            <span>配送方式</span>
            <el-select
              size="small"
              v-model="orderNumber"
              placeholder="请选择配送方式"
              class="inputWidth"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
        </li>
      </ul>
      <ul
        class="uls"
        v-if='!arrorFlag'
      >
        <li class="liItem">
          <div class="liNav">
            <span>买家昵称</span>
            <el-input
              placeholder="买家昵称"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
          <div class="liNav">
            <span>买家来源</span>
            <el-select
              size="small"
              v-model="orderNumber"
              placeholder="请选择买家来源"
              class="inputWidth"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
          <div class="liNav">
            <span>核销码</span>
            <el-input
              placeholder="核销码"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav">
            <span>门店</span>
            <el-select
              size="small"
              v-model="orderNumber"
              placeholder="请选择门店"
              class="inputWidth"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
          <div class="liNav">
            <span>标签</span>
            <el-select
              size="small"
              multiple-limit=3
              v-model="orderNumber"
              placeholder="最多选择3个标签"
              class="inputWidth"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav date">
            <span>完成时间</span>
            <el-date-picker
              class="pickerWidth"
              size="small"
              v-model="value1"
              type="datetimerange"
              range-separator="至"
              start-placeholder="完成时间"
              end-placeholder="完成时间"
            >
            </el-date-picker>
          </div>
          <div class="liNav address">
            <span>收货地址</span>
            <areaLinkage />
          </div>
        </li>
      </ul>
      <ul>
        <li class="liItem">
          <div class="liNav">
            <span>支付方式</span>
            <el-select
              size="small"
              v-model="orderNumber"
              placeholder="请选择支付方式"
              class="inputWidth"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
          <div class="liNav">
            <span>规格编码</span>
            <el-input
              placeholder="规格编码"
              size="small"
              class="inputWidth"
            ></el-input>
          </div>
        </li>
        <li class="liItem">
          <div class="liNav">
            <span>订单来源</span>
            <el-select
              size="small"
              v-model="orderNumber"
              placeholder="请选择订单来源"
              class="inputWidth"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :value="item.value"
                :label="item.label"
              >
              </el-option>
            </el-select>
          </div>
          <div class="liNav">
            <el-button
              type="primary"
              size="small"
            >筛选</el-button>
            <el-button size="small  ">导出表格</el-button>
          </div>
        </li>
      </ul>

      <ul class="arrowUl">
        <li>
          <div @click="handleToChangeArror()">
            <div
              v-if="arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >{{$t('membershipIntroduction.more')}}&nbsp;<img :src="ArrowArr[0].img_1"></div>
            <div
              v-if="!arrorFlag"
              style="color:rgb(90, 139, 255);cursor:pointer"
            >{{$t('membershipIntroduction.retract')}}&nbsp;<img :src="ArrowArr[1].img_2"></div>
          </div>
        </li>
      </ul>
    </wrapper>

    <wrapper>
      <div class="table_list">
        <el-table
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            prop=""
            label="商品"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="货号"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="单价"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="数量"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="收货人信息"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="下单时间"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="订单状态"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="支付金额"
            align="center"
          >
          </el-table-column>
        </el-table>
      </div>
    </wrapper>
  </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import areaLinkage from '@/components/admin/areaLinkage/areaLinkage.vue'

export default {
  components: { wrapper, areaLinkage },
  data () {
    return {
      orderNumber: '',
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }],
      value1: '',
      arrorFlag: true,
      ArrowArr: [
        {
          img_1: this.$imageHost + '/image/admin/show_more.png'
        },
        {
          img_2: this.$imageHost + '/image/admin/hid_some.png'
        }
      ]
    }
  },

  methods: {
    // 改变箭头事件
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    }
  }
}

</script>
<style  lang="scss" scoped>
.refundFailureOrder {
  font-size: 14px;
  .refundFailureOrder_main {
    padding: 10px 25px;
    ul {
      .liItem {
        display: flex;
        .liNav {
          display: flex;
          width: 340px;
          margin-bottom: 20px;
          span {
            display: block;
            width: 85px;
            line-height: 30px;
            height: 30px;
            text-align: right;
            color: #333;
            margin-right: 25px;
          }
          .inputWidth {
            width: 150px;
          }
        }
        .date {
          width: 500px;
        }
        .address {
          width: 500px;
          margin-left: 100px;
          span {
            width: 85px;
          }
          .areaLinkage {
            width: 415px;
            /deep/ .el-select {
              margin-right: 5px;
            }
          }
        }
      }
    }
    .arrowUl {
      margin-top: 15px;
      display: flex;
      justify-content: center;
    }
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    color: #000;
    padding: 8px 10px;
  }
  .table_list {
    position: relative;
  }
}
</style>
