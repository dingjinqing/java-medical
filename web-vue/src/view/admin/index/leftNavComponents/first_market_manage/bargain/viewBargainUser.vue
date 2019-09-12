<!--
* 砍价 - 用户信息列表页面
*
* @author:赵鑫
-->
<template>
  <div>
    <!-- 上半部分：筛选条件查询部分 -->
    <wrapper style="padding: 0 30px">
      <section class="bargainUserMain">
        <div class="bargainUserInfo">
          <span>用户昵称</span>
          <el-input
            v-model="requestParams.username"
            size="small"
            class="inputWidth"
          ></el-input>
        </div>
        <div class="bargainUserInfo">
          <span>手机号</span>
          <el-input
            v-model="requestParams.mobile"
            size="small"
            class="inputWidth"
          ></el-input>
        </div>
        <div class="bargainUserInfo">
          <span>砍价状态</span>
          <el-select
            v-model="requestParams.status"
            placeholder="请选择状态"
            size="small"
            class="inputWidth"
          >
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :value="item.value"
              :label="item.label"
            ></el-option>
          </el-select>
        </div>
      </section>
      <section class="bargainUserMain infoBottom">
        <div style="display:flex">
          <span style="height:32px;line-height:32px;margin-right:20px">发起时间</span>
          <el-date-picker
            v-model="createDate"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="small"
          >
          </el-date-picker>
        </div>
        <div style="margin:0 20px">
          <el-button
            type="primary"
            size="small"
          >筛选</el-button>
        </div>
        <div>
          <el-button size="small">导出表格</el-button>
        </div>
      </section>
    </wrapper>

    <!-- 下半部分：表格数据部分 -->
    <wrapper>
      <div class="table_list">
        <el-table
          v-loading="loading"
          header-row-class-name="tableClss"
          border
          :data="tableData"
          style="width: 100%"
        >
          <el-table-column
            prop="goodsName"
            label="商品名称"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="username"
            label="发起砍价用户昵称"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="mobile"
            label="手机号码"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="createTime"
            label="发起时间"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="bargainMoney"
            label="已砍金额"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="surplusMoney"
            label="待砍金额"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="userNumber"
            label="参与砍价人数"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop="status"
            label="砍价状态"
            align="center"
          >
          </el-table-column>

          <el-table-column
            prop=""
            label="操作"
            align="center"
          >
            <template slot-scope="scope">
              <div class="operation">
                <a
                  @click="goBargainUserPageList(scope.row.id)"
                  href="##"
                >查看砍价用户</a>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </wrapper>

  </div>
</template>

<script>
import wrapper from '@/components/admin/wrapper/wrapper'
import dateTimePicker from '@/components/admin/dateTimePicker/dateTimePicker'
import pagination from '@/components/admin/pagination/pagination'
import { getRecordPageList } from '@/api/admin/marketManage/bargain.js'

export default {
  components: { wrapper, dateTimePicker, pagination },
  mounted () {
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
      this.initDataList()
    }
  },
  data () {
    return {
      loading: false,
      requestParams: {},
      pageParams: {},
      tableData: [],
      createDate: '',
      statusOptions: [
        {
          value: '0',
          label: '成功'
        }, {
          value: '1',
          label: '失败'
        },
        {
          value: '2',
          label: '砍价中'
        }
      ],

      // 表格原始数据
      originalData: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.requestParams.bargainId = this.actId
      this.requestParams.startTime = this.createDate[0]
      this.requestParams.endTime = this.createDate[1]
      this.requestParams.currentPage = this.pageParams.currentPage
      this.requestParams.pageRows = this.pageParams.pageRows
      getRecordPageList(this.requestParams).then((res) => {
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
        switch (item.status) {
          case 0:
            item.status = '砍价中'// this.$t('couponPackage.accessModeCash')
            break
          case 1:
            item.status = '成功'// this.$t('couponPackage.accessModeTntegral')
            break
          case 2:
            item.status = '失败'// this.$t('couponPackage.accessModeFree')
            break
        }
      })
      this.tableData = data
    },

    // 跳转帮忙砍价用户列表
    goBargainUserPageList (id) {
      this.$router.push({
        path: '/admin/home/main/bargain/record/detail',
        query: {
          recordId: id
        }
      })
    }
  }

}

</script>
<style lang="scss" scoped>
* {
  font-size: 14px;
}
.bargainUserMain {
  display: flex;
  .bargainUserInfo {
    margin-right: 50px;
    span {
      margin-right: 20px;
    }
    .inputWidth {
      width: 150px;
    }
  }
}
.infoBottom {
  margin-top: 20px;
}
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  color: #000;
  padding: 8px 10px;
}
.table_list {
  position: relative;
}
</style>
