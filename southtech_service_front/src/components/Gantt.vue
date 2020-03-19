<template>
  <div ref="gantt"></div>
</template>

<script>
import {gantt} from 'dhtmlx-gantt';
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