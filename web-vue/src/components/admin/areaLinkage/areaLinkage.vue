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
        center
        title="选择区县"
        :visible.sync="innerVisible"
        append-to-body
      >
        <section class="area">
          <el-checkbox-group
            :value="item.areaDistrictId"
            v-for="(item) in this.areaDistrict "
            :key="item.areaDistrictId"
            v-model="checkAreaDistrictList"
          >
            <el-checkbox
              :label="item.districtName"
              @change="checkInAreaDistrictList"
              class="district"
            />

          </el-checkbox-group>
        </section>
        <div
          slot="footer"
          class="dialog-footer"
        >
          <el-button
            size="small"
            type="primary"
            @click="handleSelectDistrict()"
          >确定</el-button>
          <el-button
            size="small"
            @click="innerVisible = false"
          >取 消</el-button>

        </div>
      </el-dialog>
      <!-- 配送区域的数据 -->
      <section class="dialog_body">
        <!-- 全选 -->
        <section class="checkAll">
          <el-checkbox
            @change="handleCheckAllChange"
            v-model="checkAll"
          >全选</el-checkbox>
        </section>
        <!-- 省市列表 -->
        <section class="group">

          <section
            class="row"
            v-for="(p) in  province"
            :key="p.provinceId"
          >
            <section class="group_left">
              <el-checkbox>{{p.provinceName}}</el-checkbox>
            </section>
            <section class="group_right">
              <section
                class="right_row"
                v-for="(city) in  p.areaCity"
                :key="city.cityId"
              >
                <el-checkbox class="city">{{city.cityName}}</el-checkbox>
                <span
                  v-show="showCount"
                  style="color:#ff0000"
                >({{city.areaDistrict.length}})</span>

                <img
                  @click="handleChooseArea(city)"
                  :src="`${$imageHost}/image/admin/expand.png`"
                  alt=""
                  style="cursor: pointer; position:absolute;right:-8px;top:14px;"
                >

              </section>
            </section>
          </section>

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
      showCount: false,
      innerVisible: false,
      isIndeterminate: true, // 一般用于实现全选的效果
      // 全选
      checkAll: false,
      // 选中的省
      checkedProvince: [],
      // 省份
      province: [],
      citys: [],
      areaDistrict: [],
      checkedAreaDistrict: false,
      checkAreaDistrictList: []
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
      // this.fetchAreaData()
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
          this.formatData(content)
        }
      }).catch(err => console.log(err))
    },
    // 处理省市区数据
    formatData (content) {
      this.province = content
      let citys = []
      content.forEach((item, i) => {
        citys.push({
          areaCity: item['areaCity']
        })
      })

      this.citys = citys
    },
    // 全选时候
    handleCheckAllChange (val) {
      switch (val) {
        case true: this.showCount = true
          break
        case false: this.showCount = false
      }
    },
    handleCheckedChange (val) {

    },
    // 单个点击
    handleChange (val) {

    },
    // 选择区县
    handleChooseArea (city) {
      this.innerVisible = true
      this.areaDistrict = city['areaDistrict']
    },
    handleSelectDistrict (val) {
    },
    checkInAreaDistrictList () {
      let len = this.checkAreaDistrictList.length
      if (len === this.areaDistrict.length) {
      } else {
        console.log(this.checkAreaDistrictList)
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.dialog_body {
  height: 50vh;
  overflow: auto;
}
.selectAll {
  margin-bottom: 10px;
}
.group {
  border: 1px solid red;
  overflow: hidden;
  width: 100%;
}
.row {
  min-height: 40px;
  display: flex;
}
.group_left {
  min-width: 140px;
  display: flex;
  align-items: center;
}
.group_right {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  padding: 2px;
}
.city {
  margin: 6px 0px;
  margin-left: 15px;
}
.right_row {
  position: relative;
}
.area {
  background-color: #ffffee;
  min-height: 40px;
  padding: 5px 0;
}
.btn_group {
  display: flex;
  justify-content: flex-end;
}
.district {
  margin: 0 5px;
}
</style>
