<template>
  <div>
    <el-button
      type="text"
      @click="this.outerVisible = true"
    >点击打开外层 Dialog</el-button>

    <el-dialog
      title="外层 Dialog"
      :visible.sync="this.outerVisible"
    >
      <el-dialog
        width="30%"
        title="内层 Dialog"
        :visible.sync="this.innerVisible"
        append-to-body
      >
      </el-dialog>
      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="outerVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="this.innerVisible = true"
        >打开内层 Dialog</el-button>
      </div>
    </el-dialog>
    <el-checkbox-group></el-checkbox-group>
    <locat-t-p></locat-t-p>
  </div>
</template>

<script>
import { Component, Vue, Prop } from 'vue-property-decorator'
import LocatTP from './LocatTP'
@Component({
  components: { LocatTP }
})
class locationTP extends Vue {
  @Prop({ type: Array, default: [] }) locationList;
  outerVisible = false;
  innerVisible = false;
  // initial data
  msg = 123
  tableData = [
    { area: '指定配送区域和运费' }
  ]

  // // use prop values for initial data
  // helloMsg = 'Hello, ' + this.propMessage

  // lifecycle hook
  mounted () {
    this.getData()
  }

  // // computed
  // get computedMsg () {
  //     return 'computed ' + this.msg
  // }

  // method
  getData () {
    // console.log(this.locationList);
  }
}
export default locationTP
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 5px;
}

a {
  color: #42b983;
}
</style>
