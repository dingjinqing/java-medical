<template>
  <div class="content">
    <div class="main">
      <div class="navBox">
        <el-button
          type="primary"
          size="small"
          @click="addStoreHandle"
        >{{$t('storeList.addStore')}}</el-button>
      </div>
      <div class="table_box">
        <div class="filters">
          <div class="filters_item"><span>{{$t('storeList.storeGroup')}}：</span>
            <el-select
              v-model="queryParams.groupId"
              size="small"
              style="width:170px;"
            >
              <el-option
                :label="$t('storeCommon.all')"
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
          <div class="filters_item"><span>{{$t('storeList.isAuthPos')}}：</span>
            <el-select
              v-model="queryParams.isAuthPos"
              size="small"
              style="width:170px;"
            >
              <el-option
                :label="$t('storeCommon.all')"
                selected
                :value="null"
              ></el-option>
              <el-option
                :label="$t('storeCommon.yes')"
                :value="true"
              ></el-option>
              <el-option
                :label="$t('storeCommon.no')"
                :value="false"
              ></el-option>
            </el-select>
          </div>
          <div class="filters_item"><span>{{$t('storeList.storeInfo')}}：</span>
            <el-input
              v-model="queryParams.keywords"
              size="small"
              style="width:170px;"
              :placeholder="$t('storeList.storeInfoPlaceholder')"
            ></el-input>
          </div>
          <el-button
            @click="initDataList"
            class="btn"
            type="primary"
            size="small"
          >{{$t('storeCommon.filter')}}</el-button>
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
            :label="$t('storeList.storeName')"
          ></el-table-column>
          <el-table-column
            prop="posShopId"
            :label="$t('storeList.posShopId')"
          ></el-table-column>
          <el-table-column
            prop="groupName"
            :label="$t('storeList.groupName')"
          >
          </el-table-column>
          <el-table-column
            prop="address"
            :label="$t('storeList.storeAddress')"
          ></el-table-column>
          <el-table-column
            prop="manager"
            :label="$t('storeList.manager')"
          ></el-table-column>
          <el-table-column
            prop="mobile"
            :label="$t('storeList.mobile')"
          ></el-table-column>
          <el-table-column
            prop="businessHours"
            :label="$t('storeList.businessHours')"
          ></el-table-column>
          <el-table-column
            prop="businessStateName"
            :label="$t('storeList.businessState')"
          >
            <template slot-scope="scope">
              {{scope.row.businessStateName}}
              <a
                @click="closeDown(scope.row.storeId)"
                class="businessStateOperate"
                v-if="scope.row.businessState === 1"
              >{{$t('storeList.closeDown')}}</a>
              <a
                @click="opening(scope.row.storeId)"
                class="businessStateOperate"
                v-else
              >{{$t('storeList.opening')}}</a>
            </template>
          </el-table-column>
          <el-table-column
            prop=""
            :label="$t('storeList.purchaseOrderCode')"
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
                  :content="$t('storeCommon.edit')"
                  placement="top"
                >
                  <a @click="edit('edit', scope.row.storeId, scope.row)">{{$t('storeCommon.edit')}}</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeList.goodsManage')"
                  placement="top"
                >
                  <a @click="edit('goodsManage', scope.row.storeId, scope.row)">{{$t('storeList.goodsManage')}}</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeList.verifierManage')"
                  placement="top"
                >
                  <a @click="edit('verifierManage', scope.row.storeId, scope.row)">{{$t('storeList.verifierManage')}}</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeCommon.delete')"
                  placement="top"
                >
                  <a @click="del(scope.row.storeId)">{{$t('storeCommon.delete')}}</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeList.storeManage')"
                  placement="top"
                >
                  <a @click="edit('storeManage', scope.row.storeId, scope.row)">{{$t('storeList.storeManage')}}</a>
                </el-tooltip>
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="$t('storeCommon.share')"
                  placement="top"
                >
                  <a @click="edit('share',scope.row.storeId, scope.row)">{{$t('storeCommon.share')}}</a>
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

    <!-- 分享 -->
    <shareDialog
      :show="shareDialog"
      :imgPath="shareImg"
      :pagePath="sharePath"
      @close="shareDialog=false"
    />

  </div>

</template>

<script>
import { storeList, allStoreGroup, updateStore, delStore, shareStore } from '@/api/admin/storeManage/store'
import pagination from '@/components/admin/pagination/pagination'
// 地区编码
import chinaData from '@/assets/china-data'
import shareDialog from '@/components/admin/shareDialog'
export default {
  components: { pagination, shareDialog },
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
      originalData: [],

      shareImg: '',
      sharePath: '',
      shareDialog: false // 分享弹窗
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
        item.businessStateName = item.businessState === 1 ? this.$t('storeList.open') : this.$t('storeList.notOpen')
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
    // 歇业
    closeDown (id) {
      var param = {}
      param.storeId = id
      param.businessState = 0
      updateStore(param).then((res) => {
        if (res.error === 0) {
          this.$message.success({
            message: this.$t('marketCommon.successfulOperation')
          })
          this.initDataList()
        }
      })
    },
    // 开业
    opening (id) {
      var param = {}
      param.storeId = id
      param.businessState = 1
      updateStore(param).then((res) => {
        if (res.error === 0) {
          this.$message.success({
            message: this.$t('marketCommon.successfulOperation')
          })
          this.initDataList()
        }
      })
    },
    // 新增门店
    addStoreHandle () {
      this.$router.push({
        path: '/admin/home/main/store/addStore'
      })
    },
    // 删除门店
    del (id) {
      let param = {
        'storeId': id
      }
      this.$confirm(this.$t('storeList.delStoreTip'), this.$t('marketCommon.tip'), {
        confirmButtonText: this.$t('marketCommon.ok'),
        cancelButtonText: this.$t('marketCommon.cancel'),
        type: 'warning'
      }).then(() => {
        delStore(param).then((res) => {
          if (res.error === 0) {
            this.$message.success({
              message: this.$t('marketCommon.successfulOperation')
            })
            this.initDataList()
          }
        })
      })
    },
    edit (param, id, row) {
      console.log(param, id, row)
      switch (param) {
        case 'edit':
          console.log('edit', row)
          this.$router.push({
            path: '/admin/home/main/store/addStore',
            query: {
              id: id
            }
          })
          break
        case 'goodsManage':
          this.$router.push({
            path: '/admin/home/main/store/goods/list',
            query: {
              name: row.storeName,
              id: id
            }
          })
          break
        case 'verifierManage':
          this.$router.push({
            path: '/admin/home/main/store/verification/list',
            query: {
              id: id
            }
          })
          break
        case 'storeManage':
          this.$router.push({
            path: '/admin/home/main/store/storemanage',
            query: {
              id: id,
              businessHours: row.businessHours,
              businessType: row.businessType
            }
          })
          break
        case 'share':
          this.shareDialog = !this.shareDialog
          shareStore(id).then((res) => {
            if (res.error === 0) {
              this.shareImg = res.content.imageUrl
              this.sharePath = res.content.pagePath
            }
          })
          break
      }
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
    if (this.$route.query.groupId) {
      this.$set(this.queryParams, 'groupId', Number(this.$route.query.groupId))
    }
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
    padding: 15px;
  }
  .btn {
    margin-left: 5px;
  }
  .table_box {
    margin-top: 10px;
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
