<template>
  <div class="info_container">
    <div class="light_head">
      <h4><span>{{$t('information.title')}}</span></h4>
      <div>{{$t('information.content')}}</div>
    </div>
    <ul class="clearfix news_ul">
      <li
        v-for="(item,index) in newData"
        :key="index"
      >
        <div><img
            :src="item.imgUrl"
            :class="item.active_class"
            @mouseenter="enter(index)"
            @mouseleave="leave(index)"
          ></div>
        <div
          class="new_li"
          v-for="(item_list,item_index) in item.new_list"
          :key='item_index'
        >
          <span>{{item_list.date}}</span>
          <span :class="Recommend_class">{{item_list.content}}</span>
          <span style="float: right;">{{$t('Recommend')}}</span>
        </div>
      </li>
    </ul>
    <div class="news_div">
      <a
        href="/index/article/list"
        target="_blank"
        @mouseenter="more_enter()"
        @mouseleave="more_leave()"
        :class="more_active"
      >{{$t('information.more')}}</a>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      newData: [
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/news1.png',
          active_class: 'zoomout',
          new_list: [{
            date: '06/21',
            content: '1店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！'
          },
          {
            date: '06/21',
            content: '2店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！'
          },
          {
            date: '06/21',
            content: '3店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！'
          }] },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/news2.png',
          active_class: 'zoomout',
          new_list: [{
            date: '06/21',
            content: '4店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！'
          },
          {
            date: '06/21',
            content: '5店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！'
          }, {
            date: '06/21',
            content: '6店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！'
          }]
        },
        { imgUrl: 'http://mpimg2.weipubao.cn/image/admin/official/news3.png',
          active_class: 'zoomout',
          new_list: [{
            date: '06/21',
            content: '7店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！'
          },
          {
            date: '06/21',
            content: '8店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！'
          }, {
            date: '06/21',
            content: '9店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！'
          }
          ]
        }
      ],
      Recommend_class: '',
      more_active: ''
    }
  },
  mounted () {
    let that = this
    this.langDefault()
    this.$http.$on('lang_change', function (data) {
      if (data === 0) {
        that.Recommend_class = 'Recommend_class'
      } else if (data === 1) {
        that.Recommend_class = ''
      }
    })
  },
  methods: {
    // 初始化语言
    langDefault () {
      if (localStorage.getItem('WEPUBAO_LANGUAGE') === 'en') {
        this.$i18n.locale = 'en'
        this.Recommend_class = 'Recommend_class'
      } else {
        this.$i18n.locale = 'cn'
        this.Recommend_class = ''
      }
    },
    // 图片划入事件
    enter (index) {
      this.newData[index].active_class = 'zoomin'
    },
    more_enter () {
      this.more_active = 'more_active'
    },
    // 图片划出事件
    leave (index) {
      this.newData[index].active_class = 'zoomout'
    },
    more_leave () {
      this.more_active = ''
    }
  }
}
</script>
<style scoped>
.info_container {
  background: #f7f7f7;
  text-align: center;
  padding-bottom: 70px;
  position: relative;
  min-width: 1024px;
  padding: 0 0px 20px;
}
.light_head {
  text-align: center;
  padding-top: 1px;
  margin-bottom: 70px;
}
.light_head h4 {
  margin: 70px 0 0px 0;
  font-size: 30px;
  color: #333;
}
.light_head div,
.about_prompt {
  color: #666;
  font-size: 16px;
  margin-bottom: 20px;
}
.light_head h4 span {
  display: inline-block;
  padding-bottom: 20px;
}
.news_ul {
  width: 1024px;
  margin: 70px auto 50px;
  overflow: hidden;
}

.news_ul li {
  float: left;
  width: 328px;
  margin-right: 13px;
  list-style: none;
}
.news_ul li div {
  margin-bottom: 15px;
  width: 100%;
  height: 128px;
  overflow: hidden;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
}
.news_ul li img {
  width: 100%;
  height: 100%;
  cursor: pointer;
}
.zoomout {
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -o-transform: scale(1);
  -webkit-transition: all 1s;
  -moz-transition: all 1s;
  -o-transition: all 1s;
}
.zoomin {
  -webkit-transform: scale(1.2);
  -moz-transform: scale(1.2);
  -o-transform: scale(1.2);
  -webkit-transition: all 1s;
  -moz-transition: all 1s;
  -o-transition: all 1s;
}
.news_div {
  text-align: center;
}
.news_div a {
  display: inline-block;
  border: 1px solid #5a8bff;
  color: #5a8bff;
  font-size: 16px;
  width: 120px;
  height: 40px;
  line-height: 40px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
  text-decoration: none;
}
.more_active {
  background-color: #5a8bff;
  color: #fff !important;
}
.new_li {
  color: #666666;
  font-size: 14px;
  text-align: left;
  margin-bottom: 8px !important;
  line-height: 18px;
  height: auto !important;
  cursor: pointer;
}
.new_li span {
  float: left;
  display: block;
}
.new_li span:nth-of-type(2) {
  margin-left: 5px;
  color: #333;
  width: 225px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.Recommend_class {
  width: 197px !important;
}
.new_li span:nth-of-type(3) {
  display: inline-block;
  color: #f66;
  font-size: 12px;
  border: 1px solid #f66;
  border-radius: 2px;
  padding: 0px 5px;
  margin-right: 2px;
}
.new_li span:nth-of-type(3):hover {
  color: #fff;
  background-color: #f66;
}
</style>
