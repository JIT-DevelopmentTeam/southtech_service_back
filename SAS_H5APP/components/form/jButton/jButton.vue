<template>
	<view>
		<button 
		:size="size" 
		:type="type" 
		:plain="plain" 
		:disabled="disabled"
		:loading="loading"
		:form-type="formType"
		@click="onClick">
			<slot></slot>
		</button>
	</view>
</template>

<script>
	export default {
		name: 'jButton',
		data() {
			return {
			}
		},
		props:{
			size: {/* 有效值：default/mini */
				type: String,
				default: 'default'
			},
			type: {/* 有效值：primary/primary/warn */
				type: String,
				default: 'default'
			},
			plain: {
				type: Boolean,
				default: false
			},
			disabled: {
				type: Boolean,
				default: false
			},
			loading: {
				type: Boolean,
				default: false
			},
			formType: {/* 有效值：submit/reset */
				type: String
			}
		},
		methods:{
			onClick() {
				if (this.formType != 'submit' || this.formType != 'reset') {
					this.$emit('onclick')
				}
			}
		}
	}
</script>

<style scoped>
	uni-button:after {
		border: 0;
	}
</style>
