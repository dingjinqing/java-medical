<template>
  <div class="tradeProcessConfigure">
    <!-- 门店买单 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('serviceConfig.storebuy')}}
      </div>
      <div class="settingContent">
        <div
          class="Jurisdiction"
          @click.capture="handleToJurisdiction(true)"
        >
          <el-radio-group
            :disabled="radioDisabled"
            v-model="param.store_buy"
          >
            <el-radio :label="0">{{$t('serviceConfig.inactived')}}</el-radio>
            <el-radio :label="1">{{$t('serviceConfig.activated')}}</el-radio>
          </el-radio-group>
        </div>

      </div>
    </section>

    <!-- 服务评论配置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('serviceConfig.servicecommentconfig')}}
      </div>
      <div class="settingContent">
        <el-radio-group v-model="param.service_comment">
          <el-radio :label="0">{{$t('serviceConfig.config1')}}</el-radio>
          <el-radio :label="1">{{$t('serviceConfig.config2')}}</el-radio>
          <el-radio :label="2">{{$t('serviceConfig.config3')}}</el-radio>
        </el-radio-group>
      </div>
    </section>

    <!-- 职称配置 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('serviceConfig.titleconfig')}}
      </div>
      <div
        class="settingContent"
        @click="handleToJurisdiction(true)"
      >
        <el-input
          size="small"
          class="inputWidth"
          v-model="param.technician_title"
          :disabled="inputDisabled"
        ></el-input>
      </div>
    </section>

    <!-- 扫码购 -->
    <section class="settingWrapper">
      <div class="title">
        <span></span>
        {{$t('serviceConfig.qrshop')}}
      </div>
      <div class="settingContent">
        <el-button
          type="info"
          size="small"
          @click="handleTake()"
        >{{$t('serviceConfig.pleasechoose')}}</el-button>
        {{$t('serviceConfig.alreadychoose')}}
        <el-input
          size="small"
          class="inputWidthSmall"
          :disabled="true"
          v-model="param.store_scan_num"
        ></el-input>
        {{$t('serviceConfig.home')}}
        <span class="notefont">{{$t('serviceConfig.choosestore')}}</span>
      </div>
    </section>
    <section class="settingWrapper">
      <div class="settingContent">
        <el-button
          type="primary"
          size="small"
          @click="updateConfig"
        >{{$t('storeCommon.save')}}</el-button>
      </div>
    </section>

    <!-- 设置自提门店弹窗 -->
    <el-dialog
      title="选择开启'扫码购'功能的门店"
      :visible.sync="showStoreDialog"
      :close-on-click-modal='false'
      width=50%
    >
      <div class="table_list">
        <el-table
          class="version-manage-table"
          header-row-class-name="tableClss"
          :data="storeParamList"
          border
          style="width: 100%"
          ref="multipleTable"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection">
          </el-table-column>
          <el-table-column
            prop="storeName"
            :label="$t('tradeConfiguration.storename')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            :label="$t('tradeConfiguration.storeaddress')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="manager"
            :label="$t('tradeConfiguration.storeperson')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('tradeConfiguration.storephone')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="businessTime"
            :label="$t('tradeConfiguration.storebusinesstime')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            prop="businessState"
            :label="$t('tradeConfiguration.storebusinessstatus')"
            align="center"
          >
          </el-table-column>
        </el-table>
        <div class="table_footer">
          <pagination
            :page-params.sync="pageParams"
            @pagination="handleTake"
          />
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >{{$t('tradeConfiguration.save')}}</el-button>
        <el-button
          size="small"
          @click="cancle"
        >{{$t('tradeConfiguration.cancel')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { judgeJurisdictionRequest } from '@/api/admin/util.js'
import pagination from '@/components/admin/pagination/pagination'
import { getServiceConfig, updateServiceConfig } from '@/api/admin/storeManage/storemanage/serviceManage'
import { storeList } from '@/api/admin/storeManage/store'
export default {
  components: {
    pagination
  },
  mounted () {
    this.langDefault()
    // 判断门店买单radio权限
    this.handleToJurisdiction(false)
  },
  created () {
    this.initData()
  },
  data () {
    return {
      multipleSelection: [],
      showStoreDialog: false,
      allCheckFlag: false,
      pageParams: {},
      storeParamList: [],
      param: {
        service_comment: 0,
        store_buy: 0,
        store_scan_ids: '',
        technician_title: '',
        store_scan_num: ''
      },
      radioDisabled: false, // 门店买单 radio disbaled
      inputDisabled: false // 职称配置input disbaled
    }
  },
  methods: {
    // 选择门店弹窗-初始化首列复选框
    toggleSelection (rows) {
      this.$nextTick(() => {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row, true)
          })
        } else {
          this.$refs.multipleTable.clearSelection()
        }
      })
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    // 选择门店弹窗-点击触发弹窗
    handleTake () {
      this.getStoreList()
      this.showStoreDialog = true
    },
    // 选择门店弹窗-获取门店列表
    getStoreList () {
      let storPageParam = {
        currentPage: 0,
        pageRows: 20
      }
      if (this.pageParams != null) {
        storPageParam.currentPage = this.pageParams.currentPage
        storPageParam.pageRows = this.pageParams.pageRows
      }
      storeList(storPageParam).then(res => {
        console.log(res)
        if (res.error === 0) {
          let initStoreList = []
          this.pageParams = res.content.page
          this.storeParamList = res.content.dataList
          this.storeParamList.map((item, index) => {
            if (item.businessState === 0) {
              item.businessState = '关店'
            } else if (item.businessState === 1) {
              item.businessState = '营业'
            }
            item.autoPick = this.number2boolean(item.autoPick)
            item.businessTime = item.openingTime + '-' + item.closeTime
            // this.param.store_scan_ids.split(',').indexOf(item.storeId.toString()) > -1
            if (JSON.parse(this.param.store_scan_ids).indexOf(item.storeId) > -1) {
              initStoreList.push(this.storeParamList[index])
            }
          })
          // 初始化首列复选框的值
          console.log(initStoreList)
          this.toggleSelection(initStoreList)
        } else {
          this.$message.error(this.$t('storeCommon.getstorelistfailed'))
        }
      })
    },
    // 选择门店弹窗 全选本页 - 全部checkbox选中
    handleAllcheck () {
      this.allCheckFlag = false
    },
    // 选择门店弹窗-取消按钮点击,弹窗退出
    cancle () {
      this.showStoreDialog = false
    },
    // 选择门店弹窗-确认按钮点击
    initDataList () {
      this.showStoreDialog = false
      let storeScanIdsArray = []
      console.log(this.multipleSelection)
      // 设置回调值
      this.param.store_scan_num = this.multipleSelection.length
      this.multipleSelection.map((item, index) => {
        storeScanIdsArray.push(item.storeId)
      })
      console.log(storeScanIdsArray)
      this.param.store_scan_ids = JSON.stringify(storeScanIdsArray)
      console.log(this.param.store_scan_ids)
    },
    initData () {
      getServiceConfig().then(res => {
        console.log(res)
        if (res.error === 0) {
          this.param = res.content
          this.param.store_scan_num = res.content.store_scan_ids.split(',').length
          console.log(this.param.store_scan_num)
        } else {
          this.$message.error(this.$t('storeCommon.operatefailed'))
        }
      })
    },
    number2boolean (configValue) {
      if (configValue === 1) {
        return true
      } else if (configValue === 0) {
        return false
      }
    },
    boolean2number (booleanValue) {
      if (booleanValue === true) {
        return 1
      } else if (booleanValue === false) {
        return 0
      }
    },
    // 更新配置项
    updateConfig () {
      console.log(this.param)
      updateServiceConfig(this.param).then(res => {
        console.log(res)
        if (res.error === 0) {
          this.$message.success(this.$t('storeCommon.updatesuccess'))
          this.initData()
        } else {
          this.$message.error(this.$t('storeCommon.updatefailed'))
        }
      })
      console.log()
    },
    // 门店买单判断权限
    handleToJurisdiction (flag) {
      let vsNameArr = ['store_pay', 'technician']
      vsNameArr.forEach((item, index) => {
        judgeJurisdictionRequest({
          'V-EnName': 'store_service_config',
          'V-VsName': item
        }).then(res => {
          console.log(res, flag)
          if (res.error === 10031) {
            if (index) {
              this.inputDisabled = true
              if (flag) {
                this.$http.$emit('jurisdictionDialog')
              }
            } else {
              this.radioDisabled = true
              if (flag) {
                this.$http.$emit('jurisdictionDialog')
              }
            }
          }
        })
      })
    }
  }
}

</script>
<style lang="scss" scoped>
.tradeProcessConfigure {
  padding: 10px;
  padding-bottom: 20px;
  .settingWrapper {
    font-size: 13px;
    .title {
      height: 40px;
      line-height: 40px;
      background: #eef1f6;
      padding-left: 16px;
      span {
        display: inline-block;
        border-left: 2px solid #5a8bff;
        height: 14px;
        width: 8px;
        margin-bottom: -1px;
      }
    }
    .settingContent {
      height: 60px;
      line-height: 60px;
      padding-left: 10px;
      background: #ffffff;
      color: #333;
      .Jurisdiction {
        width: 140px;
      }
      .notefont {
        color: #666;
        margin-left: 20px;
      }
      .inputWidth {
        width: 100px;
        margin: 0 5px;
      }
      .inputWidthSmall {
        width: 50px;
        margin: 0 5px;
      }
    }
    .delay {
      height: 35px;
      line-height: 35px;
    }
    .top {
      margin-top: 15px;
    }
    .bottom {
      margin: 0 0 15px 10px;
    }
    .WeChatExpress {
      font-size: 14px;
      div {
        margin-bottom: 10px;
      }
      .switchText {
        line-height: 25px;
        color: #333;
        width: 85px;
        margin: 15px 20px 0 10px;
      }
      .expressInfo {
        margin-top: 15px;
        .grayText {
          color: #999;
          line-height: 25px;
        }
        .addressContent {
          color: #333;
          display: flex;
          .address {
            line-height: 25px;
            margin-right: 20px;
          }
        }
        .expressTable {
          width: 500px;
          line-height: 35px;
          table {
            width: 100%;
            text-align: center;
            thead {
              background: #f5f5f5;
            }
            tbody td {
              border: 1px solid #eee;
            }
          }
        }
      }
    }
  }
  .settingWrapper:nth-of-type(1) {
    margin-top: 20px;
  }
  .requiredInfo {
    .necessaryGoodsInfo {
      height: 60px;
      line-height: 60px;
      color: #666;
    }
    .goodsWrapper {
      margin: 10px 0;
      .addGoods {
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5a8bff;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        margin: 10px 0;
      }
    }
  }
  .btn {
    display: flex;
    justify-content: center;
    width: 100%;
    margin-top: 30px;
  }
  /deep/ .tableClss th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    padding: 8px 10px;
    color: #333;
  }
  .table_list {
    position: relative;
    .table_footer {
      background: #666;
    }
  }
}
</style>
