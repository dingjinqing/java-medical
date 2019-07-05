<template>
  <div class="main">
    <router-view></router-view>
  </div>
</template>
<script>

// import Cookies from 'js-cookie'
export default {
  // beforeRouteEnter: (to, from, next) => {
  //   console.log(to, 1111)

  //   let token = Cookies.get('V-Token')
  //   // 如果有token的话说明你已经登录,让你正常登录
  //   if (token) {
  //     next()
  //   } else {
  //     // 如果没有登录你访问的不是login就让你强制跳转到login页面
  //     if (to.path !== '/index/login') {
  //       next({ path: '/index/login' })
  //     } else {
  //       next()
  //     }
  //   }
  //   // next(vm => {
  //   //   // 此时该组件被实例化了
  //   //   alert(vm.infor)
  //   // })
  // }
}
</script>
<style scoped>
.main {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #e6e9f0;
}
</style>
