<template>
  <div class="content">
    <section class="content-top">
      <el-form
        label-width="90px"
        :inline="true"
      >

        <el-form-item :label="$t('luckyDraw.mobile')+'：'">
          <el-input
            class="filter-input"
            v-model="requestParams.mobile"
            :placeholder="$t('luckyDraw.mobile')"
            size="small"
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item :label="$t('luckyDraw.userName')+'：'">
          <el-input
            class="filter-input"
            v-model="requestParams.username"
            :placeholder="$t('luckyDraw.userName')"
            size="small"
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item :label="$t('luckyDraw.lotterySource')+'：'">
          <el-select
            v-model="requestParams.lotterySource"
            :placeholder="$t('luckyDraw.lotterySource')"
            size="small"
            class="filter-input"
            filterable
          >
            <el-option
              v-for="item in $t('luckyDraw.luckSourceList')"
              :key="item[0]"
              :label="item[1]"
              :value="item[0]"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item
          :label="$t('luckyDraw.chanceSource')+'：'"
          label-width="120px"
        >
          <el-select
            v-model="requestParams.chanceSource"
            :placeholder="$t('luckyDraw.chanceSource')"
            size="small"
            class="filter-input"
            filterable
          >
            <el-option
              v-for="item in $t('luckyDraw.chanceSourceList')"
              :key="item[0]"
              :label="item[1]"
              :value="item[0]"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item :label="$t('luckyDraw.lotteryGrade')+'：'">
          <el-select
            v-model="requestParams.lotteryGrade"
            :placeholder="$t('luckyDraw.lotteryGrade')"
            size="small"
            class="filter-input"
            filterable
          >
            <el-option
              v-for="item in $t('luckyDraw.lotteryGradeList')"
              :key="item[0]"
              :label="item[1]"
              :value="item[0]"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button
            size="small"
            type="primary"
            @click="initPageData"
          >{{$t('luckyDraw.query')}}
          </el-button>
        </el-form-item>

      </el-form>
    </section>
    <div class="table_list">
      <el-table
        :data="tableData"
        class="version-manage-table"
        header-row-class-name="tableClss"
        border
        style="width: 100%"
      >
        <el-table-column
          prop="username"
          :label="$t('luckyDraw.userName')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="mobile"
          :label="$t('luckyDraw.mobile')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="createTime"
          :label="$t('luckyDraw.createTime')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="lotterySource"
          :label="$t('luckyDraw.lotterySource')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="lotteryGrade"
          :label="$t('luckyDraw.lotteryGrade')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="chanceSource"
          :label="$t('luckyDraw.chanceSource')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="lotteryPrize"
          :label="$t('luckyDraw.lotteryPrize')"
          align="center"
        >
        </el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :page-params.sync="pageParams"
          @pagination="initPageData"
        />
      </div>
    </div>
  </div>

</template>
<script>
import {
  getLotteryRecordList
} from '@/api/admin/marketManage/luckyDraw.js'
import pagination from '@/components/admin/pagination/pagination.vue'

export default {
  components: {
    pagination
  },
  data () {
    return {
      tableData: [],
      pageParams: {},
      requestParams: {
        currentPage: 1,
        pageRows: 20,
        lotteryId: this.$route.query.id,
        mobile: null,
        username: null,
        lotteryGrade: null,
        lotterySource: null,
        chanceSource: null
      }
    }
  },
  watch: {
    lang () {
      this.initPageData()
    }
  },
  mounted () {
    // 初始化国际化
    this.langDefault()
    // 初始化页面数据
    this.initPageData()
  },
  methods: {
    initPageData () {
      console.log('getLotteryRecordList')
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      getLotteryRecordList(this.requestParams).then(res => {
        console.log(res)
        this.pageParams = res.content.page
        this.tableData = this.resDataFilter(res.content.dataList)
      }).catch(() => {
        this.$message.error(this.$t('luckyDraw.fetchDataFail'))
      })
    },
    resDataFilter (data) {
      data.map((item, index) => {
        item.chanceSource = new Map(this.$t('luckyDraw.chanceSourceList')).get(item.chanceSource)
        item.lotterySource = new Map(this.$t('luckyDraw.luckSourceList')).get(item.lotterySource)
        item.lotteryGrade = new Map(this.$t('luckyDraw.lotteryGradeList')).get(item.lotteryGrade)
        if (item.presentType === 0) {
          // item.lotteryPrize =
        } else {

        }
      })
      return data
    },
    handlePagination () {
      this.initPageData()
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
  .content-top {
    background: #fff;
    padding: 15px;
  }
  .filter-input {
    width: 170px;
  }

  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 10px 20px;

    .wrapper {
      display: flex;
      justify-content: space-between;

      .rightContent {
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
  padding: 10px 20px 10px 20px;
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

.opt {
  text-align: left;
  color: #5a8bff;

  span {
    cursor: pointer;
  }
}

.add_coupon {
  float: left;
  margin-left: 65%;
}

.footer {
  padding: 20px 0 20px 20px;
  display: flex;
  justify-content: flex-end;

  span {
    display: block;
    height: 32px;
    line-height: 32px;
  }
}
</style>
