<template>
  <div class="version-statistics">
    <div class="filter-menu pd-10 bg-white clearfixed">
      <div class="clearfixed">
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.versionNum"
          placeholder="请选择版本号"
          size="small"
          @change="handleSelectId()"
        >
          <el-option
            v-for="(item, index) in selectIdTemId"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.isAuth"
          placeholder="选择是否授权"
          @change="handleSelectId()"
          size="small"
        >
          <el-option
            v-for="(item, index) in selectIsAuthorization"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.isWxPay"
          placeholder="选择支持微信支付"
          @change="handleSelectId()"
          size="small"
        >
          <el-option
            v-for="(item, index) in selectIsPay"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.auditStatus"
          placeholder="选择审核状态"
          @change="handleSelectId()"
          size="small"
        >
          <el-option
            v-for="(item, index) in selectExamineStatus"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.isRelease"
          placeholder="选择发布状态"
          size="small"
        >
          <el-option
            v-for="(item, index) in selectReleaseStatus"
            :key="index"
            :value="item.value"
            :label="item.label"
          />
        </el-select>
      </div>
      <div class="row clearfixed">
        <span class="title fll">显示列: </span>
        <el-checkbox-group
          v-model="checkList"
          class="fll"
        >
          <el-checkbox :label="0">版本号</el-checkbox>
          <el-checkbox :label="1">是否授权</el-checkbox>
          <el-checkbox :label="2">支持微信支付</el-checkbox>
          <el-checkbox :label="3">审核状态</el-checkbox>
          <el-checkbox :label="4">发布状态</el-checkbox>
        </el-checkbox-group>
      </div>
    </div>

    <el-table
      class="auth-list mt-10"
      header-row-class-name="table-th"
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="userVersion"
        label="版本号"
        align="center"
        v-if="holdHiddenArr[0]"
      >
      </el-table-column>
      <el-table-column
        prop="isAuthOk"
        label="是否授权"
        align="center"
        v-if="holdHiddenArr[1]"
      >
      </el-table-column>
      <el-table-column
        prop="openPay"
        align="center"
        label="支持微信支付"
        v-if="holdHiddenArr[2]"
      >
      </el-table-column>
      <el-table-column
        prop="auditState"
        align="center"
        label="审核状态"
        v-if="holdHiddenArr[3]"
      >
      </el-table-column>
      <el-table-column
        prop="publishState"
        align="center"
        label="发布状态"
        v-if="holdHiddenArr[4]"
      >
      </el-table-column>
      <el-table-column
        align="center"
        label="店铺数量"
      >
        <template slot-scope="scope">
          <div class="num">{{scope.row.number}}</div>
        </template>
      </el-table-column>
    </el-table>

    <div class="footer">
      <div>每页20行记录，当前页面：{{this.currentPage}}，总页数：{{this.pageCount}}，总记录数：{{this.totle}}</div>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="20"
        layout="prev, pager, next, jumper"
        :total="totle"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { versionStatRequest, SpinnerListRequest } from '@/api/system/programManage'
export default {
  name: 'versionStatistics',
  data () {
    return {
      currentPage: 1,
      pageCount: 1,
      totle: 0,
      queryData: {
        versionNum: null, // 版本号
        isAuth: null, // 是否授权
        isWxPay: null, // 是否微信支付
        auditStatus: null, // 审核状态
        isRelease: null // 发布状态
      },
      checkList: [0, 3, 4], // 复选框选项
      tableData: [
        // {
        //   versionNum: '1.28.3',
        //   isAuth: '已授权',
        //   ispay: '支持微信支付',
        //   status1: '审核中',
        //   status2: '已发布',
        //   num: '2'
        // },
        // {
        //   versionNum: '1.28.3',
        //   isAuth: '已授权',
        //   ispay: '支持微信支付',
        //   status1: '审核中',
        //   status2: '已发布',
        //   num: '2'
        // },
        // {
        //   versionNum: '1.28.3',
        //   isAuth: '已授权',
        //   ispay: '支持微信支付',
        //   status1: '审核中',
        //   status2: '已发布',
        //   num: '2'
        // }
      ],
      holdHiddenArr: [false, false, false, false, false],
      selectIdTemId: [],
      selectIsAuthorization: [
        {
          value: '',
          label: '选择是否授权'
        },
        {
          value: '0',
          label: '未授权'
        },
        {
          value: '1',
          label: '已授权'
        }
      ],
      selectIsPay: [
        {
          value: '',
          label: '选择支持微信支付'
        },
        {
          value: '0',
          label: '不支持微信支付'
        },
        {
          value: '1',
          label: '支持微信支付'
        }
      ],
      selectExamineStatus: [
        {
          value: '',
          label: '选择审核状态'
        },
        {
          value: '0',
          label: '未提交审核'
        },
        {
          value: '1',
          label: '审核中'
        },
        {
          value: '2',
          label: '审核通过'
        },
        {
          value: '3',
          label: '审核未通过'
        }
      ],
      selectReleaseStatus: [
        {
          value: '',
          label: '选择发布状态'
        },
        {
          value: '0',
          label: '未发布'
        },
        {
          value: '1',
          label: '已发布'
        }
      ]
    }
  },
  watch: {
    checkList: {
      handler (newData) {
        console.log(newData)
        let arr = [0, 1, 2, 3, 4, 5]

        arr.map((item, index) => {
          if (newData.indexOf(item) !== -1) {
            this.holdHiddenArr[item] = true
          } else {
            this.holdHiddenArr[item] = false
          }
        })
      },
      immediate: true
    }
  },
  mounted () {
    // 初始化数据
    this.defaultData()
  },
  methods: {
    async defaultData () {
      let spinnerList = await SpinnerListRequest()
      console.log(spinnerList)
      if (spinnerList.error === 0) {
        let arr = []
        let defaultObj = {}
        defaultObj.value = ''
        defaultObj.label = '请选择版本号'
        arr.push(defaultObj)
        spinnerList.content.map((item, index) => {
          let obj = {}
          obj.value = index
          obj.label = item.userVersion
          arr.push(obj)
        })
        this.selectIdTemId = arr
      }
    },
    handleQueryTableData () {
      let obj = {
        'templateId': this.queryData.versionNum,
        'userVserion': '1.0.1',
        'isAuthOk': this.queryData.isAuth,
        'openPay': this.queryData.isWxPay,
        'auditState': this.queryData.auditStatus,
        'publishState': this.queryData.isRelease,
        'currentPage': this.currentPage,
        'pageRows': 0
      }
      versionStatRequest(obj).then((res) => {
        if (res.error === 0) {
          res.content.dataList.map((item, index) => {
            if (item.isAuthOk === 1) {
              item.isAuthOk = '已授权'
            } else {
              item.isAuthOk = '未授权'
            }
            if (item.openPay === 1) {
              item.openPay = '支持微信支付'
            } else {
              item.openPay = '不支持微信支付'
            }
            switch (item.auditState) {
              case 0:
                item.auditState = '未提交'
                break
              case 1:
                item.auditState = '深刻中'
                break
              case 2:
                item.auditState = '审核成功'
                break
              case 3:
                item.auditState = '审核失败'
                break
            }
            if (item.publishState === 0) {
              item.publishState = '已发布'
            } else {
              item.publishState = '未发布'
            }
          })
          this.tableData = res.content.dataList
        }
        console.log(res)
      })
    },
    // 选择模板下拉框选中
    handleSelectId () {
      this.handleQueryTableData()
    },
    // 当前页改变
    handleCurrentChange (val) {
      this.handleQueryTableData()
    }
  }
}
</script>

<style scoped lang="scss">
.version-statistics {
  .select-width {
    width: 140px;
  }

  .row {
    .title {
      margin-right: 20px;
      line-height: 19px;
      font-size: 14px;
    }
  }

  .num {
    border: 1px solid #dedede;
    padding: 0px 8px;
    height: 30px;
    line-height: 30px;
    width: 30px;
    margin: 0 auto;
    cursor: pointer;
    &:hover {
      background: #fff !important;
      color: #5a8bff;
      border: 1px solid #5a8bff;
    }
  }
  .footer {
    background-color: #fff;
    height: 50px;
    line-height: 50px;
    color: #333;
    font-size: 14px;
    display: flex;
    justify-content: flex-end;
    padding-right: 10px;
    /deep/ .el-pagination {
      display: flex;
      align-items: center;
      .el-pager {
        display: flex;
        align-items: center;
      }
    }
  }
}
</style>
