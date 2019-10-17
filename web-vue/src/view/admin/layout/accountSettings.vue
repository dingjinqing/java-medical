<template>
  <div class="container">
    <div class="main">
      <div class="top">
        <span v-if="shop_config_ul_flag">{{$t('accountSetting.title')}}</span>
        <span v-else>{{$t('accountSetting.modifyPasswordtitle')}}</span>
      </div>
      <div class="content">
        <div class="shop_config">
          <ul v-if="shop_config_ul_flag">
            <li>
              <div
                class="li"
                :class="modifyWidth"
              >{{$t('accountSetting.account')}}</div>
              <div class="li">
                <div class="mdy-input">{{userName}}</div>
                <div
                  class="update"
                  @click="change_modifyPassword()"
                >{{$t('accountSetting.modifyPassword')}}</div>
              </div>
            </li>
            <li>
              <div
                class="li"
                :class="modifyWidth"
              >{{$t('accountSetting.phone')}}</div>
              <div class="li mdy-input">
                <div>{{mobile}}</div>
              </div>
            </li>
            <li>
              <div
                class="li"
                :class="modifyWidth"
              >{{$t('accountSetting.name')}}</div>
              <div class="li mdy-input">
                <el-input
                  v-model="accountName"
                  :placeholder="$t('accountSetting.namePlaceholder')"
                  :value="accountName"
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
                <el-button
                  @click="to_allShops()"
                  size="medium"
                  style="margin-left:20px"
                >{{$t('accountSetting.to_shop_list')}}</el-button>
              </div>
            </li>
          </ul>
          <ul
            v-else
            class="modifyPul"
          >
            <li>
              <div>
                {{$t('accountSetting.oldPaawd')}}：
              </div>
              <div>
                <el-input
                  v-model="modifyObj.passwd"
                  size="mini"
                ></el-input>
              </div>
            </li>
            <li>
              <div>
                {{$t('accountSetting.newPaawd')}}：
              </div>
              <div>
                <el-input
                  v-model="modifyObj.newPasswd"
                  size="mini"
                ></el-input>
              </div>
            </li>
            <li>
              <div>
                {{$t('accountSetting.comfNewPaawd')}}：
              </div>
              <div>
                <el-input
                  v-model="modifyObj.confNewPasswd"
                  size="mini"
                ></el-input>
              </div>
            </li>
            <li>
              <el-button
                type="primary"
                size="mini"
                @click="modifyPasswordSure()"
              > {{$t('accountSetting.sure')}}</el-button>
              <el-button
                type="info"
                plain
                size="mini"
                style="margin-left:20px"
                @click="to_accountmain()"
              >{{$t('accountSetting.back')}}</el-button>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!--选择图片弹窗 -->
    <ImageDalog
      pageIndex='imageDalog'
      :tuneUp="tuneUp"
      :imageSize=[52,52]
      @handleSelectImg='handleSelectImg'
    />
  </div>
</template>
<script>
import { accountManageRequest, modifyPasswordRequest, queryShopRequest } from '@/api/admin/shopsPages.js'
import ImageDalog from '@/components/admin/imageDalog'
export default {
  components: { ImageDalog },
  data () {
    return {
      imageUrl: [
        { img_1: this.$imageHost + '/image/admin/icon_4.png' }
      ],
      userName: '',
      accountName: '',
      mobile: '',
      dialogTableVisible: false,
      modifyWidth: 'headWidth',
      shop_config_ul_flag: true,
      maskspanheight: '',
      imagePath: '',
      modifyObj: {
        passwd: '',
        newPasswd: '',
        confNewPasswd: ''
      },
      tuneUp: false
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    this.accountName = localStorage.getItem('V-AccountName')
    // 初始化账户设置
    this.queryAccount()
  },
  methods: {
    // 主动调起图片弹窗
    handleChangeHead () {
      // this.$http.$emit('dtVisible')
      this.tuneUp = !this.tuneUp
    },
    queryAccount () {
      queryShopRequest().then((res) => {
        this.userName = res.content.userName
        this.accountName = res.content.accountName
        this.mobile = res.content.mobile
        localStorage.setItem('V-AccountName', res.content.accountName)
        this.imageUrl[0].img_1 = res.content.shopAvatar
        this.imagePath = res.content.shopAvatarPath
        console.log(res)
      })
    },
    // 确认修改
    handleSave () {
      let obj = {
        shopAvatar: this.imagePath,
        accountName: this.accountName
      }
      accountManageRequest(obj).then((res) => {
        if (res.error === 0) {
          localStorage.setItem('V-shopAvatar', this.imageUrl[0].img_1)
          localStorage.setItem('V-AccountName', this.accountName)
          this.$http.$emit('changeHead')
          this.$message({
            message: '保存成功',
            type: 'success'
          })
        }
        console.log(res)
      })
    },
    // 弹框确定选中
    handleSelectImg (res) {
      this.imageUrl[0].img_1 = res.imgUrl
      this.imagePath = res.imgPath
      console.log(res)
    },
    // 跳转修改登录密码页面
    change_modifyPassword () {
      this.shop_config_ul_flag = false
    },
    // 跳转到店铺列表页
    to_allShops () {
      console.log(1)
      this.$router.push({
        path: '/admin/home/shopMain',
        query: {
          change_components: '4'
        }
      })
    },
    // 修改登录密码页面返回按钮事件
    to_accountmain () {
      this.shop_config_ul_flag = true
    },
    // 修改登录密码页面保存事件
    modifyPasswordSure () {
      let flag = this.checkPassword()
      if (flag === false) return
      modifyPasswordRequest(this.modifyObj).then((res) => {
        if (res.error === 0) {
          this.$message({
            message: '修改密码成功',
            type: 'success'
          })
        }
      })
    },
    // 校验登录密码
    checkPassword () {
      let reg = /^[^\u4e00-\u9fa5][\S+$]{5,16}$/
      for (let item in this.modifyObj) {
        console.log(this.modifyObj[item])
        // if (!this.modifyObj[item] || !reg.test(this.modifyObj[item])) { this.$message({ message: '警告哦，这是一条警告消息', type: 'warning' }) }
        if (!reg.test(this.modifyObj[item])) {
          this.$message({
            message: '密码应为6至16位非中文且不能为空',
            type: 'warning'
          })
          return false
        }
        if (this.modifyObj.passwd === this.modifyObj.newPasswd) {
          this.$message({
            message: '新密码不能与旧密码相同',
            type: 'warning'
          })
          return false
        }
        if (this.modifyObj.newPasswd !== this.modifyObj.confNewPasswd) {
          this.$message({
            message: '确认密码不相同',
            type: 'warning'
          })
          return false
        }
      }
      return true
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
.modifyPul li {
  display: flex;
}
.modifyPul div {
  height: 30px;
  line-height: 30px;
}
.modifyPul li div:nth-of-type(2) {
  margin-left: 45px;
}
.modifyPul li:nth-of-type(3) div:nth-of-type(2) {
  margin-left: 20px;
}
.modifyPul li:nth-of-type(4) {
  padding-left: 98px;
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
