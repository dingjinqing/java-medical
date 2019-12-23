<template>
  <div>
    <el-form
      ref="goodsDistributionInfoForm"
      :model="goodsDistributionInfo"
      label-width="120px"
    >
      <el-form-item :label="$t('goodsAddEditInfo.goodsDistribution.distributionTitle')">
        <el-checkbox v-model="goodsDistributionInfo.canRebate">{{$t('goodsAddEditInfo.goodsDistribution.distributionTitleTip')}}</el-checkbox>
        <template v-if="goodsDistributionInfo.canRebate">
          <div
            v-if="goodsProductInfoData.specInfoSwitch"
            style="border: 1px solid #ccc;padding: 10px;"
          >
            <table>
              <tr>
                <th></th>
                <th>{{$t('goodsAddEditInfo.goodsDistribution.goodsPrice')}}</th>
                <th>{{$t('goodsAddEditInfo.goodsDistribution.advicePrice')}}</th>
                <th>{{$t('goodsAddEditInfo.goodsDistribution.minPrice')}}</th>
                <th>{{$t('goodsAddEditInfo.goodsDistribution.maxPrice')}}</th>
              </tr>
              <tr
                v-for="(item,index) in goodsDistributionInfo.goodsRebatePrices"
                :key="index"
              >
                <td>{{item.prdDescTemp}}</td>
                <td>{{item.prdPrice}}</td>
                <td><input
                    type="text"
                    v-model.number="item.advisePrice"
                  /></td>
                <td><input
                    type="text"
                    v-model.number="item.minPrice"
                  /></td>
                <td><input
                    type="text"
                    v-model.number="item.maxPrice"
                  /></td>
              </tr>
            </table>
            <p style="text-align: right;line-height: 10px;margin-top: 20px;">
              <span style="font-size: 14px;">{{$t('goodsAddEditInfo.goodsDistribution.batchSetting')}}</span>
              <el-link
                size="small"
                :underline="false"
                @click="setLowestPrice"
              >{{$t('goodsAddEditInfo.goodsDistribution.lowestSetting')}}</el-link>
              <el-link
                size="small"
                :underline="false"
                @click="setHighestPrice"
              >{{$t('goodsAddEditInfo.goodsDistribution.highestSetting')}}</el-link>
            </p>
          </div>
          <div
            v-else
            style="border: 1px solid #ccc;padding: 10px;"
          >
            <table>
              <tr>
                <th>{{$t('goodsAddEditInfo.goodsDistribution.goodsPrice')}}</th>
                <th>{{$t('goodsAddEditInfo.goodsDistribution.advicePrice')}}</th>
                <th>{{$t('goodsAddEditInfo.goodsDistribution.minPrice')}}</th>
                <th>{{$t('goodsAddEditInfo.goodsDistribution.maxPrice')}}</th>
              </tr>
              <tr
                v-for="(item,index) in goodsDistributionInfo.goodsRebatePrices"
                :key="index"
              >
                <td>{{item.prdPrice}}</td>
                <td><input
                    type="text"
                    v-model.number="item.advisePrice"
                  /></td>
                <td><input
                    type="text"
                    v-model.number="item.minPrice"
                  /></td>
                <td><input
                    type="text"
                    v-model.number="item.maxPrice"
                  /></td>
              </tr>
            </table>
          </div>
        </template>
      </el-form-item>
      <el-form-item :label="$t('goodsAddEditInfo.goodsDistribution.promotionTitle')">
        <el-switch v-model="goodsDistributionInfo.promotionLanguageSwitch" />
        <span>{{goodsDistributionInfo.promotionLanguageSwitch?$t('goodsAddEditInfo.goodsDistribution.promotionSwitchOn'):$t('goodsAddEditInfo.goodsDistribution.promotionSwitchOff')}}</span>
        <span style="color: #999;margin-left: 10px;">{{$t('goodsAddEditInfo.goodsDistribution.promotionTitleTip')}}</span>
        <div
          v-show="goodsDistributionInfo.promotionLanguageSwitch"
          style="display: flex;align-items: flex-start"
        >
          <span style="line-height: 20px;">{{$t('goodsAddEditInfo.goodsDistribution.promotionLanguage')}}</span>
          <el-input
            ref="promotionLanguageInput"
            type="textarea"
            v-model="goodsDistributionInfo.promotionLanguage"
            resize="none"
            :maxlength="200"
            show-word-limit
            :rows="8"
            style="width:400px;"
          />
        </div>
      </el-form-item>
      <el-form-item :label="$t('goodsAddEditInfo.goodsDistribution.shareGoodsAction')">
        <!--默认样式-->
        <div>
          <el-radio
            v-model="goodsDistributionInfo.shareAction"
            :label="1"
            style="margin-right: 15px;"
          >{{$t('goodsAddEditInfo.goodsDistribution.shareGoodsAction')}}</el-radio>
          <el-popover
            placement="right-start"
            trigger="hover"
          >
            <el-image
              :src="goodsDistributionInfo.imgHost+'/image/admin/share/goods_info_exapmle1.jpg'"
              fit="scale-down"
              style="width:220px;height: 400px;"
            />
            <span
              slot="reference"
              style="color:#409EFF;cursor:pointer;"
            >{{$t('goodsAddEditInfo.goodsDistribution.shareGoodsImgLinkLook')}}</span>
          </el-popover>
          <el-popover
            placement="right-start"
            trigger="hover"
            style="margin-left: 10px;"
          >
            <el-image
              :src="goodsDistributionInfo.imgHost+'/image/admin/share/goods_info_exapmle.jpg'"
              fit="scale-down"
              style="width:220px;height: 400px;"
            />
            <span
              slot="reference"
              style="color:#409EFF;cursor:pointer;"
            >{{$t('goodsAddEditInfo.goodsDistribution.shareGoodsImgLinkDownload')}}</span>
          </el-popover>
        </div>
        <!--自定义样式-->
        <div>
          <el-radio
            v-model="goodsDistributionInfo.shareAction"
            :label="2"
            style="margin-right: 15px;"
          >{{$t('goodsAddEditInfo.goodsDistribution.shareGoodsActionRadio2')}}</el-radio>
        </div>
        <!--文案-->
        <div>
          <span style="width:70px;display:inline-block;">{{$t('goodsAddEditInfo.goodsDistribution.shareGoodsDoc')}}</span>
          <el-input
            ref="shareDocInput"
            v-model="goodsDistributionInfo.shareDoc"
            :disabled="goodsDistributionInfo.shareAction === 1"
            size="small"
            :placeholder="$t('goodsAddEditInfo.goodsDistribution.shareGoodsDocTip')"
            style="width:220px;"
          />
        </div>
        <!--分享图-->
        <div>
          <span style="width:70px;display:inline-block;">{{$t('goodsAddEditInfo.goodsDistribution.shareGoodsImg')}}</span>
          <el-radio
            v-model="goodsDistributionInfo.shareImgAction"
            :label="1"
            :disabled="goodsDistributionInfo.shareAction === 1"
            style="margin-right: 15px;"
          >{{$t('goodsAddEditInfo.goodsDistribution.shareGoodsImgRadio1')}}</el-radio>
        </div>
        <div>
          <span style="width:70px;display:inline-block;"></span>
          <el-radio
            v-model="goodsDistributionInfo.shareImgAction"
            :label="2"
            :disabled="goodsDistributionInfo.shareAction === 1"
            style="margin-right: 15px;"
          >{{$t('goodsAddEditInfo.goodsDistribution.shareGoodsImgRadio2')}}</el-radio>
        </div>
        <div>
          <span style="width:70px;display:inline-block;"></span>
          <el-image
            v-if="goodsDistributionInfo.shareImgObj === null"
            @click="addGoodsImg"
            fit="scale-down"
            :src="goodsDistributionInfo.imgHost+'/image/admin/add_img.png'"
            style="width: 78px; height: 78px;cursor: pointer;border: 1px solid #ccc;"
          />
          <el-image
            v-else
            @click="addGoodsImg"
            fit="cover"
            :src="goodsDistributionInfo.shareImgObj.imgUrl"
            style="width: 78px; height: 78px;cursor: pointer;border: 1px solid #ccc;"
          />
        </div>
      </el-form-item>
    </el-form>
    <!--解决图片弹框非单例的问题-->
    <ImageDalog
      :tuneUp="selfImgDialogShow"
      pageIndex='pictureSpace'
      @handleSelectImg='imgDialogSelectedCallback'
    />
  </div>
</template>

<script>
// 组件导入
import ImageDalog from '@/components/admin/imageDalog'
// js工具函数导入
import { isStrBlank } from '@/util/typeUtil'

export default {
  name: 'addingGoodsDistributionInfo',
  props: ['goodsProductInfoData'],
  inject: ['isUpdateWrap'],
  components: {
    ImageDalog
  },
  data () {
    return {
      selfImgDialogShow: false,
      goodsDistributionInfo: {
        canRebate: false,
        goodsRebatePrices: [],
        updateGoodsRebatePrices: null,
        promotionLanguageSwitch: false,
        promotionLanguage: null,
        shareAction: 1,
        shareDoc: null,
        shareImgAction: 1,
        shareImgObj: null,
        imgHost: `${this.$imageHost}`
      }
    }
  },
  watch: {
    'goodsProductInfoData.goodsSpecProducts': function (goodsSpecProducts) {
      // 初始化规格数据
      this._watchGoodsProductInfoDataGoodsSpecProducts(goodsSpecProducts)
    }
  },
  methods: {
    _watchGoodsProductInfoDataGoodsSpecProducts (goodsSpecProducts) {
      let tempData = []
      goodsSpecProducts.forEach(item => {
        let isHas = this.goodsDistributionInfo.goodsRebatePrices.some(innerItem => {
          if (innerItem.tempId === item.tempId) {
            innerItem.prdDesc = item.prdDesc
            innerItem.prdDescTemp = item.prdDescTemp
            tempData.push(innerItem)
            return true
          } else {
            return false
          }
        })

        if (!isHas) {
          tempData.push({
            prdId: item.prdId,
            tempId: item.tempId,
            prdDesc: item.prdDesc,
            prdDescTemp: item.prdDescTemp,
            prdPrice: item.prdPrice,
            advisePrice: null,
            minPrice: null,
            maxPrice: null
          })
        }
      })
      this.goodsDistributionInfo.goodsRebatePrices = tempData

      if (this.isUpdateWrap.isUpdate) {
        this.goodsDistributionInfo.goodsRebatePrices.forEach(item => {
          let temp = this.goodsDistributionInfo.updateGoodsRebatePrices.filter(updateItem => updateItem.productId === item.prdId)
          if (temp.length > 0) {
            item.advisePrice = temp[0].advisePrice
            item.minPrice = temp[0].minPrice
            item.maxPrice = temp[0].maxPrice
          }
        })
      }
    },
    setLowestPrice () {
      let minPrice = this.goodsDistributionInfo.goodsRebatePrices[0].minPrice
      this.goodsDistributionInfo.goodsRebatePrices.forEach(item => { item.minPrice = minPrice })
    },
    setHighestPrice () {
      let maxPrice = this.goodsDistributionInfo.goodsRebatePrices[0].maxPrice
      this.goodsDistributionInfo.goodsRebatePrices.forEach(item => { item.maxPrice = maxPrice })
    },
    /* 添加图片点击事件，弹出图片选择组件 */
    addGoodsImg () {
      if (this.goodsDistributionInfo.shareAction !== 2 || this.goodsDistributionInfo.shareImgAction !== 2) {
        return
      }
      this.selfImgDialogShow = !this.selfImgDialogShow
      this.$nextTick(() => this.$http.$emit('dtVisible'))
    },
    /* 添加图片点击回调事件 */
    imgDialogSelectedCallback (imgObj) {
      this.goodsDistributionInfo.shareImgObj = { imgPath: imgObj.imgPath, imgUrl: imgObj.imgUrl }
    },
    /* 初始化待修改商品数据 */
    initDataForUpdate (goodsData) {
      this.goodsDistributionInfo.canRebate = goodsData.canRebate === 1
      // 缓存分销数据，在用户点击到第三步的时候再进行分销数据的回显处理
      this.goodsDistributionInfo.updateGoodsRebatePrices = goodsData.goodsRebatePrices
      // 分销推广语按钮初始化
      this.goodsDistributionInfo.promotionLanguageSwitch = goodsData.promotionLanguageSwitch === 1
      // 分销推广语初始化
      this.goodsDistributionInfo.promotionLanguage = goodsData.promotionLanguage
      // 海报分享样式初始化
      this.goodsDistributionInfo.shareAction = goodsData.goodsSharePostConfig.shareAction
      this.goodsDistributionInfo.shareDoc = goodsData.goodsSharePostConfig.shareDoc
      this.goodsDistributionInfo.shareImgAction = goodsData.goodsSharePostConfig.shareImgAction
      if (this.goodsDistributionInfo.shareAction === 2 && this.goodsDistributionInfo.shareImgAction === 2) {
        this.goodsDistributionInfo.shareImgObj = { imgPath: goodsData.goodsSharePostConfig.shareImgPath, imgUrl: goodsData.goodsSharePostConfig.shareImgUrl }
      }
      // 初始化规格数据
      this._watchGoodsProductInfoDataGoodsSpecProducts(goodsData.goodsSpecProducts)
    },
    /* 处理复制操作的数据 */
    disposeDataForCopy () {
      this.goodsDistributionInfo.goodsRebatePrices.forEach(item => {
        item.goodsId = null
        item.productId = null
      })
    },
    /* 新增数据时数据初始化 */
    initDataForInsert () {
    },
    /* 验证数据是否全部合法 */
    validateFormData () {
      // 分销推广语长度超长
      if (this.goodsDistributionInfo.promotionLanguageSwitch && !isStrBlank(this.goodsDistributionInfo.promotionLanguage) &&
        this.goodsDistributionInfo.promotionLanguage.length > 200) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.goodsDistribution.promotionLanguageTooLong'), type: 'warning' })
        this.$refs.promotionLanguageInput.focus()
        return false
      }

      if (this.goodsDistributionInfo.shareAction === 2 && !isStrBlank(this.goodsDistributionInfo.shareDoc) &&
        this.goodsDistributionInfo.shareDoc.length > 15) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.goodsDistribution.promotionDocTooLong'), type: 'warning' })
        this.$refs.shareDocInput.focus()
        return false
      }
      return true
    },
    /* 获取传给后台的表单数据 */
    getFormData () {
      let retData = {
        canRebate: this.goodsDistributionInfo.canRebate ? 1 : 0,
        goodsRebatePrices: [],
        promotionLanguageSwitch: this.goodsDistributionInfo.promotionLanguageSwitch ? 1 : 0,
        promotionLanguage: this.goodsDistributionInfo.promotionLanguage,
        goodsSharePostConfig: {}
      }

      if (this.goodsDistributionInfo.canRebate) {
        this.goodsDistributionInfo.goodsRebatePrices.forEach(item => {
          retData.goodsRebatePrices.push({
            prdDesc: item.prdDesc,
            advisePrice: item.advisePrice,
            minPrice: item.minPrice,
            maxPrice: item.maxPrice
          })
        })
      }

      retData.goodsSharePostConfig.shareAction = this.goodsDistributionInfo.shareAction
      retData.goodsSharePostConfig.shareDoc = this.goodsDistributionInfo.shareDoc
      retData.goodsSharePostConfig.shareImgAction = this.goodsDistributionInfo.shareImgAction
      if (this.goodsDistributionInfo.shareImgObj != null) {
        retData.goodsSharePostConfig.shareImgUrl = this.goodsDistributionInfo.shareImgObj.imgPath
      } else {
        retData.goodsSharePostConfig.shareImgUrl = null
      }
      return retData
    }
  },
  mounted () {
    // 国际化
    this.langDefault()
  }
}
</script>

<style scoped>
/* 以下临时使用，可删除 */
table {
  border-collapse: collapse;
  margin: 0 auto;
  text-align: center;
  width: 100%;
}

table td,
table th {
  border: 1px solid #cad9ea;
  color: #666;
  height: 30px;
}

table thead th {
  background-color: #cce8eb;
  width: 100px;
}

table tr:nth-child(odd) {
  background: #fff;
}

table tr:nth-child(even) {
  background: #f5fafa;
}
</style>
