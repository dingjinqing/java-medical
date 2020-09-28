<template>
  <div class="container">
    <el-menu
      class="el-menu-vertical-demo"
      background-color="#323a4d"
      text-color="#ccc"
      active-text-color="#fff"
      :unique-opened="true"
      :router="true"
      :default-active="$route.path"
      v-if="isRouterAlive"
    >
      <template v-for="(listItem, listIndex) in defaultList.list">
        <el-submenu
          :index="listItem.path"
          :key="listIndex"
          v-if="listItem.children && listItem.children.length"
        >
          <template slot="title">
            <img :src="listItem.imgUrl" />
            <span>{{ listItem.name | getNavName }}</span>
          </template>
          <el-menu-item
            :index="childrenItem.path"
            v-for="(childrenItem, childrenIndex) in listItem.children"
            :key="childrenIndex"
          >
            <span slot="title">{{ childrenItem.name | getNavName }}</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item :index="listItem.path" :key="listIndex" v-else>
          <img :src="listItem.imgUrl" />
          <span slot="title" v-if="!listItem.onlyPic">{{
            listItem.name | getNavName
          }}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
// import {
//   getShowMenu
// } from '@/api/store/store'
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      defaultList: {},
      overView: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/shop_look.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/shop_look_h.png',
          path: '/admin/store/shopView',
          span: '',
          name: 'overView',
          flag: false
        }
      ],
      storeList: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/page_decoration.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/page_decoration_h.png',
          path: '/admin/store/list',
          span: '',
          name: 'storeList',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/page_decoration.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/page_decoration_h.png',
          path: '/admin/store/group/config',
          span: '',
          name: 'storeGroupConfig',
          flag: false
        }
      ],
      storeGoods: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
          path: '/admin/store/goods',
          span: '',
          name: 'storeGoods',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
          path: '/admin/store/goods/saleOut',
          span: '',
          name: 'saleOutGoods',
          flag: false
        }
      ],
      storeOrder: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
          path: '/admin/store/order',
          span: '',
          name: 'storeOrder',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
          path: '/admin/store/order/selfPickUpOrder',
          span: '',
          name: 'selfPickUpOrder',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
          path: '/admin/store/order/intraCityOrder',
          span: '',
          name: 'intraCityOrder',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
          path: '/admin/store/order/afterSale',
          span: '',
          name: 'afterSale',
          flag: false
        }
      ],
      storeMember: [
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
          path: '/admin/store/member',
          span: '',
          name: 'storeMember',
          flag: false
        },
        {
          imgUrl: this.$imageHost + '/image/admin/icon_left/product_in.png',
          imgUrl_h: this.$imageHost + '/image/admin/icon_left/product_in_h.png',
          path: '/admin/store/member/permission',
          span: '',
          name: 'memberPermission',
          flag: false
        }
      ],
      menuParam: null,
      activeIndex: null,
      isRouterAlive: false
    }
  },
  watch: {
    $route: {
      handler: function (router) {
        let { meta: { meta = null }, path } = router
        this.initLeftNav(meta)
        this.activeIndex = path
      },
      immediate: true
    }
  },
  created () {
  },
  mounted () {
    this.filterNavShow()
  },
  methods: {
    async initLeftNav (meta) {
      // if (!this.menuParam) await this.filterNavShow()
      if (!this.hasOwnProperty(meta) || this.defaultList.meta === meta) return
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
      this.defaultList = {
        meta: meta,
        list: this[meta]
      }
      console.log('进入')
      console.log(this[meta])
    },
    filterNavShow () {
      console.log(this.showMenuData)
      let souceArray = this.showMenuData
      souceArray.forEach((item) => {
        if (item.sub.length) {
          this[item.enName] = item.sub.reduce((newArray, subItem) => {
            if (subItem.check === 1) {
              let objItem = this[item.enName].find(leftItem => {
                return leftItem.name === subItem.enName
              })
              newArray = [...newArray, objItem]
            }
            return newArray
          }, [])
        } else {
          let objItem = this[item.enName].find(leftItem => {
            return leftItem.name === item.enName
          })
          this[item.enName] = [objItem]
        }
      })
      // this.menuParam = menuParam
      // Object.keys(menuParam).forEach(keyItem => {
      //   if (!this.hasOwnProperty(keyItem)) return
      //   this[keyItem] = this[keyItem].reduce((defaultData, item) => {
      //     if (item.children && item.children.length) {
      //       item.children = item.children.reduce((childrenDefault, childrenItem) => {
      //         if (menuParam[keyItem].includes(childrenItem.name)) childrenDefault.push(childrenItem)
      //         return childrenDefault
      //       }, [])
      //     }
      //     if (menuParam[keyItem].includes(item.name)) defaultData.push(item)
      //     return defaultData
      //   }, [])
      // })
      // resolve()
    }
  },
  computed: {
    // activeIndex () {
    //   if (!this.$route.meta.category) return this.$route.path
    //   let topNavList = ['first_web_manage', 'first_web_decoration', 'goods_manage', 'first_trade_manage', 'first_market_manage', 'user_manger', 'store_manage', 'base_manger', 'doctor_manger', 'prescription_manger']
    //   let activePath = null
    //   try {
    //     topNavList.forEach(item => {
    //       let newList = this[item].reduce((defaultList, listItem) => {
    //         let routerItem = []
    //         if (listItem.children && listItem.children.length) {
    //           routerItem = JSON.parse(JSON.stringify(listItem.children))
    //           delete listItem.children
    //         }
    //         defaultList.push(listItem)
    //         if (routerItem.length) {
    //           defaultList = [...defaultList, ...routerItem]
    //         }
    //         return defaultList
    //       }, [])
    //       let target = newList.find(item => item.name === this.$route.meta.category)
    //       if (target) {
    //         activePath = target.path
    //         throw Error()
    //       }
    //     })
    //   } catch (error) {
    //   }
    //   return activePath
    //   return this.$route.path
    // }
    ...mapGetters(['showMenuData'])
  },
  filters: {
    getNavName (name) {
      let leftName = {
        overView: '概况',
        storeList: '门店列表',
        storeGroupConfig: '分组管理',
        storeGoods: '全部商品',
        storeMember: '全部店员',
        memberPermission: '店员权限',
        saleOutGoods: '售罄商品',
        storeOrder: '全部订单',
        selfPickUpOrder: '自提订单',
        intraCityOrder: '同城配送',
        afterSale: '售后中心'
      }
      return leftName[name]
    }
  }
}
</script>

<style lang="scss" scoped>
.container {
  height: 100%;
  width: 150px;
  z-index: 10;
  /deep/ .el-menu-vertical-demo {
    height: 100%;
    .el-menu-item {
      &:hover {
        background-color: #181e2e !important;
      }
    }
    .el-submenu {
      .el-menu-item {
        width: 100%;
        min-width: 0;
        background-color: #242a3a !important;
        padding-left: 44px !important;
        &:hover {
          background-color: #181e2e !important;
        }
      }
    }
    .el-submenu__title {
      &:hover {
        background-color: #181e2e !important;
      }
    }
    .el-menu-item {
      &.is-active {
        background-color: #181e2e !important;
      }
    }
  }
}
</style>
