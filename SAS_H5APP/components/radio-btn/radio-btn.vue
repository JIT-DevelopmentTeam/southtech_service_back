<template>
	<radio-group @change="radioChange">
		<label class="uni-list-cell uni-list-cell-pd" v-for="(item, index) in items" :key="item.value">
			<view class="big">
				<view class="sameLine label">{{item.name}}</view>
				<view class="sameLine">
					<radio class="radioBtn" :value="item.value" :checked="item.checked" :disabled="stageStatus == 1 || type == 'isQGP' ? true : false" />
				</view>
			</view>
		</label>
	</radio-group>
</template>

<script>
	export default {
		name: 'radio',
		data() {
			return {
			}
		},
		components: {
		},
		props: {
			items: Array,
			stageStatus: String,
			type: String
		},
		computed: {
		},
		methods: {
			radioChange: function(evt) {
				for (let i = 0; i < this.items.length; i++) {
					if (this.items[i].value === evt.target.value) {
						this.items[i].checked = true
						break;
					}
				}
				this.$emit('radioChange', evt.target.value)
			}
		}
	}
</script>

<style>
	.big {
		width: 100%;
	}
	
	.sameLine {
		display: inline;
		margin-right: 15upx;
	}
	
	.label {
		font-size: 30upx;
		display: inline-block;
	}
	
	.radioBtn {
		position: absolute;
		right: 50rpx;
		margin-top: -4rpx;
	}
</style>
