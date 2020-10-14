<template>
  <div>
    <div class="searchContent">
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
        <div class="externalWrap hisWrap">
          <p class="externalTitleWrap">医院</p>
          <div class="externalSearchWrap">
            <input placeholder="名称+规格系数+药企"/>
            <button>GO</button>
          </div>
          <div class="externalTableWrap">
            <el-table height="280" border :data="externalHisGoodsList" highlight-current-row
                      @current-change="hisCurRowChange"
                      style="width: 100%;" class="tableClass">
              <el-table-column type="index" width="20px"/>
              <el-table-column label="价格" prop="goodsPrice"/>
              <el-table-column label="名称" prop="goodsCommonName"/>
              <el-table-column label="规格系数" prop="goodsQualityRatio"/>
              <el-table-column label="药企" prop="goodsProductionEnterprise"/>
            </el-table>
          </div>
          <el-pagination layout="total, prev, pager, next" :total="storePageInfo.totalRows"
                         :current-page.sync="hisPageInfo.currentPage"
                         @current-change="hisOrStorePageChange(1)"/>
        </div>

        <!--药房信息-->
        <div class="externalWrap storeWrap">
          <p class="externalTitleWrap">药房</p>
          <div class="externalSearchWrap">
            <input placeholder="名称+规格系数+药企"/>
            <button>GO</button>
          </div>
          <div class="externalTableWrap">
            <el-table height="280" border :data="externalStoreGoodsList" highlight-current-row
                      @current-change="storeCurRowChange"
                      style="width: 100%;" class="tableClass">
              <el-table-column type="index" width="20px"/>
              <el-table-column label="价格" prop="goodsPrice"/>
              <el-table-column label="名称" prop="goodsCommonName"/>
              <el-table-column label="规格系数" prop="goodsQualityRatio"/>
              <el-table-column label="药企" prop="goodsProductionEnterprise"/>
            </el-table>
          </div>
          <el-pagination layout="total, prev, pager, next" :total="storePageInfo.totalRows"
                         :current-page.sync="storePageInfo.currentPage"
                         @current-change="hisOrStorePageChange(2)"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getExternalPageList } from '@/api/admin/goodsManage/allGoods/goodsAudit.js'

export default {
  name: 'GoodsAudit',
  data () {
    return {
      hisSearchData: {
        goodsCommonName: null,
        goodsAliasName: null,
        goodsQualityRatio: null,
        goodsApprovalNumber: null,
        goodsProductionEnterprise: null,
        pageListFrom: 1
      },
      storeSearchData: {
        goodsCommonName: null,
        goodsAliasName: null,
        goodsQualityRatio: null,
        goodsApprovalNumber: null,
        goodsProductionEnterprise: null,
        pageListFrom: 2
      },
      externalHisGoodsList: [],
      externalStoreGoodsList: [{
        id: 1,
        isMedical: 1,
        isRx: 0,
        state: 1,
        goodsPrice: 44.4,
        goodsNumber: 10,
        goodsCommonName: 'AAA',
        goodsQualityRatio: 'BBBBBBBBBBBBB',
        goodsProductionEnterprise: 'XXXXXXXXXXXXXXXXXXXXX',
        goodsApprovalNumber: 'xxxxxxxxxxxxxxxxxxx'
      },
      {
        id: 2,
        isMedical: 1,
        isRx: 0,
        state: 1,
        goodsPrice: 44.4,
        goodsNumber: 10,
        goodsCommonName: 'AAA',
        goodsQualityRatio: 'BBBBBBBBBBBBB',
        goodsProductionEnterprise: 'XXXXXXXXXXXXXXXXXXXXX',
        goodsApprovalNumber: 'xxxxxxxxxxxxxxxxxxx'
      },
      {
        id: 3,
        isMedical: 1,
        isRx: 0,
        state: 1,
        goodsPrice: 44.4,
        goodsNumber: 10,
        goodsCommonName: 'AAA',
        goodsQualityRatio: 'BBBBBBBBBBBBB',
        goodsProductionEnterprise: 'XXXXXXXXXXXXXXXXXXXXX',
        goodsApprovalNumber: 'xxxxxxxxxxxxxxxxxxx'
      },
      {
        id: 4,
        isMedical: 1,
        isRx: 0,
        state: 1,
        goodsPrice: 44.4,
        goodsNumber: 10,
        goodsCommonName: 'AAA',
        goodsQualityRatio: 'BBBBBBBBBBBBB',
        goodsProductionEnterprise: 'XXXXXXXXXXXXXXXXXXXXX',
        goodsApprovalNumber: 'xxxxxxxxxxxxxxxxxxx'
      }],
      hisTableCurRow: null,
      storeTableCurRow: null,
      hisPageInfo: {
        currentPage: 1,
        totalRows: 100
      },
      storePageInfo: {
        currentPage: 1,
        totalRows: 100
      }

    }
  },
  methods: {
    tabItemClicked (routerName) {
      this.$router.push({ name: routerName })
    },
    hisCurRowChange (curRow) {
      this.hisTableCurRow = curRow
    },
    storeCurRowChange (curRow) {
      this.storeTableCurRow = curRow
    },
    hisOrStorePageChange (pageListFrom) {
      let param = {}
      if (pageListFrom === 1) {
        param = {
          ...this.hisSearchData,
          currentPage: this.hisPageInfo.currentPage
        }
      } else {
        param = {
          ...this.storeSearchData,
          currentPage: this.storePageInfo.currentPage
        }
      }
      this.loadHisOrStoreDataList(param)
    },
    loadHisOrStoreDataList (param) {
      getExternalPageList(param).then(res => {
        if (res.error !== 0) {
          this.$message.warning({ message: res.message })
          return
        }

        let {content: {page, dataList}} = res
        if (param.pageListFrom === 1) {
          this.hisPageInfo.totalRows = page.totalRows
          this.externalHisGoodsList = dataList
        } else {
          this.storePageInfo.totalRows = page.totalRows
          this.externalStoreGoodsList = dataList
        }
      })
    }
  },
  mounted () {
    this.loadHisOrStoreDataList({pageListFrom: 1})
    this.loadHisOrStoreDataList({pageListFrom: 2})
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
    justify-content: center;
  }

  .externalWrap {
    width: 49%;
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
    width: 240px;
    border-radius: 5px;
  }
</style>
