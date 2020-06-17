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
      <div class="list">
        <div class="li">
          <div class="phoneClass">充值单号</div>
          <el-input
            v-model="headerDataThird.rechargeNo"
            placeholder="请输入充值单号"
            size="small"
          ></el-input>
        </div>
        <div class="li">
          <div class="phoneClass">会员卡</div>
          <el-input
            v-model="headerDataThird.cardName"
            placeholder="请输入会员卡名称"
            size="small"
          ></el-input>
        </div>
        <div class="li">
          <div class="phoneClass">卡ID</div>
          <el-input
            v-model="headerDataThird.cardId"
            placeholder="请输入会员卡ID"
            size="small"
          ></el-input>
        </div>
        <div class="li">
          <div class="phoneClass">充值方式</div>
          <el-select
            v-model="headerDataThird.rechargeMethod"
            placeholder="请选择"
            size="small"
          >
            <el-option
              v-for="item in rechargeMethodOption"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <div class="list">
        <div class="li">
          <div class="phoneClass">会员信息</div>
          <el-input
            v-model="headerDataThird.memberInfo"
            placeholder="会员昵称/手机号"
            size="small"
          ></el-input>
        </div>
        <div
          class="li"
          style="width:auto"
        >
          <div class="phoneClass">充值时间</div>
          <el-date-picker
            v-model="headerDataThird.rechargeStartTime"
            type="date"
            value-format="yyyy-MM-dd 00:00:00"
            size="small"
          >
          </el-date-picker>
          &nbsp;至&nbsp;
          <el-date-picker
            v-model="headerDataThird.rechargeEndTime"
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
          <div class="phoneClass">充值金额</div>
          <el-input
            v-model="headerDataThird.rechargeAmountmin"
            size="small"
          ></el-input>
          &nbsp;至&nbsp;
          <el-input
            v-model="headerDataThird.rechargeAmountmax"
            size="small"
          ></el-input>

        </div>
        <div
          class="li"
          style="width:auto;margin-left:20px"
        >
          <div class="phoneClass">充值后卡余额</div>
          <el-input
            v-model="headerDataThird.afterRechargeStartTime"
            size="small"
          ></el-input>
          &nbsp;至&nbsp;
          <el-input
            v-model="headerDataThird.afterRechargeEndTime"
            size="small"
          ></el-input>
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
    <!--优惠卷礼包购买表头-->
    <div v-if="showHeader === 'fourth'">
      优惠卷礼包购买表头
    </div>
  </div>
</template>
<script>
import { download } from '@/util/excelUtil.js'
import { renewExport, chargeExport } from '@/api/admin/memberManage/memberValueAdd/memberValueAdd.js'
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
        rechargeNo: '', // 充值单号
        cardName: '', // 会员卡名称
        cardId: '', // 卡ID
        rechargeMethod: '', // 充值方式
        memberInfo: '', // 会员信息
        rechargeStartTime: '', // 充值开始时间
        rechargeEndTime: '', // 充值结束时间
        rechargeAmountmin: '', // 充值金额最小
        rechargeAmountmax: '', // 充值金额最大
        afterRechargeStartTime: '', // 充值后卡余额开始时间
        afterRechargeEndTime: '' // 充值后卡余额结束时间
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
      ],
      rechargeMethodOption: [
        {
          value: 0,
          label: '全部'
        },
        {
          value: 1,
          label: '开卡'
        },
        {
          value: 2,
          label: '用户充值'
        },
        {
          value: 3,
          label: '管理员操作'
        }
      ]
    }
  },
  watch: {
    showHeader (newData) {
      console.log(newData)
      this.headerDataSecond = {
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
      }
      this.headerDataThird = {
        rechargeNo: '', // 充值单号
        cardName: '', // 会员卡名称
        cardId: '', // 卡ID
        rechargeMethod: '', // 充值方式
        memberInfo: '', // 会员信息
        rechargeStartTime: '', // 充值开始时间
        rechargeEndTime: '', // 充值结束时间
        rechargeAmountmin: '', // 充值金额最小
        rechargeAmountmax: '', // 充值金额最大
        afterRechargeStartTime: '', // 充值后卡余额开始时间
        afterRechargeEndTime: '' // 充值后卡余额结束时间
      }
      this.handleToScreen()
    }
  },
  methods: {
    // 点击筛选
    handleToScreen () {
      console.log(this.showHeader)
      let headerDataSecond = JSON.parse(JSON.stringify(this.headerDataSecond))
      let headerDataThird = JSON.parse(JSON.stringify(this.headerDataThird))
      switch (this.showHeader) {
        case 'second':
          console.log(this.headerDataSecond)
          this.$emit('getRenewalHeaderData', headerDataSecond)
          break
        case 'third':
          this.$emit('getRenewalHeaderData', headerDataThird)
          break
      }
    },
    // 点击导出
    handleToExport () {
      let params = JSON.parse(JSON.stringify(this.headerDataSecond))
      let paramsCharge = JSON.parse(JSON.stringify(this.headerDataThird))
      console.log(this.showHeader, params)
      let para1 = {}
      let para2 = {}
      if (this.showHeader === 'second') {
        let { cardId, cardName, renewalAmountmax, renewalAmountmin, renewalNo, afterRenewalStartTime, afterRenewalEndTime, renewStartTime, renewEndTime, memberInfo, company } = params
        para1 = {
          cardId: cardId,
          cardName: cardName,
          endTime: renewEndTime,
          renewMoneyMax: renewalAmountmax,
          renewMoneyMin: renewalAmountmin,
          renewOrderSn: renewalNo,
          renewTimeMax: afterRenewalStartTime,
          renewTimeMin: afterRenewalEndTime,
          renewType: company,
          startTime: renewStartTime,
          userInfo: memberInfo
        }
      } else {
        let { cardId, cardName, afterRechargeEndTime, afterRechargeStartTime, memberInfo, rechargeAmountmax, rechargeAmountmin, rechargeEndTime, rechargeMethod, rechargeNo, rechargeStartTime } = paramsCharge
        para2 = {
          afterChargeMoneyMax: afterRechargeEndTime,
          afterChargeMoneyMin: afterRechargeStartTime,
          cardId: cardId,
          cardName: cardName,
          changeType: rechargeMethod,
          chargeMax: rechargeAmountmax,
          chargeMin: rechargeAmountmin,
          createTimeMax: rechargeEndTime,
          createTimeMin: rechargeStartTime,
          orderSn: rechargeNo,
          userInfo: memberInfo
        }
      }
      console.log(paramsCharge)
      switch (this.showHeader) {
        case 'second':
          renewExport(para1).then(res => {
            let fileName = localStorage.getItem('V-content-disposition')
            fileName = fileName.split(';')[1].split('=')[1]
            download(res, decodeURIComponent(fileName))
          })
          break
        case 'third':
          chargeExport(para2).then(res => {
            let fileName = localStorage.getItem('V-content-disposition')
            fileName = fileName.split(';')[1].split('=')[1]
            download(res, decodeURIComponent(fileName))
          })
          break
      }
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
