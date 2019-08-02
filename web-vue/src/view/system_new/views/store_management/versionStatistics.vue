<template>
  <div class="version-statistics">
    <div class="filter-menu pd-10 bg-white clearfixed">
      <div class="clearfixed">
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="versionNum"
          placeholder="请选择版本号"
          size="small"
        >
          <el-option v-for="(item, index) in selectIdOpt"
                     :key="index"
                     :value="item.value"
                     :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="isAuth"
          placeholder="选择是否授权"
          size="small"
        >
          <el-option v-for="(item, index) in selectIdOpt"
                     :key="index"
                     :value="item.value"
                     :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="isWxPay"
          placeholder="选择支持微信支付"
          size="small"
        >
          <el-option v-for="(item, index) in selectIdOpt"
                     :key="index"
                     :value="item.value"
                     :label="item.label"
          />
        </el-select>
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="isWxPay"
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
          class="fll select-width mr-6 mb-10"
          v-model="isWxPay"
          placeholder="选择发布状态"
          size="small"
        >
          <el-option v-for="(item, index) in selectIdOpt"
                     :key="index"
                     :value="item.value"
                     :label="item.label"
          />
        </el-select>
      </div>
      <div class="row clearfixed">
        <span class="title fll">显示列: </span>
        <el-checkbox-group v-model="checkList" class="fll">
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
      style="width: 100%">
      <el-table-column
        prop="date"
        label="版本号"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="name"
        label="是否授权"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="address"
        align="center"
        label="支持微信支付">
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
        label="店铺数量">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'versionStatistics',
  data () {
    return {
      versionNum: '', // 版本号
      isAuth: '', // 是否授权
      isWxPay: '', // 是否微信支付
      checkList: [0, 3], // 复选框选项
      tableData: [],
      selectIdOpt: [
        {
          value: '1',
          label: '1.0.0'
        },
        {
          value: '2',
          label: '2.0.0'
        }
      ]
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
}

</style>
