<template>
  <div class="pic_container">
    <div style="background-color:#fff;padding-bottom: 15px;">
      <!-- dialog_top -->
      <div class="dialog_top">
        <el-upload
          class="upload-demo"
          :before-upload="beforeUpLoad"
          multiple
          :limit="5"
          :on-exceed="handleExceed"
          :show-file-list="false"
          action=""
        >
          <el-button
            size="small"
            type="primary"
          >{{$t('imgageDalog.upload')}}</el-button>

          <div
            @mouseover="tip_over()"
            @mouseleave="tip_leave()"
            class="title_tip"
          >{{$t('imgsSpace.tipTitle')}}<img :src="tip_img">
            <div
              class="tip_hidden"
              v-if="tip_hidden_flag"
            >
              <div class="system_info_content_top">{{$t('imgsSpace.hiddleTitle')}}</div>
              <div class="system_info_content_bottom">
                <el-button
                  type="primary"
                  size="mini"
                >{{$t('imgsSpace.modeText')}}</el-button>
              </div>
            </div>
          </div>
          <div
            slot="tip"
            class="tips"
            :class="imageDalogTip_lineHeight"
          >
            <img :src="imgUrl[0].img_1">
            {{$t('imgageDalog.tip')}}</div>
        </el-upload>
      </div>
      <!-- dialog_middle -->
      <div class="dialog_middle">
        <div class="dialog_middle_top">
          <Tree
            pageIndex='pictureSpace'
            class="tree"
            @clickUserPic='clickUserPic'
          />
          <div class="dialog_middle_right_box">
            <div class="right_top">
              <el-select
                @change="filter"
                v-model="value"
                placeholder="请选择"
                size='mini'
              >
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  size='mini'
                >
                </el-option>
              </el-select>
              <el-input
                v-model="imgNameInput"
                :placeholder="$t('imgageDalog.imagePlaceholder')"
                size='mini'
              ></el-input>
              <el-button
                type="info"
                plain
                @click="detailImgsSearch()"
              >{{$t('imgageDalog.search')}}</el-button>

            </div>
            <div
              class="right_content"
              v-if="right_content_hidden"
            >
              <ul>
                <li
                  @mouseenter="enter(index)"
                  @mouseleave="leave(index)"
                  v-for="(item,index) in img_list"
                  :key="index"
                >
                  <div style="position:relative">
                    <a :title="item.imgName">
                      <img
                        :src="item.imgUrl"
                        @click="handleChecked(index)"
                      >
                    </a>
                    <div
                      v-show="item.checked"
                      class="img_sel"
                    ></div>
                  </div>
                  <div
                    class="img_mask"
                    :class="item.imgIndex === index?'mask_flag':''"
                  >
                    <p :class="imageDalog_p_height">
                      <a
                        class="old_pic"
                        :href="item.imgUrl"
                        target="_blank"
                        :title="$t('pageDecoration.displayTheOriginal')"
                      >{{$t('imgageDalog.OriginalImg')}}</a>
                      <a
                        :title="$t('pageDecoration.cutOutPictures')"
                        @click="handleCropper(item.imgPath,item.imgCatId,item.imgId,item.imgUrl,item.imgWidth,item.imgHeight)"
                      >
                        {{$t('pageDecoration.cropper')}}
                      </a>
                      <a
                        class="remove_image"
                        img_id="17"
                        img_width="52"
                        img_height="52"
                        img_path="upload/4748160/image/20190708/crop_aeZqHE9BhNhWub8j.jpeg"
                        :title="$t('pageDecoration.deletePictures')"
                        @click="delMaskImg(item.imgId)"
                      >{{$t('imgageDalog.delImg')}}</a>
                    </p>
                  </div>
                  <div
                    class="img_dim"
                    :class="item.imgIndex === index?'dim_flag':''"
                  >
                    <p style="text-align:center">{{item.imgWidth}}x{{item.imgHeight}}</p>
                  </div>
                </li>
              </ul>
              <div class="bottom">
                <div class="bottom_radio">
                  <el-checkbox
                    v-model="b_checked"
                    true-label="1"
                    false-label="0"
                    @change="allChecked()"
                  >{{$t('imgsSpace.allCheckedText')}}</el-checkbox>
                  <el-button
                    size="mini"
                    style="margin-right:5px"
                    @click="deleteImgs()"
                  >{{$t('imgsSpace.deleteImgsText')}}</el-button>
                  <el-button
                    size="mini"
                    @click="handleMoveimgs()"
                  >{{$t('imgsSpace.moveImgsText')}}</el-button>
                </div>
                <div class="bottom_right_p">
                  <div
                    class="totle"
                    :class="admin_imageDalog_totle"
                  >
                    <span>{{$t('imgageDalog.currentPage')}}{{this.currentPage}}/{{this.pageCount}},</span>
                    <span>{{$t('imgageDalog.totalPage')}}{{this.totalRows}}{{$t('imgageDalog.strip')}}</span>
                  </div>
                  <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page.sync="currentPage3"
                    :page-size="20"
                    layout="prev, pager, next, jumper"
                    :total="totalRows"
                    :small="pagination_b"
                  >
                  </el-pagination>
                </div>

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
              <p style="color:#999;font-size: 14px;text-align: center;margin-top: 15px">{{$t('imgsSpace.noneImgsText')}}</p>
            </div>
          </div>
        </div>

      </div>

    </div>

    <!--批量移动弹窗-->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <el-select
        v-model="value_move"
        placeholder="请选择"
      >
        <el-option
          v-for="item in options_move"
          :key="item.id"
          :label="item.name"
          :value="item.name"
        >
        </el-option>
      </el-select>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="allNodesSelectSure()"
        >确 定</el-button>
      </span>
    </el-dialog>
    <Cropper
      :allowToInput='true'
      @handleToResetPage='handleToResetPage'
    />
  </div>
</template>
<script>
import Cropper from '@/components/admin/cropper'
import { mapGetters } from 'vuex'
import Tree from '@/components/admin/tree'
import { imgsdeleteRequest, queryImgsRequest, moveImgsRequest, upmoreImgsRequest } from '@/api/admin/pictureSpace.js'
export default {
  components: { Tree, Cropper },
  data () {
    return {

      imgUrl: [
        {
          img_1: this.$imageHost + '/image/admin/notice_img.png'
        }
      ],
      options: [],
      value: '',
      imgNameInput: '',
      checked: false,
      currentPage3: 1,
      pagination_b: true,
      c_imgUrl: this.$imageHost + '/upload/0/image/20180528/i561Ez0lgWDeUOHe.jpeg!middle',
      dim_flag: 'dim_flag',
      mask_flag: 'mask_flag',
      img_list: [
        // { checked: false, imgIndex: '', imgUrl: this.$imageHost + '/upload/0/image/20180528/i561Ez0lgWDeUOHe.jpeg!middle', size: '52x52' },
        // { checked: false, imgIndex: '', imgUrl: this.$imageHost + '/upload/0/image/20180528/i561Ez0lgWDeUOHe.jpeg!middle', size: '72x72' }
      ],
      tip_img: this.$imageHost + '/image/admin/system_icon.png',
      imageDalogTip_lineHeight: '',
      imageDalog_p_height: '',
      admin_imageDalog_totle: '',
      tip_hidden_flag: false,
      b_checked: false,
      dialogVisible: false,
      options_move: [],
      value_move: '',
      allNodesName: [],
      firstNodeId: '',
      totalRows: null,
      currentPage: '',
      pageCount: '',
      right_content_hidden: true,
      checkArr: '',
      upImgWidth: '',
      upImgHeight: '',
      clickUserPicFlag: 1

    }
  },
  computed: {
    ...mapGetters(['clickNode', 'allNodes', 'picSpaceCropperFlag']),
    clickNode_ () {
      return this.clickNode
    },
    allNodes_ () {
      return this.allNodes
    },
    picSpaceCropperFlag_ () {
      return this.picSpaceCropperFlag
    }
  },
  watch: {
    clickNode_ (newData, oldData) {
      console.log(newData, 'ssss')
      this.Initialization_nodeClick(newData)
    },
    allNodes_ (newData, oldData) {
      console.log(newData)
      // 初始化图片查询数据
      if (newData.content) {
        this.Initialization_allTree(newData)
        this.firstNodeId = newData.content[0].id
      } else {
        this.firstNodeId = newData.id
      }

      this.queryImgs()
    },
    picSpaceCropperFlag_ (obj) {
      console.log(1111)
      this.queryImgs()
    },
    lang (newData) {
      console.log(newData)
      this.options = this.$t('options')
      this.value = this.options[0].value
      console.log(this.options)
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 选择器改变的时候筛选图片
    filter (val) {
      console.log(val)
      this.detailImgsSearch()
    },
    // 初始化点击节点数据
    Initialization_nodeClick (data) {
      // console.log(data)
    },
    Initialization_allTree (data) {
      let content = data.content[0]
      let obj = {
        name: content.name,
        id: content.id
      }
      this.value_move = content.name
      this.allNodesName.push(obj)
      this.handleAllNodes(content.child)
      // console.log(this.allNodesName)
      this.options_move = this.allNodesName
      // console.log(content)
    },
    // 处理所有节点数据、
    handleAllNodes (data) {
      // console.log(data)
      data.map((item, index) => {
        if (item.name !== '') {
          let obj = {
            name: item.name,
            id: item.id
          }
          this.allNodesName.push(obj)
        }
        // console.log(item.child.length)
        if (item.child.length !== 0) {
          this.handleAllNodes(item.child)
        }
      })
      // // console.log(item.chil)
    },
    // 图片分组查询
    queryImgs () {
      console.log('123123')
      // console.log(this.firstNodeId)
      let obj = {
        'page': 1,
        'imgCatId': this.firstNodeId,
        'keywords': '',
        'searchNeed': 0,
        'needImgWidth': '',
        'needImgHeight': '',
        'uploadSortId': this.value
      }
      // console.log(obj)
      queryImgsRequest(obj).then((res) => {
        if (res.error === 0) {
          // console.log(res.content.dataList.length)
          if (res.content.dataList.length === 0) {
            this.right_content_hidden = false
            return
          }

          res.content.dataList.map((item, index) => {
            // item.imgUrl = item.imgUrl.split("/")
            // console.log(item.imgUrl.split('cn')[1])
          })

          // console.log(res.content.page.totalRows)
          this.totalRows = res.content.page.totalRows
          this.currentPage = res.content.page.currentPage
          this.pageCount = res.content.page.pageCount
          res.content.dataList.map((item, index) => {
            item.checked = false
            item.imgIndex = ''

            // item.imgUrl = item.imgUrl.split('cn')[1]
            // // console.log(item.imgUrl)
          })
          this.img_list = res.content.dataList
          this.right_content_hidden = true
        }
      })
    },
    // 遮罩层删除点击
    delMaskImg (data) {
      // console.log(data)
      let obj = {
        imageIds: [data]
      }
      imgsdeleteRequest(obj).then((res) => {
        // console.log(res)
        if (res.error === 0) {
          this.detailImgsSearch()
        }
      })
    },
    upLoadImg (file) {
      console.log(this.firstNodeId)
      let is1M = file.size / 1024 / 1024 < 5 // 限制小于5M
      console.log(is1M)
      if (!is1M) {
        this.$message.error('请上传小于5M的图片')
        return false
      }
      let _URL = window.URL || window.webkitURL
      let img = new Image()
      let _this = this
      img.onload = function () {
        let fd = new FormData()
        fd.append('file', file)
        fd.append('needImgWidth', img.width)
        fd.append('needImgHeight', img.height)
        fd.append('imgCatId', _this.firstNodeId)
        localStorage.setItem('contentType', 'application/x-www-form-urlencoded;charset=UTF-8')
        upmoreImgsRequest(fd).then((res) => {
          console.log(res)
          if (res.error === 0) {
            localStorage.setItem('contentType', 'application/json;charset=UTF-8')
            _this.detailImgsSearch()
          }
        }).catch(err => {
          console.log(err)
          localStorage.setItem('contentType', 'application/json;charset=UTF-8')
        })
      }
      img.src = _URL.createObjectURL(file)
    },
    // 图片上传前的钩子
    beforeUpLoad (file) {
      console.log(file)
      let fileType = file.type.split('/')[0]
      if (fileType !== 'image') {
        this.$message.error('您上传的不是图片文件')
        return
      }
      this.upLoadImg(file)

      return false // 停止上传
    },
    // 文件数量超出限制钩子
    handleExceed () {
      this.$message.error('单次上传图片数量不能超过5张')
    },
    // pageSize 改变时会触发
    handleSizeChange () {

    },
    // currentPage 改变时会触发
    handleCurrentChange () {
      // console.log(this.currentPage3)
      let obj = {
        'page': this.currentPage3,
        'imgCatId': this.firstNodeId,
        'keywords': this.imgNameInput,
        'searchNeed': 0,
        'needImgWidth': '',
        'needImgHeight': '',
        'uploadSortId': this.value
      }
      queryImgsRequest(obj).then((res) => {
        if (res.error === 0) {
          this.detailImgsSearch(this.currentPage3)
        }
        // console.log(res)
      })
    },
    // 鼠标划入
    enter (index) {
      // // console.log(index)
      // this.mask_flag = !this.mask_flag
      // this.dim_flag = !this.dim_flag
      this.img_list[index].imgIndex = index
      // // console.log(this.img_list[index].imgIndex)
    },
    tip_over () {
      this.tip_hidden_flag = true
    },
    // 鼠标划出
    leave (index) {
      // this.mask_flag = !this.mask_flag
      // this.dim_flag = !this.dim_flag
      this.img_list[index].imgIndex = ''
      // // console.log(this.img_list[index].imgIndex)
    },
    // 头部问号说明
    tip_leave () {
      this.tip_hidden_flag = false
    },
    // 单图片选中
    handleChecked (index) {
      this.img_list[index].checked = !this.img_list[index].checked
    },
    // 全部选中
    allChecked () {
      // console.log(this.b_checked)
      this.img_list.map((item, index) => {
        if (this.b_checked === '1') {
          item.checked = true
        } else {
          item.checked = false
        }
      })
    },
    // 图片批量删除
    deleteImgs () {
      let checkArr = this.img_list.filter((item, index) => {
        return item.checked === true
      })
      if (checkArr.length === 0) {
        this.$message.error('请选择图片')
        return
      }
      // console.log(this.img_list)
      let arr = []
      this.img_list.map((item, index) => {
        if (item.checked) arr.push(item.imgId)
      })
      // console.log(arr)
      let obj = {
        imageIds: arr
      }
      imgsdeleteRequest(obj).then((res) => {
        // console.log(res)
        if (res.error === 0) {
          this.b_checked = false
          this.detailImgsSearch()
        }
      })
    },
    // 图片批量移动
    handleMoveimgs () {
      this.checkArr = this.img_list.filter((item, index) => {
        return item.checked === true
      })
      if (this.checkArr.length === 0) {
        this.$message.error('请选择图片')
        return
      }
      this.dialogVisible = true
      // console.log(this.checkArr)
    },
    // 图片批量移动下拉框确定事件
    allNodesSelectSure () {
      // console.log(this.checkArr)
      let arr = []
      this.checkArr.map((item, index) => {
        arr.push(item.imgId)
      })

      let obj = this.options_move.filter((item, index) => {
        return item.name === this.value_move
      })
      // console.log(obj[0].id)
      // console.log(this.value_move)
      let query = {
        imageIds: arr,
        imageCatId: obj[0].id
      }
      moveImgsRequest(query).then((res) => {
        // console.log(res)
        if (res.error === 0) {
          this.detailImgsSearch()
        }
      })
      this.dialogVisible = false
    },
    // 图片精确查询--------------start
    detailImgsSearch () {
      // console.log(this.value, '---', this.imgNameInput)
      // console.log(this.firstNodeId)
      // console.log(this.options)
      // let id = this.options.filter((item, index) => {
      //   // console.log(item.label)
      //   return item.label === this.value
      // })
      let pageIndex = ''
      if (this.currentPage3 !== 1) {
        pageIndex = this.currentPage3
      } else {
        pageIndex = 1
      }
      let firstNodeId = null
      if (this.clickUserPicFlag === -1) {
        firstNodeId = -1
      } else {
        firstNodeId = this.firstNodeId
      }
      console.log(firstNodeId)
      let obj = {
        'page': pageIndex,
        'imgCatId': firstNodeId,
        'keywords': this.imgNameInput,
        'searchNeed': 0,
        'needImgWidth': '',
        'needImgHeight': '',
        'uploadSortId': this.value
      }
      queryImgsRequest(obj).then((res) => {
        if (res.error === 0) {
          if (res.content.dataList.length === 0) {
            this.right_content_hidden = false
            return
          }
          console.log(res.content.dataList)
          this.totalRows = res.content.page.totalRows // 总行数
          this.currentPage = res.content.page.currentPage // 当前页码
          this.pageCount = res.content.page.pageCount // 总页数
          res.content.dataList.map((item, index) => {
            item.checked = false
            item.imgIndex = ''

            // item.imgUrl = item.imgUrl.split('cn')[1]
            // // console.log(item.imgUrl)
          })

          this.img_list = res.content.dataList
          this.right_content_hidden = true
        }
      })
    },
    // 图片精确查询--------------end
    // 裁剪弹窗调起
    handleCropper (path, catid, imgid, url, width, height) {
      let obj = {
        path: path,
        catid: catid,
        imgid: imgid,
        url: url,
        index: 2,
        imgWidth: width,
        imgHeight: height
      }
      console.log(1)
      this.$store.commit('TOCHANGE_RECRUITMENTDIALOG', obj)
    },
    clickUserPic (flag) {
      console.log(flag)
      this.clickUserPicFlag = flag
      // this.detailImgsSearch()
    },
    handleToResetPage () {
      this.currentPage3 = 1
      console.log('触发')
      this.detailImgsSearch()
    }
  }
}
</script>
<style scoped>
.img_sel {
  width: 18px;
  height: 18px;
  background: url(../../../../../../assets/adminImg/img_sel.png) no-repeat;
  position: absolute;
  right: -9px;
  top: -9px;
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
  height: 30px;
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
  padding: 15px 20px;
}
.pic_container {
  padding: 10px;
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
  height: 30px;
  line-height: 30px;
  padding: 0 20px;
  color: #666;
  font-size: 12px;
  background-color: #fff7eb;
  border: 1px solid #ffd5a3;
  display: flex;
  align-items: center;
  margin-top: 10px;
}
.tips img {
  margin-right: 10px;
}
.dialog_middle {
  /* height: 320px; */
  width: 100%;
  padding: 0 20px;
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
  max-width: 100px;
  max-height: 100px;
  border: none;
  width: 100px;
  height: 100px;
  padding: 0;
  border-radius: 2px;
}
.img_dim {
  position: absolute;
  bottom: 0;
  height: 25px;
  line-height: 28px;
  width: 100%;
  background: rgba(0, 0, 0, 0.3) !important;
  color: white;
  font-size: 12px;
  display: block;
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
.pic_container .is-always-shadow {
  background-color: #e6e9f0;
}
.pic_container .el-tree-node {
  background-color: #e6e9f0;
}
.pic_container .tree_container {
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
  overflow: auto;
}
.dialog_middle_right_box .el-select {
  width: 170px !important;
  height: 30px !important;
}
.dialog_middle_right_box .el-input {
  width: 170px !important;
}
.right_content .el-input {
  width: 50px !important;
}
.right_content .el-pagination {
  margin-top: none !important;
  /* width: 400px !important; */
  padding: none !important;
}

.pic_container .el-checkbox {
  margin: 0 10px !important;
}
.pic_container .right_top .el-button--info {
  padding: 7px 15px !important;
  font-size: 12px !important;
  border-radius: 3px !important;
}
</style>
