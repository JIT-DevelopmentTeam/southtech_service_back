<template>
  <a-card ref="card" :title="title" :bordered="true" style="width: 300px; height: 300px" :hoverable="true" :loading="false"
          type="inner" :bodyStyle="bodyStyle">
    <a-checkbox-group @change="onChange" style="line-height: 2; display: block; text-align: left">
      <a-row v-for="(item, index) in list" :key="index">
        <a-col :span="24">
          <a-checkbox :value="item.name">{{ item.name }}</a-checkbox>
        </a-col>
      </a-row>
    </a-checkbox-group>
  </a-card>
</template>

<script>
  export default {
    name: "card",
    props: {
      title: {
        type: String
      },
      list: {
        type: Array
      }
    },
    data() {
      return {
        bodyStyle: {
          height: '252px',
          padding: '0px 24px',
          overflow: 'auto'
        },
        selectList: []
      }
    },
    methods: {
      onChange(checkedValues) {
        let currentClick = this.getArrDifference(checkedValues, this.selectList)
        let obj = this.list.filter(e => e.name === currentClick[0])[0]
        obj.checked = obj.checked ? false : true;

        this.selectList = checkedValues
        this.$emit('checkboxChange', obj);
      },
      getArrDifference(arr1, arr2) {
        return arr1.concat(arr2).filter((v, i, arr) => {
          return arr.indexOf(v) === arr.lastIndexOf(v);
        })
      }
    },
  }
</script>

<style scoped>

</style>