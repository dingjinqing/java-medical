<template>
  <div class="selectTemplateDialog">
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="716px"
    >
      <div class="selectTemplateDialogMain">
        <div class="main">
          <div
            class="template_li"
            v-for="(item,index) in dialogData"
            :key="index"
          >
            <div
              class="template_li_main"
              @mouseenter="enter(index)"
              @mouseleave="leave(index)"
            >
              <div class="width:150px;height:210px">
                <img :src="item.imgUrl">
                <div
                  class="template_li_hidden"
                  :class="item.flag === index?'hiddleFlag':''"
                >
                  <div class="hidden_main">
                    <el-button
                      type="primary"
                      size="small"
                      @click="handleToDecPage(item)"
                    >{{index===0?'自定义':'使用模板'}}</el-button>
                  </div>

                </div>
              </div>

              <div class="title">{{item.title}}</div>
            </div>

          </div>
        </div>
      </div>

    </el-dialog>
  </div>
</template>
<script>
import { getTemplatesData } from '@/api/admin/smallProgramManagement/pictureSetting/pictureSetting'
export default {
  props: {
    tuneUpMiniPage: Boolean
  },
  data () {
    return {
      dialogVisible: false,
      dialogData: [
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/shop_decorate_module1.jpg',
          title: '自定义模板',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '顶部导航',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '订单',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/upload/0/image/crop_iwpm2uCcmvqt8TTk.jpeg',
          title: '图书（不可修改）',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '食品（不可修改）',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '女装（不可修改）',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '美妆（模板不可修改）',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '顶部导航',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '测试213',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '教育',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '摄影',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '顶部导航',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '汽车4s店',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '数码',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '美发',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '鲜花',
          flag: ''
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_beautify/tpl_type1.jpg',
          title: '化妆品专卖',
          flag: ''
        }
      ]
    }
  },
  computed: {
    Micropage_ () {
      return this.Micropage
    }
  },
  watch: {
    Micropage_ (newData) {
      if (newData === null) {
        this.dialogVisible = false
      } else {
        this.dialogVisible = true
      }

      console.log(newData)
    },
    dialogVisible (newData) {
      console.log(newData)
      if (!newData) {
        this.$emit('update:tuneUpMiniPage', false)
      }
    },
    tuneUpMiniPage (newData) {
      if (newData) {
        this.dialogVisible = true
      }
    }
  },
  mounted () {
    // 初始获取模板数据
    this.handleToInitData()
  },
  methods: {
    handleToInitData () {
      getTemplatesData().then(res => {
        console.log(res)
      })
    },
    // 鼠标移入
    enter (index) {
      console.log(index)
      this.dialogData[index].flag = index
      console.log(this.dialogData)
    },
    // 鼠标移出
    leave (index) {
      console.log(index)
      this.dialogData[index].flag = ''
    },
    // 跳转到装修主页
    handleToDecPage (item) {
      console.log(item)
      this.$router.push({
        path: '/admin/home/main/decorationHome',
        query: {
          pageId: -1
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.selectTemplateDialog {
  /deep/ .el-dialog__header {
    text-align: center;
    background-color: #f5f5f5;
    padding: 10px 20px 10px;
    span {
      font-size: 14px;
    }
  }
  /deep/ .el-dialog__body {
    padding: 10px 10px 0 20px;
    background-color: #e6e9f0;
  }
  .main {
    overflow-y: auto;
    height: 500px;
    .template_li {
      float: left;
      border: 1px solid #f1f1f1;
      margin-right: 15px;
      margin-bottom: 15px;
      position: relative;

      .title {
        width: 150px;
        height: 35px;
        line-height: 35px;
        text-align: center;
        background: #f3f3f3;
        color: #666;
        font-size: 12px;
        position: relative;
        top: -2px;
      }
      .template_li_hidden {
        display: none;
        position: absolute;
        top: 0px;
        left: 0px;
        width: 100%;
        height: 210px;
        background: rgba(0, 0, 0, 0.5);

        .hidden_main {
          height: 100%;
          display: flex;
          justify-content: center;
          align-items: center;
        }
      }
      .hiddleFlag {
        display: block !important;
      }
    }
  }
}
</style>
