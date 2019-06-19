<template>
  <div class="menu-container">
    <div class="absolute-top-header">
      <header id="header" style="color: white;">
        <div id="logo-group">
            <span id="logo">
                <img :src="logo_img_path"
                     style="height:49px;margin-top:13px"/>
            </span>
        </div>

        <div class="clearfix">
          <a href="#" style="color: white">
            <a href="item.linkUrl" v-for="item in menuList" :key="item.id">
              <div class="pull-left first">
                <div>{{ item.name }}</div>
              </div>
            </a>
          </a>
        </div>
        <!-- pulled right: nav area -->
        <div class="account">
          <!-- logout button -->
          <div class="btn-header transparent pull-right">
    <span>
      <span> <img :src="user.avatar" class="head-img" style="border-radius:100%;"/>                </span>
      <label>    {{ user.name}}  </label>
      <img src="/image/admin/img1.png" class="jiantou-top">
    </span>
          </div>
          <img src="/image/admin/active_top.png" class="menu_top" id="menu_top" style="display:none;">
        </div>
        <div class="log-menu" style="display:none;">
          <a href="/admin/account/manage" v-if="!user.isSubAccount">账户设置</a>
          <a href="/admin/subPasswordModify" v-else>账户设置</a>
          <a href="/admin/config/role/list" v-if="!user.isSubAccount">子账号权限管理</a>
          <a href="/admin/public/service/auth/list">授权公众号</a>
          <a href="/admin/account/shop/select" title="选择店铺" id="logout">选择店铺</a>
          <a href="/admin/logout" title="退出" id="logout">退出</a>
        </div>
      </header>
    </div>

    <div class="absolute-left-menu">
      <div class="left-menu-back">
        <div class="left-menu">
          <div class="left-menu-content">
            <dl class="item-menu" v-bind:sid="item.id" v-for="(item) in leftMenuList" :key="item.id">
              <a href="item.linkUrl" zid="0">
                <img :src="item.recommendPic" class="on show_tj" cid="0" v-if="item.recommendPic">
                <img :src="item.recommendPic" cid="1" fid="0" class="show_tj" v-if="item.recommendPic">
                <img :src="item.imageUrl" class="on" cid="0" v-if="!item.recommendPic">
                <img :src="item.imageHoverUrl" cid="1" fid="0" v-if="!item.recommendPic">
                <span class="menu-item-parent">{{item.name}}</span>
              </a>
              <div class="sub-menu" style="display:none" v-if="item.subMenu">
                <dl hidden style="display:block;" v-for="subMenu in item.subMenu" :key="subMenu.id">
                  <a :href="subMenu.linkUrl">{{ subMenu.name}}</a>
                </dl>
              </div>
            </dl>
          </div>

          <div style="margin-top:30px;">
            <div class="global_contact" id="global_contact">
              <div class="con_head" id="global_contact_header">
                <b class="con_icon"></b>
                <span class="con_content">联系我们</span>
              </div>
              <div class="con_body" id="global_contact_body">
                <div class="cons_qrcode"></div>
                <div class="cons_mobile">联系我们</div>
                <div class="cons_QQ">
                  <a href="http://wpa.qq.com/msgrd?v=3&uin=2895665430&site=qq&menu=yes"
                     class="cons_qq_btn" rel="nofollow"
                     target="_blank">企业QQ咨询</a></div>
                <div class="cons_feedback" data-toggle="modal"
                     data-target="#contact_feedback">意见反馈
                </div>
              </div>
            </div>
          </div>
          <div class="left_suspension">
            <div class="suspension">
              <div style="width: 100%;border-right: 1px solid #8c929e">
                <img src="/image/admin/left_menu_phone.png">
              </div>
              <div class="suspension_message sm1" style="width: 143px">
                <img src="/image/admin/left_menu_jt.png">
                <span style="display: inline-block;width: 100%;text-align: left;height: 30px;word-break: break-all;">客服电话：400-010-1039</span>
              </div>
            </div>
            <div class="suspension suspension_click1">
              <div style="width: 100%;border-right: 1px solid #8c929e">
                <img src="/image/admin/left_menu_zx.png">
              </div>
              <div class="suspension_message">
                <img src="/image/admin/left_menu_jt.png">
                <span><a href="tencent://message/?uin=3003715029&Site=&Menu=yes">在线咨询</a></span>
              </div>
            </div>
            <div class="suspension suspension_click2">
              <img src="/image/admin/left_menu_question.png">
              <div class="suspension_message">
                <img src="/image/admin/left_menu_jt.png">
                <span><a href="/admin/question/feedback">问题反馈</a></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import api from '@/util/api'

export default {
  name: 'menu',
  data () {
    return {
      user: {
        isSubAccount: false
      },
      menu: {
        topMenuList: [],
        leftMenuList: [],
        showMenu: true
      }
    }
  },
  mounted () {
    this.loadMenu()
    console.log('loadMenu')
  },
  methods: {
    loadMenu () {
      api('/admin/menu/list', function (d) {
        if (d && d.error === 0) {
          this.menu = d.content
        } else if (d && d.error !== 0) {
          window.layer.msg(d.message)
        }
      })
    }
  }
}
</script>
