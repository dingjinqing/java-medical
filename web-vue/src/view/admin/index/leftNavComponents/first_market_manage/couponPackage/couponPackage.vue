<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <el-tabs
          v-model="activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane
            label="全部优惠券"
            name="first"
          ></el-tab-pane>
          <el-tab-pane
            label="进行中"
            name="second"
          ></el-tab-pane>
          <el-tab-pane
            label="未开始"
            name="third"
          ></el-tab-pane>
          <el-tab-pane
            label="已过期"
            name="fourth"
          ></el-tab-pane>
          <el-tab-pane
            label="已停用"
            name="fifth"
          ></el-tab-pane>
        </el-tabs>
        <el-button
          type="primary"
          @click="addCouponPackage()"
        >添加优惠券礼包</el-button>
      </div>
      <div class="table_box">
        <div class="filters">
          <div class="filters_item"><span>活动名称：</span>
            <el-input
              v-model="act_name"
              placeholder="请输入活动名称"
              size="small"
            ></el-input>
          </div>
          <div class="filters_item"><span>礼包名称：</span>
            <el-input
              v-model="package_name"
              placeholder="请输入礼包名称"
              size="small"
            ></el-input>
          </div>
          <div class="filters_item"><span>领取方式：</span>
            <el-select
              v-model="get_type"
              placeholder="请选择"
              size="small"
            >
              <el-option
                v-for="item in get_type_option"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item">
            <el-button
              type="primary"
              size="small"
            >筛选</el-button>
          </div>
        </div>
        <el-table
          :data="packageTableData"
          style="width:100%;"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
          <el-table-column
            prop="act_name"
            label="活动名称"
          ></el-table-column>
          <el-table-column
            prop="package_name"
            label="礼包名称"
          ></el-table-column>
          <el-table-column
            prop="valid_period"
            label="有效期"
          ></el-table-column>
          <el-table-column
            prop="coupon_type_num"
            label="优惠券种类数/礼包"
          ></el-table-column>
          <el-table-column
            prop="coupon_num"
            label="优惠券数量/礼包"
          ></el-table-column>
          <el-table-column
            prop="can_send_num"
            label="可发放礼包数"
          ></el-table-column>
          <el-table-column
            prop="get_type"
            label="领取方式"
          ></el-table-column>
          <el-table-column
            prop="money"
            label="购买金额"
          ></el-table-column>
          <el-table-column
            prop="have_num"
            label="已领取礼包数"
          ></el-table-column>
          <el-table-column
            prop="act_status"
            label="活动状态"
          ></el-table-column>
          <el-table-column
            label="操作"
            width="130"
          >
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="编辑"
                  placement="top"
                >
                  <i
                    class="el-icon-edit-outline"
                    @click="edit(scope)"
                  ></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="分享"
                  placement="top"
                >
                  <i class="el-icon-share"></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="删除"
                  placement="top"
                >
                  <i class="el-icon-delete"></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="查看订单"
                  placement="top"
                >
                  <i class="el-icon-s-order"></i>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="领取明细"
                  placement="top"
                >
                  <i class="el-icon-document"></i>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="tapOneblock">
          <span class="demonstration">直接前往</span>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="20"
            layout="prev, pager, next, jumper"
            :total="totalRows"
          >
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      activeName: 'first',
      act_name: '',
      package_name: '',
      get_type: 1,
      get_type_option: [
        { value: 1, label: '全部' },
        { value: 2, label: '现金' },
        { value: 3, label: '积分' },
        { value: 4, label: '免费' }
      ],
      packageTableData: [
        { act_name: '新得周优惠券', package_name: '新得周优惠券', valid_period: '2019-08-18 15:48:49至2019-08-30 15:48:51', coupon_type_num: '23', coupon_num: '100', can_send_num: '100', get_type: '直接领取', money: '免费', have_num: '2', act_status: '进行中' },
        { act_name: '新得周优惠券', package_name: '新得周优惠券', valid_period: '2019-08-18 15:48:49至2019-08-30 15:48:51', coupon_type_num: '23', coupon_num: '100', can_send_num: '100', get_type: '直接领取', money: '免费', have_num: '2', act_status: '进行中' },
        { act_name: '新得周优惠券', package_name: '新得周优惠券', valid_period: '2019-08-18 15:48:49至2019-08-30 15:48:51', coupon_type_num: '23', coupon_num: '100', can_send_num: '100', get_type: '直接领取', money: '免费', have_num: '2', act_status: '进行中' },
        { act_name: '新得周优惠券', package_name: '新得周优惠券', valid_period: '2019-08-18 15:48:49至2019-08-30 15:48:51', coupon_type_num: '23', coupon_num: '100', can_send_num: '100', get_type: '直接领取', money: '免费', have_num: '2', act_status: '进行中' }
      ],
      currentPage: 1,
      totalRows: null
    }
  },
  methods: {
    handleCurrentChange () {
      console.log(this.currentPage)
    },
    handleClick (tab) {
      console.log(tab)
    },
    edit (scope) {
      console.log(scope)
    },
    addCouponPackage () {
      this.$router.push({
        name: 'coupon_Package_add'
      })
    }
  }

}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  .navBox {
    background-color: #fff;
    padding: 0 15px 14px;
    margin-bottom: 10px;
  }
  .table_box {
    background-color: #fff;
    padding: 15px;
    .filters {
      display: flex;
      line-height: 32px;
      margin-left: -15px;
      margin-bottom: 10px;
      .filters_item {
        max-width: 250px;
        display: flex;
        margin-left: 15px;
        > span {
          min-width: 80px;
        }
      }
    }
    .operation {
      display: flex;
      justify-content: space-around;
      > .item {
        font-size: 22px;
        color: #66b1ff;
        cursor: pointer;
      }
    }
    .tapOneblock {
      display: flex;
      justify-content: flex-end;
      margin-top: 10px;
      > span {
        height: 32px;
        line-height: 32px;
      }
    }
  }
}
</style>
