<template>
  <div style="display: flex;justify-content: center;align-items: flex-start;margin-top: 40px;">
    <div class="phoneWrap">
      <div class="phoneTop">
        <span style="font-weight:800;color:#000;font-size:16px;margin-top:33px;display: inline-block;">{{$t("goodsAddEditInfo.goodsDetail.goodsDetailTitle")}}</span>
      </div>
      <div style="height:550px;overflow-y:auto;overflow-x: auto;background-color:#F5F5F5;">
        <!--商品主图-->
        <div style="height:380px;">
          <img :src="$imageHost+'/'+goodsProductInfoData.goodsImg" style="width:100%;height:100%;"/>
        </div>
        <!--商品信息-->
        <div class="goodsItemWrap">
          <div style="color:#333;font-size:16px;">{{goodsProductInfoData.goodsName}}</div>
          <div style="color:#FF6666;font-size:18px;margin:10px 0;">
            <template v-if="goodsNumAndPriceComputed.goodsPriceMin === goodsNumAndPriceComputed.goodsPriceMax">
              {{$t("goodsAddEditInfo.goodsDetail.priceIcon")}} {{goodsNumAndPriceComputed.goodsPriceMin}}
            </template>
            <template v-else>
              {{$t("goodsAddEditInfo.goodsDetail.priceIcon")}} {{goodsNumAndPriceComputed.goodsPriceMin}} - {{goodsNumAndPriceComputed.goodsPriceMax}}
            </template>
          </div>
          <div style="color:#999;font-size:14px;"> {{$t("goodsAddEditInfo.goodsDetail.phoneNum")}}{{goodsNumAndPriceComputed.goodsNumSum}}
            {{goodsProductInfoData.unit}}
          </div>
        </div>
        <template v-if="goodsDetailInfo.isPageUp === 1">
          <!--商品描述-->
          <div class="goodsItemWrap goodsDrapItem">
            <div style="width: 70px;height: 20px;border-bottom: 1px solid #ccc;margin-bottom: 5px;">{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsDesc")}}</div>
            <div v-if="!isStrBlank(goodsDetailInfo.goodsDesc)" v-html="goodsDetailInfo.goodsDesc" style="word-break:break-all;" ></div>
            <div v-else class="contentTip">
              <p>{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsDescTip1")}}</p>
              <p>{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsDescTip2")}}</p>
            </div>
          </div>
          <!--自定义内容区域-->
          <div class="goodsItemWrap goodsDrapItem">
            <div class="contentTip">
              <template v-if="goodsDetailInfo.goodsPageData === null">
                <p style="font-size: 15px;color:#333;">{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsTempTip1")}}</p>
                <p>{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsTempTip2")}}</p>
                <p>{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsTempTip3")}}</p>
              </template>
              <template v-else>
                <p style="color: #333;">{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsTempTip4")}}{{goodsDetailInfo.goodsPageData.pageName}}</p>
              </template>
            </div>
          </div>
        </template>
        <template v-else>
          <!--自定义内容区域-->
          <div class="goodsItemWrap goodsDrapItem">
            <div class="contentTip">
              <template v-if="goodsDetailInfo.goodsPageData === null">
                <p style="font-size: 15px;color:#333;">{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsTempTip1")}}</p>
                <p>{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsTempTip2")}}</p>
                <p>{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsTempTip3")}}</p>
              </template>
              <template v-else>
                <p style="color: #333;">{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsTempTip4")}}{{goodsDetailInfo.goodsPageData.pageName}}</p>
              </template>
            </div>
          </div>
          <!--商品描述-->
          <div class="goodsItemWrap goodsDrapItem">
            <div style="width: 70px;height: 20px;border-bottom: 1px solid #ccc;margin-bottom: 5px;">{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsDesc")}}</div>
            <div v-if="!isStrBlank(goodsDetailInfo.goodsDesc)" v-html="goodsDetailInfo.goodsDesc" style="word-break:break-all;" ></div>
            <div v-else class="contentTip">
              <p>{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsDescTip1")}}</p>
              <p>{{$t("goodsAddEditInfo.goodsDetail.phoneGoodsDescTip2")}}</p>
            </div>
          </div>
        </template>
      </div>
    </div>
    <div class="goodsDetailWrap">
      <div class="goodsDetailItem">
        <p class="message">
          <span class="messageTitle">{{$t("goodsAddEditInfo.goodsDetail.goodsItemTitle")}}</span>
          <span style="color: #ccc">{{$t("goodsAddEditInfo.goodsDetail.goodsItemStyleTip")}}</span>
        </p>
      </div>
      <!-- 模板位置 -->
      <div class="goodsDetailItem">
        <p class="message">
          <span class="messageTitle">{{$t("goodsAddEditInfo.goodsDetail.goodsItemPositionTitle")}}</span>
          <span>
            <el-radio-group v-model="goodsDetailInfo.isPageUp">
              <el-radio :label="0">{{$t("goodsAddEditInfo.goodsDetail.goodsItemPositionRadio1")}}</el-radio>
              <el-radio :label="1">{{$t("goodsAddEditInfo.goodsDetail.goodsItemPositionRadio2")}}</el-radio>
            </el-radio-group>
          </span>
        </p>
      </div>
      <!-- 选择模板 -->
      <div class="goodsDetailItem">
        <p class="message" style="border-bottom: 1px solid #ccc;"><span class="messageTitle">{{$t("goodsAddEditInfo.goodsDetail.goodsItemDecorateTitle")}}</span></p>
        <p class="message">
          <span class="messageTitle">{{$t("goodsAddEditInfo.goodsDetail.goodsItemDecorateTitle2")}}</span>
          <template v-if="goodsDetailInfo.goodsPageData!==null">
            <span style="font-size: 14px;">{{goodsDetailInfo.goodsPageData.pageName}}</span>
            <span @click="goodsDetailInfo.goodsPageData=null" class="deleteIcon">×</span>
          </template>
          <!--选择模板按钮-->
          <span @click="pageTemplateShow=!pageTemplateShow" style="border: 1px solid #ccc;display: inline-block;width: 75px;font-size: 14px;line-height:20px;text-align: center;cursor: pointer;">{{$t("goodsAddEditInfo.goodsDetail.goodsItemDecorateBtn")}}</span>
          <el-link style="margin-left: 10px;" type="primary" :underline="false" href="#" target="_blank">{{$t("goodsAddEditInfo.linkRefresh")}}</el-link>
          <el-link style="margin-left: 10px;" type="primary" :underline="false" href="#" target="_blank">{{$t("goodsAddEditInfo.goodsDetail.goodsItemDecorateAdd")}}</el-link>
        </p>
      </div>
      <!-- 商品详情 -->
      <div class="goodsDetailItem">
        <p class="message" style="border-bottom: 1px solid #ccc;"><span class="messageTitle">{{$t("goodsAddEditInfo.goodsDetail.goodsItemDecorateTitle")}}</span></p>
        <div style="margin:10px 0px;border: 1px solid #ccc;width:calc(100% - 15px);">
          <TinymceEditor  v-model="goodsDetailInfo.goodsDesc"/>
        </div>
      </div>
    </div>
    <!-- 页面装修模板选择框 -->
    <selectTemplate :tuneUpSelectTemplate="pageTemplateDialogData.isShow" @handleSelectTemplate="pageTemplateTableClick"/>
  </div>
</template>
<script>
/* 接口API导入 */

/* 组件导入 */
import TinymceEditor from '@/components/admin/tinymceEditor/tinymceEditor'
import selectTemplate from '@/components/admin/selectTemplate'
/* 工具函数导入 */
import { isStrBlank } from '@/util/typeUtil'

export default {
  name: 'addingGoodsDetails',
  props: ['goodsProductInfoData'],
  components: {
    TinymceEditor,
    selectTemplate
  },
  computed: {
    goodsNumAndPriceComputed () {
      let goodsNumSum = 0
      let goodsPriceMin = Number.MAX_VALUE
      let goodsPriceMax = -Number.MIN_VALUE

      this.goodsProductInfoData.goodsSpecProducts.forEach(item => {
        goodsNumSum += item.prdNumber
        goodsPriceMin = goodsPriceMin > item.prdPrice ? item.prdPrice : goodsPriceMin
        goodsPriceMax = goodsPriceMax < item.prdPrice ? item.prdPrice : goodsPriceMax
      })
      return {
        goodsNumSum,
        goodsPriceMin,
        goodsPriceMax
      }
    }
  },
  data () {
    return {
      goodsDetailInfo: {
        // {pageId:1,pageName:"XXX"}
        goodsPageData: null,
        isPageUp: 1,
        goodsDesc: null
      },
      /* 页面装修模板弹框展示控制数据 */
      pageTemplateShow: false
    }
  },
  methods: {
    /* 页面模板点击回调 */
    pageTemplateTableClick (data) {
      if (data == null) {
        this.goodsDetailInfo.goodsPageData = null
      } else {
        this.goodsDetailInfo.goodsPageData = {pageId: data.pageId, pageName: data.pageName}
      }
    },
    /* 这样是未了让esLint不报错，否则导入的地方为报unusage */
    isStrBlank (val) {
      return isStrBlank(val)
    },
    /* 初始化待修改商品数据 */
    initDataForUpdate (goodsData) {
      this.goodsDetailInfo.isPageUp = goodsData.isPageUp
      this.goodsDetailInfo.goodsDesc = goodsData.goodsDesc
      if (goodsData.goodsPageName !== null) {
        this.goodsDetailInfo.goodsPageData = {pageId: goodsData.goodsPageId, pageName: goodsData.goodsPageName}
      }
    },
    /* 处理复制操作的数据 */
    disposeDataForCopy () {

    },
    /* 新增商品数据初始化 */
    initDataForInsert () {

    },
    /* 验证数据是否全部合法 */
    validateFormData () {
      return true
    },
    /* 获取传给后台的表单数据 */
    getFormData () {
      let retData = {
        isPageUp: this.goodsDetailInfo.isPageUp,
        goodsPageId: null,
        goodsDesc: this.goodsDetailInfo.goodsDesc
      }

      if (this.goodsDetailInfo.goodsPageData !== null) {
        retData.goodsPageId = this.goodsDetailInfo.goodsPageData.pageId
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
  .phoneWrap {
    width: 385px;
    border: 1px solid #ccc;
    box-sizing: content-box;
  }

  .phoneTop {
    width: 375px;
    height: 64px;
    margin: 0 auto;
    background: url(../../../../../../assets/adminImg/page_name.png) no-repeat;
    text-align: center;
  }

  .goodsItemWrap {
    font-size: 14px;
    min-height: 90px;
    background-color: #fff;
    padding: 5px 0px 5px 10px;
  }

  .goodsItemWrap .contentTip {
    color: #ccc;
    height: 150px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .goodsItemWrap .contentTip p {
    margin-top: 5px;
  }

  .goodsDrapItem {
    margin-top: 10px;
    min-height: 200px;
  }

  .goodsDrapItem:hover {
    cursor: pointer;
    border: 1px dashed #2057ff;
  }

  .goodsDetailWrap {
    border: 1px solid #ccc;
    border-radius: 5px;
    background-color: #F8F8F8;
    padding: 10px;
    margin-left: 30px;
  }

  .goodsDetailItem {
    width: 652px;
    box-sizing: border-box;
    border: 1px solid #ccc;
    padding-left: 15px;
    background-color: #fff;
    margin-bottom: 20px;
  }

  .goodsDetailItem .message {
    padding: 10px 0px;
  }

  .goodsDetailItem .message .messageTitle {
    display: inline-block;
    font-size: 15px;
    width: 120px;
    color: #333;
  }
  .deleteIcon {
    display: inline-block;
    width: 15px;
    height: 15px;
    color: #fff;
    background: #ccc;
    border: 1px solid #ccc;
    border-radius: 50%;
    line-height: 14px;
    text-align: center;
    cursor: pointer;
    opacity: 0.8;
    margin-right: 15px;
  }

  /* 控制整个滚动条 */
  ::-webkit-scrollbar {
    background-color: #fff;
    width: 5px;
    background-clip: border-box;
  }

  /* 滚动条中间滑动部分 */
  ::-webkit-scrollbar-thumb {
    background-color: #ccc;
    border-radius: 5px;
  }

  ::-webkit-scrollbar-thumb:hover {
    background-color: #555d63;
  }
</style>
