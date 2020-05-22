<template>
  <div class="video_container">
    <div style="background-color:#fff;padding-bottom: 15px;">
      <!-- dialog_top -->
      <div class="dialog_top">
        <el-upload
          class="upload-demo"
          :before-upload="beforeUpLoad"
          :show-file-list="false"
          accept=".mp4,.mkv,.wmv"
          action=""
        >
          <el-button
            size="small"
            type="primary"
          >{{$t('videoSpace.upload.uploadVideo')}}</el-button>

          <div
            @mouseover="tip_over()"
            @mouseleave="tip_leave()"
            class="title_tip"
          >{{$t('videoSpace.upload.currentVersionTip',{version: version,leftSpace: leftSpace})}}<img :src="$imageHost + '/image/admin/system_icon.png'">
            <div
              class="tip_hidden"
              v-if="tip_hidden_flag"
            >
              <div class="system_info_content_top">{{$t('videoSpace.upload.currentVersionTipMore')}}</div>
              <div class="system_info_content_bottom">
                <el-button
                  type="primary"
                  size="mini"
                >{{$t('videoSpace.upload.knownMore')}}</el-button>
              </div>
            </div>
          </div>
          <div
            slot="tip"
            class="tips imageDalogTip_lineHeight"
          >
            <img :src="$imageHost + '/image/admin/notice_img.png'">
            {{$t('videoSpace.upload.uploadRule')}}
          </div>
        </el-upload>
      </div>
      <!-- dialog_middle -->
      <div class="dialog_middle">
        <div class="dialog_middle_top">
          <VTree
            ref="categoryTree"
            @node-click="nodeClick"
            @node-append="nodeAppend"
            @node-remove="nodeRemove"
            @node-update="nodeUpdate"
            :selectedCatId="selectedCatId"
            :treeData="treeData"
            class="tree"
          />
          <div class="dialog_middle_right_box">
            <div class="right_top">
              <el-select
                v-model="sortId"
                :placeholder="$t('videoSpace.upload.select')"
                size='mini'
              >
                <el-option
                  v-for="item in sortOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  size='mini'
                >
                </el-option>
              </el-select>
              <el-input
                v-model="videoNameInput"
                :placeholder="$t('videoSpace.upload.inputVideoName')"
                size='mini'
              ></el-input>
              <el-button
                type="info"
                plain
                @click="searchVideos()"
              >{{$t('videoSpace.upload.search')}}</el-button>

            </div>
            <div
              class="right_content"
              v-if="hasVideoData"
            >
              <ul>
                <li
                  @mouseenter="enter(index)"
                  @mouseleave="leave(index)"
                  v-for="(item,index) in videoList"
                  :key="index"
                >
                  <div
                    v-show="item.checked"
                    class="img_sel"
                  ></div>
                  <div
                    class="img-container"
                    style="position:relative"
                  >
                    <a :title="item.videoName">
                      <img
                        :src="item.snapshotUrl"
                        @click="onVideoClick(index)"
                      >
                    </a>

                  </div>
                  <div
                    class="img_mask"
                    :class="item.videoIndex === index?'mask_flag':''"
                  >
                    <p :class="imageDalog_p_height">
                      <a
                        class="old_pic"
                        :href="item.videoUrl"
                        target="_blank"
                        :title="$t('videoSpace.upload.playVideo')"
                      >{{$t('videoSpace.upload.play')}}</a>

                      <a
                        class="remove_image"
                        :title="$t('videoSpace.upload.delVideo')"
                        @click="delMaskVideo(item.videoId)"
                      >{{$t('videoSpace.upload.del')}}</a>
                    </p>
                  </div>
                  <div
                    class="img_dim"
                    :class="item.videoIndex === index?'dim_flag':''"
                  >
                    <p style="text-align:center">{{item.videoWidth}}x{{item.videoHeight}}</p>
                    <p style="text-align:center">{{item.formatDuration}}</p>
                  </div>
                  <div
                    class="video-name"
                    :title="item.videoName"
                  >
                    {{item.videoName}}
                  </div>
                </li>
              </ul>
              <div class="bottom">
                <div
                  class="bottom_radio"
                  v-show="!dialogMode"
                >
                  <el-checkbox
                    v-model="allChecked"
                    true-label="1"
                    false-label="0"
                    @change="onAllChecked()"
                  >{{$t('videoSpace.upload.selectAll')}}</el-checkbox>
                  <el-button
                    size="mini"
                    style="margin-right:5px"
                    @click="deleteVideos()"
                  >{{$t('videoSpace.upload.batchDelete')}}</el-button>
                  <el-button
                    size="mini"
                    @click="handleMoveVideos()"
                  >{{$t('videoSpace.upload.batchMove')}}</el-button>
                </div>

                <VPagination
                  :page-params.sync="pageParams"
                  @pagination="onPagination"
                />

              </div>
            </div>
            <div
              class="text-warning padding-top-10 right_content"
              style="text-align: center;width: 100%;height: 150px;margin-top: 60px"
              v-else
            >
              <img
                :src="$imageHost + '/image/admin/image_no_data.png'"
                style="margin-top: 20px"
              >
              <p style="color:#999;font-size: 14px;text-align: center;margin-top: 15px">{{$t('videoSpace.upload.noMatchedVideo')}}</p>
            </div>
          </div>
        </div>

      </div>

    </div>

    <!--批量移动弹窗-->
    <el-dialog
      :title="$t('videoSpace.upload.tip')"
      :visible.sync="showBatchMoveDialog"
      class="batch-move-dialog"
      width="400px"
    >
      <el-select
        v-model="selectMoveCatId"
        :placeholder="$t('videoSpace.upload.select')"
      >
        <el-option
          v-for="item in selectTreeData"
          :key="item.id"
          :label="item.name"
          :value="item.id"
          :style="'padding-left:'+item.level*8+'px'"
        >
        </el-option>
      </el-select>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="showBatchMoveDialog = false">{{$t('videoSpace.upload.cancel')}}</el-button>
        <el-button
          type="primary"
          @click="batchMoveVideo()"
        >{{$t('videoSpace.upload.ok')}}</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import VTree from '@/components/admin/v-tree'
import VPagination from '@/components/admin/pagination/pagination'
import { shopInfoRequest } from '@/api/admin/survey.js'
import {
  getVideoCategoryTreeRequest,
  addVideoCategoryRequest,
  deleteVideoCategoryRequest,
  renameVideoCategoryRequest,
  batchDeleteVideoRequest,
  getVideoListRequest,
  batchMoveVideoRequest,
  uploadVideoRequest,
  getUsedVideoSpace
} from '@/api/admin/videoSpace.js'
export default {
  components: { VTree, VPagination },
  props: {
    dialogMode: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      selectedCatId: 0,
      treeData: [],
      selectTreeData: [],
      selectMoveCatId: 0,
      pageParams: {},
      sortOptions: [],
      sortId: '',
      videoNameInput: '',
      videoList: [],
      tip_hidden_flag: false,
      allChecked: false,
      showBatchMoveDialog: false,
      hasVideoData: true,
      shopType: null,
      version: null, // 版本
      leftSpace: 0, // 剩余空间
      experienceVersionSpace: 100,
      basicVersionSpace: 0,
      advanceVersionSpace: 2048,
      flagShipVersionSpace: 10240
    }
  },
  mounted () {
    this.langDefault()
    this.sortOptions = this.$t('videoSpace.options')
    this.requestTreeNodes()
    this.requestVersionAndSpaceInfo()
  },
  watch: {
    lang (newData) {
      this.sortOptions = this.$t('videoSpace.options')
    }
  },
  methods: {
    /* 获取店铺版本号和用户空间 */
    requestVersionAndSpaceInfo () {
      shopInfoRequest().then(res => {
        this.shopType = res.content.shopType
        switch (this.shopType) {
          case 'v1':
            this.version = this.$t('overview.experienceVersion')
            break
          case 'v2':
            this.version = this.$t('overview.basicEdition')
            break
          case 'v3':
            this.version = this.$t('overview.advancedVersion')
            break
          case 'v4':
            this.version = this.$t('overview.Ultimate')
            break
        }
      }).then(() => {
        getUsedVideoSpace().then(res => {
          let maxSpace = 0
          switch (this.shopType) {
            case 'v1':
              maxSpace = this.experienceVersionSpace
              break
            case 'v2':
              maxSpace = this.basicVersionSpace
              break
            case 'v3':
              maxSpace = this.advanceVersionSpace
              break
            case 'v4':
              maxSpace = this.flagShipVersionSpace
              break
          }
          this.leftSpace = maxSpace - res.content
        })
      })
    },
    requestTreeNodes: function () {
      let _this = this
      getVideoCategoryTreeRequest().then(function (res) {
        _this.treeData = res.content
        _this.selectTreeData = _this.getCategoryTreeForSelectElement(_this.treeData)
        _this.queryCategoryVideos()
        _this.$nextTick(() => {
          _this.$refs.categoryTree.setCurrentKey(_this.selectedCatId)
        })
      })
    },

    nodeClick (object) {
      console.log('nodeClick', object)
      this.selectedCatId = object.id
      this.queryCategoryVideos()
    },

    nodeAppend (object, value) {
      console.log('nodeAppend', object, value)
      let data = {
        videoCatName: value,
        videoCatParentId: object.id,
        sort: 0
      }
      let _this = this
      addVideoCategoryRequest(data).then(function (res) {
        _this.requestTreeNodes()
      })
    },
    nodeRemove (object) {
      console.log('nodeRemove', object)
      let data = {
        videoCatId: object.id
      }
      if (this.selectedCatId === object.id) {
        this.selectedCatId = 0
      }
      let _this = this
      deleteVideoCategoryRequest(data).then(function (res) {
        _this.requestTreeNodes()
      }).then(() => {
        _this.requestVersionAndSpaceInfo()
      })
    },

    nodeUpdate (object, value) {
      console.log('nodeUpdate', object)
      let data = {
        videoCatId: object.id,
        videoCatName: value
      }
      let _this = this
      renameVideoCategoryRequest(data).then(function (res) {
        _this.requestTreeNodes()
      })
    },

    getCategoryTreeForSelectElement (categoryTree) {
      var tree = []
      categoryTree.forEach(value => {
        tree.push({
          id: value.id,
          level: value.level,
          name: value.name
        })
        if (Array.isArray(value.child) && value.child.length > 0) {
          tree = tree.concat(this.getCategoryTreeForSelectElement(value.child))
        }
      })
      return tree
    },

    /**
     * 翻页事件
     * @param page
     */
    onPagination (page) {
      this.pageParams.currentPage = page.currentPage
      this.searchVideos()
    },

    // 视频分类查询
    queryCategoryVideos () {
      let obj = {
        'page': 1,
        'videoCatId': this.selectedCatId,
        'keywords': '',
        'uploadSortId': this.sortId,
        'pageRows': this.dialogMode ? 9 : 20
      }
      getVideoListRequest(obj).then((res) => {
        if (res.error === 0) {
          // console.log(res.content.dataList.length)
          if (res.content.dataList.length === 0) {
            this.hasVideoData = false
            return
          }
          this.pageParams = res.content.page
          res.content.dataList.map((item, index) => {
            item.checked = false
            item.videoIndex = ''
          })
          this.videoList = res.content.dataList
          this.hasVideoData = true
        }
      })
    },

    upLoadVideo ({ file, maxVideoSize }) {
      if (file.size > maxVideoSize) {
        let msg = this.$t('videoSpace.upload.uploadFitVideo', { maxVideoSize: maxVideoSize })
        this.$message.error(msg)
        return
      }
      let _this = this
      let fd = new FormData()
      fd.append('file', file)
      fd.append('videoCatId', this.selectedCatId)
      uploadVideoRequest(fd).then((res) => {
        if (res.error === 0) {
          _this.searchVideos()
        }
      }).then(() => {
        _this.requestVersionAndSpaceInfo()
      })
    },
    // 视频上传前的钩子
    beforeUpLoad (file) {
      this.upLoadVideo({ file, maxVideoSize: 10 * 1024 * 1024 })
      return false // 停止上传
    },
    // 鼠标划入
    enter (index) {
      this.videoList[index].videoIndex = index
    },
    tip_over () {
      this.tip_hidden_flag = true
    },
    // 鼠标划出
    leave (index) {
      this.videoList[index].videoIndex = ''
    },
    // 头部问号说明
    tip_leave () {
      this.tip_hidden_flag = false
    },
    // 单视频选中
    onVideoClick (index) {
      this.videoList[index].checked = !this.videoList[index].checked
      this.allChecked = this.getCheckedIds().length === this.videoList.length ? '1' : ''
      if (this.videoList[index].checked) {
        this.$emit('video-click', this.videoList[index])
      }
    },
    // 全部选中
    onAllChecked () {
      this.videoList.map((item, index) => {
        item.checked = this.allChecked === '1'
      })
    },
    getCheckedIds () {
      let ids = []
      this.videoList.map((item, index) => {
        if (item.checked) ids.push(item.videoId)
      })
      return ids
    },
    confirmDeleteVideo (ids) {
      let _this = this
      this.$confirm(this.$t('vTree.delVideoTip'), this.$t('vTree.tip'), {
        confirmButtonText: this.$t('vTree.ok'),
        cancelButtonText: this.$t('vTree.cancel'),
        type: 'warning'
      }).then(() => {
        let obj = {
          videoIds: ids
        }
        batchDeleteVideoRequest(obj).then((res) => {
          if (res.error === 0) {
            _this.allChecked = false
            _this.searchVideos()
          }
        })
      }).then(() => {
        _this.requestVersionAndSpaceInfo()
      }).catch(() => { })
    },
    // 遮罩层删除点击
    delMaskVideo (data) {
      this.confirmDeleteVideo([data])
    },
    // 视频批量删除
    deleteVideos () {
      let checkedIds = this.getCheckedIds()
      if (checkedIds.length === 0) {
        this.$message(this.$t('videoSpace.upload.selectVideo'))
        return
      }
      this.confirmDeleteVideo(checkedIds)
    },
    // 视频批量移动
    handleMoveVideos () {
      let checkedIds = this.getCheckedIds()
      if (checkedIds.length === 0) {
        this.$message(this.$t('videoSpace.upload.selectVideo'))
        return
      }
      this.showBatchMoveDialog = true
      // console.log(this.checkArr)
    },
    // 视频批量移动下拉框确定事件
    batchMoveVideo () {
      let checkedIds = this.getCheckedIds()
      let query = {
        videoIds: checkedIds,
        videoCatId: this.selectMoveCatId
      }
      batchMoveVideoRequest(query).then((res) => {
        if (res.error === 0) {
          this.allChecked = false
          this.searchVideos()
        }
      })
      this.showBatchMoveDialog = false
    },
    // 视频查询--------------start
    searchVideos () {
      let obj = {
        'page': this.pageParams.currentPage || 1,
        'videoCatId': this.selectedCatId,
        'keywords': this.videoNameInput,
        'videoWidth': '',
        'videoHeight': '',
        'uploadSortId': this.sortId,
        'pageRows': this.dialogMode ? 9 : 20
      }
      getVideoListRequest(obj).then((res) => {
        if (res.error === 0) {
          if (res.content.dataList.length === 0) {
            this.hasVideoData = false
            return
          }
          console.log(res.content.dataList)
          this.pageParams = res.content.page
          res.content.dataList.map((item, index) => {
            item.checked = false
            item.videoIndex = ''

            // item.imgUrl = item.imgUrl.split('cn')[1]
            // // console.log(item.imgUrl)
          })

          this.videoList = res.content.dataList
          this.hasVideoData = true
        }
      })
    }
  }
}
</script>
<style scoped>
.img_sel {
  width: 18px;
  height: 18px;
  background: url(../../../assets/adminImg/img_sel.png) no-repeat;
  position: absolute;
  right: -9px;
  top: -9px;
  z-index: 1024;
}
.bottom_right_p {
  display: flex;
  height: 28px;
}
.bottom_radio {
  display: flex;
  align-items: center;
}
.system_info_content_top {
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
  line-height: 20px;
  color: #000;
  font-size: 14px;
  text-align: justify;
}
.system_info_content_bottom {
  padding-top: 7px;
}
.tip_hidden {
  position: absolute;
  top: 30px;
  left: 0;
  width: 300px;
  padding: 10px;
  box-shadow: 0px 0px 10px #f0f0f0;
  z-index: 100;
  background-color: #fff;
}
.title_tip {
  display: inline-block;
  line-height: 30px;
  min-height: 30px;
  color: #999;
  padding: 0 12px;
  font-size: 12px;
  margin-left: 20px;
  position: relative;
}
.title_tip img {
  margin-left: 3px;
  position: relative;
  top: 2px;
}
.dialog_top {
  padding: 15px 0;
}
.video_container {
  /*padding: 10px;*/
  /* padding-right: 23px; */
  min-width: 100%;
}
.imageDalog_p_height {
  display: flex;
  flex-direction: column;
  text-align: center;
}

.mask_flag {
  display: block !important;
}
.dim_flag {
  display: none !important;
}
.tips {
  margin-top: 10px;
  min-height: 40px;
  padding: 5px 20px;
  color: #666;
  font-size: 12px;
  line-height: 20px;
  background-color: #fff7eb;
  border: 1px solid #ffd5a3;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}
.tips img {
  margin-right: 10px;
}
.dialog_middle {
  /* height: 320px; */
  width: 100%;
  /*padding: 0 20px;*/
}
.dialog_middle_top {
  /* width: 24%; */
  height: 100%;
  display: flex;
}
.dialog_middle_top > div {
  height: 100%;
}
.dialog_middle_right_box {
  height: 100%;
  margin-left: 20px;
  width: 100%;
}
.right_top {
  margin-bottom: 10px;
  width: 570px;
}
.right_content {
  width: 100%;
}
ul {
  height: auto;
  overflow: hidden;
  padding-top: 5px;
}
.bottom {
  display: flex;
  font-size: 14px;
  justify-content: space-between;
  margin-top: 15px;
}
.totle {
  /* margin-top: 23px; */
  display: flex;
  align-items: center;
}
.totle span {
  display: block;
  white-space: nowrap;
}
.right_content li {
  position: relative;
  float: left;
  line-height: 24px;
  max-width: 240px;
  height: 100px;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-right: 20px;
  margin-top: 5px;
  margin-bottom: 10px;
}
.right_content li img {
  max-width: 140px;
  max-height: 80px;
  border: none;
  width: 140px;
  height: 80px;
  padding: 0;
  border-radius: 2px;
}
.img_dim {
  position: absolute;
  bottom: 20px;
  height: 40px;
  line-height: 20px;
  width: 100%;
  background: rgba(0, 0, 0, 0.3) !important;
  color: white;
  font-size: 12px;
  display: block;
}
.img-container {
  max-width: 140px;
  max-height: 80px;
  overflow: hidden;
}

.video-name {
  position: absolute;
  bottom: 0;
  height: 20px;
  line-height: 20px;
  width: 140px;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  font-size: 14px;
}
.img_mask {
  background: rgba(0, 0, 0, 0.3) !important;
  cursor: pointer;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  display: none;
}
.img_mask p {
  display: flex;
  justify-content: space-around;
}
.img_mask a {
  display: block;
  padding: 1px 2px 1px 2px;
  color: white;
  text-decoration: none;
}
.imageDalogTip_lineHeight {
  line-height: 14px !important;
}
</style>
<style>
.video_container .is-always-shadow {
  background-color: #e6e9f0;
}
.video_container .el-tree-node {
  background-color: #e6e9f0;
}
.video_container .tree_container {
  height: 360px !important;
}

.admin_imageDalog_totle {
  font-size: 12px;
  width: 260px;
}
.el-pagination__jump {
  margin-left: 0 !important;
}
.tree_container .is-always-shadow {
  width: 160px;
}
.dialog_middle_right_box .el-select {
  width: 155px !important;
  height: 30px !important;
}
.dialog_middle_right_box .el-input {
  width: 155px !important;
}
.right_content .el-input {
  width: 50px !important;
}
.right_content .el-pagination {
  margin-top: 0 !important;
  /* width: 400px !important; */
  padding: 0 !important;
}

.video_container .el-checkbox {
  margin: 0 10px !important;
}
.video_container .right_top .el-button--info {
  padding: 7px 15px !important;
  font-size: 12px !important;
  border-radius: 3px !important;
}
</style>
