<template>
  <div class="container">
    <div class="filters">
      <div class="filters_item">
        <span>医生姓名：</span>
        <el-input
          v-model="doctorName"
          size="small"
          class="default_input"
        ></el-input>
      </div>
      <div class="filters_item">
        <span>处理状态：</span>
        <el-select v-model="rebateStatus" size="small" class="default_input">
          <el-option
            v-for="item in rebateStatusList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
      <div class="filters_item">
        <span>时间筛选：</span>
        <el-select v-model="timeType" size="small" class="default_input">
          <el-option
            v-for="item in timeTypeList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </div>
      <div class="filters_item">
        <el-button type="primary" size="small">搜索</el-button>
      </div>

      <!-- <div class="filters_item">
        <span>时间筛选：</span>
        <el-input
          v-model=""
          size="small"
          class="default_input"
        ></el-input>
      </div> -->
    </div>
    <div class="table-content">
      <el-table
        :data="tableList"
        border
        :header-cell-style="{
          'background-color': '#f5f5f5',
          border: 'none',
          'font-weight': 'bold',
          color: '#000',
          padding: '8px 10px',
        }"
      >
        <el-table-column prop="doctorName" label="医生姓名"></el-table-column>
        <el-table-column prop="mobile" label="手机号"></el-table-column>
        <el-table-column
          prop="orderSn"
          label="返利咨询订单号"
        ></el-table-column>
        <el-table-column prop="money" label="咨询费总额"></el-table-column>
        <el-table-column prop="userName" label="下单用户昵称"></el-table-column>
        <el-table-column prop="rebateMoney" label="返利金额"></el-table-column>
        <el-table-column prop="rebateStatus" label="返利状态"></el-table-column>
        <el-table-column prop="createTime" label="返利日期"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      doctorName: '',
      rebateStatus: '0',
      timeType: '0',
      rebateStatusList: [
        {
          value: '0',
          label: '待返利'
        },
        {
          value: '1',
          label: '已返利'
        },
        {
          value: '2',
          label: '未返利'
        }
      ],
      timeTypeList: [
        {
          value: '0',
          label: '最近一天'
        },
        {
          value: '1',
          label: '最近七天'
        },
        {
          value: '2',
          label: '最近三十天'
        },
        {
          value: '3',
          label: '自定义'
        }
      ],
      tableList: [
        {
          doctorName: '张三',
          mobile: '17600236996',
          orderSn: 'ksjdkj4564646545644',
          money: '100.00',
          userName: '666',
          rebeatMoney: '666.00',
          rebeatStatus: 1,
          createTime: '2018-05-13 14:12:00'
        },
        {
          doctorName: '张三',
          mobile: '17600236996',
          orderSn: 'ksjdkj4564646545644',
          money: '100.00',
          userName: '666',
          rebeatMoney: '666.00',
          rebeatStatus: 1,
          createTime: '2018-05-13 14:12:00'
        },
        {
          doctorName: '张三',
          mobile: '17600236996',
          orderSn: 'ksjdkj4564646545644',
          money: '100.00',
          userName: '666',
          rebeatMoney: '666.00',
          rebeatStatus: 1,
          createTime: '2018-05-13 14:12:00'
        }
      ]
    }
  }

}
</script>

<style lang="scss" scoped>
.filters {
  display: flex;
  line-height: 32px;
  flex-wrap: wrap;
  // max-width: 1226px;
  .filters_item {
    display: flex;
    max-width: 480px;
    min-width: 320px;
    margin-left: 15px;
    margin-bottom: 10px;
    /deep/ .areaLinkage {
      .el-select {
        margin-left: 10px;
        &:first-of-type {
          margin-left: 0;
        }
      }
    }
    > span {
      min-width: 100px;
      font-size: 14px;
      text-align: right;
    }
  }
}
.default_input {
  width: 180px;
}
.table-content {
  margin-bottom: 20px;
}
</style>
