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
                  :src="$imageHost+'/image/admin/origin_add.png'"
                  @click="handleToClickMainIcon(1)"
                >
                <img
                  class="nav_item_main_2"
                  :class="holdMainIcon?'active3':''"
                  :src="$imageHost+'/image/admin/origin_close.png'"
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
  </div>
</template>
<script>
export default {
  components: {
    selectTemplate: () => import('./selectTemplate.vue') // 选择页面弹窗
  },
  data () {
    return {
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
      isShowIcon: true // 主图标switch
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
    }
  }
}
</script>
<style scoped lang="scss">
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
        min-height: 600px;
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
          .pageLi {
            padding: 10px;
            .top {
              display: flex;
              .li {
                margin-right: 70px;
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
}
</style>
