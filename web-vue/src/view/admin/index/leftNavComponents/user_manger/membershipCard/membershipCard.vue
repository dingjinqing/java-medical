<template>
  <div class="membershipCard">
    <div class="membershipCardMain">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          label="普通会员卡"
          name="first"
        >
          <div class="firstDiv">
            <div
              class="firstListDiv"
              v-for="(item,index) in cardData"
              :key="index"
            >
              <div
                class="firstTop"
                :style="getStyle(item,index)"
              >
                <img
                  v-if="item.isStop"
                  class="hidden"
                  :src="$imageHost+'/image/admin/card_no_use.png'"
                >
                <div class="card_status">{{item.isStop?item.noUse:item.tips}}</div>
                <div class="card_info_box">
                  <img
                    class="user_head"
                    :src="item.headImgUrl"
                  >
                  <div class="card_info_Right">
                    <p class="cardName">{{item.cardName}}</p>
                    <div class="time">{{item.data}}</div>
                  </div>
                  <div class="card_edit">
                    <div @click="handleToTips(0,item,index,0)">

                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="编辑"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_edit.png'">
                      </el-tooltip>
                    </div>
                    <div
                      style="margin:0 5px"
                      @click="handleToTips(1,item,index,0)"
                    >
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="分享"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_share_new.png'">
                      </el-tooltip>
                    </div>

                    <div @click="handleToTips(2,item,index,0)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        :content="item.isStop?'启用':'停用'"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_disable.png'">
                      </el-tooltip>
                    </div>

                  </div>
                </div>
              </div>
              <div class="card_condition">
                <p>领取条件:<span>{{item.conditions}}</span></p>
                <p style="margin-top:7px">会员权益:<span>{{item.equity}}</span></p>
              </div>
              <div class="card_footer">
                <span
                  style="cursor:pointer"
                  v-for="(itemC,indexC) in item.detailsOfRights"
                  :key="indexC"
                  @click="handleToCardBottom(item,0)"
                >{{itemC}}{{indexC===item.detailsOfRights.length-1?'':'-'}}</span>
              </div>
            </div>
            <div
              class="new_card"
              @click="handleToCardDetail(0)"
            >
              <img :src="new_card_img">
              <span style="color: #9e9e9e;font-size: 15px;padding: 12px 0">添加会员卡</span>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="限次会员卡"
          name="second"
        >
          <div class="firstDiv">
            <div
              class="firstListDiv"
              v-for="(item,index) in cardDataSecond"
              :key="index"
            >
              <div
                class="firstTop"
                :style="getStyle(item,index)"
              >
                <img
                  v-if="item.isStop"
                  class="hidden"
                  :src="$imageHost+'/image/admin/card_no_use.png'"
                >
                <div class="card_status">{{item.isStop?item.noUse:item.tips}}</div>
                <div class="card_info_box">
                  <img
                    class="user_head"
                    :src="item.headImgUrl"
                  >
                  <div class="card_info_Right">
                    <p class="cardName">{{item.cardName}}</p>
                    <div class="time">{{item.data}}</div>
                  </div>
                  <div class="card_edit">
                    <div @click="handleToTips(0,item,index,1)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="编辑"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_edit.png'">
                      </el-tooltip>
                    </div>
                    <div
                      style="margin:0 5px"
                      @click="handleToTips(1,item,index,1)"
                    >
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="分享"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_share_new.png'">
                      </el-tooltip>
                    </div>
                    <div @click="handleToTips(2,item,index,1)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="停用"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_disable.png'">
                      </el-tooltip>
                    </div>

                  </div>
                </div>
              </div>
              <div class="card_condition">
                <p>领取条件:<span>{{item.conditions}}</span></p>
                <p style="margin-top:7px">会员权益:<span>{{item.equity}}</span></p>
              </div>
              <div class="card_footer">
                <span
                  style="cursor:pointer"
                  v-for="(itemC,indexC) in item.detailsOfRights"
                  :key="indexC"
                  @click="handleToCardBottom(item,1)"
                >{{itemC}}{{indexC===item.detailsOfRights.length-1?'':'-'}}</span>
              </div>
            </div>
            <div
              class="new_card"
              @click="handleToCardDetail(1)"
            >
              <img :src="new_card_img">
              <span style="color: #9e9e9e;font-size: 15px;padding: 12px 0">添加会员卡</span>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane
          label="等级会员卡"
          name="third"
        >
          <div class="firstDiv">
            <div
              class="firstListDiv"
              v-for="(item,index) in cardDataThird"
              :key="index"
            >
              <div
                class="firstTop"
                :style="getStyle(item,index)"
              >
                <img
                  v-if="item.isStop"
                  class="hidden"
                  :src="$imageHost+'/image/admin/card_no_use.png'"
                >
                <div class="card_status">{{item.isStop?item.noUse:item.tips}}</div>
                <div class="card_info_box">
                  <img
                    class="user_head"
                    :src="item.headImgUrl"
                  >
                  <div class="card_info_Right">
                    <p class="cardName">{{item.cardName}}</p>
                    <div class="time">{{item.data}}</div>
                  </div>
                  <div class="card_edit">
                    <div @click="handleToTips(0,item,index,2)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="编辑"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_edit.png'">
                      </el-tooltip>
                    </div>
                    <div
                      style="margin:0 5px"
                      @click="handleToTips(1,item,index,2)"
                    >
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="分享"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_share_new.png'">
                      </el-tooltip>
                    </div>
                    <div @click="handleToTips(2,item,index,2)">
                      <el-tooltip
                        class="item"
                        effect="dark"
                        content="停用"
                        placement="top-start"
                      >
                        <img :src="$imageHost + '/image/admin/card_disable.png'">
                      </el-tooltip>
                    </div>
                  </div>
                </div>
              </div>
              <div class="card_condition">
                <p>领取条件:<span>{{item.conditions}}</span></p>
                <p style="margin-top:7px">会员权益:<span>{{item.equity}}</span></p>
              </div>
              <div class="card_footer">
                <span
                  style="cursor:pointer"
                  v-for="(itemC,indexC) in item.detailsOfRights"
                  :key="indexC"
                  @click="handleToCardBottom(item,2)"
                >{{itemC}}{{indexC===item.detailsOfRights.length-1?'':'-'}}</span>
              </div>
            </div>
            <div
              class="new_card"
              @click="handleToCardDetail(2)"
            >
              <img :src="new_card_img">
              <span style="color: #9e9e9e;font-size: 15px;padding: 12px 0">添加会员卡</span>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!--二维码弹窗-->
    <ShareCodeDialog />
  </div>
</template>
<script>
export default {
  components: {
    ShareCodeDialog: () => import('@/components/admin/shareCodeDialog')
  },
  data () {
    return {
      activeName: 'first',
      cardData: [
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: true
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 1,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 1,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 1,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        }
      ],
      cardDataSecond: [
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 1,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 1,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },

        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        }
      ],
      cardDataThird: [
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        },
        {
          tips: '使用中',
          noUse: '停止使用',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 1,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          isStop: false
        }

      ],
      new_card_img: this.$imageHost + '/image/admin/add_card.png'
    }
  },
  mounted () {
    // 初始化tap切换
    let name = this.$route.name
    switch (name) {
      case 'user_card':
        this.activeName = 'first'
        break
      case 'limitTimes':
        this.activeName = 'second'
        break
      case 'GradeCard':
        this.activeName = 'third'
        break
    }
  },
  methods: {
    // tap切换
    handleClick (tab, event) {
      switch (tab.index) {
        case '0':
          this.$router.push({
            name: 'user_card'
          })
          break
        case '1':
          this.$router.push({
            name: 'limitTimes'
          })
          break
        case '2':
          this.$router.push({
            name: 'GradeCard'
          })
      }

      console.log(tab, event)
    },
    // 动态改变行间样式
    getStyle (item, index) {
      if (item.isStop) {
        return 'background-color:#ddd'
      }
      if (item.type === 0) {
        console.log('背景颜色')
        return 'background-color:' + item.backgroundColor
      } else {
        console.log('背景图片', item.backgroundImg)
        return 'backgroundImage:url(' + item.backgroundImg + ')'
      }
    },
    // 跳转到会员卡详情页
    handleToCardDetail () {
      console.log(1111)
      this.$router.push({
        name: 'membershipCardDetail'
      })
    },
    // tips系列点击
    handleToTips (flag, item, index, type) {
      console.log(flag)
      switch (flag) {
        case 0:
          this.$router.push({
            name: 'membershipCardDetail'
          })
          break
        case 1:
          console.log('q')
          this.$http.$emit('shareCodeDialog', item)
          break
        case 2:
          if (type === 0) {
            this.cardData[index].isStop = !this.cardData[index].isStop
          } else if (type === 1) {
            this.cardDataSecond[index].isStop = !this.cardData[index].isStop
          } else if (type === 2) {
            this.cardDataThird[index].isStop = !this.cardData[index].isStop
          }
      }
    },
    // 会员卡底部系列点击
    handleToCardBottom (item, flag) {
      let obj = {
        item,
        flag
      }
      this.$store.commit('TOCHANFE_CARDCRUMDATA', obj)
      this.$router.push({
        name: 'Cardholder'
      })
      console.log(item, flag)
    }
  }
}
</script>
<style lang="scss" scoped>
.membershipCard {
  padding: 10px;
  padding-bottom: 68px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .membershipCardMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    .firstDiv {
      background: #fff;
      padding: 0 1%;
      overflow: hidden;
      min-width: 1000px;
      .firstListDiv {
        float: left;
        margin-right: 1%;
        margin-bottom: 20px;
        background-color: #fff;
        border-radius: 10px;
        height: 255px;
        box-shadow: 0px 2px 16px 0px #e5e5e5;
        .firstTop {
          // background: #990000;
          .hidden {
            position: absolute;
            top: 0;
            left: 0;
          }
          width: 270px;
          height: 150px;
          border-radius: 10px;
          padding-top: 35px;
          position: relative;
          .card_status {
            line-height: 24px;
            padding: 0 10px;
            background-color: rgba(0, 0, 0, 0.3);
            color: #fff;
            position: absolute;
            right: 0;
            top: 0;
            border-top-right-radius: 10px;
            border-bottom-left-radius: 10px;
            font-size: 13px;
          }
          .card_info_box {
            padding: 0 10px;
            .user_head {
              float: left;
              width: 40px;
              height: 40px;
              border-radius: 50%;
            }
            .card_info_Right {
              margin-left: 50px;
              .cardName {
                color: #fff;
              }
              .time {
                margin-top: 4px;
                color: #fff;
              }
            }
            .card_edit {
              display: flex;
              justify-content: flex-end;
              height: 22px;
              margin-top: 50px;
              text-align: right;
              .item {
                cursor: pointer;
              }
            }
          }
        }
        .card_condition {
          margin: 0 7px;
          border-bottom: 1px solid #eee;
          padding-bottom: 34px;
          margin-top: 10px;
          p {
            font-size: 12px;
            font-weight: 600;
            span {
              font-weight: normal;
            }
          }
        }
        .card_footer {
          padding-left: 7px;
          line-height: 30px;
          color: #5a8bff;
          font-size: 12px;
        }
      }
      .new_card {
        float: left;
        width: 270px;
        height: 150px;
        padding: 10px 1%;
        background: #f0f0f0;
        border-radius: 10px;
        margin-bottom: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        cursor: pointer;
      }
    }
  }
}
</style>
