<template>
  <div class="news_container">
    <div class="news_list">
      <div
        class="article_list"
        v-for="(item,index) in article_list_show"
        :key="index"
      >
        <div class="article_container">
          <div class="article_left">
            <img :src="item.imgUrl">
          </div>
          <div class="article_right">
            <div class="acticle_title">{{item.title}}</div>
            <div class="acticle_content">{{item.content}}</div>
            <div class="acticle_time">{{item.date}}</div>
          </div>
        </div>
      </div>

      <div class="pagination">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="1000"
          :current-page.sync="currentPage"
          @current-change='currentChange'
          @prev-click="prevClick"
          @next-click='nextClick'
        >
        </el-pagination>
      </div>
    </div>

  </div>
</template>
<script>
export default {
  data () {
    return {
      article_list_show: [],
      article_list: [
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190625/crop_P3W3q3eWsgLpjbLl.jpeg', title: '1店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！', content: '眉州东坡牵手北京掌上先机网络科技有限公司，涉足微信小程序线上业务。经过多次沟通、售后协助服务，跟进上线前期基础部署、解决后台功能使用问题。公司23周年店庆当天，小程序线上商城正式上线。', date: '19/06/21' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '2微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '3微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '4微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '5微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '6微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190625/crop_P3W3q3eWsgLpjbLl.jpeg', title: '7店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！', content: '眉州东坡牵手北京掌上先机网络科技有限公司，涉足微信小程序线上业务。经过多次沟通、售后协助服务，跟进上线前期基础部署、解决后台功能使用问题。公司23周年店庆当天，小程序线上商城正式上线。', date: '19/06/21' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '8微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '9微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '10微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' }

      ],
      query_data: [
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '11微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '12微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190625/crop_P3W3q3eWsgLpjbLl.jpeg', title: '13店+小程序助力“眉州东坡”，7天拉新会员近2万，上线当天交易额高达17万！', content: '眉州东坡牵手北京掌上先机网络科技有限公司，涉足微信小程序线上业务。经过多次沟通、售后协助服务，跟进上线前期基础部署、解决后台功能使用问题。公司23周年店庆当天，小程序线上商城正式上线。', date: '19/06/21' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '14微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '15微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '16微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '17微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' },
        { imgUrl: 'http://mpimg2.weipubao.cn/upload/0/image/20190515/crop_iTnTovfZJu6RUJyP.jpeg', title: '18微信好物圈新增入口，好物圈组内测上线，公众号支持修改20个字符！', content: '5月11日夜间，微信又重磅推出很多好消息，为微信电商带去流量红利。目前在微信【搜一搜】入口下，新增加【好物圈】入口。继小程序搜索入口的第二大入口诞生。好物圈还新增了【新建圈组】，能让收藏的商品推荐至对应圈组，也可以邀请好友进入该圈组，实现好物共享。【大家买过】实现好友买家秀展示，形成朋友圈商品流。', date: '19/05/15' }
      ],
      currentPage: 1
    }
  },
  mounted () {
    this.getData()
  },
  methods: {
    currentChange () {
      this.getData()
    },
    getData () {
      if (this.currentPage === 1) {
        this.article_list_show = this.article_list
      } else if (this.currentPage === 2) {
        this.article_list_show = this.query_data
      }
    },
    prevClick () {
      console.log(this.currentPage - 1)
    },
    nextClick () {
      console.log(this.currentPage + 1)
    }
  }
}
</script>
<style scoped>
.news_container {
  padding: 60px 0;
  background: #f2f2f2;
}
.news_list {
  width: 1100px;
  margin: 0 auto;
  background: #fff;
  padding: 0 80px;
}
.article_list {
  padding: 40px 0;
  border-bottom: 1px solid #eee;
}
.article_container {
  overflow: hidden;
}
.article_left {
  float: left;
  width: 290px;
  height: 220px;
}
.article_right {
  float: right;
  width: 600px;
  height: 220px;
  position: relative;
}
.article_left img {
  display: block;
  width: 100%;
}
.acticle_title {
  color: rgb(51, 51, 51);
  font-size: 20px;
}
.acticle_content {
  margin-top: 20px;
  font-size: 16px;
  color: #666;
  width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
.acticle_time {
  position: absolute;
  bottom: 0;
  font-size: 14px;
  color: #999;
}
</style>
<style>
.el-pagination {
  padding: 20px 0 20px 570px !important;
}
</style>
