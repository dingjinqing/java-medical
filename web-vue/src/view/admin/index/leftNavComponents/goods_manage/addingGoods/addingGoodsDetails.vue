<template>
  <div style="display: flex;justify-content: center;align-items: flex-start;margin-top: 40px;">
    <div class="phoneWrap">
      <div class="phoneTop">
        <span style="font-weight:800;color:#000;font-size:16px;margin-top:33px;display: inline-block;">商品详情</span>
      </div>
      <div style="height:550px;overflow-y:auto;overflow-x: auto;background-color:#F5F5F5;">
        {{goodsProductInfoData.goodsName}}
        <!--商品主图-->
        <div style="height:380px;">
          <img :src="goodsProductInfoData.goodsImg" style="width:100%;height:100%;"/>
        </div>
        <!--商品信息-->
        <div class="goodsItemWrap">
          <div style="color:#333;font-size:16px;">{{goodsProductInfoData.goodsName}}</div>
          <div style="color:#FF6666;font-size:18px;margin:10px 0;">
            <template v-if="goodsNumAndPriceComputed.goodsPriceMin === goodsNumAndPriceComputed.goodsPriceMax">
              ￥ {{goodsNumAndPriceComputed.goodsPriceMin}}
            </template>
            <template v-else>
              ￥ {{goodsNumAndPriceComputed.goodsPriceMin}} - {{goodsNumAndPriceComputed.goodsPriceMax}}
            </template>
          </div>
          <div style="color:#999;font-size:14px;">库存：{{goodsNumAndPriceComputed.goodsNumSum}}
            {{goodsProductInfoData.unit}}
          </div>
        </div>
        <template v-if="goodsDetailInfo.isPageUp === 1">
          <!--商品描述-->
          <div class="goodsItemWrap goodsDrapItem">
            <div style="width: 70px;height: 20px;border-bottom: 1px solid #ccc;margin-bottom: 5px;">商品描述</div>
            <div v-if="!isStrBlank(goodsDetailInfo.goodsDesc)" v-html="goodsDetailInfo.goodsDesc" style="word-break:break-all;" ></div>
            <div v-else class="contentTip">
              <p>可在右侧编辑商品详情</p>
              <p>未添加内容时,不显示此模块内容</p>
            </div>
          </div>
          <!--自定义内容区域-->
          <div class="goodsItemWrap goodsDrapItem">
            <div class="contentTip">
              <template v-if="goodsDetailInfo.goodsPageData === null">
                <p style="font-size: 15px;color:#333;">自定义内容区域</p>
                <p>可在右侧选择商品页模板</p>
                <p>未添加内容时,不显示此模块内容</p>
              </template>
              <template v-else>
                <p style="color: #333;">已选择模板:{{goodsDetailInfo.goodsPageData.pageName}}</p>
              </template>
            </div>
          </div>
        </template>
        <template v-else>
          <!--自定义内容区域-->
          <div class="goodsItemWrap goodsDrapItem">
            <div class="contentTip">
              <template v-if="goodsDetailInfo.goodsPageData === null">
                <p style="font-size: 15px;color:#333;">自定义内容区域</p>
                <p>可在右侧选择商品页模板</p>
                <p>未添加内容时,不显示此模块内容</p>
              </template>
              <template v-else>
                <p style="color: #333;">已选择模板:{{goodsDetailInfo.goodsPageData.pageName}}</p>
              </template>
            </div>
          </div>
          <!--商品描述-->
          <div class="goodsItemWrap goodsDrapItem">
            <div style="width: 70px;height: 20px;border-bottom: 1px solid #ccc;margin-bottom: 5px;">商品描述</div>
            <div v-if="!isStrBlank(goodsDetailInfo.goodsDesc)" v-html="goodsDetailInfo.goodsDesc" style="word-break:break-all;" ></div>
            <div v-else class="contentTip">
              <p>可在右侧编辑商品详情</p>
              <p>未添加内容时,不显示此模块内容</p>
            </div>
          </div>
        </template>
      </div>
    </div>
    <div class="goodsDetailWrap">
      <div class="goodsDetailItem">
        <p class="message">
          <span class="messageTitle">商品基本信息</span><span style="color: #ccc">商品信息为固定样式仅供参考，请以实际效果为准</span>
        </p>
      </div>
      <div class="goodsDetailItem">
        <p class="message">
          <span class="messageTitle">模块位置</span>
          <span>
            <el-radio-group v-model="goodsDetailInfo.isPageUp">
              <el-radio :label="0">自定义内容在上</el-radio>
              <el-radio :label="1">商品详情在上</el-radio>
            </el-radio-group>
          </span>
        </p>
      </div>
      <div class="goodsDetailItem">
        <p class="message" style="border-bottom: 1px solid #ccc;"><span class="messageTitle">自定义内容</span></p>
        <p class="message">
          <span class="messageTitle">商品页模板</span>
          <template v-if="goodsDetailInfo.goodsPageData!==null">
            <span style="font-size: 14px;">{{goodsDetailInfo.goodsPageData.pageName}}</span>
            <span @click="goodsDetailInfo.goodsPageData=null" class="deleteIcon">×</span>
          </template>
          <span @click="pageTemplateDialogData.isShow=true" style="border: 1px solid #ccc;display: inline-block;width: 75px;font-size: 14px;line-height:20px;text-align: center;cursor: pointer;">选择模板</span>
          <el-link style="margin-left: 10px;" type="primary" :underline="false" href="#" target="_blank">刷新</el-link>
          <el-link style="margin-left: 10px;" type="primary" :underline="false" href="#" target="_blank">添加模板</el-link>
        </p>
      </div>
      <div class="goodsDetailItem">
        <p class="message" style="border-bottom: 1px solid #ccc;"><span class="messageTitle">商品详情</span></p>
        <div style="margin:10px 0px;border: 1px solid #ccc;width:calc(100% - 15px);">
          <tinymceEditor v-model="goodsDetailInfo.goodsDesc"/>
        </div>
      </div>
    </div>
    <!-- 页面装修模板选择框 -->
    <el-dialog ref="templateDialog"  title="选择模板" center :visible.sync="pageTemplateDialogData.isShow" @open="beforePageTemplateDialogShow" width="800px">
      <el-form :inline="true">
        <el-form-item label="页面名称"><el-input placeholder="请输入页面名称" size="small" v-model="pageTemplateDialogData.pageName"/></el-form-item>
        <el-form-item label="页面分类">
          <el-select size="small" v-model="pageTemplateDialogData.pageClassificationId">
            <el-option v-for="(item,index) in pageTemplateDialogData.pageClassificationList" :key="index" :value="item.id" :label="item.name"/>
          </el-select>
        </el-form-item>
        <el-form-item><el-button type="primary" size="small">搜索</el-button></el-form-item>
      </el-form>
      <el-table :data="pageTemplateDialogData.pageTemplateTableData"  @row-click="pageTemplateTableClick"  highlight-current-row border height="300">
        <el-table-column prop="createTime" label="创建时间"/>
        <el-table-column prop="pageName" label="页面名称" />
        <el-table-column label="是否首页">
          <template slot-scope="scope">
            {{scope.row.pageType ===1 ? '是':'否'}}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
/* 接口API导入 */

/* 组件导入 */
import tinymceEditor from '@/components/admin/tinymceEditor/tinymceEditor'

/* 工具函数导入 */
import { isStrBlank } from '@/util/goodsUtil'

export default {
  name: 'addingGoodsDetails',
  props: ['goodsProductInfoData'],
  components: {
    tinymceEditor
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
        goodsPageData: null,
        isPageUp: 1,
        goodsDesc: null
      },
      /* 页面装修模板辅助数据 */
      pageTemplateDialogData: {
        isShow: false,
        pageName: null,
        pageClassificationId: null,
        pageClassificationList: [],
        pageTemplateTableData: []
      }
    }
  },
  methods: {
    /* 页面模板处理弹出 */
    beforePageTemplateDialogShow () {
      this.pageTemplateDialogData.pageClassificationList = []
      this.pageTemplateDialogData.pageClassificationList.push({id: null, name: '请选择分类'}, {id: 1, name: '测试1'},
        {id: 2, name: '测试2'},
        {id: 3, name: '测试3'},
        {id: 4, name: '测试4'})

      this.pageTemplateDialogData.pageTemplateTableData = []
      this.pageTemplateDialogData.pageTemplateTableData.push({pageId: 1, createTime: 'XXXXXX', pageName: '测试页面', pageType: 1}, {pageId: 1, createTime: 'XXXXXX', pageName: '测试页面2', pageType: 1}, {pageId: 1, createTime: 'XXXXXX', pageName: '测试页面3', pageType: 1})
      this.pageTemplateDialogData.pageTemplateTableData.push({pageId: 1, createTime: 'XXXXXX', pageName: '测试页面3', pageType: 1}, {pageId: 1, createTime: 'XXXXXX', pageName: '测试页面5', pageType: 0}, {pageId: 1, createTime: 'XXXXXX', pageName: '测试页面7', pageType: 0})
      this.pageTemplateDialogData.pageTemplateTableData.push({pageId: 1, createTime: 'XXXXXX', pageName: '测试页面4', pageType: 1}, {pageId: 1, createTime: 'XXXXXX', pageName: '测试页面6', pageType: 0}, {pageId: 1, createTime: 'XXXXXX', pageName: '测试页面8', pageType: 1})
    },
    /* 页面模板点击回调 */
    pageTemplateTableClick (row, column, event) {
      this.pageTemplateDialogData.isShow = false
      this.goodsDetailInfo.goodsPageData = row
    },
    isStrBlank (val) {
      return isStrBlank(val)
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
    }
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
