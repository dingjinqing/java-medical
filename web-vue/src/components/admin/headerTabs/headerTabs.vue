<template>
  <div class="headerTabs">
    <section class="container">
      <el-tabs
        v-model="activeName"
        @tab-click="handleClick"
      >
        <el-tab-pane
          v-for="(item, index) in labels"
          :key="index"
          :label="item['label']"
          :name="item.name"
        >
        </el-tab-pane>

      </el-tabs>
    </section>
  </div>
</template>
<script>
export default {
  name: 'headerTabs',
  props: {
    labels: {
      type: Array
    }
  },
  data () {
    return {
      index: ``,
      activeName: this.labels[0]['name']
    }
  },
  mounted () {
    this.$emit('index', this.activeName)
  },
  computed: {
    indexTab () {
      return this.index
    }
  },
  methods: {
    // tab 被选中时触发
    handleClick (tab, event) {
      this.index = tab['index']
      this.$emit('index', this.indexTab)
    }
  }
}
</script>
