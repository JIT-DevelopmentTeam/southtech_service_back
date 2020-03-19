<template>
  <div itemLayout="vertical">
    <div id="card" v-for="(item, index) in list" :key="index">
      <a-card>
        <a-row>
          <a-col :span="12">
            <span class="title">{{ item.title }}</span>
          </a-col>
          <a-col :span="12" class="status">
            <span>{{ item.status }}</span>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <span>{{ item.customer }}</span>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="18">
            <span>报障时间：{{ item.upTime }}</span>
          </a-col>
          <a-col :span="6">
            <span class="time">过去4小时</span>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-icon type="environment" theme="filled"/>
            <span>{{ item.address }}</span>
          </a-col>
        </a-row>
      </a-card>
    </div>
  </div>
</template>

<script>
  export default {
    name: "listCard",
    props: {
      list: {
        type: Array
      }
    }
  }
</script>

<style scoped>
  .title {
    font-weight: bold;
    font-size: larger;
  }

  .status {
    text-align: right;
  }

  .time {
    color: #f5222d; font-size: xx-small;
  }

  #card .ant-card-body {
    padding: 20px !important;
  }
</style>