<template>
  <div ref="gantt"></div>
</template>

<script>
import {gantt} from 'dhtmlx-gantt';
import "../../node_modules/dhtmlx-gantt/codebase/locale/locale_cn.js"
export default {
  name: 'Gantt',
  components:{gantt},
  props: {
    tasks: {
      type: Object,
      default () {
        return {data: [], links: []}
      }
    }
  },

  mounted: function () {
    console.log(gantt.config);
    gantt.config.xml_date = "%Y-%m-%d";
    gantt.config.drag_links = false;
    gantt.config.drag_resize = false;
    gantt.init(this.$refs.gantt)
    gantt.parse(this.$props.tasks)
  }
}
</script>

<style>
    @import "../../node_modules/dhtmlx-gantt/codebase/dhtmlxgantt.css";
</style>