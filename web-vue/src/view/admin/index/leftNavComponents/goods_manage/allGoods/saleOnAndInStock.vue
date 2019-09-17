<template>
  <div class="saleOnAndInStock">
    <el-table
      :data="goodsData"
      class="tableClass"
      border
      style="width: 100%">
      <el-table-column
        align="center"
        width="70px"
        label="">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.check"></el-checkbox>
        </template>
      </el-table-column>
      <!-- 商品名称图片 -->
      <el-table-column
        align="center"
        prop="goodsName"
        label="名称" min-width="120px">
        <template slot-scope="scope">
          <div style="display: flex;align-items: center;">
            <img style="width: 70px;height: 70px;float: left;flex-shrink: 0;"  :src="scope.row.goodsImg">
            <div style="width: 150px;float: left;flex-shrink: 0;padding:10px;">
              <span v-if="scope.row.sourceName !== null" class="goodsTypeSpanWrap">{{scope.row.sourceName}}</span>
              <span v-if="scope.row.goodsTypeName !== null" class="goodsSourceSpanWrap">{{scope.row.goodsTypeName}}</span>
              {{scope.row.goodsName}}
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="价格">
        <template slot-scope="{row}">
           <span v-if="row.prdId !== null">{{row.shopPrice}}</span>
           <template v-else>
             <span v-if="!row.shopPriceEdit">
               {{row.shopPrice}}
               <span class="el-icon-edit-outline iconSpan" style="margin-left: 10px;" @click="shopPriceAndGoodsNumberEditClick(row,'price')"></span>
             </span>
             <input :id="'shopPrice_'+row.goodsId" v-else v-model.number="row.shopPriceOld"
                    @change="shopPriceChange(row)" @blur="row.shopPriceEdit = false" class="editInput"/>
           </template>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="goodsSn"
        label="商品货号" />
      <el-table-column
        align="center"
        prop="catName"
        label="平台分类" />
      <el-table-column
        align="center"
        prop="sortName"
        label="商家分类" />
      <el-table-column
        align="center"
        prop="brandName"
        label="品牌">
      </el-table-column>
      <el-table-column
        align="center"
        label="库存">
        <template slot-scope="{row}">
          <span v-if="row.prdId !== null">{{row.goodsNumber}}</span>
          <template v-else>
             <span v-if="!row.goodsNumberEdit">
               {{row.goodsNumber}}
               <span class="el-icon-edit-outline iconSpan" style="margin-left: 10px;" @click="shopPriceAndGoodsNumberEditClick(row,'number')"></span>
             </span>
            <input v-else :id="'goodsNumber_'+row.goodsId" v-model.number="row.goodsNumberOld"
                   @change="goodsNumberChange(row)" @blur="row.goodsNumberEdit = false" class="editInput"/>
          </template>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="goodsSaleNum"
        label="销量" />
      <el-table-column
        align="center"
        label="商品标签"
        min-width="100px">
        <template slot-scope="{row}">
          <div style="display: flex;align-items: center;">
            <div style="width:120px; flex-shrink: 0;">
              <span v-for="(item,index) in row.goodsLabels" :key="index" class="goodsLabelSpanWrap">{{item.name}}</span>
            </div>
            <div style="width: 50px;flex-shrink: 0;color:#5a8bff;cursor: pointer;">设置</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="操作">
        <template slot-scope="{row,$index}">
          <el-tooltip content="编辑" placement="top">
            <span class="el-icon-edit-outline iconSpan" @click="editIconClick(row)"></span>
          </el-tooltip>
          <el-tooltip content="复制" placement="top">
            <span class="fa fa-copy iconSpan" @click="copyIconClick(row)"></span>
          </el-tooltip>
          <el-tooltip content="分享" placement="top">
            <span class="el-icon-share iconSpan"></span>
          </el-tooltip>
          <el-tooltip content="下架" placement="top">
            <span class="el-icon-bottom iconSpan" @click="withdrawIconClick(row,$index)"></span>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <span class="el-icon-delete iconSpan" @click="deleteIconClick(row,$index)"></span>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <div class="allGoodsFooter" style="display: flex;">
      <div class="operateBtnWrap" style="width:50%;"></div>
      <div class="paginationWrap" style="width:50%">
        <pagination :page-params.sync="pageParams" @pagination="fetchGoodsData"/>
      </div>
    </div>
  </div>
</template>
<script>

import {getGoodsList, deleteGoods, batchOperateGoods} from '@/api/admin/goodsManage/allGoods/allGoods'
// 组件导入
import pagination from '@/components/admin/pagination/pagination'

export default {
  name: 'saleOnAndInStock',
  components: {pagination},
  beforeRouteEnter  (to, from, next) {
    if (to.name === 'goodsForSale') {

    } else {

    }
    next()
  },
  data () {
    return {
      filterData: {},
      goodsData: [],
      pageParams: {
        currentPage: 1,
        pageRows: 20
      }
    }
  },
  methods: {
    setFilterData (filterData) {
      this.filterData = filterData
    },
    /* 分页查询数据 */
    fetchGoodsData (filterData) {
      if (filterData !== undefined) {
        this.filterData = filterData
      }
      let param = {
        ...this.pageParams,
        ...this.filterData
      }
      getGoodsList(param).then(res => {
        let {content: {page, dataList}} = res

        this.pageParams.totalRows = page.totalRows
        this.pageParams.currentPage = page.currentPage
        this.pageParams.pageRows = page.pageRows

        dataList.forEach(item => {
          item.sourceName = item.source === 0 ? '自营' : '非自营'

          switch (item.goodsType) {
            case 1: item.goodsTypeName = '拼团商品'
              break
            case 2: item.goodsTypeName = '分销'
              break
            case 3: item.goodsTypeName = '砍价商品'
              break
            case 4: item.goodsTypeName = '积分商品'
              break
            case 5: item.goodsTypeName = '秒杀商品'
              break
            default:
              item.goodsTypeName = null
          }

          item.shopPriceEdit = false
          item.shopPriceOld = item.shopPrice
          item.goodsNumberEdit = false
          item.goodsNumberOld = item.goodsNumber
        })

        this.goodsData = dataList
      })
    },
    /* 商品价格和库存修改图标点击事件 */
    shopPriceAndGoodsNumberEditClick (row, type) {
      if (type === 'price') {
        row.shopPriceEdit = true
        this.$nextTick(() => document.getElementById('shopPrice_' + row.goodsId).focus())
      } else {
        row.goodsNumberEdit = true
        this.$nextTick(() => document.getElementById('goodsNumber_' + row.goodsId).focus())
      }
    },
    /* 商品价格输入框处理函数 */
    shopPriceChange (row) {
      row.shopPriceEdit = false
      if (typeof row.shopPriceOld !== 'number' || row.shopPriceOld < 0) {
        row.shopPriceOld = row.shopPrice
        this.$message({type: 'warning', message: '请输入正确价格'})
        return
      }
      row.shopPrice = row.shopPriceOld

      // TODO: 执行ajax进行修改操作
    },
    /* 商品数量输入框处理函数 */
    goodsNumberChange (row) {
      row.goodsNumberEdit = false
      if (typeof row.goodsNumberOld !== 'number' || row.goodsNumberOld < 0) {
        row.goodsNumberOld = row.goodsNumber
        this.$message({type: 'warning', message: '请输入正确商品数量'})
        return
      }
      row.goodsNumber = parseInt(row.goodsNumberOld)
      row.goodsNumberOld = row.goodsNumber

      // TODO:执行ajax进行修改操作
    },
    /* 修改图标按钮点击 */
    editIconClick (row) {
      this.$router.push({name: 'goods_update', params: {goodsId: row.goodsId}})
    },
    /* 删除图标按钮点击 */
    deleteIconClick (row, index) {
      this._$confirm('确认要删除吗？', '删除成功!', () => {
        return deleteGoods({goodsIds: [row.goodsId]}).then((res) => {
          this.goodsData.splice(index, 1)
        })
      })
    },
    /* 下架图标按钮点击 */
    withdrawIconClick (row, index) {
      this._$confirm('确认下架该商品吗？', '下架成功!', () => {
        return batchOperateGoods({goodsIds: [row.goodsId], isOnSale: 0}).then((res) => {
          this.goodsData.splice(index, 1)
        })
      })
    },
    /* 复制商品 */
    copyIconClick (row) {
      this.$router.push({name: 'goods_add', params: {goodsId: row.goodsId, isCopy: 1}})
    },
    /* 操作确认弹框 */
    _$confirm (questionMessage, confirmMesage, confirmCallback, cancelCallback) {
      this.$confirm(questionMessage, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return confirmCallback()
      }).then(() => {
        this.$message({
          type: 'success',
          message: confirmMesage
        })
      }).catch(() => {
        if (cancelCallback !== undefined) {
          cancelCallback()
        }
      })
    }
  },
  mounted () {
    this.fetchGoodsData()
  }
}
</script>

<style lang="css" scoped>
  .saleOnAndInStock{
    margin-top: 10px;
  }
  /deep/.tableClass th{
    background-color: #f5f5f5;
    border: none;
    height: 36px;
    font-weight: bold;
    color: #000;
    padding: 8px 10px;
  }
  .goodsSourceSpanWrap{
    border: 1px solid #EF8115 ;
    color: #Ef8115 ;
    border-radius: 3px;
    padding: 2px;
    margin-right: 2px;
  }
  .goodsTypeSpanWrap{
    border: 1px solid #FF3F3F;
    color: #FF3F3F;
    border-radius: 3px;
    padding: 2px;
    margin-right: 2px;
  }
  .goodsLabelSpanWrap{
    border: 1px solid #CCCCCC;
    color: #666;
    border-radius: 3px;
    padding: 2px;
    margin-right: 2px;
    display: inline-block;
  }
  .iconSpan{
    font-size: 20px;
    color: #5a8bff;
    cursor: pointer!important;
  }
  .editInput{
    width: 80px;
    height: 25px;
    border: 1px solid #ccc;
    text-align: center;
  }
</style>
