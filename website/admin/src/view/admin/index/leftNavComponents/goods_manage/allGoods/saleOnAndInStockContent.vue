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
          align="center"
          prop="goodsName"
          :label="$t('allGoods.allGoodsData.goodsName')"
          width="220"
        >
          <template slot-scope="scope">
            <div class="nameImgWrap">
              <img
                class="imgItem"
                :src="$imageHost+'/'+(scope.row.goodsImg||goodDefaultImgUrl)"
              >
              <div
                class="nameItem"
                :title="scope.row.goodsName"
              >
                <span v-html="scope.row.goodsName"></span>
                <br />
                {{scope.row.goodsQualityRatio}}
              </div>
            </div>
          </template>
        </el-table-column>
        <!--店铺价格-->
        <el-table-column
          prop="shopPrice"
          sortable="custom"
          align="center"
          label="价格"
          width="130"
        >
          <template slot-scope="{row}">
            <!--非默认规格-->
            <span v-if="!row.isDefaultProduct">
              <template v-if="row.minShopPrice === row.maxShopPrice">
                {{row.maxShopPrice}}
              </template>
              <template v-else>
                {{row.minShopPrice}}~{{row.maxShopPrice}}
              </template>
            </span>
            <template v-else>
              <span v-if="!row.shopPriceEdit">
                {{row.shopPrice}}
                <span
                  class="iconfont iconbianji"
                  style="margin-left: 10px;font-size:20px"
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
        <el-table-column label="来源">
          <template slot-scope="{row}">
            {{row.source ===1? '医院':'药房'}}
          </template>
        </el-table-column>
        <el-table-column label="医院价" prop="hisPrice"/>
        <el-table-column label="药房价" prop="storePrice"/>
        <!--商品货号-->
<!--        <el-table-column-->
<!--          align="center"-->
<!--          prop="goodsSn"-->
<!--          width="140"-->
<!--          :label="$t('allGoods.allGoodsData.goodsSn')"-->
<!--        />-->
        <el-table-column
          align="center"
          label="药品/处方"
          width="110"
        >
          <template slot-scope="{row}">
            {{row.isMedical?'是':'否'}}/{{row.isRx?'是':'否'}}
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="goodsCommonName"
          label="药品通用名"
          width="120"
        />
        <!--商家分类-->
        <el-table-column
          align="center"
          prop="sortName"
          :label="$t('allGoods.allGoodsData.sort')"
          width="120"
        />
        <!--商品品牌-->
        <el-table-column
          align="center"
          prop="brandName"
          :label="$t('allGoods.allGoodsData.goodsBrand')"
          width="120"
        >
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
                class="iconfont iconbianji"
                @click="editIconClick(row)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('allGoods.allGoodsData.copy')"
              placement="top"
            >
              <span
                class="iconfont iconfuzhi"
                @click="copyIconClick(row)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('allGoods.allGoodsData.share')"
              placement="top"
            >
              <span
                class="iconfont iconfenxiang1"
                @click="shareIconClick(row)"
              ></span>
            </el-tooltip>
            <el-tooltip
              v-if="filterData.isOnSale === 1"
              :content="$t('allGoods.allGoodsData.underCarriage')"
              placement="top"
            >
              <span
                class="iconfont iconxiajia"
                @click="withdrawIconClick(row,$index)"
              ></span>
            </el-tooltip>
            <el-tooltip
              v-if="filterData.isOnSale === 0"
              :content="$t('allGoods.allGoodsData.upCarriage')"
              placement="top"
            >
              <span
                class="iconfont iconshangjia"
                @click="upIconClick(row,$index)"
              ></span>
            </el-tooltip>
            <el-tooltip
              content="删除"
              placement="top"
            >
              <span
                class="iconfont iconshanchu2"
                @click="deleteIconClick(row,$index)"
              ></span>
            </el-tooltip>
          </template>
        </el-table-column>
        <!--销售数量-->
        <el-table-column
          prop="goodsSaleNum"
          sortable="custom"
          align="center"
          width="100"
          :label="$t('allGoods.allGoodsData.saleNumber')"
        />
        <!--his状态-->
        <el-table-column
          align="center"
          width="150"
          label="药品his状态"
        >
          <template slot-scope="{row}">
            {{row.hisStatus === 1 ? '上架':(row.hisStatus === 2 ? '下架': '')}}
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          width="150"
          label="药品来源"
        >
          <template slot-scope="{row}">
            <span v-if="row.hisStatus!==null && row.storeCode!==null">
              his+药房
            </span>
            <span v-if="row.hisStatus===null && row.storeCode!==null">
              药房数据
            </span>
            <span v-if="row.hisStatus !==null && row.storeCode === null">
              his数据
            </span>
          </template>
        </el-table-column>
        <!--标签-->
        <el-table-column
          align="center"
          :label="$t('allGoods.allGoodsData.goodsLabel')"
          width="120"
        >
          <template slot-scope="{row}">
            <div
              v-if="row.goodsNormalLabels.length + row.goodsPointLabels.length > 0"
              class="goodsLabelSpanWrap"
            >
              <div v-if="row.goodsPointLabels.length > 0">
                {{row.goodsPointLabels[0].name}}
              </div>
              <div v-if="row.goodsPointLabels.length === 0 && row.goodsNormalLabels.length > 0">
                {{row.goodsNormalLabels[0].name}}
              </div>
              <div style="text-align: center;">
                共{{row.goodsNormalLabels.length + row.goodsPointLabels.length }}个
              </div>
            </div>
            <div
              style="cursor: pointer;text-align: center;margin-top: 2px;color: #5a8bff;"
              @click="tdLabelSetClick(row)"
            >
              {{$t('allGoods.allGoodsData.setting')}}
            </div>
          </template>
        </el-table-column>
        <!--供应商-->
        <el-table-column
          align="center"
          label="供应商"
          prop="goodsProductionEnterprise"
        />

        <!--商品库存-->
        <el-table-column
          prop="goodsNumber"
          sortable="custom"
          align="center"
          :label="$t('allGoods.allGoodsData.goodsNumber')"
          width="130"
        >
          <template slot-scope="{row,$index}">
            <span v-if="row.isDefaultProduct === 0">
              {{row.goodsNumber}}
              <span
                class="iconfont iconbianji"
                style="margin-left: 10px;font-size:20px"
                @click="goodsNumEditClick(row)"
              ></span>
            </span>
            <template v-else>
              <span v-if="!row.goodsNumberEdit">
                {{row.goodsNumber}}
                <span
                  class="iconfont iconbianji"
                  style="margin-left: 10px; font-size:20px"
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
      </el-table>
      <div class="allGoodsFooter">
        <div class="allGoodsFooterLeft">
          <el-checkbox v-model="allChecked">{{$t('allGoods.bottomOptions.allCheck')}}</el-checkbox>
          <el-button
            type="primary"
            plain
            size="small"
            @click="handleToClickBottomBtn(0)"
          >{{initFilterData.isOnSale ===1 ? '下架':'上架'}}</el-button>
          <el-button
            type="primary"
            plain
            size="small"
            @click="handleToClickBottomBtn(1)"
          >{{$t('allGoods.bottomOptions.delete')}}</el-button>
          <el-button
            type="primary"
            plain
            size="small"
            @click="handleToClickBottomBtn(2)"
          >{{$t('allGoods.bottomOptions.batchSetup')}}</el-button>
          <el-select
            v-model="batchExportVal"
            size="small"
          >
            <el-option
              v-for="item in batchExportOptions_"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>

        <pagination
          :page-params.sync="pageParams"
          @pagination="paginationFetchGoodsData"
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
          >{{$t('allGoods.bottomOptions.downloadCode')}}</a>
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
      <!--通用标签-->
      <div
        v-if="goodsLabelData.goodsNormalLabels.length>0"
        style="display: flex;margin-top: 10px;"
      >
        <div style="width:75px;flex-shrink:0;">通用标签：</div>
        <div class="labelSelectedWrapPanel">
          <div
            class="labelSelectedWrap"
            v-for="(item,index) in goodsLabelData.goodsNormalLabels"
            :key="index"
          >
            {{item.name}}
          </div>
        </div>
      </div>
      <!--指定标签-->
      <div
        v-if="goodsLabelData.labelSelectedOptions.length>0"
        style="display: flex;margin-top: 10px;"
      >
        <div style="width:75px;flex-shrink:0;">指定标签：</div>
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
      >{{isBottomClickIndex===0?'确认操作吗?':isBottomClickIndex===1?'确认要删除已选商品吗?':isBottomClickIndex===2?`根据以下条件筛选出${screenNum}条数据,是否确认导出？`:`根据以下条件筛选出${checkScreenNum}条数据,是否确认导出？`}}</div>
      <div
        style="margin-top:10px"
        v-if="isBottomClickIndex===2 || isBottomClickIndex===3"
      >筛选条件：{{Object.keys(setupCondition).length!=0?'':'无'}}</div>
      <div v-if="Object.keys(setupCondition).length!=0">
        <div
          v-for="(item,key,index) in setupCondition"
          :key="index"
          style="margin-top: 10px;"
        >
          <div v-if="ok(key,item)">
            <div>{{$t('allGoods.allGoodsHeaderInputLabel.'+key)}}:{{item}}</div>
          </div>
        </div>
      </div>
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
    <BatchSetupDialog
      :checkGoodsData="nowCheckAll"
      :dialogVisible.sync="batchSetupVisible"
    />
    <!--多规格商品数量修改交互弹窗-->
    <el-dialog
      title="商品规格"
      :visible.sync="goodsNumEditDialogShow"
      width="40%"
    >
      <el-table
        :data="goodsPrdInfos"
        style="width: 100%"
        height="300"
        class="tableClass"
        border
      >
        <!--规格名称-->
        <el-table-column
          label="规格名称"
          align="center"
        >
          <template slot-scope="{row}">
            {{row.prdDesc.replace(';',' ')}}
          </template>
        </el-table-column>

        <el-table-column
          label="库存"
          align="center"
        >
          <template slot-scope="{row}">
            <el-input-number
              v-model="row.prdNumber"
              step-strictly
              size="small"
              controls-position="right"
              :min="0"
              style="width:170px;"
            />
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer">
        <el-button @click="goodsNumEditDialogCancel">取 消</el-button>
        <el-button
          @click="goodsNumEditDialogConfirm"
          type="primary"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { download } from '@/util/excelUtil.js'
import { goodsExport } from '@/api/admin/goodsManage/allGoods/allGoods.js'
import { getGoodsList, deleteGoodsById, deleteGoods, batchOperateSpecPrdPriceNumber, batchOperateGoods, updateLabelByGoodsId, getGoodsFilterItem, updateGoodsPrdNumbers, getGoodsPrdInfo } from '@/api/admin/goodsManage/allGoods/allGoods'
import { getGoodsQrCode } from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
// 组件导入
import pagination from '@/components/admin/pagination/pagination'
import goodsExportConfirmDialog from './goodsExportConfirmDialog'

export default {
  name: 'saleOnAndInStock',
  props: ['initFilterData'],
  components: {
    pagination,
    goodsExportConfirmDialog,
    BatchSetupDialog: () => import('./batchSetupDialog') // 批量设置弹窗
  },
  data () {
    return {
      goodDefaultImgUrl: 'image/admin/medicalGodsDefault.jpg',
      setupCondition: {}, // 批量处理筛选条件
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
        isShow: false,
        goodsNormalLabels: []
      },
      showExportConfirm: false,
      allChecked: false, // 全选checkbox flag
      isDataCheckChange: false, // 是否是因为当前页数据改变而影响的allChecked
      batchExportVal: '0',
      bottomDialogVisible: false, // 底部点击弹窗flag
      isBottomClickIndex: 0, // 底部按钮点击flag
      nowCheckAll: [], // 当前选中的总数
      batchSetupVisible: false, // 批量设置弹窗flag
      exportRowEnd: null, // 导出的商品数量
      screenNum: '', // 筛选得数量
      checkScreenNum: '', // 根据已勾选查询的筛选数量
      /* 多规格商品数量修改弹窗 */
      goodsNumEditDialogShow: false,
      goodsPrdInfos: [{ prdId: 1, prdDesc: 'color:red;df:xl', prdNumber: 12 }], // 商品规格信息
      goodsNumCurEditRow: null // 当前修改商品数量的商品
    }
  },
  computed: {
    batchExportOptions_ () {
      return this.$t('allGoods.bottomOptions.batchExportOptions')
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
        this.showExportConfirm = true
        this.isBottomClickIndex = 2 // 当前选中批量导出筛选的件商品
      } else if (newData === '2') {
        let flag = this.handleToJudgeIsChecked()
        if (!flag) {
          this.$message.warning({
            message: this.$t('allGoods.bottomOptions.selectpProduct'),
            showClose: true
          })
          return
        }
        // 查询筛选数量
        let arr = []
        this.nowCheckAll.forEach((item, index) => {
          arr.push(item.goodsId)
        })
        this.filterData.goodsIds = arr

        this.showExportConfirm = true
        this.isBottomClickIndex = 3 // 当前选中的批量导出勾选结果
      }
    },
    lang () {
      this.paginationFetchGoodsData()
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
      if (typeof row.shopPriceOld !== 'number' || row.shopPriceOld < 0) {
        row.shopPriceOld = row.shopPrice
        this.$message.warning({ type: 'warning', message: this.$t('allGoods.allGoodsData.shopPriceRequired') })
        row.shopPriceEdit = false
        return
      }
      let originPrice = row.shopPrice
      row.shopPrice = row.shopPriceOld

      let param = {
        prdId: row.goodsSpecProducts[0].prdId,
        shopPrice: row.shopPrice
      }

      batchOperateSpecPrdPriceNumber(param).then(res => {
        row.shopPriceEdit = false
        if (res.error === 0) {
          this.$message.success({ type: 'info', message: this.$t('allGoods.allGoodsData.setSuccess') })
        } else {
          row.shopPrice = originPrice
        }
      })
    },
    /* 商品数量输入框处理函数 */
    goodsNumberChange (row, index) {
      if (typeof row.goodsNumberOld !== 'number' || row.goodsNumberOld < 0) {
        row.goodsNumberOld = row.goodsNumber
        this.$message.warning({ type: 'warning', message: this.$t('allGoods.allGoodsData.goodsNumberRequired') })
        row.goodsNumberEdit = false
        return
      }
      let originNum = row.goodsNumber
      row.goodsNumber = parseInt(row.goodsNumberOld)

      let param = {
        prdId: row.prdId,
        goodsNumber: row.goodsNumber
      }

      batchOperateSpecPrdPriceNumber(param).then(res => {
        row.goodsNumberEdit = false
        if (res.error === 0) {
          this.$message.success({ type: 'info', message: this.$t('allGoods.allGoodsData.setSuccess') })
          if (row.goodsNumber === 0) {
            this.goodsData.splice(index, 1)
          }
        } else {
          row.goodsNumber = originNum
        }
      })
    },
    /* 多规格商品数量修改 */
    goodsNumEditDialogCancel () {
      this.goodsNumEditDialogShow = false
      this.goodsNumCurEditRow = null
    },
    goodsNumEditDialogConfirm () {
      let prdNumInfos = []
      let goodsNum = 0
      this.goodsPrdInfos.forEach(item => {
        prdNumInfos.push({ prdId: item.prdId, prdNumber: item.prdNumber })
        goodsNum += item.prdNumber
      })

      let numParam = {
        goodsId: this.goodsNumCurEditRow.goodsId,
        prdNumInfos: prdNumInfos
      }

      updateGoodsPrdNumbers(numParam).then(res => {
        if (res.error === 0) {
          this.$message.success({ type: 'info', message: this.$t('allGoods.allGoodsData.setSuccess') })
          this.goodsNumCurEditRow.goodsNumber = goodsNum
          this.goodsNumEditDialogShow = false
        } else {
          this.$message.error({ type: 'error', message: res.message })
        }
      })
    },
    goodsNumEditClick (row) {
      this.goodsNumCurEditRow = row
      getGoodsPrdInfo(row.goodsId).then(res => {
        this.goodsPrdInfos = res.content
        this.goodsNumEditDialogShow = true
      })
    },
    /** table表单内标签 **/
    /* table表单内标签设置按钮 */
    tdLabelSetClick (row) {
      getGoodsFilterItem({ needGoodsLabel: true }).then(res => {
        const { content: { goodsLabels } } = res
        this.goodsLabelData.currentRow = row
        this.goodsLabelData.goodsNormalLabels = row.goodsNormalLabels
        this.goodsLabelData.isShow = true
        goodsLabels.forEach(item => {
          if (this.goodsLabelData.currentRow.goodsPointLabels.some(goodsLabel => goodsLabel.id === item.id)) {
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
      this.goodsLabelData.labelSelectedOptions = this.goodsLabelData.labelSelectedOptions.slice(0, 5)
      this.goodsLabelData.labelSelectedOptions.forEach(item => param.labelIds.push(item.id))
      updateLabelByGoodsId(param).then((res) => {
        if (res.error !== 0) {
          return
        }
        this.goodsLabelData.currentRow.goodsPointLabels = this.goodsLabelData.labelSelectedOptions
        console.log(this.goodsLabelData.currentRow)
        this.goodsLabelData.labelSelectedOptions = []
        this.goodsLabelData.labelSelectOptions = []
        this.goodsLabelData.goodsNormalLabels = []
        this.goodsLabelData.currentRow = null
        this.$message.success({ type: 'info', message: this.$t('allGoods.allGoodsData.setSuccess') })
        this.goodsLabelData.isShow = false
      })
    },
    goodsLabelDialogCancel () {
      this.goodsLabelData.labelSelectedOptions = []
      this.goodsLabelData.labelSelectOptions = []
      this.goodsLabelData.goodsNormalLabels = []
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
        return deleteGoodsById(row.goodsId).then((res) => {
          if (res.error === 0) {
            this.fetchGoodsData(this.filterData)
          }
        })
      })
    },
    /* 下架图标按钮点击 */
    withdrawIconClick (row, index) {
      this._$confirm(this.$t('allGoods.allGoodsData.underCarriageTipMsg'), this.$t('allGoods.allGoodsData.underCarriageOk'), () => {
        return batchOperateGoods({ goodsIds: [row.goodsId], isOnSale: 0 }).then((res) => {
          this.fetchGoodsData(this.filterData)
        })
      })
    },
    /* 上架图标按钮点击 */
    upIconClick (row, index) {
      this._$confirm(this.$t('allGoods.allGoodsData.upCarriageTipMsg'), this.$t('allGoods.allGoodsData.upCarriageOk'), () => {
        return batchOperateGoods({ goodsIds: [row.goodsId], isOnSale: 1 }).then((res) => {
          if (res.error === 0) {
            this.fetchGoodsData(this.filterData)
          }
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
    /* 分页组件使用的分页方法，为了传递filterData数据 */
    paginationFetchGoodsData () {
      this.fetchGoodsData(this.filterData)
    },
    ok (key, item) {
      if (Array.isArray(item)) {
        if (item.length !== 0) return true
      } else {
        if (key === 'currentPage' || key === 'pageRows' || key === 'exportRowStart' || key === 'exportRowEnd') {
          return false
        }
        if (item) {
          if (item === '请选择平台分类') {
            return false
          } else {
            return true
          }
        }
      }
      return false
    },
    /* 分页查询数据方法 */
    fetchGoodsData (filterData, formFilterDataString) {
      if (filterData !== undefined) {
        this.filterData = filterData
      }
      let params = {
        ...this.pageParams,
        ...this.filterData
      }
      if (formFilterDataString) {
        Object.keys(formFilterDataString).forEach((item, index) => {
          if (!formFilterDataString[item]) {
            delete formFilterDataString[item]
          }
        })
        this.setupCondition = formFilterDataString
      }
      console.log(formFilterDataString)
      getGoodsList(params).then(res => {
        let { content: { page, dataList } } = res

        this.pageParams = page
        this.batchExportOptions_[1].label = this.$t('allGoods.bottomOptions.batchFiltered') + this.pageParams.totalRows + this.$t('allGoods.bottomOptions.commodity')
        dataList.forEach(item => {
          // item.sourceName = item.source === 0 ? '自营' : '非自营'
          // item.sourceName = item.source === 0 ? this.$t('allGoods.allGoodsHeaderData.goodsSourceOptions')[1] : this.$t('allGoods.allGoodsHeaderData.goodsSourceOptions')[2]

          // switch (item.goodsType) {
          //   // case 1: item.goodsTypeName = '拼团商品'
          //   case 1: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[0]
          //     break
          //   // case 3: item.goodsTypeName = '砍价'
          //   case 3: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[1]
          //     break
          //   // case 5: item.goodsTypeName = '秒杀'
          //   case 5: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[2]
          //     break
          //   // case 6: item.goodsTypeName = '限时降价'
          //   case 6: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[3]
          //     break
          //   // case 10: item.goodsTypeName = '预售'
          //   case 10: item.goodsTypeName = this.$t('allGoods.allGoodsData.goodsType')[4]
          //     break
          //   default:
          //     item.goodsTypeName = null
          // }

          item.shopPriceEdit = false
          item.shopPriceOld = item.shopPrice
          item.goodsNumberEdit = false
          item.goodsNumberOld = item.goodsNumber
          item.check = false

          if (!item.isDefaultProduct) {
            let skus = item.goodsSpecProducts
            let minShopPrice = skus[0].prdPrice
            let maxShopPrice = skus[0].prdPrice
            for (let sku of skus) {
              if (sku.prdPrice < minShopPrice) {
                minShopPrice = sku.prdPrice
              }
              if (sku.prdPrice > maxShopPrice) {
                maxShopPrice = sku.prdPrice
              }
            }
            item.minShopPrice = minShopPrice
            item.maxShopPrice = maxShopPrice
          }
        })
        this.$set(this, 'goodsData', dataList)
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
          message: this.$t('allGoods.bottomOptions.noItemSelected'),
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
          this.nowCheckAll.forEach((item, index) => {
            arr.push(item.goodsId)
          })
          batchOperateGoods({ goodsIds: arr, isOnSale: this.initFilterData.isOnSale === 1 ? 0 : 1 }).then((res) => {
            if (res.error === 0) {
              this.fetchGoodsData(this.filterData)
            }
          })
          break
        case 1:
          this.nowCheckAll.forEach((item, index) => {
            arr.push(item.goodsId)
          })
          deleteGoods({ goodsIds: arr }).then((res) => {
            if (res.error === 0) {
              console.log(res)
              this.fetchGoodsData(this.filterData)
            }
          })
          break
        case 2:
          this.filterData.exportRowStart = 1
          this.filterData.exportRowEnd = this.screenNum
          console.log(this.filterData)
          goodsExport(this.filterData).then(res => {
            console.log(res)
            let fileName = localStorage.getItem('V-content-disposition')
            fileName = fileName.split(';')[1].split('=')[1]
            download(res, decodeURIComponent(fileName))
          })
          break
        case 3:
          let arr1 = []
          this.nowCheckAll.forEach((item, index) => {
            arr1.push(item.goodsId)
          })
          let params = {}
          params = Object.assign(this.filterData, { goodsIds: arr1 })
          this.filterData.exportRowEnd = this.checkScreenNum
          goodsExport(params).then(res => {
            console.log(res)
            let fileName = localStorage.getItem('V-content-disposition')
            fileName = fileName.split(';')[1].split('=')[1]
            download(res, decodeURIComponent(fileName))
          })
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
@import '@/assets/aliIcon/iconfont.scss';
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
  text-align: left;
}
.nameImgWrap::after {
  content: '';
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
}
.labelSelectedWrapPanel {
  width: 80%;
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
  margin-bottom: 10px;
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
.iconfont {
  font-size: 22px;
  color: #5a8bff;
}
</style>
