<template>
	<view>
		<uni-grid :column="3">
		    <uni-grid-item>
		        <view style="text-align: center;line-height: 300rpx;"><image src="../../static/mytask1.png" style="width: 35px;height: 35px;"></image></view>
		    </uni-grid-item>
		    <uni-grid-item>
		        <view style="text-align: center;line-height: 300rpx;"><image src="../../static/porfile1.png" style="width: 35px;height: 35px;"></image></view>
		    </uni-grid-item>
		    <uni-grid-item>
		        <view style="text-align: center;line-height: 300rpx;"><image src="../../static/workbranch1.png" style="width: 35px;height: 35px;"></image></view>
		    </uni-grid-item>
		</uni-grid>
	</view>
</template>

<script>
	
	import uniGrid from "@dcloudio/uni-ui/lib/uni-grid/uni-grid.vue"
	import uniGridItem from "@dcloudio/uni-ui/lib/uni-grid-item/uni-grid-item.vue"
	
	
	export default {
		components:{
			uniGrid,
			uniGridItem
		},
		data() {
			return {
			}
		},
		created() {
			alert(this.$store.dispatch("GET_WECHAT_OPENID"));
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
