<template>
    <div class="goods-list-wrap">
        <div class="goods-list-header">
            <el-form ref="goodsListHeaderForm" :model="goodsFilterModel" :inline="true">
                <el-form-item label="商品信息：" prop="goodsName">
                    <el-input v-model="goodsFilterModel.goodsName" placeholder="商品名称" size="small"></el-input>
                </el-form-item>
                <el-form-item label="所属门店：" prop="storeId">
                    <el-select v-model="goodsFilterModel.storeId" size="small" :clearable="true">
                        <el-option v-for="(item,index) in storeInfos" :value="item.storeId" :label="item.storeName" :key="index"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否在售：" prop="isOnSale">
                    <el-select v-model="goodsFilterModel.isOnSale" size="small" :clearable="true">
                        <el-option :value="1" label="是"/>
                        <el-option :value="0" label="否"/>
                    </el-select>
                </el-form-item>
            </el-form>
            <div>
                <el-button size="small" type="primary" @click="confirmFilterGoods">确认</el-button>
                <el-button size="small" @click="resetGoodsFilterModel">重置</el-button>
            </div>
        </div>
        <div class="goods-list-content">
            <div class="goods-list-table">
                <el-table :data="goodsList" style="width: 100%" border class="tableClass">
                    <el-table-column type="index" width="50" align="center"/>
                    <el-table-column prop="storeName" label="门店名称"/>
                    <el-table-column prop="goodsCommonName" label="商品名称"/>
                    <el-table-column prop="goodsApprovalNumber"  label="规格系数"/>
                    <el-table-column prop="goodsProductionEnterprise"  label="生产企业"/>
                    <el-table-column prop="goodsStoreSn"  label="药房企业唯一编码"/>
                    <el-table-column prop="productNumber"  label="商品数量"/>
                    <el-table-column prop="productPrice"  label="商品价格"/>
                    <el-table-column label="是否在售">
                        <template slot-scope="{row}">
                            {{row.isOnSale===1?'是':'否'}}
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="goods-list-pagination">
                <el-pagination
                    :page-sizes="[20,30,40,50]"
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
import {getGoodsPageListApi} from '@/api/store/storeGoods'
import {allSourceRequest} from '@/api/store/store'

export default {
  name: 'goodsList',
  data () {
    return {
      goodsFilterModel: {
        goodsName: null,
        storeId: null,
        isOnSale: null
      },
      pageInfo: {
        currentPage: 1,
        pageRows: 20,
        total: 0
      },
      storeInfos: [],
      goodsList: []
    }
  },
  methods: {
    confirmFilterGoods () {
      this.loadGoodsList()
    },
    resetGoodsFilterModel () {
      this.$refs['goodsListHeaderForm'].resetFields()
    },
    /** 装载门店信息 */
    loadStoreInfo () {
      allSourceRequest().then(res => {
        if (res.error !== 0) {
          this.$message.warning({message: '系统错误', showClose: true})
          return
        }
        this.storeInfos = []
        res.content.forEach(item => {
          this.storeInfos.push({storeId: item.value, storeName: item.label})
        })
        console.log(this.storeInfos)
      })
    },
    /** 加载商品信息 */
    loadGoodsList () {
      let param = {
        ...this.goodsFilterModel,
        ...this.pageInfo
      }
      getGoodsPageListApi(param).then(res => {
        if (res.error !== 0) {
          this.$message.warning({message: '系统错误', showClose: true})
          return
        }
        this.goodsList = res.content.dataList
        this.pageInfo.currentPage = res.content.page.currentPage
        this.pageInfo.pageRows = res.content.page.pageRows
        this.pageInfo.totalRows = res.content.page.totalRows
      })
    },
    /** 分页信息发生改变 */
    paginationChange () {
      this.loadGoodsList()
    }
  },
  mounted () {
    // 装载门店信息
    this.loadStoreInfo()
    // 装载门店商品信息
    this.loadGoodsList()
  }
}
</script>

<style scoped>
    .goods-list-wrap{
        margin-top: 10px;
        width: calc(100% - 10px);
        height: 100%;
        overflow: hidden;
    }
    .goods-list-header{
        padding: 10px;
        background-color: white;
        height: 120px;
    }
    .goods-list-content{
        margin-top: 10px;
        height: calc(100% - 120px);
        background-color:white;
        overflow-y: auto;
    }
    .goods-list-table{
        overflow-y: auto;
    }
    /deep/.tableClass th {
        background-color: #f5f5f5;
        border: none;
        height: 36px;
        font-weight: bold;
        color: #000;
        padding: 8px 10px;
    }
    .goods-list-pagination{
        text-align: right;
        padding: 10px;

    }
</style>
