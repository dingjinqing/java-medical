<template>
  <div class="content">
    <div class="main">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          :label="$t('mintegralExchange.allPoints')"
          name="first"
        >
        </el-tab-pane>
        <el-tab-pane
          :label="$t('mintegralExchange.haveInHand')"
          name="second"
        >
        </el-tab-pane>
        <el-tab-pane
          :label="$t('mintegralExchange.notYetBegun')"
          name="third"
        >
        </el-tab-pane>
        <el-tab-pane
          :label="$t('mintegralExchange.expired')"
          name="fourth"
        >
        </el-tab-pane>
        <el-tab-pane
          :label="$t('mintegralExchange.deactivated')"
          name="fifth"
        >

        </el-tab-pane>
        <el-tab-pane
          :label="sixTitle"
          name="sixth"
          v-if="showSix"
        >
          <IntegralExchangeAdd
            :id="editId"
            @backHome="backHome"
          />
        </el-tab-pane>
      </el-tabs>
      <el-button
        type="primary"
        @click="addActivity"
        v-if="!showSix"
      >{{$t('mintegralExchange.addPoints')}}</el-button>
    </div>
    <div
      class="table_list"
      v-if="!showSix"
    >
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="name"
          :label="$t('mintegralExchange.activityName')"
          align="center"
        >

        </el-table-column>
        <el-table-column
          prop="goodsName"
          :label="$t('mintegralExchange.tradeName')"
          align="center"
          width="200"
        >
          <template slot-scope="scope">
            <div class="goodImge">
              <div>
                <img :src="$imageHost+'/'+scope.row.goodsImg">
              </div>
              <div class="name">
                {{scope.row.goodsName}}
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="actDate"
          :label="$t('mintegralExchange.termOfValidity')"
          align="center"
          width="200"
        >
          <template slot-scope="scope">
            {{scope.row.startTime}}<br>{{$t('mintegralExchange.to')}}<br>{{scope.row.endTime}}
          </template>
        </el-table-column>
        <el-table-column
          prop="money"
          :label="$t('mintegralExchange.cashExchange')"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.money?scope.row.money:'0'}}
          </template>
        </el-table-column>

        <el-table-column
          prop="score"
          :label="$t('mintegralExchange.productScore')"
          align="center"
        >
          <template slot-scope="scope">
            {{scope.row.score?scope.row.score:'0'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsNumber"
          :label="$t('mintegralExchange.commodityInventory')"
          align="center"
          width="90"
        >
          <template slot-scope="scope">
            {{scope.row.goodsNumber?scope.row.goodsNumber:'0'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="stock"
          :label="$t('mintegralExchange.exchangeInventory')"
          align="center"
          width="90"
        >
          <template slot-scope="scope">
            {{scope.row.stock?scope.row.stock:'0'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="number"
          :label="$t('mintegralExchange.quantityRedeemed')"
          align="center"
          width="90"
        >
          <template slot-scope="scope">
            {{scope.row.number?scope.row.number:'0'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="userNumber"
          :label="$t('mintegralExchange.redemptionUsers')"
          align="center"
          width="90"
        >
          <template slot-scope="scope">
            {{scope.row.userNumber?scope.row.userNumber:'0'}}
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('mintegralExchange.operation')"
          align="center"
          width="170"
        >
          <template slot-scope="scope">
            <div class="opt">
              <el-tooltip
                :content="$t('mintegralExchange.enable')"
                placement="top"
                v-if="scope.row.status===0&&scope.row.actStatus!==3"
              >
                <span
                  @click="handleToOption(scope.row,0)"
                  class="iconfont iconqiyong iconSpan"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('mintegralExchange.delete')"
                placement="top"
                v-if="scope.row.actStatus===3 || scope.row.actStatus===4"
              >
                <span
                  class="iconfont iconshanchu2 iconSpan"
                  @click="handleToOption(scope.row,1)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('mintegralExchange.edit')"
                placement="top"
                v-if="scope.row.actStatus===1 || scope.row.actStatus===2"
              >
                <span
                  class="el-icon-edit-outline iconSpan"
                  @click="handleToOption(scope.row,2)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('mintegralExchange.share')"
                placement="top"
                v-if="scope.row.actStatus!==3&&scope.row.actStatus!==4"
              >
                <span
                  class="el-icon-share iconSpan"
                  @click="handleToOption(scope.row,3)"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('mintegralExchange.discontinueUse')"
                placement="top"
                v-if="(scope.row.status===1&&scope.row.actStatus!==3)"
              >
                <span
                  @click="handleToOption(scope.row,4)"
                  class="el-icon-circle-close iconSpan"
                ></span>
              </el-tooltip>

              <el-tooltip
                :content="$t('mintegralExchange.viewPoint')"
                placement="top"
                v-if="scope.row.status!==2"
              >
                <span
                  @click="handleToOption(scope.row,5)"
                  class="iconfont icondingdan iconSpan"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('mintegralExchange.getNewUser')"
                placement="top"
                v-if="scope.row.status!==2"
              >
                <span
                  @click="handleToOption(scope.row,6)"
                  class="el-icon-user iconSpan"
                ></span>
              </el-tooltip>
              <el-tooltip
                :content="$t('mintegralExchange.viewPointRedemption')"
                placement="top"
                v-if="scope.row.status!==2"
              >
                <span
                  @click="handleToOption(scope.row,7)"
                  class="iconfont iconmingxi1 iconSpan"
                >{{scope.status}}</span>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="seacherGroupIntegrationList"
      />
      <!--二次确认弹窗-->
      <el-dialog
        :title="$t('mintegralExchange.tips')"
        :visible.sync="secondaryVisible"
        width="30%"
      >
        <div class="dialogTip">{{$t('mintegralExchange.sureToDelete')}}</div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="secondaryVisible = false">{{$t('mintegralExchange.cancellation')}}</el-button>
          <el-button
            type="primary"
            @click="handleToSecondSure()"
          >{{$t('mintegralExchange.determine')}}</el-button>
        </span>
      </el-dialog>
      <!--分享弹窗-->
      <el-dialog
        :title="$t('mintegralExchange.shareWithFriends')"
        :visible.sync="shareVisible"
        width="30%"
      >
        <div class="copyContainer">
          <img
            :src="posterAddressImgUrl"
            alt=""
            style="width:160px;height:160px"
            class="code_imgs"
          >
        </div>
        <div
          class="copyContainer"
          style="color:#999"
        >
          {{$t('mintegralExchange.downloadPoster')}}
        </div>
        <div class="copyContainer copyDiv">
          <span>{{$t('mintegralExchange.downloadPosterLink')}}</span>
          <el-input
            size="small"
            v-model="posterAddress"
            ref="qrCodePageUrlInput"
          ></el-input>
          <span
            class="copy"
            @click="handelToCopy"
          >{{$t('mintegralExchange.copy')}}</span>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { integralExchangeList, integralDel, integralDiscontinueUse, integralshare } from '@/api/admin/marketManage/integralExchange'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination.vue'), // 分页组件
    IntegralExchangeAdd: () => import('./integralExchangeAdd') // 添加积分兑换
  },
  data () {
    return {
      activeName: 'second',
      shareImgPath: '',
      sharePagePath: '',
      shareDialogShow: false,
      tableData: [], // 表格数据
      actState: 1, // 当前tap下标
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      showSix: false, // 是否显示第六个隐藏的tap
      isEditId: 0,
      editId: -1, // 编辑id
      secondaryVisible: false, // 二次确认弹窗flag
      nowClickOptionFlag: null, // 当前点击的操作项
      row: null, // 当前点击的项
      shareVisible: false, // 分享弹窗flag
      posterAddressImgUrl: '', // 分享图路径
      posterAddress: '' // 分享地址链接
    }
  },
  computed: {
    sixTitle () {
      return this.$t('mintegralExchange.addPoints')
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化数据
    this.handleToInit()
  },
  methods: {
    // 初始化数据
    handleToInit () {
      let params = {
        'actState': this.actState,
        'page': {
          'currentPage': this.pageParams.currentPage,
          'pageRows': '20'
        }
      }
      integralExchangeList(params).then(res => {
        console.log(res)
        if (res.error === 0) {
          let data = res.content
          this.tableData = data.dataList
          this.pageParams = Object.assign(this.pageParams, data.page)
        }
      })
    },
    handleClick (e) {
      if (this.activeName === 'sixth') { // 判断点的是否是第六个tap
        this.showSix = true
      } else {
        this.showSix = false
      }
      this.pageParams.currentPage = 1
      this.actState = Number(e.index)
      this.handleToInit()
    },
    seacherGroupIntegrationList () {
      this.handleToInit()
    },
    // 处理
    handleToOption (row, flag) {
      this.row = row
      this.nowClickOptionFlag = flag
      switch (flag) {
        case 0: // 启用
          integralDiscontinueUse({ id: row.id }).then(res => {
            if (res.error === 0) {
              this.$message.success({
                message: this.$t('mintegralExchange.enabledSuccessfully'),
                showClose: true
              })
              this.handleToInit()
            } else {
              this.$message.error({
                message: this.$t('mintegralExchange.failedToEnable'),
                showClose: true
              })
            }
          })
          break
        case 1: // 删除
          this.secondaryVisible = true
          break
        case 2: // 编辑
          this.editId = row.id
          this.showSix = true
          this.activeName = 'sixth'
          break
        case 3: // 分享
          integralshare({ id: row.id }).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.posterAddressImgUrl = res.content.imageUrl
              this.posterAddress = res.content.pagePath
            }
          })
          this.shareVisible = true

          break
        case 4: // 停用
          integralDiscontinueUse({ id: row.id }).then(res => {
            if (res.error === 0) {
              this.$message.success({
                message: this.$t('mintegralExchange.shutdownSuccessful'),
                showClose: true
              })
              this.handleToInit()
            } else {
              this.$message.error({
                message: this.$t('mintegralExchange.shutdownFailure'),
                showClose: true
              })
            }
          })
          break
        case 5: // 查看积分兑换订单
          console.log(row)
          this.$router.push({
            path: 'pointsExchangeOrder',
            query: {
              activityId: row.id,
              activityName: row.name
            }
          })
          break
        case 6: // 获取新用户明细
          this.$router.push({
            path: 'newUserDetails',
            query: {
              activityId: row.id,
              activityName: row.name
            }
          })
          break
        case 7: // 查看积分兑换用户
          this.$router.push({
            path: 'pointsUserList',
            query: {
              activityId: row.id
            }
          })
          break
      }
    },
    // 点击复制
    handelToCopy () {
      this.$refs.qrCodePageUrlInput.select()
      document.execCommand('Copy')
    },
    // 增加瓜分积分活动
    addActivity () {
      this.showSix = true
      this.activeName = 'sixth'
      this.editId = -1
    },
    // 二次确认弹窗处理事件
    handleToSecondSure () {
      switch (this.nowClickOptionFlag) {
        case 1: // 删除积分兑换活动
          integralDel({ id: this.row.id }).then(res => {
            console.log(res)
            if (res.error === 0) {
              this.$message.success({
                message: this.$t('mintegralExchange.deleteSuccessful'),
                showClose: true
              })
              this.handleToInit()
            } else {
              this.$message.error({
                message: this.$t('mintegralExchange.deleteFailed'),
                showClose: true
              })
            }
            this.secondaryVisible = false
          })
          break
      }
    },
    backHome (flag) { // 添加积分兑换活动页面保存返回事件
      console.log(flag)
      if (flag) {
        this.showSix = false
        this.activeName = 'first'
        this.handleToInit()
      }
    }
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/aliIcon/iconfont.scss";
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;
  }
}
.condition {
  position: relative;
  background-color: #fff;
  padding: 10px 20px 0 20px;
}
.p_top_right {
  display: flex;
  /deep/ .el-button {
    padding: none;
    height: 32px;
  }
  span {
    white-space: nowrap;
    height: 32px;
    line-height: 32px;
    margin-right: 10px;
  }
  .topRightDiv {
    &:nth-of-type(2) {
      margin: 0 10px 0 30px;
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
.table_list {
  position: relative;
  margin-top: 10px;
  background-color: #fff;
  padding: 10px 20px 20px 20px;
  min-height: 380px;
}
.paginationfooter {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
}
.opt {
  text-align: left;
  color: #5a8bff;
  span {
    cursor: pointer;
  }
}
.balanceDialo .el-dialog__body {
  padding-bottom: 0 !important;
}
.balanceDialo .el-dialog__footer {
  border-top: 1px solid #eee;
}
.setUpDialog .el-dialog__body {
  padding-top: 10px !important;
}
.add_coupon {
  float: left;
  margin-left: 65%;
}
.iconSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
  margin-top: 5px;
}
.goodImge {
  display: flex;
  img {
    width: 50px;
    height: 50px;
    line-height: 50px;
    border: 1px solid #ccc;
  }
  .name {
    width: 115px;
    height: 40px;
    text-overflow: ellipsis;
    overflow: hidden;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    display: -webkit-box;
    margin-left: 12px;
    text-align: left;
  }
}
.dialogTip {
  display: flex;
  justify-content: center;
}
.copyContainer {
  display: flex;
  justify-content: center;
  .copy {
    cursor: pointer;
    color: #5a8bff;
  }
  /deep/ .el-input {
    width: 200px;
    margin: 0 10px;
  }
}
.copyDiv {
  align-items: center;
  margin-top: 20px;
  span {
    white-space: nowrap;
  }
}
</style>
