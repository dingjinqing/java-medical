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
          @click="to_news(0,item_list.articleId)"
          v-for="(item_list,item_index) in item.new_list"
          :key='item_index'
        >
          <span>{{item_list.updateTime}}</span>
          <span :class="Recommend_class">{{item_list.desc}}</span>
          <span
            v-if="item_list.isRecommend==1?true:''"
            style="float: right;"
          >{{$t('Recommend')}}</span>
        </div>
      </li>
    </ul>
    <div class="news_div">
      <a
        @mouseenter="more_enter()"
        @mouseleave="more_leave()"
        @click="to_news(1)"
        :class="more_active"
        style="cursor:pointer"
      >{{$t('information.more')}}</a>
    </div>
    <!--底部申请使用模块-->
    <div
      class="applyModule"
      :style="'background:url('+$imageHost+'/image/admin/np_bg1.jpg'+') no-repeat'"
    >
      <div class="applyLeft">立即申请即可免费试用</div>
      <div
        class="applyBtn"
        @click="handleToTurnApplyPage()"
      >申请试用</div>
    </div>
  </div>
</template>
<script>
import { articleRequest } from '@/api/index/news.js'
export default {
  data () {
    return {
      newData: [
        { imgUrl: this.$imageHost + '/image/admin/official/news1.png',
          active_class: 'zoomout',
          new_list: [] },
        { imgUrl: this.$imageHost + '/image/admin/official/news2.png',
          active_class: 'zoomout',
          new_list: []
        },
        { imgUrl: this.$imageHost + '/image/admin/official/news3.png',
          active_class: 'zoomout',
          new_list: []
        }
      ],
      Recommend_class: '',
      more_active: ''
    }
  },
  mounted () {
    let that = this
    this.langDefault()
    this.articleRequest()
    this.$http.$on('lang_change', function (data) {
      if (data === 0) {
        that.Recommend_class = 'Recommend_class'
      } else if (data === 1) {
        that.Recommend_class = ''
      }
    })
  },
  methods: {
    // 跳转到新闻页面
    to_news (index, articleId) {
      console.log(index, this.newData)
      console.log(articleId)
      if (index === 0) {
        this.$router.push({
          name: 'newsDetail',
          query: {
            articleId: articleId
          }

        })
      } else {
        this.$router.push({
          name: 'newsList'

        })
      }
    },
    // 首页文章列表
    articleRequest () {
      let obj = {
        'categoryId': '4',
        'status': '1',
        'keywords': '',
        'sortName': 'is_recommend,desc;update_time,asc',
        'page': {
          'currentPage': '1',
          'pageRows': '9'
        }
      }
      articleRequest(obj).then((res) => {
        // res.content.dataList
        this.handleDataList(res.content.dataList)
      })
    },
    // 处理首页新闻列表数据
    handleDataList (res) {
      var obj1 = []
      let obj2 = []
      let obj3 = []
      for (let i = 0; i < res.length; i++) {
        if (i < 3) {
          obj1.push(res[i])
        } else if (i >= 3 && i < 6) {
          obj2.push(res[i])
        } else if (i >= 6) {
          obj3.push(res[i])
        }
      }
      console.log(obj1, obj2, obj3)
      for (let i = 0; i < obj1.length; i++) {
        let string = ''
        console.log(obj1[i].updateTime.split(' ')[0].split('-'))
        string = obj1[i].updateTime.split(' ')[0].split('-')[1] + '/' + obj1[i].updateTime.split(' ')[0].split('-')[2]
        console.log(string)
        obj1[i].updateTime = string
      }
      for (let i = 0; i < obj2.length; i++) {
        let string = ''
        console.log(obj2[i].updateTime.split(' ')[0].split('-'))
        string = obj2[i].updateTime.split(' ')[0].split('-')[1] + '/' + obj2[i].updateTime.split(' ')[0].split('-')[2]
        obj2[i].updateTime = string
      }
      for (let i = 0; i < obj3.length; i++) {
        let string = ''
        console.log(obj3[i].updateTime.split(' ')[0].split('-'))
        string = obj3[i].updateTime.split(' ')[0].split('-')[1] + '/' + obj3[i].updateTime.split(' ')[0].split('-')[2]
        obj3[i].updateTime = string
      }
      this.newData[0].new_list = obj1
      this.newData[1].new_list = obj2
      this.newData[2].new_list = obj3
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
    },
    // 底部点击申请使用事件
    handleToTurnApplyPage () {
      this.$router.push({
        name: 'indexHomeOntrial'
      })
    }
  }
}
</script>
<style scoped>
.info_container {
  background: #f7f7f7;
  text-align: center;
  position: relative;
  min-width: 100%;
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
<style lang="scss" scoped>
.applyModule {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 160px;
  color: #fff;
  margin-top: 60px;
  .applyLeft {
    font-size: 30px;
    margin-right: 20px;
  }
  .applyBtn {
    width: 130px;
    height: 40px;
    border: 1px solid #fff;
    border-radius: 4px;
    text-align: center;
    line-height: 40px;
    font-size: 18px;
    color: #fff;
    display: block;
    transition: background-color 0.2s linear, color 0.2s linear;
    cursor: pointer;
    &:hover {
      background-color: rgba(255, 255, 255, 0.3);
    }
  }
}
</style>
