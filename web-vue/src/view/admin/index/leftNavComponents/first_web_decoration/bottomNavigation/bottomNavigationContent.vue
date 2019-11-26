<template>
  <div class="bottomNavigationContent">
    <div class="bottomNavigationContent_main">
      <div class="cententLleft">
        <div class="cententLleft_title">{{$t('bottomNavigation.title')}}</div>
        <div class="cententLleft_bottom">
          <ul>
            <li
              v-for="(item,index) in contentList"
              :key="index"
              @click="hanleFooterLi(index)"
            >
              <img :src="ulClickIndex === index?item.hover:item.normal">
              <p :class="ulClickIndex === index?'textActive':''">{{item.text}}</p>
            </li>
          </ul>
        </div>
      </div>
      <div class="cententLRight">
        <div class="cententLRight_title">{{$t('bottomNavigation.contentTitle')}}：<span style="color:#999">{{$t('bottomNavigation.tips')}}80*80。</span></div>
        <div
          class="cententLRight_content_container"
          v-for="(item,index) in contentList"
          :key="index"
        >
          <div class="mp_nav">
            <div
              v-if="index===0||index===1||index===2?false:true"
              class="rightIcon"
              @click="handleDel(index)"
            ><img :src="
              dele_icon"></div>
            <div class="mp_list">
              <span>{{$t('bottomNavigation.listTitle')}}：</span>
              <el-input
                :placeholder="$t('bottomNavigation.listTitle')"
                size="small"
                v-model="item.text"
                :maxlength="5"
              ></el-input>
            </div>
            <div class="mp_list moDifyImg">
              <div class="nav_title">{{$t('bottomNavigation.picture')}}：<span
                  @click="handleModifyDialog(index)"
                  style="color:#5a8bff;cursor:pointer"
                >{{$t('bottomNavigation.modify')}}</span></div>
              <div class="nav_icon">
                <div class="icon_box">
                  <img :src="item.hover">
                  <span @click="handleChangeIcon(index,0)">{{$t('bottomNavigation.changeIcons')}}</span>
                </div>
                <div class="tip">{{$t('bottomNavigation.clickStatus')}}</div>
              </div>
              <div class="nav_icon">
                <div class="icon_box">
                  <img :src="item.normal">
                  <span @click="handleChangeIcon(index,1)">{{$t('bottomNavigation.changeIcons')}}</span>
                </div>
                <div class="tip">{{$t('bottomNavigation.unclickedStatus')}}</div>
              </div>
            </div>
            <div class="linkContainer">
              <span>{{$t('bottomNavigation.addConnections')}}：</span>
              <el-input
                v-model="item.page"
                :placeholder="$t('bottomNavigation.placeholder')"
                size="small"
              ></el-input>
              <el-button
                size="small"
                @click="handleSelectLinks(index)"
              >{{$t('bottomNavigation.addConnections')}}</el-button>
            </div>
          </div>
        </div>
        <div class="cententLRight_content_container">
          <el-button
            size="small"
            v-if='contentList.length<5'
            @click="addNav"
          >{{$t('bottomNavigation.addMenu')}}</el-button>
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
    <SelectLinks
      :tuneUpSelectLink="tuneUpSelectLink"
      @selectLinkPath="selectLinkPath"
    />
    <!--选择图片弹窗 -->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp="tuneUp"
      :imageSize=[80,80]
      @handleSelectImg='handleSelectImg'
    />
    <!--修改icon弹窗-->
    <el-dialog
      title="提示"
      :visible.sync="modifyDialog"
      width="48%"
    >
      <div class="modifyDialogDiv">
        <ul class="modifyDialogUl">
          <li
            v-for="(item,index) in modifyDialogList"
            :key="index"
            @click="handleSelectIcon(item,index)"
          >
            <span>{{item.text}}</span>
            <img
              :src="item.img_one"
              style="margin-right:5px"
            >
            <img :src="item.img_two">
          </li>
        </ul>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >

      </span>
    </el-dialog>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import ImageDalog from '@/components/admin/imageDalog'
import SelectLinks from '@/components/admin/selectLinks'
import { bottomGetRequest, bottomUpdateRequest } from '@/api/admin/bottomNavigation'
export default {
  components: { SelectLinks, ImageDalog },
  data () {
    return {
      tuneUpSelectLink: false,
      ulClickIndex: 0,
      inputValArr: [
        {
          input: '1'
        },
        {
          input: '2'
        },
        {
          input: '3'
        },
        {
          input: '4'
        },
        {
          input: '5'
        }
      ],
      linkInput: '',
      linkFlag: true,
      firstNavIndex: '',
      secondNavIndex: '',
      contentList: [
      ],
      dele_icon: this.$imageHost + '/image/admin/icon_delete.png',
      modifyDialog: false,
      modifyDialogList: [
        {
          img_one: this.$imageHost + '/image/admin/icon_mps/icon_yes_2.png',
          img_two: this.$imageHost + '/image/admin/icon_mps/icon_no_2.png',
          text: '首页'
        },
        {
          img_one: this.$imageHost + '/image/admin/icon_mps/icon_yes_1.png',
          img_two: this.$imageHost + '/image/admin/icon_mps/icon_no_1.png',
          text: '门店'
        },
        {
          img_one: this.$imageHost + '/image/admin/icon_mps/icon_yes_3.png',
          img_two: this.$imageHost + '/image/admin/icon_mps/icon_no_3.png',
          text: '购物车'
        },
        {
          img_one: this.$imageHost + '/image/admin/icon_mps/icon_yes_4.png',
          img_two: this.$imageHost + '/image/admin/icon_mps/icon_no_4.png',
          text: '我的'
        },
        {
          img_one: this.$imageHost + '/image/admin/icon_mps/icon_yes_5.png',
          img_two: this.$imageHost + '/image/admin/icon_mps/icon_no_5.png',
          text: '分类'
        },
        {
          img_one: this.$imageHost + '/image/admin/icon_mps/icon_yes_6.png',
          img_two: this.$imageHost + '/image/admin/icon_mps/icon_no_6.png',
          text: '活动'
        },
        {
          img_one: this.$imageHost + '/image/admin/icon_mps/icon_yes_7.png',
          img_two: this.$imageHost + '/image/admin/icon_mps/icon_no_7.png',
          text: '订单'
        }
      ],
      iconindex: '',
      linksIndex: '',
      tuneUp: false
    }
  },
  computed: {
    ...mapGetters(['afferentPath']),
    afferentPath_ () {
      return this.afferentPath
    }
  },
  watch: {
    afferentPath_ (newData, oldData) {
      console.log(newData)
      this.contentList[this.linksIndex].page = newData
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
    // 初始化查询
    this.queryBottom()
  },
  methods: {
    // 选中路径
    selectLinkPath (path) {
      console.log(path)
      this.contentList[this.linksIndex].page = path
    },
    queryBottom () {
      bottomGetRequest().then((res) => {
        if (res.error === 0) {
          console.log(res.error)
          // res.content.map((item, index) => {
          //   item.normal = this.$imageHost + item.normal
          //   console.log(item.normal)

          //   item.hover = this.$imageHost + item.hover
          // })
          console.log(res)
          this.contentList = res.content
        }
      })
    },
    // 点击选择链接
    handleSelectLinks (index) {
      this.linksIndex = index
      this.tuneUpSelectLink = !this.tuneUpSelectLink
      // this.$http.$emit('linkDialogFlag', this.linkFlag)
    },
    // 点击底部li
    hanleFooterLi (index) {
      this.ulClickIndex = index
    },
    // 点击修改
    handleModifyDialog (index) {
      this.iconindex = index
      this.modifyDialog = true
    },
    // 保存
    saveShopStyle () {
      let obj = this.contentList
      bottomUpdateRequest(obj).then((res) => {
        if (res.error === 0) {
          this.$message.success({
            message: '保存成功',
            showClose: true
          })
        }
        console.log(res)
      })
    },
    // 添加一项
    addNav () {
      let newNav = {
        'text': '首页',
        'btn': 0,
        'normal': this.$imageHost + '/image/admin/btn_add.png',
        'hover': this.$imageHost + '/image/admin/btn_add.png',
        'page': 'pages/index/index'
      }
      this.contentList.push(newNav)
    },
    // 删除某项
    handleDel (index) {
      this.contentList.splice(index, 1)
    },
    // 更换图标
    handleChangeIcon (first, second) {
      console.log(first, second)
      this.firstNavIndex = first
      this.secondNavIndex = second
      this.tuneUp = !this.tuneUp
      // this.$http.$emit('dtVisible')
    },
    // 弹框确定选中
    handleSelectImg (res) {
      // this.imageUrl[0].img_1 = res
      console.log(res)
      console.log(this.firstNavIndex, this.secondNavIndex, this.contentList)
      let first = this.firstNavIndex
      let second = this.secondNavIndex
      console.log(first, second)
      console.log(res)
      if (second === 0) {
        // this.ulDataList[first].img = res
        this.contentList[first].hover = res.imgUrl
        console.log(this.contentList)
      } else {
        // this.ulDataList[first].imgActive = res
        this.contentList[first].normal = res.imgUrl
      }
      this.$forceUpdate()
    },
    // 修改icon弹窗li点击事件
    handleSelectIcon (data, index) {
      this.contentList[this.iconindex].text = data.text
      this.contentList[this.iconindex].normal = data.img_one
      this.contentList[this.iconindex].hover = data.img_two
      this.modifyDialog = false
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
  position: relative;
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
  margin-bottom: 100px;
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
  /* padding-right: 23px; */
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
}
.bottomNavigationContent_main {
  background-color: #fff;
  height: 100%;
  overflow: hidden;
  overflow-y: auto;
  padding-bottom: 96px;
}

.cententLleft_title {
  height: 55px;
  background: url(../../../../../../assets/adminImg/phone_tops.png) no-repeat;
  line-height: 55px;
  text-align: center;
  padding-top: 9px;
  color: #fff;
}
.rightIcon {
  position: absolute;
  right: -8px;
  top: -7px;
  cursor: pointer;
}
.cententLleft_bottom li img {
  width: 20px;
  height: 20px;
}
.modifyDialogUl {
  overflow: hidden;
}
.modifyDialogUl li {
  float: left;
  width: 145px;
  height: 42px;
  line-height: 42px;
  text-align: center;
  margin-right: 12px;
  color: #666;
  margin-bottom: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
}
.modifyDialogDiv {
  padding: 0 20px;
}
.modifyDialogUl li span {
  display: inline-block;
  margin-right: 10px;
  width: 42px;
  text-align: left;
}
.modifyDialogUl li img {
  display: inline-block;
  width: 20px;
  height: 20px;
  max-width: 23px;
}
.footer {
  background: #f8f8fa;
  border-top: 1px solid #f2f2f2;
  text-align: center;
  position: absolute;
  z-index: 2;
  bottom: 0;
  padding: 10px 0;
  left: 0;
  right: 0;
  margin-right: 10px;
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
</style>
<style>
.bottomNavigationContent .el-input {
  width: auto !important;
}
.bottomNavigationContent .linkContainer .el-button {
  /* padding: 7px 15px !important; */
  font-size: 12px !important;
  border-radius: 3px !important;
}
.el-dialog__header {
  text-align: center;
}
.el-dialog__header {
  background-color: #f3f3f3;
}
</style>
