<template>
	<view>
		<view class="uni-padding-wrap uni-common-mt star" v-if="1===1">
			<view class="label">响应速度</view>
			<star-template :starOptions="speed_list" :numIndex="1" v-on:choise-star="getSpeedScore"></star-template>			
			<view class="text">{{speed}}</view>
		</view>
		<view class="uni-padding-wrap uni-common-mt star" v-if="2===2">
			<view class="label">服务态度</view>
			<star-template :starOptions="attitude_list" :numIndex="2" v-on:choise-star="getAttitudeScore"></star-template>			
			<view class="text">{{attitude}}</view>
		</view>
		<view class="example-body">
			<view v-for="(item,index) in dataList">
				<view class="tag-view" >
					<uni-tag :inverted="true" :text="item.text" @click="bindClick(item.key,item.status)"/>
				</view>
			</view>
		</view>
		<view class="example-body" >
			<view class="uni-textarea">
				<textarea style="border:#EFEFF4 solid;" placeholder-style="color:#d9d9d9;" placeholder="请输入少于200字的意见反馈"/>
			</view>
		</view>
		<view class="btn"><button type="primary" >提交</button></view>
	</view>		
</template>

<script>
	export default {
		components: {
			uniIcons: () => import('@dcloudio/uni-ui/lib/uni-icons/uni-icons.vue'),
			starTemplate: () => import('@/components/stars/stars.vue'),
			uniTag: () => import('@dcloudio/uni-ui/lib/uni-tag/uni-tag.vue')
		},
		props:{
			
		},
		data() {
			return {
				speed_list:[false,false,false,false,false] ,//对应星星个数
				attitude_list:[false,false,false,false,false], //对应星星个数
				speed:"",
				attitude:"",
				dataList:[],
				list1:[
						{key:"1",text:"服务态度差",status:0},
						{key:"2",text:"未解决",status:0},
						{key:"3",text:"现场解决认真",status:0}
					],
				list2:[
						{key:"1",text:"服务态度好",status:1},
						{key:"2",text:"热情",status:1},
						{key:"3",text:"和谐",status:1},
						{key:"4",text:"和谐",status:1},
					]
			}
		},
		methods: {
			getSpeedScore(num,numIndex){
				switch(num){
					case 1: this.speed = "非常不满意";break;
					case 2: this.speed = "不满意"; break;
					case 3: this.speed = "一般"; break;
					case 4: this.speed = "满意"; break;
					case 5: this.speed = "非常满意"; break;
				}
			},
			getAttitudeScore(num){
				switch(num){
					case 1: this.attitude = "非常不满意";this.dataList =this.list1 ;break;
					case 2: this.attitude = "不满意"; this.dataList =this.list1 ;break;
					case 3: this.attitude = "一般"; this.dataList =this.list1 ; break;
					case 4: this.attitude = "满意"; this.dataList =this.list2 ;break;
					case 5: this.attitude = "非常满意";this.dataList =this.list2 ; break;
				}
			},
			bindClick(key,status){
				if(status=== 0){
					console.log("一般以下的评价 key :"+key+",status :"+status);	
				}else{
					console.log("一般以上的评价 key :"+key+",status :"+status);	
				}
			}
		},
	}
	
</script>

<style lang="less">
	.star.uni-common-mt{
		box-sizing: border-box;
		width: 100%;
		overflow: hidden;
		margin-top: 20upx;
		.flex-item{
			display: inline-block;
			width: 12%;
			color: #999;
			padding: 0 18upx;
			.starIcon{
				font-size: 52upx;
			}
		}
		.starActive{
			color: #ff8000;
		}
	}
	.sameLine{
		display: inline-block;
	}
	.text{
		color: #F0AD4E;
		font-size: 24upx;
		margin-left: 32%;
	}
	.label {
		font-size: 30upx;
		display: inline-block;
		padding: 0 8%;
	}
	
	.example-body {
		/* #ifndef APP-PLUS-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
		padding: 20rpx;
	}
	
	.tag-view {
		/* #ifndef APP-PLUS-NVUE */
		display: flex;
		/* #endif */
		flex-direction: column;
		margin: 10rpx 15rpx;
		justify-content: center;
	}
	uni-textarea{
		width: 369px;
		height: 120px;
		display: block;
		position: relative;
		font-size: 16px;
		line-height: inherit;
		margin: 52rpx 18rpx;
	}
	.btn{
		width: 90%;
		padding: 5%;
	}
	/* uni-button {
	    color: #fff;
	    background-color: #82bcfb;
	}
	uni-button {
	    position: relative;
	    display: block;
	    margin-left: auto;
	    margin-right: auto;
	    padding-left: 14px;
	    padding-right: 14px;
	    box-sizing: border-box;
	    font-size: 18px;
	    text-align: center;
	    text-decoration: none;
	    line-height: 2.55555556;
	    border-radius: 10px;
	    -webkit-tap-highlight-color: transparent;
	    overflow: hidden;
	    color: hsla(0,0%,100%,.95);
	    background-color: #82bcfb;
	} */
</style>