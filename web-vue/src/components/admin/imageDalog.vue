<template>
  <div class="imageDalog">
    <div class="imageDalogMain">
      <el-dialog
        :title="$t('imgageDalog.title')"
        :visible.sync="dialogTableVisible"
        width="825px"
        :append-to-body='true'
      >
        <Cropper
          @handleToResetPage='handleToResetPage'
          :imageSize='imageSize'
          :cropperFlagF='cropperFlagF'
          :cropperDialog='cropperDialogF'
        />
        <div class="dialog_top">
          <el-upload
            class="upload-demo"
            action=""
            :before-upload="beforeUpLoad"
            multiple
            :limit="5"
            :show-file-list="false"
            :on-exceed="handleExceed"
          >
            <el-button
              size="small"
              type="primary"
            >{{$t('imgageDalog.upload')}}</el-button>
            <div
              slot="tip"
              class="tips"
              :class="imageDalogTip_lineHeight"
            >
              <img :src="imgUrl[0].img_1">
              {{$t('imgageDalog.tip')}}</div>
          </el-upload>
        </div>
        <div class="dialog_middle">
          <div class="dialog_middle_top">
            <Tree :pageIndex='pageIndex' />
            <div class="dialog_middle_right_box">
              <div class="right_top">
                <el-select
                  v-model="value"
                  placeholder="请选择"
                  size='mini'
                >
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
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
                  size="mini"
                  @click="handleSearch()"
                >{{$t('imgageDalog.search')}}</el-button>
                <el-checkbox
                  v-if="imageSize.length"
                  v-model="checked"
                >{{this.sizeW}}px x {{this.sizeH}}px</el-checkbox>
              </div>
              <div class="right_content">
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
                          :title="$t('imgageDalog.displayOriginalGraph')"
                        >{{$t('imgageDalog.OriginalImg')}}</a>
                        <a
                          :title="$t('imgageDalog.cutPictures')"
                          @click="handleCropper(item.imgPath,item.imgCatId,item.imgId,item.imgUrl,item.imgWidth,item.imgHeight)"
                        >
                          {{$t('imgageDalog.tailoring')}}
                        </a>
                        <a
                          class="remove_image"
                          :title="$t('imgageDalog.deletePictures')"
                          @click="delImg(item.imgId)"
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
                  <div
                    class="totle"
                    :class="admin_imageDalog_totle"
                  >
                    <span>{{$t('imgageDalog.currentPage')}}{{this.currentPage}}/{{this.pageCount}},</span>
                    <span>{{$t('imgageDalog.totalPage')}}{{this.totalRows}}{{$t('imgageDalog.strip')}}</span>
                  </div>
                  <el-pagination
                    @current-change="handleCurrentChange"
                    :current-page.sync="currentPage3"
                    :page-size="8"
                    layout="prev, pager, next, jumper"
                    :total="totalRows"
                    :small="pagination_b"
                  >
                  </el-pagination>
                </div>
              </div>
            </div>
          </div>

        </div>
        <div
          v-if="isDraggable"
          class="selectImgList"
        >
          <p>已选{{backArr.length}}长图片,拖动可修改插入顺序</p>
          <div class="selectImgContainer">
            <draggable
              class="list-group"
              element="div"
              v-model="backArr"
              :options="dragOptions"
            >

              <div
                class="selectList"
                @mouseenter='backArr[index].imgIndex = index'
                @mouseleave="backArr[index].imgIndex = ''"
                v-for="(item,index) in backArr"
                :key="index"
              >
                <div
                  class="imgDit"
                  v-if="backArr[index].imgIndex === index"
                >
                  <a
                    target="_blank"
                    title="显示原图"
                    style="cursor:pointer"
                    :href="item.imgUrl"
                  >原</a>
                  <a
                    href="javascript:void(0)"
                    title="移出图片"
                    style="cursor:pointer"
                    @click="handleToDelDraggableData(item,index)"
                  >X</a>
                </div>
                <img :src="item.imgUrl">
                <div
                  class="imgDim"
                  v-if="backArr[index].imgIndex !== index"
                ></div>
              </div>

            </draggable>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="dialogTableVisible = false"
          >{{$t('imgageDalog.cancel')}}</el-button>
          <el-button
            type="primary"
            size="small"
            @click="handleToSure()"
          >{{$t('imgageDalog.Determine')}}</el-button>
        </span>
      </el-dialog>

    </div>

  </div>
</template>
<script>
import draggable from 'vuedraggable'
import { mapGetters } from 'vuex'
import Tree from '@/components/admin/tree'
import Cropper from '@/components/admin/cropper'
import { queryHeadImgsRequest, upmoreHeadImgsRequest, imgsHeaddeleteRequest } from '@/api/admin/tree.js'
import { upmoreImgsRequest, queryImgsRequest, imgsdeleteRequest } from '@/api/admin/pictureSpace.js'
export default {
  components: { Tree, Cropper, draggable },
  props: {
    pageIndex: String, // 数据库选择
    tuneUp: { // 调起弹窗
      type: Boolean,
      default: () => false
    },
    isDraggable: { // 开启多选
      type: Boolean,
      default: () => false
    },
    imageSize: { // 限制图片宽高
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      dialogTableVisible: false,
      imgUrl: [
        {
          img_1: this.$imageHost + '/image/admin/notice_img.png'
        }
      ],
      options: this.$t('options'),
      value: '',
      imgNameInput: '',
      checked: false,
      currentPage3: 1,
      pagination_b: true,
      c_imgUrl: this.$imageHost + '/upload/0/image/20180528/i561Ez0lgWDeUOHe.jpeg!middle',
      dim_flag: 'dim_flag',
      mask_flag: 'mask_flag',
      img_list: [
        // { imgName: 'test1', checked: false, imgIndex: '', imgUrl: this.$imageHost + '/upload/0/image/20180528/i561Ez0lgWDeUOHe.jpeg!middle', size: '52x52' },
        // { imgName: 'test2', checked: false, imgIndex: '', imgUrl: this.$imageHost + '/upload/0/image/20180528/i561Ez0lgWDeUOHe.jpeg!middle', size: '72x72' }
      ],
      imageDalogTip_lineHeight: '',
      imageDalog_p_height: '',
      admin_imageDalog_totle: '',
      firstNodeId: '',
      totalRows: null,
      currentPage: '',
      pageCount: '',
      sizeW: '',
      sizeH: '',
      width: '',
      height: '',
      backArr: [],
      showMask: true,
      dragOptions: {
        sort: true,
        fallbackTolerance: '1',
        scroll: true,
        animation: 300
      },
      cropperFlagF: null,
      cropperDialogF: false
    }
  },
  computed: {
    ...mapGetters(['allNodes', 'activeFresh']),
    allNodes_ () {
      return this.allNodes
    },
    activeFresh_ () {
      return this.activeFresh
    }
  },
  watch: {

    allNodes_ (newData, oldData) {
      console.log(newData)
      // 初始化图片查询数据
      if (newData.content) {
        this.firstNodeId = newData.content[0].id
      } else {
        this.firstNodeId = newData.id
      }

      this.queryImgs()
    },
    activeFresh_ (data) {
      console.log(data, 11111)
      this.queryImgs()
    },
    tuneUp (newData) {
      console.log(newData)

      this.img_list.forEach((item, index) => {
        item.checked = false
      })
      this.backArr = []
      this.checked = false
      this.queryImgs()
      this.dialogTableVisible = true
    },
    imageSize: {
      handler: function (newData) {
        if (newData.length) {
          this.sizeW = newData[0]
          this.sizeH = newData[1]
        } else {
          this.sizeW = ''
          this.sizeH = ''
        }
      },
      immediate: true
    },
    checked (newData) {
      this.queryImgs()
    },
    '$store.getters.picSpaceCropperFlag' () {
      this.queryImgs()
    },
    dialogTableVisible (newData) {
      if (!newData) {
        document.querySelector('body').setAttribute('style', 'overflow: auto')
      }
    }
  },
  mounted () {
    console.log(this.$store.getters.picSpaceCropperFlag, 'ssdsdsd')
    this.value = this.options[0].value
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 裁剪完成后重置当前页
    handleToResetPage () {
      this.currentPage3 = 1
    },
    // 图片上传前的钩子
    beforeUpLoad (file) {
      console.log(file)
      let that = this
      let fileType = file.type.split('/')[0]
      if (fileType !== 'image') {
        this.$message.error('您上传的不是图片文件')
        return
      }
      console.log(this.firstNodeId)
      let is1M = file.size / 1024 / 1024 < 5 // 限制小于5M
      console.log(is1M)
      if (!is1M) {
        this.$message.error('请上传小于5M的图片')
        return false
      }

      // let width = 654 // 限制图片尺寸为654X270
      // let height = 270
      let _URL = window.URL || window.webkitURL
      let img = new Image()

      img.onload = function () {
        console.log(img.width, img.height)
        // this.upImgWidth = img.width
        // this.upImgHeight = img.height
        console.log(that.firstNodeId)
        let fd = new FormData()
        // console.log(fd)
        console.log(file)
        fd.append('file', file)
        fd.append('needImgWidth', img.width)
        fd.append('needImgHeight', img.height)
        fd.append('imgCatId', that.firstNodeId)
        localStorage.setItem('contentType', 'application/x-www-form-urlencoded;charset=UTF-8')
        switch (that.pageIndex) {
          case 'pictureSpace':
            upmoreImgsRequest(fd).then((res) => {
              console.log(res)
              if (res.error === 0) {
                localStorage.setItem('contentType', 'application/json;charset=UTF-8')
                that.queryImgs()
              }
            }).catch(error => {
              localStorage.setItem('contentType', 'application/json;charset=UTF-8')
              console.log(error)
            })
            break
          case 'imageDalog':
            upmoreHeadImgsRequest(fd).then((res) => {
              console.log(res)
              if (res.error === 0) {
                localStorage.setItem('contentType', 'application/json;charset=UTF-8')
                that.queryImgs()
              }
            }).catch(error => {
              localStorage.setItem('contentType', 'application/json;charset=UTF-8')
              console.log(error)
            })
            break
        }
        // let valid = img.width === width && img.height === height
      }
      img.src = _URL.createObjectURL(file)

      return false
    },
    // 文件数量超出限制钩子
    handleExceed () {
      this.$message.error('单次上传图片数量不能超过5张')
    },
    // currentPage 改变时会触发
    handleCurrentChange () {
      this.queryImgs(this.currentPage3)
    },
    // 点击搜索
    handleSearch () {
      this.queryImgs(1)
    },
    // 图片分组查询
    queryImgs (currentPage3) {
      console.log(this.firstNodeId)
      console.log(this.value)
      console.log(this.checked)
      let width, height
      // let { width, height } = this.handleToImgSize()
      if (this.checked === true) {
        width = this.sizeW
        height = this.sizeH
      } else {
        width = ''
        height = ''
      }

      console.log(this.pageIndex)
      switch (this.pageIndex) {
        case 'pictureSpace':
          console.log(123)
          this.handleToQueryImgsData(currentPage3, width, height, 0)
          break
        case 'userCardAdd':
          console.log(123)
          this.handleToQueryImgsData(currentPage3, width, height, 0)
          break
        case 'imageDalog':
          this.handleToQueryImgsData(currentPage3, width, height, 1)
      }
    },
    // 图片分组查询数据处理函数
    handleToQueryImgsData (currentPage3, width, height, flag) {
      let obj = ''
      obj = {
        'page': currentPage3,
        'imgCatId': this.firstNodeId,
        'keywords': this.imgNameInput,
        'searchNeed': 1,
        'pageRows': 10,
        'needImgWidth': width,
        'needImgHeight': height,
        'uploadSortId': this.value
      }
      switch (flag) {
        case 0:
          this.cropperFlagF = 1
          queryImgsRequest(obj).then((res) => {
            console.log(res)
            if (res.error === 0) {
              this.totalRows = res.content.page.totalRows
              this.currentPage = res.content.page.currentPage
              this.pageCount = res.content.page.pageCount
              res.content.dataList.map((item, index) => {
                item.checked = false
                item.imgIndex = ''

                // item.imgUrl = item.imgUrl.split('cn')[1]
                // console.log(item.imgUrl)
              })
              this.img_list = res.content.dataList
              console.log(this.img_list, 1)
            }
          })
          break
        case 1:
          this.cropperFlagF = 0
          queryHeadImgsRequest(obj).then((res) => {
            console.log(res)
            if (res.error === 0) {
              this.totalRows = res.content.page.totalRows
              this.currentPage = res.content.page.currentPage
              this.pageCount = res.content.page.pageCount
              res.content.dataList.map((item, index) => {
                item.checked = false
                item.imgIndex = ''

                // item.imgUrl = item.imgUrl.split('cn')[1]
                // console.log(item.imgUrl)
              })
              this.img_list = res.content.dataList
              console.log(this.img_list, 1)
            }
          })
      }
    },
    // 单张图片删除
    delImg (data) {
      console.log(data)
      let obj = {
        imageIds: [data]
      }
      switch (this.pageIndex) {
        case 'pictureSpace':
          imgsdeleteRequest(obj).then((res) => {
            console.log(res)
            if (res.error === 0) {
              this.queryImgs(this.currentPage3)
            }
          })
          break
        case 'imageDalog':
          imgsHeaddeleteRequest(obj).then((res) => {
            console.log(res)
            if (res.error === 0) {
              this.queryImgs(this.currentPage3)
            }
          })
      }
    },
    // 图片选中
    handleChecked (index) {
      if (this.sizeW && this.sizeH) {
        if (this.img_list[index].imgWidth !== this.sizeW || this.img_list[index].imgHeight !== this.sizeH) {
          this.$message.error({
            message: '图片宽高不符合要求',
            showClose: true
          })
          return
        }
      }

      this.img_list[index].checked = !this.img_list[index].checked
      console.log(this.img_list[index])
      if (this.isDraggable) {
        console.log(this.img_list[index])
        let data = JSON.parse(JSON.stringify(this.img_list[index]))
        data.imgIndex = null
        if (data.checked) {
          this.backArr.push(data)
        } else {
          console.log(data)
          let delIndex = ''
          this.backArr.forEach((item, index) => {
            if (item.imgId === data.imgId) {
              delIndex = index
            }
          })
          this.backArr.splice(delIndex, 1)
        }

        console.log(this.backArr)
      } else {
        this.$emit('handleSelectImg', this.img_list[index])
        this.dialogTableVisible = false
      }
    },
    // 弹窗底部拖拽部分点击图片右上角删除icon
    handleToDelDraggableData (item, index) {
      this.backArr.splice(index, 1)
      this.img_list.forEach((itemC, indexC) => {
        if (itemC.imgId === item.imgId) {
          console.log(111)
          itemC.checked = false
        }
      })

      console.log(item, index, this.img_list)
    },
    // 弹窗点击确定事件
    handleToSure () {
      if (this.isDraggable) {
        this.$emit('handleSelectImg', this.backArr)
      }
      this.dialogTableVisible = false
    },
    // 鼠标划入
    enter (index) {
      console.log(index)
      // this.mask_flag = !this.mask_flag
      // this.dim_flag = !this.dim_flag
      this.img_list[index].imgIndex = index
      console.log(this.img_list[index].imgIndex)
    },
    // 鼠标划出
    leave (index) {
      // this.mask_flag = !this.mask_flag
      // this.dim_flag = !this.dim_flag
      this.img_list[index].imgIndex = ''
      console.log(this.img_list[index].imgIndex)
    },
    // 裁剪弹窗调起
    handleCropper (path, catid, imgid, url, imgWidth, imgHeight) {
      let obj = {
        path: path,
        catid: catid,
        imgid: imgid,
        url: url,
        index: 1,
        imgWidth: imgWidth,
        imgHeight: imgHeight
      }
      console.log(1)
      this.$store.commit('TOCHANGE_RECRUITMENTDIALOG', obj)
    }
  }
}
</script>
<style scoped>
.dialog_top {
  margin-top: 10px;
}
.img_sel {
  width: 18px;
  height: 18px;
  background: url(../../assets/adminImg/img_sel.png) no-repeat;
  position: absolute;
  right: -9px;
  top: -9px;
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
  height: 30px;
  line-height: 30px;
  padding: 0 20px;
  color: #666;
  font-size: 12px;
  background-color: #fff7eb;
  border: 1px solid #ffd5a3;
  display: flex;
  align-items: center;
}
.tips img {
  margin-right: 10px;
}
.dialog_middle {
  height: 320px;
  margin-top: 15px;
  width: 100%;
}
.dialog_middle_top {
  width: 24%;
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
  width: 618px;
}
ul {
  height: 230px;
}
.bottom {
  display: flex;
  padding-top: 23px;
  justify-content: flex-end;
}
.totle {
  display: flex;
  align-items: center;
}
.totle span {
  display: block;
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
.selectImgList {
  width: 100%;
  margin-top: 3px;
  padding: 3px 0;
  min-height: 70px;
}
.selectImgList p {
  background: #eee;
  padding: 5px 0;
}
.selectImgContainer {
  background: #fff;
  min-height: 60px;
  padding-bottom: 5px;
  overflow-y: hidden;
  overflow-x: auto;
  white-space: nowrap;
}
.selectList {
  width: 50px;
  height: 50px;
  margin: 5px;
  position: relative;
  display: inline-block;
  cursor: Move;
}
.selectList img {
  width: 50px;
  height: 50px;
}
.selectList .imgDit {
  position: absolute;
  top: 0;
  height: 25px;
  background: rgba(0, 0, 0, 0.3);
  width: 100%;
  color: #fff;
  display: flex;
  justify-content: space-between;
  padding: 3px 3px 0 3px;
}
.selectList .imgDit a {
  color: #fff;
  text-decoration: none;
}
.selectList .imgDim {
  position: absolute;
  bottom: 0;
  height: 25px;
  background: rgba(0, 0, 0, 0.3);
  width: 100%;
}
</style>
<style>
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
.imageDalog .right_content .el-pagination {
  margin-top: 23px;
  width: 300px !important;
}
.imageDalog .el-button {
  padding: 7px 15px !important;
  font-size: 12px !important;
  border-radius: 3px !important;
}
.imageDalog .imageDalogMain .el-input__inner {
  width: 155px !important;
}
.imageDalog .imageDalogMain .el-dialog__body {
  padding: 20px 20px !important;
}
.imageDalog .imageDalogMain .el-pagination__editor .el-input__inner {
  width: 40px !important;
}

/* .el-popper[x-placement^="bottom"] {
  margin-top: 10px !important;
} */
</style>
