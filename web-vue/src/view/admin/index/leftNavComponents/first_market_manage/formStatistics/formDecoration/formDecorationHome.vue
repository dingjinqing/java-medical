<template>
  <div class="formDecorationHome">
    <div class="main">
      <div class="decLeft">
        <div class="decLeftDrag">
          <div class="title">
            表单元素
          </div>
          <div class="dragContainer">
            <div
              class="dragLi"
              v-for="item in formElements"
              :key="item.id"
              :dataId="item.id"
            >
              <img :src="item.imgUrl">
              <span>{{item.text}}</span>
            </div>
          </div>
        </div>
        <div class="decLeftDrag">
          <div class="title">
            表单元素
          </div>
          <div class="dragContainer">
            <div
              class="dragLi"
              v-for="item in graphicCategory"
              :key="item.id"
              :dataId="item.id"
            >
              <img :src="item.imgUrl">
              <span>{{item.text}}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="decMiddle">
        decMiddle
      </div>
      <div class="decRight">
        decRight
      </div>
    </div>
    <!--保存-->
    <div class="footer">
      <div>
        <el-button
          type="primary"
          size="small"
        >{{$t('pageSetUp.saveAndPublish')}}</el-button>
        <el-button size="small">{{$t('pageSetUp.saveAsDraft')}}</el-button>
        <el-button size="small">{{$t('pageSetUp.previewEffect')}}</el-button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      formElements: [
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_name.png',
          text: '姓名',
          id: 1,
          flag: true,
          name: 'm_input_name'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/drag_phone.png',
          text: '手机号',
          id: 1,
          flag: true,
          name: 'm_input_mobile'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '省市区',
          id: 1,
          flag: true,
          name: 'm_address'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '邮箱',
          id: 1,
          flag: true,
          name: 'm_input_email'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '性别',
          id: 1,
          flag: true,
          name: 'm_sex'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '下拉',
          id: 1,
          flag: true,
          name: 'm_slide'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '输入框',
          id: 1,
          flag: true,
          name: 'm_input_text'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '选项',
          id: 1,
          flag: true,
          name: 'm_choose'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '日期',
          id: 1,
          flag: true,
          name: 'm_dates'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '图片上传',
          id: 1,
          flag: true,
          name: 'm_imgs'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '视频上传',
          id: 1,
          flag: true,
          name: 'm_upload_video'
        }
      ],
      graphicCategory: [
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '轮播图',
          id: 1,
          flag: true,
          name: 'm_scroll_image'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '富文本',
          id: 1,
          flag: true,
          name: 'm_rich_text'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '图片广告',
          id: 1,
          flag: true,
          name: 'm_image_small'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '文本模块',
          id: 1,
          flag: true,
          name: 'm_text'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '辅助线',
          id: 1,
          flag: true,
          name: 'm_dashed_line'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '辅助空白',
          id: 1,
          flag: true,
          name: 'm_blank'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '电话模块',
          id: 1,
          flag: true,
          name: 'm_phone'
        },
        {
          imgUrl: this.$imageHost + '/image/admin/shop_deco/icon_area.png',
          text: '公众号',
          id: 1,
          flag: true,
          name: 'm_official_accounts'
        }
      ]
    }
  }
}
</script>
<style lang="scss" scoped>
.formDecorationHome {
  padding: 10px;
  .main {
    position: relative;
    background-color: #fff;
    padding: 10px 20px 53px 20px;
    display: flex;
    justify-content: flex-start;
    .decLeft {
      width: 236px;
      .decLeftDrag {
        background-color: #fff;
        border: 1px solid transparent;
        border-color: #ddd;
        margin-bottom: 5px;
        .title {
          padding: 7px 14px;
          font-size: 14px;
          color: #333;
          background-color: #f5f5f5;
          border-color: #ddd;
        }
        .dragContainer {
          padding: 10px 11px 10px 0;
          display: flex;
          flex-wrap: wrap;
          .dragLi {
            border: 1px solid #eee;
            background: #f8f8f8;
            border-radius: 5px;
            line-height: 24px;
            width: 60px;
            height: 60px;
            cursor: move;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            margin-left: 14px;
            margin-bottom: 10px;
            img {
              width: 34px;
              height: 20px;
            }
          }
        }
      }
    }
    .decMiddle {
      width: 254px;
    }
    .decMiddle {
      width: 350px;
      border: 1px solid #e5e5e5;
      margin-left: 30px;
    }
    .decRight {
      z-index: 100;
      width: 41.4%;
      margin-left: 20px;
    }
  }
  .footer {
    background: #f8f8fa;
    border-top: 1px solid #f2f2f2;
    text-align: center;
    position: fixed;
    bottom: 0;
    padding: 10px 0;
    left: 0;
    right: 0;
  }
}
</style>
