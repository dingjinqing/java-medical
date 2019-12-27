<template>
  <div>
    <div class="top">
      <span>{{$t('selectLinks.marketingActivities')}}/ {{this.navText}}</span>
    </div>
    <div class="content">
      <table width='100%'>
        <thead>
          <tr>
            <td>{{$t('selectLinks.name')}}</td>
            <td v-if="couponFlag">{{$t('selectLinks.type')}}</td>
            <td>{{$t('selectLinks.termOfValidity')}}</td>
            <td>{{$t('selectLinks.link')}}</td>
          </tr>
        </thead>
        <tbody v-if="tbodyFlag">
          <tr
            v-for="(item,index) in trList"
            :key="index"
            :class="clickIindex===index?'clickClass':''"
            @click="handleClick(index)"
          >
            <td>{{item.actName}}</td>
            <td v-if="couponFlag"></td>
            <td class="link">{{item.startTime}}{{$t('selectLinks.to')}}{{item.endTime}}</td>
            <td class="tb_decorate_a">
              {{path}}{{item.id}}
            </td>
          </tr>
        </tbody>

      </table>
      <div
        class="noData"
        v-if="!tbodyFlag"
      >
        <img :src="noImg">
        <span>{{$t('selectLinks.noDataAvailable')}}</span>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters, mapActions } from 'vuex'
import { packListRequest, assessListRequest, cardListRequest, voucherListRequest, packageListRequest, mrkingListRequest, lotteryListRequest, pinListRequest, integrationListRequest, promoteListRequest, priceListRequest } from '@/api/admin/selectLinksApi/selectLinksApi'
export default {
  data () {
    return {
      trList: [],
      clickIindex: null,
      tbodyFlag: true,
      noImg: this.$imageHost + '/image/admin/no_data.png',
      navText: '',
      couponFlag: false, // 优惠券td flag
      path: '' //  显示和保存的路径
    }
  },
  computed: {
    ...mapGetters(['selectlinksIndex']),
    selectlinksIndex_ () {
      console.log(this.selectlinksIndex)
      return this.selectlinksIndex
    }
  },
  watch: {
    selectlinksIndex_: {
      handler (newData, oldData) {
        console.log(newData)
        if (newData.index === 7) {
          this.couponFlag = true
        } else {
          this.couponFlag = false
        }
        // 初始化数据
        this.defaultData(newData)
      },
      immediate: true
    }
  },
  mounted () {
    // 初始化语言
    this.langDefault()
  },
  methods: {
    ...mapActions(['choisePagePath']),
    defaultData (newData) {
      console.log(newData)
      this.clickIindex = null
      if (newData.levelIndex === 1) {
        this.navText = newData.navText
        switch (newData.index) {
          case 0:
            pinListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages/pinlotterylist/pinlotterylist?group_draw_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 1:
            integrationListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages/pinlotterylist/pinlotterylist?group_draw_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 2:
            promoteListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages/pinlotterylist/pinlotterylist?group_draw_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 3:
            priceListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages/maingoodslist/maingoodslist?identity_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 4:
            lotteryListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages/lottery/lottery?lottery_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 5:
            mrkingListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages/lottery/lottery?lottery_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 6:
            packageListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages/packagesalelist/packagesalelist?package_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 7:
            voucherListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages/getcoupon/getcoupon?code='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 8:
            cardListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages/usercardinfo/usercardinfo?card_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 9:
            assessListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages2/assessstart/assessstart?assess_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
            break
          case 10:
            packListRequest().then((res) => {
              if (res.error === 0) {
                if (!res.content.length) {
                  this.tbodyFlag = false
                } else {
                  this.tbodyFlag = true
                }
                this.path = 'pages1/couponpackage/couponpackage?pack_id='
                this.trList = res.content
              } else if (res.error === -1) this.tbodyFlag = false
              console.log(res)
            })
        }
      }
      console.log(newData)
    },
    // 行选中高亮
    handleClick (index) {
      this.clickIindex = index
      let path = `${this.path}${this.trList[index].id}`
      this.$emit('handleToGetDetailData', this.trList[index])
      this.choisePagePath(path)
    }

  }
}
</script>
<style scoped>
.top {
  padding: 10px;
  font-size: 14px;
  color: #333;
}
.noData {
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* width: 650px; */
  flex-direction: column;
  border: 1px solid #eee;
  margin-top: 10px;
}
.noData span {
  margin: 10px;
}
.top_container {
  padding-bottom: 10px;
}
.top_container {
  display: flex;
  justify-content: space-around;
}
.top_left {
  display: flex;
  align-items: center;
  /* margin-left: 7px; */
}
.top_middle {
  display: flex;
  align-items: center;
}

.clickClass {
  background-color: #eee !important;
}
.spanClass {
  display: block !important;
}
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
thead td {
  background: #faf9f8;
  text-align: center;
  color: #333;
  padding: 8px 10px;
  vertical-align: middle !important;
}
thead td:nth-of-type(1) {
  width: 120px;
}
thead td:nth-of-type(2) {
  width: 175px;
}
tbody td {
  text-align: center;
  border: 1px solid #eff1f5;
  color: #666;
}
td {
  padding: 8px 10px;
  vertical-align: middle !important;
  text-align: center;
}
</style>
