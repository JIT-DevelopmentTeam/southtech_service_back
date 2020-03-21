<template>
  <div class="card">
    <div :style="{ borderBottom: '1px solid #E9E9E9',  }">
      <a-row>
        <a-col :span="8">
          <a-checkbox :indeterminate="indeterminate" @change="onCheckAllChange" :checked="checkAll">
            全选
          </a-checkbox>
        </a-col>
        <a-col :span="8" :style="{fontWeight: 'bold'}">{{ title }}</a-col>
        <a-col :span="8"></a-col>
      </a-row>
    </div>
    <div class="content" @scroll="hscroll($event)">
      <a-spin :spinning="spinning">
        <a-checkbox-group @change="onChange" v-model="checkedList"
                          style="line-height: 2; display: block; text-align: left; margin: 0 20px;">
          <a-list itemLayout="horizontal" :dataSource="dataSource" :split="false">
            <a-list-item slot="renderItem" slot-scope="item, index">
              <a-checkbox :value="item.clientName ? item.clientName : item.realname">{{ item.clientName ? item.clientName : item.realname }}</a-checkbox>
            </a-list-item>
          </a-list>
          <!--        <a-row v-for="(item, index) in list" :key="index">-->
          <!--          <a-col :span="24">-->
          <!--            <a-checkbox :value="item.name">{{ item.name }}</a-checkbox>-->
          <!--          </a-col>-->
          <!--        </a-row>-->
        </a-checkbox-group>
      </a-spin>
    </div>
  </div>
</template>

<script>
  import {getAction} from '@/api/manage'

  export default {
    name: "card",
    props: {
      title: {
        type: String
      },
      url: {
        type: Object
      },
      value: {
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
        checkedList: [],
        indeterminate: false,
        checkAll: false,
        pageNo: 1,
        pageSize: 10,
        columns: [],
        dataSource: [],
        spinning: false,
        total: 0
      }
    },
    methods: {
      onChange(checkedList) {
        this.indeterminate = !!checkedList.length && checkedList.length < this.dataSource.length;
        this.checkAll = checkedList.length === this.dataSource.length;
        const selectList = this.dataSource.filter(e => checkedList.includes(e.clientName ? e.clientName : e.realname))
        this.$emit('input', selectList)
      },
      onCheckAllChange(e) {
        let list = this.dataSource
        let checkedList = []
        for (let i = 0; i < list.length; i++) {
          checkedList.push(list[i].clientName ? list[i].clientName : list[i].realname)
        }
        Object.assign(this, {
          checkedList: e.target.checked ? checkedList : [],
          indeterminate: false,
          checkAll: e.target.checked,
        });
        const result = e.target.checked ? this.dataSource.slice() : [];
        this.$emit("input", result)
      },
      hscroll(e) {
        //变量scrollTop是滚动条滚动时，距离顶部的距离
        var scrollTop = parseInt(e.target.scrollTop);
        //变量windowHeight是可视区的高度
        var windowHeight = e.target.clientHeight;
        //变量scrollHeight是滚动条的总高度
        var scrollHeight = e.target.scrollHeight;
        var loading = false;
        //滚动条到底部的条件
        if (scrollTop + windowHeight == scrollHeight) {
          if (this.total != this.dataSource.length) {
            if (loading) return;
            loading = true;
            this.spinning = true;
            //写后台加载数据的函数
            console.log("距顶部" + scrollTop + "可视区高度" + windowHeight + "滚动条总高度" + scrollHeight);
            setTimeout(() => {
              this.pageNo += 1;
              this.loadData(this.pageNo);
              loading = false;
              this.spinning = false;
            }, 2000)
          }

        }
      },
      loadData(pageNo) {
        getAction(this.url.list, {pageNo: pageNo})
          .then(res => {
            if (res.success) {
              this.total = res.result.total;
              let list = res.result.records;
              list.forEach(item => {
                this.dataSource.push(item);
              })
            }
            if(res.code===510){
              this.$message.warning(res.message)
            }
          })
      }
    },
    created() {
      this.loadData(this.pageNo)
    }
  }
</script>

<style scoped>
  .card {
    border: 1px solid rgb(233, 233, 233);
    width: 300px;
    height: 300px;
  }

  .content {
    height: 250px;
    overflow: auto;
  }
</style>
