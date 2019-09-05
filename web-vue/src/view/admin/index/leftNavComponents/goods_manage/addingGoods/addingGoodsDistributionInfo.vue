<template>
  <div>
    <el-form ref="goodsDistributionInfoForm" :model="goodsDistributionInfo" label-width="120px">
      <el-form-item label="分销改价：">
        <el-checkbox v-model="goodsDistributionInfo.canRebate">允许分销员分销商品时修改商品售价</el-checkbox>
        <template v-if="goodsDistributionInfo.canRebate">
          <div v-if="goodsProductInfoData.specInfoSwitch" style="border: 1px solid #ccc;padding: 10px;">
            <table>
              <tr>
                <th></th>
                <th>规格价格(元)</th>
                <th>建议售价(元)</th>
                <th>最低售价(元)</th>
                <th>最高售价（元）</th>
              </tr>
              <tr
                v-for="(item,index) in goodsDistributionInfo.goodsRebatePrices"
                :key="index"
              >
                <td>{{item.prdDescTemp}}</td>
                <td>{{item.prdPrice}}</td>
                <td><input type="text" v-model.number="item.advisePrice"/></td>
                <td><input type="text" v-model.number="item.minPrice"/></td>
                <td><input type="text" v-model.number="item.maxPrice"/></td>
              </tr>
            </table>
            <p style="text-align: right;line-height: 10px;margin-top: 20px;">
              <span style="font-size: 14px;">批量设置：</span>
              <el-link size="small" :underline="false" @click="setLowestPrice">最低售价</el-link>
              <el-link size="small" :underline="false" @click="setHighestPrice">最高售价</el-link>
            </p>
          </div>
          <div v-else style="border: 1px solid #ccc;padding: 10px;">
            <table>
              <tr>
                <th>商品价格(元)</th>
                <th>建议售价(元)</th>
                <th>最低售价(元)</th>
                <th>最高售价（元）</th>
              </tr>
              <tr
                v-for="(item,index) in goodsDistributionInfo.goodsRebatePrices"
                :key="index"
              >
                <td>{{item.prdPrice}}</td>
                <td><input type="text" v-model.number="item.advisePrice"/></td>
                <td><input type="text" v-model.number="item.minPrice"/></td>
                <td><input type="text" v-model.number="item.maxPrice"/></td>
              </tr>
            </table>
          </div>
        </template>
      </el-form-item>
      <el-form-item label="分销推广语：">
        <el-switch v-model="goodsDistributionInfo.promotionLanguageSwitch" />
        <span>{{goodsDistributionInfo.promotionLanguageSwitch?'已开启':'已关闭'}}</span>
        <span style="color: #999;margin-left: 10px;">分销员下载当前商品海报时将直接复制此推广语到手机剪贴板</span>
        <div v-show="goodsDistributionInfo.promotionLanguageSwitch" style="display: flex;align-items: flex-start">
          <span style="line-height: 20px;">推广语内容：</span>
          <el-input ref="promotionLanguageInput" type="textarea" v-model="goodsDistributionInfo.promotionLanguage" placeholder="请输入" resize="none" :maxlength="200" show-word-limit :rows="8" style="width:400px;"/>
        </div>
      </el-form-item>
      <el-form-item label="商品分享海报：">
        <!--默认样式-->
        <div>
          <el-radio v-model="goodsDistributionInfo.shareAction" :label="1" style="margin-right: 15px;">默认样式</el-radio>
          <el-popover placement="right-start" trigger="hover">
            <el-image :src="goodsDistributionInfo.imgHost+'/image/admin/share/goods_info_exapmle1.jpg'" fit="scale-down" style="width:220px;height: 400px;"/>
            <span slot="reference" style="color:#409EFF;cursor:pointer;">查看示例</span>
          </el-popover>
          <el-popover placement="right-start" trigger="hover" style="margin-left: 10px;">
            <el-image :src="goodsDistributionInfo.imgHost+'/image/admin/share/goods_info_exapmle.jpg'" fit="scale-down" style="width:220px;height: 400px;"/>
            <span slot="reference" style="color:#409EFF;cursor:pointer;">下载海报</span>
          </el-popover>
        </div>
        <!--自定义样式-->
        <div>
          <el-radio v-model="goodsDistributionInfo.shareAction" :label="2" style="margin-right: 15px;">自定义样式</el-radio>
        </div>
        <!--文案-->
        <div>
          <span style="width:70px;display:inline-block;">文案：</span>
          <el-input ref="shareDocInput" v-model="goodsDistributionInfo.shareDoc" :disabled="goodsDistributionInfo.shareAction === 1" size="small" placeholder="请输入15个以内的字符" style="width:220px;"/>
        </div>
        <!--分享图-->
        <div>
          <span style="width:70px;display:inline-block;">分享图：</span>
          <el-radio v-model="goodsDistributionInfo.shareImgAction" :label="1" :disabled="goodsDistributionInfo.shareAction === 1" style="margin-right: 15px;">商品主图</el-radio>
        </div>
        <div>
          <span style="width:70px;display:inline-block;"></span>
          <el-radio v-model="goodsDistributionInfo.shareImgAction" :label="2" :disabled="goodsDistributionInfo.shareAction === 1" style="margin-right: 15px;">自定义图片</el-radio>
        </div>
        <div>
          <span style="width:70px;display:inline-block;"></span>
          <el-image v-if="goodsDistributionInfo.shareImgUrl === null" @click="addGoodsImg"  fit="scale-down" :src="goodsDistributionInfo.imgHost+'/image/admin/add_img.png'" style="width: 78px; height: 78px;cursor: pointer;border: 1px solid #ccc;"/>
          <el-image v-else @click="addGoodsImg" fit="cover" :src="$imageHost+'/'+goodsDistributionInfo.shareImgUrl" style="width: 78px; height: 78px;cursor: pointer;border: 1px solid #ccc;"/>
        </div>
      </el-form-item>
    </el-form>
    <!--解决图片弹框非单例的问题-->
    <ImageDalog v-if="stepData.currentStep === 3"
      pageIndex='pictureSpace'
      @handleSelectImg='imgDialogSelectedCallback'
    />
  </div>
</template>

<script>
// 组件导入
import ImageDalog from '@/components/admin/imageDalog'
// js工具函数导入
import { isStrBlank } from '@/util/goodsUtil'

export default {
  name: 'addingGoodsDistributionInfo',
  props: ['goodsProductInfoData'],
  inject:['stepData'],
  components: {
    ImageDalog
  },
  watch: {
    'goodsProductInfoData.goodsSpecProducts': function (goodsSpecProducts) {
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
    }
  },
  data () {
    return {
      goodsDistributionInfo: {
        canRebate: false,
        goodsRebatePrices: [],
        promotionLanguageSwitch: false,
        promotionLanguage: null,
        shareAction: 1,
        shareDoc: null,
        shareImgAction: 1,
        shareImgUrl: null,
        imgHost: `${this.$imageHost}`
      }
    }
  },
  methods: {
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
      console.log("currentStep")
      console.log(this.stepData)
      if (this.goodsDistributionInfo.shareAction !== 2 || this.goodsDistributionInfo.shareImgAction !== 2) {
        return
      }

      this.$http.$emit('dtVisible')
    },
    /* 添加图片点击回调事件 */
    imgDialogSelectedCallback (imgObj) {
      this.goodsDistributionInfo.shareImgUrl = imgObj.imgPath
    },
    /* 验证数据是否全部合法 */
    validateFormData () {
      // 分销推广语长度超长
      if (this.goodsDistributionInfo.promotionLanguageSwitch && !isStrBlank(this.goodsDistributionInfo.promotionLanguage) &&
        this.goodsDistributionInfo.promotionLanguage.length > 200) {
        this.$message({message: '推广语超长', type: 'warning'})
        this.$refs.promotionLanguageInput.focus()
        return false
      }

      if (this.goodsDistributionInfo.shareAction === 2 && !isStrBlank(this.goodsDistributionInfo.shareDoc) &&
        this.goodsDistributionInfo.shareDoc.length > 15) {
        this.$message({message: '文档内容过长', type: 'warning'})
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

      this.goodsDistributionInfo.goodsRebatePrices.forEach(item => {
        retData.goodsRebatePrices.push({
          prdDesc: item.prdDesc,
          advisePrice: item.advisePrice,
          minPrice: item.minPrice,
          maxPrice: item.maxPrice
        })
      })

      retData.goodsSharePostConfig.shareAction = this.goodsDistributionInfo.shareAction
      retData.goodsSharePostConfig.shareDoc = this.goodsDistributionInfo.shareDoc
      retData.goodsSharePostConfig.shareImgAction = this.goodsDistributionInfo.shareImgAction
      retData.goodsSharePostConfig.shareImgUrl = this.goodsDistributionInfo.shareImgUrl

      return retData
    }
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
