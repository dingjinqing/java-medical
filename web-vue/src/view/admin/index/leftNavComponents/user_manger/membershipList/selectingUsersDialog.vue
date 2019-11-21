<template>
  <div>
    <div class="baseInfo">
      <el-dialog
        title="选择用户"
        :visible.sync="modifypersonDialogVisible"
        width="50%"
        :modal-append-to-body="false"
      >
        <div
          class="modifypersonDiv"
          style="margin-bottom:30px"
        >
          <div class="modifypersonDivTop">
            <div>
              <span>昵称</span>
              <el-input
                size="small"
                v-model="nameInput"
                placeholder="请输入内容"
              ></el-input>
            </div>
            <div>
              <span style="width:80px">手机号</span>
              <el-input
                size="small"
                v-model="nameInput"
                placeholder="请输入内容"
              ></el-input>
            </div>
            <div>
              <span style="width:90px">真实姓名</span>
              <el-input
                size="small"
                v-model="nameInput"
                placeholder="请输入内容"
              ></el-input>
            </div>
            <el-button
              type="primary"
              size="small"
            >搜索</el-button>
          </div>
          <!--底部表格-->
          <div
            class="content"
            v-if="page_one"
          >
            <table width='100%'>
              <thead>
                <tr>
                  <td>用户ID</td>
                  <td>昵称</td>
                  <td>手机号</td>
                  <td>真实姓名</td>

                </tr>
              </thead>
              <tbody v-if="tbodyFlag">
                <tr
                  v-for="(item,index) in trList"
                  :key="index"
                  :class="clickIindex===index?'clickClass':''"
                  @click="handleClick(index)"
                >

                  <td>{{item.title}}</td>
                  <td>{{item.title}}</td>
                  <td>{{item.title}}</td>
                  <td>{{item.title}}</td>
                  <td class="link">{{item.status}}</td>
                  <td class="tb_decorate_a">
                    {{item.path}}
                  </td>
                </tr>
              </tbody>

            </table>
            <div
              class="noData"
              v-if="!tbodyFlag"
            >
              <img :src="noImg">
              <span>暂无相关数据</span>
            </div>
          </div>
          <div
            class="content_two"
            v-else
          >
            <table width='100%'>
              <thead>
                <tr>
                  <td>名称</td>

                  <td>链接</td>
                </tr>
              </thead>
              <tbody v-if="tbodyFlag">
                <tr
                  v-for="(item,index) in trList"
                  :key="index"
                  :class="clickIindex===index?'clickClass':''"
                  @click="handleClick(index)"
                >
                  <td>{{item.title}}</td>

                  <td class="tb_decorate_a">
                    {{item.path}}
                  </td>
                </tr>
              </tbody>

            </table>
            <div
              class="noData"
              v-if="!tbodyFlag"
            >
              <img :src="noImg">
              <span>暂无相关数据</span>
            </div>
          </div>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            @click="modifypersonDialogVisible = false"
          >取 消</el-button>
          <el-button
            type="primary"
            size="small"
            @click="modifypersonDialogVisible = false"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  data () {
    return {
      modifypersonDialogVisible: false,
      nameInput: '',
      page_one: true,
      page_two: true,
      tbodyFlag: false,
      tbodyFlagTwo: true,
      trList: [
        {
          title: '111',
          path: 'pages/index/index',
          classification: '分类1',
          status: '营业中',
          spanId: ''
        },
        {
          title: '门店列表页',
          path: 'pages/storelist/storelist',
          spanId: '',
          classification: '分类2',
          status: '歇业中'
        },
        {
          title: '购物车页',
          path: 'pages/cart/cart',
          classification: '分类3',
          spanId: '',
          status: '营业中'
        }

      ],
      noImg: this.$imageHost + '/image/admin/no_data.png'
    }
  },
  computed: {
    ...mapGetters(['toHandleSelectingUsersflag']),
    toHandleSelectingUsersflag_ () {
      return this.toHandleSelectingUsersflag
    }
  },
  watch: {
    toHandleSelectingUsersflag_ (newData) {
      this.modifypersonDialogVisible = newData
    },
    modifypersonDialogVisible (newData) {
      if (newData === false) {
        this.toHandleSelectingUsersDialog(false)
      }
    }
  },
  methods: {
    ...mapActions(['toHandleSelectingUsersDialog'])
  }
}
</script>
<style scoped>
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
table {
  border: 1px solid #eff1f5;
  border-collapse: collapse;
  font-size: 14px;
  border-spacing: 0 0;
}
.clickClass {
  background-color: #eee !important;
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
/* thead td:nth-of-type(1) {
  width: 220px;
}
thead td:nth-of-type(2) {
  width: 104px;
} */

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
.modifypersonDivTop,
.modifypersonDivTop > div {
  display: flex;
}
.modifypersonDivTop > div > span {
  line-height: 32px;
  height: 32px;
  display: block;
  width: 56px;
}
.content {
  margin-top: 10px;
}
</style>
<style>
.modifypersonDivTop .el-input__inner {
  width: 140px !important;
}
</style>
