<template>
  <div class="suspendWindow">
    <div class="suspendWindowMain">
      <div class="config">
        <div class="configLeft">
          <div
            class="title"
            :style="`background:url(${$imageHost}/image/admin/new_shop_beautify/page_name.png) no-repeat`"
          >
          </div>
          <div class="leftInfo">
            <div class="floatNav">
              <div
                class="navList"
                v-if="mainImgUrl.length<4"
              >
                <div
                  class="nav_item navLi"
                  v-for="(item,index) in mainImgUrl"
                  :key="index"
                  :class="holdMainIcon?'navListAct':''"
                  :style="holdMainIcon?`transform: translateY(-${(index+1)*58}px)`:''"
                >
                  <img :src="item.imgUrl">
                </div>
              </div>
              <div class="nav_item nav_item_main">
                <img
                  class="nav_item_main_1"
                  :class="holdMainIcon?'active1':''"
                  :src="beforeExpansionUrl"
                  @click="handleToClickMainIcon(1)"
                >
                <img
                  class="nav_item_main_2"
                  :class="holdMainIcon?'active3':''"
                  :src="afterExpansion"
                  @click="handleToClickMainIcon(0)"
                >
              </div>
              <div
                class="surpass_content"
                v-if="mainImgUrl.length>3"
                :style="holdMainIcon?'transform: none;opacity: 1':''"
              >
                <div
                  class="surpass_every"
                  v-for="(item,index) in mainImgUrl"
                  :key="index"
                >
                  <div class="surpass_div">
                    <img :src="item.imgUrl">
                  </div>
                  <span>{{item.name}}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--右边部分-->
        <div class="configRight">
          <div class="list">
            <div class="listTitle">
              <span>应用页面</span>
              <div class="rightRadio">
                <div style="color:#999999;margin-right:5px">开启后，以下页面将显示悬浮窗</div>
                <el-switch
                  v-model="isShowSuspension"
                  active-color="#f7931e"
                  inactive-color="#ddd"
                >
                </el-switch>
              </div>
            </div>
            <div class="pageContent">
              <div class="pageLi">
                <div class="top">
                  <div class="li">
                    <el-checkbox v-model="goodsDetail">商品详情页</el-checkbox>
                  </div>
                  <div>
                    <el-checkbox v-model="peasonCenter">商品详情页</el-checkbox>
                  </div>
                </div>
                <div class="bottom">
                  <div>
                    <el-checkbox v-model="customPage">自定义页面</el-checkbox>
                  </div>
                  <div
                    class="addPage"
                    @click="handleToClickAddPage()"
                  >
                    选择页面
                  </div>
                  <span>已选择{{customPageSelect.length}}个页面</span>
                </div>
              </div>
            </div>
          </div>
          <div class="list">
            <div class="listTitle">
              <span>悬浮方式</span>
            </div>
            <div class="pageContent">
              <div class="pageLi">
                <div class="top">
                  <div class="li">
                    <el-radio
                      v-model="fixedShow"
                      label="1"
                    >固定位置显示</el-radio>
                  </div>
                  <div>
                    <el-radio
                      v-model="fixedShow"
                      label="2"
                    >上滑消失下滑显示</el-radio>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="list">
            <div class="listTitle">
              <span>主图标</span>
              <div class="rightRadio">
                <div style="color:#999999;margin-right:5px">开启后，点击主图标展开显示子图标</div>
                <el-switch
                  v-model="isShowIcon"
                  active-color="#f7931e"
                  inactive-color="#ddd"
                >
                </el-switch>
              </div>
            </div>
            <div class="pageContent">
              <div class="pageLi">
                <div class="top">
                  <div class="navIconLeft">
                    <div class="nav_icon">
                      <div class="icon_box">
                        <img :src="beforeExpansionUrl">
                        <span @click="handleChangeIcon(index,0)">{{$t('bottomNavigation.changeIcons')}}</span>
                      </div>
                    </div>
                    <div class="tips">
                      <div style="color:#000">
                        展开前
                      </div>
                      <div style="font-size:12px;color:#999">
                        建议尺寸：50 * 50
                      </div>
                      <div
                        class="reset"
                        @click="beforeExpansionUrl=beforeExpansionUrlB"
                      >
                        <i class="el-icon-refresh-right"></i>重置图标
                      </div>
                    </div>
                  </div>

                  <div class="navIconLeft">
                    <div class="nav_icon">
                      <div class="icon_box">
                        <img :src="afterExpansion">
                        <span @click="handleChangeIcon(index,1)">{{$t('bottomNavigation.changeIcons')}}</span>
                      </div>
                    </div>
                    <div class="tips">
                      <div style="color:#000">
                        展开后
                      </div>
                      <div style="font-size:12px;color:#999">
                        建议尺寸：50 * 50
                      </div>
                      <div class="reset">
                        <i class="el-icon-refresh-right"></i>重置图标
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="list">
            <div class="listTitle">
              <span>子图标</span>
              <div class="rightRadio">
                <div style="color:#999999;margin-right:5px">主图标开关开启，最多可勾选6个子图标</div>
              </div>
            </div>
            <div class="pageContent">
              <div
                class="pageLi iconBottom"
                v-for="(item,index) in childrenIconData"
                :key="index"
              >
                <div class="childIconLeft">
                  <el-checkbox v-model="item.titleChecked">{{item.title}}</el-checkbox>
                </div>
                <div class="top childTop">
                  <div
                    v-if="index===1"
                    style="margin-bottom:10px"
                  >
                    <el-input
                      v-model="item.phInput"
                      size="small"
                      placeholder="请输入电话号码"
                    ></el-input>
                  </div>

                  <div class="navIconLeft">
                    <div
                      class="nav_icon"
                      style="margin-left:0"
                      v-if="index<5"
                    >
                      <div class="icon_box">
                        <img :src="item.iconImgUrl">
                        <span @click="handleChangeChldrenIcon(index)">{{$t('bottomNavigation.changeIcons')}}</span>
                      </div>
                    </div>
                    <div
                      class="tips"
                      style="padding:5px 0 0 10px"
                      v-if="index<5"
                    >
                      <div style="font-size:12px;color:#999">
                        建议尺寸：50 * 50
                      </div>
                      <div class="resetDiv">
                        <div
                          class="reset"
                          @click="handleToClickSysDialog(index)"
                        >
                          <i :class="index>1&&index<5?'el-icon-refresh-right':'el-icon-s-tools' "></i>
                          {{index>1?'重置图标':'系统图标'}}
                        </div>
                        <el-checkbox v-model="item.isIndependentShow">独立主图标展示</el-checkbox>
                      </div>

                    </div>
                  </div>
                  <div
                    v-if="index>4"
                    style="margin-bottom:10px"
                    class="custom"
                  >
                    <el-input
                      v-model="item.customIconName"
                      size="small"
                      placeholder="请输入图标名称"
                    ></el-input>
                    <i class="el-icon-delete"></i>
                  </div>
                  <div
                    v-if="index>4"
                    class="customLink"
                  >
                    <el-input
                      v-model="item.customLink"
                      size="small"
                    ></el-input>
                    <div
                      @click="handleToClickPath(index)"
                      class="choiseLink"
                    >选择链接</div>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>
      </div>

      <!--保存-->
      <div class="footer">
        <div>
          <el-button
            type="primary"
            size="small"
            @click="handleToFooter(0)"
          >{{$t('pageSetUp.saveAndPublish')}}</el-button>
          <el-button
            size="small"
            @click="handleToFooter(1)"
          >{{$t('pageSetUp.saveAsDraft')}}</el-button>

        </div>
      </div>
    </div>
    <!--选择页面弹窗-->
    <selectTemplate
      @handleSelectTemplate="handleSelectTemplate"
      :tuneUpSelectTemplate="tuneUpSelectTemplate"
      :backSelectData="backSelectDataSus"
    />
    <!--选择图片弹窗 -->
    <ImageDalog
      pageIndex='pictureSpace'
      :tuneUp="tuneUp"
      @handleSelectImg='handleSelectImg'
    />
    <!--选择系统图标弹窗-->
    <el-dialog
      title="请选择系统图标"
      :visible.sync="systemIconVisible"
      width="340px"
    >
      <div class="systemIconMain">
        <div
          v-for="(item,index) in systemIconDataNowShow"
          :key="index"
          class="sysList"
        >
          <img :src="item.imgUrl">
          <el-radio
            :label="item.checked"
            v-model="checked2"
          ></el-radio>
        </div>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="systemIconVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="handleToSureSysIcon()"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!--选择链接弹窗-->
    <SelectLinks
      :tuneUpSelectLink="tuneUpSelectLink"
      @selectLinkPath="selectLinkPath"
    />
  </div>
</template>
<script>
export default {
  components: {
    selectTemplate: () => import('./selectTemplate.vue'), // 选择页面弹窗
    ImageDalog: () => import('@/components/admin/imageDalog'), // 选择图片弹窗
    SelectLinks: () => import('@/components/admin/selectLinks') // 选择链接弹窗
  },
  data () {
    return {
      tuneUpSelectLink: false, // 选择链接弹窗flag
      tuneUp: false, // 选择图片弹窗flag
      tuneUpSelectTemplate: false, // 选择页面弹窗flag
      holdMainIcon: false, // 控制左侧主icon切换
      mainImgUrl: [
        {
          name: '电话',
          imgUrl: 'http://mpimg2.weipubao.cn/image/admin/suspend_icon/origin_ph_2.png'
        },
        {
          name: '客服',
          imgUrl: 'http://mpimg2.weipubao.cn/image/admin/suspend_icon/origin_kf_2.png'
        },
        {
          name: '客服',
          imgUrl: 'http://mpimg2.weipubao.cn/image/admin/suspend_icon/origin_kf_2.png'
        }, {
          name: '客服',
          imgUrl: 'http://mpimg2.weipubao.cn/image/admin/suspend_icon/origin_kf_2.png'
        }
      ], //  左侧icon子项数据
      isShowSuspension: false, // 是否显示悬浮窗switch
      goodsDetail: true, // 商品详情页checkbox
      peasonCenter: true, // 个人中心checkbox
      customPage: true, // 自定义页面 checkbox
      customPageSelect: [], // 选择的自定义页面数据
      backSelectDataSus: [], // 选择页面弹窗回显数据
      fixedShow: '1', // 悬浮方式raido
      isShowIcon: true, // 主图标switch
      beforeExpansionUrlB: this.$imageHost + '/image/admin/origin_add.png',
      afterExpansionB: this.$imageHost + '/image/admin/origin_close.png',
      beforeExpansionUrl: this.$imageHost + '/image/admin/origin_add.png', // 展开前icon
      afterExpansion: this.$imageHost + '/image/admin/origin_close.png', // 展开后icon
      isChangeMainIcon: false, // 是否是主图标里的图片切换
      changeMainIconIndex: null, // 切换的主图icon下标
      checked: true,
      checked1: true,
      checked2: 1, // 选择系统图标选中项radio值
      systemIconVisible: false, // 选择系统图标弹窗flag
      systemIconDataNowShow: [],
      systemIconData: [ // 选择系统图标客服数据
        {
          imgUrl: this.$imageHost + '/image/admin/origin_kf_1.png',
          checked: 0
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_kf_2.png',
          checked: 1
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_kf_3.png',
          checked: 2
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_kf_4.png',
          checked: 3
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_kf_5.png',
          checked: 4
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_kf_6.png',
          checked: 5
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_kf_7.png',
          checked: 6
        }
      ],
      systemIconPhoneData: [ // 选择系统图标手机数据
        {
          imgUrl: this.$imageHost + '/image/admin/origin_ph_1.png',
          checked: 0
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_ph_2.png',
          checked: 1
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_ph_3.png',
          checked: 2
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_ph_4.png',
          checked: 3
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_ph_5.png',
          checked: 4
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_ph_6.png',
          checked: 5
        },
        {
          imgUrl: this.$imageHost + '/image/admin/origin_ph_7.png',
          checked: 6
        }
      ],
      childrenIconData: [
        {
          title: '客服',
          titleChecked: true,
          iconImgUrl: this.$imageHost + '/image/admin/origin_kf_2.png',
          isIndependentShow: true
        },
        {
          title: '电话',
          titleChecked: true,
          iconImgUrl: this.$imageHost + '/image/admin/origin_ph_1.png',
          isIndependentShow: true,
          phInput: ''
        },
        {
          title: '分享',
          titleChecked: false,
          iconImgUrl: this.$imageHost + '/image/admin/origin_share.png',
          isIndependentShow: false,
          resetIcon: this.$imageHost + '/image/admin/origin_share.png'
        },
        {
          title: '购物车',
          titleChecked: false,
          iconImgUrl: this.$imageHost + '/image/admin/origin_cart.png',
          isIndependentShow: false,
          resetIcon: this.$imageHost + '/image/admin/origin_cart.png'
        },
        {
          title: '返回顶部',
          titleChecked: false,
          iconImgUrl: this.$imageHost + '/image/admin/origin_go_top.png',
          isIndependentShow: false,
          resetIcon: this.$imageHost + '/image/admin/origin_go_top.png'
        },
        {
          title: '自定义',
          titleChecked: false,
          iconImgUrl: this.$imageHost + '/image/admin/origin_question.png',
          isIndependentShow: false,
          resetIcon: this.$imageHost + '/image/admin/origin_question.png',
          customIconName: '',
          customLink: ''
        }
      ], // 子图标数组
      changeChldernIconIndex: null // 子图标项中点击更换图标下标
    }
  },
  methods: {
    // 底部保存综合处理
    handleToFooter (flag) {
      console.log(flag)
    },
    // 点击左边部分主icon
    handleToClickMainIcon (flag) {
      console.log(flag)
      if (flag) {
        this.holdMainIcon = true
      } else {
        this.holdMainIcon = false
      }
      console.log(this.holdMainIcon)
    },
    // 点击选择页面
    handleToClickAddPage () {
      this.tuneUpSelectTemplate = !this.tuneUpSelectTemplate
    },
    // 选择页面弹窗选中回传数据
    handleSelectTemplate (res) {
      console.log(res)
      let arr = []
      res.forEach((item, index) => {
        console.log(typeof item)
        if (typeof item === 'number') {
          arr.push(item)
        } else {
          arr.push(item.pageId)
        }
      })
      console.log(arr)
      this.backSelectDataSus = arr
      // 374 375 354 353
      this.customPageSelect = res
    },
    // 点击主图标展开
    handleChangeIcon (index, flag) {
      this.isChangeMainIcon = true
      this.changeMainIconIndex = index
      this.tuneUp = !this.tuneUp
    },
    // 选择主图  选择图片回传数据
    handleSelectImg (res) {
      console.log(res)
      if (this.isChangeMainIcon) {
        if (this.changeMainIconIndex) {
          this.afterExpansion = res.imgUrl
        } else {
          this.beforeExpansionUrl = res.imgUrl
        }
      } else {
        this.childrenIconData[this.changeChldernIconIndex].iconImgUrl = res.imgUrl
        if (this.changeChldernIconIndex === 0) {
          this.checked2 = 0
        } else if (this.changeChldernIconIndex === 1) {
          this.checked2 = 0
        }
      }
      this.isChangeMainIcon = false
    },
    // 调用系统弹窗
    handleToClickSysDialog (index) {
      if (index > 1 && index < 5) {
        this.childrenIconData[index].iconImgUrl = this.childrenIconData[index].resetIcon
        return
      }
      this.changeChldernIconIndex = index
      if (index) {
        this.systemIconDataNowShow = this.systemIconPhoneData
      } else {
        this.systemIconDataNowShow = this.systemIconData
      }
      this.systemIconDataNowShow.forEach((item, index) => {
        console.log(item.imgUrl, this.childrenIconData[this.changeChldernIconIndex].iconImgUrl)
        if (item.imgUrl === this.childrenIconData[this.changeChldernIconIndex].iconImgUrl) {
          this.checked2 = index
        }
      })
      this.systemIconVisible = true
    },
    // 选择系统弹窗确定事件
    handleToSureSysIcon () {
      this.systemIconVisible = false
      this.childrenIconData[this.changeChldernIconIndex].iconImgUrl = this.systemIconDataNowShow[this.checked2].imgUrl
      console.log(this.changeChldernIconIndex)
    },
    // 子图标中的点击更换图标
    handleChangeChldrenIcon (index) {
      console.log(index)
      this.isChangeMainIcon = false
      this.changeChldernIconIndex = index
      this.tuneUp = !this.tuneUp
    },
    // 点击选择链接按钮
    handleToClickPath (index) {
      this.changeChldernIconIndex = index
      this.tuneUpSelectLink = !this.tuneUpSelectLink
    },
    // 选择链接弹窗选中数据回传
    selectLinkPath (path) {
      console.log(path)
      if (path) {
        console.log(path, this.childrenIconData, this.changeChldernIconIndex)
        this.childrenIconData[this.changeChldernIconIndex].customLink = path
      }
    }
  }
}
</script>
<style scoped lang="scss">
@import "@/assets/aliIcon/iconfont.scss";
.suspendWindow {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 650px;
  position: relative;
  .suspendWindowMain {
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding-bottom: 96px;
    display: flex;
    justify-content: center;
    padding-top: 28px;
    .config {
      width: 920px;
      display: flex;
      justify-content: space-between;
      .configLeft {
        width: 375px;
        height: 600px;
        border: 1px solid #ccc;
        background: #f5f5f5;
        .title {
          height: 55px;
        }
        .leftInfo {
          position: relative;
          height: 545px;
          .floatNav {
            position: absolute;
            bottom: 80px;
            right: 10px;
            width: 50px;
            height: 50px;
            z-index: 2;
            transition: transform 0.3s;
            .nav_item_main {
              position: absolute;
              top: 0;
              left: 0;
            }
            .nav_item {
              width: 50px;
              height: 50px;
              border-radius: 50%;
              box-shadow: 0px 2px 10px 0px rgba(204, 204, 204, 0.66);
              overflow: hidden;
              cursor: pointer;
              img {
                height: 50px;
                width: 50px;
                z-index: 1;
                transition: all 0.3s;
              }
              .nav_item_main_1 {
                position: absolute;
                top: 0;
                left: 0;
              }
              .nav_item_main_2 {
                opacity: 0;
                visibility: hidden;
                transform: rotate(-90deg);
              }
              .active1 {
                opacity: 0;
                visibility: hidden;
                transform: rotate(90deg);
              }
              .active3 {
                opacity: 1;
                visibility: visible;
                transform: none;
              }
            }
            .navList {
              .navLi {
                position: absolute;
                top: 0;
                left: 0;
                opacity: 0;
                visibility: hidden;
                transition: all 0.3s;
              }
              .navListAct {
                opacity: 1;
                visibility: visible;
              }
            }
            .surpass_content {
              background: rgba(0, 0, 0, 0.5);
              height: 150px;
              position: absolute;
              bottom: 0px;
              left: 50%;
              width: 210px;
              margin-left: -245px;
              border-radius: 10px;
              display: flex;
              flex-wrap: wrap;
              align-items: center;
              padding: 10px 4px 5px 6px;
              transform: translate3d(calc(50vw - 40px), 50%, 0) scale3d(0, 0, 1);
              opacity: 0;
              transition: transform 0.3s, opacity 0.2s;
              .surpass_every {
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                margin-bottom: 5px;
                width: 65px;
                .surpass_div {
                  width: 45px;
                  height: 45px;
                  border-radius: 50%;
                  background: #fff;
                  margin-bottom: 2px;
                  overflow: hidden;
                  img {
                    height: 45px;
                  }
                }
                span {
                  color: #fff;
                  font-size: 12px;
                }
              }
            }
          }
        }
      }
      .configRight {
        width: 480px;
        .list {
          width: 100%;
          border: 1px solid #efefef;
          margin-bottom: 10px;
          .listTitle {
            padding: 0 12px;
            line-height: 40px;
            color: #000;
            background: #f8f8f8;
            display: flex;
            justify-content: space-between;

            .rightRadio {
              display: flex;
              align-items: center;
              justify-content: center;
            }
          }
          .pageContent {
            padding: 0 10px;
          }
          .pageLi {
            padding: 10px 0;
            .top {
              display: flex;
              .li {
                margin-right: 70px;
              }
              .navIconLeft {
                display: flex;
                .nav_icon {
                  // margin-top: -5px;
                  display: inline-block;
                  width: 70px;
                  height: 70px;
                  border: 1px solid #e6e6e6;
                  text-align: center;
                  line-height: 70px;
                  position: relative;
                  margin-left: 20px;
                  .icon_box {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100%;
                    img {
                      width: 70px;
                      height: 70px;
                    }
                    span {
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
                  }
                }
                .tips {
                  display: flex;
                  align-items: flex-start;
                  justify-content: space-between;
                  flex-direction: column;
                  height: 70px;
                  padding-left: 10px;
                  .reset {
                    color: #5a8bff;
                    cursor: pointer;
                    i {
                      font-size: 16px;
                    }
                  }
                }
              }
            }
            .childTop {
              display: flex;
              flex-direction: column;
              /deep/ .el-input {
                width: 175px;
              }
            }
            .bottom {
              display: flex;
              margin-top: 10px;
              align-items: center;
              .addPage {
                width: 80px;
                height: 30px;
                border-radius: 2px;
                border: solid 1px #5a8bff;
                line-height: 30px;
                text-align: center;
                color: #5a8bff;
                cursor: pointer;
                margin: 0 10px;
              }
            }
            .childIconLeft {
              width: 80px;
              /deep/ .el-checkbox__label {
                padding-left: 5px;
              }
            }
          }
          .iconBottom {
            display: flex;
            border-bottom: 1px dashed #ddd;
          }
          .resetDiv {
            width: 270px;
            display: flex;
            justify-content: space-between;
          }
        }
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
  .systemIconMain {
    display: flex;
    flex-wrap: wrap;
    .sysList {
      display: flex;
      flex-direction: column;
      width: 70px;
      align-items: center;
      img {
        width: 50px;
        height: 50px;
      }
      /deep/ .el-checkbox {
        display: flex;
        justify-content: center;
      }
      /deep/ .el-radio__label {
        display: none;
      }
    }
  }
  .custom {
    display: flex;
    justify-content: space-between;
    width: 348px;
    i {
      color: #5a8bff;
      cursor: pointer;
    }
  }
  .customLink {
    display: flex;
    .choiseLink {
      height: 30px;
      line-height: 30px;
      text-align: center;
      color: #333;
      background-color: #fff;
      border: 1px solid #eee;
      padding: 0 5px;
      margin-left: 5px;
      font-size: 13px;
      cursor: pointer;
    }
  }
}
</style>
