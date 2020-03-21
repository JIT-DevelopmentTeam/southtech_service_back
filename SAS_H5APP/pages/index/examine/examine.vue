<template>
	<view class="content">
		<view class="uni-list">
			<checkbox-group @change="checkboxChange">
				<label class="uni-list-cell uni-list-cell-pd" v-for="item in items" :key="item.id">
					<view>
						<checkbox :value="item.id" :checked="isChecked.indexOf(item.id) > -1" />
					</view>
					<view class="name">{{item.name}}</view>
					<view class="company">
						<div>公司名称：</div>
						<div class="companyName">{{item.client.name}}</div>
					</view>
				</label>
			</checkbox-group>
		</view>
		<view class="seize"></view>
		<view class="foot">
			<view class="bottom">
				<checkbox class="checked-all" @click="checkedAll" :checked="isCheckedAll"/>
				<button type="primary" class="confirm" @click="confirm">审核确认</button>
			</view>
		</view>
		
	</view>
</template>

<script>
	import {updateStatus} from '@/api/client_user.js'
	import qs from 'qs'
	
	export default {
		name: 'examine',
		data() {
			return {
				items: [],
				isChecked: [],
				isCheckedAll: false
			}
		},
		components: {
		},
		methods: {
			checkboxChange: function (e) {
				let items = this.items,
					values = e.detail.value;
				this.isChecked = values
				if (this.isChecked.length === this.items.length) {
					this.isCheckedAll = true
				} else {
					this.isCheckedAll = false
				}
			},
			checkedAll() {
				this.isCheckedAll = !this.isCheckedAll
				if (this.isCheckedAll) {
					this.isChecked = []
					for (let items of this.items) {
						this.isChecked.push(items.id)
					}
				} else {
					this.isChecked = []
				}
			},
			confirm() {
				if (this.isChecked.length === 0) {
					uni.showToast({
						title: "最少选择一个",
						duration: 1000,
						icon: "none",
						mask: true
					})
					return
				}
				updateStatus({ids: this.isChecked}).then(res => {
					if (res.status === 200) {
						uni.showToast({
							title: res.data.msg,
							duration: 1000,
							mask: true
						})
						this.$store.dispatch("clientUser/GetDataList").then(res => {
							this.items = res
						})
					}
				}).catch(err => {
					console.log(err);
				})
			}
		},
		onLoad() {
			this.$store.dispatch("clientUser/GetDataList").then(res => {
				this.items = res
			})
		}
	}
</script>

<style scoped>
	.name {
		position: absolute;
		left: 55px;
	}
	
	.company {
		position: absolute;
		left: 135px;
	}
	
	.companyName {
		margin-left: 20px;
	}
	
	.bottom {
		display: grid;
		grid-template-columns: 15% 85%;
		background-color: #FFFFFF;
		padding: 10px 0;
	}
	
	.confirm {
		width: 90%;
	}
	
	.checked-all {
		text-align: center;
		margin-top: 8px;
		font-size: 15px;
	}
	
	.foot {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		z-index: 100;
	}
	
	.seize {
		height: 66px;
	}
</style>
