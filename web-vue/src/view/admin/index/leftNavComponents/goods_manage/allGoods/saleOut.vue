<template>
  <div class="saleOut">
    <div style="width: 100%; padding: 10px;background: #fff;">
      <el-table
        :data="goodsData"
        class="tableClass"
        border
        style="width: 100%"
      >
        <el-table-column
          align="center"
          width="70px"
          label=""
        >
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.check"></el-checkbox>
          </template>
        </el-table-column>
        <!-- 商品名称图片 -->
        <el-table-column
          align="center"
          prop="goodsName"
          :label="$t('allGoods.allGoodsData.goodsName')"
          width="180px"
        >
          <template slot-scope="{row}">
            <div>
              <img
                style="width: 70px;height: 70px;float: left;"
                :src="row.prdDesc === ''? row.goodsImg : row.prdImg"
              >
              <div style="padding:10px;">
                <span
                  v-if="row.sourceName !== null"
                  class="goodsTypeSpanWrap"
                >{{row.sourceName}}</span>
                <span
                  v-if="row.goodsTypeName !== null"
                  class="goodsSourceSpanWrap"
                >{{row.goodsTypeName}}</span>
                {{row.goodsName}}
                <span v-if="row.prdDesc === ''">
                  ({{$t('allGoods.allGoodsData.noPrdSn')}})
                </span>
                <span v-else>
                  {{row.prdDesc}}
                </span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('allGoods.allGoodsData.shopPrice')"
        >
          <template slot-scope="{row}">
            <span v-if="!row.prdPriceEdit">
              {{row.prdPrice}}
              <span
                class="el-icon-edit-outline iconSpan"
                style="margin-left: 10px;"
                @click="prdPriceAndPrdNumberEditClick(row,'price')"
              ></span>
            </span>
            <input
              :id="'prdPrice_'+row.prdId"
              v-else
              v-model.number="row.prdPriceOld"
              @change="prdPriceChange(row)"
              @blur="row.prdPriceEdit = false"
              class="editInput"
            />
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="prdSn"
          :label="$t('allGoods.allGoodsData.prdSn')"
        />
        <el-table-column
          align="center"
          prop="catName"
          :label="$t('allGoods.allGoodsData.cat')"
        />
        <el-table-column
          align="center"
          prop="sortName"
          :label="$t('allGoods.allGoodsData.sort')"
        />
        <el-table-column
          align="center"
          prop="brandName"
          :label="$t('allGoods.allGoodsData.goodsBrand')"
        >
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('allGoods.allGoodsData.goodsNumber')"
        >
          <template slot-scope="{row}">
            <span v-if="!row.prdNumberEdit">
              {{row.prdNumber}}
              <span
                class="el-icon-edit-outline iconSpan"
                style="margin-left: 10px;"
                @click="prdPriceAndPrdNumberEditClick(row,'number')"
              ></span>
            </span>
            <input
              v-else
              :id="'prdNumber_'+row.prdId"
              v-model.number="row.prdNumberOld"
              @change="goodsNumberChange(row)"
              @blur="row.prdNumberEdit = false"
              class="editInput"
            />
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('allGoods.allGoodsData.goodsLabel')"
          min-width="120px"
        >
          <template slot-scope="{row}">
            <div style="">
              <div style="width:120px; float: left;">
                <span
                  v-for="(item,index) in row.goodsLabels"
                  :key="index"
                  class="goodsLabelSpanWrap"
                >{{item.name}}</span>
              </div>
              <div
                style="width: 50px;float:right;color:#5a8bff;cursor: pointer;"
                @click="tdLabelSetClick(row)"
              >{{$t('allGoods.allGoodsData.setting')}}</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('allGoods.allGoodsData.operate')"
        >
          <template slot-scope="{row}">
            <el-tooltip
              :content="$t('allGoods.allGoodsData.edit')"
              placement="top"
            >
              <span
                class="el-icon-edit-outline iconSpan"
                @click="editIconClick(row)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('allGoods.allGoodsData.share')"
              placement="top"
            >
              <span
                class="el-icon-share iconSpan"
                @click="shareIconClick(row)"
              ></span>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        :page-params.sync="pageParams"
        @pagination="fetchGoodsData"
      />
    </div>

    <!--预览商品太阳码-->
    <el-dialog
      :visible.sync="qrCodeData.isShow"
      :title="$t('allGoods.allGoodsData.shareGoodsTitle')"
      width="350px"
    >
      <div style="text-align: center;">
        <el-image
          fit="scale-down"
          :src="qrCodeData.imgFullUrl"
          style="width: 250px; height: 230px;"
        />
        <el-input
          v-model="qrCodeData.pageUrl"
          disabled
        />
        <span>{{$t('allGoods.allGoodsData.copy')}}</span>
      </div>
    </el-dialog>

    <!--标签设置-->
    <el-dialog
      :visible.sync="goodsLabelData.isShow"
      :title="$t('allGoods.allGoodsData.setLabelTitle')"
      width="30%"
      @closed="goodsLabelDialogCancel"
    >
      <div style="background-color:#FFF7EB;border: 1px solid #FFD5A3;line-height: 30px;padding-left: 20px;margin-bottom: 10px;">{{$t('allGoods.allGoodsData.setLabelTip')}}</div>
      <div>
        <span>{{$t('allGoods.allGoodsData.goodsLabel')}}：</span>
        <el-select
          v-model="goodsLabelData.labelSelectedTempVal"
          :placeholder="$t('allGoods.allGoodsData.chooseCategory')"
          size="small"
          @change="tdLabelSelectChange"
          style="width:170px;"
        >
          <el-option
            v-for="item in goodsLabelData.labelSelectOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </div>
      <div
        v-if="goodsLabelData.labelSelectedOptions.length>0"
        style="display: flex;flex-wrap: wrap;align-items:center;margin-top: 10px;"
      >
        <div>{{$t('allGoods.allGoodsData.selected')}}：</div>
        <div
          class="selectedWrap"
          v-for="(item,index) in goodsLabelData.labelSelectedOptions"
          :key="index"
        >
          {{item.name}}
          <span
            @click="tdDeleteLabel(item,index)"
            class="deleteIcon"
          >×</span>
        </div>
      </div>
      <div slot="footer">
        <el-button
          @click="goodsLabelDialogConfirm"
          type="primary"
          size="small"
        >{{$t('goodsAddEditInfo.confirmBtn')}}</el-button>
        <el-button
          @click="goodsLabelDialogCancel"
          type="primary"
          size="small"
        >{{$t('goodsAddEditInfo.cancelBtn')}}</el-button>
      </div>
    </el-dialog>
    <!-- 商品导出确认弹窗 -->
    <goodsExportConfirmDialog
      :show.sync="showExportConfirm"
      :param="this.filterData"
      :paramString="this.filterDataString"
    />
  </div>
</template>
<script>

import { getGoodsProductList, batchOperateSpecPrdPriceNumber, updateLabelByGoodsId } from '@/api/admin/goodsManage/allGoods/allGoods'
import { getGoodsQrCode, goodsSortAndGoodsBrandInitApi } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
// 组件导入
import pagination from '@/components/admin/pagination/pagination'
import goodsExportConfirmDialog from './goodsExportConfirmDialog'

export default {
  name: 'saleOut',
  components: { pagination, goodsExportConfirmDialog },
  data () {
    return {
      filterData: {},
      filterDataString: {}, // 用于导出时展示已选条件
      goodsData: [],
      pageParams: {
        currentPage: 1,
        pageRows: 20
      },
      qrCodeData: {
        imgFullUrl: null,
        pageUrl: null,
        isShow: false
      },
      goodsLabelData: {
        currentRow: null,
        labelSelectedTempVal: null,
        labelSelectOptions: [],
        labelSelectedOptions: [],
        isShow: false
      },
      showExportConfirm: false
    }
  },
  methods: {
    /* 商品价格和库存修改图标点击事件 */
    prdPriceAndPrdNumberEditClick (row, type) {
      if (type === 'price') {
        row.prdPriceEdit = true
        this.$nextTick(() => document.getElementById('prdPrice_' + row.prdId).focus())
      } else {
        row.prdNumberEdit = true
        this.$nextTick(() => document.getElementById('prdNumber_' + row.prdId).focus())
      }
    },
    /* 商品价格输入框处理函数 */
    prdPriceChange (row) {
      row.prdPriceEdit = false
      if (typeof row.prdPriceOld !== 'number' || row.prdPriceOld < 0) {
        row.prdPriceOld = row.shopPrice
        this.$message.warning({ type: 'warning', message: this.$t('allGoods.allGoodsData.shopPriceRequired') })
        return
      }
      row.shopPrice = row.prdPriceOld
      let shopPrices = {}
      shopPrices[row.goodsId] = [{
        prdId: row.prdId,
        shopPrice: row.shopPrice
      }]
      batchOperateSpecPrdPriceNumber({
        goodsIds: [row.goodsId],
        goodsPriceNumbers: shopPrices
      }).then(res => {
        if (res.error === 0) {
          this.$message.success({ type: 'info', message: '设置成功!' })
        }
      })
    },
    /* 商品数量输入框处理函数 */
    goodsNumberChange (row) {
      row.prdNumberEdit = false
      if (typeof row.prdNumberOld !== 'number' || row.prdNumberOld < 0) {
        row.prdNumberOld = row.goodsNumber
        this.$message.warning({ type: 'warning', message: this.$t('allGoods.allGoodsData.goodsNumberRequired') })
        return
      }
      row.goodsNumber = parseInt(row.prdNumberOld)
      row.prdNumberOld = row.goodsNumber

      let goodsNumbers = {}
      goodsNumbers[row.goodsId] = [{
        prdId: row.prdId,
        goodsNumber: row.goodsNumber
      }]
      batchOperateSpecPrdPriceNumber({
        goodsIds: [row.goodsId],
        goodsPriceNumbers: goodsNumbers
      }).then(res => {
        if (res.error === 0) {
          this.$message.success({ type: 'info', message: '设置成功!' })
          this.fetchGoodsData()
        }
      })
    },
    /** table表单内标签 **/
    /* table表单内标签设置按钮 */
    tdLabelSetClick (row) {
      goodsSortAndGoodsBrandInitApi().then(res => {
        const { content: { goodsLabels } } = res
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
        this.$message.success.success({ type: 'info', message: '设置成功' })
        // 刷新数据
        this.fetchGoodsData()
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
      this.$router.push({ name: 'goods_update', params: { goodsId: row.goodsId } })
    },
    shareIconClick (row) {
      getGoodsQrCode(row.goodsId).then(res => {
        this.qrCodeData.imgFullUrl = res.content.imgFullUrl
        this.qrCodeData.pageUrl = res.content.pageUrl
        this.qrCodeData.isShow = true
      })
    },
    /* 操作确认弹框 */
    _$confirm (questionMessage, confirmMesage, confirmCallback, cancelCallback) {
      this.$confirm(questionMessage, this.$t('allGoods.allGoodsData.tip'), {
        confirmButtonText: this.$t('allGoods.allGoodsData.confirm'),
        cancelButtonText: this.$t('allGoods.allGoodsData.cancel'),
        type: 'warning'
      }).then(() => {
        return confirmCallback()
      }).then(() => {
        this.$message.success({
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
      getGoodsProductList(params).then(res => {
        let { content: { page, dataList } } = res

        this.pageParams.totalRows = page.totalRows
        this.pageParams.currentPage = page.currentPage
        this.pageParams.pageRows = page.pageRows

        dataList.forEach(item => {
          // item.sourceName = item.source === 0 ? '自营' : '非自营'
          item.sourceName = item.source === 0 ? this.$t('allGoods.allGoodsHeaderData.goodsSourceOptions')[1] : this.$t('allGoods.allGoodsHeaderData.goodsSourceOptions')[2]

          switch (item.goodsType) {
            // case 1: item.goodsTypeName = '拼团商品'
            case 1: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[0]
              break
            // case 2: item.goodsTypeName = '分销'
            case 2: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[1]
              break
            // case 3: item.goodsTypeName = '砍价商品'
            case 3: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[2]
              break
            // case 4: item.goodsTypeName = '积分商品'
            case 4: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[3]
              break
            // case 5: item.goodsTypeName = '秒杀商品'
            case 5: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[4]
              break
            default:
              item.goodsTypeName = null
          }

          item.prdPriceEdit = false
          item.prdPriceOld = item.shopPrice
          item.prdNumberEdit = false
          item.prdNumberOld = item.goodsNumber
        })

        this.goodsData = dataList
      })
    },
    showExportDialog (filterData, filterDataString) {
      if (filterData !== undefined) {
        this.filterData = filterData
        this.filterDataString = filterDataString
      }
      this.showExportConfirm = true
    }
  }
}
</script>

<style lang="scss" scoped>
/deep/.tableClass th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.goodsSourceSpanWrap {
  border: 1px solid #ef8115;
  color: #ef8115;
  border-radius: 3px;
  padding: 2px;
  margin-right: 2px;
}
.goodsTypeSpanWrap {
  border: 1px solid #ff3f3f;
  color: #ff3f3f;
  border-radius: 3px;
  padding: 2px;
  margin-right: 2px;
}
.goodsLabelSpanWrap {
  border: 1px solid #cccccc;
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
.iconSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
}
.editInput {
  width: 80px;
  height: 25px;
  border: 1px solid #ccc;
  text-align: center;
}
</style>
