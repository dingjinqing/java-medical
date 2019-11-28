<template>
  <div class="allGoodsRecommendSort">
    <allGoodsSortHeaderTab :tabIndex="1" />
    <div class="content">
      <el-form label-width="140px">
        <el-form-item :label="$t('goodsRecommendSorts.isEnableRecommendSort') + '：'">
          <el-radio
            v-model="recommendSortCfg.recommendSortStatus"
            :label="1"
          >{{$t('goodsRecommendSorts.enable')}}</el-radio>
          <el-radio
            v-model="recommendSortCfg.recommendSortStatus"
            :label="0"
          >{{$t('goodsRecommendSorts.disable')}}</el-radio>
          <span class="inputTip">{{$t('goodsRecommendSorts.enableRecommendSortTip')}}</span>
        </el-form-item>
        <el-form-item :label="$t('goodsRecommendSorts.goodsSortHeadImg') + '：'">
          <img
            v-if="recommendSortCfg.recommendSortImgObj === null"
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
              style="width: 100%;height: 100%;cursor: pointer;cursor: pointer;"
              :src="recommendSortCfg.recommendSortImgObj.imgUrl"
            />
            <div style="position:absolute;bottom:0px;width:100%;text-align:center;color:#fff;background-color: rgba(0,0,0,0.5);cursor: pointer;">{{$t('goodsRecommendSorts.changeIcon')}}</div>
            <span
              @click.stop="deleteSortImg"
              class="deleteIcon"
            >×</span>
          </div>
          <span class="inputTip">{{$t('goodsRecommendSorts.goodsSortHeadImgTip')}}</span>
          <span style="font-size: 14px;color: #666;">{{$t('goodsRecommendSorts.goodsSortHeadImgLink')}}：</span>
          <el-input
            :readonly="true"
            v-model="recommendSortCfg.recommendImgLink"
            size="small"
            style="width: 170px;"
          />
          <el-button
            @click="chooseImgLink"
            size="small"
            style="margin-left: 5px;color: #666666;"
          >{{$t('goodsRecommendSorts.addHeadImgLink')}}</el-button>
        </el-form-item>
      </el-form>
      <el-button
        @click="addGoodsRecommendSort"
        type="primary"
        size="small"
        style="margin-bottom: 10px;"
      >{{$t('goodsRecommendSorts.addRecommendSort')}}</el-button>
      <el-table
        class="version-manage-table"
        header-row-class-name="tableClss"
        :data="goodsRecommendSortData"
        border
        style="width: 100%"
      >
        <el-table-column
          align="center"
          :label="$t('goodsRecommendSorts.goodsSortName')"
          prop="sortName"
        />
        <el-table-column
          align="center"
          :label="$t('goodsRecommendSorts.goodsSortImg')"
        >
          <template slot-scope="{row}">
            <div style="height: 50px;">
              <img
                v-if="row.sortImg"
                :src="row.sortImg"
                style="height: 50px;min-width: 160px;"
              />
            </div>

          </template>
        </el-table-column>
        <el-table-column
          align="center"
          :label="$t('goodsRecommendSorts.goodsSortImgLink')"
          prop="imgLink"
        />
        <el-table-column
          align="center"
          :label="$t('goodsRecommendSorts.goodsSortFirst')"
          prop="first"
        />
        <el-table-column
          align="center"
          :label="$t('goodsRecommendSorts.goodsSortCreateTime')"
          prop="createTime"
        />
        <el-table-column
          align="center"
          :label="$t('goodsRecommendSorts.goodsSortOperate')"
        >
          <template slot-scope="{row}">
            <el-tooltip
              :content="$t('goodsRecommendSorts.edit')"
              placement="top"
            >
              <span
                class="el-icon-edit-outline operateSpan"
                @click="editGoodsRecommendSortClicked(row)"
              ></span>
            </el-tooltip>
            <el-tooltip
              :content="$t('goodsRecommendSorts.delete')"
              placement="top"
            >
              <span
                class="el-icon-delete operateSpan"
                @click="deleteGoodsRecommendSortClicked(row)"
              ></span>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="contentFooter">
      <el-button
        @click="save"
        type="primary"
        size="small"
      >{{$t('goodsRecommendSorts.save')}}</el-button>
    </div>
    <!--图片dialog-->
    <ImageDialog
      :tuneUp="imgDialogShow"
      pageIndex='pictureSpace'
      :imageSize="[510,200]"
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
import { getGoodsSortList, deleteGoodsSort, setRecommendSortConfig, getRecommendSortConfig } from '@/api/admin/goodsManage/goodsSortManagement/goodsSortManagement'
// 组件导入
import allGoodsSortHeaderTab from './allGoodsSortHeaderTab'
import ImageDialog from '@/components/admin/imageDalog'
import LinkDialog from '@/components/admin/selectLinks'

export default {
  name: 'allGoodsRecommendSort',
  components: {
    allGoodsSortHeaderTab,
    ImageDialog,
    LinkDialog
  },
  data () {
    return {
      recommendSortCfg: {
        recommendSortStatus: 1,
        recommendSortImgObj: {},
        recommendImgLink: null
      },
      imgDialogShow: true,
      linkDialogShow: true,

      goodsRecommendSortData: []
    }
  },
  methods: {
    /* 选择图标 */
    chooseSortImg () {
      this.imgDialogShow = !this.imgDialogShow
    },
    /* 选择图标回调 */
    imgDialogSelectedCallback (imgObj) {
      this.recommendSortCfg.recommendSortImgObj = { imgUrl: imgObj.imgUrl, imgPath: imgObj.imgPath }
    },
    /* 删除图标 */
    deleteSortImg () {
      this.recommendSortCfg.recommendSortImgObj = null
    },
    /* 选择链接 */
    chooseImgLink () {
      this.linkDialogShow = !this.linkDialogShow
    },
    /* 选择链接回调 */
    imgLinkDialogSelectedCallback (linkObj) {
      this.recommendSortCfg.recommendImgLink = linkObj
    },
    /* 添加推荐分类 */
    addGoodsRecommendSort () {
      this.$router.push({ name: 'addGoodsRecommendSort' })
    },
    /* 编辑商品分类 */
    editGoodsRecommendSortClicked (row) {
      this.$router.push({ name: 'updateGoodsRecommendSort', params: { sortId: row.sortId } })
    },
    /* 删除商品分类 */
    deleteGoodsRecommendSortClicked (row) {
      this.$confirm(this.$t('goodsRecommendSorts.goodsSortDeleteMsg'), this.$t('goodsRecommendSorts.goodsSortDeleteTip'), {
        confirmButtonText: this.$t('goodsRecommendSorts.ok'),
        cancelButtonText: this.$t('goodsRecommendSorts.cancel'),
        type: 'info'
      }).then(() => {
        deleteGoodsSort(row.sortId).then(res => {
          this._fetchGoodsSortData()
        })
      })
    },
    /* 获取商品分类 */
    _fetchGoodsSortData () {
      getGoodsSortList({ parentId: 0, type: 1 }).then(res => {
        this.goodsRecommendSortData = res.content
      })
    },
    /* 获取推荐分类配置项信息 */
    _fetchGoodsSortConfig () {
      getRecommendSortConfig().then(res => {
        let content = res.content
        this.recommendSortCfg.recommendSortStatus = content.recommendSortStatus
        this.recommendSortCfg.recommendSortImgObj = {
          imgUrl: content.recommendSortImg,
          imgPath: content.recommendSortImgPath
        }
        this.recommendSortCfg.recommendImgLink = content.recommendImgLink
      })
    },
    /* 保存按钮 */
    save () {
      let params = {
        recommendSortStatus: this.recommendSortCfg.recommendSortStatus,
        recommendSortImgPath: this.recommendSortCfg.recommendSortImgObj.imgPath,
        recommendImgLink: this.recommendSortCfg.recommendImgLink
      }
      setRecommendSortConfig(params).then(res => {
        if (res.error !== 0) {
          this.$message.error({ message: res.message() })
          return
        }
        this.$message.success({ message: this.$t('goodsRecommendSorts.saveSuccess') })
      })
    }
  },
  mounted () {
    this.langDefault()
    this._fetchGoodsSortData()
    this._fetchGoodsSortConfig()
  }
}
</script>

<style scoped>
.content {
  margin: 20px 0px;
  padding-left: 10px;
  margin-bottom: 50px;
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
/deep/ .tableClss th {
  background-color: #f5f5f5;
  border: none;
  height: 36px;
  font-weight: bold;
  color: #000;
  padding: 8px 10px;
}
.operateSpan {
  font-size: 22px;
  color: #5a8bff;
  cursor: pointer !important;
}
.contentFooter {
  position: absolute;
  bottom: 0;
  right: 27px;
  left: 160px;
  height: 52px;
  padding: 10px 0;
  background-color: #fff;
  text-align: center;
  border-top: 1px solid #eee;
  z-index: 99;
}
</style>
