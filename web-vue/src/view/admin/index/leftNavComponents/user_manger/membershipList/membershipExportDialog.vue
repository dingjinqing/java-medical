<template>
  <div class="member-ship-export-dialog">
    <el-dialog
      title="导出表格"
      :visible.sync="visible"
      width="700px"
    >
      <div class="dialog-content">
        <el-alert
          title="警告提示的文案"
          type="warning"
          :closable="false"
          show-icon
        >
        </el-alert>
        <p style="margin-top:20px;">筛选条件：</p>
        <p style="margin-top:10px;">来源：全部</p>
        <div class="dashed-line"></div>
        <div style="margin-top:10px;">导出条数(一次最多导出{{cfgInfo.max_num?cfgInfo.max_num:0}}条数据)</div>
        <div>
          <el-input-number
            size="small"
            v-model="cfgInfo.min_num"
            controls-position="right"
            style="width:100px;"
            :max="cfgInfo.avail_num"
          ></el-input-number>
          至
          <el-input-number
            size="small"
            v-model="cfgInfo.avail_num"
            controls-position="right"
            style="width:100px;"
          ></el-input-number>
        </div>
        <div class="dashed-line"></div>
        <div>
          <table
            border="1"
            class="export-table-items"
          >
            <tbody>
              <tr>
                <td class="table-title">
                  <el-checkbox>基础信息:</el-checkbox>
                </td>
                <td class="table-list">
                  <el-checkbox-group v-model="checkedCfgs">
                    <ul class="check-list">

                      <li style="color:red;">
                        <el-checkbox label="user_id">用户id</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="username">昵称</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="mobile">手机号</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="wx_openid">OpenID</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="account">余额</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="score">积分</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="user_source">用户来源</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="create_time">注册时间</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="user_card">会员卡</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="user_address">地址</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="order_account">累计消费金额</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="order">累计消费单数</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="return_order_money">累计退款金额</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="return_order">累计退款订单数</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="remark">备注</el-checkbox>
                      </li>
                    </ul>
                  </el-checkbox-group>
                </td>
              </tr>
              <tr>
                <td class="table-title">
                  <el-checkbox>分销信息：</el-checkbox>
                </td>
                <td class="table-list">
                  <el-checkbox-group v-model="checkedCfgs">
                    <ul class="check-list">
                      <li>
                        <el-checkbox label="invite_user_name">邀请人</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="invite_mobile">邀请人手机号</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="invite_group_name">邀请人分销员分组</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="rebate_order_num">获返利订单数量</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="calculate_money">返利商品总金额</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="rebate_money">获返利订单佣金总额</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="withdraw_money">已提现佣金总额</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="sublayer_number">下级用户数</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="level_name">分销员等级</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="group_name">分销员分组</el-checkbox>
                      </li>
                    </ul>
                  </el-checkbox-group>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          size="small"
          type="primary"
          @click="determineDialog"
        >确定</el-button>
        <el-button
          size="small"
          @click="cancelDialog"
        >取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getExportCfg } from '@/api/admin/membershipList.js'
export default {
  props: {
    dialogVisible: Boolean
  },
  data () {
    return {
      cfgInfo: {
        min_num: 1
      },
      checkedCfgs: []
    }
  },
  computed: {
    visible: {
      get: function () {
        if (this.dialogVisible) {
          this.initData()
        }
        return this.dialogVisible
      },
      set: function (val) {
        this.$emit('update:dialogVisible', val)
      }
    }
  },
  methods: {
    initData () {
      console.log('init....')
      getExportCfg().then(res => {
        if (res.error === 0) {
          this.cfgInfo = Object.assign({}, this.cfgInfo, res.content)
          this.checkedCfgs = res.content.choosed_cfg
        } else {
          this.$message.error(res.message)
        }
      })
    },
    cancelDialog () {
      this.visible = false
    },
    determineDialog () {
      this.visible = false
    }
  }
}
</script>

<style lang="scss" scoped>
.table-title {
  width: 25%;
  vertical-align: middle;
}
.check-list {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  li {
    width: 150px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    line-height: 20px;
    cursor: pointer;
    .el-checkbox {
      margin-right: 10px;
    }
  }
}
.dashed-line {
  display: block;
  width: 100%;
  padding-top: 10px;
  border-radius: 2px;
  border-top: 0.5px dashed;
  margin-top: 10px;
}
.export-table-items {
  td {
    border: 1px solid #ccc;
    padding: 8px 10px;
  }
}
</style>
