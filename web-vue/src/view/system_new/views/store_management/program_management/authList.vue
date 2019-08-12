<template>
  <div class="auth-list">
    <div
      class="menu-list pd-10 bg-white mt-10"
      style="padding-bottom: 0;"
    >
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedId"
        placeholder="请选择模板id"
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
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedAuth"
        placeholder="请选择是否授权"
        size="small"
        @change="handleSelectId()"
      >
        <el-option
          v-for="(item, index) in selectIsAuthorization"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedWxPay"
        placeholder="选择是否微信支付"
        size="small"
        @change="handleSelectId()"
      >
        <el-option
          v-for="(item, index) in selectIsPay"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedAuditStatus"
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
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedReleaseStatus"
        placeholder="选择发布状态"
        @change="handleSelectId()"
        size="small"
      >
        <el-option
          v-for="(item, index) in selectReleaseStatus"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedShopStatus"
        placeholder="选择店铺状态"
        @change="handleSelectId()"
        size="small"
      >
        <el-option
          v-for="(item, index) in selectShopStatus"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-input
        class="fll mr-6 input-width mb-10"
        v-model="queryData.appid"
        placeholder="请输入appid"
        size="small"
      />
      <el-input
        class="fll mr-6 input-width mb-10"
        v-model="queryData.shop_id"
        placeholder="请输入shop_id"
        size="small"
      />
      <el-input
        class="fll mr-6 input-width mb-10"
        v-model="queryData.programName"
        placeholder="请输入小程序名称"
        size="small"
      />
      <el-button
        size="small"
        type="primary"
        class="mr-6 mb-10"
        @click="handleQuery()"
      >搜索</el-button>
    </div>

    <el-table
      class="auth-list mt-10"
      header-row-class-name="table-th"
      :data="tableData"
      border
      style="width: 100%"
    >
      <el-table-column
        prop="appId"
        label="appid"
        align="center"
        width="200"
      >
      </el-table-column>
      <el-table-column
        prop="shopId"
        label="店铺ID"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="nickName"
        align="center"
        label="昵称"
      >
      </el-table-column>
      <el-table-column
        align="center"
        label="头像"
      >
        <template slot-scope="scope">
          <img :src="scope.row.headImg">
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="是否授权"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.isAuthOk === 0"
          >已取消授权</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.isAuthOk === 1"
          >已授权</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="是否认证"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.verifyTypeInfo === '0'"
          >未认证</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.verifyTypeInfo === '1'"
          >已认证</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        width="150"
        label="是否支持微信支付"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.openPay === 0"
          >不支持</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.openPay === 1"
          >支持</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="lastAuthTime"
        align="center"
        label="最后授权时间"
      >
      </el-table-column>
      <el-table-column
        prop="bindTemplateId"
        align="center"
        label="绑定模板ID"
      >
      </el-table-column>
      <el-table-column
        prop="status1"
        align="center"
        label="审核状态"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.auditState === 0">未提交</span>
          <span v-if="scope.row.auditState === 1">审核中</span>
          <span v-if="scope.row.auditState === 2">审核失败</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status2"
        align="center"
        label="发布状态"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.publishState === 0"
          >未发布</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.publishState === 1"
          >已发布</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status3"
        align="center"
        label="店铺状态"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.shopState === 0">已过期</span>
          <span v-if="scope.row.shopState === 1">使用中</span>

        </template>
      </el-table-column>
      <el-table-column
        prop="operation"
        align="center"
        label="操作"
        width="200"
      >
        <template slot-scope="scope">
          <div class='lastDiv'>
            <span
              v-for="(item,index) in operationData"
              :key="index"
              @click="handleToClick(scope.row,index)"
            >{{item}}</span>
          </div>

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
import { authListRequest, SpinnerListRequest } from '@/api/system/programManage'
export default {
  name: 'authList',
  data () {
    return {
      pageCount: 1,
      queryData: {
        selectedId: '', // 选择模板id
        selectedAuth: null, // 选择是否授权
        selectedWxPay: null, // 选择是否微信支付
        selectedAuditStatus: null, // 审核状态
        selectedReleaseStatus: null, // 发布状态
        selectedShopStatus: null, // 选择店铺状态
        appid: null, // appid输入框数据
        shop_id: null, // shop——id输入框数据
        programName: null // 小程序名称输入框数据

      },
      selectIsAuthorization: [
        {
          value: '0',
          label: '未授权'
        },
        {
          value: '1',
          label: '已授权'
        }
      ],
      page: 1,
      selectIdOpt: [
        {
          value: '1',
          label: '1.0.0'
        },
        {
          value: '2',
          label: '2.0.0'
        }
      ],
      selectIsPay: [
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
          value: '0',
          label: '未发布'
        },
        {
          value: '1',
          label: '已发布'
        }
      ],
      selectShopStatus: [
        {
          value: '1',
          label: '使用中'
        },
        {
          value: '0',
          label: '已过期'
        }
      ],
      tableData: [
        // {
        //   app_id: 'wx0d2a00751c5c6aa6',
        //   id: '3603035',
        //   name: '帅飞',
        //   img: 'http://wx.qlogo.cn/mmopen/xTMBoLQLB83uKQwjDwe8t8KOicdr7XzcM6nERK4ia49EM3ViaUvjTuxlrwO5pxXvHicOZP19Bljo9uAVKynlFSm9xUiap6RuuSyiab/0',
        //   iscansel: '已取消授权',
        //   isAuthentication: '已认证',
        //   ispay: '支持',
        //   lastTime: '2019-05-23 03:09',
        //   temId: '199',
        //   status1: '审核成功',
        //   status2: '已发布',
        //   status3: '使用中',
        //   operation: ''
        // },
        // {
        //   app_id: 'wx0d2a00751c5c6aa6',
        //   id: '3603035',
        //   name: '帅飞',
        //   img: 'http://wx.qlogo.cn/mmopen/xTMBoLQLB83uKQwjDwe8t8KOicdr7XzcM6nERK4ia49EM3ViaUvjTuxlrwO5pxXvHicOZP19Bljo9uAVKynlFSm9xUiap6RuuSyiab/0',
        //   iscansel: '已授权',
        //   isAuthentication: '未认证',
        //   ispay: '不支持',
        //   lastTime: '2019-05-23 03:09',
        //   temId: '199',
        //   status1: '审核失败',
        //   status2: '未发布',
        //   status3: '使用中',
        //   operation: ''
        // },
        // {
        //   app_id: 'wx0d2a00751c5c6aa6',
        //   id: '3603035',
        //   name: '帅飞',
        //   img: 'http://wx.qlogo.cn/mmopen/xTMBoLQLB83uKQwjDwe8t8KOicdr7XzcM6nERK4ia49EM3ViaUvjTuxlrwO5pxXvHicOZP19Bljo9uAVKynlFSm9xUiap6RuuSyiab/0',
        //   iscansel: '已取消授权',
        //   isAuthentication: '已认证',
        //   ispay: '支持',
        //   lastTime: '2019-05-23 03:09',
        //   temId: '199',
        //   status1: '审核成功',
        //   status2: '已发布',
        //   status3: '使用中',
        //   operation: ''
        // }
      ],
      operationData: ['查看详细', '版本操作日志'],
      selectIdTemId: [],
      currentPage: 1,
      totle: 0
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
        spinnerList.content.map((item, index) => {
          let obj = {}
          obj.value = index
          obj.label = item.userVersion
          arr.push(obj)
        })
        console.log(arr)
        this.selectIdTemId = arr
        this.handleQueryTableData()
      }
    },
    // 小程序列表分页查询
    handleQueryTableData () {
      console.log(this.queryData)
      let obj = {
        'templateId': this.queryData.selectedId,
        'isAuthOk': this.queryData.selectedAuth,
        'shopState': this.queryData.selectedShopStatus,
        'openPay': this.queryData.selectedWxPay,
        'auditState': this.queryData.selectedAuditStatus,
        'publishState': this.queryData.selectedReleaseStatus,
        'appId': this.queryData.appid,
        'shopId': this.queryData.shop_id,
        'nickName': this.queryData.programName,
        'currentPage': this.currentPage,
        'pageRows': 0
      }
      authListRequest(obj).then((res) => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = res.content.dataList
          this.pageCount = res.content.page.pageCount
          this.totle = res.content.page.totalRows
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
    },
    // 操作点击处理
    handleToClick (row, index) {

    },
    // 点击搜索
    handleQuery () {
      this.handleQueryTableData()
    }
  },
  watch: {
    queryData: {
      deep: true,
      handler (val) {

      }
    }
  }
}
</script>

<style scoped lang="scss">
.auth-list {
  .selected-id {
    width: 140px;
  }
  .input-width {
    width: 140px;
  }
  .useSpan {
    font-weight: 700;
    color: #fff;
    padding: 4px;
    border-radius: 5px;
    background-color: #a90329;
  }
  .nuSpan {
    background-color: #739e73;
  }
  .lastDiv {
    span {
      display: inline-block;
      cursor: pointer;
    }
    span:nth-of-type(1) {
      margin-right: 4px;
    }
  }
  img {
    width: 50px;
    height: 50px;
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
