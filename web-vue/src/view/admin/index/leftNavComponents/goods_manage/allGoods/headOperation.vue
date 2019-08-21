<template>
  <!-- 头部操作组件 -->
  <div class="headOperation">
    <el-form
      :inline="true"
      :model="formData"
    >
      <!-- 商品名称 -->
      <el-form-item label="商品名称：">
        <el-input
          style="width:150px"
          size="small"
          placeholder="搜索商品"
          v-model="goodsName"
          class="input-with-select"
        >
          <el-button
            slot="append"
            size="small"
            icon="el-icon-search"
          ></el-button>
        </el-input>
      </el-form-item>
      <!-- 平台分类 -->
      <el-form-item label="平台分类：">
        <el-select
          style="width:150px"
          size="small"
          v-model="value1"
          filterable
          placeholder="请选择平台分类"
        >
          <el-option
            v-for="item in options1"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 商家分类 -->
      <el-form-item label="商家分类:">
        <el-select
          style="width:150px"
          size="small"
          v-model="value2"
          filterable
          placeholder="请选择商家分类"
        >
          <el-option
            v-for="item in options2"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 商品标签 -->
      <el-form-item label="商品标签:">
        <el-select
          style="width:150px"
          size="small"
          v-model="value3"
          filterable
          placeholder="请选择商品标签"
        >
          <el-option
            v-for="item in options3"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 商品品牌 -->
      <el-form-item label="商品品牌:">
        <el-select
          style="width:150px"
          size="small"
          v-model="value4"
          filterable
          placeholder="请选择品牌"
        >
          <el-option
            v-for="item in options4"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 商品来源 -->
      <el-form-item label="商品来源:">
        <el-select
          style="width:150px"
          size="small"
          v-model="value5"
          filterable
          placeholder="请选择商品来源"
        >
          <el-option
            v-for="item in options5"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 活动类型 -->
      <el-form-item label="活动类型:">
        <el-select
          style="width:150px"
          size="small"
          v-model="value6"
          filterable
          placeholder="请选择活动类型"
        >
          <el-option
            v-for="item in options6"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 商家时间 -->
      <el-form-item label="上架时间:">
        <el-date-picker
          size="small"
          v-model="value7"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <!-- 价格 -->
      <el-form-item label="价格：">
        <el-input
          size="small"
          style="width:150px"
        ></el-input>至
        <el-input
          size="small"
          style="width:150px"
        ></el-input>

      </el-form-item>

    </el-form>
    <el-button
      @click="handleSearch"
      type="primary"
      size="small"
    >查询</el-button>
    <el-button
      @click="handleExport"
      type="primary"
      size="small"
    >导出商品</el-button>
    <el-button
      @click="handleAddGoods  "
      type="primary"
      size="small"
    >添加商品</el-button>
    <!-- 当前版本为旗舰版，不限制商品个数 -->
    <el-popover
      placement="bottom"
      width="400"
      trigger="hover"
    >
      <section>体验版最多创建10个商品 ，基础版最多创建500个商品 ，高级版不限制商品个数 ，旗舰版不限制商品个数</section>
      <el-divider></el-divider>
      <section class="to_more">
        <el-button
          @click="handleUnderstandMore"
          size="small"
          type="primary"
        >了解更多</el-button>
      </section>
      <span
        slot="reference"
        class="now"
      >当前版本为旗舰版，不限制商品个数
        <img
          :src="system_icon"
          alt="system_icon"
        >
      </span>
    </el-popover>
  </div>
</template>
<script>
export default {
  name: 'headOperation',
  // 数据data
  data () {
    return {
      system_icon: this.$imageHost + '/image/admin/system_icon.png',
      formData: {},
      goodsName: ``,
      options1: [],
      value1: `请选择平台分类`,
      options2: [],
      value2: '',
      options3: [],
      value3: '',
      options4: [],
      value4: '',
      options5: [],
      value5: '',
      options6: [],
      value6: '',
      value7: ''
    }
  },
  // Vue生命周期钩子函数
  mounted () {

  },
  // methods 方法
  methods: {
    // 查询
    handleSearch () {

    },
    // 导出商品
    handleExport () {

    },
    // 添加商品
    handleAddGoods () {

    },
    // 了解更多
    handleUnderstandMore () {

    }
  }
}
</script>

<style lang="css" scoped>
/* 样式文件 要加scoped作用于当前的组件，方式样式污染 */
.now {
  color: #999;
  font-size: 12px;
}
.to_more {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
