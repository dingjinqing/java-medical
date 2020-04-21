<template>
  <div class="container bulk-shipment-page">
    <div class="top">
      <div class="explanation">
        <div class="explanation-con">
          <span>说明：</span>
          <ul>
            <ol>1、批量发货目前仅支持快递订单，暂不支持自提订单和同城配送方式的订单</ol>
            <ol>2、目前批量发货仅支持整订单发货</ol>
          </ul>
        </div>
        <el-button
          class="bulk-shipment-btn"
          type="primary"
          size="small"
        >批量发货</el-button>
      </div>
      <el-form :inline="true">
        <el-form-item label="批次号：">
          <el-input
            class="filter-input"
            placeholder="请输入批次号"
          ></el-input>
        </el-form-item>
        <el-form-item label="操作时间：">
          <el-date-picker
            type="datetime"
            v-model="queryParams.beginTime"
            :placeholder="$t('goodsImport.psTime')"
            size="small"
            style="width:170px;"
            default-time="00:00:00"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            :picker-options="startTimePicker"
          ></el-date-picker>
          <span style="display:inline-block; margin:0 10px;line-height:30px;">{{$t('goodsImport.to')}}</span>
          <el-date-picker
            type="datetime"
            v-model="queryParams.endTime"
            :placeholder="$t('goodsImport.psTime')"
            size="small"
            style="width:170px;"
            default-time="23:59:59"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            :picker-options="endTimePicker"
          ></el-date-picker>
        </el-form-item>
      </el-form>
    </div>
    <div class="content"></div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      queryParams: {},
      pageParams: {},
      startTimePicker: {
        disabledDate: time => {
          return time.getTime() > new Date(this.queryParams.endTime).getTime()
        }
      },
      endTimePicker: {
        disabledDate: time => {
          return time.getTime() < new Date(this.queryParams.beginTime).getTime()
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  padding: 10px;
  font-size: 14px;
  color: #333;
  .top {
    padding: 15px;
    background-color: #fff;
  }
  .content {
    margin-top: 10px;
    padding: 15px;
    background-color: #fff;
    /deep/ .tableClss th {
      background-color: #f5f5f5;
      border: none;
      height: 36px;
      font-weight: bold;
      color: #000;
      padding: 8px 10px;
    }
    .iconSpan {
      font-size: 22px;
      color: #5a8bff;
      cursor: pointer;
    }
  }
}
.bulk-shipment-page {
  .explanation {
    background: #f5f5f5;
    padding: 10px;
    overflow: hidden;
  }
  .explanation-con {
    float: left;
    display: flex;
    width: 80%;
    line-height: 27px;
    color: #666;
    span {
      flex-basis: 43px;
    }
    & > div {
      flex: 1;
    }
  }
  .bulk-shipment-btn {
    float: right;
  }
}
</style>
