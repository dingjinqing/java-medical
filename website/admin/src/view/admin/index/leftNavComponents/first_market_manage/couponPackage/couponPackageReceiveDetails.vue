<template>
  <div class="content">
    <div class="main">
      <div class="filters">
        <div class="filters_item">
          <span>{{$t('marketCommon.username')}}：</span>
          <el-input
            v-model="pageParams.username"
            :placeholder="$t('marketCommon.usernamePlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>{{$t('marketCommon.mobile')}}：</span>
          <el-input
            v-model="pageParams.mobile"
            :placeholder="$t('marketCommon.mobilePlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <span>{{$t('couponPackage.receiveTime')}}：</span>
          <el-date-picker
              v-model="pageParams.startTime"
              type="datetime"
              :placeholder="$t('membershipIntroduction.Starttime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              class="time_input"
              @change="datePickerChange(true,pageParams)"
              size="small"
            />
            至
           <el-date-picker
              v-model="pageParams.endTime"
              type="datetime"
              :placeholder="$t('membershipIntroduction.Endtime')"
              value-format="yyyy-MM-dd HH:mm:ss"
              @change="datePickerChange(false,pageParams)"
              :picker-options="endTime"
              default-time="23:59:59"
              class="time_input"
              size="small"
            />
        </div>
        <div class="filters_item">
          <span>{{$t('couponPackage.accessMode')}}：</span>
          <el-select
            v-model="pageParams.accessMode"
            size="small"
            class="default_input"
          >
            <el-option
              :label="$t('couponPackage.accessModeAll')"
              :value="-1"
            ></el-option>
            <el-option
              :label="$t('couponPackage.accessModeCash')"
              :value="0"
            ></el-option>
            <el-option
              :label="$t('couponPackage.accessModeTntegral')"
              :value="1"
            ></el-option>
            <el-option
              :label="$t('couponPackage.accessModeFree')"
              :value="2"
            ></el-option>
          </el-select>
        </div>
        <div class="filters_item">
          <span>{{$t('marketCommon.orderSn')}}：</span>
          <el-input
            v-model="pageParams.orderSn"
            :placeholder="$t('marketCommon.orderSnPlaceholder')"
            size="small"
            class="default_input"
          ></el-input>
        </div>
        <div class="filters_item">
          <el-button
            @click="initDataList"
            type="primary"
            size="small"
          >{{$t('marketCommon.filter')}}</el-button>
        </div>
      </div>
      <div class="table_box">
        <el-table
          v-loading="loading"
          :data="tableData"
          style="width:100%;"
          border
          :header-cell-style="{
            'background-color':'#f5f5f5',
            'text-align':'center',
            'border':'none'
          }"
          :cell-style="{
            'text-align':'center'
          }"
        >
          <el-table-column
            prop="username"
            :label="$t('marketCommon.username')"
          ></el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('marketCommon.mobile')"
          ></el-table-column>
          <el-table-column
            prop="accessMode"
            :label="$t('couponPackage.accessMode')"
          ></el-table-column>
          <el-table-column
            prop="orderSn"
            :label="$t('marketCommon.orderSn')"
          ></el-table-column>
          <el-table-column
            prop="createTime"
            :label="$t('couponPackage.receiveTime')"
          ></el-table-column>
          <el-table-column
            prop="voucherAccessCount"
            :label="$t('couponPackage.voucherAccessCount')"
          ></el-table-column>
        </el-table>
        <pagination
          :page-params.sync="pageParams"
          @pagination="initDataList"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { getCouponPackDetailPageList } from '@/api/admin/marketManage/couponPackage.js'
export default {
  components: {
    pagination: () => import('@/components/admin/pagination/pagination')
  },
  data () {
    return {
      actId: null,
      pageParams: {
        startTime: null,
        endTime: null
      },
      effectiveDate: '',
      tableData: [],
      loading: false,

      // 表格原始数据
      originalData: [],
      endTime: {
        disabledDate: time => {
          return time.getTime() < new Date(this.pageParams.startTime).getTime()
        }
      }
    }
  },
  methods: {
    initDataList () {
      this.loading = true
      this.pageParams.id = this.actId
      getCouponPackDetailPageList(this.pageParams).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = {
            ...this.pageParams,
            ...res.content.page
          }
          this.loading = false
        }
      })
    },
    // 表格数据处理
    handleData (data) {
      data.map((item, index) => {
        switch (item.accessMode) {
          case 0:
            item.accessMode = this.$t('couponPackage.accessModeCash')
            break
          case 1:
            item.accessMode = this.$t('couponPackage.accessModeTntegral')
            break
          case 2:
            item.accessMode = this.$t('couponPackage.accessModeFree')
            break
        }
      })
      this.tableData = data
    },
    /* 验证输入的时间范围是否合法 */
    datePickerChange (isStart, target) {
      console.log(target)
      if (target.startTime === null || target.endTime === null) {
        return
      }
      if (new Date(target.startTime).getTime() <= new Date(target.endTime).getTime()) {
        return
      }
      if (isStart) {
        target.startTime = null
      } else {
        target.endTime = null
      }
    }
  },
  watch: {
    // data内变量国际化
    lang () {
      // 重新渲染表格数据
      let originalData = JSON.parse(JSON.stringify(this.originalData))
      this.handleData(originalData)
    }
  },
  mounted () {
    this.langDefault()
    if (this.$route.query.id > 0) {
      this.actId = this.$route.query.id
    }
    this.initDataList()
  }
}
</script>

<style lang="scss" scoped>
.content {
  padding: 10px;
  .main {
    .filters {
      display: flex;
      line-height: 32px;
      margin-bottom: 10px;
      background-color: #fff;
      padding: 10px 10px 0 0;
      flex-wrap: wrap;
      .filters_item {
        display: flex;
        max-width: 480px;
        margin-left: 15px;
        margin-bottom: 10px;
        > span {
          min-width: 80px;
          font-size: 14px;
        }
      }
    }
    .navBox {
      background-color: #fff;
      padding: 10px;
      margin-bottom: 10px;
    }
    .table_box {
      background-color: #fff;
      padding: 15px;
      .operation {
        display: flex;
        justify-content: space-around;
        > .item {
          font-size: 22px;
          color: #66b1ff;
          cursor: pointer;
        }
      }
    }
    .default_input {
      width: 150px;
    }
    .time_input{
      width: 185px;
    }
  }
}
</style>
