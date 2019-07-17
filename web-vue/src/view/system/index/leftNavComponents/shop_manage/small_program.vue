<template>
<div id="tab">
  <!-- <div class="tab-bar clearfix">
    <a href="javascript:;"
        @click="tab(index)"
        :key="index"
        v-for="(item,index) in items"
        :class="{active : index===curId}"
    >{{item.item}}</a>
  </div>
  <div class="tab-con">
    <div
      :key="index"
      v-show="index===curId"
      v-for="(content, index) in contents" >{{content.content}}</div>
  </div> -->
</div>
</template>

<script>
export default {
  // data () {
  //   return {
  //     curId: 0,
  //     items: [
  //       {item: '商家账号列表'},
  //       {item: '商家账号添加'}
  //     ],
  //     contents: [
  //       {content: 'HTML'},
  //       {content: 'CSS'},
  //       {content: 'JavaScript'},
  //       {content: 'Vue'},
  //     ]
  //   }
  // },

  // methods: {
  //   tab (index) {
  //     this.curId = index;
  //   }
  // }
}
</script>

<style scoped>
  /* #tab {
    width: 400px;
    border: 1px solid #ccc;
    margin: 60px auto 0;
  }
  .tab-bar {
    width: 400px;
    background-color: #ccc;
  }
  .tab-bar a {
    float: left;
    width: 100px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    text-decoration: none;
    color: #000;
  }
  .tab-bar .active {
    background-color: #0099ff;
  }
  .tab-con div {
    text-align: left;
    height: 100px;
  } */
</style>
