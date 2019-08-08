<template>
  <div class="imageDalog">
    <div class="imageDalogMain">
      <el-dialog
        :title="$t('imgageDalog.title')"
        :visible.sync="dialogTableVisible"
        width="825px"
      >
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
                <el-checkbox v-model="checked">{{this.size}}px x {{this.size}}px</el-checkbox>
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
                          href="http://mpdevimg2.weipubao.cn/upload/4748160/image/20190708/crop_aeZqHE9BhNhWub8j.jpeg"
                          target="_blank"
                          title="显示原图"
                        >{{$t('imgageDalog.OriginalImg')}}</a>
                        <a
                          title="裁剪图片"
                          @click="handleCropper(item.imgPath,item.imgCatId,item.imgId)"
                        >
                          裁剪
                        </a>
                        <a
                          class="remove_image"
                          title="删除图片"
                          @click="delImg(item.imgId)"
                        >{{$t('imgageDalog.delImg')}}</a>
                      </p>
                    </div>
                    <div
                      class="img_dim"
                      :class="item.imgIndex === index?'dim_flag':''"
                    >
                      <p style="text-align:center">{{item.size}}</p>
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
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="dialogTableVisible = false">{{$t('imgageDalog.cancel')}}</el-button>
          <el-button
            type="primary"
            @click="dialogTableVisible = false"
          >{{$t('imgageDalog.Determine')}}</el-button>
        </span>
      </el-dialog>

    </div>
    <Cropper />
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import Tree from '@/components/admin/tree'
import Cropper from '@/components/admin/cropper'
import { queryHeadImgsRequest, upmoreHeadImgsRequest, imgsHeaddeleteRequest } from '@/api/admin/tree.js'
import { upmoreImgsRequest, queryImgsRequest, imgsdeleteRequest } from '@/api/admin/pictureSpace.js'
export default {
  components: { Tree, Cropper },
  props: ['pageIndex'],
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
      size: ''
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
    }
  },
  mounted () {
    // accountSettings组件控制本组件弹窗
    this.$http.$on('dtVisible', () => {
      console.log(123)
      this.dialogTableVisible = true
    })
    this.value = this.options[0].value
    // 初始化语言
    this.langDefault()
  },
  methods: {
    // 图片上传前的钩子
    beforeUpLoad (file) {
      console.log(file)
      let that = this
      console.log(this.firstNodeId)
      let is1M = file.size / 1024 / 1024 < 5 // 限制小于1M
      console.log(is1M)
      if (!is1M) this.$message.error('请上传小于5M的图片')

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
        switch (that.pageIndex) {
          case 'pictureSpace':
            upmoreImgsRequest(fd).then((res) => {
              console.log(res)
              if (res.error === 0) {
                that.queryImgs()
              }
            })
            break
          case 'imageDalog':
            upmoreHeadImgsRequest(fd).then((res) => {
              console.log(res)
              if (res.error === 0) {
                that.queryImgs()
              }
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

      let width = ''
      let height = ''
      let obj = ''
      switch (this.pageIndex) {
        case 'pictureSpace':
          this.size = 80
          if (this.checked === true) {
            width = 80
            height = 80
          } else {
            width = ''
            height = ''
          }
          obj = {
            'page': currentPage3,
            'imgCatId': this.firstNodeId,
            'keywords': this.imgNameInput,
            'searchNeed': 1,
            'pageRows': 8,
            'needImgWidth': width,
            'needImgHeight': height,
            'uploadSortId': this.value
          }
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
        case 'imageDalog':
          this.size = 52
          if (this.checked === true) {
            width = 52
            height = 52
          } else {
            width = ''
            height = ''
          }
          obj = {
            'page': currentPage3,
            'imgCatId': this.firstNodeId,
            'keywords': this.imgNameInput,
            'searchNeed': 1,
            'pageRows': 8,
            'needImgWidth': width,
            'needImgHeight': height,
            'uploadSortId': this.value
          }
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
    // 单图片选中
    handleChecked (index) {
      this.img_list[index].checked = !this.img_list[index].checked
      this.$emit('handleSelectImg', this.img_list[index].imgUrl)
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
    handleCropper (path, catid, imgid) {
      let obj = {
        path: path,
        catid: catid,
        imgid: imgid,
        index: 1
      }
      this.$store.commit('TOCHANGE_RECRUITMENTDIALOG', obj)
    }
  }
}
</script>
<style scoped>
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
}
.totle {
  margin-top: 23px;
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
