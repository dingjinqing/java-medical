<template>
  <li>
    <span @click="toggle">
      <em
        v-if="hasChild"
        class="icon"
      >{{open ? '-' : '+'}}</em>
      <em
        v-if="!hasChild"
        class="icon file-text"
      ></em>
      <el-checkbox v-model="checked"></el-checkbox>
      {{data.catName?data.catName:data.sortName }}
    </span>
    <ul
      v-show="open"
      v-if="hasChild"
    >
      <tree-item
        v-for="(item, index) in data.children"
        :data="item"
        :key="index"
        :checkedParent='checked'
      ></tree-item>
    </ul>
  </li>
</template>

<script>
export default {
  name: 'TreeItem',
  props: {
    data: {
      type: [Object, Array],
      required: true
    }
  },
  data () {
    return {
      open: false,
      checked: false
    }
  },
  computed: {
    hasChild () {
      return this.data.children && this.data.children.length
    }
  },
  watch: {
    checked (newData) {
      console.log(newData)
      let obj = {
        checked: newData,
        sortId: this.data.sortId ? this.data.sortId : this.data.catId
      }
      if (this.data.sortId) {
        this.$http.$emit('clickBusNode', obj)
      } else {
        this.$http.$emit('clickBrandNode', obj)
      }
    },
    '$parent.checked' (newData) {
      this.checked = newData
      console.log(newData)
    }
  },
  mounted () {
    console.log(this.$parent)
    console.log(this.data.sortId)
    this.$http.$on('addingBusBack', res => {
      console.log(res)
      res.filter(item => {
        console.log(this.data.sortId, item)
        if (`${this.data.sortId}` === item) {
          this.checked = true
        }
      })
      console.log(res)
    })
    this.type = false
  },
  methods: {
    toggle () {
      if (this.hasChild) {
        this.open = !this.open
      }
    }
  }
}
</script>

<style scoped lang="scss">
ul {
  list-style: none;
  margin: 10px 20px;
  li {
    padding: 3px 0;
    span {
      cursor: pointer;
      font-size: 14px;
      line-height: 20px;
    }
    &:visited {
      background: #fff;
    }
  }
  em.icon {
    display: inline-block;
    width: 20px;
    height: 20px;
    margin-right: 5px;
    background-repeat: no-repeat;
    vertical-align: middle;
  }
}
</style>
