<template>
  <div>
    <el-dialog
      title="浏览图片"
      :visible.sync="dialogTableVisible"
      width="825px"
    >
      <div class="dialog_top">
        <el-upload
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-remove="handleRemove"
          :on-success="handleSuccess"
          :before-remove="beforeRemove"
          multiple
          :limit="3"
          :on-exceed="handleExceed"
        >
          <el-button
            size="small"
            type="primary"
          >上传图片</el-button>
          <div
            slot="tip"
            class="tips"
          >
            <img :src="imgUrl[0].img_1">
            上传图片支持jpeg、jpg、png、bmp格式，为保障前端加载顺利，单张图片大小不能超过5M</div>
        </el-upload>
      </div>
      <div class="dialog_middle">
        <div class="dialog_middle_top">
          <Tree />
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
                placeholder="请输入图片名称"
                size='mini'
              ></el-input>
              <el-button
                type="info"
                plain
                size="mini"
              >搜索</el-button>
              <el-checkbox v-model="checked">52px x 52px</el-checkbox>
            </div>
            <div class="right_content">
              <ul>
                <li
                  @mouseenter="enter(index)"
                  @mouseleave="leave(index)"
                  v-for="(item,index) in img_list"
                  :key="index"
                >

                  <div>
                    <img :src="item.imgUrl">
                  </div>
                  <div
                    class="img_mask"
                    :class="item.imgIndex === index?'mask_flag':''"
                  >
                    <p>
                      <a
                        class="old_pic"
                        href="http://mpdevimg2.weipubao.cn/upload/4748160/image/20190708/crop_aeZqHE9BhNhWub8j.jpeg"
                        target="_blank"
                        title="显示原图"
                      >原图</a>
                      <a
                        class="remove_image"
                        url="http://mpdevimg2.weipubao.cn/upload/4748160/image/20190708/crop_aeZqHE9BhNhWub8j.jpeg"
                        img_id="17"
                        img_width="52"
                        img_height="52"
                        img_path="upload/4748160/image/20190708/crop_aeZqHE9BhNhWub8j.jpeg"
                        title="删除图片"
                      >删除</a>
                    </p>
                  </div>
                  <div
                    class="img_dim"
                    :class="item.imgIndex === index?'dim_flag':''"
                  >
                    <p style="text-align:center">{{item.size}}</p>
                  </div>
                </li>

                <!-- <li
                  @mouseenter="enter(index)"
                  @mouseleave="leave(index)"
                >

                  <div>
                    <img :src="c_imgUrl">
                  </div>
                  <div
                    class="img_mask"
                    v-show="mask_flag"
                  >
                    <p>
                      <a
                        class="old_pic"
                        href="http://mpdevimg2.weipubao.cn/upload/4748160/image/20190708/crop_aeZqHE9BhNhWub8j.jpeg"
                        target="_blank"
                        title="显示原图"
                      >原图</a>
                      <a
                        class="remove_image"
                        url="http://mpdevimg2.weipubao.cn/upload/4748160/image/20190708/crop_aeZqHE9BhNhWub8j.jpeg"
                        img_id="17"
                        img_width="52"
                        img_height="52"
                        img_path="upload/4748160/image/20190708/crop_aeZqHE9BhNhWub8j.jpeg"
                        title="删除图片"
                      >删除</a>
                    </p>
                  </div>
                  <div
                    class="img_dim"
                    v-show="dim_flag"
                  >
                    <p style="text-align:center">52x52</p>
                  </div>
                </li> -->
              </ul>
              <div class="bottom">
                <div class="totle">
                  <span>当前页面1/2,</span>
                  <span>总记录12条</span>
                </div>
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page.sync="currentPage3"
                  :page-size="100"
                  layout="prev, pager, next, jumper"
                  :total="1000"
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
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="dialogTableVisible = false"
        >确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import Tree from '@/components/admin/tree'
export default {
  components: { Tree },
  data () {
    return {
      dialogTableVisible: false,
      imgUrl: [
        {
          img_1: this.$imageHost + '/image/admin/notice_img.png'
        }
      ],
      options: [{
        value: '选项1',
        label: '按上传时间从晚到早'
      }, {
        value: '选项2',
        label: '按上传时间从早到晚'
      }, {
        value: '选项3',
        label: '按图片从大到小'
      }, {
        value: '选项4',
        label: '按图片从小到大'
      }, {
        value: '选项5',
        label: '按图片名降序'
      }, {
        value: '选项6',
        label: '按图片名升序'
      }],
      value: '',
      imgNameInput: '',
      checked: false,
      currentPage3: 5,
      pagination_b: true,
      c_imgUrl: this.$imageHost + '/upload/0/image/20180528/i561Ez0lgWDeUOHe.jpeg!middle',
      dim_flag: 'dim_flag',
      mask_flag: 'mask_flag',
      img_list: [
        { imgIndex: '', imgUrl: this.$imageHost + '/upload/0/image/20180528/i561Ez0lgWDeUOHe.jpeg!middle', size: '52x52' },
        { imgIndex: '', imgUrl: this.$imageHost + '/upload/0/image/20180528/i561Ez0lgWDeUOHe.jpeg!middle', size: '72x72' }
      ]
    }
  },
  mounted () {
    // accountSettings组件控制本组件弹窗
    this.$http.$on('dtVisible', () => {
      this.dialogTableVisible = true
    })
    this.value = this.options[0].label
  },
  methods: {

    // 文件上传成功后的钩子
    handleSuccess () {

    },
    // 删除前的钩子
    beforeRemove () {

    },
    // 图片删除钩子
    handleRemove () {

    },
    // 文件数量超出限制钩子
    handleExceed () {

    },
    // pageSize 改变时会触发
    handleSizeChange () {

    },
    // currentPage 改变时会触发
    handleCurrentChange () {

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
    }
  }
}
</script>
<style scoped>
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
</style>
<style>
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
  margin-top: 23px;
  width: 400px !important;
}
</style>
