<template>
	<view class="uni-flex uni-row sameLine">
		<view :class="{starActive:item}" @click="choise(index)" class="flex-item iconfont" v-for="(item,index) in list">
			<view v-if="item" class="iconfont iconxing starIcon"/>
			<view v-else class="iconfont iconxing1 starIcon"/>
		</view>
	</view>
</template>

<script>
	export default {
		components: {
		},
		props:{
			starOptions: {
				type: Array,
				default () {
					return []
				}
			} // 数据
		},
		data() {
			return {
				list:this.starOptions
			}
		},
		methods: {
			//点击选择
			choise(num){
				// num 为点击的星星在数组中的下标
				this.list=[false,false,false,false,false];
				num=num+1;
				for(let i= 0 ; i < num ; i++){
					this.list[i]=true;
				}
				//将分数传给父组件
				this.$emit('choise-star',num);
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
</style>