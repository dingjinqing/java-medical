<template>
  <div class="container">
    <!-- 外层的弹窗 -->
    <el-dialog
      title="请选择配送区域"
      :visible.sync="outerVisible"
      :before-close="closeDialog"
      center
    >
      <el-dialog
        width="40%"
        title="选择区县"
        :visible.sync="innerVisible"
        append-to-body
      >
      </el-dialog>
      <!-- 配送区域的数据 -->
      <section class="dialog_body">
        <!-- 全选 -->
        <section class="selectAll">
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
          >全选</el-checkbox>
        </section>
        <!-- 省份组 -->
        <section class="provinceGroup">
          <el-checkbox-group
            v-model="checkedProvince"
            @change="handleCheckedProvinceChange"
          >
            <section
              :class="i%2===0?'white':'gray'"
              v-for="(p,i) in province"
              :key="p.provinceId"
            >
              <el-checkbox :label="p.provinceName">{{p.provinceName}}</el-checkbox>
            </section>

          </el-checkbox-group>
        </section>
      </section>
      <!-- 确定|取消按钮 -->
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          size="small"
          @click="handleOuterSave"
        >确定</el-button>
        <el-button
          @click="handleOutercancel"
          size="small"
        > 取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getAreaSelect } from '@/api/admin/goodsManage/deliverTemplate/deliverTemplate.js'
export default {
  // 组件名
  name: 'areaLinkage',
  props: {
    outerVisible: {
      type: Boolean
    }
  },
  // data 数据
  data () {
    return {
      innerVisible: false,
      isIndeterminate: true, // 一般用于实现全选的效果
      checkAll: false,
      checkedProvince: [],
      province: []
    }
  },
  // 生命周期钩子
  create () {
  },
  mounted () {
    this.fetchAreaData()
  },
  // 方法
  methods: {
    // 点击外层确定按钮
    handleOuterSave () {
      this.fetchAreaData()
    },
    // 点击外层取消按钮
    handleOutercancel () {
      this.$emit('update:outerVisible', false)
    },
    // 关闭外层的弹窗
    closeDialog () {
      this.$emit('update:outerVisible', false)
    },
    // 获取区域代码弹窗
    fetchAreaData () {
      getAreaSelect().then(res => {
        const { error, content } = res
        if (error === 0) {
          this.province = content
        }
      }).catch(err => console.log(err))
    },
    // 全选的方法
    handleCheckAllChange (val) {

    },
    //
    handleCheckedProvinceChange () {

    }
  }
}
</script>
<style lang="scss" scoped>
.white {
  min-height: 40px;
  background-color: #fff;
  display: flex;
  align-items: center;
}
.gray {
  min-height: 40px;
  background-color: #f9f9f9;
  display: flex;
  align-items: center;
}
.dialog_body {
  background-color: skyblue;
}
</style>
