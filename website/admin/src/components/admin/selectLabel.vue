<template>
  <div class="container">
    <label class="flex-block">
      <span
        class="default-title"
        :class="titleClass?titleClass:'default-title-class'"
      >
        <slot name="title"></slot>
      </span>
      <el-select
        v-model="selected"
        @change="selectChange"
        style="width:170px"
        size="small"
        :placeholder="placeholder"
      >
        <el-option
          v-for="(item,index) in selectDatas"
          :key="index"
          :label="item[selectLabel]"
          :value="item[selectValue]"
        ></el-option>
      </el-select>
    </label>
    <div class="flex-block has-selected">
      <span
        class="default-title"
        :class="titleClass?titleClass:'default-title-class'"
      >已选择：</span>
      <ul class="label-list">
        <li
          v-for="(item, index) in selectLabels"
          :key="item[selectValue]"
          class="label-item"
        >
          <span
            class="iconfont el-icon-error"
            @click="deleteLabel(item, index)"
          ></span>
          <span v-text="item[selectLabel]"></span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  model: {
    prop: 'select',
    event: 'change'
  },
  props: {
    select: {
      type: Array, // id数组
      default: () => []
    },
    // 所有选项数据[对象数组]
    datas: Array,
    // 选项的label
    selectLabel: {
      type: String,
      default: 'name'
    },
    // 选项的value
    selectValue: {
      type: String,
      default: 'id'
    },
    placeholder: String,
    titleClass: String
  },
  data () {
    return {
      selectDatas: [],
      selectLabels: [],
      selected: ''
    }
  },
  watch: {
    datas: {
      handler: function (newVal) {
        console.log('datas: ', newVal, 'selects:', this.selects)
        let select = this.select || []
        let selectDatas = []
        let selectLabels = []
        if (newVal.length > 0) {
          newVal.forEach(item => {
            if (select.indexOf(item[this.selectValue]) > -1) {
              selectLabels.push(item)
            } else {
              selectDatas.push(item)
            }
          })
          console.log('selectDatas:', selectDatas)
          this.selectDatas = selectDatas
          this.selectLabels = selectLabels
        }
      },
      immediate: true
    },
    select: {
      handler: function (newVal) {
        console.log('datas: ', this.datas, 'selects:', this.selects)
        if (!newVal) newVal = []
        let datas = this.datas
        let selectDatas = []
        let selectLabels = []
        if (datas.length > 0) {
          datas.forEach(item => {
            if (newVal.indexOf(item[this.selectValue]) > -1) {
              selectLabels.push(item)
            } else {
              selectDatas.push(item)
            }
          })
          this.selectDatas = selectDatas
          this.selectLabels = selectLabels
        }
      }
    }
  },
  methods: {
    selectChange (val) {
      let index = this.selectDatas.findIndex(item => item[this.selectValue] === val)
      let select = this.selectDatas.splice(index, 1)
      this.selected = ''
      this.selectLabels.push(...select)
      this.selectLabelChange()
    },
    deleteLabel (item, index) {
      let select = this.selectLabels.splice(index, 1)
      this.selected = ''
      this.selectDatas.push(...select)
      this.selectLabelChange()
    },
    selectLabelChange () {
      let selectIds = this.selectLabels.map(item => item[this.selectValue])
      console.log(selectIds)
      this.$emit('change', selectIds)
    }
  }
}
</script>

<style lang="scss" scoped>
.has-selected {
  padding: 10px 0;
}
.label-list {
  display: inline-block;
}
.label-item {
  display: inline-block;
  border: 1px solid #ccc;
  position: relative;
  padding: 0 10px;
  border-radius: 4px;
  margin-right: 10px;
  margin-top: 5px;
}
.el-icon-error {
  position: absolute;
  right: -7px;
  top: -7px;
  color: #ccc;
  cursor: pointer;
}
.flex-block {
  display: flex;
}
.default-title {
  display: inline-block;
  text-align: right;
}
.default-title-class {
  width: 70px;
  flex: 0 0 70px;
}
</style>
