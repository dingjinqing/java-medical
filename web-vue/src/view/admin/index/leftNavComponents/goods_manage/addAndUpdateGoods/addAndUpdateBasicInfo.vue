<template>
  <div>
    <div class="title">{{$t("goodsAddEditInfo.basicInfo.title")}}</div>
    <div>
      <el-form
        ref="basicInfoForm"
        :model="goodsProductInfo"
        :rules="basicInfoRules"
        label-width="120px"
      >
        <el-form-item
          :label="$t('goodsAddEditInfo.basicInfo.goodsName')"
          prop="goodsName"
        >
          <el-input
            ref="goodsNameInput"
            v-model="goodsProductInfo.goodsName"
            size="small"
            style="width:170px"
            @change="goodsNameChangeRepeatCheck"
          />
        </el-form-item>
        <el-form-item :label="$t('goodsAddEditInfo.basicInfo.goodsAd')">
          <el-input
            v-model="goodsProductInfo.goodsAd"
            size="small"
            style="width:170px"
          />
        </el-form-item>
        <el-form-item :label="$t('goodsAddEditInfo.basicInfo.goodsSn')">
          <el-input
            v-model="goodsProductInfo.goodsSn"
            size="small"
            style="width:170px;"
            @change="goodsSnChangeRepeatCheck"
          />
          <span class="inputTip">{{$t("goodsAddEditInfo.basicInfo.goodsSnTip")}}</span>
        </el-form-item>
        <el-form-item
          :label="$t('goodsAddEditInfo.basicInfo.catId')"
          prop="catId"
        >
          <el-select
            ref="catSelect"
            v-model="catIdTemp.firstCatId"
            size="small"
            @change="catIdSelectChange(1,$event)"
            style="width:170px;"
          >
            <el-option
              :value="null"
              :label="$t('goodsAddEditInfo.basicInfo.catIdSelectDefault')"
            />
            <el-option
              v-for="item in catIdTemp.firstCatData"
              :label="item.catName"
              :value="item.catId"
              :key="item.catId"
            />
          </el-select>
          <el-select
            v-if="!!catIdTemp.firstCatId"
            v-model="catIdTemp.secondCatId"
            size="small"
            @change="catIdSelectChange(2,$event)"
            style="width:170px;"
          >
            <el-option
              :value="null"
              :label="$t('goodsAddEditInfo.basicInfo.catIdSelectDefault')"
            />
            <el-option
              v-for="item in catIdTemp.secondCatData"
              :label="item.catName"
              :value="item.catId"
              :key="item.catId"
            />
          </el-select>
          <el-select
            v-if="!!catIdTemp.firstCatId&&!!catIdTemp.secondCatId"
            v-model="catIdTemp.thirdCatId"
            size="small"
            @change="catIdSelectChange(3,$event)"
            style="width:170px;"
          >
            <el-option
              :value="null"
              :label="$t('goodsAddEditInfo.basicInfo.catIdSelectDefault')"
            />
            <el-option
              v-for="item in catIdTemp.thirdCatData"
              :label="item.catName"
              :value="item.catId"
              :key="item.catId"
            />
          </el-select>
          <span class="inputTip">
            {{$t("goodsAddEditInfo.basicInfo.catIdTip")}}
          </span>
          <el-link
            type="primary"
            :underline="false"
            href="#"
            target="_blank"
          >{{$t("goodsAddEditInfo.basicInfo.catIdGo")}}</el-link>
        </el-form-item>
        <el-form-item :label="$t('goodsAddEditInfo.basicInfo.goodsImg')">
          <div style="display: flex;align-items: center;flex-wrap: wrap;">
            <div
              v-for="(item,index) in goodsProductInfo.goodsImgs"
              :key="index"
              class="goodsImgWrap"
            >
              <el-image
                fit="cover"
                :src="item.imgUrl"
                style="width: 78px; height: 78px;"
              ></el-image>
              <span
                @click="deleteGoodsImg(index)"
                class="deleteIcon"
              >×</span>
              <span
                @click="moveGoodsImgIndex(index,-1)"
                class="moveIcon"
                style="left: 0px;"
              >←</span>
              <span
                @click="moveGoodsImgIndex(index,1)"
                class="moveIcon"
                style="right: 0px;"
              >→</span>
            </div>
            <div
              class="goodsImgWrap"
              @click="addGoodsImg"
            >
              <el-image
                fit="scale-down"
                :src="imgHost+'/image/admin/add_img.png'"
                style="width: 78px; height: 78px;cursor: pointer;"
              />
            </div>
            <span class="inputTip">
              {{$t('goodsAddEditInfo.basicInfo.goodsImgTip')}}
            </span>
          </div>
        </el-form-item>
      </el-form>
      <!-- 基本信息更多配置 -->
      <div
        @click="handleToChangeArror"
        style="padding: 0 0 30px 50px"
      >
        <div
          v-if="arrorFlag"
          style="color:rgb(90, 139, 255);cursor:pointer;width: 200px;"
        >
          {{ $t('seckill.openConfigure') }}&nbsp;<img :src="ArrowArr[0].img_1">
        </div>
        <div
          v-if="!arrorFlag"
          style="color:rgb(90, 139, 255);cursor:pointer;width: 200px;"
        >
          {{ $t('seckill.closeConfigure') }}&nbsp;<img :src="ArrowArr[1].img_2">
        </div>
      </div>

      <el-form
        ref="basicInfoOtherForm"
        :model="goodsProductInfo"
        :rules="basicInfoRules"
        label-width="120px"
        v-show="!arrorFlag"
      >
        <el-form-item
          :label="$t('goodsAddEditInfo.basicInfoOther.unit')"
          prop="unit"
        >
          <el-select
            ref="unitSelect"
            v-model="unitSelectedValue"
            @change="unitSelectChange"
            size="small"
            style="width:170px;"
          >
            <el-option
              v-for="(item,index) in unitSelectOptions"
              :key="index"
              :value="item.value"
              :label="item.label"
            />
          </el-select>
          <el-input
            v-if="unitSelectedValue===null"
            v-model="unitCustomerValue"
            @change="unitCustomerChange"
            size="small"
            style="width:100px;"
          />
          <span
            v-if="unitSelectedValue===null"
            class="inputTip"
          >{{$t('goodsAddEditInfo.basicInfoOther.unitTip')}}</span>
        </el-form-item>
        <sortCatTreeSelect  ref="sortTree" :autoLoad="false" :filterGoodsInfo="{needGoodsNum: false}" treeType="sort" :selectedId.sync="goodsProductInfo.sortId"/>
        <el-form-item :label="$t('goodsAddEditInfo.basicInfoOther.goodsLabel')">
          <el-select
            v-model="labelSelectedTempVal"
            :placeholder="$t('goodsAddEditInfo.basicInfoOther.goodsLabelDefault')"
            size="small"
            @change="labelSelectChange"
            style="width:170px;"
          >
            <el-option
              v-for="item in labelSelectOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
          <el-link
            type="primary"
            :underline="false"
            @click.native="labelSelectRefresh"
            href="#"
            style="margin:0 5px;"
          >{{$t('goodsAddEditInfo.linkRefresh')}}
          </el-link>
          |
          <el-link
            type="primary"
            :underline="false"
            @click.native="$router.push({name: 'addGoodsLabel'})"
            href="#"
            style="margin:0 5px;"
          >{{$t('goodsAddEditInfo.basicInfoOther.goodsLabelNew')}}</el-link>
          |
          <el-link
            type="primary"
            :underline="false"
            @click.native="$router.push({name: 'label'})"
            href="#"
            style="margin:0 5px;"
          >{{$t('goodsAddEditInfo.basicInfoOther.goodsLabelManage')}}</el-link>
          <div
            v-if="labelSelectedItems.length>0"
            style="display: flex;flex-wrap: wrap;align-items:center;background-color: #f8f8f8;"
          >
            <div>已选：</div>
            <div
              class="selectedWrap"
              v-for="(item,index) in labelSelectedItems"
              :key="index"
            >
              {{item.name}}
              <span
                @click="deleteLabel(item,index)"
                class="deleteIcon"
              >×</span>
            </div>
          </div>
        </el-form-item>
        <el-form-item :label="$t('goodsAddEditInfo.basicInfoOther.goodsBrand')">
          <el-button
            @click="goodsBrandDialogData.goodsBrandDialogShow=true"
            size="small"
            style="width: 170px;"
          >
            {{currentGoodsBrandData.brandName}}
          </el-button>
          <el-link
            type="primary"
            :underline="false"
            @click.native="$router.push({name: 'addBrand'})"
            href="#"
            style="margin:0 5px;"
          >{{$t('goodsAddEditInfo.basicInfoOther.goodsBrandNew')}}</el-link>
          |
          <el-link
            type="primary"
            :underline="false"
            @click.native="$router.push({name: 'brand'})"
            href="#"
            style="margin:0 5px;"
          >{{$t('goodsAddEditInfo.basicInfoOther.goodsBrandManage')}}</el-link>
        </el-form-item>
        <el-form-item
          :label="$t('goodsAddEditInfo.basicInfoOther.goodsVideo') + '：'"
          prop="video"
        >
          <VideoSpaceDialog
            :visible.sync="showVideoSpaceDialog"
            @video-click="videoSelected"
          />
          <div style="display: flex; align-items: center;flex-wrap: wrap;">
            <div class="add-video-container">
              <el-image
                fit="scale-down"
                class="add-goods-video"
                :src="videoSnapShotUrl"
                @click="videoInputClick"
              />
              <el-image
                fit="scale-down"
                @click="videoRemove"
                :src="imgHost+'/image/admin/icon_delete.png'"
                class="img-delete good_img_deletes"
                v-show="showRemoveVideoIcon"
              />

              <el-link
                type="primary"
                :underline="false"
                :href="videoUrl"
                v-show="showRemoveVideoIcon"
                class="btn_playa"
                target="_blank"
              >
                {{$t('videoSpace.upload.play')}}
              </el-link>
            </div>
            <span class="inputTip">{{$t('goodsAddEditInfo.basicInfoOther.goodsVideoTip')}}</span>
          </div>
        </el-form-item>
      </el-form>
      <!--图片dialog-->
      <ImageDalog
        :tuneUp="selfImgDialogShow"
        pageIndex='pictureSpace'
        :isDraggable="true"
        :imageSize="[800,800]"
        @handleSelectImg='imgDialogSelectedCallback'
      />
      <!--添加品牌dialog-->
      <el-dialog
        :title="$t('goodsAddEditInfo.basicInfoOther.goodsBrandTitle')"
        :visible.sync="goodsBrandDialogData.goodsBrandDialogShow"
        @open="goodsBrandDialogBeforeOpen"
        modal
        width="40%"
        :show-close="false"
      >
        <el-form
          :inline="true"
          :model="goodsBrandDialogData.formData"
          label-width="80px"
        >
          <el-form-item :label="$t('goodsAddEditInfo.basicInfoOther.goodsBrandName')">
            <el-input v-model="goodsBrandDialogData.formData.brandName" />
          </el-form-item>
          <el-form-item :label="$t('goodsAddEditInfo.basicInfoOther.goodsBrandClassify')">
            <el-select v-model="goodsBrandDialogData.formData.classifyId">
              <el-option
                :label="$t('goodsAddEditInfo.basicInfoOther.goodsBrandClassifyDefault')"
                :value="null"
              />
              <el-option
                v-for="(item,index) in goodsBrandDialogData.formData.brandClassifyOptions"
                :key="index"
                :label="item.classifyName"
                :value="item.classifyId"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              @click="fetchGoodsBrandDialogTableData"
              type="primary"
              size="small"
            >{{$t('goodsAddEditInfo.filter')}}</el-button>
          </el-form-item>
        </el-form>
        <div class="tableContent">
          <el-table
            :data="goodsBrandDialogData.tableData"
            v-loading="goodsBrandDialogData.loading"
            border
            height="300"
            highlight-current-row
            @current-change="brandTableCurrentChange"
            style="width: 100%;"
          >
            <el-table-column
              prop="brandName"
              :label="$t('goodsAddEditInfo.basicInfoOther.goodsBrandName')"
            />
            <el-table-column
              prop="classifyName"
              :label="$t('goodsAddEditInfo.basicInfoOther.goodsBrandClassify')"
            />
            <el-table-column
              prop="createTime"
              :label="$t('goodsAddEditInfo.createTime')"
            />
          </el-table>
          <pagination
            :page-params.sync="goodsBrandDialogData.formData.pageParams"
            @pagination="fetchGoodsBrandDialogTableData"
          />
        </div>
        <div slot="footer">
          <el-button
            @click="brandDialogConfirm"
            type="primary"
            size="small"
          >{{$t('goodsAddEditInfo.confirmBtn')}}</el-button>
          <el-button
            @click="brandDialogCancel"
            type="primary"
            size="small"
          >{{$t('goodsAddEditInfo.cancelBtn')}}</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
// 接口函数引入
import {
  selectPlatformClassification,
  selectParentPlatfromClassification,
  isGoodsColumnValueExist
} from '@/api/admin/goodsManage/addAndUpdateGoods/addAndUpdateGoods'
import {getGoodsFilterItem} from '@/api/admin/goodsManage/allGoods/allGoods'
import { goodsBrandClassifyListApi, goodsBrandPageListApi } from '@/api/admin/goodsManage/brandManagement/brandManagement'
// js工具函数导入
import { isStrBlank } from '@/util/typeUtil'
// 组件导入
import sortCatTreeSelect from '@/components/admin/sortCatTreeSelect'
import ImageDalog from '@/components/admin/imageDalog'
import pagination from '@/components/admin/pagination/pagination'
import VideoSpaceDialog from '@/components/admin/videoSpace/videoSpaceDialog'
export default {
  components: { sortCatTreeSelect, ImageDalog, pagination, VideoSpaceDialog },
  data () {
    return {
      selfImgDialogShow: false,
      goodsProductInfo: {
        goodsId: null,
        // 基本信息
        goodsName: null,
        goodsNameBak: null,
        goodsAd: null,
        goodsSn: null,
        goodsSnBak: null,
        catId: null,
        goodsImgs: [],
        unit: null,
        sortId: null,
        goodsLabels: null,
        brandId: null,
        goodsVideo: null,
        goodsVideoImg: null,
        goodsVideoSize: null,
        goodsVideoId: null
      },
      /* 基本信息部分 */
      // 基本信息验证
      basicInfoRules: {
        goodsName: [
          { required: true, message: '请输入商品名称', trigger: 'change' }
        ],
        catId: [
          { required: true, message: '请选择平台分类', trigger: 'change' }
        ],
        goodsImgs: [
          { required: true, message: '请选择商品图片', trigger: 'change' }
        ],
        unit: [
          { required: true, message: '单位不可为空', trigger: 'change' }
        ]
      },
      // 基本信平台分类辅助数据，对应分类每一级别的下落框数据和选中值
      catIdTemp: {
        firstCatId: null,
        secondCatId: null,
        thirdCatId: null,
        firstCatData: null,
        secondCatData: null,
        thirdCatData: null
      },
      imgHost: `${this.$imageHost}`,
      /* 基本信息更多配置部分 */
      collapseActiveName: 'basicMore',
      unitSelectOptions: [],
      unitSelectedValue: null,
      unitCustomerValue: null,
      /* 商品品牌服辅助数据 */
      // 商品品牌选中对象
      currentGoodsBrandData: {
        id: null,
        brandName: '添加品牌'
      },
      goodsBrandDialogData: {
        goodsBrandDialogShow: false,
        formData: {
          brandName: null,
          classifyId: null,
          pageParams: {
            currentPage: 1,
            pageRows: 20
          },
          brandClassifyOptions: []
        },
        tableData: [],
        loading: false,
        currentSelectedRow: null
      },
      /* 商品标签辅助数据 */
      // 商品标签下拉框
      labelSelectOptions: [],
      // 标签已选中列表
      labelSelectedItems: [],
      // 标签来下框选中瞬间的值
      labelSelectedTempVal: null,
      // 视频辅助数据
      showVideoSpaceDialog: false,
      videoSnapShotUrl: this.$imageHost + '/image/admin/add_video.png',
      videoUrl: '',
      showRemoveVideoIcon: false,
      arrorFlag: true, // 展开更多配置
      // 展开设置箭头
      ArrowArr: [{
        img_1: this.$imageHost + '/image/admin/show_more.png'
      }, {
        img_2: this.$imageHost + '/image/admin/hid_some.png'
      }]
    }
  },
  watch: {
    lang () {
      this.basicInfoRules.goodsName[0].message = this.$t('goodsAddEditInfo.warningInfo.requireGoodsName')
      this.basicInfoRules.catId[0].message = this.$t('goodsAddEditInfo.warningInfo.requirePlatformClassify')
      this.basicInfoRules.goodsImgs[0].message = this.$t('goodsAddEditInfo.warningInfo.requireGoodsImage')
      this.basicInfoRules.unit[0].message = this.$t('goodsAddEditInfo.warningInfo.requireGoodsUnit')
      this.currentGoodsBrandData.brandName = this.$t('goodsAddEditInfo.basicInfoOther.goodsBrandTitle')
      let i18nUnitOptions = this.$t('goodsAddEditInfo.basicInfoOther.unitOptions')
      this.unitSelectedValue = i18nUnitOptions[0]
      this.unitSelectOptions = [
        { value: i18nUnitOptions[0], label: i18nUnitOptions[0] },
        { value: i18nUnitOptions[1], label: i18nUnitOptions[1] },
        { value: i18nUnitOptions[2], label: i18nUnitOptions[2] },
        { value: i18nUnitOptions[3], label: i18nUnitOptions[3] },
        { value: i18nUnitOptions[4], label: i18nUnitOptions[4] },
        { value: i18nUnitOptions[5], label: i18nUnitOptions[5] },
        { value: i18nUnitOptions[6], label: i18nUnitOptions[6] },
        { value: i18nUnitOptions[7], label: i18nUnitOptions[7] },
        { value: i18nUnitOptions[8], label: i18nUnitOptions[8] },
        { value: i18nUnitOptions[9], label: i18nUnitOptions[9] },
        { value: i18nUnitOptions[10], label: i18nUnitOptions[10] },
        { value: i18nUnitOptions[11], label: i18nUnitOptions[11] },
        { value: i18nUnitOptions[12], label: i18nUnitOptions[12] },
        { value: i18nUnitOptions[13], label: i18nUnitOptions[13] },
        { value: i18nUnitOptions[14], label: i18nUnitOptions[14] },
        { value: i18nUnitOptions[15], label: i18nUnitOptions[15] },
        { value: i18nUnitOptions[16], label: i18nUnitOptions[16] },
        { value: i18nUnitOptions[17], label: i18nUnitOptions[17] },
        { value: i18nUnitOptions[18], label: i18nUnitOptions[18] },
        { value: null, label: i18nUnitOptions[19] }
      ]
      this.goodsProductInfo.unit = this.unitSelectOptions[0].value
    }
  },
  methods: {
    /* 基本信息部分 */
    // 商品名称重复性检查
    goodsNameChangeRepeatCheck () {
      if (isStrBlank(this.goodsProductInfo.goodsName)) {
        this.goodsProductInfo.goodsNameBak = this.goodsProductInfo.goodsName
        return
      }

      let data = {
        columnCheckFor: 0,
        goodsId: this.goodsProductInfo.goodsId,
        goodsName: this.goodsProductInfo.goodsName
      }
      isGoodsColumnValueExist(data).then(res => {
        if (res.error === 0) {
          this.$message.warning({ type: 'warning', message: this.$t('goodsAddEditInfo.warningInfo.goodsNameRepeat') })
          this.goodsProductInfo.goodsName = this.goodsProductInfo.goodsNameBak
        } else {
          this.goodsProductInfo.goodsNameBak = this.goodsProductInfo.goodsName
        }
      })
    },
    // 商品货号重复性检查
    goodsSnChangeRepeatCheck () {
      if (isStrBlank(this.goodsProductInfo.goodsSn)) {
        this.goodsProductInfo.goodsSnBak = this.goodsProductInfo.goodsSn
        return
      }

      let data = {
        columnCheckFor: 0,
        goodsId: this.goodsProductInfo.goodsId,
        goodsSn: this.goodsProductInfo.goodsSn
      }
      isGoodsColumnValueExist(data).then(res => {
        if (res.error === 0) {
          this.$message.warning({ type: 'warning', message: this.$t('goodsAddEditInfo.warningInfo.goodsNameRepeat') })
          this.goodsProductInfo.goodsSn = this.goodsProductInfo.goodsSnBak
        } else {
          this.goodsProductInfo.goodsSnBak = this.goodsProductInfo.goodsSn
        }
      })
    },
    // 平台分类下拉框交互
    catIdSelectChange (level, catId) {
      this.goodsProductInfo.catId = catId

      //  选则一级则重置二三级
      if (level === 1) {
        this.catIdTemp.secondCatId = null
        this.catIdTemp.secondCatData = null
        this.catIdTemp.thirdCatId = null
        this.catIdTemp.thirdCatData = null
      }
      // 选择二级则重置三级
      if (level === 2) {
        this.catIdTemp.thirdCatId = null
        this.catIdTemp.thirdCatData = null
        if (catId === null) {
          this.goodsProductInfo.catId = this.catIdTemp.firstCatId
        }
      }
      // 选择三级
      if (level === 3 && catId === null) {
        this.goodsProductInfo.catId = this.catIdTemp.secondCatId
      }

      if (catId === null) {
        return
      }

      selectPlatformClassification(catId).then(res => {
        if (level === 1) {
          this.catIdTemp.secondCatData = res.content
        }
        if (level === 2) {
          this.catIdTemp.thirdCatData = res.content
        }
      })
    },
    /* 添加图片点击事件，弹出图片选择组件 */
    addGoodsImg () {
      this.selfImgDialogShow = !this.selfImgDialogShow
    },
    /* 商品图片点击回调函数 */
    imgDialogSelectedCallback (imgObjs) {
      if (imgObjs == null || imgObjs.length === 0) {
        return
      }
      this.goodsProductInfo.goodsImgs = []
      imgObjs.forEach(imgObj => {
        this.goodsProductInfo.goodsImgs.push({ imgPath: imgObj.imgPath, imgUrl: imgObj.imgUrl })
      })
      if (this.goodsProductInfo.goodsImgs.length > 5) {
        this.goodsProductInfo.goodsImgs.splice(5)
      }
    },
    /* 删除商品图片 */
    deleteGoodsImg (index) {
      this.goodsProductInfo.goodsImgs.splice(index, 1)
    },
    /* 移动商品图片 -1:左移 1:右移 */
    moveGoodsImgIndex (index, direction) {
      let tempArr = this.goodsProductInfo.goodsImgs.splice(index, 1)
      let arrLength = this.goodsProductInfo.goodsImgs.length
      let targetIndex = index + direction

      index = targetIndex < 0 ? arrLength : targetIndex > arrLength ? 0 : targetIndex

      this.goodsProductInfo.goodsImgs.splice(index, 0, tempArr[0])
    },
    /* 基本信息更多配置部分 */
    // 单位选择下拉框处理事件
    unitSelectChange (value) {
      if (value === null) {
        // 当用户自定义过单位然后选择其他单位，当再次选择自定义时可以保留上一次的输入
        this.goodsProductInfo.unit = this.unitCustomerValue
      } else {
        this.goodsProductInfo.unit = value
      }
      this.$refs.basicInfoOtherForm.validateField('unit')
    },
    // 自定义单位处理事件
    unitCustomerChange () {
      // 自定义单位长度超过3字符，则返回
      if (!isStrBlank(this.unitCustomerValue) && this.unitCustomerValue.length > 3) {
        this.unitCustomerValue = this.unitCustomerValue.substring(0, 3)
        this.goodsProductInfo.unit = this.unitCustomerValue
        this.$refs.basicInfoOtherForm.validateField('unit')
        return
      }
      this.goodsProductInfo.unit = this.unitCustomerValue
      this.$refs.basicInfoOtherForm.validateField('unit')
    },
    /* 标签下拉框选择事件 */
    labelSelectChange () {
      this.labelSelectOptions = this.labelSelectOptions.filter(item => {
        if (item.id === this.labelSelectedTempVal) {
          this.labelSelectedItems.push(item)
          return false
        }
        return true
      })
      this.labelSelectedTempVal = null
    },
    /* 刷新标签下拉列表，要将已选的项剔除 */
    labelSelectRefresh () {
      getGoodsFilterItem({needGoodsLabel: true}).then(res => {
        const { content: { goodsLabels } } = res
        this.labelSelectOptions = goodsLabels.filter(item => !this.labelSelectedItems.some(innerItem => innerItem.id === item.id))
      })
    },
    /* 删除商品已选标签,并将删除的项添加到下拉框内 */
    deleteLabel (item, index) {
      this.labelSelectedItems.splice(index, 1)
      this.labelSelectOptions.push(item)
    },
    /* 品牌辅助函数 */
    /* brandDialog 打开前预处理 */
    goodsBrandDialogBeforeOpen () {
      // 商品品牌下拉列表初始化
      goodsBrandClassifyListApi().then(res => {
        this.goodsBrandDialogData.formData.brandClassifyOptions = res.content
      })
      this.fetchGoodsBrandDialogTableData()
    },
    /* 获取分页数据 */
    fetchGoodsBrandDialogTableData () {
      let param = {
        brandName: this.goodsBrandDialogData.formData.brandName,
        classifyId: this.goodsBrandDialogData.formData.classifyId,
        ...this.goodsBrandDialogData.formData.pageParams
      }
      this.goodsBrandDialogData.loading = true

      goodsBrandPageListApi(param).then(ret => {
        this.goodsBrandDialogData.formData.pageParams.totalRows = ret.content.page.totalRows
        this.goodsBrandDialogData.formData.pageParams.currentPage = ret.content.page.currentPage
        this.goodsBrandDialogData.formData.pageParams.pageRows = ret.content.page.pageRows
        this.goodsBrandDialogData.tableData = ret.content.dataList
        this.goodsBrandDialogData.loading = false
      })
    },
    /* 商品品牌table选择行事件 */
    brandTableCurrentChange (currentRow) {
      this.goodsBrandDialogData.currentSelectedRow = currentRow
    },
    /* brandDialog确认按钮 */
    brandDialogConfirm () {
      this.goodsBrandDialogData.goodsBrandDialogShow = false
      if (this.goodsBrandDialogData.currentSelectedRow !== null) {
        this.currentGoodsBrandData = this.goodsBrandDialogData.currentSelectedRow
        this.goodsProductInfo.brandId = this.currentGoodsBrandData.id
      }
    },
    /* brandDialog取消按钮 */
    brandDialogCancel () {
      this.goodsBrandDialogData.goodsBrandDialogShow = false
    },
    videoInputClick () {
      this.showVideoSpaceDialog = true
    },
    videoSelected (item) {
      console.log('videoSelected', item)
      this.refreshVideo(item.snapshotUrl, item.videoSnapPath, item.videoUrl, item.videoPath, item.videoSize, item.videoId)
    },
    /* 快照全路径，快照相对路径，视频全路径，视频相对路径，视频大小，视频id */
    refreshVideo (snapshotUrl, snapshotPath, videoUrl, videoPath, videoSize, videoId) {
      this.videoSnapShotUrl = snapshotUrl || this.$imageHost + '/image/admin/add_video.png'
      this.goodsProductInfo.goodsVideoImg = snapshotPath
      this.videoUrl = videoUrl
      this.goodsProductInfo.goodsVideo = videoPath
      this.goodsProductInfo.goodsVideoId = videoId
      this.goodsProductInfo.goodsVideoSize = videoSize
      this.showRemoveVideoIcon = !!snapshotUrl
    },
    videoRemove () {
      this.refreshVideo()
    },
    /* 初始化平台分类 */
    _initCatId (goodsData) {
      let catId = goodsData.catId
      this.goodsProductInfo.catId = catId
      selectParentPlatfromClassification(catId).then(res => {
        let catList = res.content
        this._catFirstInit()

        if (catList[0] !== undefined) {
          this.catIdTemp.firstCatId = catList[0].cat_id
          this._catSecondInit(catList[0].cat_id)
        }

        if (catList[1] !== undefined) {
          this.catIdTemp.secondCatId = catList[1].cat_id
          this._catThirdInit(catList[1].cat_id)
        }

        if (catList[2] !== undefined) {
          this.catIdTemp.thirdCatId = catList[2].cat_id
        }
      })
    },
    /* 初始化平台分类一级下拉框数据 */
    _catFirstInit () {
      selectPlatformClassification(0).then(res => {
        this.catIdTemp.firstCatData = res.content
      })
    },
    /* 初始化平台分类二级下拉框数据 */
    _catSecondInit (catId) {
      selectPlatformClassification(catId).then(res => {
        this.catIdTemp.secondCatData = res.content
      })
    },
    /* 初始化平台分类三级下拉框数据 */
    _catThirdInit (catId) {
      selectPlatformClassification(catId).then(res => {
        this.catIdTemp.thirdCatData = res.content
      })
    },
    /* 初始化视频信息 */
    _initGoodsVideo (goodsData) {
      this.refreshVideo(goodsData.goodsVideoImgUrl, goodsData.goodsVideoImg, goodsData.goodsVideoUrl, goodsData.goodsVideo, goodsData.goodsVideoSize, goodsData.goodsVideoId)
    },
    /* 初始化图片 */
    _initGoodsImgs (goodsData) {
      this.goodsProductInfo.goodsImgs = [{ imgUrl: goodsData.goodsImg, imgPath: goodsData.goodsImgPath }]
      if (goodsData.goodsImgs !== null && goodsData.goodsImgs.length > 0) {
        for (let i = 0; i < goodsData.goodsImgs.length; i++) {
          this.goodsProductInfo.goodsImgs.push({ imgUrl: goodsData.goodsImgs[i], imgPath: goodsData.goodsImgsPath[i] })
        }
      }
    },
    /* 初始化商品单位 */
    _initGoodsUnit (goodsData) {
      let unit = goodsData.unit
      // 看是否是用户自定义的,最后那个不等号是为了剔除用户输入的自定义单位也叫做自定义
      let has = this.unitSelectOptions.some(item => item.label === unit && item.value !== null)
      // true 不是自定义
      if (has) {
        this.unitSelectedValue = unit
      } else {
        this.unitSelectedValue = null
        this.unitCustomerValue = unit
      }
      this.unitSelectChange(this.unitSelectedValue)
    },
    /* 初始化商品标签 */
    _initGoodsLabel (goodsData) {
      let goodsLabelList = goodsData.goodsLabelListVos
      if (goodsLabelList === null) {
        return
      }
      this.labelSelectedItems = goodsLabelList
      this.labelSelectOptions = this.labelSelectOptions.filter(item => {
        let has = this.labelSelectedItems.some(selectedItem => selectedItem.id === item.id)
        return !has
      })
    },
    // 初始化商家分类和商品标签
    _sortAndLabelSelectInit () {
      let p1 = getGoodsFilterItem({needGoodsLabel: true}).then(res => {
        this.labelSelectOptions = res.content.goodsLabels
      })
      let p2 = this.$refs.sortTree.loadData()

      return Promise.all([p1, p2])
    },
    /* 初始化待修改商品数据 */
    initDataForUpdate (goodsData) {
      // 打开basicForm，否则display = none时会因数据尚未渲染报错
      this.arrorFlag = false
      // 先初始化页面数据再渲染待修改商品数据
      return this._sortAndLabelSelectInit().then(() => {
        this.goodsProductInfo.goodsId = goodsData.goodsId
        this.goodsProductInfo.goodsName = goodsData.goodsName
        this.goodsProductInfo.goodsNameBak = goodsData.goodsName
        this.goodsProductInfo.goodsAd = goodsData.goodsAd
        this.goodsProductInfo.goodsSn = goodsData.goodsSn
        this.goodsProductInfo.goodsSnBak = goodsData.goodsSn
        this.goodsProductInfo.catId = goodsData.catId
        // 初始化平台分类
        this._initCatId(goodsData)
        // 初始化图片
        this._initGoodsImgs(goodsData)
        // 初始化视频
        this._initGoodsVideo(goodsData)
        // 初始化商品单位
        this._initGoodsUnit(goodsData)
        // 初始化商家分类
        this.$refs.sortTree.setSelectedId(goodsData.sortId)
        // 初始化商品标签
        this._initGoodsLabel(goodsData)
        // 初始化商品品牌
        if (goodsData.brandName !== null) {
          this.currentGoodsBrandData.id = goodsData.brandId
          this.currentGoodsBrandData.brandName = goodsData.brandName
        }
        // 初始化商品视频
        this.goodsVideo = goodsData.goodsVideo
        this.goodsVideoImg = goodsData.goodsVideoImg
        this.goodsVideoSize = goodsData.goodsVideoSize
        this.goodsVideoId = goodsData.goodsVideoId
      })
    },
    /* 处理复制操作的数据 */
    disposeDataForCopy () {
    },
    /* 新增数据时数据初始化 */
    initDataForInsert () {
      this._catFirstInit()
      // 商家分类和品牌初始化
      this._sortAndLabelSelectInit()
    },
    /* 验证数据是否全部合法 */
    validateFormData () {
      if (isStrBlank(this.goodsProductInfo.goodsName)) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.requireGoodsName'), type: 'warning' })
        this.$refs.goodsNameInput.focus()
        return false
      }

      if (this.goodsProductInfo.catId === null) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.requirePlatformClassify'), type: 'warning' })
        this.$refs.catSelect.focus()
        return false
      }

      if (this.goodsProductInfo.goodsImgs.length === 0) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.requireGoodsImage'), type: 'warning' })
        return false
      }

      if (isStrBlank(this.goodsProductInfo.unit)) {
        this.$message.warning({ message: this.$t('goodsAddEditInfo.warningInfo.requireGoodsUnit'), type: 'warning' })
        this.$refs.unitSelect.focus()
        return false
      }

      return true
    },
    /* 获取传给后台的表单数据 */
    getFormData () {
      let retData = {
        ...this.goodsProductInfo,
        goodsImg: null
      }

      retData.goodsLabels = []
      this.labelSelectedItems.forEach(item => retData.goodsLabels.push(item.id))

      // 处理商品图片
      // 只有一个商品主图
      if (this.goodsProductInfo.goodsImgs.length === 1) {
        retData.goodsImg = this.goodsProductInfo.goodsImgs[0].imgPath
        retData.goodsImgs = null
      } else if (this.goodsProductInfo.goodsImgs.length > 1) {
        // 有商品主图和幅图
        // 避免和goodsProductInfo共享同一个数组
        retData.goodsImgs = []
        retData.goodsImg = this.goodsProductInfo.goodsImgs[0].imgPath
        for (let i = 1; i < this.goodsProductInfo.goodsImgs.length; i++) {
          retData.goodsImgs.push(this.goodsProductInfo.goodsImgs[i].imgPath)
        }
      }
      return retData
    },
    /* 展开更多配置 */
    handleToChangeArror () {
      this.arrorFlag = !this.arrorFlag
    }
  },
  mounted () {
    // 国际化
    this.langDefault()
  }
}
</script>
<style scoped>
.inputTip {
  color: #999;
  margin-left: 15px;
}
.title {
  font-weight: bold;
  height: 40px;
  background: #f8f8f8;
  line-height: 40px;
  width: 100%;
  padding-left: 10px;
  margin: 20px 0;
}
.goodsImgWrap {
  width: 80px;
  height: 80px;
  border: 1px solid #ccc;
  margin: 5px 5px;
  position: relative;
}
.goodsImgWrap .deleteIcon {
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
.goodsImgWrap .moveIcon {
  width: 17px;
  height: 17px;
  display: none;
  color: #fff;
  background: #ccc;
  border: 1px solid #ccc;
  line-height: 17px;
  text-align: center;
  position: absolute;
  bottom: 0px;
  cursor: pointer;
  opacity: 0.8;
}
.goodsImgWrap:hover .moveIcon {
  display: block;
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
.add-video-container {
  float: left;
  position: relative;
  margin-right: 15px;
  background: #f7f7f7;
  border: 1px solid #ccc;
  width: 80px;
  height: 81px;
  text-align: center;
  line-height: 80px;
  cursor: pointer;
}
.add-goods-video {
  width: 100%;
  height: 100%;
}
.good_img_deletes {
  position: absolute;
  right: -10px;
  top: -7px;
  cursor: pointer;
}
.btn_playa {
  color: #5a8bff !important;
  width: 80px;
  height: 20px;
  line-height: 20px;
  position: absolute;
  left: 88px;
  text-align: left;
  top: 0;
  z-index: 999;
}
</style>
