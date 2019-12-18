<template>
  <div class="addSort">
    <!-- header -->
    <el-row class="addSortRadio">
      <el-radio
        v-model="addSortRadio_radio"
        label="1"
      >添加一级分类</el-radio>
      <el-radio
        v-model="addSortRadio_radio"
        label="2"
      >添加二级分类</el-radio>
    </el-row>
    <!-- main -->
    <el-row
      class="one"
      v-if="addSortRadio_radio == '1'"
    >
      <!-- 一级分类 -->
      <el-form
        :model="firstLevel"
        :rules="firstLevel_rules"
        ref="firstLevel"
        label-width="100px"
        class="firstLevel"
      >
        <!-- 分类名称 -->
        <el-form-item
          label="分类名称："
          prop="sortName"
        >
          <el-input
            v-model="firstLevel.sortName"
            style="width:300px"
          > </el-input>
        </el-form-item>
        <!-- 分类优先级 -->
        <el-form-item
          label="分类优先级："
          prop="first"
        >
          <el-input
            placeholder="请输入1~100之间的整数"
            v-model="firstLevel.first"
            style="width:300px"
          ></el-input>
          <span class="first_tips">可填写1到100间的整数，数字越大前端排列顺序越靠前。优先级重复，则按照分类添加时间排序，添加越早越靠前排列</span>
        </el-form-item>
        <!-- 分类头图 -->
        <el-form-item
          label="分类头图："
          prop="sortImg"
        >
          <!-- 添加图片 -->
          <section
            class="addImg"
            @click="addImg"
          >
            <img
              class="add_simple"
              :src="$imageHost+'/image/admin/addSort/add_simple.png'"
              alt=""
            >
          </section>
          <span class="first_tips">显示在分类页顶部，不填写则不显示，建议尺寸510*200</span>

        </el-form-item>
        <!-- 头图链接 -->
        <el-form-item
          label="头图链接："
          prop="imgLink"
        >
          <el-input
            v-model="firstLevel.imgLink"
            style="width:300px"
          ></el-input>
          <el-button @click="addLink">添加链接</el-button>
        </el-form-item>
      </el-form>
      <el-card class="save_card">
        <el-button
          @click="save_one"
          type="primary"
          size="small"
        >保存</el-button>
      </el-card>
    </el-row>
    <!-- 二级分类 -->
    <el-row
      class="two"
      v-if="addSortRadio_radio == '2'"
    >
      <el-form
        :model="secondary"
        :rules="secondary_rules"
        ref="secondary"
        label-width="100px"
        class="secondary"
      >
        <!-- 一级分类 -->
        <el-form-item
          label="一级分类："
          prop=""
          :rules="[
             { required: true},
            ]"
        >
          <el-select
            v-model="secondary.sort"
            placeholder="请选择"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          <span
            @mouseover="showTips"
            @mouseleave="hiddenTips"
          >
            <el-link
              type="primary"
              :underline="false"
            >查看示例
            </el-link>
          </span>

          <section
            class="hover_show"
            v-if="isShowTips"
          >
            <img
              class="sort_img"
              :src="$imageHost+'/image/admin/addSort/sort.jpg'"
              alt="sort"
            >
          </section>
        </el-form-item>
        <!-- 分类名称 -->
        <el-form-item
          label="分类名称："
          prop="name"
        >
          <el-input
            v-model="secondary.name"
            style="width:300px"
          ></el-input>
        </el-form-item>
        <!-- 分类优先级 -->
        <el-form-item
          label="分类优先级："
          prop=""
        >
          <el-input
            v-model="secondary.name"
            style="width:300px"
          ></el-input>
          <span class="first_tips">可填写1到100间的整数，数字越大前端排列顺序越靠前。优先级重复，则按照分类添加时间排序，添加越早越靠前排列</span>
        </el-form-item>
        <!-- 分类图标 -->
        <el-form-item
          label="分类图标："
          prop=""
        >
          <el-link
            class="reviseLink"
            type="primary"
            :underline="false"
          >修改</el-link>
          <section
            class="sort_icon"
            :style="'background:url('+$imageHost+'/image/admin/sort_moren.png) center center / 70px 70px no-repeat'"
          >
            <span class="change_icon">更换图标</span>
          </section>
          <span class="tips">150*140</span>
        </el-form-item>
      </el-form>
      <el-card class="save_card">
        <el-button
          @click="save_two"
          type="primary"
          size="small"
        >保存</el-button>
      </el-card>
    </el-row>

  </div>
</template>
<script>
export default {
  data () {
    return {
      addSortRadio_radio: '1',
      // 一级分类
      firstLevel: {
        sortName: '',
        first: null,
        sortImg: ''
      },
      firstLevel_rules: {
        sortName: [
          { required: true, message: '请输入分类名称', trigger: 'blur' }
        ]
      },
      // 二级分类
      secondary: {
        sort: '',
        name: ''
      },
      secondary_rules: {
        name: [
          { required: true, message: '请输入分类名称', trigger: 'blur' }
        ]
      },
      options: [
        {
          lable: '服装',
          value: '服装'
        },
        {
          lable: '箱包',
          value: '箱包'
        },
        {
          lable: '彩妆',
          value: '彩妆'
        },
        {
          lable: '版衣',
          value: '版衣'
        }
      ],
      isShowTips: false
    }
  },
  created () {

  },
  methods: {
    addFirst () {
      // let params = {
      //   'sortName': '服装', // 分类名称
      //   'parentId': 0, // 父节点id,默认值0表示一级节点
      //   'level': 0, // 0表示一级，1表示二级
      //   'sortImg': 'https://s2.ax1x.com/2019/08/06/efLsm9.jpg', // 分类图标
      //   'imgLink': 'pages/storelist/storelist', // 图标链接
      //   'first': 8, // 优先级 0-100
      //   'type': 0, // 0普通分类，1推荐分类
      //   'sortDesc': ``// 分类说明，不写建议传空字符串
      // }

      // addSort(params).then(res => console.log(res)).catch(err => console.log(err))
    },
    addImg () {
      alert('添加图片')
    },
    addLink () {
      alert('添加链接')
    },
    showTips () {
      this.isShowTips = true
    },
    hiddenTips () {
      this.isShowTips = false
    },
    save_one () {
      this.addFirst()
    },
    save_two () {
      // let params = {
      //   'sortName': '男装', // 分类名称
      //   'parentId': 0, // 父节点id,默认值0表示一级节点
      //   'level': 1, // 0表示一级，1表示二级
      //   'sortImg': 'https://s2.ax1x.com/2019/08/06/efOp0s.jpg', // 分类图标
      //   'imgLink': 'pages/storelist/storelist', // 图标链接
      //   'first': 88, // 优先级 0-100
      //   'type': 0, // 0普通分类，1推荐分类
      //   'sortDesc': ``// 分类说明，不写建议传空字符串
      // }
      // addSort(params).then(res => { console.log(res) }).catch(err => console.log(err))
    }
  }

}
</script>
<style scoped>
.first_tips {
  color: #999;
  font-size: 12px;
}
.addImg {
  width: 230px;
  height: 90px;
}
.add_simple {
  max-width: 100%;
  max-height: 100%;
  vertical-align: middle;
}
.addSortRadio {
  margin-bottom: 20px;
}
.sort_img {
  width: 200px;
  height: 355.74px;
  border: 1px solid #eee;
  vertical-align: middle;
}
.hover_show {
  /* display: none; */
  margin-left: 250px;
  position: absolute;
  left: 68px;
  top: -45px;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  z-index: 3;
  box-shadow: 1px 1px 10px 5px #eee;
}

.sort_icon {
  float: left;
  width: 70px;
  height: 70px;
  border: 1px solid #e5e5e5;
  position: relative;
  margin-right: 10px;
}
.change_icon {
  position: absolute;
  width: 100%;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
  padding: 3px 0;
  font-size: 11px;
  text-align: center;
}
.reviseLink {
  float: left;
  color: #5a8bff;
  margin-right: 10px;
}
.tips {
  color: #999;
  height: 16px;
}
.save_card {
  background-color: #f8f8fa;
  position: fixed;
  bottom: 0;
  width: 85.4%;
  z-index: 3;
  margin-left: -21px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
}
.one {
  margin-bottom: 30px;
}
</style>
