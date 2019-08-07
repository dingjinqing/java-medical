<template>
  <div class="version-statistics">
    <div class="filter-menu pd-10 bg-white clearfixed">
      <div class="clearfixed">
        <el-select
          class="fll select-width mr-6 mb-10"
          v-model="queryData.versionNum"
          placeholder="请选择版本号"
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
          class="fll select-width mr-6 mb-10"
          v-model="queryData.isAuth"
          placeholder="选择是否授权"
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
          class="fll select-width mr-6 mb-10"
          v-model="queryData.isWxPay"
          placeholder="选择支持微信支付"
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
          class="fll select-width mr-6 mb-10"
          v-model="queryData.auditStatus"
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
          class="fll select-width mr-6 mb-10"
          v-model="queryData.isRelease"
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
        prop="versionNum"
        label="版本号"
        align="center"
        v-if="holdHiddenArr[0]"
      >
      </el-table-column>
      <el-table-column
        prop="isAuth"
        label="是否授权"
        align="center"
        v-if="holdHiddenArr[1]"
      >
      </el-table-column>
      <el-table-column
        prop="ispay"
        align="center"
        label="支持微信支付"
        v-if="holdHiddenArr[2]"
      >
      </el-table-column>
      <el-table-column
        prop="status1"
        align="center"
        label="审核状态"
        v-if="holdHiddenArr[3]"
      >
      </el-table-column>
      <el-table-column
        prop="status2"
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
          <div class="num">{{scope.row.num}}</div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'versionStatistics',
  data () {
    return {
      queryData: {
        versionNum: '', // 版本号
        isAuth: '', // 是否授权
        isWxPay: '', // 是否微信支付
        auditStatus: '', // 审核状态
        isRelease: '' // 发布状态
      },
      checkList: [0, 3, 4], // 复选框选项
      tableData: [
        {
          versionNum: '1.28.3',
          isAuth: '已授权',
          ispay: '支持微信支付',
          status1: '审核中',
          status2: '已发布',
          num: '2'
        },
        {
          versionNum: '1.28.3',
          isAuth: '已授权',
          ispay: '支持微信支付',
          status1: '审核中',
          status2: '已发布',
          num: '2'
        },
        {
          versionNum: '1.28.3',
          isAuth: '已授权',
          ispay: '支持微信支付',
          status1: '审核中',
          status2: '已发布',
          num: '2'
        }
      ],
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
      holdHiddenArr: [false, false, false, false, false]
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
}
</style>
