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
      >
        <el-option
          v-for="(item, index) in selectIdOpt"
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
      >
        <el-option
          v-for="(item, index) in selectIdOpt"
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
      >
        <el-option
          v-for="(item, index) in selectIdOpt"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedAuditStatus"
        placeholder="选择审核状态"
        size="small"
      >
        <el-option
          v-for="(item, index) in selectIdOpt"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedReleaseStatus"
        placeholder="选择发布状态"
        size="small"
      >
        <el-option
          v-for="(item, index) in selectIdOpt"
          :key="index"
          :value="item.value"
          :label="item.label"
        />
      </el-select>
      <el-select
        class="fll selected-id mr-6 mb-10"
        v-model="queryData.selectedShopStatus"
        placeholder="选择店铺状态"
        size="small"
      >
        <el-option
          v-for="(item, index) in selectIdOpt"
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
        prop="app_id"
        label="appid"
        align="center"
        width="200"
      >
      </el-table-column>
      <el-table-column
        prop="id"
        label="店铺ID"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="name"
        align="center"
        label="昵称"
      >
      </el-table-column>
      <el-table-column
        align="center"
        label="头像"
      >
        <template slot-scope="scope">
          <img :src="scope.row.img">
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="是否授权"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.iscansel === '已取消授权'"
          >{{scope.row.iscansel}}</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.iscansel === '已授权'"
          >{{scope.row.iscansel}}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="是否认证"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.isAuthentication === '未认证'"
          >{{scope.row.isAuthentication}}</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.isAuthentication === '已认证'"
          >{{scope.row.isAuthentication}}</span>
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
            v-if="scope.row.ispay === '不支持'"
          >{{scope.row.ispay}}</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.ispay === '支持'"
          >{{scope.row.ispay}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="lastTime"
        align="center"
        label="最后授权时间"
      >
      </el-table-column>
      <el-table-column
        prop="temId"
        align="center"
        label="绑定模板ID"
      >
      </el-table-column>
      <el-table-column
        prop="status1"
        align="center"
        label="审核状态"
      >
      </el-table-column>
      <el-table-column
        prop="status2"
        align="center"
        label="发布状态"
      >
        <template slot-scope="scope">
          <span
            class="useSpan"
            v-if="scope.row.status2 === '未发布'"
          >{{scope.row.status2}}</span>
          <span
            class="useSpan nuSpan"
            v-if="scope.row.status2 === '已发布'"
          >{{scope.row.status2}}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status3"
        align="center"
        label="店铺状态"
      >
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

    <div class="clearfixed pagination-wrap">
      <el-pagination
        class="flr"
        @current-change="handleChangePage"
        :current-page="page"
        :page-size="20"
        layout="total, sizes, prev, pager, next, jumper"
        :total="400"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'authList',
  data () {
    return {
      queryData: {
        selectedId: '', // 选择模板id
        selectedAuth: '', // 选择是否授权
        selectedWxPay: '', // 选择是否微信支付
        selectedAuditStatus: '', // 审核状态
        selectedReleaseStatus: '', // 发布状态
        selectedShopStatus: '', // 选择店铺状态
        appid: '', // appid输入框数据
        shop_id: '', // shop——id输入框数据
        programName: '' // 小程序名称输入框数据
      },
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
      tableData: [
        {
          app_id: 'wx0d2a00751c5c6aa6',
          id: '3603035',
          name: '帅飞',
          img: 'http://wx.qlogo.cn/mmopen/xTMBoLQLB83uKQwjDwe8t8KOicdr7XzcM6nERK4ia49EM3ViaUvjTuxlrwO5pxXvHicOZP19Bljo9uAVKynlFSm9xUiap6RuuSyiab/0',
          iscansel: '已取消授权',
          isAuthentication: '已认证',
          ispay: '支持',
          lastTime: '2019-05-23 03:09',
          temId: '199',
          status1: '审核成功',
          status2: '已发布',
          status3: '使用中',
          operation: ''
        },
        {
          app_id: 'wx0d2a00751c5c6aa6',
          id: '3603035',
          name: '帅飞',
          img: 'http://wx.qlogo.cn/mmopen/xTMBoLQLB83uKQwjDwe8t8KOicdr7XzcM6nERK4ia49EM3ViaUvjTuxlrwO5pxXvHicOZP19Bljo9uAVKynlFSm9xUiap6RuuSyiab/0',
          iscansel: '已授权',
          isAuthentication: '未认证',
          ispay: '不支持',
          lastTime: '2019-05-23 03:09',
          temId: '199',
          status1: '审核失败',
          status2: '未发布',
          status3: '使用中',
          operation: ''
        },
        {
          app_id: 'wx0d2a00751c5c6aa6',
          id: '3603035',
          name: '帅飞',
          img: 'http://wx.qlogo.cn/mmopen/xTMBoLQLB83uKQwjDwe8t8KOicdr7XzcM6nERK4ia49EM3ViaUvjTuxlrwO5pxXvHicOZP19Bljo9uAVKynlFSm9xUiap6RuuSyiab/0',
          iscansel: '已取消授权',
          isAuthentication: '已认证',
          ispay: '支持',
          lastTime: '2019-05-23 03:09',
          temId: '199',
          status1: '审核成功',
          status2: '已发布',
          status3: '使用中',
          operation: ''
        }
      ],
      operationData: ['查看详细', '版本操作日志']
    }
  },
  methods: {
    handleChangePage (val) {

    },
    // 操作点击处理
    handleToClick (row, index) {

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
}
</style>
