<template>
  <div class="bottomNavigationContent">
    <div class="bottomNavigationContent_main">
      <div class="cententLleft">
        <div class="cententLleft_title">底部导航配置</div>
        <div class="cententLleft_bottom">
          <ul>
            <li
              v-for="(item,index) in ulDataList"
              :key="index"
              @click="hanleFooterLi(index)"
            >
              <img :src="ulClickIndex === index?item.imgActive:item.img">
              <p :class="ulClickIndex === index?'textActive':''">{{item.title}}</p>
            </li>
          </ul>
        </div>
      </div>
      <div class="cententLRight">
        <div class="cententLRight_title">导航设置：<span style="color:#999">最少需要使用三个导航设置，最多五个。图标大小80*80。</span></div>
        <div class="cententLRight_content_container">
          <div class="mp_nav">
            <div class="mp_list">
              <span>导航文字：</span>
              <el-input
                v-model="input"
                placeholder="请输入内容"
                size="mini"
              ></el-input>
            </div>
            <div class="mp_list moDifyImg">
              <div class="nav_title">图片：<span style="color:#5a8bff">修改</span></div>
              <div class="nav_icon">
                <div class="icon_box">
                  <img :src="iconImgs[0].img">
                  <span>更换图标</span>
                </div>
                <div class="tip">点击状态</div>
              </div>
              <div class="nav_icon">
                <div class="icon_box">
                  <img :src="iconImgs[1].img">
                  <span>更换图标</span>
                </div>
                <div class="tip">未点击状态</div>
              </div>
            </div>
            <div class="linkContainer">
              <span>添加链接：</span>
              <el-input
                v-model="linkInput"
                placeholder="请输入内容"
                size="mini"
              ></el-input>
              <el-button
                size="mini"
                @click="handleSelectLinks"
              >选择链接</el-button>
            </div>
          </div>
        </div>

      </div>
      <!--保存-->
      <div class="footer">
        <div
          class="save"
          @click="saveShopStyle()"
        >{{$t('shopStyle.saveText')}}</div>
      </div>
    </div>
    <!--选择链接弹窗-->
    <SelectLinks />
  </div>
</template>
<script>
import SelectLinks from '@/components/admin/selectLinks'
export default {
  components: { SelectLinks },
  data () {
    return {
      ulDataList: [
        { title: '首页', img: this.$imageHost + '/image/admin/icon_mps/icon_no_2.png', imgActive: this.$imageHost + '/image/admin/icon_mps/icon_yes_2.png' },
        { title: '门店', img: this.$imageHost + '/image/admin/icon_mps/icon_no_1.png', imgActive: this.$imageHost + '/image/admin/icon_mps/icon_yes_1.png' },
        { title: '购物车', img: this.$imageHost + '/image/admin/icon_mps/icon_no_1.png', imgActive: this.$imageHost + '/image/admin/icon_mps/icon_no_1.png' },
        { title: '商品分类', img: this.$imageHost + '/image/admin/icon_mps/icon_no_5.png', imgActive: this.$imageHost + '/image/admin/icon_mps/icon_yes_5.png' },
        { title: '个人中心', img: this.$imageHost + '/image/admin/icon_mps/icon_no_4.png', imgActive: this.$imageHost + '/image/admin/icon_mps/icon_yes_4.png' }
      ],
      ulClickIndex: 0,
      input: '首页',
      iconImgs: [
        { img: this.$imageHost + '/image/admin/icon_mps/icon_yes_2.png' },
        { img: this.$imageHost + '/image/admin/icon_mps/icon_yes_2.png' }
      ],
      linkInput: 'pages/index/index',
      linkFlag: true
    }
  },
  mounted () {

  },
  methods: {
    // 点击选择链接
    handleSelectLinks () {
      this.$http.$emit('linkDialogFlag', this.linkFlag)
    },
    // 点击底部li
    hanleFooterLi (index) {
      this.ulClickIndex = index
    },
    // 保存
    saveShopStyle () {

    }
  }
}
</script>
<style scoped>
.linkContainer {
  margin-top: 36px;
  padding-left: 40px;
}
.mp_nav {
  width: 100%;
  border: 1px solid #e5e5e5;
  padding: 15px 10px;
  background: #fff;
  margin-bottom: 15px;
  margin-top: 13px;
}
.nav_icon img {
  width: 40px;
  height: 40px;
}
.nav_icon {
  margin-top: -5px;
  display: inline-block;
  width: 70px;
  height: 70px;
  border: 1px solid #e6e6e6;
  text-align: center;
  line-height: 70px;
  position: relative;
  margin-left: 20px;
}
.icon_box {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.tip {
  position: absolute;
  left: 0;
  bottom: -27px;
  width: 100%;
  height: 30px;
  line-height: 30px;
  font-size: 12px;
}
.icon_box span {
  display: block;
  position: absolute;
  bottom: 0px;
  left: 0;
  width: 100%;
  height: 20px;
  line-height: 20px;
  font-size: 12px;
  color: #fff;
  background: rgba(0, 0, 0, 0.5);
  cursor: pointer;
}
.nav_title {
  display: inline-block;
  vertical-align: top;
}
.mp_list {
  padding-left: 40px;
}
.mp_list span {
  display: inline-block;
}
.moDifyImg {
  margin-top: 20px;
}
.cententLRight {
  float: left;
  margin: 80px 0 0 30px;
  border: 1px solid #e5e5e5;
  background: #f8f8f8;
  padding: 15px 12px 22px;
  border-radius: 3px;
}
.cententLleft_bottom ul {
  background-color: #fff;
  padding: 10px 0;
  position: absolute;
  bottom: 0;
  width: 100%;
  display: flex;
  font-size: 12px;
}
.textActive {
  color: #f66;
}
.cententLleft_bottom ul img {
  width: 20px;
  margin-bottom: 5px;
}
.cententLleft_bottom ul li {
  cursor: pointer;
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.cententLleft {
  width: 323px;
  height: 510px;
  border: 1px solid #ccc;
  background: #eee;
  position: relative;
  float: left;
  margin: 70px 0 0 224px;
}
.bottomNavigationContent {
  padding: 10px;
  padding-right: 23px;
  min-width: 1400px;
  font-size: 14px;
  height: 100%;
  position: relative;
}
.bottomNavigationContent_main {
  position: relative;
  background-color: #fff;
  height: 100%;
  overflow: hidden;
  overflow-y: auto;
  padding-bottom: 96px;
}

.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: fixed;
  z-index: 2;
  bottom: 0;
  width: 89%;
  padding: 10px 0;
}
.save {
  width: 70px;
  height: 30px;
  line-height: 30px;
  border: none;
  background: #5a8bff;
  color: #fff;
  margin: auto;
  cursor: pointer;
}
.cententLleft_title {
  height: 55px;
  background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
  line-height: 55px;
  text-align: center;
  padding-top: 9px;
  color: #fff;
}
</style>
<style>
.bottomNavigationContent .el-input {
  width: auto !important;
}
.bottomNavigationContent .linkContainer .el-button {
  padding: 7px 15px !important;
  font-size: 12px !important;
  border-radius: 3px !important;
}
</style>
