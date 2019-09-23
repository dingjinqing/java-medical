<template>
  <div class="content">
    <wrapper>
      <section>
        <el-form label-width="100px">
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="grid-content">
                <el-form-item :label="$t('luckyDraw.mobile')">
                  <el-input
                    v-model="requestParams.mobile"
                    :placeholder="$t('luckyDraw.mobile')"
                    size="small"
                    clearable
                  ></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="grid-content">
                <el-form-item :label="$t('luckyDraw.userName')">
                  <el-input
                    v-model="requestParams.username"
                    :placeholder="$t('luckyDraw.userName')"
                    size="small"
                    clearable
                  ></el-input>
                </el-form-item>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="grid-content ">
                <el-form-item :label="$t('luckyDraw.luckSource')">
                  <el-select
                    v-model="requestParams.lotterySource"
                    :placeholder="$t('luckyDraw.luckSource')"
                    size="small"
                    class="inputWidth"
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
              </div>
            </el-col>
            <el-col :span="6">
              <div class="grid-content ">
                <el-form-item :label="$t('luckyDraw.chanceSource')">
                  <el-select
                    v-model="requestParams.chanceSource"
                    :placeholder="$t('luckyDraw.chanceSource')"
                    size="small"
                    class="inputWidth"
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
              </div>
            </el-col>
          </el-row>

        </el-form>
      </section>
    </wrapper>
    <wrapper>
      <div class="table_list">
        <el-table
          :data="tableData"
          class="version-manage-table"
          header-row-class-name="tableClss"
          border
          style="width: 100%"
        >
          <el-table-column
            prop="lotteryName"
            :label="$t('luckyDraw.activityName')"
            align="center"
          >
          </el-table-column>

        </el-table>
        <div class="footer">
          <pagination
            :page-params.sync="pageParams"
            @pagination="handlePagination"
          />
        </div>
      </div>
    </wrapper>
  </div>

</template>
<script>
import {
  getLotteryRecordList
} from '@/api/admin/marketManage/luckyDraw.js'
import pagination from '@/components/admin/pagination/pagination.vue'
import wrapper from '@/components/admin/wrapper/wrapper'

export default {
  components: {
    pagination,
    wrapper
  },
  data () {
    return {
      tableData: [],
      pageParams: {},
      requestParams: {
        currentPage: 1,
        pageRows: 20,
        lotteryId: null,
        mobile: null,
        username: null,
        lotteryGrade: 0,
        lotterySource: 0,
        chanceSource: 0
      }
    }
  },
  watch: {
    lang () {
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
      getLotteryRecordList(this.requestParams).then(res => {
        console.log(res)
        this.pageParams = res.content.page
        this.tableData = res.content.dataList
      }).catch(() => {
        this.$message.error('数据加载失败')
      })
    },
    resDataFilter (data) {
      data.map((item, index) => {
        item.dateValidity = item.startTime + '~' + item.endTime
        item.statusText = this.getActStatusString(item.status, item.startTime, item.endTime)
      })
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
