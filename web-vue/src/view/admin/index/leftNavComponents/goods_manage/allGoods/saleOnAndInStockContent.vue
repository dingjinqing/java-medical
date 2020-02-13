<template>
  <div class="saleOnAndInStock">
    <div style="width: 100%; padding: 10px;background: #fff;">
      <el-table
        :data="goodsData"
        class="tableClass"
        border
        style="width: 100%"
        @sort-change="sortChange"
      >
        <!-- 复选框 -->
        <el-table-column
          align="center"
          width="50"
          label=""
        >
          <template slot-scope="scope">
            <el-checkbox
              :key="scope.row.goodsId"
              v-model="scope.row.check"
            ></el-checkbox>
          </template>
        </el-table-column>
        <!-- 商品名称图片 -->
        <el-table-column
          align="left"
          prop="goodsName"
          :label="$t('allGoods.allGoodsData.goodsName')"
          width="200"
        >
          <template slot-scope="scope">
            <div class="nameImgWrap">
              <img
                class="imgItem"
                :src="scope.row.goodsImg"
              >
              <div
                class="nameItem"
                :title="scope.row.goodsName"
              >
                <span
                  v-if="scope.row.sourceName !== null"
                  class="goodsTypeSpanWrap"
                >{{scope.row.sourceName}}</span>
                <span
                  v-if="scope.row.goodsTypeName !== null"
                  class="goodsSourceSpanWrap"
                >{{scope.row.goodsTypeName}}</span>
                <span>{{scope.row.goodsName}}</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="shopPrice"
          sortable="custom"
          align="center"
          :label="$t('allGoods.allGoodsData.shopPrice')"
          width="100"
        >
          <template slot-scope="{row}">
            <!--非默认规格-->
            <span v-if="!row.isDefaultPrd">
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
                <span
                  class="el-icon-edit-outline iconSpan"
                  style="margin-left: 10px;"
                  @click="shopPriceAndGoodsNumberEditClick(row,'price')"
                ></span>
              </span>
              <input
                v-else
                :id="'shopPrice_'+row.goodsId"
                v-model.number="row.shopPriceOld"
                @change="shopPriceChange(row)"
                @blur="row.shopPriceEdit = false"
                class="editInput"
              />
            </template>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="goodsSn"
          width="100"
          :label="$t('allGoods.allGoodsData.goodsSn')"
        />
        <!--平台分类-->
        <el-table-column
          align="center"
          prop="catName"
          :label="$t('allGoods.allGoodsData.cat')"
          width="100"
        />
        <!--商家分类-->
        <el-table-column
          align="center"
          prop="sortName"
          :label="$t('allGoods.allGoodsData.sort')"
          width="100"
        />
        <!--商品品牌-->
        <el-table-column
          align="center"
          prop="brandName"
          :label="$t('allGoods.allGoodsData.goodsBrand')"
          width="90"
        >
        </el-table-column>
        <!--商品库存-->
        <el-table-column
          prop="goodsNumber"
          sortable="custom"
          align="center"
          :label="$t('allGoods.allGoodsData.goodsNumber')"
          width="120"
        >
          <template slot-scope="{row,$index}">
            <span v-if="row.prdId === null">{{row.goodsNumber}}</span>
            <template v-else>
              <span v-if="!row.goodsNumberEdit">
                {{row.goodsNumber}}
                <span
                  class="el-icon-edit-outline iconSpan"
                  style="margin-left: 10px;"
                  @click="shopPriceAndGoodsNumberEditClick(row,'number')"
                ></span>
              </span>
              <input
                v-else
                :id="'goodsNumber_'+row.goodsId"
                v-model.number="row.goodsNumberOld"
                @change="goodsNumberChange(row,$index)"
                @blur="row.goodsNumberEdit = false"
                class="editInput"
              />
            </template>
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsSaleNum"
          sortable="custom"
          align="center"
          width="100"
          :label="$t('allGoods.allGoodsData.saleNumber')"
        />
        <el-table-column
          align="center"
          :label="$t('allGoods.allGoodsData.goodsLabel')"
          width="130"
        >
          <template slot-scope="{row}">
            <div style="display: flex;justify-content: flex-end;align-items: center;">
              <div>
                <span
                  v-for="(item,index) in row.goodsLabels"
                  :key="index"
                  class="goodsLabelSpanWrap"
                >{{item.name}}</span>
              </div>
              <div
                style="width:40px;flex-shrink:0;cursor: pointer;"
                @click="tdLabelSetClick(row)"
              >
                {{$t('allGoods.allGoodsData.setting')}}
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('allGoods.allGoodsData.operate')"
        >
          <template slot-scope="{row,$index}">
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
              :content="$t('allGoods.allGoodsData.copy')"
              placement="top"
            >
              <span
                style="font-size: 22px;color:#5a8bff;"
                class="fa fa-copy iconSpan"
                @click="copyIconClick(row)"
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
            <el-tooltip
              v-if="filterData.isOnSale === 1"
              :content="$t('allGoods.allGoodsData.underCarriage')"
              placement="top"
            >
              <span
                class="el-icon-download iconSpan"
                @click="withdrawIconClick(row,$index)"
              ></span>
            </el-tooltip>
            <el-tooltip
              v-if="filterData.isOnSale === 0"
              :content="$t('allGoods.allGoodsData.upCarriage')"
              placement="top"
            >
              <span
                class="el-icon-upload2 iconSpan"
                @click="upIconClick(row,$index)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('allGoods.allGoodsData.delete')"
              placement="top"
            >
              <span
                class="el-icon-delete iconSpan"
                @click="deleteIconClick(row,$index)"
              ></span>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <div class="allGoodsFooter">
        <div class="allGoodsFooterLeft">
          <el-checkbox v-model="allChecked">全选</el-checkbox>
          <el-button
            type="primary"
            plain
            size="small"
            @click="handleToClickBottomBtn(0)"
          >下架</el-button>
          <el-button
            type="primary"
            plain
            size="small"
            @click="handleToClickBottomBtn(1)"
          >删除</el-button>
          <el-button
            type="primary"
            plain
            size="small"
            @click="handleToClickBottomBtn(2)"
          >批量设置</el-button>
          <el-select
            v-model="batchExportVal"
            size="small"
          >
            <el-option
              v-for="item in batchExportOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>

        <pagination
          :page-params.sync="pageParams"
          @pagination="fetchGoodsData"
        />
      </div>

    </div>

    <!--预览商品太阳码-->
    <div class="qrCodeDialogWrap">
      <el-dialog
        :visible.sync="qrCodeData.isShow"
        :title="$t('allGoods.allGoodsData.shareGoodsTitle')"
        width="400px"
      >
        <div style="text-align: center;">
          <img
            :src="qrCodeData.imgFullUrl"
            alt
            style="width: 180px;height: 180px;border: none;"
          />
          <a
            :href="qrCodeData.imgFullUrl"
            download=""
            class="downLoadQrImg"
          >下载二维码</a>
          <div style="text-align: left;padding: 5px 5px 5px 50px;">
            <el-input
              ref="qrCodePageUrlInput"
              v-model="qrCodeData.pageUrl"
              size="small"
              style="width:230px;"
            />
            <span
              @click="copyPageUrlClick"
              class="copyQrCodeUrl"
            >{{this.$t('allGoods.allGoodsData.copy')}}</span>
          </div>
        </div>
      </el-dialog>
    </div>

    <!--标签设置-->
    <el-dialog
      :visible.sync="goodsLabelData.isShow"
      :title="$t('allGoods.allGoodsData.setLabelTitle')"
      width="30%"
      @closed="goodsLabelDialogCancel"
    >
      <div style="background-color:#FFF7EB;border: 1px solid #FFD5A3;line-height: 30px;padding-left: 20px;margin-bottom: 10px;">
        {{this.$t('allGoods.allGoodsData.setLabelTip')}}
      </div>
      <div>
        <span> {{this.$t('allGoods.allGoodsData.goodsLabel')}}：</span>
        <el-select
          v-model="goodsLabelData.labelSelectedTempVal"
          :placeholder="$t('allGoods.allGoodsHeaderData.chooseGoodsLabel')"
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
        style="display: flex;margin-top: 10px;"
      >
        <div style="width:45px;flex-shrink:0;">{{this.$t('allGoods.allGoodsData.selected')}}：</div>
        <div class="labelSelectedWrapPanel">
          <div
            class="labelSelectedWrap"
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
    <!--底部点击综合提示框-->
    <el-dialog
      :title="isBottomClickIndex===0 || isBottomClickIndex===1?'提醒':'提示'"
      :visible.sync="bottomDialogVisible"
      width="30%"
    >
      <div
        class="bottomTip"
        :style="isBottomClickIndex===2 || isBottomClickIndex===3?'text-align:left':''"
      >{{isBottomClickIndex===0?'确认要下架吗?':isBottomClickIndex===1?'确认要删除已选商品吗?':isBottomClickIndex===2?`根据以下条件筛选出${pageParams.totalRows}条数据,是否确认导出？`:`根据以下条件筛选出${nowCheckAll.length}条数据,是否确认导出？`}}</div>
      <div
        style="margin-top:10px"
        v-if="isBottomClickIndex===2 || isBottomClickIndex===3"
      >筛选条件：无</div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="bottomDialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToBottomClickSure()"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!--批量弹窗设置-->
    <BatchSetupDialog :dialogVisible.sync="batchSetupVisible" />
  </div>
</template>
<script>

import { getGoodsList, deleteGoods, batchOperateGoods, updateLabelByGoodsId, getGoodsFilterItem } from '@/api/admin/goodsManage/allGoods/allGoods'
import { getGoodsQrCode } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
// 组件导入
import pagination from '@/components/admin/pagination/pagination'
import goodsExportConfirmDialog from './goodsExportConfirmDialog'

export default {
  name: 'saleOnAndInStock',
  components: {
    pagination,
    goodsExportConfirmDialog,
    BatchSetupDialog: () => import('./batchSetupDialog') // 批量设置弹窗
  },
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
      showExportConfirm: false,
      allChecked: false, // 全选checkbox flag
      isDataCheckChange: false, // 是否是因为当前页数据改变而影响的allChecked
      batchExportVal: '0',
      batchExportOptions: [{
        value: '0',
        label: '批量导出'
      }, {
        value: '1',
        label: '批量导出筛选的件商品'
      }, {
        value: '2',
        label: '批量导出勾选结果'
      }],
      bottomDialogVisible: false, // 底部点击弹窗flag
      isBottomClickIndex: 0, // 底部按钮点击flag
      nowCheckAll: [], // 当前选中的总数
      batchSetupVisible: false // 批量设置弹窗flag
    }
  },
  watch: {
    goodsData: { // 监听当页数据判断是否全选
      handler (newData) {
        console.log(newData)
        let flag = newData.filter((item, index) => {
          console.log(item.check)
          return item.check
        })
        this.nowCheckAll = flag
        if (flag.length === newData.length) {
          this.allChecked = true
        } else {
          this.allChecked = false
          this.isDataCheckChange = true
        }
        console.log(flag, newData)
      },
      deep: true
    },
    allChecked (newData) { // 监听全选按钮
      console.log(newData)
      if (newData) {
        this.goodsData.forEach((item, index) => {
          item.check = true
        })
      } else {
        if (!this.isDataCheckChange) { // 点击全选触发
          this.goodsData.forEach((item, index) => {
            item.check = false
          })
        }
      }
      this.isDataCheckChange = false
      console.log(this.goodsData)
    },
    batchExportVal (newData) { // 底部 批量导出下拉框值变化
      if (newData === '1') {
        this.bottomDialogVisible = true
        this.isBottomClickIndex = 2 // 当前选中批量导出筛选的件商品
      } else if (newData === '2') {
        let flag = this.handleToJudgeIsChecked()
        if (!flag) {
          this.$message.error({
            message: '请选择商品',
            showClose: true
          })
          return
        }
        this.bottomDialogVisible = true
        this.isBottomClickIndex = 3 // 当前选中的批量导出勾选结果
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
        this.$message.warning({ type: 'warning', message: this.$t('allGoods.allGoodsData.shopPriceRequired') })
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
          this.$message.success({ type: 'info', message: this.$t('allGoods.allGoodsData.setSuccess') })
        }
      })
    },
    /* 商品数量输入框处理函数 */
    goodsNumberChange (row, index) {
      row.goodsNumberEdit = false
      if (typeof row.goodsNumberOld !== 'number' || row.goodsNumberOld < 0) {
        row.goodsNumberOld = row.goodsNumber
        this.$message.warning({ type: 'warning', message: this.$t('allGoods.allGoodsData.goodsNumberRequired') })
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
          this.$message.success({ type: 'info', message: this.$t('allGoods.allGoodsData.setSuccess') })
          if (row.goodsNumber === 0) {
            this.goodsData.splice(index, 1)
          }
        }
      })
    },
    /** table表单内标签 **/
    /* table表单内标签设置按钮 */
    tdLabelSetClick (row) {
      getGoodsFilterItem({ needGoodsLabel: true }).then(res => {
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
      this.goodsLabelData.isShow = false
      this.goodsLabelData.labelSelectedOptions = this.goodsLabelData.labelSelectedOptions.slice(0, 5)
      this.goodsLabelData.labelSelectedOptions.forEach(item => param.labelIds.push(item.id))
      updateLabelByGoodsId(param).then((res) => {
        if (res.error !== 0) {
          return
        }
        this.goodsLabelData.currentRow.goodsLabels = this.goodsLabelData.labelSelectedOptions
        this.goodsLabelData.labelSelectedOptions = []
        this.goodsLabelData.labelSelectOptions = []
        this.goodsLabelData.currentRow = null
        this.$message.success({ type: 'info', message: this.$t('allGoods.allGoodsData.setSuccess') })
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
    /* 删除图标按钮点击 */
    deleteIconClick (row, index) {
      this._$confirm(this.$t('allGoods.allGoodsData.deleteTipMsg'), this.$t('allGoods.allGoodsData.deleteOk'), () => {
        return deleteGoods({ goodsIds: [row.goodsId] }).then((res) => {
          this.goodsData.splice(index, 1)
        })
      })
    },
    /* 下架图标按钮点击 */
    withdrawIconClick (row, index) {
      this._$confirm(this.$t('allGoods.allGoodsData.underCarriageTipMsg'), this.$t('allGoods.allGoodsData.underCarriageOk'), () => {
        return batchOperateGoods({ goodsIds: [row.goodsId], isOnSale: 0 }).then((res) => {
          this.goodsData.splice(index, 1)
        })
      })
    },
    /* 上架图标按钮点击 */
    upIconClick (row, index) {
      this._$confirm(this.$t('allGoods.allGoodsData.upCarriageTipMsg'), this.$t('allGoods.allGoodsData.upCarriageOk'), () => {
        return batchOperateGoods({ goodsIds: [row.goodsId], isOnSale: 1 }).then((res) => {
          this.goodsData.splice(index, 1)
        })
      })
    },
    /* 复制商品 */
    copyIconClick (row) {
      this.$router.push({
        name: 'goods_add',
        params: { goodsId: row.goodsId, isCopy: 1 }
      })
    },
    shareIconClick (row) {
      getGoodsQrCode(row.goodsId).then(res => {
        this.qrCodeData.imgFullUrl = res.content.imgFullUrl
        this.qrCodeData.pageUrl = res.content.pageUrl
        this.qrCodeData.isShow = true
      })
    },
    copyPageUrlClick () {
      this.$refs.qrCodePageUrlInput.select()
      document.execCommand('Copy')
    },
    /* 表头排序 */
    sortChange (data) {
      this.$emit('sortChange', data.prop, data.order)
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
      console.log(filterData)
      getGoodsList(params).then(res => {
        let { content: { page, dataList } } = res

        this.pageParams.totalRows = page.totalRows
        this.pageParams.currentPage = page.currentPage
        this.pageParams.pageRows = page.pageRows
        this.batchExportOptions[1].label = `批量导出筛选的${page.totalRows}件商品`
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

          item.shopPriceEdit = false
          item.shopPriceOld = item.shopPrice
          item.goodsNumberEdit = false
          item.goodsNumberOld = item.goodsNumber
          item.check = false
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
    },
    // 底部栏按钮点击事件综合处理
    handleToClickBottomBtn (index) {
      // 判断是否有商品被选中
      let flag = this.handleToJudgeIsChecked()
      if (!flag) {
        this.$message.error({
          message: '未选择任何商品',
          showClose: true
        })
        return
      }
      switch (index) {
        case 0:
          this.bottomDialogVisible = true
          this.isBottomClickIndex = index
          break
        case 1:
          this.bottomDialogVisible = true
          this.isBottomClickIndex = index
          break
        case 2:
          this.batchSetupVisible = true
          console.log(this.batchSetupVisible)
          break
      }
    },
    // 判断是否有商品被选中
    handleToJudgeIsChecked () {
      let flag = this.goodsData.filter((item, index) => {
        return item.check
      })
      if (flag.length) return true
    },
    // 底部下架、删除等弹窗确定综合处理
    handleToBottomClickSure () {
      let arr = []
      switch (this.isBottomClickIndex) {
        case 0:
          console.log(this.nowCheckAll)
          this.nowCheckAll.forEach((item, index) => {
            arr.push(item.goodsId)
          })
          batchOperateGoods({ goodsIds: arr, isOnSale: 0 }).then((res) => {
            console.log(res)
            if (res.error === 0) {
              console.log(this.filterData)
              delete this.pageParams.totalRows
              this.fetchGoodsData(this.filterData)
            }
          })
          break
      }
      this.bottomDialogVisible = false
    }
  },
  mounted () {
    this.langDefault()
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
.nameImgWrap {
  display: flex;
}
.nameImgWrap::after {
  content: "";
  display: block;
  clear: both;
}
.imgItem {
  width: 60px;
  height: 60px;
  border-radius: 2px;
  border: 1px solid #ccc;
  margin: 2px 7px 0 0;
}
.nameItem {
  flex: 1;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
}
.goodsSourceSpanWrap {
  border: 1px solid #ef8115;
  color: #ef8115;
  border-radius: 3px;
  padding: 0px 2px;
}
.goodsTypeSpanWrap {
  border: 1px solid #ff3f3f;
  color: #ff3f3f;
  border-radius: 3px;
  padding: 0px 2px;
}
.goodsLabelSpanWrap {
  border: 1px solid #cccccc;
  color: #666;
  border-radius: 3px;
  padding: 0px 2px;
  margin-right: 2px;
  display: inline-block;
}
.labelSelectedWrapPanel {
  background-color: #f8f8f8;
  width: 80%;
  border: 1px solid #ccc;
  padding: 10px;
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  align-items: center;
}
.labelSelectedWrap {
  border: 1px solid #ccc;
  text-align: center;
  background-color: #fff;
  position: relative;
  padding: 5px 5px;
  margin-right: 10px;
  margin-top: 10px;
  flex-shrink: 0;
}
.labelSelectedWrap .deleteIcon {
  width: 13px;
  height: 13px;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  border-radius: 50%;
  line-height: 10px;
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
  margin-top: 5px;
}
.editInput {
  width: 80px;
  height: 25px;
  border: 1px solid #ccc;
  text-align: center;
}

/deep/.qrCodeDialogWrap .el-dialog__header {
  background-color: transparent;
}
/deep/.qrCodeDialogWrap .el-dialog__body {
  padding: 5px;
}
.downLoadQrImg {
  color: #999;
  font-size: 16px;
  display: inline-block;
  height: 40px;
  line-height: 40px;
  width: 100%;
  text-align: center;
  margin-left: 0;
  border-bottom: 1px solid #eee;
  text-decoration: none;
}
.copyQrCodeUrl {
  display: inline-block;
  margin-left: 5px;
  color: #5a8bff;
  background: #fff;
  border: none;
  height: 35px;
  line-height: 35px;
  font-size: 16px;
  cursor: pointer;
}
.allGoodsFooter {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-left: 22px;
  .allGoodsFooterLeft {
    /deep/ .el-button {
      margin: 0 5px;
    }
  }
}
.bottomTip {
  text-align: center;
}
</style>
