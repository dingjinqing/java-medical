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
          <el-input ref="sortNameFirst" v-model="goodsSortDataFirst.sortName" size="small"  style="width: 170px;"/>
        </el-form-item>
        <el-form-item label="分类优先级：">
          <el-input @change="firstChanged" v-model.number="goodsSortDataFirst.firstOld" size="small"  style="width: 170px;"/>
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
          <el-button @click="chooseImgLink" size="small" style="margin-left: 5px;color: #ccc;">添加链接</el-button>
        </el-form-item>
      </el-form>
      <!-- 二级分类表单 -->
      <el-form v-show="level === 1"
        ref="goodsSortSecondForm"
        :rules="goodsSortRules"
        :model="goodsSortDataSecond"
        label-width="120px">
        <el-form-item label="一级分类：" prop="firstSortId">
          <el-select ref="firstSortIdSelector" v-model="goodsSortDataSecond.firstSortId">
            <el-option label="请选择" :value="null"/>
            <el-option v-for="(item,index) in firstSortOptions" :label="item.sortName" :value="item.sortId" :key="index"/>
          </el-select>
          <el-popover placement="right" trigger="hover">
            <el-image :src="$imageHost+'/image/admin/share/goods_info_exapmle.jpg'" fit="scale-down" style="width:220px;height: 400px;"/>
            <span slot="reference" style="color:#409EFF;cursor:pointer;">查看示例</span>
          </el-popover>
        </el-form-item>
        <el-form-item label="分类名称：" prop="sortName">
          <el-input ref="sortNameSecond" v-model="goodsSortDataSecond.sortName" size="small"  style="width: 170px;"/>
        </el-form-item>
        <el-form-item label="分类优先级：">
          <el-input @change="firstChanged" v-model.number="goodsSortDataSecond.firstOld" size="small"  style="width: 170px;"/>
          <br/>
          <span class="inputTip">可填写1到100间的整数，数字越大前端排列顺序越靠前。优先级重复，则按照分类添加时间排序，添加越早越靠前排列</span>
        </el-form-item>
        <el-form-item label="分类图标：" prop="sortImg">
          <div style="display: flex;justify-content: left;align-items: center;line-height: 20px;">
            <span style="align-self: flex-start;color: #5a8bff;cursor: pointer;">修改</span>
            <div style="position: relative;margin: 0px 10px;height: 70px;cursor: pointer;" @click="chooseSortImg" >
              <img v-if="goodsSortDataSecond.sortImgObj === null" :src="$imageHost+'/image/admin/sort_moren.png'" style="width: 70px;height: 70px;">
              <img v-else :src="goodsSortDataSecond.sortImgObj.imgUrl"  style="width: 70px;height: 70px;">
              <div style="position:absolute;bottom:0px;width:100%;text-align:center;color:#fff;background-color: rgba(0,0,0,0.5);">更换图标</div>
            </div>
            <span style="align-self: flex-end;">150*140</span>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="contentFooter">
      <el-button type="primary" @click="save">保存</el-button>
    </div>
    <!--图片dialog-->
    <ImageDialog :tuneUp="imgDialogShow" pageIndex='pictureSpace' @handleSelectImg='imgDialogSelectedCallback'/>
    <!--链接dialog-->
    <LinkDialog :tuneUpSelectLink="linkDialogShow" @selectLinkPath="imgLinkDialogSelectedCallback"/>
  </div>
</template>

<script>
// 导入api
import { getGoodsSortList, addGoodsSort, updateGoodsSort, getGoodsSort } from '@/api/admin/goodsManage/goodsSortManagement/goodsSortManagement'
// 导入工具
import { isStrBlank, isNumberBlank } from '@/util/goodsUtil'

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
        firstOld: null,
        sortImg: null,
        sortImgObj: null
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
    /* 分类优先级改变事件 */
    firstChanged () {
      let target = this.level === 0 ? this.goodsSortDataFirst : this.goodsSortDataSecond

      if (isNumberBlank(target.firstOld)) {
        target.first = null
      } else if (typeof target.firstOld !== 'number') {
        target.firstOld = target.first
      } else {
        target.first = target.firstOld
      }
    },
    /* 选择图标 */
    chooseSortImg () {
      this.imgDialogShow = !this.imgDialogShow
    },
    /* 选择图标回调 */
    imgDialogSelectedCallback (imgObj) {
      if (this.level === 0) {
        this.goodsSortDataFirst.sortImgObj = {imgUrl: imgObj.imgUrl, imgPath: imgObj.imgPath}
      } else {
        this.goodsSortDataSecond.sortImgObj = {imgUrl: imgObj.imgUrl, imgPath: imgObj.imgPath}
      }
    },
    /* 删除图标 */
    deleteSortImg () {
      this.goodsSortDataFirst.sortImgObj = null
    },
    /* 选择链接 */
    chooseImgLink () {
      this.linkDialogShow = !this.linkDialogShow
    },
    /* 选择链接回调 */
    imgLinkDialogSelectedCallback (linkObj) {
      this.goodsSortDataFirst.imgLink = linkObj
    },
    /* 页面数据初始化 */
    _initData () {
      return getGoodsSortList({parentId: 0, type: 0}).then(res => {
        this.firstSortOptions = res.content
      })
    },
    /* 修改页面初始化数据 */
    _initDataForUpdate (sortId) {
      getGoodsSort(sortId).then(res => {
        if (res.error !== 0) {
          this.$message.error({message: res.message})
          return
        }

        let sort = res.content
        let target = sort.level === 0 ? this.goodsSortDataFirst : this.goodsSortDataSecond
        target.sortId = sort.sortId
        target.sortName = sort.sortName
        target.first = sort.first
        target.firstOld = sort.first
        this.level = sort.level
        target.sortImgObj = {
          imgPath: sort.sortImg,
          imgUrl: sort.sortImgUrl
        }
        target.imgLink = sort.imgLink
        target.parentId = sort.parentId
        if (sort.level === 1) {
          target.firstSortId = sort.parentId
        }
      })
    },
    /* 验证数据正确性 */
    _validateFormData () {
      if (this.level === 0) {
        if (isStrBlank(this.goodsSortDataFirst.sortName)) {
          this.$message.warning({message: '分类名称不可为空'})
          this.$refs.sortNameFirst.focus()
          return false
        }
      } else {
        if (this.goodsSortDataSecond.firstSortId === null) {
          this.$message.warning({message: '请选择一级分类'})
          this.$refs.firstSortIdSelector.focus()
          return false
        }

        if (isStrBlank(this.goodsSortDataSecond.sortName)) {
          this.$message.warning({message: '分类名称不可为空'})
          this.$refs.sortNameSecond.focus()
          return false
        }

        if (this.goodsSortDataSecond.sortImgObj === null) {
          this.$message.warning({message: '分类图标不可为空'})
          return false
        }
      }
      return true
    },
    /* 获得表单有效数据 */
    _getFormData () {
      let formData = {type: 0}
      if (this.level === 0) {
        formData.sortId = this.goodsSortDataFirst.sortId
        formData.sortName = this.goodsSortDataFirst.sortName
        formData.level = this.level
        formData.parentId = 0
        formData.first = this.goodsSortDataFirst.first
        formData.sortImg = this.goodsSortDataFirst.sortImgObj === null ? null : this.goodsSortDataFirst.sortImgObj.imgPath
        formData.imgLink = this.goodsSortDataFirst.imgLink
      } else {
        formData.sortId = this.goodsSortDataSecond.sortId
        formData.parentId = this.goodsSortDataSecond.firstSortId
        formData.sortName = this.goodsSortDataSecond.sortName
        formData.level = this.level
        formData.first = this.goodsSortDataSecond.first
        formData.sortImg = this.goodsSortDataSecond.sortImgObj === null ? null : this.goodsSortDataSecond.sortImgObj.imgPath
      }
      return formData
    },
    /* 保存按钮接口调用 */
    save () {
      if (!this._validateFormData()) {
        return
      }
      let formData = this._getFormData()

      let execFun = this.isUpdate ? updateGoodsSort : addGoodsSort

      execFun(formData).then(res => {
        if (res.error !== 0) {
          this.$message.error({message: res.message})
          return
        }
        this.$router.push({name: 'allGoodsSort'})
      })
    }
  },
  mounted () {
    let sortId = this.$route.params.sortId
    if (sortId !== undefined) {
      this.isUpdate = true
      this._initDataForUpdate(sortId)
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
