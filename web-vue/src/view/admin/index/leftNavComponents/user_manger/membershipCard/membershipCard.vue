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
              <div class="firstTop">
                <div class="card_status">{{item.tips}}</div>
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
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="编辑"
                      placement="top-start"
                    >
                      <img :src="$imageHost + '/image/admin/card_edit.png'">
                    </el-tooltip>
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="分享"
                      placement="top-start"
                    >
                      <img :src="$imageHost + '/image/admin/card_share_new.png'">
                    </el-tooltip>
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
              <div class="card_condition">
                <p>领取条件:<span>{{item.conditions}}</span></p>
                <p style="margin-top:7px">会员权益:<span>{{item.equity}}</span></p>
              </div>
              <div class="card_footer">
                <span
                  style="cursor:pointer"
                  v-for="(itemC,indexC) in item.detailsOfRights"
                  :key="indexC"
                >{{itemC}}{{indexC===item.detailsOfRights.length-1?'':'-'}}</span>
              </div>
            </div>
            <div class="new_card">
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
              v-for="(item,index) in cardData"
              :key="index"
            >
              <div class="firstTop">
                <div class="card_status">{{item.tips}}</div>
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
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="编辑"
                      placement="top-start"
                    >
                      <img :src="$imageHost + '/image/admin/card_edit.png'">
                    </el-tooltip>
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="分享"
                      placement="top-start"
                    >
                      <img :src="$imageHost + '/image/admin/card_share_new.png'">
                    </el-tooltip>
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
              <div class="card_condition">
                <p>领取条件:<span>{{item.conditions}}</span></p>
                <p style="margin-top:7px">会员权益:<span>{{item.equity}}</span></p>
              </div>
              <div class="card_footer">
                <span
                  style="cursor:pointer"
                  v-for="(itemC,indexC) in item.detailsOfRights"
                  :key="indexC"
                >{{itemC}}{{indexC===item.detailsOfRights.length-1?'':'-'}}</span>
              </div>
            </div>
            <div class="new_card">
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
              v-for="(item,index) in cardData"
              :key="index"
            >
              <div
                class="firstTop"
                :style="getStyle(item,index)"
              >
                <div class="card_status">{{item.tips}}</div>
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
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="编辑"
                      placement="top-start"
                    >
                      <img :src="$imageHost + '/image/admin/card_edit.png'">
                    </el-tooltip>
                    <el-tooltip
                      class="item"
                      effect="dark"
                      content="分享"
                      placement="top-start"
                    >
                      <img :src="$imageHost + '/image/admin/card_share_new.png'">
                    </el-tooltip>
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
              <div class="card_condition">
                <p>领取条件:<span>{{item.conditions}}</span></p>
                <p style="margin-top:7px">会员权益:<span>{{item.equity}}</span></p>
              </div>
              <div class="card_footer">
                <span
                  style="cursor:pointer"
                  v-for="(itemC,indexC) in item.detailsOfRights"
                  :key="indexC"
                >{{itemC}}{{indexC===item.detailsOfRights.length-1?'':'-'}}</span>
              </div>
            </div>
            <div class="new_card">
              <img :src="new_card_img">
              <span style="color: #9e9e9e;font-size: 15px;padding: 12px 0">添加会员卡</span>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      activeName: 'first',
      cardData: [
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        },
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        },
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        },
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        },
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        },
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        },
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        },
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        },
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        },
        {
          tips: '使用中',
          headImgUrl: this.$imageHost + '/image/admin/img_home/testImg.jpeg',
          cardName: '我的会员卡',
          data: '永久有效',
          conditions: '直接领取;需要激活;无需审核;',
          equity: '会员折扣3.00折;充值奖励;积分奖励;',
          detailsOfRights: ['持卡会员', '充值记录'],
          type: 0,
          backgroundColor: '#990000',
          backgroundImg: this.$imageHost + '/image/admin/woment'
        }
      ],
      new_card_img: this.$imageHost + '/image/admin/add_card.png'
    }
  },
  methods: {
    // tap切换
    handleClick (tab, event) {
      console.log(tab, event)
    },
    getStyle (item, index) {
      console.log(item)
      // if (item.type === 0) {
      //   return 'background-color:' + item.backgroundColor
      // } else {
      //   return 'background-image:' + item.backgroundImg
      // }
      if (item.type === 0) {
        console.log('背景颜色')
        return 'background-color:#000'
      } else {
        console.log('背景图片')
        return 'background-image:http://mpdevimg2.weipubao.cn/upload/0/image/20190726/crop_EaDUXTmPqxVtgAga.jpeg'
      }
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
