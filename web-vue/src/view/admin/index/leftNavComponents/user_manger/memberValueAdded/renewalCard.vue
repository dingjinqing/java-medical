<template>
  <div>
    <!--会员卡购买表头-->
    <div v-if="showHeader === 'first'">
      会员卡购买表头
    </div>
    <!--会员卡续费表头-->
    <div v-if="showHeader === 'second'">
      <div class="list">
        <div class="li">
          <div class="phoneClass">续费单号</div>
          <el-input
            v-model="headerDataSecond.renewalNo"
            placeholder="请输入续费单号"
            size="small"
          ></el-input>
        </div>
        <div class="li">
          <div class="phoneClass">会员卡</div>
          <el-input
            v-model="headerDataSecond.cardName"
            placeholder="请输入会员卡名称"
            size="small"
          ></el-input>
        </div>
        <div class="li">
          <div class="phoneClass">卡ID</div>
          <el-input
            v-model="headerDataSecond.cardId"
            placeholder="请输入会员卡ID"
            size="small"
          ></el-input>
        </div>
      </div>
      <div class="list">
        <div class="li">
          <div class="phoneClass">会员信息</div>
          <el-input
            v-model="headerDataSecond.memberInfo"
            placeholder="会员昵称/手机号"
            size="small"
          ></el-input>
        </div>
        <div
          class="li"
          style="width:auto"
        >
          <div class="phoneClass">续费时间</div>
          <el-date-picker
            v-model="headerDataSecond.renewStartTime"
            type="date"
            value-format="yyyy-MM-dd 00:00:00"
            size="small"
          >
          </el-date-picker>
          &nbsp;至&nbsp;
          <el-date-picker
            v-model="headerDataSecond.renewEndTime"
            type="date"
            value-format="yyyy-MM-dd 23:59:59"
            size="small"
          >
          </el-date-picker>
        </div>
      </div>
      <div class="list">
        <div
          class="li"
          style="width:auto"
        >
          <div class="phoneClass">续费金额</div>
          <el-input
            v-model="headerDataSecond.renewalAmountmin"
            size="small"
          ></el-input>
          &nbsp;至&nbsp;
          <el-input
            v-model="headerDataSecond.renewalAmountmax"
            size="small"
          ></el-input>
          <div class="company">
            <el-select
              v-model="headerDataSecond.company"
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
          </div>

        </div>
        <div
          class="li"
          style="width:auto;margin-left:20px"
        >
          <div class="phoneClass">续费后有效期至</div>
          <el-date-picker
            v-model="headerDataSecond.afterRenewalStartTime"
            type="date"
            value-format="yyyy-MM-dd 00:00:00"
            size="small"
          >
          </el-date-picker>
          &nbsp;至&nbsp;
          <el-date-picker
            v-model="headerDataSecond.afterRenewalEndTime"
            type="date"
            value-format="yyyy-MM-dd 23:59:59"
            size="small"
          >
          </el-date-picker>
          &nbsp;之间
        </div>
      </div>
      <div class="list button">
        <el-button
          size="small"
          type="primary"
          @click="handleToScreen()"
        >筛选</el-button>
        <el-button
          @click="handleToExport()"
          size="small"
        >数据导出</el-button>
      </div>
    </div>
    <!--会员卡充值表头-->
    <div v-if="showHeader === 'third'">
      会员卡充值表头
    </div>
    <!--优惠卷礼包购买表头-->
    <div v-if="showHeader === 'fourth'">
      优惠卷礼包购买表头
    </div>
  </div>
</template>
<script>
export default {
  props: {
    showHeader: {
      Type: String,
      default: ''
    }
  },
  data () {
    return {
      headerDataFirst: {
      },
      headerDataSecond: {
        renewalNo: '', // 续费单号
        cardName: '', // 会员卡名称
        cardId: '', // 卡ID
        memberInfo: '', // 会员信息
        renewStartTime: '', // 续费开始时间
        renewEndTime: '', // 续费结束时间
        renewalAmountmin: '', // 续费金额最小
        renewalAmountmax: '', // 续费金额最大
        afterRenewalStartTime: '', // 续费后有效期开始时间
        afterRenewalEndTime: '', // 续费后有效期结束时间
        company: 0 // 钱单位
      },
      headerDataThird: {
      },
      headerDataFourth: {
      },
      options: [
        {
          value: 0,
          label: '元'
        },
        {
          value: 1,
          label: '积分'
        }
      ]
    }
  },
  methods: {
    // 点击筛选
    handleToScreen () {

    },
    // 点击导出
    handleToExport () {

    }
  }
}
</script>
<style lang="scss" scoped>
.list {
  display: flex;
  margin-bottom: 15px;
  .li {
    width: 280px;
    display: flex;
    align-items: center;
    .phoneClass {
      line-height: 30px;
      height: 30px;
      color: #333;
      white-space: nowrap;
      padding-right: 5px;
    }
    /deep/ .el-input {
      width: 180px;
    }
    .company {
      margin-left: 5px;
      /deep/ .el-input {
        width: 80px;
      }
    }
  }
}
.button {
  /deep/ .el-button {
    width: 85px;
  }
}
</style>
