<template>
  <div class="addingGoodsDetails">
    <el-row>
      <!-- phone_box -->
      <el-col
        class="phone_box"
        :span="10"
      >
        <div class="phone_box_content">
          <div class="phone_top">
            <span class="phone_page_title">商品详情</span>
          </div>
          <div class="phone">
            <div class="drag_area_container">
              <div class="item_goods">
                <div class="item_img"></div>
                <div class="item_name"></div>
                <div class="item_price"></div>
                <div class="item_mess"></div>
              </div>
              <div class="detail-show-box">
                <div class="sp_title"></div>
                <div class="show-box-white"></div>
              </div>
              <div class="drag_area"></div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="14">
        <!-- 商品基本信息 -->
        <section class="detail-edit">
          <el-form
            ref="right_form"
            :model="right_form"
            label-width="100px"
          >
            <!-- 商品基本信息 -->
            <el-form-item label="商品基本信息">
              <span style="color:#999">商品信息为固定样式仅供参考，请以实际效果为准</span>
            </el-form-item>
            <!-- 模块位置 -->
            <el-form-item label="模块位置">
              <el-radio
                v-model="radio"
                label="1"
              >自定义内容在上</el-radio>
              <el-radio
                v-model="radio"
                label="2"
              >商品详情在上</el-radio>
            </el-form-item>
            <!-- 自定义内容 -->
            <el-form-item label="自定义内容">
              <label for="">商品页模板</label>
              <el-button
                size="small "
                class="choose"
              >选择模板</el-button>
              <el-link
                class="refresh"
                type="primary"
                :underline="false"
              > 刷新</el-link>
              <el-link
                type="primary"
                :underline="false"
              >添加模板</el-link>
            </el-form-item>
            <!-- 商品详情 -->
            <section class="goodsDetails">
              <p class="goodsDetails_top">商品详情</p>
              <div>
                <tinymce
                  v-model="content"
                  :height="300"
                />
              </div>
            </section>

          </el-form>
        </section>
      </el-col>

    </el-row>
    <!-- footer -->
    <div class="addingGoodsFooter">
      <section class="addingGoodsFooter">
        <el-button
          type="primary"
          size="small"
          @click.native.prevent="handleToList"
        >保存后返回列表</el-button>
        <el-button
          size="small"
          @click.native.prevent="handlePreStep"
        >上一步</el-button>
        <el-button
          size="small"
          @click.native.prevent="handleNextStep"
        >下一步</el-button>
        <el-button
          type="primary"
          size="small"
          @click.native.prevent="handleAddAfterSaving"
        >保存后继续添加</el-button>
        <el-button
          type="primary"
          size="small"
          @click.native.prevent="handlePreview"
        >保存后预览商品</el-button>
      </section>
    </div>
  </div>
</template>
<script>
import Tinymce from '@/components/admin/Tinymce'
export default {
  name: 'addingGoodsDetails',
  components: { Tinymce },
  data () {
    return {
      right_form: {

      },
      radio: '1',
      content: ``

    }
  },
  methods: {
    handleToList () {

    },
    handlePreStep () {
      this.$emit('toFirst')
    },
    handleNextStep () {
      this.$emit('toThird')
    },
    handleAddAfterSaving () {

    },
    handlePreview () {

    }
  }
}
</script>
<style scoped>
.phone_box {
  width: 385px;
  margin: 0px 2px 2px 10px;

  padding: 0;
  height: 616px;
  margin-top: 40px;
}
.phone_box_content {
  border: 1px solid #e5e5e5;
}
.phone_top {
  width: 375px;
  height: 64px;
  margin: 0 auto;
  width: 375px;
  height: 64px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  background: url() no-repeat;
}
.phone_page_title {
  display: inline-block;
  margin-top: 16px;
  font-size: 16px;
  color: #000;
  font-weight: 800;
}
.phone {
  width: 383px;
  /* margin: 0 auto; */
  /* padding: 0 14px 0 13px; */
  position: relative;
  min-height: 200px;
  box-sizing: content-box;
}
.drag_area_container {
  position: relative;
  max-height: 550px;
  background: #f5f5f5 !important;
  overflow-y: auto;
}
.drag_area {
  width: 320px;
  min-height: 514px;
  background: #eee;
  /* border: 1px solid #cacaca; */
  font-family: "Microsoft Yahe";
  text-align: left;
  min-height: 200px !important;
  height: auto;
  width: 378px !important;
  overflow-y: auto;
  overflow-x: hidden;
  margin-top: 8px;
}
.detail-edit {
  width: 684px;
  border: 1px solid #e5e5e5;
  background: #f8f8f8;
  padding: 16px 15px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;

  margin-top: 40px;
}
.el-form-item {
  background-color: #fff;
  border: 1px solid #ddd;
}
.refresh,
.choose {
  margin: 0 10px;
}
.goodsDetails {
  border: 1px solid #dddddd;
  padding: 5px;
}
.goodsDetails_top {
  font-size: 14px;
  display: inline-block;
  height: 40px;
  line-height: 40px;
  color: #333;
  border-bottom: 1px solid #ddd;
  width: 97%;
  text-align: left;
  margin-left: 16px;
  margin-bottom: 10px;
}
.addingGoodsFooter {
  position: fixed;
  bottom: 0;
  width: 1092px;
  padding: 10px;
  margin-left: -16px;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  z-index: 2;
  background: #f8f8fa;
  display: block;
}
</style>
