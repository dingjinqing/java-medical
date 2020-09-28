<template>
  <div class="layout-wrap">
    <div class="list-filters">
      <el-form ref="listFiltersForm" :model="listFiltersForm" :inline="true">
        <el-form-item label="门店信息：" prop="keywords">
          <el-input
            v-model="listFiltersForm.keywords"
            placeholder="门店名称/编码"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="营业状态：" prop="businessState">
          <el-select
            v-model="listFiltersForm.businessState"
            size="small"
            :clearable="true"
          >
            <el-option :value="1" label="营业" />
            <el-option :value="0" label="未营业" />
          </el-select>
        </el-form-item>
        <el-form-item label="负责人：" prop="manager">
          <el-input
            v-model="listFiltersForm.manager"
            placeholder="负责人名称"
            size="small"
          ></el-input>
        </el-form-item>
        <el-form-item label="门店自提：" prop="autoPick">
          <el-select
            v-model="listFiltersForm.autoPick"
            size="small"
            :clearable="true"
          >
            <el-option :value="1" label="已开启" />
            <el-option :value="0" label="未开启" />
          </el-select>
        </el-form-item>
        <el-form-item label="同城配送：" prop="cityService">
          <el-select
            v-model="listFiltersForm.cityService"
            size="small"
            :clearable="true"
          >
            <el-option :value="1" label="已开启" />
            <el-option :value="0" label="未开启" />
          </el-select>
        </el-form-item>
      </el-form>
      <div>
        <el-button size="small" type="primary" @click="listFilterConfirm"
          >确认</el-button
        >
        <el-button size="small" @click="listFilterReset">重置</el-button>
      </div>
    </div>
    <div class="list-content">
      <div class="list-content-table">
        <el-table
          :data="storeList"
          style="width: 100%"
          border
          class="tableClass"
        >
          <el-table-column type="index" width="50" align="center" />
          <el-table-column prop="storeName" label="门店名称" />
          <el-table-column prop="storeCode" label="门店编码" />
          <el-table-column prop="addressBak" label="门店地址" />
          <el-table-column prop="manager" label="负责人" />
          <el-table-column prop="mobile" label="联系电话" />
          <el-table-column label="营业时间">
            <template slot-scope="{ row }">
              {{ row.openingTime + '-' + row.closeTime }}
            </template>
          </el-table-column>
          <el-table-column label="门店自提" align="center">
            <template slot-scope="{ row }">
              <el-checkbox
                v-model="row.autoPick"
                :true-label="1"
                :false-label="0"
                @change="storeFunctionChange(row, 'autoPick')"
              />
            </template>
          </el-table-column>
          <el-table-column label="同城配送" align="center">
            <template slot-scope="{ row }">
              <el-checkbox
                v-model="row.cityService"
                :true-label="1"
                :false-label="0"
                @change="storeFunctionChange(row, 'cityService')"
              />
            </template>
          </el-table-column>
          <el-table-column label="营业状态" align="center">
            <template slot-scope="{ row }">
              <el-checkbox
                v-model="row.businessState"
                :true-label="1"
                :false-label="0"
                @change="storeFunctionChange(row, 'businessState')"
              />
            </template>
          </el-table-column>
          <!--<el-table-column prop="productPrice"  label="操作" align="center">-->
          <!--<span style="color:#5A8BFF;cursor: pointer;">编辑</span>-->
          <!--</el-table-column>-->
        </el-table>
      </div>
      <div class="list-content-pagination">
        <el-pagination
          :page-sizes="[20, 30, 40, 50]"
          :page-size.sync="pageInfo.pageRows"
          :total="pageInfo.totalRows"
          :page-count="10"
          :current-page.sync="pageInfo.currentPage"
          layout="sizes,total,prev,pager,next"
          @size-change="paginationChange"
          @current-change="paginationChange"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { getStoreList, updateStore } from '@/api/store/store'
// 地区编码
import chinaData from '@/assets/china-data'
export default {
  name: 'storeList',
  data () {
    return {
      listFiltersForm: {
        keywords: null,
        businessState: null,
        manager: null,
        autoPick: null,
        cityService: null
      },
      pageInfo: {
        currentPage: 1,
        pageRows: 20,
        totalRows: 0
      },
      storeList: []
    }
  },
  methods: {
    listFilterReset () {
      this.$refs['listFiltersForm'].resetFields()
    },
    listFilterConfirm () {
      this.loadStoreList()
    },
    getFullAddress (item) {
      let address = ''
      let province = chinaData.filter(function (area) {
        return area.provinceId === parseInt(item.provinceCode)
      })
      province = province[0]
      address += province.provinceName
      let areaCity = province.areaCity.filter(function (area) {
        return area.cityId === parseInt(item.cityCode)
      })
      areaCity = areaCity[0]
      address += ' ' + areaCity.cityName
      let areaDistrict = areaCity.areaDistrict.filter(function (area) {
        return area.districtId === parseInt(item.districtCode)
      })
      areaDistrict = areaDistrict[0]
      address += ' ' + areaDistrict.districtName + ' ' + item.address

      return address
    },
    /** 加载门店列表 */
    loadStoreList () {
      let param = {
        ...this.listFiltersForm,
        ...this.pageInfo
      }
      getStoreList(param).then(res => {
        if (res.error !== 0) {
          this.$message.warning({ message: res.message, showClose: true })
          return
        }
        let pageResult = res.content.storePageListVo
        this.pageInfo.totalRows = pageResult.page.totalRows
        for (let i = 0; i < pageResult.dataList.length; i++) {
          let item = pageResult.dataList[i]
          item.addressBak = this.getFullAddress(item)
        }
        this.storeList = pageResult.dataList
      })
    },
    /** 门店功能属性发生变化 */
    storeFunctionChange (row, fieldName) {
      updateStore(row).then(res => {
        if (res.error !== 0) {
          this.$message.warning({ message: res.message, showClose: true })
        } else {
          this.$message.success({ message: '更新完成', showClose: true })
        }
      })
    },
    /** 分页信息发生改变 */
    paginationChange () {
      this.loadStoreList()
    }
  },
  mounted () {
    this.loadStoreList()
  }
}
</script>

<style scoped>
.layout-wrap {
  padding: 10px;
  height: 100%;
  box-sizing: border-box;
  overflow: hidden;
}
.list-filters {
  padding: 15px;
  background-color: white;
}
.list-filters .el-select,
.list-filters .el-input {
  width: 180px;
}
.list-content {
  padding: 15px;
  background-color: white;
  margin-top: 10px;
  height: calc(100% - 130px);
  overflow-y: auto;
}
/deep/.tableClass th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
  text-align: center;
}
.list-content-pagination {
  text-align: right;
  padding: 10px;
}
</style>
