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
          <div >
            <img style="width: 70px;height: 70px;float: left;"  :src="scope.row.goodsImg">
            <div style="padding:10px;">
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
           <span v-if="row.prdId === null">
             <template v-if="row.prdMinShopPrice === row.prdMaxShopPrice">
               {{row.prdMinShopPrice}}
             </template>
             <template v-else>
               {{row.prdMinShopPrice}}~{{row.prdMaxShopPrice}}
             </template>
           </span>
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
          <span v-if="row.prdId === null">{{row.goodsNumber}}</span>
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
        min-width="120px">
        <template slot-scope="{row}">
          <div style="">
            <div style="width:120px; float: left;">
              <span v-for="(item,index) in row.goodsLabels" :key="index" class="goodsLabelSpanWrap">{{item.name}}</span>
            </div>
            <div style="width: 50px;float:right;color:#5a8bff;cursor: pointer;" @click="tdLabelSetClick(row)">设置</div>
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
            <span class="el-icon-share iconSpan" @click="shareIconClick(row)"></span>
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
      <div class="operateBtnWrap" style="width:50%;">
      </div>
      <div class="paginationWrap" style="width:50%">
        <pagination :page-params.sync="pageParams" @pagination="fetchGoodsData"/>
      </div>
    </div>

    <!--预览商品太阳码-->
    <el-dialog :visible.sync="qrCodeData.isShow" title="扫一扫，分享给好友吧~" width="350px">
      <div style="text-align: center;">
        <el-image
          fit="scale-down"
          :src="qrCodeData.imgFullUrl"
          style="width: 250px; height: 230px;"
        />
        <el-input v-model="qrCodeData.imgFullUrl" disabled />
        <span>复制</span>
      </div>
    </el-dialog>

    <!--标签设置-->
    <el-dialog :visible.sync="goodsLabelData.isShow" title="设置标签" width="30%" @closed="goodsLabelDialogCancel">
      <div style="background-color:#FFF7EB;border: 1px solid #FFD5A3;line-height: 30px;padding-left: 20px;margin-bottom: 10px;"> 可以在这里编辑商品标签信息,添加或删除标签</div>
      <div>
        <span>商品标签：</span>
        <el-select v-model="goodsLabelData.labelSelectedTempVal" placeholder="请选择标签" size="small" @change="tdLabelSelectChange" style="width:170px;">
          <el-option v-for="item in goodsLabelData.labelSelectOptions" :key="item.id" :label="item.name" :value="item.id"/>
        </el-select>
      </div>
      <div v-if="goodsLabelData.labelSelectedOptions.length>0" style="display: flex;flex-wrap: wrap;align-items:center;margin-top: 10px;">
        <div>已选：</div>
        <div class="selectedWrap" v-for="(item,index) in goodsLabelData.labelSelectedOptions" :key="index">
          {{item.name}}
          <span @click="tdDeleteLabel(item,index)" class="deleteIcon">×</span>
        </div>
      </div>
      <div slot="footer">
        <el-button @click="goodsLabelDialogConfirm" type="primary" size="small">{{$t('goodsAddEditInfo.confirmBtn')}}</el-button>
        <el-button @click="goodsLabelDialogCancel" type="primary" size="small">{{$t('goodsAddEditInfo.cancelBtn')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>

import {getGoodsList, deleteGoods, batchOperateGoods, updateLabelByGoodsId} from '@/api/admin/goodsManage/allGoods/allGoods'
import {getGoodsQrCode, goodsSortAndGoodsBrandInitApi} from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
// 组件导入
import pagination from '@/components/admin/pagination/pagination'

export default {
  name: 'saleOnAndInStock',
  components: {pagination},
  data () {
    return {
      filterData: {},
      goodsData: [],
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      qrCodeData: {
        imgFullUrl: null,
        isShow: false
      },
      goodsLabelData: {
        currentRow: null,
        labelSelectedTempVal: null,
        labelSelectOptions: [],
        labelSelectedOptions: [],
        isShow: false
      }
    }
  },
  methods: {
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
      let shopPrices = {}
      shopPrices[row.goodsId] = [{
        prdId: row.prdId,
        shopPrice: row.shopPrice
      }]
      batchOperateGoods({
        goodsIds: [row.goodsId],
        goodsPriceNumbers: shopPrices
      }).then(res => {
        if (res.error === 0) {
          this.$message({type: 'info', message: '设置成功!'})
        }
      })
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

      let goodsNumbers = {}
      goodsNumbers[row.goodsId] = [{
        prdId: row.prdId,
        goodsNumber: row.goodsNumber
      }]
      batchOperateGoods({
        goodsIds: [row.goodsId],
        goodsPriceNumbers: goodsNumbers
      }).then(res => {
        if (res.error === 0) {
          this.$message({type: 'info', message: '设置成功!'})
        }
      })
    },
    /** table表单内标签 **/
    /* table表单内标签设置按钮 */
    tdLabelSetClick (row) {
      goodsSortAndGoodsBrandInitApi().then(res => {
        const {content: {goodsLabels}} = res
        this.goodsLabelData.currentRow = row
        this.goodsLabelData.isShow = true
        goodsLabels.forEach(item => {
          if (this.goodsLabelData.currentRow.goodsLabels.some(goodsLabel => goodsLabel.id === item.id)) {
            this.goodsLabelData.labelSelectedOptions.push(item)
          } else {
            this.goodsLabelData.labelSelectOptions.push(item)
          }
        })
      })
    },
    /* talbe表单内标签下拉框交互 */
    tdLabelSelectChange () {
      this.goodsLabelData.labelSelectOptions = this.goodsLabelData.labelSelectOptions.filter(item => {
        if (item.id === this.goodsLabelData.labelSelectedTempVal) {
          this.goodsLabelData.labelSelectedOptions.push(item)
          return false
        }
        return true
      })
      this.goodsLabelData.labelSelectedTempVal = null
    },
    /* talbe表单内标签删除图标点击 */
    tdDeleteLabel (item, index) {
      this.goodsLabelData.labelSelectedOptions.splice(index, 1)
      this.goodsLabelData.labelSelectOptions.push(item)
    },
    /* table表单内标签dialog确认按钮 */
    goodsLabelDialogConfirm () {
      let param = {
        goodsId: this.goodsLabelData.currentRow.goodsId,
        labelIds: []
      }
      this.goodsLabelData.labelSelectedOptions.forEach(item => param.labelIds.push(item.id))
      updateLabelByGoodsId(param).then((res) => {
        if (res.error !== 0) {
          return
        }
        this.goodsLabelData.currentRow.goodsLabels = this.goodsLabelData.labelSelectedOptions
        this.goodsLabelData.labelSelectedOptions = []
        this.goodsLabelData.labelSelectOptions = []
        this.goodsLabelData.currentRow = null
        this.goodsLabelData.isShow = false
        this.$message({type: 'info', message: '设置成功'})
      })
    },
    goodsLabelDialogCancel () {
      this.goodsLabelData.labelSelectedOptions = []
      this.goodsLabelData.labelSelectOptions = []
      this.goodsLabelData.currentRow = null
      this.goodsLabelData.isShow = false
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
      this.$router.push({
        name: 'goods_add',
        params: {goodsId: row.goodsId, isCopy: 1}
      })
    },
    shareIconClick (row) {
      getGoodsQrCode(row.gooodsId).then(res => {
        this.qrCodeData.imgFullUrl = res.content.imgFullUrl
        this.qrCodeData.isShow = true
      })
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
    },
    /* 分页查询数据 */
    fetchGoodsData (filterData) {
      if (filterData !== undefined) {
        this.filterData = filterData
      }
      let params = {
        ...this.pageParams,
        ...this.filterData
      }
      getGoodsList(params).then(res => {
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
    }
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
  .selectedWrap {
    min-width: 70px;
    height: 22px;
    border: 1px solid #ccc;
    line-height: 22px;
    text-align: center;
    padding: 0px 5px;
    margin: 0px 5px;
    background-color: #fff;
    position: relative;
  }
  .selectedWrap .deleteIcon {
    width: 17px;
    height: 17px;
    color: #fff;
    background: #ccc;
    border: 1px solid #ccc;
    border-radius: 50%;
    line-height: 17px;
    text-align: center;
    position: absolute;
    top: -8px;
    right: -8px;
    cursor: pointer;
    opacity: 0.8;
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
