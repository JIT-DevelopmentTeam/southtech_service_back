<template>
    <view class="uni-padding-wrap">
        <view class="uni-title">
            <text>故障申报</text>
        </view>
    	<form @submit="formSubmit" @reset="formReset">
    		<view class="uni-flex uni-column">
    			<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>编号:</view>
    				<input type="text" class="uni-input" placeholder="编号"/>
    			</view>
    			<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>客户</view>
					 <picker>
							<view class="uni-input"></view>
					</picker>
				</view>
    			<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>联系人</view>
					 <picker>
							<view class="uni-input"></view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>接入方式</view>
					 <picker>
							<view class="uni-input"></view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>紧急程度</view>
					 <picker>
							<view class="uni-input"></view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>客服</view>
					 <picker>
							<view class="uni-input"></view>
					</picker>
				</view>
				<view class="flex-item flex-item-V">
					<view class="title"><span style="color: red;">*</span>申报时间</view>
					 <view class="uni-list-cell">
						 <view class="uni-list-cell-db">
							 <picker mode="date" :value="date" :start="startDate" :end="endDate" @change="bindDateChange">
								 <view class="uni-input">{{date}}</view>
							 </picker>
						 </view>
					 </view>
				</view>
				<br>
				<view class="flex-item flex-item-V">	
					<uni-list>
					    <uni-list-item title="设备编号" :show-arrow="false">
							<picker style="display: inline;">
									<view class="uni-input"></view>
							</picker>
						</uni-list-item>
					    <uni-list-item title="标题文字"></uni-list-item>
					    <uni-list-item title="标题文字" :show-badge="true" badge-text="12"></uni-list-item>
					    <uni-list-item title="禁用状态" :disabled="true" :show-badge="true" badge-text="12"></uni-list-item>
					</uni-list>
				</view>
    		</view>
    	</form>
    </view>
</template>

<script>
	import uniList from "@dcloudio/uni-ui/lib/uni-list/uni-list.vue"
	import uniListItem from "@dcloudio/uni-ui/lib/uni-list-item/uni-list-item.vue"
	import uniCard from "@dcloudio/uni-ui/lib/uni-card/uni-card.vue"
	
	export default {
		components:{
			uniCard,
			uniList,
			uniListItem
		},
		data() {
			const currentDate = this.getDate({
				format: true
			})
			return {
				title: 'picker',
				array: ['中国', '美国', '巴西', '日本'],
				index: 0,
				date: currentDate,
				time: '12:01',
				validatorRules:{
					number:{rules: [{ required: true, message: '请输入编号!' }]}	
				},
			}
		},
		computed: {
			startDate() {
				return this.getDate('start');
			},
			endDate() {
				return this.getDate('end');
			}
		},
		methods: {
			 formSubmit: function(e) {
				console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
				var formdata = e.detail.value
				uni.showModal({
					content: '表单数据内容：' + JSON.stringify(formdata),
					showCancel: false
				});
			},
			formReset: function(e) {
				console.log('清空数据')
			},
			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.index = e.target.value
			},
			bindDateChange: function(e) {
				this.date = e.target.value
			},
			bindTimeChange: function(e) {
				this.time = e.target.value
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
	
				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			}
		}
	}
</script>

<style>

</style>
