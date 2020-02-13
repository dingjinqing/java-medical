<template>
  <div class="container">
    <div class="filters">
      <el-form
        :inline="true"
        :model="queryParams"
        class="filters-form"
        label-width="110px"
        size="small"
      >
        <el-form-item :label="$t('memberIntroductionList.mobile')+'：'">
          <el-input
            class="filter-input"
            :placeholder="$t('memberIntroductionList.piMobile')"
            v-model="queryParams.mobile"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('memberIntroductionList.realName')+'：'">
          <el-input
            class="filter-input"
            :placeholder="$t('memberIntroductionList.piRealName')"
            v-model="queryParams.realName"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('memberIntroductionList.isDistributor')+'：'">
          <el-select
            class="filter-input"
            v-model="queryParams.isDistributor"
          >
            <el-option
              :label="$t('memberIntroductionList.pleaseChoose')"
              value=""
            ></el-option>
            <el-option
              :label="$t('memberIntroductionList.yes')"
              :value="1"
            ></el-option>
            <el-option
              :label="$t('memberIntroductionList.no')"
              :value="0"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('memberIntroductionList.distributorGroup')+'：'">
          <el-select
            class="filter-input"
            v-model="queryParams.groupId"
          >
            <el-option
              :label="$t('memberIntroductionList.ungroup')"
              value=""
            ></el-option>
            <el-option
              v-for="group in distributionGroupData"
              :key="group.id"
              :label="group.groupName"
              :value="group.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('memberIntroductionList.importTime')+'：'">
          <el-date-picker
            class="filter-datetime"
            v-model="queryParams.startTime"
            type="datetime"
            :placeholder="$t('memberIntroductionList.selectTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
          <span>至</span>
          <el-date-picker
            class="filter-datetime"
            v-model="queryParams.endTime"
            type="datetime"
            :placeholder="$t('memberIntroductionList.selectTime')"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time='23:59:59'
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('memberIntroductionList.importBatchNum')+'：'">
          <el-input
            v-model="queryParams.batchId"
            class="filter-input"
            :placeholder="$t('memberIntroductionList.piBatchNum')"
          ></el-input>
        </el-form-item>
        <el-button
          type="primary"
          size="small"
          @click="initDataList"
        >{{$t('memberIntroductionList.filter')}}</el-button>
      </el-form>
    </div>
    <div class="table">
      <el-table
        :data="tableData"
        header-row-class-name='tableClss'
        border
      >
        <el-table-column
          prop="mobile"
          :label="$t('memberIntroductionList.mobile')"
          align="center"
        >
        </el-table-column>
        <el-table-column
          prop="name"
          :label="$t('memberIntroductionList.realName')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="createTime"
          :label="$t('memberIntroductionList.importTime')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="batchId"
          :label="$t('memberIntroductionList.importBatchNum')"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="isDistributor"
          :label="$t('memberIntroductionList.isDistributor')"
          align="center"
          :formatter="formatIsDistributor"
        ></el-table-column>
        <el-table-column
          prop="groupName"
          :label="$t('memberIntroductionList.distributorGroup')"
          align="center"
          :formatter="formatGroupName"
        ></el-table-column>
      </el-table>
      <div class="footer">
        <pagination
          :pageParams="pageParams"
          @pagination="initDataList"
        ></pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { getNoActiveList, getDistributionGroup } from '@/api/admin/memberManage/membershipIntroduction.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      queryParams: {
        mobile: '',
        realName: '',
        isDistributor: '', // 是否是分销员 0:不是；1：是
        groupId: '', // 分销员分组id
        startTime: '',
        endTime: '',
        batchId: '' // 导入批次号
      },
      pageParams: {},
      tableData: [],
      distributionGroupData: []
    }
  },
  mounted () {
    if (this.$route.query.id) {
      this.$set(this.queryParams, 'batchId', this.$route.query.id)
    }
    this.initDistributionGroup() // 分销员分组
    this.initDataList()
  },
  methods: {
    initDataList () {
      let params = Object.assign({}, this.queryParams, this.pageParams)
      getNoActiveList(params).then(res => {
        if (res.error === 0) {
          console.log(res)
          this.tableData = res.content.dataList
          this.pageParams = res.content.page
        }
      })
    },
    initDistributionGroup () {
      getDistributionGroup().then(res => {
        if (res.error === 0) {
          this.distributionGroupData = res.content
        }
      })
    },
    // 格式化table
    formatIsDistributor (row, column, value, index) {
      if (value === 0 || value === '0') {
        return this.$t('memberIntroductionList.yes')
      } else if (value === 1 || value === '1') {
        return this.$t('memberIntroductionList.no')
      }
    },
    formatGroupName (row, column, value, index) {
      if (!value) {
        return this.$t('memberIntroductionList.ungrouped')
      } else {
        return value
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  width: 100%;
  height: 100%;
  font-size: 14px;
  .filters {
    padding: 15px;
    background-color: #fff;
    .filter-input {
      width: 150px;
    }
    .filter-datetime {
      width: 200px;
    }
  }
  .table {
    padding: 15px;
    margin-top: 10px;
    background-color: #fff;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
    }
  }
}
</style>
