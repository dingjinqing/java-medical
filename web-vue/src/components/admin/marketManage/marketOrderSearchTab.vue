<!--营销活动订单-搜索条件-->
<template>
  <div class="filters">
    <section style="display: flex;margin-bottom: 10px;width:100%">
      <div class="filters_item"><span>{{$t('marketCommon.goodsName')}}</span>
        <el-input
          v-model="requestParams.goodsName"
          :placeholder="$t('marketCommon.goodsName')"
          size="small"
          class="default_input"
        ></el-input>
      </div>
      <div class="filters_item"><span>{{$t('marketCommon.orderSn')}}</span>
        <el-input
          v-model="requestParams.orderSn"
          :placeholder="$t('marketCommon.orderSn')"
          size="small"
          class="default_input"
        ></el-input>
      </div>
      <div class="filters_item"><span>{{$t('marketCommon.orderStatus')}}</span>
        <el-select
          v-model="requestParams.selectedOrderStatus"
          :placeholder="$t('marketCommon.selectPlaceholder')"
          size="small"
          class="default_input"
        >
          <el-option
            v-for="item in $t('order.orderStatusList')"
            :key="item[0]"
            :label="item[1]"
            :value="item[0]"
          ></el-option>
        </el-select>
      </div>
    </section>
    <section style="display: flex;margin-bottom: 10px;width:100%">
      <div class="filters_item"><span>{{$t('marketCommon.consigneeName')}}</span>
        <el-input
          v-model="requestParams.consignee"
          :placeholder="$t('marketCommon.consigneeNamePlaceholder')"
          size="small"
          class="default_input"
        ></el-input>
      </div>
      <div class="filters_item"><span>{{$t('marketCommon.consigneeMobile')}}</span>
        <el-input
          v-model="requestParams.mobile"
          :placeholder="$t('marketCommon.consigneeMobilePlaceholder')"
          size="small"
          class="default_input"
        ></el-input>
      </div>
      <div class="filters_item"><span>{{$t('marketCommon.orderTime')}}</span>
        <el-date-picker
          v-model="requestParams.createTimeStart"
          type="datetime"
          :placeholder="$t('marketCommon.orderTime')"
          size="small"
          value-format="yyyy-MM-dd HH:mm:ss"
          class="date_picker"
        ></el-date-picker>
      </div>
    </section>
    <section style="display: flex;width:100%">
      <div
        class="filters_item"
        style="width: 485px"
      ><span>{{$t('marketCommon.shippingAddress')}}</span>
        <areaLinkage
          @areaData="handleAreaData"
          style="width:365px;"
        />
      </div>
      <div class="filters_item">
        <el-button
          @click="searchDataList()"
          type="primary"
          size="small"
        >{{$t('marketCommon.filter')}}</el-button>
        <el-button
          @click="exportDataList()"
          type="default"
          size="small"
          class="export-data"
        >{{$t('marketCommon.export')}}</el-button>
      </div>
    </section>
  </div>
</template>

<script>

export default {
  components: {
    areaLinkage: () => import('@/components/admin/areaLinkage/areaLinkage.vue')
  },
  props: { requestParams: Object },
  data () {
    return {
    }
  },
  methods: {
    searchDataList () {
      this.$emit('filter', this.requestParams)
    },
    exportDataList () {
      this.$emit('export', this.requestParams)
    },
    handleAreaData (data) {
      this.requestParams.provinceCode = data.province
      this.requestParams.cityCode = data.city
      this.requestParams.districtCode = data.district
    }
  }
}

</script>
<style lang="scss" scoped>
.filters {
  padding: 10px;
  display: flex;
  line-height: 32px;
  background-color: #fff;
  flex-wrap: wrap;
  .filters_item {
    width: 320px;
    display: flex;
    margin-left: 15px;
    margin-bottom: 10px;
    text-align: right;
    > span {
      width: 90px;
      font-size: 14px;
      margin-right: 20px;
    }
    .export-data {
      margin-left: 15px;
    }
  }
}
.default_input {
  width: 175px;
}
.date_picker {
  width: 175px;
}
.address_choose {
  margin-left: 10px;
}
/deep/ .no_padding {
  padding: 0;
  .cell {
    padding: 0;
  }
}
/deep/ .areaLinkage {
  .el-select {
    margin-left: 10px;
    &:first-of-type {
      margin-left: 0;
    }
  }
}
</style>
