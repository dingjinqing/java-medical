<template>
  <div class="content">
    <div class="main">
      <statusTab
        v-model="tabIndex"
        :activityName="activityName"
        :standard="true"
      />
      <div class="wrapper">
        <el-button
          type="primary"
          size="small"
          @click="addActivity"
        >{{$t('firstSpecial.addFirstSpecial')}}</el-button>

        <div class="rightContent">
          <div style="margin:7px 3px 0 0">
            <el-tooltip
              content="例：共设置了三件活动商品A、B、C，用户可从中任选两件（如：A、B）购买，超出限购数量的(C)将以商品原价购买"
              placement="top"
              effect="light"
            >
              <img
                :src="$imageHost + '/image/admin/analysis_tishi.png'"
                alt=""
              >
            </el-tooltip>
          </div>
          <span>{{$t('firstSpecial.firstSpecialLimitGoodsTip1')}}</span>
          <el-input-number
            v-model="firstSpecialLimitGoods"
            controls-position="right"
            size="small"
            :precision="0"
            :min="0"
            style="width: 100px;margin: 0 5px;"
          ></el-input-number>
          <!-- <el-input
            v-model="firstSpecialLimitGoods"
            style="width: 80px;margin: 0 5px;"
            size="small"
            type="number"
            min='0'
          ></el-input> -->
          <span>{{$t('firstSpecial.firstSpecialLimitGoodsTip2')}}</span>
          <span style="margin-left:3px">{{$t('firstSpecial.firstSpecialLimitGoodsTip3')}}</span>
          <el-button
            @click="setFirstSpecialLimitGoods"
            type="primary"
            size="small"
          >{{$t('firstSpecial.save')}}</el-button>
        </div>
      </div>
    </div>
    <div class="table_list">
      <el-table
        v-loading="loading"
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="tableData"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="name"
          :label="$t('firstSpecial.actName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="first"
          :label="$t('firstSpecial.first')"
          align="center"
          width="100"
        >
        </el-table-column>

        <el-table-column
          prop="goodsAmount"
          :label="$t('firstSpecial.goodsAmount')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="vaildDate"
          :label="$t('firstSpecial.vaildDate')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="statusName"
          :label="$t('firstSpecial.statusName')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="orderAmount"
          :label="$t('firstSpecial.orderAmount')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="userAmount"
          :label="$t('firstSpecial.userAmount')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          prop="paymentTotalAmount"
          :label="$t('firstSpecial.paymentTotalAmount')"
          align="center"
        >
        </el-table-column>

        <el-table-column
          :label="$t('marketCommon.operate')"
          align="center"
        >
          <template slot-scope="scope">
            <div class="operation">
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('marketCommon.edit')"
                placement="top"
                v-if="scope.row.status === 1"
              >
                <i
                  class="el-icon-edit-outline"
                  @click="edit(scope.row.id)"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('marketCommon.delete')"
                placement="top"
                v-else
              >
                <i
                  @click="deleteFirstSpecial(scope.row.id)"
                  class="el-icon-delete"
                ></i>
              </el-tooltip>
              <el-tooltip
                v-if="scope.row.status === 1"
                class="item"
                effect="dark"
                :content="$t('marketCommon.disable')"
                placement="top"
              >
                <i
                  @click="disabledFirstSpecial(scope.row.id)"
                  class="el-icon-remove-outline"
                ></i>
              </el-tooltip>
              <el-tooltip
                v-else
                class="item"
                effect="dark"
                :content="$t('marketCommon.enabled')"
                placement="top"
              >
                <i
                  @click="enableFirstSpecial(scope.row.id)"
                  class="el-icon-circle-check"
                ></i>
              </el-tooltip>
              <el-tooltip
                class="item"
                effect="dark"
                :content="$t('firstSpecial.orderDetail')"
                placement="top"
              >
                <i
                  class="el-icon-s-order"
                  @click="checkOrderList(scope.row.id)"
                ></i>
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>

</template>
<script>
import { firstSpecialList, updateFirstSpecial, delFirstSpecial, getFirstSpecialLimitGoods, setFirstSpecialLimitGoods } from '@/api/admin/marketManage/firstSpecial.js'
import statusTab from '@/components/admin/marketManage/status/statusTab'
import pagination from '@/components/admin/pagination/pagination'
export default {
  components: { pagination, statusTab },
  data () {
    return {
      activityName: '',
      // 默认显示进行中的活动
      tabIndex: 1,
      currentPage: 1,
      tableData: [],
      pageParams: {},
      loading: false,
      firstSpecialLimitGoods: 0,

      // 表格原始数据
      originalData: []
    }
  },
  watch: {
    'tabIndex' (n, o) {
      this.initDataList()
      this.langDefault()
    },

    // data内变量国际化
    lang () {
      this.activityName = this.$t('firstSpecial.firstSpecial')

      // 重新渲染表格数据
      let originalData = JSON.parse(JSON.stringify(this.originalData))
      this.handleData(originalData)
    }
  },
  mounted () {
    if (this.$route.query.tabIndex) {
      this.tabIndex = Number(this.$route.query.tabIndex)
    }
    // 初始列表化数据
    this.initDataList()
    // 取用户仅可购买活动商品中的数量
    getFirstSpecialLimitGoods().then((res) => {
      if (res.error === 0) {
        this.firstSpecialLimitGoods = res.content
      }
    })
    this.langDefault()
  },
  methods: {
    initDataList () {
      this.loading = true
      this.pageParams.state = parseInt(this.tabIndex)
      firstSpecialList(this.pageParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },

    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        if (item.isForever === 1) {
          item.vaildDate = this.$t('firstSpecial.permanent')
        } else {
          item.vaildDate = `${item.startTime} ` + this.$t('marketCommon.to') + ` ${item.endTime}`
        }
        item.statusName = this.getActStatusString(item.currentState)
      })
      this.tableData = data
    },

    // 停用
    disabledFirstSpecial (id) {
      let param = {
        'id': id,
        'status': 0
      }
      this.$confirm(this.$t('marketCommon.actDisableConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        updateFirstSpecial(param).then((res) => {
          if (res.error === 0) {
            this.$message.success(this.$t('marketCommon.successfulOperation'))
            this.initDataList()
          }
        })
      })
    },

    // 启用
    enableFirstSpecial (id) {
      let param = {
        'id': id,
        'status': 1
      }
      this.$confirm(this.$t('marketCommon.actEnabledConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        updateFirstSpecial(param).then((res) => {
          if (res.error === 0) {
            this.$message.success(this.$t('marketCommon.successfulOperation'))
            this.initDataList()
          }
        })
      })
    },

    // 删除
    deleteFirstSpecial (id) {
      let param = {
        'id': id
      }
      this.$confirm(this.$t('marketCommon.actDeleteConfirmTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        delFirstSpecial(param).then((res) => {
          if (res.error === 0) {
            this.$message.success(this.$t('marketCommon.successfulOperation'))
            this.initDataList()
          }
        })
      })
    },
    // 设置用户仅可购买活动商品中的数量
    setFirstSpecialLimitGoods () {
      setFirstSpecialLimitGoods(this.firstSpecialLimitGoods).then((res) => {
        if (res.error === 0) {
          this.$message.success(this.$t('marketCommon.successfulOperation'))
        } else {
          this.$message.error(this.$t('marketCommon.failureOperation'))
        }
      })
    },

    // 编辑点击事件
    edit (id) {
      this.$router.push({
        path: '/admin/home/main/firstSpecial/add',
        query: {
          id: id
        }
      })
    },

    // 跳转首单特惠订单页
    checkOrderList (id) {
      this.$router.push({
        path: '/admin/home/main/firstSpecial/orderList',
        query: {
          id: id
        }
      })
    },

    // 跳转到添加页
    addActivity () {
      this.$router.push({
        name: 'first_special_add'
      })
    }
  }

}
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  .main {
    position: relative;
    background-color: #fff;
    padding: 15px;
    .wrapper {
      display: flex;
      justify-content: space-between;
      .rightContent {
        display: flex;
        .el-button {
          margin-left: 5px;
        }
        span {
          height: 30px;
          line-height: 30px;
        }
        :nth-of-type(3) {
          color: #999;
        }
      }
    }
  }
}
.operation {
  display: flex;
  flex-wrap: wrap;
  margin-left: -5px;
  > .item {
    font-size: 22px;
    color: #66b1ff;
    cursor: pointer;
    margin-left: 5px;
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
  padding: 15px;
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
.footer {
  display: flex;
  justify-content: flex-end;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
}
</style>
