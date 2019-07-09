<template>
  <div class="container">
    <div class="main">
      <div class="top">
        <span>{{$t('accountSetting.title')}}</span>
      </div>
      <div class="content">
        <div class="shop_config">
          <ul>
            <li>
              <div
                class="li"
                :class="modifyWidth"
              >{{$t('accountSetting.account')}}</div>
              <div class="li">
                <div class="mdy-input">tester001</div>
                <div class="update">{{$t('accountSetting.modifyPassword')}}</div>
              </div>
            </li>
            <li>
              <div
                class="li"
                :class="modifyWidth"
              >{{$t('accountSetting.phone')}}</div>
              <div class="li mdy-input">
                <div>13683043470</div>
              </div>
            </li>
            <li>
              <div
                class="li"
                :class="modifyWidth"
              >{{$t('accountSetting.name')}}</div>
              <div class="li mdy-input">
                <el-input
                  v-model="username"
                  :placeholder="$t('accountSetting.namePlaceholder')"
                  :value="username"
                ></el-input>
              </div>
            </li>
            <li
              class="li_head"
              @click="handleChangeHead()"
            >
              <div
                class="li"
                :class="modifyWidth"
              >{{$t('accountSetting.head')}}</div>
              <div class="li mdy-input head">
                <img :src="imageUrl[0].img_1">
                <span :class="maskspanheight">{{$t('accountSetting.modify')}}</span>
              </div>
            </li>
            <li>
              <div class="li modify btn">
                <el-button
                  type="primary"
                  @click="handleSave()"
                >{{$t('accountSetting.s_modify')}}</el-button>
                <el-button size="medium">{{$t('accountSetting.to_shop_list')}}</el-button>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!--选择图片弹窗 -->
    <ImageDalog />
  </div>
</template>
<script>
import { accountManageRequest } from '@/api/admin/shopsPages.js'
import ImageDalog from '@/components/admin/imageDalog'
export default {
  components: { ImageDalog },
  data () {
    return {
      imageUrl: [
        { img_1: this.$imageHost + '/image/admin/icon_4.png' }
      ],
      username: '',
      dialogTableVisible: false,
      modifyWidth: 'headWidth',
      maskspanheight: ''
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.username = localStorage.getItem('V-Username')
  },
  methods: {
    // 主动调起图片弹窗
    handleChangeHead () {
      this.$http.$emit('dtVisible')
    },
    // 确认修改
    handleSave () {
      let obj = {
        shopAvatar: null,
        accountName: this.username
      }
      accountManageRequest(obj).then((res) => {
        console.log(res)
      })
    }
  }
}
</script>
<style scoped>
.container {
  width: 100%;
  height: 100%;
  background-color: #fff;
  padding: 107px;
}
.main {
  width: 850px;
  margin: 0 auto;
}
.top {
  height: 50px;
  line-height: 50px;
}
.top span {
  font-size: 16px;
}
.content {
  border: 1px solid #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 500px;
}
.shop_config li {
  min-height: 45px;
  font-size: 13px;
  color: #666;
}
.li {
  float: left;
}
.li:nth-of-type(1) {
  width: 110px;
  line-height: 30px;
  margin-left: 16px;
}
.headWidth {
  width: 120px !important;
}
.mdy-input,
.update {
  line-height: 30px;
  float: left;
}
.mdy-input {
  width: 150px;
  height: 30px;
  padding-left: 12px;
}
.update {
  margin-left: 10px;
  color: #5a8bff;
  margin-left: 16px;
  cursor: pointer;
}
.head {
  line-height: 30px;
  height: 80px;
  width: 80px;
  border: 1px solid #ccc;
  position: relative;
  padding-left: 0px;
  margin-left: 12px;
}
.head img {
  width: 100%;
}
.head span {
  position: absolute;
  bottom: 0;
  display: block;
  width: 100%;
  text-align: center;
  color: #fff;
  background: rgba(0, 0, 0, 0.5);
  height: 20px;
  line-height: 20px;
  font-size: 12px;
}
.maskspanheight {
  height: 40px !important;
}
.li_head {
  margin-top: 12px;
  cursor: pointer;
}
li:nth-of-type(5) {
  margin-top: 50px;
}
.modify {
  margin-left: 138px !important;
}
.btn {
  display: flex;
}
</style>
<style>
.el-dialog__header {
  text-align: center;
  font-size: 14px;
}
.el-dialog__body {
  padding: 0 20px;
}
</style>
