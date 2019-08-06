<template>
   <div class="auth-list">
      <div class="menu-list pd-10 bg-white mt-10" style="padding-bottom: 0;">
        <el-select
          class="fll selected-id mr-6 mb-10"
          v-model="queryData.selectedId"
          placeholder="请选择模板id"
          size="small"
        >
          <el-option v-for="(item, index) in selectIdOpt"
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
          <el-option v-for="(item, index) in selectIdOpt"
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
          <el-option v-for="(item, index) in selectIdOpt"
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
          <el-option v-for="(item, index) in selectIdOpt"
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
          <el-option v-for="(item, index) in selectIdOpt"
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
          <el-option v-for="(item, index) in selectIdOpt"
                     :key="index"
                     :value="item.value"
                     :label="item.label"
          />
        </el-select>
        <el-input
          class="fll mr-6 input-width mb-10"
          v-model="queryData.appid"
          placeholder="请输入appid"
          size="small" />
        <el-input
          class="fll mr-6 input-width mb-10"
          v-model="queryData.shop_id"
          placeholder="请输入shop_id"
          size="small" />
        <el-input
          class="fll mr-6 input-width mb-10"
          v-model="queryData.programName"
          placeholder="请输入小程序名称"
          size="small"
        />
        <el-button size="small" type="primary" class="mr-6 mb-10">搜索</el-button>
      </div>

      <el-table
       class="auth-list mt-10"
       header-row-class-name="table-th"
       :data="tableData"
       border
       style="width: 100%">
       <el-table-column
         prop="date"
         label="appid"
         align="center"
       >
       </el-table-column>
       <el-table-column
         prop="name"
         label="店铺ID"
         align="center"
       >
       </el-table-column>
       <el-table-column
         prop="address"
         align="center"
         label="昵称">
       </el-table-column>
       <el-table-column
         prop="address"
         align="center"
         label="头像">
       </el-table-column>
       <el-table-column
         prop="address"
         align="center"
         label="是否授权">
       </el-table-column>
        <el-table-column
         prop="address"
         align="center"
         label="是否认证">
       </el-table-column>
        <el-table-column
          prop="address"
          align="center"
          width="150"
          label="是否支持微信支付">
        </el-table-column>
        <el-table-column
          prop="address"
          align="center"
          label="最后授权时间">
        </el-table-column>
        <el-table-column
          prop="address"
          align="center"
          label="绑定模板ID">
        </el-table-column>
        <el-table-column
          prop="address"
          align="center"
          label="审核状态">
        </el-table-column>
        <el-table-column
          prop="address"
          align="center"
          label="发布状态">
        </el-table-column>
        <el-table-column
          prop="address"
          align="center"
          label="店铺状态">
        </el-table-column>
       <el-table-column
         prop="address"
         align="center"
         label="操作">
       </el-table-column>
      </el-table>

     <div class="clearfixed pagination-wrap">
        <el-pagination
          class="flr"
          @current-change="handleChangePage"
          :current-page="page"
          :page-size="20"
          layout="total, sizes, prev, pager, next, jumper"
          :total="400">
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
      tableData: []
    }
  },
  methods: {
    handleChangePage (val) {

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
}
</style>
