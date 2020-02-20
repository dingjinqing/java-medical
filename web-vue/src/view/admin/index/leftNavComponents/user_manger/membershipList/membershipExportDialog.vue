<template>
  <div class="member-ship-export-dialog">
    <el-dialog
      title="导出表格"
      :visible.sync="dialogVisible"
      width="700">
      <div class="dialog-content">
        <el-alert
          title="警告提示的文案"
          type="warning"
          :closable="false"
          show-icon>
        </el-alert>
        <p>筛选条件：</p>
        <p>来源：全部</p>
        <div>导出条数(一次最多导出5000条数据)</div>
        <div>
          <el-input-number size="small" controls-position="right" style="width:100px;" ></el-input-number>
          至
          <el-input-number size="small" controls-position="right" style="width:100px;"></el-input-number>
        </div>
        <div>
          <table border="1" class="export-table-items">
            <tbody>
              <tr>
                <td class="table-title">
                  <el-checkbox></el-checkbox>
                  基础信息:
                </td>
                <td>
                  <ul class="check-list">
                    <li><el-checkbox></el-checkbox>用户id</li>
                    <li><el-checkbox></el-checkbox>昵称</li>
                    <li><el-checkbox></el-checkbox>手机号</li>
                    <li><el-checkbox></el-checkbox>OpenID</li>
                    <li><el-checkbox></el-checkbox>余额</li>
                    <li><el-checkbox></el-checkbox>积分</li>
                    <li><el-checkbox></el-checkbox>用户来源</li>
                    <li><el-checkbox></el-checkbox>注册时间</li>
                    <li><el-checkbox></el-checkbox>会员卡</li>
                    <li><el-checkbox></el-checkbox>地址</li>
                    <li><el-checkbox></el-checkbox>累计消费金额</li>
                    <li><el-checkbox></el-checkbox>累计消费单数</li>
                    <li><el-checkbox></el-checkbox>累计退款金额</li>
                    <li><el-checkbox></el-checkbox>累计退款订单数</li>
                    <li><el-checkbox></el-checkbox>备注</li>
                  </ul>
                </td>
              </tr>
              <tr>
                <td class="table-title">
                  <el-checkbox></el-checkbox>
                  分销信息：
                </td>
                <td>
                  <ul class="check-list">
                    <li><el-checkbox></el-checkbox>邀请人</li>
                    <li><el-checkbox></el-checkbox>邀请人手机号</li>
                    <li><el-checkbox></el-checkbox>邀请人分销员分组</li>
                    <li><el-checkbox></el-checkbox>获返利订单数量</li>
                    <li><el-checkbox></el-checkbox>返利商品总金额</li>
                    <li><el-checkbox></el-checkbox>获返利订单佣金总额</li>
                    <li><el-checkbox></el-checkbox>已提现佣金总额</li>
                    <li><el-checkbox></el-checkbox>下级用户数</li>
                    <li><el-checkbox></el-checkbox>分销员等级</li>
                    <li><el-checkbox></el-checkbox>分销员分组</li>
                  </ul>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="determineDialog">确定</el-button>
        <el-button size="small" @click="cancelDialog">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  props: {
    dialogVisible: Boolean
  },
  data () {
    return {}
  },
  methods: {
    cancelDialog () {
      this.$emit('update:dialogVisible', false)
    },
    determineDialog () {
      this.$emit('update:dialogVisible', false)
    }
  }
}
</script>

<style lang="scss" scoped>
  .table-title {
    width: 100px;
  }
  .check-list {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    li {
      width: 122px;
      flex: 1 1 122px;
      white-space: nowrap;
      word-wrap: none;
    }
  }
  .export-table-items td{
    border: 1px solid #ccc;
  }
</style>
