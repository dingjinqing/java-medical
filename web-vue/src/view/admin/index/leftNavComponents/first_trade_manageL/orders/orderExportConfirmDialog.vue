<template>
  <el-dialog
    :title="$t('orderCommon.tip')"
    :visible.sync="showNodes"
    custom-class="custom"
    width="30%"
    v-loading="loading"
    center
  >
    <p>{{$t('order.orderExportConfirmTip_1')}}{{totalRows}}{{$t('order.orderExportConfirmTip_2')}}</p>
    <div style="margin-top: 10px;">
      {{$t('order.filterCondition')}}
    </div>
    <div
      v-for="(item,key,index) in searchParam"
      :key="index"
      style="margin-top: 10px;"
    >
      <div v-if="ok(key,item)">
        <div v-if="key === 'orderStatus'">
          {{$t('orderSearch.'+key)}}:
          <span
            v-for="status in item"
            :key="status"
          >
            {{orderStatusMap.get(status)}}
          </span>
        </div>
        <div v-else-if="key === 'goodsType'">
          {{$t('orderSearch.'+key)}}:
          <span>
            {{goodsTypeMap.get(item)}}
          </span>
        </div>
        <div v-else-if="key === 'deliverType'">
          {{$t('orderSearch.'+key)}}:
          <span>
            {{deliverTypeMap.get(item)}}
          </span>
        </div>
        <div v-else-if="key === 'paymentType'">
          {{$t('orderSearch.'+key)}}:
          <span>
            {{paymentTypeMap.get(item)}}
          </span>
        </div>
        <div v-else-if="key === 'provinceCode'">
          {{$t('orderSearch.'+key)}}:
          <span>
            {{getProvince(item)}}
          </span>
        </div>
        <div v-else-if="key === 'cityCode'">
          {{$t('orderSearch.'+key)}}:
          <span>
            {{getCity(item)}}
          </span>
        </div>
        <div v-else-if="key === 'districtCode'">
          {{$t('orderSearch.'+key)}}:
          <span>
            {{getDistrict(item)}}
          </span>
        </div>
        <div
          v-else
          style="margin-top: 10px;"
        >{{$t('orderSearch.'+key)}}:{{item}}</div>

      </div>
    </div>
    <div
      v-if="totalRows > 0"
      style="margin-top: 10px;"
    >
      <div style="margin-bottom: 10px;">{{$t('order.orderExportLimitTip')}}</div>
      <el-input-number
        v-model="exportRowStart"
        placeholder=""
        :min="1"
        :max="exportRowEnd"
        :precision="0"
        size="small"
        controls-position="right"
        style="width: 150px;"
      ></el-input-number>
      {{$t('orderCommon.to')}}
      <el-input-number
        v-model="exportRowEnd"
        placeholder=""
        size="small"
        :min="exportRowStart"
        :max="searchParam.exportRowEnd"
        :precision="0"
        style="width: 150px;"
        controls-position="right"
      ></el-input-number>
    </div>
    <span
      slot="footer"
      class="dialog-footer"
    >
      <el-button
        @click="showNodes = false"
        size="small"
      >{{$t('orderCommon.cancel')}}</el-button>
      <el-button
        type="primary"
        @click="confirm"
        v-if="totalRows > 0"
        size="small"
      >{{$t('orderCommon.ok')}}</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { download } from '@/util/excelUtil.js'
import { orderExport, getExportTotalRows } from '@/api/admin/orderManage/order.js'
import { deepCloneObj } from '@/util/deepCloneObj'
import chinaData from '@/assets/china-data'
export default {
  data () {
    return {
      totalRows: 0,
      showNodes: false,
      loading: false,
      orderStatusMap: new Map(this.$t('order.orderStatusList')),
      goodsTypeMap: new Map(this.$t('order.goodsTypeList')),
      deliverTypeMap: new Map(this.$t('order.deliverTypeList')),
      paymentTypeMap: new Map(this.$t('order.paymentTypeList')),
      exportRowStart: 1,
      exportRowEnd: 5000,
      searchParam: '',
      area: {},
      city: {},
      district: {}

    }
  },
  mounted () {
    this.initArea()
  },
  props: {
    show: Boolean,
    param: Object
  },
  methods: {
    initArea () {
      this.area = deepCloneObj(chinaData)
    },
    initData () {
      this.searchParam = this.param
      console.log(this.searchParam)
      this.searchParam.exportRowStart = this.exportRowStart
      this.searchParam.exportRowEnd = this.exportRowEnd
      if (this.searchParam.orderStatus) {
        this.searchParam.orderStatus = [this.searchParam.orderStatus]
      }
      getExportTotalRows(this.searchParam).then(res => {
        if (res.error === 0) {
          this.totalRows = res.content
          if (this.totalRows < 5000) {
            this.exportRowEnd = this.totalRows
            this.searchParam.exportRowEnd = this.totalRows
          }
        }
      }).catch(() => {
      })
    },
    closeDialog () {
      this.$emit('update:show', false)
    },
    confirm () {
      this.loading = true
      this.searchParam.exportRowStart = this.exportRowStart
      this.searchParam.exportRowEnd = this.exportRowEnd
      orderExport(this.searchParam).then(res => {
        let fileName = localStorage.getItem('V-content-disposition')
        fileName = fileName.split(';')[1].split('=')[1]
        this.loading = false
        download(res, decodeURIComponent(fileName))
      }).catch(() => {
        this.loading = false
      })
    },
    ok (key, item) {
      if (Array.isArray(item)) {
        if (item.length !== 0) return true
      } else {
        if (key === 'currentPage' || key === 'pageRows' || key === 'exportRowStart' || key === 'exportRowEnd' || key === 'orderStatus2') {
          return false
        }
        if (item) return true
      }
      return false
    },
    getProvince (code) {
      let province = this.area.find((item1, index1) => code === item1['provinceId'])
      this.city = province.areaCity
      return province.provinceName
    },
    getCity (code) {
      let thisCity = this.city.find((item1, index1) => code === item1['cityId'])
      this.district = thisCity.areaDistrict
      return thisCity.cityName
    },
    getDistrict (code) {
      return this.district.find((item1, index1) => code === item1['districtId'])['districtName']
    }
  },
  watch: {
    showNodes (newval) {
      this.closeDialog()
    },
    show (newVal) {
      if (newVal === true) {
        this.showNodes = true
        this.initData()
      }
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/ .custom {
  .el-dialog__header {
    background: #f3f3f3;
    padding-top: 10px;
    .el-dialog__title {
      font-size: 14px;
    }
    .el-dialog__headerbtn {
      top: 10px;
    }
  }
  .el-checkbox-button.is-disabled .el-checkbox-button__inner {
    background-color: #f5f7fa;
  }
}
</style>
