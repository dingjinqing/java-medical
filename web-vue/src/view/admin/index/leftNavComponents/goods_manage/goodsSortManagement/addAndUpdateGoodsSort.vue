<template>
  <div class="addGoodsRecommendSort">
    <allGoodsSortHeaderTab :tabIndex="isUpdate ? 4 : 2" />
    <div class="content">
      <el-radio-group
        v-model="level"
        style="margin-bottom: 10px;"
      >
        <el-radio :label="0" :disabled="isUpdate">{{$t('goodsSorts.addFirstLevel')}}</el-radio>
        <el-radio :label="1" :disabled="isUpdate">{{$t('goodsSorts.addSecondLevel')}}</el-radio>
      </el-radio-group>
      <!-- 一级分类表单 -->
      <el-form
        v-show="level === 0"
        ref="goodsSortFirstForm"
        :rules="goodsSortRules"
        :model="goodsSortDataFirst"
        label-width="120px"
      >
        <el-form-item
          :label="$t('goodsSorts.goodsSortName')"
          prop="sortName"
        >
          <el-input
            ref="sortNameFirst"
            v-model="goodsSortDataFirst.sortName"
            size="small"
            style="width: 170px;"
          />
        </el-form-item>
        <el-form-item :label="$t('goodsSorts.goodsSortFirst')">
          <el-input
            @change="firstChanged"
            v-model.number="goodsSortDataFirst.firstBind"
            size="small"
            style="width: 170px;"
          />
          <br />
          <span class="inputTip">{{$t('goodsSorts.goodsSortFirstTip')}}</span>
        </el-form-item>
        <el-form-item :label="$t('goodsSorts.goodsSortHeadImg')">
          <img
            v-if="goodsSortDataFirst.sortImgObj === null"
            @click="chooseSortImg"
            style=" display:block;width: 230px;height: 90px;cursor:pointer;"
            :src="$imageHost+'/image/admin/addSort/add_simple.png'"
          />
          <div
            v-else
            style="width: 230px;height: 90px;position: relative;"
            @click="chooseSortImg"
          >
            <img
              style="width: 100%;height: 100%;cursor: pointer;"
              :src="goodsSortDataFirst.sortImgObj.imgUrl"
            />
            <div style="position:absolute;bottom:0px;width:100%;text-align:center;color:#fff;background-color: rgba(0,0,0,0.5);cursor: pointer;">{{$t('goodsSorts.changeIcon')}}</div>
            <span
              @click.stop="deleteSortImg"
              class="deleteIcon"
            >×</span>
          </div>
          <span class="inputTip">{{$t('goodsSorts.goodsSortHeadImgTip')}}</span>
          <span style="font-size: 14px;color: #666;">{{$t('goodsSorts.goodsSortHeadImgLink')}}：</span>
          <el-input
            v-model="goodsSortDataFirst.imgLink"
            :readonly="true"
            size="small"
            style="width: 280px;"
          />
          <el-button
            @click="chooseImgLink"
            size="small"
            style="margin-left: 5px;color: #666666;"
          >{{$t('goodsSorts.addHeadImgLink')}}</el-button>
        </el-form-item>
      </el-form>
      <!-- 二级分类表单 -->
      <el-form
        v-show="level === 1"
        ref="goodsSortSecondForm"
        :rules="goodsSortRules"
        :model="goodsSortDataSecond"
        label-width="120px"
      >
        <el-form-item
          :label="$t('goodsSorts.firstLevel')"
          prop="firstSortId"
        >
          <el-select
            ref="firstSortIdSelector"
            v-model="goodsSortDataSecond.firstSortId"
          >
            <el-option
              :label="$t('goodsSorts.pleaseChoose')"
              :value="null"
            />
            <el-option
              v-for="(item,index) in firstSortOptions"
              :label="item.sortName"
              :value="item.sortId"
              :key="index"
            />
          </el-select>
          <el-popover
            placement="right"
            trigger="hover"
          >
            <el-image
              :src="$imageHost+'/image/admin/share/goods_info_exapmle.jpg'"
              fit="scale-down"
              style="width:220px;height: 400px;"
            />
            <span
              slot="reference"
              style="color:#409EFF;cursor:pointer;"
            >{{$t('goodsSorts.lookExample')}}</span>
          </el-popover>
        </el-form-item>
        <el-form-item
          :label="$t('goodsSorts.goodsSortName')"
          prop="sortName"
        >
          <el-input
            ref="sortNameSecond"
            v-model="goodsSortDataSecond.sortName"
            size="small"
            style="width: 170px;"
          />
        </el-form-item>
        <el-form-item :label="$t('goodsSorts.goodsSortFirst')">
          <el-input
            @change="firstChanged"
            v-model.number="goodsSortDataSecond.firstBind"
            size="small"
            style="width: 170px;"
          />
          <br />
          <span class="inputTip">{{$t('goodsSorts.goodsSortFirstTip')}}</span>
        </el-form-item>
        <el-form-item
          :label="$t('goodsSorts.goodsSortImg')"
          prop="sortImg"
        >
          <div style="display: flex;justify-content: left;align-items: center;line-height: 20px;">
            <span style="align-self: flex-start;color: #5a8bff;cursor: pointer;">{{$t('goodsSorts.update')}}</span>
            <div
              style="position: relative;margin: 0px 10px;height: 70px;cursor: pointer;"
              @click="chooseSortImg"
            >
              <img
                v-if="goodsSortDataSecond.sortImgObj === null"
                :src="$imageHost+'/image/admin/sort_moren.png'"
                style="width: 70px;height: 70px;"
              >
              <img
                v-else
                :src="goodsSortDataSecond.sortImgObj.imgUrl"
                style="width: 70px;height: 70px;"
              >
              <div style="position:absolute;bottom:0px;width:100%;text-align:center;color:#fff;background-color: rgba(0,0,0,0.5);">{{$t('goodsSorts.changeIcon')}}</div>
            </div>
            <span style="align-self: flex-end;">150*140</span>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="contentFooter">
      <el-button
        type="primary"
        @click="save"
      >{{$t('goodsSorts.save')}}</el-button>
    </div>
    <!--图片dialog-->
    <ImageDialog
      :tuneUp="imgDialogShow"
      pageIndex='pictureSpace'
      :imageSize="[imgWidth,imgHeight]"
      @handleSelectImg='imgDialogSelectedCallback'
    />
    <!--链接dialog-->
    <LinkDialog
      :tuneUpSelectLink="linkDialogShow"
      @selectLinkPath="imgLinkDialogSelectedCallback"
    />
  </div>
</template>

<script>
// 导入api
import { getGoodsSortSelectList, addGoodsSort, updateGoodsSort, getGoodsSort } from '@/api/admin/goodsManage/goodsSortManagement/goodsSortManagement'
// 导入工具
import { isStrBlank, isNumberBlank } from '@/util/typeUtil'

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
      imgWidth: 510,
      imgHeight: 200,
      goodsSortDataFirst: {
        sortId: null,
        sortName: null,
        first: null,
        firstBind: null,
        imgLink: null,
        sortImg: null,
        sortImgObj: null
      },
      goodsSortDataSecond: {
        firstSortId: null,
        sortId: null,
        sortName: null,
        first: null,
        firstBind: null,
        sortImg: null,
        sortImgObj: null
      },
      goodsSortRules: {
        sortName: [
          { required: true, message: this.$t('goodsSorts.sortNameNotNull'), trigger: 'change' }
        ],
        firstSortId: [
          { required: true, message: this.$t('goodsSorts.chooseFirstLevel'), trigger: 'change' }
        ],
        sortImg: [
          { required: true, message: this.$t('goodsSorts.chooseSortIcon'), trigger: 'change' }
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
      // 用户输入为空
      if (isNumberBlank(target.firstBind)) {
        target.first = null
      } else if (typeof target.firstBind !== 'number') {
        // 用户输入的不是数字，则还原数据
        target.firstBind = target.first
      } else if (target.firstBind < 0 || target.firstBind > 100) {
        target.firstBind = target.first
      } else {
        target.first = target.firstBind
      }
    },
    /* 选择图标 */
    chooseSortImg () {
      if (this.level === 0) {
        this.imgWidth = 510
        this.imgHeight = 200
      } else {
        this.imgWidth = 150
        this.imgHeight = 140
      }
      this.imgDialogShow = !this.imgDialogShow
    },
    /* 选择图标回调 */
    imgDialogSelectedCallback (imgObj) {
      if (this.level === 0) {
        this.goodsSortDataFirst.sortImgObj = { imgUrl: imgObj.imgUrl, imgPath: imgObj.imgPath }
      } else {
        this.goodsSortDataSecond.sortImgObj = { imgUrl: imgObj.imgUrl, imgPath: imgObj.imgPath }
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
      return getGoodsSortSelectList().then(res => {
        this.firstSortOptions = res.content
      })
    },
    /* 修改页面初始化数据 */
    _initDataForUpdate (sortId) {
      getGoodsSort(sortId).then(res => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message })
          return
        }

        let sort = res.content
        let target = sort.level === 0 ? this.goodsSortDataFirst : this.goodsSortDataSecond
        target.sortId = sort.sortId
        target.sortName = sort.sortName
        target.first = sort.first
        target.firstBind = sort.first
        this.level = sort.level
        target.sortImgObj = {
          imgPath: sort.sortImg,
          imgUrl: sort.sortImgUrl
        }
        target.imgLink = sort.imgLink
        target.parentId = sort.parentId
        target.oldParentId = sort.parentId
        if (sort.level === 1) {
          target.firstSortId = sort.parentId
        }
      })
    },
    /* 验证数据正确性 */
    _validateFormData () {
      if (this.level === 0) {
        if (isStrBlank(this.goodsSortDataFirst.sortName)) {
          this.$message.warning({ message: this.$t('goodsSorts.sortNameNotNull') })
          this.$refs.sortNameFirst.focus()
          return false
        }
      } else {
        if (this.goodsSortDataSecond.firstSortId === null) {
          this.$message.warning({ message: this.$t('goodsSorts.chooseFirstLevel') })
          this.$refs.firstSortIdSelector.focus()
          return false
        }

        if (isStrBlank(this.goodsSortDataSecond.sortName)) {
          this.$message.warning({ message: this.$t('goodsSorts.sortNameNotNull') })
          this.$refs.sortNameSecond.focus()
          return false
        }

        if (this.goodsSortDataSecond.sortImgObj === null) {
          this.$message.warning({ message: this.$t('goodsSorts.chooseSortIcon') })
          return false
        }
      }
      return true
    },
    /* 获得表单有效数据 */
    _getFormData () {
      let formData = { type: 0 }
      if (this.level === 0) {
        formData.sortId = this.goodsSortDataFirst.sortId
        formData.sortName = this.goodsSortDataFirst.sortName
        formData.level = this.level
        formData.parentId = 0
        formData.first = this.goodsSortDataFirst.first || 0
        formData.sortImg = this.goodsSortDataFirst.sortImgObj === null ? null : this.goodsSortDataFirst.sortImgObj.imgPath
        formData.imgLink = this.goodsSortDataFirst.imgLink
      } else {
        formData.sortId = this.goodsSortDataSecond.sortId
        formData.parentId = this.goodsSortDataSecond.firstSortId
        if (this.isUpdate) {
          formData.oldParentId = this.goodsSortDataSecond.oldParentId
        }
        formData.sortName = this.goodsSortDataSecond.sortName
        formData.level = this.level
        formData.first = this.goodsSortDataSecond.first || 0
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
          this.$message.error({ message: res.message })
          return
        }
        this.$router.push({ name: 'allGoodsSort' })
      })
    }
  },
  mounted () {
    this.langDefault()
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
.content {
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
.contentFooter {
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
