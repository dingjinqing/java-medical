<template>
  <div class="addGoodsRecommendSort">
    <allGoodsSortHeaderTab :tabIndex="isUpdate ? 4 : 2"/>
    <div class="content">
      <el-radio-group v-model="level" style="margin-bottom: 10px;">
        <el-radio :label="0">一级分类</el-radio>
        <el-radio :label="1">二级分类</el-radio>
      </el-radio-group>
      <!-- 一级分类表单 -->
      <el-form v-show="level === 0"
        ref="goodsSortFirstForm"
        :rules="goodsSortRules"
        :model="goodsSortDataFirst"
        label-width="120px">
        <el-form-item label="分类名称：" prop="sortName">
          <el-input v-model="goodsSortDataFirst.sortName" size="small"  style="width: 170px;"/>
        </el-form-item>
        <el-form-item label="分类优先级：">
          <el-input v-model.number="goodsSortDataFirst.firstOld" size="small"  style="width: 170px;"/>
          <br/>
          <span class="inputTip">可填写1到100间的整数，数字越大前端排列顺序越靠前。优先级重复，则按照分类添加时间排序，添加越早越靠前排列</span>
        </el-form-item>
        <el-form-item label="分类头图：">
            <img v-if="goodsSortDataFirst.sortImgObj === null"  @click="chooseSortImg"
                 style=" display:block;width: 230px;height: 90px;cursor:pointer;"
                 :src="$imageHost+'/image/admin/addSort/add_simple.png'"/>
          <div v-else style="width: 230px;height: 90px;position: relative;" @click="chooseSortImg">
            <img style="width: 100%;height: 100%;cursor: pointer;"
                 :src="goodsSortDataFirst.sortImgObj.imgUrl"/>
            <div style="position:absolute;bottom:0px;width:100%;text-align:center;color:#fff;background-color: rgba(0,0,0,0.5);">更换图标</div>
            <span @click.stop="deleteSortImg" class="deleteIcon">×</span>
          </div>
          <span class="inputTip">显示在分类页顶部，不填写则不显示，建议尺寸510*200</span>
          <span style="font-size: 14px;color: #666;">头图链接：</span>
          <el-input v-model="goodsSortDataFirst.imgLink" size="small" style="width: 280px;"/>
          <el-button @click="chooseImgLink" size="small" style="margin-left: 5px;">添加链接</el-button>
        </el-form-item>
      </el-form>
      <!-- 二级分类表单 -->
      <el-form v-show="level === 1"
        ref="goodsSortSecondForm"
        :rules="goodsSortRules"
        :model="goodsSortDataSecond"
        label-width="120px">
        <el-form-item label="一级分类：" prop="firstSortId">
          <el-select v-model="goodsSortDataSecond.firstSortId">
            <el-option label="请选择" :value="null"/>
            <el-option v-for="(item,index) in firstSortOptions" :label="item.sortName" :value="item.sortId" :key="index"/>
          </el-select>
        </el-form-item>
        <el-form-item label="分类名称：" prop="sortName">
          <el-input v-model="goodsSortDataSecond.sortName" size="small"  style="width: 170px;"/>
        </el-form-item>
        <el-form-item label="分类优先级：">
          <el-input v-model.number="goodsSortDataSecond.firstOld" size="small"  style="width: 170px;"/>
          <br/>
          <span class="inputTip">可填写1到100间的整数，数字越大前端排列顺序越靠前。优先级重复，则按照分类添加时间排序，添加越早越靠前排列</span>
        </el-form-item>
        <el-form-item label="分类图标：" prop="sortImg">
          <div style="display: flex;justify-content: left;align-items: center;line-height: 20px;">
            <span style="align-self: flex-start;color: #5a8bff;cursor: pointer;">修改</span>
            <div style="position: relative;margin: 0px 10px;height: 70px;cursor: pointer;" @click="chooseSortImg" >
              <img :src="goodsSortDataSecond.sortImgObj.imgUrl"  style="width: 70px;height: 70px;">
              <div style="position:absolute;bottom:0px;width:100%;text-align:center;color:#fff;background-color: rgba(0,0,0,0.5);">更换图标</div>
            </div>
            <span style="align-self: flex-end;">150*140</span>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="contentFooter">
      <el-button type="primary">保存</el-button>
    </div>
    <!--图片dialog-->
    <ImageDialog :tuneUp="imgDialogShow" pageIndex='pictureSpace' @handleSelectImg='imgDialogSelectedCallback'/>
    <!--链接dialog-->
    <LinkDialog :tuneUpSelectLink="linkDialogShow" @selectLinkPath="imgLinkDialogSelectedCallback"/>
  </div>
</template>

<script>
// 导入api
import { getGoodsSortList } from '@/api/admin/goodsManage/goodsSortManagement/goodsSortManagement'
// 组件导入
import allGoodsSortHeaderTab from './allGoodsSortHeaderTab'
import ImageDialog from '@/components/admin/imageDalog'
import LinkDialog from '@/components/admin/selectLinks'

export default {
  name: 'addGoodsRecommendSort',
  components: {
    allGoodsSortHeaderTab,
    ImageDialog,
    LinkDialog
  },
  data () {
    return {
      isUpdate: false,
      level: 0,
      goodsSortDataFirst: {
        sortId: null,
        sortName: null,
        first: null,
        firstOld: null,
        imgLink: null,
        sortImg: null,
        sortImgObj: null
      },
      goodsSortDataSecond: {
        firstSortId: null,
        sortId: null,
        sortName: null,
        first: null,
        sortImg: null,
        sortImgObj: {}
      },
      goodsSortRules: {
        sortName: [
          { required: true, message: '分类名称不可为空', trigger: 'change' }
        ],
        firstSortId: [
          { required: true, message: '请选择一级分类', trigger: 'change' }
        ],
        sortImg: [
          { required: true, message: '请选择分类图标', trigger: 'change' }
        ]
      },
      firstSortOptions: [],
      imgDialogShow: false,
      linkDialogShow: false
    }
  },
  methods: {
    chooseSortImg () {
      this.imgDialogShow = !this.imgDialogShow
    },
    imgDialogSelectedCallback (imgObj) {
      if (this.level === 0) {
        this.goodsSortDataFirst.sortImgObj = {imgUrl: imgObj.imgUrl, imgPath: imgObj.imgPath}
      } else {
        this.goodsSortDataSecond.sortImgObj = {imgUrl: imgObj.imgUrl, imgPath: imgObj.imgPath}
      }
    },
    deleteSortImg () {
      this.goodsSortDataFirst.sortImgObj = null
    },
    chooseImgLink () {
      this.linkDialogShow = !this.linkDialogShow
    },
    imgLinkDialogSelectedCallback (linkObj) {
      this.goodsSortDataFirst.imgLink = linkObj
    },
    /* 页面数据初始化 */
    _initData () {
      return getGoodsSortList({parentId: 0, type: 0}).then(res => {
        this.firstSortOptions = res.content
      })
    }
  },
  mounted () {
    let sortId = this.$route.params.sortId
    if (sortId !== undefined) {
      this.isUpdate = true
    }

    this._initData()
  }
}
</script>

<style scoped>
  .content{
    margin: 20px 0px;
  }
  .inputTip {
    color: #999;
    display: block;
  }
  .deleteIcon {
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
  .contentFooter{
    background: #f8f8fa;
    text-align: center;
    box-sizing: border-box;
    height: 60px;
    padding-top: 10px;
    position: fixed;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 2;
  }
</style>
