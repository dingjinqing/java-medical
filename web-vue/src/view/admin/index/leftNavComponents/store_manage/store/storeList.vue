<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <el-button
          type="primary"
          @click="add()"
        >新建门店</el-button>
      </div>
      <div class="table_box">
        <div class="filters">
          <div class="filters_item"><span>门店分组：</span>
            <el-select
              v-model="queryParams.groupId"
              size="small"
            >
              <el-option
                label="全部"
                :value="null"
              ></el-option>
              <el-option
                v-for="item in storeGroup"
                :key="item.groupId"
                :label="item.groupName"
                :value="item.groupId"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item"><span>是否已授权POS：</span>
            <el-select
              v-model="queryParams.isAuthPos"
              size="small"
            >
              <el-option
                label="全部"
                selected
                :value="null"
              ></el-option>
              <el-option
                label="是"
                :value="true"
              ></el-option>
              <el-option
                label="否"
                :value="false"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item"><span>门店信息：</span>
            <el-input
              v-model="queryParams.keywords"
              class="inputWidth"
              size="small"
              placeholder="门店名称|编码|负责人"
            ></el-input>
          </div>
          <el-button
            @click="initDataList"
            class="btn"
            type="primary"
            size="small"
          >{{$t('marketCommon.filter')}}</el-button>
        </div>
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
            prop="storeName"
            label="门店名称"
          ></el-table-column>
          <el-table-column
            prop="posShopId"
            label="门店编码"
          ></el-table-column>
          <el-table-column
            prop="groupName"
            label="分组名"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            label="门店地址"
          ></el-table-column>
          <el-table-column
            prop="manager"
            label="负责人"
          ></el-table-column>
          <el-table-column
            prop="mobile"
            label="联系电话"
          ></el-table-column>
          <el-table-column
            prop="businessHours"
            label="营业时间"
          ></el-table-column>
          <el-table-column
            prop="businessStateName"
            label="营业状态"
          >
            <template slot-scope="scope">
              {{scope.row.businessStateName}}
              <a
                class="businessStateOperate"
                v-if="scope.row.businessState === 1"
              >歇业</a>
              <a
                class="businessStateOperate"
                v-else
              >开业</a>
            </template>
          </el-table-column>
          <el-table-column
            prop=""
            label="买单码"
          ></el-table-column>

          <el-table-column
            :label="$t('marketCommon.operate')"
            width="230px"
          >
            <template slot-scope="scope">
              <div class="operation">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="编辑"
                  placement="top"
                >
                  <a @click="edit(scope.row.id)">编辑</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="商品管理"
                  placement="top"
                >
                  <a @click="edit(scope.row.id)">商品管理</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="核销员管理"
                  placement="top"
                >
                  <a @click="edit(scope.row.id)">核销员管理</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="删除"
                  placement="top"
                >
                  <a @click="edit(scope.row.id)">删除</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="门店管理"
                  placement="top"
                >
                  <a @click="edit(scope.row.id)">门店管理</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="分享"
                  placement="top"
                >
                  <a @click="edit(scope.row.id)">分享</a>
                </el-tooltip>
              </div>
            </template>
          </el-table-column>
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
import { storeList, allStoreGroup } from '@/api/admin/storeManage/store'
import pagination from '@/components/admin/pagination/pagination'
// 地区编码
import chinaData from '@/assets/china-data'
export default {
  components: { pagination },
  data () {
    return {
      loading: false,
      langDefaultFlag: false,

      pageParams: {},
      queryParams: {
        groupId: null,
        isAuthPos: null
      },
      tableData: [],
      storeGroup: [],

      // 表格原始数据
      originalData: []
    }
  },
  methods: {
    initDataList () {
      this.loading = true

      storeList(Object.assign(this.queryParams, this.pageParams)).then((res) => {
        if (res.error === 0) {
          this.originalData = res.content.dataList
          let originalData = JSON.parse(JSON.stringify(this.originalData))
          this.handleData(originalData)
          this.pageParams = res.content.page
          this.loading = false
        }
      })
    },
    // 表格数据处理/渲染
    handleData (data) {
      data.map((item, index) => {
        item.address = this.getFullAddress(item)
        item.businessHours = item.openingTime + ' - ' + item.closeTime
        item.businessStateName = item.businessState === 1 ? '营业' : '未营业'
      })
      this.tableData = data
      this.langDefaultFlag = true
    },
    initGroupList () {
      allStoreGroup().then((res) => {
        if (res.error === 0) {
          this.storeGroup = res.content
        }
      })
    },
    getFullAddress (item) {
      var address = ''

      var province = chinaData.filter(function (area) {
        return area.provinceId === parseInt(item.provinceCode)
      })
      province = province[0]
      address += province.provinceName
      var areaCity = province.areaCity.filter(function (area) {
        return area.cityId === parseInt(item.cityCode)
      })
      areaCity = areaCity[0]
      address += ' ' + areaCity.cityName
      var areaDistrict = areaCity.areaDistrict.filter(function (area) {
        return area.districtId === parseInt(item.districtCode)
      })
      areaDistrict = areaDistrict[0]
      address += ' ' + areaDistrict.districtName + ' ' + item.address

      return address
    },
    edit (id) {

    }
  },
  watch: {
    // data内变量国际化
    lang () {
      if (this.langDefaultFlag) {
        // 重新渲染表格数据
        let originalData = JSON.parse(JSON.stringify(this.originalData))
        this.handleData(originalData)
      }
    }
  },
  mounted () {
    this.langDefault()
    this.initGroupList()
    this.initDataList()
  }

}
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
  .navBox {
    background-color: #fff;
    padding: 0 15px 14px;
    margin-bottom: 10px;
  }
  .btn {
    margin-left: 5px;
  }
  .table_box {
    background-color: #fff;
    padding: 15px;
    .filters {
      display: flex;
      line-height: 32px;
      margin-left: -15px;
      margin-bottom: 10px;
      .filters_item {
        max-width: 350px;
        display: flex;
        margin-left: 15px;
        > span {
          min-width: 80px;
          font-size: 14px;
        }
      }
    }
    .operation {
      display: flex;
      flex-wrap: wrap;
      margin-left: -5px;
      > .item {
        font-size: 14px;
        color: #66b1ff;
        cursor: pointer;
        margin-right: 8px;
      }
    }
    .businessStateOperate {
      font-size: 14px;
      color: #66b1ff;
      cursor: pointer;
    }
    .tapOneblock {
      display: flex;
      justify-content: flex-end;
      margin-top: 10px;
      > span {
        height: 32px;
        line-height: 32px;
      }
    }
  }
}
</style>
