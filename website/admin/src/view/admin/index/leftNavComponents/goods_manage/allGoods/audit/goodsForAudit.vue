<template>
  <div>
    <div class="searchContent" v-loading="isLoading">
      <div class="headerTab">
        <div class="tabItem" @click="tabItemClicked('goodsForSale')">{{$t('allGoods.allGoodsRouterHeader.saleOn')}}
        </div>
        <div class="tabItem" @click="tabItemClicked('goodsForInStock')">
          {{$t('allGoods.allGoodsRouterHeader.inStock')}}
        </div>
        <div class="tabItem tabItemActive">商品审核</div>
      </div>
      <div class="hisAndStoreWrap">
        <!--his信息-->
        <div class="externalWrap hisWrap" style="height: auto;">
          <p class="externalTitleWrap">医院</p>
          <div class="externalSearchWrap">
            <button @click="refreshData">刷新</button>
          </div>
          <div class="externalTableWrap">
            <el-table border :data="externalHisGoodsList" class="tableClass">
              <el-table-column width="40px"/>
              <el-table-column label="Id" prop="id" width="60px"/>
              <el-table-column label="使用计算价" width="70px" align="center">
                <template slot-scope="{row}">
                  <el-checkbox v-model="row.useCalculatePrice"/>
                </template>
              </el-table-column>
              <el-table-column label="价格" prop="goodsPrice" width="100"/>
              <el-table-column label="计算价格" prop="calculatePrice" width="100"/>
              <el-table-column label="名称" prop="goodsCommonName" width="200"/>
              <el-table-column label="规格系数" prop="goodsQualityRatio" width="150"/>
              <el-table-column label="药企" prop="goodsProductionEnterprise" width="150"/>
              <el-table-column label="基本单位" prop="goodsBasicUnit" width="100"/>
              <el-table-column label="包装单位" prop="goodsPackageUnit" width="100"/>
              <el-table-column label="转换系数" prop="goodsUnitConvertFactor" width="100"/>
              <el-table-column label="批准文号" prop="goodsApprovalNumber" width="150"/>
              <el-table-column label="药品/RX" width="120">
                <template slot-scope="{row}">
                  {{row.isMedical ===1 ? '是':'否'}}/{{row.isRx ===1 ? '是':'否'}}
                </template>
              </el-table-column>
              <el-table-column label="等效量" prop="goodsEquivalentQuantity" width="100"/>
              <el-table-column label="等效单位" prop="goodsEquivalentUnit" width="100"/>
              <el-table-column label="编号" prop="goodsCode"/>
            </el-table>
          </div>
        </div>

        <!--药房信息-->
        <div class="externalWrap storeWrap">
          <p class="externalTitleWrap">药房</p>
          <div class="externalSearchWrap">
            <input placeholder="名称" v-model="storeSearchData.goodsCommonName"/>
            <input placeholder="规格系数" v-model="storeSearchData.goodsQualityRatio"/>
            <input placeholder="药企" v-model="storeSearchData.goodsProductionEnterprise"/>
            <input placeholder="批准文号" v-model="storeSearchData.goodsApprovalNumber"/>
            <button @click="loadHisOrStoreDataList(storeSearchData)">搜索</button>
            <button @click="resetSearchData">重置</button>
          </div>
          <div class="externalTableWrap">
            <el-table height="280" border :data="externalStoreGoodsList" style="width: 100%;" class="tableClass" @row-click="storeRowClicked">
              <el-table-column width="40px">
                <template slot-scope="{row}">
                  <el-checkbox v-model="row.checked" @change="storeCheckedChange(row)"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column label="Id" prop="id" width="60px"/>
              <el-table-column label="价格" prop="goodsPrice" width="100"/>
              <el-table-column label="名称" prop="goodsCommonName" width="200"/>
              <el-table-column label="规格系数" prop="goodsQualityRatio" width="150"/>
              <el-table-column label="药企" prop="goodsProductionEnterprise" width="150"/>
              <el-table-column label="基本单位" prop="goodsBasicUnit" width="100"/>
              <el-table-column label="包装单位" prop="goodsPackageUnit" width="100"/>
              <el-table-column label="转换系数" prop="goodsUnitConvertFactor" width="100"/>
              <el-table-column label="批准文号" prop="goodsApprovalNumber" width="150"/>
              <el-table-column label="药品/RX" width="120">
                <template slot-scope="{row}">
                  {{row.isMedical ===1 ? '是':'否'}}/{{row.isRx ===1 ? '是':'否'}}
                </template>
              </el-table-column>
              <el-table-column label="等效量" prop="goodsEquivalentQuantity" width="100"/>
              <el-table-column label="等效单位" prop="goodsEquivalentUnit" width="100"/>
              <el-table-column label="编号" prop="goodsCode"/>
            </el-table>
          </div>
          <el-pagination layout="total, prev, pager, next" :total="storeSearchData.totalRows"
                         :current-page.sync="storeSearchData.currentPage"
                         :page-size="storeSearchData.pageRows"
                         @current-change="storePageChange(2)"/>
        </div>
      </div>
      <div class="operateBtnWrap" style="height: 40px;text-align: center;">
        <button style="width: 100px;height: 30px;margin-right: 20px;cursor: pointer;" @click="failHisDataToMatch">放 弃</button>
        <button style="width: 100px;height: 30px;margin-right: 20px;cursor: pointer;" @click="hisAndStoreGoodsMatch">配 对</button>
        <button style="width: 100px;height: 30px;margin-right: 20px;cursor: pointer;" @click="useStoreGoodsToMatch">上架药房药品</button>
        <button style="width: 100px;height: 30px;cursor:pointer;" @click="saveMatchedGoodsInfos">入库保存</button>
      </div>
      <div class="readyToSaveGoodsWrap">
        <el-table :data="readyToSaveGoodsList" border height="600" class="tableClass">
          <el-table-column type="index" width="40"/>
          <el-table-column label="价格" prop="hisPrice" width="100"/>
          <el-table-column label="名称" prop="goodsCommonName"/>
          <el-table-column label="规格系数" prop="goodsQualityRatio"/>
          <el-table-column label="药企" prop="goodsProductionEnterprise"/>
          <el-table-column label="来源">
            <template slot-scope="{row}">
                {{row.source ===1 ? '医院':'药房'}}
            </template>
          </el-table-column>
          <el-table-column label="批准文号" prop="goodsApprovalNumber"/>
          <el-table-column label="药品/RX">
            <template slot-scope="{row}">
              {{row.isMedical ===1 ? '是':'否'}}/{{row.isRx ===1 ? '是':'否'}}
            </template>
          </el-table-column>
          <el-table-column label="状态">
            <template slot-scope="{row}">
              {{row.state ===1 ? '启用':'禁用'}}
            </template>
          </el-table-column>
          <el-table-column label="基本单位" prop="goodsBasicUnit" width="100"/>
          <el-table-column label="包装单位" prop="goodsPackageUnit" width="100"/>
          <el-table-column label="转换系数" prop="goodsUnitConvertFactor" width="100"/>
          <el-table-column label="等效量" prop="goodsEquivalentQuantity" width="100"/>
          <el-table-column label="等效单位" prop="goodsEquivalentUnit" width="100"/>
          <el-table-column label="his编号" prop="hisGoodsCode"/>
          <el-table-column label="store编号" prop="storeGoodsCode"/>
          <el-table-column label="操作">
            <template slot-scope="{row}">
              <button @click="cancelMatchedData">取 消</button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import { getExternalPageList, insertMatchedGoodsList, failMatch } from '@/api/admin/goodsManage/allGoods/goodsAudit.js'

export default {
  name: 'GoodsAudit',
  data () {
    return {
      isLoading: false,
      hisSearchData: {
        pageListFrom: 1
      },
      storeSearchData: {
        goodsCommonName: null,
        goodsAliasName: null,
        goodsQualityRatio: null,
        goodsApprovalNumber: null,
        goodsProductionEnterprise: null,
        pageListFrom: 2,
        currentPage: 1,
        pageRows: 20,
        totalRows: 0
      },
      externalHisGoodsList: [],
      externalStoreGoodsList: [],
      storeTableCurRow: null,
      /* 已经匹配的数据 */
      readyToSaveGoodsList: []
    }
  },
  methods: {
    tabItemClicked (routerName) {
      this.$router.push({ name: routerName })
    },
    storeCheckedChange (row) {
      if (!row.checked) {
        this.storeTableCurRow = null
        return
      }
      for (let i = 0; i < this.externalStoreGoodsList.length; i++) {
        if (this.externalStoreGoodsList[i] !== row) {
          this.externalStoreGoodsList[i].checked = false
        }
      }
      this.storeTableCurRow = row
    },
    storeRowClicked (row) {
      row.checked = !row.checked
      this.storeCheckedChange(row)
    },
    storePageChange () {
      let param = {
        ...this.storeSearchData
      }
      this.loadHisOrStoreDataList(param)
    },
    resetSearchData () {
      this.storeSearchData = {
        goodsCommonName: null,
        goodsAliasName: null,
        goodsQualityRatio: null,
        goodsApprovalNumber: null,
        goodsProductionEnterprise: null,
        pageListFrom: 2
      }
    },
    loadHisOrStoreDataList (param) {
      this.isLoading = true
      return getExternalPageList(param).then(res => {
        this.isLoading = false
        if (res.error !== 0) {
          this.$message.warning({ message: res.message })
          return
        }
        let {content: {page, dataList}} = res
        // his部分数据处理
        if (param.pageListFrom === 1) {
          if (dataList !== null && dataList.length > 0) {
            this.storeSearchData.goodsCommonName = dataList[0].goodsCommonName
            this.storeSearchData.goodsApprovalNumber = dataList[0].goodsApprovalNumber
            if (dataList[0].goodsProductionEnterprise !== null) {
              this.storeSearchData.goodsProductionEnterprise = dataList[0].goodsProductionEnterprise.substr(0, 2)
            }

            dataList[0].useCalculatePrice = false
            let targetData = dataList[0]
            if (!!targetData.goodsPrice && !!targetData.goodsUnitConvertFactor) {
              try {
                targetData.calculatePrice = (targetData.goodsPrice * targetData.goodsUnitConvertFactor).toFixed(2)
              } catch (e) {
                console.log(e)
              }
            }
          }
          this.externalHisGoodsList = dataList
        } else {
          // store部分数据处理
          this.storeTableCurRow = null
          this.storeSearchData.totalRows = page.totalRows
          dataList.forEach(row => {
            row.checked = false
          })
          this.externalStoreGoodsList = dataList
        }
      })
    },
    /* 配对，入库相关操作 */
    hisAndStoreGoodsMatch () {
      if (this.externalHisGoodsList == null || this.externalHisGoodsList.length === 0) {
        this.$message.warning({message: '无医院商品!'})
        return
      }
      if (this.readyToSaveGoodsList.length !== 0) {
        this.$message.warning({message: '本医院药品已配对，未保存入库!'})
        return
      }
      if (this.storeTableCurRow === null) {
        this.$message.warning({message: '请选择一条药房药品!'})
        return
      }
      let goodsInfo = this.mergeHisAndStoreInfoToGoodsInfo(this.externalHisGoodsList[0], this.storeTableCurRow)
      this.readyToSaveGoodsList.push(goodsInfo)
    },
    mergeHisAndStoreInfoToGoodsInfo (hisGoodsInfo, storeGoodsInfo) {
      let ret = {
        fromHisId: hisGoodsInfo.id,
        // 这个是药房中间表的id，不是门店id哦
        fromStoreId: storeGoodsInfo.id,
        hisGoodsCode: hisGoodsInfo.goodsCode,
        storeGoodsCode: storeGoodsInfo.goodsCode,
        source: 1,
        ...hisGoodsInfo
      }
      if (hisGoodsInfo.useCalculatePrice) {
        ret.hisPrice = hisGoodsInfo.calculatePrice
      } else {
        ret.hisPrice = hisGoodsInfo.goodsPrice
      }
      ret.storePrice = storeGoodsInfo.goodsPrice
      ret['goodsBarCode'] = storeGoodsInfo.goodsBarCode
      return ret
    },
    /* 只上架药房商品 */
    useStoreGoodsToMatch: function () {
      if (this.readyToSaveGoodsList.length !== 0) {
        this.$message.warning({message: '本医院药品已配对，未保存入库!'})
        return
      }
      if (this.storeTableCurRow === null) {
        this.$message.warning({message: '请选择一条药房药品!'})
      }
      let goodsInfo = this.convertStoreInfoToGoodsInfo(this.storeTableCurRow)
      this.readyToSaveGoodsList.push(goodsInfo)
    },
    convertStoreInfoToGoodsInfo (storeGoodsInfo) {
      let ret = {
        ...storeGoodsInfo,
        storeGoodsCode: storeGoodsInfo.goodsCode,
        storePrice: storeGoodsInfo.goodsPrice,
        source: 2
      }
      return ret
    },
    /* 刷新数据 */
    refreshData () {
      this.readyToSaveGoodsList = []
      this.storeTableCurRow = null
      this.loadHisOrStoreDataList(this.hisSearchData).then(res => {
        this.loadHisOrStoreDataList(this.storeSearchData)
      })
    },
    /* 保存已配对的数据 */
    saveMatchedGoodsInfos () {
      if (this.readyToSaveGoodsList.length === 0) {
        this.$message.warning({message: '请先配对医院和药房药品!'})
        return
      }
      this.isLoading = true
      insertMatchedGoodsList(this.readyToSaveGoodsList).then(res => {
        this.isLoading = false
        if (res.error === 400056) {
          this.$message.warning({message: '信息已被他人处理!'})
          this.refreshData()
          return
        }

        if (res.error !== 0) {
          this.$message.warning({message: '操作失败!'})
          return
        }

        this.refreshData()
      })
    },
    /* 放弃配对 */
    failHisDataToMatch () {
      if (this.externalHisGoodsList == null || this.externalHisGoodsList.length === 0) {
        this.$message.warning({message: '无医院商品!'})
        return
      }
      let hisId = this.externalHisGoodsList[0].id
      this.isLoading = true
      failMatch({hisId}).then(res => {
        this.isLoading = false
        if (res.error !== 0) {
          this.$message.warning({message: '操作失败!'})
          return
        }
        this.refreshData()
      })
    },
    cancelMatchedData () {
      this.readyToSaveGoodsList = []
    }
  },
  mounted () {
    this.refreshData()
  }
}
</script>

<style scoped>
  /deep/ .tableClass th {
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }

  .searchContent {
    width: 100%;
    padding: 10px;
    background: #fff;
    margin-bottom: 10px;
  }

  .headerTab {
    border-bottom: 1px solid #eeeeee;
    color: #666;
    display: flex;
  }

  .tabItem {
    font-size: 14px;
    min-width: 50px;
    line-height: 40px;
    /* margin: 0px 5px; */
    margin-right: 20px;
    cursor: pointer;
  }

  .tabItemActive {
    border-bottom: 1.5px solid #5a8bff;
  }

  .hisAndStoreWrap {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .externalWrap {
    width: calc(100% - 20px);
    height: 400px;
    border: 1px solid #ccc;
    margin: 10px;
    padding: 5px;
  }

  .externalTitleWrap {
    font-weight: bold;
    letter-spacing: 2px;
    height: 20px;
    border-bottom: 1px solid #ccc;
  }

  .externalSearchWrap {
    display: flex;
    justify-content: center;
    margin: 10px 0px;
  }

  .externalSearchWrap button {
    margin-left: 5px;
    cursor: pointer;
  }

  .externalSearchWrap input {
    border: 1px solid #616161;
    outline: none;
    height: 30px;
    width: 200px;
    margin-right: 10px;
    border-radius: 5px;
  }
</style>
