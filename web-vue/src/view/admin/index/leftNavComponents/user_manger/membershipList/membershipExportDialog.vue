<template>
  <div class="member-ship-export-dialog">
    <el-dialog
      :title="$t('membershipIntroduction.exportTable')"
      :visible.sync="visible"
      width="700px"
    >
      <div class="dialog-content">
        <el-alert
          :title="$t('membershipIntroduction.filterOnFollowing') + cfgInfo.avail_num + $t('membershipIntroduction.articleData')+','+ $t('membershipIntroduction.ayExport')+'?'"
          type="warning"
          :closable="false"
          show-icon
        >
        </el-alert>
        <p style="margin-top:20px;">{{ $t('membershipIntroduction.filterCirteria') }}：</p>
        <div class="filters">
          <ul>
            <li
              class="filter-li"
              v-for="(item, key) in filters"
              :key="key"
            >
              <p v-if="!!item">{{item}}</p>
            </li>
          </ul>
        </div>
        <div class="dashed-line"></div>
        <div style="margin:10px 0;">{{ $t('membershipIntroduction.numExports') }}{{cfgInfo.max_num?cfgInfo.max_num:0}}{{ $t('membershipIntroduction.articleDatas') }})</div>
        <div>
          <el-input-number
            size="small"
            v-model="cfgInfo.min_num"
            controls-position="right"
            style="width:100px;"
            :max="cfgInfo.avail_num"
          ></el-input-number>
          {{ $t('membershipIntroduction.to') }}
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
                  <el-checkbox
                    :indeterminate="baseisIndeterminate"
                    v-model="baseCheck"
                    @change="baseCheckAllChange"
                  >{{ $t('membershipIntroduction.basicInformation') }}:</el-checkbox>
                </td>
                <td class="table-list">
                  <el-checkbox-group
                    v-model="baseChecked"
                    @change="baseCheckGroupChange"
                  >
                    <ul class="check-list">

                      <li style="color:red;">
                        <el-checkbox label="user_id">{{ $t('membershipIntroduction.userId2') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="username">{{ $t('membershipIntroduction.nickname2') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="mobile">{{ $t('membershipIntroduction.phone') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="wx_openid">OpenID</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="account">{{ $t('membershipIntroduction.balance') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="score">{{ $t('membershipIntroduction.integral2') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="user_source">{{ $t('membershipIntroduction.userSource') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="create_time">{{ $t('membershipIntroduction.registrationTime2') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="user_card">{{ $t('membershipIntroduction.memberShip') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="user_address">{{ $t('membershipIntroduction.address2') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="order_account">{{ $t('membershipIntroduction.cumulativeSpending') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="order">{{ $t('membershipIntroduction.cumulativeConsumption') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="return_order_money">{{ $t('membershipIntroduction.cumulativeRefund') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="return_order">{{ $t('membershipIntroduction.cumulativeOrder') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="remark">{{ $t('membershipIntroduction.remarks') }}</el-checkbox>
                      </li>
                    </ul>
                  </el-checkbox-group>
                </td>
              </tr>
              <tr>
                <td class="table-title">
                  <el-checkbox
                    :indeterminate="distributionIndeterminate"
                    v-model="distributionCheck"
                    @change="distributionCheckAllChange"
                  >{{ $t('membershipIntroduction.distributionIntro') }}：</el-checkbox>
                </td>
                <td class="table-list">
                  <el-checkbox-group
                    v-model="distributionChecked"
                    @change="distributionCheckGroupChange"
                  >
                    <ul class="check-list">
                      <li>
                        <el-checkbox label="invite_user_name">{{ $t('membershipIntroduction.invitePe') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="invite_mobile">{{ $t('membershipIntroduction.inviteMobile') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="invite_group_name">{{ $t('membershipIntroduction.invitedDistribut') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="rebate_order_num">{{ $t('membershipIntroduction.numRebate') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="calculate_money">{{ $t('membershipIntroduction.rebateProducts') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="rebate_money">{{ $t('membershipIntroduction.totalRebate') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="withdraw_money">{{ $t('membershipIntroduction.totalCommission') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="sublayer_number">{{ $t('membershipIntroduction.numSubordinate') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="level_name">{{ $t('membershipIntroduction.distributorLevel') }}</el-checkbox>
                      </li>
                      <li>
                        <el-checkbox label="group_name">{{ $t('membershipIntroduction.distributorGroup') }}</el-checkbox>
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
        >{{ $t('membershipIntroduction.determine') }}</el-button>
        <el-button
          size="small"
          @click="cancelDialog"
        >{{ $t('membershipIntroduction.cancel2') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getExportCfg, exportCfg } from '@/api/admin/membershipList.js'
import { download } from '@/util/excelUtil.js'
export default {
  props: {
    dialogVisible: Boolean,
    queryParams: Object
  },
  data () {
    return {
      cfgInfo: {
        min_num: 1
      },
      checkedCfgs: [],
      baseCheck: false,
      baseisIndeterminate: false,
      baseOptions: [
        'user_id',
        'username',
        'mobile',
        'wx_openid',
        'account',
        'score',
        'user_source',
        'create_time',
        'user_card',
        'user_address',
        'order_account',
        'order',
        'return_order_money',
        'return_order',
        'remark'
      ],
      baseChecked: [],
      distributionCheck: false,
      distributionIndeterminate: false,
      distributionOptions: [
        'invite_user_name',
        'invite_mobile',
        'invite_group_name',
        'rebate_order_num',
        'calculate_money',
        'rebate_money',
        'withdraw_money',
        'sublayer_number',
        'level_name',
        'group_name'
      ],
      distributionChecked: [],
      loading: false
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
    },
    filters: {
      get () {
        let params = this.queryParams
        let paramDescription = {}
        for (const key in params) {
          if (params.hasOwnProperty(key)) {
            let value = params[key]
            if ((!!value && !Array.isArray(value)) || (Array.isArray(value) && value.length > 0)) {
              switch (key) {
                case 'mobile':
                  paramDescription[key] = '手机号：' + value
                  break
                case 'username':
                  paramDescription[key] = '微信昵称：' + value
                  break
                case 'source':
                  paramDescription[key] = '来源：' + params.sourceLabel
                  break
                case 'tagName':
                  paramDescription[key] = '标签：' + params.tagSourceLabel
                  break
                case 'inviteUserName':
                  paramDescription[key] = '邀请人：' + value
                  break
                case 'createTime':
                case 'endTime':
                  paramDescription['registrationTime'] = '注册时间：' + params.createTime + '至' + params.endTime
                  break
                case 'cardId':
                  paramDescription[key] = '会员卡：' + params.membershipCardLabel
                  break
                case 'loginStartTime':
                case 'loginEndTime':
                  paramDescription['loginTime'] = '指定时间内有登录记录：' + params.loginStartTime + '至' + params.loginEndTime
                  break
                case 'cartStartTime':
                case 'cartEndTime':
                  paramDescription['cartTime'] = '指定时间内有加购行为：' + params.cartStartTime + '至' + params.cartEndTime
                  break
                case 'buyStartTime':
                case 'buyEndTime':
                  paramDescription['buyTime'] = '指定时间内有交易记录：' + params.buyStartTime + '至' + params.buyEndTime
                  break
                case 'unitPriceLow':
                case 'unitPriceHight':
                  paramDescription['unitPrice'] = '客单价：' + params.unitPriceLow + '至' + params.unitPriceHight
                  break
                case 'buyCountLow':
                case 'buyCountHight':
                  paramDescription['buyCount'] = '累计购买次数：' + params.buyCountLow + '至' + params.buyCountHight
                  break
                case 'goodsId':
                  paramDescription[key] = '商品名称：' + params.chooseGoodsLabel
                  break
                case 'hasMobile':
                  paramDescription[key] = '有手机号'
                  break
                case 'hasScore':
                  paramDescription[key] = '有积分'
                  break
                case 'hasBalance':
                  paramDescription[key] = '有余额'
                  break
                case 'hasCard':
                  paramDescription[key] = '有会员卡'
                  break
                case 'hasDelete':
                  paramDescription[key] = '已禁止登录'
                  break
                case 'hasImport':
                  paramDescription[key] = '导入的会员'
                  break
              }
            }
          }
        }
        return paramDescription
      },
      set () {

      }
    }
  },
  // watch: {
  //   checkedCfgs: {
  //     handler: function (val) {
  //       let baseChecked = []
  //       let distributionChecked = []
  //       val = val || []
  //       val.forEach(item => {
  //         if (this.baseOptions.includes(item)) {
  //           baseChecked.push(item)
  //         } else if (this.distributionOptions.includes(item)) {
  //           distributionChecked.push(item)
  //         }
  //       })
  //       this.baseChecked = baseChecked
  //       this.distributionChecked = distributionChecked
  //     }
  //   }
  // },
  methods: {
    initData () {
      console.log('init....')
      getExportCfg(this.queryParams).then(res => {
        if (res.error === 0) {
          this.cfgInfo = Object.assign({}, this.cfgInfo, res.content)
          let choosedCfg = res.content.choosed_cfg || []
          this.checkedCfgs = choosedCfg
          let baseChecked = []
          let distributionChecked = []
          choosedCfg.forEach(item => {
            if (this.baseOptions.includes(item)) {
              baseChecked.push(item)
            } else if (this.distributionOptions.includes(item)) {
              distributionChecked.push(item)
            }
          })
          this.baseChecked = baseChecked
          this.distributionChecked = distributionChecked
          if (baseChecked.length > 0 && baseChecked.length < this.baseOptions.length) {
            this.baseisIndeterminate = true
          } else if (baseChecked.length === this.baseOptions.length) {
            this.baseCheck = true
          }
          if (distributionChecked.length > 0 && distributionChecked.length < this.distributionOptions.length) {
            this.distributionIndeterminate = true
          } else if (distributionChecked.length === this.distributionOptions.length) {
            this.distributionCheck = true
          }
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
      let checkedCfgs = this.baseChecked.concat(this.distributionChecked)
      let params = Object.assign({}, this.queryParams, {
        userExpParam: {
          startNum: '',
          endNum: '',
          columns: checkedCfgs
        }
      })
      this.loading = true
      exportCfg(params).then(res => {
        this.loading = false
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        download(res, decodeURIComponent(fileName))
      })
    },
    // 基础信息选中改变
    baseCheckAllChange (val) {
      this.baseChecked = val ? this.baseOptions : []
      this.baseisIndeterminate = false
    },
    baseCheckGroupChange (val) {
      let checkedCount = val.length
      let baseOptionsCount = this.baseOptions.length
      this.baseCheck = checkedCount === baseOptionsCount
      this.baseisIndeterminate = checkedCount > 0 && checkedCount < baseOptionsCount
    },
    // 分销信息选中改变
    distributionCheckAllChange (val) {
      this.distributionChecked = val ? this.distributionOptions : []
      this.distributionIndeterminate = false
    },
    distributionCheckGroupChange (val) {
      let checkedCount = val.length
      let distributionOptionsCount = this.distributionOptions.length
      this.distributionCheck = checkedCount === distributionOptionsCount
      this.distributionIndeterminate = checkedCount > 0 && checkedCount < distributionOptionsCount
    }
  }
}
</script>

<style lang="scss" scoped>
.member-ship-export-dialog {
  .dialog-content {
    max-height: 500px;
    overflow: auto;
  }
  .filters {
    margin: 10px 0;
  }
  .filter-li {
    padding-bottom: 5px;
  }
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
}
</style>
