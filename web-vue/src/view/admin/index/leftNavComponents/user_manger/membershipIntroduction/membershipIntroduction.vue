<template>
  <!-- 会员导入 -->
  <div class="introductionContainer">
    <div class="introductionMain">
      <div class="filter-list">
        <div class="introductionMain_top">
          <div class="topLeft">{{$t('memberIntroductionList.description')}}</div>
          <div class="topRight">
            <el-button
              type="primary"
              size="small"
              @click="handleSetAll(0)"
            >{{$t('memberIntroductionList.setNotify')}}</el-button>
            <el-button
              type="primary"
              size="small"
              @click="handleSetAll(1)"
            >{{$t('memberIntroductionList.memberImport')}}</el-button>
          </div>
        </div>
        <div class="introductionMain_middle">
          <div class="batchNumber">
            <span>{{$t('memberIntroductionList.importBatchNum')}}</span>
            <el-input
              v-model="queryParams.batchId"
              :placeholder="$t('memberIntroductionList.piCon')"
              size="small"
            ></el-input>
          </div>
          <div
            class="batchNumber"
            style="margin-right:10px"
          >
            <span>{{$t('memberIntroductionList.importTime')}}</span>
            <el-date-picker
              v-model="introducionDate"
              type="daterange"
              :range-separator="$t('memberIntroductionList.to')"
              :start-placeholder="$t('memberIntroductionList.startDate')"
              :end-placeholder="$t('memberIntroductionList.endDate')"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00','23:59:59']"
              size="small"
            >
            </el-date-picker>
          </div>
          <el-button
            type="primary"
            size="small"
            @click="handleSetAll(2)"
          >{{$t('memberIntroductionList.filter')}}</el-button>
          <el-button
            type="primary"
            size="small"
            @click="goMemberList"
          >{{$t('memberIntroductionList.viewInactive')}}</el-button>
        </div>
      </div>
      <div class="table-list">
        <el-table
          class="auth-list"
          header-row-class-name='tableClss'
          :data="tableData"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="id"
            :label="$t('memberIntroductionList.importBatchNum')"
            align="center"
            width="110px"
          >
          </el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('memberIntroductionList.importTime')"
            align="center"
          >
          </el-table-column>
          <el-table-column
            align="center"
            :label="$t('memberIntroductionList.memberCard')"
          >
            <template slot-scope="{row}">
              <div
                v-for="(item, index) in row.cardList"
                :key="index"
              >
                <router-link :to="{name: '/admin/home/main/normalCardDetail', query: {cardId: row.cardId}}">{{row.cardName}}</router-link>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="successNum"
            align="center"
            :label="$t('memberIntroductionList.successNum')"
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="failNum"
            align="center"
            :label="$t('memberIntroductionList.failNum')"
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="activateNum"
            align="center"
            :label="$t('memberIntroductionList.activeNum')"
            width="100px"
          >

          </el-table-column>
          <el-table-column
            align="center"
            :label="$t('memberIntroductionList.operate')"
          >
            <template slot-scope="{row}">
              <div
                class="num"
                v-if="row.totalNum - row.successNum > 0"
                @click="handleClickO(row, 0)"
              >{{$t('memberIntroductionList.downFail')}}</div>
              <div
                v-else-if="row.activateNum > 0"
                class="num"
                @click="handleClickO(row, 1)"
              >{{$t('memberIntroductionList.downActive')}}</div>
              <div
                v-else-if="row.successNum - row.activateNum > 0"
                class="num"
                @click="handleClickO(row, 2)"
              >{{$t('memberIntroductionList.viewInactive')}}</div>
            </template>
          </el-table-column>
        </el-table>
        <div class="footer">
          <pagination
            :pageParams="pageParams"
            @pagination="initDataList"
          ></pagination>
        </div>
      </div>
    </div>
    <!--设置激活通知弹窗-->
    <ActivationNotificationDialog :visible.sync="activationNotificationVisible"></ActivationNotificationDialog>
    <!-- 会员导入 -->
    <MemberIntroductionDialog :visible.sync="memberIntroductionVisible"></MemberIntroductionDialog>
  </div>
</template>
<script>
import { getImportList, exportActivate, exportFailData } from '@/api/admin/memberManage/membershipIntroduction.js'
import { download } from '@/util/excelUtil.js'
export default {
  components: {
    ActivationNotificationDialog: () => import('./activationNotificationDialog'),
    MemberIntroductionDialog: () => import('./memberIntroductionDialog'),
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      loading: false,
      introducionDate: '', // 导入时间
      tableData: [],
      pageParams: {},
      queryParams: {
        batchId: '',
        startTime: '',
        endTime: ''
      },
      activationNotificationVisible: false, // 设置激活通知
      memberIntroductionVisible: false // 会员导入
    }
  },
  watch: {
    introducionDate: function (newVal) {
      if (newVal) {
        this.$set(this.queryParams, 'startTime', newVal[0])
        this.$set(this.queryParams, 'endTime', newVal[1])
      }
    }
  },
  mounted () {
    this.initDataList()
  },
  methods: {
    initDataList () {
      let that = this
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getImportList(params).then(res => {
        if (res.error === 0) {
          that.tableData = res.content.dataList
          that.pageParams = res.content.page
        }
      })
    },
    // 操作
    handleClickO (row, mark) {
      switch (mark) {
        // 下载失败数据
        case 0:
          this.downloadFailData(row)
          break
        // 下载激活数据
        case 1:
          this.downloadActivateData(row)
          break
        // 查看未激活成员
        case 2:
          this.$router.push({
            path: '/admin/home/main/inactiveMembersList',
            query: {
              id: row.id
            }
          })
          break
      }
    },
    // 下载激活数据
    downloadActivateData (row) {
      let params = {
        batchId: row.id
      }
      this.loading = true
      exportActivate(params).then(res => {
        this.loading = false
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch((err, data) => {
        console.error('err:', err)
        this.loading = false
      })
    },
    // 下载失败数据
    downloadFailData (row) {
      let params = {
        batchId: row.id
      }
      this.loading = true
      exportFailData(params).then(res => {
        this.loading = false
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName && fileName !== 'undefined' ? fileName.split(';')[1].split('=')[1] : 'template.xlsx'
        download(res, decodeURIComponent(fileName))
      }).catch((err, data) => {
        console.error('err:', err)
        this.loading = false
      })
    },
    // 查看未激活的会员
    goMemberList () {
      this.$router.push({
        path: '/admin/home/main/inactiveMembersList'
      })
    },
    // 设置激活通知  会员导入  筛选 综合处理
    handleSetAll (flag) {
      let that = this
      switch (flag) {
        case 0:
          that.activationNotificationVisible = !that.activationNotificationVisible
          break
        case 1:
          that.memberIntroductionVisible = !that.memberIntroductionVisible
          break
        case 2:
          that.initDataList()
          break
      }
    }
  }
}
</script>

<style scoped lang="scss">
.introductionContainer {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .introductionMain {
    position: relative;
    // background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    // padding: 10px;
    .introductionMain_top {
      display: flex;
      justify-content: space-between;
      background-color: #f5f5f5;
      padding: 10px;
      .topLeft {
        height: 32px;
        line-height: 32px;
        color: #666;
      }
    }
    .introductionMain_middle {
      padding: 8px 0;
      display: flex;
      span {
        white-space: normal;
        display: block;
        height: 32px;
        line-height: 32px;
        margin: 0 25px;
      }
      .batchNumber {
        display: flex;
        /deep/ .el-input {
          width: 150px;
        }
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
  .num {
    color: #5a8bff;
    cursor: pointer;
    &:hover {
      color: #000;
    }
  }
  .footer {
    height: 50px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
  .filter-list {
    padding: 15px;
    background: #fff;
  }
  .table-list {
    padding: 15px;
    background: #fff;
    margin-top: 10px;
  }
}
</style>
