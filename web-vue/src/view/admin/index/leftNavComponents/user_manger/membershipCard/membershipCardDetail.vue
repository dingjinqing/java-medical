<template>
  <div class="membershipCardDetail">
    <div class="membershipCardDetailMain">
      <div class="leftContainer">
        <div class="left_Top"></div>
        <div class="left_middle">
          <div class="example_card">
            <div class="card_detail">
              <img :src="$imageHost+'/image/admin/img_home/testImg.jpeg'">
            </div>
            <div class="effect_date">
              有效期：-
            </div>
          </div>
          <div class="discount_power">
            <div class="power_title">会员权益（折扣）</div>
          </div>
          <div class="score_power">
            <div class="s_power_title">会员折扣（积分）</div>
            <div class="s_power_detail">
              <p class="make_give"></p>
              <div
                class="man"
                style="display: block;"
              >
                <p>购物满100送100积分</p>
              </div>
              <p
                class="every_man"
                style="display: none;"
              ></p>
            </div>
          </div>
          <div class="score_power">
            <div class="s_chong_title">卡充值规则</div>
            <div class="s_power_detail">
              <p class="make_give"></p>
              <div
                class="man"
                style="display: block;"
              >
                <p>仅充值</p>
              </div>
              <p
                class="every_man"
                style="display: none;"
              ></p>
            </div>
          </div>
          <div class="ex_use_notice">
            <div class="notice-title">会员卡使用说明</div>
          </div>
          <div class="score_power">
            <div class="s_mendian_title">使用门店</div>
            <div class="s_power_detail">
              <p class="make_give"></p>
              <div
                class="man"
                style="display: block;"
              >
                <p>全部门店</p>
              </div>
              <p
                class="every_man"
                style="display: none;"
              ></p>
            </div>
          </div>
        </div>
      </div>
      <div class="rightContainer">

      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      leftNavData: [
        {
          backGroundImgUrl: '../../../../../../assets/adminImg/mem_card.png',
          title: '会员权益(折扣)',
          children: []
        },
        {
          backGroundImgUrl: '../../../../../../assets/adminImg/mem_card.png',
          title: '会员折扣(积分)',
          children: ['购物满100宋100积分']
        },
        {
          backGroundImgUrl: '../../../../../../assets/adminImg/mem_card.png',
          title: '卡充值规则',
          children: ['仅充值']
        },
        {
          backGroundImgUrl: '../../../../../../assets/adminImg/mem_card.png',
          title: '会员卡使用说明',
          children: []
        },
        {
          backGroundImgUrl: '../../../../../../assets/adminImg/mem_card.png',
          title: '使用门店',
          children: ['全部门店']
        }
      ]
    }
  }
}
</script>
<style scoped lang="scss">
.membershipCardDetail {
  padding: 10px;
  min-width: 100%;
  font-size: 14px;
  height: 100%;
  position: relative;
  overflow-y: auto;
  .membershipCardDetailMain {
    position: relative;
    background-color: #fff;
    overflow: hidden;
    overflow-y: auto;
    padding: 15px 25px;
    height: 100%;
    .leftContainer {
      width: 300px;
      margin-right: 20px;
      height: 594px;
      overflow-y: auto;
      border: 1px solid #ccc;
      background: #f5f5f5;
      .left_Top {
        height: 50px;
        width: 100%;
        background: url(../../../../../../assets/adminImg/mem_card.png)
          no-repeat;
        background-size: 100%;
      }
      .left_middle {
        .example_card {
          width: 90%;
          margin: 10px auto;
          border-radius: 10px;
          background: #e6cb96;
          color: #ffffff;
          padding: 10px 12px;
          .card_detail {
            margin-bottom: 30px;
            img {
              width: 50px;
              height: 50px;
              border-radius: 120px;
              margin-right: 10px;
            }
          }
          .effect_date {
            font-size: 13px;
          }
        }
        .discount_power {
          background-color: #fff;
          .power_title {
            padding: 10px 0;
            background: url(../../../../../../assets/adminImg/discount.png)
              no-repeat;
            background-size: 6%;
            background-position: center left;
            padding-left: 25px;
            font-size: 13px;
            color: #333;
            margin-left: 12px;
          }
        }
        .score_power {
          background-color: #fff;
          .s_power_title {
            padding: 10px 0;
            background: url(../../../../../../assets/adminImg/score_mem.png)
              no-repeat;
            background-size: 6%;
            background-position: center left;
            padding-left: 25px;
            font-size: 13px;
            color: #333;
            margin-left: 12px;
          }
          .man {
            color: #878787;
            padding: 0 0 10px;
            padding-left: 25px;
            margin-left: 12px;
            p {
              padding-top: 10px;
              color: #878787;
              padding: 0 0 10px;
            }
          }
          .s_chong_title {
            padding: 10px 0;
            background: url(../../../../../../assets/adminImg/charge_icon.png)
              no-repeat;
            background-size: 6%;
            background-position: center left;
            padding-left: 25px;
            font-size: 13px;
            color: #333;
            margin-left: 12px;
          }
        }
        .ex_use_notice {
          background-color: #fff;
          .notice-title {
            padding: 10px 0;
            background: url(../../../../../../assets/adminImg/article.png)
              no-repeat;
            background-size: 6%;
            background-position: center left;
            padding-left: 25px;
            font-size: 13px;
            color: #333;
            margin-left: 12px;
          }
        }
        .s_mendian_title {
          padding: 10px 0;
          background: url(../../../../../../assets/adminImg/store_icon.png)
            no-repeat;
          background-size: 6%;
          background-position: center left;
          padding-left: 25px;
          font-size: 13px;
          color: #333;
          margin-left: 12px;
        }
      }
    }
  }
}
</style>
