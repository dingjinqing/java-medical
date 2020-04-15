<template>
  <div
    :class="'store-multiple ' + myClass"
    :style="myStyle"
  >
    <label
      v-show="showTitle"
      class="label"
    >{{$t('storePermission.chooseStore')}}：</label>
    <div class="content">
      <el-select
        size="small"
        v-model="selectId"
        @change="selectChangeHandle"
      >
        <el-option
          v-for="item in unSelectedOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <div class="con-bottom">
        <ul>
          <li
            class="select-item"
            v-for="(item,index) in selectedOptions"
            :key="index"
          >
            <i
              class="close-icon el-icon-error"
              @click="deleteSelectHandle(item, index)"
            ></i>
            <span>{{item.label}}</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    myClass: {
      type: String,
      default: ''
    },
    myStyle: String,
    storeDatas: Array,
    selectedIds: Array,
    showTitle: Boolean
  },
  data () {
    return {
      unSelectedOptions: [],
      selectedOptions: [],
      selectId: ''
    }
  },
  watch: {
    selectedOptions: function (newVal, oldVal) {
      console.log(newVal)
      let selectIds = []
      if (newVal && newVal.length > 0) {
        selectIds = newVal.map(item => item.value)
      }
      console.log('ids:', selectIds)
      this.$emit('update:selectedIds', selectIds)
    }
  },
  mounted () {
    console.log(this.storeDatas)
    console.log(this.selectedIds)
    let selects = []
    let unselects = []
    this.storeDatas.forEach(item => {
      if (this.selectedIds.includes(item.value)) {
        console.log(item)
        selects.push(item)
      } else {
        unselects.push(item)
      }
    })
    this.selectedOptions = selects
    this.unSelectedOptions = unselects
  },
  updated () {
    console.log('update....')
  },
  methods: {
    selectChangeHandle (val) {
      if (val) {
        let index = this.unSelectedOptions.findIndex(item => item.value === val)
        let item = this.unSelectedOptions.splice(index, 1)
        this.selectId = ''
        this.selectedOptions.push(...item)
      }
    },
    deleteSelectHandle (item, index) {
      console.log(item, index)
      this.selectedOptions.splice(index, 1)
      this.unSelectedOptions.push(item)
    }
  }
}
</script>

<style lang="scss" scoped>
.store-multiple {
  position: relative;
  overflow: hidden;
  display: flex;
  .label {
    line-height: 30px;
  }
  .content {
    flex: 1;
    margin-left: 4px;
    .el-select {
      width: 170px;
    }
    .select-item {
      position: relative;
      display: inline-block;
      padding: 0px 5px;
      line-height: 26px;
      border: 1px solid #dcdfe6;
      margin-right: 10px;
      margin-top: 2px;
      margin-bottom: 8px;
      font-size: 12px;
    }
    .close-icon {
      position: absolute;
      top: -5px;
      right: -5px;
      cursor: pointer;
      color: #d1d1d1;
      font-size: 14px;
    }
    .con-bottom {
      margin-top: 10px;
    }
  }
}
</style>
